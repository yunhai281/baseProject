package com.boyuyun.base.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.boyuyun.base.util.ConstantUtil;
import com.dhcc.common.util.FileUtil;

/**
 * Created by wangjian on 2017-02-14.
 */
public class FileUploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static String basePath = "";
    static {
    	basePath = ConstantUtil.UPLOAD_FOLDER;
    }
    public FileUploadServlet(){
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = null;
        JSONArray ja = new JSONArray();
        try{
            String  userId =  req.getParameter("userId");
            if(!StringUtils.isNotBlank(userId)){
                userId = "default";
            }
            boolean isMultipart = ServletFileUpload.isMultipartContent(req);
            if (isMultipart) {
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                
                // 得到所有的表单域，它们目前都被当作FileItem
                List<FileItem> fileItems = upload.parseRequest(req);

                String id = "";//webuploader 上传时附带的ID
                String type = "";//文件类型
                String fileMd5 = "";//文件的MD5值
                String fileName = "";//上传用户的文件名称，比如：1.avi
                // 如果大于1说明是分片处理
                int chunks = 1;//所有的分片数量
                int chunk = 0;//第几块分片

                FileItem tempFileItem = null;//分片文件

                for (FileItem fileItem : fileItems) {
                    if (fileItem.getFieldName().equals("id")) {
                        id = fileItem.getString();
                    } else if (fileItem.getFieldName().equals("name")) {
                        fileName = new String(fileItem.getString().getBytes("ISO-8859-1"), "UTF-8");
                    } else if (fileItem.getFieldName().equals("type")) {
                        type = new String(fileItem.getString().getBytes("ISO-8859-1"), "UTF-8");
                    } else if (fileItem.getFieldName().equals("fileMd5")) {
                        fileMd5 = new String(fileItem.getString().getBytes("ISO-8859-1"), "UTF-8");
                    } else if (fileItem.getFieldName().equals("chunks")) {
                        chunks = Integer.parseInt(fileItem.getString());
                    } else if (fileItem.getFieldName().equals("chunk")) {
                        chunk = Integer.parseInt(fileItem.getString());
                    } else if (fileItem.getFieldName().equals("file")) {
                        tempFileItem = fileItem;
                    }
                }
                if (fileName.equals("")) {

                }
                if (chunks > 1) {
                    //分片处理
                    // 临时目录用来存放所有分片文件
                    String ext = fileName.substring(fileName.lastIndexOf("."));
                    String tempFileDir = basePath + File.separator + "attachment" + File.separator + "chrunk" + File.separator + fileMd5;
                    File parentFileDir = new File(tempFileDir);
                    if (!parentFileDir.exists()) {
                        parentFileDir.mkdirs();
                    }
                    // 分片处理时，前台会多次调用上传接口，每次都会上传文件的一部分到后台(默认每片为5M)
                    File tempPartFile = new File(parentFileDir, fileMd5 + "_" + chunk + ".part");
                    if (!tempPartFile.exists()) {
                        FileUtil.copyInputStreamToFile(tempFileItem.getInputStream(), tempPartFile);
                    }
                    // 是否全部上传完成
                    // 所有分片都存在才说明整个文件上传完成
                    boolean uploadDone = true;
                    for (int i = 0; i < chunks; i++) {
                        File partFile = new File(parentFileDir, fileMd5 + "_" + i + ".part");
                        if (!partFile.exists()) {
                            uploadDone = false;
                            break;
                        }
                    }
                    // 所有分片文件都上传完成
                    // 将所有分片文件合并到一个文件中
                    if (uploadDone && parentFileDir.exists()) {
                        //最新的保存路径（本地和hadoop),包含文件名称，相对路径
                        String newSavePath = getSavePath("resource", userId, fileName);
                        //目录
                        String filePath = newSavePath.substring(0, newSavePath.lastIndexOf("/"));
                        String newFileName = newSavePath.substring(newSavePath.lastIndexOf("/") + 1);
                        //真实保存目录
                        String savePath = basePath + filePath;
                        //文件的真实路径
                        String targetPath = basePath + newSavePath;
                        //真实文件
                        File destTempFile = new File(targetPath);
                        File dirPathFile = new File(savePath);
                        if (!dirPathFile.isDirectory()) {
                            dirPathFile.mkdirs();
                        }
                        for (int i = 0; i < chunks; i++) {
                            File partFile = new File(parentFileDir, fileMd5 + "_" + i + ".part");
                            FileOutputStream destTempfos = new FileOutputStream(destTempFile, true);
                            destTempfos.flush();
                            if (parentFileDir.exists()) {
                                FileUtils.copyFile(partFile, destTempfos);
                            }
                            destTempfos.close();
                        }
                        long fileSize = destTempFile.length();

                        // 删除临时目录中的分片文件
                        try {
                            if (parentFileDir.exists()) {
                                FileUtils.deleteDirectory(parentFileDir);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        JSONObject jsonObject = new JSONObject();
                        //1.视频处理，转码
                        jsonObject.element("filename", newSavePath);//相对保存路径
                        jsonObject.element("fileMd5", fileMd5);
                        jsonObject.element("name", fileName);    //文件原名
                        jsonObject.element("uploadname", newFileName);//上传后的名字
                        jsonObject.element("src", filePath);//上传后的相对目录
                        String fileType = newFileName.substring(newFileName.lastIndexOf(".") + 1);
                        jsonObject.element("type", fileType);//后缀类型
                        jsonObject.element("size", fileSize);//文件大小

                        ja.add(jsonObject);

                    } else {
                        //分片上传成功，不返回值
                    }
                } else {//直接上传，文件大小小于分片大小
                    //最新的保存路径（本地和hadoop),包含文件名称，相对路径
                    String newSavePath =getSavePath("resource", userId, fileName);
                    //目录
                    String filePath = newSavePath.substring(0, newSavePath.lastIndexOf("/"));
                    //真实保存目录
                    String newFileName = newSavePath.substring(newSavePath.lastIndexOf("/") + 1);
                    String savePath = basePath + filePath;
                    //文件的真实路径
                    String targetPath = basePath + newSavePath;
                    //真实文件
                    File destTempFile = new File(targetPath);
                    File dirPathFile = new File(savePath);
                    if (!dirPathFile.isDirectory()) {
                        dirPathFile.mkdirs();
                    }
                    FileUtil.copyInputStreamToFile(tempFileItem.getInputStream(), destTempFile);
                    //获取文件大小
                    long fileSize = destTempFile.length();
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.element("fileMd5", fileMd5);
                    jsonObject.element("filename", newSavePath);//相对保存路径
                    jsonObject.element("name", fileName);    //文件原名
                    jsonObject.element("uploadname", newFileName);//上传后的名字
                    jsonObject.element("src", filePath);//上传后的相对目录
                    String fileType = newFileName.substring(newFileName.lastIndexOf(".") + 1);
                    jsonObject.element("type", fileType);//后缀类型
                    jsonObject.element("size", fileSize);//文件大小
                    jsonObject.element("realpath", savePath);//真是目录
                    ja.add(jsonObject);
                }
            }
            out = resp.getWriter();
            out.write(ja.toString());
            out.flush();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(out != null){
                out.close();
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
    
    
    /**
	 * 通过文件名判断文件类型images，audio，video，doc，other
	 * @auther gtp
	 * @since 2016-7-21
	 */
	public String getFileSortString(String fileName) {
		String result = "";
		//类型分组处理
		String[] imagesArr = {"jpg","jpeg","gif","png","bmp","ico","tiff"};
		String[] audioArr = {"mp3","wav","mid","wma","ape"};
		String[] videoArr = {"avi","mov","mpeg","mpg","qt","ram","viv","mp4","rmvb","mkv","rm","flv"};
		String[] docArr = {"txt","doc","pdf","docx","xls","xlsx","ppt","pptx","rtf"};
		Map<String, String> sortMap = new HashMap<String, String>();
		for (String string : imagesArr) {
			sortMap.put(string, "images");
		}
		for (String string : audioArr) {
			sortMap.put(string, "audio");
		}
		for (String string : videoArr) {
			sortMap.put(string, "video");
		}
		for (String string : docArr) {
			sortMap.put(string, "doc");
		}
		//文件名判断
		String[] arr = fileName.split("\\.");// 处理客户端传过来的文件名：（关于XX的决定[作者.张三].doc）对于这种多个.的文假名，取最后一个.后面的字符作为后缀
		String extname = "";
		if (arr.length > 1) {
			extname = arr[arr.length - 1];
		}
		if (sortMap.get(extname.toLowerCase())!=null) {
			result = sortMap.get(extname.toLowerCase());
		}else {
			result = "other";
		}
		return result;
	}
	/**
	 * 获取附件的保存路径
	 * @param sysName 系统名 boyuyun beike resource
	 * @param userId adminId或userId
	 * @param fileName 文件名带扩展名
	 * @throws UnsupportedEncodingException 
	 * @auther gtp
	 * @since 2016-7-21
	 */
	public String getSavePath(String sysName,String userId,String fileName) {
		String dateStr = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String[] arr = fileName.split("\\.");// 处理客户端传过来的文件名：（关于XX的决定[作者.张三].doc）对于这种多个.的文假名，取最后一个.后面的字符作为后缀
		String extname = "";
		if (arr.length > 1) {
			extname = arr[arr.length - 1];
		}
		String result = "/" + "attachment" 
					  + "/" + sysName 
					  + "/" + userId
					  + "/" + getFileSortString(fileName)
					  + "/" + dateStr
//					  + "/" + fileName;
					  + "/" + new SimpleDateFormat("yyyyMMddHHmmssSS").format(new Date())+"."+extname;
		return result;
	}
}
