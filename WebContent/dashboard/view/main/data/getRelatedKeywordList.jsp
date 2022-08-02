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
	String sDate = pr.getString("sDate");		//시작일
	String eDate = pr.getString("eDate");		//종료일
	String md_seq = pr.getString("md_seq");		//종료일
	ArrayList<HashMap<String, String>> keyword_list = new MainMgr().getRelatedKeywordList(md_seq);
	JSONObject result = new JSONObject();
	JSONArray arrJson = new JSONArray(keyword_list);	
	
	//전체값 추가 요청
	String pat_seq_group = "";
	int total_cnt = 0;
	HashMap<String, String> tmp = new HashMap<String, String>();
	if(arrJson.length() > 0){
		for(int i=0; i<arrJson.length(); i++){
			tmp = keyword_list.get(i);
			if("".equals(pat_seq_group)){
				pat_seq_group = tmp.get("SEQ");
				total_cnt =  Integer.parseInt(tmp.get("CNT"));
			}else{
				pat_seq_group += ","+tmp.get("SEQ");
				total_cnt +=  Integer.parseInt(tmp.get("CNT"));
			}
		
		result.put("data", arrJson);
		result.put("TOTAL", total_cnt);
		result.put("SEQS", pat_seq_group);
	
	}
	} else{
		result.put("data", arrJson);
	}
	
%>
<%=result.toString()%>

















