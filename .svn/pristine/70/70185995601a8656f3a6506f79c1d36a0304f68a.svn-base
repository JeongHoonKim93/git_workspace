<%@page contentType="application/vnd.ms-excel;charset=UTF-8"%>   
<%@ page import="	java.util.ArrayList					
					,risk.util.DateUtil 
					,risk.util.ParseRequest
					,risk.util.StringUtil
					,risk.util.ConfigUtil
					,risk.search.MetaMgr 
					,risk.statistics.StatisticsMgr
					,risk.statistics.StatisticsBean
					" %>

<%
	DateUtil   du = new DateUtil();
	ParseRequest  pr = new ParseRequest(request);
	StatisticsMgr staticMgr = new StatisticsMgr();
	StringUtil su = new StringUtil();
	ConfigUtil cu = new ConfigUtil();
	
	response.setContentType("application/vnd.ms-excel; charset=EUC-KR") ;
	response.setHeader("Content-Disposition", "attachment;filename=Statistical_Analysis02"+ du.getCurrentDate("yyyyMMdd") +".xls");
	response.setHeader("Content-Description", "JSP Generated Data");
	
	ArrayList static_keyword_chartdata = new ArrayList();
	ArrayList Keyword_data = new ArrayList();
	ArrayList Str_Keyword_name = new ArrayList();
	ArrayList Site_group = new ArrayList();
	ArrayList Site_Gname = new ArrayList();
	
	String Keyword_name = "";
	String sDateFrom ="";
	String sDateTo ="";
	String minNo = "";
	String maxNo = "";
	String Keyword_K_xps = "";
	String str_chart_legend = "";
	String get_site_group_data_sum = "";
	
	pr.printParams();
	Keyword_K_xps = cu.getConfig("KeywordXp");
	sDateTo = du.getCurrentDate("yyyy-MM-dd");
	sDateFrom = du.getCurrentDate("yyyy-MM-dd");
	
	MetaMgr  mMgr = new MetaMgr();
	mMgr.getMaxMinNo(sDateFrom,sDateTo);
	minNo = mMgr.msMinNo;
	maxNo = mMgr.msMaxNo;

	//===================================그룹별키워드 일일동향DATA=========================================
	Str_Keyword_name = staticMgr.get_Keyword_name();
	Site_Gname = staticMgr.get_siteG_name();
	str_chart_legend = staticMgr.get_chart_legend_name(Keyword_K_xps);
	//===================================그룹별키워드 일일동향DATA=========================================
			
	
%>

