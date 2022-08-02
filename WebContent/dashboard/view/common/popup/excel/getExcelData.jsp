<%@page import="java.util.HashMap"%>
<%@page import="risk.dashboard.view.popup.PopupMgr"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="risk.util.*"%>
<%
	PopupMgr  mgr = new PopupMgr();
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	
	DateUtil du = new DateUtil();
	
	String sDate = pr.getString("i_sdate");		//시작일
	String eDate = pr.getString("i_edate");		//종료일
	
	String title = pr.getString("subject");
	String mode = pr.getString("mode","");		
	String ic_code = pr.getString("ic_code","");	//선택한 보도자료명 ic_code
	String senti = pr.getString("senti","");	//감성
	String selectedSenti = pr.getString("selectedSenti","");	//감성
	String sg_seq = pr.getString("sg_seq", "");	//사이트그룹
	String chart_type = pr.getString("chart_type", "day");
	
	//검색어
	String searchkey = pr.getString("searchkey","");

	
	String fileName = "BING_POPUP_DATA_"+du.getDate("yyyyMMdd_HHmmss")+".xlsx";
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
	String dayDiff = mgr.getDayDiff(sDate, eDate);
	if("0".equals(dayDiff)){dayDiff="1";}
	
	String pre_eDate = pr.getString("pre_eDate",du.addDay_v2(sDate,-1));
	String pre_sDate = pr.getString("pre_sDate",du.addDay_v2(pre_eDate,-(Integer.parseInt(dayDiff)-1)));
	
	int idx = 0;
	
	
	//전체 현황
	if("list".equals(mode)){
		sheetNameArr = new String []{""};
		
		//보도자료 목록 및 확산 추적
		titleArr = new String[]{"출처","제목","URL","수집일","감성"};
		total_title.add(idx, titleArr);
		excelList = mgr.getPopDataList_excel(sDate, eDate, ic_code, senti, sg_seq, searchkey, titleArr.length);
		
		dataMap.put(idx, excelList);	

		subject = title +" ["+sDate+"~"+eDate+"]";
		//sheet 한개일 경우
		poiAdd.addExcel(filePath, fileName, subject, titleArr, excelList);
		//sheet 여러개일 경우
		//poiAdd.sheet_Excel2(filePath, fileName, sheetNameArr, subject, total_title, dataMap);
		
	} else if("chart".equals(mode)) {
		sheetNameArr = new String []{""};
		
		//보도자료 목록 및 확산 추적
		titleArr = new String[]{"일자","정보량"};
		total_title.add(idx, titleArr);
		excelList = mgr.getPopChartList_excel(sDate, eDate, ic_code, senti, sg_seq, searchkey, chart_type, titleArr.length);
		
		dataMap.put(idx, excelList);	

		subject = title +" ["+sDate+"~"+eDate+"]";
		//sheet 한개일 경우
		poiAdd.addExcel(filePath, fileName, subject, titleArr, excelList);
		//sheet 여러개일 경우
		//poiAdd.sheet_Excel2(filePath, fileName, sheetNameArr, subject, total_title, dataMap);
	} else if("relation".equals(mode)) {
		sheetNameArr = new String []{""};
		
		//보도자료 목록 및 확산 추적
		titleArr = new String[]{"연관어","연관량","증감률"};
		total_title.add(idx, titleArr);
		excelList = mgr.getPopRelationKeywordList_excel(sDate, eDate, pre_sDate, pre_eDate, ic_code, senti, sg_seq, searchkey, titleArr.length);
		
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