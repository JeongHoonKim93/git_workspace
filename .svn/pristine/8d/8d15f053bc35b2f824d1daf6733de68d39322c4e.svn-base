<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="risk.admin.member.MemberDao"%>
<%@page import="risk.util.ParseRequest"%>
<%@page import="risk.search.userEnvMgr"%>
<%@page import="risk.search.userEnvInfo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="risk.util.DateUtil"%>
<%@ include file="/riskv3/inc/sessioncheck.jsp" %>
<jsp:include page="../inc/inc_page_top.jsp" flush="false" />
<%
ParseRequest    pr = new ParseRequest(request);
//기간설정
DateUtil du = new DateUtil();
String currentDate = du.getCurrentDate("yyyy-MM-dd");
//currentDate = du.addDay_v2(currentDate, -1);

String yesteray = du.addDay(du.getCurrentDate(),-1,"yyyy-MM-dd");	

String preWeekDay = du.addDay_v2(currentDate, -6);
String sDate = preWeekDay;
String eDate = currentDate;

//이슈설정
//MainMgr mgr = new MainMgr();
String code = "1";
ArrayList<String[]> issuelist =  mgr.getIssueList(code);

//사용자 기본 환경을 조회한다.
//없으면  NCS기본 유저의 환경을 조회하여 가져온다.

/* userEnvInfo uei = null;
uei     = (userEnvInfo) session.getAttribute("ENV"); */

String sCurrDate    = du.getCurrentDate();
/* String sInit = (String)session.getAttribute("INIT");

if ( sInit != null  && sInit.equals("INIT") ) {
    userEnvMgr uem = new userEnvMgr();
    uei = uem.getUserEnv((String)session.getAttribute("SS_M_ID"));
    session.removeAttribute("INIT");
	
} */

//타이머 설정 변수
/* String sTimer = "Y";
String interval = pr.getString("interval", uei.getSt_reload_time());
uei.setSt_reload_time(interval);

if("0".equals(uei.getSt_reload_time())){
	sTimer = "N";
} */

MemberDao mDao = new MemberDao();
String mgseq = mDao.getMgseq(SS_M_NAME);
String hcode = "1";
if(mgseq.equals("71")) {
	hcode = "1";
} else if(mgseq.equals("73")) {
	hcode = "2";
} 
//String ua=request.getHeader("User-Agent");
String ua=request.getHeader("User-Agent").toLowerCase();

//System.out.print("LOG User-Agent : " + ua);

//접속 핸드폰

boolean mobile1 = ua.matches( ".*(iphone|ipod|ipad|android|windows ce|blackberry|symbian|windows phone|webos|opera mini|opera mobi|polaris|iemobile|lgtelecom|nokia|sonyericsson).*");
boolean mobile2 = ua.matches(".*(lg|samsung).*");

//PC접속인 경우
/* if( !mobile1 && !mobile2 ){
	if(!mDao.chk_ip(request.getRemoteAddr())){
		out.print("<SCRIPT Language='JavaScript'>		\n");
		out.print("document.location = '../../dashboard/view/error/err_auth.jsp';	\n");
		out.print("</SCRIPT>	\n");	
		return;
	}	
//모바일 접속인 경우 - mobileCube만 접속 허용 - 2021.05.06 고객사 배윤숙 CS팀 (메일 요청)
}else{
	if(!ua.matches(".*(mobilecube).*")){
		out.print("<SCRIPT Language='JavaScript'>		\n");
		out.print("document.location = '../../error/err_auth.jsp';	\n");
		out.print("</SCRIPT>	\n");		
		return;
	}
} */

//지금까지 설정 내역을 세션에 저장
/* session.removeAttribute("ENV");
session.setAttribute("ENV",uei); */ 

%>
<jsp:include page="../inc/inc_page_top.jsp" flush="false" />


<body id="pdf_wrap">   <!-- pdf인경우 'is-pdf' 클래스 사용(프론트에서 컨트롤) -->
	<div id="wrap" class=<%if(mgseq.equals("71")) { %>"is-card" <%} else if(mgseq.equals("73")) { %> "is-capital" <%}else { %>"is-card" <%} %>>   <!-- 카드 : 'is-card' / 캐피탈 : 'is-capital' / 커머셜 : 'is-commercial' -->
		<!-- Include HEADER -->
		<jsp:include page="../inc/inc_header.jsp" flush="false" />
		<!-- // Include HEADER -->	
		<script type="text/javascript" src="./js/index.js"></script> 

		<!-- Include Devels -->
		<%-- <jsp:include page="../inc/inc_devels.jsp" flush="false" /> --%>
		
		
		<script>
			/* function timerStart($time){
				if($("#timer").val() == 'Y'){
					setTimer($time);
				}
			} */
			
			/* $(document).ready(function() {				
				timerStart();
			}); */
			
/*			  function doSubmit(){				
				location.reload();
				main.hndl_search();
			}*/   
		</script>		
		<!-- // Include Devels -->
		
		<div id="container">
			<h2 id="page_title" class="ui_invisible">&nbsp;</h2>
			<div id="content" class="page_main">

				<!-- Search -->
				<section id="top_searchs" class="ui_searchs is-only-web">
					<h3 class="ui_invisible">검색조건</h3>
					<div class="wrap">
						<!-- 검색조건 설정 -->
						<div class="searchs_inputs">
							<form id="searchs_frm">
							<input type="hidden" name="sg_seqs" value="">
							<input type="hidden" name="sg_name" value="">
							<input type="hidden" name="company_code" id ="company_code" value="<%= hcode %>">
							<input type="hidden" name="md_seq" value=""/>							
								<table>
									<colgroup>
                                        <col style="width:62px">
                                        <col style="width:209px">
                                        <col style="width:62px">
                                        <col>
                                        <col style="width:100px">
									</colgroup>
									<tbody>
                                        <tr>
                                            <th><span>기간</span></th>
                                            <td>
                                                <div class="ui_datepicker_range" data-range="5Y">		<!-- 기간제한(최종날짜 기준) : Y : 년, M : 월, 숫자만 : 일 -->
                                                    <div class="input_wrap">
                                                        <div class="input" readonly style="min-width: 189px">
                                                            <input id="ts_dr_result" type="text" class="date_result"><label for="ts_dr_result">검색기간</label>		<!-- 선택시 input에 'active'클래스 추가 -->
                                                        </div>
                                                    </div>
                                                    <div class="calendars">
                                                        <div class="calendars_container">
                                                            <div class="wrap">
                                                      		  <div id="ts_dr_s" class="dp_wrap searchs_dp_start" data-date="<%=yesteray %>"></div>
                                                        	  <div id="ts_dr_e" class="dp_wrap searchs_dp_end" data-date="<%=yesteray %>"></div>                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </td>
                                            <th><span>채널</span></th>
                                            <td>
                                                <div class="dcp"><input id="ts_chns_all" type="checkbox" data-grp="ts_chns" class="allChecker"><label for="ts_chns_all"><strong>전체</strong></label></div>
                                                <div class="dcp"><input id="ts_chns_01" type="checkbox" data-grp="ts_chns" class="Checker" value="93"><label for="ts_chns_01"><strong>언론</strong></label></div>
                                                <div class="dcp"><input id="ts_chns_02" type="checkbox" data-grp="ts_chns" class="Checker" value="95" checked><label for="ts_chns_02"><strong>카페</strong></label></div>                                      
                                                <div class="dcp"><input id="ts_chns_03" type="checkbox" data-grp="ts_chns" class="Checker" value="94"><label for="ts_chns_03"><strong>블로그</strong></label></div>
                                                <div class="dcp"><input id="ts_chns_04" type="checkbox" data-grp="ts_chns" class="Checker" value="99"><label for="ts_chns_04"><strong>트위터</strong></label></div>
                                                <div class="dcp"><input id="ts_chns_05" type="checkbox" data-grp="ts_chns" class="Checker" value="97" checked><label for="ts_chns_05"><strong>커뮤니티</strong></label></div>
                                                <div class="dcp"><input id="ts_chns_06" type="checkbox" data-grp="ts_chns" class="Checker" value="100"><label for="ts_chns_06"><strong>페이스북</strong></label></div>
                                                <div class="dcp"><input id="ts_chns_07" type="checkbox" data-grp="ts_chns" class="Checker" value="102"><label for="ts_chns_07"><strong>유튜브</strong></label></div>
                                            </td>
                                            <td rowspan="2">
                                                <button id="btn_search" type="button" class="ui_btn hl_black"><span>검색</span></button>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th><span>검색</span></th>
                                            <td colspan="3">
                                                <div class="dcp">
                                                    <select id="ts_keyword_type">
                                                        <option value="0">제목 + 내용</option>
                                                        <option value="1">제목</option>
                                                        <option value="2">내용</option>
                                                    </select><label for="ts_keyword_type"></label>
                                                </div>
                                                <input id="ts_keyword" type="text" class="ui_input" onkeydown="javascript:if(event.keyCode == 13){main.hndl_search();}">
                                            </td>
                                        </tr>
									</tbody>
								</table>
							</form>
						</div>
						<!-- // 검색조건 설정 -->
					</div>
				</section>
                <!-- // Search -->
                
                <!-- SideBar -->
                <aside id="sidebar" class="is-only-web">
                    <a href="#" class="ui_btn" onclick="pdfDownload(); return false;"><span class="txt">PDF 다운로드</span><span class="in_loader">Loading</span></a>

                    <ul>
                        <li><a href="#navi_01" class="navigator">Daily VOC TOP5</a></li>
                        <li><a href="#navi_02" class="navigator">연관어 분석</a></li>
                        <li><a href="#navi_03" class="navigator">정보추이</a></li>
                        <li><a href="#navi_04" class="navigator">주제별 현황</a></li>
                        <li>
                            <div class="details">
                                <input id="sidebar_detail_more" type="checkbox"><label for="sidebar_detail_more"><span>상세 항목 더보기</span></label>
                                <ul class="list">
                                    <li><a href="#navi_05" class="navigator">VOC 속성정보</a></li>
                                    <li><a href="#navi_06" class="navigator">전체 VOC 리스트</a></li>
                                    <li><a href="#navi_07" class="navigator">실시간 포탈<br>TOP 노출현황</a></li>
                                    <li><a href="#navi_08" class="navigator">포탈 댓글 현황</a></li>
                                    <li><a href="#navi_09" class="navigator">주요 이슈 정보 추이</a></li>
                                </ul>
                            </div>
                        </li>
                    </ul>

                </aside>
                <!-- // SideBar -->

				<!-- Content -->
				<div class="wrap">

                    <!-- Daily VOC TOP5 -->
					<div class="ui_row nav_sec is-pdf-page-0">
                        <div class="ui_col">
                            <section class="ui_box" id="navi_01">
                                <div class="box_header">
                                    <h3>
                                        Daily VOC TOP5
                                        <a href="#" class="btn_more is-only-web" onclick="main.getVocList(); return false;">More</a>
                                    </h3>                      <!--  popupMngr.open( '#popup_voc' );      -->        
                                    <div class="box_header_rc">
                                        <button type="button" class="ui_btn is-only-web" title="Excel Download"><span class="icon download">-</span><span class="in_loader">Loading</span></button>
                                    </div>
                                </div>
                                <div class="box_content no_pad voc_list_wrap">
                                    <!-- 상품&서비스 -->
                                    <div class="ui_row">
                                        <div class="ui_col">
                                            <div class="voc_list is-pdt ui_loader_container" id="ajax_con_11">
                                                <h4>
                                                    상품<br>&amp;<br>서비스
                                                    <span class="icon"></span>
                                                </h4>
                                                <table>
                                                    <tbody id="top5_content_1">
