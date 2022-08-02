
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.ArrayList
                 ,risk.util.StringUtil
                 ,risk.util.ParseRequest
                 ,risk.sms.AddressBookDao
				 "
%>

<%
    StringUtil      su = new StringUtil();
    ParseRequest    pr = new ParseRequest(request);

    String n = pr.getString("n"); 
    
    AddressBookDao abd = new AddressBookDao();
	String url = abd.smsurlsearch(n);	
%>

<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<script>
<!--
	document.location = '<%=url%>';
-->
</script>
</body>
</html>