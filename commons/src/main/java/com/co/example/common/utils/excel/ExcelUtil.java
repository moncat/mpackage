package com.co.example.common.utils.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.fastjson.JSON;

/**
 * 去http://jakarta.apache.org/site/downloads/downloads_poi.cgi下载poi项目相关的jar包和文档
 */
public class ExcelUtil {

	/**
	 * 新建一个Excel文件，里面添加5行5列的内容，再添加两个高度为2的大单元格。
	 * 
	 * @param fileName
	 */
	public void writeExcel(String fileName) {

		//目标文件
		File file = new File(fileName);
		FileOutputStream fOut = null;
		try {
			//	创建新的Excel 工作簿
			HSSFWorkbook workbook = new HSSFWorkbook();

			//	在Excel工作簿中建一工作表，其名为缺省值。
			//	也可以指定工作表的名字。
			HSSFSheet sheet = workbook.createSheet("Test_Table");

			//  创建字体，红色、粗体
			HSSFFont font = workbook.createFont();
			font.setColor(HSSFFont.COLOR_RED);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

			//  创建单元格的格式，如居中、左对齐等
			HSSFCellStyle cellStyle = workbook.createCellStyle();
			//  水平方向上居中对齐
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			//  垂直方向上居中对齐
			cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			//  设置字体
			cellStyle.setFont(font);

			//下面将建立一个4行3列的表。第一行为表头。
			int rowNum = 0;//行标
			int colNum = 0;//列标
			//建立表头信息
			//	在索引0的位置创建行（最顶端的行）
			HSSFRow row = sheet.createRow((short) rowNum);
			//  单元格
			HSSFCell cell = null;
			for (colNum = 0; colNum < 5; colNum++) {
				//	在当前行的colNum列上创建单元格
				cell = row.createCell((short) colNum);

				//	定义单元格为字符类型，也可以指定为日期类型、数字类型
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				//  定义编码方式，为了支持中文，这里使用了ENCODING_UTF_16
//				cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				//  为单元格设置格式
				cell.setCellStyle(cellStyle);

				//	添加内容至单元格
				cell.setCellValue("表头名-" + colNum);
			}
			rowNum++;
			for (; rowNum < 5; rowNum++) {
				//  新建第rowNum行
				row = sheet.createRow((short) rowNum);
				for (colNum = 0; colNum < 5; colNum++) {
					// 在当前行的colNum位置创建单元格
					cell = row.createCell((short) colNum);
//					cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellStyle(cellStyle);
					cell.setCellValue("值-" + rowNum + "-" + colNum);
				}
			}

			//  合并单元格
			//  先创建2行5列的单元格，然后将这些单元格合并为2个大单元格
			rowNum = 5;
			for (; rowNum < 7; rowNum++) {
				row = sheet.createRow((short) rowNum);
				for (colNum = 0; colNum < 5; colNum++) {
					// 在当前行的colNum位置创建单元格
					cell = row.createCell((short) colNum);
				}
			}
			//建立第一个大单元格，高度为2，宽度为2
			rowNum = 5;
			colNum = 0;
			Region region = new Region(rowNum, (short) colNum, (rowNum + 1),
					(short) (colNum + 1));
			sheet.addMergedRegion(region);
			//获得第一个大单元格
			cell = sheet.getRow(rowNum).getCell((short) colNum);
//			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("第一个大单元格");

			//建立第二个大单元格，高度为2，宽度为3
			colNum = 2;
			region = new Region(rowNum, (short) colNum, (rowNum + 1),
					(short) (colNum + 2));
			sheet.addMergedRegion(region);
			//获得第二个大单元格
			cell = sheet.getRow(rowNum).getCell((short) colNum);
//			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("第二个大单元格");

			//  工作薄建立完成，下面将工作薄存入文件
			//	新建一输出文件流
			fOut = new FileOutputStream(file);
			//	把相应的Excel 工作簿存盘
			workbook.write(fOut);
			fOut.flush();
			//	操作结束，关闭文件
			fOut.close();

			System.out
					.println("Excel文件生成成功！Excel文件名：" + file.getAbsolutePath());
		} catch (Exception e) {
			System.out.println("Excel文件" + file.getAbsolutePath()  + "生成失败：" + e);
		} finally {
			if (fOut != null){
				try {
					fOut.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	/**
	 * 读Excel文件内容
	 * 
	 * @param fileName
	 */
	public static void readExcel(String fileName,String sheetName) {
		
		File file = new File(fileName);
		FileInputStream in = null;
		try {
			//	创建对Excel工作簿文件的引用
			in = new FileInputStream(file);
			HSSFWorkbook workbook = new HSSFWorkbook(in);

			//	创建对工作表的引用。
			//	这里使用按名引用
			HSSFSheet sheet = workbook.getSheet(sheetName);
			//	也可用getSheetAt(int index)按索引引用，
			//	在Excel文档中，第一张工作表的缺省索引是0，其语句为：
			//	HSSFSheet sheet = workbook.getSheetAt(0);

			//下面读取Excel的前5行的数据
			System.out.println("下面是Excel文件" + file.getAbsolutePath() + "的内容：");
			HSSFRow row = null;
			HSSFCell cell = null;
			int rowNum = 0;//行标
			int colNum = 0;//列标
			for (; rowNum < 33; rowNum++) {
				//  获取第rowNum行
				row = sheet.getRow((short) rowNum);
				for (colNum = 0; colNum < 33; colNum++) {
					// 获取当前行的colNum位置的单元格
					cell = row.getCell((short) colNum);
					System.out.print(cell.getStringCellValue() + "\t");
				}
				//换行
				System.out.println();
			}

			in.close();
		} catch (Exception e) {
			System.out.println("读取Excel文件" + file.getAbsolutePath() + "失败：" + e);
		}  finally {
			if (in != null){
				try {
					in.close();
				} catch (IOException e1) {
				}
			}
		}

	}
	
	
	  public static Workbook readExcel(String filePath){
	        Workbook wb = null;
	        if(filePath==null){
	            return null;
	        }
	        String extString = filePath.substring(filePath.lastIndexOf("."));
	        InputStream is = null;
	        try {
	            is = new FileInputStream(filePath);
	            if(".xls".equals(extString)){
	                return wb = new HSSFWorkbook(is);
	            }else if(".xlsx".equals(extString)){
	                return wb = new XSSFWorkbook(is);
	            }else{
	                return wb = null;
	            }
	            
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return wb;
	    }
	    public static Object getCellFormatValue(Cell cell){
	        Object cellValue = null;
	        if(cell!=null){
	            //判断cell类型
	            switch(cell.getCellType()){
	            case Cell.CELL_TYPE_NUMERIC:{
	                cellValue = String.valueOf(cell.getNumericCellValue());
	                break;
	            }
	            case Cell.CELL_TYPE_FORMULA:{
	                //判断cell是否为日期格式
	                if(DateUtil.isCellDateFormatted(cell)){
	                    //转换为日期格式YYYY-mm-dd
	                    cellValue = cell.getDateCellValue();
	                }else{
	                    //数字
	                    cellValue = String.valueOf(cell.getNumericCellValue());
	                }
	                break;
	            }
	            case Cell.CELL_TYPE_STRING:{
	                cellValue = cell.getRichStringCellValue().getString();
	                break;
	            }
	            default:
	                cellValue = "";
	            }
	        }else{
	            cellValue = "";
	        }
	        return cellValue;
	    }

	    public static List<Map<String,String>>  getList() {
	        Workbook wb =null;
	        Sheet sheet = null;
	        Row row = null;
	        List<Map<String,String>> list = null;
	        String cellData = null;
//	        String filePath = "d:/Desktop/t_br_xk_web.xlsx";
	        String filePath = "d:/Desktop/t_br_xk_app.xlsx";
	        String columns[] = 
	        {"businessLicenseNumber","businessPerson","certStr","cityCode","countyCode","creatUser","createTime","endTime","epsAddress","epsName","epsProductAddress","id","isimport","legalPerson","offDate","offReason","parentid","preid","processid","productSn","provinceCode","qfDate","qfManagerName","qualityPerson","rcManagerDepartName","rcManagerUser","xkCompleteDate","xkDate","xkDateStr","xkName","xkProject","xkRemark","xkType"};
	        		
	        wb = readExcel(filePath);
	        if(wb != null){
	            //用来存放表中数据
	            list = new ArrayList<Map<String,String>>();
	            //获取第一个sheet
	            sheet = wb.getSheetAt(0);
	            //获取最大行数
	            int rownum = sheet.getPhysicalNumberOfRows();
	            //获取第一行
	            row = sheet.getRow(0);
	            //获取最大列数
	            int colnum = row.getPhysicalNumberOfCells();
	            for (int i = 1; i<rownum; i++) {
	                Map<String,String> map = new LinkedHashMap<String,String>();
	                row = sheet.getRow(i);
	                if(row !=null){
	                    for (int j=0;j<colnum;j++){
	                        cellData = (String) getCellFormatValue(row.getCell(j));
	                        map.put(columns[j], cellData);
	                    }
	                }else{
	                    break;
	                }
	                list.add(map);
	            }
	        }
//	        String data = JSON.toJSONString(list);
//	        try {
//				FileUtils.write(new File("D:\\Desktop\\webjson1.txt"), data);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				System.out.println("errorerrorerrorerror");
//			}
	        System.out.println("***************over************************");
	        //遍历解析出来的list
//	        for (Map<String,String> map : list) {
//	            for (Entry<String,String> entry : map.entrySet()) {
//	                System.out.print(entry.getKey()+":"+entry.getValue()+",");
//	            }
//	            System.out.println();
//	        }
			return list;

	    }
	    
//	public static void main(String[] args) throws Exception {
//		ExcelUtil excel = new ExcelUtil();
//		String fileName = "d:/Desktop/t_br_xk_web.xls";
////		excel.writeExcel(fileName);
////		excel.readExcel(fileName,"t_br_xk_web");
//		excel.readExcel(fileName);
//	}
}