<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,
				 risk.admin.domain_keyword.DomainKeywordBean,
                 risk.admin.domain_keyword.DomainKeywordMng,
                 risk.util.ParseRequest,
                 risk.json.JSONArray,
                 risk.json.JSONObject,
                 java.net.*" 
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
	
	ParseRequest    pr = new ParseRequest(request);
	pr.printParams();
	
	//AJAX  사용시 한글 처리
	URLDecoder ud = new URLDecoder();
	String xp = pr.getString("xp");			
	String yp = pr.getString("yp");			
	String zp = pr.getString("zp");			
	
	System.out.println(xp + " " + yp + " " + zp);  
	
	
	DomainKeywordMng dkMgr = new DomainKeywordMng();
	JSONObject jsonObj = null;
	jsonObj = dkMgr.getBoardList(xp, yp, zp);
	JSONArray jarr = jsonObj.getJSONArray("list");
	
%>
<%-- <form name="keygroup" method="post">
<input type="hidden" name="xp" value="<%=xp%>"> <!--K_XP  -->
<input type="hidden" name="yp" value="<%=yp%>"> <!--K_YP  -->
<input type="hidden" name="zp" value="<%=zp%>"> <!--K_ZP  --> --%>
<table border="0" cellpadding="0" cellspacing="0"  id="board_01" style="table-layout:fixed;">
	<col width="10%"><col width="20%"><col width="20%"><col width="10%"><col width="33%"><col width="7%">
		<tr>               
			<!-- <th><input type="checkbox" name="checkAll" onclick="selectAll(this.checked);"></th> -->
			<th style="cursor:pointer; text-align:left; padding-left:3px;">범위</th>
			<th style="cursor:pointer; text-align:left;">채널</th>
			<th style="cursor:pointer; text-align:left;">사이트</th>
			<th style="text-align:left;">영역</th>
			<th style="text-align:left;">키워드</th>
			<th></th>
		</tr>
<% 	
	if( jarr != null && jarr.length() > 0){
		for(int i = 0; i < jarr.length(); i++){
			jsonObj = (JSONObject)jarr.opt(i);
			
			String channel_name = "";
			if(!jsonObj.optString("SG_SEQS").equals("")) {
				channel_name = dkMgr.getGroupConcat("SITE_GROUP", "SG_NAME", "SG_SEQ", jsonObj.optString("SG_SEQS"));
			}
			
			String site_name = "";
			if(!jsonObj.optString("S_SEQS").equals("")) {
				site_name = dkMgr.getGroupConcat("SITE", "S_NAME", "S_SEQ", jsonObj.optString("S_SEQS"));
			}
%>	
		<tr>         
			<%-- <td><input type="checkbox" name="checkId" value="<%=jsonObj.optString("SKR_SEQ")%>"></td> --%>
			<td style="text-align:left; padding-left:3px;"><%=jsonObj.optString("K_SECTION_TYPE")%></td>
			<td style="text-align:left;"><%=channel_name%></td>
			<td style="text-align:left;"><%=site_name%></td>
			<td style="text-align:left;"><%=jsonObj.optString("SKR_TYPE")%></td>
			<td style="text-align:left;"><%=jsonObj.optString("SKR_KEYWORD")%></td>
			<td><img src="../../../images/admin/keyword/form_del.gif" hspace="5" vspace="5" onclick="del_board(<%=jsonObj.optString("SKR_SEQ")%>,<%=xp %>,<%=yp %>,<%=zp %>);" style="cursor:hand; align:center;"></td>
		</tr>
<%		
		}
	}else{ 
%>
			<tr><td colspan="4" style="text-align:center">NO DATA</td></tr>
<%	
	} 
%>
	
</table>
<!-- </form> -->
