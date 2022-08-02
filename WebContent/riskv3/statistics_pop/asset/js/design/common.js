/*!
 *
 * @author: RSN R&D Team LHS(GUNI)
 *			h2dlhs@realsn.com
 *
 *
 **/


// Nav Form Send
function gotoPage( $url, $frmName ){
	location.href = $url;
}

// UI Reset
var ui_reset = function( $tg ){
	if( !$tg ) $tg = $( "body" );

	// Design SelectBox 셋팅
	$tg.find( ".dcp > select" ).each( function(){
		var tg = $( this );
		tg.change( selectSet );
		selectSet();
		new MutationObserver( function( $e ) {
			if( tg.attr( "value" ) ) {
				tg[ 0 ].value = tg.attr( "value" );
				tg.removeAttr( "value" );
				tg.trigger( "change" );
			} else {
				selectSet();
			}
		}).observe( tg[ 0 ], { attributes: true, childList: true, characterData: true, subtree: true, attributeOldValue: true, characterDataOldValue: true });

		function selectSet() {
			tg.find( "+ label" ).html( tg.find( "> option:selected" ).html() );
		}
	});

	// Design CheckBox - 전체선택 기능
	designChk( $tg );
	function designChk( $tg ){
		$tg.find( ".dcp > input[type='checkbox'], .dcp_c > input[type='checkbox'], .ui_tab .tab_comp input[type='checkbox']" ).unbind( "change", hndl_designChk ).change( hndl_designChk );
		function hndl_designChk(){
			if( $( this ).hasClass( "allChecker" ) ) {
				if( this.checked ) {
					$tg.find( "input[data-grp='" + $( this ).attr( "data-grp" ) + "']:not(.allChecker)" ).not( "[disabled]" ).each(function(){
						this.checked = true;
					});
				} else {
					$tg.find( "input[data-grp='" + $( this ).attr( "data-grp" ) + "']:not(.allChecker)" ).not( "[disabled]" ).each(function(){
						this.checked = false;
					});
				}
			} else {
				var allChker = true;
				$tg.find( "input[data-grp='" + $( this ).attr( "data-grp" ) + "']:not(.allChecker)" ).not( "[disabled]" ).each(function(){
					if( !this.checked ) allChker = false;
				});
				if( allChker ) {
					$tg.find( "input[data-grp='" + $( this ).attr( "data-grp" ) + "'].allChecker" ).not( "[disabled]" ).prop( "checked", true );
				} else {
					$tg.find( "input[data-grp='" + $( this ).attr( "data-grp" ) + "'].allChecker" ).not( "[disabled]" ).prop( "checked", false );
				}
			}
		}
	}

	// 커스텀 셀렉트박스 (체크박스 + 자동완성)
	$tg.find( ".dcp_custom_select" ).autocomplete_check_select();

	// 자동완성 - 셀렉트 박스
	$tg.find( ".dcp_autocomplete_select" ).autocomplete_select();

	// 기간 Datepicker 검색 설정
	$tg.find( ".ui_datepicker_range" ).each( function(){
		var calWrap = $( this );
		var btnPrvMonth = calWrap.find( ".btn_prv_month" );
		var btnNxtMonth = calWrap.find( ".btn_nxt_month" );
		var firstDp = calWrap.find( ".searchs_dp_start" );
		var lastDp = calWrap.find( ".searchs_dp_end" );
		var rangeInput = calWrap.find( ".date_result" );

		btnPrvMonth.click( evt_prvMonth );
		btnNxtMonth.click( evt_nxtMonth );

		if( calWrap.find( ".calendars" ).attr( "data-fixed" ) == "true" ) {
			$( window, "*" ).unbind( "scroll", hndl_calPos ).scroll( hndl_calPos );
			$( window ).unbind( "resize", hndl_calPos ).resize( hndl_calPos );
		}

		var range = calWrap.data( "range" );
		var minFixDate = firstDp.data( "mindate" ) ? new Date( firstDp.data( "mindate" ) ) : null;
		var maxFixDate = lastDp.data( "maxdate" ) ? new Date( lastDp.data( "maxdate" ) ) : null;

		calWrap.find( ".dp_wrap" ).c_datepicker({
			dateChange: calDateChage
		});
		setTimeout( function(){
			calWrap.find( ".dp_wrap" ).each( function(){
				$( this ).data( "c_datepicker" ).setDate( $( this ).attr( "data-date" ) );
			});
			hndl_fdp_minDate();
			hndl_ldp_maxDate();
			lastDp.data( "c_datepicker" ).setMinDate( firstDp.data( "c_datepicker" ).getDate() );
			firstDp.data( "c_datepicker" ).setMaxDate( lastDp.data( "c_datepicker" ).getDate() );
			renderResult();
		}, 100 );
		calWrap.find( ".date_result" ).click( hndl_calendars );

		function renderResult(){
			var resultCal = firstDp.data( "c_datepicker" ).getDate() + " ~ " + lastDp.data( "c_datepicker" ).getDate();
			if( firstDp.attr( "data-time" ) ) resultCal = firstDp.attr( "data-date" ) + " " + firstDp.attr( "data-time" ) + " ~ " + lastDp.attr( "data-date" ) + " " + lastDp.attr( "data-time" );
			rangeInput.val( resultCal );
			hndl_dateGrp();
		}

		function calDateChage( $cal ){
			$cal.attr( "data-date", $cal.data( "c_datepicker" ).getDate() );
			if( $cal.attr( "data-time" ) ) $cal.attr( "data-time", $cal.data( "c_datepicker" ).getTime() );

			if( $cal.hasClass( "searchs_dp_start" ) ){		// 시작날짜
				lastDp.data( "c_datepicker" ).setMinDate( firstDp.data( "c_datepicker" ).getDate() );
			} else {										// 종료날짜
				firstDp.data( "c_datepicker" ).setMaxDate( lastDp.data( "c_datepicker" ).getDate() );
				hndl_fdp_minDate();
			}
			renderResult();
		}

		function hndl_fdp_minDate(){
			var minDate;
			if( range ){
				minDate = new Date( lastDp.data( "c_datepicker" ).getDate() );
				if( range.indexOf( "Y" ) >= 0 ) minDate.setFullYear( minDate.getFullYear() - ( range.split( "Y" )[ 0 ] ) );
				else if( range.indexOf( "M" ) >= 0 ) minDate.setMonth( minDate.getMonth() - ( range.split( "M" )[ 0 ] ) );
				else minDate.setDate( minDate.getDate() - range );

				if( minFixDate ) {
					if( minFixDate.getTime() > minDate.getTime() ) minDate = new Date( minFixDate );
				}
			} else {
				if( minFixDate ) minDate = new Date( minFixDate );
			}
			if( minDate ) firstDp.data( "c_datepicker" ).setMinDate( minDate.toISOString().slice(0,10).replace(/-/g,"-") );
		}
		function hndl_ldp_maxDate(){
			if( maxFixDate ) lastDp.data( "c_datepicker" ).setMaxDate( maxFixDate.toISOString().slice(0,10).replace(/-/g,"-") );
		}

		function hndl_calendars(){
			if( !calWrap.parent().find( ".calendars" ).is( ":visible" ) ){
				calWrap.find( ".date_result" ).addClass( "active" );
				calWrap.addClass( "active" );
				calWrap.parent().find( ".calendars" ).fadeIn( 120 );
				$( document ).click( docClick );
				/*
				var contentChkPos = parseInt( $( "#content" ).offset().left ) + parseInt( $( "#content" ).outerWidth() );
				var calChkPos = parseInt( calWrap.parent().find( ".calendars" ).offset().left ) + parseInt( calWrap.parent().find( ".calendars" ).outerWidth() );
				if( calChkPos > contentChkPos ) {
					calWrap.parent().find( ".calendars" ).css({
						left : "auto",
						right : 0
					});
				}
				*/
				if( calWrap.find( ".calendars" ).attr( "data-fixed" ) == "true" ) hndl_calPos();
			} else {
				calWrap.find( ".date_result" ).removeClass( "active" );
				calWrap.removeClass( "active" );
				$( document ).unbind( "click", docClick );
				calWrap.parent().find( ".calendars" ).fadeOut( 120 );
			}
		}
		function docClick( $e ){
			var tg = $e.target;
			if(  $( tg ).closest( calWrap ).length == 0 && $( tg ).parents( ".ui-datepicker-header" ).length == 0 ) hndl_calendars();
		}

		function evt_prvMonth(){
			var tmpDate1 = new Date( firstDp.data( "c_datepicker" ).getDate() );
			var tmpDate2;
			tmpDate1.setMonth( tmpDate1.getMonth() - 1 );
			tmpDate1.setDate( 1 );
			firstDp.data( "c_datepicker" ).setDate( tmpDate1 );
			tmpDate2 = new Date( firstDp.data( "c_datepicker" ).getDate() );
			tmpDate2.setMonth( tmpDate2.getMonth() + 1 );
			tmpDate2.setDate( tmpDate2.getDate() - 1 );
			lastDp.data( "c_datepicker" ).setDate( tmpDate2 );
		}
		function evt_nxtMonth(){
			var tmpDate1 = new Date( lastDp.data( "c_datepicker" ).getDate() );
			var tmpDate2;
			tmpDate1.setMonth( tmpDate1.getMonth() + 2 );
			tmpDate1.setDate( 1 );
			tmpDate1.setDate( tmpDate1.getDate() - 1 );
			lastDp.data( "c_datepicker" ).setDate( tmpDate1 );
			tmpDate2 = new Date( lastDp.data( "c_datepicker" ).getDate() );
			tmpDate2.setDate( 1 );
			firstDp.data( "c_datepicker" ).setDate( tmpDate2 );
		}

		function hndl_calPos(){
			var pos = {};
			pos.top = calWrap.find( ".input_wrap" )[ 0 ].getBoundingClientRect().top + calWrap.find( ".input_wrap" ).outerHeight();
			pos.left = calWrap.find( ".input_wrap" )[ 0 ].getBoundingClientRect().left;
			calWrap.find( ".calendars" ).css( { "top" : pos.top, "left" : pos.left } );
		}

		function hndl_dateGrp() {
			calWrap.find( "+ .ui_date_grp button" ).each( function(){
				$( this ).removeClass( "active" );
				var curDate = new Date();
				var sdate = firstDp.data( "c_datepicker" ).getDate();
				var edate = lastDp.data( "c_datepicker" ).getDate();
				var optEdate = curDate.getFullYear() + "-" + ( curDate.getMonth() + 1 ).numToStr_addZero() + "-" + curDate.getDate().numToStr_addZero();
				var dr = $( this ).data( "value" ).toString();
				if( dr.indexOf( "Y" ) >= 0 ) {
					curDate.setFullYear( curDate.getFullYear() - dr.split( "Y" )[ 0 ] );
				} else if( dr.indexOf( "M" ) >= 0 ) {
					curDate.setMonth( curDate.getMonth() - dr.split( "M" )[ 0 ] );
				} else if( dr.indexOf( "TH" ) >= 0 ) {
					if( dr.indexOf( "TH" ) > ( curDate.getMonth() - 1 ) ) {
						curDate.setFullYear( curDate.getFullYear() - 1 );
					}
					curDate.setDate(1);
					curDate.setMonth( dr.split("TH")[0] - 1 );
				} else {
					curDate.setDate( curDate.getDate() - ( dr - 1 ) );
				}
				var optSdate = curDate.getFullYear() + "-" + ( curDate.getMonth() + 1 ).numToStr_addZero() + "-" + curDate.getDate().numToStr_addZero();

				if( sdate == optSdate && sdate == optSdate ) $( this ).addClass( "active" );
			});
		}

		// 날짜 선택 그룹
		calWrap.find( "+ .ui_date_grp" ).each( function(){
			$( this ).find( "button" ).each( function(){
				var dr = $( this ).data( "value" ).toString();
				var ld = new Date();
				var fd = new Date();
				if( dr.indexOf( "Y" ) >= 0 ) {
					fd.setFullYear( fd.getFullYear() - dr.split( "Y" )[ 0 ] );
				} else if( dr.indexOf( "M" ) >= 0 ) {
					fd.setMonth( fd.getMonth() - dr.split( "M" )[ 0 ] );
				} else if( dr.indexOf( "TH" ) >= 0 ) {
					if( dr.split( "TH" )[ 0 ] > ( ld.getMonth() + 1 ) ) {
						fd.setFullYear( fd.getFullYear() - 1 );
						ld.setFullYear( ld.getFullYear() - 1 );
					}
					fd.setDate(1);
					fd.setMonth( dr.split("TH")[0] - 1 );
					ld.setDate(1);
					ld.setMonth( dr.split("TH")[0] );
					ld.setDate( ld.getDate() - 1 );
				} else {
					fd.setDate( fd.getDate() - ( dr - 1 ) );
				}

				$( this ).attr( "data-fd", fd.toISOString().slice(0,10).replace(/-/g,"-") );
				$( this ).attr( "data-ld", ld.toISOString().slice(0,10).replace(/-/g,"-") );

				$( this ).click( function(){
					lastDp.data( "c_datepicker" ).setMinDate( $( this ).data( "fd" ) );
					firstDp.data( "c_datepicker" ).setMaxDate( $( this ).data( "ld" ) );

					lastDp.data( "c_datepicker" ).setDate( $( this ).data( "ld" ) );
					firstDp.data( "c_datepicker" ).setDate( $( this ).data( "fd" ) );
				});
			});
		});
		function hndl_dateGrp() {
			if( calWrap.find( "+ .ui_date_grp" ).length > 0 ) {
				var sdate = firstDp.data( "c_datepicker" ).getDate();
				var edate = lastDp.data( "c_datepicker" ).getDate();

				calWrap.find( "+ .ui_date_grp button" ).each( function(){
					if( $( this ).data( "fd" ) == sdate && $( this ).data( "ld" ) == edate ) $( this ).addClass( "active" );
					else $( this ).removeClass( "active" );
				});


			}
		}
	});

	// 단독 Datepicker 설정
	$tg.find( ".ui_datepicker" ).each( function(){
		var tg = $( this );
		var calInput = $( this ).find( ".ui_datepicker_input input" );
		var btnPrvMonth = tg.find( ".btn_prv_month" );
		var btnNxtMonth = tg.find( ".btn_nxt_month" );
		btnPrvMonth.click( evt_prvMonth );
		btnNxtMonth.click( evt_nxtMonth );

		var minFixDate = tg.data( "mindate" ) ? new Date( tg.data( "mindate" ) ) : null;
		var maxFixDate = tg.data( "maxdate" ) ? new Date( tg.data( "maxdate" ) ) : null;

		var cal = $( this ).c_datepicker({
			dateChange: function(){
				tg.attr( "data-date", cal.data( "c_datepicker" ).getDate() );
				if( tg.attr( "data-time" ) ) tg.attr( "data-time", cal.data( "c_datepicker" ).getTime() );
				calInput.val( tg.attr( "data-date" ) );
				if( tg.attr( "data-time" ) ) calInput.val( tg.attr( "data-date" ) + " " + tg.attr( "data-time" ) );
				if( !cal.data( "time" ) ) {
					active = false;
					hndl_calendar();
				}
				if( tg.attr( "data-change" ) ) eval( tg.attr( "data-change" ) )( cal.data( "c_datepicker" ).getDate() );
			}
		});
		setTimeout( function(){
			tg.data( "c_datepicker" ).setMinDate( minFixDate );
			tg.data( "c_datepicker" ).setMaxDate( maxFixDate );

			if( calInput.val().length <= 0 ) calInput.val( cal.data( "c_datepicker" ).getDate() );
			if( cal.attr( "data-date" ) ) cal.data( "c_datepicker" ).setDate( cal.attr( "data-date" ) );
			renderResult();
		}, 100 );

		function renderResult(){
			tg.attr( "data-date", cal.data( "c_datepicker" ).getDate() );
			if( tg.attr( "data-time" ) ) tg.attr( "data-time", cal.data( "c_datepicker" ).getTime() );
			calInput.val( tg.attr( "data-date" ) );
			if( tg.attr( "data-time" ) ) calInput.val( tg.attr( "data-date" ) + " " + tg.attr( "data-time" ) );
		}

		// 이벤트 할당
		var active = false;
		calInput.click( function(){
			active = !active;
			hndl_calendar();
		});

		function hndl_calendar(){
			if( active ) {
				calInput.addClass( "active" );
				cal.data( "c_datepicker" ).show();
				$( document ).click( cal_docClick );
			} else {
				calInput.removeClass( "active" );
				cal.data( "c_datepicker" ).hide();
				$( document ).unbind( "click", cal_docClick );
			}
		}
		function cal_docClick( $e ){
			var $tg = $e.target;
			if(  $( $tg ).closest( tg ).length == 0 ) {
				active = false;
				hndl_calendar();
			}
		}

		function evt_prvMonth(){
			var tmpDate = new Date( cal.data( "c_datepicker" ).getDate() );
			tmpDate.setMonth( tmpDate.getMonth() - 1 );
			cal.data( "c_datepicker" ).setDate( tmpDate );
		}
		function evt_nxtMonth(){
			var tmpDate = new Date( cal.data( "c_datepicker" ).getDate() );
			tmpDate.setMonth( tmpDate.getMonth() + 1 );
			cal.data( "c_datepicker" ).setDate( tmpDate );
		}
	});

	// 게시판 정렬 토글
	$tg.find( ".ui_btn_sort" ).each( function(){
		var tg = $( this );
		tg.click( function(){
			var val = parseInt( tg.attr( "data-sort" ) ) + 1;
			tg.attr( "data-sort", ( ( val <= 2 ) ? val : 0 ) );
			hndl_sort();
		});
		hndl_sort();
		function hndl_sort(){
			switch( tg.attr( "data-sort" ) ){
				case "0" :
					tg.attr( "title", "정렬없음" );
					break;
				case "1" :
					tg.attr( "title", "오름차순" );
					break;
				case "2" :
					tg.attr( "title", "내림차순" );
					break;
			}
		}
	});

	// Bubble Box
	$tg.find( ".ui_bubble_box" ).each( function(){
		var activeChk = false;
		var bubbleBox = $( this );
		var code = $( this ).data( "bubble-for" );
		var btn = $( this ).parent().find( "*[data-bubble-id=\"" + code + "\"]" );

		if( btn.data( "action" ) == "click" ){
			btn.click( function(){
				activeChk = !activeChk;
				hndl_bubbleBox();
			});
		} else {
			btn.hover( function( $e ){
				if( $e.type == "mouseenter" ) activeChk = true;
				else activeChk = false;
				hndl_bubbleBox();
			});
		}

		if( bubbleBox.attr( "data-fixed" ) == "true" ) {
			$( window, "*" ).unbind( "scroll", hndl_bubbleBox ).scroll( hndl_bubbleBox );
			$( window ).unbind( "resize", hndl_bubbleBox ).resize( hndl_bubbleBox );
		}

		function hndl_bubbleBox(){
			if( activeChk ){
				var pos = getPos( bubbleBox.data( "pos" ) );
				bubbleBox.css( { "top" : pos.top, "left" : pos.left } ).fadeIn( 120 );
				btn.addClass( "active" );
			} else {
				bubbleBox.fadeOut( 120 );
				btn.removeClass( "active" );
			}
		}

		function getPos( $type ){
			var result = {};
			var space = 11;

			if( bubbleBox.attr( "data-fixed" ) == "true" ) {
				if( $type.indexOf( "T" ) >= 0 ) {
					result.top = btn[ 0 ].getBoundingClientRect().top - bubbleBox.outerHeight() - space;
				} else if( $type.indexOf( "B" ) >= 0 ) {
					result.top = btn[ 0 ].getBoundingClientRect().top + btn.outerHeight() + space;
				}
				if( $type.indexOf( "L" ) >= 0 ) {
					result.left = btn[ 0 ].getBoundingClientRect().left;
					if( bubbleBox.attr( "data-arrowcenter" ) == "true" ) {
						var posX = result.left - btn[ 0 ].getBoundingClientRect().left + ( btn.outerWidth() / 2 ) - ( bubbleBox.find( ".arrow" ).outerWidth() / 2 );
						bubbleBox.find( ".arrow" ).css( "left", posX );
					}
				} else if( $type.indexOf( "R" ) >= 0 ) {
					result.left = btn[ 0 ].getBoundingClientRect().left + btn.outerWidth() - bubbleBox.outerWidth();
					if( bubbleBox.attr( "data-arrowcenter" ) == "true" ) {
						var posX = btn[ 0 ].getBoundingClientRect().left - result.left + ( btn.outerWidth() / 2 ) - ( bubbleBox.find( ".arrow" ).outerWidth() / 2 );
						bubbleBox.find( ".arrow" ).css( "left", posX );
					}
				} else if( $type.indexOf( "C" ) >= 0 ) {
					result.left = ( btn[ 0 ].getBoundingClientRect().left + ( btn.outerWidth() / 2 ) ) - ( bubbleBox.outerWidth() / 2 );
				}
			} else {
				if( $type.indexOf( "T" ) >= 0 ) {
					result.top = btn.position().top - bubbleBox.outerHeight() - space;
				} else if( $type.indexOf( "B" ) >= 0 ) {
					result.top = btn.position().top + btn.outerHeight() + space;
				}

				if( $type.indexOf( "L" ) >= 0 ) {
					result.left = btn.position().left;
					if( bubbleBox.attr( "data-arrowcenter" ) == "true" ) {
						var posX = result.left - btn.position().left + ( btn.outerWidth() / 2 ) - ( bubbleBox.find( ".arrow" ).outerWidth() / 2 );
						bubbleBox.find( ".arrow" ).css( "left", posX );
					}
				} else if( $type.indexOf( "R" ) >= 0 ) {
					result.left = btn.position().left + btn.outerWidth() - bubbleBox.outerWidth();
					if( bubbleBox.attr( "data-arrowcenter" ) == "true" ) {
						var posX = btn.position().left - result.left + ( btn.outerWidth() / 2 ) - ( bubbleBox.find( ".arrow" ).outerWidth() / 2 );
						bubbleBox.find( ".arrow" ).css( "left", posX );
					}
				} else if( $type.indexOf( "C" ) >= 0 ) {
					result.left = ( btn.position().left + ( btn.outerWidth() / 2 ) ) - ( bubbleBox.outerWidth() / 2 );
				}

			}

			return result;
		}
	});

	// Toggle버튼
	$tg.find( ".ui_toggle_btn" ).each( function(){
		$( this ).click( function(){
			$( this ).toggleClass( "active" );
		});
	});

	// Fixed table
	$tg.find( ".ui_fixed_table" ).each( function(){
		$( this ).fixed_table();
	});

	// Loader 삽입
	$tg.find( ".ui_loader_container" ).each( function(){
		if( $( this ).find( "> .ui_loader" ).length <= 0 ) {
			$( this ).append( "<div class=\"ui_loader\"><span class=\"loader\">Load</span></div>" );
		}
	});

	/*
	// Ui Col > Width 재정렬(테이블 포함 일 경우 소수점 없애기)
	$tg.find( ".ui_row" ).each( function(){
		var cnt = 0;
		var wid = 0;

		$( this ).find( "> .ui_col" ).each( function(){
			if( $( this ).has( "table" ) ) {
				$( this ).width( $( this ).width() );
				wid += $( this ).outerWidth();
				cnt++;
			}
		});

		if( $( this ).has( "table" ) && cnt > 0 ) {
			if( $( this ).outerWidth() > wid ){
				var gap = $( this ).outerWidth() - wid;
				for( var Loop1 = 1 ; Loop1 <= gap ; ++Loop1 ){
					$( this ).find( "> .ui_col" ).eq( Loop1 ).each( function(){
						$( this ).width( $( this ).width() + 1 );
					});
				}
			}
		}
	});
	*/
}


