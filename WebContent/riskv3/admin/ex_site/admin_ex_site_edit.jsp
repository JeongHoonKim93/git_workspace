<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="EUC-KR"%>
<%@ page import="java.util.ArrayList,
				risk.admin.ex_site.ExSiteMng,
                 risk.util.ParseRequest" 
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
	System.out.println("��-------------admin_ex_site_edit.jsp----------------��");
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	
	
	String s_seq = pr.getString("s_seq","");
	 String ekSeq= pr.getString("ekSeq","");	
	
	String searchWords = pr.getString("searchWords","");
	
	ExSiteMng eSMng = new ExSiteMng();
	
	
	String mode = pr.getString("mode"); 
	ArrayList arExSite = null;
	
	if(mode.equals("update")){
		arExSite = new ArrayList();
		arExSite = eSMng.updatePopUp_getExList(ekSeq, searchWords);
	}	

%>
<html>
<head>
<title>Insert title here</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="../../../css/base.css" type="text/css"/>
<script src="../../../js/ajax.js" type="text/javascript"></script>
<script src="../../../js/jquery.js" type="text/javascript"></script>
<script type="text/javascript">

	<%-- var flag = <%=mode%>
	
	$(function(){
		alert(1);
		if(flag == "insert"){
			alert(2);
			onLoadList();
		}
	}); --%>	
	
	function onLoadList(){
		selectSgSite();
		selectSSSite();
		selectSiteURL();
	}
	
	function selectSgSite()
	{	
		document.getElementById('selectGroup').value = "SG";
		ajax.post("popUpContents_ex_site.jsp", "editForm", "td_SgSeq_SiteList");
	}
	function selectSSSite()
	{	
		document.getElementById('selectGroup').value = "S";
		document.getElementById('sg_seq').value = '3'; //�ʱⰪ: 18.Ʈ����
		ajax.post("popUpContents_ex_site.jsp", "editForm", "td_SSeq_SiteList");
	}
	function selectSiteURL()
	{	
		document.getElementById('selectGroup').value = "site_URL";
		ajax.post("popUpContents_ex_site.jsp", "editForm", "td_siteUrl");
	}
	function selectSite(name, value)
	{	
		
		var targetId = ""; 
		//SG_SITE���� ���ý� S_SITE Ÿ�� td_SSeq_SiteList
		//S_SITE���� ���ý� URL�� ����Ʈ ��ȣ ���̱�
		if(name == 'SG_SITE') {
			//target����
			targetId = 'td_SSeq_SiteList';
			//select S_SITE�� ������ �����.
			document.getElementById('selectGroup').value = "S";
			//sg_seq�ѱ��
			document.getElementById('sg_seq').value = value;
			document.getElementById('s_seq').value = ""; //�ʱ�ȭ
		}else if(name == 'S_SITE'){
			//siteUrl�� ����Ѵ�.
			targetId = 'td_siteUrl';
			//����Ʈ URL�� ��ȣ�� ����������.
			document.getElementById('selectGroup').value = "site_URL";
			//sg_seq,s_seq�ѱ�� //sg_seq�� �����Ǿ� ����.
			document.getElementById('s_seq').value = value;
		}
		
		//else targetId = ''; 
		document.getElementById(name).value = value;
		ajax.post('popUpContents_ex_site.jsp', 'editForm', targetId);
		
		//URL ���̱�
		if(name == 'SG_SITE') {
			targetId = 'td_siteUrl';
			//����Ʈ URL�� ��ȣ�� ����������.
			document.getElementById('selectGroup').value = "site_URL";
			ajax.post('popUpContents_ex_site.jsp', 'editForm', targetId);
		}
		
	}
	function excuteUpdateExSite(mode){
		
			var f = document.editForm;
			var s_seq <% if (s_seq.equals("")) out.print(";"); else out.print("="+s_seq); %>;
			
			document.getElementById("ex_url").value = document.getElementById("text_include").value;
			
			document.getElementById("siteURL").value = $("#siteUrl").text();
			if(mode == "update"){
				document.getElementById("s_seq").value = s_seq;
			}
			
			f.action = "popUpContents_ex_site_InsertUpdate.jsp";
			f.submit();
		
	}
</script>
</head>

<body <% if(mode.equals("insert")) out.print("onload=" +"'onLoadList();'"); %> >
<form id="editForm" name="editForm" method="post" action=""> <!-- onsubmit="return false;"  -->
<input type="hidden" id= "mode" name="mode" value="<%=mode%>"/>
<input type="hidden" id= "selectGroup" name="selectGroup" value=""/>
<input type="hidden" id= "sg_seq" name="sg_seq" value=""/>
<input type="hidden" id= "s_seq" name="s_seq" value=""/>
<input type="hidden" id= "ex_url" name="ex_url" value=""/>
<input type="hidden" id= "siteURL" name="siteURL" value=""/>

<input type="hidden" name="ekSeq" value="<%=ekSeq%>"/> 