<!--                                                    <tr>
                                                            <td class="ff_uni_hr">1</td>
                                                            <td><span class="ui_status_box is-positive">긍정</span></td>
                                                            <td class="title">
                                                          	    <a href="#" target="_blank" class="lnk"><span class="txt">스벅 현카 어케 생각해?</span></a>
                                                          	</td>
                                                            <td>
                                                                <a href="#" class="btn_cafe_p" title="PC 버전 링크">PC 버전 링크</a>
                                                                <a href="#" class="btn_cafe_m" title="Mobile 버전 링크">Mobile 버전 링크</a>
                                                            </td>
                                                            <td>디시인사이드</td>
                                                            <td><span class="ui_status_box is-lv1">Lv.1</span></td>
                                                            <td>02/07 13:55</td>
                                                            <td>
                                                                <div class="ui_in_search is-confirm is-only-web" style="width:120px"><input id="voc_brd_txt_01_01" type="text" placeholder="대응내역"><label for="voc_brd_txt_01_01" class="ui_invisible">이슈 입력</label><button title="저장" onmousedown="console.log('');"><span>저장</span></button></div>
                                                                <span class="res_txt is-only-pdf">-</span>
                                                            </td>
                                                            <td><input id="voc_brd_issue_01_01" type="checkbox" class="toggle_issue"><label for="voc_brd_issue_01_01"></label></td>
                                                            <td class="ui_ar">
                                                                <div class="dcp ui_ac is-only-web">
                                                                    <select id="voc_brd_state_01_01" style="width:80px">
                                                                        <option value="0" selected>진행상황</option>
                                                                        <option value="1">개선완료</option>
                                                                        <option value="2">검토중</option>
                                                                        <option value="3">Drop</option>
                                                                    </select><label for="voc_brd_state_01_01"></label>
                                                                </div>
                                                                <span class="result_state is-only-pdf">-</span>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td class="ff_uni_hr">2</td>
                                                            <td><span class="ui_status_box is-negative">부정</span></td>
                                                            <td class="title">
                                                                <a href="#" class="ui_btn small btn_cafe" title="카페 바로가기"><span class="txt">M</span></a>
                                                                <a href="#" target="_blank" class="lnk"><span class="txt">스벅 현카 어케 생각해?</span></a>
                                                            </td>
                                                            <td>
                                                                <a href="#" class="btn_cafe_p" title="PC 버전 링크">PC 버전 링크</a>
                                                                <a href="#" class="btn_cafe_m" title="Mobile 버전 링크">Mobile 버전 링크</a>
                                                            </td>
                                                            <td>디시인사이드</td>
                                                            <td><span class="ui_status_box is-lv2">Lv.2</span></td>
                                                            <td>02/07 13:55</td>
                                                            <td>
                                                                <div class="ui_in_search is-confirm is-only-web" style="width:120px"><input id="voc_brd_txt_01_02" type="text" placeholder="대응내역"><label for="voc_brd_txt_01_02" class="ui_invisible">이슈 입력</label><button title="저장"><span>저장</span></button></div>
                                                                <span class="res_txt is-only-pdf">대응내역은 이렇습니다. 어쩌구 저쩌구</span>
                                                            </td>
                                                            <td><input id="voc_brd_issue_01_02" type="checkbox" class="toggle_issue"><label for="voc_brd_issue_01_02"></label></td>
                                                            <td class="ui_ar">
                                                                <div class="dcp ui_ac is-only-web">
                                                                    <select id="voc_brd_state_01_02" style="width:80px">
                                                                        <option value="0">진행상황</option>
                                                                        <option value="1" selected>개선완료</option>
                                                                        <option value="2">검토중</option>
                                                                        <option value="3">Drop</option>
                                                                    </select><label for="voc_brd_state_01_02"></label>
                                                                </div>
                                                                <span class="result_state is-only-pdf">개선완료</span>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td class="ff_uni_hr">3</td>
                                                            <td><span class="ui_status_box is-neutral">중립</span></td>
                                                            <td class="title"><a href="#" target="_blank" class="lnk"><span class="txt">스벅 현카 어케 생각해?</span></a></td>
                                                            <td>
                                                                <a href="#" class="btn_cafe_p" title="PC 버전 링크">PC 버전 링크</a>
                                                                <a href="#" class="btn_cafe_m" title="Mobile 버전 링크">Mobile 버전 링크</a>
                                                            </td>
                                                            <td>디시인사이드</td>
                                                            <td><span class="ui_status_box is-lv3">Lv.3</span></td>
                                                            <td>02/07 13:55</td>
                                                            <td>
                                                                <div class="ui_in_search is-confirm is-only-web" style="width:120px"><input id="voc_brd_txt_01_03" type="text" placeholder="대응내역"><label for="voc_brd_txt_01_03" class="ui_invisible">이슈 입력</label><button title="저장"><span>저장</span></button></div>
                                                                <span class="res_txt is-only-pdf">대응내역은 이렇습니다. 어쩌구 저쩌구</span>
                                                            </td>
                                                            <td><input id="voc_brd_issue_01_03" type="checkbox" class="toggle_issue"><label for="voc_brd_issue_01_03"></label></td>
                                                            <td class="ui_ar">
                                                                <div class="dcp ui_ac is-only-web">
                                                                    <select id="voc_brd_state_01_03" style="width:80px">
                                                                        <option value="0">진행상황</option>
                                                                        <option value="1">개선완료</option>
                                                                        <option value="2" selected>검토중</option>
                                                                        <option value="3">Drop</option>
                                                                    </select><label for="voc_brd_state_01_03"></label>
                                                                </div>
                                                                <span class="result_state is-only-pdf">검토중</span>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td class="ff_uni_hr">4</td>
                                                            <td><span class="ui_status_box is-positive">긍정</span></td>
                                                            <td class="title"><a href="#" target="_blank" class="lnk"><span class="txt">스벅 현카 어케 생각해?</span></a></td>
                                                            <td>
                                                                <a href="#" class="btn_cafe_p" title="PC 버전 링크">PC 버전 링크</a>
                                                                <a href="#" class="btn_cafe_m" title="Mobile 버전 링크">Mobile 버전 링크</a>
                                                            </td>
                                                            <td>디시인사이드</td>
                                                            <td><span class="ui_status_box is-lv4">Lv.4</span></td>
                                                            <td>02/07 13:55</td>
                                                            <td>
                                                                <div class="ui_in_search is-confirm is-only-web" style="width:120px"><input id="voc_brd_txt_01_04" type="text" placeholder="대응내역"><label for="voc_brd_txt_01_04" class="ui_invisible">이슈 입력</label><button title="저장"><span>저장</span></button></div>
                                                                <span class="res_txt is-only-pdf">대응내역은 이렇습니다. 어쩌구 저쩌구</span>
                                                            </td>
                                                            <td><input id="voc_brd_issue_01_04" type="checkbox" class="toggle_issue"><label for="voc_brd_issue_01_04"></label></td>
                                                            <td class="ui_ar">
                                                                <div class="dcp ui_ac is-only-web">
                                                                    <select id="voc_brd_state_01_04" style="width:80px">
                                                                        <option value="0">진행상황</option>
                                                                        <option value="1">개선완료</option>
                                                                        <option value="2">검토중</option>
                                                                        <option value="3" selected>Drop</option>
                                                                    </select><label for="voc_brd_state_01_04"></label>
                                                                </div>
                                                                <span class="result_state is-only-pdf">Drop</span>
                                                            </td>
                                                        </tr>
                                                        <tr><td class="ff_uni_hr">5</td><td></td><td class="title">-</td><td colspan="6"></td></tr>  -->  
										   			    <tr><td class="ff_uni_hr"></td><td></td><td class="no_over"></td><td colspan="6"></td></tr>
										  			    <tr><td class="ff_uni_hr"></td><td></td><td class="no_over"></td><td colspan="6"></td></tr>
										 			    <tr><td class="ff_uni_hr"></td><td></td><td class="no_over no_data in_list"><span class="ui_no_data_txt">데이터가 없습니다.</span></td><td colspan="6"></td></tr>
										 			    <tr><td class="ff_uni_hr"></td><td></td><td class="no_over"></td><td colspan="6"></td></tr>
												        <tr><td class="ff_uni_hr"></td><td></td><td class="no_over"></td><td colspan="6"></td></tr>		
                                                  </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- // 상품&서비스 -->

                                    <!-- 영업 -->
                                    <div class="ui_row">
                                        <div class="ui_col">
                                            <div class="voc_list is-sales ui_loader_container" id="ajax_con_12">
                                                <h4>
                                                    영업
                                                    <span class="icon"></span>
                                                </h4>
                                                <table>
                                                    <tbody id="top5_content_2">
                                    <!--                <tr><td class="ff_uni_hr">1</td><td></td><td class="title">-</td><td colspan="6"></td></tr>
                                                        <tr><td class="ff_uni_hr">2</td><td></td><td class="title">-</td><td colspan="6"></td></tr>
                                                        <tr><td class="ff_uni_hr">3</td><td></td><td class="title">-</td><td colspan="6"></td></tr>
                                                        <tr><td class="ff_uni_hr">4</td><td></td><td class="title">-</td><td colspan="6"></td></tr>
                                                        <tr><td class="ff_uni_hr">5</td><td></td><td class="title">-</td><td colspan="6"></td></tr>     -->
                                                        
										   			    <tr><td class="ff_uni_hr"></td><td></td><td class="no_over"></td><td colspan="6"></td></tr>
										  			    <tr><td class="ff_uni_hr"></td><td></td><td class="no_over"></td><td colspan="6"></td></tr>
										 			    <tr><td class="ff_uni_hr"></td><td></td><td class="no_over no_data in_list"><span class="ui_no_data_txt">데이터가 없습니다.</span></td><td colspan="6"></td></tr>
										 			    <tr><td class="ff_uni_hr"></td><td></td><td class="no_over"></td><td colspan="6"></td></tr>
												        <tr><td class="ff_uni_hr"></td><td></td><td class="no_over"></td><td colspan="6"></td></tr>		                                   
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- // 영업 -->

                                    <!-- 디지털 -->
                                    <div class="ui_row">
                                        <div class="ui_col">
                                            <div class="voc_list is-consulting ui_loader_container" id="ajax_con_13"> <!-- loading 있었음 -->
                                                <h4>
                                                    디지털
                                                    <span class="icon"></span>
                                                </h4>
                                                <table>
                                                    <tbody id="top5_content_3">
                                    <!--                <tr><td class="ff_uni_hr">1</td><td></td><td class="title">-</td><td colspan="6"></td></tr>
                                                        <tr><td class="ff_uni_hr">2</td><td></td><td class="title">-</td><td colspan="6"></td></tr>
                                                        <tr><td class="ff_uni_hr">3</td><td></td><td class="title">-</td><td colspan="6"></td></tr>
                                                        <tr><td class="ff_uni_hr">4</td><td></td><td class="title">-</td><td colspan="6"></td></tr>
                                                        <tr><td class="ff_uni_hr">5</td><td></td><td class="title">-</td><td colspan="6"></td></tr>     -->
                                                        
										   			    <tr><td class="ff_uni_hr"></td><td></td><td class="no_over"></td><td colspan="6"></td></tr>
										  			    <tr><td class="ff_uni_hr"></td><td></td><td class="no_over"></td><td colspan="6"></td></tr>
										 			    <tr><td class="ff_uni_hr"></td><td></td><td class="no_over no_data in_list"><span class="ui_no_data_txt">데이터가 없습니다.</span></td><td colspan="6"></td></tr>
										 			    <tr><td class="ff_uni_hr"></td><td></td><td class="no_over"></td><td colspan="6"></td></tr>
												        <tr><td class="ff_uni_hr"></td><td></td><td class="no_over"></td><td colspan="6"></td></tr>		                                   
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- // 디지털 -->

                                    <!-- Operation -->
                                    <div class="ui_row">
                                        <div class="ui_col">
                                            <div class="voc_list is-operation ui_loader_container" id="ajax_con_14">
                                                <h4>
                                                    Operation
                                                    <span class="icon"></span>
                                                </h4>
                                                <table>
                                                    <tbody id="top5_content_4">
                                    <!--                <tr><td class="ff_uni_hr">1</td><td></td><td class="title">-</td><td colspan="6"></td></tr>
                                                        <tr><td class="ff_uni_hr">2</td><td></td><td class="title">-</td><td colspan="6"></td></tr>
                                                        <tr><td class="ff_uni_hr">3</td><td></td><td class="title">-</td><td colspan="6"></td></tr>
                                                        <tr><td class="ff_uni_hr">4</td><td></td><td class="title">-</td><td colspan="6"></td></tr>
                                                        <tr><td class="ff_uni_hr">5</td><td></td><td class="title">-</td><td colspan="6"></td></tr>     -->
                                                        
										   			    <tr><td class="ff_uni_hr"></td><td></td><td class="no_over"></td><td colspan="6"></td></tr>
										  			    <tr><td class="ff_uni_hr"></td><td></td><td class="no_over"></td><td colspan="6"></td></tr>
										 			    <tr><td class="ff_uni_hr"></td><td></td><td class="no_over no_data in_list"><span class="ui_no_data_txt">데이터가 없습니다.</span></td><td colspan="6"></td></tr>
										 			    <tr><td class="ff_uni_hr"></td><td></td><td class="no_over"></td><td colspan="6"></td></tr>
												        <tr><td class="ff_uni_hr"></td><td></td><td class="no_over"></td><td colspan="6"></td></tr>		                                   
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- // Operation -->

                                    <!-- 금융소비자 보호법 -->
                                    <div class="ui_row">
                                        <div class="ui_col">
                                            <div class="voc_list is-cpa ui_loader_container" id="ajax_con_15">
                                                <h4>
                                                    금융소비자<br>보호법
                                                    <span class="icon"></span>
                                                </h4>
                                                <table>
                                                    <tbody id="top5_content_5">
                                    <!--                <tr><td class="ff_uni_hr">1</td><td></td><td class="title">-</td><td colspan="6"></td></tr>
                                                        <tr><td class="ff_uni_hr">2</td><td></td><td class="title">-</td><td colspan="6"></td></tr>
                                                        <tr><td class="ff_uni_hr">3</td><td></td><td class="title">-</td><td colspan="6"></td></tr>
                                                        <tr><td class="ff_uni_hr">4</td><td></td><td class="title">-</td><td colspan="6"></td></tr>
                                                        <tr><td class="ff_uni_hr">5</td><td></td><td class="title">-</td><td colspan="6"></td></tr>     -->
                                                        
										   			    <tr><td class="ff_uni_hr"></td><td></td><td class="no_over"></td><td colspan="6"></td></tr>
										  			    <tr><td class="ff_uni_hr"></td><td></td><td class="no_over"></td><td colspan="6"></td></tr>
										 			    <tr><td class="ff_uni_hr"></td><td></td><td class="no_over no_data in_list"><span class="ui_no_data_txt">데이터가 없습니다.</span></td><td colspan="6"></td></tr>
										 			    <tr><td class="ff_uni_hr"></td><td></td><td class="no_over"></td><td colspan="6"></td></tr>
												        <tr><td class="ff_uni_hr"></td><td></td><td class="no_over"></td><td colspan="6"></td></tr>		                                   
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- // 금융소비자 보호법 -->

                                    <!-- 발급/한도 -->
                                    <div class="ui_row">
                                        <div class="ui_col">
                                            <div class="voc_list is-limit ui_loader_container" id="ajax_con_17">
                                                <h4>
                                                    발급/한도
                                                    <span class="icon"></span>
                                                </h4>
                                                <table>
                                                    <tbody>
                                    <!--                <tr><td class="ff_uni_hr">1</td><td></td><td class="title">-</td><td colspan="6"></td></tr>
                                                        <tr><td class="ff_uni_hr">2</td><td></td><td class="title">-</td><td colspan="6"></td></tr>
                                                        <tr><td class="ff_uni_hr">3</td><td></td><td class="title">-</td><td colspan="6"></td></tr>
                                                        <tr><td class="ff_uni_hr">4</td><td></td><td class="title">-</td><td colspan="6"></td></tr>
                                                        <tr><td class="ff_uni_hr">5</td><td></td><td class="title">-</td><td colspan="6"></td></tr>     -->
                                                        
										   			    <tr><td class="ff_uni_hr"></td><td></td><td class="no_over"></td><td colspan="6"></td></tr>
										  			    <tr><td class="ff_uni_hr"></td><td></td><td class="no_over"></td><td colspan="6"></td></tr>
										 			    <tr><td class="ff_uni_hr"></td><td></td><td class="no_over no_data in_list"><span class="ui_no_data_txt">데이터가 없습니다.</span></td><td colspan="6"></td></tr>
										 			    <tr><td class="ff_uni_hr"></td><td></td><td class="no_over"></td><td colspan="6"></td></tr>
												        <tr><td class="ff_uni_hr"></td><td></td><td class="no_over"></td><td colspan="6"></td></tr>		                                   
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- // 발급/한도 -->
                                    
                                    <!-- FRAUD -->
                                    <div class="ui_row is-child-pdf-1" id="fraud_html">
                                        <div class="ui_col">
                                            <div class="voc_list is-fraud ui_loader_container" id="ajax_con_18">
                                                <h4>
                                                    FRAUD
                                                    <span class="icon"></span>
                                                </h4>
                                                <table>
                                                    <tbody>
                                                        <tr><td class="ff_uni_hr">1</td><td></td><td class="title">-</td><td colspan="6"></td></tr>
                                                        <tr><td class="ff_uni_hr">2</td><td></td><td class="title">-</td><td colspan="6"></td></tr>
                                                        <tr><td class="ff_uni_hr">3</td><td></td><td class="title">-</td><td colspan="6"></td></tr>
                                                        <tr><td class="ff_uni_hr">4</td><td></td><td class="title">-</td><td colspan="6"></td></tr>
                                                        <tr><td class="ff_uni_hr">5</td><td></td><td class="title">-</td><td colspan="6"></td></tr>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- // FRAUD -->

                                    <!-- 기타 --> 
                                    <div class="ui_row is-child-pdf-2">
                                        <div class="ui_col">
                                            <div class="voc_list is-other ui_loader_container" id="ajax_con_16">
                                                <h4>
                                                    기타
                                                    <span class="icon"></span>
                                                </h4>
                                                <table>
                                                    <tbody id="top5_content_6">
                                    <!--                <tr><td class="ff_uni_hr">1</td><td></td><td class="title">-</td><td colspan="6"></td></tr>
                                                        <tr><td class="ff_uni_hr">2</td><td></td><td class="title">-</td><td colspan="6"></td></tr>
                                                        <tr><td class="ff_uni_hr">3</td><td></td><td class="title">-</td><td colspan="6"></td></tr>
                                                        <tr><td class="ff_uni_hr">4</td><td></td><td class="title">-</td><td colspan="6"></td></tr>
                                                        <tr><td class="ff_uni_hr">5</td><td></td><td class="title">-</td><td colspan="6"></td></tr>     -->
                                                        
										   			    <tr><td class="ff_uni_hr"></td><td></td><td class="no_over"></td><td colspan="6"></td></tr>
										  			    <tr><td class="ff_uni_hr"></td><td></td><td class="no_over"></td><td colspan="6"></td></tr>
										 			    <tr><td class="ff_uni_hr"></td><td></td><td class="no_over no_data in_list"><span class="ui_no_data_txt">데이터가 없습니다.</span></td><td colspan="6"></td></tr>
										 			    <tr><td class="ff_uni_hr"></td><td></td><td class="no_over"></td><td colspan="6"></td></tr>
												        <tr><td class="ff_uni_hr"></td><td></td><td class="no_over"></td><td colspan="6"></td></tr>		                                   
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- // 기타 -->
                                </div>
                            </section>
                        </div>
                    </div>
                    <!-- // Daily VOC TOP5 -->

                    <!-- 연관어 분석 -->
					<div class="ui_row nav_sec is-pdf-page-1">
                        <div class="ui_col">
                            <section class="ui_box ui_loader_container" id="navi_02">
                                <div class="box_header">
                                    <h3>연관어 분석</h3>
                                    <div class="box_header_rc">
                                        <button type="button" class="ui_btn is-only-web" title="Excel Download"><span class="icon download">-</span><span class="in_loader">Loading</span></button>
                                    </div>
                                </div>
                                <div class="box_content">
                                    <div class="ui_row">
                                        <div class="ui_col w7">
                                            <div class="wrap" style="padding: 30px 40px 10px 20px">
                                                <div id="s02_cloud" class="ui_awsome_cloud ui_nodata" style="height: 418px"> <!--  데이터 없는 경우 'ui_nodata' 클래스 추가 -->
                                                    <span data-weight="70" style="color:#81afba"><a href="javascript:tmpFunc('1');">google</a></a></span>
                                                    <span data-weight="39" style="color:#81afba"><a href="javascript:tmpFunc('2');">risu</a></span>
                                                    <span data-weight="38" style="color:#788dd9"><a href="javascript:tmpFunc('3');">matti</a></span>
                                                    <span data-weight="38" style="color:#788dd9"><a href="javascript:tmpFunc('4');">ornar</a></span>
                                                    <span data-weight="36" style="color:#788dd9"><a href="javascript:tmpFunc('5');">lacinia</a></span>
                                                    <span data-weight="35" style="color:#81afba"><a href="javascript:tmpFunc('6');">magna</a></span>
                                                    <span data-weight="34" style="color:#81afba"><a href="javascript:tmpFunc('7');">lorem</a></span>
                                                    <span data-weight="33" style="color:#81afba"><a href="javascript:tmpFunc('8');">fermentum</a></span>
                                                    <span data-weight="32" style="color:#cd79da"><a href="javascript:tmpFunc('9');">nulla</a></span>
                                                    <span data-weight="32" style="color:#cd79da"><a href="javascript:tmpFunc('10');">enim</a></span>
                                                    <span data-weight="31" style="color:#81afba"><a href="javascript:tmpFunc('11');">conval</a></span>
                                                    <span data-weight="28" style="color:#a9a9a9"><a href="javascript:tmpFunc('12');">enim</a></span>
                                                    <span data-weight="27" style="color:#81afba"><a href="javascript:tmpFunc('13');">conval</a></span>
                                                    <span data-weight="24" style="color:#788dd9"><a href="javascript:tmpFunc('14');">faucibu</a></span>
                                                    <span data-weight="21" style="color:#788dd9"><a href="javascript:tmpFunc('15');">egesta</a></span>
                                                    <span data-weight="20" style="color:#81afba"><a href="javascript:tmpFunc('16');">puru</a></span>
                                                    <span data-weight="20" style="color:#81afba"><a href="javascript:tmpFunc('17');">risu</a></span>
                                                    <span data-weight="19" style="color:#788dd9"><a href="javascript:tmpFunc('18');">ultrici</a></span>
                                                    <span data-weight="18" style="color:#5ea3e1"><a href="javascript:tmpFunc('19');">ornar</a></span>
                                                    <span data-weight="17" style="color:#5ea3e1"><a href="javascript:tmpFunc('20');">dignissim</a></span>
                                                    <span data-weight="17" style="color:#788dd9"><a href="javascript:tmpFunc('21');">lacu</a></span>
                                                    <span data-weight="17" style="color:#788dd9"><a href="javascript:tmpFunc('22');">ultric</a></span>
                                                    <span data-weight="16" style="color:#788dd9"><a href="javascript:tmpFunc('23');">elementum</a></span>
                                                    <span data-weight="16" style="color:#cd79da"><a href="javascript:tmpFunc('24');">lacinia</a></span>
                                                    <span data-weight="12" style="color:#d96392"><a href="javascript:tmpFunc('25');">magna</a></span>
                                                    <span data-weight="12" style="color:#cd79da"><a href="javascript:tmpFunc('26');">lorem</a></span>
                                                    <span data-weight="11" style="color:#a9a9a9"><a href="javascript:tmpFunc('27');">fermentum</a></span>
                                                    <span data-weight="11" style="color:#d96392"><a href="javascript:tmpFunc('28');">nulla</a></span>
                                                    <span data-weight="11" style="color:#cd79da"><a href="javascript:tmpFunc('29');">enim</a></span>
                                                    <span data-weight="10" style="color:#cd79da"><a href="javascript:tmpFunc('30');">feli</a></span>
                                                    <span data-weight="10" style="color:#a9a9a9"><a href="javascript:tmpFunc('31');">test</a></span>
                                                    <span data-weight="10" style="color:#cd79da"><a href="javascript:tmpFunc('32');">luctu</a></span>
                                                    <span data-weight="9" style="color:#d96392"><a href="javascript:tmpFunc('33');">posuer</a></span>
                                                    <span data-weight="9" style="color:#d96392"><a href="javascript:tmpFunc('34');">malesuada</a></span>
                                                    <span data-weight="9" style="color:#d96392"><a href="javascript:tmpFunc('35');">diam</a></span>
                                                    <span data-weight="9" style="color:#d96392"><a href="javascript:tmpFunc('36');">puru</a></span>
                                                    <span data-weight="9" style="color:#5ea3e1"><a href="javascript:tmpFunc('37');">risu</a></span>
                                                    <span data-weight="9" style="color:#5ea3e1"><a href="javascript:tmpFunc('38');">ultrici</a></span>
                                                    <span data-weight="9" style="color:#5ea3e1"><a href="javascript:tmpFunc('39');">sodal</a></span>
                                                    <span data-weight="9" style="color:#a9a9a9"><a href="javascript:tmpFunc('40');">matti</a></span>
                                                    <span data-weight="9" style="color:#a9a9a9"><a href="javascript:tmpFunc('41');">ornar</a></span>
                                                    <span data-weight="9" style="color:#a9a9a9"><a href="javascript:tmpFunc('42');">dignissim</a></span>
                                                </div>
                                                <script type="text/javascript">
