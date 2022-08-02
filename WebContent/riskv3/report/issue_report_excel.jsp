<%//@ page contentType="text/html; charset=EUC-KR" %>
<%@page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%@ page import = "risk.util.ParseRequest" %>
<%@ page import = "risk.util.DateUtil" %>
<%@ page import = "risk.util.StringUtil" %>
<%@ page import = "risk.util.PageIndex" %>
<%@ page import = "risk.search.userEnvInfo" %>
<%@ page import="risk.issue.IssueReportMgr"%>
<%@ page import="risk.issue.IssueReportBean"%>						
<%@ page import = "java.util.ArrayList" %>
<%@ include file="../inc/sessioncheck.jsp" %>
<%
//try{
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();

	String reportchk = pr.getString("reportchk", "");
	String sDateFrom = pr.getString("param_sDate");
	String sDateTo = pr.getString("param_eDate");
	String ir_stime = pr.getString("param_sTime", "16");
	String ir_etime = pr.getString("param_eTime", "15");
			
	String ir_type = pr.getString("ir_type");
	
	//세션정보 가져오기~~
  	userEnvInfo uei = null;
    uei = (userEnvInfo) session.getAttribute("ENV");
	
  	//검색날짜 설정 : 기본 7일간 검색한다.
  	DateUtil du = new DateUtil();
  	String sCurrDate    = du.getCurrentDate();
  	if (sDateTo.equals("")) sDateTo = du.getCurrentDate();
  	if (sDateFrom.equals("")) {
  		du.addDay(-1);
  		sDateFrom = du.getDate();
  	}	
    	
	IssueReportMgr irMgr = new IssueReportMgr();
    

	ArrayList arrIrBean = irMgr.getIssueReportList_excel("",ir_type,"","","");	
  
	response.setContentType("application/vnd.ms-excel; charset=EUC-KR") ;
    response.setHeader("Content-Disposition", "attachment;filename=Issue_Report_"+ du.getCurrentDate("yyyyMMdd") +".xls");
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
    <table width="2400" border="1" cellspacing="0" cellpadding="0">
     
      <tr>
        <td width="120" align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 제목     </strong></td>
        <td width="440" align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 작성일시     </strong></td>
        <td width="200" align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 발송일시     </strong></td>
        <td width="200" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 메일발송 </strong></td>   
                
	  </tr>
    </table>
    <%  
    
    	String strSenti = "";
	for( int i = 0 ; i < arrIrBean.size() ; i++ ) {    
    		IssueReportBean irBean = new IssueReportBean();
    		irBean = (IssueReportBean) arrIrBean.get(i);
      		    	
    %>
    <table width="4480" border="1" cellspacing="0" cellpadding="0">
      <tr>
        <td width="120" align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=irBean.getIr_title()%>
        </td>
        <td width="440" align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=irBean.getIr_regdate()%>
        </td>
        <td width="200" align="center" class="menu_gray" style="padding: 3px 0px 0px 0px; text-overflow : ellipsis; overflow : hidden;">
          <nobr><%=irBean.getIr_maildate()%></nobr>
        </td>
		
        <td width="200" align="center" style="padding: 3px 0px 0px 5px;">
          <%=irBean.getIr_mailyn()%>
        </td>

       
      </tr>
    </table>
    <%  }%>
          <%//}catch(Exception e){System.out.println("issue_report_excel.jsp : "+e);} %>
</body>
</html>