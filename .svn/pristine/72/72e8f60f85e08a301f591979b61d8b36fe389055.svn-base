<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,
				 risk.admin.portal_keyword.PortalKeywordBean,
                 risk.admin.portal_keyword.PortalKeywordMgr,
                 risk.util.ParseRequest,
                 java.net.*" 
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
	
	ParseRequest    pr = new ParseRequest(request);
	pr.printParams();
	
	//AJAX  사용시 한글 처리
	URLDecoder ud = new URLDecoder();
	String searchWord = pr.getString("searchWord");			//검색어
	String order = pr.getString("order", "PK_VALUE ASC");	//정렬 순서
	
	String str_word = "";
	String str_weight = "";
	String str_writer = "";
	String str_date = "";
	
	String ico_word = "";
	String ico_weight = "";
	String ico_writer = "";
	String ico_date = "";
	
	if( order.equals("PK_VALUE ASC") ) {
		str_word = "PK_VALUE DESC";		
		str_writer = "PK_WRITER ASC";
		str_date = "PK_REGDATE DESC";
		ico_word = "▲";
	}else if( order.equals("PK_VALUE DESC") ) {
		str_word = "PK_VALUE ASC";		
		str_writer = "PK_WRITER ASC";
		str_date = "PK_REGDATE DESC";
		ico_word = "▼";
	}else if( order.equals("PK_WRITER ASC") ) {
		str_word = "PK_VALUE ASC";		
		str_writer = "PK_WRITER DESC";
		str_date = "PK_REGDATE DESC";
		ico_writer = "▲";
	}else if( order.equals("PK_WRITER DESC") ) {
		str_word = "PK_VALUE ASC";		
		str_writer = "PK_WRITER ASC";
		str_date = "PK_REGDATE DESC";
		ico_writer = "▼";
	}else if( order.equals("PK_REGDATE DESC") ) {
		str_word = "PK_VALUE ASC";		
		str_writer = "PK_WRITER ASC";
		str_date = "PK_REGDATE ASC";
		ico_date = "▼";
	}else if( order.equals("PK_REGDATE ASC") ) {
		str_word = "PK_VALUE ASC";
		str_weight = "PK_WRITER DESC";		
		str_date = "PK_REGDATE DESC";
		ico_date = "▲";
	}
	
	PortalKeywordMgr pkMgr = new PortalKeywordMgr();
	PortalKeywordBean[] arrPKbean = null;
	arrPKbean = pkMgr.getKeywordList(searchWord, order);	
%>
<table id="board_01" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
<col width="5%"><col width="35%"><col width="15%"><col width="15%"><col width="15%"><col width="15%">
	<tr>               
		<th><input type="checkbox" name="checkAll" onclick="selectAll(this.checked);"></th>
		<th onclick="excuteOrder('<%=str_word%>');" style="cursor:pointer">키워드</th>
		<th onclick="excuteOrder('<%=str_writer%>');" style="cursor:pointer">등록자</th>
		<th onclick="excuteOrder('<%=str_date%>');" style="cursor:pointer">등록일</th>
		<th>수정자</th>
		<th>수정일</th>
	</tr>
<%
	if(arrPKbean != null && arrPKbean.length > 0){
		for(int i = 0; i < arrPKbean.length; i++){
%> 
	<tr>         
		<td><input type="checkbox" name="checkId" value="<%=arrPKbean[i].getPkSeq()%>"></td>
		<td><p class="board_01_tit" onclick="popupEdit('<%=arrPKbean[i].getPkSeq()%>','update');" style="cursor:pointer"><%=arrPKbean[i].getPkValue()%></p></td>
		<td><%=arrPKbean[i].getPkWriter()%></td>
		<td><%=arrPKbean[i].getPkDate()%></td>
		<td><%=arrPKbean[i].getPkUpdater()%></td>
		<td><%=arrPKbean[i].getPkUpdate()%></td>
	</tr>
<%
		}
	}else{
%>
	<tr>
		<td colspan="6" style="height:40px;font-weight:bold" align="center">조건에 맞는 데이터가 없습니다.</td>
	</tr>
<%
	}
%>
</table>