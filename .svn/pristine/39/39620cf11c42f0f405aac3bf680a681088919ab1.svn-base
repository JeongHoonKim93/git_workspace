<%@page import="java.util.HashMap"%>
<%@page import="risk.report.ReportMgr_hycard"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="risk.util.*"%>
<%
	ReportMgr_hycard hyMgr = new ReportMgr_hycard();
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	
	DateUtil du = new DateUtil();
	
	int nowpage = 0;
	int rowCnt = 0;
	String ir_sdate = pr.getString("ir_sdate");		//시작일
	String ir_edate = pr.getString("ir_edate");		//종료일
	
	String department = pr.getString("department");
	String type = pr.getString("type");
	String seq = pr.getString("seq");
	
	String keyword = pr.getString("keyword");					
	String senti_type = pr.getString("senti_type");	
	String name = pr.getString("name");	
	String mode = pr.getString("mode");
	//String title = "연관어 분석 : ";	
	String title = "현대카드";
	if(type.equals("12")) {
		title = "현대카드";
	} else if(type.equals("22")) {
		title = "현대캐피탈";
	}
	
	String fileName = title + "_RELATION_DATA_"+du.getDate("yyyyMMdd_HHmmss")+".xlsx";
	if(mode.equals("voc")) {
		fileName = 	title + "_DAILY_VOC_"+du.getDate("yyyyMMdd_HHmmss")+".xlsx";
	} else if(mode.equals("news")) {
		fileName = "소비자보호NEWS_" + du.getDate("yyyyMMdd_HHmmss")+".xlsx";
	}
	
	ConfigUtil cu = new ConfigUtil();
	String URL = cu.getConfig("URL");
	String  filePath = cu.getConfig("EXCELPATH");

	
	String[] sheetNameArr = null;
	String[] titleArray = null;
	//HashMap<Integer, ArrayList> dataMap = new HashMap<Integer, ArrayList>(); 
	
	POIExcelAdd poiAdd = new POIExcelAdd();			
	
	ArrayList excelList = new ArrayList();	
	String[] titleArr = null;
	ArrayList<String[]> total_title = new ArrayList<String[]>() ;
	String subject ="";

		
	int idx = 0;
	
	if(mode.equals("rel_info")) {
		idx = 0;
		//sheetNameArr = new String []{"연관어 분석"};
	
		//titleArr = new String[]{"감성","제목","원문", "URL", "출처", "일시"};
		titleArr = new String[]{"감성","제목", "URL", "출처", "일시"};
		total_title.add(idx, titleArr);
			
		excelList = hyMgr.getRelationData_excel(ir_sdate, ir_edate, type, seq, keyword, senti_type, titleArr.length );

		idx++;
		
		subject = "연관어 분석 : " + title + " '" + name + "'  ["+ir_sdate+"~"+ir_edate+"]";
		//sheet 한개일 경우
		poiAdd.addExcel(filePath, fileName, subject, titleArr, excelList);
		//sheet 여러개일 경우
		//poiAdd.sheet_Excel2(filePath, fileName, sheetNameArr, subject, total_title, dataMap);
		
	} else if(mode.equals("voc")) {
		idx = 0;
		
		//titleArr = new String[]{"감성","제목", "원문", "URL", "주제", "대응등급", "출처", "일시"};
		titleArr = new String[]{"감성","제목", "URL", "주제", "대응등급", "출처", "일시"};
		total_title.add(idx, titleArr);
			
		excelList = hyMgr.getDailyVocData_excel(ir_sdate, ir_edate, type, mode, keyword, senti_type, titleArr.length);
		
		subject = title + " Daily VOC  ["+ir_sdate+"~"+ir_edate+"]";
		//sheet 한개일 경우
		poiAdd.addExcel(filePath, fileName, subject, titleArr, excelList);
		//sheet 여러개일 경우
		//poiAdd.sheet_Excel2(filePath, fileName, sheetNameArr, subject, total_title, dataMap);
	} else if(mode.equals("news")) {
		idx = 0;
		
		//titleArr = new String[]{ "제목","원문", "URL", "일시" };
		titleArr = new String[]{ "제목", "URL", "출처", "일시" };
		total_title.add(idx, titleArr);
		
		excelList = hyMgr.getConsumerNews_excel(ir_sdate, ir_edate, keyword, titleArr.length);
		
		subject = "소비자보호NEWS  ["+ir_sdate+"~"+ir_edate+"]";
		poiAdd.addExcel(filePath, fileName, subject, titleArr, excelList);
	}
	
	
	String realPath = URL+"excel_file/"+fileName;
	out.print(realPath);
	
	
%>	