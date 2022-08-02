<%@page import="java.util.HashMap"%>
<%@page import="risk.dashboard.view.press_release.PressReleaseMgr"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="risk.util.*"%>
<%
	PressReleaseMgr  mgr = new PressReleaseMgr();
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	
	DateUtil du = new DateUtil();
	
	String sDate = pr.getString("i_sdate");		//시작일
	String eDate = pr.getString("i_edate");		//종료일
	
	String title = pr.getString("subject");
	String mode = pr.getString("mode");
	String ic_code = pr.getString("ic_code"); 
	String sg_seq = pr.getString("sg_seq", "");
	String senti = pr.getString("senti", "");
	
	int rowLimit = pr.getInt("rowLimit");
	int listLimit = pr.getInt("listLimit");

	
	String fileName = "BING_PRESS_DATA_"+du.getDate("yyyyMMdd_HHmmss")+".xlsx";
	ConfigUtil cu = new ConfigUtil();
	String URL = cu.getConfig("URL");
	String  filePath = cu.getConfig("EXCELPATH");
	
	//전월 일자
	/*
	int interval = (int)du.DateDiff("yyyy-MM-dd",eDate, sDate )+1;
	du.setDate(sDate);
	du.addDay( - interval);
	String psDate = du.getDate("yyyy-MM-dd");
	
	du.setDate(eDate);
	du.addDay( - interval);
	String peDate = du.getDate("yyyy-MM-dd");
	*/
	
	String[] sheetNameArr = null;
	String[] titleArray = null;
	HashMap<Integer, ArrayList> dataMap = new HashMap<Integer, ArrayList>(); 
	
	POIExcelAdd poiAdd = new POIExcelAdd();			
	
	ArrayList excelList = new ArrayList();
	String[] titleArr = null;
	ArrayList<String[]> total_title = new ArrayList<String[]>() ;
	String subject ="";
	
	
	String[] pressReleaseDate = mgr.getPressReleaseDate(ic_code);
	String chartSDate= pressReleaseDate[0];  //시작일
	String chartEDate= pressReleaseDate[1];  //종료일
	
	String dayDiff = mgr.getDayDiff(chartSDate, chartEDate);
	if("0".equals(dayDiff)){dayDiff="1";}
	int idx = 0;
	
	
	//전체 현황
	if("dataList".equals(mode)){
		sheetNameArr = new String []{""};
		
		//보도자료 목록 및 확산 추적
		titleArr = new String[]{"담당부서","배포일자","보도자료명","최초출처","확산량"};
		total_title.add(idx, titleArr);
		excelList = mgr.getPressDataList_excel(sDate, eDate, titleArr.length);
		
		dataMap.put(idx, excelList);	

		subject = title +" ["+sDate+"~"+eDate+"]";
		//sheet 한개일 경우
		poiAdd.addExcel(filePath, fileName, subject, titleArr, excelList);
		//sheet 여러개일 경우
		//poiAdd.sheet_Excel2(filePath, fileName, sheetNameArr, subject, total_title, dataMap);
		
	} else if("channel".equals(mode)) {
		sheetNameArr = new String []{""};
		
		//보도자료 목록 및 확산 추적
		titleArr = new String[]{"채널","긍정","부정","중립","총 건수"};
		total_title.add(idx, titleArr);
		excelList = mgr.getPressReleaseChannel_excel(chartSDate, chartEDate, ic_code, titleArr.length);
		
		dataMap.put(idx, excelList);	


		subject = title +" ["+sDate+"~"+eDate+"]";
		//sheet 한개일 경우
		poiAdd.addExcel(filePath, fileName, subject, titleArr, excelList);
		//sheet 여러개일 경우
		//poiAdd.sheet_Excel2(filePath, fileName, sheetNameArr, subject, total_title, dataMap);
	} else if("amount".equals(mode)) {
		sheetNameArr = new String []{""};
		
		//보도자료 목록 및 확산 추적
		titleArr = new String[]{"일자","긍정","부정","중립","총 건수"};
		total_title.add(idx, titleArr);
		excelList = mgr.getPressReleaseAmount_excel(chartSDate, ic_code, dayDiff, titleArr.length);
		
		dataMap.put(idx, excelList);	

		subject = title +" ["+sDate+"~"+eDate+"]";
		//sheet 한개일 경우
		poiAdd.addExcel(filePath, fileName, subject, titleArr, excelList);
		//sheet 여러개일 경우
		//poiAdd.sheet_Excel2(filePath, fileName, sheetNameArr, subject, total_title, dataMap);
	} else if("coveringDataList".equals(mode)) {
		sheetNameArr = new String []{""};
		
		//보도자료 목록 및 확산 추적
		titleArr = new String[]{"일자","채널","제목","URL","출처","기자명", "성향"};
		total_title.add(idx, titleArr);
		excelList = mgr.getCoverindDataList_excel(chartSDate, chartEDate, ic_code, sg_seq, senti, titleArr.length);
		
		dataMap.put(idx, excelList);	

		subject = title +" ["+sDate+"~"+eDate+"]";
		//sheet 한개일 경우
		poiAdd.addExcel(filePath, fileName, subject, titleArr, excelList);
		//sheet 여러개일 경우
		//poiAdd.sheet_Excel2(filePath, fileName, sheetNameArr, subject, total_title, dataMap);
	}
	
	String realPath = URL+"excel_file/"+fileName;
	out.print(realPath);
	
	
%>	