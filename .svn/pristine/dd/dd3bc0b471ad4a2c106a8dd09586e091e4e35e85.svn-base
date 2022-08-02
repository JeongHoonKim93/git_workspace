//공통팝업 호출
var g_parm = '';
var g_sql = '';
var pop_id = "#popup_rel_info_detail";
/**중복실행 방지 변수**/
var ajax_relation_word;
var ajax_chart;

var originParam;
var reset_param;
var apiType = "api";
var dateType = "day";
var search_sdate = '';
var search_edate = '';
var popup_date = '';
var popup_rel = '';	//연관어명
var popup_relattr = '';	//연관어 속성

$( document ).ready( function(){
	//연관어 성향
	$("#popup_01_chart_sel_01").change(function() {
		getRelationWord(g_parm);
	});
	
	
	//정보량 추이
	$("input[name=pop_01_chart_range]").on("click",function(){
		getDataCntGraph(g_parm);
	});
	
	
});


function searchPopup(){
		
	getPopup(g_parm);
}

function resetPopup() {
//	$("#popup_search").val("");
	popup_date = "";
	popup_rel = "";		
	popup_relattr = "";		
	$("#subData").hide();
	$("#subInfoVal").hide();
	$("#subWord").hide();
	$("#search_date").show();
	$("input[name=searchkey]").val("");
	getPopup(reset_param, "reset");
}

function getPopup(parm, mode){
	
	if(parm == ""){
		alert('파라미터가 없습니다.');
		return;
	}
	
	g_parm = parm;
	
	
	
	//팝업 오픈시 파라미터 초기화
	if('open' == mode){
		//팝업오픈
		popupMngr.open( pop_id );
		/***** 초기화 ****/
		$("input[name=searchkeyword]").val("");
		$("#popup_search_02").text("");
		//연관어 조건
		/*$("#popup_03_radios_02").find("input[type=checkbox]").each(function(idx,item){
			if(idx!=0){
				$(this).prop('checked', false);
			}
		});
		$("#popup_03_radio_02_all").prop('checked', true);
		$("#popup_03_radio_01_01").prop('checked', true);
		$("#popup_02_radio_01_01").prop('checked', true);
		$("#pop_02_chart_01").addClass("ui_no_data");*/
		
		
		//중복실행 방지
		/*if(ajax_relation_word && ajax_relation_word.readystate != 4){
			ajax_relation_word.abort();
			
		}	
	
		if(ajax_chart && ajax_chart.readystate != 4){
				ajax_chart.abort();					
		}		*/		
			
	} else if('option' == mode) {
		delete g_parm.mode;
		delete g_parm.chart_type;
	}
	

	//데이터 목록	
	getPopupList(g_parm);
	
	//정보량 추이
	getDataCntGraph(g_parm);
	
	//연관어
	getRelationWord(g_parm);
	
	/*if($("#popup_search").val()!="") {
		$(pop_id).find("#subData").text("");
		$(pop_id).find("#subInfoVal").text("");
		$(pop_id).find("#subWord").text("");
			
		var subData = '<span title="'+$("#popup_search").val()+'" class="ui_state_box is-small is-color-searchkeyword"><span>'+$("#popup_search").val()+"</span></span>";
		if(popup_date != ""){
			subData += '<span class="ui_state_box is-small"><span>'+popup_date+"</span></span>";
		}
		if(popup_rel != ""){
			subData += '<span title="'+popup_rel+'" class="ui_state_box is-small is-color-voc01"><span>'+popup_rel+"</span></span>";
		}
		
		$(pop_id).find("#subData").append(subData);
		$(pop_id).find("#subInfoVal").append(subData);
		$(pop_id).find("#subWord").append(subData);
		$("#subData").show();
		$("#subInfoVal").show();
		$("#subWord").show();
		$("#search_date").hide();
	}*/
}

