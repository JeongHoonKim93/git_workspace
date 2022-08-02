<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.ArrayList
                 ,risk.util.Log
                 ,risk.util.StringUtil
                 ,risk.util.ParseRequest
                 ,risk.util.DateUtil
                 ,risk.search.MetaBean
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
<%@include file="../inc/mobile_sessioncheck.jsp"%>
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
    
    
    
    uei.setDateFrom( pr.getString("sDateFrom",sCurrDate ) ) ;
    uei.setDateTo  ( pr.getString("sDateTo"  ,sCurrDate   ) ) ;

    
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
    uei.setSg_seq( pr.getString("sg_seq","")  );
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
    ArrayList alGroup = smgr.getSiteGroup( uei.getMg_site() );
    
   	if(uei.getSg_seq().equals("")){
   		if(alGroup.size() > 0){
   			uei.setSg_seq(String.valueOf(((siteGroupInfo)alGroup.get(0)).getSg_seq()));
   		}
   	}
    
    
    
    //검색 키워드 그룹 대그룹, 중그룹
    ArrayList alKeyword =  smgr.getMobileKeyword( uei.getMg_xp(), "", "" );   

	//선택 키워드 그룹
	ArrayList seKeyword = null;
	
	if(uei.getK_xp().length()>0){
		seKeyword = smgr.getMobileKeyword( uei.getK_xp(), uei.getK_yp(), uei.getK_zp() );
	}
    
    ArrayList alData = null;
    
	if ( uei.getSearchMode().equals("ALLKEY") ) {
		if(uei.getK_xp().length()<1){
		System.out.println("uei.getK_xp().length() "+uei.getK_xp().length());
		        alData = smgr.getKeySearchList(
		                                       iNowPage                                     ,   //int    piNowpage ,
		                                       Integer.parseInt(uei.getSt_list_cnt())         ,   //int    piRowCnt  ,
		                                       uei.getMg_xp()                                ,   //String psXp      ,
		                                       uei.getK_yp()                                ,   //String psYp      ,
		                                       uei.getK_zp()                                ,   //String psZp      ,
		                                       uei.getSg_seq()                              ,   //String psSGseq   ,
		                                       uei.getS_seq()                              ,   //String psSDgsn   ,
		                                       uei.getDateFrom()         ,   //String psDateFrom,
		                                       uei.getDateTo()           ,   //String psDateTo  ,
		                                       uei.getKeyword()                             ,   //String psKeyWord ,
		                                       uei.getMd_type()                              ,   //String psType
		                                       uei.getOrder() + " " + uei.getOrderAlign()      //String psOrder
		                                       ,""
		                                       ,uei.getDelName());
		}else{
		        alData = smgr.getKeySearchList(
		                                       iNowPage                                     ,   //int    piNowpage ,
		                                       Integer.parseInt(uei.getSt_list_cnt())         ,   //int    piRowCnt  ,
		                                       uei.getK_xp()                                ,   //String psXp      ,
		                                       uei.getK_yp()                                ,   //String psYp      ,
		                                       uei.getK_zp()                                ,   //String psZp      ,
		                                       uei.getSg_seq()                              ,   //String psSGseq   ,
		                                       uei.getS_seq()                              ,   //String psSDgsn   ,
		                                       uei.getDateFrom()         ,   //String psDateFrom,
		                                       uei.getDateTo()       ,   //String psDateTo  ,
		                                       uei.getKeyword()                             ,   //String psKeyWord ,
		                                       uei.getMd_type()                              ,   //String psType
		                                       uei.getOrder() + " " + uei.getOrderAlign()      //String psOrder
		                                       ,""
		                                       ,uei.getDelName());
		}
    } else if ( uei.getSearchMode().equals("ALLDB") ) {
    	alData = smgr.getAllSearchList(
                                       iNowPage                                     ,   //int    piNowpage ,
                                       Integer.parseInt(uei.getSt_list_cnt())         ,   //int    piRowCnt  ,
                                       uei.getK_xp()                                ,   //String psXp      ,
                                       uei.getK_yp()                                ,   //String psYp      ,
                                       uei.getK_zp()                                ,   //String psZp      ,
                                       uei.getSg_seq()                              ,   //String psSGseq   ,
                                       uei.getS_seq()                              ,   //String psSDgsn   ,
                                       uei.getDateFrom()       ,   //String psDateFrom,
                                       uei.getDateTo()         ,   //String psDateTo  ,
                                       uei.getKeyword()                             ,   //String psKeyWord ,
                                       uei.getMd_type()                              ,   //String psType
                                       uei.getOrder() + " " + uei.getOrderAlign()   ,   //String psOrder
                                       uei.getSearchMode()						
                                        );
        

    }else if(uei.getSearchMode().equals("DELIDX")){
    	
    	alData = smgr.getKeySearchList(
                iNowPage                                     ,   //int    piNowpage ,
                Integer.parseInt(uei.getSt_list_cnt())         ,   //int    piRowCnt  ,
                uei.getMg_xp()                               ,   //String psXp      ,
                uei.getK_yp()                                ,   //String psYp      ,
                uei.getK_zp()                                ,   //String psZp      ,
                uei.getSg_seq()                              ,   //String psSGseq   ,
                uei.getS_seq()                              ,   //String psSDgsn   ,
                uei.getDateFrom()         ,   //String psDateFrom,
                uei.getDateTo()           ,   //String psDateTo  ,
                uei.getKeyword()                             ,   //String psKeyWord ,
                uei.getMd_type()                              ,   //String psType
                uei.getOrder() + " " + uei.getOrderAlign(),      //String psOrder
                uei.getSearchMode(),
    			uei.getDelName());
    	
    }


    int iPageCnt = smgr.mBeanPageCnt;
    int iDataCnt = smgr.mBeanDataCnt;
    int iTotalCnt= smgr.mBeanTotalCnt;

    int iTotalPage      = iTotalCnt / Integer.parseInt(uei.getSt_list_cnt());
    if ( ( iTotalCnt % Integer.parseInt(uei.getSt_list_cnt()) ) > 0 ) iTotalPage++;
    
    
   	//이슈데이터 등록 관련
   	IssueMgr isMgr = new IssueMgr();
   	IssueBean isBean = new IssueBean();
   	   	
   	IssueCodeMgr icMgr = IssueCodeMgr.getInstance();
   	IssueCodeBean icBean = new IssueCodeBean();
   	icMgr.init(1);
   	
   	ArrayList arrIssueBean = new ArrayList();
   	arrIssueBean = isMgr.getIssueList(0,0,"","","","","Y");
   	
   	int nextPage = 0;
   	int prePage = 0;
   	
   	if(iNowPage<iTotalPage){
   		nextPage = iNowPage+1;
   	}
   	
   	if(iNowPage>1){
   		prePage = iNowPage-1;
   	}
   	
   	//검색 날짜
   	/*
   	String nowDay = du.getCurrentDate();
   	String SnowDay = du.getCurrentDate("M월 dd일(E)");
   	String nowDay1 = du.addDay(nowDay,-1);
   	du.setDate(nowDay1);
   	String SnowDay1 = du.getDate("M월 dd일(E)");
   	String nowDay2 = du.addDay(nowDay,-2);
   	du.setDate(nowDay2);
   	String SnowDay2 = du.getDate("M월 dd일(E)");
   	*/
   	
   	String nowDay = uei.getDateTo();
   	String SnowDay = du.getDate(nowDay, "M월 dd일(E)");
   	String nowDay1 = du.addDay_v2(nowDay,-1);
   	String SnowDay1 = du.getDate(nowDay1,"M월 dd일(E)");
   	String nowDay2 = du.addDay_v2(nowDay,1);
   	String SnowDay2 = du.getDate(nowDay2,"M월 dd일(E)");
   	
   	
   	//선택 키워드
   	String keyName = pr.getString("keyName","전체 키워드 그룹");
   	
	keywordInfo kBean = null;
   	if(seKeyword!=null && seKeyword.size()>0){
        kBean = (keywordInfo)seKeyword.get(0);
   		
   		keyName = kBean.getK_Value();
   	}
   	System.out.println("uei.getSg_seq() : "+uei.getSg_seq());
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, target-densitydpi=medium-dpi" />
<title>RIS-K</title>
<link rel="stylesheet" type="text/css" href="../../css/mobile/basic.css">
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
	background-image: url(../../images/mobile/login_bg01.gif);
	background-color: #384b5a;
}

