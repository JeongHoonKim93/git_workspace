<%@page import="risk.json.JSONObject"%>
<%@page import="risk.report.ReportMgrMain"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../inc/sessioncheck.jsp" %>
<%@ page import="risk.util.ParseRequest
                ,risk.search.userEnvMgr
                ,risk.search.userEnvInfo
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
	
	String ir_sdate = pr.getString("ir_sdate");
	String ir_edate = pr.getString("ir_edate");
	
	String ir_stime = pr.getString("ir_stime");
	String ir_etime = pr.getString("ir_etime");
	
	//시간값 자릿수에 따른 처리
	if(ir_stime.length()==1){
		ir_stime = "0"+ir_stime+":00:00";
	}else if(ir_stime.length()==2){
		ir_stime = ir_stime+":00:00";
	}
	
	if(ir_etime.length()==1){
		ir_etime = "0"+ir_etime+":00:00";
	}else if(ir_etime.length()==2){
		ir_etime = ir_etime+":00:00";
	}	
	
	JSONArray chartData = null;
	JSONObject chartDataObj = null;
	
	if(chartType.equals("chart1")){
		chartDataObj = reportMgr.getChannelCount(ir_sdate, ir_edate, ir_stime, ir_etime);	//유통 경로별 정보 현황 - 채널별
	}else if(chartType.equals("chart2")){
		chartDataObj = reportMgr.getChannelSentiCount(ir_sdate, ir_edate, ir_stime, ir_etime);	//유통 경로별 정보 현황 - 채널별(긍부정)
	}
		
	if(null!=chartDataObj){
		out.println(chartDataObj.toString());
	}else{
		out.println(chartData.toString());
	}
	
%>
