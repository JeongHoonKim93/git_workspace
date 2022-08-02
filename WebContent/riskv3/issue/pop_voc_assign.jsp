<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="EUC-KR"%>
<%@ page import="java.util.ArrayList
                 , risk.util.ParseRequest
                 , risk.voc.VocDataMgr
                 , risk.voc.VocBean
                 " 
%>
<%@ include file="../inc/sessioncheck.jsp" %>
<%
	ParseRequest pr = new ParseRequest(request);
	
	pr.printParams();
	
	String id_seq= pr.getString("id_seq");
	String md_pseq= pr.getString("md_pseq");
	String md_seq= pr.getString("md_seq");
	String md_title = pr.getString("md_title");
	
	VocDataMgr vMgr = new VocDataMgr();
	ArrayList ar_member =  vMgr.getAssMember();
	
%>

<%@page import="risk.voc.VocDataMgr"%><html>
<head>
<title>Insert title here</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="../../css/base.css" type="text/css">
<script language="javascript">
<!--
	function save()
	{
		var f = document.fSend;

		if(f.voc_member.selectedIndex == 0){
			alert('배정자를 선택하세요');
			return;

		}


		document.getElementById("sending").style.display = '';
		f.target='processFrm';
		f.action='issue_data_prc.jsp';
		f.submit();
	}

	function loding(){
	 var imgObj = document.getElementById("sending");
	 imgObj.style.left = (document.body.clientWidth / 2) - (imgObj.width / 2);
	 imgObj.style.top = (document.body.clientHeight / 2) - (imgObj.height / 2);
	 imgObj.style.display = 'none'; 
}

	
//-->
</script>
</head>
<body onload="loding();">
<form name="fSend" method="post" onsubmit="return false;">
<input type="hidden" name="mode" value="voc">
<input type="hidden" name="id_seq" value="<%=id_seq%>">
<input type="hidden" name="md_pseq" value="<%=md_pseq%>">
<input type="hidden" name="md_seq" value="<%=md_seq%>">
<input type="hidden" name="md_title" value="<%=md_title%>">
<img  id="sending" src="../../images/report/sending.gif"" style="position: absolute;  " >
<table style="width:100%;" border="0" cellpadding="0" cellspacing="10">
	<tr>
		<td colspan="3" id="pop_head">
			<p>VOC 배정</p>
			<span><a href="javascript:close();"><img src="../../images/search/pop_tit_close.gif"></a></span>
		</td>
	</tr>
</table>
<table style="width:100%;" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td style="padding-left:10px"><table width="370" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="120" align="left" style="padding: 3px 0px 0px 0px;" class="menu_gray"><strong>배정자 선택 :</strong></td>
			<td width="311" align="left" style="padding: 3px 0px 0px 0px;">
				<select name="voc_member" style="width: 120px">
					<option value="">선택하세요</option>
					<%
						VocBean.CodeBean bean = null;
						for(int i =0; i < ar_member.size(); i++){
							bean = (VocBean.CodeBean)ar_member.get(i);
					%>
						<option value="<%=bean.getMail()%>"><%=bean.getName()%></option>		
					<%
						}
					%>
				</select>
			</td>
		</tr>
	</table></td>
	</tr>
</table>
<table width="378" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td height="60" align="center">
		<img src="../../images/admin/member/btn_save2.gif"  hspace="5" onclick="save();" style="cursor:pointer;">
		<img src="../../images/admin/member/btn_cancel.gif" onclick="window.close();" style="cursor:pointer;"></td>
	</tr>
</table>

</form>
<iframe id="processFrm" name ="processFrm" width="0" height="0" style="display: none;" ></iframe>
</body>
</html>