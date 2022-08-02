<%@ page contentType="text/html; charset=EUC-KR" %>
<%@ page import="risk.admin.domain_keyword.DomainKeywordMng,
                 risk.util.DateUtil,
                 risk.util.ParseRequest" 
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
    DateUtil        du = new DateUtil();
	DomainKeywordMng kg = new DomainKeywordMng();
	
	response.setContentType("application/vnd.ms-excel; charset=utf-8") ;
    response.setHeader("Content-Disposition", "attachment;filename=keyword_"+ du.getCurrentDate("yyyyMMdd") +".xls");
    response.setHeader("Content-Description", "JSP Generated Data");
	
	String kgHtml = kg.getAllKGHtml();
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style>
<!--
body {
	scrollbar-face-color: #FFFFFF; 
	scrollbar-shadow-color:#B3B3B3; 
	scrollbar-highlight-color:#B3B3B3; 
	scrollbar-3dlight-color: #FFFFFF; 
	scrollbar-darkshadow-color: #EEEEEE; 
	scrollbar-track-color: #F6F6F6; 
	scrollbar-arrow-color: #8B9EA6;
}
-->
</style>
<body bgcolor="#FFFFFF" leftmargin="15" topmargin="5">
<%
	out.println(kgHtml);
%>
</body>