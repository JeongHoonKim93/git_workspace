<%//@ page contentType="text/html; charset=EUC-KR" %>
<%@page contentType="application/vnd.ms-excel;charset=UTF-8"%>

<%@ page import="risk.issue.IssueMgr,
				 risk.issue.IssueDataBean,
				 risk.issue.IssueCodeBean,
				 risk.issue.IssueCodeMgr,
				 risk.issue.IssueCommentBean,
				 risk.util.StringUtil,
                 risk.util.DateUtil,
                 risk.util.ParseRequest,
                 risk.admin.member.MemberBean,
                 risk.admin.member.MemberDao,                 
                 java.util.ArrayList,
                 java.net.URLDecoder,
                 risk.search.userEnvMgr,
                risk.search.userEnvInfo"
                  
%>
<%@ include file="../inc/sessioncheck.jsp" %>
<%
//try{
	ParseRequest pr	= new ParseRequest(request);
	pr.printParams();	
	DateUtil du	= new DateUtil();
	StringUtil	su 	= new StringUtil();
	IssueMgr issueMgr = new IssueMgr();
	ArrayList arrIcBean = null;
	ArrayList arrIdBean = null;
	ArrayList arrIdcBean =  null;
	
	int chk_cnt = 0;
	int nowPage = 0;	
	int pageCnt = 0;
	int totalCnt = 0;
	int totalPage = 0;	
	
	String searchKey = URLDecoder.decode(pr.getString("encodingSearchKey",""),"UTF-8");
	String i_seq = pr.getString("i_seq","");
	String it_seq = pr.getString("it_seq","");
	String sDateFrom = pr.getString("sDateFrom");
	String sDateTo = pr.getString("sDateTo");
	String typeCode = pr.getString("typeCode");
	String typeCode_Etc = pr.getString("typeCode_Etc");
	String m_seq = pr.getString("m_seq",SS_M_NO);
	String check_no = pr.getString("check_no","");
	String language = pr.getString("language","");
	String relationKey = pr.getString("relKeyword");
	String register = pr.getString("register");
	String ir_stime = pr.getString("ir_stime");
	String ir_etime = pr.getString("ir_etime");
	String keyType = pr.getString("keyType");
	
	//세션정보 가져오기~~
	userEnvInfo uei = null;
    uei = (userEnvInfo) session.getAttribute("ENV");
	
	String srtMsg = null;
	
	//IssueCodeMgr 인스턴스 생성
	IssueCodeMgr icMgr = IssueCodeMgr.getInstance();
	IssueCodeBean icBean = null;	
	icMgr.init(0);		
	//관련정보 리스트
	IssueDataBean idBean = null;
	//arrIdBean = issueMgr.getIssueDataList_groupBy(nowPage,pageCnt,check_no,i_seq,it_seq,"","","2",sDateFrom,ir_stime+":00:00",sDateTo,ir_etime+":00:00",typeCode,"","Y", language,"","","");
	arrIdBean = issueMgr.getExcel_SK(sDateFrom,ir_stime+":00:00",sDateTo,ir_etime+":59:59", typeCode, typeCode_Etc, relationKey, searchKey, keyType, register);
	    
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
a:link { color: blue; text-decoration: underline; }
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
      	<td width="120" align="center"  bgcolor="#FFFF00"  style="padding: 3px 0px 0px 0px;"> <strong> 번호  </strong></td>   
     	<td width="120" align="center"  bgcolor="#FFFF00"  style="padding: 3px 0px 0px 0px;"> <strong> 일자(yyyy-mm-dd) </strong></td>   
        <td width="120" align="center" bgcolor="#FFFF00"  style="padding: 3px 0px 0px 0px;"> <strong> 사이트     </strong></td>
        <td width="440" align="center" bgcolor="#FFFF00"  style="padding: 3px 0px 0px 0px;"> <strong> 제목     </strong></td>
        <td width="200" align="center" bgcolor="#FFFF00"  style="padding: 3px 0px 0px 0px;"> <strong> URL     </strong></td>
       
       <%-- 
        <td colspan="5" width="100" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> TM </strong></td>
        <td colspan="5" width="100" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> Family </strong></td>
        <td colspan="5" width="100" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 인물 </strong></td>
        <td colspan="5" width="100" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> SK그룹 </strong></td>
        <td colspan="5" width="100" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 관계사 </strong></td>
        
        <td rowspan="2" width="100" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 영향력구분 </strong></td>
        <td rowspan="2" width="100" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 출처구분 </strong></td>
        <td rowspan="2" width="100" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 정보유형구분 </strong></td>
        <td rowspan="2" width="100" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 단순언급 </strong></td>
		<td rowspan="2" width="100" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 주요이슈 </strong></td>	
        --%>
	  </tr>