/*                                                    $("#s02_cloud").awesomeCloud({
                                                        "size" : {
                                                            "grid" : 2,
                                                            "factor" : 0,
                                                            "normalize" : false
                                                        },
                                                        "options" : {
                                                            "rotationRatio" : 0
                                                        },
                                                        "font" : "Malgun Gothic,'맑은고딕','돋움',Dotum,AppleGothic,Sans-serif,Tahoma",
                                                        "shape" : "circle"
                                                    });
    
                                                   function tmpFunc( $val ){
                                                        popupMngr.open( '#popup_rel_info' );
                                                    } */
                                                </script>
                                            </div>
                                        </div>
                                        <div class="ui_col w5" id="s02_cloud_list">
                                            <div class="ui_brd_list">
                                                <div class="wrap">
                                                    <table>
                                                        <colgroup>
                                                            <col style="width:64px">
                                                            <col>
                                                            <col style="width:100px">
                                                        </colgroup>
                                                        <thead>
                                                            <tr>
                                                                <th><span>순위</span></th>
                                                                <th><span>키워드</span></th>
                                                                <th><span>정보량</span></th>
                                                            </tr>
                                                        </thead>
                                                        <tbody  class="content_body">
<!--                                                    <tr>
                                                            <td class="rank"><span>1</span></td>
                                                            <td><span title="할인">할인</span></td>
                                                            <td><button type="button" class="lnk" target="_blank" title="새창에서 링크 열기" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><span>123,456,789</span></button></td>
                                                        </tr> -->
                                                        <tr><td class="rank"><span>1</span></td><td>-</td><td></td></tr>
                                                        <tr><td class="rank"><span>2</span></td><td>-</td><td></td></tr>
                                                        <tr><td class="rank"><span>3</span></td><td>-</td><td></td></tr>
                                                        <tr><td class="rank"><span>4</span></td><td>-</td><td></td></tr>
                                                        <tr><td class="rank"><span>5</span></td><td>-</td><td></td></tr>
                                                        <tr><td class="rank"><span>6</span></td><td>-</td><td></td></tr>
                                                        <tr><td class="rank"><span>7</span></td><td>-</td><td></td></tr>
                                                        <tr><td class="rank"><span>8</span></td><td>-</td><td></td></tr>
                                                        <tr><td class="rank"><span>9</span></td><td>-</td><td></td></tr>
                                                        <tr><td class="rank"><span>10</span></td><td>-</td><td></td></tr>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </section>
                        </div>
                    </div>
                    <!-- // 연관어 분석 -->

                    <!-- 정보추이 -->
					<div class="ui_row nav_sec is-pdf-page-2">
                        <div class="ui_col">
                            <section class="ui_box ui_loader_container" id="navi_03">
                                <div class="box_header">
                                    <h3>정보추이</h3>
                                    <div class="box_header_rc">
                                        <button type="button" class="ui_btn is-only-web" title="Excel Download"><span class="icon download">-</span><span class="in_loader">Loading</span></button>
                                    </div>
                                </div>
                                <div class="box_content">
                                    <div class="ui_row">
                                        <div class="ui_col w3" id="information_cnt">
                                            <div class="info_trend">
                                                <strong class="title is-card">현대카드</strong> <!-- 캐피탈 : 'is-capital' / 카드 : 'is-card' / 커머셜 : 'is-commercial' -->
<!--                                                 <span class="dv"><button type="button" onclick="popupMngr.open( '#popup_rel_info' ); return false;">0</button></span> -->
                                                <span class="dv"><button type="button">0</button></span>
                                                <span class="ui_fluc after none">0</span> <!-- up: 증가 / dn: 감소 / none: 변화없음 -->
                                            </div>
                                        </div>
                                        <div class="ui_col w9">
                                            <div class="wrap" style="padding: 0 0 0 20px;">
                                                <div class="info_trans_radio_group">
                                                    <ul class="ui_radio_group">
														<li><input id="info_trans_radio_group_01" name="info_trans_radio_group" type="radio" value="0" checked><label for="info_trans_radio_group_01"><span>Weekly</span></label></li>
														<li><input id="info_trans_radio_group_02" name="info_trans_radio_group" type="radio" value="1"><label for="info_trans_radio_group_02"><span>Monthly</span></label></li>
													</ul>
                                                </div>
                                                <script>
                                        		$("input:radio[name=info_trans_radio_group]").click(function(){
                                        		//	devel.chart.getChartById("s03_chart").removeListener(devel.chart.getChartById(""), 'clickGraphItem');
                                        			delete charts[0];
                                        			main.updateAjax_con_31();
                                        			main.updateAjax_con_32();                                        		
                                        		});		
                                                </script>                                            
                                                <div id="s03_chart" class="ui_chart_wrap is-not-hand" style="height: 231px"></div> <!-- 데이터 없는 경우 'ui_nodata' 클래스 추가 -->
                                                
<!--                                                 <div id="s03_chart" class="ui_chart_wrap is-not-hand ui_nodata" style="height: 255px"></div> 데이터 없는 경우 'ui_nodata' 클래스 추가
 -->                                                <script type="text/javascript">
                                                 
                                                </script>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </section>
                        </div>
                    </div>
                    <!-- // 정보추이 -->

                    <!-- 주제별 현황 -->
					<!-- <div class="ui_row nav_sec is-pdf-page-3" style="padding-bottom:25px;"> -->
					<div class="ui_row nav_sec is-pdf-page-3">
                        <div class="ui_col">
                            <section class="ui_box ui_loader_container" id="navi_04">
                                <div class="box_header">
                                    <h3>주제별 현황</h3>
                                    <div class="box_header_rc">
                                        <button type="button" class="ui_btn is-only-web" title="Excel Download"><span class="icon download">-</span><span class="in_loader">Loading</span></button>
                                    </div>
                                </div>
                                <div class="box_content">
                                    <div class="ui_row on_bd">
                                        <div class="ui_col w5">
                                            <div class="wrap" style="padding: 0 20px">
                                                <div id="s04_01_chart" class="ui_chart_wrap ui_nodata" style="height: 262px"></div> <!-- 데이터 없는 경우 'ui_nodata' 클래스 추가 -->
                                                <script type="text/javascript">