.select_he{height: 27px; width: 130px}     

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
		setXpYp();

		document.fSend.action='mobile_content_list.jsp';
		document.fSend.target='';
		document.fSend.submit();
	}

	function setXpYp(){
		
		//var xpyp = document.getElementById('kXp').value;
		var xpyp = document.fSend.kXp.value;

		if(xpyp.length>0){
			//document.getElementById('xp').value = xpyp.split(',')[0];
			//document.getElementById('yp').value = xpyp.split(',')[1];

			document.fSend.xp.value = xpyp.split(',')[0];
			document.fSend.yp.value = xpyp.split(',')[1];
			
		} 
	}

	function nextPage(next){
		if(next!=0){
			//document.getElementById('nowpage').value = next;
			document.fSend.nowpage.value = next;
			search();
		}
	}
	function prePage(pre){
		if(pre!=0){
			//document.getElementById('nowpage').value = pre;
			document.fSend.nowpage.value = pre;
			search();
		}
	}
	function setDate(date){
		//document.getElementById('sDateFrom').value = date;
		//document.getElementById('sDateTo').value = date;
		document.fSend.sDateFrom.value = date;
		document.fSend.sDateTo.value = date;
				
		search();
	}

	function setSg_seq(sg_seq){
		//document.getElementById('fSend').sg_seq.value = sg_seq;
		//document.getElementById('nowpage').value = 1;

		document.fSend.sg_seq.value = sg_seq;
		document.fSend.nowpage.value = 1;
		
		search();
	}

	function getMenu(menu){

		if(menu == 'search'){
			
			document.fSend.nowpage.value = '1';
			document.fSend.xp.value = '';
			document.fSend.yp.value = '';
			document.fSend.zp.value = '';
			document.fSend.sg_seq.value = '';
			document.fSend.kXp.value = '';
			document.fSend.sDateFrom.value = '';
			document.fSend.sDateTo.value = '';
			

			document.fSend.action = 'mobile_content_list.jsp';
			document.fSend.target = '';
			document.fSend.submit();	
		}else if(menu == 'issue'){
			
			document.issue.action = 'mobile_issue_list.jsp';
			document.issue.target = ''; 
			document.issue.submit();
		}else if(menu == 'report'){ 
			
			document.report.action = 'mobile_report_list.jsp';
			document.report.target = '';
			document.report.submit();
		}else if(menu == 'dashboard'){
			document.location.href = 'mobile_dashboard.jsp';
		} 
		
	}
	
