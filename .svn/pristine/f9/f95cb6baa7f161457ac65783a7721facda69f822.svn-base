<%@page import="risk.util.LoginManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
		String result = "true";
		String USER_ID = (String)session.getAttribute("SS_M_ID")   == null ? "": (String)session.getAttribute("SS_M_ID")  ;
		int limitTime = 60*10; //고객사요청 - 10분제한
		long lastAccesseTime = session.getLastAccessedTime();
		long systemTime = System.currentTimeMillis();
		long chklastAccesseTime = (systemTime - lastAccesseTime) / 1000; //마지막 사용시간과 현재시간 차이
		

		if(null== request.getSession(false) || "".equals(USER_ID) || chklastAccesseTime >  limitTime ){
			result = "false";
		}
		
%>

<%=result%>