<%
	if(mode.equals("insert")){
		
%>
<table style="width:100%;" border="0" cellpadding="0" cellspacing="10">
	<tr>
		<td colspan="3" id="pop_head" style="line-height:normal;">
			<p>���ܻ���Ʈ���</p>
			<span><a href="javascript:close();"><img src="../../../images/search/pop_tit_close.gif"></a></span>
		</td>
	</tr>
</table>
<table style="width:100%;" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td style="padding-left:10px;padding-right:10px" ><table id="board_01"  style="width:100%;"  border="0" cellspacing="0" cellpadding="0">
    <!-- 	<tr>
			<td width="23%" style="padding: 3px 0px 0px 0px;"><strong>����</strong></td>
			<td  style="padding: 3px 0px 0px 0px;"><strong>����</strong></td>
		</tr> -->
		<tr>
			<td width="23%" style="padding: 3px 0px 0px 0px;"><strong>�׷�</strong></td>
			<td width="100" id="td_SgSeq_SiteList" style="text-align: left"></td> <!-- �׷켱�� -->
		</tr>
		<tr>
			<td width="23%" style="padding: 3px 0px 0px 0px;"><strong>����Ʈ</strong></td>	
			<td  id="td_SSeq_SiteList"  style="text-align: left"></td> <!-- ����Ʈ -->
		</tr>
		
		<!-- URL ���,����Ʈ��ȣ, ���Դܾ� ���-->
		<tr >
			<td width="100%" colspan="3" id="td_siteUrl" style="text-align: left">
			</td>
		</tr>
	</table></td>
	</tr>
</table>

<table width="378" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td height="40" align="center">
		<img src="../../../images/admin/member_group/btn_save.gif"  hspace="5" onclick="excuteUpdateExSite('insert');" style="cursor:pointer;">
		<img src="../../../images/admin/member/btn_cancel.gif" onclick="window.close();" style="cursor:pointer;"></td>
	</tr>
</table>



<%
	}else if(mode.equals("update")){ 
		
		String tmp[] = (String[]) arExSite.get(arExSite.size()-1);
		
		/* 
		tmp[0] = rs.getString("S_SEQ");
    	tmp[1] = rs.getString("EX_SEQ");
    	tmp[2] = rs.getString("EX_URL");
    	tmp[3] = rs.getString("EX_KEYWORD");
    	tmp[4] = rs.getString("S_NAME");
    	tmp[5] = rs.getString("SG_NAME"); */
		
%>
<table style="width:100%;" border="0" cellpadding="0" cellspacing="10">
	<tr>
		<td colspan="3" id="pop_head" style="line-height:normal;">
			<p>���ܻ���Ʈ����</p>
			<span><a href="javascript:close();"><img src="../../../images/search/pop_tit_close.gif"></a></span>
		</td>
	</tr>
</table>
<table style="width:100%;" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td style="padding-left:10px;padding-right:10px"><table id="board_01" style="width:100%;"  border="0" cellspacing="0" cellpadding="0">
		<!-- <tr>
			<td width="23%" style="padding: 3px 0px 0px 0px;"><strong>����</strong></td>
			<td  style="padding: 3px 0px 0px 0px;"><strong>����</strong></td>
		</tr> -->
		<tr>
			<td width="23%" style="padding: 3px 0px 0px 0px;"><strong>��ó</strong></td>
			<td width="100" id="td_SgSeq_SiteList" style="text-align: left"><%out.print(tmp[4]);%></td> <!-- �׷켱�� -->
		</tr>
		<tr>
			<td width="23%" style="padding: 3px 0px 0px 0px;"><strong>����Ʈ</strong></td>	
			<td  id="td_SSeq_SiteList"  style="text-align: left"><%=tmp[3]%></td> <!-- ����Ʈ -->
		</tr>
		
		
		<!-- URL ��� -->
	<%-- 	<tr>
			<td width="23%"><strong>���� URL</strong></td>
			<td colspan="2" id="siteUrl" style="text-align: left"><%=tmp[2] %></td>
		</tr> --%>
		<%-- <tr>
			<td width="23%"><strong>����Ʈ ��ȣ</strong></td>
			<td colspan="2" style="text-align: left"><%=tmp[0] %></td>
		</tr> --%>
		<tr>
			<td width="23%" style="padding: 3px 0px 0px 3px;" valign="top"><strong>����URL</strong></td>
			<td colspan="4"><textarea style="width: 100%;height: 100px;" id="text_include" style="text-align: left"><%=tmp[2] %></textarea></td>
		</tr>
		</table>
	</td>
	
	</tr>
</table>

<table width="378" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td height="40" align="center">
		<img src="../../../images/admin/member_group/btn_modify.gif"  hspace="5" onclick="excuteUpdateExSite('update');" style="cursor:pointer;" >
		<img src="../../../images/admin/member/btn_cancel.gif" onclick="window.close();" style="cursor:pointer;"></td>
	</tr>
</table>
<%
	}
%>
</form>
</body>
</html>