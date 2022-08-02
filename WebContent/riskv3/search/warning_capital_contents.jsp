<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.ArrayList
				 ,java.util.List
				 ,java.util.Map
				 ,java.util.HashMap
                 ,risk.util.Log
                 ,risk.util.StringUtil
                 ,risk.util.ParseRequest
                 ,risk.util.DateUtil
                 ,risk.search.MetaBean
                 ,risk.search.WarningMgr
                 ,risk.search.siteGroupInfo
                 ,risk.search.siteDataInfo
                 ,risk.search.userEnvMgr
                 ,risk.search.userEnvInfo
                 ,java.text.DecimalFormat
                 ,risk.issue.IssueCodeMgr
                 ,risk.issue.IssueCodeBean
                 ,risk.issue.IssueMgr
                 ,risk.issue.IssueBean
                 ,risk.issue.IssueTitleBean
                 ,risk.util.PageIndex
                 ,risk.admin.tier.TierSiteMgr
                 ,risk.admin.tier.TierSiteBean                 
                 ,risk.admin.aekeyword.StorageBean
                 ,risk.admin.aekeyword.StorageMgr
                 "
%>
<%@include file="../inc/sessioncheck.jsp" %>
<%

	//페이지에 사용할 변수 선언 부분
	String ipAddress = request.getRemoteAddr();
	boolean ipChk = false;
/* 	
	if(ipAddress.indexOf("1.215.131") > -1 || ipAddress.indexOf("127.0.0.1") > -1){
		ipChk = true;
	} */
	
	//페이지에 사용할 변수 선언 부분
	StringUtil      su = new StringUtil();
    ParseRequest    pr = new ParseRequest(request);
    DateUtil        du = new DateUtil();
    siteDataInfo   sdi = null;
    siteGroupInfo  sgi = null;
    WarningMgr    smgr = new WarningMgr();
    StorageMgr stomgr = new StorageMgr();
    DecimalFormat df = new DecimalFormat("###,###,###");
    DecimalFormat df_per = new DecimalFormat("###.##");
    pr.printParams();
    
try{    
    //사용자 기본 환경을 조회한다.
    //없으면  NCS기본 유저의 환경을 조회하여 가져온다.
    userEnvInfo uei = null;
    uei     = (userEnvInfo) session.getAttribute("ENV");

    String sCurrDate    = du.getCurrentDate();
    System.out.println(sCurrDate + "<<sCurrDate");
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
            uei.setDateFrom( sCurrDate ) ;
            uei.setDateTo  (  sCurrDate  ) ;
        }  
        System.out.println(uei.getDateFrom() + "<<getDateFrom");
        uei.setStime("00");
        uei.setEtime("23"); 
    }
    
	// 원문보기 권한 줄 ID
	//final String[] id_chk = {"devel", "jongwon", "cwjeong", "bae", "shbae", "jwpark", "hjjung", "shb"};
	
	//원문보기 권한 부여
	if(SS_M_ORGVIEW_USEYN.equals("Y")){
		ipChk = true;
	}
	
	if(uei.getStime()==null || uei.getStime().equals(""))  uei.setStime("00");
    if(uei.getEtime()==null || uei.getEtime().equals(""))  uei.setEtime("23");

	if ( request.getParameter("xp") != null ) uei.setK_xp( pr.getString("xp") );
      
    // 키워드그룹 일련번호
    if ( request.getParameter("yp") != null ) uei.setK_yp( pr.getString("yp") );

    // 키워드 일련번호
    if ( request.getParameter("zp") != null ) uei.setK_zp( pr.getString("zp") );

    if ( uei.getK_xp().equals("0") ) 
    uei.setK_xp("");
    if ( uei.getK_yp().equals("0") ) 
    uei.setK_yp("");
    if ( uei.getK_zp().equals("0") ) 
    uei.setK_zp("");
  

    // 검색단어    
    if( pr.getString("sKeyword","").equals(""))
       uei.setKeyword("");   
    else{
    	uei.setKeyword(su.Trim(request.getParameter("sKeyword")).replaceAll("[ ]{1,}|[ ]{1,}&\\?[ ]{1,}", "&").replaceAll("\\&\\&|\\&[ ]{1,}|[ ]{1,}&\\&","&").replaceAll("\\&\\||\\|\\&","|").replaceAll("\\&\\-|\\-\\&","-"));
    }
   	String keywordKind = pr.getString("sKeywordKind","2");
    String sDataRange   = pr.getString("");         // 검색 범위 ( 전체,전체,기사,게시,공지 )
    
    uei.setDateFrom( pr.getString("sDateFrom",uei.getDateFrom() ) ) ;
    uei.setDateTo  ( pr.getString("sDateTo"  ,uei.getDateTo()   ) ) ;
    uei.setStime( pr.getString("stime"  ,uei.getStime() ) ) ;
    uei.setEtime( pr.getString("etime"  ,uei.getEtime() ) ) ;
    
    if ( uei.getDateFrom()== null ) {
        if ( Integer.parseInt( uei.getSt_interval_day() ) > 1 ) {
            du.addDay( -1 * ( Integer.parseInt( uei.getSt_interval_day() )-1 ) );
            uei.setDateFrom( du.getDate() );
            uei.setDateTo  ( sCurrDate    ) ;
        } else {
            uei.setDateFrom( sCurrDate  ) ;
            uei.setDateTo  ( sCurrDate  ) ;
        }
        uei.setStime("00");
        uei.setEtime("23");
    }
    
    uei.setSt_interval_day( pr.getString("Period",uei.getSt_interval_day())) ;
    uei.setMd_type( pr.getString("type", uei.getMd_type() ) );
    uei.setSg_seq( pr.getString("sg_seq",uei.getSg_seq())  );
    
    System.out.println(uei.getSg_seq() + "uei.getSg_seq()");
    uei.setS_seq(pr.getString("sd_gsn", uei.getS_seq()));
    
    //전체 선택시 빈문자열로 대체
    if ( uei.getSg_seq().equals("") ) uei.setSg_seq("");
    if ( uei.getS_seq().equals("0") ) uei.setS_seq("");

    String sOrder       = pr.getString("sOrder","MD_DATE");
    String sOrderAlign  = pr.getString("sOrderAlign","DESC"); 
    
    uei.setOrder( pr.getString("sOrder","MD_DATE") );
    uei.setOrderAlign( pr.getString("sOrderAlign","DESC") );
    
    uei.setSt_list_cnt( pr.getString("rowcnt",uei.getSt_list_cnt()) );   
    int iNowPage        = pr.getInt("nowpage",1);
    
    String bookMarkYn = pr.getString("bookMarkYn","N");

    uei.setSt_menu( pr.getString("searchtype",uei.getSt_menu()) );   // 상세 검색창 표출 여부

 	//데이터 검색 모드 ALLKEY, ALLDB
    uei.setSearchMode(  pr.getString("searchmode", su.nvl(uei.getSearchMode(),"ALLKEY") ) );
    
    if(uei.getSearchMode().equals("DELIDX")){   	    	
    	uei.setDelName(pr.getString("del_mname",SS_M_NO));    	
    }else{
    	uei.setDelName(pr.getString("del_mname",SS_M_NO));
    }

    //타이머 설정 변수
    String sTimer       = "Y";
    uei.setSt_reload_time(pr.getString("interval", uei.getSt_reload_time() ));


    //지금까지 설정 내역을 세션에 저장
    session.removeAttribute("ENV");
    session.setAttribute("ENV",uei);
    
    //이슈등록 여부
    String issue_check = pr.getString("issue_check", "");
    
  	//댓글등록 여부
    String reply_check = pr.getString("reply_check", "");
  	
  	//보관함 이름
    String storageName = pr.getString("storageName","");

    //Uiix의 파일권한과 동일한 개념으로 간다.
    //기사 1. 게시 2. 공지 4.
    String sType1       = "";
    String sType2       = "";
    String sType3       = "";
    String sTypeAll     = "";
    if( uei.getMd_type().equals("1") ) {
        sType1  = "checked";
    } else if   ( uei.getMd_type().equals("2") ) {
        sType2  = "checked";
    } else if   ( uei.getMd_type().equals("4") ) {
        sType3  = "checked";
    } else if   ( uei.getMd_type().equals("3") ) {
        sType1  = "checked";
        sType2  = "checked";
    } else if   ( uei.getMd_type().equals("5") ) {
        sType1  = "checked";
        sType3  = "checked";
    } else if   ( uei.getMd_type().equals("6") ) {
        sType2  = "checked";
        sType3  = "checked";
    } else if   ( uei.getMd_type().equals("7") ) {
        sTypeAll= "checked";
    }
    
    /**
    * 필드별 정렬
    */
   	String sOrderImg    = "";
    String sOrderMark1 = "";
    String sOrderMark2 = "";
    String sOrderMark3 = "";
    String sOrderMark4 = "";
    String sOrderMark5 = "";
    String sOrderMark6 = "";
    String sOrderMark7 = "";

    if ( uei.getOrderAlign().equals("ASC" ) ) {
        sOrderImg = "▲";
    } else if( uei.getOrderAlign().equals("DESC" ) ) {
        sOrderImg = "▼";
    }   
    
    if(uei.getOrder()!=null){
	    if ( uei.getOrder().equals("MD_SITE_NAME") )             {
	        sOrderMark1 = sOrderImg;
	    } else if ( uei.getOrder().equals("MD_TITLE") )     {
	        sOrderMark2 = sOrderImg;
	    } else if ( uei.getOrder().equals("MD_SAME_COUNT") )   {
	        sOrderMark3 = sOrderImg;
	    } else if ( uei.getOrder().equals("REPLY_CNT") )  {
	        sOrderMark4 = sOrderImg;
	    } else if ( uei.getOrder().equals("MD_DATE") )      {
	        sOrderMark5 = sOrderImg;
	    }else if ( uei.getOrder().equals("I_DELDATE") )      {
	        sOrderMark6 = sOrderImg;
	    }else if ( uei.getOrder().equals("M_NAME") )      {
	        sOrderMark7 = sOrderImg;
	    }
    }

    //HTML Colspan
    String colspan = "";
    
    //검색 사이트 검색
    //ArrayList alGroup = smgr.getSiteGroup( uei.getMg_site() );
    /* 언론, 금융사이트 */
    ArrayList alGroup = smgr.getSiteGroup_warning( "93,108" );

    //그룹에 등록된 사이트 리스트
    ArrayList alSite = smgr.getSiteData("93,108");
    
    //String k_xp = smgr.getWarningKXP();
   
    // tier 정보
	String[] ts_type = pr.getStringArr("ts_type");
    
    String ts_types = "";
    if(ts_type != null && ts_type.length > 0){
    	
    	for(int i = 0; i < ts_type.length; i++){
    		if(ts_types.equals("")){
    			ts_types = ts_type[i];  
    		}else{
    			ts_types += "," + ts_type[i];
    		}
    	}
    	
    }
    
    if(ts_types.equals("")){
    
    	if(pr.getString("tsTypeCheck","0").equals("1")){
    		
    		uei.setTs_type(ts_types);	
    	}
    }else{
    	uei.setTs_type(ts_types);
    }
    ArrayList alData = null;
  	String keySearch = pr.getString("keySearch");
  	
  	
  	
  	//사이트 그룹 전체인지 확인
  	String[] arrSelected = {"93","108"};
	/* if(!uei.getSg_seq().equals("")){
		arrSelected = uei.getSg_seq().split(",");
	} */
  	
	for(int i = 0; i < arrSelected.length; i++) {
		System.out.println(arrSelected[i] + "<<arrSelected");
		System.out.println(arrSelected.length + "<<arrSelected");
	}
  	boolean siteGroupCheck = false;
  	int cnt = 0;
	for( int i = 0; i < alGroup.size() ; i++ ) {
		sgi = (siteGroupInfo)alGroup.get(i);
		if(arrSelected != null){
			for(int j = 0; j < arrSelected.length; j++){
				if(Integer.parseInt(arrSelected[j]) == sgi.getSg_seq()){
					//checked = "checked";
					cnt++;
					break;
				}
			}
		}

	}
	
	if(alGroup.size() == cnt){
		siteGroupCheck = true;
	}else{
		siteGroupCheck = false;
	}
  	
    

    
	if ( uei.getSearchMode().equals("ALLKEY") ) {
		colspan = "colspan=\"11\"";
		if(uei.getK_xp().length()<1){
		System.out.println("uei.getK_xp().length() "+uei.getK_xp().length());
				alData = smgr.getKeySearchList(
		                                       iNowPage                                     ,   //int    piNowpage ,
		                                       Integer.parseInt(uei.getSt_list_cnt())         ,   //int    piRowCnt  ,
		                                       uei.getK_xp()                                ,   //String psXp      ,
		                                       uei.getK_yp()                                ,   //String psYp      ,
		                                       uei.getK_zp()                                ,   //String psZp      ,
		                                       uei.getSg_seq()                              ,   //String psSGseq   ,
		                                       uei.getS_seq()                              ,   //String psSDgsn   ,
		                                       uei.getDateFrom()         ,   //String psDateFrom,
		                                       uei.getDateTo()           ,   //String psDateTo  ,
		                                       uei.getKeyword()                             ,   //String psKeyWord ,
		                                       uei.getMd_type()                              ,   //String psType
		                                       uei.getOrder() + " " + uei.getOrderAlign(),      //String psOrder
		                                       "",
		                                       uei.getDelName(),
		                                       bookMarkYn,
		                                       uei.getTs_type(),
		                                       uei.getM_seq(),
		                                       uei.getStime(),
		                                       uei.getEtime(),
		                                       "",
		                                       issue_check,
		                                       keywordKind,
		                                       reply_check,
		                                       siteGroupCheck,
		                                       storageName
		                                       );
		}else{
		        alData = smgr.getKeySearchList(
		                                       iNowPage                                     ,   //int    piNowpage ,
		                                       Integer.parseInt(uei.getSt_list_cnt())         ,   //int    piRowCnt  ,
		                                       //uei.getK_xp()                                ,   //String psXp      ,
		                                       uei.getK_xp(),
		                                       uei.getK_yp()                                ,   //String psYp      ,
		                                       uei.getK_zp()                                ,   //String psZp      ,
		                                       //"",
		                                       //"",
		                                       uei.getSg_seq()                              ,   //String psSGseq   ,
		                                       uei.getS_seq()                              ,   //String psSDgsn   ,
		                                       uei.getDateFrom()         ,   //String psDateFrom,
		                                       uei.getDateTo()       ,   //String psDateTo  ,
		                                       uei.getKeyword()                             ,   //String psKeyWord ,
		                                       uei.getMd_type()                              ,   //String psType
		                                       uei.getOrder() + " " + uei.getOrderAlign()    ,  //String psOrder
		                                       "",
		                                       uei.getDelName(),
		                                       bookMarkYn,
		                                       uei.getTs_type(),
		                                       uei.getM_seq(),
		                                       uei.getStime(),
		                                       uei.getEtime(),
		                                       "",
		                                       issue_check,
		                                       keywordKind,
		                                       reply_check,
		                                       siteGroupCheck,
		                                       storageName
		                                       );
		}
    }
	
	//북마크일 시 현재 페이지 설정
	String bookMarkNum  = "";
	if(bookMarkYn.equals("Y"))
	{	
		iNowPage = smgr.getBookMarkPage();
		bookMarkNum = smgr.getBookMarkNum();		
	}
	
	int iPageCnt = smgr.mBeanPageCnt;
    int iDataCnt = smgr.mBeanDataCnt;
    int iTotalCnt= smgr.mBeanTotalCnt;
    int iTotalCntSame= smgr.mBeanTotalCntSame;
    int iIssueTotalCnt= smgr.mBeanIssueTotalCnt;
    int iTempTotalCnt= smgr.mBeanTempTotalCnt;
    int iAllTotalCnt= smgr.mBeanAllTotalCnt;

    int iTotalPage      = iTotalCnt / Integer.parseInt(uei.getSt_list_cnt());
    if ( ( iTotalCnt % Integer.parseInt(uei.getSt_list_cnt()) ) > 0 ) iTotalPage++;
    
    
   	//이슈데이터 등록 관련
   	IssueMgr isMgr = new IssueMgr();
   	IssueBean isBean = new IssueBean();
   	   	
   	IssueCodeMgr icMgr = IssueCodeMgr.getInstance();
   	IssueCodeBean icBean = new IssueCodeBean();
   	icMgr.init(1);
   	
 /*   	ArrayList arrIssueBean = new ArrayList();
   	arrIssueBean = isMgr.getIssueList(0,0,"","","","","Y"); */
   	
   	
	String[] menu;
	int issue = 0;
	menu = uei.getMg_menu().split(",");
	for(int i=0; i < menu.length; i++){
		if(menu[i].equals("2")){
			issue = 1;
		}
	}
	
	/*
	String viewManager = "display:none;";
	
	if(uei.getMg_seq().equals("2") || uei.getMg_seq().equals("3") || uei.getMg_seq().equals("9") || uei.getMg_seq().equals("9")  ){ // 관리자(2), 정보관리자(3), 개발자(4)
		viewManager = "";
	}
	*/
	
	String viewManager = "";
	
	
	
	//출처별 건수 구하기(유사포함)
	ArrayList arr_sourceCnt = smgr.getArrSourceCnt();
	String strSourceCnts = "";
	String[] arScnt = null;
	for(int i =0; i <arr_sourceCnt.size(); i++){
		arScnt = ((String)arr_sourceCnt.get(i)).split(",");
		if(i!=0 && (i+1)==(arr_sourceCnt.size())){
			strSourceCnts = "" +  arScnt[0] + ":<strong>"  + df.format(Integer.parseInt(arScnt[1])) + "</strong> 건, "+strSourceCnts;
		}else{
			if(strSourceCnts.equals("")){
				strSourceCnts =  arScnt[0] + ":<strong>" + df.format(Integer.parseInt(arScnt[1])) + "</strong> 건"; 	
			}else{
				strSourceCnts += ", " +  arScnt[0] + ":<strong>"  + df.format(Integer.parseInt(arScnt[1])) + "</strong> 건";
			}
		}
	}
	
	
	// 버튼 숨기기~~
	String Jnone = "";
	/* if(uei.getMg_seq().equals("2") || uei.getMg_seq().equals("3") || uei.getMg_seq().equals("9")){
		Jnone = "";
	}else{
		Jnone = "";		
	} */
	
	//티어 정보 가져오기~
	TierSiteMgr tiermng = new TierSiteMgr();
	List tierGroupList = tiermng.getTierGroupList();
	
	//키워드 수집타입 정보
    String mdseq = "";
    if(alData.size() > 0){
    	for(int i = 0; i < alData.size(); i++){
    		MetaBean mb = (MetaBean)alData.get(i);
    		if(mdseq.equals("")){
    			mdseq = String.valueOf(mb.getMd_seq());
    		}else{
    			mdseq += ","+String.valueOf(mb.getMd_seq());
    		}
    	}
    }
    ArrayList keywordSearchInfo = new ArrayList();
    if(!mdseq.equals("")){
    	keywordSearchInfo = smgr.getKeywordSearchInfo(mdseq, uei.getK_xp() , uei.getK_yp(), uei.getK_zp());
    } 

    
    //chart처리
   String s_chart = pr.getString("s_chart", uei.getS_Chart());
   if(s_chart != null && !s_chart.equals("")){
   	uei.setS_Chart(s_chart);
   }else{
   	uei.setS_Chart("1");
   }
	
   String stime = pr.getString("stime", uei.getStime());
   if(stime != null && !stime.equals("")){
   	uei.setStime(stime);
   }else{
	   stime = "00:00";
   	uei.setStime(stime);
   }
   String etime = pr.getString("etime", uei.getEtime());
   if(etime != null && !etime.equals("")){
   	uei.setEtime(etime);
   }else{
	   etime = "23:59";
   	uei.setEtime(etime);
   } 
   
   //차트에서 선택된 날짜 값
   String chartDate = pr.getString("chartDate","");
   if(!chartDate.equals("")){
   	System.out.println("chartDate : "+chartDate);
   	//uei.setDateFrom(chartDate);
   	//uei.setDateTo(chartDate);
   	uei.setStime(stime);
   	uei.setEtime(etime);
   }
   
   //chart data
   String[] chartData = null;
   String sg_seq = "93,108";
   //chartData = smgr.getChartData(sCurrDate, uei.getK_xp(), uei.getK_yp(), uei.getK_zp(), uei.getSg_seq(), uei.getMg_seq()); 
   chartData = smgr.getChartData(sCurrDate, uei.getK_xp(), uei.getK_yp(), uei.getK_zp(), sg_seq, uei.getMg_seq()); 
   
