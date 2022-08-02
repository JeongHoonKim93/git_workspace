<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,
				 risk.admin.keyword.KeywordBean,
                 risk.admin.keyword.KeywordMng,
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
	String writerList = pr.getString("writerList");
	String GroupKind = pr.getString("sGroupKind","1");		//검색 단어
 	String MapxpList = pr.getString("MapxpList");			//키워드 대그룹명
	 
	KeywordMng kmgr = new KeywordMng();
	KeywordBean[] arrkBean = null;
	arrkBean = kmgr.getMapList(searchWord, MapxpList, GroupKind);	
%>
<table id="board_01" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
<col width="5%"><col width="15%"><col width="35%"><col width="45%">
	<tr>               
		<th><input type="checkbox" name="checkAll" onclick="selectAll(this.checked);"></th>
		<th style="cursor:pointer">키워드 그룹</th>
		<th style="cursor:pointer">회사</th>
		<th style="cursor:pointer">제품군</th>
	</tr>
<%
	if(arrkBean != null && arrkBean.length > 0){
		for(int i = 0; i < arrkBean.length; i++){
%> 
	<tr>         
		<td><input type="checkbox" name="checkId" value="<%=arrkBean[i].getK_seq()%>"></td>
 		<td><p class="board_01_tit" onclick="popupEdit('<%=arrkBean[i].getK_seq()%>','update');" style="cursor:pointer"><%=arrkBean[i].getK_value()%></p></td>
		<td><%=arrkBean[i].getCom_name()%></td>
		<td><%=arrkBean[i].getProduct_name()%></td>
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