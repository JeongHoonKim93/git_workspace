<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="risk.util.DateUtil"%>
<%@ page import="risk.util.ParseRequest"%>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="risk.json.JSONObject" %>
<%@ page import="risk.json.JSONArray" %>
<%@ page import="risk.issue.IssueMgr" %>
<%
	ParseRequest pr	= new ParseRequest(request);
	pr.printParams();
	
	String sDateFrom = pr.getString("param_sDate");
	String sDateTo = pr.getString("param_eDate");
	String ir_stime = pr.getString("param_sTime", "00");
	String ir_etime = pr.getString("param_eTime", "23");
	
	String typeCodeSeries = pr.getString("typeCodeSelect");
	String sentiSeries = pr.getString("typeCodeSenti");
	String infoSeries = pr.getString("typeCodeInfo");
	String relationKeywordSeries = pr.getString("relationKeyword");
	String register = pr.getString("registerInput");
	String searchKeyword = URLDecoder.decode(pr.getString("param_searchKey"), "UTF-8");
	String searchKeyType = pr.getString("param_keyType");

	// 생성자
	DateUtil du = new DateUtil();
	IssueMgr issueMgr = new IssueMgr();

	// 날짜 출력 표시 포맷
	if(ir_stime.length() < 2){
		ir_stime = "0"+ir_stime;
	}
	
	if(ir_etime.length() < 2){
		ir_etime = "0"+ir_etime;
	}
	
	String setStime = "";
	String setEtime = "";
	if(ir_stime.equals("24")){
		setStime = "23:59:59";	
	}else{
		setStime = ir_stime + ":00:00";
	}
	if(ir_etime.equals("24")){
		setEtime = "23:59:59";	
	}else{
		setEtime = ir_etime + ":59:59";
	}
	
	long diffSecond = du.DateDiffSecond("yyyy-MM-dd HH", sDateTo + " " + ir_etime, sDateFrom + " " + ir_stime);
	int diffHour = (int)(diffSecond /(60*60));  
	String dateFormat = "";
	boolean parseDatesTF = false;
	if(diffHour > 24){
		dateFormat = "%Y%m%d";
		parseDatesTF = true;
	} else {
		dateFormat = "%Y%m%d %H";
		parseDatesTF = false;
	}
	
	JSONArray jsonArray = issueMgr.getChartJSON(dateFormat, sDateFrom, setStime, sDateTo, setEtime, typeCodeSeries, sentiSeries, infoSeries, relationKeywordSeries
												, searchKeyType, searchKeyword, register);
	
	JSONObject jsonResult = new JSONObject();
	
	jsonResult.put("parseDatesTF", parseDatesTF);
	jsonResult.put("data", jsonArray);
	
	out.print(jsonResult.toString());

%>