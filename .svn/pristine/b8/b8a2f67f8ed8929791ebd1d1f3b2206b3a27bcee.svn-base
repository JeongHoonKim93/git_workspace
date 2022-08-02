<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.ArrayList
                 ,risk.util.Log
                 ,risk.util.StringUtil
                 ,risk.util.ParseRequest
                 ,risk.util.DateUtil
                 ,risk.search.PortalBean
                 ,risk.search.PortalMgr
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

<%@include file="../inc/sessioncheck.jsp" %>
<%
	//페이지에 사용할 변수 선언 부분
	StringUtil      su = new StringUtil();
    ParseRequest    pr = new ParseRequest(request);
    DateUtil        du = new DateUtil();
    siteDataInfo   sdi = null;
    siteGroupInfo  sgi = null;
    PortalMgr     pmgr = new PortalMgr();
    pr.printParams();
    
    String ipAddress = request.getRemoteAddr();
    boolean ipChk = false;
/* 	//소현씨 집 IP(2/25일 요청) 180.67.12
	if(ipAddress.indexOf("1.215.131") > -1 || ipAddress.indexOf("127.0.0.1") > -1 || ipAddress.indexOf("180.67.12") > -1){
		ipChk = true;
	} */
	// 원문보기 권한 줄 ID
	final String[] id_chk = {"devel", "jongwon", "cwjeong", "bae", "shbae"};
	
	//원문보기 권한 부여
	if(su.inarray(id_chk, SS_M_ID)){
		ipChk = true;
	} 
	
try{    
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
        
        uei.setStime("0");
        uei.setEtime("23");
    }

    if(uei.getStime()==null || uei.getStime().equals(""))  uei.setStime("0");
    if(uei.getEtime()==null || uei.getEtime().equals(""))  uei.setEtime("23");
    
    // 검색단어    
    if( pr.getString("sKeyword","").equals(""))
    	uei.setKeyword("");   
    else{
    	uei.setKeyword(su.Trim(request.getParameter("sKeyword")).replaceAll("[ ]{1,}|[ ]{1,}&\\?[ ]{1,}", "&").replaceAll("\\&\\&|\\&[ ]{1,}|[ ]{1,}&\\&","&").replaceAll("\\&\\||\\|\\&","|").replaceAll("\\&\\-|\\-\\&","-"));
    }
    
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
        
        uei.setStime("0");
        uei.setEtime("23");
    }
    
    uei.setSt_interval_day( pr.getString("Period",uei.getSt_interval_day())) ;
 
    String sOrder       = pr.getString("sOrder","PM_DATE");
    String sOrderAlign  = pr.getString("sOrderAlign","DESC"); 
    
    uei.setOrder( pr.getString("sOrder","PM_DATE") );
    uei.setOrderAlign( pr.getString("sOrderAlign","DESC") );
    
    uei.setSt_list_cnt( pr.getString("rowcnt",uei.getSt_list_cnt()) );   
    int iNowPage        = pr.getInt("nowpage",1);
    
    //타이머 설정 변수
    String sTimer       = "Y";
    uei.setSt_reload_time(pr.getString("interval", uei.getSt_reload_time() ));

    //지금까지 설정 내역을 세션에 저장
    session.removeAttribute("ENV");
    session.setAttribute("ENV",uei);
    
    /**
    * 필드별 정렬
    */
   	String sOrderImg   = "";
    String sOrderMark1 = "";
    String sOrderMark2 = "";
    String sOrderMark3 = "";
    String sOrderMark4 = "";

    if ( uei.getOrderAlign().equals("ASC" ) ) {
        sOrderImg = "▲";
    } else if( uei.getOrderAlign().equals("DESC" ) ) {
        sOrderImg = "▼";
    }   

    if(uei.getOrder()!=null){
	    if ( uei.getOrder().equals("PM_SITE") )             {
	        sOrderMark1 = sOrderImg;
	    } else if ( uei.getOrder().equals("PM_BOARD") )     {
	        sOrderMark2 = sOrderImg;
	    } else if ( uei.getOrder().equals("PM_TITLE") )      {
	        sOrderMark3 = sOrderImg;
	    } else if ( uei.getOrder().equals("PM_DATE") )      {
	        sOrderMark4 = sOrderImg;
	    }
    }

    //검색 사이트 검색

    String keywordKind = pr.getString("sKeywordKind","");
    ArrayList  alData = new ArrayList();
	
    alData = pmgr.getSearchPortalKeyword(iNowPage, Integer.parseInt(uei.getSt_list_cnt()), keywordKind, uei.getKeyword(), sOrder, sOrderAlign, uei.getDateFrom(), uei.getDateTo(), uei.getStime(), uei.getEtime());
	int iPageCnt = pmgr.pBeanPageCnt;
    int iDataCnt = pmgr.pBeanDataCnt;
    int iTotalCnt= pmgr.pBeanTotalCnt;

    int iTotalPage      = iTotalCnt / Integer.parseInt(uei.getSt_list_cnt());
    if ( ( iTotalCnt % Integer.parseInt(uei.getSt_list_cnt()) ) > 0 ) iTotalPage++;
    

