package util;

import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelDownload {
	
	XSSFWorkbook 	workbook 	= new XSSFWorkbook();				// MS-Office 2007이상 버전과 호환 (97~2003버전은 HSSFWorkbook)
	XSSFSheet 		sheet		= workbook.createSheet("DATA");
	XSSFRow 		row			= null;
	XSSFCell 		cell		= null;
	
	/**
	 * POI를 사용하여 엑셀파일을 만든다.
	 * 
	 * @param data	0번째 열에는 헤더 정보가 들어가야하는 2차원 배열 데이터
	 * @param colSplit 행고정 인덱스
	 * @param rowSplit 열고정 인덱스
	 * @param ExportPath export full path
	 */
	
	public void exportPoiExcel(String[][] data, int colSplit, int rowSplit, String ExportPath){
		
		try{
			sheet.setDefaultColumnWidth(10);
			
			CellStyle hederStyle = workbook.createCellStyle(); 				// header스타일
			CellStyle hederBottomStyle  = workbook.createCellStyle(); 		// headerBottom스타일
			CellStyle bodyStyle  = workbook.createCellStyle(); 				// data스타일
			Font font = null;
			
			
			/*** freeze ***/
			sheet.createFreezePane(colSplit, rowSplit);
			
			/*** hederStyle ***/
			font = workbook.createFont();
			font.setFontName("맑은 고딕");
			font.setFontHeightInPoints((short) 9);
			font.setBoldweight((short) font.BOLDWEIGHT_BOLD);
			hederStyle.setFont(font);
			hederStyle.setAlignment(CellStyle.ALIGN_CENTER);
			hederStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			hederStyle.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.index);
			hederStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			/*** 테두리설정 ***/
			hederStyle.setBorderTop(CellStyle.BORDER_THIN);								
			hederStyle.setBorderLeft(CellStyle.BORDER_THIN);
			hederStyle.setBorderRight(CellStyle.BORDER_THIN);
			hederStyle.setBorderBottom(CellStyle.BORDER_THIN);
			
			/*** hederBottomStyle ***/
			font = workbook.createFont();
			font.setFontName("맑은 고딕");
			font.setFontHeightInPoints((short) 9);
			font.setBoldweight((short) font.BOLDWEIGHT_BOLD);
			hederBottomStyle.setFont(font);
			hederBottomStyle.setAlignment(CellStyle.ALIGN_CENTER);
			hederBottomStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			hederBottomStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
			hederBottomStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			/*** 테두리설정 ***/
			hederBottomStyle.setBorderTop(CellStyle.BORDER_THIN);								
			hederBottomStyle.setBorderLeft(CellStyle.BORDER_THIN);
			hederBottomStyle.setBorderRight(CellStyle.BORDER_THIN);
			hederBottomStyle.setBorderBottom(CellStyle.BORDER_THIN);
			
			/*** bodyStyle ***/
			font = workbook.createFont();
			font.setFontName("맑은 고딕");
			font.setFontHeightInPoints((short) 9);
			font.setBoldweight((short) font.BOLDWEIGHT_NORMAL);
			bodyStyle.setFont(font);
			bodyStyle.setAlignment(CellStyle.ALIGN_RIGHT);
			bodyStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			/*** 테두리설정 ***/
			bodyStyle.setBorderTop(CellStyle.BORDER_THIN);								
			bodyStyle.setBorderLeft(CellStyle.BORDER_THIN);
			bodyStyle.setBorderRight(CellStyle.BORDER_THIN);
			bodyStyle.setBorderBottom(CellStyle.BORDER_THIN);
			
			/*** excel데이터 만들기 시작  ***/
			for (int i = 0; i < data.length; i++) {
				String[] excelData = (String[])data[i];
				row=sheet.createRow(i);
				for(int j=0;j<excelData.length;j++){
					cell=row.createCell(j);
					cell.setCellValue(excelData[j]);
					
					if(i==0){
						cell.setCellStyle(hederStyle);
					}else{
						if(j<colSplit){
							cell.setCellStyle(hederBottomStyle);
						}else{
							cell.setCellStyle(bodyStyle);
						}	
					}
				}
			}
			/*** excel데이터 만들기 끝  ***/
			
			for(int i=0; i<colSplit; i++){
				sheet.autoSizeColumn(i);
				if(sheet.getColumnWidth(i)+500 > (short)14000){
					sheet.setColumnWidth(i, (short)13000 );
				}else{
					sheet.setColumnWidth(i, (sheet.getColumnWidth(i))+512 );
				}
			}
			FileOutputStream fileoutputstream=new FileOutputStream(ExportPath);
			//파일을 쓴다
			workbook.write(fileoutputstream);
			//필수로 닫아주어야함 
			fileoutputstream.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}