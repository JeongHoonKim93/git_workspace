/*!
 *
 * @author: GUNI, h2dlhs@realsn.com
 **/
 
 
(function ($){

	var acClass = function( $el, $options ){

        // var OPTS = JSON.parse( JSON.stringify( $el.data( "opts" ) ) );
        var id;
        var firstDay = 0;
        var lastDay;
        var curDate = {};
        var toDay = new Date();
        var curType;
        var typeActive = false;
        var calActive = false;
        var calOneOnly = false;
        var evtTimer;

        var weekChangeTimeChk = false;
        
        var $grp, $grpItems;
        var $inputs, $calType, $calTypeResult, $calTypeList, $input;
        var $calendars, $firstCal, $lastCal, $firstSel, $lastSel;
        var $move_prev, $move_next, moveChk;


        this.init = function (){
            // 이미 생성되었으면 캔슬
            if( $el.children().length ) return false;

            // Observer
            new MutationObserver( function( $e ) {
                $e.filter( function( $event ){
                    if( $event.attributeName == "data-type" ) hndl_typeChange();
                    if( $event.attributeName == "data-date" ) {
                        hndl_calendarChange();
                        
                        // 22-03-14 고기범 추가 검색조건의 검색기간 및 비교기간 변경 이벤트 호출
                        if($event.target.id == "ts_dp_01" || ($event.target.id == "ts_dp_02" && $("#"+$event.target.id).hasClass("is-active"))){
                			if( $("#"+$event.target.id).attr( "data-changeEvent" ) ){
                                eval( $("#"+$event.target.id).attr( "data-changeEvent" ) )({
                                	type: $event.target.id
                                });
                            }
                        }
                        // 외부 이벤트 함수 호출
                        // 여러번 실행 방지를 위해 타임아웃 사용
                        if( $el.attr( "data-change" ) ){
                            clearTimeout( evtTimer );
                            evtTimer = setTimeout( function(){
                                eval( $el.attr( "data-change" ) + "(" + $el.attr( "data-date" ) + ")" );
                            }, 50 );
                        }
                    }
                })
            }).observe( $el[ 0 ], { attributes: true });
            // }).observe( tg[ 0 ], { attributes: true, childList: true, characterData: true, subtree: true, attributeOldValue: true, characterDataOldValue: true });

            id = $el.attr( "id" );
            if( !id ) {
                console.log( "*** Datepidcker Build Err  :  ID not defined."  );
                return false;
            }

            // 시작 요일 셋팅
            firstDay = $el.attr( "data-firstday" ) ? $el.attr( "data-firstday" ) : 0;
            lastDay = function(){
                var result = firstDay;
                for( var Loop1 = 0 ; Loop1 < 6 ; ++Loop1 ){
                    result++;
                    if( result > 6 ) result = 0;
                }
                return result;
            }();

            // 날짜 셋팅
            curDate = JSON.parse( JSON.stringify( $el.data( "date" ) ) );

            // 달력 타입 설정
            curType = $el[ 0 ].getAttribute( "data-type" ) ? (function(){
                return getTypeOpts.filter( function( $item ){
                    return $item.code == $el[ 0 ].getAttribute( "data-type" );
                })[ 0 ];
            })() : getTypeOpts[ 0 ];

            // 1개 달력인지 체크
            calOneOnly = $el.attr( "data-oneCalendar" ) ? true : false;
            // 1개 달력인 경우에 초기날짜 재설정(첫날짜 기준으로 설정)
            if( calOneOnly ) {
                var ld = new Date( curDate.sDate.dateReplaceIE() );
                
                if( curType.code == "month" ) {
                    ld.setMonth( ld.getMonth() + 1 );
                    ld.setDate( ld.getDate() - 1 );
                } else if( curType.code == "quarter" ) {
                    ld.setMonth( ld.getMonth() + 3 );
                    ld.setDate( ld.getDate() - 1 );
                } else if( curType.code == "half" ) {
                    ld.setMonth( ld.getMonth() + 6 );
                    ld.setDate( ld.getDate() - 1 );
                } else if( curType.code == "year" ) {
                    ld.setFullYear( ld.getFullYear() + 1 );
                    ld.setDate( ld.getDate() - 1 );
                }
                curDate.eDate = ld.dateToStr();
                $el[ 0 ].setAttribute( "data-date", JSON.stringify( curDate ) );
            }

            build();

            // console.log( "*** Datepickers ********************************");
            // console.log( "id ::  ", id );
            // console.log( "curDate ::  ", curDate );
            // console.log( "curType ::  ", curType );
            // console.log( "************************************************");

            // Init();
            hndl_typeChange();
            // hndl_calendarChange();

            // Scroll Event
            $( document ).scroll( function(){
                typeActive = false;
                calActive = false;
                $calType.removeClass( "is-active" );
                $input.removeClass( "is-active" );
                $calendars.removeClass( "is-active" );
                hndl_active();
            })

            // Resize Event
            $( window ).resize( function(){
                typeActive = false;
                calActive = false;
                $calType.removeClass( "is-active" );
                $input.removeClass( "is-active" );
                $calendars.removeClass( "is-active" );
                hndl_active();
            })
		}

        // **********		Build		************************************************************************************************ //
        function build(){
            // 날짜 퀵 선택 그룹
            if( $el.attr( "data-grps" ) ) {
                $grp = $( '<div class="grps dcp_grp is-radio is-btn" />' );
                if( $el.hasClass( "is-small") ) $grp.addClass( "is-small" );
                var ul = $( '<ul />' );
                JSON.parse( $el.attr( "data-grps" ) ).filter( function( $item, $idx ){
                    var li = $( '<li />' );
                    var item = $( '<div class="dcp" data-date='
                        + (function(){
                            var tmpDate = new Date( toDay.dateToStr().dateReplaceIE() + " 00:00:00" );
                            var result = {
                                sDate: null,
                                eDate: toDay.dateToStr()
                            }
                            if( $el.attr( "data-lastday" ) && ( $item.indexOf( "YA" ) >= 0 || $item.indexOf( "TH" ) >= 0 || ( /^\d+$/.test( $item ) ) ) ) {
                                tmpDate.setDate( tmpDate.getDate() + parseInt( $el.attr( "data-lastday" ) ) );
                                result.eDate = tmpDate.dateToStr();
                            }
                            if( $item.indexOf( "YR" ) >= 0 ) {
                                tmpDate.setFullYear( parseInt( $item.split( "YR" )[ 0 ] ) );
                                tmpDate.setMonth( 0 );
                                tmpDate.setDate( 1 );
                                var tmpDate2 = new Date( tmpDate );
                                tmpDate2.setMonth( 11 );
                                tmpDate2.setDate( 31 );
                                result.eDate = tmpDate2.dateToStr();
                            } else if( $item.indexOf( "YA" ) >= 0 ) {
                                tmpDate.setFullYear( tmpDate.getFullYear() - parseInt( $item.split( "YA" )[ 0 ] ) );
                            } else if( $item.indexOf( "MT" ) >= 0 ) {
                                tmpDate.setDate( 1 );
                                tmpDate.setMonth( parseInt( $item.split( "MT" )[ 0 ] ) - 1 );
                                var tmpDate2 = new Date( tmpDate );
                                tmpDate2.setMonth( tmpDate2.getMonth() + 1 );
                                tmpDate2.setDate( tmpDate2.getDate() - 1 );

                                var tmpToday = new Date( toDay.dateToStr().dateReplaceIE() + " 00:00:00" );
                                tmpToday.setMonth( tmpToday.getMonth() + 2 );

                                if( tmpDate.getTime() > tmpToday.getTime() ) {
                                    tmpDate.setFullYear( tmpDate.getFullYear() - 1 );
                                    tmpDate2.setFullYear( tmpDate2.getFullYear() - 1 );
                                }
                                result.eDate = tmpDate2.dateToStr();
                            } else if( $item.indexOf( "TH" ) >= 0 ) {
                                tmpDate.setMonth( tmpDate.getMonth() - parseInt( $item.split( "TH" )[ 0 ] ) );
                                if( curType.code == "month" ) {
                                    var tmpDate2 = new Date( toDay.dateToStr() + " 00:00:00" );
                                    tmpDate2.setMonth( tmpDate2.getMonth() + 1 );
                                    tmpDate2.setDate( 1 );
                                    tmpDate2.setDate( tmpDate2.getDate() - 1 );
                                    result.eDate = tmpDate2.dateToStr();
                                    tmpDate = new Date( tmpDate2 );
                                    tmpDate.setMonth( tmpDate.getMonth() - parseInt( $item.split( "TH" )[ 0 ] ) + 1 );
                                    tmpDate.setDate( 1 );
                                }
                            } else if( $item.indexOf( "QT" ) >= 0 ) {
                            } else if( $item.indexOf( "HF" ) >= 0 ) {
                            } else {
                                tmpDate.setDate( tmpDate.getDate() - ( parseInt( $item ) >= 1 ? parseInt( $item ) - 1 : 0 ) );
                            }
                            result.sDate = tmpDate.dateToStr();

                            return JSON.stringify( result );
                        })()
                        + '><input class="is-btn" type="radio" id="' + ( id + '_' + $idx ) + '" name="' + ( id + '_dategrp' ) + '"><label for="' + ( id + '_' + $idx ) + '"><span class="txt">' + getGrpName( $item ) + '</span></label></div>' );
                    $grp.append( item );
                })
                $el.append( $grp );

                $grpItems = $grp.find( ".dcp" );
                $grpItems.change( evt_grpChange );
            }

            // Inputs Dom
            $inputs = $( '<div class="inputs" />' );
            $calType = $( "<div class='cal_type' />" );
                $calTypeResult = $( "<button type='button' class='btn_type'></button>" );
                $calType.append( $calTypeResult );
                $calTypeList = $( "<ul class='list' />" );
                    getTypeOpts.filter( function( $item ){
                        $calTypeList.append( "<li><button type='button' data-code='" + $item.code + "'><span>" + $item.name + "</span></button></li>" );
                    })
                    // $calTypeList += "</ul>";
                $calType.append( $calTypeList );
                $calTypeResult.click( evt_typeClick );
                $calType.find( ".list button" ).click( evt_type_listClick );
            $input = $( "<button type='button' class='result' />" );
            $inputs.append( $calType );
            $inputs.append( $input );
            $el.prepend( $inputs );
            
            $input.click( evt_inputClick );
            $( document ).click( evt_cal_docClick );

            // Moves
            if( $el.attr( "data-move" ) ){
                $inputs.addClass( "is-move" );
                $move_prev = $( '<button class="btn_prev">&#xe001;</button>' );
                $move_next = $( '<button class="btn_next">&#xe000;</button>' );
                $inputs.prepend( $move_prev );
                $inputs.append( $move_next );
                $move_prev.click( evt_prevClick );
                $move_next.click( evt_nextClick );
            }
        }

        // 타입에 따른 달력 드로우
        function drawCalendar(){
            if( curType.code == "day" || curType.code == "week" ) $calendars = drawCalendar_calendar();
            else $calendars = drawCalendar_selector();
            
            if( $el.attr( "data-type-opts" ) == "disabled" ) $el.addClass( "is-not-type" );
        }
        // 달력 생성
        function drawCalendar_calendar(){

            //데이터 타입이 일/주 일경우 생성하는 달력
            
            // 기존 달력 제거
            if( $el.find( ".calendars" ).length ) {
                if( $firstCal ) {
                    if( $firstCal.data( "c_datepicker" ) ) $firstCal.data( "c_datepicker" ).destroyed();
                    $firstCal = null;
                }
                if( $lastCal ) {
                    if( $lastCal.data( "c_datepicker" ) ) $lastCal.data( "c_datepicker" ).destroyed();
                    $lastCal = null;
                }
                $el.find( ".calendars" ).remove();
            }

            // 시작 달력(달력 한개일 때)
            var calendar = $( "<div class='calendars' />" );
            $firstCal = $( "<div class='dp' />" );
            $firstCal.c_datepicker({
                onlyOne: calOneOnly,
                firstDate: curDate.minDate,
                lastDate: curDate.maxDate,
                firstDay: firstDay,
                initDate: curDate.sDate,
                type: curType.code,
                dateChange: evt_dateChange_first,
                updateCalendar: curDate.eDate ? evt_updateCalendar : null
            });
            calendar.append( $firstCal );

            // 끝 달력
            if( !calOneOnly ) {
                $lastCal = $( "<div class='dp' />" );
                $lastCal.c_datepicker({
                    firstDate: curDate.minDate,
                    lastDate: curDate.maxDate,
                    firstDay: firstDay,
                    initDate: curDate.eDate,
                    type: curType.code,
                    dateChange: evt_dateChange_last,
                    updateCalendar: curDate.eDate ? evt_updateCalendar : null
                });
                calendar.append( $lastCal );
            }

            $el.append( calendar );
            return calendar;
        }
        // 셀렉터 생성
        function drawCalendar_selector(){
            // 기존 셀렉터 제거
            if( $el.find( ".calendars" ).length ) {
                if( $firstSel ) {
                    if( $firstSel.data( "c_datepicker" ) ) $firstSel.data( "c_datepicker" ).destroyed();
                    $firstSel = null;
                }
                if( $lastSel ) {
                    if( $lastSel.data( "c_datepicker" ) ) $lastSel.data( "c_datepicker" ).destroyed();
                    $lastSel = null;
                }
                $el.find( ".calendars" ).remove();
            }

            var calendar = $( "<div class='calendars' />" );

            // 시작 셀렉터
            $firstSel = $( "<div class='dp' />" );
            $firstSel.c_datepicker_selectors({
                id: id + "_first",
                onlyOne: calOneOnly,
                firstDate: curDate.minDate,
                lastDate: curDate.maxDate,
                initDate: curDate.sDate,
                type: curType.code,
                day: "first",
                dateChange: evt_dateChange_first
            });
            calendar.append( $firstSel );

            // 끝 셀렉터
            if( !calOneOnly ) {
                $lastSel = $( "<div class='dp' />" );
                $lastSel.c_datepicker_selectors({
                    id: id + "_last",
                    firstDate: curDate.minDate,
                    lastDate: curDate.maxDate,
                    initDate: curDate.eDate,
                    type: curType.code,
                    day: "last",
                    dateChange: evt_dateChange_last
                });
                calendar.append( $lastSel );
            }

            $el.append( calendar );
            return calendar;
        }

        // 타입 설정
        var getTypeOpts = (function (){
            var typeOpts = [
                { code: "day",     name: "일" },
                { code: "week",    name: "주" },
                { code: "month",   name: "월" },
                { code: "quarter", name: "분기" },
                { code: "half",    name: "반기" },
                { code: "year",    name: "년" }
            ]
            var result = typeOpts;

            if( $el.attr( "data-type-opts" ) && $el.attr( "data-type-opts" ) != "disabled" ) {
                var tmp = JSON.parse( $el.attr( "data-type-opts" ) );
                result = [];
                tmp.filter( function( $item ){
                    for( var Loop1 = 0 ; Loop1 < typeOpts.length ; ++Loop1 ) {
                        if( $item.trim() == typeOpts[ Loop1 ].code ) result.push( typeOpts[ Loop1 ] );
                    }
                })
            }
            return result;
        })();

        // 그룹 이름
        function getGrpName( $name ) {
            if( $name.indexOf( "YR" ) >= 0 ) {
                return $name.split( "YR" )[ 0 ] + "년";
            } else if( $name.indexOf( "YA" ) >= 0 ) {
                return $name.split( "YA" )[ 0 ] + "년";
            } else if( $name.indexOf( "MT" ) >= 0 ) {
                return $name.split( "MT" )[ 0 ] + "월";
            } else if( $name.indexOf( "TH" ) >= 0 ) {
                return $name.split( "TH" )[ 0 ] + "개월";
            } else if( $name.indexOf( "QT" ) >= 0 ) {
                return $name.split( "QT" )[ 0 ] + "분기";
            } else if( $name.indexOf( "HF" ) >= 0 ) {
                return $name.split( "HF" )[ 0 ] + "반기";
            }
            return $name == 0 ? "오늘" : $name + "일";
        };




        // **********		Hndler		************************************************************************************************ //
        // 컴포넌트 활성화
        function hndl_active(){
            $calendars.css( "min-width", curType.code == "day" || curType.code == "week" ? "none" : $inputs.outerWidth() );

            setRePos();
            if( typeActive || calActive ) $el.addClass( "is-active" );
            else $el.removeClass( "is-active" );
        }
        function setRePos(){
            var pos = {};
            pos.top = $inputs.offset().top - $( document ).scrollTop() + $inputs.outerHeight();
            pos.left = $inputs.offset().left - $( document ).scrollLeft();

            if( pos.top + $calendars.outerHeight() > $( window ).height() ) pos.top -= $calendars.outerHeight() + $inputs.outerHeight() + 2;
            if( pos.left + $calendars.outerWidth() > $( window ).width() ) pos.left -= $calendars.outerWidth() - $inputs.outerWidth();

            if( pos.top < 0 ) pos.top = $inputs.offset().top - $( document ).scrollTop() + $inputs.outerHeight();

            $calendars.css( { top: pos.top, left: pos.left } )
        }

        // 타입 목록 On/Off
        function hndl_type(){
            var pos = $calTypeResult.offset();
            pos.top = pos.top - $( document ).scrollTop() + $calTypeResult.outerHeight();
            pos.left = pos.left - $( document ).scrollLeft();
            $calTypeList.css( pos );

            if( typeActive ) {
                $calType.addClass( "is-active" );
                calActive = false;
                hndl_calendar();
            } else {
                $calType.removeClass( "is-active" );
            }
            hndl_active();
        }

        // 타입 바뀔 때
        function hndl_typeChange(){
            curType = (function(){
                return getTypeOpts.filter( function( $item ){
                    return $item.code == ( $el[ 0 ].getAttribute( "data-type" ) || getTypeOpts[ 0 ].code );
                })[ 0 ];
            })();
            $calTypeResult.text( curType.name );
            
            $calType.find( ".list button" ).each( function(){
                if( this.getAttribute( "data-code" ) == curType.code ) $( this ).addClass( "is-active" );
                else $( this ).removeClass( "is-active" );
            });
            
            $el.removeClass( "is-day is-week is-month is-quarter is-half is-year" );
            $el.addClass( "is-" + curType.code );
            
            curDate = JSON.parse( $el[ 0 ].getAttribute( "data-date" ) );
            var fd = new Date( curDate.sDate.dateReplaceIE() + " 00:00:00" );
            var ld = new Date( curDate.eDate.dateReplaceIE() + " 00:00:00" );

            if( calOneOnly ) {
                ld = new Date( fd );
                if( curType.code == "month" ) {
                    ld.setMonth( ld.getMonth() + 1 );
                    ld.setDate( 0 );
                } else if( curType.code == "quarter" ) {
                    ld.setMonth( ld.getMonth() + 3 );
                    ld.setDate( 0 );
                } else if( curType.code == "half" ) {
                    ld.setMonth( ld.getMonth() + 6 );
                    ld.setDate( 0 );
                } else if( curType.code == "year" ) {
                    ld.setMonth( 12 );
                    ld.setDate( 0 );
                }
                curDate.eDate = ld.dateToStr();
            }

            drawCalendar();
            $el[ 0 ].setAttribute( "data-date", JSON.stringify( curDate ) );
        }

        // 달력 On/Off
        function hndl_calendar(){
            if( calActive ) {
                $input.addClass( "is-active" );
                $calendars.addClass( "is-active" );
                typeActive = false;
                hndl_type();
                // hndl_calendarChange_range();
            } else {
                $input.removeClass( "is-active" );
                $calendars.removeClass( "is-active" );
            }
            hndl_active();
        }

        // 날짜 바뀔 때
        function hndl_calendarChange(){

            curDate = JSON.parse( $el[ 0 ].getAttribute( "data-date" ) );
            var fd = new Date( curDate.sDate.dateReplaceIE() + " 00:00:00" );
            var ld = new Date( curDate.eDate.dateReplaceIE() + " 00:00:00" );

            if( $firstCal && $firstCal.data( "c_datepicker" ) ) $firstCal.data( "c_datepicker" ).setDate( fd.dateToStr() );
            if( $lastCal && curDate.eDate && $lastCal.data( "c_datepicker" ) ) $lastCal.data( "c_datepicker" ).setDate( ld.dateToStr() );

            if( $firstSel && $firstSel.data( "c_datepicker_selectors" ) ) $firstSel.data( "c_datepicker_selectors" ).setDate( fd.dateToStr() );
            if( $lastSel && curDate.eDate && $lastSel.data( "c_datepicker_selectors" ) ) {
                $lastSel.data( "c_datepicker_selectors" ).setDate( ld.dateToStr() );
            }

            if( calOneOnly ) {

                if( curType.code == "day" ) {
                    $input.text( fd.dateToStr() );
                } else if( curType.code == "week" ) {
                    $input.text( new Date( fd ).getFullYear() + "년 " + fd.dateToStr().getWeekDay() + "주차" );
                } else if( curType.code == "month" ) {
                    $input.text( new Date( fd ).getFullYear() + "년 " + ( new Date( fd ).getMonth() + 1 ) + "월" );
                } else if( curType.code == "quarter" ) {
                    var tmpDate = new Date( fd );
                    var result = (function(){
                        if( tmpDate.getMonth() >= 0 && tmpDate.getMonth() < 3 ) return "1분기";
                        else if( tmpDate.getMonth() >= 3 && tmpDate.getMonth() < 6 ) return "2분기";
                        else if( tmpDate.getMonth() >= 6 && tmpDate.getMonth() < 9 ) return "3분기";
                        return "4분기";
                    })();
                    $input.text( new Date( fd ).getFullYear() + "년 " + result );
                } else if( curType.code == "half" ) {
                    var tmpDate = new Date( fd );
                    var result = (function(){
                        if( tmpDate.getMonth() >= 0 && tmpDate.getMonth() < 6 ) return "상반기";
                        return "하반기";
                    })();
                    $input.text( new Date( fd ).getFullYear() + "년 " + result );
                } else {
                    $input.text( new Date( fd ).getFullYear() + "년" );
                }

                calActive = false;
                hndl_calendar();
            } else {
                $input.text( fd.dateToStr() + " ~ " + ld.dateToStr() );
            }

            if( ld && fd > ld ) ld = new Date( fd );

            // 주별 달력 일때 날짜 재설정

            if( ld && curType.code == "week" ) {
                var changeChk = false;
                var tmpDate = JSON.parse( JSON.stringify( curDate ) );

                if( weekChangeTimeChk ) {
                    setTimeout( function(){
                        weekChangeTimeChk = false;
                    }, 100 )
                    return;
                }

                // 시작날짜가 변경
                if( fd.getDay() != firstDay ) {
                    changeChk = true;
                    weekChangeTimeChk = true;
                    tmpDate.sDate = curDate.sDate.dateReplaceIE().getDayDate(firstDay);
                }

                // 종료날짜가 변경
                if( ld.getDay() != lastDay ) {
                    changeChk = true;
                    weekChangeTimeChk = true;
                    var tmpLD = new Date( curDate.eDate.dateReplaceIE().getDayDate(firstDay) );
                    tmpLD.setDate( tmpLD.getDate() + 6 );
                    tmpDate.eDate = tmpLD.dateToStr();
                }

                if( tmpDate && changeChk ) {
                    $el[ 0 ].setAttribute( "data-date", JSON.stringify( tmpDate ) );
                    return false;
                }
            }
            
            if( curDate.eDate ) hndl_calendarChange_range();

        }
        // 날짜(범위) 바뀔 때
        function hndl_calendarChange_range( $tg ){
            var fd = new Date( curDate.sDate.dateReplaceIE() + " 00:00:00" );
            var ld = new Date( curDate.eDate.dateReplaceIE() + " 00:00:00" );

            
            $el.find( '.calendar_wrap.hasDatepicker .ui-datepicker-calendar tbody > tr > *' ).removeClass( "is-range-first" );
            $el.find( '.calendar_wrap.hasDatepicker .ui-datepicker-calendar tbody > tr > *' ).each( function(){
                
                var dateStr = $( this ).attr( "data-year" ) + "/" + ( parseInt( $( this ).attr( "data-month" ) ) + 1 ) + "/" + $( this ).text();
                var itemDate = new Date( dateStr + " 00:00:00" );

                if( itemDate >= fd && itemDate <= ld ) $( this ).addClass( "is-range" );
                else $( this ).removeClass( "is-range" );
                if( itemDate.dateToStr() == fd.dateToStr() ) $( this ).addClass( "is-range-first" );
                else $( this ).removeClass( "is-range-first" );
                if( itemDate.dateToStr() == ld.dateToStr() ) $( this ).addClass( "is-range-last" );
                else $( this ).removeClass( "is-range-last" );
                if( curType.code == "week" && $( this ).hasClass( "ui-datepicker-week-col" ) && $( this ).next().hasClass( "is-range" ) ) {
                    $( this ).addClass( "is-range" );
                }
            })

            hndl_dateGrps();
        }

        // 날짜 그룹 변경
        function hndl_dateGrps(){
            if( $grpItems ) {
                $grpItems.each( function(){
                    if( $( this ).attr( "data-date" ) == JSON.stringify( curDate ) ) $( this ).find( "input" )[ 0 ].checked = true;
                    else $( this ).find( "input" )[ 0 ].checked = false;
                })
            }
        }


        // **********		Event		************************************************************************************************ //
        function evt_typeClick( $e ){
            typeActive = !typeActive;
            hndl_type();
            $( document ).unbind( "click", evt_type_docClick );
            if( typeActive ) $( document ).click( evt_type_docClick );
        }
        function evt_type_docClick( $e ){
            if(  $( $e.target ).closest( $inputs ).length == 0 ) {
                $( document ).unbind( "click", evt_type_docClick );
                typeActive = false;
                hndl_type();
                hndl_active();
            }
        }
        function evt_type_listClick( $e ){
            if( curType.code == $( this ).data( "code" ) ) return false;
            $el[ 0 ].setAttribute( "data-type", $( this ).data( "code" ) );
            typeActive = false;
            hndl_type();
        }

        function evt_inputClick( $e ){
            calActive = !calActive;
            hndl_calendar();
        }
        function evt_cal_docClick( $e ){
            if( $( $e.target ).closest( $inputs ).length == 0 && $( $e.target ).closest( $calendars ).length == 0 ) {
                calActive = false;
                hndl_calendar();
            };
        }

        // 달력에서 날짜 변경 시(Mutation 에서 호출)
        function evt_dateChange_first( $date ){
            curDate.sDate = $date;

            if( calOneOnly ) {
                var ld = new Date( curDate.sDate );

                if( curType.code == "month" ) {
                    ld.setMonth( ld.getMonth() + 1 );
                    ld.setDate( ld.getDate() - 1 );
                } else if( curType.code == "quarter" ) {
                    ld.setMonth( ld.getMonth() + 3 );
                    ld.setDate( ld.getDate() - 1 );
                } else if( curType.code == "half" ) {
                    ld.setMonth( ld.getMonth() + 6 );
                    ld.setDate( ld.getDate() - 1 );
                } else if( curType.code == "year" ) {
                    ld.setFullYear( ld.getFullYear() + 1 );
                    ld.setDate( ld.getDate() - 1 );
                }
                curDate.eDate = ld.dateToStr();
            }

            // 마지막 달력 날짜가 더 큰경우
            if( curDate.eDate ) {
                var fd = new Date( curDate.sDate );
                var ld = new Date( curDate.eDate );
                if( !moveChk && fd > ld ) ld = new Date( fd );
                curDate.eDate = ld.dateToStr();
            }

            $el[ 0 ].setAttribute( "data-date", JSON.stringify( curDate ) );

            setTimeout( function(){
                moveChk = false;
            }, 100 );
        }
        function evt_dateChange_last( $date ){
            curDate.eDate = $date;

            // 마지막 달력 날짜가 더 큰경우
            var fd = new Date( curDate.sDate );
            var ld = new Date( curDate.eDate );
            if( !moveChk && fd > ld ) {
                fd = new Date( ld );
                curDate.sDate = fd.dateToStr();
            }

            $el[ 0 ].setAttribute( "data-date", JSON.stringify( curDate ) );

            setTimeout( function(){
                moveChk = false;
            }, 100 );
        }
        // 달력 업데이트시(year/month 변경시 호출)
        function evt_updateCalendar( $year, $month, $e ){
            setTimeout( hndl_calendarChange_range, 0 );
        }

        // 그룹 날짜 변경 시
        function evt_grpChange(){
            if( curType.code != "day" ) {
                $el[ 0 ].setAttribute( "data-type", "day" );
            }
            $el[ 0 ].setAttribute( "data-date", $( this ).attr( "data-date" ) );
        }

        // 날짜 이동
        function evt_prevClick(){
            moveChk = true;

            var fd = new Date( curDate.sDate.dateReplaceIE() );
            var ld = new Date( curDate.eDate.dateReplaceIE() );

            if( curType.code == "week" ) {
                fd.setDate( fd.getDate() - 7 );
                ld.setDate( ld.getDate() - 7 );
            } else if( curType.code == "month" ) {
                fd.setMonth( fd.getMonth() - 1 );
                ld.setDate( 0 );
            } else if( curType.code == "quarter" ) {
                fd.setMonth( fd.getMonth() - 3 );
                ld.setMonth( ld.getMonth() - 3 );
                ld.setDate( 0 );
            } else if( curType.code == "half" ) {
                fd.setMonth( fd.getMonth() - 6 );
                ld.setMonth( ld.getMonth() - 6 );
                ld.setDate( 0 );
            } else if( curType.code == "year" ) {
                fd.setFullYear( fd.getFullYear() - 1 );
                ld.setFullYear( ld.getFullYear() - 1 );
            } else {
                fd.setDate( fd.getDate() - 1 );
                ld.setDate( ld.getDate() - 1 );
            }

            curDate.sDate = fd.dateToStr();
            curDate.eDate = ld.dateToStr();

            $el[ 0 ].setAttribute( "data-date", JSON.stringify( curDate ) );
        }
        function evt_nextClick(){
            moveChk = true;

            var fd = new Date( curDate.sDate.dateReplaceIE() + " 00:00:00" );
            var ld = new Date( curDate.eDate.dateReplaceIE() + " 00:00:00" );

            if( curType.code == "week" ) {
                fd.setDate( fd.getDate() + 7 );
                ld.setDate( ld.getDate() + 7 );
            } else if( curType.code == "month" ) {
                fd.setMonth( fd.getMonth() + 1 );
                ld.setDate(1);
                ld.setMonth( ld.getMonth() + 1 );
            } else if( curType.code == "quarter" ) {
                fd.setMonth( fd.getMonth() + 3 );
                ld.setMonth( ld.getMonth() + 3 );
                ld.setDate( 0 );
            } else if( curType.code == "half" ) {
                fd.setMonth( fd.getMonth() + 6 );
                ld.setMonth( ld.getMonth() + 6 );
                ld.setDate( 0 );
            } else if( curType.code == "year" ) {
                fd.setFullYear( fd.getFullYear() + 1 );
                ld.setFullYear( ld.getFullYear() + 1 );
            } else {
                fd.setDate( fd.getDate() + 1 );
                ld.setDate( ld.getDate() + 1 );
            }

            curDate.sDate = fd.dateToStr();
            curDate.eDate = ld.dateToStr();
            $el[ 0 ].setAttribute( "data-date", JSON.stringify( curDate ) );
        }





		// **********		Out Method		************************************************************************************************ //
		this.update = function( $value ){
            console.log( "update" );
			//reset();
		}



	};

	$.fn.datepickers = function ( $options ) {
		return this.each(function() {
			var dc = new acClass( $( this ), $options );
			$.data( this, 'datepickers', dc );
			$( document ).ready( dc.init );
		});
	}
})(jQuery);
