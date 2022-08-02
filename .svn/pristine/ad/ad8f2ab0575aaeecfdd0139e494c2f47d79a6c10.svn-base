<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="risk.util.ParseRequest
				,risk.util.StringUtil
				,java.util.ArrayList
				,risk.issue.IssueCodeMgr
				,risk.issue.IssueCodeBean
				"%>
<%

	//이슈데이터 등록 관련
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	StringUtil su = new StringUtil();
	IssueCodeMgr icm = new IssueCodeMgr();
	IssueCodeBean icb = new IssueCodeBean();
	
	//String sel_issue = pr.getString("sel_issue","");
	
	String targetValue = pr.getString("targetValue","");
	
	String selectChk = "";
	
	String ic_pType = "";
	String ic_pCode = "";
	if(!targetValue.equals("")){
		ic_pType = targetValue.split(",")[0];
		ic_pCode = targetValue.split(",")[1];
	}
	
	ArrayList ar_data = icm.getSearchIssueCode("7",ic_pType, ic_pCode); 
%>           			

<%@page import="risk.issue.IssueCodeMgr;"%><select name="typeCode7">
<%					
				for (int i = 0; i < ar_data.size(); i++) {
					icb = (IssueCodeBean)ar_data.get(i);
%>
	<option value="<%=icb.getIc_type()%>,<%=icb.getIc_code()%>" <%=selectChk%>><%=icb.getIc_name()%></option>
<%
				}
%>
</select>

