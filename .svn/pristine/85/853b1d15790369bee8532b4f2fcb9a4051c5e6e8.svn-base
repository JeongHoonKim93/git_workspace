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
    ArrayList alGroup = smgr.getSiteGroup( uei.getMg_site() );
    
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
   	String nowDay = du.getCurrentDate();
   	String SnowDay = du.getCurrentDate("M월 dd일(E)");
   	String nowDay1 = du.addDay(nowDay,-1);
   	du.setDate(nowDay1);
   	String SnowDay1 = du.getDate("M월 dd일(E)");
   	String nowDay2 = du.addDay(nowDay,-2);
   	du.setDate(nowDay2);
   	String SnowDay2 = du.getDate("M월 dd일(E)");
   	
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
<title>삼성SDI</title>
<link rel="stylesheet" type="text/css" href="../../css/mobile/base.css">
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

		document.fSend.target='';
		document.fSend.submit();
	}

	function setXpYp(){
		var xpyp = document.getElementById('kXp').value;

		if(xpyp.length>0){
			document.getElementById('xp').value = xpyp.split(',')[0];
			document.getElementById('yp').value = xpyp.split(',')[1];
		} 
	}

	function nextPage(next){
		if(next!=0){
			document.getElementById('nowpage').value = next;
			search();
		}
	}
	function prePage(pre){
		if(pre!=0){
			document.getElementById('nowpage').value = pre;
			search();
		}
	}
	function setDate(date){
		document.getElementById('sDateFrom').value = date;
		document.getElementById('sDateTo').value = date;
		search();
	}

	function setSg_seq(sg_seq){
		document.getElementById('fSend').sg_seq.value = sg_seq;
		search();
	}
	
//-->	
</script>
</head>

<body onload="window.scrollTo(0,1);">
<form name="fSend" id="fSend" action="mobile_content_list.jsp" method="post">
<input type="hidden" name="xp" id="xp">
<input type="hidden" name="yp" id="yp">
<input type="hidden" name="zp" id="zp">
<input type="hidden" name="nowpage" id="nowpage" value="<%=iNowPage%>">
<input type="hidden" name="sDateFrom" id="sDateFrom" value="<%=uei.getDateFrom()%>">
<input type="hidden" name="sDateTo" id="sDateTo" value="<%=uei.getDateTo()%>">
<input type="hidden" name="keyName" id="keyName" value="">
<input type="hidden" name="sg_seq" id="sg_seq" value="">

<div id="top">
	<dl>
		<dt><img src="../../images/mobile/top_logo.gif" /></dt>
		<dd>
			<select name="kXp" id="kXp" onChange="search();">
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
		</dd>
	</dl>
</div>

<div id="top_menu">
<table border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
<%
	for( int i = 0; i < alGroup.size() ; i++ ) {
%>
	<col width="<%=100/alGroup.size()%>">
<%
	}
%>
	<tr>
<%
	for( int i = 0; i < alGroup.size() ; i++ ) {
		String sSelected = "";
		sgi = (siteGroupInfo)alGroup.get(i);
		if ( Integer.toString( sgi.getSg_seq() ).equals(uei.getSg_seq()) ) sSelected = "selected";
		
		if(uei.getSg_seq().split(",").length > 1){
			if(i == 0){
				out.print("<th style=\"cursor:pointer\" onclick=\"setSg_seq('"+sgi.getSg_seq()+"')\">"+sgi.getSd_name()+"</th>");
			}else{
				out.print("<td style=\"cursor:pointer\" onclick=\"setSg_seq('"+sgi.getSg_seq()+"')\">"+sgi.getSd_name()+"</td>");
			}
		}else{
			if(uei.getSg_seq().trim().equals(String.valueOf(sgi.getSg_seq()))){
				out.print("<th style=\"cursor:pointer\" onclick=\"setSg_seq('"+sgi.getSg_seq()+"')\">"+sgi.getSd_name()+"</th>");
			}else{
				out.print("<td style=\"cursor:pointer\" onclick=\"setSg_seq('"+sgi.getSg_seq()+"')\">"+sgi.getSd_name()+"</td>");
			}
		}
	}
%>
	</tr>
</table>
</div>

<div id="list_top">
	<dl>
		<dt><%=keyName%>선택</dt>
		<dd><b><%=iTotalCnt%></b>건</dd>
	</dl>
</div>

<div id="list_body">
<table border="0" cellpadding="0" cellspacing="0">
<%
	for( int i = 0 ; i < alData.size() ; i++ ){
		MetaBean mBean = (MetaBean) alData.get(i);
%>
	<tr>
		<th><a onClick="hrefPop('<%=mBean.getMd_url()%>');" href="javascript:void(0);"><%=su.nvl(su.cutString(mBean.getMd_title(),30,"..." ),"제목없음")%></a></th>
	</tr>
	<tr>
		<td>
		<%=mBean.getHighrightHtml(50,"0000CC")%>
		<dl>
			<dt><%=mBean.getMd_site_name()%></dt>
			<dd><%=mBean.getFormatMd_date("yy/MM/dd HH:mm")%></dd>
		</dl>
		</td>
	</tr>
<%
	}
%>  
</table>
</div>

<div id="paging">
<table border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="left" style="cursor:pointer;" onClick="prePage('<%=prePage%>');"><span class="prev">이전</span></td>
		<td style="cursor:pointer;" onClick="nextPage('<%=nextPage%>');"><span class="next">다음</span></td>
	</tr>
</table>
</div>

<div id="paging_date">
<table border="0" cellpadding="0" cellspacing="0">
	<tr>
		<%if(uei.getDateFrom().equals(nowDay2)){ %><th class="line" onClick="setDate('<%=nowDay2%>');"><%=SnowDay2%></th><%}else{ %><td class="line" onClick="setDate('<%=nowDay2%>');"><%=SnowDay2%></td><%}%>
		<%if(uei.getDateFrom().equals(nowDay1)){%><th class="line" onClick="setDate('<%=nowDay1%>');"><%=SnowDay1%></th><%}else{%><td class="line" onClick="setDate('<%=nowDay1%>');"><%=SnowDay1%></td><%}%>
		<%if(uei.getDateFrom().equals(nowDay)){%><th onClick="setDate('<%=nowDay%>');"><%=SnowDay%></th><%}else{%><td onClick="setDate('<%=nowDay%>');"><%=SnowDay%></td><%}%>
	</tr>
</table>
</div>

<div id="foot">
	<dl>
		<dt>Copyright ⓒ <b>RSN.</b></dt>
		<dd><img src="../../images/mobile/btn_foot_01.gif" onClick="goPcVersion();" style="cursor:pointer;"/></dd>
		<dd><img src="../../images/mobile/btn_foot_02.gif" onClick="logOut();" style="cursor:pointer;"/></dd>
	</dl>
</div>

</form>
</body>
</html>