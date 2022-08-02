<%@ page contentType = "text/html; charset=utf-8"%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="risk.util.ParseRequest"%>
<%@ page import="risk.admin.keyword.KeywordMng"%>
<%@ page import="risk.admin.keyword.KeywordBean"%>
<%
	ParseRequest pr = new ParseRequest(request);
	KeywordMng km = new KeywordMng();
	KeywordBean kb = new KeywordBean();
	
	String xp = pr.getString("xp");
	ArrayList ypList = new ArrayList();
	ypList = km.getYpList(xp);
	if(ypList.size() > 0){
			out.print("<option value=\"\">중그룹</option>");
		for(int i = 0; i < ypList.size(); i++){
			kb = (KeywordBean)ypList.get(i);
			out.print("<option value=\""+kb.getK_yp()+"\">"+kb.getK_value()+"</option>");
		}
	}
%>