// 메세지 매니저
var msgMngr = {
	stack : [],
	send : function( $txt, $title, $btnType, $mType, $func ) {
		var stackData = {};
		stackData.txt = $txt;
		stackData.title = $title;
		stackData.btnType = $btnType;
		stackData.mType = $mType;
		stackData.func = $func;
		msgMngr.stack.push( stackData );
		if( !$( "#msg_box" ).is( ":visible" ) ) msgMngr.openMBox();
	},
	openMBox : function(){
		var arrAlertType = [];
			arrAlertType[ 1 ] = $( "<div class='icons'><span class='icon_error'></span></div>" );
			arrAlertType[ 2 ] = $( "<div class='icons'><span class='icon_warning'></span></div>" );
			arrAlertType[ 3 ] = $( "<div class='icons'><span class='icon_info'></span></div>" );
			arrAlertType[ 4 ] = $( "<div class='icons'><span class='icon_question'></span></div>" );
		var arrAlertBtns = [];
			arrAlertBtns[ 0 ] = $( "<button type='button' class='ui_shadow_00' data-value='0'><span>확인</span></button>" );
			arrAlertBtns[ 1 ] = $( "<button type='button' class='ui_shadow_00' data-value='1'><span>취소</span></button>" );
			arrAlertBtns[ 2 ] = $( "<button type='button' class='ui_shadow_00' data-value='2'><span>예</span></button>" );
			arrAlertBtns[ 3 ] = $( "<button type='button' class='ui_shadow_00' data-value='3'><span>아니요</span></button>" );

		var $txt = msgMngr.stack[ 0 ].txt;
		var $title = msgMngr.stack[ 0 ].title;
		var $btnType = msgMngr.stack[ 0 ].btnType;
		var $mType = msgMngr.stack[ 0 ].mType;
		var $func = msgMngr.stack[ 0 ].func;

		$( "#msg_box .box" ).html("");
		$( "#msg_box .box" ).hide();
		if( $title && $title != "" ) $( "#msg_box .box" ).append( "<h2>" + $title + "</h2>" );
		if( $mType && $mType != 0 ) $( "#msg_box .box" ).append( arrAlertType[ $mType ] );
		if( $txt != "" ){
			if( !$mType || $mType == 0 ) $( "#msg_box .box" ).append( "<div class='txts alone'><span>" + String($txt).replaceAll("\n", "<br>") + "</span></div>" );
			else $( "#msg_box .box" ).append( "<div class='txts'><span>" + $txt + "</span></div>" );
		}
		$( "#msg_box .box" ).append( "<div class='btns'></div>" );
		if( !$btnType ) $btnType = 0;
		switch( $btnType ){
			case 0 :
				$( "#msg_box .box .btns" ).append( arrAlertBtns[ 0 ] );
				break;
			case 1 :
				$( "#msg_box .box .btns" ).append( arrAlertBtns[ 0 ] );
				$( "#msg_box .box .btns" ).append( arrAlertBtns[ 1 ] );
				break;
			case 2 :
				$( "#msg_box .box .btns" ).append( arrAlertBtns[ 2 ] );
				$( "#msg_box .box .btns" ).append( arrAlertBtns[ 3 ] );
				break;
			case 3 :
				$( "#msg_box .box .btns" ).append( arrAlertBtns[ 2 ] );
				$( "#msg_box .box .btns" ).append( arrAlertBtns[ 3 ] );
				$( "#msg_box .box .btns" ).append( arrAlertBtns[ 1 ] );
				break;
		}
		$( "#msg_box .box .btns > *" ).click( function(){
			if( $func ) $func( $( this ).attr( "data-value" ) );
			msgMngr.close();
		});
		$( "#msg_box" ).fadeIn( 200, function(){
			$( "#msg_box .box" ).css( "top", "53%" ).show().animate( { "top" : "50%" }, 200, "easeOutQuad", function(){
				if( msgMngr.stack.length > 0 ) msgMngr.stack.splice( 0, 1 );
				$( "#msg_box .box .btns > *" ).eq( 0 ).focus();
			});
			$( "#msg_box .box" ).css( { "margin-top" : -$( "#msg_box .box" ).outerHeight() / 2, "margin-left" : -$( "#msg_box .box" ).outerWidth() / 2 });
		});
	},
	close : function(){
		$( "#msg_box .box" ).animate( { "top" : "47%" }, 200, "easeInQuad" );
		$( "#msg_box" ).fadeOut( 200, function(){
			$( "#msg_box .box .btns > *" ).unbind( "click" );
			$( "#msg_box .box" ).children().remove();

			if( msgMngr.stack.length > 0 ) {
				msgMngr.openMBox();
				return;
			}
		});
	}
};