/*                                                     (function(){
                                                        var chartOption = {
                                                            "export": {  
                                                                "enabled": true,  
                                                                "menu": []  
                                                            },
                                                            "type": "pie",
                                                            "balloonText": "<strong>[[category]]</strong> : [[value]]건 ([[percents]]%)",
                                                            "pieX": 95,
                                                            "innerRadius": 74,
                                                            "labelRadius": -20,
                                                            "labelText": "",
                                                            "pullOutRadius": "0%",
                                                            "radius": 95,
                                                            "startAngle": 90,
                                                            "startRadius": "0%",
                                                            "colors": [
                                                                "#737cda",
                                                                "#a698ed",
                                                                "#50c7d3",
                                                                "#92ddb3",
                                                                "#e2e250",
                                                                "#f49541",
                                                                "#cbcbcb"
                                                            ],
                                                            "hideLabelsPercent": 5,
                                                            "marginBottom": 0,
                                                            "marginTop": 0,
                                                            "maxLabelWidth": 100,
                                                            "outlineThickness": 3,
                                                            "pullOutDuration": 0,
                                                            "startDuration": 0,
                                                            "titleField": "category",
                                                            "valueField": "column-1",
                                                            "accessible": false,
                                                            "addClassNames": true,
                                                            "autoResize": false,
                                                            "fontSize": 12,
                                                            "percentPrecision": 1,
                                                            "allLabels": [],
                                                            "balloon": {},
                                                            "legend": {
                                                                "enabled": true,
                                                                "align": "center",
                                                                "autoMargins": false,
                                                                "color": "#444444",
                                                                "marginLeft": 0,
                                                                "marginRight": 0,
                                                                "markerLabelGap": 6,
                                                                "markerSize": 11,
                                                                "markerType": "circle",
                                                                "periodValueText": "",
                                                                "position": "right",
                                                                "spacing": 20,
                                                                "valueText": "[[percents]]%",
                                                                "valueWidth": 70,
                                                                "verticalGap": 3
                                                            },
                                                            "titles": [],
                                                            "dataProvider": [
                                                                {
                                                                    "category": "상품&서비스",
                                                                    "column-1": "29"
                                                                },
                                                                {
                                                                    "category": "영업",
                                                                    "column-1": "27"
                                                                },
                                                                {
                                                                    "category": "디지털",
                                                                    "column-1": "29"
                                                                },
                                                                {
                                                                    "category": "Operation",
                                                                    "column-1": "29"
                                                                },
                                                                {
                                                                    "category": "금소법",
                                                                    "column-1": "29"
                                                                },                                              
                                                                {
                                                                    "category": "발급&한도",
                                                                    "column-1": "29"
                                                                },
                                                                {
                                                                    "category": "기타",
                                                                    "column-1": "29"
                                                                }
                                                            ]
                                                        };
                                                        
                                                        var chart = AmCharts.makeChart( "s04_01_chart", chartOption );
 */                                                    
                                                        /* chart.addListener( "clickSlice", function( $e ){
                                                            popupMngr.open( '#popup_rel_info' );
                                                        });
                                                       
                                                        charts.push( { id: "s04_01_chart", chart: chart } ); */
                                                        
                                                //    })(); 
                                                </script>
                                            </div>
                                        </div>
                                        <div class="ui_col w7">
                                            <div class="wrap" style="padding: 0 0 0 10px">
                                                <div id="s04_02_chart" class="ui_chart_wrap ui_nodata" style="height: 262px"></div> <!-- 데이터 없는 경우 'ui_nodata' 클래스 추가 -->
                                                <script type="text/javascript">
                                                    (function(){
                                                        var chartOption = {
                                                            "export": {  
                                                                "enabled": true,  
                                                                "menu": []  
                                                            },
                                                            "type": "serial",
                                                            "categoryField": "category",
                                                            "columnSpacing": 0,
                                                            "columnWidth": 0.2,
                                                            "marginBottom": 0,
                                                            "marginRight": 0,
                                                            "marginTop": 15,
                                                            "colors": [
                                                                "#5ea3e1",
                                                                "#ea7070",
                                                                "#a9a9a9",
                                                                "#000000"
                                                            ],
                                                            "addClassNames": true,
                                                            "fontSize": 12,
                                                            "pathToImages": "https://design.realsn.com/design_asset/img/amchart/",
                                                            "percentPrecision": 1,
                                                            "categoryAxis": {
                                                                "autoRotateAngle": 0,
                                                                "autoWrap": true,
                                                                "gridPosition": "start",
                                                                "axisColor": "#D8D8D8",
                                                                "boldPeriodBeginning": false,
                                                                "centerLabelOnFullPeriod": false,
                                                                "centerLabels": true,
                                                                "centerRotatedLabels": true,
                                                                "color": "#444444",
                                                                "gridAlpha": 0,
                                                                "markPeriodChange": false,
                                                                "minHorizontalGap": 0,
                                                                "minVerticalGap": 0,
                                                                "tickLength": 0,
                                                                "titleFontSize": 12,
                                                                "titleRotation": 0
                                                            },
                                                            "chartCursor": {
                                                                "enabled": true,
                                                                "categoryBalloonDateFormat": "YYYY-MM-DD",
                                                                "cursorColor": "#000000"
                                                            },
                                                            "trendLines": [],
                                                            "graphs": [
                                                                {
                                                                    "balloonText": "<strong>[[title]]</strong> : [[value]]건 ([[percents]]%)",
                                                                    "bulletBorderThickness": 0,
                                                                    "color": "#FFFFFF",
                                                                    "fillAlphas": 1,
                                                                    "id": "AmGraph-1",
                                                                    "title": "긍정",
                                                                    "type": "column",
                                                                    "valueAxis": "ValueAxis-1",
                                                                    "valueField": "column-1"
                                                                },
                                                                {
                                                                    "balloonText": "<strong>[[title]]</strong> : [[value]]건 ([[percents]]%)",
                                                                    "bulletBorderThickness": 0,
                                                                    "color": "#FFFFFF",
                                                                    "fillAlphas": 1,
                                                                    "id": "AmGraph-2",
                                                                    "title": "부정",
                                                                    "type": "column",
                                                                    "valueAxis": "ValueAxis-2",
                                                                    "valueField": "column-2"
                                                                },
                                                                {
                                                                    "balloonText": "<strong>[[title]]</strong> : [[value]]건 ([[percents]]%)",
                                                                    "bulletBorderThickness": 0,
                                                                    "color": "#FFFFFF",
                                                                    "fillAlphas": 1,
                                                                    "id": "AmGraph-3",
                                                                    "title": "중립",
                                                                    "type": "column",
                                                                    "valueAxis": "ValueAxis-3",
                                                                    "valueField": "column-3"
                                                                }
                                                            ],
                                                            "guides": [],
                                                            "valueAxes": [
                                                                {
                                                                    "id": "ValueAxis-1",
                                                                    "stackType": "regular",
                                                                    "autoGridCount": false,
                                                                    "axisThickness": 0,
                                                                    "color": "#c0c0c0",
                                                                    "dashLength": 2,
                                                                    "gridAlpha": 1,
                                                                    "gridColor": "#D8D8D8",
                                                                    "tickLength": 0,
                                                                    "title": ""
                                                                }
                                                            ],
                                                            "allLabels": [],
                                                            "balloon": {},
                                                            "legend": {
                                                                "enabled": true,
                                                                "align": "center",
                                                                "autoMargins": false,
                                                                "color": "#444444",
                                                                "equalWidths": false,
                                                                "marginLeft": 0,
                                                                "marginRight": 0,
                                                                "markerLabelGap": 6,
                                                                "markerSize": 11,
                                                                "markerType": "circle",
                                                                "periodValueText": "",
                                                                "spacing": 20,
                                                                "valueText": "",
                                                                "valueWidth": 0,
                                                                "verticalGap": 0
                                                            },
                                                            "titles": [],
                                                            "dataProvider": [
                                                                {
                                                                    "category": "상품서비스",
                                                                    "column-1": "5",
                                                                    "column-2": 8,
                                                                    "column-3": 1
                                                                },
                                                                {
                                                                    "category": "영업",
                                                                    "column-1": "3",
                                                                    "column-2": 2,
                                                                    "column-3": 1
                                                                },
                                                                {
                                                                    "category": "디지털",
                                                                    "column-1": "1",
                                                                    "column-2": 4,
                                                                    "column-3": 9
                                                                },
                                                                {
                                                                    "category": "Operation",
                                                                    "column-1": "1",
                                                                    "column-2": 4,
                                                                    "column-3": 9
                                                                },
                                                                {
                                                                    "category": "금소법",
                                                                    "column-1": "5",
                                                                    "column-2": 8,
                                                                    "column-3": 2
                                                                },
                                                                {
                                                                    "category": "발급/한도",
                                                                    "column-1": "5",
                                                                    "column-2": 8,
                                                                    "column-3": 2
                                                                },
                                                                {                                                                	
                                                                    "category": "기타",
                                                                    "column-1": "8",
                                                                    "column-2": 10,
                                                                    "column-3": 5
                                                                }
                                                            ]
                                                        }
                                                        var chart = AmCharts.makeChart( "s04_02_chart", chartOption );
/*                                                         chart.addListener( "clickGraphItem", function( $e ){
                                                            popupMngr.open( '#popup_rel_info' );
                                                        });
                                                      charts.push( { id: "s04_02_chart", chart: chart } ); */  
                                                    })();
                                                </script>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </section>
                        </div>
                    </div>
                    <!-- // 주제별 현황 -->

                    <!-- 상세 항목 더보기 -->
                    <div class="ui_row is-pdf-page-4">
                        <div class="ui_col">
                            <h3 class="ui_invisible">상세정보</h3>
                            <input type="checkbox" id="detail_more" class="is-only-web"><label for="detail_more" class="is-only-web"><span>상세 항목 더보기</span></label>
                            <div class="detail_content">

                                <!-- VOC 속성정보 -->
                                <div class="ui_row nav_sec is-child-pdf-1">
                                    <div class="ui_col">
                                        <section class="ui_box ui_loader_container" id="navi_05">
                                            <div class="box_header no_bd">
                                                <h4>VOC 속성정보</h4>
                                                <div class="box_header_rc">
                                                    <button type="button" class="ui_btn is-only-web" title="Excel Download"><span class="icon download">-</span><span class="in_loader">Loading</span></button>
                                                </div>
                                            </div>
                                            <div class="box_content">
                                                <div class="ui_tab is-bd">
                                                    <ul>
                                                        <li id="voc_attr_01"><input type="radio" id="voc_attr_tab_01" value="0" name="voc_attr_tab" checked><label for="voc_attr_tab_01"><span>전체</span></label></li>
                                                        <li id="voc_attr_02"><input type="radio" id="voc_attr_tab_02" value="1" name="voc_attr_tab"><label for="voc_attr_tab_02"><span>프리미엄</span></label></li>
                                                        <li id="voc_attr_03"><input type="radio" id="voc_attr_tab_03" value="5" name="voc_attr_tab"><label for="voc_attr_tab_03"><span>PLCC</span></label></li>
                                                    </ul>
                                                </div>
                                                <script>
                                        		$("input:radio[name=voc_attr_tab]").click(function(){
                                        			delete charts[3];
                                        			main.updateAjax_con_50();
                                        			main.updateAjax_con_51();                                        		
                                        		});		                                          		
                                                </script>                                            

                                                <!--
	                                                    전체 높이값은 css에서 설정되어 있음.
	                                                    테이블 tr은 아이템 갯수에 맞춰서 생성
	                                                    차트 높이값 - 기본 1개일때 61px, 1개씩 늘때마다 31px 플러스
                                                -->
                                                <!-- 기본테이블 -->
                                            <div id="voc_content">                                               
                                                <div class="voc_attr_table" id ="info_senti">
                                                    <table>
                                                        <colgroup>
                                                            <col>
                                                            <col style="width: 8.2%">
                                                            <col style="width: 8.2%">
                                                            <col style="width: 8.2%">
                                                            <col style="width: 8.2%">
                                                        </colgroup>
                                                        <thead>
                                                            <tr>
                                                                <th></th>
                                                                <th><strong>긍정</strong></th>
                                                                <th><strong>부정</strong></th>
                                                                <th><strong>중립</strong></th>
                                                                <th><strong>합계</strong></th>
                                                            </tr>
                                                        </thead>
                                                        <tbody id ="info_sentilist">
                                                            <tr>
                                                                <td></td>
                                                                <td><a href="#" class="ui_lnk" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><span class="txt">123,456</span></a></td>
                                                                <td><a href="#" class="ui_lnk" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><span class="txt">123,456</span></a></td>
                                                                <td><a href="#" class="ui_lnk" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><span class="txt">123,456</span></a></td>
                                                                <td><a href="#" class="ui_lnk" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><span class="txt">123,456</span></a></td>
                                                            </tr>
                                                            <tr>
                                                                <td></td>
                                                                <td><a href="#" class="ui_lnk" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><span class="txt">123,456</span></a></td>
                                                                <td><a href="#" class="ui_lnk" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><span class="txt">123,456</span></a></td>
                                                                <td><a href="#" class="ui_lnk" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><span class="txt">123,456</span></a></td>
                                                                <td><a href="#" class="ui_lnk" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><span class="txt">123,456</span></a></td>
                                                            </tr>
                                                            <tr>
                                                                <td></td>
                                                                <td><a href="#" class="ui_lnk" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><span class="txt">123,456</span></a></td>
                                                                <td><a href="#" class="ui_lnk" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><span class="txt">123,456</span></a></td>
                                                                <td><a href="#" class="ui_lnk" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><span class="txt">123,456</span></a></td>
                                                                <td><a href="#" class="ui_lnk" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><span class="txt">123,456</span></a></td>
                                                            </tr>
                                                            <tr>
                                                                <td></td>
                                                                <td><a href="#" class="ui_lnk" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><span class="txt">123,456</span></a></td>
                                                                <td><a href="#" class="ui_lnk" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><span class="txt">123,456</span></a></td>
                                                                <td><a href="#" class="ui_lnk" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><span class="txt">123,456</span></a></td>
                                                                <td><a href="#" class="ui_lnk" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><span class="txt">123,456</span></a></td>
                                                            </tr>
                                                            <tr>
                                                                <td></td>
                                                                <td><a href="#" class="ui_lnk" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><span class="txt">123,456</span></a></td>
                                                                <td><a href="#" class="ui_lnk" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><span class="txt">123,456</span></a></td>
                                                                <td><a href="#" class="ui_lnk" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><span class="txt">123,456</span></a></td>
                                                                <td><a href="#" class="ui_lnk" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><span class="txt">123,456</span></a></td>
                                                            </tr>
                                                            <tr>
                                                                <td></td>
                                                                <td><a href="#" class="ui_lnk" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><span class="txt">123,456</span></a></td>
                                                                <td><a href="#" class="ui_lnk" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><span class="txt">123,456</span></a></td>
                                                                <td><a href="#" class="ui_lnk" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><span class="txt">123,456</span></a></td>
                                                                <td><a href="#" class="ui_lnk" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><span class="txt">123,456</span></a></td>
                                                            </tr>
                                                            <tr>
                                                                <td></td>
                                                                <td><a href="#" class="ui_lnk" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><span class="txt">123,456</span></a></td>
                                                                <td><a href="#" class="ui_lnk" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><span class="txt">123,456</span></a></td>
                                                                <td><a href="#" class="ui_lnk" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><span class="txt">123,456</span></a></td>
                                                                <td><a href="#" class="ui_lnk" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><span class="txt">123,456</span></a></td>
                                                            </tr>
                                                            <tr>
                                                                <td></td>
                                                                <td><a href="#" class="ui_lnk" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><span class="txt">123,456</span></a></td>
                                                                <td><a href="#" class="ui_lnk" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><span class="txt">123,456</span></a></td>
                                                                <td><a href="#" class="ui_lnk" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><span class="txt">123,456</span></a></td>
                                                                <td><a href="#" class="ui_lnk" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><span class="txt">123,456</span></a></td>
                                                            </tr>
                                                            <tr>
                                                                <td></td>
                                                                <td><a href="#" class="ui_lnk" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><span class="txt">123,456</span></a></td>
                                                                <td><a href="#" class="ui_lnk" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><span class="txt">123,456</span></a></td>
                                                                <td><a href="#" class="ui_lnk" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><span class="txt">123,456</span></a></td>
                                                                <td><a href="#" class="ui_lnk" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><span class="txt">123,456</span></a></td>
                                                            </tr>
                                                            <tr>
                                                                <td></td>
                                                                <td><a href="#" class="ui_lnk" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><span class="txt">123,456</span></a></td>
                                                                <td><a href="#" class="ui_lnk" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><span class="txt">123,456</span></a></td>
                                                                <td><a href="#" class="ui_lnk" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><span class="txt">123,456</span></a></td>
                                                                <td><a href="#" class="ui_lnk" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><span class="txt">123,456</span></a></td>
                                                            </tr>
                                                            <tr>
                                                                <td></td>
                                                                <td><a href="#" class="ui_lnk" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><span class="txt">123,456</span></a></td>
                                                                <td><a href="#" class="ui_lnk" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><span class="txt">123,456</span></a></td>
                                                                <td><a href="#" class="ui_lnk" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><span class="txt">123,456</span></a></td>
                                                                <td><a href="#" class="ui_lnk" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><span class="txt">123,456</span></a></td>
                                                            </tr>
                                                            <tr>
                                                                <td></td>
                                                                <td><a href="#" class="ui_lnk" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><span class="txt">123,456</span></a></td>
                                                                <td><a href="#" class="ui_lnk" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><span class="txt">123,456</span></a></td>
                                                                <td><a href="#" class="ui_lnk" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><span class="txt">123,456</span></a></td>
                                                                <td><a href="#" class="ui_lnk" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><span class="txt">123,456</span></a></td>
                                                            </tr>
                                                        </tbody>
                                                    </table>
                                                </div>

                                                <div class="chart_wrap">
                                                    <!-- 현대카드 -->
                                                    <div id="s_detail_01_chart" class="ui_chart_wrap" style="height: 402px"></div> <!-- 데이터 없는 경우 'ui_nodata' 클래스 추가 -->
