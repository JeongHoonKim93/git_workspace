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
	
	String str_ts_type = pr.getString("ts_type","0");
	int ts_type = Integer.parseInt(str_ts_type);
	
	String sch_key = pr.getString("sch_key","");

	TierSiteMgr tiermng = new TierSiteMgr();
	//티어타입 가져오기
	List sglist = tiermng.getTs_type();
	//티어사이트 가져오기
	List sitelist = tiermng.getTierList(sch_key,ts_type);
%>

<%@page import="risk.admin.tier.TierSiteMgr"%>
<%@page import="risk.admin.tier.TierSiteBean"%><html>
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

	function edit_tier(mode)
	{
		var url = "pop_edit_tier.jsp?mode="+mode;
		window.open(url, "pop_tierRank", "width=400,height=150");
	}
	
	function del_tier(){
		
		if(fSend.ts_type.value == 0){
			alert('매체 순위를 선택해 주세요.');
		} else {
			if( confirm('해당 매체 순위를 삭제하시겠습니까?') ) {
				location.href = "tier_prc.jsp?mode=del&ts_type="+fSend.ts_type.value;	
			}
		}
	}

	function search_TargetSite_frm()
	{
		fSend.sch_key.value = parent.target_form.sch_key.value;
		fSend.submit();
	}
//-->
</script>
</head>

<body>
<form name="fSend" action="ifram_target.jsp" method="post">
<input name=sch_key type="hidden">

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
						<img src="../../../images/admin/site/admin_ico03.gif"  hspace="3">매체순위 선택
					</td>
				</tr>
				<tr height="3"><td></td></tr>
				
				<tr>
					
					
					<td width="150">
						<select name="ts_type" class="t"  style="width:140px;" onchange="search_TargetSite_frm();">
							<option value="0">전체</option>
					<%
						
					
						for(int i=0; i < sglist.size();i++) {
							//SitegroupBean SGinfo = (SitegroupBean)sglist.get(i);
					%>
					<option value="<%=(Integer)sglist.get(i)%>" <%if( (Integer)sglist.get(i) == ts_type) out.println("selected");%>><%= "Tier" + (Integer)sglist.get(i)%></option>
					<%
						}
					%>
							</select>
					</td>
					<td width="200">
						<img src="../../../images/admin/site/form_add.gif"  onclick="edit_tier('add');"  align="absmiddle" style="cursor:pointer;">
						<img src="../../../images/admin/site/form_del.gif"  onclick="del_tier();"  align="absmiddle" style="cursor:pointer;">
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
        <td height="20"><img src="../../../images/admin/site/admin_ico03.gif" width="10" height="11" hspace="3">타겟 사이트 리스트</td>
      </tr>
      <tr>
        <td><select name="gsn"  multiple style="width:310px;height:220px;" class="t">
<%
	
	

	for(int i=0; i < sitelist.size();i++) {
		TierSiteBean siteinfo = (TierSiteBean)sitelist.get(i);
%>
<option value="<%= siteinfo.getTs_seq()%>"><%= siteinfo.getTs_name()%></option>
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
