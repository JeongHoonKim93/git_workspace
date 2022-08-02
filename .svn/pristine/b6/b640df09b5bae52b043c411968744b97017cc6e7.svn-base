<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ page import="risk.util.ConfigUtil"%>				
<%@page import="risk.admin.member.MemberDao"%>
<%@page import="risk.admin.member.MemberBean"%>
<%@ include file="/riskv3/inc/sessioncheck.jsp" %>
<%
ConfigUtil cu = new ConfigUtil();
String siteUrl = cu.getConfig("URL");
MemberDao mDao = new MemberDao();
String mname = mDao.get_Mname(SS_M_NAME);
String mgseq = mDao.getMgseq(SS_M_NAME);
%>
<div class="util">
  <div class="wrap">
    <a class="user"><%=SS_M_NAME %>님</a>
    <a href="/riskv3/logout.jsp" class="ui_btn is-color-blk" title="Logout"><span class="icon">&#xe071;</span></a>
    <a href="/riskv3/main.jsp" class="ui_btn is-color-blk" target="_blank" title="System 바로가기"><span class="icon">&#xe070;</span></a>
  </div>
</div>