<!--                                            	<div id="s_detail_02_chart" class="ui_chart_wrap ui_nodata" style="height: 340px;display:none;"></div> 데이터 없는 경우 'ui_nodata' 클래스 추가
                                                	<div id="s_detail_03_chart" class="ui_chart_wrap ui_nodata" style="height: 340px;display:none;"></div> 데이터 없는 경우 'ui_nodata' 클래스 추가  -->
                                                    
                                                    <script type="text/javascript">
                                                        (function(){
                                                            var chartOption = {
                                                                "export": {  
                                                                    "enabled": true,  
                                                                    "menu": []  
                                                                },
                                                                "type": "serial",
                                                                "categoryField": "category",
                                                                "columnSpacing": 0,
                                                                "columnWidth": 0.54,
                                                                "rotate": true,
                                                                "marginBottom": 0,
                                                                "marginRight": 0,
                                                                "marginTop": 0,
                                                                "colors": [
                                                                    "#c8a6ee",
                                                                    "#f58a9e",
                                                                    "#9dcefa",
                                                                    "#92a5eb",
                                                                    "#bcda7c",
                                                                    "#80ceaa",
                                                                    "#cbcbcb"
                                                                ],
                                                                "addClassNames": true,
                                                                "fontSize": 12,
                                                                "pathToImages": "http://design.realsn.com/design_asset/img/amchart/",
                                                                "percentPrecision": 1,
                                                                "categoryAxis": {
                                                                    "autoRotateAngle": 0,
                                                                    "autoWrap": true,
                                                                    "gridPosition": "start",
                                                                    "axisColor": "#D8D8D8",
                                                                    "axisThickness": 0,
                                                                    "boldPeriodBeginning": false,
                                                                    "centerLabelOnFullPeriod": false,
                                                                    "centerLabels": true,
                                                                    "centerRotatedLabels": true,
                                                                    "color": "#444444",
                                                                    "gridAlpha": 0,
                                                                    "gridThickness": 0,
                                                                    "markPeriodChange": false,
                                                                    "minHorizontalGap": 0,
                                                                    "minVerticalGap": 0,
                                                                    "tickLength": 0,
                                                                    "titleFontSize": 12,
                                                                    "titleRotation": 0
                                                                },
                                                                "chartCursor": {
                                                                    "enabled": true,
                                                                    "categoryBalloonDateFormat": "YYYY-MM-DD",
                                                                    "cursorColor": "#000000"
                                                                },
                                                                "trendLines": [],
                                                                "graphs": [
                                                                    {
                                                                        "balloonText": "<strong>[[title]]</strong> : [[value]]건 ([[percents]]%)",
                                                                        "bulletBorderThickness": 0,
                                                                        "color": "#FFFFFF",
                                                                        "fillAlphas": 1,
                                                                        "id": "AmGraph-1",
                                                                        "labelText": "[[percents]]%",
                                                                        "title": "기사",
                                                                        "type": "column",
                                                                        "valueAxis": "ValueAxis-1",
                                                                        "valueField": "column-1"
                                                                    },
                                                                    {
                                                                        "balloonText": "<strong>[[title]]</strong> : [[value]]건 ([[percents]]%)",
                                                                        "bulletBorderThickness": 0,
                                                                        "color": "#FFFFFF",
                                                                        "fillAlphas": 1,
                                                                        "id": "AmGraph-2",
                                                                        "labelText": "[[percents]]%",
                                                                        "title": "클레임",
                                                                        "type": "column",
                                                                        "valueAxis": "ValueAxis-2",
                                                                        "valueField": "column-2"
                                                                    },
                                                                    {
                                                                        "balloonText": "<strong>[[title]]</strong> : [[value]]건 ([[percents]]%)",
                                                                        "bulletBorderThickness": 0,
                                                                        "color": "#FFFFFF",
                                                                        "fillAlphas": 1,
                                                                        "id": "AmGraph-3",
                                                                        "labelText": "[[percents]]%",
                                                                        "title": "칭찬/제안",
                                                                        "type": "column",
                                                                        "valueAxis": "ValueAxis-3",
                                                                        "valueField": "column-3"
                                                                    },
                                                                    {
                                                                        "balloonText": "<strong>[[title]]</strong> : [[value]]건 ([[percents]]%)",
                                                                        "bulletBorderThickness": 0,
                                                                        "color": "#FFFFFF",
                                                                        "fillAlphas": 1,
                                                                        "id": "AmGraph-4",
                                                                        "labelText": "[[percents]]%",
                                                                        "title": "후기",
                                                                        "type": "column",
                                                                        "valueAxis": "ValueAxis-4",
                                                                        "valueField": "column-4"
                                                                    },
                                                                    {
                                                                        "balloonText": "<strong>[[title]]</strong> : [[value]]건 ([[percents]]%)",
                                                                        "bulletBorderThickness": 0,
                                                                        "color": "#FFFFFF",
                                                                        "fillAlphas": 1,
                                                                        "id": "AmGraph-5",
                                                                        "labelText": "[[percents]]%",
                                                                        "title": "문의",
                                                                        "type": "column",
                                                                        "valueAxis": "ValueAxis-5",
                                                                        "valueField": "column-5"
                                                                    },
                                                                    {
                                                                        "balloonText": "<strong>[[title]]</strong> : [[value]]건 ([[percents]]%)",
                                                                        "bulletBorderThickness": 0,
                                                                        "color": "#FFFFFF",
                                                                        "fillAlphas": 1,
                                                                        "id": "AmGraph-6",
                                                                        "labelText": "[[percents]]%",
                                                                        "title": "단순언급",
                                                                        "type": "column",
                                                                        "valueAxis": "ValueAxis-6",
                                                                        "valueField": "column-6"
                                                                    },
                                                                    {
                                                                        "balloonText": "<strong>[[title]]</strong> : [[value]]건 ([[percents]]%)",
                                                                        "bulletBorderThickness": 0,
                                                                        "color": "#FFFFFF",
                                                                        "fillAlphas": 1,
                                                                        "id": "AmGraph-7",
                                                                        "labelText": "[[percents]]%",
                                                                        "title": "기타",
                                                                        "type": "column",
                                                                        "valueAxis": "ValueAxis-7",
                                                                        "valueField": "column-7"
                                                                    }
                                                                ],
                                                                "guides": [],
                                                                "valueAxes": [
                                                                    {
                                                                        "id": "ValueAxis-1",
                                                                        "stackType": "100%",
                                                                        "zeroGridAlpha": 0,
                                                                        "autoGridCount": false,
                                                                        "axisThickness": 0,
                                                                        "color": "#c0c0c0",
                                                                        "dashLength": 2,
                                                                        "gridAlpha": 1,
                                                                        "gridColor": "#D8D8D8",
                                                                        "gridThickness": 0,
                                                                        "labelsEnabled": false,
                                                                        "tickLength": 0,
                                                                        "title": ""
                                                                    }
                                                                ],
                                                                "allLabels": [],
                                                                "balloon": {},
                                                                "legend": {
                                                                    "enabled": true,
                                                                    "align": "center",
                                                                    "autoMargins": false,
                                                                    "color": "#444444",
                                                                    "equalWidths": false,
                                                                    "marginLeft": 0,
                                                                    "marginRight": 0,
                                                                    "markerLabelGap": 6,
                                                                    "markerSize": 11,
                                                                    "markerType": "circle",
                                                                    "periodValueText": "",
                                                                    "spacing": 20,
                                                                    "valueText": "",
                                                                    "valueWidth": 0,
                                                                    "verticalGap": 0
                                                                },
                                                                "titles": [],
                                                                "dataProvider": [
                                                                    {
                                                                        "category": "프리미엄카드",
                                                                        "column-1": "5",
                                                                        "column-2": 8,
                                                                        "column-3": 1,
                                                                        "column-4": 2,
                                                                        "column-5": 10,
                                                                        "column-6": 0,
                                                                        "column-7": 7
                                                                    },
                                                                    {
                                                                        "category": "포인트카드",
                                                                        "column-1": "3",
                                                                        "column-2": 2,
                                                                        "column-3": 1,
                                                                        "column-4": 6,
                                                                        "column-5": 8,
                                                                        "column-6": 1,
                                                                        "column-7": 0
                                                                    },
                                                                    {
                                                                        "category": "할인카드",
                                                                        "column-1": "1",
                                                                        "column-2": 4,
                                                                        "column-3": 9,
                                                                        "column-4": 9,
                                                                        "column-5": 3,
                                                                        "column-6": 6,
                                                                        "column-7": 8
                                                                    },
                                                                    {
                                                                        "category": "ZERO카드",
                                                                        "column-1": "5",
                                                                        "column-2": 8,
                                                                        "column-3": 2,
                                                                        "column-4": 1,
                                                                        "column-5": 7,
                                                                        "column-6": 9,
                                                                        "column-7": 5
                                                                    },
                                                                    {
                                                                        "category": "PLCC",
                                                                        "column-1": "8",
                                                                        "column-2": 10,
                                                                        "column-3": 5,
                                                                        "column-4": 10,
                                                                        "column-5": 3,
                                                                        "column-6": 2,
                                                                        "column-7": 9
                                                                    },
                                                                    {
                                                                        "category": "기타카드",
                                                                        "column-1": "3",
                                                                        "column-2": "4",
                                                                        "column-3": "8",
                                                                        "column-4": 1,
                                                                        "column-5": 5,
                                                                        "column-6": 8,
                                                                        "column-7": 10
                                                                    },
                                                                    {
                                                                        "category": "자동차구매",
                                                                        "column-1": "5",
                                                                        "column-2": "2",
                                                                        "column-3": "7",
                                                                        "column-4": 8,
                                                                        "column-5": 4,
                                                                        "column-6": 10,
                                                                        "column-7": 2
                                                                    },
                                                                    {
                                                                        "category": "카드대출",
                                                                        "column-1": "4",
                                                                        "column-2": "5",
                                                                        "column-3": "3",
                                                                        "column-4": 10,
                                                                        "column-5": 6,
                                                                        "column-6": 10,
                                                                        "column-7": 2
                                                                    },
                                                                    {
                                                                        "category": "기타서비스",
                                                                        "column-1": "6",
                                                                        "column-2": "3",
                                                                        "column-3": "5",
                                                                        "column-4": 5,
                                                                        "column-5": 0,
                                                                        "column-6": 1,
                                                                        "column-7": 5
                                                                    },
                                                                    {
                                                                        "category": "컬쳐&라이프",
                                                                        "column-1": "3",
                                                                        "column-2": "4",
                                                                        "column-3": "6",
                                                                        "column-4": 0,
                                                                        "column-5": 10,
                                                                        "column-6": 4,
                                                                        "column-7": 5
                                                                    },
                                                                    {
                                                                        "category": "컬쳐&라이프",
                                                                        "column-1": "3",
                                                                        "column-2": "4",
                                                                        "column-3": "6",
                                                                        "column-4": 0,
                                                                        "column-5": 10,
                                                                        "column-6": 4,
                                                                        "column-7": 5
                                                                    },
                                                                    {
                                                                        "category": "컬쳐&라이프",
                                                                        "column-1": "3",
                                                                        "column-2": "4",
                                                                        "column-3": "6",
                                                                        "column-4": 0,
                                                                        "column-5": 10,
                                                                        "column-6": 4,
                                                                        "column-7": 5
                                                                    }
                                                                ]
                                                            }
                                                            var chart = AmCharts.makeChart( "s_detail_01_chart", chartOption );
                                                         /*   chart.addListener( "clickGraphItem", function( $e ){
                                                                popupMngr.open( '#popup_rel_info' );
                                                            }); */
                                                            //charts.push( { id: "s_detail_01_chart", chart: chart } );
                                                        })();
                                                    </script>
    
                                                    <!-- 현대캐피탈 
                                                    <div id="s_detail_02_chart" class="ui_chart_wrap" style="height: 154px"></div>
                                                    <script type="text/javascript">
                                                        (function(){
                                                            var chartOption = {
                                                                "export": {  
                                                                    "enabled": true,  
                                                                    "menu": []  
                                                                },
                                                                "type": "serial",
                                                                "categoryField": "category",
                                                                "columnSpacing": 0,
                                                                "columnWidth": 0.54,
                                                                "rotate": true,
                                                                "marginBottom": 0,
                                                                "marginRight": 0,
                                                                "marginTop": 0,
                                                                "colors": [
                                                                    "#c8a6ee",
                                                                    "#f58a9e",
                                                                    "#9dcefa",
                                                                    "#92a5eb",
                                                                    "#bcda7c",
                                                                    "#80ceaa",
                                                                    "#cbcbcb"
                                                                ],
                                                                "addClassNames": true,
                                                                "fontSize": 12,
                                                                "pathToImages": "http://design.realsn.com/design_asset/img/amchart/",
                                                                "percentPrecision": 1,
                                                                "categoryAxis": {
                                                                    "autoRotateAngle": 0,
                                                                    "autoWrap": true,
                                                                    "gridPosition": "start",
                                                                    "axisColor": "#D8D8D8",
                                                                    "axisThickness": 0,
                                                                    "boldPeriodBeginning": false,
                                                                    "centerLabelOnFullPeriod": false,
                                                                    "centerLabels": true,
                                                                    "centerRotatedLabels": true,
                                                                    "color": "#444444",
                                                                    "gridAlpha": 0,
                                                                    "gridThickness": 0,
                                                                    "markPeriodChange": false,
                                                                    "minHorizontalGap": 0,
                                                                    "minVerticalGap": 0,
                                                                    "tickLength": 0,
                                                                    "titleFontSize": 12,
                                                                    "titleRotation": 0
                                                                },
                                                                "chartCursor": {
                                                                    "enabled": true,
                                                                    "categoryBalloonDateFormat": "YYYY-MM-DD",
                                                                    "cursorColor": "#000000"
                                                                },
                                                                "trendLines": [],
                                                                "graphs": [
                                                                    {
                                                                        "balloonText": "<strong>[[title]]</strong> : [[value]]건 ([[percents]]%)",
                                                                        "bulletBorderThickness": 0,
                                                                        "color": "#FFFFFF",
                                                                        "fillAlphas": 1,
                                                                        "id": "AmGraph-1",
                                                                        "labelText": "[[percents]]%",
                                                                        "title": "기사",
                                                                        "type": "column",
                                                                        "valueAxis": "ValueAxis-1",
                                                                        "valueField": "column-1"
                                                                    },
                                                                    {
                                                                        "balloonText": "<strong>[[title]]</strong> : [[value]]건 ([[percents]]%)",
                                                                        "bulletBorderThickness": 0,
                                                                        "color": "#FFFFFF",
                                                                        "fillAlphas": 1,
                                                                        "id": "AmGraph-2",
                                                                        "labelText": "[[percents]]%",
                                                                        "title": "클레임",
                                                                        "type": "column",
                                                                        "valueAxis": "ValueAxis-2",
                                                                        "valueField": "column-2"
                                                                    },
                                                                    {
                                                                        "balloonText": "<strong>[[title]]</strong> : [[value]]건 ([[percents]]%)",
                                                                        "bulletBorderThickness": 0,
                                                                        "color": "#FFFFFF",
                                                                        "fillAlphas": 1,
                                                                        "id": "AmGraph-3",
                                                                        "labelText": "[[percents]]%",
                                                                        "title": "칭찬/제안",
                                                                        "type": "column",
                                                                        "valueAxis": "ValueAxis-3",
                                                                        "valueField": "column-3"
                                                                    },
                                                                    {
                                                                        "balloonText": "<strong>[[title]]</strong> : [[value]]건 ([[percents]]%)",
                                                                        "bulletBorderThickness": 0,
                                                                        "color": "#FFFFFF",
                                                                        "fillAlphas": 1,
                                                                        "id": "AmGraph-4",
                                                                        "labelText": "[[percents]]%",
                                                                        "title": "후기",
                                                                        "type": "column",
                                                                        "valueAxis": "ValueAxis-4",
                                                                        "valueField": "column-4"
                                                                    },
                                                                    {
                                                                        "balloonText": "<strong>[[title]]</strong> : [[value]]건 ([[percents]]%)",
                                                                        "bulletBorderThickness": 0,
                                                                        "color": "#FFFFFF",
                                                                        "fillAlphas": 1,
                                                                        "id": "AmGraph-5",
                                                                        "labelText": "[[percents]]%",
                                                                        "title": "문의",
                                                                        "type": "column",
                                                                        "valueAxis": "ValueAxis-5",
                                                                        "valueField": "column-5"
                                                                    },
                                                                    {
                                                                        "balloonText": "<strong>[[title]]</strong> : [[value]]건 ([[percents]]%)",
                                                                        "bulletBorderThickness": 0,
                                                                        "color": "#FFFFFF",
                                                                        "fillAlphas": 1,
                                                                        "id": "AmGraph-6",
                                                                        "labelText": "[[percents]]%",
                                                                        "title": "단순언급",
                                                                        "type": "column",
                                                                        "valueAxis": "ValueAxis-6",
                                                                        "valueField": "column-6"
                                                                    },
                                                                    {
                                                                        "balloonText": "<strong>[[title]]</strong> : [[value]]건 ([[percents]]%)",
                                                                        "bulletBorderThickness": 0,
                                                                        "color": "#FFFFFF",
                                                                        "fillAlphas": 1,
                                                                        "id": "AmGraph-7",
                                                                        "labelText": "[[percents]]%",
                                                                        "title": "기타",
                                                                        "type": "column",
                                                                        "valueAxis": "ValueAxis-7",
                                                                        "valueField": "column-7"
                                                                    }
                                                                ],
                                                                "guides": [],
                                                                "valueAxes": [
                                                                    {
                                                                        "id": "ValueAxis-1",
                                                                        "stackType": "100%",
                                                                        "zeroGridAlpha": 0,
                                                                        "autoGridCount": false,
                                                                        "axisThickness": 0,
                                                                        "color": "#c0c0c0",
                                                                        "dashLength": 2,
                                                                        "gridAlpha": 1,
                                                                        "gridColor": "#D8D8D8",
                                                                        "gridThickness": 0,
                                                                        "labelsEnabled": false,
                                                                        "tickLength": 0,
                                                                        "title": ""
                                                                    }
                                                                ],
                                                                "allLabels": [],
                                                                "balloon": {},
                                                                "legend": {
                                                                    "enabled": true,
                                                                    "align": "center",
                                                                    "autoMargins": false,
                                                                    "color": "#444444",
                                                                    "equalWidths": false,
                                                                    "marginLeft": 0,
                                                                    "marginRight": 0,
                                                                    "markerLabelGap": 6,
                                                                    "markerSize": 11,
                                                                    "markerType": "circle",
                                                                    "periodValueText": "",
                                                                    "spacing": 20,
                                                                    "valueText": "",
                                                                    "valueWidth": 0,
                                                                    "verticalGap": 0
                                                                },
                                                                "titles": [],
                                                                "dataProvider": [
                                                                    {
                                                                        "category": "자동차금융",
                                                                        "column-1": "5",
                                                                        "column-2": 8,
                                                                        "column-3": 1,
                                                                        "column-4": 2,
                                                                        "column-5": 10,
                                                                        "column-6": 0,
                                                                        "column-7": 7
                                                                    },
                                                                    {
                                                                        "category": "신용대출",
                                                                        "column-1": "3",
                                                                        "column-2": 2,
                                                                        "column-3": 1,
                                                                        "column-4": 6,
                                                                        "column-5": 8,
                                                                        "column-6": 1,
                                                                        "column-7": 0
                                                                    },
                                                                    {
                                                                        "category": "주택대출",
                                                                        "column-1": "1",
                                                                        "column-2": 4,
                                                                        "column-3": 9,
                                                                        "column-4": 9,
                                                                        "column-5": 3,
                                                                        "column-6": 6,
                                                                        "column-7": 8
                                                                    },
                                                                    {
                                                                        "category": "기타서비스",
                                                                        "column-1": "5",
                                                                        "column-2": 8,
                                                                        "column-3": 2,
                                                                        "column-4": 1,
                                                                        "column-5": 7,
                                                                        "column-6": 9,
                                                                        "column-7": 5
                                                                    }
                                                                ]
                                                            }
                                                            var chart = AmCharts.makeChart( "s_detail_02_chart", chartOption );
                                                            chart.addListener( "clickGraphItem", function( $e ){
                                                                popupMngr.open( '#popup_rel_info' );
                                                            });
                                                            charts.push( { id: "s_detail_02_chart", chart: chart } );
                                                        })();
                                                    </script>
                                                    -->
    
                                                    <!-- 현대커머셜
                                                    <div id="s_detail_03_chart" class="ui_chart_wrap" style="height: 185px"></div>
                                                    <script type="text/javascript">
                                                        (function(){
                                                            var chartOption = {
                                                                "export": {  
                                                                    "enabled": true,  
                                                                    "menu": []  
                                                                },
                                                                "type": "serial",
                                                                "categoryField": "category",
                                                                "columnSpacing": 0,
                                                                "columnWidth": 0.54,
                                                                "rotate": true,
                                                                "marginBottom": 0,
                                                                "marginRight": 0,
                                                                "marginTop": 0,
                                                                "colors": [
                                                                    "#c8a6ee",
                                                                    "#f58a9e",
                                                                    "#9dcefa",
                                                                    "#92a5eb",
                                                                    "#bcda7c",
                                                                    "#80ceaa",
                                                                    "#cbcbcb"
                                                                ],
                                                                "addClassNames": true,
                                                                "fontSize": 12,
                                                                "pathToImages": "http://design.realsn.com/design_asset/img/amchart/",
                                                                "percentPrecision": 1,
                                                                "categoryAxis": {
                                                                    "autoRotateAngle": 0,
                                                                    "autoWrap": true,
                                                                    "gridPosition": "start",
                                                                    "axisColor": "#D8D8D8",
                                                                    "axisThickness": 0,
                                                                    "boldPeriodBeginning": false,
                                                                    "centerLabelOnFullPeriod": false,
                                                                    "centerLabels": true,
                                                                    "centerRotatedLabels": true,
                                                                    "color": "#444444",
                                                                    "gridAlpha": 0,
                                                                    "gridThickness": 0,
                                                                    "markPeriodChange": false,
                                                                    "minHorizontalGap": 0,
                                                                    "minVerticalGap": 0,
                                                                    "tickLength": 0,
                                                                    "titleFontSize": 12,
                                                                    "titleRotation": 0
                                                                },
                                                                "chartCursor": {
                                                                    "enabled": true,
                                                                    "categoryBalloonDateFormat": "YYYY-MM-DD",
                                                                    "cursorColor": "#000000"
                                                                },
                                                                "trendLines": [],
                                                                "graphs": [
                                                                    {
                                                                        "balloonText": "<strong>[[title]]</strong> : [[value]]건 ([[percents]]%)",
                                                                        "bulletBorderThickness": 0,
                                                                        "color": "#FFFFFF",
                                                                        "fillAlphas": 1,
                                                                        "id": "AmGraph-1",
                                                                        "labelText": "[[percents]]%",
                                                                        "title": "기사",
                                                                        "type": "column",
                                                                        "valueAxis": "ValueAxis-1",
                                                                        "valueField": "column-1"
                                                                    },
                                                                    {
                                                                        "balloonText": "<strong>[[title]]</strong> : [[value]]건 ([[percents]]%)",
                                                                        "bulletBorderThickness": 0,
                                                                        "color": "#FFFFFF",
                                                                        "fillAlphas": 1,
                                                                        "id": "AmGraph-2",
                                                                        "labelText": "[[percents]]%",
                                                                        "title": "클레임",
                                                                        "type": "column",
                                                                        "valueAxis": "ValueAxis-2",
                                                                        "valueField": "column-2"
                                                                    },
                                                                    {
                                                                        "balloonText": "<strong>[[title]]</strong> : [[value]]건 ([[percents]]%)",
                                                                        "bulletBorderThickness": 0,
                                                                        "color": "#FFFFFF",
                                                                        "fillAlphas": 1,
                                                                        "id": "AmGraph-3",
                                                                        "labelText": "[[percents]]%",
                                                                        "title": "칭찬/제안",
                                                                        "type": "column",
                                                                        "valueAxis": "ValueAxis-3",
                                                                        "valueField": "column-3"
                                                                    },
                                                                    {
                                                                        "balloonText": "<strong>[[title]]</strong> : [[value]]건 ([[percents]]%)",
                                                                        "bulletBorderThickness": 0,
                                                                        "color": "#FFFFFF",
                                                                        "fillAlphas": 1,
                                                                        "id": "AmGraph-4",
                                                                        "labelText": "[[percents]]%",
                                                                        "title": "후기",
                                                                        "type": "column",
                                                                        "valueAxis": "ValueAxis-4",
                                                                        "valueField": "column-4"
                                                                    },
                                                                    {
                                                                        "balloonText": "<strong>[[title]]</strong> : [[value]]건 ([[percents]]%)",
                                                                        "bulletBorderThickness": 0,
                                                                        "color": "#FFFFFF",
                                                                        "fillAlphas": 1,
                                                                        "id": "AmGraph-5",
                                                                        "labelText": "[[percents]]%",
                                                                        "title": "문의",
                                                                        "type": "column",
                                                                        "valueAxis": "ValueAxis-5",
                                                                        "valueField": "column-5"
                                                                    },
                                                                    {
                                                                        "balloonText": "<strong>[[title]]</strong> : [[value]]건 ([[percents]]%)",
                                                                        "bulletBorderThickness": 0,
                                                                        "color": "#FFFFFF",
                                                                        "fillAlphas": 1,
                                                                        "id": "AmGraph-6",
                                                                        "labelText": "[[percents]]%",
                                                                        "title": "단순언급",
                                                                        "type": "column",
                                                                        "valueAxis": "ValueAxis-6",
                                                                        "valueField": "column-6"
                                                                    },
                                                                    {
                                                                        "balloonText": "<strong>[[title]]</strong> : [[value]]건 ([[percents]]%)",
                                                                        "bulletBorderThickness": 0,
                                                                        "color": "#FFFFFF",
                                                                        "fillAlphas": 1,
                                                                        "id": "AmGraph-7",
                                                                        "labelText": "[[percents]]%",
                                                                        "title": "기타",
                                                                        "type": "column",
                                                                        "valueAxis": "ValueAxis-7",
                                                                        "valueField": "column-7"
                                                                    }
                                                                ],
                                                                "guides": [],
                                                                "valueAxes": [
                                                                    {
                                                                        "id": "ValueAxis-1",
                                                                        "stackType": "100%",
                                                                        "zeroGridAlpha": 0,
                                                                        "autoGridCount": false,
                                                                        "axisThickness": 0,
                                                                        "color": "#c0c0c0",
                                                                        "dashLength": 2,
                                                                        "gridAlpha": 1,
                                                                        "gridColor": "#D8D8D8",
                                                                        "gridThickness": 0,
                                                                        "labelsEnabled": false,
                                                                        "tickLength": 0,
                                                                        "title": ""
                                                                    }
                                                                ],
                                                                "allLabels": [],
                                                                "balloon": {},
                                                                "legend": {
                                                                    "enabled": true,
                                                                    "align": "center",
                                                                    "autoMargins": false,
                                                                    "color": "#444444",
                                                                    "equalWidths": false,
                                                                    "marginLeft": 0,
                                                                    "marginRight": 0,
                                                                    "markerLabelGap": 6,
                                                                    "markerSize": 11,
                                                                    "markerType": "circle",
                                                                    "periodValueText": "",
                                                                    "spacing": 20,
                                                                    "valueText": "",
                                                                    "valueWidth": 0,
                                                                    "verticalGap": 0
                                                                },
                                                                "titles": [],
                                                                "dataProvider": [
                                                                    {
                                                                        "category": "자동차금융",
                                                                        "column-1": "5",
                                                                        "column-2": 8,
                                                                        "column-3": 1,
                                                                        "column-4": 2,
                                                                        "column-5": 10,
                                                                        "column-6": 0,
                                                                        "column-7": 7
                                                                    },
                                                                    {
                                                                        "category": "신용대출",
                                                                        "column-1": "3",
                                                                        "column-2": 2,
                                                                        "column-3": 1,
                                                                        "column-4": 6,
                                                                        "column-5": 8,
                                                                        "column-6": 1,
                                                                        "column-7": 0
                                                                    },
                                                                    {
                                                                        "category": "주택대출",
                                                                        "column-1": "1",
                                                                        "column-2": 4,
                                                                        "column-3": 9,
                                                                        "column-4": 9,
                                                                        "column-5": 3,
                                                                        "column-6": 6,
                                                                        "column-7": 8
                                                                    },
                                                                    {
                                                                        "category": "기타서비스",
                                                                        "column-1": "5",
                                                                        "column-2": 8,
                                                                        "column-3": 2,
                                                                        "column-4": 1,
                                                                        "column-5": 7,
                                                                        "column-6": 9,
                                                                        "column-7": 5
                                                                    }
                                                                ]
                                                            }
                                                            var chart = AmCharts.makeChart( "s_detail_03_chart", chartOption );
                                                            chart.addListener( "clickGraphItem", function( $e ){
                                                                popupMngr.open( '#popup_rel_info' );
                                                            });
                                                            charts.push( { id: "s_detail_03_chart", chart: chart } );
                                                        })();
                                                    </script>
                                                     -->
                                                </div>
                                            </div>
                                            </div>
                                        </section>
                                    </div>
                                </div>
                                <!-- // VOC 속성정보 -->

                                <!-- 전체 VOC 리스트 -->
                                <div class="ui_row nav_sec is-child-pdf-2">
                                    <div class="ui_col">
                                        <section class="ui_box ui_loader_container" id="navi_06">
                                            <div class="box_header">
                                                <h4>전체 VOC 리스트</h4>
                                                <div class="box_header_rc">
                                                    <button type="button" class="ui_btn is-only-web" title="Excel Download"><span class="icon download">-</span><span class="in_loader">Loading</span></button>
                                                </div>                                                
                                            </div>
                                            <div class="box_content">
                                                <!-- 긍정 -->
                                                <div class="ui_row">
                                                    <div class="ui_col" id="voc_pos">
                                                        <div class="ui_brd_list">
                                                            <div class="header">
                                                                <div class="lc">
                                                                    <h5 class="fc_postive">긍정</h5>
                                                                </div>
                                                                <div class="rc">
                                                                    <button type="button" class="ui_btn is-only-web" title="Excel Download" id="pos_download"><span class="icon download">-</span><span class="in_loader">Loading</span></button>
                                                                </div>
                                                            </div>
                                                            <div class="wrap">
                                                                <table>
                                                                    <colgroup>
                                                                        <col>
                                                                        <col style="width: 42px">
                                                                        <col style="width: 140px">
                                                                        <col style="width: 90px">
                                                                        <col style="width: 100px">
                                                                        <col style="width: 80px">
                                                                    </colgroup>
                                                                    <thead>
                                                                        <tr>
                                                                            <th><span>제목</span></th>
                                                                            <th><span>링크</span></th>                                                                            
                                                                            <th><span>출처</span></th>
                                                                            <th><span>주제 유형</span></th>
                                                                            <th><span>제품 유형</span></th>
                                                                            <th><span>일시</span></th>
                                                                        </tr>
                                                                    </thead>
                                                                    <tbody>
                                                                        <!-- 데이터 없는 경우 -->
                                                                        <tr><td colspan="6" class="no_over"></td></tr>
                                                                        <tr><td colspan="6" class="no_over"></td></tr>
                                                                        <tr><td colspan="6" class="no_over no_data in_list"><span class="ui_no_data_txt">데이터가 없습니다.</span></td></tr>
                                                                        <tr><td colspan="6" class="no_over"></td></tr>
                                                                        <tr><td colspan="6" class="no_over"></td></tr>
                                                                       
