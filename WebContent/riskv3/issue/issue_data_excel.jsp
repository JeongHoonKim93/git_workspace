<%//@ page contentType="text/html; charset=EUC-KR" %>
<%@page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%@ page import = "risk.util.ParseRequest" %>
<%@ page import = "risk.util.DateUtil" %>
<%@ page import = "risk.util.StringUtil" %>
<%@ page import = "risk.util.PageIndex" %>
<%@ page import = "risk.search.userEnvInfo" %>
<%@ page import = "risk.issue.IssueMgr" %>
<%@ page import = "risk.issue.IssueDataBean" %>
<%@ page import = "java.util.ArrayList" %>
<%@ include file="../inc/sessioncheck.jsp" %>
<%@ page import = "java.net.URLDecoder" %>
<%@page import="java.util.HashMap"%>
<%
//try{
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();

	String id_seqs = pr.getString("id_seqs", "");
	String sDateFrom = pr.getString("param_sDate");
	String sDateTo = pr.getString("param_eDate");
	String ir_stime = pr.getString("param_sTime", "16");
	String ir_etime = pr.getString("param_eTime", "15");
	
	String typeCodeSeries = pr.getString("typeCodeSelect");
	String sentiSeries = pr.getString("typeCodeSenti");
	String transSeries = pr.getString("typeTransyn");
	String infoSeries = pr.getString("typeCodeInfo");
	String relationKeywordSeries = pr.getString("relationKeyword");
	String register = pr.getString("register");
	String searchKeyword = URLDecoder.decode(pr.getString("param_searchKey"), "UTF-8");
	String searchKeyType = pr.getString("param_keyType");
	String sltSiteGroups = pr.getString("sltSiteGroups","");
	
	//세션정보 가져오기~~
  	userEnvInfo uei = null;
    uei = (userEnvInfo) session.getAttribute("ENV");
	
    // 데이터 정렬
    String sOrder       = pr.getString("sOrder","MD_DATE");
    String sOrderAlign  = pr.getString("sOrderAlign","DESC"); 
	uei.setOrder( sOrder );
    uei.setOrderAlign( sOrderAlign );
	
  	//검색날짜 설정 : 기본 7일간 검색한다.
  	DateUtil du = new DateUtil();
  	String sCurrDate    = du.getCurrentDate();
  	if (sDateTo.equals("")) sDateTo = du.getCurrentDate();
  	if (sDateFrom.equals("")) {
  		du.addDay(-1);
  		sDateFrom = du.getDate();
  	}	
    
    String setStime = "";
	String setEtime = "";
	if(ir_stime.equals("24")){
		setStime = "23:59:59";	
	}else{
		setStime = ir_stime + ":00:00";
	}
	if(ir_etime.equals("24")){
		setEtime = "23:59:59";	
	}else{
		setEtime = ir_etime + ":59:59";
	}
		
    IssueMgr issueMgr = new IssueMgr();
	ArrayList arrIdBean = issueMgr.getIssueDataExcel(id_seqs, sDateFrom, setStime, sDateTo, setEtime, typeCodeSeries, sentiSeries, infoSeries, relationKeywordSeries
			, searchKeyType, searchKeyword, register, uei.getOrder(), uei.getOrderAlign(), sltSiteGroups, transSeries);
	
	HashMap<String, String> codeMap = new HashMap<String, String>();
	codeMap = issueMgr.getCodeSiteGroupMap();
	
  
	response.setContentType("application/vnd.ms-excel; charset=EUC-KR") ;
    response.setHeader("Content-Disposition", "attachment;filename=Issue_Data_"+ du.getCurrentDate("yyyyMMdd") +".xls");
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
        <td width="120" align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 출처     </strong></td>
        <td width="440" align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 제목     </strong></td>
        <td width="200" align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> URL     </strong></td>
        <td width="200" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 수집 시간 </strong></td>   
        <td width="200" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 구분 </strong></td>   
        <td width="200" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 신라호텔 </strong></td>   
        <td width="100" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 신라모노그램 </strong></td>   
        <td width="100" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 신라스테이 </strong></td>   
        <td width="100" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 호텔_리스크·VOC 유형 </strong></td>   
        <td width="100" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 영업점 </strong></td>   
        <td width="100" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> TR_리스크·VOC 유형 </strong></td>   
        <td width="100" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 자회사·계열사 </strong></td>   
        <td width="100" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> SHP_리스크·VOC 유형 </strong></td>   
        <td width="100" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> SBTM_리스크·VOC 유형 </strong></td>   
        <td width="100" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> CEO/경영진_리스크·VOC 유형 </strong></td>   
        <td width="100" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 사업/IR/준법/임직원 등_리스크·VOC 유형 </strong></td>   
        <td width="100" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 정부/ESG 등_리스크·VOC 유형 </strong></td>   
        <td width="100" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 리스크·VOC 등급 </strong></td>   
        <td width="100" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 영향력 </strong></td>   
        <td width="100" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 성향 </strong></td>   
        <td width="100" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 사이트그룹 </strong></td>   
        <td width="100" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 연관어 </strong></td>   
        <td width="120" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 등록자  </strong></td>        
	  </tr>
	  
    </table>
    <%  
    
    	String strClout = "";
    	String strSenti = "";
    	for( int i = 0 ; i < arrIdBean.size() ; i++ ) {
    	
	    	IssueDataBean IDBean = new IssueDataBean();
	    	IDBean = (IssueDataBean) arrIdBean.get(i);
	    	
	      	String[] ar_SiteCnt = IDBean.getMd_same_ct().split(",");
	      	
	      	String code1 = "-";
	      	if(!IDBean.getCode1().equals("")) {
	      		code1 = IDBean.getCode1();
 	      	}
	      	
	      	String code2 = "-";
	      	if(!IDBean.getCode2().equals("")) {
	      		code2 = IDBean.getCode2();
 	      	}
	      	
	      	String code3 = "-";
	      	if(!IDBean.getCode3().equals("")) {
	      		code3 = IDBean.getCode3();
 	      	}
	      	
	      	String code4 = "-";
	      	if(!IDBean.getCode4().equals("")) {
	      		code4 = IDBean.getCode4();
 	      	}
	      	
	      	String code5 = "-";
	      	if(!IDBean.getCode5().equals("")) {
	      		code5 = IDBean.getCode5();
 	      	}
	      	
	      	String code6 = "-";
	      	if(!IDBean.getCode6().equals("")) {
	      		code6 = IDBean.getCode6();
 	      	}
	      	
	      	String code7 = "-";
	      	if(!IDBean.getCode7().equals("")) {
	      		code7 = IDBean.getCode7();
 	      	}
	      	
	      	String code8 = "-";
	      	if(!IDBean.getCode8().equals("")) {
	      		code8 = IDBean.getCode8();
 	      	}
	      	
	      	String code9 = "-";
	      	if(!IDBean.getCode9().equals("")) {
	      		code9 = IDBean.getCode9();
 	      	}
	      	
	      	String code10 = "-";
	      	if(!IDBean.getCode10().equals("")) {
	      		code10 = IDBean.getCode10();
 	      	}
	      	
	      	String code11 = "-";
	      	if(!IDBean.getCode11().equals("")) {
	      		code11 = IDBean.getCode11();
 	      	}
	      	
	      	String code12 = "-";
	      	if(!IDBean.getCode12().equals("")) {
	      		code12 = IDBean.getCode12();
 	      	}
	      	
	      	String code13 = "-";
	      	if(!IDBean.getCode13().equals("")) {
	      		code13 = IDBean.getCode13();
 	      	}
	      	
	      	String code14 = "-";
	      	if(!IDBean.getCode14().equals("")) {
	      		code14 = IDBean.getCode14();
 	      	}
	      	
 	      	if(IDBean.getId_clout().equals("1")){
 	      		strClout = "파워";
	      	}else if(IDBean.getId_clout().equals("2")){
	      		strClout = "일반";
	      	}

	      	if(IDBean.getSenti().equals("1")){
	      		strSenti = "긍정";
	      	}else if(IDBean.getSenti().equals("2")){
	      		strSenti = "부정";
	      	}else {
	      		strSenti = "중립";
	      	}
			
 	      	String reportyn = "미포함";
 	      	if(IDBean.getId_reportyn().equals("Y")) {
 	      		reportyn = "포함";
 	      	}
 	      	
    %>
    <table width="4480" border="1" cellspacing="0" cellpadding="0">
      <tr>
        <td width="120" align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=IDBean.getMd_site_name()%>
        </td>
        
        <td width="440" align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=IDBean.getId_title()%>
        </td>
        
        <td width="200" align="center" class="menu_gray" style="padding: 3px 0px 0px 0px; text-overflow : ellipsis; overflow : hidden;">
          <nobr><%=IDBean.getId_url()%></nobr>
        </td>
		
        <td width="200" align="center" style="padding: 3px 0px 0px 5px;">
          <%=IDBean.getMd_date()%>
        </td>

		<td width="100" align="center" style="padding: 3px 0px 0px 5px;">
          <%=code1%>
        </td>

		<td width="100" align="center" style="padding: 3px 0px 0px 5px;">
          <%=code3%>
        </td>

		<td width="100" align="center" style="padding: 3px 0px 0px 5px;">
          <%=code4%>
        </td>

		<td width="100" align="center" style="padding: 3px 0px 0px 5px;">
          <%=code5%>
        </td>

		<td width="100" align="center" style="padding: 3px 0px 0px 5px;">
          <%=code6%>
        </td>

		<td width="100" align="center" style="padding: 3px 0px 0px 5px;">
          <%=code7%>
        </td>

		<td width="100" align="center" style="padding: 3px 0px 0px 5px;">
          <%=code8%>
        </td>

		<td width="100" align="center" style="padding: 3px 0px 0px 5px;">
          <%=code9%>
        </td>

		<td width="100" align="center" style="padding: 3px 0px 0px 5px;">
          <%=code10%>
        </td>

		<td width="100" align="center" style="padding: 3px 0px 0px 5px;">
          <%=code11%>
        </td>

		<td width="100" align="center" style="padding: 3px 0px 0px 5px;">
          <%=code12%>
        </td>

		<td width="100" align="center" style="padding: 3px 0px 0px 5px;">
          <%=code13%>
        </td>

		<td width="100" align="center" style="padding: 3px 0px 0px 5px;">
          <%=code14%>
        </td>

		<td width="100" align="center" style="padding: 3px 0px 0px 5px;">
          <%=code2%>
        </td>

        <td width="100" align="center" style="padding: 3px 0px 0px 5px;">
          <%=strClout%>
        </td>

        <td width="100" align="center" style="padding: 3px 0px 0px 5px;">
          <%=strSenti%>
        </td>
        
        <td width="100" align="center" style="padding: 3px 0px 0px 5px;">
          <%=codeMap.get(IDBean.getSg_seq())%>
        </td>
                
         <td width="100" align="center" style="padding: 3px 0px 0px 5px;">
          <%=IDBean.getRelationkeys()%>
        </td>

         <td width="120" align="center" style="padding: 3px 0px 0px 5px;">
          <%=IDBean.getM_name()%>
        </td>
            
      </tr>
    </table>
    <%  }%>
          <%//}catch(Exception e){System.out.println("issue_data_excel.jsp : "+e);} %>
</body>
</html>