<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<style type="text/css">
<!--
td { font-size:12px; color:#333333; font-family:"gulim"; ; line-height: 18px}
   input { font-size:12px; border:0px solid #CFCFCF; height:16px; color:#767676; }
.t {  font-family: "Tahoma"; font-size: 11px; color: #666666}
   .tCopy { font-family: "Tahoma"; font-size: 12px; color: #000000; font-weight: bold}
a:link { color: #333333; text-decoration: none; }
a:visited { text-decoration: none; color: #000000; }
a:hover { text-decoration: none; color: #FF9900; }
a:active { text-decoration: none; }

body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	SCROLLBAR-face-color: #F2F2F2;
	SCROLLBAR-shadow-color: #999999;
	SCROLLBAR-highlight-color: #999999;
	SCROLLBAR-3dlight-color: #FFFFFF;
	SCROLLBAR-darkshadow-color: #FFFFFF;
	SCROLLBAR-track-color: #F2F2F2;
	SCROLLBAR-arrow-color: #333333;
     }
.menu_black {  font-family: "돋움", "돋움체"; font-size: 12px; line-height: 16px; color: #000000}
.textw { font-family: "돋움", "돋움체"; font-size: 12px; line-height: normal; color: #FFFFFF; font-weight: normal}

.menu_blue {
	font-family: "돋움", "돋움체";
	font-size: 12px;
	line-height: 16px;
	color: #3D679C;
	font-weight: normal;
}
.menu_gray {
font-family: "돋움", "돋움체"; font-size: 12px; line-height: 16px; color: #4B4B4B
}
.menu_red {
font-family: "돋움", "돋움체"; font-size: 12px; line-height: 16px; color: #CC0000
}
.menu_blueOver {

	font-family: Tahoma;
	font-size: 11px;
	line-height: 16px;
	color: #3D679C;
	font-weight: normal;
}
.menu_blueTEXTover {


	font-family: "돋움", "돋움체";
	font-size: 12px;
	line-height: 16px;
	color: #3366CC;
	font-weight: normal;
}
.textwbig {
font-family: "돋움", "돋움체"; font-size: 14px; line-height: normal; color: #FFFFFF; font-weight: normal
}
.textBbig {

font-family: "돋움", "돋움체"; font-size: 14px; line-height: normal; color: #000000; font-weight: normal
}
.menu_grayline {
	font-family: "돋움", "돋움체";
	font-size: 12px;
	line-height: 16px;
	color: #4B4B4B;
	text-decoration: underline;
}
.menu_grayS {

font-family: "돋움", "돋움체"; font-size: 11px; line-height: 16px; color: #4B4B4B
}
-->
</style>
</head>
<body>
<table>
 <tr>
 	<td height="30">&nbsp;</td>
 </tr>
</table>
<table>
 <tr>
 	<td width="300" height="30"><strong>키워드 그룹별통계&nbsp;(<%=sDateFrom %>&nbsp;~&nbsp;<%=sDateTo %>)</strong></td>
 </tr>
</table>
<table border="1" cellspacing="0" cellpadding="0">
  
      <tr>
        <td colspan="2"><table width="750"  border="1" cellspacing="0" cellpadding="1">
            <tr bgcolor="#CFCFCF">
              <td width="300" height="30" align="center" style="padding: 3px 0px 0px 0px;" ></td>
<%

for(int i = 0; i < Site_Gname.size(); i++){
	StatisticsBean sb = (StatisticsBean)Site_Gname.get(i);
%>            
              <td width="75" align="center" style="padding: 3px 0px 0px 0px; font-weight: bold;"><%=sb.getSg_name()%></td>
<%
}
%>              
              <td width="75" align="center" style="padding: 3px 0px 0px 0px; font-weight: bold;">계</td>
            </tr>
<%
for(int i = 0; i < Str_Keyword_name.size(); i++){
	StatisticsBean sb = (StatisticsBean)Str_Keyword_name.get(i);
%>            
            <tr bgcolor="#FFFFFF">
              <td width="50" height="28" align="center" bgcolor="#FFFFFF" style="padding: 3px 0px 0px 0px;"><%=sb.getK_value()%></td>
<%
	Site_group = staticMgr.get_site_group_data(minNo,maxNo,sb.getK_xp());
	int cnt = 0;
	for(int j = 0; j < Site_group.size(); j++){
		StatisticsBean sb1 = (StatisticsBean)Site_group.get(j);
%>              
              <td align="center"  style="padding: 3px 0px 0px 0px;">
<%
if(sb1.getK_cnt().equals("0")){
%>              
              	<%=sb1.getK_cnt()%>
<%
}else{
%>
              	<%=sb1.getK_cnt()%>
<%
}
%>          	
              </td>
<%
	}
	get_site_group_data_sum = staticMgr.get_site_group_data_sum(sDateFrom,sDateTo,sb.getK_xp());	
%>
			  <td align="center" bgcolor="#FFFFFF" style="padding: 3px 0px 0px 0px;">
<%
if(get_site_group_data_sum.equals("0")){
%>			  
			  	<%=get_site_group_data_sum%>
<%
}else{
%>
			  	<%=get_site_group_data_sum%>
			  	
<%
}
%>	  	
			  </td>
            </tr>
<%            
}
%>            
        </table></td>
      </tr>  
</table>
</body>
</html>
      