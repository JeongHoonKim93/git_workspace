<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="EUC-KR"%>
<%@ page import="java.util.ArrayList,
 				 risk.admin.aekeyword.ExceptionKeywordBean,
                 risk.admin.aekeyword.ExceptionKeywordMgr,
                 risk.util.ParseRequest" 
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
	ParseRequest pr = new ParseRequest(request);
	ExceptionKeywordMgr ekMgr = new ExceptionKeywordMgr();
	ExceptionKeywordBean eKeywordBean = null;
	pr.printParams();
	
	String ekSeq= pr.getString("ekSeq");	
	String mode = pr.getString("mode");
	

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
	function insertKeyword()
	{
		var f = document.editForm;
		f.mode.value = 'insert';
		if(f.ekValue.value=='')
		{	
			alert('키워드 를 입력해주세요.'); return;
		}else{
			//앞뒤 공백 제거
			f.ekValue.value = f.ekValue.value.replace(/^\s*/,'');
			f.ekValue.value = f.ekValue.value.replace(/\s*$/,'');	
		}
			
		f.target='';
		f.action='aekeyword_prc.jsp';
		f.submit();
	}

	function updateKeyword()
	{
		var f = document.editForm;
		f.mode.value = 'update';
		if(f.ekValue.value=='')
		{	
			alert('키워드 를 입력해주세요.'); return;
		}else{
			//앞뒤 공백 제거
			f.ekValue.value = f.ekValue.value.replace(/^\s*/,'');
			f.ekValue.value = f.ekValue.value.replace(/\s*$/,'');	
		}	
		f.target='';
		f.action='aekeyword_prc.jsp';
		f.submit();
	}
//-->
</script>
</head>
<body>
<form name="editForm" method="post" onsubmit="return false;">
<input type="hidden" name="mode" value="<%=mode%>">
<input type="hidden" name="ekSeq" value="<%=ekSeq%>">

<table style="width:100%;" border="0" cellpadding="0" cellspacing="10">
	<tr>
		<td colspan="3" id="pop_head">
			<p>제외키워드등록</p>
			<span><a href="javascript:close();"><img src="../../../images/search/pop_tit_close.gif"></a></span>
		</td>
	</tr>
</table>
<table style="width:100%;" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td style="padding-left:10px"><table width="370" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="120" align="left" style="padding: 3px 0px 0px 0px;" class="menu_gray"><strong>제외키워드 :</strong></td>
			<td width="311" align="left" style="padding: 3px 0px 0px 0px;"><input type="text" name="ekValue" value=""></td>
		</tr>
		<tr>
			<td width="120" align="left" style="padding: 3px 0px 0px 0px;" class="menu_gray"><strong>검색조건 &nbsp;&ensp;:</strong></td>
			<td colspan="2" width="311" align="left" style="padding: 3px 0px 0px 0px;">
				<select name="ek_op" style="width:200px;" class="t">
					<option value="1">다음 단어를 모두 포함(AND)</option>
					<option value="2">다음 단어들이 인접하여 포함(인접)</option>
					<option value="3">다음 단어를 정확하게 포함(구문)</option>
					<option value="4">단어 앞에 공백 포함 검색(고유)</option>
					<option value="5">단어 앞에 공백,뒤에 공백 또는 한글(고유2)</option>
					<option value="6">다음 문구 앞에 공백 포함 정확하게 검색(구문2)</option>
					<option value="7">다음 문구 앞,뒤에 공백 포함 정확하게 검색(구문3)</option>
                </select>
            </td>   
		</tr>
	</table></td>
	</tr>
</table>
<table width="378" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td height="40" align="right">
		<img src="../../../images/admin/member/btn_save2.gif"  hspace="5" onclick="insertKeyword();" style="cursor:pointer;">
		<img src="../../../images/admin/member/btn_cancel.gif" onclick="window.close();" style="cursor:pointer;"></td>
	</tr>
</table>
</form>
</body>
</html>