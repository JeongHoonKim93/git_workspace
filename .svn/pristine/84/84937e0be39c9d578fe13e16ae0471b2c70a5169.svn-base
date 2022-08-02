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
	
	ParseRequest    pr = new ParseRequest(request);
	pr.printParams();
	
	//AJAX  사용시 한글 처리
	URLDecoder ud = new URLDecoder();
	String searchWord = pr.getString("searchWord");	//검색어
	String searchType = "";
	if(searchType != null) searchType = pr.getString("searchType");
	System.out.println(searchType + "<<searchType");
	
	BackupListMgr bm = new BackupListMgr();
	BackupListBean[] blBean2 = null;
	blBean2 = bm.getSearchTableList_insert(searchWord, searchType);	
	BackupListBean[] blBean3 = null;
	blBean3 = bm.getSearchTableList_update(searchWord, searchType);	
%>
<form name="editForm" method="post" onsubmit="return false;">
<%
	if(searchType.equals("insert")) {
%>
	<table id="board_01" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
	<!-- <col width="5%"> --><col width="85%"><!-- <col width="14%"><col width="14%"><col width="14%"><col width="14%"> --><col width="15%"><!-- <col width="15%"> -->
		<tr>               
			<!-- <th><input type="checkbox" name="checkAll" onclick="selectAll(this.checked);"></th> -->
			<th style="cursor:pointer">테이블명</th>
			<th style="cursor:pointer; text-align:right; padding-right: 25px;">관리</th>
		</tr>
<%
		if(blBean2 != null && blBean2.length > 0){
			for(int i = 0; i < blBean2.length; i++){						
%> 
				<tr> 
					<!-- <td><input type="checkbox" name="checkId" value="" ></td> -->
					<td><p class="board_01_tit" style="cursor:pointer"><%=blBean2[i].getBl_tbName().replaceAll("\"", "").replaceAll("'", "")%></p></td>		
				
					<td>
						<img src="../../../images/admin/aekeyword/btn_edit.gif" align="right" style="cursor:pointer; padding:5px 10px 5px 0" onclick="popupEdit('','update','<%=blBean2[i].getBl_tbName() %>','','','','','','','');"/>
						<%-- <img src="../../../images/admin/aekeyword/btn_del.gif" style="cursor:pointer" onclick="deleteBackupList('<%=blBean[i].getBl_seq()%>');"/>&nbsp; --%>
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
<%
	} else if (searchType.equals("update")) {
		
%>
	<table id="board_01" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
	<!-- <col width="5%"> --><col width="85%"><!-- <col width="14%"><col width="14%"><col width="14%"><col width="14%"> --><col width="15%"><!-- <col width="15%"> -->
		<tr>               
			<!-- <th><input type="checkbox" name="checkAll" onclick="selectAll(this.checked);"></th> -->
			<th style="cursor:pointer">테이블명</th>
			<th style="cursor:pointer; text-align:right; padding-right: 25px;">관리</th>
		</tr>
<%
			if(blBean3 != null && blBean3.length > 0){
				for(int i = 0; i < blBean3.length; i++){						
%> 
					<tr> 
						<!-- <td><input type="checkbox" name="checkId" value="" ></td> -->
						<td><p class="board_01_tit" style="cursor:pointer"><strong><%=blBean3[i].getBl_tbName().replaceAll("\"", "").replaceAll("'", "")%></strong></p></td>		
				
						<td>
							<img src="../../../images/admin/aekeyword/btn_edit.gif" align="right" style="cursor:pointer; padding:5px 10px 5px 0" 
								 onclick="popupEdit('<%=blBean3[i].getBl_seq() %>','update','<%=blBean3[i].getBl_tbName() %>','<%=blBean3[i].getBl_op() %>','<%=blBean3[i].getBl_useYn() %>','<%=blBean3[i].getBl_del_useYn() %>','<%=blBean3[i].getBl_day_term() %>','<%=blBean3[i].getIns_field_name() %>','<%=blBean3[i].getDel_date_field_name() %>','<%=blBean3[i].getDel_date_field_type() %>');"/>&nbsp;
							<%-- <img src="../../../images/admin/aekeyword/btn_del.gif" style="cursor:pointer; padding:5px 0 0 0" onclick="deleteBackupList('<%=blBean3[i].getBl_seq()%>','<%=blBean3[i].getBl_tbName()%>');"/>&nbsp; --%>
						</td>
					</tr>
<%				}
			}else{
%>
				<tr>
					<td colspan="6" style="height:40px;font-weight:bold" align="center">조건에 맞는 데이터가 없습니다.</td>
				</tr>
<%
			}
%>	
	</table>
<%		
	} else {
		
%>
	<table id="board_01" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
	<!-- <col width="5%"> --><col width="85%"><!-- <col width="14%"><col width="14%"><col width="14%"><col width="14%"> --><col width="15%"><!-- <col width="15%"> -->
		<tr>               
			<!-- <th><input type="checkbox" name="checkAll" onclick="selectAll(this.checked);"></th> -->
			<th style="cursor:pointer">테이블명</th>
			<th style="cursor:pointer; text-align:right; padding-right: 25px;">관리</th>
		</tr>
<%
		if(blBean3 != null && blBean3.length > 0 && blBean2 != null && blBean2.length > 0){
			for(int i = 0; i < blBean3.length; i++){						
%> 
				<tr> 
					<!-- <td><input type="checkbox" name="checkId" value="" ></td> -->
					<td><p class="board_01_tit" style="cursor:pointer"><strong><%=blBean3[i].getBl_tbName().replaceAll("\"", "").replaceAll("'", "")%></strong></p></td>		
				
					<td>
						<img src="../../../images/admin/aekeyword/btn_edit.gif" align="right" style="cursor:pointer; padding:5px 10px 5px 0" 
							 onclick="popupEdit('<%=blBean3[i].getBl_seq() %>','update','<%=blBean3[i].getBl_tbName() %>','<%=blBean3[i].getBl_op() %>','<%=blBean3[i].getBl_useYn() %>','<%=blBean3[i].getBl_del_useYn() %>','<%=blBean3[i].getBl_day_term() %>','<%=blBean3[i].getIns_field_name() %>','<%=blBean3[i].getDel_date_field_name() %>','<%=blBean3[i].getDel_date_field_type() %>');"/>&nbsp;
						<%-- <img src="../../../images/admin/aekeyword/btn_del.gif" style="cursor:pointer; padding:5px 0 0 0" onclick="deleteBackupList('<%=blBean3[i].getBl_seq()%>','<%=blBean3[i].getBl_tbName()%>');"/>&nbsp; --%>
					</td>
				</tr>
<%			}
			for(int i = 0; i < blBean2.length; i++){						
%> 
				<tr> 
					<!-- <td><input type="checkbox" name="checkId" value="" ></td> -->
					<td><p class="board_01_tit" style="cursor:pointer"><%=blBean2[i].getBl_tbName().replaceAll("\"", "").replaceAll("'", "")%></p></td>		
				
					<td>
						<img src="../../../images/admin/aekeyword/btn_edit.gif" align="right" style="cursor:pointer; padding:5px 10px 5px 0" onclick="popupEdit('','update','<%=blBean2[i].getBl_tbName() %>','','','','','','','');"/>
						<%-- <img src="../../../images/admin/aekeyword/btn_del.gif" style="cursor:pointer" onclick="deleteBackupList('<%=blBean[i].getBl_seq()%>');"/>&nbsp; --%>
					</td>
				</tr>
<%			}
		} else if(blBean2 != null && blBean2.length > 0) {
			for(int i = 0; i < blBean2.length; i++){						
%> 
				<tr> 
					<!-- <td><input type="checkbox" name="checkId" value="" ></td> -->
					<td><p class="board_01_tit" style="cursor:pointer"><%=blBean2[i].getBl_tbName().replaceAll("\"", "").replaceAll("'", "")%></p></td>		
				
					<td>
						<img src="../../../images/admin/aekeyword/btn_edit.gif" align="right" style="cursor:pointer; padding:5px 10px 5px 0" onclick="popupEdit('','update','<%=blBean2[i].getBl_tbName() %>','','','','','','','');"/>
						<%-- <img src="../../../images/admin/aekeyword/btn_del.gif" style="cursor:pointer" onclick="deleteBackupList('<%=blBean[i].getBl_seq()%>');"/>&nbsp; --%>
					</td>
				</tr>
<%			}
		} else if(blBean3 != null && blBean3.length > 0) {
			for(int i = 0; i < blBean3.length; i++){						
%> 
				<tr> 
					<!-- <td><input type="checkbox" name="checkId" value="" ></td> -->
					<td><p class="board_01_tit"style="cursor:pointer"><strong><%=blBean3[i].getBl_tbName().replaceAll("\"", "").replaceAll("'", "")%></strong></p></td>		
				
					<td>
						<img src="../../../images/admin/aekeyword/btn_edit.gif" align="right" style="cursor:pointer; padding:5px 10px 5px 0" 
							 onclick="popupEdit('<%=blBean3[i].getBl_seq() %>','update','<%=blBean3[i].getBl_tbName() %>','<%=blBean3[i].getBl_op() %>','<%=blBean3[i].getBl_useYn() %>','<%=blBean3[i].getBl_del_useYn() %>','<%=blBean3[i].getBl_day_term() %>','<%=blBean3[i].getIns_field_name() %>','<%=blBean3[i].getDel_date_field_name() %>','<%=blBean3[i].getDel_date_field_type() %>');"/>&nbsp;
						<%-- <img src="../../../images/admin/aekeyword/btn_del.gif" style="cursor:pointer; padding:5px 0 0 0" onclick="deleteBackupList('<%=blBean3[i].getBl_seq()%>','<%=blBean3[i].getBl_tbName()%>');"/>&nbsp; --%>
					</td>
				</tr>
<%			}
		} else {
%>
		<tr>
			<td colspan="6" style="height:40px;font-weight:bold" align="center">조건에 맞는 데이터가 없습니다.</td>
		</tr>
<%
		}
%>
	</table>
<%
	}
%>
</form>