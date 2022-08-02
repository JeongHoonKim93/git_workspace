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
					" %>

<%

	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	DateUtil du = new DateUtil();
	long diffDay = 0;
	int intDiffDay = 0;
	String first = "";
	String last = "";
	
	response.setContentType("application/vnd.ms-excel; charset=EUC-KR") ;
	response.setHeader("Content-Disposition", "attachment;filename=Statistical_Analysis01("+ du.getCurrentDate("yyyyMMdd") +").xls");
	response.setHeader("Content-Description", "JSP Generated Data");
	
	
	String[] date  = null;
	ArrayList keywordList = new ArrayList();
	ArrayList keywordData = new ArrayList();
	
	String sDateFrom = pr.getString("sDateFrom");
	String sDateTo = pr.getString("sDateTo");
	String k_xp = pr.getString("k_xp");
	String k_yp = pr.getString("k_yp");
	String k_zp = pr.getString("k_zp");
	
	first = du.getDate(sDateFrom,"yyyyMMdd");
	last = du.getDate(sDateTo,"yyyyMMdd");
	
	diffDay = -du.DateDiff("yyyyMMdd",first,last);
	intDiffDay = Integer.parseInt(String.valueOf(diffDay));
	
	if(intDiffDay>0)
	{
		date  = new String[intDiffDay+1];
		for(int i = 0; i<intDiffDay+1; i++)
		{
			date[i] = du.getDate(du.addDay(first, i , "yyyyMMdd"),"MM.dd");										
		}
	}	        
	
	KeywordMng kMng = new KeywordMng();
	StatisticsMgr sMgr = new StatisticsMgr();
	if(!k_xp.equals("")&&!k_yp.equals("") && !k_zp.equals("")){
		//keywordList = kMng.getKeywordInfo(k_xp,k_yp,"2");
		
		keywordList = sMgr.getSiteGroup();
		keywordData = sMgr.getKeywordData(sDateFrom,sDateTo,k_xp,k_yp,k_zp);
	}
	
	
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
  	<td width="220" align="center" ><strong>키워드별 통계</strong></td>
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
					for(int i=0;i<keywordList.size();i++){
						StatisticsBean keyInfo = new StatisticsBean();
						keyInfo = (StatisticsBean)keywordList.get(i);
%>       
		            <tr>
		              <td width="75" align="center" style="padding: 3px 0px 0px 0px;"><%=keyInfo.getSg_name()%></td>
		          	  
<%				
						String cnt = "0";
						for(int j=0; j<date.length; j++){
						
							for(int k=0; k<keywordData.size(); k++){
								StatisticsBean sBean = new StatisticsBean();
								sBean = (StatisticsBean)keywordData.get(k);
								
								cnt = "0";
								
								//if(keyInfo.getKGxp().equals(sBean.getK_xp()) && keyInfo.getKGyp().equals(sBean.getK_yp()) && keyInfo.getKGzp().equals(sBean.getK_zp()) && date[j].equals(sBean.getDate()))
								if(keyInfo.getSg_seq().equals(sBean.getSg_seq()) && date[j].equals(sBean.getDate()))
								{cnt = sBean.getS_cnt(); break;}				
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
      