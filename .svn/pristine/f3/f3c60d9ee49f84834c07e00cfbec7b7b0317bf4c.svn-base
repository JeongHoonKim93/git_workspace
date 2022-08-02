<%@page import="java.text.DecimalFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,
                 risk.util.ParseRequest,
                 java.net.*,
                 risk.admin.backup.BackupListBean,
                 risk.admin.backup.BackupListMgr" 
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
	
	DecimalFormat formatter = new DecimalFormat("###,###");
	ParseRequest    pr = new ParseRequest(request);
	pr.printParams();
	
	//AJAX  사용시 한글 처리
	URLDecoder ud = new URLDecoder();
	String searchWord = pr.getString("searchWord");			//검색어
	
	String order = pr.getString("order", "ISNULL(BL_TBNAME) ASC, BL_TBNAME ASC");	//정렬 순서
 	
	String str_name = "";
	String str_useYn = "";
	String str_day_term = "";
	String str_bl_op = "";
	String str_rows = "";
	String str_update_date = "";
	
	String ico_name = "";
	String ico_op = "";
	String ico_del_useYn = ""; 
	
	str_name = "BL_TBNAME ASC";		
	str_useYn = "BL_USEYN DESC";		
	str_bl_op = "BL_OP DESC";		
	str_day_term = "BL_DAY_TERM DESC";		
	str_rows = "TABLE_ROWS DESC";
	str_update_date = "UPDATE_TIME DESC";
	
 	if( order.equals("BL_TBNAME ASC") ) {
 		str_name = "BL_TBNAME DESC";
 		
	}else if( order.equals("BL_USEYN DESC") ) {
		str_useYn = "BL_USEYN ASC";	
		
	}else if( order.equals("BL_OP DESC") ) {
		str_bl_op = "BL_OP ASC";	
		
	}else if( order.equals("BL_DAY_TERM DESC") ) {
		str_day_term = "BL_DAY_TERM ASC";		
		
	}else if( order.equals("TABLE_ROWS DESC") ) {
		str_rows = "TABLE_ROWS ASC";		
	
	}else if( order.equals("UPDATE_TIME DESC") ) {
		str_update_date = "UPDATE_TIME ASC";
	
	} 
	
	BackupListMgr bm = new BackupListMgr();
	BackupListBean[] blBean = null;
	blBean = bm.getAllTableList(order);	
%>

<form name="editForm" method="post" onsubmit="return false;">
	<table id="board_01" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
	<col width="5%"><col width="34%"><col width="10%"><col width="10%"><col width="10%"><col width="14%"><col width="14%"><col width="8%"><!-- <col width="15%"> -->
		<tr>               
			<th><input type="checkbox" name="checkAll" onclick="selectAll(this.checked);"></th>
			<th onclick="excuteOrder('<%=str_name%>');" style="cursor:pointer">테이블명</th>
			<th onclick="excuteOrder('<%=str_useYn%>');" style="cursor:pointer">백업<br> 사용여부</th>
			<th onclick="excuteOrder('<%=str_bl_op%>');">옵션</th>
			<th onclick="excuteOrder('<%=str_day_term%>');" style="cursor:pointer">데이터<br> 보관기간</th> 
			<th onclick="excuteOrder('<%=str_update_date%>');">업데이트 일자</th>
			<th onclick="excuteOrder('<%=str_rows%>');" style="text-align:right; padding-right:5px;">용량(ROW)</th>
			<th></th>
		</tr>
