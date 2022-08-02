<%/*******************************************************
*  1. 분    류    명 : RSN
*  2. 업무 시스템 명 : 정보검색
*  3. 프로그램  개요 : 검색 기능 사용자 화면
*  4. 관 련  Table명 :
*  5. 작    성    자 :
*  6. 작    성    일 : 2006.4.13
*  7. 주  의  사  항 :
*  8. 변   경   자   :
*  9. 변 경  일 자   :
* 10. 변 경  사 유   :
********************************************************/%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.ArrayList
                 ,risk.util.Log
                 ,risk.util.StringUtil
                 ,risk.util.ParseRequest
                 ,risk.util.DateUtil
                 ,risk.search.MetaBean
                 ,risk.search.PortalBean
                 ,risk.search.MetaMgr
                 ,risk.search.keywordInfo
                 ,risk.search.siteGroupInfo
                 ,risk.search.siteDataInfo
                 ,risk.search.userEnvMgr
                 ,risk.search.userEnvInfo"
%>
<%@page import="risk.issue.IssueCodeMgr"%>
<%@page import="risk.issue.IssueCodeBean"%>
<%@page import="risk.issue.IssueMgr"%>
<%@page import="risk.issue.IssueBean"%>
<%@page import="risk.issue.IssueTitleBean"%>
<%@page import="risk.util.PageIndex"%>
<%@include file="../inc/mobile_sessioncheck.jsp" %>
<%
	//페이지에 사용할 변수 선언 부분
	
	StringUtil      su = new StringUtil();
    ParseRequest    pr = new ParseRequest(request);
    DateUtil        du = new DateUtil();
    siteDataInfo   sdi = null;
    siteGroupInfo  sgi = null;
    MetaMgr     smgr = new MetaMgr();
	
    pr.printParams();

    //사용자 기본 환경을 조회한다.
    //없으면  NCS기본 유저의 환경을 조회하여 가져온다.
    userEnvInfo uei = null;
    uei     = (userEnvInfo) session.getAttribute("ENV");

    String sCurrDate    = du.getCurrentDate();
    String sInit = (String)session.getAttribute("INIT");
 
    //Top메뉴에서 로고나 정보검색 메뉴 클릭시에는 검색조건을 초기화 시킨다.
    if ( sInit != null  && sInit.equals("INIT") ) {
        userEnvMgr uem = new userEnvMgr();
        uei = uem.getUserEnv( SS_M_ID );
        session.removeAttribute("INIT");

        if ( Integer.parseInt(uei.getSt_interval_day()) > 1 ) {
            du.addDay( -1 * ( Integer.parseInt( uei.getSt_interval_day() )-1 ) );
            uei.setDateFrom( du.getDate() );
            uei.setDateTo  ( sCurrDate    ) ;
        } else {
            uei.setDateFrom( sCurrDate  ) ;
            uei.setDateTo  ( sCurrDate  ) ;
        }
    }

	if ( request.getParameter("xp") != null ) uei.setK_xp( pr.getString("xp") );
      
    // 키워드그룹 일련번호
    if ( request.getParameter("yp") != null ) uei.setK_yp( pr.getString("yp") );

    // 키워드 일련번호
    if ( request.getParameter("zp") != null ) uei.setK_zp( pr.getString("zp") );

    if ( uei.getK_xp().equals("0") ) uei.setK_xp("");
    if ( uei.getK_yp().equals("0") ) uei.setK_yp("");
    if ( uei.getK_zp().equals("0") ) uei.setK_zp("");
  

    // 검색단어    
    if( pr.getString("sKeyword","").equals(""))
       uei.setKeyword("");   
    else{
    	uei.setKeyword(su.Trim(request.getParameter("sKeyword")).replaceAll("[ ]{1,}|[ ]{1,}&\\?[ ]{1,}", "&").replaceAll("\\&\\&|\\&[ ]{1,}|[ ]{1,}&\\&","&").replaceAll("\\&\\||\\|\\&","|").replaceAll("\\&\\-|\\-\\&","-"));
    }
    
    String sDataRange   = pr.getString("");         // 검색 범위 ( 전체,전체,기사,게시,공지 )
    
    uei.setDateFrom( pr.getString("sDateFrom",uei.getDateFrom() ) ) ;
    uei.setDateTo  ( pr.getString("sDateTo"  ,uei.getDateTo()   ) ) ;

    if ( uei.getDateFrom()== null ) {

        if ( Integer.parseInt( uei.getSt_interval_day() ) > 1 ) {
            du.addDay( -1 * ( Integer.parseInt( uei.getSt_interval_day() )-1 ) );
            uei.setDateFrom( du.getDate() );
            uei.setDateTo  ( sCurrDate    ) ;
        } else {
            uei.setDateFrom( sCurrDate  ) ;
            uei.setDateTo  ( sCurrDate  ) ;
        }
    }
    
    uei.setSt_interval_day( pr.getString("Period",uei.getSt_interval_day())) ;
    uei.setMd_type( pr.getString("type", uei.getMd_type() ) );
    uei.setSg_seq( pr.getString("sg_seq",uei.getSg_seq())  );
    uei.setS_seq(pr.getString("sd_gsn", uei.getS_seq()));
 
    //전체 선택시 빈문자열로 대체
    if ( uei.getSg_seq().equals("0") ) uei.setSg_seq("");
    if ( uei.getS_seq().equals("0") ) uei.setS_seq("");


    String sOrder       = pr.getString("sOrder","MD_DATE");
    String sOrderAlign  = pr.getString("sOrderAlign","DESC"); 
    
    uei.setOrder( pr.getString("sOrder","MD_DATE") );
    uei.setOrderAlign( pr.getString("sOrderAlign","DESC") );
    
    uei.setSt_list_cnt( pr.getString("rowcnt",uei.getSt_list_cnt()) );   
    int iNowPage        = pr.getInt("nowpage",1);

    //String sSearchType  = pr.getString("searchtype","1");
    uei.setSt_menu( pr.getString("searchtype",uei.getSt_menu()) );   // 상세 검색창 표출 여부

    uei.setSearchMode(  pr.getString("searchmode", su.nvl(uei.getSearchMode(),"ALLKEY") ) );     // 데이터 검색 모드 ALLKEY, ALLDB
    
    if(uei.getSearchMode().equals("DELIDX")){   	    	
    	uei.setDelName(pr.getString("del_mname",SS_M_NO));    	
    }else{
    	uei.setDelName(pr.getString("del_mname",SS_M_NO));
    }

    String sTimer       = "Y";
    uei.setSt_reload_time(pr.getString("interval", uei.getSt_reload_time() ));
	//System.out.println("\n\n pr.getString interval : "+pr.getString("interval", uei.getSt_reload_time() )+"\n\n");

    //지금까지 설정 내역을 세션에 저장
    session.removeAttribute("ENV");
    session.setAttribute("ENV",uei);

    //검색 사이트 검색
    //ArrayList alGroup = smgr.getSiteGroup( uei.getMg_site() );
    
    //검색 키워드 그룹 대그룹, 중그룹
    //ArrayList alKeyword =  smgr.getMobileKeyword( uei.getMg_xp(), "", "" );   

	//선택 키워드 그룹
	//ArrayList seKeyword = null;
	
	//if(uei.getK_xp().length()>0){
	//	seKeyword = smgr.getMobileKeyword( uei.getK_xp(), uei.getK_yp(), uei.getK_zp() );
	//}
    
    ArrayList alData = null;

    alData = smgr.getPortalList(
                                   iNowPage                                     ,   //int    piNowpage ,
                                   Integer.parseInt(uei.getSt_list_cnt())         ,   //int    piRowCnt  ,
                                   uei.getDateFrom()         ,   //String psDateFrom,
                                   uei.getDateTo()           ,   //String psDateTo  ,
                                   ""                              //String psKeyWord ,
                               );




    int iPageCnt = smgr.mBeanPageCnt;
    int iDataCnt = smgr.mBeanDataCnt;
    int iTotalCnt= smgr.portalCnt;

    int iTotalPage      = iTotalCnt / Integer.parseInt(uei.getSt_list_cnt());
    if ( ( iTotalCnt % Integer.parseInt(uei.getSt_list_cnt()) ) > 0 ) iTotalPage++;
    
    
    
   	//이슈데이터 등록 관련
   	/*
   	IssueMgr isMgr = new IssueMgr();
   	IssueBean isBean = new IssueBean();
   	   	
   	IssueCodeMgr icMgr = IssueCodeMgr.getInstance();
   	IssueCodeBean icBean = new IssueCodeBean();
   	icMgr.init(1);
   	
   	ArrayList arrIssueBean = new ArrayList();
   	arrIssueBean = isMgr.getIssueList(0,0,"","","","","Y");
	*/   	
   	
   	
   	
   	int nextPage = 0;
   	int prePage = 0;
   	
   	if(iNowPage<iTotalPage){
   		nextPage = iNowPage+1;
   	}
   	
   	if(iNowPage>1){
   		prePage = iNowPage-1;
   	}
   	
   	//검색 날짜
   	String nowDay = du.getCurrentDate();
   	String SnowDay = du.getCurrentDate("M월 dd일(E)");
   	String nowDay1 = du.addDay(nowDay,-1);
   	du.setDate(nowDay1);
   	String SnowDay1 = du.getDate("M월 dd일(E)");
   	String nowDay2 = du.addDay(nowDay,-2);
   	du.setDate(nowDay2);
   	String SnowDay2 = du.getDate("M월 dd일(E)");
   	
   	//선택 키워드
   	/*
   	String keyName = pr.getString("keyName","전체 키워드 그룹");
   	
	keywordInfo kBean = null;
   	if(seKeyword!=null && seKeyword.size()>0){
        kBean = (keywordInfo)seKeyword.get(0);
   		
   		keyName = kBean.getK_Value();
   	}
   	*/
   	
