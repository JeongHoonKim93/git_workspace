<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="EUC-KR"%>
<%@ page
	import="java.util.ArrayList,
				 risk.admin.aekeyword.StorageBean,
                 risk.admin.aekeyword.StorageMgr,
                 risk.util.ParseRequest"%>
<%@ include file="../../inc/sessioncheck.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	ParseRequest pr = new ParseRequest(request);
StorageMgr sMgr = new StorageMgr();
StorageBean storageBean = null;
pr.printParams();

String sto_seq = pr.getString("sto_seq");
String mode = pr.getString("mode");
String storageName = pr.getString("storageName");

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

	function insertStorage()
	{
		var f = document.editForm;
		f.mode.value = 'insert';
		if(f.storageName.value=='')
		{	
			alert('보관함명을 입력해주세요.'); return;
		}else{
			//앞뒤 공백 제거
			f.storageName.value = f.storageName.value.replace(/^\s*/,'').replace(/\s*$/,'');
		}
			
		f.target='';
		f.action='storage_prc.jsp';
		f.submit();
	}

 	function updateStorage()
	{
		var f = document.editForm2;
		f.mode.value = 'update';		
		if(f.storageName.value=='')
		{	
			alert('보관함명을 입력해주세요.'); return;
		}else{
			//앞뒤 공백 제거
			f.storageName.value = f.storageName.value.replace(/^\s*/,'').replace(/\s*$/,'');
		}	
		f.target='';
		f.action='storage_prc.jsp';
		f.submit();
	}
 
</script>
</head>
<body>
	<%
		if (mode.equals("insert")) {
	%>
	<form name="editForm" method="post" onsubmit="return false;">
		<input type="hidden" name="mode" value="<%=mode%>"> <input
			type="hidden" name="sto_seq" value="<%=sto_seq%>">

		<table style="width: 100%;" border="0" cellpadding="0"
			cellspacing="10">

			<tr>
				<td colspan="3" id="pop_head">
					<p>보관함 등록</p> <span><a href="javascript:close();"><img
							src="../../../images/search/pop_tit_close.gif"></a></span>
				</td>
			</tr>
		</table>
		<table style="width: 100%;" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td style="padding-left: 10px"><table width="370" border="0"
						cellspacing="0" cellpadding="0">
						<tr>
							<td width="120" align="left" style="padding: 3px 0px 0px 0px;"
								class="menu_gray"><strong>보관함명 :</strong></td>
							<td width="311" align="left" style="padding: 3px 0px 0px 0px;"><input
								type="text" name="storageName" value=""></td>
						</tr>
					</table></td>
			</tr>
		</table>
		<table width="378" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="40" align="right"><img
					src="../../../images/admin/member/btn_save2.gif" hspace="5"
					onclick="insertStorage();" style="cursor: pointer;"> <img
					src="../../../images/admin/member/btn_cancel.gif"
					onclick="window.close();" style="cursor: pointer;"></td>
			</tr>
		</table>
	</form>
	<%
		} else if (mode.equals("update")) {
	%>
	<form name="editForm2" method="post" onsubmit="return false;">
		<input type="hidden" name="mode" value="<%=mode%>"> 
		<input type="hidden" name="sto_seq" value="<%=sto_seq%>">

		<table style="width: 100%;" border="0" cellpadding="0"
			cellspacing="10">

			<tr>
				<td colspan="3" id="pop_head"><p>보관함 수정</p> <span><a href="javascript:close();"><img src="../../../images/search/pop_tit_close.gif"></a></span>
				</td>
			</tr>
		</table>
		<table style="width: 100%;" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td style="padding-left: 10px"><table width="370" border="0"
						cellspacing="0" cellpadding="0">
						<tr>
							<td width="120" align="left" style="padding: 3px 0px 0px 0px;"
								class="menu_gray"><strong>보관함명 : </strong></td>
							<td width="311" align="left" style="padding: 3px 0px 0px 0px;"><input
								type="text" name="storageName" value="<%=storageName%>"></td>
						</tr>
					</table></td>
			</tr>
		</table>
		<table width="378" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="40" align="right"><img
					src="../../../images/admin/member/btn_save2.gif" hspace="5"
					onclick="updateStorage();" style="cursor: pointer;"> <img
					src="../../../images/admin/member/btn_cancel.gif"
					onclick="window.close();" style="cursor: pointer;"></td>
			</tr>
		</table>
	</form>
<%
	}
%>
</body>
</html>