//-->	
</script>
</head>

<body onload="window.scrollTo(0,1);" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<form name="issue" id="issue" action="mobile_issue_list.jsp" method="post">
<input type="hidden" name="nowpage" value="1">
<input type="hidden" name="typeCode4" value="">
<input type="hidden" name="totalPage" value="">
<input type="hidden" name="sDateFrom" value="">
<input type="hidden" name="sDateTo" value="">
</form>

<form name="report" id="report" action="mobile_report_list.jsp" method="post">
<input type="hidden" name="nowpage" value="1">
<input type="hidden" name="totalPage" value="">
<input type="hidden" name="ir_type" value="">
</form>

<form name="fSend" id="fSend" action="mobile_content_list.jsp" method="post">
<input type="hidden" name="xp" id="xp">
<input type="hidden" name="yp" id="yp">
<input type="hidden" name="zp" id="zp">
<input type="hidden" name="nowpage" id="nowpage" value="<%=iNowPage%>">
<input type="hidden" name="sDateFrom" id="sDateFrom" value="<%=uei.getDateFrom()%>">
<input type="hidden" name="sDateTo" id="sDateTo" value="<%=uei.getDateTo()%>">
<input type="hidden" name="keyName" id="keyName" value="">
<input type="hidden" name="sg_seq" id="sg_seq" value="">

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td background="../../images/mobile/bg.gif"><img src="../../images/mobile/list_top.gif"></td>
  </tr>
    
  <tr>
    <td height="35" background="../../images/mobile/menu_bg_03.gif"><table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0">
      <tr>
      	<td width="25%" align="center" background="../../images/mobile/menu_bg_02.gif" class="menu_blueTEXTover" style="background-repeat:no-repeat; cursor: pointer;" onclick="getMenu('dashboard')"><strong><img src="../../images/mobile/menu_04_off.gif" width="19" height="18" align="absmiddle"> 대시보드</strong></td>
        <td width="25%" align="center" background="../../images/mobile/menu_bg_01.gif" class="menu_blueOver" style="cursor: pointer;" onclick="getMenu('search')"><img src="../../images/mobile/menu_01_on.gif" width="22" height="22" align="absmiddle" ><strong> 정보검색</strong></td>
        <td width="25%" align="center" background="../../images/mobile/menu_bg_02.gif" class="menu_blueTEXTover" style="background-repeat:no-repeat; cursor: pointer;" onclick="getMenu('issue')"><img src="../../images/mobile/menu_02_off.gif" width="19" height="18" align="absmiddle" ><strong> 이슈관리</strong></td>
        <td width="25%" align="center" background="../../images/mobile/menu_bg_02.gif" class="menu_blueTEXTover" style="background-repeat:no-repeat; cursor: pointer;" onclick="getMenu('report')"><img src="../../images/mobile/menu_03_off.gif" width="18" height="20" align="absmiddle" > <strong>보고서</strong></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="37" bgcolor="#e4e4e4" style="padding:3px 13px 0px 13px"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><strong class="menu_gray">정보검색</strong> <img src="../../images/mobile/txt_line.gif" width="9" height="13" align="absmiddle"><strong class="menu_black"> <%=iTotalCnt%>건</strong></td>
        <td align="right">
        
        <select name="kXp" id="kXp" onChange="search();" class="select_he">
				<option value="">전체 키워드 그룹</option>
