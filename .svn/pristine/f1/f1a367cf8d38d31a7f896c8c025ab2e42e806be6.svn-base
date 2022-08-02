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
	int mainType = pr.getInt("mainType");
	
	if(mainType != -1){
		IssueCodeMgr icMgr = new IssueCodeMgr();
		icMgr.init(1);
		ArrayList arrIcBean = icMgr.GetType(mainType);
%>
	<option value="">선택하세요</option>
<%
		for(int i=0; i < arrIcBean.size(); i++){
			IssueCodeBean icBean = (IssueCodeBean)arrIcBean.get(i);
			if(mainType == 13 && (selectType == 5 || selectType == 7) && icBean.getIc_code() != 2){
				continue;
			}
%>
	<option value="<%=selectType%>,<%=selectCode%>,<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>"><%=icBean.getIc_name()%></option>
<%
		}
	} else {
		IssueMgr issueMgr = new IssueMgr();
		ArrayList relationKeyList = issueMgr.getTypeCodeRelationKey(selectType, selectCode);
		
%>
	<option value="">선택하세요</option>
<%
		for(int i=0; i < relationKeyList.size(); i++){
			String[] relKey = (String[])relationKeyList.get(i);
%>
	<option value="<%=selectType%>,<%=selectCode%>,<%=relKey[0]%>"><%=relKey[1]%></option>
<%			
		}
	}
%>
