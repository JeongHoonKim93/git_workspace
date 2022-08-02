<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%@ page import="	java.util.ArrayList
					,risk.util.ParseRequest
					,risk.issue.IssueCodeBean
					,risk.issue.IssueCodeMgr
					" %>
<%
	ParseRequest pr = new ParseRequest(request);
	IssueCodeMgr icm;
	IssueCodeBean icBean;
	pr.printParams();

	String seqList = pr.getString("seqList","");
	
	String[] seqs = seqList.split(",");
	
	icm = IssueCodeMgr.getInstance();
	
	for(int i=0;i<seqs.length;i++){
		if(!seqs[i].equals("")){
			icBean = new IssueCodeBean();
			icBean.setIc_seq(Integer.parseInt(seqs[i]));
			icBean.setIc_dispyn(pr.getString("dispYN"+seqs[i],""));
			icm.updateMainIssueDisplay(icBean);
		}
	}
	
	
%>
<SCRIPT LANGUAGE="JavaScript">
<!--
	location.href = 'mainissue_list.jsp';
//-->
</SCRIPT>