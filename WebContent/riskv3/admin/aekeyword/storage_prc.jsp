<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,
				 risk.admin.aekeyword.StorageBean,
                 risk.admin.aekeyword.StorageMgr,
                 risk.util.ParseRequest" 
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	ParseRequest    pr = new ParseRequest(request);
	pr.printParams();
	String m_seq = SS_M_NO;
	String storageName = pr.getString("storageName");
	String checkIds = pr.getString("checkIds");
	String mode = pr.getString("mode");
	String type2 = pr.getString("type2");
	String targetSeq = pr.getString("targetSeq");
	String sto_seq = pr.getString("sto_seq");
	
	StorageMgr sMgr = new StorageMgr();
	if(mode.equals("insert")){
		if(sMgr.insertStorage(m_seq,storageName))
		{
%>
			 <script type="text/javascript">
			 	opener.loadList();
			 	window.close();
			 </script>
<%
		}else{
%>
			 <script type="text/javascript">
			 	alert('중복된 단어 입니다.');
			 	window.close();
			 </script>
<%
		}
	}else if(mode.equals("update")){		
		if(sMgr.updateStorage(m_seq,storageName,sto_seq))
		{
%>
			 <script type="text/javascript">
			 	opener.loadList();
			 	window.close();	
			 </script>
<%
		 }else{
			
%>
			 <script type="text/javascript">
			 	alert('기존 정보와 같거나 중복된 단어 입니다.');
			 	window.close();
			 </script>
<%

		}		
		
		
	}else if(mode.equals("delete")){
		if(sMgr.delStorage(checkIds))
		{
%>
			 <script type="text/javascript">
			 	parent.loadList();
			 	window.close();	
			 </script>
<%
		}else{
%>
			 <script type="text/javascript">
			 	alert('1000개이상 삭제 할 수 없습니다.');
			 	window.close();			 	
			 </script>
<%
		}
	}
%>