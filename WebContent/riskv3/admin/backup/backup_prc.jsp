<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,
                 risk.util.ParseRequest,
                 risk.admin.backup.BackupListBean,
                 risk.admin.backup.BackupListMgr" 
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	ParseRequest    pr = new ParseRequest(request);
	pr.printParams();
	BackupListMgr bm = new BackupListMgr();
	
	String mName = SS_M_NAME;
	String bl_seq = pr.getString("bl_seq");
	String bl_tbName = pr.getString("bl_tbName");
	String bl_op = pr.getString("bl_op");
	String bl_useYn = pr.getString("bl_useYn");
	String bl_del_useYn = pr.getString("bl_del_useYn");
	String bl_day_term = pr.getString("bl_day_term");
	String ins_field_name = pr.getString("ins_field_name");
	String del_date_field_name = pr.getString("del_date_field_name");
	String del_date_field_type = pr.getString("del_date_field_type");
	String mode = pr.getString("mode");
	//String checkIds = pr.getString("checkIds");
	
	if(bl_useYn.equals("Y")){				 
		if(bm.UpdateBackupList(bl_seq, bl_tbName, bl_op, bl_useYn, bl_del_useYn, bl_day_term, ins_field_name, del_date_field_name, del_date_field_type ))
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
%>
<%-- 		
	}else if(mode.equals("delete")){
		if(bm.DeleteBackupLists(checkIds))
		{
%>
			 <script type="text/javascript">
			 	alert('삭제하였습니다.');
			 	parent.loadList();
			 	window.close();	
			 </script>
<%	
		} else{
%>
			 <script type="text/javascript">
			 	alert('1000개이상 삭제 할 수 없습니다.');
			 	window.close();			 	
			 </script>
<%
		}
		 --%>
<%
	} else if(bl_useYn.equals("N")) {
		if(bm.DeleteBackupList(bl_seq, bl_tbName)) {
%>
			<script type="text/javascript">
			 	
			 	opener.loadList();
			 	window.close();	
			 </script>
<%			
		} else {
%>
		 <script type="text/javascript">
		 	alert('저장에 실패하였습니다.');
		 	window.close();
		 </script>
<% 
		}
	} 

%>
<%-- 	else if(mode.equals("deleteList")) {
		if(bm.DeleteBackupList(bl_seq,bl_tbName)) {
			<script type="text/javascript">
			 	alert('삭제하였습니다.');
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
%> --%>