<%@page import="risk.util.StringUtil"%>
<%@page import="risk.dashboard.main.MainMgr"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.HashMap"%>
<%@page import="risk.util.Paging"%>
<%@page import="risk.util.DateUtil"%>
<%@ page import="risk.util.ConfigUtil" %>
<%@page import="java.util.HashSet"%>
<%@page import="java.util.ArrayList"%>
<%@page import="risk.util.ParseRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();	
	MainMgr mgr = new MainMgr();

	String name = "";
 	String issue_text = pr.getString("issue_text", "");
	String proceed_code = pr.getString("proceed_code");
	String action_code = pr.getString("action_code");
	String select_id_seq = pr.getString("select_id_seq");
 	
	String mode = pr.getString("mode");
	String code = pr.getString("code");
	String company_code = pr.getString("company_code");
	
	String UpdateIssueData = mgr.Update_IssueData(mode, issue_text, proceed_code, action_code, select_id_seq, company_code);
%>