<%@page import="risk.dashboard.main.MainMgr"%>
<%@page import="risk.util.DateUtil"%>
<%@page import="risk.json.JSONArray"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="risk.json.JSONObject"%>
<%@page import="risk.util.ParseRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	MainMgr mgr = new MainMgr();
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	String sDate = pr.getString("sDate");		//시작일
	String eDate = pr.getString("eDate");		//종료일	
	String company_code = pr.getString("company_code");
	String sg_seqs = pr.getString("sg_seqs");
	String mode = pr.getString("mode");					
	String keyword_type = pr.getString("keyword_type");					
	String search_keyword = pr.getString("search_keyword");					
	
	JSONObject result = new JSONObject();
	JSONArray arrJson = null;
	if("circle".equals(mode)){
	arrJson = new JSONArray(mgr.get_TitleCnt(sDate, eDate, company_code, sg_seqs, keyword_type, search_keyword));
	} else if("senti".equals(mode)){
	arrJson = new JSONArray(mgr.get_SentiCnt(sDate, eDate, company_code, sg_seqs, keyword_type, search_keyword));
	}
	
	result.put("data", arrJson);
	
%>
<%=result.toString()%>

















