<%@page import="risk.sms.AddressBookBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%@ page import="	java.util.ArrayList
					,java.net.*
                 	,risk.sms.AddressBookGroupBean
					,risk.sms.AddressBookDao
					,risk.util.ParseRequest									
				 	,java.util.List
					" 
%>
<%		
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	AddressBookDao abDao = new AddressBookDao();
	AddressBookGroupBean agBean = null;
	AddressBookBean abBean = null;
	ArrayList receiverList_group = new ArrayList();
	ArrayList receiverList = new ArrayList();
	
	//선택된 번호가 없을시 0으로 대체(0번 정보수신자는 없음)
	String selectedAbSeq = pr.getString("selectedAbSeq","0");
	String selectedAgSeq = pr.getString("selectedAgSeq","0");
	String ab_seq = pr.getString("ab_seq","");
	String ag_seq = pr.getString("ag_seq","");
	
	
	receiverList_group = abDao.getAdressBookGroupList(selectedAgSeq);
	receiverList = abDao.getAddressList(selectedAbSeq,"","","","","",ag_seq);
	
%>
		
<%
	
	//그룹 수신자
	if(receiverList_group!=null)
	{
%>
							<table width="100%"  border="0" cellspacing="0" cellpadding="0" >
<%
	for( int i=0 ; i < receiverList_group.size() ; i++ ) {
			
		agBean = (AddressBookGroupBean)receiverList_group.get(i);
%>
							<tr>
								<td class="pop_mail_group_td"><span style="font-weight:bold;color:#1886f7;"><%=agBean.getAg_name()%></span></td>
								<td style="text-align:right;display:none;" class="pop_mail_group_td"></td>
								<td style="text-align:right;" class="pop_mail_group_td"><img src="../../../images/common/btn_cancel.gif" onclick="selectLeftMoveGroup('<%=agBean.getAg_seq()%>')" style="cursor:pointer"/></td>
							</tr>
<%			 
	 }
%>
		
<%
	//개인 수신자
	if(receiverList!=null)	
	{
%>
							<table width="100%"  border="0" cellspacing="0" cellpadding="0" >
<%
		for( int i=0 ; i < receiverList.size() ; i++ ) {
			
			abBean = (AddressBookBean)receiverList.get(i);
%>
							<tr>
								<td class="pop_mail_group_td"><span style="font-weight:bold;color:#1886f7;"><%=abBean.getMab_name()%></span></td>
								<td style="text-align:right;display:none;" class="pop_mail_group_td"></td>
								<td style="text-align:right;" class="pop_mail_group_td"><img src="../../../images/common/btn_cancel.gif" onclick="selectLeftMove('<%=abBean.getMab_seq()%>')" style="cursor:pointer"/></td>
							</tr>
<%			 
		 }
	}
%>	
		
		
		
							</table>
<%
	}
%>
	
