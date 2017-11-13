package com.tj.devil.quartz.util;

import com.jfinal.plugin.activerecord.Record;
import org.apache.poi.hssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author jeanzhang
 */
public class WriteEXCEL {
	/**
	 * 导出excel文件名字
	 */
	private String excelName ;
	/**
	 * sheet名字
	 */
	private String sheetTitle ;
	private HSSFWorkbook workbook ;
	private SimpleDateFormat dateFormat ;
	private LinkedHashMap<String,String> keyTitle ;
	/**
	 * 实例化导出对象
	 * @param excelName 文件名字
	 * @param sheetTitle sheet名字
	 * @param dateFormat 日期类型
	 */
	public WriteEXCEL(String excelName, String sheetTitle, String dateFormat, HSSFWorkbook workbook){
		this.excelName = excelName ;
		this.sheetTitle = sheetTitle ;
		this.dateFormat = new SimpleDateFormat(dateFormat) ;
		this.workbook = workbook;
	}

	/**
	 * 执行导出-web
	 * @param <E>
	 * @param response 响应对象
	 * @param dataList	数据列表
	 * @param keyTitle	读取对象属性
	 */
	public <E> void write(HttpServletResponse response, List<E> dataList, LinkedHashMap<String,String> keyTitle, String sheetName){
		OutputStream out = exportHeader(response) ;
		write(dataList, keyTitle, sheetName, out);
	}

	/**
	 * 执行导出-excel
	 * @param <E>
	 * @param dataList	数据列表
	 * @param keyTitle	读取对象属性
	 * @param sheetName sheet名
	 */
	public <E> void write(List<E> dataList, LinkedHashMap<String,String> keyTitle, String sheetName, String path) throws IOException {
		File file = new File(path);
		if (!file.exists()) {
			file.createNewFile();
		}
		//清空
		FileWriter fileWriter =new FileWriter(file);
		fileWriter.write("");
		fileWriter.flush();
		fileWriter.close();
		FileOutputStream out = new FileOutputStream(file);
		write(dataList, keyTitle, sheetName, out);
	}

	public <E> void write(HttpServletResponse response,List<E> dataList,LinkedHashMap<String,String> keyTitle) {
		write(response,dataList,keyTitle,null);
	}

	private <E> void write(List<E> dataList, LinkedHashMap<String,String> keyTitle, String sheetName, OutputStream out) {
		this.keyTitle = keyTitle;
//		// 声明一个工作薄
//		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		sheetTitle = sheetName==null?sheetTitle:sheetName;
		HSSFSheet sheet = workbook.createSheet(sheetTitle);
		setColumnWidth(sheet);
		setTitle(sheet, workbook);
		setContent(sheet, dataList);
		try {
			workbook.write(out);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 设置表内容
	 */
	private <E> void setContent(HSSFSheet sheet, List<E> dataList){
		for (int i = 0; i < dataList.size(); i++) {
	    	HSSFRow row = sheet.createRow(i+1);
	    	int index = 0 ;
		    for (String key  : keyTitle.keySet()) {
				HSSFCell cell = row.createCell(index);
				try {
					if (dataList.get(i) instanceof Record) {
						cell.setCellValue(new HSSFRichTextString(formatText(EntityUtil.getRecord((Record) dataList.get(i), key))));
					} else {
						cell.setCellValue(new HSSFRichTextString(formatText(EntityUtil.getTer(dataList.get(i), key))));
					}
				} catch (Exception e) {
					cell.setCellValue(new HSSFRichTextString(""));
				}
				index++;
			}
		}
	}
	/**
	 * 设置表头
	 */
	private void setTitle(HSSFSheet sheet,HSSFWorkbook workbook){
		//产生表格标题行
	    HSSFRow row = sheet.createRow(0);
		int index = 0 ;
	    for (String key : keyTitle.keySet()) {
	        HSSFCell cell = row.createCell(index) ;
	        HSSFCellStyle style = workbook.createCellStyle();
	        HSSFFont f = workbook.createFont();
	        f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗
	        style.setFont(f);
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中   
	        cell.setCellStyle(style);    
	    	cell.setCellValue(new HSSFRichTextString(keyTitle.get(key)));
	    	index++;
		}
	}

	/**
	 * 设置列宽
	 * @param sheet
	 */
	private void setColumnWidth(HSSFSheet sheet){
		int index = 0 ;
		for (String key  : keyTitle.keySet()) {
			String valueTemp = keyTitle.get(key) ;
			String[] valueArr = valueTemp.split(",") ;
			if(valueArr.length>1){
				keyTitle.put(key,valueArr[0]) ;
				Integer width = Integer.valueOf(valueArr[1]) ;
				width = width*600 ;
				sheet.setColumnWidth(index, width);
			}
			index++ ;
		}
	}
	/**
	 * 格式化读取的内容
	 */
	private <E> String formatText(E entity) {
		if(null!=entity) {
			if(entity instanceof Date || entity instanceof Timestamp) {
				return dateFormat.format(entity) ;
			} else {
				return String.valueOf(entity) ;
			}
		}
		return "" ;
	}

    /**
     * 输出流
     * @param response
     * @return
     */
    private OutputStream exportHeader(HttpServletResponse response){
		try {
			String downLoadFileName = String.valueOf(System.currentTimeMillis()).trim()+".xls" ;
			if(null!=excelName && !"".equals(excelName.trim())){
				downLoadFileName = excelName+".xls" ;
			}
			downLoadFileName = new String(downLoadFileName.getBytes("utf-8"),"iso-8859-1");
			response.setCharacterEncoding("GBK");
			response.setHeader("Content-disposition",  "attachment;  filename="+downLoadFileName);
			response.setContentType("application/vnd.ms-excel"); 
			return response.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
