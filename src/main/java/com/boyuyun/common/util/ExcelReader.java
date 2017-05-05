package com.boyuyun.common.util;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddressList;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 操作Excel表格的功能类
 */
public class ExcelReader {
    private POIFSFileSystem fs;
    private HSSFSheet sheet;
    private HSSFRow row;
    private XSSFRow xrow;
    private Logger logger = Logger.getLogger(ExcelReader.class);

//    /**
//     * 读取Excel表格表头的内容
//     * @param InputStream
//     * @return String 表头内容的数组
//     */
//    public String[] readExcelTitle(InputStream is) {
//        try {
//            fs = new POIFSFileSystem(is);
//            wb = new HSSFWorkbook(is);
//        } catch (IOException e) {
//            try {
//				wbk = new XSSFWorkbook(is);
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			}
//        }
//        sheet = wb.getSheetAt(0);
//        row = sheet.getRow(0);
//        // 标题总列数
//        int colNum = row.getPhysicalNumberOfCells();
//        System.out.println("colNum:" + colNum);
//        String[] title = new String[colNum];
//        for (int i = 0; i < colNum; i++) {
//            //title[i] = getStringCellValue(row.getCell((short) i));
//            title[i] = getCellFormatValue(row.getCell((short) i));
//        }
//        return title;
//    }

    public Map<Integer, List> readExcelContent(InputStream is) {
        Map<Integer, List> content = new HashMap<Integer, List>();
        try {
            
            Workbook wb2 = WorkbookFactory.create(is);//此WorkbookFactory在POI-3.10版本中使用需要添加dom4j
            
            if (wb2 instanceof XSSFWorkbook) {
                XSSFWorkbook xWb = (XSSFWorkbook) wb2;
               XSSFSheet sheet = xWb.getSheetAt(0);
               content = readExcelContent(sheet);
            }else if(wb2 instanceof HSSFWorkbook){
                HSSFWorkbook hWb = (HSSFWorkbook) wb2;
                HSSFSheet sheet = hWb.getSheetAt(0);
                content = readExcelContent(sheet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }
    /**
     * 读取Excel数据内容
     * @param InputStream
     * @return Map 包含单元格数据内容的Map对象
     */
    public Map<Integer, List> readExcelContent(HSSFSheet sheet) {
        Map<Integer, List> content = new HashMap<Integer, List>();
        // 得到总行数
        int rowNum = sheet.getLastRowNum();
        row = sheet.getRow(0);
        int colNum = row.getPhysicalNumberOfCells();
        // 正文内容应该从第二行开始,第一行为表头的标题
        for (int i = 1; i <= rowNum; i++) {
            row = sheet.getRow(i);
            if(null != row){
            	int j = 0;
                List contentList = new ArrayList();
                while (j < colNum) {
                	HSSFCell cell = row.getCell(j);
                	String str = "";
                	if(null != cell){
                		str = getCellFormatValue(cell).trim();	
                	}
                    contentList.add(str);
                    j++;
                }
                content.put(i, contentList);
            }
        }
        return content;
    }

    
    public Map<Integer, List> readExcelContent(XSSFSheet sheet) {
        Map<Integer, List> content = new HashMap<Integer, List>();
        // 得到总行数
        int rowNum = sheet.getLastRowNum();
        xrow = sheet.getRow(0);
        int colNum = xrow.getPhysicalNumberOfCells();
        // 正文内容应该从第二行开始,第一行为表头的标题
        for (int i = 1; i <= rowNum; i++) {
            xrow = sheet.getRow(i);
            if(null != xrow){
            	int j = 0;
                List contentList = new ArrayList();
                while (j < colNum) {
                	XSSFCell cell = xrow.getCell(j);
                	String str = "";
                	if(null != cell){
                		str = getCellFormatValue(cell).trim();	
                	}
                    contentList.add(str);
                    j++;
                }
                content.put(i, contentList);
            }
        }
        return content;
    }

    /**
     * 获取单元格数据内容为字符串类型的数据
     * 
     * @param cell Excel单元格
     * @return String 单元格数据内容
     */
    private String getStringCellValue(HSSFCell cell) {
        String strCell = "";
        switch (cell.getCellType()) {
        case HSSFCell.CELL_TYPE_STRING:
            strCell = cell.getStringCellValue();
            break;
        case HSSFCell.CELL_TYPE_NUMERIC:
        	if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
				SimpleDateFormat sdf = null;
				if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {
					sdf = new SimpleDateFormat("HH:mm");
				} else {// 日期
					sdf = new SimpleDateFormat("yyyy-MM-dd");
				}
				Date date = cell.getDateCellValue();
				strCell = sdf.format(date);
			} else if (cell.getCellStyle().getDataFormat() == 58) {
				// 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				double value = cell.getNumericCellValue();
				Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
				strCell = sdf.format(date);
			} else {
				double value = cell.getNumericCellValue();
				CellStyle style = cell.getCellStyle();
				DecimalFormat format = new DecimalFormat();
				String temp = style.getDataFormatString();
				// 单元格设置成常规
				if (temp.equals("General")) {
					format.applyPattern("#");
				}
				strCell = format.format(value);
			}
            break;
        case HSSFCell.CELL_TYPE_BOOLEAN:
            strCell = String.valueOf(cell.getBooleanCellValue());
            break;
        case HSSFCell.CELL_TYPE_BLANK:
            strCell = "";
            break;
        default:
            strCell = "";
            break;
        }
        if (strCell.equals("") || strCell == null) {
            return "";
        }
        if (cell == null) {
            return "";
        }
        return strCell;
    }

    /**
     * 获取单元格数据内容为日期类型的数据
     * 
     * @param cell
     *            Excel单元格
     * @return String 单元格数据内容
     */
    private String getDateCellValue(HSSFCell cell) {
        String result = "";
        try {
            int cellType = cell.getCellType();
            if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
                Date date = cell.getDateCellValue();
                result = (date.getYear() + 1900) + "-" + (date.getMonth() + 1)
                        + "-" + date.getDate();
            } else if (cellType == HSSFCell.CELL_TYPE_STRING) {
                String date = getStringCellValue(cell);
                result = date.replaceAll("[年月]", "-").replace("日", "").trim();
            } else if (cellType == HSSFCell.CELL_TYPE_BLANK) {
                result = "";
            }
        } catch (Exception e) {
//            System.out.println("日期格式不正确!");
        	logger.error("日期格式不正确!");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据HSSFCell类型设置数据
     * @param cell
     * @return
     */
    public String getCellFormatValue(HSSFCell cell) {
        String cellvalue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
	            // 如果当前Cell的Type为NUMERIC
	            case HSSFCell.CELL_TYPE_NUMERIC:
	            	if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
	    				SimpleDateFormat sdf = null;
	    				if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {
	    					sdf = new SimpleDateFormat("HH:mm");
	    				} else {// 日期
	    					sdf = new SimpleDateFormat("yyyy-MM-dd");
	    				}
	    				Date date = cell.getDateCellValue();
	    				cellvalue = sdf.format(date);
	    			} else if (cell.getCellStyle().getDataFormat() == 58) {
	    				// 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
	    				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    				double value = cell.getNumericCellValue();
	    				Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
	    				cellvalue = sdf.format(date);
	    			} else {
	    				double value = cell.getNumericCellValue();
//	    				CellStyle style = cell.getCellStyle();
//	    				DecimalFormat format = new DecimalFormat();
	    				BigDecimal bd = new BigDecimal(value);
	    				cellvalue = bd.toPlainString();
//	    				String temp = style.getDataFormatString();
//	    				// 单元格设置成常规
//	    				if (temp.equals("General")) {
//	    					format.applyPattern("#");
//	    				}
//	    				cellvalue = String.valueOf(value);
//	    				cellvalue = format.format(value);
	    			}
	            	break;
	            case HSSFCell.CELL_TYPE_FORMULA: {
	                // 判断当前的cell是否为Date
	                if (HSSFDateUtil.isCellDateFormatted(cell)) {
	                    // 如果是Date类型则，转化为Data格式
	                    
	                    //方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
	                    //cellvalue = cell.getDateCellValue().toLocaleString();
	                    
	                    //方法2：这样子的data格式是不带带时分秒的：2011-10-12
	                    Date date = cell.getDateCellValue();
	                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	                    cellvalue = sdf.format(date);
	                    
	                }
	                // 如果是纯数字
	                else {
	                    // 取得当前Cell的数值
	                    cellvalue = String.valueOf(cell.getNumericCellValue());
	                }
	                break;
	            }
	            // 如果当前Cell的Type为STRING
	            case HSSFCell.CELL_TYPE_STRING:
	                // 取得当前的Cell字符串
	                cellvalue = cell.getRichStringCellValue().getString();
	                break;
	            // 默认的Cell值
	            default:
	                cellvalue = " ";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;

    }
    private String getCellFormatValue(XSSFCell cell) {
        String cellvalue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
	            // 如果当前Cell的Type为NUMERIC
	            case HSSFCell.CELL_TYPE_NUMERIC:
	            	if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
	    				SimpleDateFormat sdf = null;
	    				if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {
	    					sdf = new SimpleDateFormat("HH:mm");
	    				} else {// 日期
	    					sdf = new SimpleDateFormat("yyyy-MM-dd");
	    				}
	    				Date date = cell.getDateCellValue();
	    				cellvalue = sdf.format(date);
	    			} else if (cell.getCellStyle().getDataFormat() == 58) {
	    				// 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
	    				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    				double value = cell.getNumericCellValue();
	    				Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
	    				cellvalue = sdf.format(date);
	    			} else {
	    				double value = cell.getNumericCellValue();
	    				BigDecimal bd = new BigDecimal(value);
	    				cellvalue = bd.toPlainString();
//	    				CellStyle style = cell.getCellStyle();
//	    				DecimalFormat format = new DecimalFormat();
//	    				String temp = style.getDataFormatString();
//	    				// 单元格设置成常规
//	    				if (temp.equals("General")) {
//	    					format.applyPattern("#");
//	    				}
//	    				cellvalue = format.format(value);
	    			}
	            	break;
	            case HSSFCell.CELL_TYPE_FORMULA: {
	                // 判断当前的cell是否为Date
	                if (HSSFDateUtil.isCellDateFormatted(cell)) {
	                    // 如果是Date类型则，转化为Data格式
	                    
	                    //方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
	                    //cellvalue = cell.getDateCellValue().toLocaleString();
	                    
	                    //方法2：这样子的data格式是不带带时分秒的：2011-10-12
	                    Date date = cell.getDateCellValue();
	                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	                    cellvalue = sdf.format(date);
	                    
	                }
	                // 如果是纯数字
	                else {
	                    // 取得当前Cell的数值
	                    cellvalue = String.valueOf(cell.getNumericCellValue());
	                }
	                break;
	            }
	            // 如果当前Cell的Type为STRING
	            case HSSFCell.CELL_TYPE_STRING:
	                // 取得当前的Cell字符串
	                cellvalue = cell.getRichStringCellValue().getString();
	                break;
	            // 默认的Cell值
	            default:
	                cellvalue = " ";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;

    }
    
    
  //下拉列表元素很多的情况

    public static HSSFDataValidation SetDataValidation(String strFormula,int firstRow,int firstCol,int endRow,int endCol)
    {
	    //String formula = "Sheet2!$A$2:$A$59" ;// 表示Z列1-N行作为下拉列表来源数据
	    // HSSFDataValidation dataValidation = new HSSFDataValidation((short) 1,
	    // (short) 1, (short) 300, (short) 1); //原顺序为 起始行 起始列 终止行 终止列
	    // dataValidation.setDataValidationType(HSSFDataValidation.DATA_TYPE_LIST);
	    // dataValidation.setFirstFormula(formula);
	    // dataValidation.setSecondFormula(null);
	    CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
	    // CellRangeAddressList regions = new CellRangeAddressList( firstRow,
	    // (short) 300, (short) 1, (short) 1);//add 新顺序为 起始行 终止行 起始列 终止列
	    DVConstraint constraint = DVConstraint.createFormulaListConstraint(strFormula);//add
	    HSSFDataValidation dataValidation = new HSSFDataValidation(regions,constraint);//add
	
	    dataValidation.createErrorBox("Error", "Error");
	    dataValidation.createPromptBox("", null);
	
	    return dataValidation;
    }
    //255以内的下拉
    public static DataValidation setDataValidation(Sheet sheet,String[] textList, int firstRow, int endRow, int firstCol, int endCol) {
	
	    DataValidationHelper helper = sheet.getDataValidationHelper();
	    // 加载下拉列表内容
	    DataValidationConstraint constraint = helper.createExplicitListConstraint(textList);
	    // DVConstraint constraint = new DVConstraint();
	    constraint.setExplicitListValues(textList);
	
	    // 设置数据有效性加载在哪个单元格上。
	    // 四个参数分别是：起始行、终止行、起始列、终止列
	    CellRangeAddressList regions = new CellRangeAddressList((short) firstRow, (short) endRow, (short) firstCol, (short) endCol);
	
	    // 数据有效性对象
	    DataValidation data_validation = helper.createValidation(constraint, regions);
	    //DataValidation data_validation = new DataValidation(regions, constraint);
	
	    return data_validation;
    }

}