%>

<html>
<head>
<title><%=SS_TITLE%></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="../../css/base.css" type="text/css">
<style>
iframe.hide { border:0px solid red; position:absolute; top:0px; left:0px; z-index:-99; width:148px; height:150px; filter: alpha(opacity=0);}

</style>
<script src="<%=SS_URL%>js/jquery.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/ajax.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/popup.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/Calendar.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/timer.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/input_date.js" type="text/javascript"></script>
<script language="JavaScript" type="text/JavaScript">
<!--
    var selecedOrder = "<%=uei.getOrder()%>";
    var click = "";
    var sCurrDate = '<%=sCurrDate%>';
    /**
    * 검색 SUBMIT
    */
    function doSubmit(){
    	var f = document.getElementById('fSend');
    	f.target = '';
    	f.action = "search_main_portalKeyword.jsp";
		if((f.sOrder.value == selecedOrder) && (click == "TRUE")){
			if(f.sOrderAlign.value == "ASC"){
				f.sOrderAlign.value = "DESC";
			}else{
				f.sOrderAlign.value = "ASC";
			}
		}else{
			f.sOrder.value = selecedOrder;
		}
		f.submit();
    }

    /**
    * 페이지처리
    */
    function pageClick(paramUrl){
    	var f = document.getElementById('fSend');
    	f.target = '';
        f.action = "search_main_portalKeyword.jsp" + paramUrl;
		if((f.sOrder.value == selecedOrder) && (click == "TRUE")){
			if(f.sOrderAlign.value == "ASC"){
				f.sOrderAlign.value = "DESC";
			}else{
				f.sOrderAlign.value = "ASC";
			}
		}else{
			f.sOrder.value = selecedOrder;
		}
		f.submit();
        //doSubmit();
    }

    /**
    * 검색
    */
    function doSearch(){
        document.fSend.nowpage.value = '1';
        doSubmit();
    }
    
    /**
    * 검색기간
    */
    function setDateTerm( day ) {
    	
        var f = document.getElementById('fSend');
        f.sDateFrom.value   = getdate( day );
        f.sDateTo.value     = sCurrDate;
        
        if(day == 0){
        	f.stime.value = '0';
        	f.etime.value = '23';
        }else{
        	f.stime.value = '<%=uei.getStime()%>';
        	f.etime.value = '<%=uei.getEtime()%>';
        }
        
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
	* URL열기
	*/
	var chkPop = 1;
	function hrefPop(url, m_seq, md_seq){
		//window.open(url,'hrefPop'+chkPop,'');
		window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop'+chkPop,'');
		chkPop++;
		
/* 		// 열어본 페이지는 DB저장 및 색상변경
		send_comfirm.mode.value = 'insert';
		send_comfirm.m_seq.value = m_seq;
		send_comfirm.md_seq.value = md_seq;
		send_comfirm.target = 'proceeFrame';
		send_comfirm.action = 'search_comfirm_prc.jsp';
		send_comfirm.submit();	 */
	}


	function timerStart(){
		var f = document.getElementById('fSend');
		if(f.timer.value == 'Y'){
			setTimer(<%=uei.getSt_reload_time()%>);
		}
	}
	window.onload = timerStart;

	function saveExcel() {

		var f = document.fSend;
        f.action = "portal_download_excel.jsp";
        f.target = 'proceeFrame';
        f.submit();
		//f.action = 'search_main_contents.jsp';
		//f.target = '';

    }
	
//-->

</script>
</head>
<body style="margin-left: 15px">

<!--열어본 페이지 색상변경 및  DB저장-->
<!-- <form name="send_comfirm" id="send_comfirm" method="post">
	<input name="m_seq" type="hidden">
	<input name="md_seq" type="hidden">
	<input name="md_seqs" type="hidden">
	<input name="mode" type="hidden">
</form> -->

<table style="height:100%; width:100%;" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="body_bg_top" valign="top">
		<table style="width:820px;" border="0" cellpadding="0" cellspacing="0">
			<!-- 타이틀 시작 -->
			<tr>
				<td class="tit_bg" style="height:67px;padding-top:20px;">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="../../images/search/tit_icon.gif" /><img src="../../images/search/tit_0104.gif" /></td>
						<td align="right">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="navi_home">HOME</td>
								<td class="navi_arrow">정보검색</td>
								<td class="navi_arrow2">포탈키워드검색</td>
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
				<form name="fSend" id="fSend" action="search_main_portalKeyword.jsp" method="post"  style="margin-bottom: 0px">
					<input type="hidden" name="timer" value="<%=sTimer%>" >
					<input type="hidden" name="interval" value="<%=uei.getSt_reload_time()%>" >
					<input type="hidden" name="sOrder" value="<%=uei.getOrder()%>">
					<input type="hidden" name="sOrderAlign" value="<%=uei.getOrderAlign()%>">
					<input type="hidden" name="Period" value="<%=uei.getSt_reload_time()%>">
					<input name="nowpage" type="hidden" value="<%=iNowPage%>">          
					<input name="iTotalCnt" type="hidden" value="<%=iTotalCnt%>">
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
				            	<option value="2" <%if("2".equals(keywordKind)){out.print("selected");} %>>출처</option>
				            	</select>	
				            	<input name="sKeyword" type="text" class="textbox3" value="<%=uei.getKeyword()%>" style="width:400px;" onkeydown="javascript:if(event.keyCode == 13){doSearch();}"/>		            
				            	<img src="../../images/search/search_btn.gif" width="61" height="22" align="absmiddle" onclick="doSearch();"  style="cursor: pointer;"/></td>
				          </tr>
				        </table></td>
				        <td width="12"><img src="../../images/search/table_bg_03.gif" width="12" height="35" /></td>
				      </tr>
				    </table></td>
				  </tr>
				  <tr id="hide1" <%if(uei.getSt_menu().equals("0")){out.print("style=\"display:none\"");}%>>
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
				                    <td width="55"><select name="stime"><%for(int i=0; i<24; i++){ if(i==Integer.parseInt(uei.getStime())){out.print("<option value="+ i +" selected>"+i+"시</option>");}else{out.print("<option value="+i+">"+i+"시</option>");}} %></select> </td>
				                    <td>~</td>
				                    <td width="95"><input style="width:90px;text-align:center;" class="textbox" type="text" name="sDateTo" id="sDateTo" value="<%=uei.getDateTo()%>"></td>
				                    <td width="30"><img src="../../images/search/btn_calendar.gif" style="cursor:pointer" onclick="calendar_sh(document.getElementById('sDateTo'))"/></td>
				                    <td width="55"><select name="etime"><%for(int i=0; i<24; i++){ if(i==Integer.parseInt(uei.getEtime())){out.print("<option value="+ i +" selected>"+i+"시</option>");}else{out.print("<option value="+i+">"+i+"시</option>");}} %></select></td>
				                    <td style="padding-left:10px;"><img src="../../images/search/btn_calendar_1day.gif" width="38" height="24"  onclick="setDateTerm(0);" style="cursor:pointer"/><img src="../../images/search/btn_calendar_3day.gif" width="38" height="24" onclick="setDateTerm(2);" style="cursor:pointer"/><img src="../../images/search/btn_calendar_7day.gif" width="38" height="24" onclick="setDateTerm(6);" style="cursor:pointer"/></td>
				                  </tr>
				                </table></td>
				                </tr>
				            </table></td>
				          </tr>
	<!-- 			          <tr>
				            <td><img src="../../images/search/dotline.gif" width="798" height="3" /></td>
				          </tr> -->
				        </table></td>
				        <td width="1" bgcolor="D3D3D3"></td>
				      </tr>
				    </table></td>
				  </tr>
				 <tr id="hide2" <%if(uei.getSt_menu().equals("0")){out.print("style=\"display:none\"");}%>>
				    <td height="1" bgcolor="D3D3D3"></td>
				  </tr>
				</table>
				</form>
				</td>
			</tr>
			<!-- 검색 끝 -->
			<tr>
				<td style="height:45px;background:url('../../images/search/list_top_line.gif') bottom repeat-x;">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="../../images/search/icon_search_bullet.gif"> <strong> 검색결과 </strong><strong> <%=iTotalCnt%></strong> 건, <strong><%=iTotalCnt==0?0:iNowPage%></strong>/<strong><%=iTotalPage%></strong> pages</td>
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
		</table>
		<form name="fCheck" id="fCheck" target="_blank" method="post" style="margin-top: 0px;">
		<table style="width:820px;" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td style="height:40px;">	
					<table border="0" cellpadding="0" cellspacing="0" >
					<tr>
						<td>&nbsp;</td>
						<td><img src="../../images/search/btn_excelsave.gif" onclick="saveExcel();" style="cursor:pointer; display: "/></td>
					</tr>
					</table>
				</td>
			</tr>
			<!-- 게시판 시작 -->
			<tr>
				<td>
				<table id="board_01" border="0" cellpadding="0" cellspacing="0" style="table-layout: fixed;">
				<col width="15%"><col width="5%"><col width="60%"><col width="20%">
				<tr>
					<th style="cursor:pointer" onclick="setOrder('PM_SITE');" style="cursor:pointer">출처<%=sOrderMark1%></th>
					<th></th>
					<%-- <th style="cursor:pointer" onclick="setOrder('PM_BOARD');" style="cursor:pointer">상세출처<%=sOrderMark2%></th> --%>
					<th style="cursor:pointer" onclick="setOrder('PM_TITLE');" style="cursor:pointer">제목<%=sOrderMark3%></th>
					<th style="cursor:pointer" onclick="setOrder('PM_DATE');" style="cursor:pointer">수집시간<%=sOrderMark4%></th>
				</tr>
<%
if(alData.size() > 0){
	for(int i = 0; i < alData.size(); i++){
		PortalBean pBean = (PortalBean) alData.get(i);
			   	
		String overColor = "#F3F3F3";
        String outColor = "";
        String bookMarkColor = "";
        String comfirmColor = "";

%>
					<tr onmouseover="this.style.backgroundColor='<%=overColor%>';" onmouseout="this.style.backgroundColor='<%=outColor%>';">
						<!--↓↓출처-->
						<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;"><font style="font-size:11px;" color="#225bd1"><%=pBean.getPm_site().toString().replaceAll("<","&lt;").replaceAll(">","&gt;")%></font></td>
						<%-- <!--↓↓상세출처-->
						<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;"><font style="font-size:11px;" color="#225bd1"><%=pBean.getPm_board()%></font></td> --%>
						<td></td>
						<!--↓↓제목-->
						<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">
							<p class="board_01_tit_00"><a onClick="hrefPop('<%=pBean.getPm_url()%>', '<%=uei.getM_seq()%>', '<%=pBean.getPm_seq()%>');" onfocus="this.blur();" href="javascript:void(0);" onmouseover="show_me(<%=i%>);" onmouseout="close_me(<%=i%>);"><%=pBean.getHtmlPm_title()%></a></p>
						</td>
						<!--↓↓수집시간-->
						<td><%=pBean.getFormatPm_date("MM-dd HH:mm")%></td>
					</tr>
	<%
		}
	}
	%>
				</table>
				</td>
			</tr>
			<!-- 게시판 끝 -->
		</table>
		</form>
		<table style="width:820px;" border="0" cellpadding="0" cellspacing="0">
			<!-- 페이징 -->
			<tr>
				<td align="center">
				<table style="padding-top:10px;" border="0" cellpadding="0" cellspacing="1">
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
</body>
</html>
<%
	}catch(Exception e){
		e.printStackTrace();
	}
%>