/*!
 *
 * @author: GUNI, h2dlhs@realsn.com
 **/
 
 
(function ($) {

	var dpsClass = function( $el, $options ) {

        var sel1_opts;
        var sel2_opts;

        var id = $options.id;
        var firstDate, curDate;
        var date = $options.initDate;
        var type = $options.type;
        var day = $options.day;

        var $selector, $resultWrap, $result, $date, $calWrap;
        var $sel01, $sel02;


		this.init = function () {
			build();
		}

        // **********		Build		************************************************************************************************ //
		function build(){
            var tmpDate = ( new Date() ).dateToStr();
            firstDate = $options.firstDate ? new Date( $options.firstDate.dateReplaceIE() + " 00:00:00" ) : new Date( tmpDate.dateReplaceIE() + " 00:00:00" );
            if( !$options.firstDate ) firstDate.setFullYear( firstDate.getFullYear() - 2 );
            curDate = new Date( tmpDate.dateReplaceIE() + " 00:00:00" );

            $selector = $( '<div class="ui_calendar" />' );
            $resultWrap = $( '<div class="date_wrap" />' );
            $result = $( '<div class="result"></div>' );
            $date = $( '<div class="date"></div>' );
            $calWrap = $( '<div class="calendar_wrap" />' );
            $resultWrap.append( $result );
            $resultWrap.append( $date );
            $selector.append( $resultWrap );
            $selector.append( $calWrap );
            $el.append( $selector );

            var corrDate;

            if( type == "month" ) {         // 월간
                corrDate = new Date( date.split( "-" )[ 0 ], parseInt( date.split( "-" )[ 1 ] ) - 1, getDate( date.split( "-" )[ 0 ] + "-" + date.split( "-" )[ 1 ] ) ).dateToStr();
                if( corrDate != date ) {
                    date = corrDate;
                    $options.dateChange( date );
                }
                sel1_opts = getOpts_year();
            } else if( type == "quarter" ) { // 분기
                corrDate = getQuarter( date );
                if( corrDate != date ) {
                    date = corrDate;
                    $options.dateChange( date );
                }
                sel1_opts = getOpts_quarter();
            } else if( type == "half" ) {   // 반기
                corrDate = getHalf( date );
                if( corrDate != date ) {
                    date = corrDate;
                    $options.dateChange( date );
                }
                sel1_opts = getOpts_half();
            } else if( type == "year" ) {  // 연간
                corrDate = getYear( date );
                if( corrDate != date ) {
                    date = corrDate;
                    $options.dateChange( date );
                }
                sel1_opts = getOpts_year();
            }

            drawSelector_1();
            if( type == "month" ) {
                sel2_opts = getOpts_month();
                drawSelector_2();
            }

			rendered();
        }
        
        function drawSelector_1(){
            $sel01 = $( '<div class="dcp is-small" />' );
            var select = $( '<select id="' + ( id + '_01' ) + '" />' );
            var label = $( '<label for="' + ( id + '_01' ) + '" />' );

            sel1_opts.filter( function( $item ){
                var opt = $( '<option value="' + $item.code + '">' + $item.name + '</option>' );
                if( $item.selected ) opt.attr( "selected", true );
                select.append( opt );
            });

            $sel01.append( select );
            $sel01.append( label );
            $calWrap.append( $sel01 );

            select.change( evt_select_1_change );

            ui_reset( $el );
        }
        function drawSelector_2(){
            var select;
            if( !$sel02 ) {
                $sel02 = $( '<div class="dcp is-small" />' );
                select = $( '<select id="' + ( id + '_02' ) + '" />' );
                var label = $( '<label for="' + ( id + '_02' ) + '" />' );
                select.change( evt_select_2_change );
                $sel02.append( select );
                $sel02.append( label );
                $calWrap.append( $sel02 );
                ui_reset( $el );
            } else {
                select = $sel02.find( "select" );
                select.html( "" );
            }

            sel2_opts.filter( function( $item, $idx ){
                var opt = $( '<option value="' + $item.code + '">' + $item.name + '</option>' );
                if( ( date.split( "-" )[ 1 ] - 1 ) == $item.code ) opt.attr( "selected", true );
                select.append( opt );
            });
        }

        function rendered(){
            if( $date && date ) {
                if( !$options.onlyOne ) $date.text( date );
                else {
                    var fd = new Date( date.dateReplaceIE() + " 00:00:00" );
                    var ld = new Date( date.dateReplaceIE() + " 00:00:00" );

                    if( type == "month" ) {
                        ld.setMonth( ld.getMonth() + 1 );
                        ld.setDate( 0 );
                    } else if( type == "quarter" ) {
                        ld.setMonth( ld.getMonth() + 3 );
                        ld.setDate( 0 );
                    } else if( type == "half" ) {
                        ld.setMonth( ld.getMonth() + 6 );
                        ld.setDate( 0 );
                    } else if( type == "year" ) {
                        ld.setFullYear( ld.getFullYear() + 1 );
                        ld.setDate( 0 );
                    }
                    $date.text( fd.dateToStr() + " ~ " + ld.dateToStr() );
                }
            }
            if( $result && date ) $result.text( function(){
                if( type == "month" ) return date.split( "-" )[ 0 ] + "년 " + parseInt( date.split( "-" )[ 1 ] ) + "월";
                else if( type == "quarter" ) {
                    var tmpDate = new Date( date );
                    var result = (function(){
                        if( tmpDate.getMonth() >= 0 && tmpDate.getMonth() < 3 ) return "1분기";
                        else if( tmpDate.getMonth() >= 3 && tmpDate.getMonth() < 6 ) return "2분기";
                        else if( tmpDate.getMonth() >= 6 && tmpDate.getMonth() < 9 ) return "3분기";
                        return "4분기";
                    })();
                    return tmpDate.getFullYear() + "년 " + result;
                } else if( type == "half" ) {
                    var tmpDate = new Date( date );
                    var result = (function(){
                        if( tmpDate.getMonth() >= 0 && tmpDate.getMonth() < 6 ) return "상반기";
                        return "하반기";
                    })();
                    return tmpDate.getFullYear() + "년 " + result;
                } else if( type == "year" ) {
                    var tmpDate = new Date( date );
                    return tmpDate.getFullYear() + "년";
                }
                return "";
            })
        }



        // **********		Hndler		************************************************************************************************ //
        // 연도 옵션 목록
        function getOpts_year(){
            var initDate = new Date( date );
            var tmpDate1 = new Date( firstDate );
            var tmpDate2 = new Date( curDate );

            var result = [];
            do {
                result.push({
                    code: tmpDate1.getFullYear(),
                    name: tmpDate1.getFullYear() + "년",
                    selected: initDate.getFullYear() == tmpDate1.getFullYear() ? true : false
                })
                tmpDate1.setFullYear( tmpDate1.getFullYear() + 1 );
            } while ( tmpDate1 <= tmpDate2 );
            return result;
        }
        // 월별 옵션 목록(년도에 영향 받음)
        function getOpts_month(){

            var tmpDate1 = new Date( date.split( "-" )[ 0 ] + "/01/01 00:00:00" );
            var tmpDate2 = new Date( date.split( "-" )[ 0 ] + "/12/31 00:00:00" );
            var tmpFdate = new Date( firstDate );
            var tmpLdate = new Date( curDate );
            tmpFdate.setDate( 1 );
            tmpLdate.setDate( 1 );
            var result = [];
            do {
                if( tmpDate1 >= tmpFdate && tmpDate1 <= tmpLdate ) {
                    result.push({
                        code: tmpDate1.getMonth(),
                        name: parseInt( tmpDate1.getMonth() ) + 1 + "월"
                    })
                }
                tmpDate1.setMonth( tmpDate1.getMonth() + 1 );
            } while ( tmpDate1 <= tmpDate2 );
            return result;
        }
        // 분기 옵션 목록
        function getOpts_quarter(){
            var tmpDate1 = new Date( getQuarter( firstDate.dateToStr() ).dateReplaceIE() + " 00:00:00" );
            tmpDate1.setDate( 1 );
            var tmpDate2 = new Date( curDate );
            if( tmpDate2.getMonth() < 3 ) tmpDate2.setMonth( 2 );
            else if( tmpDate2.getMonth() >= 3 && tmpDate2.getMonth() < 6 ) tmpDate2.setMonth( 5 );
            else if( tmpDate2.getMonth() >= 6 && tmpDate2.getMonth() < 9 ) tmpDate2.setMonth( 8 );
            else tmpDate2.setMonth( 11 );
            var result = [];
            do {
                result.push({
                    code: tmpDate1.getFullYear() + "-" + parseInt( tmpDate1.getMonth() + 1 ).numToStr_addZero(),
                    name: tmpDate1.getFullYear() + "년 " + Math.floor( ( tmpDate1.getMonth() + 3 ) / 3 ) + "분기",
                    selected: tmpDate1.getFullYear() + "-" + parseInt( tmpDate1.getMonth() + 1 ).numToStr_addZero() == date.substr( 0, 7 ) ? true : false
                })
                tmpDate1.setMonth( tmpDate1.getMonth() + 3 );
            } while ( tmpDate1 <= tmpDate2 );
            return result;
        }
        // 반기 옵션 목록
        function getOpts_half(){
            var tmpDate1 = new Date( getHalf( firstDate.dateToStr() ).dateReplaceIE() + " 00:00:00" );
            tmpDate1.setDate(1);
            var tmpDate2 = new Date( getHalf( curDate.dateToStr() ).dateReplaceIE() + " 00:00:00" );
            if( tmpDate2.getMonth() < 6 ) tmpDate2.setMonth( 5 );
            else tmpDate2.setMonth( 11 );
            var result = [];
            do {
                result.push({
                    code: tmpDate1.getFullYear() + "-" + parseInt( tmpDate1.getMonth() + 1 ).numToStr_addZero(),
                    name: tmpDate1.getFullYear() + "년 " + ( Math.floor( ( tmpDate1.getMonth() + 6 ) / 6 ) > 1 ? "하" : "상" ) + "반기",
                    selected: tmpDate1.dateToStr().substr( 0, 7 ) == date.substr( 0, 7 ) ? true : false
                })
                tmpDate1.setMonth( tmpDate1.getMonth() + 6 );
            } while ( tmpDate1 <= tmpDate2 );
            return result;
        }

        // 날짜 계산
        function getDate( $year_month ){
            var tmpDate = new Date( $year_month.split( "-" )[ 0 ], parseInt( $year_month.split( "-" )[ 1 ] ), 0, 00, 00, 00 );
            return day == "first" ? "01" : parseInt( tmpDate.getDate() ).numToStr_addZero();
        }
        // 분기 계산
        function getQuarter( $date ) {
            var tmpDate = new Date( $date.split( "-" )[ 0 ], $date.split( "-" )[ 1 ] - 1, 1, 0, 0, 0 );
            if( tmpDate.getMonth() >= 0 && tmpDate.getMonth() < 3 ) tmpDate.setMonth( 0 );
            else if( tmpDate.getMonth() >= 3 && tmpDate.getMonth() < 6 ) tmpDate.setMonth( 3 );
            else if( tmpDate.getMonth() >= 6 && tmpDate.getMonth() < 9 ) tmpDate.setMonth( 6 );
            else tmpDate.setMonth( 9 );
            if( day == "first" ) tmpDate.setDate( 1 );
            else {
                tmpDate.setMonth( tmpDate.getMonth() + 3 );
                tmpDate.setDate( tmpDate.getDate() - 1 );
            }
            return tmpDate.dateToStr();
        }
        // 반기 계산
        function getHalf( $date ){
            var tmpDate = new Date( $date.split( "-" )[ 0 ], $date.split( "-" )[ 1 ] - 1, 1, 0, 0, 0 );
            if( tmpDate.getMonth() >= 0 && tmpDate.getMonth() < 6 ) tmpDate.setMonth( 0 );
            else tmpDate.setMonth( 6 );
            if( day == "first" ) tmpDate.setDate( 1 );
            else {
                tmpDate.setMonth( tmpDate.getMonth() + 6 );
                tmpDate.setDate( tmpDate.getDate() - 1 );
            }
            return tmpDate.dateToStr();
        }
        // 년 계산
        function getYear( $date ){
            var tmpDate = new Date( $date.substr( 0, 4 ), 0, 1 );
            if( day == "last" ) {
                tmpDate.setMonth( 11 );
                tmpDate.setDate( 31 );
            }
            return tmpDate.dateToStr();
        }





        // **********		Event		************************************************************************************************ //
        // 셀렉터 1 체인지 이벤트
        function evt_select_1_change( $e ){
            var tmpDate;
            if( type == "month" ) {
                tmpDate = $( $e.target ).val() + "-" + date.split( "-" )[ 1 ] + "-" + date.split( "-" )[ 2 ];
            } else if( type == "quarter" || type == "half" ) {
                if( !$( $e.target ).val() ) {
                    var tmpDate1 = new Date( date );
                    if( curDate < tmpDate1 ) $( $e.target ).val( $( $e.target ).find( "option:last-child" ).eq( 0 ).val() );
                    else $( $e.target ).val( $( $e.target ).find( "option" ).eq( 0 ).val() );
                }
                tmpDate = $( $e.target ).val() + "-" + getDate( $( $e.target ).val() );
            } else if( type == "year" ) {
                if( !$( $e.target ).val() ) {
                    var tmpDate1 = new Date( date );
                    if( curDate < tmpDate1 ) $( $e.target ).val( $( $e.target ).find( "option:last-child" ).eq( 0 ).val() );
                    else $( $e.target ).val( $( $e.target ).find( "option" ).eq( 0 ).val() );
                }
                tmpDate = $( $e.target ).val() + ( day == "first" ? "-01-01" : "-12-31" );
            }

            date = tmpDate;

            if( type == "month" ) {
                sel2_opts = getOpts_month();
                date = date.split( "-" )[ 0 ] + "-" + ( sel2_opts[ 0 ].code + 1 ).numToStr_addZero() + "-" + getDate( date.split( "-" )[ 0 ] + "-" + ( sel2_opts[ 0 ].code + 1 ).numToStr_addZero() );
                $options.dateChange( date );
                drawSelector_2();
            } else {
                $options.dateChange( date );
            }

            rendered();
        }

        // 셀렉터 2 체인지 이벤트
        function evt_select_2_change( $e ){
            if( !$( $e.target ).val() ) {
                var tmpDate1 = new Date( date );
                if( curDate < tmpDate1 ) $( $e.target ).val( $( $e.target ).find( "option:last-child" ).eq( 0 ).val() );
                else $( $e.target ).val( $( $e.target ).find( "option" ).eq( 0 ).val() );
            }
            var tmpDate;
            tmpDate = date.split( "-" )[ 0 ] + "-" + ( parseInt( $( $e.target ).val() ) + 1 ).numToStr_addZero() + "-" + getDate( date.split( "-" )[ 0 ] + "-" + ( parseInt( $( $e.target ).val() ) + 1 ).numToStr_addZero() );
            date = tmpDate;
            $options.dateChange( date );
            rendered();
        }




		// **********		Out Method		************************************************************************************************ //

		// Get Date ( return : YYYY-MM-DD )
		// this.getDate = function(){
		// 	var result = calWrap.datepicker().val();
		// 	//if( time ) result = result + " " + parseInt( calWrap_timewrap_time_00.find( "select" ).val() ).numToStr_addZero() + ":" + parseInt( calWrap_timewrap_time_01.find( "select" ).val() ).numToStr_addZero(); 
		// 	return result;
		// }

		// Set Date ( YYYY-MM-DD )
		this.setDate = function( $date ){
            if( $date != date ) {
                if( type == "month" ) {
                    var tmpDate = $date.split( "-" )[ 0 ] + "-" + $date.split( "-" )[ 1 ] + "-" + getDate( $date.split( "-" )[ 0 ] + "-" + $date.split( "-" )[ 1 ] );
                    date = tmpDate;
                    $options.dateChange( date );
                    if( $sel01.find( "select" ).val() != date.split( "-" )[ 0 ] ) {
                        $sel01.find( "select" ).attr( "value", date.split( "-" )[ 0 ] );
                    }
                    if( $sel02 ) $sel02.find( "select" ).attr( "value", parseInt( date.split( "-" )[ 1 ] ) - 1 );
                } else if( type == "quarter" || type == "half" ) {
                    var tmpDate = ( type == "quarter" ) ? getQuarter( $date ) : getHalf( $date );
                    date = tmpDate;
                    $options.dateChange( date );
                    if( $sel01.find( "select" ).val() != date.substr( 0, 7 ) ) {
                        $sel01.find( "select" ).attr( "value", date.substr( 0, 7 ) );
                    }
                } else if( type == "year" ) {
                    var tmpDate = getYear( $date );
                    date = tmpDate;
                    $options.dateChange( date );
                    if( $sel01.find( "select" ).val() != date.substr( 0, 4 ) ) {
                        $sel01.find( "select" ).attr( "value", date.substr( 0, 4 ) );
                    }
                }
            }
            rendered();
		}
        
        this.destroyed = function() {
            if( $el ) $el.removeChild();
        }
	};

	$.fn.c_datepicker_selectors = function ( $options ) {
		return this.each(function() {
			var dps = new dpsClass( $( this ), $options );
			$.data( this, 'c_datepicker_selectors', dps );
			$( document ).ready( dps.init );
		});
	}
})(jQuery);