// 팝업 매니저
var popupMngr = {
	open : function( $tg ){
		var $popup = $( "#popup_container" );
		var $tg = $( $tg );
		$popup.fadeIn( 300 );
		$tg.show().css({
			"top" : "50%",
			"left" : "50%",
			"margin-top" : -( $tg.outerHeight() / 2 ) + 50,
			"margin-left" : -$tg.outerWidth() / 2,
		}).animate( {
			"margin-top" : -( $tg.outerHeight() / 2 )
		}, 300, "easeOutQuad" );
	},
	close : function( $tg ){
		$( "#inPopup_container .inPopup_item" ).each( function(){
			inPopupMngr.close( this );
		});
		var $popup = $( "#popup_container" );
		var $tg = $( $tg );
		$popup.fadeOut( 300 );
		$tg.animate( {
			"margin-top" : -$tg.outerHeight() / 2 - 50
		}, 300, "easeInQuad", function(){
				$tg.hide();
		});
	},
	update : function( $tg ){				// 팝업 포지션 업데이트
		var $tg = $( $tg );
		var mt = -( $tg.outerHeight() / 2 );
		$tg.css( "margin-top", mt );
	}
}
var inPopupMngr = {
	open : function( $tg ){
		var $popup = $( "#inPopup_container" );
		var $tg = $( $tg );
		$popup.fadeIn( 300 );
		$tg.show().css({
			"top" : "50%",
			"left" : "50%",
			"margin-top" : -( $tg.outerHeight() / 2 ) + 50,
			"margin-left" : -$tg.outerWidth() / 2,
		}).animate( {
			"margin-top" : -( $tg.outerHeight() / 2 )
		}, 300, "easeOutQuad" );
	},
	close : function( $tg ){
		var $popup = $( "#inPopup_container" );
		var $tg = $( $tg );
		$popup.fadeOut( 300 );
		$tg.animate( {
			"margin-top" : -$tg.outerHeight() / 2 - 50
		}, 300, "easeInQuad", function(){
				$tg.hide();
		});
	}
}

