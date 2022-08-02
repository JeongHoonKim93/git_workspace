<%@page import="risk.dashboard.main.MainMgr"%>
<%@page import="risk.util.DateUtil"%>
<%@ page import="java.util.Calendar" %>
<%@page import="risk.json.JSONArray"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="risk.json.JSONObject"%>
<%@page import="risk.util.ParseRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	MainMgr mgr = new MainMgr();
	DateUtil du = new DateUtil();
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	String info_sDate = "";						//정보추이 그래프 관련 시작일 (일자 간격 7 미만이면 sDate가 아닌 info_sDate 사용)
	String info_eDate = "";
	String sDate = pr.getString("sDate");		//시작일
	String eDate = pr.getString("eDate");		//종료일	
	String company_type = pr.getString("company_type");
	String company_code = pr.getString("company_code");
	String product_code = pr.getString("product_code", "");
	String issue_code = pr.getString("issue_code");
	String sg_seqs = pr.getString("sg_seqs");
	String mode = pr.getString("mode");
	String keyword_type = pr.getString("keyword_type");
	String search_keyword = pr.getString("search_keyword");
	
	String dayDiff = mgr.getDayDiff(sDate, eDate);
		
	Calendar c = Calendar.getInstance();
	
	java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
	
	c.setFirstDayOfWeek(Calendar.SUNDAY);
	String[] dates = eDate.split("-");
	
	int year = Integer.parseInt(dates[0]);
	int month = Integer.parseInt(dates[1])-1;
	int day = Integer.parseInt(dates[2]);
	
	c.set(year, month - 1, day);
	c.set(Calendar.YEAR,year);
	c.set(Calendar.MONTH,month);
	c.set(Calendar.WEEK_OF_MONTH,c.get(Calendar.WEEK_OF_MONTH));
	c.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
	
 	if(formatter.format(c.getTime()).compareTo(eDate) < 0){
		c.add(c.DATE,7);
	}

	info_eDate = formatter.format(c.getTime());
	
	if(mode.equals("weekly")){		
		du.setDate(eDate);
		du.addDay( - 6);
		info_sDate = du.getDate("yyyy-MM-dd");
	} else if(mode.equals("monthly")){
		du.setDate(info_eDate);
		du.addDay( - 48);
		info_sDate = du.getDate("yyyy-MM-dd");	
	}
	
	if("0".equals(dayDiff)){
		dayDiff="1";
	}else if(mode.equals("weekly") || mode.equals("monthly")){
		dayDiff="7";	
	}
	
	JSONObject result = new JSONObject();
	JSONArray arrJson = null;
	if(mode.equals("weekly")){
		arrJson = new JSONArray(mgr.get_InformationGraph(mode, info_sDate, eDate, company_code, sg_seqs, dayDiff, keyword_type, search_keyword));
	} else if(mode.equals("monthly")){
		arrJson = new JSONArray(mgr.get_InformationGraph(mode, info_sDate, info_eDate, company_code, sg_seqs, dayDiff, keyword_type, search_keyword));
	}else if(mode.equals("voc_information") || mode.equals("pr_information") || mode.equals("plcc_information")){	
		arrJson = new JSONArray(mgr.get_VOCInformationGraph(mode, sDate, eDate, company_code, product_code, sg_seqs, keyword_type, search_keyword));
	} else if(mode.equals("issue_information")){
		arrJson = new JSONArray(mgr.get_IssueInformationGraph(sDate, eDate, company_code, issue_code, dayDiff, keyword_type, search_keyword));		
	}

	result.put("data", arrJson);
	
%>
<%=result.toString()%>





