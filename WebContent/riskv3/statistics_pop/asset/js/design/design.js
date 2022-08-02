/*!
 *
 * @author: RSN R&D Team LHS(GUNI)
 *			h2dlhs@realsn.com
 *
 *
 **/

var gnbIDX = "";

function designScripts(){
	// 페이지 전체 UI재설정(디자인 요소)
	ui_reset();

	// Message Box 컨텐이너
	var msgBoxContainer = $( "<section id=\"msg_box\"><div class=\"bg\"></div><div class=\"box ui_shadow_00\"><h2 class=\"invisible\">팝업 메시지</h2></div></section>" );
	$( "body" ).prepend( msgBoxContainer );

	// GNB / LNB
    var curUrl = location.href;
	init_gnb();
	function init_gnb(){
		$( "header nav th" ).each( function(){
			$( this ).find( "a" ).each( function(){
                // console.log( getUrlPath( this.href ) + "  ::  " + curUrl );
				// if( getUrlPath( this.href ) == curUrl ) {
				// 	$( this ).addClass( "active" );
                //     MODEL.setPageName( $( this ).find( "> span" ).text() );
                // }
                if( curUrl.indexOf( this.href ) >= 0 ) {
                    $( this ).addClass( "active" );
                    MODEL.setPageName( $( this ).find( "> span" ).text() );
                }
            });
		});
	}
	function getUrlPath( $url, $type ){
        if( $url.split( "/view/" ).length <= 1 ) return $url

        var tmpUrl = $url.split( "/view/" )[ 1 ].split( "/" );
		var result = "/";
		for( var Loop1 = 0 ; Loop1 < tmpUrl.length - 1 ; ++Loop1 ){
            result += tmpUrl[ Loop1 ] + "/";
		}
		return result;
	}

	// Page Title
	$( "#page_title" ).each( function(){
		$( this ).text( MODEL.getPageName() );
	});

	$( window ).resize( function(){
		$( ".popup_item" ).each( function(){
			//popupMngr.update( $( this ) );
		});
	});

	// 팝업 백그라운드 클릭
	$( "#popup_container > .bg" ).click( function(){
		$( "#popup_container" ).find( ".popup_item:visible" ).each( function(){
			popupMngr.close( $( this ) );
		});
	});
	$( "#inPopup_container > .bg" ).click( function(){
		$( "#inPopup_container" ).find( ".inPopup_item:visible" ).each( function(){
			inPopupMngr.close( $( this ) );
		});
	});

	// 팝업 리사이즈 포지션 업데이트
	$( window ).resize( function() {
		$( "#popup_container .popup_item:visible" ).each( function(){
			popupMngr.update( this );
		});
	});

	// 상단 고정
	$( ".btn_header_pin input" ).each( function(){
		$( this ).change( function(){
			if( this.checked ) $.cookie( "headerPin", "true", { expires : 99999999, path : "/" } );
			else $.cookie( "headerPin", "false", { expires : 99999999, path : "/" } );
			hndl_header();
		});
		
		(function(){
			if( $.cookie( "headerPin" ) == "true" ) $( ".btn_header_pin input" )[ 0 ].checked = true;
			else $( ".btn_header_pin input" )[ 0 ].checked = false;
			hndl_header();
		})();
		
		function hndl_header(){
			if( $.cookie( "headerPin" ) == "true" ) {
				$( "header" ).addClass( "fixed" );
				$( ".btn_header_pin .title" ).html( "고정해제" );
				$( ".btn_header_pin label" ).attr( "title", "메뉴 고정 해제" );
				$( ".ui_searchs" ).addClass( "fixed" );
				$( "#content > .wrap" ).css( { "padding-top" : $( "#top_searchs" ).outerHeight() + 30 } );
			} else {
				$( "header" ).removeClass( "fixed" );
				$( ".btn_header_pin .title" ).html( "메뉴고정" );
				$( ".btn_header_pin label" ).attr( "title", "메뉴 고정" );
				$( ".ui_searchs" ).removeClass( "fixed" );
				$( "#content > .wrap" ).css( { "padding-top" : 30 } );
			}
		};

		$( window ).scroll( hndl_scroll_header );
		hndl_scroll_header();
		function hndl_scroll_header( $e ){
			if( $( "header" ).hasClass( "fixed" ) ) {
				$( "header" ).css( { "left" : -$( window ).scrollLeft() } );
				$( "#top_searchs" ).css( { "left" : -$( window ).scrollLeft() } );
			} else {
				$( "header" ).css( { "left" : 0 } );
				$( "#top_searchs" ).css( { "left" : 0 } );
			}
		}
	});

	// 상단 검색
	$( ".ui_searchs" ).each( function(){
		var tg = $( this );
		var active = true;
		var isFirst = true;
		$( this ).find( ".btn_search_toggle" ).addClass( "active" );
		$( this ).find( ".btn_search_toggle" ).click( function(){
			$( this ).toggleClass( "active" );
			active = $( this ).hasClass( "active" );
			topSearchsHndl();
		});

		topSearchsHndl = function( $tweenTimeFixed ){
			var tweenTime_01 = 0;
			var tweenTime_02 = 0;
			if( !isFirst ) {
				tweenTime_01 = 300;
				tweenTime_02 = 400;
			}
			if( $tweenTimeFixed == 0 ) {
				tweenTime_01 = $tweenTimeFixed;
				tweenTime_02 = $tweenTimeFixed;
			}

			tg.find( "> .wrap" ).css( "overflow", "hidden" );

			if( active ){
				tg.find( "> .wrap" ).stop().animate( { "margin-top" : 0, height : tg.find( ".searchs_inputs" ).outerHeight() }, tweenTime_02, "easeInOutExpo", function(){
					tg.find( "> .wrap" ).css( "overflow", "visible" );
					tg.find( ".searchs_result" ).hide();
				});
				if( $.cookie( "headerPin" ) == "true" ) $( "#content > .wrap" ).stop().animate( { "padding-top" : tg.find( ".searchs_inputs" ).outerHeight() + 30 }, tweenTime_02, "easeInOutExpo" );
			} else {
				tg.find( ".ui_datepicker_input_range" ).find( ".date_result" ).removeClass( "active" );
				tg.find( ".ui_datepicker_input_range" ).find( ".calendars" ).fadeOut( 120 );
				tg.find( ".searchs_result" ).show();
				tg.find( "> .wrap" ).stop().animate( { "margin-top" : -tg.find( ".searchs_inputs" ).outerHeight(), height : tg.find( ".searchs_inputs" ).outerHeight() + tg.find( ".searchs_result" ).outerHeight() }, tweenTime_02, "easeInOutExpo" );
				if( $.cookie( "headerPin" ) == "true" ) $( "#content > .wrap" ).stop().animate( { "padding-top" : tg.find( ".searchs_result" ).outerHeight() + 30 }, tweenTime_02, "easeInOutExpo" );
			}
			isFirst = false;
		}
		topSearchsHndl();
	});
}