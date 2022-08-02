/*!
 *
 * @author: GUNI, h2dlhs@realsn.com
 **/
 
 
(function ($){

	var asClass = function( $el, $options ){

        var VS_use = true;

        var active = false;
        var id;
        var datas;
        var allListChk = false;
        var firstScrollChk = false;
        var keyChk = false;
        var keyCnt = 0;
        var visibleIdx = { start: 0, end : 10000000 };
        var list;

		var $inputs, $input, $inputBtn, $resetBtn;
		var $list, $listUL;

		this.init = function (){
            // 가상 스크롤 제거
            if( $el.attr( "data-vs" ) == "false" ) VS_use = false;

            $( document ).scroll( function(){
                active = false;
                hndl_list();
            })

            build();
        }
        



        // **********		Build		************************************************************************************************ //
        function build(){
            id = $el.attr( "id" );
            datas = $options && $options.datas ? JSON.parse( JSON.stringify( $options.datas ) ) : null;

            $inputs = $( '<div class="inputs" />' );
            $input = $( '<input type="text" id="' + id + '" class="select_box"><label for="' + id + '" class="is-invisible">제품검색</label>' );
            $resetBtn = $( '<button type="button" class="btn_reset"><span>초기화</span></button>' );
            $inputBtn = $( '<button type="button" class="btn_expand"><span>열기/닫기</span></button>' );
            $inputs.append( $input );
            $inputs.append( $resetBtn );
            $inputs.append( $inputBtn );
            $el.append( $inputs );
            $inputBtn.click( evt_arrowClick );
            $input.keyup( evt_inputKeyUp );
            $el.keydown( evt_inputKeyDown );
            $input.val( datas.filter( function( $item ){
                $item.code === $el.attr( "data-value" );
            }));
            $input.focusout( evt_inputBlur );
            $resetBtn.click( evt_resetClick );

            $list = $( '<div class="list" />' );
            $listUL = $( '<ul />' );
            $list.append( $listUL );
            $el.append( $list );
            if( VS_use) $list.scroll( evt_listScroll );

            $list.css( "min-width", $inputs.outerWidth() );

            new MutationObserver( function( $e ) {
                $e.filter( function( $evt ){
                    if( $evt.attributeName == "data-value" ) {
                        if( $evt.oldValue != $el.attr( "data-value" ) ) hndl_change();
                    }
                })
            }).observe( $el[ 0 ], { attributes: true, attributeFilter: ['data-value'], attributeOldValue : true });

            evt_listScroll();
            drawList();
            rendered();
        }

        function drawList(){
            if( !datas ) return false;

            // removes
            $listUL.find( "li button" ).unbind( "click", evt_itemClick );
            $listUL.find( "li" ).remove();

            list = getList();
            if( VS_use ) {
                $listUL.height( list.length * 24 );
                $listUL.css({
                    "height": ( list.length * 24 ) + 10,
                    "padding-top": ( visibleIdx.start * 24 ) + 5
                });
            }

            list.filter( function( $item, $idx ){
                if( $idx >= visibleIdx.start && $idx < visibleIdx.end ) {
                    var li = $( '<li />' );
                    var item = $( '<button type="button" class="item" data-value="' + $item.code + '">' + getText( $item.name ) + '</button>' );
                    if( $item.code == $el.attr( "data-value" ) ) item.addClass( "is-active" );
                    if( $idx == keyCnt - 1 ) item.addClass( "is-over" );
                    li.append( item );
                    $listUL.append( li );
                    item.click( evt_itemClick );
                }
            });

            hndl_scroll();

            if( keyChk ) {
                keyChk = false;
                if( list.length > 0 ) active = true;
                else active = false;
                hndl_list();
            }
        }

        function rendered(){
            var result = "선택없음";

            for( var Loop1 = 0 ; Loop1 < datas.length ; ++Loop1 ){
                if( datas[ Loop1 ].code == $el.attr( "data-value" ) ) {
                    result = datas[ Loop1 ].name;
                    break;
                }
            }

            $input.val( result );
        }

        // 매칭 키워드 변환 후 반환
        function getText( $text ){
            if( allListChk ) return $text;
            if( $input.val() == "" ) return $text;

            var result = String( $text );
            var txt = new RegExp( $input.val(), "gi" );
            result = result.replace( txt, function( $txt ){
                return '<em>' + $txt + '</em>'
            })
            return result;
        }

        // 매칭 키워드 목록 반환
        function getList(){
            if( allListChk ) {
                return JSON.parse( JSON.stringify( datas ) );
            }

            var result = [];

            if( datas ) {
                if( $input.val() != "" ) {
                    datas.filter( function( $item ){
                        if( $item.name.toLowerCase().indexOf( $input.val().toLowerCase() ) >= 0 ) result.push( $item );
                    })
                } else {
                    result = JSON.parse( JSON.stringify( datas ) );
                }
            }

            return result;
        }




        // **********		Hndler		************************************************************************************************ //
        function hndl_list() {
            if( active ) {
                drawList();
                setRePos();
                $el.addClass( "is-active" );
                $( document ).unbind( "click", evt_docClick ).click( evt_docClick );
            } else {
                allListChk = false;
                $el.removeClass( "is-active" );
                keyCnt = 0;
            }
        }

        function hndl_change(){
            if( !datas ) return;
            rendered();

            if( $el.attr( "data-change" ) ) eval( $el.attr( "data-change" ) + "(" + $el.attr( "data-value" ) + ")" );
        }

        // 선택 아이템으로 Scroll
        function hndl_scroll( $idx ){
            if( $idx == null ) {
                if( allListChk && firstScrollChk ) {
                    var idx;
                    datas.filter( function( $item, $idx ){
                        if( $item.code == $el.attr( "data-value" ) ) idx = $idx;
                    });
                    $list.scrollTop( idx * 24 );
                }
                firstScrollChk = false;
            } else {
                var idx = $idx - 9 <= 0 ? 0 : $idx - 10;
                $list.scrollTop( idx * 24 );
            }
        }

        function setRePos(){
            if( !$input.is( ":visible" ) ) return false;
            
            var pos = {};
            pos.top = $input.offset().top - $( document ).scrollTop() + $input.outerHeight();
            pos.left = $input.offset().left - $( document ).scrollLeft();

            if( pos.top + $list.outerHeight() > $( window ).height() ) pos.top -= $list.outerHeight() + $input.outerHeight() + 2;
            if( pos.left + $list.outerWidth() > $( window ).width() ) pos.left -= $list.outerWidth() - $input.outerWidth();

            if( pos.top < 0 ) pos.top = $input.offset().top - $( document ).scrollTop() + $input.outerHeight();

            $list.css( { top: pos.top, left: pos.left } )
        }

        function hndl_itemSel(){
            $list.find( ".item" ).removeClass( "is-over" );

            if( keyCnt - 1 >= 0 ) {
                $list.find( ".item" ).eq( keyCnt - visibleIdx.start - 1 ).addClass( "is-over" );
            }
        }





        // **********		Event		************************************************************************************************ //
        function evt_arrowClick(){
            active = !active;
            if( active ) {
                allListChk = true;
                firstScrollChk = true;
            } else {
                rendered();
            }
            hndl_list();
        }
        function evt_docClick( $e ){
            var tg = $e.target;
			if( $el.has( tg ).length === 0){
				active = false;
				hndl_list();
                $( document ).unbind( "click", evt_docClick );
                rendered();
            }
        }
        
        // Item Click
        function evt_itemClick( $e ){
            $el[ 0 ].setAttribute( "data-value", $( this ).attr( "data-value" ) )
            active = false;
            hndl_list();
            rendered();
        }

        // Input Event
        function evt_resetClick( $e ){
            $input.val( "" );
            $input.focus();
            drawList();
        }
        function evt_inputBlur( $e ){
            if( !active ) rendered();
        }
        function evt_inputKeyUp( $e ){
            if( $e.keyCode == 38 || $e.keyCode == 40 ) {
                if( !active ) {
                    active = true;
                    hndl_list();
                }
                return;
            }
            if( $e.keyCode == 27 ) {
                active = false;
                hndl_list();
                rendered();
                $input.blur();
                return;
            };
            if( $e.keyCode == 13 ) {
                if( $list.find( ".item.is-over" ).length > 0 ) {
                    $el[ 0 ].setAttribute( "data-value", $list.find( ".item.is-over" ).attr( "data-value" ) );
                    active = false;
                    hndl_list();
                }
                return;
            }

            keyCnt = 0;
            allListChk = false;
            keyChk = true;
            drawList();
        }

        // Input Cursor Event
        function evt_inputKeyDown( $e ){
            if( !active ) {
                keyCnt = 0;
                drawList();
                return;
            }
			if( $e.keyCode == 38 ) {            // UP
				if( keyCnt > 1 ) keyCnt--;
                hndl_itemSel();
                hndl_scroll( keyCnt );
				return false;
			} else if( $e.keyCode == 40 ) {     // DOWN
                if( keyCnt < list.length ) keyCnt++;
                hndl_itemSel();
                hndl_scroll( keyCnt );
				return false;
			} else if( $e.keyCode == 33 ) {     // Page UP
                if( keyCnt > 1 ) keyCnt -= 10;
                if( keyCnt < 1 ) keyCnt = 1;
                hndl_itemSel();
                hndl_scroll( keyCnt );
				return false;
            } else if( $e.keyCode == 34 ) {     // Page DOWN
                if( keyCnt < list.length ) keyCnt += 10;
                if( keyCnt > list.length ) keyCnt = list.length;
                hndl_itemSel();
                hndl_scroll( keyCnt );
				return false;
            }
        }

        // List Scroll
        function evt_listScroll( $e ){
            if( !VS_use ) return;
            
            var th = 1000000000;            // 가상 높이
            var vp = 1000;                  // 보이는 부분 높이
            var rh = 24;                    // 행 높이
            var y = $list.scrollTop(),
                buffer = vp,
                top = Math.floor((y-buffer)/rh),
                bottom = Math.ceil((y+vp+buffer)/rh);

            visibleIdx.start = Math.max(0,top);
            visibleIdx.end = Math.min(th/rh,bottom);

            drawList();
        }
        
		



		// **********		Out Method		************************************************************************************************ //
		this.update = function( $value ){
			//reset();
		}



	};

	$.fn.autocomplete_select = function ( $options ) {
        if( !$( this ).hasClass( "is-select" ) ) {
            return this.each(function() {
                var as = new asClass( $( this ), $options );
                $( this ).addClass( "is-select" );
                $.data( this, 'autocomplete_select', as );
                $( document ).ready( as.init );
            });
        }
	}
})(jQuery);
