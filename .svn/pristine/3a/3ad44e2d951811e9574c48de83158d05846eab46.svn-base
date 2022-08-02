<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="EUC-KR"%>
<%@ page import="java.util.ArrayList,
 				 risk.admin.relation.RelationBean,
                 risk.admin.relation.RelationMgr,
                 risk.util.ParseRequest" 
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
	ParseRequest pr = new ParseRequest(request);
	RelationMgr rMgr = new RelationMgr();
	RelationBean rBean = null;
	
	pr.printParams();
	
	String rk_seq= pr.getString("rk_seq");
	String rk_name= pr.getString("rk_name");
	String mode = pr.getString("mode");
	
	String[] arr_rk_seq = rk_seq.split(",");
	String[] arr_rk_name = rk_name.split(",");
		
	rBean = rMgr.getKeyword(rk_seq);

%>

<html>
<head>
<title>연관키워드 추가</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="../../../css/base.css" type="text/css">
<style>
<!--	
	.t {  font-family: "Tahoma"; font-size: 11px; color: #666666}

-->
	</style>
<script language="javascript">

function insertKeyword()
{
	var f = document.editForm;
	//f.mode.value = 'add';
	if(f.rk_name.value=='')
	{	
		alert('키워드 를 입력해주세요.'); return;
	}else{
		//앞뒤 공백 제거
		f.rk_name.value = f.rk_name.value.replace(/^\s*/,'');
		f.rk_name.value = f.rk_name.value.replace(/\s*$/,'');	
	}
		
	f.target='';
	f.action='relation_prc.jsp';
	f.submit();
}

function selectedKeyword(keyVal){
	editForm.rk_name.value = keyVal;
}

function winResize()
{
  var Dwidth = parseInt(document.body.scrollWidth);
  var Dheight = parseInt(document.body.scrollHeight);
  var divEl = document.createElement("div");
  divEl.style.position = "absolute";
  divEl.style.left = "0px";
  divEl.style.top = "0px";
  divEl.style.width = "100%";
  divEl.style.height = "100%";
  
  document.body.appendChild(divEl);
  
  window.resizeBy(Dwidth-divEl.offsetWidth, Dheight-divEl.offsetHeight);
  document.body.removeChild(divEl);
}

</script>
</head>
<body onload="winResize();">
<form name="editForm" method="post" onsubmit="return false;">
<input type="hidden" name="mode" value="<%=mode%>">
<input type="hidden" name="rk_seq" value="<%=rk_seq%>">

<table style="width:100%;" border="0" cellpadding="0" cellspacing="10">
	<tr>
		<td colspan="3" id="pop_head">
			<p>연관키워드관리</p>
			<span><a href="javascript:close();"><img src="../../../images/search/pop_tit_close.gif"></a></span>
		</td>
	</tr>
</table>
<% if(mode.equals("add") || mode.equals("update")) { %>
<table style="width:100%;" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td style="padding-left:10px"><table width="370" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="120" align="left" style="padding: 3px 0px 0px 0px;" class="menu_gray"><strong>연관키워드 :</strong></td>
			<td width="311" align="left" style="padding: 3px 0px 0px 0px;">
			<input class="textbox" type="text" name="rk_name" 
			<% if(mode.equals("add")) { %> value="" <% } %>
			<% if(mode.equals("update")){ %> value="<%=rBean.getRk_name()%>" <% } %>
			></td>
		</tr>
	</table></td>
	</tr>
</table>
<% } %>
<%if(mode.equals("sum")) { %>
<table style="width:100%;" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td style="padding-left:10px"><table width="370" border="0" cellspacing="0" cellpadding="0">
		<% for(int i=0; i<arr_rk_seq.length; i++) { %>
		<tr>
			<td width="120" align="left" style="padding: 3px 0px 0px 0px;" class="menu_gray"><strong>연관키워드 :</strong></td>
			<td width="311" align="left" style="padding: 3px 0px 0px 0px;"><a href="javascript:selectedKeyword('<%=arr_rk_name[i]%>');"><%=arr_rk_name[i]%></a>
			<%-- <input class="textbox" type="text" id=<%=arr_rk_seq[i]%> name="rk_name" readonly="readonly" onclick="alert('11111111')" value=""> --%>
			</td>
		</tr>
		<% } %>			
		<tr>
			<td width="120" align="left" style="padding: 3px 0px 0px 0px;" class="menu_gray"><strong>키워드 :</strong></td>
			<td width="311" align="left" style="padding: 3px 0px 0px 0px;">
			<input class="textbox" type="text" id="rk_name" name="rk_name" value="">
			</td>
		</tr>	
	</table></td>
	</tr>
</table>
<% } %>

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