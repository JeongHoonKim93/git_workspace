<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="EUC-KR"%>
<%@ page
	import="java.util.ArrayList,
				 risk.admin.aekeyword.StorageBean,
                 risk.admin.aekeyword.StorageMgr,
                 risk.search.MetaBean,
                 risk.search.MetaMgr,
                 risk.util.ParseRequest"%>
<%@ include file="../../inc/sessioncheck.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="sMgr" class="risk.admin.aekeyword.StorageMgr" scope="session"/>
<%
	ParseRequest pr = new ParseRequest(request);
	StorageMgr smgr = new StorageMgr();
	StorageBean storageBean = null;
	pr.printParams();

	String m_seq = SS_M_NO;
	String sto_seq = pr.getString("sto_seq");
	String mode = pr.getString("mode");
	String storageName = pr.getString("storageName");
	String md_seq = pr.getString("md_seq");
	String md_seqs = pr.getString("SaveList");
	String subMode = pr.getString("subMode");
%>

<html>
<head>
<title>Insert title here</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="../../../css/base.css" type="text/css">

<style>
<!--
.t {
	font-family: "Tahoma";
	font-size: 11px;
	color: #666666
}
-->
</style>
<script language="javascript">

	function insertStorage(mode)
	{
		var f = document.editForm;
		f.mode.value = mode;
/* 		f.mode.value = mode;
		f.storageName.value = id; */
		//alert(f.storageName.value);
		if(f.storageName.value=='')
		{	
			alert('보관함을 선택하세요.'); return;
		}
		
			
	 	f.target='';
		f.action='pop_storage_prc.jsp';
		f.submit(); 
		
	}

 
</script>
</head>
<body>

	<form name="editForm" method="post" onsubmit="return false;">
		<input type="hidden" name="mode" value="<%=mode%>"> 
		<input type="hidden" name="md_seqs" value="<%=md_seqs%>">
		<input type="hidden" name="subMode" value="<%=subMode%>">
		<table style="width: 100%;" border="0" cellpadding="0" cellspacing="10">

			<tr>
				<td colspan="3" id="pop_head">
					<p>보관함 이동</p> <span><a href="javascript:close();"><img
							src="../../../images/search/pop_tit_close.gif"></a></span>
				</td>
			</tr>
		</table>
		<table style="width: 100%;" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td style="padding-left: 10px">
					<table width="370" border="0"
						cellspacing="0" cellpadding="0">
						<tr>
							<td width="400" align="left" style="padding: 7px 0px 10px 0px; font-size:110%;"
								class="menu_gray"><img src="../../../images/search/icon_search_bullet.gif" width="9" height="9" />
								<strong>보관함을 선택하세요.</strong>&nbsp;&nbsp; - 총 <strong><%= (md_seqs.split(",")).length %>건</strong> 을 이동합니다.</td>
						</tr>
				<%-- 		<tr>
							<td width="160" align="left" style="padding: 10px 0px 5px 0px;" class="menu_gray">
							  - 총 <strong><%= (md_seqs.split(",")).length %>건</strong> 을 이동합니다.
							</td>
						</tr> --%>
						<tr>
							
							<td><strong>보관함&nbsp;&nbsp;:&nbsp;&nbsp;</strong>
				          		<select name="storageName" id="storageName" title="보관함 선택">
 									<%
										ArrayList<StorageBean> list = sMgr.getStorageNameList(SS_M_NO);
										int counter = list.size();
										if(counter > 0) {
											for(StorageBean sNameList : list) {
									 %>
												<option value="" selected disabled hidden>선택하세요.</option>
												<option value="<%=sNameList.getStoSeq() %>"><%=sNameList.getStoName() %></option>
									 <%
											 }
										 }
											
									  %> 
									
								 </select>
				          	</td> 
				          </tr>	
					</table>
				</td>
			</tr>
		</table>
		<table width=440" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="40" align="right">
					<img src="../../../images/admin/member/btn_save2.gif" hspace="5" onclick="insertStorage('insert');" style="cursor: pointer;"> 
					<img src="../../../images/admin/member/btn_cancel.gif" onclick="window.close();" style="cursor: pointer;">
				</td>
			</tr>
		</table>
	</form>


</body>
</html>