%>
<html>
<head>
<title><%=SS_TITLE%></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, width=device-width" />
<link rel="stylesheet" href="css/basic.css" type="text/css">
<style type="text/css"> 
<!--
.t_gray {
	font-family: Tahoma, "돋움", "돋움체";
	font-size: 11px;
	color: #9F9F9F;
}
.input {
	BORDER-RIGHT: #E1E2E1 1px solid; BORDER-TOP: #E1E2E1 1px solid; BORDER-LEFT: #E1E2E1 1px solid; BORDER-BOTTOM: #E1E2E1 1px solid; COLOR: #767676;  HEIGHT: 20px; FONT-SIZE: 12px; PADDING-TOP: 3px; BACKGROUND-COLOR: #ffffff
}
body {
	background-image: url(images/login_bg01.gif);
	background-color: #384b5a;
}
-->
</style>
<script language="JavaScript" type="text/JavaScript">
<!--
	var chkPop = 1;
	function hrefPop(url){
		//window.open(url,'hrefPop'+chkPop,'');
		window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop'+chkPop,'');
		chkPop++;
	}

	function goPcVersion(){
		document.location = '../main.jsp';		
	}
	function logOut(){
		document.location = '../logout.jsp?mType=m';
	}

	function search(){
		//setXpYp();
		
		document.fSend.target='';
		document.fSend.submit();
	}

	function setXpYp(){

		//var obj = document.all.kXp;

		//var xpyp = obj(obj.selectedIndex).value;
		var xpyp = document.all.kXp.value;

		if(xpyp.length>0){
			document.all.xp.value = xpyp.split(',')[0];
			document.all.yp.value = xpyp.split(',')[1];
		} 

		//document.all.keyName.value = obj(obj.selectedIndex).text;
		
	}

	function nextPage(next){
		if(next!=0){
			document.all.nowpage.value = next;
			search();
		}
	}
	function prePage(pre){
		if(pre!=0){
			document.all.nowpage.value = pre;
			search();
		}
	}
	function setDate(date){
		document.all.sDateFrom.value = date;
		document.all.sDateTo.value = date;
		search();
	}
	
