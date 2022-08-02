<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="EUC-KR"%>
<%@ page import="java.util.ArrayList,
 				 risk.admin.hashtag.HashtagBean,
                 risk.admin.hashtag.HashtagMgr,
                 risk.util.ParseRequest" 
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
	ParseRequest pr = new ParseRequest(request);
	HashtagMgr hMgr = new HashtagMgr();
	HashtagBean arrHbean = null;
	pr.printParams();
	
	String hcCode= pr.getString("hcCode");	
	String mode = pr.getString("mode");
	String modeText = "";
	
	if(mode.equals("insert")) {
		modeText = "추가";
	} else if(mode.equals("update")) {
		modeText = "수정";
		arrHbean = hMgr.getKeyword(hcCode);
	}

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

	function updatehCodeName()
	{
		var f = document.editForm;
		console.log(f.hcName.value);
		f.mode.value = 'update';
		if(f.hcName.value=='')
		{	
			alert('키워드 를 입력해주세요.'); return;
		}else{
			//앞뒤 공백 제거
			f.hcName.value = f.hcName.value.replace(/^\s*/,'');
			f.hcName.value = f.hcName.value.replace(/\s*$/,'');	
		}
			
		f.target='';
		f.action='hashtag_prc.jsp';
		f.submit();
		console.log(f);
	}
	
	function inserthCodeName() {
		console.log("insert test");
		var f = document.editForm;
		console.log(f);
		console.log(f.hcName.value);
		
		f.mode.value = 'insert';
		if(f.hcName.value=='')
		{	
			alert('키워드 를 입력해주세요.'); return;
		}else{
			//앞뒤 공백 제거
			f.hcName.value = f.hcName.value.replace(/^\s*/,'');
			f.hcName.value = f.hcName.value.replace(/\s*$/,'');	
		}
		
		f.target='';
		f.action='hashtag_prc.jsp';
		f.submit();
	}

</script>
</head>
<body>
<form name="editForm" method="post" onsubmit="return false;">
<input type="hidden" name="mode" value="<%=mode%>">
<input type="hidden" name="hcCode" value="<%=hcCode%>">

<table style="width:100%;" border="0" cellpadding="0" cellspacing="10">
	<tr>
		<td colspan="3" id="pop_head">
			<p>해시태그<%=modeText%></p>
			<span><a href="javascript:close();"><img src="../../../images/search/pop_tit_close.gif"></a></span>
		</td>
	</tr>
</table>
<table style="width:100%;" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td style="padding-left:10px"><table width="370" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="100" align="left" style="padding: 3px 0px 0px 0px;" class="menu_gray"><strong>해시태그 :</strong></td>
			<%
			if(modeText.equals("추가")) {
				%>
				<td width="311" align="left" style="padding: 3px 0px 0px 0px;"><input type="text" name="hcName" value=""></td>
				<%
			} else if(modeText.equals("수정")) {
				%>
				<td width="311" align="left" style="padding: 3px 0px 0px 0px;"><input type="text" name="hcName" value="<%=arrHbean.getHcName()%>"></td>
				<%
			}
			%>
		</tr>
	</table></td>
	</tr>
</table>
<table width="310" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td height="40" align="right">
		<%
		if(modeText.equals("추가")) {
			%>
			<img src="../../../images/admin/member/btn_save2.gif"  hspace="5" onclick="inserthCodeName();" style="cursor:pointer;">
			<%
		} else if(modeText.equals("수정")) {
			%>
			<img src="../../../images/admin/member/btn_save2.gif"  hspace="5" onclick="updatehCodeName();" style="cursor:pointer;">
			<%
		}
		%>
<!-- 		<img src="../../../images/admin/member/btn_save2.gif"  hspace="5" onclick="updatehCodeName();" style="cursor:pointer;"> -->
		<img src="../../../images/admin/member/btn_cancel.gif" onclick="window.close();" style="cursor:pointer;"></td>
	</tr>
</table>
</form>
</body>
</html>