function getPopupList($parm, $pageNum){
	$parm += "&mode=list";

	if(null != $pageNum && "" != $pageNum){
		$parm += "&nowPage="+$pageNum;
	}
	
	if($("input[name=searchkeyword]").val() != '') {
		$parm += "&searchkey="+$("input[name=searchkeyword]").val();
	}
	
	ajaxPopMngr.open($("#pad_list").find(".ui_loader_container").first(),'../common/popup/data/popupDao.jsp', $parm, function( data ){		
		//상단
		var rowCnt = data.rowCnt;
		var listCnt = 10;
		var totalCnt = data.count;
		var pageCnt = 1;
		var nowPage = data.nowPage;		
		if( totalCnt == null || "" == totalCnt){ totalCnt = 0;}
		if( Number(totalCnt) > Number(rowCnt)) pageCnt =  Math.ceil(totalCnt / rowCnt);
			
		$(pop_id).find('#popid_totalCnt').empty().html(devel.number.numberWithCommas(totalCnt.toString()));
		//$(pop_id).find('#popid_totalPage').empty().html(devel.number.numberWithCommas(pageCnt.toString()));
		$(pop_id).find('#popid_totalPage').empty().html('(<em>'+nowPage+'</em>/'+devel.number.numberWithCommas(pageCnt.toString())+'p)');
		
		
		//리스트
		var idx = rowCnt;
		var innerHtml = '';
		var senti_class = '';
		var message = '';
		var colspan = 4;
		if(null !=  data.list){
				$(data.list).each(function(i) {
					--idx;
					senti_class = '';
	
					if("긍정"== data.list[i].ID_SENTI){
						senti_class = 'fc_positive';
					}else if("부정"==data.list[i].ID_SENTI){
						senti_class = 'fc_negative';
					}else if("중립"==data.list[i].ID_SENTI){
						senti_class = 'fc_neutral';
					}
					
               		if(data.list[i].ID_TITLE.length > 200){
               			message = data.list[i].ID_TITLE.substring(0,200);
               		}else{
						message = data.list[i].ID_TITLE;
					}					
               		
					innerHtml += '<tr>';
                    innerHtml += '<td>'+data.list[i].MD_SITE_NAME+'</td>';
                    innerHtml += '<td class="ui_al">';
					
					//네이버카페, 다음카페
					if("3555" == data.list[i].S_SEQ || "4943" == data.list[i].S_SEQ) {
					innerHtml += '<a href="#" onclick="javascipt:devel.hrefPop(\'\',\''+data.list[i].S_SEQ+'\', \''+data.list[i].ID_TITLE+'\'); return false;\" class="lnk clr_00" title="'+message+'" target="_blank">';
					innerHtml += '<span>'+ message +'</span>';
					innerHtml += '</a>';
					} else {
					innerHtml += '<a href="http://hub.buzzms.co.kr?url='+encodeURIComponent(data.list[i].ID_URL)+'" class="lnk clr_00" target="_blank" title="'+ message +'"><span>'+ message +'</span></a>';
						
					} 	 
                    innerHtml += '</td>';
                    innerHtml += '<td>'+data.list[i].MD_DATE+'</td>';
                    innerHtml += '<td><span class="'+senti_class+' is-dot"></span></td>';
                    innerHtml += '</tr>';
				});
				
				
				//빈값처리
				for(var i =0; i<idx; i++){
					if(rowCnt == idx && i ==  (idx/2-1) ){
						innerHtml += '<tr><td colspan="'+colspan+'"" class="no_over no_data in_list"><span class="">데이터가 없습니다.</span></td></tr>';
					}else{
						innerHtml += '<tr><td colspan="'+colspan+'" class="no_over"></td></tr>';
					}
				}
				
				$(pop_id).find("tbody").empty().append(innerHtml);
				
	
				//페이징처리
				var endPage = (  Math.floor((nowPage-1) / listCnt) + 1 ) * listCnt;
								
				if(endPage<=10){
					endPage = 10;
				}
				var startPage =  endPage - 9;
				if(pageCnt < endPage){endPage = pageCnt};
				innerHtml = "";
				if(nowPage <= 10){
					innerHtml += '<a href="#" class="page_prev prev disabled" title="이전 10 페이지" onclick="return false;" disabled></a>';
				}else{
					innerHtml += '<a href="#" class="page_prev prev" title="이전 10 페이지" onclick="popuPageClick(\''+(endPage-10)+'\'); return false;"></a>';
				}
	
				//이전버튼
				if(nowPage>1){
					  innerHtml += '<a href="#" class="page_prev" onclick="popuPageClick(\''+(Number(nowPage)-1)+'\'); return false;"></a>';
				}else{
					  innerHtml += '<a href="#" class="page_prev disabled" onclick="return false" disabled></a>';
				}
	
				//페이징넘버
				for(var i = startPage; i<=endPage; i++){
					if(nowPage == i){
						innerHtml += '<a href="#" class="active">'+i+'</a>';
					}else{
						innerHtml += '<a href="#" onclick="popuPageClick(\''+i+'\'); return false;">'+i+'</a>';
					}
				}
				
				//다음페이지
				if(nowPage < pageCnt){
					innerHtml += '<a href="#" class="page_next" onclick="popuPageClick(\''+(Number(nowPage)+1)+'\'); return false;"></a>';
				}else{
					innerHtml += '<a href="#" class="page_next disabled" onclick="return false;" disabled></a>';	
				}
				
				//다음 10페이지 
				if(pageCnt <= 10 || pageCnt <= endPage){
					innerHtml += '<a href="#" class="page_next next disabled" title="다음 10 페이지" onclick="return false;" disabled></a>';
				}else{
					innerHtml += '<a href="#" class="page_next next" title="다음 10 페이지" onclick="popuPageClick(\''+(endPage+1)+'\'); return false;"></a>';
				}
				
				$(pop_id).find('.ui_paginate').find('.in_wrap').empty().html(innerHtml);
			
		//데이터 없는 경우
		}else{
				innerHtml += '<tr><td colspan="'+colspan+'" class="no_over"></td></tr>';
				innerHtml += '<tr><td colspan="'+colspan+'" class="no_over"></td></tr>';
				innerHtml += '<tr><td colspan="'+colspan+'" class="no_over no_data in_list"><span class="">데이터가 없습니다.</span></td></tr>';
				innerHtml += '<tr><td colspan="'+colspan+'" class="no_over"></td></tr>';
			 	innerHtml += '<tr><td colspan="'+colspan+'" class="no_over"></td></tr>';
	
				
				$(pop_id).find("tbody").empty().append(innerHtml);
				
	
				//페이징처리
				innerHtml = "";
	
				//첫페이지 
				innerHtml += '<a href="#" class="page_prev prev disabled" onclick="return false"></a>';
				//이전버튼
                innerHtml += '<a href="#" class="page_prev disabled" title="이전 10 페이지" onclick="return false"></a>';
	
				//페이징넘버
				for(var i = 1; i<=1; i++){
					innerHtml += '<a href="#" class="active">'+i+'</a>';
				}
	
				//다음버튼
				innerHtml += '<a href="#" class="page_next disabled" onclick="return false;"></a>';
				//마지막페이지
				innerHtml += '<a href="#" class="page_next next disabled" title="다음 10 페이지" onclick="return false;"></a>';
                     
                       
				$(pop_id).find('.ui_paginate').find('.in_wrap').empty().html(innerHtml);
			
		}
	});
}