<%
	String k_val = "";
	String selected = "";
	for(int i=0; i<alKeyword.size(); i++){
		kBean = (keywordInfo)alKeyword.get(i);
		selected = "";
		k_val = "";
		if(!kBean.getK_Yp().equals("0")){
			k_val = "--"+kBean.getK_Value();
		}else{
			k_val = ""+kBean.getK_Value();
		}
		if(pr.getString("kXp","").equals(kBean.getK_Xp()+","+kBean.getK_Yp())){
			selected = "selected";
		}
%>
				<option <%=selected%> value="<%=kBean.getK_Xp()%>,<%=kBean.getK_Yp()%>"><%=k_val%></option>
<%
	}
%>
			</select> 

		</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="" bgcolor="#B9B9B9"></td>
  </tr>
  <tr>
    <td height="30" background="../../images/mobile/menu_bg_04.gif"><table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0">
      <tr>
      	
<%
      	
      	
	for( int i = 0; i < alGroup.size() ; i++ ) {
		
		//String sSelected = "";
		sgi = (siteGroupInfo)alGroup.get(i);
		
		if(sgi.getSg_seq() != 30 && sgi.getSg_seq() != 20){
		
			if(uei.getSg_seq().equals("")){
				if (i==0){
					out.print("<td onclick=\"setSg_seq('"+sgi.getSg_seq()+"')\" align=\"center\" background=\"../../images/mobile/menu_bg_05.gif\" class=\"menu_blueOver\" style=\"cursor:pointer; \">"+sgi.getSd_name()+"</td>");
				}else{
					out.print("<td onclick=\"setSg_seq('"+sgi.getSg_seq()+"')\" align=\"center\" background=\"../../images/mobile/menu_bg_06.gif\" class=\"menu_blueTEXTover\" style=\"background-repeat:no-repeat; cursor:pointer; \">"+sgi.getSd_name()+"</td>");	
				}	
			}else{
				if ( Integer.toString( sgi.getSg_seq() ).equals(uei.getSg_seq()) ){
					out.print("<td onclick=\"setSg_seq('"+sgi.getSg_seq()+"')\" align=\"center\" background=\"../../images/mobile/menu_bg_05.gif\" class=\"menu_blueOver\" style=\"cursor:pointer; \">"+sgi.getSd_name()+"</td>");
				}else{
					out.print("<td onclick=\"setSg_seq('"+sgi.getSg_seq()+"')\" align=\"center\" background=\"../../images/mobile/menu_bg_06.gif\" class=\"menu_blueTEXTover\" style=\"background-repeat:no-repeat; cursor:pointer; \">"+sgi.getSd_name()+"</td>");	
				}
			}
		}		
	}