<!--                                                                    <tr>
                                                                            <td class="title"><a href="#" class="lnk" target="_blank" title="현대카드 상담사 잘만났네요"><span>현대카드 상담사 잘만났네요</span></a></td>
                                                                            <td>
                                                                                <a href="#" class="btn_cafe_p" title="PC 버전 링크">PC 버전 링크</a>
                                                                                <a href="#" class="btn_cafe_m" title="Mobile 버전 링크">Mobile 버전 링크</a>
                                                                            </td>
                                                                            <td><span title="가나다라마바사아자차카타파하">가나다라마바사아자차카타파하</span></td>
                                                                            <td><span title="상품&amp;서비스">상품&amp;서비스</span></td>
                                                                            <td><span title="중고산업재금융">중고산업재금융</span></td>
                                                                            <td><span>01/01 11:33</span></td>
                                                                        </tr>
                                                                        <tr><td colspan="5" class="no_over"></td></tr>
                                                                        <tr><td colspan="5" class="no_over"></td></tr>
                                                                        <tr><td colspan="5" class="no_over"></td></tr>
                                                                        <tr><td colspan="5" class="no_over"></td></tr>   -->
                                                                    </tbody>
                                                                </table>
                                                            </div>
                                                            <div class="footer is-only-web">
                                                                <div class="ui_paginate">
                                                                    <div class="in_wrap">
                                                                        <a href="#" class="page_prev ui_disabled" onclick="return false">이전페이지</a>
                                                                        <a href="#" class="" onclick="return false;">1</a>
                                                                        <a href="#" class="active" onclick="return false;">2</a>
                                                                        <a href="#" onclick="return false;">3</a>
                                                                        <a href="#" onclick="return false;">4</a>
                                                                        <a href="#" onclick="return false;">5</a>
                                                                        <a href="#" class="page_next disabled" onclick="return false;">다음페이지</a>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                                <!-- 부정 -->
                                                <div class="ui_row">
                                                    <div class="ui_col" id="voc_neg">
                                                        <div class="ui_brd_list">
                                                            <div class="header">
                                                                <div class="lc">
                                                                    <h5 class="fc_negative">부정</h5>
                                                                </div>
                                                                <div class="rc">
                                                                    <button type="button" class="ui_btn is-only-web" title="Excel Download"><span class="icon download">-</span><span class="in_loader">Loading</span></button>
                                                                </div>
                                                            </div>
                                                            <div class="wrap">
                                                                <table>
                                                                    <colgroup>
                                                                        <col>
                                                                        <col style="width: 42px">
                                                                        <col style="width: 140px">
                                                                        <col style="width: 90px">
                                                                        <col style="width: 100px">
                                                                        <col style="width: 80px">
                                                                    </colgroup>
                                                                    <thead>
                                                                        <tr>
                                                                            <th><span>제목</span></th>
                                                                            <th><span>링크</span></th>                                                                            
                                                                            <th><span>출처</span></th>
                                                                            <th><span>주제 유형</span></th>
                                                                            <th><span>제품 유형</span></th>
                                                                            <th><span>일시</span></th>
                                                                        </tr>
                                                                    </thead>
                                                                    <tbody>
                                                                        <!-- 데이터 없는 경우    -->
                                                                        <tr><td colspan="6" class="no_over"></td></tr>
                                                                        <tr><td colspan="6" class="no_over"></td></tr>
                                                                        <tr><td colspan="6" class="no_over no_data in_list"><span class="ui_no_data_txt">데이터가 없습니다.</span></td></tr>
                                                                        <tr><td colspan="6" class="no_over"></td></tr>
                                                                        <tr><td colspan="6" class="no_over"></td></tr>
                                                                        
