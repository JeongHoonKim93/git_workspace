<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<jsp:include page="../inc/inc_page_top.jsp" flush="false" />

<body>
    <script>
        gnbIDX = "01";
    </script>

	<div id="wrap">
		<!-- Include HEADER -->
		<jsp:include page="../inc/inc_header.jsp" flush="false" />
		<!-- // Include HEADER -->

		<div id="container">
			<h2 id="page_title" class="ui_invisible">&nbsp;</h2>
			<div id="content" class="page-summary">

				<!-- Search -->
				<section id="top_searchs" class="ui_searchs">
					<h3 class="ui_invisible">검색조건</h3>
					<div class="wrap">
                        <!-- <input type="checkbox" id="ts_toggle" checked><label for="ts_toggle"><span>검색 열기/닫기</span></label> -->

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
                                                        <div class="ui_datepickers" id="dp11" data-date='{ "sDate": "2022-05-06", "eDate": "2022-05-12" }' data-move="true" data-grps='[ "1", "7", "30", "7MT", "8MT" ]' data-type-opts="disabled" ></div>
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
                                <li class="active"><a href="#qid_01">총 정보량/정보량 현황</a></li>
                                <li><a href="#qid_02">유통 채널별 점유율</a></li>
                                <li><a href="#qid_03">유통 채널별 정보량 현황</a></li>
                                <li><a href="#qid_04">주제별 정보량 현황/세부속성 정보량 현황</a></li>
                                <li><a href="#qid_05">포탈 TOP 노출 현황</a></li>
                                <li><a href="#qid_06">포탈자료 댓글 현황</a></li>
                                <li><a href="#qid_07">모니터링 데이터 목록</a></li>
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
                                <!-- 전체 정보량 -->
                                <div class="ui_col w8" style="width: 416px !important;">
                                    <div class="wrap">
                                        <div class="ui_box  ui_loader_container /*is-loading*/">
                                            <div class="box_header">
                                                <h4>전체 정보량</h4>
                                                <div class="box_header_rc">
                                                    <button type="button" class="ui_btn is-icon-only" data-bubble-id="qid_01_01_bubble"><span class="icon bubble">&#xe088;</span></button>
                                                    <div class="ui_bubble_box" data-bubble-for="qid_01_01_bubble" data-pos="RB" data-arrowcenter="true">
                                                        <span class="arrow"></span>
                                                        <div class="tip">
                                                            <strong class="title"><span>Guide</span></strong>
                                                            <span class="txt">자사 관련 전체 정보량</span>
                                                        </div>
                                                    </div>
                                                    <button type="button" class="ui_btn is-icon-only"><span class="icon">&#xe005;</span></button>
                                                </div>
                                            </div>
                                            <div class="box_content" style="height: 290px;">
                                                <div class="totals">
                                                    <span class="title">VOLUME</span>
                                                    <a href="#" class="ui_lnk dv" onclick="popupMngr.open( '#popup_rel_info_detail' ); return false;"><strong class="txt">1,234,567k</strong></a>
                                                    <span class="ui_fluc before up">1,234,221</span>
													<div id="qid_01_01_chart" class="ui_chart_wrap" style="width: 350px;"></div>
													<script type="text/javascript">
														$(function(){
															var chartOption = {
																		"type": "serial",
																		"categoryField": "category",
																		"maxSelectedTime": 0,
																		"rotate": true,
																		"sequencedAnimation": false,
																		"autoMargins": false,
																		"columnWidth": 1,
																		"marginLeft": 8,
																		"marginRight": 4,
																		"addClassNames": true,
                                                                        "percentPrecision": 1,
                                                                        "colors": [
                                                                            "#5ba1e0",
                                                                            "#ea7070",
                                                                            "#808080",
                                                                        ],
																		"categoryAxis": {
																			"gridPosition": "start",
																			// "startOnAxis": true,
																			"autoGridCount": false,
																			"axisAlpha": 0,
																			"fontSize": 0,
																			"gridAlpha": 0
																		},
																		"trendLines": [],
																		"graphs": [
																			{
																				"accessibleLabel": "[[title]] [[category]] [[value]] [[percents]]",
                                                                                // "balloonText": "<strong style='color: #5699d5;'>[[title]]</strong><span style='color:#5699d5; font-size: 12px;'>: [[value]]건</span> <span style='color:#5699d5'>([[percents]]%)</span>",
                                                                                "balloonText": "<strong style='color: #5699d5;'>[[title]]: [[value]] <span style='font-size: 11px;'>([[percents]]%)</span></strong>",
																				"bulletAlpha": 0,
																				"color": "#ffffff",
																				"id": "AmGraph-1",
																				"fillAlphas": 1,
																				"lineAlpha": 1,
																				"labelText": "[[value]]",
                                                                                "fontSize": 14,
																				"showHandOnHover": true,
																				"title": "긍정",
																				"type": "column",
																				"valueField": "column-1"
																			},
																			{
																				"accessibleLabel": "[[title]] [[category]] [[value]] [[percents]]",
                                                                                // "balloonText": "<strong style='color: #de6a6a;'>[[title]]</strong><span style='color:#de6a6a; font-size: 12px;'>: [[value]]건</span> <span style='color:#de6a6a'>([[percents]]%)</span>",
                                                                                "balloonText": "<strong style='color: #de6a6a;'>[[title]]: [[value]] <span style='font-size: 11px;'>([[percents]]%)</span></strong>",
																				"bulletAlpha": 0,
																				"color": "#ffffff",
																				"fillAlphas": 1,
																				"id": "AmGraph-2",
																				"labelText": "[[value]]",
                                                                                "fontSize": 14,
																				"showHandOnHover": true,
																				"title": "부정",
																				"type": "column",
																				"valueField": "column-2"
																			},
																			{
																				"accessibleLabel": "[[title]] [[category]] [[value]] [[percents]]",
                                                                                // "balloonText": "<strong style='color: #797979;'>[[title]]</strong><span style='color:#797979; font-size: 12px;'>: [[value]]건</span> <span style='color:#797979'>([[percents]]%)</span>",
                                                                                "balloonText": "<strong style='color: #797979;'>[[title]]: [[value]] <span style='font-size: 11px;'>([[percents]]%)</span></strong>",
																				"bulletAlpha": 0,
																				"color": "#ffffff",
																				"fillAlphas": 1,
																				"id": "AmGraph-3",
																				"labelText": "[[value]]",
                                                                                "fontSize": 14,
																				"showHandOnHover": true,
																				"title": "중립",
																				"type": "column",
																				"valueField": "column-3"
																			}
																		],
																		"guides": [],
																		"valueAxes": [
																			{
																				"id": "ValueAxis-1",
																				"stackType": "100%",
																				"zeroGridAlpha": 0,
																				"autoRotateAngle": 0,
																				"axisAlpha": 0,
																				"axisThickness": 0,
																				"centerLabels": true,
																				"centerRotatedLabels": true,
																				"fontSize": 0,
																				"gridAlpha": 0,
																				"tickLength": 0,
																			}
																		],
																		"allLabels": [],
                                                                        "balloon": {
                                                                            "fillAlpha": 0.95,
		                                                                    "borderThickness": 1,
                                                                            "fixedPosition": false,
                                                                            "fontSize": 12,
                                                                            "horizontalPadding": 10,
                                                                            "pointerWidth": 4,
                                                                            "shadowAlpha": 0.28,
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
																			{
																				"column-1": 1,
																				"column-2": 2,
																				"column-3": 3,
																			}
																		]
																	}
															var chart = AmCharts.makeChart("qid_01_01_chart", chartOption);
															chart.addListener("clickGraphItem", function(){
																popupMngr.open( "#popup_rel_info_detail" );
															});
														});
													</script>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                
                                <!-- 정보량 현황 -->
                                <div class="ui_col w16" style="width: 766px !important;">
                                    <div class="wrap">
                                        <div class="ui_box  ui_loader_container /*is-loading*/">
                                            <div class="box_header">
                                                <h4>정보량 현황</h4>
                                                <div class="box_header_rc">
                                                    <button type="button" class="ui_btn is-icon-only" data-bubble-id="qid_01_02_bubble"><span class="icon bubble">&#xe088;</span></button>
                                                    <div class="ui_bubble_box" data-bubble-for="qid_01_02_bubble" data-pos="RB" data-arrowcenter="true">
                                                        <span class="arrow"></span>
                                                        <div class="tip">
                                                            <strong class="title"><span>Guide</span></strong>
                                                            <span class="txt">자사 관련 검색기간 내 성향별 추이</span>
                                                        </div>
                                                    </div>
                                                    <button type="button" class="ui_btn is-icon-only"><span class="icon">&#xe005;</span></button>
                                                </div>
                                            </div>
                                            <div class="box_content is-no-pad-top">
                                                <div class="wrap">
                                                    <!-- 컬럼 - 일/주별 -->
                                                    <div id="qid_01_02_01_chart_01" class="ui_chart_wrap" style="height: 273px;"></div>        <!-- 데이터 없는 경우/초기 'ui_no_data' 클래스 추가-->
                                                    <script>
                                                        // 컬럼 - 일/주별
                                                        (function(){
                                                            var chart = AmCharts.makeChart( "qid_01_02_01_chart_01", 
                                                                {
                                                                    "type": "serial",
                                                                    "categoryField": "category",
                                                                    "addClassNames": true,
                                                                    "fontSize": 12,
                                                                    "columnWidth": 0.35,
                                                                    "autoMarginOffset": 0,
                                                                    "marginRight": 10,
                                                                    "marginTop": 15,
	                                                                "columnSpacing": 0,
                                                                    "percentPrecision": 1,
                                                                    "colors": [
                                                                        "#5ba1e0",
                                                                        "#ea7272",
                                                                        "#808080",
                                                                        "#4f4f4f",
                                                                    ],
                                                                    "color": "#666666",
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
                                                                            "title": "긍정",
			                                                                "markerType": "round",
                                                                            "type": "column",
                                                                            "valueField": "column-1"
                                                                        },
                                                                        { 
                                                                            // "balloonText": "<strong style='color: #de6a6a;'>[[title]]</strong><span style='color:#de6a6a; font-size: 12px;'>: [[value]]</span> <span style='color:#de6a6a'>([[percents]]%)</span>",
                                                                            "balloonText": "<strong style='color: #de6a6a;'>[[title]]: [[value]] <span style='font-size: 11px;'>([[percents]]%)</span></strong>",
                                                                            "fillAlphas": 1,
                                                                            "id": "AmGraph-2",
                                                                            "title": "부정",
			                                                                "markerType": "round",
                                                                            "type": "column",
                                                                            "valueField": "column-2"
                                                                        },
                                                                        {
                                                                            // "balloonText": "<strong style='color: #797979;'>[[title]]</strong><span style='color:#797979; font-size: 12px;'>: [[value]]</span> <span style='color:#797979'>([[percents]]%)</span>",
                                                                            "balloonText": "<strong style='color: #797979;'>[[title]]: [[value]] <span style='font-size: 11px;'>([[percents]]%)</span></strong>",
                                                                            "fillAlphas": 1,
                                                                            "id": "AmGraph-3",
                                                                            "title": "중립",
			                                                                "markerType": "round",
                                                                            "type": "column",
                                                                            "valueField": "column-3"
                                                                        },
                                                                        {
                                                                            // "balloonText": "<strong style='color: #4f4f4f;'>[[title]]</strong><span style='color:#4f4f4f; font-size: 12px;'>: [[value]]건</span> <span style='color:#4f4f4f'>([[percents]]%)</span>",
                                                                            "balloonFunction": get_chartBalloonValueSensi,
                                                                            "bullet": "round",
                                                                            "bulletSize": 8,
                                                                            "lineThickness": 2,
                                                                            "stackable": false,
                                                                            "id": "AmGraph-4",
                                                                            "title": "전체 정보량",
                                                                            "valueField": "column-4",
                                                                            "sensi": "sensi-4",
													                        "valueAxis": "ValueAxis-2",
		                                                                    "useGraphSettings": true,
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
                                                                        },
                                                                        {
                                                                            "id": "ValueAxis-2",
                                                                            "position": "right",
                                                                            "zeroGridAlpha": 0,
                                                                            "axisThickness": 0,
                                                                            "color": "#999999",
                                                                            "fontSize": 10,
                                                                            // "dashLength": 4,
                                                                            "gridAlpha": 0,
                                                                            // "gridColor": "#E9E9E9",
                                                                            "tickLength": 0,
                                                                            "title": ""
                                                                        },
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
		                                                                "useGraphSettings": true,
                                                                        // "autoMargins": false,
                                                                        "color": "#999999",
                                                                        "fontSize": 11,
                                                                        "marginLeft": 0,
                                                                        "marginRight": 0,
                                                                        "marginTop": 10,
                                                                        "markerSize": 8,
                                                                        "position": "bottom",
                                                                        "spacing": 0,
                                                                        "valueText": "",
                                                                        "valueWidth": 0,
                                                                        "verticalGap": 0
                                                                    },
                                                                    "titles": [],
                                                                    "dataProvider": [
                                                                        {
                                                                            "category": "2021-06-01",
                                                                            "column-1": 8,
                                                                            "column-2": 5,
                                                                            "column-3": 11,
                                                                            "column-4": 30,
                                                                            "sensi-4": 5,
                                                                        },
                                                                        {
                                                                            "category": "2021-06-02",
                                                                            "column-1": 6,
                                                                            "column-2": 7,
                                                                            "column-3": 9,
                                                                            "column-4": 40,
                                                                            "sensi-4": -5,
                                                                        },
                                                                        {
                                                                            "category": "2021-06-03",
                                                                            "column-1": 2,
                                                                            "column-2": 3,
                                                                            "column-3": 10,
                                                                            "column-4": 50,
                                                                            "sensi-4": -95,
                                                                        },
                                                                        {
                                                                            "category": "2021-06-04",
                                                                            "column-1": 2,
                                                                            "column-2": 3,
                                                                            "column-3": 10,
                                                                            "column-4": 15,
                                                                            "sensi-4": 95,
                                                                        }
                                                                    ]
                                                                }
                                                            );
                                                            chart.addListener( "clickGraphItem", function( $e ){
                                                                popupMngr.open( '#popup_rel_info_detail' );
                                                            });
                                                        })();
                                                    </script>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- // 정보량 현황 -->
                            </div>

                            <!-- 유통 채널별 점유율 -->
                            <div class="ui_row on_pad" id="qid_02">
                                <div class="ui_col">
                                    <div class="wrap">
                                        <div class="ui_box  ui_loader_container /*is-loading*/">
                                            <div class="box_header">
                                                <h4>유통 채널별 점유율</h4>
                                                <div class="box_header_rc">
                                                    <button type="button" class="ui_btn is-icon-only" data-bubble-id="qid_02_01_bubble"><span class="icon bubble">&#xe088;</span></button>
                                                    <div class="ui_bubble_box" data-bubble-for="qid_02_01_bubble" data-pos="RB" data-arrowcenter="true">
                                                        <span class="arrow"></span>
                                                        <div class="tip">
                                                            <strong class="title"><span>Guide</span></strong>
                                                            <span class="txt">자사 관련 채널별 점유율</span>
                                                        </div>
                                                    </div>
                                                    <button type="button" class="ui_btn is-icon-only"><span class="icon">&#xe005;</span></button>
                                                </div>
                                            </div>
                                            <div class="box_content">
                                                <div class="ui_treemap">
                                                    <div id="qid_02_01_treemap" class="treemap_wrap" style=" height:300px"></div>
                                                    <div class="treemap_bubble_wrap"></div>
                                                </div>
                                                <script type="text/javascript">
                                                    $( "#qid_02_01_treemap" ).each( function(){
                                                        var _this = $( this );
                                                        /*
                                                        포탈        #555555
                                                        언론        #385593
                                                        커뮤니티    #ec8e56
                                                        블로그      #91d085
                                                        카페        #f2d301
                                                        인스타그램  #e57fb6
                                                        유튜브      #ce553f
                                                        트위터      #75cae5
                                                        페이스북    #458eed
                                                        정부기관    #94582f
                                                        공공/단체   #985fa8
                                                        기타        #cccdcd
                                                        */
                                                        var treemapData1 = [
                                                            { fill: "#555555", label: "<div class='proper_color2 proper_st'><span class='infos'>포탈</span></div>", value: 860 },
                                                            { fill: "#385593", label: "<div class='proper_color2 proper_st'><span class='infos'>언론</span></div>", value: 760 },
                                                            { fill: "#ec8e56", label: "<div class='proper_color2 proper_st'><span class='infos'>커뮤니티</span></div>", value: 660 },
                                                            { fill: "#91d085", label: "<div class='proper_color2 proper_st'><span class='infos'>블로그</span></div>", value: 560 },
                                                            { fill: "#f2d301", label: "<div class='proper_color2 proper_st'><span class='infos'>카페</span></div>", value: 460 },
                                                            { fill: "#e57fb6", label: "<div class='proper_color2 proper_st'><span class='infos'>인스타그램</span></div>", value: 360 },
                                                            { fill: "#ce553f", label: "<div class='proper_color2 proper_st'><span class='infos'>유튜브</span></div>", value: 260 },
                                                            { fill: "#75cae5", label: "<div class='proper_color2 proper_st'><span class='infos'>트위터</span></div>", value: 160 },
                                                            { fill: "#458eed", label: "<div class='proper_color2 proper_st'><span class='infos'>페이스북</span></div>", value: 60 },
                                                            { fill: "#94582f", label: "<div class='proper_color2 proper_st'><span class='infos'>정부기관</span></div>", value: 60 },
                                                            { fill: "#985fa8", label: "<div class='proper_color2 proper_st'><span class='infos'>공공/단체</span></div>", value: 60 },
                                                            { fill: "#cccdcd", label: "<div class='proper_color2 proper_st'><span class='infos'>기타</span></div>", value: 40 },
                                                        ];
                                                        $( this ).setTreemap( treemapData1, function( e, b ){
                                                            _this.each(function(){
                                                                popupMngr.open( '#popup_rel_info_detail' );
                                                            });
                                                        });
                                                    });
                                                </script>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- // 유통 채널별 점유율 -->

                            <div class="ui_row " id="qid_03">
                                <div class="ui_col">
                                    <div class="wrap">
                                        <div class="ui_box  ui_loader_container /*is-loading*/">
                                            <div class="box_header">
                                                <h4>유통 채널별 정보량 현황</h4>
                                                <div class="box_header_rc">
                                                    <button type="button" class="ui_btn is-icon-only" data-bubble-id="qid_03_01_bubble"><span class="icon bubble">&#xe088;</span></button>
                                                    <div class="ui_bubble_box" data-bubble-for="qid_03_01_bubble" data-pos="RB" data-arrowcenter="true">
                                                        <span class="arrow"></span>
                                                        <div class="tip">
                                                            <strong class="title"><span>Guide</span></strong>
                                                            <span class="txt">자사 관련 채널별 성향 그래프</span>
                                                        </div>
                                                    </div>
                                                    <button type="button" class="ui_btn is-icon-only"><span class="icon">&#xe005;</span></button>
                                                </div>
                                            </div>
                                            <div class="box_content">
                                                <div class="wrap">
                                                    <!-- 컬럼 -->
                                                    <div id="qid_03_02_chart_01" class="ui_chart_wrap" style="height: 270px;"></div>       <!-- 데이터 없는 경우/초기 'ui_no_data' 클래스 추가-->
                                                    <script>
                                                        // 컬럼
                                                        (function(){
                                                            var chart = AmCharts.makeChart( "qid_03_02_chart_01", 
                                                                {
                                                                    "type": "serial",
                                                                    "categoryField": "category",
                                                                    "addClassNames": true,
                                                                    "fontSize": 12,
                                                                    "columnWidth": 0.35,
                                                                    "autoMarginOffset": 0,
                                                                    "marginRight": 10,
                                                                    "marginTop": 15,
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
                                                                        {
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
                                                                        },
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
                                </div>
                            </div>

                            <div class="ui_row on_pad" id="qid_04">
                                <div class="ui_col w12">
                                    <div class="wrap">
                                        <div class="ui_box  ui_loader_container /*is-loading*/">
                                            <div class="box_header">
                                                <h4>주제별 정보량 현황</h4>
                                                <div class="box_header_rc">
                                                    <button type="button" class="ui_btn is-icon-only" data-bubble-id="qid_04_01_bubble"><span class="icon bubble">&#xe088;</span></button>
                                                    <div class="ui_bubble_box" data-bubble-for="qid_04_01_bubble" data-pos="RB" data-arrowcenter="true">
                                                        <span class="arrow"></span>
                                                        <div class="tip">
                                                            <strong class="title"><span>Guide</span></strong>
                                                            <span class="txt">자사 관련 주제별 동향</span>
                                                        </div>
                                                    </div>
                                                    <button type="button" class="ui_btn is-icon-only"><span class="icon">&#xe005;</span></button>
                                                </div>
                                            </div>
                                            <div class="box_content">
                                                <div class="wrap">
                                                    <!-- 컬럼 -->
                                                    <div id="qid_04_01_chart_01" class="ui_chart_wrap" style="height: 270px;"></div>       <!-- 데이터 없는 경우/초기 'ui_no_data' 클래스 추가-->
                                                    <script>
                                                        // 컬럼
                                                        (function(){
                                                            var chart = AmCharts.makeChart( "qid_04_01_chart_01", 
                                                                {
                                                                    "type": "serial",
                                                                    "categoryField": "category",
                                                                    "addClassNames": true,
                                                                    "fontSize": 12,
                                                                    "columnWidth": 0.35,
                                                                    "autoMarginOffset": 0,
                                                                    "marginRight": 10,
                                                                    "marginTop": 15,
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
                                                                        {
                                                                            "category": "기업경영",
                                                                            "column-1": 8,
                                                                            "column-2": 5,
                                                                            "column-3": 11
                                                                        },
                                                                        {
                                                                            "category": "기업윤리",
                                                                            "column-1": 6,
                                                                            "column-2": 7,
                                                                            "column-3": 9
                                                                        },
                                                                        {
                                                                            "category": "기업운영",
                                                                            "column-1": 2,
                                                                            "column-2": 3,
                                                                            "column-3": 10
                                                                        },
                                                                        {
                                                                            "category": "제품관련",
                                                                            "column-1": 8,
                                                                            "column-2": 5,
                                                                            "column-3": 11
                                                                        },
                                                                        {
                                                                            "category": "VOC",
                                                                            "column-1": 6,
                                                                            "column-2": 7,
                                                                            "column-3": 9
                                                                        },
                                                                        {
                                                                            "category": "사건/사고",
                                                                            "column-1": 2,
                                                                            "column-2": 3,
                                                                            "column-3": 10
                                                                        },
                                                                        {
                                                                            "category": "기타",
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
                                </div>
                                <div class="ui_col w12">
                                    <div class="wrap">
                                        <div class="ui_box  ui_loader_container /*is-loading*/">
                                            <div class="box_header">
                                                <h4>세부속성 정보량 현황</h4>
                                                <div class="box_header_rc">
                                                    <div class="dcp small">
                                                        <select id="qid_04_02_01_chart_sel_01">
                                                            <!-- <option value="1">회사구분</option> -->
                                                            <option value="1">기업경영</option>
                                                            <option value="2">기업윤리</option>
                                                            <option value="3">기업운영</option>
                                                            <option value="4">제품관련</option>
                                                            <option value="5">VOC</option>
                                                            <option value="6">사건/사고</option>
                                                            <option value="7">기타</option>
                                                        </select>
                                                        <label for="qid_04_02_01_chart_sel_01"></label>
                                                    </div>
                                                    <button type="button" class="ui_btn is-icon-only" data-bubble-id="qid_04_02_bubble"><span class="icon bubble">&#xe088;</span></button>
                                                    <div class="ui_bubble_box" data-bubble-for="qid_04_02_bubble" data-pos="RB" data-arrowcenter="true">
                                                        <span class="arrow"></span>
                                                        <div class="tip">
                                                            <strong class="title"><span>Guide</span></strong>
                                                            <span class="txt">자사 관련 주제별 상세 속성 추이</span>
                                                        </div>
                                                    </div>
                                                    <button type="button" class="ui_btn is-icon-only"><span class="icon">&#xe005;</span></button>
                                                </div>
                                            </div>
                                            <div class="box_content">
                                                <div class="wrap">
                                                    <!-- 컬럼 -->
                                                    <div id="qid_04_02_chart_01" class="ui_chart_wrap" style="height: 270px;"></div>       <!-- 데이터 없는 경우/초기 'ui_no_data' 클래스 추가-->
                                                    <script>
                                                        // 컬럼
                                                        (function(){
                                                            var chart = AmCharts.makeChart( "qid_04_02_chart_01", 
                                                                {
                                                                    "type": "serial",
                                                                    "categoryField": "category",
                                                                    "addClassNames": true,
                                                                    "fontSize": 12,
                                                                    "columnWidth": 0.35,
                                                                    "autoMarginOffset": 0,
                                                                    "marginRight": 10,
                                                                    "marginTop": 15,
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
                                                                        {
                                                                            "category": "맛/향",
                                                                            "column-1": 8,
                                                                            "column-2": 5,
                                                                            "column-3": 11
                                                                        },
                                                                        {
                                                                            "category": "가격",
                                                                            "column-1": 6,
                                                                            "column-2": 7,
                                                                            "column-3": 9
                                                                        },
                                                                        {
                                                                            "category": "패키지/디자인",
                                                                            "column-1": 2,
                                                                            "column-2": 3,
                                                                            "column-3": 10
                                                                        },
                                                                        {
                                                                            "category": "용량/유통",
                                                                            "column-1": 8,
                                                                            "column-2": 5,
                                                                            "column-3": 11
                                                                        },
                                                                        {
                                                                            "category": "광고/이벤트",
                                                                            "column-1": 8,
                                                                            "column-2": 5,
                                                                            "column-3": 11
                                                                        },
                                                                        {
                                                                            "category": "이물질 내재",
                                                                            "column-1": 6,
                                                                            "column-2": 7,
                                                                            "column-3": 9
                                                                        },
                                                                        {
                                                                            "category": "불매",
                                                                            "column-1": 6,
                                                                            "column-2": 7,
                                                                            "column-3": 9
                                                                        },
                                                                        {
                                                                            "category": "기타",
                                                                            "column-1": 2,
                                                                            "column-2": 3,
                                                                            "column-3": 10
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
                                </div>
                            </div>
                            
                            <div class="ui_row on_pad is-pad-05" id="qid_05">
                                <!-- 포탈 TOP 노출 현황 -->
                                <div class="ui_col">
                                    <div class="wrap">
                                        <div class="ui_box  ui_loader_container /*is-loading*/">
                                            <div class="box_header">
                                                <h4>포탈 TOP 노출 현황</h4>
                                                <div class="box_header_rc">
                                                    <button type="button" class="ui_btn is-icon-only" data-bubble-id="qid_05_01_bubble"><span class="icon bubble">&#xe088;</span></button>
                                                    <div class="ui_bubble_box" data-bubble-for="qid_05_01_bubble" data-pos="RB" data-arrowcenter="true">
                                                        <span class="arrow"></span>
                                                        <div class="tip">
                                                            <strong class="title"><span>Guide</span></strong>
                                                            <span class="txt">네이버/ 다음/ 네이트 포탈 탑 노출 현황</span>
                                                        </div>
                                                    </div>
                                                    <button type="button" class="ui_btn is-icon-only"><span class="icon">&#xe005;</span></button>
                                                </div>
                                            </div>
                                            <div class="box_content">
                                                <div class="ui_brd_list">
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
                                                            <col style="width: 88px;">
                                                            <col style="width: 140px;">
                                                            <col>
                                                            <col style="width: 136px;">
                                                            </colgroup>
                                                            <thead>
                                                            <tr>
                                                                <th>포탈</th>
                                                                <th>노출 페이지</th>
                                                                <th>최초 노출시간</th>
                                                                <th>제목</th>
                                                                <th>출처</th>
                                                            </tr>
                                                            </thead>
                                                            <!-- 데이터 없는 경우 
                                                            <tr><td colspan="5" class="no_over"></td></tr>
                                                            <tr><td colspan="5" class="no_over"></td></tr>
                                                            <tr><td colspan="5" class="no_over"></td></tr>
                                                            <tr><td colspan="5" class="no_over"></td></tr>
                                                            <tr><td colspan="5" class="no_over"></td></tr>
                                                            <tr><td colspan="5" class="no_over no_data in_list">키워드가 없습니다.</td></tr>
                                                            <tr><td colspan="5" class="no_over"></td></tr>
                                                            <tr><td colspan="5" class="no_over"></td></tr>
                                                            <tr><td colspan="5" class="no_over"></td></tr>
                                                            <tr><td colspan="5" class="no_over"></td></tr> -->
                                                            <tbody>
                                                            <tr>
                                                                <td>Naver</td>
                                                                <td>PC</td>
                                                                <td>2021-08-04 00:00:00</td>
                                                                <td class="ui_al"><a href="#" class="lnk"><span class="txt">빙그레의 쿠키앤크림 아이스크림을 간식으로 먹었어요~</span></a></td>
                                                                <td>에너지경제신문</td>
                                                            </tr>
                                                            <tr>
                                                                <td>Daum</td>
                                                                <td>PC</td>
                                                                <td>2021-08-04 00:00:00</td>
                                                                <td class="ui_al"><a href="#" class="lnk"><span class="txt">빙그레의 쿠키앤크림 아이스크림을 간식으로 먹었어요~</span></a></td>
                                                                <td>에너지경제신문</td>
                                                            </tr>
                                                            <tr>
                                                                <td>Nate</td>
                                                                <td>PC</td>
                                                                <td>2021-08-04 00:00:00</td>
                                                                <td class="ui_al"><a href="#" class="lnk"><span class="txt">빙그레의 쿠키앤크림 아이스크림을 간식으로 먹었어요~</span></a></td>
                                                                <td>에너지경제신문</td>
                                                            </tr>
                                                            <tr>
                                                                <td>Daum</td>
                                                                <td>Mobile</td>
                                                                <td>2021-08-04 00:00:00</td>
                                                                <td class="ui_al"><a href="#" class="lnk"><span class="txt">빙그레의 쿠키앤크림 아이스크림을 간식으로 먹었어요~</span></a></td>
                                                                <td>에너지경제신문</td>
                                                            </tr>
                                                            <tr>
                                                                <td>Daum</td>
                                                                <td>Mobile</td>
                                                                <td>2021-08-04 00:00:00</td>
                                                                <td class="ui_al"><a href="#" class="lnk"><span class="txt">빙그레의 쿠키앤크림 아이스크림을 간식으로 먹었어요~</span></a></td>
                                                                <td>에너지경제신문</td>
                                                            </tr>
                                                            <tr>
                                                                <td>Naver</td>
                                                                <td>PC</td>
                                                                <td>2021-08-04 00:00:00</td>
                                                                <td class="ui_al"><a href="#" class="lnk"><span class="txt">빙그레의 쿠키앤크림 아이스크림을 간식으로 먹었어요~</span></a></td>
                                                                <td>에너지경제신문</td>
                                                            </tr>
                                                            <tr>
                                                                <td>Daum</td>
                                                                <td>PC</td>
                                                                <td>2021-08-04 00:00:00</td>
                                                                <td class="ui_al"><a href="#" class="lnk"><span class="txt">빙그레의 쿠키앤크림 아이스크림을 간식으로 먹었어요~</span></a></td>
                                                                <td>에너지경제신문</td>
                                                            </tr>
                                                            <tr>
                                                                <td>Nate</td>
                                                                <td>PC</td>
                                                                <td>2021-08-04 00:00:00</td>
                                                                <td class="ui_al"><a href="#" class="lnk"><span class="txt">빙그레의 쿠키앤크림 아이스크림을 간식으로 먹었어요~</span></a></td>
                                                                <td>에너지경제신문</td>
                                                            </tr>
                                                            <tr>
                                                                <td>Daum</td>
                                                                <td>Mobile</td>
                                                                <td>2021-08-04 00:00:00</td>
                                                                <td class="ui_al"><a href="#" class="lnk"><span class="txt">빙그레의 쿠키앤크림 아이스크림을 간식으로 먹었어요~</span></a></td>
                                                                <td>에너지경제신문</td>
                                                            </tr>
                                                            <tr>
                                                                <td>Daum</td>
                                                                <td>Mobile</td>
                                                                <td>2021-08-04 00:00:00</td>
                                                                <td class="ui_al"><a href="#" class="lnk"><span class="txt">빙그레의 쿠키앤크림 아이스크림을 간식으로 먹었어요~</span></a></td>
                                                                <td>에너지경제신문</td>
                                                            </tr>
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
                                <!-- // 포탈 TOP 노출 현황 -->
                            </div>

                            <div class="ui_row on_pad is-pad-05" id="qid_06">
                                <!-- 포탈자료 댓글 현황 -->
                                <div class="ui_col">
                                    <div class="wrap">
                                        <div class="ui_box  ui_loader_container /*is-loading*/">
                                            <div class="box_header">
                                                <h4>포탈자료 댓글 현황</h4>
                                                <div class="box_header_rc">
                                                    <button type="button" class="ui_btn is-icon-only" data-bubble-id="qid_06_01_bubble"><span class="icon bubble">&#xe088;</span></button>
                                                    <div class="ui_bubble_box" data-bubble-for="qid_06_01_bubble" data-pos="RB" data-arrowcenter="true">
                                                        
                                                        <span class="arrow"></span>
                                                        <div class="tip">
                                                            <strong class="title"><span>Guide</span></strong>
                                                            <span class="txt">네이버/ 다음/ 네이트 포탈 댓글 상위 현황</span>
                                                        </div>
                                                    </div>
                                                    <button type="button" class="ui_btn is-icon-only"><span class="icon">&#xe005;</span></button>
                                                </div>
                                            </div>
                                            <div class="box_content">
                                                <div class="ui_brd_list">
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
                                                            <col style="width: 125px;">
                                                            <col style="width: 88px;">
                                                            <col>
                                                            <col style="width: 136px;">
                                                            </colgroup>
                                                            <thead>
                                                            <tr>
                                                                <th>포탈</th>
                                                                <th>댓글<span class="fc_positive is-dot">긍</span><span class="fc_negative is-dot">부</span><span class="fc_neutral is-dot">중</span></th>
                                                                <th>일자</th>
                                                                <th>제목</th>
                                                                <th>출처</th>
                                                            </tr>
                                                            </thead>
                                                            <!-- 데이터 없는 경우 
                                                            <tr><td colspan="5" class="no_over"></td></tr>
                                                            <tr><td colspan="5" class="no_over"></td></tr>
                                                            <tr><td colspan="5" class="no_over"></td></tr>
                                                            <tr><td colspan="5" class="no_over"></td></tr>
                                                            <tr><td colspan="5" class="no_over"></td></tr>
                                                            <tr><td colspan="5" class="no_over no_data in_list">키워드가 없습니다.</td></tr>
                                                            <tr><td colspan="5" class="no_over"></td></tr>
                                                            <tr><td colspan="5" class="no_over"></td></tr>
                                                            <tr><td colspan="5" class="no_over"></td></tr>
                                                            <tr><td colspan="5" class="no_over"></td></tr> -->
                                                            <tbody>
                                                            <tr>
                                                                <td>Naver</td>
                                                                <td><a href="#" onclick="popupMngr.open( '#popup_rel_info_detail' ); return false;" class="ui_lnk"><span>369</span></a> (<a href="#" onclick="popupMngr.open( '#popup_rel_info_detail' ); return false;" class="fc_positive ui_lnk"><span>123</span></a>/<a href="#" onclick="popupMngr.open( '#popup_rel_info_detail' ); return false;" class="fc_negative ui_lnk"><span>123</span></a>/<a href="#" onclick="popupMngr.open( '#popup_rel_info_detail' ); return false;" class="fc_neutral ui_lnk"><span>123</span></a>)</td>
                                                                <td>2021-08-04</td>
                                                                <td class="ui_al">
                                                                    <a href="#" target="_blank" class="lnk">빙그레의 쿠키앤크림 아이스크림을 간식으로 먹었어요~</a>
                                                                </td>
                                                                <td>에너지경제신문</td>
                                                            </tr>
                                                            <tr>
                                                                <td>Daum</td>
                                                                <td><a href="#" onclick="popupMngr.open( '#popup_rel_info_detail' ); return false;" class="ui_lnk"><span>369</span></a> (<a href="#" onclick="popupMngr.open( '#popup_rel_info_detail' ); return false;" class="fc_positive ui_lnk"><span>123</span></a>/<a href="#" onclick="popupMngr.open( '#popup_rel_info_detail' ); return false;" class="fc_negative ui_lnk"><span>123</span></a>/<a href="#" onclick="popupMngr.open( '#popup_rel_info_detail' ); return false;" class="fc_neutral ui_lnk"><span>123</span></a>)</td>
                                                                <td>2021-08-04</td>
                                                                <td class="ui_al">
                                                                    <a href="#" target="_blank" class="lnk">빙그레의 쿠키앤크림 아이스크림을 간식으로 먹었어요~</a>
                                                                </td>
                                                                <td>에너지경제신문</td>
                                                            </tr>
                                                            <tr>
                                                                <td>Nate</td>
                                                                <td><a href="#" onclick="popupMngr.open( '#popup_rel_info_detail' ); return false;" class="ui_lnk"><span>369</span></a> (<a href="#" onclick="popupMngr.open( '#popup_rel_info_detail' ); return false;" class="fc_positive ui_lnk"><span>123</span></a>/<a href="#" onclick="popupMngr.open( '#popup_rel_info_detail' ); return false;" class="fc_negative ui_lnk"><span>123</span></a>/<a href="#" onclick="popupMngr.open( '#popup_rel_info_detail' ); return false;" class="fc_neutral ui_lnk"><span>123</span></a>)</td>
                                                                <td>2021-08-04</td>
                                                                <td class="ui_al">
                                                                    <a href="#" target="_blank" class="lnk">빙그레의 쿠키앤크림 아이스크림을 간식으로 먹었어요~</a>
                                                                </td>
                                                                <td>에너지경제신문</td>
                                                            </tr>
                                                            <tr>
                                                                <td>Daum</td>
                                                                <td><a href="#" onclick="popupMngr.open( '#popup_rel_info_detail' ); return false;" class="ui_lnk"><span>369</span></a> (<a href="#" onclick="popupMngr.open( '#popup_rel_info_detail' ); return false;" class="fc_positive ui_lnk"><span>123</span></a>/<a href="#" onclick="popupMngr.open( '#popup_rel_info_detail' ); return false;" class="fc_negative ui_lnk"><span>123</span></a>/<a href="#" onclick="popupMngr.open( '#popup_rel_info_detail' ); return false;" class="fc_neutral ui_lnk"><span>123</span></a>)</td>
                                                                <td>2021-08-04</td>
                                                                <td class="ui_al">
                                                                    <a href="#" target="_blank" class="lnk">빙그레의 쿠키앤크림 아이스크림을 간식으로 먹었어요~</a>
                                                                </td>
                                                                <td>에너지경제신문</td>
                                                            </tr>
                                                            <tr>
                                                                <td>Daum</td>
                                                                <td><a href="#" onclick="popupMngr.open( '#popup_rel_info_detail' ); return false;" class="ui_lnk"><span>369</span></a> (<a href="#" onclick="popupMngr.open( '#popup_rel_info_detail' ); return false;" class="fc_positive ui_lnk"><span>123</span></a>/<a href="#" onclick="popupMngr.open( '#popup_rel_info_detail' ); return false;" class="fc_negative ui_lnk"><span>123</span></a>/<a href="#" onclick="popupMngr.open( '#popup_rel_info_detail' ); return false;" class="fc_neutral ui_lnk"><span>123</span></a>)</td>
                                                                <td>2021-08-04</td>
                                                                <td class="ui_al">
                                                                    <a href="#" target="_blank" class="lnk">빙그레의 쿠키앤크림 아이스크림을 간식으로 먹었어요~</a>
                                                                </td>
                                                                <td>에너지경제신문</td>
                                                            </tr>
                                                            <tr>
                                                                <td>Naver</td>
                                                                <td><a href="#" onclick="popupMngr.open( '#popup_rel_info_detail' ); return false;" class="ui_lnk"><span>369</span></a> (<a href="#" onclick="popupMngr.open( '#popup_rel_info_detail' ); return false;" class="fc_positive ui_lnk"><span>123</span></a>/<a href="#" onclick="popupMngr.open( '#popup_rel_info_detail' ); return false;" class="fc_negative ui_lnk"><span>123</span></a>/<a href="#" onclick="popupMngr.open( '#popup_rel_info_detail' ); return false;" class="fc_neutral ui_lnk"><span>123</span></a>)</td>
                                                                <td>2021-08-04</td>
                                                                <td class="ui_al">
                                                                    <a href="#" target="_blank" class="lnk">빙그레의 쿠키앤크림 아이스크림을 간식으로 먹었어요~</a>
                                                                </td>
                                                                <td>에너지경제신문</td>
                                                            </tr>
                                                            <tr>
                                                                <td>Daum</td>
                                                                <td><a href="#" onclick="popupMngr.open( '#popup_rel_info_detail' ); return false;" class="ui_lnk"><span>369</span></a> (<a href="#" onclick="popupMngr.open( '#popup_rel_info_detail' ); return false;" class="fc_positive ui_lnk"><span>123</span></a>/<a href="#" onclick="popupMngr.open( '#popup_rel_info_detail' ); return false;" class="fc_negative ui_lnk"><span>123</span></a>/<a href="#" onclick="popupMngr.open( '#popup_rel_info_detail' ); return false;" class="fc_neutral ui_lnk"><span>123</span></a>)</td>
                                                                <td>2021-08-04</td>
                                                                <td class="ui_al">
                                                                    <a href="#" target="_blank" class="lnk">빙그레의 쿠키앤크림 아이스크림을 간식으로 먹었어요~</a>
                                                                </td>
                                                                <td>에너지경제신문</td>
                                                            </tr>
                                                            <tr>
                                                                <td>Nate</td>
                                                                <td><a href="#" onclick="popupMngr.open( '#popup_rel_info_detail' ); return false;" class="ui_lnk"><span>369</span></a> (<a href="#" onclick="popupMngr.open( '#popup_rel_info_detail' ); return false;" class="fc_positive ui_lnk"><span>123</span></a>/<a href="#" onclick="popupMngr.open( '#popup_rel_info_detail' ); return false;" class="fc_negative ui_lnk"><span>123</span></a>/<a href="#" onclick="popupMngr.open( '#popup_rel_info_detail' ); return false;" class="fc_neutral ui_lnk"><span>123</span></a>)</td>
                                                                <td>2021-08-04</td>
                                                                <td class="ui_al">
                                                                    <a href="#" target="_blank" class="lnk">빙그레의 쿠키앤크림 아이스크림을 간식으로 먹었어요~</a>
                                                                </td>
                                                                <td>에너지경제신문</td>
                                                            </tr>
                                                            <tr><td colspan="5" class="no_over"></td></tr>
                                                            <tr><td colspan="5" class="no_over"></td></tr>
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
                                <!-- // 포탈자료 댓글 현황 -->
                            </div>

                            <div class="ui_row on_pad is-pad-05" id="qid_07">
                                <!-- 모니터링 데이터 목록 -->
                                <div class="ui_col">
                                    <div class="wrap">
                                        <div class="ui_box  ui_loader_container /*is-loading*/">
                                            <div class="box_header">
                                                <h4>모니터링 데이터 목록</h4>
                                                <div class="box_header_rc">
                                                    <div class="dcp is-mselect is-small" data-label="채널" data-value="1,2,3,4,5,6,7,8,9,10,11,12" style="width: 120px" >
                                                        <select id="qid_07_01_01_chart_sel_01" multiple>
                                                            <!-- <option value="1">채널 전체</option> -->
                                                            <option value="1">언론</option>
                                                            <option value="2">포탈</option>
                                                            <option value="3">블로그</option>
                                                            <option value="4">카페</option>
                                                            <option value="5">커뮤니티</option>
                                                            <option value="6">트위터</option>
                                                            <option value="7">유튜브</option>
                                                            <option value="8">페이스북</option>
                                                            <option value="9">인스타그램</option>
                                                            <option value="10">정부기관</option>
                                                            <option value="11">공공/단체</option>
                                                            <option value="12">기타</option>
                                                        </select>
                                                        <label for="qid_07_01_01_chart_sel_01"></label>
                                                    </div>
                                                    <div class="dcp is-mselect is-small" data-label="성향" data-value="1,2,3" style="width: 120px" >
                                                        <select id="qid_07_01_01_chart_sel_02" multiple>
                                                            <option value="1">긍정</option>
                                                            <option value="2">부정</option>
                                                            <option value="3">중립</option>
                                                        </select>
                                                        <label for="qid_07_01_01_chart_sel_02"></label>
                                                    </div>
                                                    <button type="button" class="ui_btn is-icon-only" data-bubble-id="qid_07_01_bubble"><span class="icon bubble">&#xe088;</span></button>
                                                    <div class="ui_bubble_box" data-bubble-for="qid_07_01_bubble" data-pos="RB" data-arrowcenter="true">
                                                        <span class="arrow"></span>
                                                        <div class="tip">
                                                            <strong class="title"><span>Guide</span></strong>
                                                            <span class="txt">빙그레 뉴스/SNS 전체 데이터 목록</span>
                                                        </div>
                                                    </div>
                                                    <button type="button" class="ui_btn is-icon-only"><span class="icon">&#xe005;</span></button>
                                                </div>
                                            </div>
                                            <div class="box_content">
                                                <div class="ui_brd_list">
                                                    <div class="header is-pad-t-7 no_bg">
                                                        <div class="lc">
                                                        <span class="page_info">Total: <span class="cnt">115</span>
                                                            <span class="page">(<em>1</em>/29p)</span></span>
                                                        </div>
                                                    </div>
                                                    <div class="wrap">
                                                        <table>
                                                            <colgroup>
                                                                <col style="width:128px">
                                                                <col style="width:131px">
                                                                <col>
                                                                <col style="width:92px">
                                                            </colgroup>
                                                            <thead>
                                                            <tr>
                                                                <th scope="col"><span>일자</span></th>
                                                                <th scope="col"><span>채널</span></th>
                                                                <th scope="col"><span>제목</span></th>
                                                                <th scope="col"><span>성향</span></th>
                                                            </tr>
                                                            </thead>
                                                            <tbody>
                                                                <!-- 데이터 없는 경우
                                                                <tr><td colspan="5" class="no_over"></td></tr>
                                                                <tr><td colspan="5" class="no_over"></td></tr>
                                                                <tr><td colspan="5" class="no_over"></td></tr>
                                                                <tr><td colspan="5" class="no_over"></td></tr>
                                                                <tr><td colspan="5" class="no_over no_data in_list"><span class="ui_no_data_txt">키워드가 없습니다.</span></td></tr>
                                                                <tr><td colspan="5" class="no_over"></td></tr>
                                                                <tr><td colspan="5" class="no_over"></td></tr>
                                                                <tr><td colspan="5" class="no_over"></td></tr>
                                                                <tr><td colspan="5" class="no_over"></td></tr>
                                                                <tr><td colspan="5" class="no_over"></td></tr>
                                                                -->
                                                                <tr>
                                                                    <td>2021-07-21</td>
                                                                    <td>언론</td>
                                                                    <td class="ui_al"><a href="#" class="lnk " target="_blank" title="상담사 잘만났네요"><span>상담사 잘만났네요</span></a></td>
                                                                    <td><div class="ui_label fc_positive"><span>긍정</span></div></td>
                                                                </tr>
                                                                <tr>
                                                                    <td>2021-07-21</td>
                                                                    <td>언론</td>
                                                                    <td class="ui_al"><a href="#" class="lnk " target="_blank" title="상담사 잘만났네요"><span>상담사 잘만났네요</span></a></td>
                                                                    <td><div class="ui_label fc_neutral"><span>중립</span></div></td>
                                                                </tr>
                                                                <tr>
                                                                    <td>2021-07-21</td>
                                                                    <td>포탈</td>
                                                                    <td class="ui_al"><a href="#" class="lnk " target="_blank" title="상담사 잘만났네요"><span>상담사 잘만났네요</span></a></td>
                                                                    <td><div class="ui_label fc_negative"><span>부정</span></div></td>
                                                                </tr>
                                                                <tr>
                                                                    <td>2021-07-21</td>
                                                                    <td>인스타그램</td>
                                                                    <td class="ui_al"><a href="#" class="lnk " target="_blank" title="상담사 잘만났네요"><span>상담사 잘만났네요</span></a></td>
                                                                    <td><div class="ui_label fc_neutral"><span>중립</span></div></td>
                                                                </tr>
                                                                <tr><td colspan="4" class="no_over"></td></tr>
                                                                <tr><td colspan="4" class="no_over"></td></tr>
                                                                <tr><td colspan="4" class="no_over"></td></tr>
                                                                <tr><td colspan="4" class="no_over"></td></tr>
                                                                <tr><td colspan="4" class="no_over"></td></tr>
                                                                <tr><td colspan="4" class="no_over"></td></tr>
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
                                <!-- //모니터링 데이터 목록 -->
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
			<%-- <jsp:include page="../common/popup/popup_rel_info_detail.jsp" flush="false" /> --%>
            <!-- // 관련정보 - 상세 -->
		</div>
		<!-- // Popup -->
    </div>

<jsp:include page="../inc/inc_page_bot.jsp" flush="false" />
