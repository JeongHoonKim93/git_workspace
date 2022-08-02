<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,
				 risk.admin.info.*,                            
                 risk.util.*,
                 java.net.*" 
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
	
	ParseRequest    pr = new ParseRequest(request);
	DateUtil du = new DateUtil();
	pr.printParams();
	
	ArrayList arr = new ArrayList();
	InfoGroupMgr igMgr = new InfoGroupMgr();
	InfoGroupBean igBean = new InfoGroupBean();
	arr = igMgr.getInfoGroup();
	
%>
<table id="board_01" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
<col width="5%"><col width="65%"><col width="15%"><col width="15%">
	<tr>           
		<th><input type="checkbox" name="checkAll" onclick="selectAll(this.checked);"></th>
		<th>정보그룹</th>
		<th>등록자</th>
		<th>등록일</th>
	</tr>
<%
		if(arr!=null)
		{
			for(int i =0; i<arr.size(); i++)
			{
				igBean = (InfoGroupBean)arr.get(i);
%> 
	<tr>
		<td><input type="checkbox" name="checkId" value="<%=igBean.getI_seq()%>"></td>
		<td style="cursor:pointer" onclick="popupEdit('<%=igBean.getI_seq()%>','update');"><p class="board_01_tit"><%=igBean.getI_nm()%></p></td>
		<td><%=igBean.getM_name()%></td>
		<td><%=du.getDate(igBean.getI_regdate(),"yyyy/MM/dd")%></td>
	</tr>
<%
				}
		}
%>
</table>