// 상단 검색결과 매니저
var searchResultMngr = {
	destroy : function(){
		$( "#top_searchs .searchs_result .lists > *" ).remove();
	},
	addItem : function( $txt, $title ){
		var item = "<li><span class='item' title='" + $title + " : " + $txt + "'>" + $txt + "</span></li>";
		$( "#top_searchs .searchs_result .lists" ).append( item );
	}
}

// Treemap
$.fn.extend({
	setTreemap: function( $data ){
		var tg = $( this );
		$( this ).treemap( $data, {
			smallestFontSize: 12,
			startingFontSize: 12,
			nodeClass: function ( $node, $box, $itemLen ){
				var result = "";

				if( $box.width() < 30 || $box.height() < 30 ) result = "full hide ";
				else if( $box.width() < 70 || $box.height() < 90 ) result = "hide ";

				var len = $itemLen - 5;
				var lenAddCnt = len / 5;
				if( $box.index() == 0 ) return result + "share_1";
				else if( $box.index() == 1 ) return result + "share_2";
				else if( $box.index() == 2 ) return result + "share_3";
				else if( $box.index() == 3 ) return result + "share_4";
				else if( $box.index() == 4 ) return result + "share_5";
				else if( $box.index() > 4 && $box.index() <= 5 + ( lenAddCnt * 1 ) ) return result + "share_6";
				else if( $box.index() > 5 + ( lenAddCnt * 1 ) && $box.index() <= 5 + ( lenAddCnt * 2 ) ) return result + "share_7";
				else if( $box.index() > 5 + ( lenAddCnt * 2 ) && $box.index() <= 5 + ( lenAddCnt * 3 ) ) return result + "share_8";
				else if( $box.index() > 5 + ( lenAddCnt * 3 ) && $box.index() <= 5 + ( lenAddCnt * 4 ) ) return result + "share_9";
				else return result + "share_10";
			},
			ready: function (){
				$( this.$div ).find( ".treemap-item" ).each( function(){
					if( $( this ).width() > $( this ).parent().width() || $( this ).height() > $( this ).parent().height() ) $( this ).parent().addClass( "hide" );
					$( this ).css( "margin-top", ( $( this ).parent().height() - $( this ).height() ) / 2 );
				});
				this.$div.find( ".treemap-node" ).each( function( $idx ){
					if( $( this ).hasClass( "hide" ) ){
						var idx = tg.attr( "id" ) + "_" + $idx;
						$( this ).attr( "data-bubble-id", idx );
						var bubbleBox = $( "<div class=\"ui_bubble_box\" data-bubble-for=\"" + idx + "\" data-pos=\"CT\"><span class=\"arrow\">-</span><div class=\"wrap\">" + $( this ).find( ".infos" ).html() + "</div></div>" );
						tg.parent().find( ".treemap_bubble_wrap" ).append( bubbleBox );
					}
				});
				setBubble( tg.parent() );
			},
			//mouseenter: evt_treemap_mouseEnter,
			//mouseleave: evt_treemap_mouseLeave,
			//mousemove: evt_treemap_mouseMove,
			//click: function( $node, $e ){
			//},
			itemMargin: 1
		});

		function setBubble( $tg ){
			$tg.find( ".ui_bubble_box" ).each( function(){
				var activeChk = false;
				var bubbleBox = $( this );
				var code = $( this ).data( "bubble-for" );
				var btn = $( document ).find( "*[data-bubble-id=\"" + code + "\"]" );

				if( btn.data( "action" ) == "click" ){
					btn.click( function(){
						activeChk = !activeChk;
						hndl_bubbleBox();
					});
				} else {
					btn.hover( function( $e ){
						if( $e.type == "mouseenter" ) activeChk = true;
						else activeChk = false;
						hndl_bubbleBox();
					});
				}

				function hndl_bubbleBox(){
					if( activeChk ){
						var pos = getPos( bubbleBox.data( "pos" ) );
						bubbleBox.css( { "top" : pos.top, "left" : pos.left } ).fadeIn( 120 );
					} else {
						bubbleBox.fadeOut( 120 );
					}
				}

				function getPos( $type ){
					var result = {};
					var space = 11;

					console.log( bubbleBox.outerHeight() );

						result.top = ( btn.position().top + ( btn.outerHeight() / 2 ) ) - bubbleBox.outerHeight() - space;
						result.left = ( btn.position().left + ( btn.outerWidth() / 2 ) ) - ( bubbleBox.outerWidth() / 2 );

						/*

						if( $type.indexOf( "T" ) >= 0 ) {
							result.top = btn.position().top - bubbleBox.outerHeight() - space;
						} else if( $type.indexOf( "B" ) >= 0 ) {
							result.top = btn.position().top + btn.outerHeight() + space;
						}

						if( $type.indexOf( "L" ) >= 0 ) {
							result.left = btn.position().left;
							if( bubbleBox.attr( "data-arrowcenter" ) == "true" ) {
								var posX = result.left - btn.position().left + ( btn.outerWidth() / 2 ) - ( bubbleBox.find( ".arrow" ).outerWidth() / 2 );
								bubbleBox.find( ".arrow" ).css( "left", posX );
							}
						} else if( $type.indexOf( "R" ) >= 0 ) {
							result.left = btn.position().left + btn.outerWidth() - bubbleBox.outerWidth();
							if( bubbleBox.attr( "data-arrowcenter" ) == "true" ) {
								var posX = btn.position().left - result.left + ( btn.outerWidth() / 2 ) - ( bubbleBox.find( ".arrow" ).outerWidth() / 2 );
								bubbleBox.find( ".arrow" ).css( "left", posX );
							}
						} else if( $type.indexOf( "C" ) >= 0 ) {

						}
						*/


					return result;
				}
			});
		}
	}
});
	/*
elementPrototype.setTreemap = function( $data ){


}
	*/
