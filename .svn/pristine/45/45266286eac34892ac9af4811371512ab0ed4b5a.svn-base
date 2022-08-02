<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ page import ="risk.issue.*
				  ,risk.util.*
				"
%>
<%@ include file="../inc/sessioncheck.jsp" %>
<%
	ParseRequest pr = new ParseRequest(request);
	IssueCodeMgr icMgr = new IssueCodeMgr();
	pr.printParams();
	
	String param_ic_type = pr.getString("param_ic_type");
	String issue_code = pr.getString("issue_code");
	
	String script_str="";
	boolean chk = icMgr.insertIssueCode(SS_M_NO, issue_code, param_ic_type);
	
	if(chk){
		script_str = "alert('키워드가 저장되었습니다.'); parent.window.opener.viewSelectBox(); parent.window.close();\n";
	}else{
		script_str = "alert('키워드저장이 실패하였습니다.'); parent.window.close(); \n";
	}
	

%>
<script language="JavaScript" type="text/JavaScript">
<%=script_str%>
</script>