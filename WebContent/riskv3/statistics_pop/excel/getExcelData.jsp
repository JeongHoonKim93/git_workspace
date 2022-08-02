<%@page import="risk.statistics_pop.StatisticsPopMgr"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="risk.util.DateUtil"%>
<%@page import="risk.json.JSONArray"%>
<%@page import="java.util.Map"%>
<%@page import="risk.json.JSONObject"%>
<%@page import="risk.util.ParseRequest"%>
<%@page import="risk.util.*"%>

<%
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	DateUtil du = new DateUtil();
	
	String sDate = pr.getString("sDate");
	String eDate = pr.getString("eDate");
	String psDate =pr.getString("PsDate");
	String peDate = pr.getString("PeDate");
	
	String settingCode = pr.getString("settingCode");
	String orderBy = pr.getString("orderBy"); //정렬	
	String orderByName  = pr.getString("orderByName"); //정렬
	String sg_code = pr.getString("site_group_code");
	
	String type = pr.getString("type");
	String typeName = pr.getString("typeName");
	
	String code = pr.getString("code");
	String type2 = pr.getString("type2");
	String code2 = pr.getString("code2");
	String title = pr.getString("title");
	
	String mode = pr.getString("mode");	
	
	StatisticsPopMgr mgr = new StatisticsPopMgr();
	
	String fileName = "CJ_STATISTICS_DATA_"+du.getDate("yyyyMMdd_HHmmss")+".xlsx";
	ConfigUtil cu = new ConfigUtil();
	String URL = cu.getConfig("URL");
	String  filePath = cu.getConfig("EXCELPATH");
	
	String[] sheetNameArr = null;
	String[] titleArray = null;
	
	POIExcelAdd poiAdd = new POIExcelAdd();			
	
	ArrayList excelList = new ArrayList();
	String[] titleArr = null;
	ArrayList<String[]> total_title = new ArrayList<String[]>() ;
	String subject ="";
	

	/**/
	
	if("section_10".equals(mode)){
		
		sheetNameArr = new String []{"제품순위"};
		
		titleArr = new String[]{"제품","비교일","기준일","변화량(건수)","변화량(증가율)"};
		
		subject = title +"   [기준일:"+sDate+"~"+eDate+" / 비교일 :"+psDate+"~"+peDate+"] [분류:"+typeName+" / 정렬:"+orderByName+" ]";
		excelList = mgr.getDataList_excel(type, sDate, eDate, psDate, peDate, settingCode, sg_code, orderBy,  titleArr.length);	
		
	}else if("section_11".equals(mode)){
			
		sheetNameArr = new String []{"세부 내용 - 분류"};
			
		titleArr = new String[]{"분류","비교일","기준일","변화량(건수)","변화량(증가율)"};
		subject = title +"   [기준일:"+sDate+"~"+eDate+" / 비교일 :"+psDate+"~"+peDate+"] [분류:"+typeName+" / 정렬:"+orderByName+" ]";
			
		excelList = mgr.getDataList2_excel(type, type2, code, sDate, eDate, psDate, peDate, settingCode, sg_code, orderBy,  titleArr.length);	
			
	}else if("section_12".equals(mode)){
		
		sheetNameArr = new String []{"세부 내용 - 연관키워드"};
		
		titleArr = new String[]{"분류","비교일","기준일","변화량(건수)","변화량(증가율)"};
		
		subject = title +"   [기준일:"+sDate+"~"+eDate+" / 비교일 :"+psDate+"~"+peDate+"] [분류:"+typeName+" / 정렬:"+orderByName+" ]";
		excelList = mgr.getRelationKeywordList_excel(type, code, sDate, eDate, psDate, peDate, settingCode, sg_code, orderBy,  titleArr.length);	
		
	}else if("section_21".equals(mode)){
		
		sheetNameArr = new String []{"기간별 분석"};
		
		titleArr = new String[]{"일자","건수"};
		
		subject = title +"   [기준일:"+sDate+"~"+eDate+" / 비교일 :"+psDate+"~"+peDate+"]";
		excelList = mgr.getEachTypeDataList_excel(type, code, type2, code2, sDate, eDate, psDate, peDate, settingCode, sg_code, titleArr.length);	
		
	}else if("section_31".equals(mode)){
		String[] printType = new String[]{"11","12","13","14","6","7","8","9","20","21"}; //제품군-11,제품군2-12, 제품군3-13, 제품군4-14,게시1-6,게시2-7,게시3-8,온라인클레임-9,온라인유포언급-20,온라인이슈등급-21
		sheetNameArr = new String []{"데이터 리스트"};
		
		titleArr = new String[]{ "출처","제목","URL","수집시간","제품군 1","제품군 2","제품군 3","제품군 4","게시물 유형 1","게시물 유형 2","게시물 유형 3","온라인이슈 클레임인입","온라인이슈 유포언급","온라인이슈 이슈등급","등록자"};
		
		subject = title +"   [기준일:"+sDate+"~"+eDate+" / 비교일 :"+psDate+"~"+peDate+"]";
		excelList = mgr.getRowDataList_excel( type,  code, type2, code2, sDate,  eDate,  psDate, peDate, settingCode,  sg_code,  printType, titleArr.length);
		
	}
				
	
	//sheet 한개일 경우
	poiAdd.addExcel(filePath, fileName, subject, titleArr, excelList);
	//sheet 여러개일 경우
	//poiAdd.sheet_Excel2(filePath, fileName, sheetNameArr, subject, total_title, dataMap);
	
	
	String realPath = URL+"excel_file/"+fileName;
	out.print(realPath);
	
%>	
