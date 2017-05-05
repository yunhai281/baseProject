package com.boyuyun.base.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
 

public class ExcelUtil {

	private static String realBasePath = "";
	static {
		realBasePath = ConstantUtil.UPLOAD_FOLDER;
	}
	
	/**
	 * @Description 读取excel 
	 * @author jms
	 * @param inputStream
	 * @param isExcel2003
	 * @return
	 */
	public static Workbook read(InputStream inputStream, boolean isExcel2003){  
        List<List<String>> dataLst = null;  
        /** 根据版本选择创建Workbook的方式 */  
        Workbook wb = null;  
        try {  
            if (isExcel2003){  
                wb = new HSSFWorkbook(inputStream);  
            } else   {  
                wb = new XSSFWorkbook(inputStream);  
            }    
        } catch (IOException e){  
            e.printStackTrace();  
        }  
        return wb;  
    }  
}
