<%@page contentType="application/vnd.ms-excel;charset=UTF-8"%>    
<%@ page import="	java.util.ArrayList					
					,risk.util.DateUtil 
					,risk.util.ParseRequest
					,risk.util.StringUtil
					,risk.util.ConfigUtil
					,risk.search.MetaMgr 
					,risk.admin.keyword.*
					,risk.statistics.StatisticsMgr
					,risk.statistics.StatisticsBean
					,risk.statistics.StatisticsSuperBean
					" %>

<%

	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	DateUtil du = new DateUtil();

	response.setContentType("application/vnd.ms-excel; charset=EUC-KR") ;
	response.setHeader("Content-Disposition", "attachment;filename=Statistical_Analysis01("+ du.getCurrentDate("yyyyMMdd") +").xls");
	response.setHeader("Content-Description", "JSP Generated Data");
	
	
	String sDateFrom = pr.getString("sDateFrom");
	String sDateTo = pr.getString("sDateTo");
	
	// 일자 마스터 가져오기	
	String first = du.getDate(sDateFrom,"yyyyMMdd");
	String last = du.getDate(sDateTo,"yyyyMMdd");
	
	long diffDay = -du.DateDiff("yyyyMMdd",first,last);
	int intDiffDay = Integer.parseInt(String.valueOf(diffDay));
	
	String[] date  = null;
	if(intDiffDay>0)
	{
		date  = new String[intDiffDay+1];
		for(int i = 0; i<intDiffDay+1; i++)
		{
			date[i] = du.getDate(du.addDay(first, i , "yyyyMMdd"),"MM.dd");										
		}
	}
	
	
	StatisticsMgr sMgr = new StatisticsMgr();
	ArrayList arMaster = sMgr.getIssueCode("11");
	ArrayList arData = sMgr.getStatisticsIssue(sDateFrom, sDateTo);
	
	
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
<table width="<%=(intDiffDay*75+150)%>" border="1" cellspacing="0" cellpadding="0"> 		
  <tr>    
  	<td width="220" align="center" ><strong>개인미디어 유형 통계</strong></td>
    <td align="left"></td>
  </tr> 
  <tr>
    <td colspan="2">
		<table width="<%=(intDiffDay*75+150)%>" border="1" cellspacing="0" cellpadding="0">
		      <tr>
		        <td colspan="2">&nbsp;</td>
		      </tr>
		      <tr>
		        <td colspan="2"><table width="<%=(intDiffDay*75+150)%>"  border="1" cellspacing="1" cellpadding="1">
		            <tr>
		              <td width="150" height="30" bgcolor="gray" align="center" style="padding: 3px 0px 0px 0px;font-weight: bold;">키워드</td>
<%
					for(int i=0; i<date.length; i++){
%>            
		              <td width="75" bgcolor="gray" align="center" style="padding: 3px 0px 0px 0px; font-weight: bold;"><%=date[i]%></td>
<%
					}
%>                        
		            </tr>
<%

					StatisticsSuperBean.CodeNameBean masterBean = null;
					StatisticsSuperBean.CodeNameBean dataBean = null;

					for(int i=0;i<arMaster.size();i++){
						masterBean = (StatisticsSuperBean.CodeNameBean)arMaster.get(i);
%>       
		            <tr>
		              <td width="75" align="center" style="padding: 3px 0px 0px 0px;"><%=masterBean.getName()%></td>
		          	  
<%				
						String cnt = "0";
						for(int j=0; j<date.length; j++){
							
							for(int k=0; k < arData.size(); k++){
								dataBean = (StatisticsSuperBean.CodeNameBean)arData.get(k);
								
								cnt = "0";
								
								if(date[j].equals(dataBean.getDate()))
								{cnt = dataBean.getCnt(); break;}				
							}
							
							out.print("<td width=\"75\" height=\"30\" align=\"center\" bgcolor=\"#FFFFFF\" style=\"padding: 3px 0px 0px 0px;\">"+cnt+"</td>");
							
						}
%>              
			  		  <td align="center" bgcolor="#FFFFFF" style="padding: 3px 0px 0px 0px;"></td>
		            </tr>   
<%
					}
%>       
		        </table></td>
		      </tr>
		</table>
	</td>
  </tr>
</table>
</body>
</html>
      