//-->	
</script>	
</head>
<form name="fSend" action="mobile_portal_list.jsp" method="post">
<input type="hidden" name="xp">
<input type="hidden" name="yp">
<input type="hidden" name="zp">
<input type="hidden" name="nowpage" value="<%=iNowPage%>">
<input type="hidden" name="sDateFrom" value="<%=uei.getDateFrom()%>">
<input type="hidden" name="sDateTo" value="<%=uei.getDateTo()%>">
<input type="hidden" name="keyName" value="">
<body onload="window.scrollTo(0,1);" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td bgcolor="#374154"><img src="images/list_top.gif"></td>
  </tr>
  <tr>
	  <td bgcolor="#374154"><table width="100%" border="0" cellspacing="0" cellpadding="0" >
	  	<tr>
	  		<td><a href="mobile_content_list.jsp"><img src="images/m_menu_key.gif"></a></td>
	  		<td><img src="images/m_menu_center_bar.gif"></td>
	  		<td><a href="mobile_portal_list.jsp"><img src="images/m_menu_por.gif"></a></td>	
	  	</tr>
	  </table></td>
  </tr>

  <tr>
    <td height="2" background="images/list_top_line.gif" style="padding:0px 13px 0px 13px"></td>
  </tr>
  
  <tr>
    <td height="31" bgcolor="#e4e4e4" style="padding:3px 13px 0px 13px"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
      <tr>
