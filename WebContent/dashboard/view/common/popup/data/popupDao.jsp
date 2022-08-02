<%@page import="risk.dashboard.view.popup.PopupMgr"%>
<%@page import="risk.util.DateUtil"%>
<%@page import="risk.util.ParseRequest"%>
<%@page import="risk.json.JSONArray"%>
<%@page import="risk.json.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Enumeration" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.*"%>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="java.lang.*" %>
<%@page import="risk.util.DateUtil"%>
<%
	ParseRequest pr = new ParseRequest(request);
	DateUtil du = new DateUtil();
	pr.printParams();
	PopupMgr pmgr = new PopupMgr();	
	
	int nowPage = pr.getInt("nowPage", 1);
	int rowCnt = pr.getInt("rowCnt", 10);
	String info_eDate = "";						//정보추이 그래프 관련 종료일	
	String info_sDate = "";						//정보추이 그래프 관련 시작일	
	String sDate = pr.getString("i_sdate","");	//검색 시작일
	String eDate = pr.getString("i_edate","");	//검색 마지막일
	String target_date = pr.getString("target_date","");
	
	String mode = pr.getString("mode","");		
	String ic_code = pr.getString("ic_code","");	//선택한 보도자료명 ic_code
	String senti = pr.getString("senti","");	//감성
	String selectedSenti = pr.getString("selectedSenti","");	//감성
	String sg_seq = pr.getString("sg_seq", "");	//사이트그룹
	String chart_type = pr.getString("chart_type", "day");
	
	//검색어
	String search = pr.getString("search","");
	String keyword_type = pr.getString("keyword_type","");
	String searchkey = pr.getString("searchkey","");
	
	String temp = pr.getString("temp", "");
	
	//비교기간
	String dayDiff = pmgr.getDayDiff(sDate, eDate);
	if("0".equals(dayDiff)){dayDiff="1";}
	String pre_eDate = pr.getString("pre_eDate",du.addDay_v2(sDate,-1));
	String pre_sDate = pr.getString("pre_sDate",du.addDay_v2(pre_eDate,-(Integer.parseInt(dayDiff)-1)));
	
	JSONObject jsonObj = null;
	JSONArray jsonArr = null;
	
	if("list".equals(mode)) {
		jsonObj = pmgr.getPopDataList(sDate, eDate, ic_code, senti, sg_seq, nowPage, rowCnt, searchkey);
	} else if("chart".equals(mode)) {
		jsonObj = pmgr.getPopChartList(sDate, eDate, ic_code, senti, sg_seq, searchkey, chart_type);
	} else if("relation".equals(mode)) {
		
		if(!selectedSenti.equals("")) {
			senti = selectedSenti;
		}
		
		jsonObj = pmgr.getPopRelationKeywordList(sDate, eDate, pre_sDate, pre_eDate, ic_code, senti, sg_seq, searchkey);
	}

	
	 if(null!= jsonObj){
		 out.println(jsonObj);
			System.out.println(jsonObj);		 
	} else { 
		out.println(jsonArr);	
		System.out.println(jsonArr);
	} 	
%>
