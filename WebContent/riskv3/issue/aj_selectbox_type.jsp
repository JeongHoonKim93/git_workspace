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
	String depth = pr.getString("depth");
	
	IssueCodeMgr icMgr = new IssueCodeMgr();
	icMgr.init(1);
	//ArrayList arrIcBean = icMgr.GetType(mainType);
	ArrayList arrIcBean = icMgr.getSearchIssueCode( String.valueOf(selectType), String.valueOf(selectCode));
	
	
	
%>
	<select id="typeCode_<%=mainType%>_<%=depth%>" data-depth="<%=depth%>" name="typeCodeSelect_<%=mainType%>_<%=depth%>" class="textbox3 subType_<%=mainType%>" style="width:115px;  margin-right: 20px" onchange="getSubTypeCode(<%=mainType%>,<%=depth%>);">
	<option value="">선택하세요</option>
<%
	if(selectType != 0 && selectCode != 0 ) {
		
		for(int i=0; i < arrIcBean.size(); i++){
			IssueCodeBean icBean = (IssueCodeBean)arrIcBean.get(i);			
%>
	<option value="<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>"><%=icBean.getIc_name()%></option>
<%
		}
	}
%>
	</select>