/*
function setTreeMap(){


	function evt_treemap_mouseEnter( $node, $e ) {
		var dummy = $( $node.label );
		var bubble = $( $e.target ).parent().parent().parent().find( "svg" );

		bubble.find( ".bubble_name" ).text( dummy.find( "> .title" ).html().toString().limit( 8 ) );
		bubble.find( ".bubble_dv_1" ).text( dummy.find( ".datas .dv" ).html() );
		bubble.find( ".bubble_dv_2" ).text( dummy.find( ".ui_fluc" ).html() + ")" );
		bubble.find( ".bubble_dv_3" ).text( dummy.find( ".per" ).html() );

		if( dummy.find( ".ui_icon_hot" ).length > 0 ) $( "#icon_hot" ).show();
		else $( "#icon_hot" ).hide();

		bubble.find( ".fluc_up, .fluc_dn, .fluc_none" ).hide();

		if( $node.data.fluc == 1 ) bubble.find( ".fluc_up" ).show();
		else if( $node.data.fluc == 2 ) bubble.find( ".fluc_dn" ).show();
		else bubble.find( ".fluc_none" ).show();

		var tg = $( $e.target ).parent();
		if( tg.hasClass( "hide" ) ) {
			bubble.stop().fadeIn( 80 );
		}
	}
	function evt_treemap_mouseLeave( $node, $e ) {
		var bubble = $( $e.target ).parent().parent().parent().find( "svg" );
		bubble.stop().fadeOut( 80 );
	}
	function evt_treemap_mouseMove( $node, $e ) {
		var treemapContainer = $( $e.target ).parent().parent().parent();
		var treemap = $( $e.target ).parent().parent().parent().find( ".ui_treemap" );
		var bubble = $( $e.target ).parent().parent().parent().find( "svg" );
		var pos = { top : $e.pageY -treemap.offset().top - bubble.outerHeight(), left : $e.pageX - treemap.offset().left - ( bubble.outerWidth() / 2 )  };
		if( ( pos.left + bubble.width() ) > treemapContainer.width() ){
			pos.left = pos.left - ( ( pos.left + bubble.width() ) - treemapContainer.width() );
		}
		if( pos.left < 0 ){
			pos.left = 0;
		}
		if( pos.top < -52 ){
			pos.top = -52;
		}
		bubble.css( { top : pos.top, left : pos.left } );
		var bubblePos = $e.pageX - treemap.offset().left - pos.left - 5;
		if( bubblePos > 128 ) bubblePos = 128;
		bubble.find( ".bubble_arrow" ).attr( "transform", "translate(" + bubblePos + " 81)" );
	}
}
*/
