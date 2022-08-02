<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="EUC-KR"%>
<%@ page import="java.util.ArrayList,
                 risk.util.ParseRequest" 
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	
	String itype = pr.getString("itype");
	String icode = pr.getString("icode");
	
	String ic_seq = pr.getString("ic_seq");	
	String ic_name = pr.getString("ic_name");
%>

<html>
<head>
<title>Insert title here</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="../../../css/base.css" type="text/css">
<style>
<!--	
	.t {  font-family: "Tahoma"; font-size: 11px; color: #666666}

-->
	</style>
<script language="javascript">
<!--
	function modify()
	{
		var f = document.editForm;
		
		if(f.ic_name.value=='')
		{	
			alert('명칭 를 입력해주세요.'); return;
		}else{
			//앞뒤 공백 제거
			f.ic_name.value = f.ic_name.value.replace(/^\s*/,'');
			f.ic_name.value = f.ic_name.value.replace(/\s*$/,'');	
		}
			
		f.target='';
		f.action='classification_prc.jsp';
		f.submit();
	}

//-->
</script>
</head>
<body>
<form name="editForm" method="post" onsubmit="return false;">
<input type="hidden" name="itype" value="<%=itype%>">
<input type="hidden" name="icode" value="<%=icode%>">
<input type="hidden" name="ic_seq" value="<%=ic_seq%>">
<input type="hidden" name="mode" value="update">

<table style="width:100%;" border="0" cellpadding="0" cellspacing="10">
	<tr>
		<td colspan="3" id="pop_head">
			<p>분류항목 수정</p>
			<span><a href="javascript:close();"><img src="../../../images/search/pop_tit_close.gif"></a></span>
		</td>
	</tr>
</table>
<table style="width:100%;" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td style="padding-left:10px"><table width="370" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="120" align="left" style="padding: 3px 0px 0px 0px;" class="menu_gray"><strong>분류명 :</strong></td>
			<td width="311" align="left" style="padding: 3px 0px 0px 0px;"><input type="text" name="ic_name" value="<%=ic_name%>"></td>
		</tr>
	</table></td>
	</tr>
</table>
<table width="378" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td height="40" align="right">
		<img src="../../../images/admin/member/btn_save2.gif"  hspace="5" onclick="modify();" style="cursor:pointer;">
		<img src="../../../images/admin/member/btn_cancel.gif" onclick="window.close();" style="cursor:pointer;"></td>
	</tr>
</table>
</form>
</body>
</html>