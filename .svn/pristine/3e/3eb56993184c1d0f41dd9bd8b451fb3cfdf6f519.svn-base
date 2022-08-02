<%@page import="java.net.URLDecoder"%>
<%@page import="risk.statistics_pop.StatisticsPopMgr"%>
<%@page import="risk.util.DateUtil"%>
<%@page import="risk.json.JSONArray"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="risk.json.JSONObject"%>
<%@page import="risk.util.ParseRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	StatisticsPopMgr mgr = new StatisticsPopMgr();
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	String sDate = pr.getString("sDate");
	String eDate = pr.getString("eDate");
	String psDate =pr.getString("PsDate");
	String peDate = pr.getString("PeDate");
	
	String settingCode = pr.getString("settingCode");
	String orderBy = pr.getString("orderBy"); //정렬	
	String sg_code = pr.getString("site_group_code");
	
	String type = pr.getString("type");
	String code = pr.getString("code");
	String type2 = pr.getString("type2");
	String code2 = pr.getString("code2");
	
	JSONObject result = new JSONObject();
	JSONArray arrJson = null;
		
	arrJson = new JSONArray(mgr.getEachTypeDataList(type, code, type2, code2, sDate, eDate, psDate, peDate, settingCode, sg_code));
		
	result.put("data", arrJson);
%>
<%=result.toString()%>

















