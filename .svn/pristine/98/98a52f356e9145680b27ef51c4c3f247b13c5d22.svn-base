<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,
				 risk.admin.aekeyword.StorageBean,
                 risk.admin.aekeyword.StorageMgr,
                 risk.search.MetaBean,
                 risk.search.MetaMgr,
                 risk.util.ParseRequest" 
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	ParseRequest    pr = new ParseRequest(request);
	StorageMgr smgr = new StorageMgr();
	pr.printParams();

	String m_seq = SS_M_NO;
	String storageName = pr.getString("storageName");
	String checkIds = pr.getString("checkIds");
	String mode = pr.getString("mode");
	String type2 = pr.getString("type2");
	String targetSeq = pr.getString("targetSeq");
	String sto_seq = pr.getString("sto_seq");
	String md_seqs = pr.getString("md_seqs");
	String subMode = pr.getString("subMode","");
	
	if(mode.equals("insert")){
		
	
		if(smgr.insertStorageList(md_seqs,storageName,subMode))
		{
%>	
			 <script type="text/javascript">
			 	window.opener.location.reload();
				window.close();
			 </script>
<%
		}else{
%>
			 <script type="text/javascript">
			 	alert('중복된 게시글 입니다.');
			 	window.close();
			 </script>
<%
		} 
	} else {
%>
		 <script type="text/javascript">
		 	window.close();
		 </script>
<%
	} 
%>
