<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="risk.util.*
				,risk.issue.IssueMgr"           
                 
%>    
<%
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	String yData = pr.getString("yData");
	String nData = pr.getString("nData");
	/* String sMtPno    = pr.getString("original_md_Pseq");
	String sMtno     = pr.getString("original_md_seq");
	String searchMode = pr.getString("original_searchmode");
	String issue_Check = pr.getString("original_issue_Check");   */  
	IssueMgr ismgr = new IssueMgr();
	ismgr.saveOriginalorNot(yData, nData, "ISSUE");

%>
<script language="JavaScript" type="text/JavaScript">
alert('원문저장 하였습니다.');
<%-- parent.openSameList('<%=issue_Check%>','<%=sMtPno%>','<%=sMtno%>','<%=searchMode%>'); --%>
</script>