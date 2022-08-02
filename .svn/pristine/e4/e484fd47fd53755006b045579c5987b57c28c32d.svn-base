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
	String yp = pr.getString("yp");
	ArrayList zpList = new ArrayList();
	zpList = km.getZpList(xp, yp);
	if(zpList.size() > 0){
			out.print("<option value=\"\">키워드</option>");
		for(int i = 0; i < zpList.size(); i++){
			kb = (KeywordBean)zpList.get(i);
			out.print("<option value=\""+kb.getK_seq()+"\">"+kb.getK_value()+"</option>");
		}
	}
%>