<!--                                                                    <tr>
                                                                            <td class="title"><a href="#" class="lnk" target="_blank" title="현대카드 상담사 잘만났네요"><span>현대카드 상담사 잘만났네요</span></a></td>
                                                                            <td>
                                                                                <a href="#" class="btn_cafe_p" title="PC 버전 링크">PC 버전 링크</a>
                                                                                <a href="#" class="btn_cafe_m" title="Mobile 버전 링크">Mobile 버전 링크</a>
                                                                            </td>                                                                            
                                                                            <td><span title="뽐뿌">뽐뿌</span></td>
                                                                            <td><span title="프로모션&이벤트">프로모션&이벤트</span></td>
                                                                            <td><span title="중고산업재금융">중고산업재금융</span></td>
                                                                            <td><span>01/01 11:33</span></td>
                                                                        </tr>
                                                                        <tr><td colspan="5" class="no_over"></td></tr>
                                                                        <tr><td colspan="5" class="no_over"></td></tr>
                                                                        <tr><td colspan="5" class="no_over"></td></tr>
                                                                        <tr><td colspan="5" class="no_over"></td></tr>      -->
                                                                    </tbody>
                                                                </table>
                                                            </div>
                                                            <div class="footer is-only-web">
                                                                <div class="ui_paginate">
                                                                    <div class="in_wrap">
                                                                        <a href="#" class="page_prev ui_disabled" onclick="return false">이전페이지</a>
                                                                        <a href="#" class="" onclick="return false;">1</a>
                                                                        <a href="#" class="active" onclick="return false;">2</a>
                                                                        <a href="#" onclick="return false;">3</a>
                                                                        <a href="#" onclick="return false;">4</a>
                                                                        <a href="#" onclick="return false;">5</a>
                                                                        <a href="#" class="page_next disabled" onclick="return false;">다음페이지</a>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                                <!-- 중립 -->
                                                <div class="ui_row">
                                                    <div class="ui_col" id="voc_neu">
                                                        <div class="ui_brd_list">
                                                            <div class="header">
                                                                <div class="lc">
                                                                    <h5 class="fc_neutral">중립</h5>
                                                                </div>
                                                                <div class="rc">
                                                                    <button type="button" class="ui_btn is-only-web" title="Excel Download"><span class="icon download">-</span><span class="in_loader">Loading</span></button>
                                                                </div>
                                                            </div>
                                                            <div class="wrap">
                                                                <table>
                                                                    <colgroup>
                                                                        <col>
                                                                        <col style="width: 42px">
                                                                        <col style="width: 140px">
                                                                        <col style="width: 90px">
                                                                        <col style="width: 100px">
                                                                        <col style="width: 80px">
                                                                    </colgroup>
                                                                    <thead>
                                                                        <tr>
                                                                            <th><span>제목</span></th>
                                                                            <th><span>링크</span></th>
                                                                            <th><span>출처</span></th>
                                                                            <th><span>주제 유형</span></th>
                                                                            <th><span>제품 유형</span></th>
                                                                            <th><span>일시</span></th>
                                                                        </tr>
                                                                    </thead>
                                                                    <tbody>
                                                                        <!-- 데이터 없는 경우
                                                                        <tr><td colspan="5" class="no_over"></td></tr>
                                                                        <tr><td colspan="5" class="no_over"></td></tr>
                                                                        <tr><td colspan="5" class="no_over no_data in_list"><span class="ui_no_data_txt">데이터가 없습니다.</span></td></tr>
                                                                        <tr><td colspan="5" class="no_over"></td></tr>
                                                                        <tr><td colspan="5" class="no_over"></td></tr>
                                                                        -->
                                                                        <tr>
                                                                            <td class="title"><a href="#" class="lnk" target="_blank" title="현대카드 상담사 잘만났네요"><span>현대카드 상담사 잘만났네요</span></a></td>
                                                                            <td>
                                                                                <a href="#" class="btn_cafe_p" title="PC 버전 링크">PC 버전 링크</a>
                                                                                <a href="#" class="btn_cafe_m" title="Mobile 버전 링크">Mobile 버전 링크</a>
                                                                            </td>
                                                                            <td><span title="뽐뿌">뽐뿌</span></td>
                                                                            <td><span title="프로모션&이벤트">프로모션&이벤트</span></td>
                                                                            <td><span title="중고산업재금융">중고산업재금융</span></td>
                                                                            <td><span>01/01 11:33</span></td>
                                                                        </tr>
                                                                        <tr><td colspan="5" class="no_over"></td></tr>
                                                                        <tr><td colspan="5" class="no_over"></td></tr>
                                                                        <tr><td colspan="5" class="no_over"></td></tr>
                                                                        <tr><td colspan="5" class="no_over"></td></tr>
                                                                    </tbody>
                                                                </table>
                                                            </div>
                                                            <div class="footer is-only-web">
                                                                <div class="ui_paginate">
                                                                    <div class="in_wrap">
                                                                        <a href="#" class="page_prev ui_disabled" onclick="return false">이전페이지</a>
                                                                        <a href="#" class="" onclick="return false;">1</a>
                                                                        <a href="#" class="active" onclick="return false;">2</a>
                                                                        <a href="#" onclick="return false;">3</a>
                                                                        <a href="#" onclick="return false;">4</a>
                                                                        <a href="#" onclick="return false;">5</a>
                                                                        <a href="#" class="page_next disabled" onclick="return false;">다음페이지</a>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>                                          
                                            </div>
                                        </section>
                                    </div>
                                </div>
                                <!-- // 전체 VOC 리스트 -->

                                <!-- 실시간 포탈 TOP 노출 현황 -->
                                <div class="ui_row nav_sec is-child-pdf-3">
                                    <div class="ui_col">
                                        <section class="ui_box ui_loader_container" id="navi_07">
                                            <div class="box_header">
                                                <h4>실시간 포탈 TOP 노출 현황</h4>
                                                <div class="box_header_rc">
                                                    <button type="button" class="ui_btn is-only-web" title="Excel Download"><span class="icon download">-</span><span class="in_loader">Loading</span></button>
                                                </div>
                                            </div>
                                            <div class="box_content" id="potaltop">
                                                <div class="ui_brd_list">
                                                    <div class="wrap">
                                                        <table>
                                                            <colgroup>
										                        <col>
										                        <col style="width: 15%">
										                        <col style="width: 15%">
										                        <col style="width: 14%">
                                                            </colgroup>
                                                            <thead>
                                                                <tr>
                                                                    <th><span>제목</span></th>
                                                                    <th><span>출처</span></th>
                                                                    <th><span>영역</span></th>
                                                                    <th><span>최초노출일시</span></th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <!-- 데이터 없는 경우 -->
                                                                <tr><td colspan="4" class="no_over"></td></tr>
                                                                <tr><td colspan="4" class="no_over"></td></tr>
                                                                <tr><td colspan="4" class="no_over no_data in_list"><span class="ui_no_data_txt">데이터가 없습니다.</span></td></tr>
                                                                <tr><td colspan="4" class="no_over"></td></tr>
                                                                <tr><td colspan="4" class="no_over"></td></tr>
                                                                
<!--                                                            <tr>
                                                                    <td class="title"><a href="#" class="lnk" target="_blank" title="현대카드 상담사 잘만났네요"><span>현대카드 상담사 잘만났네요</span></a></td>
                                                                    <td><span title="조선비즈">조선비즈</span></td>
                                                                    <td><span title="뉴스스탠드">뉴스스탠드</span></td>
                                                                    <td><span>21/01/01 00:00</span></td>
                                                                </tr>
                                                                <tr><td colspan="4" class="no_over"></td></tr>
                                                                <tr><td colspan="4" class="no_over"></td></tr>
                                                                <tr><td colspan="4" class="no_over"></td></tr>
                                                                <tr><td colspan="4" class="no_over"></td></tr>     -->
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                    <div class="footer is-only-web">
                                                        <div class="ui_paginate">
                                                            <div class="in_wrap">
                                                                <a href="#" class="page_prev ui_disabled" onclick="return false">이전페이지</a>
                                                                <a href="#" class="" onclick="return false;">1</a>
                                                                <a href="#" class="active" onclick="return false;">2</a>
                                                                <a href="#" onclick="return false;">3</a>
                                                                <a href="#" onclick="return false;">4</a>
                                                                <a href="#" onclick="return false;">5</a>
                                                                <a href="#" class="page_next disabled" onclick="return false;">다음페이지</a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </section>
                                    </div>
                                </div>
                                <!-- // 실시간 포탈 TOP 노출 현황 -->

                                <!-- 포탈 뉴스 댓글 현황 -->
                                <div class="ui_row nav_sec is-child-pdf-4">
                                    <div class="ui_col">
                                        <section class="ui_box ui_loader_container" id="navi_08">
                                            <div class="box_header">
                                                <h4>포탈 뉴스 댓글 현황</h4>
                                            </div>
                                            <div class="box_content">
                                                <!-- 목록 -->                                                
                                                <div class="ui_brd_list">
                                                    <div class="header">
                                                        <div class="rc">
                                                            <div id = "dp_01" class="ui_datepicker is-right is-only-web" data-date="<%=eDate%>" data-change="evt_portalDateChange">
                                                                <div class="ui_datepicker_input"><input id="dp_01_input" type="text" readonly><label for="dp_01_input"></label></div>
                                                            </div>
                                                            <script>
                                                                 function evt_portalDateChange( $date ){
                                                                 	$( "#dp_01_input" ).val( $date );
                    											 if(document.getElementById('sidebar_detail_more').checked == true || document.getElementById('detail_more').checked == true){
                                                                    main.updateAjax_con_80();
                    											  }
                    											 /* else if(document.getElementById('sidebar_detail_more').checked == false || document.getElementById('detail_more').checked == false){
                    												  $('#sidebar_detail_more').click(function(){
                                                                          main.updateAjax_con_80();                    													  
                    												  });
                    												  $('#detail_more').click(function(){
                                                                          main.updateAjax_con_80();                    													  
                    												  });                    												  
                  											 	} */ 	                    											 
                                                              } 
                                                            </script> 
                                                            <span class="is-only-pdf">선택날짜 : <span id="portalDateTxt" class="dv"><%=eDate%></span></span>
                                                            <hr class="is-only-web">
                                                            <button type="button" class="ui_btn is-only-web" title="Excel Download"><span class="icon download">-</span><span class="in_loader">Loading</span></button>
                                                        </div>
                                                    </div>
                                                   <div class="ui_brd_list" id="potal_reply">
                                                    <div class="wrap">                             
                                                        <table>
                                                            <colgroup>
                                                                <col>
                                                                <col style="width: 140px">
                                                                <col style="width: 124px">
                                                                <col style="width: 80px">
                                                            </colgroup>
                                                            <thead>
                                                                <tr>
                                                                    <th><span>제목</span></th>
                                                                    <th><span>댓글</span></th>
                                                                    <th><span>포탈구분</span></th>
                                                                    <th><span>일시</span></th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <!-- 데이터 없는 경우 -->
                                                                <tr><td colspan="4" class="no_over"></td></tr>
                                                                <tr><td colspan="4" class="no_over"></td></tr>
                                                                <tr><td colspan="4" class="no_over no_data in_list"><span class="ui_no_data_txt">데이터가 없습니다.</span></td></tr>
                                                                <tr><td colspan="4" class="no_over"></td></tr>
                                                                <tr><td colspan="4" class="no_over"></td></tr>
                                                                