<%--	  <tr>
      	 <td width="100"  align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong>TM 상세</strong></td>
         <td width="100"  align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong>성향</strong></td>
         <td width="100"  align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong>과거성향</strong></td>
         <td width="100"  align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong>정보속성</strong></td>
         <td width="100"  align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong>연관키워드</strong></td>
         <td width="100"  align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong>Family 상세</strong></td>
         <td width="100"  align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong>성향</strong></td>
         <td width="100"  align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong>과거성향</strong></td>
         <td width="100"  align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong>정보속성</strong></td>
         <td width="100"  align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong>연관키워드</strong></td>
      	 <td width="100"  align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong>인물 상세</strong></td>
         <td width="100"  align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong>성향</strong></td>
         <td width="100"  align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong>과거성향</strong></td>
         <td width="100"  align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong>정보속성</strong></td>
         <td width="100"  align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong>연관키워드</strong></td>
         <td width="100"  align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong>그룹 상세</strong></td>
         <td width="100"  align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong>성향</strong></td>
         <td width="100"  align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong>과거성향</strong></td>
         <td width="100"  align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong>정보속성</strong></td>
         <td width="100"  align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong>연관키워드</strong></td>
      	 <td width="100"  align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong>관계사 상세</strong></td>
         <td width="100"  align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong>성향</strong></td>
         <td width="100"  align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong>과거성향</strong></td>
         <td width="100"  align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong>정보속성</strong></td>
         <td width="100"  align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong>연관키워드</strong></td>
      </tr> --%>
      
      
    </table>
    <%  for( int i = 0 ; i < arrIdBean.size() ; i++ ) {
    	
    	IssueDataBean IDBean = new IssueDataBean();
    	IDBean = (IssueDataBean) arrIdBean.get(i);
    	ArrayList arrIDCBean = new ArrayList();
    	arrIDCBean = IDBean.getArrCodeList();
    	
      	//String[] ar_SiteCnt = IDBean.getMd_same_ct().split(",");
    %>
    <table width="4480" border="1" cellspacing="0" cellpadding="0">
      <tr>
		<td width="120" align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=(i+1)%>
        </td>      
        <td width="120" align="center" style="padding: 3px 0px 0px 5px;">
          <%=IDBean.getDate()%>
        </td>        
        <td width="120" align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=IDBean.getMd_site_name()%>
        </td>
        <td width="440" align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=IDBean.getId_title()%>
        </td>
        <td width="200" align="center" class="menu_gray" style="padding: 3px 0px 0px 0px; text-overflow : ellipsis; overflow : hidden;">
        <a href="<%=IDBean.getId_url()%>"><%=IDBean.getId_url()%></a>
        </td>
               
<%--         <td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=IDBean.getA8()%>
        </td>
        
        <td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=IDBean.getA9_2()%>
        </td>
        
        <td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=IDBean.getA20_2()%>
        </td>
        <td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=IDBean.getA14_2()%>
        </td>
		<td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=IDBean.getRk2()%>
        </td>
        
  		<td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=IDBean.getA10()%>
        </td>
        
        <td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=IDBean.getA9_3()%>
        </td>
         <td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=IDBean.getA20_3()%>
        </td>       
        <td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=IDBean.getA14_3()%>
        </td>

		<td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=IDBean.getRk3()%>
        </td>
        
        <td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=IDBean.getA11()%>
        </td>
        
        <td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=IDBean.getA9_4()%>
        </td>
        
        <td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=IDBean.getA20_4()%>
        </td>  
           
        <td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=IDBean.getA14_4()%>
        </td>

		<td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=IDBean.getRk4()%>
        </td>   
        
        <td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=IDBean.getA12()%>
        </td>
        
        <td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=IDBean.getA9_5()%>
        </td>
        
        <td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=IDBean.getA20_5()%>
        </td>         
        <td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=IDBean.getA15_5()%>
        </td>

		<td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=IDBean.getRk5()%>
        </td>
        
        <td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=IDBean.getA13()%>
        </td>
        
        <td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=IDBean.getA9_7()%>
        </td>
        
        <td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=IDBean.getA20_7()%>
        </td>       
        <td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=IDBean.getA15_7()%>
        </td>

		<td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=IDBean.getRk7()%>
        </td>
        
        <td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=IDBean.getA16()%>
        </td> 
        
        <td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=IDBean.getA6()%>
        </td>
        
        <td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=IDBean.getA17()%>
        </td>
        
        <td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=IDBean.getA18()%>
        </td>

        <td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=IDBean.getA19()%>
        </td> --%>
       
      </tr>
    </table>
    <%  }%>
          <%//}catch(Exception e){System.out.println("issue_data_excel.jsp : "+e);} %>
</body>
</html>