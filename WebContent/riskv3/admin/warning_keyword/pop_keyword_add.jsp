<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="risk.util.*,
				 java.util.List,
				 risk.admin.warning_keyword.KeywordMng,
				 risk.admin.warning_keyword.KeywordBean
                 "
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
    ParseRequest    pr = new ParseRequest(request);
	pr.printParams();
    String xp          = pr.getString("xp");
    String yp          = pr.getString("yp");
    String zp          = pr.getString("zp");

	String value = "";
	String part = "total";
	String partname = "대분류";

	String xpname = "";
	String ypname = "";
	String zpname = "";

	KeywordMng keymng = KeywordMng.getInstance();

	if( xp.equals("0") || xp.equals("") ) {
	} else if( yp.equals("0") || yp.equals("") ) {
		part = "upkg";
		partname = "소분류";
		
		String[] arValue =  keymng.getKG(xp); 
		xpname = arValue[0];
		
		
	} else if( zp.equals("0") || zp.equals("") ) {
		part = "downkg";
		partname = "키워드";
		String[] arValue =  keymng.getKG(xp); 
		xpname = arValue[0];
		ypname = keymng.getKG(xp,yp);
	} else {
		part = "kg";
		partname = "제외키워드";
		String[] arValue =  keymng.getKG(xp); 
		xpname = arValue[0];
		ypname = keymng.getKG(xp,yp);
		zpname = keymng.getKG(xp,yp,zp)[0];
	}

%>

<%@page import="java.util.ArrayList"%><html>
<head>
<title><%=SS_TITLE%></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="../../../css/base.css" type="text/css">
<script language="JavaScript" type="text/JavaScript">
<!--
	function init(){
		keygroup.val.focus();	
	}
	window.onload = init;
	
	function add_kg() {
		if( !keygroup.val.value )
		{
			alert('<%=partname%>명을 입력하십시요.');
		} else {
			keygroup.mode.value = 'ins';
			keygroup.submit();
			window.close();
		}
	}
//-->
</script>
</head>
<body>
<form name="keygroup" action="admin_keyword_prc.jsp" method="post" target="keyword_right">
<input type="hidden" name="tg" value="<%=part%>">
<input type="hidden" name="mode">
<input type="hidden" name="xp" value="<%=xp%>">
<input type="hidden" name="yp" value="<%=yp%>">
<input type="hidden" name="zp" value="<%=zp%>">
<table style="width:100%;" border="0" cellpadding="0" cellspacing="10">
	<tr>
		<td colspan="3" id="pop_head">
			<p>키워드등록</p>
			<span><a href="javascript:close();"><img src="../../../images/search/pop_tit_close.gif"></a></span>
		</td>
	</tr>
</table>
<table style="width:100%;" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td style="padding-left:10px"><table width="370" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td colspan="2" align="left" style="padding: 3px 0px 0px 0px;" class="menu_gray"><strong><%=partname%> 추가</strong></td>
		</tr>
		<tr>
			<td width="60" align="left" style="padding: 3px 0px 0px 0px;" class="menu_gray"><strong>대분류 :</strong></td>
			<td width="311" align="left" style="padding: 3px 0px 0px 0px;"><%=xpname%></td>
		</tr>
		<tr>
			<td width="60" align="left" style="padding: 3px 0px 0px 0px;" class="menu_gray"><strong>소분류 :</strong></td>
			<td width="311" align="left" style="padding: 3px 0px 0px 0px;"><%=ypname%></td>
		</tr>
		<tr>
			<td width="60" align="left" style="padding: 3px 0px 0px 0px;" class="menu_gray"><strong>키워드 :</strong></td>
			<td width="311" align="left" style="padding: 3px 0px 0px 0px;"><%=zpname%></td>
		</tr>
		<tr>
			<td colspan="2" width="311" align="left" style="padding: 3px 0px 0px 0px;">
<%
	if( part.equals("downkg") ) {
%>
				<select name="k_op" style="width:250px;" class="t">
					<option value="1">다음 단어를 모두 포함(AND)</option>
					<option value="2">다음 단어들이 인접하여 포함(인접)</option>
					<option value="3">다음 단어를 정확하게 포함(구문)</option>
					<option value="4">단어 앞에 공백 포함 검색(고유)</option>
					<option value="5">단어 앞에 공백,뒤에 공백 또는 한글(고유2)</option>
                </select>
<%
	}
%>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="left" style="padding: 3px 0px 0px 0px;"><textarea name="val" rows="6" cols="28"></textarea></td>
		</tr>
	</table></td>
	</tr>
</table>
<table width="378" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td height="40" align="right">
		<img src="../../../images/admin/member/btn_save2.gif"  hspace="5" onclick="add_kg();" style="cursor:pointer;">
		<img src="../../../images/admin/member/btn_cancel.gif" onclick="window.close();" style="cursor:pointer;"></td>
	</tr>
</table>
</form>
</body>
</html>
