<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="EUC-KR"%>
<%@ page import="java.util.ArrayList,
 				 risk.admin.portal_keyword.PortalKeywordBean,
                 risk.admin.portal_keyword.PortalKeywordMgr,
                 risk.util.ParseRequest" 
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
	ParseRequest pr = new ParseRequest(request);
	PortalKeywordMgr pkMgr = new PortalKeywordMgr();
	pr.printParams();
	
	String pkSeq= pr.getString("pkSeq");	
	String mode = pr.getString("mode");
	
	String[] prev_keyword = pkMgr.getPortalKeyword(pkSeq);
	
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
	function updateKeyword()
	{
		var f = document.editForm;
		f.mode.value = 'update';
		if(f.pkValue.value=='')
		{	
			alert('키워드 를 입력해주세요.'); return;
		}else{
			//앞뒤 공백 제거
			f.pkValue.value = f.pkValue.value.replace(/^\s*/,'');
			f.pkValue.value = f.pkValue.value.replace(/\s*$/,'');	
		}	
		f.target='';
		f.action='portal_keyword_prc.jsp';
		f.submit();
	}
//-->
</script>
</head>
<body>
<form name="editForm" method="post" onsubmit="return false;">
<input type="hidden" name="mode" value="<%=mode%>">
<input type="hidden" name="pkSeq" value="<%=pkSeq%>">

<table style="width:100%;" border="0" cellpadding="0" cellspacing="10">
	<tr>
		<td colspan="3" id="pop_head">
			<p>포탈키워드수정</p>
			<span><a href="javascript:close();"><img src="../../../images/search/pop_tit_close.gif"></a></span>
		</td>
	</tr>
</table>
<table style="width:100%;" border="0" cellspacing="0" cellpadding="0">
	<tr>
		  <td style="padding-left:10px;padding-bottom:10px;"><table width="385" border="0" cellpadding="0" cellspacing="1" bgcolor="#E0E0E0">
	       <tr>
	         <td bgcolor="#F6F6F6" style="padding:10px 10px 10px 10px"><table width="100%" border="0" cellspacing="0" cellpadding="0">
	           <tr>
	             <td height="20"><img src="../../../images/admin/aekeyword/arrow.gif" width="6" height="7"><span class="blue_text"><strong>등록된키워드 :<%if(prev_keyword!=null)out.print(prev_keyword[0]); %></strong></span></td>
	           </tr>
	           <tr>
	             <td height="20"><img src="../../../images/admin/aekeyword/arrow.gif" width="6" height="7">등록자 : <%if(prev_keyword!=null)out.print(prev_keyword[1]); %></td>
	           </tr>
	           <tr>
	             <td height="20"><img src="../../../images/admin/aekeyword/arrow.gif" width="6" height="7">입력일자 : <%if(prev_keyword!=null)out.print(prev_keyword[2]); %></td>
	           </tr>
	         </table></td>
	       </tr>
	     </table></td>
	</tr>
  
  <tr>
    <td style="padding-left:10px"><table width="370" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="120" align="left" style="padding: 3px 0px 0px 0px;" class="menu_gray"><strong>포탈키워드 :</strong></td>
			<td width="311" align="left" style="padding: 3px 0px 0px 0px;"><input type="text" name="pkValue" value=""></td>
		</tr>
	</table></td>
	</tr>
</table>
<table width="378" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td height="40" align="right">
		<img src="../../../images/admin/member/btn_save2.gif"  hspace="5" onclick="updateKeyword();" style="cursor:pointer;">
		<img src="../../../images/admin/member/btn_cancel.gif" onclick="window.close();" style="cursor:pointer;"></td>
	</tr>
</table>
</form>
</body>
</html>