<!--                                                            <tr class="active">
                                                                    <td class="title">
                                                                        <button type="button" class="ui_btn small btn_anal" title="댓글 분석">&#xe063;</button>
                                                                        <a href="#" class="lnk" target="_blank" title="현대카드 상담사 잘만났네요"><span>현대카드 상담사 잘만났네요</span></a>
                                                                    </td>
                                                                    <td>                                                                    
                                                                        <span>
                                                                            <button type="button" class="lnk" target="_blank" title="123,456,789" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><span>123,456k</span></button>
                                                                            <span>(<span class="fc_postive">1</span>/<span class="fc_negative">1</span>/<span class="fc_neutral">0</span>)</span>
                                                                        </span>
                                                                    </td>
                                                                    <td><span title="연합뉴스_한민족센터">연합뉴스_한민족센터</span></td>
                                                                    <td><span>01/01 11:33</span></td>
                                                                </tr>
                                                                <tr>
                                                                    <td class="title">
                                                                        <button type="button" class="ui_btn small btn_anal" title="댓글 분석">&#xe063;</button>
                                                                        <a href="#" class="lnk" target="_blank" title="현대카드 상담사 잘만났네요"><span>현대카드 상담사 잘만났네요</span></a>
                                                                    </td>
                                                                    <td>
                                                                        <span>
                                                                            <button type="button" class="lnk" target="_blank" title="123,456,789" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><span>123,456k</span></button>
                                                                            <span>(<span class="fc_postive">1</span>/<span class="fc_negative">1</span>/<span class="fc_neutral">0</span>)</span>
                                                                        </span>
                                                                    </td>
                                                                    <td><span title="네이버카페">네이버카페</span></td>
                                                                    <td><span>01/01 11:33</span></td>
                                                                </tr>
                                                                <tr><td colspan="4" class="no_over"></td></tr>
                                                                <tr><td colspan="4" class="no_over"></td></tr>
                                                                <tr><td colspan="4" class="no_over"></td></tr>                -->
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                    <div class="footer is-only-web">
                                                        <div class="ui_paginate">
                                                            <div class="in_wrap">
                                                                <a href="#" class="page_prev ui_disabled" onclick="return false">이전페이지</a>
                                                                <a href="#" class="" onclick="return false;">1</a>
                                                                <a href="#" class="active" onclick="return false;">2</a>
                                                                <a href="#" onclick="return false;">3</a>
                                                                <a href="#" onclick="return false;">4</a>
                                                                <a href="#" onclick="return false;">5</a>
                                                                <a href="#" class="page_next disabled" onclick="return false;">다음페이지</a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    
                                                    </div>
                                                 </div>
                                                <!-- // 목록 -->

                                                <div class="ui_row">
                                                    <div class="ui_col">
                                                        <section class="ui_box dims">
                                                            <div class="box_header">
                                                                <h5>포탈 뉴스 댓글 분석<span id="article_name"></span></h5>
                                                            </div>
                                                            <div class="box_content" id="article_type" style="padding: 30px 0">
                                                                <div class="ui_row on_bd">
                                                                    <!-- 감성별 점유율 -->
                                                                    <div class="ui_col w6" id="senti_count">
                                                                        <section class="ui_box">
                                                                            <div class="box_header">
                                                                                <h6>감성별 점유율</h6>
                                                                                <div class="box_header_rc">
                                                                                    <button type="button" class="ui_btn is-only-web" title="Excel Download"><span class="icon download">-</span><span class="in_loader">Loading</span></button>
                                                                                </div>
                                                                            </div>
                                                                            <div class="box_content">
                                                                                <div id="s_detail_05_chart" class="ui_chart_wrap ui_nodata" style="height: 260px"></div>
                                                                                <script type="text/javascript">
                                                                                    (function(){
                                                                                        var chartOption = {
                                                                                            "export": {  
                                                                                                "enabled": true,  
                                                                                                "menu": []  
                                                                                            },
                                                                                            "type": "pie",
                                                                                            "balloonText": "<strong>[[category]]</strong> : [[value]]건 ([[percents]]%)",
                                                                                            "innerRadius": 74,
                                                                                            "labelRadius": -20,
                                                                                            "labelText": "",
                                                                                            "pullOutRadius": "0%",
                                                                                            "radius": 95,
                                                                                            "startAngle": 0,
                                                                                            "startRadius": "0%",
                                                                                            "colors": [
                                                                                                "#5ea3e1",
                                                                                                "#ea7070",
                                                                                                "#a9a9a9"
                                                                                            ],
                                                                                            "hideLabelsPercent": 5,
                                                                                            "marginBottom": 0,
                                                                                            "marginLeft": 20,
                                                                                            "marginRight": 20,
                                                                                            "marginTop": 0,
                                                                                            "maxLabelWidth": 100,
                                                                                            "outlineThickness": 3,
                                                                                            "pullOutDuration": 0,
                                                                                            "startDuration": 0,
                                                                                            "titleField": "category",
                                                                                            "valueField": "column-1",
                                                                                            "accessible": false,
                                                                                            "addClassNames": true,
                                                                                            "fontSize": 12,
                                                                                            "percentPrecision": 1,
                                                                                            "allLabels": [
                                                                                                {
                                                                                                    "align": "center",
                                                                                                    "color": "#999999",
                                                                                                    "id": "Label-1",
                                                                                                    "size": 11,
                                                                                                    "text": "VOLUME",
                                                                                                    "y": "40%"
                                                                                                },
                                                                                                {
                                                                                                    "align": "center",
                                                                                                    "color": "#000000",
                                                                                                    "id": "Label-2",
                                                                                                    "size": 30,
                                                                                                    "text": "1,234k",
                                                                                                    "y": "45%"
                                                                                                }
                                                                                            ],
                                                                                            "balloon": {},
                                                                                            "legend": {
                                                                                                "enabled": true,
                                                                                                "align": "center",
                                                                                                "autoMargins": false,
                                                                                                "bottom": 0,
                                                                                                "color": "#444444",
                                                                                                "labelWidth": 100,
                                                                                                "marginLeft": 0,
                                                                                                "marginRight": 40,
                                                                                                "markerLabelGap": 6,
                                                                                                "markerSize": 11,
                                                                                                "markerType": "circle",
                                                                                                "periodValueText": "",
                                                                                                "position": "right",
                                                                                                "spacing": 0,
                                                                                                "valueText": "[[percents]]%",
                                                                                                "valueWidth": 60
                                                                                            },
                                                                                            "titles": [],
                                                                                            "dataProvider": [
                                                                                                {
                                                                                                    "category": "긍정",
                                                                                                    "column-1": "29"
                                                                                                },
                                                                                                {
                                                                                                    "category": "부정",
                                                                                                    "column-1": "27"
                                                                                                },
                                                                                                {
                                                                                                    "category": "중립",
                                                                                                    "column-1": "3"
                                                                                                }
                                                                                            ]
                                                                                        }
                                                                                        var chart = AmCharts.makeChart( "s_detail_05_chart", chartOption );
/*                                                                                         chart.addListener("clickSlice", function( $e ){
                                                                                            popupMngr.open( '#popup_rel_info' );
                                                                                        });
                                                                                      chart.addListener("rendered", function( $e ){
                                                                                            $( "#s_detail_05_chart .amcharts-label.amcharts-label-Label-2" ).css( { "pointer-events": "all", "cursor": "pointer" } );
                                                                                            $( "#s_detail_05_chart .amcharts-label.amcharts-label-Label-2" ).click( function(){
                                                                                               popupMngr.open( '#popup_rel_info' )
                                                                                            })
                                                                                        }); */
                                                                                     //   charts.push( { id: "s_detail_05_chart", chart: chart } );  
                                                                                    })();
                                                                                </script>
                                                                            </div>
                                                                        </section>
                                                                    </div>
                                                                    <!-- // 감성별 점유율 -->

                                                                    <!-- 연관어 클라우드 -->
                                                                    <div class="ui_col w6" id="rel_cloud">
                                                                        <section class="ui_box">
                                                                            <div class="box_header">
                                                                                <h6>연관어 클라우드</h6>
                                                                                <div class="box_header_rc">
                                                                                    <button type="button" class="ui_btn is-only-web" title="Excel Download"><span class="icon download">-</span><span class="in_loader">Loading</span></button>
                                                                                </div>
                                                                            </div>
                                                                            <div class="box_content">
                                                                                <div id="s_detail_05_cloud" class="ui_elm_cloud small ui_nodata" style="height: 260px; width: 430px;">
                                                                                    <div class="line_area"></div>
                                                                                    <div class="item all"><button type="button"><strong>댓글 연관어</strong><span>총 <span>123,456k</span>건</span></button></div>
                                                                                    <div class="item"><button type="button"><strong>아이템 1</strong><span>총 <span>0</span>건</span></button></div>
                                                                                    <div class="item"><button type="button"><strong>아이템 2</strong><span>총 <span>0</span>건</span></button></div>
                                                                                    <div class="item"><button type="button"><strong>아이템 3</strong><span>총 <span>0</span>건</span></button></div>
                                                                                    <div class="item"><button type="button"><strong>아이템 4</strong><span>총 <span>0</span>건</span></button></div>
                                                                                    <div class="item"><button type="button"><strong>아이템 5</strong><span>총 <span>0</span>건</span></button></div>
                                                                                    <div class="item"><button type="button"><strong>아이템 6</strong><span>총 <span>0</span>건</span></button></div>
                                                                                    <div class="item"><button type="button"><strong>아이템 7</strong><span>총 <span>0</span>건</span></button></div>
                                                                                    <div class="item"><button type="button"><strong>아이템 8</strong><span>총 <span>0</span>건</span></button></div>
                                                                                </div>
                                                                                <script type="text/javascript">
                                                                                    (function(){
                                                                                        $( "#s_detail_05_cloud").elm_cloud({
                                                                                            startDistance : 0.8,
                                                                                            shuffle : true
                                                                                        })
/*                                                                                         $( "#s_detail_05_cloud button" ).click( function($e){
                                                                                            popupMngr.open( '#popup_rel_info' );
                                                                                            return false;
                                                                                        });
 */                                                                                        //$( ".elm_cloud" ).data( "elm_cloud" ).update();
                                                                                    })();
                                                                                </script>
                                                                            </div>
                                                                        </section>
                                                                    </div>
                                                                    <!-- // 연관어 클라우드 -->
                                                                </div>
                                                            </div>
                                                        </section>
                                                    </div>
                                                </div>
                                            </div>
                                        </section>
                                    </div>
                                </div>
                                <!-- // 포탈 뉴스 댓글 현황 -->

                                <!-- 주요 이슈 정보 추이 -->
                                <div class="ui_row nav_sec is-child-pdf-5">
                                    <div class="ui_col">
                                        <section class="ui_box ui_loader_container" id="navi_09">
                                            <div class="box_header" id="issue_list">
                                                <h4>주요 이슈<em id="issue_name"> - 이슈 1</em></h4>
                                                <div class="box_header_rc">
                                                    <div class="dcp is-only-web">
                                                   <!-- <select name="issue_option" id="s09_issue" onchange="chageLangSelect()" style="min-width: 150px; max-width: 300px;"> -->
                                                        <select name="issue_option" id="s09_issue" style="min-width: 150px; max-width: 300px;">
												        <%
												       	String[] tmp_info = null;
												       	 for(int i=0; i<issuelist.size(); i++){
												       		tmp_info = issuelist.get(i);
												      	%>
												       	<option onclick="isuue_ad" value="<%=tmp_info[1]%>"><%=tmp_info[0]%></option>     
												      	<%}%>												      	
                                                        </select><label for="s09_issue"></label>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="box_content">
                                                <div class="ui_row">
                                                    <div class="ui_col">
                                                        <div class="ui_box" id="issue_info">
                                                            <div class="box_header">
                                                                <h5>정보추이</h5>
                                                                <div class="box_header_rc">
                                                                    <button type="button" class="ui_btn is-only-web" title="Excel Download"><span class="icon download">-</span><span class="in_loader">Loading</span></button>
                                                                </div>
                                                            </div>
                                                            <div class="box_content">
                                                                <!-- 차트 -->
                                                                <div id="s09_chart" class="ui_chart_wrap is-not-hand ui_nodata" style="height: 300px"></div> <!-- 데이터 없는 경우 'ui_nodata' 클래스 추가 -->
                                                                <script type="text/javascript">
                                                                </script>
                                                                <!-- // 차트 -->
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="ui_box" id="rel_info">
                                                    <div class="box_header">
                                                        <h5>관련정보</h5>
                                                        <div class="box_header_rc">
                                                            <button type="button" class="ui_btn is-only-web" title="Excel Download"><span class="icon download">-</span><span class="in_loader">Loading</span></button>
                                                        </div>
                                                    </div>
                                                    <div class="box_content" id="relation_information">
                                                        <div class="ui_brd_list">
                                                            <div class="wrap">
                                                                <table>
                                                                    <colgroup>
                                                                        <col>
                                                                        <col style="width: 42px">
                                                                        <col style="width: 140px">
                                                                        <col style="width: 100px">
                                                                        <col style="width: 80px">
                                                                    </colgroup>
                                                                    <thead>
                                                                        <tr>
                                                                            <th><span>제목</span></th>
                                                                            <th><span>링크</span></th>                                                                            
                                                                            <th><span>출처</span></th>
                                                                            <th><span>확산건수</span></th>
                                                                            <th><span>일시</span></th>
                                                                        </tr>
                                                                    </thead>
                                                                    <tbody>
                                                                        <!-- 데이터 없는 경우   -->
                                                                        <tr><td colspan="5" class="no_over"></td></tr>
                                                                        <tr><td colspan="5" class="no_over"></td></tr>
                                                                        <tr><td colspan="5" class="no_over no_data in_list"><span class="ui_no_data_txt">데이터가 없습니다.</span></td></tr>
                                                                        <tr><td colspan="5" class="no_over"></td></tr>
                                                                        <tr><td colspan="5" class="no_over"></td></tr>
                                                                        
<!--                                                                    <tr>
                                                                            <td class="title"><a href="#" class="lnk" target="_blank" title="현대카드 상담사 잘만났네요"><span>현대카드 상담사 잘만났네요</span></a></td>
                                                                            <td>
                                                                                <a href="#" class="btn_cafe_p" title="PC 버전 링크">PC 버전 링크</a>
                                                                                <a href="#" class="btn_cafe_m" title="Mobile 버전 링크">Mobile 버전 링크</a>
                                                                            </td>                                                                            
                                                                            <td><span title="뽐뿌">뽐뿌</span></td>
                                                                            <td><span>123,456k</span></td>
                                                                            <td><span>01/01 11:33</span></td>
                                                                        </tr>
                                                                        <tr><td colspan="5" class="no_over"></td></tr>
                                                                        <tr><td colspan="5" class="no_over"></td></tr>
                                                                        <tr><td colspan="5" class="no_over"></td></tr>
                                                                        <tr><td colspan="5" class="no_over"></td></tr>       -->             
                                                                    </tbody>
                                                                </table>
                                                            </div>
                                                            <div class="footer is-only-web">
                                                                <div class="ui_paginate">
                                                                    <div class="in_wrap">
                                                                        <a href="#" class="page_prev ui_disabled" onclick="return false">이전페이지</a>
                                                                        <a href="#" class="" onclick="return false;">1</a>
                                                                        <a href="#" class="active" onclick="return false;">2</a>
                                                                        <a href="#" onclick="return false;">3</a>
                                                                        <a href="#" onclick="return false;">4</a>
                                                                        <a href="#" onclick="return false;">5</a>
                                                                        <a href="#" class="page_next disabled" onclick="return false;">다음페이지</a>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </section>
                                    </div>
                                </div>
                                <!-- // 주요 이슈 정보 추이 -->

                            </div>
                        </div>
                    </div>
                    <!-- // 상세 항복 더보기 -->

				</div>
				<!-- // Content -->

			</div>
		</div>

		<!-- Popup -->
		<div id="popup_container">
            <div class="bg"></div>

            <!-- 관련정보 -->
			<jsp:include page="../common/popup/popup_rel_info.jsp" flush="false" />
            <!-- // 관련정보 -->

            <!-- VOC -->
			<jsp:include page="../common/popup/popup_voc.jsp" flush="false" />
            <!-- // VOC -->            
		</div>
		<!-- // Popup -->
    </div>
    <iFrame id="processFrm" name ="processFrm" width="0" height="0" title="excel data frame" style="display: none;" ></iFrame>

    <!-- <canvas id="canvas" width="1040" height="5700"></canvas> -->
<!-- <script type="text/javascript" src="../common/js/timer.js"></script> -->
<script type="text/javascript" src="../common/js/common_devel.js"></script>
<script type="text/javascript" src="../common/js/devel.js"></script>
<jsp:include page="../inc/inc_page_bot.jsp" flush="false" />
