<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import= "risk.util.ParseRequest" %>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	
	String mode = pr.getString("mode");
	
	String title = "";
	
	if( mode.equals( "add" ) ){
		title = "매체순위 추가";
	}

%>
<html>
<head>
<title>RISK V3 - RSN</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="../../../css/base.css" type="text/css">
<script type="text/JavaScript">
<!--
	function init()
	{
		tier_pop_form.tier_name.focus();	
	}
	
	function add_tierRank()
	{
		regNumber = /^[0-9]*$/;
		
		if( !tier_pop_form.tier_name.value ) {
			alert('매체순위명을 입력하십시요.');
			
		} else if(!regNumber.test(tier_pop_form.tier_name.value)) {
			alert('숫자만 입력해주세요.');
			
		} else {
			tier_pop_form.mode.value = 'ins';
			tier_pop_form.submit();
			window.close();
		}
	}
//-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="init();">
<form name="tier_pop_form" action="tier_prc.jsp" method="post" target="tg_sitemng">
<input type="hidden" name="mode">

<table style="width:100%;" border="0" cellpadding="0" cellspacing="10">
	<tr>
		<td colspan="3" id="pop_head">
			<p><%=title %></p>
			<span><a href="javascript:close();"><img src="../../../images/search/pop_tit_close.gif"></a></span>
		</td>
	</tr>
</table>
<table style="width:100%;" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td style="padding-left:10px"><table width="370" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="120" align="left" style="padding: 3px 0px 0px 0px;" class="menu_gray"><strong>매체순위 명 :</strong></td>
			<td width="311" align="left" style="padding: 3px 0px 0px 0px;"><input type="text" name="tier_name" value="" OnKeyDown="Javascript:if (event.keyCode == 13) { add_tierRank();}"></td>
		</tr>
	</table></td>
	</tr>
</table>
<table width="378" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td height="40" align="right">
			<img src="../../../images/admin/member/btn_save2.gif"  hspace="5" onclick="add_tierRank();" style="cursor:pointer;">
		</td>
	</tr>
</table>
</form>
</body>