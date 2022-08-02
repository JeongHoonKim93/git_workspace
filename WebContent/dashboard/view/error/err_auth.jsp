<%@page import="risk.util.ParseRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<jsp:include page="../inc/inc_page_top.jsp" flush="false" />
<%
	// 생성자
	ParseRequest pr	= new ParseRequest(request);
	
%>

<body>
	<div id="wrap" class="is-error">
        <div class="error">
            <div class="wrap">
                <div class="img"><img src="../../asset/img/err_auth.png" alt="권한없음"></div>
                <div class="title">본 서비스는 인가된 IP 및 접속 환경에서만<br>접속이 가능합니다.</div>
                <div class="msg">현재 사용자의 IP : <strong><%=request.getRemoteAddr()%></strong></div>
            </div>
        </div>
    </div>

<jsp:include page="../inc/inc_page_bot.jsp" flush="false" />
