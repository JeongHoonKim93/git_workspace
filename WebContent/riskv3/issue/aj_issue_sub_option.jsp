<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
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
	IssueMgr issueMgr = new IssueMgr();
	DateUtil	 du = new DateUtil();
	StringUtil		su = new StringUtil();
	pr.printParams();
	
	String ptype = pr.getString("targetType");
	String pcode = pr.getString("targetCode");
	int depth = (pr.getInt("parentDepth")+1);
	String StandType = pr.getString("parentType");
	IssueCodeMgr 	icm = new IssueCodeMgr();
	IssueCodeBean icBean = new IssueCodeBean();
	ArrayList arrIcBean = new ArrayList();	 //분류코드 어레이
	icm.init(0);
	arrIcBean = icm.getSearchIssueCode(ptype, pcode);
	
	
	//String title = icm.getIssueCodeName(Integer.parseInt(ptype), Integer.parseInt(pcode));
	String checked = "";
%>
<%if(arrIcBean.size()>0){%> 
			<input type="hidden" id="focus_<%=StandType%>_<%=depth%>" value="">
			<%
					
					for (int i = 0; i < arrIcBean.size(); i++) {
						icBean = (IssueCodeBean) arrIcBean.get(i);			
				%>
					
					<input type='radio' id="input_info<%=icBean.getIc_type()%>_<%=icBean.getIc_code()%>" name="input_infoAttr_<%=icBean.getIc_type()%>" data-group="<%=StandType%>" data-depth="<%=depth%>" value="<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>"; onclick="getSubDepth(<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>,<%=depth%>,<%=StandType%>);" <%=checked %>/>
					<label for="input_info<%=icBean.getIc_type()%>_<%=icBean.getIc_code()%>" style="cursor:pointer;"><%=icBean.getIc_name()%></label>
					
					<%
						if((i+1)%5==0 && i!=0) {
							out.print("<br>");
						}						
					}
				 
	 } %>