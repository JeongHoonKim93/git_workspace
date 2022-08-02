<%@page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%@ page import="java.util.ArrayList
				,java.util.List
				,java.util.HashMap
				,risk.issue.IssueMgr"
%>
<%
IssueMgr mgr = new IssueMgr();
ArrayList dataList = new ArrayList();

dataList = mgr.getExcel();
HashMap map = null;
response.setContentType("application/vnd.ms-excel; charset=EUC-KR") ;
response.setHeader("Content-Disposition", "attachment;filename=excel_reply.xls");
response.setHeader("Content-Description", "JSP Generated Data");
%>    
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<style>
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
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" border="1" >
    <table width="1200" border="1" cellspacing="0" cellpadding="0">
      <tr>
        <td rowspan="2" width="200" align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 번호 </strong></td>
        <td rowspan="2" width="200" align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 이름 </strong></td>
        <td rowspan="2" width="200" align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 작성시간 </strong></td>
		<td rowspan="2" width="200" align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 수정시간 </strong></td>
		<td rowspan="2" width="400" align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 내용 </strong></td>
	  </tr>
    </table>
    <% 
    for(int i=0; i < dataList.size(); i++){
    	map = new HashMap();
    	map = (HashMap)dataList.get(i);
    %>
    <table width="1000" border="1" cellspacing="0" cellpadding="0">
      <tr>
        <td align="left" class="menu_gray" style="padding: 3px 0px 0px 0px;">
        <%=map.get("commentNo").toString()%>
        </td>
        <td align="left" class="menu_gray" style="padding: 3px 0px 0px 0px;">
        <%=map.get("userName").toString()%>
        </td>
        <td align="left" class="menu_gray" style="padding: 3px 0px 0px 0px;">
        <%=map.get("regTime").toString()%>
        </td>
       	<td align="left" class="menu_gray" style="padding: 3px 0px 0px 0px;">
       	<%=map.get("modTime").toString()%>
        </td>
        <td align="left" class="menu_gray" style="padding: 3px 0px 0px 0px;">
        <%=map.get("contents").toString()%>
        </td>
      </tr>
    </table>
    <%} %>
</body>
</html>