function getRelationWord(param){
	var rel_mode = "";
	var target_id = "#pop_02_chart_01";
	param += "&mode=relation";

	if($("input[name=searchkeyword]").val() != '') {
		param += "&searchkey="+$("input[name=searchkeyword]").val();
	}
	
	var senti = $("#popup_01_chart_sel_01 option:selected").val();
	//param += "&selectedSenti=" + senti;
	
	//중복실행 방지
	if(ajax_relation_word && ajax_relation_word.readystate != 4){
			ajax_relation_word.abort();
			
	}
	
	 ajax_relation_word = 	$.ajax({ 
								url : "../common/popup/data/popupDao.jsp",
								type : "POST",
								dataType : "json",
								data : param,
								beforeSend: function(){
									$("#pad_relation").find(".ui_loaderContainer").addClass("is-loading");
								},	
								success : function( data ){
									$("#pad_relation").find(".ui_loaderContainer").removeClass("is-loading");
									
									var color_set = ["#288887","#5072a8","#e08e1b","#5ba1e0"," #ea7070","#b4b4b4"];
									var weight = 70;
									var word_arr = [];
									var total_cnt = 0;
									if(data.list.length > 0){
										$(data.list).each(function(i,e) {

											e["code"] = i;	
											e["word"] = e.RK_NAME;	
											e["color"] = color_set[Math.floor(Math.random() * color_set.length)]
											e["percent"] = e.FACTOR_PER;		
											e["weight"] = e.CNT_D;		
											total_cnt += Number(e.CNT_D);
											word_arr.push(e);
											
											weight -= 2;
										});
										
										$("#pop_02_chart_01" ).each( function(){
											var tg = $( this );
											tg.find( ".ui_cloud" ).jQWCloud({
							                    words: word_arr,
							                    minFont: 11,
							                    maxFont: 30,
							                    //fontOffset: 5,
							                    padding_left: 0,
							                    word_common_classes: 'word_item',
							                    verticalEnabled: false,
							                    word_click: function( $e, $word ){
							                        tg.find( ".word_item" ).removeClass( "is-active" );
							                        $( $e.target ).parents( ".word_item" ).addClass( "is-active" );
													
							                    },
							                    word_mouseOver: function( $e, $word ){
							                        var _color = $word.color;
                                                    var _word = $word.word;
                                                    var _weight = $word.weight;
                                                    //var _positive = $word.positive;
                                                    //var _negative = $word.negative;
                                                    //var _neutral = $word.neutral;
                                                    var _percent = $word.percent;
                                                    var bubble = $( tg ).find( ".bubble" );
													/*
							                        if(  Number($word.percent ) > 0 ){
							                            _fluc = "up"
							                        } else if ( Number( $word.percent ) < 0 ){
							                            _fluc = "dn"
							                        } else if ( String( $word.percent ) == "New" ){
							                            _fluc = "new"
							                        }
													*/
													
													/*
													if(  $word.point == 'up'  ){
							                            _fluc = "up"
							                        } else if ( $word.point ==  'dn' ){
														  _fluc = "dn"
													}
													*/	

													//정보량 (점유율))
							                        dataInput();
							                        position();

							                        function dataInput(){
							                            bubble.find( "#bub_word" ).text( _word );
                                                        bubble.find( "#bub_weight" ).text( String(_weight).addComma() + "건" );
                                                        //bubble.find( "#bub_positive" ).text( String(_positive).addComma() + "건" );
                                                        //bubble.find( "#bub_negative" ).text( String(_negative).addComma() + "건" );
                                                        //bubble.find( "#bub_neutral" ).text( String(_neutral).addComma() + "건" );
                                                        bubble.find( "#bub_percent" ).text( _percent );
                                                        bubble.css({ "border-color" : _color });
                                                        bubble.find( ".arrow" ).css({ "border-color" : _color });
                                                        bubble.find( "strong" ).css({ "background" : _color });

							                        }
							                        function position(){
							                            var top = $( $e.target ).parents( ".word_item" ).position().top;
                                                        var left = $( $e.target ).parents( ".word_item" ).position().left;
                                                        var halfWidth = Number( $( $e.target ).parents( ".word_item" ).width() ) / 2;
                                                        
                                                        var bubHlafWidth = Number( bubble.outerWidth() / 2 );
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
										});
										
										
										if(total_cnt>0){
											$("#pop_02_chart_01").find(".ui_cloud").removeClass("ui_no_data");

										}else{
											$("#pop_02_chart_01").find(".ui_cloud").addClass("ui_no_data");
										}
									} else {
										$("#pop_02_chart_01").find(".ui_cloud").addClass("ui_no_data");
									}
									
								
								}
	});
						
}


function getDataCntGraph(param){
	var target_id = "#popup_01_chart_01";
	param += "&mode=chart";
	var chart_type =  $('input[name="pop_01_chart_range"]:checked').val();
	param += "&chart_type=" + chart_type; 
	
	if($("input[name=searchkeyword]").val() != '') {
		param += "&searchkey="+$("input[name=searchkeyword]").val();
	}
	
	//중복실행 방지
	if(ajax_chart && ajax_chart.readystate != 4){
			ajax_chart.abort();
			
	}
	
	 ajax_chart = 	$.ajax({ 
							url : "../common/popup/data/popupDao.jsp",
							type : "POST",
							dataType : "json",
							data : param,
							beforeSend: function(){
								$("#pad_chart").find(".ui_loader_container").addClass("is-loading");
							},	
							success : function( $result ){
								$("#pad_chart").find(".ui_loader_container").removeClass("is-loading");
								
								
								if(chart_type=="week") {
										var weekRes = [];
										var weekNum = [];
										
										var sunday = "";
										var saturday = "";
										var daychk = "";
										var tmp = 0;
										var week_s = "";
										var week_e = "";
										var sDate = param.substr(param.indexOf("i_sdate=")+8, 10);
										var eDate = param.substr(param.indexOf("i_edate=")+8, 10);
										
										var weekNum1 = weekVal(new Date(sDate));
										var weekNum2 = weekVal(new Date(eDate));
										
										if(weekNum2 == 1) {
											weekNum2 = 53;
										}
										
										for(var i=0; i < (weekNum2-weekNum1)+1; i++) {
											daychk = new Date(new Date(sDate).setDate(new Date(sDate).getDate() + 7*i));
											
											sunday = weekSunday(new Date(new Date(sDate).setDate(new Date(sDate).getDate() + 7*i)));
											
											if(i == 0) {
												week_s = daychk.toISOString().substring(0,10).replace(/-/g,'');
												saturday = weekSaturday(daychk).substring(0,10).replace(/-/g,'');
											} else {
												if(daychk.toISOString().substring(0,10).replace(/-/g,'') > new Date(eDate).toISOString().substring(0,10).replace(/-/g,'')) {
													week_s = weekSunday(new Date(eDate)).substring(0,10).replace(/-/g,'');
												} else {
													week_s = sunday.substring(0,10).replace(/-/g,'');
												}
												saturday = weekSaturday(new Date(sunday)).substring(0,10).replace(/-/g,'');
											}
											
											tmp = saturday - new Date(eDate).toISOString().substring(0,10).replace(/-/g,'');
											if(tmp > 0) {
												week_e = new Date(eDate).toISOString().substring(0,10).replace(/-/g,'');
											} else {
												week_e = saturday;
											}
											weekRes[i] = week_s + "  ~  " + week_e;
											
											var test = (week_s.substring(0,4) + "-" + week_s.substring(4,6) + "-" + week_s.substring(6,8)).getWeekDay();
											weekNum[i] = week_s.substring(0,4) + "_W" + test;    //주차 수 구하기
											
										}
										
									}
								
								
								var chartData =[];
					
								var tmpCnt = 0;
								var precnt = 0;
								var totalcnt = 0;
								var updownPer = 0;
								var sdate = "";
								var edate = "";
								$($result.list).each(function(i,e){
									if(precnt != 0) {
										updownPer = ((e.CNT - precnt) / precnt * 100 ).toFixed(1);
									} else if(precnt != 0 && e.CNT == 0) {
										updownPer = -100;
									} else if(precnt == 0 && e.CNT != 0 ) {
										updownPer = 100;
									} else {	
										updownPer = 0;
									}
									
									if(chart_type=="week") {
										e["category"] = weekRes[i];
										
										if(i == 0) {
											sdate = weekNum[i];
										} 
										if(i == $result.list.length - 1) {
											edate = weekNum[ $result.list.length - 1];
										}
										
									} else {
										e["category"] = e.DATE;
										
										if(i == 0) {
											sdate = e.DATE;
										} 
										if(i == $result.list.length - 1) {
											edate = e.DATE;
										}
									}
									e["column-1"] = e.CNT;
									e["sensi-1"] = updownPer;
									
									chartData.push(e);
									precnt = e.CNT;
									totalcnt += e.CNT;
									
								});
								
								
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
														"labelFunction":
														function($txt, $date, $axis) {
															if(chart_type == "week") {
																var year = $txt.substring(0,4);
																var days = ($txt.substring(0,4) + "-" + $txt.substring(4,6) + "-" + $txt.substring(6,8)).getWeekDay();
																result = year + "_W" + days + "\n";
																return result;
															} else {
																result = $txt;
																return result;
															}
														}
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
                                                    "dataProvider": chartData
                                                }
                                            );

											chart.addListener("clickGraphItem", function($e) {
												if(chart_type == "week") {
													var weekSplit = ($e.item.dataContext.category).replaceAll(" ","").split("~");
												}
												
												var splitVal = g_parm.split("&");
												var dateParam = "";
												var subData = "";
												for(var i=0; i<splitVal.length; i++) {
													if(splitVal[i].includes("i_sdate")) {
														if(chart_type == "day") {
															splitVal[i] = "i_sdate="+$e.item.dataContext.category;
															search_sdate = splitVal[i];
															
														} else {
															splitVal[i] = "i_sdate="+ weekSplit[0].slice(0,4) +"-"+ weekSplit[0].slice(4,6) +"-"+ weekSplit[0].slice(6,8);
															search_sdate = splitVal[i];
														}
													} else if(splitVal[i].includes("i_edate")) {
														if(chart_type == "day") {
															splitVal[i] = "i_edate="+$e.item.dataContext.category;
															search_edate = splitVal[i];
														} else {
															splitVal[i] = "i_edate="+ weekSplit[1].slice(0,4) +"-"+ weekSplit[1].slice(4,6) +"-"+ weekSplit[1].slice(6,8);
															search_edate = splitVal[i];
														}
													}
													if(i != 0) {
														dateParam += "&";
													}
													dateParam += splitVal[i];
												}
												
												getPopup(dateParam,'option');
											
										})
				
				
                                        })();
								
								var chart = devel.chart.getChartById("popup_01_chart_01");
								if($result.list.length>0 && totalcnt > 0){
									$( chart.div ).removeClass( "ui_no_data" );
								}else{
									$( chart.div ).addClass( "ui_no_data" );	
								}
								//showChart( chart.div );		
								
								
								$("#pad_chart").find("#chartPeriod").empty().text(sdate + " ~ " + edate);
				            }
	});
						
}

	function showChart( $tg ){
		if( !$( $tg ).attr( "data-first-ani" ) ){
			$( $tg ).attr( "data-first-ani", true );
			$( $tg ).css( { opacity : 0 } ).delay( 100 ).animate( { opacity : 1 }, 300 );
		}
	}


	function excel_download(mode){	
			var param = g_parm;
			param += '&mode=' + mode;
			
			if(mode == 'list') {
				param += '&subject=' + '데이터 목록';
 			} else if(mode == 'chart') {
				param += '&subject=' + '정보량 추이';
				param += '&chart_type=' + $('input[name="pop_01_chart_range"]:checked').val();
			} else if(mode == 'relation') {
				param += '&subject=' + '연관어';
			}
			
			//검색어
			if($("input[name=searchkeyword]").val() != '') {
				param += "&searchkey="+$("input[name=searchkeyword]").val();
			}
		
			$.ajax({ 
				url :"../common/popup/excel/getExcelData.jsp"
				,type : "POST"
				,timeout : 1800000
				,dataType : "text"
				,data : param
				,beforeSend: function(){
					//$target_btn.addClass("loading");
				}	
				,success : function( responseUrl ){
//					console.log("responseUrl");
//					console.log(responseUrl );
					//$target_btn.removeClass("loading");
					var result = responseUrl ;
					if(result.indexOf('no_data')>-1){
						alert("해당 데이터가 존재하지 않습니다.");
					}else{
						$("#processFrm" ).show();
						$("#processFrm" ).attr( "src", decodeURIComponent( responseUrl ) );
					}
					var interval = setInterval(function () {
					   iframe = document.getElementById('processFrm');
					   var iframeDoc = iframe.contentDocument || iframe.contentWindow.document;
			              if ( iframeDoc.readyState  == 'complete' || iframeDoc.readyState == 'interactive' ) {
			                  clearInterval(interval);
						$("#processFrm" ).attr( "src","");
						$("#processFrm" ).hide();
						 return;
			              }
			          }, 1000);					
				}
				, error : function(){
					//$target_btn.removeClass("loading");
					alert("엑셀 다운로드중 오류가 발생하였습니다.\n재시도 부탁드립니다. 감사합니다.");
				}
			});
	}

	var chkOriginal = 1;
	function portalSearch(s_seq, md_title, s_name){
		//네이버카페
		if(s_name.includes('네이버카페')){ 
			url = "https://cafe.naver.com/ca-fe/home/search/articles?pr=0&se=1&et="+ md_title;	
			window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop'+chkOriginal,'');		
			
		//다음카페    			
		}else if(s_name.includes('다음카페')){
			url = "http://search.daum.net/search?w=cafe&nil_search=btn&enc=utf8&ASearchType=1&lpp=10&rlang=0&q=" + md_title;
			window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop'+chkOriginal,'');
		}	
		
		chkOriginal ++;
	}	

