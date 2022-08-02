<%@page import="risk.issue.IssueCodeBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import=" java.util.ArrayList
                , risk.util.ParseRequest
                ,risk.issue.IssueMgr
                , risk.issue.IssueCodeMgr
                "%>
<%@ include file="../inc/sessioncheck.jsp" %>
<%
	IssueMgr issueMgr = new IssueMgr();
	System.out.println("SK json_typecode.jsp");
	ParseRequest pr	= new ParseRequest(request);

	
	String IC_CODE = pr.getString("icCode");
	
	ArrayList arData = issueMgr.getRelationKeyword(IC_CODE);
	
	String result = "";
	
	//json 데이터 만들기~
	if(arData != null){
		result += "[";
		IssueCodeBean bean = null;
		for(int i = 0; i < arData.size(); i++){
			bean = (IssueCodeBean) arData.get(i);
			
			result += "{ \"name\":\""+ bean.getIc_name() +"\"}";
			        
			if(i != arData.size() -1){
				result += ",";	
			}
		}
		
		result += "]";
	}
	System.out.print(result);
	out.print(result);
%>