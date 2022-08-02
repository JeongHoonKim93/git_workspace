<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="risk.util.*,
				 java.util.List,
				 risk.admin.keyword.KeywordMng
                 "
%>
<%@ include file="../inc/sessioncheck.jsp" %>
<%
    ParseRequest    pr = new ParseRequest(request);
	String ir_title = pr.getString("ir_title");
	String ir_seq = pr.getString("ir_seq");
	String ir_type = pr.getString("ir_type");
    
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!--<link rel="stylesheet" href="css/basic.css" type="text/css">-->
<link rel="stylesheet" href="../../css/base.css" type="text/css">


<script language="JavaScript" type="text/JavaScript">
<!--
	function update_name(ir_seq){
		var f = document.fSend;
		f.ir_seq.value = ir_seq;
		f.target = '';
		f.action = 'pop_reportName_update_prc.jsp';
		f.submit();
	}
//-->
</script>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<form name="fSend" action="" method="post">
<input type="hidden" name="ir_seq" value="">
<input type="hidden" name="ir_type" value="<%=ir_type%>">
<input type="hidden" name="past_ir_title" value="<%=ir_title%>">


<table style="width:100%;" border="0" cellpadding="0" cellspacing="10">
	<tr>
		<td colspan="3" id="pop_head">
			<p>제목수정</p>
			<span><a href="javascript:close();"><img src="../../../images/search/pop_tit_close.gif"></a></span>
		</td>
	</tr>
</table>
<table style="width:100%;" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td style="padding-left:10px"><table width="370" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="150" align="right" style="padding: 3px 5px 0px 0px;" class="menu_gray"><strong>수정전 제목명 :</strong></td>
			<td width="281" align="left" style="padding: 3px 0px 0px 0px;"><%=ir_title%></td>
		</tr>
		<tr>
			<td  align="right" style="padding: 3px 5px 0px 0px;" class="menu_gray"><strong>제목명 :</strong></td>
			<td  align="left" style="padding: 3px 0px 0px 0px;"><input style="width: 200px" type="text" autocomplete="off" name="ir_title" value="" OnKeyDown="Javascript:if (event.keyCode == 13) { update_name('<%=ir_seq%>');}"></td>
		</tr>
	</table></td>
	</tr>
</table>
<table width="378" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td height="40" align="right">

		<img src="../../../images/admin/member/btn_save2.gif"  hspace="5" onclick="update_name('<%=ir_seq%>');" style="cursor:hand;">

		<img src="../../../images/admin/member/btn_cancel.gif" onclick="window.close();" style="cursor:hand;"></td>
	</tr>
</table>



</form>
</body>
</html>