function popuPageClick(page){
	getPopupList(g_parm, page);	
}


function popupSearch(){
	getPopup('serach',g_parm);
}

function pageClick(page){
	$('#nowPage').val(page);
	getPopup('serach',g_parm);
}

function weekVal(date) {
	var oneJan = new Date(date.getFullYear(),0,1);
	var numberOfDays = Math.floor((date - oneJan) / (24 * 60 * 60 * 1000));
	var result = Math.ceil(( date.getDay() + 1 + numberOfDays) / 7);
	return result;
}

function weekSunday(sdate) {
	var res1 = "";
	var diff1 = sdate.getDate() - sdate.getDay();
	
	res1 = new Date(sdate.setDate(diff1)).toISOString().substring(0,10);
	
	return res1;
}

function weekSaturday(sdate) {
	var res2 = "";
	var dayVal2 = sdate.getDay();
	var diff2 = sdate.getDate() - dayVal2 + (dayVal2 == 6 ? 0 : 6);
	
	res2 = new Date(sdate.setDate(diff2)).toISOString().substring(0,10);
	
	return res2;
}

//Ajax 매니저
var ajaxPopMngr = {
	open : function( $tg, $lnk, $data, $comFunc ){
		var lnk = $lnk.split( "?" )[ 0 ];
		var param;
		if( $lnk.split( "?" ).length > 1 ) param = $lnk.split( "?" )[ 1 ].paramToJson();
		if( $data ) param = $data;
		ajaxs.add({
			type : "POST",
			dataType : "json",
			url : lnk,
			data : param,
			timeout : 1000 * 60 * 3,
			cache : false,
			success : function( $result ){
				$( $tg ).removeClass("is-loading");
				if( $comFunc ) $comFunc( $result );
			},
			beforeSend : function(){
				$( $tg ).addClass("is-loading");
			},
			complete : function(){
				setTimeout(function(){
					$( $tg ).removeClass("is-loading");
				},400);
			},
			error : function($err){
				//if(chk_session2 == 0){
				//	alert("sesstion time out. go to login page.1");
				//	chk_session2++;
				//}
				//gotoPage(GLB_SITE_URL+"/view/member/login.jsp");
				//return false;
				//if( $err.statusText != "abort" ) msgMngr.send( $err.status + "<br>url : " + $lnk, "Ajax Error", 0, 1 );
			}
		});
	}
	
};


