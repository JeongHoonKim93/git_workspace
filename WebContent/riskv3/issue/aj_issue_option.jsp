<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList
				,risk.util.ParseRequest
				,risk.util.DateUtil
				,risk.util.StringUtil
				,risk.issue.IssueCodeMgr
				,risk.issue.IssueCodeBean
				,risk.issue.IssueBean	
				,risk.issue.IssueMgr
				,java.util.List
				,java.util.Iterator
				,risk.issue.IssueDataBean"
%>
<%
	ParseRequest pr = new ParseRequest(request);		
	DateUtil	 du = new DateUtil();
	StringUtil		su = new StringUtil();
	pr.printParams();

	String ptype = pr.getString("targetType");
	String pcode = pr.getString("targetCode");
	IssueCodeMgr 	icm = new IssueCodeMgr();
	IssueCodeBean icb = new IssueCodeBean();
	ArrayList arrIcBean = new ArrayList();	 //분류코드 어레이
	icm.init(0);
	
	arrIcBean = icm.getSearchIssueCode(ptype, pcode);
	
	String title = icm.getIssueCodeName(Integer.parseInt(ptype), Integer.parseInt(pcode));
	String checked = "";
/* 	if(ptype.equals("2")){
		checked = "checked=checked";
	} */
%>
<div id="typeCode<%=ptype %>_idx<%=pcode%>">	
<div style="height:5px"></div>
<input type="hidden" name="ptypeCode" value="<%=ptype%>,<%=pcode%>">
<span style="font-weight:bold;font-size:12px; font-family:dotum;"> &nbsp;<%=title%>*</span><br>
	<%
		for(int i=0; i<arrIcBean.size(); i++){
			icb = (IssueCodeBean) arrIcBean.get(i);
	%>
	<div style='display:inline-block;*display:inline;*zoom:1;width:150px'>
		<input type='checkbox' id='typeCode<%=icb.getIc_type()%>_<%=icb.getIc_code() %>' name='typeCode<%=icb.getIc_type()%>' onclick="getTrend(this, '<%= ptype%>', '<%= pcode%>');" value='<%=icb.getIc_type()%>,<%=icb.getIc_code()%>' <%=checked%>><%=icb.getIc_name() %>
	</div>
	<% } %>
<div style="height:5px"></div>
</div>
