<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,
                 risk.util.ParseRequest,
                 java.net.*,
                 risk.admin.ex_site.ExSiteMng
                 "
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
	System.out.println("■-------------behind_ex_site_list.jsp----------------■");
	ParseRequest    pr = new ParseRequest(request);
	pr.printParams();
	
	//AJAX  사용시 한글 처리
	//URLDecoder ud = new URLDecoder();
	
	String searchWord = pr.getString("searchWord");			//검색어
	String order = pr.getString("order", "EK_VALUE ASC");	//정렬 순서
	

 	String str_word = "";
	String str_weight = "";
	String str_writer = "";
	String str_date = "";
	
	String ico_word = "";
	String ico_weight = "";
	String ico_writer = "";
	String ico_date = ""; 
	
	String ex_seq = pr.getString("ex_seq", "");
	
	//DB 쿼리
	ExSiteMng eSMng = new ExSiteMng();
	ArrayList arExSite = eSMng.getExSitetList(ex_seq, searchWord);
	
	
	/*  if( order.equals("EK_VALUE ASC") ) {
		str_word = "EK_VALUE DESC";		
		str_writer = "EK_FWRITER ASC";
		str_date = "EK_DATE DESC";
		ico_word = "▲";
	}else if( order.equals("EK_VALUE DESC") ) {
		str_word = "EK_VALUE ASC";		
		str_writer = "EK_FWRITER ASC";
		str_date = "EK_DATE DESC";
		ico_word = "▼";
	}else if( order.equals("EK_FWRITER ASC") ) {
		str_word = "EK_VALUE ASC";		
		str_writer = "EK_FWRITER DESC";
		str_date = "EK_DATE DESC";
		ico_writer = "▲";
	}else if( order.equals("EK_FWRITER DESC") ) {
		str_word = "EK_VALUE ASC";		
		str_writer = "EK_FWRITER ASC";
		str_date = "EK_DATE DESC";
		ico_writer = "▼";
	}else if( order.equals("EK_DATE DESC") ) {
		str_word = "EK_VALUE ASC";		
		str_writer = "EK_FWRITER ASC";
		str_date = "EK_DATE ASC";
		ico_date = "▼";
	}else if( order.equals("EK_DATE ASC") ) {
		str_word = "EK_VALUE ASC";
		str_weight = "EK_FWRITER DESC";		
		str_date = "EK_DATE DESC";
		ico_date = "▲";
	}  */
	
	/* ExceptionKeywordMgr ekMgr = new ExceptionKeywordMgr();
	ExceptionKeywordBean[] arrEKbean = null;
	arrEKbean = ekMgr.getKeywordList(searchWord, order); */	
	
	
%>
<table id="board_01" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
<col width="5%"><col width="30%"><col width="50%">
	<tr>               
		<th ><input type="checkbox" name="checkAll" onclick="selectAll(this.checked);"></th>
		<th style="text-align: left;">사이트(그룹)</th><%--style="cursor:pointer" onclick="excuteOrder('<%=str_writer%>');" --%>
		<th style="text-align: left;">제외 URL</th>
		<%-- <th onclick="excuteOrder('<%=str_date%>');" style="cursor:pointer">사용유무(Y, N)</th> --%>
	</tr>
<%	
	String tmp[];
	if(arExSite != null && arExSite.size() > 0){
		for(int i = 0; i < arExSite.size(); i++){
			tmp = new String[5];
			tmp = (String[])arExSite.get(i);
			
%> 
	<tr>         
		<td ><input type="checkbox" name="checkId" value="<%=tmp[0]%>"></td>
		<td><p onclick="popupEdit('<%=tmp[0]%>','update');" style="cursor:pointer;text-align: left;"><%=tmp[3]%>(<%=tmp[4]%>)</p></td><!--  제외사이트  -->
		<td><p onclick="popupEdit('<%=tmp[0]%>','update');" style="cursor:pointer;text-align: left;"><%=tmp[2]%></p></td><!--  제외사이트  -->
		<%-- <td><p onclick="popupEdit('<%=tmp[0]%>','update');" style="cursor:pointer;text-align: left;"><% if(tmp[3].length() > 30) tmp[3] = tmp[3].substring(0, 27) +"...";
																										out.print(tmp[3]);%></p></td> --%> <!-- 제외단어 -->
<%-- 		<td><p onclick="popupEdit('<%=tmp[0]%>','update');" style="cursor:pointer;"><%=tmp[6]%></p></td> <!-- 출처 -->
 --%>		
		<!-- TO DO LIST 이게 공백이면 DOT가 안생김 IE 8.0 VERSION-->
		<%-- <td><%=tmp[4]%></td> --%>
	</tr>
<%
	tmp = null;
		}
	}else{
%>
	<tr>
		<td colspan="3" style="height:40px;font-weight:bold" align="center">조건에 맞는 데이터가 없습니다.</td>
	</tr>
<%
	}
%>
</table>