<!--        <td><strong class="menu_gray"><%//=keyName%> 선택</strong></td>-->
        <td align="right"><strong class="menu_gray"><%=iTotalCnt%>건&nbsp;  <%=iTotalCnt==0?0:iNowPage%>/<%=iTotalPage%> pages</strong></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="1" bgcolor="#d3d3d3" style="padding:0px 13px 0px 13px"></td>
  </tr>
<%

for( int i = 0 ; i < alData.size() ; i++ ){
	//MetaBean mBean = (MetaBean) alData.get(i);
	PortalBean pBean = (PortalBean) alData.get(i);
%>  
  <tr>
    <td bgcolor="#FFFFFF" style="padding:8px 13px 8px 13px"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="23" class="menu_blue"><a onClick="hrefPop('<%=pBean.getUrl()%>');" href="javascript:void(0);"><%=su.nvl(su.cutString(pBean.getTitle(),30,"..." ),"제목없음")%></a></td>
      </tr>
      <tr>
        <td class="menu_gray">
        <%=pBean.getHighrightHtml(50,"0000CC", "삼성 sds")%></td>
      </tr>
      <tr>
        <td height="20"><span class="menu_red"><%=pBean.getName()%></span> <span class="menu_gray02"><%=pBean.getFormatMd_date("yy/MM/dd HH:mm")%></span></td>
      </tr>
    </table></td>
  </tr>
<%if(i!=alData.size()-1){ %>  
  <tr>
    <td height="1" bgcolor="#e1e1e1" ></td>
  </tr>
<%}else{ %>  
  <tr>
    <td height="2" background="images/list_line01.gif" ></td>
  </tr>
<%}%>  
<%
}
%>  
    
   
  
  <tr>
    <td height="28" background="images/list_bg01.gif" bgcolor="#FFFFFF" ><table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="50%" align="center" class="textwhite" style="cursor:pointer;" onClick="prePage('<%=prePage%>');"><strong class="textwhite02">&lt; 이전</strong></td>
        <td width="50%" align="center" background="images/list_bar01.gif" onClick="nextPage('<%=nextPage%>');" class="textwhite02" style="cursor:pointer; background-repeat:no-repeat; font-weight: bold;">다음 &gt;</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="2" background="images/list_line02.gif"></td>
  </tr>
  <tr>
    <td height="33" background="images/list_bg02.gif"><table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="33%" align="center" background="images/list_bar02.gif" <%if(uei.getDateFrom().equals(nowDay2)){%> class="menu_blueOver" <%}else{%> class="menu_blueTEXTover" <%}%> style="background-repeat:no-repeat;cursor:pointer;" onClick="setDate('<%=nowDay2%>');"><%=SnowDay2%></td>
        <td width="33%" align="center" background="images/list_bar02.gif" <%if(uei.getDateFrom().equals(nowDay1)){%> class="menu_blueOver" <%}else{%> class="menu_blueTEXTover" <%}%> style="background-repeat:no-repeat; cursor:pointer;"  onClick="setDate('<%=nowDay1%>');"><%=SnowDay1%></td>
        <td width="33%" align="center" <%if(uei.getDateFrom().equals(nowDay)){%> class="menu_blueOver" <%}else{%> class="menu_blueTEXTover" <%}%> style="cursor:pointer;"  onClick="setDate('<%=nowDay%>');"><%=SnowDay%></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="2" background="images/list_line03.gif"></td>
  </tr>
  <tr>
    <td height="38" background="images/list_bg03.gif"><table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="50%" align="center" class="textwhite" onClick="goPcVersion();" style="cursor:pointer;"><strong>PC버전</strong></td>
        <td width="50%" align="center" background="images/list_bar03.gif" onClick="logOut();" class="textwhite" style="cursor:pointer; background-repeat:no-repeat; font-weight: bold;">로그아웃</td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</form>
</html>


