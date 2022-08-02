<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.lang.*" %>
<%@ page import="risk.admin.site.SiteBean" %>
<%@ page import="risk.admin.site.SitegroupBean" %>
<%@ page import="risk.admin.site.SiteMng" %>
<%@ page import="risk.util.ParseRequest" %>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
	
	ParseRequest pr = new ParseRequest(request);
	
	String str_sg_seq = pr.getString("sg_seq","0");
	int sg_seq = Integer.parseInt(str_sg_seq);
	
	
	String sch_key = pr.getString("sch_key","");
	
	SiteMng sitemng = new SiteMng();
	//사이트그룹 가져오기~
	List sglist = sitemng.getSGList();
	//사이트 가져오기
	List sitelist = sitemng.getList_tier(sch_key,sg_seq,0);
	
%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="css/basic.css" type="text/css">
<style>
	<!--
	td { font-size:12px; color:#333333; font-family:"dotum"; ; line-height: 18px}
    input { font-size:12px; border:1px solid #CFCFCF; height:16px; color:#767676; }
	.t {  font-family: "Tahoma"; font-size: 11px; color: #666666}
    .tCopy { font-family: "Tahoma"; font-size: 12px; color: #000000; font-weight: bold}
body {
	margin-left: 4px;
	margin-top: 5px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-color: #F3F3F3;
}
	-->
	</style>
<script language="JavaScript" type="text/JavaScript">
<!--

	function search_TargetSite_frm()
	{
	    fSend.sch_key.value = parent.source_form.sch_key.value; 
		fSend.submit();
	}
//-->
</script>
</head>

<body>
<form name="fSend" action="ifram_source.jsp" method="post">
<input name="sch_key" type="hidden">

<table width="315" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="right" style="padding-left:10px">
    <table width="300" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><img src="../../../images/admin/site/brank.gif" width="1" height="5"></td>
      </tr>
      
      <tr>
		<td>
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<img src="../../../images/admin/site/admin_ico03.gif"  hspace="3">사이트 그룹 선택
					</td>
				</tr>
				<tr height="3"><td></td></tr>
				
	  				
				<tr>
					<td width="140">
						<select name="sg_seq" class="t"  style="width:140px;" onchange="search_TargetSite_frm();">
							<option value="0">전체</option>
					<%
						
					
						for(int i=0; i < sglist.size();i++) {
							SitegroupBean SGinfo = (SitegroupBean)sglist.get(i);
					%>
					<option value="<%=SGinfo.get_seq()%>" <%if( SGinfo.get_seq() == sg_seq) out.println("selected");%>><%= SGinfo.get_name()%></option>
					<%
						}
					%>
							</select>
					</td>
				</tr>
				
			</table>
		</td>
	</tr>
	<tr height="3"><td></td></tr>
      
      <tr>
        <td><img src="../../../images/admin/site/brank.gif" width="1" height="6"></td>
      </tr>
      <tr>
        <td height="20"><img src="../../../images/admin/site/admin_ico03.gif" width="10" height="11" hspace="3">수집사이트 리스트</td>
      </tr>
	   
      <tr>
        <td><select name="gsn"  multiple style="width:310px;height:220px;" class="t">
<%
	
	


	for(int i=0; i < sitelist.size();i++) {
		SiteBean siteinfo = (SiteBean)sitelist.get(i);
%>
	<option value="<%= siteinfo.get_gsn()%>"><%= siteinfo.get_name()%></option>
<%
	}
%>
			</select></td>
      </tr>
    </table></td>
  </tr>
</table>
</form>
</body>
</html>