%>
  
      </tr>
    </table></td>
  </tr>
  
  
<%
	for( int i = 0 ; i < alData.size() ; i++ ){
		MetaBean mBean = (MetaBean) alData.get(i);
%> 
  
  
  <tr>
    <td bgcolor="#FFFFFF" style="padding:8px 13px 8px 13px"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="23" class="menu_blue"><a onClick="hrefPop('<%=mBean.getMd_url()%>');" href="javascript:void(0);"><%=su.nvl(su.cutString(mBean.getMd_title(),30,"..." ),"제목없음")%></a></td>
      </tr>
      <tr>
        <td class="menu_gray"><%=mBean.getHighrightHtml(50,"0000CC")%></td>
      </tr>
      <tr>
        <td height="20"><span class="menu_red"><%=mBean.getMd_site_name()%></span> <span class="menu_gray02"><%=mBean.getFormatMd_date("yy/MM/dd HH:mm")%> </span></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="1" bgcolor="#e1e1e1" ></td>
  </tr>
<%
	}
%>  

  <tr>
    <td height="2" background="../../images/mobile/list_line01.gif" ></td>
  </tr>
  <tr>
    <td height="28" background="../../images/mobile/list_bg01.gif" bgcolor="#FFFFFF" ><table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="50%" align="center" class="textwhite"   style="cursor:pointer;" onClick="prePage('<%=prePage%>');"><strong class="textwhite02">&lt; 이전</strong></td>
        <td width="50%" align="center" background="../../images/mobile/list_bar01.gif"  style="cursor:pointer;" onClick="nextPage('<%=nextPage%>');" class="textwhite02" style="background-repeat:no-repeat; font-weight: bold;">다음 &gt;</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="2" background="../../images/mobile/list_line02.gif"></td>
  </tr>
  <tr>
    <td height="33" background="../../images/mobile/list_bg02.gif"><table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="33%" align="center" class="menu_blueOver" onClick="setDate('<%=nowDay1%>');" style="cursor: pointer;"><%=SnowDay1%></td>
        <td width="33%" align="center" onClick="setDate('<%=nowDay%>');" background="../../images/mobile/list_bar02.gif" class="menu_blueTEXTover" style="background-repeat:no-repeat; cursor: pointer;"><%=SnowDay%></td>
        <td width="33%" align="center" onClick="setDate('<%=nowDay2%>');" background="../../images/mobile/list_bar02.gif" class="menu_blueTEXTover" style="background-repeat:no-repeat; cursor: pointer; "><%=SnowDay2%></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="2" background="../../images/mobile/list_line03.gif"></td>
  </tr>
  <tr>
    <td height="38" background="../../images/mobile/list_bg03.gif"><table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="50%" align="center" class="textwhite" onClick="goPcVersion();" style="cursor: pointer;"><strong>PC버전</strong></td>
        <td width="50%" align="center" onClick="logOut();" background="../../images/mobile/list_bar03.gif" class="textwhite" style="background-repeat:no-repeat; font-weight: bold; cursor: pointer;">로그아웃</td>
      </tr>
    </table></td>
  </tr>
</table>






</form>
</body>
</html>