<%@page import="risk.util.StringUtil"%>
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
	StringUtil su = new StringUtil();
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	MainMgr mgr = new MainMgr();
	String info_sDate = "";						//정보추이 그래프 관련 시작일
	String info_eDate = "";						//정보추이 그래프 관련 종료일 
	String sDate = pr.getString("sDate");		//시작일
	String eDate = pr.getString("eDate");		//종료일
	String company_type = pr.getString("company_type");
	String company_code = pr.getString("company_code");
	String sg_seqs = pr.getString("sg_seqs");
	String keyword_type = pr.getString("keyword_type");
	String search_keyword = pr.getString("search_keyword");
	String mode = pr.getString("mode");
	String peDate = "";
	String psDate = "";
	int interval = 0;
	DateUtil du = new DateUtil();	
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

 	if(mode.equals("weekly_cnt")){
		info_eDate = eDate;
		du.setDate(info_eDate);
		du.addDay( - 6);
		info_sDate = du.getDate("yyyy-MM-dd");
		interval = 7;
	} else if(mode.equals("monthly_cnt")){
		info_eDate = formatter.format(c.getTime());
		du.setDate(info_eDate);
		du.addDay( - 48);
		info_sDate = du.getDate("yyyy-MM-dd");	
		interval = 49;
	}

	//이전기간 수치 
	//자사 - 전체 수치
	du.setDate(info_sDate);
	du.addDay( - interval);
	psDate = du.getDate("yyyy-MM-dd");
	
	du.setDate(info_eDate);
	du.addDay( - interval);
	peDate = du.getDate("yyyy-MM-dd");
		
	HashMap<String, String> currentCnt_map = mgr.get_InformationCNT(company_type, company_code, info_sDate, info_eDate, psDate, peDate, sg_seqs, keyword_type, search_keyword);	
	JSONObject result = new JSONObject();	
	result.put("CNT_D", su.digitFormat(currentCnt_map.get("CNT_D")));
	result.put("FACTOR_CNT", su.digitFormat(currentCnt_map.get("FACTOR_CNT")));
	result.put("POINT", su.digitFormat(currentCnt_map.get("POINT")));
	result.put("upDown", (result.getInt("POINT")==1)?"up":(result.getInt("POINT")==-1)?"dn":"none");	
%>
<% 
	if(company_code.equals("1")){ %>
	   <strong class="title is-card">현대카드</strong> <!-- 캐피탈 : 'is-capital' / 카드 : 'is-card' -->
<%  } else if(company_code.equals("2")){ %>
	   <strong class="title is-capital">현대캐피탈</strong> <!-- 캐피탈 : 'is-capital' / 카드 : 'is-card' -->	   
<%  } %>
	   <span class="dv"><button type="button" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><%=result.getString("CNT_D")%></button></span>
<% 
	if(result.getString("POINT").equals("1")){ %>	   
	   <span class="ui_fluc after up"><%=result.getString("FACTOR_CNT")%></span> <!-- up: 증가 / dn: 감소 / none: 변화없음 -->
<%  } else if(result.getString("POINT").equals("-1")){ %>
	   <span class="ui_fluc after dn"><%=result.getString("FACTOR_CNT")%></span> <!-- up: 증가 / dn: 감소 / none: 변화없음 -->
<%  } else if(result.getString("POINT").equals("0")){ %>
	   <span class="ui_fluc after none"><%=result.getString("FACTOR_CNT")%></span> <!-- up: 증가 / dn: 감소 / none: 변화없음 -->
<%	} %>