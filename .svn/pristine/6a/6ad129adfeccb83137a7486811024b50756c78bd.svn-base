<%@page import="risk.util.StringUtil"%>
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
    MainMgr  mgr = new MainMgr();
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	String sDate = pr.getString("sDate");		//시작일
	String eDate = pr.getString("eDate");		//종료일
	
	String department = pr.getString("department");
	String company_code = pr.getString("company_code");
	
	String type = pr.getString("type");					//집계할 코드의 타입
	String mode = pr.getString("mode");					//집계할 코드의 타입
	
	StringUtil su = new StringUtil();
	
	JSONObject result = new JSONObject();
	JSONArray arrJson = null; 
	if("subject".equals(mode)){
		arrJson = new JSONArray(mgr.getTopic_EachTypeCount(department, sDate, eDate, company_code, type ));
	}else if("siteGroup".equals(mode)){
		arrJson = new JSONArray(mgr.getTopic_EachTypeSgCount(department, sDate, eDate, company_code ));
	}else if("portal_reply".equals(mode)){
		String md_seq = pr.getString("md_seq");	
		ArrayList<HashMap<String, String>> tmp_result = mgr.getTopic_PortalReplyPer(md_seq);
		int tmp_cnt = Integer.parseInt(tmp_result.get(0).get("TOTAL_CNT"));
		////그래프 가운데 들어가는 전체 카운트 셋팅 1,000이상일 경우 k표시
		if(tmp_cnt>1000){
			tmp_cnt = (int)Math.floor(tmp_cnt/1000);
			result.put("TOTAL", su.digitFormat(tmp_cnt)+"k");			
		}else{
				
			result.put("TOTAL", su.digitFormat(tmp_cnt));
		}
		
		
		tmp_result.remove(0); //전체카운트는 그래프 리스트에서 삭제
		arrJson = new JSONArray(tmp_result);
		
				
	}
	
	result.put("data", arrJson);
%>
<%=result.toString()%>

















