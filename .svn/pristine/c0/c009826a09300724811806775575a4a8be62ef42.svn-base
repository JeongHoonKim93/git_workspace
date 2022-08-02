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
	AddressBookDao abDao = new AddressBookDao();
	AddressBookGroupBean abBean = null;
	ArrayList receiverList = new ArrayList();
	
	String selectedAbSeq = pr.getString("selectedAbSeq","");
	String selectedAgSeq = pr.getString("selectedAgSeq","");
	String ab_seq = pr.getString("ab_seq","");
	String ag_seq = pr.getString("ag_seq","");
	String ab_name = pr.getString("ab_name","");
	String ab_dept = pr.getString("ab_dept","");
	String ab_issue_receivechk = pr.getString("ab_issue_receivechk","");
	String ab_report_day_chk = pr.getString("ab_report_day_chk","");
	String ab_report_week_chk = pr.getString("ab_report_week_chk","");

	receiverList = abDao.getAdressBookGroupList(ag_seq,selectedAgSeq);	
	//receiverList = abDao.getAddressList(ab_seq,ab_name,ab_dept,ab_issue_receivechk,ab_report_day_chk,ab_report_week_chk,ag_seq,selectedAbSeq);
	
	pr.printParams();	
	String allReceivers = "";
	if(receiverList!=null)
	{
		
		for( int i=0 ; i < receiverList.size() ; i++ ) {
			
			abBean = (AddressBookGroupBean)receiverList.get(i);
			
			if(!allReceivers.equals("")){
				 allReceivers += ","+abBean.getAg_seq();
			}else {
				 allReceivers = String.valueOf(abBean.getAg_seq());
			}
		}
%>
<input type="hidden" id="receiverSeqGroup" name="receiverSeqGroup" value="<%=allReceivers%>">
<table width="100%"  border="0" cellspacing="0" cellpadding="0" >
<%
	for( int i=0 ; i < receiverList.size() ; i++ ) {
				
		 abBean = (AddressBookGroupBean)receiverList.get(i);
		 
%>
		<tr>
			<td class="pop_mail_group_td">
			<span style="font-weight:bold;color:#1886f7;"><%=abBean.getAg_name()%></span></td>
			<td style="text-align:right;" class="pop_mail_group_td"><img src="../../../images/common/btn_select.gif" onclick="selectRightGroupMove('<%=abBean.getAg_seq()%>')" style="cursor:pointer"/></td>
		</tr>
<%
	 }
%>
</table>
								

<%
	}else{
%>

<%
	}
%>
