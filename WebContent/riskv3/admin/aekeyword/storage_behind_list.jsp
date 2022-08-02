<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,
				 risk.admin.aekeyword.StorageBean,
                 risk.admin.aekeyword.StorageMgr,
                 risk.util.ParseRequest,
                 java.net.*" 
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
	
	ParseRequest    pr = new ParseRequest(request);
	pr.printParams();
	
	//AJAX  사용시 한글 처리
	URLDecoder ud = new URLDecoder();
	String searchWord = pr.getString("searchWord");
	String writerList = pr.getString("writerList");
	String order = pr.getString("order", "STO_SEQ ASC");	//정렬 순서
	String m_seq = SS_M_NO;
	
	String str_word = "";
	String str_date = "";
	String str_name = "";
	String str_writer = "";
	
	String ico_word = "";
	String ico_weight = "";
	String ico_writer = "";
	String ico_date = "";
	
	if( order.equals("STO_NAME ASC") ) {
		str_word = "STO_NAME DESC";		
		str_date = "STO_DATE ASC";
		str_name = "M_NAME DESC";
		ico_word = "▲";
	}else if( order.equals("STO_NAME DESC") ) {
		str_word = "STO_NAME ASC";		
		str_date = "STO_DATE ASC";
		str_name = "M_NAME DESC";
		ico_word = "▼";
	}else if( order.equals("STO_DATE ASC") ) {
		str_word = "STO_NAME ASC";		
		str_date = "STO_DATE DESC";
		str_name = "M_NAME DESC";
		ico_writer = "▲";
	}else if( order.equals("STO_DATE DESC") ) {
		str_word = "STO_NAME ASC";		
		str_date = "STO_DATE ASC";
		str_name = "M_NAME DESC";
		ico_writer = "▼";
	} else if( order.equals("M_SEQ DESC") ) {
		str_word = "STO_NAME ASC";		
		str_date = "STO_DATE ASC";
		str_name = "M_NAME ASC";
		ico_date = "▼";
	}else if( order.equals("M_SEQ ASC") ) {
		str_word = "STO_NAME ASC";
		str_date = "STO_DATE DESC";		
		str_name = "M_NAME DESC";
		ico_date = "▲";
	}
	
	StorageMgr sMgr = new StorageMgr();
	StorageBean[] storagebean = null;
	storagebean = sMgr.getStorageList(searchWord, writerList, order, m_seq);
%>
<form name="editForm" method="post" onsubmit="return false;">
	<input type="hidden" name="m_seq" value="<%=m_seq%>"> 
<table id="board_01" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
<col width="5%"><col width="55%"><col width="30%"><col width="30%">
	<tr>               
		<th><input type="checkbox" name="checkAll" onclick="selectAll(this.checked);"></th>
		<th onclick="excuteOrder('<%=str_word%>');" style="cursor:pointer">보관함명</th>
		<th onclick="excuteOrder('<%=str_name%>');" style="cursor:pointer">작성자</th>
		<th onclick="excuteOrder('<%=str_date%>');" style="cursor:pointer">생성일</th>
	</tr>
<%
	if(storagebean != null && storagebean.length > 0){
		for(int i = 0; i < storagebean.length; i++){
%> 
	<tr>         
		<td><input type="checkbox" name="checkId" value="<%=storagebean[i].getStoSeq()%>"></td>
		<td><p class="board_01_tit" onclick="popupEdit('<%=storagebean[i].getStoSeq()%>','update','<%=storagebean[i].getStoName()%>');" style="cursor:pointer"><%=storagebean[i].getStoName().replaceAll("\"", "").replaceAll("'", "")%></p></td>
		<td><%=storagebean[i].getmName()%></td>
		<td><%=storagebean[i].getStoDate()%></td>
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
</form>