%>

<html>
<head>
<title><%=SS_TITLE%></title>
<link rel="stylesheet" href="../../css/base.css" type="text/css">
<style type="text/css">
	iframe.hide { border:0px solid red; position:absolute; top:0px; left:0px; z-index:-99; width:148px; height:150px; filter: alpha(opacity=0);}
	.contextMenuBody{ height:300px; overflow :auto; }

	.gage_loading{position:absolute;top:200px;left:420px;margin:0 0 0 -97px;border:1px solid #c0c0c0;border-radius:10px;background:#ffffff;overflow:hidden}
	.gage_loader{display:none;position:absolute;top:200px;left:420px;width:196px;height:170px;margin:0 0 0 -97px;border:1px solid #c0c0c0;border-radius:10px;background:#ffffff;overflow:hidden}
	.gage_loader > .gage{display:block;width:115px;height:15px;margin:0px auto 8px;background:url(../../images/search/loading_01.gif) no-repeat}
	.gage_loader > span{display:block;color:#363838;font-size:15px;font-weight:bold;text-align:center}
</style>
<link rel="stylesheet" type="text/css" href="../../axisj/ui/arongi/page.css" />
<link rel="stylesheet" type="text/css" href="../../axisj/ui/arongi/AXJ.min.css"/>
<script type="text/javascript" src="../../axisj/jquery/jquery.min.js"></script>
<script type="text/javascript" src="../../axisj/dist/AXJ.min.js"></script>
<%--   
<link href="../../css/ui-lightness/jquery-ui-1.9.2.custom.css" rel="stylesheet">
<script src="<%=SS_URL%>js/jquery-1.8.3.js"></script>
<script src="<%=SS_URL%>js/jquery-ui-1.9.2.custom.js"></script>
 --%>
<%-- 
<script src="<%=SS_URL%>js/jquery.js" type="text/javascript"></script>
--%> 
<script src="../../js/ajax.js" type="text/javascript"></script>
<script src="../../js/popup.js" type="text/javascript"></script>
<script src="../../js/Calendar.js" type="text/javascript"></script>
<script src="../../js/timer.js" type="text/javascript"></script>
<script src="../../js/input_date.js" type="text/javascript"></script>
<script src="../../js/makeChart.js" type="text/javascript"></script>
<script src="../../js/highcharts.js" type="text/javascript"></script>
<script language="JavaScript" type="text/JavaScript">
<!--
	
	var selecedOrder = "<%=uei.getOrder()%>";
    var click = "";
    var sCurrDate = '<%=sCurrDate%>';
    var num = '<%=alData.size()%>';
    
    
    //차트처리
	
	var category = [<%=chartData[0]%>];
	var data = [<%=chartData[1]%>];
	var selectDate = '<%=chartDate%>';
	
	
    $(window).scroll(function(){
    	fnObj.pageStart();
    });
    
     //AXISJ 바인딩
   var myProgress = new AXProgress();
	var fnObj = {
		pageStart: function(){
	
			//본문요약보기 TOOLTIP
			/* for(var i = 0; i < num; i++){
				$("#img" + i).bindTooltip({direction:"bottom", width:300}); //{direction:"[auto|top|bottom]"}
    	  	} */
		},
	    unbindInput: function () {
	        
	    },
	    bindInput: function () {
	        
	    }
	};
	jQuery(document).ready(fnObj.pageStart.delay(0.1));
 
    /**
    * 검색 SUBMIT
    */
    function doSubmit(){
    	
    	var imgObj = document.getElementById('sending');
		imgObj.style.display = '';
		

    
    	var f = document.getElementById('fSend');

        var typeValue   = 0;

        if(f.chkGisa.checked == false && f.chkGesi.checked == false && f.chkGongji.checked == false && f.chkAll.checked == false){
			f.chkAll.checked = true;
        }

        if(f.chkGisa.checked == true)typeValue += 1;
        if(f.chkGesi.checked == true)typeValue += 2;
        if(f.chkGongji.checked == true)typeValue += 4;
        if(f.chkAll.checked == true)typeValue = 7;

        f.type.value = typeValue;
    	
		if((f.sOrder.value == selecedOrder) && (click == "TRUE")){
			if(f.sOrderAlign.value == "ASC"){
				f.sOrderAlign.value = "DESC";
			}else{
				f.sOrderAlign.value = "ASC";
			}
		}else{
			f.sOrder.value = selecedOrder;
		}
		f.sg_seq.value = chkSiteGroup();
		f.submit();
    }


    /**
    * 정보유형
    */
    function checkAll() {
    	var f = document.getElementById('fSend');

        if(f.chkAll.checked == true){
			f.chkGisa.checked = false;
			f.chkGesi.checked = false;
			f.chkGongji.checked = false;
        }
		doSubmit();
    }
    function checkEtc() {
    	var f = document.getElementById('fSend');
    	
        if(f.chkGisa.checked == true && f.chkGesi.checked == true && f.chkGongji.checked == true){
            f.chkAll.checked = true;
            f.chkGisa.checked = false;
            f.chkGesi.checked = false;
            f.chkGongji.checked = false;
		}else{
            f.chkAll.checked = false;
        }
		doSubmit();
    }


    /**
    * 기사 삭제 관련
    */
    function idxProcess(mode) {
    	var f = document.getElementById('fSend');
		var typeValue   = 0;
		var subMode = f.subMode.value;
		
        frm = document.getElementById('fCheck');
        var selectedList = "";
        
        var first = 0;
        
        if(mode == 'truncate'){
        	 if ( !confirm("선택된 목록을 삭제 하시겠습니까?" ) ) {
		            return;
		     }        
        }else if(mode=='revert'){            		        
			if ( !confirm("선택된 목록을 복원 하시겠습니까?" ) ) {
	            return;
	        }		       
		}else if(mode=='del'){
			 if ( !confirm("선택된 목록을 영구 삭제 하시겠습니까?" ) ) {
		            return;
		     }			
		}

        if(frm.chkData){
			if(frm.chkData.length>1){					
			 	for(var i=0; i<frm.chkData.length; i++)
			 	{
				 	if(frm.chkData[i].checked){
			 			selectedList =  selectedList == '' ? frm.chkData[i].value : selectedList+','+frm.chkData[i].value;
				 	}
			 	}
			}else{
				selectedList = frm.chkData.value;
			}
		}		
        
        f.SaveList.value = selectedList;

        f.action = "idx_prc.jsp?idxMode="+mode+ "&subMode=" + subMode;
        f.target = 'if_samelist';
        f.submit();
        
		f.action = 'warning_main_contents.jsp';
		f.target = '';
		f.submit();
    }
    

    /**
    * 페이지처리
    */
    function pageClick(paramUrl){

    	var f = document.getElementById('fSend');
        f.action = "warning_main_contents.jsp" + paramUrl;
        doSubmit();
    }

    /**
    * 체크된 사이트 코드 세팅
    */
	function chkSiteGroup(){
		var f = document.getElementById('fSend');
		var seqs = '';
		var obj = f.sltSiteGroup;
			for(var i =0; i<obj.length; i++){
				if(obj[i].checked){
					seqs += seqs == '' ?  obj[i].value : ',' + obj[i].value;
				}
			}			
		return seqs;
	}
    
    /**
    * 사이트로 정렬
    */
    function setSiteData(sg_seq, sd_gsn){
    	var f = document.getElementById('fSend');
        f.sg_seq.value = sg_seq;
        f.sd_gsn.value = sd_gsn;
        doSubmit();
    }

    /**
    * 휴지통 삭제자 정렬
    */
    function setDelname(delname){
    	var f = document.getElementById('fSend');
        f.del_mname.value = delname;
        doSubmit();
    }

    /**
    * 검색
    */
    function doSearch(){
    	var sDateFrom = document.getElementById('fSend').sDateFrom.value;
    	var sDateTo = document.getElementById('fSend').sDateTo.value;
    	if(check_date_diff(sDateFrom, sDateTo)){
    		document.fSend.action = 'warning_main_contents.jsp';
    		document.fSend.target = '';
        	document.fSend.nowpage.value = '1';
        	doSubmit();
    	}
    }
    
    
    function check_date_diff(sDatePram, eDatePram){
    	var fixSearchRange = 7;
    	var diff_date = getDateDiff(sDatePram, eDatePram);
    	if(diff_date > fixSearchRange){
    		alert("※검색 가능 최대 기간은 일주일(7일)입니다.");
    		return false;
    	}else{
    		return true;
    	} 
    }
    
    function getDateDiff(sDate, eDate){
    	var fromDate = sDate;
    	var toDate = eDate;
    	
    	var fromDateArry = fromDate.split('-');
    	var toDateArry = toDate.split('-');
    	
    	var startDate = new Date(fromDateArry[0], Number(fromDateArry[1])-1, fromDateArry[2]);
    	var endDate = new Date(toDateArry[0], Number(toDateArry[1])-1, toDateArry[2]);
    	var betweenDay = (endDate.getTime() - startDate.getTime())/1000/60/60/24;
    	
    	return betweenDay;
    }
    
    

    /**
    * 헤드라인 이벤트
    */
    function show_me(i){
		div_show.innerHTML = eval('div'+i).innerHTML;
    	div_show.style.top = event.y + document.body.scrollTop + 15;
    	div_show.style.display='';
    }
    function close_me(i){
    	div_show.style.display='none';
    }

    /**
    * 유사기사 헤드라인 이벤트
    */
    function showSamelist(i){
		div_show.innerHTML = eval('sameContent'+i).innerHTML;
    	div_show.style.top = event.y + document.body.scrollTop + 15;
    	div_show.style.display='';
    }
    function closeSamelist(i){
    	div_show.style.display='none';
    }

    /**
    * 검색기간
    */
    function setDateTerm( day ) {
        var f = document.getElementById('fSend');
        f.sDateFrom.value   = getdate( day );
        f.sDateTo.value     = sCurrDate;
        f.Period.value      = day + 1;
    }

	function getdate(day){
		var newdate = new Date();
		var resultDateTime;
		var tempDateTime = 0;
		
		if(tempDateTime==0)
		{
			tempDateTime = newdate.getTime();
		}  
              
		resultDateTime = tempDateTime - ( day * 24 * 60 * 60 * 1000);
        newdate.setTime(resultDateTime);

        var year;
        var month;
        var day;
		var result_date;
		year = newdate.getFullYear();
		month = newdate.getMonth()+1;
		day = newdate.getDate();
		if(month < 10){
			month = '0'+month;
		}
		if(day < 10){
			day = '0'+day;
		}
        result_date = year + '-' + month + '-' + day;
        return result_date;
	}

    /**
    * 필드정렬
    */
    function setOrder(order){
        selecedOrder = order;
        click = "TRUE";
        doSubmit();
    }

    /**
    * 기사 전체 체크
    */
    function reverseAll( chked ) {
        var frm = document.getElementById('fCheck');
        var first = 0;
        for (var i = 0; i < frm.elements.length; i++) {
            var e = frm.elements[i];
            //중요 체크박스는 해당x
            if ( e.type == "checkbox" && e.name != "chkData_important" && e.name != "sameChk_important" ) {
                e.checked = chked;
            }
        }
    }

    /**
    * 유사기사 리스트 보기
    */
    var pre_SameList = '';
    function openSameList(md_pseq, md_seq,searchMode){
    	
    	var target = "issue_menu_icon"+md_seq;
        var sameLayer = document.getElementById('SameList_' + md_pseq);
		
    	if(pre_SameList == sameLayer && pre_SameList.style.display==''){
    		pre_SameList.style.display = 'none';    		
    		return;
    	}

    	if(pre_SameList){
    		pre_SameList.style.display = 'none';
    	}
    	
    	var issue_Check = "";
    	if($("#"+target).attr("src") == "../../images/search/btn_manage_on.gif"){
    		issue_Check = "Y";	
    	}else{
    		issue_Check = "N";
    	}
    	
    	var subMode = 'warning_new';
    	
    	pre_SameList = sameLayer;
    	sameLayer.innerHTML = '로딩중...';
    	if_samelist.location.href = "inc_same_list.jsp?md_pseq=" + md_pseq + "&md_seq=" + md_seq + "&searchMode=" + searchMode+ "&issue_Check="+issue_Check+ "&subMode="+subMode;
    	sameLayer.style.display = '';
    }
    
    function openSameList_reShow(issue_Check, md_pseq, md_seq,searchMode){
    	
        var sameLayer = document.getElementById('SameList_' + md_pseq);
        var subMode = 'warning_new';
    	sameLayer.innerHTML = '로딩중...';
    	if_samelist.location.href = "inc_same_list.jsp?md_pseq=" + md_pseq + "&md_seq=" + md_seq + "&searchMode=" + searchMode+ "&issue_Check="+issue_Check+ "&subMode="+subMode;
    	sameLayer.style.display = '';
    }
    
    
    function fillSameList(no){
    	var ly = document.getElementById('SameList_'+no);    	
	    ly.innerHTML = if_samelist.zzFilter.innerHTML;
    }

	/**
	* 이슈등록 팝업 열기
	*/
	function send_issue(mode, md_seq){
		//close_menu();
		var f = document.getElementById('fCheck');	
		document.send_mb.md_seqs.value = "";	

		if(mode=='insert'){
			document.send_mb.mode.value = mode;
			document.send_mb.md_seq.value = md_seq;		
        	popup.openByPost('send_mb','../issue/pop_issue_data_form.jsp',1400,800,false,true,false,'send_issue');
        	
		}else if(mode=='update'){
			document.send_mb.mode.value = mode;
			document.send_mb.md_seq.value = md_seq;	
        	popup.openByPost('send_mb','../issue/pop_issue_data_form.jsp',1400,800,false,true,false,'send_issue');
		
		}else if(mode=='multi'){		
			document.send_mb.mode.value = mode;	
			if(f.chkData){
				if(f.chkData.length>1){					
				 	for(var i=0; i<f.chkData.length; i++)
				 	{
					 	if(f.chkData[i].checked){
				 			document.send_mb.md_seqs.value =  document.send_mb.md_seqs.value == '' ? f.chkData[i].value : document.send_mb.md_seqs.value+','+f.chkData[i].value;
					 	}
				 	}
				}else{
					//document.send_mb.md_seqs.value = f.chkData.value;
					document.send_mb.md_seqs.value = document.send_mb.md_seqs.value == '' ?  f.chkData.value : document.send_mb.md_seqs.value+','+f.chkData.value;
				}
			}
			
			if(f.sameChk){
				if(f.sameChk.length>1){					
				 	for(var i=0; i<f.sameChk.length; i++)
				 	{
					 	if(f.sameChk[i].checked){
				 			document.send_mb.md_seqs.value =  document.send_mb.md_seqs.value == '' ? f.sameChk[i].value : document.send_mb.md_seqs.value+','+f.sameChk[i].value;
					 	}
				 	}
				}else{
					document.send_mb.md_seqs.value = document.send_mb.md_seqs.value == '' ?  f.sameChk.value : document.send_mb.md_seqs.value+','+f.sameChk.value;
				}
			}
			if(document.send_mb.md_seqs.value==''){alert('정보를 선택해주세요.'); return;}
			popup.openByPost('send_mb','../issue/pop_issue_data_form.jsp',718,800,false,true,false,'send_issue');
		} else if(mode=='news'){
			//기사공유 버튼 추가 2018.2.13 고기범
			//대표 기사 체크
			var a = document.getElementsByName('chkData');
			//대표 기사 중요
			//var b = document.getElementsByName('chkData_important');
			//유사 기사 체크
			var c = document.getElementsByName('sameChk');
			//유사 기사 중요
			//var d = document.getElementsByName('sameChk_important');
			var chkedseq = '';
			var cnt = 0;
			
			for(var i=0; i<a.length; i++){
				//if(a[i].checked == true && b[i].checked == true){
				if(a[i].checked == true){	
					if(a[i].value!=""){
		 				if(chkedseq == ''){
		 					chkedseq = a[i].value;
		 				}else{
		 					chkedseq = chkedseq+','+a[i].value;
		 				}
		 				cnt++;
		 			}
				}
			}
			
			for(var i=0; i<c.length; i++){
				//if(c[i].checked == true && d[i].checked == true){
				if(c[i].checked == true){	
					if(c[i].value!=""){
		 				if(chkedseq == ''){
		 					chkedseq = c[i].value;
		 				}else{
		 					chkedseq = chkedseq+','+c[i].value;
		 				}
		 				cnt++;
		 			}
				}
			}
			
			var f = document.send_important_mail;
			if(chkedseq == '') {
				//alert('선택된 정보가 없습니다.\n앞부분 체크와 중요 체크가 모두 선택되어야 합니다.'); return;
				alert('선택된 정보가 없습니다.'); return;
			}else {
				if(confirm(cnt+'건의 기사를 공유합니다.')) {				
					//f.mode.value = 'mail';
					f.check_no.value = chkedseq;
					//f.ir_type.value = 'E';
					window.open("about:blank",'importantNews', 'width=850,height=700,scrollbars=yes');
		       		f.action = 'pop_important_report_form.jsp';
		       		f.target='importantNews';
					f.submit();
				}
			}
        	
		}
		
	}
	
	/**
	* URL열기
	*/
	var chkPop = 1;
	function hrefPop(url, m_seq, md_seq){
		//window.open(url,'hrefPop'+chkPop,'');
		window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop'+chkPop,'');
		chkPop++;
		
		// 열어본 페이지는 DB저장 및 색상변경
		send_comfirm.mode.value = 'insert';
		send_comfirm.m_seq.value = m_seq;
		send_comfirm.md_seq.value = md_seq;
		send_comfirm.target = 'proceeFrame';
		send_comfirm.action = 'search_comfirm_prc.jsp';
		send_comfirm.submit();	
	}

	function listClear(m_seq){
		var f = document.getElementById('fCheck');

		var selectedMdSeq = '';

		if(f.chkData){
			if(f.chkData.length){					
			 	for(var i=0; i<f.chkData.length; i++)
			 	{
				 	if(f.chkData[i].checked){
				 		if(selectedMdSeq == ''){
				 			selectedMdSeq = f.chkData[i].value;
				 		}else{
				 			selectedMdSeq += "," +  f.chkData[i].value;
				 		}
				 	}
			 	}
			}else{
				selectedMdSeq = f.chkData.value;
			}
		}
	
		if(selectedMdSeq == ''){
			alert("선택된 기사가 없습니다.");
			return;
		}
		
		send_comfirm.md_seqs.value = selectedMdSeq;
		send_comfirm.mode.value = 'delete';	
		send_comfirm.m_seq.value = m_seq;
		send_comfirm.target = 'proceeFrame';
		send_comfirm.action = 'search_comfirm_prc.jsp';
		send_comfirm.submit();
	}
	
    /**
	* 이슈 타이틀 변경
	*/
    function changeIssueTitle(){
    	
		ajax.post('selectbox_issue_title.jsp','send_mb','td_it_title');
	}

	
	/**
	* 북마크 저장 관련
	*/
	function saveBookMark(){
		var count = 0;
		var obj = document.getElementById('fCheck');
		for (var i = 0; i <obj.length; i++) {
			if(obj[i].checked){send_bookmark.bookMarkSeq.value = obj[i].value; count++;}        
  	    }
  	    if(count>1){alert('하나 이상 선택하셨습니다.'); return;}
		if(send_bookmark.bookMarkSeq.value=='') {alert('정보를 선택을 해주세요.'); return;}  	    
		send_bookmark.target = 'proceeFrame';
		send_bookmark.action = 'search_bookmark_prc.jsp';
		send_bookmark.submit();			  	
	}

	/**
	* 북마크 검색 관련
	*/
    function doBookMarkSearch() {
    	var f = document.getElementById('fSend');
        f.bookMarkYn.value = 'Y';
        doSubmit();
    }

	/**
	* 검색유형 전체 체크
	*/	
	function chkSiteGroupAll(chk){
		var group = document.getElementsByName('sltSiteGroup');
		if(group){
			if(group.length){
				for(var i = 0; i < group.length; i++){
					group[i].checked = chk;
				}
			}
		} 
	}


	function timerStart(){
		var f = document.getElementById('fSend');
		if(f.timer.value == 'Y'){
			setTimer(<%=uei.getSt_reload_time()%>);
		}
	}
	window.onload = timerStart;

	function actionLayer(obj){
		if(obj.src.indexOf("close") > -1){

			/*
			for(var i = 1; i < 9; i++){
				document.getElementById('hide'+i).style.display = 'none';
			}
			*/
			document.getElementById('hide1').style.display = 'none';
			document.getElementById('hide2').style.display = 'none';
			obj.src = '../../images/search/btn_searchbox_open.gif';
			document.getElementById('fSend').searchtype.value = "0";
		}else{
			/*
			for(var i = 1; i < 9; i++){
				document.getElementById('hide'+i).style.display = '';
			}
			*/
			document.getElementById('hide1').style.display = '';
			document.getElementById('hide2').style.display = '';
			obj.src = '../../images/search/btn_searchbox_close.gif';
			document.getElementById('fSend').searchtype.value = "1";
		}
	}

	/*
	function goExcelTo(){

 		var f = document.fSend;
 		
		f.action = 'search_download_excel.jsp';
		f.target = 'proceeFrame';
		f.submit();
		//f.action = 'issue_list.jsp';
		//f.target = 'proceeFrame';
 	}
 	*/

	function listAlter(md_seq, md_pseq){
		var f = document.fSend;
		var subMode = 'warning_new';
		f.action = "alterList.jsp?md_seq="+md_seq+ "&md_pseq=" + md_pseq +"&nowpage=" + f.nowpage.value+"&subMode=" + subMode;
        f.target = 'if_samelist';
        f.submit();
	}

	function originalView(md_seq, m_seq){
		var url = '';
		var subMode = 'warning_new';
		url = "originalView.jsp?md_seq="+md_seq+ "&subMode=" + subMode;	
		window.open(url, "originalPop", "width=708, height=672, scrollbars=yes");		
		
		// 열어본 페이지는 DB저장 및 색상변경
		send_comfirm.mode.value = 'insert';
		send_comfirm.m_seq.value = m_seq;
		send_comfirm.md_seq.value = md_seq;
		send_comfirm.subMode.value = subMode;
		send_comfirm.target = 'proceeFrame';
		send_comfirm.action = 'search_comfirm_prc.jsp';
		send_comfirm.submit();	
	}

	var chkOriginal = 1;
	function portalSearch(s_seq, md_title, s_name){
		if(s_seq == '3555'){
			//네이버까페
			//url = "http://section.cafe.naver.com/CombinationSearch.nhn?query=" + md_title;
			//url = "https://section.cafe.naver.com/cafe-home/search/articles?query=\"" +   md_title+"\"";
			url = "https://cafe.naver.com/ca-fe/home/search/articles?pr=2&se=1&et="+ md_title;
			window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop'+chkOriginal,'');
			//window.open(url,'hrefPop'+chkOriginal,'');
		}else if(s_seq == '4943'){
			//다음까페
			url = "http://search.daum.net/search?w=cafe&nil_search=btn&enc=utf8&ASearchType=1&lpp=10&rlang=0&q=" + md_title;
			//window.open(url,'hrefPop'+chkOriginal,'');
			window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop'+chkOriginal,'');
			//네이버카페    			
		}else if(s_name.includes('네이버카페')){
			url = "https://m.cafe.naver.com/ca-fe/home/search/articles?q="+ encodeURIComponent(md_title);
			
			window.open('http://hub.buzzms.co.kr?url=' + url,'hrefPop'+chkOriginal,'');    				
		
		//다음카페    			
		}else if(s_name.includes('다음카페')){
			url = "http://m.cafe.daum.net/_search/article?query=" + md_title +"&r=https%3A%2F%2Fm.cafe.daum.net%2F";
			//url = "http://search.daum.net/search?w=cafe&nil_search=btn&enc=utf8&ASearchType=1&lpp=10&rlang=0&q=" + md_title;
			window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop'+chkOriginal,'');
		}

		chkOriginal ++;
	}

	function sendMail(){
		var count = 0;
		var obj = document.getElementById('fCheck');
		for (var i = 0; i <obj.length; i++) {
			if(obj[i].checked){send_mail.md_seqs.value = obj[i].value; count++;}        
  	    }
  	    if(count>1){alert('하나 이상 선택할수 없습니다.'); return;}
		if(send_mail.md_seqs.value=='') {alert('정보를 선택을 해주세요.'); return;}  	    
		send_mail.target = 'proceeFrame';
		send_mail.action = 'pop_mail_send.jsp';
		send_mail.submit();
	}

	function saveExcel() {

		var f = document.fSend;
        f.action = "warning_download_excel.jsp";
        f.target = 'proceeFrame';
        f.submit();
		//f.action = 'search_main_contents.jsp';
		//f.target = '';

    }

    function ts_type_check(){
    	fSend.tsTypeCheck.value = '1';
    }
    
    //유사 데이터 전체선택, 해제 
    function AllCheck(md_Pseq){
    	
    	var flag = $("#allChk").attr('atr');    	
    	var target = "SameList_"+md_Pseq;
    	
    	$("#"+target).find("[name=sameChk]").each(function(){
			
			if(flag == 'C'){
				$(this).attr('checked', true);
				$("#allChk").attr('atr', 'U');
			}else{
				$(this).attr('checked', false);
				$("#allChk").attr('atr', 'C');
			}
						
		});
	}
    
    function search_v2(){
    	
    	doSearch();
    	
    	/*
    	if($("input[name=sKeyword]").val().trim() == ""){
    		
    		document.fSend.keySearch.value =  "N";
    		doSearch();
    	}else{
    		
    		
    		
    		var f = document.getElementById('fSend');

            var typeValue   = 0;

            if(f.chkGisa.checked == false && f.chkGesi.checked == false && f.chkGongji.checked == false && f.chkAll.checked == false){
    			f.chkAll.checked = true;
            }

            if(f.chkGisa.checked == true)typeValue += 1;
            if(f.chkGesi.checked == true)typeValue += 2;
            if(f.chkGongji.checked == true)typeValue += 4;
            if(f.chkAll.checked == true)typeValue = 7;

            f.type.value = typeValue;
        	
    		if((f.sOrder.value == selecedOrder) && (click == "TRUE")){
    			if(f.sOrderAlign.value == "ASC"){
    				f.sOrderAlign.value = "DESC";
    			}else{
    				f.sOrderAlign.value = "ASC";
    			}
    		}else{
    			f.sOrder.value = selecedOrder;
    		}
    		f.sg_seq.value = chkSiteGroup();
    		
    		
    		
    		document.fSend.keySearch.value =  "Y";
    		
        	dateIdx = 0;
        	dateCnt = checkTerm($("input[name=sDateTo]").val().replace(/-/gi,""), $("input[name=sDateFrom]").val().replace(/-/gi,"")) + 1;
        	
        	
        	$(".gage_loader").show();
        	dateIdx++;
        	keySearch("idx="+dateIdx+"&diffCnt=" + dateCnt);
    	}
    	
    	*/
    }
    
    function keySearch(subParm){
		var parm = "";
		parm = $("#fSend").serialize();
		
		if(subParm){
			if(parm == ""){
				parm = subParm; 
			}else{
				parm += "&" + subParm; 
			}	
		}
		
		$.ajax({ type : "POST"
			   , async : true
			   , url : 'ajax_key_search.jsp'
			   , timeout : 60000
			   , dataType : "text"
			   , data : parm
			   , success : function(data){
					
		   				if(data.trim() == "Y"){
		   					$("#fileName").val(data.trim().split("|")[1]);
		   					if(dateIdx >= dateCnt){
		   						
		   						$(".gage_loader").hide();
		   						doSearch();
		   						
		   					}else{
		   						gageLoader_progress(dateIdx / dateCnt * 100);
		   						dateIdx++;
		   						
		   						keySearch("idx="+dateIdx+"&diffCnt=" + dateCnt);
		   					}
		   				}
				   				
				   				
							   				   
						   }
		       ,beforeSend : function(){
		    	}
		});
		
	}
    
    function saveTypeCode19(md_Pseq, md_seq, searchmode, issue_check){
    	var target = "SameList_"+md_Pseq;
    	var yData = "";
    	var nData = "";
    	
    	var num = $("#"+target).find("[name=sameChk]").length;
    	
    	$("#"+target).find("[name=sameChk]").each(function(){
    		if($(this).is(":checked")){
    			if(yData == ""){
    				yData += $(this).val(); 
    			}else{
    				yData += ","+$(this).val();
    			}
    		}else{
    			
    			if(nData == ""){
    				nData += $(this).val(); 
    			}else{
    				nData += ","+$(this).val();
    			}
    		}
    	});
    	
    	
    	$("#original_md_Pseq").val(md_Pseq);
    	$("#original_md_seq").val(md_seq);
    	$("#original_searchmode").val(searchmode);
    	$("#original_issue_Check").val(issue_check);
    	
    	var f = document.send_same_kind;
		f.action = "same_typeCode_prc.jsp?yData="+yData+"&nData="+nData;
        f.target = 'if_samelist';
        f.submit();
    	
    }
    function previewOriginal(md_Seq , obj){
    	var f = obj;
    	
    	f.tooltip({delay:0});
    	
    }
    
    function setBindTooltip(su){
    	
    	for(var i = 0; i < su; i++){
			$("#same_img_future" + i).bindTooltip({direction:"top", width:300}); //{direction:"[auto|top|bottom]"}
	 	};
    }
    
    //2015-12-07
    function samePackage(){
    	if ( !confirm("선택된 항목을 묶으시겠습니까?" ) ) {
            return;
     	}
		var sameChk = true;
    	var f = document.getElementById('fCheck');
    	document.send_mb.md_seqs.value = '';
    	if(f.chkData){
    		if(f.chkData.length>1){					
    		 	for(var i=0; i<f.chkData.length; i++)
    		 	{
    			 	if(f.chkData[i].checked){
    		 			document.send_mb.md_seqs.value =  document.send_mb.md_seqs.value == '' ? f.chkData[i].value : document.send_mb.md_seqs.value+','+f.chkData[i].value;
  
    		 			if($('#issue_menu_icon'+f.chkData[i].value).attr('src') == '../../images/search/btn_manage_on.gif'){
    			 			sameChk = false;
    			 			alert('등록된 이슈는 이슈관리에서 유사묶기가 가능합니다.');
    			 			return;
    			 		}
    			 	}
    		 	}
    		}else{
    			document.send_mb.md_seqs.value = f.chkData.value;
    		}
    	}
    	
    	if(!sameChk){
    		alert('등록된 이슈는 이슈관리에서 유사묶기가 가능합니다.');
 			return;
    	}
    	return;
    	document.send_mb.target = 'proceeFrame';
    	document.send_mb.action = 'search_same_package_prc.jsp?nowpage=' + document.fSend.nowpage.value;
    	document.send_mb.submit();
    }

    function sameListChk(check, md_pseq, md_seq){
      	//유사목록 펼칠경우, 전체 선택 /해제 기능 추가 - 고객사요청(2020.11)
	   	var sameList = document.getElementById('SameList_' + md_pseq);
      	var target = document.getElementsByName('sameChk');
	   	//유사 목록이 보인다면, 
	   	if('' == sameList.style.display){
	   		//유사기사  전체 선택	   		
	   		if(check){
	   			for(var i=0; i<target.length; i++){
	   				target[i].checked = true;
			 	}
	   		//유사기사  전체 해제
	   		}else{
	   			for(var i=0; i<target.length; i++){
	   				target[i].checked = false;
			 	}
	   		}
	   	}
    }
    
	 function detectChk(checked){ // 기사에서 체크박스 목록 감지하여 한 개라도 풀리면 전체선택 체크박스 해제
		   	var frm = document.fCheck;
		   	var chk_flg = true;
		   	
		       for (var i = 0; i < frm.elements.length; i++) {
		           var e = frm.elements[i];
		           if ( e.name == "chkData" && e.checked ==false ) {
		              chk_flg = false;
		           }
		       }
		   	
		       if(chk_flg){
		       	 document.fCheck.chkAllCheck.checked = true;
		       }

		   	if(checked==false){
		            document.fCheck.chkAllCheck.checked = false;
		       }       
	 }
	 
	 //중요 체크시 ajax로 데이터 전달 2018.02.08 고기범
	 function importantChk(checked,value){
		 
		 var important_val = "";
		 // 중요 == 1 , 중요x == 0
		 if(checked == true) important_val = "1";
		 if(checked == false) important_val = "0";
		 
		 var params = "important_val="+important_val+"&md_seq="+value+"&mode=important_check";
		 $.ajax({
		 	 type:"post",
		 	 url: "warning_data_prc.jsp",
		 	 async : true,
		 	 timeout: 30000,
		 	 dataType:'text',
		 	 data: params,
		 	 success: function(data){
		 		/* if(important_val == "1") alert("중요체크");
				if(important_val == "0") alert("중요체크취소"); */
		 	 },
		 	 error: function(e){
		 	 	alert("관리자에게 문의하시기 바랍니다.");
		 	 }
		 });
		 
	 }
	 
	    function gageLoader_progress( $value ) {
			var per = Math.round( $value );
			var temp;	
			temp = Math.ceil( per * 0.1 );
			if( temp == 0 && per > 0 ) temp = 1;
			$( ".gage_loader > span" ).html( per + "%" );
			$( ".gage_loader > .gage" ).css( { "background-position-y" : -( temp * 15 ) } );
		}
	    
		function chkSiteGroupTypeAll(chk){
			var group = document.getElementsByName('sltSiteGroupType');
			if(group){
				if(group.length){
					for(var i = 0; i < group.length; i++){
						group[i].checked = chk;
					}
				}
			} 
		}
		
		//임시등록
		function multiTempReg(){
			var f = document.getElementById('fSend');
			var frm = document.getElementById('fCheck');

			var selectedList = "";

			if(frm.chkData){
				if(frm.chkData.length>1){					
					for(var i=0; i<frm.chkData.length; i++){
						if(frm.chkData[i].checked){
						 	selectedList =  selectedList == '' ? frm.chkData[i].value : selectedList+','+frm.chkData[i].value;
						}
					}
				}else{
					if(frm.chkData.checked){
						if(selectedList == ""){
							selectedList = frm.chkData.value;	
						}else{
							selectedList += "," + frm.chkData.value;
						}
					}
				}
			}
			
			if(frm.sameChk){
				if(frm.sameChk.length>1){					
				 	for(var i=0; i<frm.sameChk.length; i++){
					 	if(frm.sameChk[i].checked){
				 			selectedList =  selectedList == '' ? frm.sameChk[i].value : selectedList+','+frm.sameChk[i].value;
					 	}
				 	}
				}else{
					if(frm.sameChk.checked){
						if(selectedList == ""){
							selectedList = frm.sameChk.value;	
						}else{
							selectedList += "," + frm.sameChk.value;
						}
					}
				}
			}
			
			


			f.SaveList.value = selectedList;
			f.subMode.value = 'warning_new';
			
			if(f.SaveList.value == ''){
				alert('선택된 데이터가 없습니다.');
				return;
			}
			
			f.action = "search_tempIssueReg_prc.jsp";
	        f.target = 'proceeFrame';
	        f.submit();
			        
		}
		
		
		//보관함
		function showStorage(mode) {
			var f = document.getElementById('fSend');
			var frm = document.getElementById('fCheck');
			var selectedList = "";

			if(frm.chkData){
						if(frm.chkData.length>1){					
						 	for(var i=0; i<frm.chkData.length; i++)
						 	{
							 	if(frm.chkData[i].checked){
						 			selectedList =  selectedList == '' ? frm.chkData[i].value : selectedList+','+frm.chkData[i].value;
							 	}
						 	}
						}else{
							selectedList = frm.chkData.value;
						}
					}


			f.SaveList.value = selectedList;
			f.subMode.value = 'warning_new';
			
			if(f.SaveList.value == ''){
				alert('선택된 데이터가 없습니다.');
				return;
			} else {
				if(mode=='showList'){	
					//window.open("search_storage_prc.jsp","보관함",1400,800,false,true,false);
					popup.openByPost('fSend','../admin/aekeyword/pop_storage_add.jsp',460,200,false,true,false,'');
					
				}
			}

		}
	   
		//트랜드차트
		function showSearchChart(element){
			var f = document.getElementById('fSend');
			var obj = document.getElementById('hide_chart');
			if(obj.style.display == 'none'){
				obj.style.display = 'block';		
				f.s_chart.value = '1';
			}else{
				obj.style.display = 'none';		
				f.s_chart.value = '0';
			}
 			if(element.src.indexOf('open') > -1){
				element.src = element.src.replace('open', 'close');
			}else{
				element.src = element.src.replace('close', 'open');
			} 
		}
		
	    //차트 링크
	    function chartLink(date, cnt){
	        var f = document.getElementById('fSend');
	        f.iTotalCntSame.value = cnt;
	        f.chartDate.value = date;
	        f.sDateFrom.value = date;
	        f.sDateTo.value = date;
	        f.stime[0].selected = true;
	        f.etime[23].selected = true;
	        doSubmit();

	    }
 		
	 
	 
//-->

</script>
</head>
<body style="margin-left: 15px">
<!-- <img  id="sending" src="../../images/search/loding.gif" style="position: absolute; display: none; " > -->
<div class="gage_loading" id="sending" style="display: none;">
	<img src="../../images/search/loading_00.gif" alt="로딩중입니다.">
</div>
<div class="gage_loader">
	<img src="../../images/search/loading_00.gif" alt="로딩중입니다.">
	<div class="gage"></div>
	<span>0%</span>
</div>

<!--헤드라인 레이어-->
<div id=div_show style="font-size: 12px; padding-right: 5px; display: none; padding-left: 5px; left: 140px; width:500px; height:auto; padding-bottom: 5px; padding-top: 5px; position: absolute; background-color: #ffffcc; border: #ff6600 2px solid;"></div>

<!-- 원문저장 -->
<form name="send_same_kind" id="send_same_kind" method="post">
	<input type="hidden" name="original_md_Pseq" 		id="original_md_Pseq"  	  />
	<input type="hidden" name="original_md_seq" 		id="original_md_seq"  	  />
	<input type="hidden" name="original_searchmode" 	id="original_searchmode" />
	<input type="hidden" name="original_issue_Check" 	id="original_issue_Check" />
</form>

<!--북마크 정보 FORM-->
<form name="send_bookmark" id="send_bookmark" method="post">
	<input name="bookMarkContents" type="hidden" value="search">
	<input name="bookMarkSeq" type="hidden" value="">
</form>

<!--열어본 페이지 색상변경 및  DB저장-->
<form name="send_comfirm" id="send_comfirm" method="post">
	<input name="m_seq" type="hidden">
	<input name="md_seq" type="hidden">
	<input name="md_seqs" type="hidden">
	<input name="mode" type="hidden">
	<input type="hidden" name="subMode" value="warning_new">
</form>

<!--메일발송-->
<form name="send_mail" id="send_mail" method="post">
	<input name="md_seqs" type="hidden">
</form>

<!--기사공유-->
<form name="send_important_mail" id="send_important_mail" method="post">
	<input name="check_no" id="check_no" type="hidden">
</form>

<form name="send_same" id="send_same" method="post">
	<input name="md_seq" id="md_seq" type="hidden">
	<input name="keyword" id="keyword" type="hidden">
	<input name="md_pseqs" id="md_pseqs" type="hidden">
</form>

<!--유사묶기폼-->
<form name="send_mb" id="send_mb" method="post">
<input type="hidden" name="mode">
<input type="hidden" name="md_seq">
<input type="hidden" name="md_seqs">
<input type="hidden" name="subMode" value="warning_new">

</form>

<table style="height:100%; width:100%;" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="body_bg_top" valign="top" >
			<table style="width:820px;" border="0" cellpadding="0" cellspacing="0">
		<%-- <%
			if(!uei.getSearchMode().equals("DELIDX")){
		%>
			<table style="width:1100px;" border="0" cellpadding="0" cellspacing="0">
		<%
			}else{
		%>
			<table style="width:820px;" border="0" cellpadding="0" cellspacing="0">
		<%
			}
		%> --%>
		
			<!-- 타이틀 시작 -->
			<tr>
				<td class="tit_bg" style="height:67px;padding-top:20px;">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="../../images/search/tit_icon.gif" /><img src="../../images/search/tit_1216.gif" /></td>
						<td align="right">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="navi_home">HOME</td>
								<td class="navi_arrow">정보검색</td>
								<td class="navi_arrow2">소비자뉴스검색(캐피탈)</td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td height="15"></td>
			</tr>
			<!-- 타이틀 끝 -->
			<!-- 검색 시작 -->
			<tr>
				<td>
				<form name="fSend" id="fSend" action="warning_capital_contents.jsp" method="post"  style="margin-bottom: 0px;" >
					<input type="hidden" name="xp" value="<%=uei.getK_xp()%>">
					<input type="hidden" name="yp" value="<%=uei.getK_yp()%>">
					<input type="hidden" name="zp" value="<%=uei.getK_zp()%>">
					<input type="hidden" name="timer" value="<%=sTimer%>" >
					<input type="hidden" name="interval" value="<%=uei.getSt_reload_time()%>" >
					<input type="hidden" name="type" value="<%=uei.getMd_type()%>">
					<input type="hidden" name="searchtype" value="<%=uei.getSt_menu()%>">
					<input type="hidden" name="sOrder" value="<%=uei.getOrder()%>">
					<input type="hidden" name="sOrderAlign" value="<%=uei.getOrderAlign()%>">
					<input type="hidden" name="sg_seq" value="<%=uei.getSg_seq()%>">
					<input type="hidden" name="sd_gsn" value="<%=uei.getS_seq()%>">
					<input type="hidden" name="searchmode" value="<%=uei.getSearchMode()%>">
					<input type="hidden" name="Period" value="<%=uei.getSt_reload_time()%>">
					<input name="nowpage" type="hidden" value="<%=iNowPage%>">        
					<input type="hidden" name="keySearch" value="<%=keySearch%>">  
					<input name="del_mname" type="hidden" value="<%=uei.getDelName()%>">
					<input type="hidden" name="SaveList">
					<input name="bookMarkYn" type="hidden">
					<input name="iTotalCnt" type="hidden" value="<%=iTotalCnt%>">
					<input name="iTotalCntSame" type="hidden" value="<%=iTotalCntSame%>">
					<input type="hidden" name="tsTypeCheck">
					<input name="sTimeFrom" type="hidden" value="<%=uei.getStime()%>">
					<input name="sTimeTo" type="hidden" value="<%=uei.getEtime()%>">
					<input type="hidden" name="chartDate" id="chartDate" value="<%=chartDate%>">
					<input type="hidden" name="s_chart" id="s_chart" value="<%=uei.getS_Chart()%>">
					<input type="hidden" name="subMode" id="subMode">
<%-- <% if(!uei.getSearchMode().equals("DELIDX")){ %>
			<table width="1100" border="0" cellspacing="0" cellpadding="0">
				<tr>
				    <td><table width="1100" border="0" cellspacing="0" cellpadding="0">
				      <tr>
				        <td width="15"><img src="../../images/search/table_bg_01.gif" width="15" height="35" /></td>
						<td width="1073" background="../../images/search/table_bg_02.gif"><table width="700" border="0" cellspacing="0" cellpadding="0">				        
<% }else{%>
			<table width="820" border="0" cellspacing="0" cellpadding="0">
				<tr>
				    <td><table width="820" border="0" cellspacing="0" cellpadding="0">
				      <tr>
				        <td width="15"><img src="../../images/search/table_bg_01.gif" width="15" height="35" /></td>
						<td width="793" background="../../images/search/table_bg_02.gif"><table width="700" border="0" cellspacing="0" cellpadding="0">				        				    
<% } %> --%>
			<table width="820" border="0" cellspacing="0" cellpadding="0">
				<tr>
				    <td><table width="820" border="0" cellspacing="0" cellpadding="0">
				      <tr>
				        <td width="15"><img src="../../images/search/table_bg_01.gif" width="15" height="35" /></td>
						<td width="793" background="../../images/search/table_bg_02.gif"><table width="700" border="0" cellspacing="0" cellpadding="0">	
				          <tr>
				            <td width="16"><img src="../../images/search/table_icon_01.gif" width="16" height="12" /></td>
				            <td width="63" class="b_text"><strong>검색단어 </strong></td>
				            <td width="621">
				            <select name="sKeywordKind">
				            	<option value="1" <%if("1".equals(keywordKind)){out.print("selected");} %> >제목</option>
				            	<option value="2" <%if("2".equals(keywordKind)){out.print("selected");} %>>제목+내용</option>
				            	<option value="3" <%if("3".equals(keywordKind)){out.print("selected");} %>>출처</option>
				            </select>
				            <input name="sKeyword" type="text" class="textbox3" value="<%=uei.getKeyword()%>" style="width:400px;" onkeydown="javascript:if(event.keyCode == 13){search_v2();}"/>
				            <img src="../../images/search/search_btn.gif" width="61" height="22" align="absmiddle" onclick="search_v2();"  style="cursor: pointer;"/></td>
				          </tr>
				        </table></td>
				        <td width="12"><img src="../../images/search/table_bg_03.gif" width="12" height="35" /></td>
				      </tr>
				    </table></td>
				  </tr>
				  <tr id="hide1" <%if(uei.getSt_menu().equals("0")){out.print("style=\"display:none\"");}%>>
<%-- <% if(!uei.getSearchMode().equals("DELIDX")){ %>
				    <td><table width="1100" border="0" cellspacing="0" cellpadding="0">
				      <tr>
				        <td width="1" bgcolor="D3D3D3"></td>
				        <td width="1098" bgcolor="F7F7F7" style="padding:10px 0px 5px 0px"><table width="1078" border="0" align="center" cellpadding="0" cellspacing="0">
				        
				          <tr>
				            <td height="24" style="padding:0px 0px 5px 7px"><table width="1070" border="0" cellspacing="0" cellpadding="0">
				              <tr>
				                <td width="75" class="b_text"><img src="../../images/search/icon_search_bullet.gif" width="9" height="9" /> <strong>검색기간</strong></td>
				                <td><table width="550" border="0" cellpadding="0" cellspacing="0">				        
<% }else{%>
				    <td><table width="820" border="0" cellspacing="0" cellpadding="0">
				      <tr>
				        <td width="1" bgcolor="D3D3D3"></td>
				        <td width="818" bgcolor="F7F7F7" style="padding:10px 0px 5px 0px"><table width="798" border="0" align="center" cellpadding="0" cellspacing="0">
				        
				          <tr>
				            <td height="24" style="padding:0px 0px 5px 7px"><table width="790" border="0" cellspacing="0" cellpadding="0">
				              <tr>
				                <td width="75" class="b_text"><img src="../../images/search/icon_search_bullet.gif" width="9" height="9" /> <strong>검색기간</strong></td>
				                <td><table width="550" border="0" cellpadding="0" cellspacing="0">				        
<% } %> --%>
				    <td><table width="820" border="0" cellspacing="0" cellpadding="0">
				      <tr>
				        <td width="1" bgcolor="D3D3D3"></td>
				        <td width="818" bgcolor="F7F7F7" style="padding:10px 0px 5px 0px"><table width="798" border="0" align="center" cellpadding="0" cellspacing="0">
				        
				          <tr>
				            <td height="24" style="padding:0px 0px 5px 7px"><table width="790" border="0" cellspacing="0" cellpadding="0">
				              <tr>
				                <td width="75" class="b_text"><img src="../../images/search/icon_search_bullet.gif" width="9" height="9" /> <strong>검색기간</strong></td>
				                <td><table width="550" border="0" cellpadding="0" cellspacing="0">
				                
				                  <tr>
				                    <td width="95"><input style="width:90px;text-align:center;" class="textbox" type="text" name="sDateFrom" id="sDateFrom" value="<%=uei.getDateFrom()%>"></td>
				                    <td width="30"><img src="../../images/search/btn_calendar.gif" style="cursor:pointer" onclick="calendar_sh(document.getElementById('sDateFrom'))"/></td>
				                    <td width="55"><select name="stime"><%for(int j=0; j<24; j++){ if(j==Integer.parseInt(uei.getStime())){out.print("<option value="+ j +" selected>"+j+"시</option>");}else{out.print("<option value="+j+">"+j+"시</option>");}} %></select> </td>
				                    <td>~</td>
				                    <td width="95"><input style="width:90px;text-align:center;" class="textbox" type="text" name="sDateTo" id="sDateTo" value="<%=uei.getDateTo()%>"></td>
				                    <td width="30"><img src="../../images/search/btn_calendar.gif" style="cursor:pointer" onclick="calendar_sh(document.getElementById('sDateTo'))"/></td>
				                    <td width="55"><select name="etime"><%for(int j=0; j<24; j++){ if(j==Integer.parseInt(uei.getEtime())){out.print("<option value="+ j +" selected>"+j+"시</option>");}else{out.print("<option value="+j+">"+j+"시</option>");}} %></select></td>
				                    <td style="padding-left:10px;"><img src="../../images/search/btn_calendar_1day.gif" width="38" height="24"  onclick="setDateTerm(0);" style="cursor:pointer"/><img src="../../images/search/btn_calendar_3day.gif" width="38" height="24" onclick="setDateTerm(2);" style="cursor:pointer"/><img src="../../images/search/btn_calendar_7day.gif" width="38" height="24" onclick="setDateTerm(6);" style="cursor:pointer"/></td>
				                  </tr>
				                </table></td>
				                </tr>
				            </table></td>
				          </tr>
				          <tr>
<%-- <% if(!uei.getSearchMode().equals("DELIDX")){ %>
				            <td><img src="../../images/search/dotline.gif" width="1078" height="3" /></td>
				          </tr>
				          <tr>
				            <td style="padding:5px 0px 5px 7px"><table width="1070" border="0" cellspacing="0" cellpadding="0">				            
<% }else{%>
				            <td><img src="../../images/search/dotline.gif" width="798" height="3" /></td>
				          </tr>
				          <tr>
				            <td style="padding:5px 0px 5px 7px"><table width="790" border="0" cellspacing="0" cellpadding="0">				            
<% } %> --%>
		  					 <td><img src="../../images/search/dotline.gif" width="798" height="3" /></td>
						          </tr>
						          <tr>
						      <td style="padding:5px 0px 5px 7px"><table width="790" border="0" cellspacing="0" cellpadding="0">
						      
				              <tr>
				                <td width="75" class="b_text"><img src="../../images/search/icon_search_bullet.gif" width="9" height="9" /> <strong>정보유형</strong></td>
				                <td style="color:#2f5065;"><input name="chkAll" type="checkbox" onclick="javascript:checkAll();" <%=sTypeAll%>/><strong>전체</strong>&nbsp;
				                  <input name="chkGisa" type="checkbox" onclick="javascript:checkEtc();" <%=sType1%>/><img src="../../images/search/con_icon01.gif" width="13" height="13" align="absmiddle" /> 기사&nbsp;
				                  <input name="chkGesi"  type="checkbox" onclick="javascript:checkEtc();" <%=sType2%>/><img src="../../images/search/con_icon02.gif" width="12" height="13" align="absmiddle" /> 게시&nbsp;
				                  <input name="chkGongji" type="checkbox" onclick="javascript:checkEtc();" <%=sType3%>/><img src="../../images/search/con_icon03.gif" width="9" height="13" align="absmiddle" /> 공지</td>
				              </tr>
				            </table></td>
				          </tr>
				          <tr>
				          
<%-- <% if(!uei.getSearchMode().equals("DELIDX")){ %>
				            <td><img src="../../images/search/dotline.gif" width="1078" height="3" /></td>
				          </tr>
				          <tr>
				            <td style="padding:5px 0px 5px 7px"><table width="1070" border="0" cellspacing="0" cellpadding="0">
				              <tr>
				                <td width="75" class="b_text"><img src="../../images/search/icon_search_bullet.gif" width="9" height="9" /> <strong>검색유형</strong></td>
				                <td><table width="995" style="color:#2f5065;" border="0" cellpadding="0" cellspacing="0">				            
<% }else{%>
				            <td><img src="../../images/search/dotline.gif" width="798" height="3" /></td>
				          </tr>
				          <tr>
				            <td style="padding:5px 0px 5px 7px"><table width="790" border="0" cellspacing="0" cellpadding="0">
				              <tr>
				                <td width="75" class="b_text"><img src="../../images/search/icon_search_bullet.gif" width="9" height="9" /> <strong>검색유형</strong></td>
				                <td><table width="500" style="color:#2f5065;" border="0" cellpadding="0" cellspacing="0">				            
<% } %>			 --%>
				            <td><img src="../../images/search/dotline.gif" width="798" height="3" /></td>
				          </tr>
				          <tr>
				            <td style="padding:5px 0px 5px 7px"><table width="790" border="0" cellspacing="0" cellpadding="0">
				              <tr>
				                <td width="75" class="b_text"><img src="../../images/search/icon_search_bullet.gif" width="9" height="9" /> <strong>검색유형</strong></td>
				                <td><table width="500" style="color:#2f5065;" border="0" cellpadding="0" cellspacing="0">
				                	          
											<tr>
												<td>
												<%
													/* if(!uei.getSg_seq().equals("")){
														arrSelected = uei.getSg_seq().split(",");
													} */
												%>
													<div style="width:90px;float:left"><input type="checkbox" name="sltSiteGroupAll" value="0" onclick="chkSiteGroupAll(this.checked);" <%if(alGroup.size() == arrSelected.length){out.print("checked");}%>><strong>전체</strong></div>
													<%
														String checked = "";
														for( int i = 0; i < alGroup.size() ; i++ ) {
															sgi = (siteGroupInfo)alGroup.get(i);
															if(arrSelected != null){
																for(int j = 0; j < arrSelected.length; j++){
																	if(Integer.parseInt(arrSelected[j]) == sgi.getSg_seq()){
																		System.out.println(arrSelected[j] + "<<arrSelected[j]");																			
																		checked = "checked";
																		break;
																	}
																}
															}
													%>
													<div style="width:90px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;float:left"><input type="checkbox" name="sltSiteGroup" value="<%=sgi.getSg_seq()%>" <%=checked%>><%=sgi.getSd_name()%></div>
													<%
															checked = "";
														}
													%>
												</td>
											</tr>
										</table></td>
				              </tr>
				            </table></td>
				          </tr>
				          <tr>
				          
<%-- <% if(!uei.getSearchMode().equals("DELIDX")){ %>
				            <td><img src="../../images/search/dotline.gif" width="1078" height="3" /></td>
				          </tr>
				          <tr>
				            <td style="padding:5px 0px 5px 7px"><table width="1070" border="0" cellspacing="0" cellpadding="0">				            
<% }else{%>
				            <td><img src="../../images/search/dotline.gif" width="798" height="3" /></td>
				          </tr>
				          <tr>
				            <td style="padding:5px 0px 5px 7px"><table width="790" border="0" cellspacing="0" cellpadding="0">				            
<% } %>		 --%>	
				            <td><img src="../../images/search/dotline.gif" width="798" height="3" /></td>
				          </tr>
				          
				      <%--     <tr>
				          <td style="padding:5px 0px 5px 7px"><table width="790" border="0" cellspacing="0" cellpadding="0">
					          <tr>
	 				          <td width="75" class="b_text"><img src="../../images/search/icon_search_bullet.gif" width="9" height="9" /> <strong>Tier정보</strong></td>
	 				          <td style="color:#2f5065;" >
	 				          <table border="0" cellpadding="0" cellspacing="0">
	 				          <tr>
							<%
								int end_idx = 0;
								int fix_length = 7;
								String ts_check = "";
								String elem_ts_type = "";
								String tierName = "";
								
								int tier_length = tierGroupList.size();
								
								if(tier_length > fix_length){
									tier_length = fix_length;
								}
								
								for(int i=0; i < tier_length;i++) {
									
									Map elem = (HashMap) tierGroupList.get(i);
									elem_ts_type = (String)elem.get("TS_TYPE");
									tierName = (String)elem.get("TS_DESCRIPTION");
									if(su.inarray(uei.getTs_type().split(","), elem_ts_type )){
										ts_check = "checked";
									}else{
										ts_check = "";
									}
									end_idx++;
									/* // SK그룹 복사 영향.... 하드코딩..
									if(Integer.toString((Integer)sglist.get(i)).equals("1"))
										tierName = "tier1 (경제지)";
									else if(Integer.toString((Integer)sglist.get(i)).equals("2"))
										tierName = "tier1 (방송-종편/뉴스전문)";
									else if(Integer.toString((Integer)sglist.get(i)).equals("3"))
										tierName = "tier1 (방송-지상파)";
									else if(Integer.toString((Integer)sglist.get(i)).equals("4"))
										tierName = "tier1 (영자지)";
									else if(Integer.toString((Integer)sglist.get(i)).equals("5"))
										tierName = "tier1 (온라인)";
									else if(Integer.toString((Integer)sglist.get(i)).equals("6"))
										tierName = "tier1 (전문지)";
									else if(Integer.toString((Integer)sglist.get(i)).equals("7"))
										tierName = "tier1 (종합지)";
									else if(Integer.toString((Integer)sglist.get(i)).equals("8"))
										tierName = "tier1 (통신사)";
									else if(Integer.toString((Integer)sglist.get(i)).equals("9"))
										tierName = "tier2 (방송-케이블 등)";
									else if(Integer.toString((Integer)sglist.get(i)).equals("10"))
										tierName = "tier2 (스포츠지)";
									else if(Integer.toString((Integer)sglist.get(i)).equals("11"))
										tierName = "tier2 (온라인)";
									else if(Integer.toString((Integer)sglist.get(i)).equals("12"))
										tierName = "tier3 (온라인)";
									else if(Integer.toString((Integer)sglist.get(i)).equals("13"))
										tierName = "tier4"; */
							%>
								<td width="120px"><input id="ts_type_<%=elem_ts_type%>" name="ts_type" type="checkbox" onclick="ts_type_check();" value="<%=elem_ts_type%>" <%=ts_check%>>
								<label for="ts_type_<%=elem_ts_type%>" style="cursor:pointer"><%=tierName%></label>
								<%//= "Tier" + (Integer)sglist.get(i)%>&nbsp;
								</td>	
							<%		
								}
							%>
							</tr>
							
							<tr>
							<%
								if(end_idx < tierGroupList.size()){
									for(int i=end_idx; i < tierGroupList.size();i++) {
										Map elem = (HashMap) tierGroupList.get(i);
										elem_ts_type = (String)elem.get("TS_TYPE");
										tierName = (String)elem.get("TS_DESCRIPTION");
										
										if(su.inarray(uei.getTs_type().split(","), elem_ts_type )){
											ts_check = "checked";
										}else{
											ts_check = "";
										}
										
										// SK그룹 복사 영향.... 하드코딩..
										/* if(Integer.toString((Integer)sglist.get(i)).equals("1"))
											tierName = "tier1 (경제지)";
										else if(Integer.toString((Integer)sglist.get(i)).equals("2"))
											tierName = "tier1 (방송-종편/뉴스전문)";
										else if(Integer.toString((Integer)sglist.get(i)).equals("3"))
											tierName = "tier1 (방송-지상파)";
										else if(Integer.toString((Integer)sglist.get(i)).equals("4"))
											tierName = "tier1 (영자지)";
										else if(Integer.toString((Integer)sglist.get(i)).equals("5"))
											tierName = "tier1 (온라인)";
										else if(Integer.toString((Integer)sglist.get(i)).equals("6"))
											tierName = "tier1 (전문지)";
										else if(Integer.toString((Integer)sglist.get(i)).equals("7"))
											tierName = "tier1 (종합지)";
										else if(Integer.toString((Integer)sglist.get(i)).equals("8"))
											tierName = "tier1 (통신사)";
										else if(Integer.toString((Integer)sglist.get(i)).equals("9"))
											tierName = "tier2 (방송-케이블 등)";
										else if(Integer.toString((Integer)sglist.get(i)).equals("10"))
											tierName = "tier2 (스포츠지)";
										else if(Integer.toString((Integer)sglist.get(i)).equals("11"))
											tierName = "tier2 (온라인)";
										else if(Integer.toString((Integer)sglist.get(i)).equals("12"))
											tierName = "tier3 (온라인)";
										else if(Integer.toString((Integer)sglist.get(i)).equals("13"))
											tierName = "tier4";
										else 
											tierName = (String)sglist.get(i); */
								%>
								<td width="120px"><input id="ts_type_<%=elem_ts_type%>" name="ts_type" type="checkbox" onclick="ts_type_check();" value="<%=elem_ts_type%>" <%=ts_check%>>
								<label for="ts_type_<%=elem_ts_type%>" style="cursor:pointer"><%=tierName%></label>
								<%//= "Tier" + (Integer)sglist.get(i)%>&nbsp;
								</td>	
							<%	
									}
								}
							%>
							</tr>
	 				          </table>
	 				          </td>
	 				          </tr>
	 				         
				          </table></td>
				          </tr>
				          
				          <tr>
				          	<td><img src="../../images/search/dotline.gif" width="798" height="3" /></td>
				          </tr> --%>
				          
				          <tr>
				            <td style="padding:5px 0px 5px 7px"><table width="790" border="0" cellspacing="0" cellpadding="0">	
				            			    
				              <tr>
 				                
									<td width="120" class="b_text"><img src="../../images/search/icon_search_bullet.gif" width="9" height="9" /> <strong>이슈등록 여부</strong></td>
				          			<td style="color:#2f5065;" >
				          				<select name="issue_check" id="issue_check">
											<option value="" <%if(issue_check.equals("")){out.print("selected");}%>>전체</option>
											<option value="Y" <%if(issue_check.equals("Y")){out.print("selected");}%>>등록</option>
											<option value="T" <%if(issue_check.equals("T")){out.print("selected");}%>>임시등록</option>
											<option value="N" <%if(issue_check.equals("N")){out.print("selected");}%>>미등록</option>
										</select>
				          			</td>
				          			<td width="120" class="b_text">
				          			<img src="../../images/search/icon_search_bullet.gif" width="9" height="9" /> <strong>보관함 목록</strong></td>
				          			<td style="color:#2f5065;" >
				          				<select name="storageName" id="storageName" title="보관함 선택" style="width: 90;">
										<option value="">전체</option> 
											<%
												ArrayList<StorageBean> list = stomgr.getStorageNameList(SS_M_NO);
												
												StorageBean sNameList;
												String storageSelected = "";
												//for(StorageBean sNameList : list) {
											   for(int i=0; i<list.size(); i++) {
												   sNameList = list.get(i);
												   
												   //if( !storageName.equals("") &&  (Integer.parseInt(storageName) == sNameList.getStoSeq())){
												   if(storageName.equals(Integer.toString(sNameList.getStoSeq()))){
													   storageSelected = "selected";
												   }else{
													   storageSelected = "";
												   }
												   
										%>
											<%-- <option value="<%=sNameList.getStoSeq() %>" <%if("55".equals(storageName)){out.print("selected");} %>><%=sNameList.getStoName()%>//<%=storageName %>//<%=sNameList.getStoSeq()%></option> --%>
											 	<%--  <option value="<%=sNameList.getStoSeq() %>" <%if("sNameList.getStoSeq()".equals("storageName")){out.print("selected");} %>><%=sNameList.getStoName()%>//<%=storageName %>//<%=sNameList.getStoSeq()%></option>--%>
											 	<option value="<%=sNameList.getStoSeq()%>" <%=storageSelected%>><%=sNameList.getStoName()%></option>
											<%
												}
											%>
											
									</select>
				          			<%-- <img src="../../images/search/icon_search_bullet.gif" width="9" height="9" /> <strong>댓글등록 여부</strong></td>
				          			<td style="color:#2f5065;" >
				          				<select name="reply_check" id="reply_check">
											<option value="" <%if(reply_check.equals("")){out.print("selected");}%>>전체</option>
											<option value="Y" <%if(reply_check.equals("Y")){out.print("selected");}%>>등록</option>
										</select> --%>
				          			</td> 
				              </tr>
				            </table></td>
				          </tr>
				        </table></td>
				        <td width="1" bgcolor="D3D3D3"></td>
				      </tr>
				      
				    </table></td>
				  </tr>
				  <tr id="hide2" <%if(uei.getSt_menu().equals("0")){out.print("style=\"display:none\"");}%>>
				    <td height="1" bgcolor="D3D3D3"></td>
				  </tr>
				  <tr>
				    <td align="center"><img src="<%if(uei.getSt_menu().equals("0")){out.print("../../images/search/btn_searchbox_open.gif");}else{out.print("../../images/search/btn_searchbox_close.gif");}%>" style="cursor:pointer" onclick="actionLayer(this)"/></td>
				  </tr>
				</table>
				</form>
				</td>
			</tr>
			<!-- 검색 끝 -->
			 
			<!-- 트렌츠차트 -->
			<tr>
				<td height="20"></td>
			</tr>
			<table width="820" border="0" cellspacing="0" cellpadding="0">
				<tr>
				    <td><table width="820" border="0" cellspacing="0" cellpadding="0">
				      <tr>
				        <td width="15"><img src="../../images/search/table_bg_01.gif" width="15" height="35" /></td>
						<td width="793" background="../../images/search/table_bg_02.gif"><table width="700" border="0" cellspacing="0" cellpadding="0">	
				          <tr>
				            <td width="16"><img src="../../images/search/table_icon_01.gif" width="16" height="12" /></td>
				            <td width="83" class="b_text"><strong>Trend Chart </strong></td>
				            <td width="600">
				          </tr>
				        </table></td>
				        <td width="12"><img src="../../images/search/table_bg_03.gif" width="12" height="35" /></td>
				      </tr>
				    </table></td>
				  </tr>
				  <tr id="hide1" <%if(uei.getS_Chart().equals("0")){out.print("style=\"display:none\"");}%>>
				  
				<tr>
					<td><table width="820" border="0" cellspacing="0" cellpadding="0">
					
						<tr id="hide_chart" style="display:<%if(uei.getS_Chart().equals("1")){out.print("block");}else{out.print("none");}%>">
							<td><table width="820" border="0" cellpadding="0" cellspacing="1" bgcolor="E1E1E1">
								<tr>
									<td align="center" bgcolor="F2F2F2" style="padding:10px 10px 10px 10px">
										<div id="chartArea" style="height:220px"></div>
									</td>
								</tr>
								</table></td>
						</tr>
						<tr>
							<td align="center"><img src="<%if(uei.getS_Chart().equals("0")){out.print("../../images/search/btn_searchbox_open.gif");}else{out.print("../../images/search/btn_searchbox_close.gif");}%>" style="cursor:pointer" onclick="showSearchChart(this)"/></td>
	<!-- 					<td align="right" valign="top" style="padding:0px 10px 0px 0px"><img src="../../images/search/btn_searchbox_open.gif" width="68" height="9" style="cursor:pointer" onclick="showSearchChart(this)"></td> -->
						</tr>
						</table></td>
					</tr>
				<tr>
					<td height="20"></td>
				</tr> 
				
			<tr>
				<td style="height:45px;background:url('../../images/search/list_top_line.gif') bottom repeat-x;">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="../../images/search/icon_search_bullet.gif"> <strong><%=smgr.msKeyTitle%></strong>에 대한 검색결과 /<strong> <%=df.format(iTotalCnt)%></strong> 건 (유사포함 <strong> <%=df.format(iTotalCntSame)%></strong> 건), <strong><%=df.format(iTotalCnt==0?0:iNowPage)%></strong>/<strong><%=df.format(iTotalPage)%></strong> pages
						<%
							double issuePer = 0.0;
							double tempPer = 0.0;
							int zero = 0;
							
							//issuePer = ((double)iIssueTotalCnt/(double)iAllTotalCnt)*100;
							//tempPer = ((double)iTempTotalCnt/(double)iAllTotalCnt)*100;
							
							if(iAllTotalCnt != zero)  {
								issuePer = ((double)iIssueTotalCnt/(double)iAllTotalCnt)*100;
							}
							
							if(iAllTotalCnt != zero) {
								tempPer = ((double)iTempTotalCnt/(double)iAllTotalCnt)*100;
							}
							
						%>
							<br/>
							<img src="../../images/search/icon_search_bullet.gif"> 이슈등록 / <strong><%=df.format(iIssueTotalCnt) %></strong> 건 (<strong><%=df_per.format(issuePer)%></strong> %) &nbsp;&nbsp; 임시등록 / <strong><%=df.format(iTempTotalCnt) %></strong> 건 (<strong><%=df_per.format(tempPer) %></strong> %)</td>
						
						<td width="150" align="left" style="padding: 2px 0px 0px 3px;">
							<span class="search_reset_time" id="watch"><strong>새로고침 중지중..</strong></span>
						</td>
						<td width="90">
							<select name="slttimer" class="t" OnChange="setTimer(this.value);" >
								<option value="0"  <%if(uei.getSt_reload_time().equals("0")) out.println("selected");%>>사용하지 않음</option>
								<option value="5"  <%if(uei.getSt_reload_time().equals("5")) out.println("selected");%>>5분마다 새로고침</option>
								<option value="10" <%if(uei.getSt_reload_time().equals("10")) out.println("selected");%>>10분마다 새로고침</option>
								<option value="20" <%if(uei.getSt_reload_time().equals("20")) out.println("selected");%>>20분마다 새로고침</option>
								<option value="30" <%if(uei.getSt_reload_time().equals("30")) out.println("selected");%>>30분마다 새로고침</option>
							</select>
						</td>
					</tr>
				</table>	
				</td>
			</tr>
			
<!-- 			<tr> -->
<!-- 				<td> -->
<!-- 				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0"> -->
<!-- 					<tr> -->
					
<%-- 						<td><img src="../../images/search/icon_search_bullet.gif"> <strong>출처별</strong> 검색결과(유사포함): <%=strSourceCnts%></td> --%>
<!-- 					</tr> -->
<!-- 					<tr height="5px" ><td></td></tr> -->
<!-- 				</table>	 -->
<!-- 				</td> -->
<!-- 			</tr> -->
			
			
		</table>
		<form name="fCheck" id="fCheck" target="_blank" method="post" style="margin-top: 0px;">
		<%-- <%
			if(!uei.getSearchMode().equals("DELIDX")){
		%>
			<table style="width:1100px;" border="0" cellpadding="0" cellspacing="0">
		<%
			}else{
		%>
			<table style="width:820px;" border="0" cellpadding="0" cellspacing="0">
		<%
			}
		%> --%>
		<table style="width:820px;" border="0" cellpadding="0" cellspacing="0">
			<tr style="display: <%=Jnone%>;">
				<td style="height:40px;">
				<table border="0" cellpadding="0" cellspacing="0" >
					<tr>
						<td><img src="../../images/search/btn_allselect.gif" onclick="reverseAll(!document.getElementById('fCheck').chkAllCheck.checked);"/></td>
						<td>&nbsp;</td>
<%
	if(uei.getSearchMode().equals("ALLKEY")){
%>  
						<td><img src="../../images/search/btn_del.gif" onclick="idxProcess('truncate');" style="cursor:pointer"/></td>
						<td>&nbsp;</td>
<!-- 						<td><img src="../../images/search/btn_bookmark.gif" onclick="saveBookMark();" style="cursor:pointer"/></td>
						<td>&nbsp;</td>
						<td><img src="../../images/search/btn_bookmark_search.gif" onclick="doBookMarkSearch();" style="cursor:pointer"/></td>
						<td>&nbsp;</td> -->
						<%-- <%if(uei.getMg_seq().equals("2")|| uei.getMg_seq().equals("3") || uei.getMg_seq().equals("9")){ %> --%>
						<td><img src="../../images/search/btn_multi.gif" onclick="send_issue('multi');" style="cursor:pointer"/></td>
						<td>&nbsp;</td>
						<td><img src="../../images/search/btn_excelsave.gif" onclick="saveExcel();" style="cursor:pointer; display: "/></td>
						<td>&nbsp;</td>
						<td><img src="../../images/search/btn_tempReg.gif" onclick="multiTempReg();" style="cursor:pointer"/></td>
						<td>&nbsp;</td>
						<td><img src="../../images/search/btn_storage.gif" onclick="showStorage('showList');" style="cursor:pointer"/></td>
						<td>&nbsp;</td>
						<%-- <%}%> --%>
						<!-- <td><img src="../../images/search/btn_news.gif" onclick="send_issue('news');" style="cursor:pointer"/></td>
						<td>&nbsp;</td> -->
						<td><img src="../../images/search/btn_mailsend.gif" onclick="sendMail();" style="cursor:pointer; display: none;"/></td>
						<!-- <td>&nbsp;</td>
						<td><img src="../../images/search/btn_excelsave.gif" onclick="saveExcel();" style="cursor:pointer; display: "/></td> -->
						<td><img src="../../images/search/btn_same.gif" onclick="samePackage();" style="cursor:pointer; display:none "/></td>
						
<!--						<td><input type="button" value="지우개" style="width: 88px; height: 24px" onclick="listClear('<%=uei.getM_seq()%>')"></td>-->
<%
	}else if(uei.getSearchMode().equals("DELIDX")){
%>
						<td><img src="../../images/search/btn_restoration.gif" onclick="idxProcess('revert');" style="cursor:pointer"/></td>
						<td>&nbsp;</td>
						<td><img src="../../images/search/btn_del3.gif" onclick="idxProcess('del');" style="cursor:pointer"/></td>
						<td>&nbsp;</td>
						<td align="right"><b style="color:navy">* 휴지통의 기사는 삭제 7일 후 영구삭제 됩니다.</b></td>
<%
	}
%>
					</tr>
				</table>				
				</td>
			</tr>
			<!-- 게시판 시작 -->
			<tr>
				<td>
				<table id="board_01" border="0" cellpadding="0" cellspacing="0" style="table-layout: fixed;">
<%
	if(!uei.getSearchMode().equals("DELIDX")){
%>
				<!-- 수집키워드 추가 -->
				<col width="3%"><col width="8%"><col width="38%"><col width="2%"><col width="2%"><!-- <col width="7%"> --><!-- <col width="11%"> --><!-- <col width="3%"> --><!-- <col width="12%"> -->
				<col width="3%"><col width="4%"><col width="7%">
				<!-- <col width="3%"><col width="10%"><col width="42%"><col width="2%"><col width="8%"><col width="12%"><col width="4%"><col width="4%"><col width="5%"><col width="10%"> -->		
				<!-- <col width="4%"><col width="10%"><col width="49%"><col width="4%"><col width="10%"><col width="4%"><col width="4%"><col width="5%"><col width="10%"> -->
				<!-- <col width="5%"><col width="15%"><col width="51%"><col width="4%"><col width="5%"><col width="5%"><col width="5%"><col width="10%"> -->
<%
	}else{
%>
				<col width="5%"><col width="15%"><col width="50%"><col width="10%"><col width="10%"><col width="10%"><col width="10%">
<%
	}
%>
					<tr>
						<th><input type="checkbox" name="chkAllCheck" value="" onclick="reverseAll(this.checked);"></th>
						<th style="cursor:pointer" onclick="setOrder('MD_SITE_NAME');" style="cursor:pointer">출처<%=sOrderMark1%></th>
<%
	if(!uei.getSearchMode().equals("DELIDX")){
%>
						<th style="cursor:pointer" onclick="setOrder('MD_TITLE');" style="cursor:pointer">제목<%=sOrderMark2%></th>
						<th></th>
						<th></th>
<%
		if(uei.getSearchMode().equals("ALLKEY")){
%>
						<!-- <th><span>제휴컨텐츠</span></th> -->
						<!-- <th><span>포탈메인 노출여부</span></th> -->
						<!-- <th><span>중요</span></th> -->
						<!-- <th><span>수집 키워드</span></th> -->
		<%-- <%if(uei.getMg_seq().equals("2") || uei.getMg_seq().equals("3") || uei.getMg_seq().equals("9")){ %> --%>
						<th><span style="<%=viewManager%>">관리</span></th>
	<%-- 	<%}else{ %>
						<th></th>
		<%} %> --%>
		
<%
		}
%>
						<th style="cursor:pointer" onclick="setOrder('MD_SAME_COUNT');" style="cursor:pointer">유사<%=sOrderMark3%></th>
						<th style="cursor:pointer" onclick="setOrder('MD_DATE');" style="cursor:pointer">수집시간<%=sOrderMark5%></th>
<%
	}else{
%>
						<th style="cursor:pointer" onclick="setOrder('MD_TITLE');" style="cursor:pointer">제목<%=sOrderMark2%></th>
						<th style="cursor:pointer" onclick="setOrder('MD_DATE');" style="cursor:pointer">수집시간<%=sOrderMark5%></th>
						<th style="cursor:pointer" onclick="setOrder('M_NAME');" style="cursor:pointer">삭제자<%=sOrderMark7%></th>
						<th style="cursor:pointer" onclick="setOrder('I_DELDATE');" style="cursor:pointer">삭제시간<%=sOrderMark6%></th>
<%
	}
%>
					</tr>
<%
if(alData.size() > 0){
	
		String star = "";
		String reply = "";
		String follower = "";
		String original ="";
		String folder = "";
		//String[] kValAry;
		
	for(int i = 0; i < alData.size(); i++){
		MetaBean mBean = (MetaBean) alData.get(i);
		
	   	//내용 강조
	   	String Html = "";	   	
	   	if(!uei.getKeyword().equals("")) {
	   		Html=mBean.getHighrightHtml(200,"0000CC");
	   	}else{
	   		Html=mBean.getHighrightHtml(200,"0000CC");
	   	}
	   	
	   	//관리버튼
	   	String managerButton = "";
		if(uei.getSearchMode().equals("ALLKEY")){	  		
			managerButton ="<img id=\"issue_menu_icon"+mBean.getMd_seq()+"\" width=\"18\" height=\"17\" style=\"cursor:pointer\" ";
			if(mBean.getIssue_check().equals("Y")){
				//managerButton += "src=\"../../images/search/btn_manage_on.gif\" title=\"이슈로 등록된 정보입니다.\" onclick=\"\" >";
				managerButton += "src=\"../../images/search/btn_manage_on.gif\" title=\"이슈로 등록된 정보입니다.\" onclick=\"send_issue('update','"+mBean.getMd_seq()+"');\" >";
			}else if(mBean.getIssue_check().equals("T")){
				managerButton += "src=\"../../images/search/btn_manage_temp.gif\" title=\"임시 이슈로 등록된 정보입니다.\" onclick=\"send_issue('insert','"+mBean.getMd_seq()+"');\">";
			}else{
				managerButton += "src=\"../../images/search/btn_manage_off.gif\" title=\"이슈 미등록 정보입니다.\" onclick=\"send_issue('insert','"+mBean.getMd_seq()+"');\">";
			}
		}
		
		//전체키워드검색
		if(uei.getSearchMode().equals("ALLKEY")){
			String overColor = "#F3F3F3";
        	String outColor = "";
        	String bookMarkColor = "";
        	String comfirmColor = "";
        	
        	if(bookMarkYn.equals("Y") && mBean.getMd_seq().equals(bookMarkNum)){
        		overColor = "#00bfff";
        		outColor = "#00bfff";
        		bookMarkColor = "#00bfff";
        	}
        	
        	//카페 일경우~
        	star = "";
        	if(mBean.getS_seq().equals("3555") || mBean.getS_seq().equals("4943") 
        		|| mBean.getMd_site_name().contains("네이버카페") || mBean.getMd_site_name().contains("다음카페") 
	
        	){
        		star = "<img  style='vertical-align: middle;' src='../../images/search/yellow_star.gif' style='cursor:pointer' onclick=portalSearch('"+mBean.getS_seq()+"','"+java.net.URLEncoder.encode(mBean.getMd_title(),"utf-8")+"','"+mBean.getMd_site_name()+"')>";
        	}
        	
/*         	//댓글이 있을경우
        	reply = "";
        	if( uei.getSearchMode().equals("ALLKEY") ){
	        	if(Integer.parseInt(mBean.getReply_cnt()) > 0){
	        		reply = "<strong>["+ mBean.getReply_cnt() +"]</strong>";
	        	}
        	}
        	 */
        	//트위터 일경우
        	follower = "";
        	if(!mBean.getT_followers().equals("")){
        		follower = "<font color=\"D72D2D\"><strong>[팔로워 : "+mBean.getT_followers()+"개]&nbsp;&nbsp;</strong></font>";   
        	}else{
        		follower = mBean.getT_followers();
        	}
        	//원문저장
        	original ="";        	
        	if("Y".equals(mBean.getIssue_check())){
        		if(isMgr.getOriginalType(mBean.getMd_seq()) > 0){
        			original ="<img src='../../images/search/btn_original.png' />";
        		}else{
        			original ="";
        		}
        	}
        	
        	
        	if(mBean.getMd_comfirm().equals("Y")){
        		comfirmColor = "<font id ='list"+mBean.getMd_seq()+"' color='0000CC'>" + mBean.getHtmlMd_title() + "</font>"; 
        	}else if(mBean.getMd_comfirm().equals("N")){
        		//comfirmColor = "<font id ='list"+mBean.getMd_seq()+"' color='977d46'/>";
        		comfirmColor = "<font id ='list"+mBean.getMd_seq()+"'>" + mBean.getHtmlMd_title() + "</font>";
        	}else{
        		comfirmColor = "<font id ='list"+mBean.getMd_seq()+"'>" + mBean.getHtmlMd_title() + "</font>";
        	}
        	
        	/*
        	MetaBean mbean2 = new MetaBean();
        	mbean2 = smgr.getMetaData(mBean.getMd_seq(), "");
        	
        	String title = mbean2.getMd_content();
        	title.replaceAll("\n", " ");
        	*/
        	
        	//kValAry = mBean.getK_value2().trim().split(",");
        	
%>
						<tr style="background-color:<%=bookMarkColor%>" onmouseover="this.style.backgroundColor='<%=overColor%>';" onmouseout="this.style.backgroundColor='<%=outColor%>';">
						<!--↓↓체크박스-->
						<td><input type="checkbox" name="chkData" onclick="detectChk(this.checked);sameListChk(this.checked,'<%=mBean.getMd_pseq()%>','<%=mBean.getMd_seq()%>');" value="<%=mBean.getMd_seq()%>">
						<input type="hidden" id="same_<%=mBean.getMd_seq()%>" name="chkSameCheck" value="<%=mBean.getIssue_check()%>">
						</td>
						<!--↓↓출처-->
						<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;"><font style="font-size:11px;" color="#225bd1"><%=mBean.getMd_site_name()%></font></td>
						<!--↓↓제목-->
						<td style="text-align: left;">
							<div class="board_01_tit_0<%=mBean.getMd_type()%>" style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;text-align:left;width:390px; display: inline-block;"  >
							<%=star%>
							<%
								String isStorage = mBean.getIs_storage();
								if(isStorage.equals("N")) {
									isStorage = "";
								} else {
									isStorage = "<img style='vertical-align: middle;' src='../../images/search/icon_folder.gif' style='cursor:pointer'>";
								}
							%>				
							<a onClick="hrefPop('<%=mBean.getMd_url()%>', '<%=uei.getM_seq()%>', '<%=mBean.getMd_seq()%>');" onfocus="this.blur();" href="javascript:void(0);" onmouseover="show_me(<%=i%>);" onmouseout="close_me(<%=i%>);">
							<%
								if(comfirmColor.equals("")){
									out.print(mBean.getHtmlMd_title());							
								}else{
									out.print(comfirmColor);
								}
							%>
							</a>
							<%
								String replyCnt =  mBean.getMd_reply_count();
								 if( "0".equals(replyCnt)) {
									replyCnt = "";
								} else {
									replyCnt = "[" + mBean.getMd_reply_count() + "]";
								} 
							%>
							<strong><%=replyCnt%></strong>
							</div>
							<div id=div<%=i%> style="display:none;width:200px;height:80px;">
								<%=follower + Html%>  
								
								<%
									if(keywordSearchInfo.size() > 0){
										out.print("<p>");
										for(int k = 0; k < keywordSearchInfo.size(); k++){
											HashMap bean = (HashMap)keywordSearchInfo.get(k);
											String imgMark = "";
											if(String.valueOf(bean.get("K_OP")).equals("1")){
												imgMark = "a";
											}else if(String.valueOf(bean.get("K_OP")).equals("2")){
												imgMark = "n";
											}else if(String.valueOf(bean.get("K_OP")).equals("3")){
												imgMark = "p";
											}else if(String.valueOf(bean.get("K_OP")).equals("4")){
												imgMark = "o";
											}
											
											%>
											
											<%
											if(mBean.getMd_seq().equals( String.valueOf(bean.get("MD_SEQ")))){
												%>
												<div><%=bean.get("K_VALUE1")%> - <%=bean.get("K_VALUE2")%> - <img src="../../images/search/icon_<%=imgMark%>.jpg" style="vertical-align: middle"><%=bean.get("K_VALUE3")%></div>	
												<%-- <div><%=bean.get("K_VALUE1")%>" - "<%=bean.get("K_VALUE2")%> - <img src="<%=SS_URL%>/images/admin/keyword/icon_<%=imgMark%>.gif" style="vertical-align: middle"><%=bean.get("K_VALUE3")%></div> --%>
												<%
											}
										}
									}
								%>       
							</div>
							
							<%-- <p class="board_01_tit_0<%=mBean.getMd_type()%>"><a onClick="hrefPop('<%=mBean.getMd_url()%>', '<%=uei.getM_seq()%>', '<%=mBean.getMd_seq()%>');" onfocus="this.blur();" href="javascript:void(0);" onmouseover="show_me(<%=i%>);" onmouseout="close_me(<%=i%>);"><%=comfirmColor%></a><%=star%><%=reply%></p>
							<div id=div<%=i%> style="display:none;width:200px;height:80px;">
								<%=follower + Html%>     
							</div> --%>
						</td>
						<!--↓↓원문보기버튼-->
						<td>
							<%=isStorage %>
						</td> 
						<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;" id="title_<%=i%>">						
<!--							<img src="../../images/search/ico_original.gif" style="cursor:pointer;" onclick="originalView('<%=mBean.getMd_seq()%>','<%=mBean.getS_seq()%>','<%=java.net.URLEncoder.encode(mBean.getMd_title(),"utf-8")%>');">-->
						
						<%if(ipChk){out.print("<img id='img"+i+"' src=\"../../images/search/icon00.png\" style=\"cursor:pointer;\" onclick=\"originalView('"+mBean.getMd_seq()+"','"+uei.getM_seq()+"');\">");}%>
							<%-- <div id="img<%=i%>_AX_tooltip" class="AXTooltipContent">
								<%=su.toHtmlString(mBean.getMd_content())%>								
							</div>	 --%>
						</td>
						<%-- <!--↓↓제휴컨텐츠-->
						<%if(mBean.getSga_name() == null){%>
							<td></td>
						<%}else{ %>
							<td><%=mBean.getSga_name()%></td>
						<%} %> --%>
						<!--↓↓포탈메인 노출여부-->
						<%-- <%if(mBean.getPortal_check() == null){%>
							<td></td>
						<%}else{ %>
							<td><%=mBean.getPortal_check()%></td>
						<%} %> --%>
						<!--↓↓중요버튼-->
						<%-- <td><input type="checkbox" name="chkData_important" onclick="importantChk(this.checked,this.value);" value="<%=mBean.getMd_seq()%>" <% if(mBean.getMd_important().equals("1")){%>checked<%}%> >
						</td> --%>
						<!--↓↓수집 키워드-->
						<%-- <td>
						<%
						if(kValAry.length >= 3){
							out.print(kValAry[0]+","+kValAry[1]+","+kValAry[2]);
						}else if(kValAry.length == 2){
							out.print(kValAry[0]+","+kValAry[1]);
						}else{ 
							out.print(kValAry[0]);
						}
						%>
						</td> --%>
						<!--↓↓관리버튼-->
						<td>
<%-- 						<%if(uei.getMg_seq().equals("2") || uei.getMg_seq().equals("3") || uei.getMg_seq().equals("9")){ %> --%>
						<span style="<%=viewManager%>"><%=managerButton%></span><%if(viewManager.equals("")){%>&nbsp;<%}%>
<%-- 						<%}%> --%>
						</td>
						<!--↓↓유사-->
						<td>
					<%
						if(mBean.getMd_same_count().equals("0")){
												out.print(mBean.getMd_same_count());
						}else{
					%>
							<a href="javascript:openSameList('<%=mBean.getMd_pseq()%>','<%=mBean.getMd_seq()%>','<%=uei.getSearchMode()%>');"><%=mBean.getMd_same_count()%></a>
<%
	}
%>
						</td>
						<!--↓↓수집시간-->
						<td><%=mBean.getFormatMd_date("MM-dd HH:mm")%></td>
					</tr>
					<!--↓↓원문 미리보기-->
					<%-- <tr>
						<td class="same" colspan="7">							
							<div>
								<table width="1100" border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed;">
								<col width="3%"><col width="8%"><col width="40%"><col width="2%"><col width="7%"><col width="11%"><col width="3%"><col width="12%"><col width="3%"><col width="4%"><col width="7%">
									<tr>
										<td colspan="2"></td>
										<td bgcolor="FFFFCC" colspan="4" ><%=follower + Html%></td>
										<td colspan="5"></td>
									</tr>
								</table>
							</div>
						</td>
					</tr> --%>
					<tr>
						<td class="same" colspan="7">							
							<div id="SameList_<%=mBean.getMd_pseq()%>" style="display:block;"></div>
						</td>
					</tr>
<%
		//휴지통
		}else if(uei.getSearchMode().equals("DELIDX")){
%>
					<tr>
						<td><input type="checkbox" name="chkData" value="<%=mBean.getMd_seq()%>"></td>
						<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;"><%=mBean.getMd_site_name()%></td>
						<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">
							<p class="board_01_tit_0<%=mBean.getMd_type()%>">
								<%=mBean.getHtmlMd_title()%>
							</p>
							<div id=div<%=i%> style="display:none;width:200px;height:80px;">
								<%=Html%>       
							</div>
						</td>
						<td><%=mBean.getFormatMd_date("MM-dd HH:mm")%></td>
						<td><%=mBean.getM_name()%></td>
						<td><%=mBean.getFormatI_deldate("MM-dd HH:mm")%></td>
					</tr>
<%
		}
	}
}
%>
				</table>
				</td>
			</tr>
			
			<tr style="display: <%=Jnone%>;">
				<td style="height:40px;">
				<table border="0" cellpadding="0" cellspacing="0" >
					<tr>
						<td><img src="../../images/search/btn_allselect.gif" onclick="reverseAll(!document.getElementById('fCheck').chkAllCheck.checked);"/></td>
						<td>&nbsp;</td>
<%
	if(uei.getSearchMode().equals("ALLKEY")){
%>  
						<td><img src="../../images/search/btn_del.gif" onclick="idxProcess('truncate');" style="cursor:pointer"/></td>
						<td>&nbsp;</td>
<!-- 						<td><img src="../../images/search/btn_bookmark.gif" onclick="saveBookMark();" style="cursor:pointer"/></td>
						<td>&nbsp;</td>
						<td><img src="../../images/search/btn_bookmark_search.gif" onclick="doBookMarkSearch();" style="cursor:pointer"/></td>
						<td>&nbsp;</td> -->
						<%-- <%if(uei.getMg_seq().equals("2") || uei.getMg_seq().equals("3") || uei.getMg_seq().equals("9")){ %> --%>
						<td><img src="../../images/search/btn_multi.gif" onclick="send_issue('multi');" style="cursor:pointer"/></td>
						<td>&nbsp;</td>
						<td><img src="../../images/search/btn_excelsave.gif" onclick="saveExcel();" style="cursor:pointer; display: "/></td>
						<td>&nbsp;</td>
						<td><img src="../../images/search/btn_tempReg.gif" onclick="multiTempReg();" style="cursor:pointer"/></td>
						<td>&nbsp;</td>
						<td><img src="../../images/search/btn_storage.gif" onclick="showStorage('showList');" style="cursor:pointer"/></td>
						<td>&nbsp;</td>
						<%-- <%}%> --%>
						<!-- <td><img src="../../images/search/btn_news.gif" onclick="send_issue('news');" style="cursor:pointer"/></td>
						<td>&nbsp;</td> -->
						<td><img src="../../images/search/btn_mailsend.gif" onclick="sendMail();" style="cursor:pointer; display: none;"/></td>
						<td><img src="../../images/search/btn_same.gif" onclick="samePackage();" style="cursor:pointer; display: none"/></td>
<!--						<td><input type="button" value="지우개" style="width: 88px; height: 24px" onclick="listClear('<%=uei.getM_seq()%>')"></td>-->
<%
	}else if(uei.getSearchMode().equals("DELIDX")){
%>
						<td><img src="../../images/search/btn_restoration.gif" onclick="idxProcess('revert');" style="cursor:pointer"/></td>
						<td>&nbsp;</td>
						<td><img src="../../images/search/btn_del3.gif" onclick="idxProcess('del');" style="cursor:pointer"/></td>
						<td>&nbsp;</td>
						<td align="right"><b style="color:navy">* 휴지통의 기사는 삭제 7일 후 영구삭제 됩니다.</b></td>
<%
	}
%>
					</tr>
				</table>				
				</td>
			</tr>
			
			
			
			
			
			<!-- 게시판 끝 -->
		</table>
		</form>
<%-- 		<%
			if(!uei.getSearchMode().equals("DELIDX")){
		%>
			<table style="width:1100px;" border="0" cellpadding="0" cellspacing="0">
		<%
			}else{
		%>
			<table style="width:820px;" border="0" cellpadding="0" cellspacing="0">
		<%
			}
		%> --%>
		<table style="width:820px;" border="0" cellpadding="0" cellspacing="0">
			<!-- 페이징 -->
			<tr>
				<td >
				<table align="center" style="padding-top:10px;" border="0" cellpadding="0" cellspacing="1">
					<tr>
						<td>
							<table id="paging" border="0" cellpadding="0" cellspacing="2">
								<tr>
									<%=PageIndex.getPageIndex(iNowPage,iTotalPage,"","" )%>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				</td>
			</tr>
			<!-- 페이징 -->
		</table>
		</td>
		<!-- 퀵 -->
		<!-- <td style="background:#eaeaea;" >
			<table class="quick_bg" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="quick_bg2" valign="top"><img src="../../images/common/quick_bg_top.gif" /></td>
				</tr>
			</table>
		</td> -->
		<!-- 퀵 -->
	</tr>
</table>
<%-- 달력 테이블 --%>
<table width="162" border="0" cellspacing="0" cellpadding="0" id="calendar_conclass" style="position:absolute;display:none;">
	<tr>
		<td><img src="../../images/calendar/menu_bg_004.gif" width="162" height="2"></td>
	</tr>
	<tr>
		<td align="center" background="../../images/calendar/menu_bg_005.gif"><table width="148" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="6"></td>
			</tr>                                		
			<tr>
				<td id="calendar_calclass">
				</td>
			</tr>
			<tr>
				<td height="5"></td>
			</tr>
		</table></td>
	</tr>
	<tr>
		<td><img src="../../images/calendar/menu_bg_006.gif" width="162" height="2"></td>
	</tr>
</table>

<br><br><br><br>
<iframe id="if_samelist" name="if_samelist" width="0" height="0" src="about:blank"></iframe>
<iframe id="proceeFrame" name="proceeFrame" width="0" height="0"></iframe>
<script>
	//차트 그리기
	makeChart.barChart('d', 'chartArea', category, data, true, selectDate, '#669BD0', '#CCCCCC', '13', false);
</script>
</body>
</html>
<%
	}catch(Exception e){
		e.printStackTrace();
	}
%>