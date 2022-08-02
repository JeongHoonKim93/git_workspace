<%@page import="risk.dashboard.main.MainMgr"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.HashMap"%>
<%@page import="risk.util.Paging"%>
<%@page import="java.util.HashSet"%>
<%@page import="java.util.ArrayList"%>
<%@page import="risk.util.ParseRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();	
	MainMgr mgr = new MainMgr();
	HashMap<String, String> map = new HashMap<String, String>();
	
	String mode = pr.getString("mode");
	String code = pr.getString("code");
   	String option = pr.getString("issue_option","1");
	ArrayList<String[]> issuelist =  mgr.getIssueList(code);
%>
   <h4>주요 이슈<em> - </em></h4>
   <div class="box_header_rc">
       <div class="dcp is-only-web">
           <select name="issue_option" id="s09_issue" style="min-width: 150px; max-width: 300px;">
           <%
        	String[] tmp_info = null;
       		 for(int i=0; i<issuelist.size(); i++){
        		tmp_info = issuelist.get(i);
      	   %>
        	<option value="<%=tmp_info[1]%>"><%=tmp_info[0]%></option>     
      	   <%}%>
           </select><label for="s09_issue"></label>
       </div>
   </div>

   