<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,
				 risk.admin.hashtag.HashtagBean,
                 risk.admin.hashtag.HashtagMgr,
                 risk.util.ParseRequest" 
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	ParseRequest    pr = new ParseRequest(request);
	pr.printParams();
	String mName = SS_M_NAME;
	String mode = pr.getString("mode");
	String hcCode = pr.getString("hcCode");
	String hcName = pr.getString("hcName");
	
	HashtagMgr hMgr = new HashtagMgr();
	if(mode.equals("update")){
		if(hMgr.updatehCode(hcCode,hcName)) {
%>
			 <script type="text/javascript">
			 	alert('저장하였습니다.');
			 	opener.submitF();
			 	window.close();
			 </script>
<%
 		} else {
%>
			<script type="text/javascript">
			 	alert('기존 정보와 같거나 중복된 단어 입니다.');
			 	window.close();
			 </script>
<%
 		}
 	} else if(mode.equals("insert")) {
 		if(hMgr.inserthName(hcName)) {
%>
			 <script type="text/javascript">
			 	alert('저장하였습니다.');
			 	opener.submitF();
			 	window.close();
			 </script>
<%
 		} else {
%>
			<script type="text/javascript">
			 	alert('기존 정보와 같거나 중복된 단어 입니다.');
			 	window.close();
			 </script>
<%
 		}
 	} else if(mode.equals("delete")) {
 		if(hMgr.delKeyword(hcCode)) {
%>
			 <script type="text/javascript">
				alert('삭제하였습니다.');
			 	parent.submitF();
			 	window.close();
			 </script>
<%
 		} else {
%>
			<script type="text/javascript">
				alert('1000개이상 삭제 할 수 없습니다.');
			 	window.close();
			 </script>
<%
 		}
 	}
%>