/*!
 *
 * @author: RSN R&D Team LHS(GUNI)
 *			h2dlhs@realsn.com
 *
 *
 **/

var gnbIDX = "";
var charts = [];

function designScripts() {
    if ($("body").hasClass("is-pdf")) return false;

    // 페이지 전체 UI재설정(디자인 요소)
    ui_reset();

    // Message Box 컨텐이너
    var msgBoxContainer = $("<section id=\"msg_box\"><div class=\"bg\"></div><div class=\"box ui_shadow_00\"><h2 class=\"invisible\">팝업 메시지</h2></div></section>");
    $("body").prepend(msgBoxContainer);

    // GNB / LNB
    $("header nav a").each(function () {
        if ($(this).data("idx") == gnbIDX) $(this).addClass("is-active");
    })

    // Page Title
    $("#page_title").each(function () {
        $(this).text(MODEL.getPageName());
    });

    $(window).resize(function () {
        $(".popup_item").each(function () {
            //popupMngr.update( $( this ) );
        });
    });

    // 팝업 백그라운드 클릭
    $("#popup_container > .bg").click(function () {
        $("#popup_container").find(".popup_item:visible").each(function () {
            popupMngr.close($(this));
        });
    });
    $("#inPopup_container > .bg").click(function () {
        $("#inPopup_container").find(".inPopup_item:visible").each(function () {
            inPopupMngr.close($(this));
        });
    });

    // 팝업 리사이즈 포지션 업데이트
    $(window).resize(function () {
        $("#popup_container .popup_item:visible").each(function () {
            popupMngr.update(this);
        });
    });


    // 상단 고정
    $(".header_fix_pin input").each(function () {
        var tg = $(this);
        $(this).change(function () {
            if (this.checked) $.cookie("headerPin", "true", { expires: 9999, path: "/;SameSite=Lax" });
            else $.cookie("headerPin", "false", { expires: 9999, path: "/;SameSite=Lax" });
            hndl_header();
        });

        if ($.cookie("headerPin") == "false") {
            tg[0].checked = false;
        } else {
            tg[0].checked = true;
        }
        tg.trigger("change");

        function hndl_header() {
            if ($.cookie("headerPin") == "true") {
                $("header").addClass("is-fixed");
            } else {
                $("header").removeClass("is-fixed");
            }
        };
    });

    // table active
    // $( ".ui_brd_list" ).each(function(){
    //     var _this = $( this );
    //     _this.find( "table.active_able tbody tr" ).click(function(){
    //         if( !$( this ).hasClass( "no_over" ) ){
    //             _this.find( "table.active_able tbody tr" ).removeClass( "active" );
    //             $( this ).addClass( "active" );
    //         }
    //     });
    // });

    // Header 축소/확장
    // $("header").each(function () {
    //     var tg = $(this);

    //     $(document).scroll(evt_scroll_header);
    //     evt_scroll_header();
    //     function evt_scroll_header($e) {
    //         if ($(window).scrollTop() > 1) tg.addClass("is-fold");
    //         else tg.removeClass("is-fold");
    //     }

    //     $(document).scroll(evt_docScroll);
    //     // // evt_docScroll();
    //     function evt_docScroll() {
    //         if ($(window).scrollTop() > 70) $("header").addClass("is-fixed-scroll");
    //         else $("header").removeClass("is-fixed-scroll");
    //     }
    // });


    // 상단 검색
    $(".ui_searchs").each(function () {
        var isFirst = true;
        var wrap = $(this).find("> .wrap");
        var searchs = $(this).find(".wrap > .searchs_inputs");
        var results = $(this).find(".wrap > .searchs_result");
        var toggle = $(this).find("#ts_toggle");
        var aning = false;
        toggle.unbind("change", topSearchHndl).change(topSearchHndl);
        results.show();

        function topSearchHndl($e, $tweenTime) {
            if (!toggle || !toggle[0]) return;

            if (!$tweenTime) $tweenTime = 0;
            aning = true;
            if (toggle[0].checked) {
                $("#searchs_today").removeClass("is-fold");
                searchs.animate({ "margin-top": 0 }, $tweenTime, "easeInOutExpo", function () {
                    setTimeout(function () {
                        aning = false;
                    }, 50)
                });
                wrap.animate({ "height": toggle.outerHeight() + searchs.outerHeight() }, $tweenTime, "easeInOutExpo");
            } else {
                $("#searchs_today").addClass("is-fold");
                searchs.animate({ "margin-top": -searchs.outerHeight() }, $tweenTime, "easeInOutExpo", function () {
                    setTimeout(function () {
                        aning = false;
                    }, 50)
                });
                wrap.animate({ "height": results.outerHeight() }, $tweenTime, "easeInOutExpo");
            }
        }

        $(document).bind("scroll", function ($e) {
            if (!toggle || !toggle[0]) return;

            if ($(document).scrollTop() > 0 && toggle[0].checked && !aning) {
                if (!isFirst) {
                    toggle[0].checked = false;
                    toggle.trigger("change");
                }
                isFirst = false;
            }
        })

        // init
        topSearchHndl(null, 0);
    });


    // Side Navigator
    $("#sidebar").each(function () {
        var _this = this
        var aning = false;
        var topBtn = $(_this).find(".side-topBtn");
        var toggle = $("#ts_toggle");
        var toggleLabel = $("#ts_toggle + label");
        var searchs = $(".ui_searchs").find(".wrap > .searchs_inputs");
        var results = $(".ui_searchs").find(".wrap > .searchs_result");
        var header = $("header");
        var headerNav = $("header").find("nav");

        var sectionTop = [];
        var ids = "";

        for (var i = 0; i < $(".ui_row").length; i++) {
            ids = $($(".ui_row")[i]).attr("id");

            if (ids) {
                if ($($(".ui_row")[i]).attr("id").indexOf("qid_0") >= 0) {
                    sectionTop.push($($(".ui_row")[i]).offset().top - (120 + 80));
                }
            }
        }

        nowScrActive(0);

        $(document).scroll(function () {
            var nowTop = $(document).scrollTop();
            var nowNum = 0;

            for (var i = 0; i < sectionTop.length; i++) {
                if (nowTop > sectionTop[i]) {
                    nowNum = i;
                }
            }
            nowScrActive(nowNum);
        });

        function nowScrActive(num) {
            var nowTop = $(document).scrollTop();

            if (nowTop >= 0 && nowTop < Number(sectionTop[0])) {
                $(_this).find("li").removeClass("active")
                $(_this).find("a[href='#" + $($(".ui_row")[0]).attr("id") + "']").parent("li").addClass("active");

            } else if (nowTop > sectionTop[num] && nowTop < sectionTop[num + 1]) {
                $(_this).find("li").removeClass("active")
                $(_this).find("a[href='#" + $($(".ui_row")[num]).attr("id") + "']").parent("li").addClass("active");
            }
        }
        // $($(".ui_row")[1]).attr( "id" )

        // if( !$( header ).is( ".is-fold" )  ){
        // 	$( _this ).css( { "margin-top" : "110px"} );
        // } else {
        // 	$( _this ).css( { "margin-top" : "60px"} );
        // }

        // toggle.change( asideHndl );

        // function asideHndl( $e, $tweenTime ){
        //     if( !$tweenTime ) $tweenTime = 0;
        //     aning = true;

        //     if( toggle[ 0 ].checked ){
        //         $( _this ).stop().animate( { "top" : headerNav.outerHeight() + searchs.outerHeight() + toggleLabel.outerHeight() }, $tweenTime, "easeInOutExpo", function(){
        // 			aning = false;
        //         });
        //     } else {
        //         $( _this ).stop().animate( { "top" : headerNav.outerHeight() + results.outerHeight() + toggleLabel.outerHeight() }, $tweenTime, "easeInOutExpo", function(){
        //             aning = false;
        //         });
        //     }
        // }

        // $( document ).bind( "scroll", function( $e ){
        //     // console.log( $( document ).scrollTop() )
        // 	if( !aning ){
        // 		if( !$( header ).is( ".is-fold" ) ){
        // 			$( _this ).stop().animate({ "margin-top" : "110px" }, 0);
        // 		} else if ( $( header ).is( ".is-fold" ) ){
        // 			$( _this ).stop().animate({ "margin-top" : "60px" }, 0);
        // 		}
        // 	}
        // });

        // // init
        // asideHndl( null ,0 );

        topBtn.click(function () {
            $("html").animate({ "scrollTop": 0 }, 400);
        });
    });

    $(".page-bsi").find("#qid_02 .ui_data_brd").each(function () {
        var _this = this;

        $(this).find(".wrap").filter(function () {
            var hdOffset = $(_this).find(".header").outerHeight();
            var width = $(_this).find(".wrap th.active").outerWidth();
            var wrapHeight = $(_this).find(".wrap").outerHeight();
            var activePos = ($(_this).find(".wrap th.active").position().left);

            var top = $("<div class='active_bar'></div>").css({
                "top": hdOffset,
                "left": activePos + "px",
                "width": width + "px",
                "height": 1 + "px"
            });
            var left = $("<div class='active_bar'></div>").css({
                "top": hdOffset,
                "left": activePos + "px",
                "width": 1 + "px",
                "height": wrapHeight + "px"
            });
            var right = $("<div class='active_bar'></div>").css({
                "top": hdOffset,
                "left": (Number(activePos) + Number(width) - 1) + "px", "width": 1 + "px",
                "height": wrapHeight + "px"
            });
            var bottom = $("<div class='active_bar'></div>").css({
                "top": ((hdOffset + wrapHeight) - 1) + "px",
                "left": activePos + "px",
                "width": width + "px",
                "height": 1 + "px"
            });

            $(this)[0].appendChild(top[0]);
            $(this)[0].appendChild(left[0]);
            $(this)[0].appendChild(right[0]);
            $(this)[0].appendChild(bottom[0]);
        });
    });

    // 셀렉트 가능 리스트 active 
    $(".ui_list_select").each(function () {
        var _this = $(this);
        var listBtn = _this.find(".sel_btn");

        listBtn.click(function (e) {

            if (!$(e.target).parent().is("a")) {

                listBtn.removeClass("active");
                $(this).addClass("active");
            }
        });
    });
}

// 브라우저 체크
function BrowserVersionCheck() {
    var word;
    var versionOrType = "another";
    var ieName = navigator.appName;
    var agent = navigator.userAgent.toLowerCase();

    /*** 1. IE 버전 체크 ***/
    // IE old version ( IE 10 or Lower ) 
    if (ieName == "Microsoft Internet Explorer") {
        word = "msie ";
    } else {
        // IE 11 
        if (agent.search("trident") > -1) word = "trident/.*rv:";
        // IE 12 ( Microsoft Edge ) 
        else if (agent.search("edge/") > -1) word = "edge/";
    }

    if (word == "msie" || word == "trident/.*rv:" || word == "edge/") {
        var head = document.getElementsByTagName('head')[0];
        var link = document.createElement('link');
        link.rel = 'stylesheet';
        link.type = 'text/css';
        link.href = '../../asset/css/design_ie.css';
        head.appendChild(link);
    }
}
BrowserVersionCheck();