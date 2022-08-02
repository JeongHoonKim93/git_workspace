<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="EUC-KR"%>
<%@ page import="java.util.ArrayList,
 				 risk.admin.info.*,
                 risk.util.ParseRequest" 
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
	ParseRequest pr = new ParseRequest(request);
	InfoGroupMgr igMgr = new InfoGroupMgr();
	InfoGroupBean igBean = new InfoGroupBean();
	pr.printParams();
	
	String i_seq= pr.getString("i_seq");	
	String mode = pr.getString("mode");
		
	if(mode.equals("insert"))
	{
		
	}else if (mode.equals("update")){
		igBean = igMgr.getInfoGroupBean(i_seq);
	}

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
	<!--
	td { font-size:12px; color:#333333; font-family:"gulim"; ; line-height: 18px}
    input { font-size:12px; border:1px solid #CFCFCF; height:16px; color:#767676; }
	.t {  font-family: "Tahoma"; font-size: 11px; color: #666666}
    .tCopy { font-family: "Tahoma"; font-size: 12px; color: #000000; font-weight: bold}
	.textwbig { font-family: "", "u"; font-size: 14px; line-height: normal; color: #FFFFFF; font-weight: normal}
	input.readonly {
	BORDER-RIGHT: #cfcfcf 1px solid; BORDER-TOP: #cfcfcf 1px solid; FONT-SIZE: 12px; BORDER-LEFT: #cfcfcf 1px solid; COLOR: #767676; BORDER-BOTTOM: #cfcfcf 1px solid; HEIGHT: 14px ;PADDING-TOP: 4px ; BACKGROUND-COLOR: #FFDEE9
	}	
	-->
</style>
<script language="javascript">
<!--
	function insertKeyword()
	{
		var f = document.editForm;
		f.mode.value = 'insert';
		if(f.i_nm.value=='')
		{	
			alert('정보그룹을 입력해주세요.'); return;
		}else{
			//앞뒤 공백 제거
			f.i_nm.value = f.i_nm.value.replace(/^\s*/,'');
			f.i_nm.value = f.i_nm.value.replace(/\s*$/,'');	
		}
					
		f.target='';
		f.action='infogroup_prc.jsp';
		f.submit();
	}

	function updateKeyword()
	{
		var f = document.editForm;
		f.mode.value = 'update';
		if(f.i_nm.value=='')
		{	
			alert('정보그룹을 입력해주세요.'); return;
		}else{
			//앞뒤 공백 제거
			f.i_nm.value = f.i_nm.value.replace(/^\s*/,'');
			f.i_nm.value = f.i_nm.value.replace(/\s*$/,'');	
		}	
		f.target='';
		f.action='infogroup_prc.jsp';
		f.submit();
	}
//-->
</script>
<title>Insert title here</title>
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form name="editForm" method="post" onsubmit="return false;">
<input type="hidden" name="mode" value="<%=mode%>">
<input type="hidden" name="i_seq" value="<%=i_seq%>">

<%
	if(mode.equals("insert")){
%>
	<table width="400" height="40" border="0" cellpadding="0" cellspacing="0">
	  <tr>
	    <td width="13" background="images/pop_topbg.gif"><img src="images/pop_topimg01.gif" width="13" height="40"></td>
	    <td width="374" background="images/pop_topbg.gif" class="textwbig"><img src="images/pop_ico01.gif" width="3" height="11">&nbsp;&nbsp;<strong>정보그룹 등록</strong></td>
	    <td width="13" align="right" background="images/pop_topbg.gif"><img src="images/pop_topimg02.gif" width="13" height="40"></td>
	  </tr>
	</table>
	<table width="400" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td bgcolor="#E8E8E8"><table width="100%" border="0" cellspacing="5" cellpadding="5">
	      <tr>
	        <td height="20" align="center" valign="top" bgcolor="#FFFFFF">
	          <table width="378" border="0" cellspacing="0" cellpadding="0">
	            <tr>
	              <td><img src="images/brank.gif" width="1" height="5"></td>
	            </tr>
	            <tr>
	              <td><img src="images/pop_dotline.gif" width="378" height="1"></td>
	            </tr>
	            <tr>
	              <td height="50" bgcolor="#F6F6F6"><table width="370" border="0" cellspacing="0" cellpadding="0">
	                <tr>
	                  <td width="8"><img src="images/pop_ico02.gif" width="3" height="5" align="absmiddle"></td>
	                  <td width="90" align="left" style="padding: 3px 0px 0px 0px;" class="menu_gray"><strong>정보그룹 :</strong></td>
	                  <td width="311" align="left" style="padding: 3px 0px 0px 0px;"><input type="text" name="i_nm" style="ime-mode:active" value=""></td>
	                </tr>
	              </table></td>
	            </tr>
	            <tr>
	              <td><img src="images/pop_dotline.gif" width="378" height="1"></td>
	            </tr>
	          </table>
	          <table width="378" border="0" cellspacing="0" cellpadding="0">
	            <tr>
	              <td height="40" align="right"><img src="images/pop_btn_add.gif" width="38" height="21" hspace="5" onclick="insertKeyword();" style="cursor:hand;"><img src="images/pop_btn_cancel.gif" width="38" height="21" onclick="window.close();" style="cursor:hand;"></td>
	            </tr>
	          </table></td>
	      </tr>
	    </table></td>
	  </tr>
	</table>
<%
	}else if(mode.equals("update")){
%>
	<table width="400" height="40" border="0" cellpadding="0" cellspacing="0">
	  <tr>
	    <td width="13" background="images/pop_topbg.gif"><img src="images/pop_topimg01.gif" width="13" height="40"></td>
	    <td width="374" background="images/pop_topbg.gif" class="textwbig"><img src="images/pop_ico01.gif" width="3" height="11">&nbsp;&nbsp;<strong>정보그룹 등록</strong></td>
	    <td width="13" align="right" background="images/pop_topbg.gif"><img src="images/pop_topimg02.gif" width="13" height="40"></td>
	  </tr>
	</table>
	<table width="400" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td bgcolor="#E8E8E8"><table width="100%" border="0" cellspacing="5" cellpadding="5">
	      <tr>
	        <td height="20" align="center" valign="top" bgcolor="#FFFFFF">
	          <table width="378" border="0" cellspacing="0" cellpadding="0">
	            <tr>
	              <td><img src="images/brank.gif" width="1" height="5"></td>
	            </tr>
	            <tr>
	              <td><img src="images/pop_dotline.gif" width="378" height="1"></td>
	            </tr>
	            <tr>
	              <td height="30" bgcolor="#F6F6F6"><table width="370" border="0" cellspacing="0" cellpadding="0">
	                <tr>
	                  <td width="8"><img src="images/pop_ico02.gif" width="3" height="5" align="absmiddle"></td>
	                  <td width="90" align="left" style="padding: 3px 0px 0px 0px;" class="menu_gray"><strong>정보그룹 :</strong></td>
	                  <td width="311" align="left" style="padding: 3px 0px 0px 0px;"><input type="text" name="i_nm" style="ime-mode:active" value="<%=igBean.getI_nm()%>"></td>
	                </tr>
	              </table></td>
	            </tr>	           
	            <tr>
	              <td><img src="images/pop_dotline.gif" width="378" height="1"></td>
	            </tr>
	          </table>
	          <table width="378" border="0" cellspacing="0" cellpadding="0">
	            <tr>
	              <td height="40" align="right"><img src="images/pop_btn_apply.gif" width="38" height="21" hspace="5" onclick="updateKeyword();" style="cursor:hand;"><img src="images/pop_btn_cancel.gif" width="38" height="21" onclick="window.close();" style="cursor:hand;"></td>
	            </tr>
	          </table></td>
	      </tr>
	    </table></td>
	  </tr>
	</table>	
<%
	}
%>
</form>
</body>
</html>