<%@page import="risk.report.ReportMgrMain"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../inc/sessioncheck.jsp" %>
<%@ page import="risk.util.ParseRequest
                ,risk.util.StringUtil
                ,risk.util.DateUtil
                ,risk.json.JSONArray
                ,risk.util.PageIndex" %>
<%
	ParseRequest pr	= new ParseRequest(request);
	pr.printParams();	
	DateUtil du	= new DateUtil();
	StringUtil	su 	= new StringUtil();
	
	ReportMgrMain reportMgr = new ReportMgrMain();

	String chartType = pr.getString("chartType");
	String chartBinary = pr.getString("chartBinary");
	String result =  reportMgr.saveChartImage(chartType, chartBinary, SS_M_NO); 
	System.out.println(result);
	
	out.print(result);
%>