<%
		if(blBean != null && blBean.length > 0){
			for(int i = 0; i < blBean.length; i++){	

				String bl_seq = "";
				if(blBean[i].getBl_seq()!= null) {
					bl_seq = blBean[i].getBl_seq() + "/" + blBean[i].getBl_tbName(); 
				} 
				String tmp_seq = "";
				if(blBean[i].getBl_seq().equals("")) {
					tmp_seq +=(i+1) + "/" + blBean[i].getBl_tbName(); 
				}
				
				String bl_comment = "";
				if(!blBean[i].getBl_comment().equals("")) {
					bl_comment = blBean[i].getBl_comment();
				}
				
				String bl_useYn = "";
				if(blBean[i].getBl_useYn().equals("Y")) {
					bl_useYn = "동작";
				} else if(blBean[i].getBl_useYn().equals("N")) {
					bl_useYn = "중지";
				} else {
					bl_useYn = "중지";
				}
				
				String bl_op = "X";
				if(blBean[i].getBl_op().equals("1")) {
					bl_op = "전체";
				} else if(blBean[i].getBl_op().equals("2")) {
					bl_op = "증감";
				}
				
				String bl_del_useYn = "X";
				if(blBean[i].getBl_del_useYn().equals("Y")) {
					bl_del_useYn = "O";
				} else if(blBean[i].getBl_del_useYn().equals("N")) {
					bl_del_useYn = "X";
				}
				
 				String update_date = "";
				if(!blBean[i].getUpdate_time().equals("")) {
					update_date = blBean[i].getUpdate_time().substring(0,10);
				}  
				
				String rows = "";
				if(!blBean[i].getTable_rows().equals("")) {
					rows = formatter.format(Integer.parseInt(blBean[i].getTable_rows()));
				} 
				
 				String color = "#090909";
				if(!blBean[i].getTable_rows().equals("") && Integer.parseInt(blBean[i].getTable_rows())>= 10000) {
					color = "#F80303";
				} 
				
%> 
		<tr>    
			<%
				if(!blBean[i].getBl_seq().equals("")) {
			%>
					<td><input type="checkbox" name="checkId" id="checkId" value="<%=bl_seq%>"></td>
			<%		
				} else {
			%>
					<td><input type="checkbox" name="checkId" id="checkId" value="<%=tmp_seq%>"></td>
			<%
				}
			%>     
			<%-- <td><input type="checkbox" name="checkId" id="checkId" value="<%=bl_seq%>"></td> --%>
			<%-- <td><p class="board_01_tit" onclick="popupEdit('<%=blBean.getBl_seq()%>','update');" style="cursor:pointer"><%=blBean.getBl_tbName()%></p></td> --%>
			<%
				if(!blBean[i].getBl_seq().equals("")) {
			%>
					<td>
						<p class="board_01_tit" style="cursor:pointer; color:<%=color %>;" onmouseover="show_me(<%=i %>);" onmouseout="close_me(<%=i %>)"><strong><%=blBean[i].getBl_tbName().replaceAll("\"", "").replaceAll("'", "")%></strong></p>
						<div id=div<%=i%> style="display:none;width:200px;height:80px;">
							<strong>*TABLE INFORMATION*</strong> <br/>
							<strong>COMMENT</strong>&nbsp;&nbsp;<%=bl_comment %> <br/>
							<strong>ENGINE</strong>&nbsp;&nbsp;<%=blBean[i].getEngine() %> <br/>
							<strong>TABLE ROWS</strong>&nbsp;&nbsp;<%=blBean[i].getTable_rows() %> <br/>
							<strong>CREATE TIME</strong>&nbsp;&nbsp;<%=blBean[i].getCreate_time() %> <br/>
							<strong>UPDATE TIME</strong>&nbsp;&nbsp;<%=blBean[i].getUpdate_time() %>
						</div>
					</td>
			<%
				} else {
			%>
					<td>
						<p class="board_01_tit" style="cursor:pointer; color:<%=color %>;" onmouseover="show_me(<%=i %>);" onmouseout="close_me(<%=i %>)"><%=blBean[i].getBl_tbName().replaceAll("\"", "").replaceAll("'", "")%></p>
						<div id=div<%=i%> style="display:none;width:200px;height:80px;">
							<strong>*TABLE INFORMATION*</strong> <br/>
							<strong>COMMENT</strong>&nbsp;&nbsp;<%=bl_comment %> <br/>
							<strong>ENGINE</strong>&nbsp;&nbsp;<%=blBean[i].getEngine() %> <br/>
							<strong>TABLE ROWS</strong>&nbsp;&nbsp;<%=blBean[i].getTable_rows() %> <br/>
							<strong>CREATE TIME</strong>&nbsp;&nbsp;<%=blBean[i].getCreate_time() %> <br/>
							<strong>UPDATE TIME</strong>&nbsp;&nbsp;<%=blBean[i].getUpdate_time() %>
						</div>
					</td>
			<%
				}
			%>
			<%
				if(blBean[i].getBl_useYn().equals("Y")) {
			%>
					<td><strong><%=bl_useYn%></strong></td>
			<%		
				} else {
			%>		
					<td><%=bl_useYn%></td>
			<%
				}
			%>
			<td><%=bl_op%></td>
			<%
				String day_term = "-";
				if(blBean[i].getBl_del_useYn().equals("N")) {
			%>
					<td><%=day_term%></td>
			<% 
				} else {
			%>
					<td><%=blBean[i].getBl_day_term()%></td>
			<%
				}
			%>
			<%-- <td><%=blBean[i].getBl_day_term()%></td> --%>
			
			<%-- <td><%=blBean[i].getUpdate_time()%></td> --%>
			<td><%=update_date %></td>
			<td style="text-align:right; padding-right:5px;"><%=rows%></td>
			
			<%-- <td><%=bl_useYn%></td> --%>
			<%-- <%
				String checked = "";
				if(blBean[i].getBl_useYn().equals("Y")) {
					checked = "checked";
				}
			%>
			<td><input type="checkbox" name="bl_useYn" value="<%=blBean[i].getBl_useYn()%>" <%=checked %> onclick="popupEdit('<%=blBean[i].getBl_seq()%>','update','<%=blBean[i].getBl_tbName()%>','<%=blBean[i].getBl_op()%>','<%=blBean[i].getBl_useYn()%>','<%=blBean[i].getBl_del_useYn()%>','<%=blBean[i].getBl_day_term()%>','<%=blBean[i].getIns_field_name()%>','<%=blBean[i].getDel_date_field_name()%>');" style="cursor:pointer"></td> 
			--%>
			<td>
				<img src="../../../images/admin/aekeyword/btn_edit.gif" style="cursor:pointer; padding-top:5px" onclick="popupEdit('<%=blBean[i].getBl_seq()%>','update','<%=blBean[i].getBl_tbName()%>','<%=blBean[i].getBl_op()%>','<%=blBean[i].getBl_useYn()%>','<%=blBean[i].getBl_del_useYn()%>','<%=blBean[i].getBl_day_term()%>','<%=blBean[i].getIns_field_name()%>','<%=blBean[i].getDel_date_field_name()%>','<%=blBean[i].getDel_date_field_type()%>');"/>&nbsp;
			</td>
		</tr>
<%			}
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