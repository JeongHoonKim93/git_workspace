/*!
 *
 * @author: GUNI, h2dlhs@realsn.com
 **/
 
 
(function ($) {

    // Datepicker Before Show 확장
    $.extend($.datepicker, {
        _inlineDatepicker2: $.datepicker._inlineDatepicker,
        _inlineDatepicker: function (target, inst) {
            this._inlineDatepicker2(target, inst);
            var beforeShow = $.datepicker._get(inst, 'beforeShow');
            if (beforeShow) {
                beforeShow.apply(target, [target, inst]);
            }
        }
    });

	var dpClass = function( $el, $options ) {

		var cal;
		var calWrap;
		var calWrap_datewrap;
		var calWrap_calwrap;
		var calWrap_timewrap;
		var calWrap_timewrap_time_00;
		var calWrap_timewrap_time_01;
		var calWrap_result;
		var calWrap_date;

		var btnsGrp;
		var btnsGrp_prv_y;
		var btnsGrp_prv_m;
		var btnsGrp_nxt_m;
		var btnsGrp_nxt_y;

		var calDate;
		var time;
		var arrMonths = [ "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" ];
        var arrDays = [ "일", "월", "화", "수", "목", "금", "토" ];
        
        var weekStr;


		this.init = function () {
			time = ( $el.data( "time" ) ) ? $el.data( "time" ) : null;

			buildCalendar();

			$( window, "*" ).scroll( hndl_pos );
			$( window ).resize( hndl_pos );
		}

		// **********		Build		************************************************************************************************ //
		function buildCalendar(){
			cal = $( "<div class='ui_calendar'></div>" );
			calWrap_datewrap = $( "<div class='date_wrap'></div>" );
			calWrap_calwrap = $( "<div class='calendar_wrap'></div>" );
			calWrap_result = $( "<div class='result'></div>" );
			calWrap_date = $( "<div class='date'></div>" );

			cal.append( calWrap_datewrap );
			cal.append( calWrap_calwrap );
			calWrap_datewrap.append( calWrap_result );
			calWrap_datewrap.append( calWrap_date );

			// Time 옵션이 있을경우에 Time 활성화
			if( time ){
				calWrap_timewrap = $( "<div class='time_wrap'><strong>시간</strong><div class='wrap'></div></div>" );
				calWrap_timewrap_time_00 = build_time( "hour" );
				calWrap_timewrap_time_01 = build_time( "min" );
				calWrap_timewrap_time_00.find( "select" ).change( evt_timeChange );
				calWrap_timewrap_time_01.find( "select" ).change( evt_timeChange );
				cal.append( calWrap_timewrap );
				calWrap_timewrap.find( ".wrap" ).append( calWrap_timewrap_time_00 );
				calWrap_timewrap.find( ".wrap" ).append( calWrap_timewrap_time_01 );
				calWrap_timewrap_time_00.find( "select" ).val( parseInt( time.split( ":" )[ 0 ] ) );
				calWrap_timewrap_time_01.find( "select" ).val( parseInt( time.split( ":" )[ 1 ] ) );
			}

			btnsGrp = $( "<div class='btns_grp'></div>" );
			btnsGrp_prv_y = $( "<button class='btn_prv_y' title='이전년도'><span class='icon'>이전년도</span></button>" );
			btnsGrp_prv_m = $( "<button class='btn_prv_m' title='이전달'><span class='icon'>이전월</span></button>" );
			btnsGrp_nxt_m = $( "<button class='btn_nxt_m' title='다음달'><span class='icon'>다음월</span></button>" );
			btnsGrp_nxt_y = $( "<button class='btn_nxt_y' title='다음년도'><span class='icon'>다음년도</span></button>" );
			btnsGrp.append( btnsGrp_prv_y );
			btnsGrp.append( btnsGrp_prv_m );
			btnsGrp.append( btnsGrp_nxt_m );
			btnsGrp.append( btnsGrp_nxt_y );
			cal.append( btnsGrp );

			btnsGrp_prv_y.click( evt_prv_y );
			btnsGrp_prv_m.click( evt_prv_m );
			btnsGrp_nxt_m.click( evt_nxt_m );
			btnsGrp_nxt_y.click( evt_nxt_y );

			//$( "body" ).append( cal );
			$el.append( cal );
			$el.bind( "update", evt_update );
            calWrap = cal.find( ".calendar_wrap" );
			calWrap.datepicker({
                dateFormat: "yy-mm-dd",
                minDate: $options.firstDate,
                maxDate: $options.lastDate,
                defaultDate: $options.initDate,
                firstDay: $options.firstDay,
                monthNames: arrMonths,
                dayNamesMin: arrDays,
                showOtherMonths: true,
                selectOtherMonths: true,
				onChangeMonthYear: hndl_renderCal,
                onSelect: evt_onSelect,
                showWeek: $options.type == "week" ? true : false,
                weekHeader: $options.type == "week" ? "#" : "",
				calculateWeek: function( $date ){
                    var tmpDate = new Date( $date.getFullYear(), 0, 1 );
                    var firstDate = new Date( tmpDate.dateToStr().getDayDate().split( "-" )[ 0 ], tmpDate.dateToStr().getDayDate().split( "-" )[ 1 ] - 1, tmpDate.dateToStr().getDayDate().split( "-" )[ 2 ], 0, 0, 0 );
                    var curDate = new Date( $date );
                    var curDateNext = new Date( $date );
                    curDateNext.setDate( curDateNext.getDate() + 6 );
                    var result = Math.floor( ( curDate - firstDate ) / (1000 * 3600 * 24) / 7 ) + 1;
                    if( curDate.getFullYear() != curDateNext.getFullYear() ) result = 1;

                    return result;
                    // return new Date( $date ).dateToStr().getWeekDay();
                },
                beforeShowDay: function( $date ){
                    if( $date.toString().indexOf( 'Sun ' ) != -1 ) return [ true, 'is-holiday' ];
                    else return [ true ];
                }
			});
			// }).datepicker( "setDate",  );

			hndl_renderCal();
			hndl_pos();

			ui_reset( $el );
		}

		function build_time( $type ){
			var len = 23;
			var typeStr = "시";
			if( $type == "min" ){
				len = 59;
				typeStr = "분";
			}
			var idx = "time_" + ( Math.random() * 10000000 );
			var result = $( "<div class='dcp'></div>" );
			var sel = $( "<select id='" + idx + "' class='ui_select'></select>" );
			var label = $( "<label for=" + idx + "></label>" );
			
			for( var Loop1 = 0 ; Loop1 <= len ; ++Loop1 ){
				var item = $( "<option value=" + Loop1 + ">" + Loop1.numToStr_addZero() + typeStr + "</option>" );
				sel.append( item );
			}

			result.append( sel );
			result.append( label );
			return result;
		}



		// **********		Hndler		************************************************************************************************ //
		function hndl_renderCal(){
            if( calWrap.find( "a.ui-state-active" ).length ) weekStr = calWrap.find( "a.ui-state-active" ).parent().parent().find( "td:first-child" ).text();
            calDate = calWrap.datepicker( "getDate" );
			calWrap_result.html( function(){
                if( $options.type == "day" ) return arrDays[ calDate.getDay() ] + "요일";
                // else if( $options.type == "week" ) return calDate.getFullYear() + "년 " + $.datepicker.iso8601Week( calDate ) + "주차";
                // else if( $options.type == "week" ) return calDate.getFullYear() + "년 " + calWrap.find( "a.ui-state-active" ).parent().parent().find( "td:first-child" ).text() + "주차";
                else if( $options.type == "week" ) return calDate.getFullYear() + "년 " + weekStr + "주차";
                else if( $options.type == "month" ) return "month";
                else if( $options.type == "quarter" ) return "quarter";
                else if( $options.type == "half" ) return "half";
                else if( $options.type == "year" ) return "year";
            });

            if( !$options.onlyOne || $options.type == "day" ) calWrap_date.html( calDate.getFullYear() + "-" + arrMonths[ calDate.getMonth() ] + "-" + calDate.getDate().numToStr_addZero() );
            else {
                var fd = new Date( ( calDate.dateToStr() ).dateReplaceIE() + " 00:00:00" );
                var ld = new Date( ( calDate.dateToStr() ).dateReplaceIE() + " 00:00:00" );
                ld.setDate( ld.getDate() + 6 );
                calWrap_date.html( fd.dateToStr() + " ~ " + ld.dateToStr() );
            }

			calWrap.find( "td" ).each(function() {
				var $this = $(this);
                $this.html( $this.html().replace(/&nbsp;/g, '') );
                
                if( $this.hasClass( "ui-datepicker-week-col" ) ) {
                    $this.html( "<span>" + $this.text() + "</span>");
                }
            });
            
            if( $options.updateCalendar ) $options.updateCalendar();
			//evt_timeChange();
		}

		function hndl_pos(){
			if( !cal.is( ":visible" ) ) return;
			if( cal.css( "position" ) == "fixed" ) {
				var pos = {};
				$el[ 0 ].getBoundingClientRect()
				pos.top = $el[ 0 ].getBoundingClientRect().top + $el.find( ".ui_datepicker_input" ).outerHeight();
				pos.left = $el[ 0 ].getBoundingClientRect().left;
				// cal.css( { top : pos.top, left : pos.left } );
			} else {
				var pos = {};
				$el.position()
				pos.top = $el.position().top + $el.find( ".ui_datepicker_input" ).outerHeight();
				pos.left = $el.position().left;
				// cal.css( { top : pos.top, left : pos.left } );
			}
		}


		// **********		Event		************************************************************************************************ //
		function evt_prv_y(){
			$.datepicker._adjustDate( calWrap, -1, 'Y' );
			// hndl_renderCal();
			return false;
		}
		function evt_prv_m(){
			$.datepicker._adjustDate( calWrap, -1, 'M' );
			// hndl_renderCal();
			return false;
		}
		function evt_nxt_m(){
			$.datepicker._adjustDate( calWrap, +1, 'M' );
			// hndl_renderCal();
			return false;
		}
		function evt_nxt_y(){
			$.datepicker._adjustDate( calWrap, +1, 'Y' );
			// hndl_renderCal();
			return false;
		}

		function evt_onSelect(){
			hndl_renderCal();
			if( $options && $options.dateChange ) $options.dateChange( calWrap.datepicker().val() );
		}

		function evt_timeChange(){
			if( time ) {
				$el.attr( "data-date", calWrap.datepicker().val() + " " + calWrap_timewrap_time_00.find( "select" ).val() + ":" + calWrap_timewrap_time_01.find( "select" ).val() );
				if( $options && $options.dateChange ) {
					$options.dateChange( $el );
				}
				hndl_renderCal();
			}
		}

		function evt_update( $e, $date ){
			if( time ){
				calWrap.datepicker( "setDate", $date.split( " " )[ 0 ] );
				calWrap_timewrap_time_00.find( "select" ).val( $date.split( " " )[ 1 ].split( ":" )[ 0 ] );
				calWrap_timewrap_time_01.find( "select" ).val( $date.split( " " )[ 1 ].split( ":" )[ 1 ] );
				calWrap_timewrap_time_00.find( "select" ).trigger( "change" );
				calWrap_timewrap_time_01.find( "select" ).trigger( "change" );
			} else {
				calWrap.datepicker( "setDate", $date );
			}

			hndl_renderCal();
			evt_onSelect();
			if( $options && $options.dateChange ) $options.dateChange( $el );
		}






		// **********		Out Method		************************************************************************************************ //

		// Get Date ( return : YYYY-MM-DD )
		this.getDate = function(){
			var result = calWrap.datepicker().val();
			console.log( calWrap.datepicker() );
			//if( time ) result = result + " " + parseInt( calWrap_timewrap_time_00.find( "select" ).val() ).numToStr_addZero() + ":" + parseInt( calWrap_timewrap_time_01.find( "select" ).val() ).numToStr_addZero(); 
			return result;
		}

		// Set Date ( YYYY-MM-DD )
		this.setDate = function( $date ){
			if( calWrap ) {
                calWrap.datepicker( "setDate", $date );
                hndl_renderCal();
                // if( $options && $options.dateChange ) $options.dateChange( $el );
            }
		}

		// Get Time ( 00:00 );
		this.getTime = function(){
			// var result = parseInt( calWrap_timewrap_time_00.find( "select" ).val() ).numToStr_addZero() + ":" + parseInt( calWrap_timewrap_time_01.find( "select" ).val() ).numToStr_addZero()
			// //if( time ) result = result + " " + parseInt( calWrap_timewrap_time_00.find( "select" ).val() ).numToStr_addZero() + ":" + parseInt( calWrap_timewrap_time_01.find( "select" ).val() ).numToStr_addZero(); 
			// return result;
		}

		// Set MinDate ( Value )
		this.setMinDate = function( $value ){
			calWrap.datepicker( "option", "minDate", $value );
			hndl_renderCal();
		}
		// Set MaxDate ( Value )
		this.setMaxDate = function( $value ){
			calWrap.datepicker( "option", "maxDate", $value );
			hndl_renderCal();
		}

		// Option Setting ( Attribute, Value )
		this.option = function( $attr, $value ){
			calWrap.datepicker( "option", $attr, $value );
			hndl_renderCal();
		}

		// Datepicker On
		this.show = function( $value ){
			cal.fadeIn( 120 );
			hndl_renderCal();
			hndl_pos();
		}

		// Datepicker Off
		this.hide = function( $value ){
			hndl_renderCal();
			cal.fadeOut( 120 );
        }
        
        this.destroyed = function() {
            if( calWrap ) calWrap.datepicker("destroy");
        }
	};

	$.fn.c_datepicker = function ( $options ) {
		return this.each(function() {
			var dp = new dpClass( $( this ), $options );
			$.data( this, 'c_datepicker', dp );
			$( document ).ready( dp.init );
		});
	}
})(jQuery);
