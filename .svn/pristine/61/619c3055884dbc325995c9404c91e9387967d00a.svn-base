<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<form method="post" id="fPopup" name="fPopup" >
<input type="hidden" name="i_sdate"  id="i_sdate">
<input type="hidden" name="i_edate"  id="i_edate">

<section id="popup_rel_info_detail" class="popup_item">
<script type="text/javascript" src="../../view/common/popup/js/popup.js"></script> 
	<div class="header">
        <h2>관련정보</h2>
        <a href="#" class="ui_btn is-icon-only is-large close" onclick="popupMngr.close( $( this ).parents( '.popup_item' ).eq( 0 ) );return false;" title="닫기"><span class="icon">&#xe019;</span></a>
        <div class="dcp ver_popup" style="width: 182px;"><input type="text" name= "searchkeyword" id="popup_search_02" placeholder="검색어를 입력해 주세요." OnKeyDown="Javascript:if (event.keyCode == 13) { searchPopup(); return false;}"><button type="button" class="search" onclick="searchPopup(); return false;"><span class="ui_icon">&#xe007;</span></button></div>
	</div>
	<div class="content ui_loader_container">
		<div class="wrap">
            <div class="ui_row on_pad">
                <div class="ui_col w13" id="pad_list">
                    <div class="ui_box ui_loader_container /*is-loading*/">
                        <div class="box_header">
                            <h3>데이터 목록</h3>
                            <div class="box_header_rc">
                                <button type="button" class="ui_btn small is-icon-only" onclick="excel_download('list')"><span class="icon"></span></button>
                            </div>
                        </div>
                        <div class="box_content">
                            <div class="ui_brd_list">
                                <div class="header is-pad-t-7 no_bg">
                                    <div class="lc">
                                      <span class="page_info">Total: <span class="cnt" id="popid_totalCnt">115</span>
                                        <span class="page" id="popid_totalPage">(<em>1</em>/29p)</span></span>
                                    </div>
                                </div>
                                <div class="wrap">
                                    <table>
                                        <colgroup>
                                            <col style="width:90px">
                                            <col>
                                            <col style="width:100px">
                                            <col style="width:50px">
                                        </colgroup>
                                        <thead>
                                        <tr>
                                            <th scope="col"><span>출처</span></th>
                                            <th scope="col"><span>제목</span></th>
                                            <th scope="col"><span>수집일</span></th>
                                            <th scope="col"><span>감성</span></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                            <!-- 데이터 없는 경우
                                            <tr><td colspan="4" class="no_over"></td></tr>
                                            <tr><td colspan="4" class="no_over"></td></tr>
                                            <tr><td colspan="4" class="no_over"></td></tr>
                                            <tr><td colspan="4" class="no_over"></td></tr>
                                            <tr><td colspan="4" class="no_over no_data in_list"><span class="ui_no_data_txt">키워드가 없습니다.</span></td></tr>
                                            <tr><td colspan="4" class="no_over"></td></tr>
                                            <tr><td colspan="4" class="no_over"></td></tr>
                                            <tr><td colspan="4" class="no_over"></td></tr>
                                            <tr><td colspan="4" class="no_over"></td></tr>
                                            <tr><td colspan="4" class="no_over"></td></tr>
                                            -->
                                            <tr>
                                                <td>헤럴드경제</td>
                                                <td class="ui_al"><a href="#" class="lnk clr_00" target="_blank" title="상담사 잘만났네요"><span>상담사 잘만났네요</span></a></td>
                                                <td>2021-07-21</td>
                                                <td><span class="fc_positive is-dot"></span></td>
                                            </tr>
                                            <tr>
                                                <td>헤럴드경제</td>
                                                <td class="ui_al"><a href="#" class="lnk clr_00" target="_blank" title="상담사 잘만났네요"><span>상담사 잘만났네요</span></a></td>
                                                <td>2021-07-21</td>
                                                <td><span class="fc_negative is-dot"></span></td>
                                            </tr>
                                            <tr>
                                                <td>헤럴드경제</td>
                                                <td class="ui_al"><a href="#" class="lnk clr_00" target="_blank" title="상담사 잘만났네요"><span>상담사 잘만났네요</span></a></td>
                                                <td>2021-07-21</td>
                                                <td><span class="fc_neutral is-dot"></span></td>
                                            </tr>
                                            <tr><td colspan="4" class="no_over"></td></tr>
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
                <div class="ui_col w11" id="pad_chart"> 
                    <div class="ui_row">
                        <div class="ui_col is-no-pad">
                            <div class="ui_box dims ui_loader_container /*is-loading*/">
                                <div class="box_header">
                                    <h3>정보량 추이 <span id="chartPeriod">2021-07-05 ~ 2021-07-15</span></h3>
                                    <div class="box_header_rc">
                                        <div class="dcp is-small"><input id="pop_01_chart_range_01" type="radio" name="pop_01_chart_range" checked="" value="day"><label for="pop_01_chart_range_01"><span>일별</span></label></div>
                                        <div class="dcp is-small"><input id="pop_01_chart_range_02" type="radio" name="pop_01_chart_range" value="week"><label for="pop_01_chart_range_02"><span>주별</span></label></div>
                                        <div class="dcp is-small"><input id="pop_01_chart_range_03" type="radio" name="pop_01_chart_range" value="month"><label for="pop_01_chart_range_03"><span>월별</span></label></div>
                                        <button type="button" class="ui_btn small is-icon-only" style="margin-left: 10px;" onclick="excel_download('chart')"><span class="icon"></span></button>
                                    </div>
                                </div>
                                <div class="box_content">
                                    <!-- 컬럼 - 일/주별 -->
                                    <div id="popup_01_chart_01" class="ui_chart_wrap" style="height: 178px;"></div>        <!-- 데이터 없는 경우/초기 'ui_no_data' 클래스 추가-->
                                    <script>
                                        // 컬럼 - 일/주별
                                        (function(){
                                            var chart = AmCharts.makeChart( "popup_01_chart_01", 
                                                {
                                                    "type": "serial",
                                                    "categoryField": "category",
                                                    "addClassNames": true,
                                                    "fontSize": 12,
                                                    "columnWidth": 0.35,
                                                    // "autoMarginOffset": 0,
                                                    "marginRight": 10,
                                                    "marginTop": 30,
                                                    "marginBottom": 30,
	                                                "columnSpacing": 0,
                                                    "percentPrecision": 1,
                                                    "colors": [
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
                                                        "fontSize": 10,
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
                                                            // "balloonText": "<strong style='color: #4f4f4f;'>[[title]]</strong> <span style='color:#4f4f4f; font-size: 12px;'>: [[value]]건</span> <span style='color:#4f4f4f'>([[percents]]%)</span>",
                                                            "balloonFunction": get_chartBalloonValueSensi,
                                                            "bullet": "round",
                                                            "bulletSize": 8,
                                                            "lineThickness": 2,
                                                            "stackable": false,
                                                            "id": "AmGraph-1",
                                                            "title": "전체 정보량",
                                                            "valueField": "column-1",
                                                            "sensi": "sensi-1"
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
                                                        "enabled": false,
                                                    },
                                                    "titles": [],
                                                    "dataProvider": [
                                                        {
                                                            "category": "2021-06-01",
                                                            "column-1": 5,
                                                            "sensi-1": 5,
                                                        },
                                                        {
                                                            "category": "2021-06-02",
                                                            "column-1": 3,
                                                            "sensi-1": -5,
                                                        },
                                                        {
                                                            "category": "2021-06-03",
                                                            "column-1": 20,
                                                            "sensi-1": -95,
                                                        },
                                                        {
                                                            "category": "2021-06-04",
                                                            "column-1": 2,
                                                            "sensi-1": 95,
                                                        }
                                                    ]
                                                }
                                            );
                                        })();
                                    </script>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="ui_row" id="pad_relation">
                        <div class="ui_col is-no-pad">
                            <div class="ui_box dims">
                                <div class="box_header">
                                    <h3>연관어 Top 30</h3>
                                    <div class="box_header_rc">
                                        <div class="dcp small">
                                            <select id="popup_01_chart_sel_01">
                                                <option value="1,2,3">성향 전체</option>
                                                <option value="1">긍정</option>
                                                <option value="2">부정</option>
                                                <option value="3">중립</option>
                                            </select>
                                            <label for="popup_01_chart_sel_01"></label>
                                        </div>
                                        <button type="button" class="ui_btn small is-icon-only" style="margin-left: 10px;" onclick="excel_download('relation')"><span class="icon"></span></button>
                                    </div>
                                </div>
                                <div class="box_content ui_loaderContainer">
                                    <div class="wrap">
                                        <div id="pop_02_chart_01" class="ui_cloud_wrap">
                                            <div class="ui_cloud is-over" style="height: 211px; width: 470px;"></div>
                                            <div class="bubble">
                                                <span class="arrow bd"></span>
                                                <span class="arrow in"></span>
                                                <!-- <span id="bub_category" style="margin-bottom: 3px;">단어1</span> -->
                                                <span class="txt big wrap" id="bub_word">-</span>
                                                <span class="txt wrap"><span id="bub_weight">-</span></span>
                                                <span class="txt wrap">(<span id="bub_percent" class="ui_fluc before">0</span>)</span>
                                            </div>
                                        </div>
                                        <script>
                                            /*
                                                컨셉: #288887, 행위: #5072a8, 공간: #e08e1b, 긍정: #5ba1e0, 부정: #ea7070, 일반: #b4b4b4;
                                            */
                                           /*  $("#pop_02_chart_01" ).each( function(){
                                                var tg = $( this );
                                                tg.find( ".ui_cloud" ).jQWCloud({
                                                    words: [
                                                        { "code": "1",      "weight": 1565308,  "positive": 121,  "negative": 174,  "neutral": 454,    "percent": 13.1,            "word": "생각",                "color": "#f16a40" },
                                                        { "code": "2",      "weight": 955660,   "positive": 121,  "negative": 174,  "neutral": 4554,    "percent": 13.2,            "word": "가능하다",           "color": "#f16a40" },
                                                        { "code": "3",      "weight": 864588,   "positive": 121,  "negative": 174,  "neutral": 454,    "percent": 13.3,            "word": "못한다",              "color": "#f16a40" },
                                                        { "code": "4",      "weight": 763446,   "positive": 121,  "negative": 174,  "neutral": 34354,    "percent": 13.4,            "word": "제공",              "color": "#f16a40" },
                                                        { "code": "5",      "weight": 757949,   "positive": 121,  "negative": 1374,  "neutral": 454,    "percent": 13.5,            "word": "가격",               "color": "#f16a40" },
                                                        { "code": "6",      "weight": 751557,   "positive": 121,  "negative": 174,  "neutral": 454,    "percent": 13.6,            "word": "정보",                "color": "#d73686" },
                                                        { "code": "7",      "weight": 744484,   "positive": 1521,  "negative": 1574,  "neutral": 4554,    "percent": 13.7,            "word": "모르다",           "color": "#d73686" },
                                                        { "code": "8",      "weight": 720483,   "positive": 1721,  "negative": 174,  "neutral": 44,    "percent": 13.8,            "word": "사용",                "color": "#d73686" },
                                                        { "code": "9",      "weight": 686637,   "positive": 121,  "negative": 1734,  "neutral": 44,    "percent": 13.9,            "word": "서울",                "color": "#d73686" },
                                                        { "code": "10",     "weight": 677506,   "positive": 1231,  "negative": 174,  "neutral": 454,    "percent": 13.0,            "word": "추천",               "color": "#d73686" },
                                                        { "code": "11",     "weight": 630762,   "positive": 1221,  "negative": 174,  "neutral": 44,    "percent": 13.2,            "word": "제품",                "color": "#d73686" },
                                                        { "code": "12",     "weight": 628582,   "positive": 14521,  "negative": 1274,  "neutral": 54,    "percent": 13.4,            "word": "사랑",              "color": "#a74eb7" },
                                                        { "code": "13",     "weight": 584689,   "positive": 1221,  "negative": 174,  "neutral": 454,    "percent": 13.5,            "word": "구매",               "color": "#a74eb7" },
                                                        { "code": "14",     "weight": 546029,   "positive": 121,  "negative": 1734,  "neutral": 454,    "percent": 13.6,            "word": "최고",               "color": "#a74eb7" },
                                                        { "code": "15",     "weight": 544414,   "positive": 1221,  "negative": 174,  "neutral": 454,    "percent": 13.7,            "word": "문제",               "color": "#a74eb7" },
                                                        { "code": "16",     "weight": 536716,   "positive": 121,  "negative": 1574,  "neutral": 454,    "percent": 13.8,            "word": "2019년",             "color": "#5ba1e0" },
                                                        { "code": "17",     "weight": 536636,   "positive": 1241,  "negative": 1374,  "neutral": 54,    "percent": 13.9,            "word": "좋아하다",           "color": "#5ba1e0" },
                                                        { "code": "18",     "weight": 489756,   "positive": 121,  "negative": 174,  "neutral": 454,    "percent": 13.0,            "word": "지역",                "color": "#5ba1e0" },
                                                        { "code": "19",     "weight": 489594,   "positive": 1521,  "negative": 174,  "neutral": 54,    "percent": 13.4,            "word": "다음",                "color": "#5ba1e0" },
                                                        { "code": "20",     "weight": 474915,   "positive": 121,  "negative": 1754,  "neutral": 454,    "percent": 13.3,            "word": "이용",               "color": "#ea7070" },
                                                        { "code": "21",     "weight": 463203,   "positive": 121,  "negative": 174,  "neutral": 45,    "percent": 13.4,            "word": "한국",                 "color": "#ea7070" },
                                                        { "code": "22",     "weight": 460977,   "positive": 1231,  "negative": 1374,  "neutral": 454,    "percent": 13.2,            "word": "기준",              "color": "#ea7070" },
                                                        { "code": "23",     "weight": 444364,   "positive": 1221,  "negative": 174,  "neutral": 454,    "percent": 13.4,            "word": "안되다",             "color": "#ea7070" },
                                                        { "code": "24",     "weight": 427236,   "positive": 121,  "negative": 174,  "neutral": 454,    "percent": 13.1,            "word": "판매",                "color": "#ea7070" },
                                                        { "code": "25",     "weight": 415954,   "positive": 1521,  "negative": 5174,  "neutral": 54,    "percent": 13.4,            "word": "쉽다",               "color": "#6f7bb7" },
                                                        { "code": "26",     "weight": 414022,   "positive": 121,  "negative": 174,  "neutral": 54,    "percent": 13.5,           "word": "귀엽다",               "color": "#6f7bb7" },
                                                        { "code": "27",     "weight": 410440,   "positive": 1231,  "negative": 174,  "neutral": 4,    "percent": 13.3,           "word": "아이",                 "color": "#6f7bb7" },
                                                        { "code": "28",     "weight": 404494,   "positive": 121,  "negative": 1743,  "neutral": 44,    "percent": 13.2,            "word": "관리",                "color": "#5ba1e0" },
                                                        { "code": "29",     "weight": 403825,   "positive": 1251,  "negative": 174,  "neutral": 54,    "percent": 13.3,            "word": "소개",                "color": "#5ba1e0" },
                                                        { "code": "30",     "weight": 400561,   "positive": 121,  "negative": 174,  "neutral": 44,    "percent": 13.1,            "word": "일정",                 "color": "#ea7070" }
                                                    ],
                                                    minFont: 11,
                                                    maxFont: 30,
                                                    //fontOffset: 5,
                                                    padding_left: 0,
                                                    word_common_classes: 'word_item',
                                                    verticalEnabled: false,
                                                    word_click: function( $e, $word ){
                                                        // popupMngr.open( '#popup_rel_info_detail' );
                                                    },
                                                    
                                                    word_mouseOver: function( $e, $word ){
                                                        var _color = $word.color;
                                                        var _word = $word.word;
                                                        var _weight = $word.weight;
                                                        var _positive = $word.positive;
                                                        var _negative = $word.negative;
                                                        var _neutral = $word.neutral;
                                                        var _percent = $word.percent;
                                                        var bubble = $( tg ).find( ".bubble" );
            
                                                        //정보량 (점유율))
                                                        dataInput();
                                                        position();
            
                                                        function dataInput(){
                                                            bubble.find( "#bub_word" ).text( _word );
                                                            bubble.find( "#bub_weight" ).text( String(_weight).addComma() + "건" );
                                                            bubble.find( "#bub_positive" ).text( String(_positive).addComma() + "건" );
                                                            bubble.find( "#bub_negative" ).text( String(_negative).addComma() + "건" );
                                                            bubble.find( "#bub_neutral" ).text( String(_neutral).addComma() + "건" );
                                                            bubble.find( "#bub_percent" ).text( _percent );
                                                            bubble.css({ "border-color" : _color });
                                                            bubble.find( ".arrow" ).css({ "border-color" : _color });
                                                            bubble.find( "strong" ).css({ "background" : _color });
                                                            
                                                        }
                                                        function position(){
                                                            var top = $( $e.target ).parents( ".word_item" ).position().top;
                                                            var left = $( $e.target ).parents( ".word_item" ).position().left;
                                                            var halfWidth = Number( $( $e.target ).parents( ".word_item" ).width() ) / 2;
                                                            
                                                            var bubHlafWidth = bubble.outerWidth() / 2;
                                                            var bubHeight = bubble.outerHeight();
                                                            
                                                            bubble.css({
                                                                "display": "block",
                                                                "top": ( top - bubHeight )- 10,
                                                                "left": ( left + halfWidth ) - bubHlafWidth,
                                                            });
                                                        }
                                                    },
                                                    word_mouseOut: function(){
                                                        var bubble = $( tg ).find( ".bubble" );
                                                        bubble.css({ "display": "none" });
                                                    }
                                                });
                                            }) */
                                        </script>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
		</div>

		<!-- Loader -->
		<div class="ui_loader"><span class="loader"></span></div>
		<!-- // Loader -->
	</div>
	
	<jsp:include page="../../inc/inc_devels.jsp" flush="false" />
</section>
</form>