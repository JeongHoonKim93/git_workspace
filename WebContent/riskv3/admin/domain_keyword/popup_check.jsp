<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="risk.util.*,
				 java.lang.*,
				 risk.admin.domain_keyword.DomainKeywordMng,
				 risk.admin.domain_keyword.DomainKeywordBean
                 "
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
	
	ParseRequest    pr = new ParseRequest(request);

    String xp			= pr.getString("xp");
    String yp			= pr.getString("yp");
    String zp			= pr.getString("zp");
    
    DomainKeywordMng wMgr = new DomainKeywordMng();
    int check = wMgr.popup_check(xp, yp, zp);
%>

<% out.println(check);%>