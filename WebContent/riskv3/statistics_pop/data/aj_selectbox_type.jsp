<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ page import = "risk.util.ParseRequest" %>
<%@ page import = "risk.issue.IssueCodeMgr" %>
<%@ page import = "risk.issue.IssueCodeBean" %>
<%@ page import = "risk.issue.IssueMgr" %>
<%@ page import = "java.util.ArrayList" %>
<%
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	
	int selectType = pr.getInt("selectType");
	int selectCode = pr.getInt("selectCode");
	
	IssueCodeMgr icMgr = new IssueCodeMgr();
	icMgr.init(1);
	ArrayList arrIcBean = icMgr.getSearchIssueCode( String.valueOf(selectType), String.valueOf(selectCode));
	
%>
	<option value="">선택하세요</option>
<%
		for(int i=0; i < arrIcBean.size(); i++){
			IssueCodeBean icBean = (IssueCodeBean)arrIcBean.get(i);			
%>
	<option value="<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>"><%=icBean.getIc_name()%></option>
<%
		}
	
%>
