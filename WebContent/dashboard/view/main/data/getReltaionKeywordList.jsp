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

	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	String sg_seqs = pr.getString("sg_seqs");
	String company_type = pr.getString("company_type");
	String company_code = pr.getString("company_code");
	String sDate = pr.getString("sDate");		//시작일
	String eDate = pr.getString("eDate");		//종료일
	String keyword_type = pr.getString("keyword_type");		
	String search_keyword = pr.getString("search_keyword");		
	
	
	int rowLimit = pr.getInt("rowLimit",20);
	MainMgr mgr = new MainMgr();
	
	JSONObject result = new JSONObject();
	JSONArray arrJson = null; 
	
	arrJson = new JSONArray(mgr.get_RelationKeywordList(company_type, company_code, sDate, eDate, sg_seqs, rowLimit, keyword_type, search_keyword));

	result.put("data", arrJson);
	
%>
<%=result.toString()%>

















