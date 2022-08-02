<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="../inc/sessioncheck.jsp" %>
<%@ page import="risk.issue.IssueMgr" %>
<%@ page import="risk.search.MetaBean" %>
<%@ page import="risk.util.ParseRequest" %>
<%
	ParseRequest pr = new ParseRequest(request);
	String id_seq = pr.getString("id_seq", "");	
	IssueMgr ismgr = new IssueMgr();	
	String content = ismgr.getIdContent(id_seq).replaceAll("\n\n\n", "");
%>
<html>
<head>
<title><%=SS_TITLE%></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" href="<%=SS_URL%>/riskv3/css/basic.css" type="text/css">
</head>
<body>
<table border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td><%=content.replaceAll("\n", "<br>")%></td>
	</tr>	
</table>
</body>
</html>