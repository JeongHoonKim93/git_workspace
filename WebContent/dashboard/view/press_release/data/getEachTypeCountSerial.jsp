<%@page import="risk.dashboard.view.press_release.PressReleaseMgr"%>
<%@page import="risk.util.DateUtil"%>
<%@page import="risk.json.JSONArray"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="risk.json.JSONObject"%>
<%@page import="risk.util.ParseRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	PressReleaseMgr mgr = new PressReleaseMgr();
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	String sDate = pr.getString("i_sdate");		//시작일
	String eDate = pr.getString("i_edate");		//종료일
	
	String mode = pr.getString("mode");			//집계할 코드의 타입
	String ic_code = pr.getString("ic_code");	//선택된 보도자료명 ic_code
	
	JSONObject result = new JSONObject();
	JSONObject obj = new JSONObject();
	JSONArray arrJson = null;
	
	String dayDiff = mgr.getDayDiff(sDate, eDate); 
	if("0".equals(dayDiff)){dayDiff="1";}	
	
	
	if("channel".equals(mode)){
		arrJson = new JSONArray(mgr.getPressReleaseChannel(sDate, eDate, ic_code));
	} else if("amount".equals(mode)) {
		arrJson = new JSONArray(mgr.getPressReleaseAmount(sDate, ic_code, dayDiff));
	}
	result.put("data", arrJson);
	result.put("sdate", sDate);
	result.put("edate", eDate);
%>
<%=result.toString()%>

















