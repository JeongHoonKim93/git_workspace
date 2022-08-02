<%/*******************************************************
*  1. 분    류    명 : RSN
*  2. 업무 시스템 명 : 정보검색
*  3. 프로그램  개요 : 리스트 엑셀파일 저장
*  4. 관 련  Table명 :
*  5. 작    성    자 :
*  6. 작    성    일 : 2006.4.13
*  7. 주  의  사  항 :
*  8. 변   경   자   :
*  9. 변 경  일 자   :
* 10. 변 경  사 유   :
********************************************************/%>
<%@page contentType="application/vnd.ms-excel;charset=UTF-8"%>

<%@ page import="java.util.ArrayList
                 ,risk.util.StringUtil
                 ,risk.util.ParseRequest
                 ,risk.util.DateUtil
                 ,risk.search.PortalMgr
                 ,risk.search.PortalBean
                 ,risk.search.userEnvInfo"
%>
<%
	DateUtil du = new DateUtil();

    String sCurrDate    = du.getCurrentDate();

    response.setContentType("application/vnd.ms-excel; charset=UTF-8") ;
    response.setHeader("Content-Disposition", "attachment;filename=" + sCurrDate + ".xls");
    response.setHeader("Content-Description", "JSP Generated Data");
%>

<%@include file="../inc/sessioncheck.jsp" %>
<%
	//사용자 기본 환경을 조회한다.
    //없으면  NCS기본 유저의 환경을 조회하여 가져온다.
    //userEnvInfo uei = null;
    //uei     = (userEnvInfo) session.getAttribute("ENV");

    //페이지에 사용할 변수 선언 부분
    StringUtil      su = new StringUtil();
    ParseRequest    pr = new ParseRequest(request);
    PortalMgr     pmgr = new PortalMgr();

    /*
    String sOrder       = pr.getString("sOrder","MT_DATE");       //
    uei.setOrder( pr.getString("sOrder","MT_DATE") );

    String sOrderAlign  = pr.getString("sOrderAlign","DESC");     //
    uei.setOrderAlign( pr.getString("sOrderAlign","DESC") );
	*/
	
	String sDateFrom = pr.getString("sDateFrom");
	String sDateTo = pr.getString("sDateTo");
	String sKeywordKind = pr.getString("sKeywordKind");
	String sKeyword = pr.getString("sKeyword");
	String sOrder = pr.getString("sOrder");
	String sOrderAlign = pr.getString("sOrderAlign");
	String stime = pr.getString("stime");
	String etime = pr.getString("etime");
	int iTotalCnt = pr.getInt("iTotalCnt");
	
	String psOrder = "";
	if (sOrder.equals("PM_DATE")) {
		psOrder = sOrder + " " + sOrderAlign;
	} else {
		psOrder = sOrder + " " + sOrderAlign
				+ ", PM_DATE DESC";
	}
    
    ArrayList alData = null;
    alData = pmgr.getExcelSearchList(
    		sDateFrom,
    		sDateTo,
    		sKeywordKind,
    		sKeyword,
    		psOrder,
    		stime,
    		etime,
    		iTotalCnt
    );
    
    
    
%>
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>

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

</style>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" border="1" >
    <table width="900" border="1" cellspacing="0" cellpadding="0">
      <tr>
        <td width="100" align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 출처     </strong></td>
        <td width="150" align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 상세출처 </strong></td>
        <td width="350" align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 제목     </strong></td>
        <td width="200" align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> URL     </strong></td>
        <td width="100" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 수집시간 </strong></td>
      </tr>
    </table>
    <%
    	for( int i = 0 ; i < alData.size() ; i++ ) {
                PortalBean pBean = (PortalBean) alData.get(i);
    %>
    <table width="950" border="1" cellspacing="0" cellpadding="0">
      <tr>
        <td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
        	<%=pBean.getPm_site()%>
        </td>
        <td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
      		<%=pBean.getPm_board()%>
        </td>
        <td align="left" style="padding: 3px 0px 0px 5px;">
          <%=su.nvl(pBean.getPm_title(),"제목없음")%>
        </td>
        <td align="left" style="padding: 3px 0px 0px 5px;">
          <%=pBean.getPm_url()%>
        </td>
        <td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=pBean.getFormatPm_date("yyyy-MM-dd")%>
        </td>
      </tr>
    </table>
    <%  }%>
</body>
</html>