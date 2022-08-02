<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="risk.util.StringUtil"%>
<%@page import="risk.util.DateUtil"%>
<%@page import="risk.util.ParseRequest"%>
<%@page import="java.util.ArrayList"%>
<%@page import="risk.dashboard.view.press_release.PressReleaseMgr"%>
<%
	ParseRequest pr = new ParseRequest(request);
	DateUtil du = new DateUtil();
	StringUtil	su 	= new StringUtil();
	PressReleaseMgr mgr = new PressReleaseMgr();
	
	/*시간셋팅****************************************************************/
	String today = du.getCurrentDate("yyyy-MM-dd");
	String sDate = pr.getString("sDate",du.addDay_v2(today,-29));	//"기본 30일";
	
	//현재 달 구하기
	int monthValue = Integer.parseInt(today.split("-")[1]);
	int pre_monthValue = monthValue -1;
	if(pre_monthValue == 0) {
		pre_monthValue = 12;
	}
	/*************************************************************************/
	
	//사이트그룹
	ArrayList<String[]> siteGroupList = mgr.getSiteGroupList();
	
%>
<jsp:include page="../inc/inc_page_top.jsp" flush="false" />

<body>

    <script>
        gnbIDX = "02";
    </script>

	<div id="wrap">
		<!-- Include HEADER -->
		<jsp:include page="../inc/inc_header.jsp" flush="false" />
		<script type="text/javascript" src="./js/index.js"></script> 
		<script type="text/javascript" src="../common/js/timer.js"></script>
		<script type="text/javascript" src="../common/js/common_devel.js"></script>
		<script type="text/javascript" src="../common/popup/js/popup.js"></script>
		<!-- // Include HEADER -->

		<div id="container">
			<h2 id="page_title" class="ui_invisible">&nbsp;</h2>
			<div id="content" class="page-brand">

				<!-- Search -->
				<section id="top_searchs" class="ui_searchs">
                    <h3 class="ui_invisible">검색조건</h3>
                    <div class="wrap">
                        <!-- 검색조건 설정 -->
						<form id="searchs_frm" class="searchs_inputs">
                            <div class="inputs_wrap">
                                <div class="searchs_table">
                                    <table>
                                        <colgroup>
                                            <col>
                                        </colgroup>
                                        <tbody>
                                            <tr>
                                                <td>
                                                    <div class="fl">
                                                        <strong>검색기간</strong>
                                                        <div class="ui_datepickers" id="dp11" data-date='{ "sDate": "<%=sDate %>", "eDate": "<%=today %>" }' data-move="true" data-grps='[ "1", "7", "30", "<%=pre_monthValue %>MT", "<%=monthValue %>MT" ]' data-type-opts="disabled" ></div>
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="fr">
                                                        <hr>
                                                        <button type="button" class="ui_btn is-small btn_search " style="width: 66px"><span class="txt">검색</span></button>
                                                    </div>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </form>
                        <!-- // 검색조건 설정 -->
                    </div>
                </section>
                <!-- // Search -->
                
                <!-- SideBar -->
                <aside id="sidebar">
                    <div class="side-wrap">
                        <div class="side-box">
                            <ul>
                                <li>
                                    <a href="#qid_01">보도자료 목록 및 확산 추적</a>
                                    <ul>
                                        <li><a href="#qid_01_01">보도자료 확산 채널 추적</a></li>
                                        <li><a href="#qid_01_02">보도자료 확산 추이</a></li>
                                        <li><a href="#qid_01_03">보도자료 커버링 데이터 목록</a></li>
                                    </ul>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <button type="button" class="side-topBtn"><span>TOP</span></button>
                </aside>
                <!-- // SideBar -->

				<!-- Content -->
				<div class="wrap">
                    <section class="ui_section">
                        <div class="wrap">
                            
                            <div class="ui_row" id="qid_01">
                                <!-- 보도자료 목록 및 확산 추적 -->
                                <div class="ui_col">
                                    <div class="wrap">
                                        <div class="ui_box ui_loader_container /*is-loading*/">
                                            <div class="box_header">
                                                <h4>보도자료 목록 및 확산 추적</h4>
                                                <div class="box_header_rc">
                                                    <button type="button" class="ui_btn is-icon-only" data-bubble-id="qid_01_bubble"><span class="icon bubble">&#xe088;</span></button>
                                                    <div class="ui_bubble_box" data-bubble-for="qid_01_bubble" data-pos="RB" data-arrowcenter="true">
                                                        <span class="arrow"></span>
                                                        <div class="tip">
                                                            <strong class="title"><span>Guide</span></strong>
                                                            <span class="txt">자사 보도자료 확산량 및 상세 추이</span>
                                                        </div>
                                                    </div>
                                                    <button type="button" class="ui_btn is-icon-only download_top"><span class="icon">&#xe005;</span></button>
                                                </div> 
                                            </div>
                                            <div class="box_content">
                                                <div class="ui_brd_list" id="qid_01_div">
                                                    <div class="header is-pad-t-7 no_bg">
                                                        <div class="lc">
                                                          <span class="page_info">Total: <span class="cnt">115</span>
                                                            <span class="page">(<em>1</em>/29p)</span></span>
                                                        </div>
                                                    </div>
                                                    <div class="wrap">
                                                        <table>
                                                            <colgroup>
                                                            <col style="width: 88px;">
                                                            <col style="width: 109px;">
                                                            <col>
                                                            <col style="width: 136px;">
                                                            <col style="width: 88px;">
                                                            </colgroup>
                                                            <thead>
                                                            <tr>
                                                                <th>담당부서</th>
                                                                <th>배포일자</th>
                                                                <th>보도자료명</th>
                                                                <th>최초출처</th>
                                                                <th>확산량</th>
                                                            </tr>
                                                            </thead>
                                                            <tbody>
                                                            <!-- 데이터 없는 경우 -->
                                                            <tr><td colspan="5" class="no_over"></td></tr>
                                                            <tr><td colspan="5" class="no_over"></td></tr>
                                                            <tr><td colspan="5" class="no_over"></td></tr>
                                                            <tr><td colspan="5" class="no_over"></td></tr>
                                                            <tr><td colspan="5" class="no_over"></td></tr>
                                                            <tr><td colspan="5" class="no_over no_data in_list">키워드가 없습니다.</td></tr>
                                                            <tr><td colspan="5" class="no_over"></td></tr>
                                                            <tr><td colspan="5" class="no_over"></td></tr>
                                                            <tr><td colspan="5" class="no_over"></td></tr>
                                                            <tr><td colspan="5" class="no_over"></td></tr>
                                                            <!-- <tr class="active">
                                                                <td>마케팅</td>
                                                                <td>2021-08-04</td>
                                                                <td class="ui_al"><a href="#" class="lnk" onclick="trActive( this ); return false;"><span class="txt">빙그레 건강 tft, 신제품 ‘마노플랜 눈 건강’ 선봬빙그레 건강 tft, 신제품 ‘마노플랜 눈 건강’ 선봬빙그레 건강 tft, 신제품 ‘마노플랜 눈 건강’ 선봬빙그레 건강 tft, 신제품 ‘마노플랜 눈 건강’ 선봬빙그레 건강 tft, 신제품 ‘마노플랜 눈 건강’ 선봬빙그레 건강 tft, 신제품 ‘마노플랜 눈 건강’ 선봬빙그레 건강 tft, 신제품 ‘마노플랜 눈 건강’ 선봬빙그레 건강 tft, 신제품 ‘마노플랜 눈 건강’ 선봬</span></a></td>
                                                                <td>에너지경제신문</td>
                                                                <td>85</td>
                                                            </tr>
                                                            <tr>
                                                                <td>마케팅</td>
                                                                <td>2021-08-04</td>
                                                                <td class="ui_al"><a href="#" class="lnk" onclick="trActive( this ); return false;"><span class="txt">빙그레 건강 tft, 신제품 ‘마노플랜 눈 건강’ 선봬</span></a></td>
                                                                <td>에너지경제신문</td>
                                                                <td>85</td>
                                                            </tr>
                                                            <tr>
                                                                <td>마케팅</td>
                                                                <td>2021-08-04</td>
                                                                <td class="ui_al"><a href="#" class="lnk" onclick="trActive( this ); return false;"><span class="txt">빙그레 건강 tft, 신제품 ‘마노플랜 눈 건강’ 선봬</span></a></td>
                                                                <td>에너지경제신문</td>
                                                                <td>85</td>
                                                            </tr>
                                                            <tr>
                                                                <td>마케팅</td>
                                                                <td>2021-08-04</td>
                                                                <td class="ui_al"><a href="#" class="lnk" onclick="trActive( this ); return false;"><span class="txt">빙그레 건강 tft, 신제품 ‘마노플랜 눈 건강’ 선봬</span></a></td>
                                                                <td>에너지경제신문</td>
                                                                <td>85</td>
                                                            </tr>
                                                            <tr>
                                                                <td>마케팅</td>
                                                                <td>2021-08-04</td>
                                                                <td class="ui_al"><a href="#" class="lnk" onclick="trActive( this ); return false;"><span class="txt">빙그레 건강 tft, 신제품 ‘마노플랜 눈 건강’ 선봬</span></a></td>
                                                                <td>에너지경제신문</td>
                                                                <td>85</td>
                                                            </tr>
                                                            <tr>
                                                                <td>마케팅</td>
                                                                <td>2021-08-04</td>
                                                                <td class="ui_al"><a href="#" class="lnk" onclick="trActive( this ); return false;"><span class="txt">빙그레 건강 tft, 신제품 ‘마노플랜 눈 건강’ 선봬</span></a></td>
                                                                <td>에너지경제신문</td>
                                                                <td>85</td>
                                                            </tr>
                                                            <tr>
                                                                <td>마케팅</td>
                                                                <td>2021-08-04</td>
                                                                <td class="ui_al"><a href="#" class="lnk" onclick="trActive( this ); return false;"><span class="txt">빙그레 건강 tft, 신제품 ‘마노플랜 눈 건강’ 선봬</span></a></td>
                                                                <td>에너지경제신문</td>
                                                                <td>85</td>
                                                            </tr>
                                                            <tr>
                                                                <td>마케팅</td>
                                                                <td>2021-08-04</td>
                                                                <td class="ui_al"><a href="#" class="lnk" onclick="trActive( this ); return false;"><span class="txt">빙그레 건강 tft, 신제품 ‘마노플랜 눈 건강’ 선봬</span></a></td>
                                                                <td>에너지경제신문</td>
                                                                <td>85</td>
                                                            </tr>
                                                            <tr class="no_over"><td colspan="5"></td></tr>
                                                            <tr class="no_over"><td colspan="5"></td></tr> -->
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                    <!-- <div class="footer">
                                                        <div class="ui_paginate">
                                                            <div class="in_wrap">
                                                                <a href="#" class="page_prev prev ui_disabled" onclick="return false"></a>
                                                                <a href="#" class="page_prev ui_disabled" onclick="return false"></a>
                                                                <a href="#" class="" onclick="return false;">1</a>
                                                                <a href="#" class="active" onclick="return false;">2</a>
                                                                <a href="#" onclick="return false;">3</a>
                                                                <a href="#" onclick="return false;">4</a>
                                                                <a href="#" onclick="return false;">5</a>
                                                                <a href="#" onclick="return false;">6</a>
                                                                <a href="#" onclick="return false;">7</a>
                                                                <a href="#" onclick="return false;">8</a>
                                                                <a href="#" onclick="return false;">9</a>
                                                                <a href="#" onclick="return false;">10</a>
                                                                <a href="#" class="page_next disabled" onclick="return false;"></a>
                                                                <a href="#" class="page_next next disabled" onclick="return false;"></a>
                                                            </div>
                                                        </div>
                                                    </div> -->
                                                </div>
                                                <div class="wrap is-2-depth">
                                                	<input type="hidden" name="pressReleaseIc_code" id="pressReleaseIc_code">
                                                	<input type="hidden" name="pressReleaseSDate" id="pressReleaseSDate">
                                                	<input type="hidden" name="pressReleaseEDate" id="pressReleaseEDate">
                                                    <!-- 보도자료 확산 채널 추적 -->
                                                    <div class="ui_row" id="qid_01_01">
                                                        <div class="ui_col is-no-pad">
                                                            <div class="ui_box is-2-depth ui_loader_container">
                                                                <div class="box_header">
                                                                    <h5><span>보도자료 확산 채널 추적</span><em>‘빙그레 건강 tft, 신제품 ‘마노플랜 눈 건강’ 선봬 빙그레 건강 tft, 신제품 ‘마노플랜 눈 건강’ 선봬 빙그레 건강 tft, 신제품 ‘마노플랜 눈 건강’ 선봬 빙그레 건강 tft, 신제품 ‘마노플랜 눈 건강’ 선봬‘</em> <span>관련</span></h5>
                                                                    <div class="box_header_rc">
                                                                        <button type="button" class="ui_btn is-icon-only small" data-bubble-id="qid_02_01_01_bubble"><span class="icon bubble">&#xe088;</span></button>
                                                                        <div class="ui_bubble_box" data-bubble-for="qid_02_01_01_bubble" data-pos="RB" data-arrowcenter="true">
                                                                                <span class="arrow"></span>
                                                                                <div class="tip">
                                                                                <strong class="title"><span>Guide</span></strong>
                                                                                <span class="txt">선택한 보도자료의 채널별 정보량 그래프</span>
                                                                            </div>
                                                                        </div>
                                                                        <button type="button" class="ui_btn is-icon-only small download"><span class="icon">&#xe005;</span></button>
                                                                    </div>
                                                                </div>
                                                                <div class="box_content" id="qid_01_01_div">
                                                                    <!-- 컬럼 -->
                                                                    <div id="qid_01_01_chart_01" class="ui_chart_wrap" style="height: 440px;"></div>       <!-- 데이터 없는 경우/초기 'ui_no_data' 클래스 추가-->
                                                                    <script>
                                                                        // 컬럼
                                                                        (function(){
                                                                            var chart = AmCharts.makeChart( "qid_01_01_chart_01", 
                                                                                {
                                                                                    "type": "serial",
                                                                                    "categoryField": "category",
                                                                                    "addClassNames": true,
                                                                                    "fontSize": 12,
                                                                                    "columnWidth": 0.35,
                                                                                    "autoMarginOffset": 0,
                                                                                    "marginRight": 10,
                                                                                    "marginTop": 48,
                                                                                    "percentPrecision": 1,
                                                                                    "colors": [
                                                                                        "#5ba1e0",
                                                                                        "#ea7070",
                                                                                        "#808080",
                                                                                    ],
                                                                                    "color": "#505050",
                                                                                    "categoryAxis": {
                                                                                        "labelOffset": -2,
                                                                                        "equalSpacing": true,
                                                                                        "gridPosition": "start",
                                                                                        "axisColor": "#ececec",
                                                                                        "gridThickness": 0,
                                                                                        "markPeriodChange": false,
                                                                                        "color": "#666666",
                                                                                        "fontSize": 11,
                                                                                    },
                                                                                    "chartCursor": {
                                                                                        "enabled": true,
                                                                                        "animationDuration": 0,
                                                                                        "categoryBalloonDateFormat": "MM-DD",
                                                                                        "categoryBalloonColor": "#212121",
                                                                                        "cursorAlpha": 0.1,
                                                                                        "cursorColor": "#000000",
                                                                                        "fullWidth": true
                                                                                    },
                                                                                    "trendLines": [],
                                                                                    "graphs": [
                                                                                        {
                                                                                            // "balloonText": "<strong style='color: #5699d5;'>[[title]]</strong><span style='color:#5699d5; font-size: 12px;'>: [[value]]</span> <span style='color:#5699d5'>([[percents]]%)</span>",
                                                                                            "balloonText": "<strong style='color: #5699d5;'>[[title]]: [[value]] <span style='font-size: 11px;'>([[percents]]%)</span></strong>",
                                                                                            "fillAlphas": 1,
                                                                                            "id": "AmGraph-1",
                                                                                            "labelText": "[[value]]",
                                                                                            "fontSize": 14,
                                                                                            "color": "#ffffff",
                                                                                            "title": "긍정",
                                                                                            "type": "column",
                                                                                            "valueField": "column-1"
                                                                                        },
                                                                                        {
                                                                                            // "balloonText": "<strong style='color: #de6a6a;'>[[title]]</strong><span style='color:#de6a6a; font-size: 12px;'>: [[value]]</span> <span style='color:#de6a6a'>([[percents]]%)</span>",
                                                                                            "balloonText": "<strong style='color: #de6a6a;'>[[title]]: [[value]] <span style='font-size: 11px;'>([[percents]]%)</span></strong>",
                                                                                            "fillAlphas": 1,
                                                                                            "id": "AmGraph-2",
                                                                                            "labelText": "[[value]]",
                                                                                            "fontSize": 14,
                                                                                            "color": "#ffffff",
                                                                                            "title": "부정",
                                                                                            "type": "column",
                                                                                            "valueField": "column-2"
                                                                                        },
                                                                                        {
                                                                                            // "balloonText": "<strong style='color: #797979;'>[[title]]</strong><span style='color:#797979; font-size: 12px;'>: [[value]]</span> <span style='color:#797979'>([[percents]]%)</span>",
                                                                                            "balloonText": "<strong style='color: #797979;'>[[title]]: [[value]] <span style='font-size: 11px;'>([[percents]]%)</span></strong>",
                                                                                            "fillAlphas": 1,
                                                                                            "id": "AmGraph-3",
                                                                                            "labelText": "[[value]]",
                                                                                            "fontSize": 14,
                                                                                            "color": "#ffffff",
                                                                                            "title": "중립",
                                                                                            "type": "column",
                                                                                            "valueField": "column-3"
                                                                                        }
                                                                                    ],
                                                                                    "guides": [],
                                                                                    "valueAxes": [
                                                                                        {
                                                                                            "id": "ValueAxis-1",
                                                                                            "stackType": "regular",
                                                                                            "zeroGridAlpha": 0,
                                                                                            "axisThickness": 0,
                                                                                            "color": "#999999",
                                                                                            "fontSize": 10,
                                                                                            "dashLength": 4,
                                                                                            "gridAlpha": 1,
                                                                                            "gridColor": "#E9E9E9",
                                                                                            "tickLength": 0,
                                                                                            "title": ""
                                                                                        }
                                                                                    ],
                                                                                    "allLabels": [],
                                                                                    "balloon": {
                                                                                        "fillAlpha": 0.95,
                                                                                        "borderThickness": 1,
                                                                                        "animationDuration": 0
                                                                                    },
                                                                                    "legend": {
                                                                                        "enabled": true,
                                                                                        "align": "center",
                                                                                        "markerType": "round",
                                                                                        "autoMargins": false,
                                                                                        "color": "#999999",
                                                                                        "fontSize": 11,
                                                                                        "marginLeft": 0,
                                                                                        "marginRight": 0,
                                                                                        "marginTop": 10,
                                                                                        "markerSize": 8,
                                                                                        "position": "bottom",
                                                                                        "spacing": 20,
                                                                                        "valueText": "",
                                                                                        "valueWidth": 0,
                                                                                        "verticalGap": 0
                                                                                    },
                                                                                    "titles": [],
                                                                                    "dataProvider": [
                                                                                        /* {
                                                                                            "category": "언론",
                                                                                            "column-1": 8,
                                                                                            "column-2": 5,
                                                                                            "column-3": 11
                                                                                        },
                                                                                        {
                                                                                            "category": "포탈",
                                                                                            "column-1": 6,
                                                                                            "column-2": 7,
                                                                                            "column-3": 9
                                                                                        },
                                                                                        {
                                                                                            "category": "블로그",
                                                                                            "column-1": 2,
                                                                                            "column-2": 3,
                                                                                            "column-3": 10
                                                                                        },
                                                                                        {
                                                                                            "category": "카페",
                                                                                            "column-1": 8,
                                                                                            "column-2": 5,
                                                                                            "column-3": 11
                                                                                        },
                                                                                        {
                                                                                            "category": "커뮤니티",
                                                                                            "column-1": 6,
                                                                                            "column-2": 7,
                                                                                            "column-3": 9
                                                                                        },
                                                                                        {
                                                                                            "category": "트위터",
                                                                                            "column-1": 2,
                                                                                            "column-2": 3,
                                                                                            "column-3": 10
                                                                                        },
                                                                                        {
                                                                                            "category": "유튜브",
                                                                                            "column-1": 8,
                                                                                            "column-2": 5,
                                                                                            "column-3": 11
                                                                                        },
                                                                                        {
                                                                                            "category": "페이스북",
                                                                                            "column-1": 8,
                                                                                            "column-2": 5,
                                                                                            "column-3": 11
                                                                                        },
                                                                                        {
                                                                                            "category": "인스타그램",
                                                                                            "column-1": 6,
                                                                                            "column-2": 7,
                                                                                            "column-3": 9
                                                                                        },
                                                                                        {
                                                                                            "category": "정부기관",
                                                                                            "column-1": 2,
                                                                                            "column-2": 3,
                                                                                            "column-3": 10
                                                                                        },
                                                                                        {
                                                                                            "category": "공공/단체",
                                                                                            "column-1": 8,
                                                                                            "column-2": 5,
                                                                                            "column-3": 11
                                                                                        }, */
                                                                                    ]
                                                                                }
                                                                            );
                                                                            chart.addListener( "clickGraphItem", function( $e ){
                                                                                //popupMngr.open( '#popup_rel_info_detail' );
                                                                            })
                                                                        })(); 
                                                                    </script>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
    
                                                    <!-- 보도자료 확산 추이 -->
                                                    <div class="ui_row" id="qid_01_02">
                                                        <div class="ui_col is-no-pad">
                                                            <div class="ui_box is-2-depth ui_loader_container">
                                                                <div class="box_header">
                                                                    <h5><span>보도자료 확산 추이</span><em>‘빙그레 건강 tft, 신제품 ‘마노플랜 눈 건강’ 선봬 ‘</em> <span>관련</span></h5>
                                                                    <div class="box_header_rc">
                                                                        <button type="button" class="ui_btn is-icon-only small" data-bubble-id="qid_02_01_02_bubble"><span class="icon bubble">&#xe088;</span></button>
                                                                        <div class="ui_bubble_box" data-bubble-for="qid_02_01_02_bubble" data-pos="RB" data-arrowcenter="true">
                                                                                <span class="arrow"></span>
                                                                                <div class="tip">
                                                                                <strong class="title"><span>Guide</span></strong>
                                                                                <span class="txt">선택한 보도자료의 기간별 성향 그래프</span>
                                                                            </div>
                                                                        </div>
                                                                        <button type="button" class="ui_btn is-icon-only small download"><span class="icon">&#xe005;</span></button>
                                                                    </div>
                                                                </div>
                                                                <div class="box_content" id="qid_01_02_div">
                                                                    <!-- 컬럼 -->
                                                                    <div id="qid_01_02_chart_01" class="ui_chart_wrap" style="height: 340px;"></div>       <!-- 데이터 없는 경우/초기 'ui_no_data' 클래스 추가-->
                                                                    <script>
                                                                        // 컬럼
                                                                        (function(){
                                                                            var chart = AmCharts.makeChart( "qid_01_02_chart_01", 
                                                                                {
                                                                                    "type": "serial",
                                                                                    "categoryField": "category",
                                                                                    "addClassNames": true,
                                                                                    "fontSize": 12,
                                                                                    "columnWidth": 0.35,
                                                                                    "autoMarginOffset": 0,
                                                                                    "marginRight": 10,
                                                                                    "marginTop": 48,
                                                                                    "percentPrecision": 1,
                                                                                    "colors": [
                                                                                        "#5ba1e0",
                                                                                        "#ea7070",
                                                                                        "#808080",
                                                                                    ],
                                                                                    "color": "#505050",
                                                                                    "categoryAxis": {
                                                                                        "labelOffset": -2,
                                                                                        "equalSpacing": true,
                                                                                        "gridPosition": "start",
                                                                                        "axisColor": "#ececec",
                                                                                        "gridThickness": 0,
                                                                                        "markPeriodChange": false,
                                                                                        "color": "#666666",
                                                                                        "fontSize": 11,
                                                                                        "autoGridCount": false,		//날짜 안겹치게
                                                                                    },
                                                                                    "chartCursor": {
                                                                                        "enabled": true,
                                                                                        "animationDuration": 0,
                                                                                        "categoryBalloonDateFormat": "MM-DD",
                                                                                        "categoryBalloonColor": "#212121",
                                                                                        "cursorAlpha": 0.1,
                                                                                        "cursorColor": "#000000",
                                                                                        "fullWidth": true
                                                                                    },
                                                                                    "trendLines": [],
                                                                                    "graphs": [
                                                                                        {
                                                                                            // "balloonText": "<strong style='color: #5699d5;'>[[title]]</strong><span style='color:#5699d5; font-size: 12px;'>: [[value]]</span> <span style='color:#5699d5'>([[percents]]%)</span>",
                                                                                            "balloonText": "<strong style='color: #5699d5;'>[[title]]: [[value]] <span style='font-size: 11px;'>([[percents]]%)</span></strong>",
                                                                                            "id": "AmGraph-1",
			                                                                                "bullet": "round",
			                                                                                "lineThickness": 2,
                                                                                            "title": "긍정",
                                                                                            "valueField": "column-1"
                                                                                        },
                                                                                        {
                                                                                            // "balloonText": "<strong style='color: #de6a6a;'>[[title]]</strong><span style='color:#de6a6a; font-size: 12px;'>: [[value]]</span> <span style='color:#de6a6a'>([[percents]]%)</span>",
                                                                                            "balloonText": "<strong style='color: #de6a6a;'>[[title]]: [[value]] <span style='font-size: 11px;'>([[percents]]%)</span></strong>",
                                                                                            "id": "AmGraph-2",
			                                                                                "bullet": "round",
			                                                                                "lineThickness": 2,
                                                                                            "title": "부정",
                                                                                            "valueField": "column-2"
                                                                                        },
                                                                                        {
                                                                                            // "balloonText": "<strong style='color: #797979;'>[[title]]</strong><span style='color:#797979; font-size: 12px;'>: [[value]]</span> <span style='color:#797979'>([[percents]]%)</span>",
                                                                                            "balloonText": "<strong style='color: #797979;'>[[title]]: [[value]] <span style='font-size: 11px;'>([[percents]]%)</span></strong>",
                                                                                            "id": "AmGraph-3",
			                                                                                "bullet": "round",
			                                                                                "lineThickness": 2,
                                                                                            "title": "중립",
                                                                                            "valueField": "column-3"
                                                                                        }
                                                                                    ],
                                                                                    "guides": [],
                                                                                    "valueAxes": [
                                                                                        {
                                                                                            "id": "ValueAxis-1",
                                                                                            "zeroGridAlpha": 0,
                                                                                            "axisThickness": 0,
                                                                                            "color": "#999999",
                                                                                            "fontSize": 10,
                                                                                            "dashLength": 4,
                                                                                            "gridAlpha": 1,
                                                                                            "gridColor": "#E9E9E9",
                                                                                            "tickLength": 0,
                                                                                            "title": ""
                                                                                        }
                                                                                    ],
                                                                                    "allLabels": [],
                                                                                    "balloon": {
                                                                                        "fillAlpha": 0.95,
                                                                                        "borderThickness": 1,
                                                                                        "animationDuration": 0
                                                                                    },
                                                                                    "legend": {
                                                                                        "enabled": true,
		                                                                                "useGraphSettings": true,
                                                                                        "align": "center",
                                                                                        "autoMargins": false,
                                                                                        "color": "#999999",
                                                                                        "fontSize": 11,
                                                                                        "marginLeft": 0,
                                                                                        "marginRight": 0,
                                                                                        "marginTop": 10,
                                                                                        "markerSize": 8,
                                                                                        "position": "bottom",
                                                                                        "spacing": 20,
                                                                                        "valueText": "",
                                                                                        "valueWidth": 0,
                                                                                        "verticalGap": 0
                                                                                    },
                                                                                    "titles": [],
                                                                                    "dataProvider": [
                                                                                        {
                                                                                            "category": "2021-08-01",
                                                                                            "column-1": 8,
                                                                                            "column-2": 5,
                                                                                            "column-3": 11
                                                                                        },
                                                                                        {
                                                                                            "category": "2021-08-02",
                                                                                            "column-1": 6,
                                                                                            "column-2": 7,
                                                                                            "column-3": 9
                                                                                        },
                                                                                        {
                                                                                            "category": "2021-08-03",
                                                                                            "column-1": 2,
                                                                                            "column-2": 3,
                                                                                            "column-3": 10
                                                                                        },
                                                                                        {
                                                                                            "category": "2021-08-04",
                                                                                            "column-1": 8,
                                                                                            "column-2": 5,
                                                                                            "column-3": 11
                                                                                        },
                                                                                        {
                                                                                            "category": "2021-08-05",
                                                                                            "column-1": 6,
                                                                                            "column-2": 7,
                                                                                            "column-3": 9
                                                                                        },
                                                                                        {
                                                                                            "category": "2021-08-06",
                                                                                            "column-1": 2,
                                                                                            "column-2": 3,
                                                                                            "column-3": 10
                                                                                        },
                                                                                        {
                                                                                            "category": "2021-08-07",
                                                                                            "column-1": 8,
                                                                                            "column-2": 5,
                                                                                            "column-3": 11
                                                                                        },
                                                                                        {
                                                                                            "category": "2021-08-08",
                                                                                            "column-1": 8,
                                                                                            "column-2": 5,
                                                                                            "column-3": 11
                                                                                        }
                                                                                    ]
                                                                                }
                                                                            );
                                                                            chart.addListener( "clickGraphItem", function( $e ){
                                                                                popupMngr.open( '#popup_rel_info_detail' );
                                                                            })
                                                                        })();
                                                                    </script>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
    
                                                    <!-- 보도자료 커버링 데이터 목록  -->
                                                    <div class="ui_row" id="qid_01_03">
                                                        <div class="ui_col is-no-pad">
                                                            <div class="ui_box is-2-depth ui_loader_container">
                                                                <div class="box_header">
                                                                    <h5><span>보도자료 커버링 데이터 목록</span><em style="max-width: 550px;">‘빙그레 건강 tft, 신제품 ‘마노플랜 눈 건강’ 선봬 ‘</em> <span>관련</span></h5>
                                                                    <div class="box_header_rc">
                                                                        <div class="dcp is-mselect is-small" data-label="채널" data-value="102,100,99,109,97,95,94,93,110,111,112,113" style="width: 120px" >
                                                                            <select id="qid_01_03_01_chart_sel_01" multiple>
                                                                                <!-- <option value="1">채널 전체</option> -->
                                                                                <%
                                                                                String[] data = new String[2];
                                                                                for(int i = 0;i < siteGroupList.size();i++) {
                                                                                	data = siteGroupList.get(i);
                                                                                %>
                                                                                <option value="<%=data[0]%>"><%=data[1]%></option>
                                                                                <%	
                                                                                }
                                                                                %>
                                                                                <!-- <option value="1">언론</option>
                                                                                <option value="2">포탈</option>
                                                                                <option value="3">블로그</option>
                                                                                <option value="4">카페</option>
                                                                                <option value="5">커뮤니티</option>
                                                                                <option value="99">트위터</option>
                                                                                <option value="102">유튜브</option>
                                                                                <option value="100">페이스북</option>
                                                                                <option value="9">인스타그램</option>
                                                                                <option value="10">정부기관</option>
                                                                                <option value="11">공공/단체</option>
                                                                                <option value="12">기타</option> -->
                                                                            </select>
                                                                            <label for="qid_01_03_01_chart_sel_01"></label>
                                                                        </div>
                                                                        <div class="dcp is-mselect is-small" data-label="성향" data-value="1,2,3" style="width: 120px" >
                                                                            <select id="qid_01_03_01_chart_sel_02" multiple>
                                                                                <option value="1">긍정</option>
                                                                                <option value="2">부정</option>
                                                                                <option value="3">중립</option>
                                                                            </select>
                                                                            <label for="qid_01_03_01_chart_sel_02"></label>
                                                                        </div>
                                                                        <button type="button" class="ui_btn is-icon-only small" data-bubble-id="qid_01_03_bubble"><span class="icon bubble">&#xe088;</span></button>
                                                                        <div class="ui_bubble_box" data-bubble-for="qid_01_03_bubble" data-pos="RB" data-arrowcenter="true">
                                                                                <span class="arrow"></span>
                                                                                <div class="tip">
                                                                                <strong class="title"><span>Guide</span></strong>
                                                                                <span class="txt">선택한 보도자료의 커버링 데이터 목록</span>
                                                                            </div>
                                                                        </div>
                                                                        <button type="button" class="ui_btn is-icon-only small download"><span class="icon">&#xe005;</span></button>
                                                                    </div>
                                                                </div>
                                                                <div class="box_content">
                                                                    <div class="ui_brd_list" id="qid_01_03_div">
                                                                        <div class="header is-pad-t-7 no_bg">
                                                                            <div class="lc">
                                                                              <span class="page_info">Total: <span class="cnt">115</span>
                                                                                <span class="page">(<em>1</em>/29p)</span></span>
                                                                            </div>
                                                                        </div>
                                                                        <div class="wrap">
                                                                            <table>
                                                                                <colgroup>
                                                                                <col style="width:110px">
                                                                                <col style="width:118px">
                                                                                <col>
                                                                                <col style="width:128px">
                                                                                <col style="width:92px">
                                                                                <col style="width:100px">
                                                                                </colgroup>
                                                                                <thead>
                                                                                <tr>
                                                                                    <th scope="col"><span>일자</span></th>
                                                                                    <th scope="col"><span>채널</span></th>
                                                                                    <th scope="col"><span>제목</span></th>
                                                                                    <th scope="col"><span>출처</span></th>
                                                                                    <th scope="col"><span>기자명</span></th>
                                                                                    <th scope="col"><span>성향</span></th>
                                                                                </tr>
                                                                                </thead>
                                                                                <tbody>
                                                                                    <!-- 데이터 없는 경우
                                                                                    <tr><td colspan="6" class="no_over"></td></tr>
                                                                                    <tr><td colspan="6" class="no_over"></td></tr>
                                                                                    <tr><td colspan="6" class="no_over"></td></tr>
                                                                                    <tr><td colspan="6" class="no_over"></td></tr>
                                                                                    <tr><td colspan="6" class="no_over no_data"><span>키워드가 없습니다.</span></td></tr>
                                                                                    <tr><td colspan="6" class="no_over"></td></tr>
                                                                                    <tr><td colspan="6" class="no_over"></td></tr>
                                                                                    <tr><td colspan="6" class="no_over"></td></tr>
                                                                                    <tr><td colspan="6" class="no_over"></td></tr>
                                                                                    <tr><td colspan="6" class="no_over"></td></tr> -->
                                                                                    <tr>
                                                                                        <td>2021-07-21</td>
                                                                                        <td>언론</td>
                                                                                        <td class="ui_al"><a href="#" class="lnk " target="_blank" title="상담사 잘만났네요"><span>상담사 잘만났네요</span></a></td>
                                                                                        <td>에너지경제신문</td>
                                                                                        <td>홍길동</td>
                                                                                        <td><div class="ui_label fc_positive"><span>긍정</span></div></td>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <td>2021-07-21</td>
                                                                                        <td>포탈</td>
                                                                                        <td class="ui_al"><a href="#" class="lnk " target="_blank" title="상담사 잘만났네요"><span>상담사 잘만났네요</span></a></td>
                                                                                        <td>경제신문</td>
                                                                                        <td>홍길동</td>
                                                                                        <td><div class="ui_label fc_neutral"><span>중립</span></div></td>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <td>2021-07-21</td>
                                                                                        <td>언론</td>
                                                                                        <td class="ui_al"><a href="#" class="lnk " target="_blank" title="상담사 잘만났네요"><span>상담사 잘만났네요</span></a></td>
                                                                                        <td>커뮤니티</td>
                                                                                        <td>홍길동</td>
                                                                                        <td><div class="ui_label fc_negative"><span>부정</span></div></td>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <td>2021-07-21</td>
                                                                                        <td>인스타그램</td>
                                                                                        <td class="ui_al"><a href="#" class="lnk " target="_blank" title="상담사 잘만났네요"><span>상담사 잘만났네요</span></a></td>
                                                                                        <td>커뮤니티</td>
                                                                                        <td>홍길동</td>
                                                                                        <td><div class="ui_label fc_negative"><span>부정</span></div></td>
                                                                                    </tr>
                                                                                    <tr><td colspan="6" class="no_over"></td></tr>
                                                                                    <tr><td colspan="6" class="no_over"></td></tr>
                                                                                    <tr><td colspan="6" class="no_over"></td></tr>
                                                                                    <tr><td colspan="6" class="no_over"></td></tr>
                                                                                    <tr><td colspan="6" class="no_over"></td></tr>
                                                                                    <tr><td colspan="6" class="no_over"></td></tr>
                                                                                    <tr><td colspan="6" class="no_over"></td></tr>
                                                                                </tbody>
                                                                            </table>
                                                                        </div>
                                                                        <div class="footer">
                                                                            <div class="ui_paginate">
                                                                                <div class="in_wrap">
                                                                                    <a href="#" class="page_prev prev ui_disabled" onclick="return false"></a>
                                                                                    <a href="#" class="page_prev ui_disabled" onclick="return false"></a>
                                                                                    <a href="#" class="" onclick="return false;">1</a>
                                                                                    <a href="#" class="active" onclick="return false;">2</a>
                                                                                    <a href="#" onclick="return false;">3</a>
                                                                                    <a href="#" onclick="return false;">4</a>
                                                                                    <a href="#" onclick="return false;">5</a>
                                                                                    <a href="#" onclick="return false;">6</a>
                                                                                    <a href="#" onclick="return false;">7</a>
                                                                                    <a href="#" onclick="return false;">8</a>
                                                                                    <a href="#" onclick="return false;">9</a>
                                                                                    <a href="#" onclick="return false;">10</a>
                                                                                    <a href="#" class="page_next disabled" onclick="return false;"></a>
                                                                                    <a href="#" class="page_next next disabled" onclick="return false;"></a>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- // 보도자료 목록 및 확산 추적 -->
                            </div>
                        </div>
                    </section>

				</div>
				<!-- // Content -->

			</div>
		</div>

		<!-- Popup -->
		<div id="popup_container">
            <div class="bg"></div>

            <!-- 관련정보 - 기본 -->
			<jsp:include page="../common/popup/popup_rel_info.jsp" flush="false" />
            <!-- // 관련정보 - 기본 -->

            <!-- 관련정보 - 상세 -->
			<jsp:include page="../common/popup/popup_rel_info_detail.jsp" flush="false" />
            <!-- // 관련정보 - 상세 -->
		</div>
		<!-- // Popup -->
    </div>

<jsp:include page="../inc/inc_page_bot.jsp" flush="false" />
<jsp:include page="../inc/inc_devels.jsp" flush="false" />
