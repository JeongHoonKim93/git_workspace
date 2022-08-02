<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../inc/sessioncheck.jsp" %>
<%@ page import="risk.issue.IssueDataBean
                ,risk.issue.IssueMgr
                ,java.util.ArrayList
                ,risk.util.ParseRequest
                ,risk.issue.IssueCodeMgr
                ,risk.issue.IssueCodeBean
                ,risk.issue.IssueSuperBean
                ,risk.issue.IssueBean                
                ,risk.search.MetaMgr
                ,risk.search.MetaBean
                ,risk.search.userEnvMgr
                ,risk.search.userEnvInfo
                ,risk.util.StringUtil
                ,risk.util.DateUtil
                ,risk.util.PageIndex
                ,java.net.URLDecoder" %>
<%
	ParseRequest pr	= new ParseRequest(request);
	pr.printParams();	
	DateUtil du	= new DateUtil();
	StringUtil	su 	= new StringUtil();
	IssueMgr issueMgr = new IssueMgr();
	MetaMgr smgr = new MetaMgr();
	
	String id_seqs = pr.getString("same_id_seqs" , "");
	String pseqs = issueMgr.updateMdPseqs(id_seqs);
	Boolean result = false;
	
	if(!pseqs.equals("")){
		smgr.UpdateSamePseq(pseqs);
		smgr.UpdateSameIssuePseq(pseqs);
		smgr.DeleteSamePseq(pseqs);
		
		result = true;
	}
	
	if(result){
		out.print("success");
	}else{
		out.print("fail");
	}
%>
