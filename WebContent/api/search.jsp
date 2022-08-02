<%@page import="risk.api.SearchMgr"%>
<%@page import="risk.util.ParseRequest"%>
<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%
SearchMgr sMgr = new SearchMgr();

ParseRequest pr = new ParseRequest(request);
String domain = request.getServerName();

//String id			= pr.getString("id","");  
String search_date	= pr.getString("search_date","");  
String last_seq		= pr.getString("last_seq","");
String size			= pr.getString("size","");
int search_date_cnt	= pr.getInt("search_date_cnt",1);
String jsonStr = "";

/* 
String label_seq	= pr.getString("label_seq","");   
String search_type 	= pr.getString("search_type","");
String search_key 	= pr.getString("search_key","");
 */
/*
if(size == null || "".equals(size)){
	size = "20";
}
*/
 if(last_seq == null || "".equals(last_seq)){
		last_seq = "0";
	}
  
jsonStr = sMgr.searchWebwatch(search_date, last_seq, size, search_date_cnt, domain);

%>

<%=jsonStr %>