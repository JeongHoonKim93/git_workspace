/*!
 *
 * @author: RSN R&D Team LHS(GUNI)
 *			h2dlhs@realsn.com
 *
 *
 **/


// Nav Form Send
function gotoPage($url, $frmName) {
	location.href = $url;
}

// UI Reset
var ui_reset = function ($tg) {
	if (!$tg) $tg = $("body");

	// password
	$tg.find(".dcp > input.password").each(function () {
		var tg = $(this);
		var input = $(this).parent().find("input:not(.password)");
		tg.unbind("change", evt_passChange).change(evt_passChange);

		evt_passChange();
		function evt_passChange() {
			if (tg[0].checked) input.attr("type", "password");
			else input.attr("type", "text");
		}
	});

	// DCP Input in Delete
	$tg.find(".dcp.is-input-del .btn_del").each(function () {
		var input = $(this).parent().find("input");
		$(this).unbind("click").click(function () {
			input.val("");
		});
	})

	// Design SelectBox 셋팅
	$tg.find(".dcp > select").each(function () {
		var tg = $(this);
		tg.change(selectSet);
		selectSet();
		new MutationObserver(function ($e) {
			if (tg.attr("value")) {
				tg[0].value = tg.attr("value");
				tg.removeAttr("value");
				tg.trigger("change");
			} else {
				selectSet();
			}
		}).observe(tg[0], { attributes: true, childList: true, characterData: true, subtree: true, attributeOldValue: true, characterDataOldValue: true });

		function selectSet() {
			tg.find("+ label").html(tg.find("> option:selected").html());
		}
	});


	// Design SelectBox 셋팅
	$tg.find(".dcp:not(.is-mselect) > select").each(function () {
		var tg = $(this);
		tg.change(selectSet);

		selectSet();
		new MutationObserver(function ($e) {
			if (tg.attr("value")) {
				tg[0].value = tg.attr("value");
				tg.removeAttr("value");
				tg.trigger("change");
			} else {
				selectSet();
			}
		}).observe(tg[0], { attributes: true, childList: true, characterData: true, subtree: true, attributeOldValue: true, characterDataOldValue: true });

		function selectSet() {
			tg.blur();
			tg.find("+ label").html(tg.find("> option:selected").html());
		}
	});
	$tg.find(".dcp.is-mselect").mselect();

	// Checkbox 전체 포함(dcp_grp)
	$($tg).find(".dcp_grp").each(function () {
		var grp = $(this);
		var all = $(this).find(".dcp.is-all input[type=checkbox]");
		var item = $(this).find(".dcp:not(.is-all) input[type=checkbox]");

		// init
		var allValue = "";
		item.each(function ($idx) {
			if ($idx > 0) allValue += ","
			allValue += this.value;
		})
		all.val(allValue);
		if (allValue == grp.data("value")) {
			all[0].checked = true;
			all.trigger("change");
		} else {
			item.each(function ($idx) {
				if (String(grp.data("value")).strToArr().indexOf(this.value) >= 0) this.checked = true;
			})
		}

		item.unbind("change", itemChange).change(itemChange);
		function itemChange() {
			var chk = true;
			var chkCnt = 0;
			item.each(function () {
				if (!this.checked) chk = false;
				else chkCnt++;
			})
			if (chk) all[0].checked = true;
			else all[0].checked = false;
			if (chkCnt == 0) all[0].checked = true;

			var grpValue = "";
			item.each(function ($idx) {
				if (this.checked) {
					if (grpValue != "") grpValue += ","
					grpValue += this.value;
				}
			})
			grp.attr("data-value", grpValue);
			all.trigger("change");
		}

		all.unbind("change", allChange).change(allChange);
		function allChange() {
			if (this.checked) {
				grp.attr("data-value", allValue);
				item.each(function () {
					this.checked = false;
				})
			} else {
				var chk = false;
				item.each(function () {
					if (this.checked) chk = true;
				})
				if (!chk) {
					all[0].checked = true;
					return false;
					// grp.attr( "data-value", "" );
				}
			}
			grp.trigger("val_change");
		}
	})

	// Bubble Box
	$tg.find(".ui_bubble_box").each(function () {
		var activeChk = false;
		var bubbleBox = $(this);
		var code = $(this).data("bubble-for");
		var btn = $(this).parent().find("*[data-bubble-id=\"" + code + "\"]");

		if (btn.data("action") == "click") {
			btn.click(function () {
				activeChk = !activeChk;
				hndl_bubbleBox();
			});
		} else {
			btn.hover(function ($e) {
				if ($e.type == "mouseenter") activeChk = true;
				else activeChk = false;
				hndl_bubbleBox();
			});
		}

		if (bubbleBox.attr("data-fixed") == "true") {
			$(window, "*").unbind("scroll", hndl_bubbleBox).scroll(hndl_bubbleBox);
			$(window).unbind("resize", hndl_bubbleBox).resize(hndl_bubbleBox);
		}

		function hndl_bubbleBox() {
			if (activeChk) {
				var pos = getPos(bubbleBox.data("pos"));
				bubbleBox.css({ "top": pos.top, "left": pos.left }).fadeIn(120);
				btn.addClass("active");
			} else {
				bubbleBox.fadeOut(120);
				btn.removeClass("active");
			}
		}

		function getPos($type) {
			var result = {};
			var space = 11;

			if (bubbleBox.attr("data-fixed") == "true") {
				if ($type.indexOf("T") >= 0) {
					result.top = btn[0].getBoundingClientRect().top - bubbleBox.outerHeight() - space;
				} else if ($type.indexOf("B") >= 0) {
					result.top = btn[0].getBoundingClientRect().top + btn.outerHeight() + space;
				}
				if ($type.indexOf("L") >= 0) {
					result.left = btn[0].getBoundingClientRect().left;
					if (bubbleBox.attr("data-arrowcenter") == "true") {
						var posX = result.left - btn[0].getBoundingClientRect().left + (btn.outerWidth() / 2) - (bubbleBox.find(".arrow").outerWidth() / 2);
						bubbleBox.find(".arrow").css("left", posX);
					}
				} else if ($type.indexOf("R") >= 0) {
					result.left = btn[0].getBoundingClientRect().left + btn.outerWidth() - bubbleBox.outerWidth();
					if (bubbleBox.attr("data-arrowcenter") == "true") {
						var posX = btn[0].getBoundingClientRect().left - result.left + (btn.outerWidth() / 2) - (bubbleBox.find(".arrow").outerWidth() / 2);
						bubbleBox.find(".arrow").css("left", posX);
					}
				} else if ($type.indexOf("C") >= 0) {
					result.left = (btn[0].getBoundingClientRect().left + (btn.outerWidth() / 2)) - (bubbleBox.outerWidth() / 2);
				}
			} else {
				if ($type.indexOf("T") >= 0) {
					result.top = btn.position().top - bubbleBox.outerHeight() - space;
				} else if ($type.indexOf("B") >= 0) {
					result.top = btn.position().top + btn.outerHeight() + space;
				}

				if ($type.indexOf("L") >= 0) {
					result.left = btn.position().left;
					if (bubbleBox.attr("data-arrowcenter") == "true") {
						var posX = result.left - btn.position().left + (btn.outerWidth() / 2) - (bubbleBox.find(".arrow").outerWidth() / 2);
						bubbleBox.find(".arrow").css("left", posX);
					}
				} else if ($type.indexOf("R") >= 0) {
					result.left = btn.position().left + btn.outerWidth() - bubbleBox.outerWidth();
					if (bubbleBox.attr("data-arrowcenter") == "true") {
						var posX = btn.position().left - result.left + (btn.outerWidth() / 2) - (bubbleBox.find(".arrow").outerWidth() / 2);
						bubbleBox.find(".arrow").css("left", posX);
					}
				} else if ($type.indexOf("C") >= 0) {
					result.left = (btn.position().left + (btn.outerWidth() / 2)) - (bubbleBox.outerWidth() / 2);
				}

			}

			return result;
		}
	});

	// 커스텀 셀렉트박스 (체크박스 + 자동완성)
	$tg.find(".dcp_custom_select").autocomplete_check_select();


	$tg.find(".dcp input[type=checkbox].on-off").change(function () {
		var _this = $(this);
		var select = _this.parents(".ui_function").find(".dcp_custom_select");

		if (this.checked) select.addClass("ui_disabled");
		else select.removeClass("ui_disabled");

	});

	// 자동완성 - 셀렉트 박스
	$tg.find(".dcp_autocomplete_select").autocomplete_select();

	// Datepickers
	$tg.find(".ui_datepickers").datepickers();

	// Page Tab Editable
	$tg.find(".ui_tab.page_tab li > input[type=radio]").each(function () {
		var tg = $(this);
		var label = $(this).find("+ label");
		var input = $(this).find("+ label > .input");
		var edit = $(this).find("+ label > .btn_edit");
		var confirm = $(this).find("+ label > .btn_confirm");

		input.blur(function ($e) {
			label.removeClass("editable");
			input.attr("contentEditable", false);
			hndl_item();
		});

		input.keydown(function ($e) {
			if ($e.keyCode == 13) {
				input.blur();
				$e.preventDefault();
			}
		});

		edit.click(function ($e) {
			label.addClass("editable");
			input.attr("contentEditable", true);
			hndl_item();
			return false;
		});

		function hndl_item() {
			if (label.hasClass("editable")) {
				input.attr("readonly", false);
				input.focus();
			} else {
				input.select("");
				input.attr("readonly", true);
			}
		}

		$(this).change(hndl_change);
		function hndl_change() {
		}
	});


	// Helper - 개별
	$tg.find(".ui_help_box").each(function () {
		var evtTarget = $(this);
		var input = evtTarget.find("input");
		var id = input.attr("id");
		var helperBox = $(".ui_helper[data-helper-id=" + id + "]");

		evt_change();
		input.change(evt_change);
		function evt_change() {
			if (this.checked) {
				evtTarget.attr("title", "Guide Close");
				helperBox.show();
			} else {
				evtTarget.attr("title", "Guide Open");
				helperBox.hide();
			}
		}
	});


	// Quick Nav Set
	$("#sidebar a").unbind("click").click(function ($e) {
		$e.preventDefault();
		var id = this.href.split("#")[1];
		locationMove("#" + id);
	})
}


// 메세지 매니저
var msgMngr = {
	stack: [],
	send: function ($txt, $title, $btnType, $mType, $func) {
		var stackData = {};
		stackData.txt = $txt;
		stackData.title = $title;
		stackData.btnType = $btnType;
		stackData.mType = $mType;
		stackData.func = $func;
		msgMngr.stack.push(stackData);
		if (!$("#msg_box").is(":visible")) msgMngr.openMBox();
	},
	openMBox: function () {
		var arrAlertType = [];
		arrAlertType[1] = $("<div class='icons'><span class='icon_error'></span></div>");
		arrAlertType[2] = $("<div class='icons'><span class='icon_warning'></span></div>");
		arrAlertType[3] = $("<div class='icons'><span class='icon_info'></span></div>");
		arrAlertType[4] = $("<div class='icons'><span class='icon_question'></span></div>");
		var arrAlertBtns = [];
		arrAlertBtns[0] = $("<button type='button' class='ui_shadow_00' data-value='0'><span>확인</span></button>");
		arrAlertBtns[1] = $("<button type='button' class='ui_shadow_00' data-value='1'><span>취소</span></button>");
		arrAlertBtns[2] = $("<button type='button' class='ui_shadow_00' data-value='2'><span>예</span></button>");
		arrAlertBtns[3] = $("<button type='button' class='ui_shadow_00' data-value='3'><span>아니요</span></button>");

		var $txt = msgMngr.stack[0].txt;
		var $title = msgMngr.stack[0].title;
		var $btnType = msgMngr.stack[0].btnType;
		var $mType = msgMngr.stack[0].mType;
		var $func = msgMngr.stack[0].func;

		$("#msg_box .box").html("");
		$("#msg_box .box").hide();
		if ($title && $title != "") $("#msg_box .box").append("<h2>" + $title + "</h2>");
		if ($mType && $mType != 0) $("#msg_box .box").append(arrAlertType[$mType]);
		if ($txt != "") {
			if (!$mType || $mType == 0) $("#msg_box .box").append("<div class='txts alone'><span>" + String($txt).replaceAll("\n", "<br>") + "</span></div>");
			else $("#msg_box .box").append("<div class='txts'><span>" + $txt + "</span></div>");
		}
		$("#msg_box .box").append("<div class='btns'></div>");
		if (!$btnType) $btnType = 0;
		switch ($btnType) {
			case 0:
				$("#msg_box .box .btns").append(arrAlertBtns[0]);
				break;
			case 1:
				$("#msg_box .box .btns").append(arrAlertBtns[0]);
				$("#msg_box .box .btns").append(arrAlertBtns[1]);
				break;
			case 2:
				$("#msg_box .box .btns").append(arrAlertBtns[2]);
				$("#msg_box .box .btns").append(arrAlertBtns[3]);
				break;
			case 3:
				$("#msg_box .box .btns").append(arrAlertBtns[2]);
				$("#msg_box .box .btns").append(arrAlertBtns[3]);
				$("#msg_box .box .btns").append(arrAlertBtns[1]);
				break;
		}
		$("#msg_box .box .btns > *").click(function () {
			if ($func) $func($(this).attr("data-value"));
			msgMngr.close();
		});
		$("#msg_box").fadeIn(200, function () {
			$("#msg_box .box").css("top", "53%").show().animate({ "top": "50%" }, 200, "easeOutQuad", function () {
				if (msgMngr.stack.length > 0) msgMngr.stack.splice(0, 1);
				$("#msg_box .box .btns > *").eq(0).focus();
			});
			$("#msg_box .box").css({ "margin-top": -$("#msg_box .box").outerHeight() / 2, "margin-left": -$("#msg_box .box").outerWidth() / 2 });
		});
	},
	close: function () {
		$("#msg_box .box").animate({ "top": "47%" }, 200, "easeInQuad");
		$("#msg_box").fadeOut(200, function () {
			$("#msg_box .box .btns > *").unbind("click");
			$("#msg_box .box").children().remove();

			if (msgMngr.stack.length > 0) {
				msgMngr.openMBox();
				return;
			}
		});
	}
};

// 팝업 매니저
var popupMngr = {
	open: function ($tg) {
		var $popup = $("#popup_container");
		var $tg = $($tg);
		$popup.fadeIn(300);
		$tg.show().css({
			"top": "50%",
			"left": "50%",
			"margin-top": -($tg.outerHeight() / 2) + 50,
			"margin-left": -$tg.outerWidth() / 2,
		}).animate({
			"margin-top": -($tg.outerHeight() / 2)
		}, 300, "easeOutQuad");
	},
	close: function ($tg) {
		$("#inPopup_container .inPopup_item").each(function () {
			inPopupMngr.close(this);
		});
		var $popup = $("#popup_container");
		var $tg = $($tg);
		$popup.fadeOut(300);
		$tg.animate({
			"margin-top": -$tg.outerHeight() / 2 - 50
		}, 300, "easeInQuad", function () {
			$tg.hide();
		});
	},
	update: function ($tg) {				// 팝업 포지션 업데이트
		var $tg = $($tg);
		var mt = -($tg.outerHeight() / 2);
		$tg.css("margin-top", mt);
	}
}
var inPopupMngr = {
	open: function ($tg) {
		var $popup = $("#inPopup_container");
		var $tg = $($tg);
		$popup.fadeIn(300);
		$tg.show().css({
			"top": "50%",
			"left": "50%",
			"margin-top": -($tg.outerHeight() / 2) + 50,
			"margin-left": -$tg.outerWidth() / 2,
		}).animate({
			"margin-top": -($tg.outerHeight() / 2)
		}, 300, "easeOutQuad");
	},
	close: function ($tg) {
		var $popup = $("#inPopup_container");
		var $tg = $($tg);
		$popup.fadeOut(300);
		$tg.animate({
			"margin-top": -$tg.outerHeight() / 2 - 50
		}, 300, "easeInQuad", function () {
			$tg.hide();
		});
	}
}

// 상단 검색결과 매니저
var searchResultMngr = {
	destroy: function () {
		$("#top_searchs .searchs_result .lists > *").remove();
	},
	addItem: function ($txt, $title) {
		var item = "<li><span class='item' title='" + $title + " : " + $txt + "'>" + $txt + "</span></li>";
		$("#top_searchs .searchs_result .lists").append(item);
	},
	addItemDate: function ($txt, $title, $cTxt, $cTitle) {
		var item = "<li><span class='item' title='" + $title + " : " + $txt + ", " + $cTitle + " : " + $cTxt + "'>" + $txt + " vs " + $cTxt + "</span></li>";
		$("#top_searchs .searchs_result .lists").append(item);
	}
}

// 차트 벌룬 - NaN 처리 - ver.증감율
function get_chartBalloonValueSensi($a, $b) {
	var sensiNum = Number($a.dataContext[$b["sensi"]]);
	var upNdn;
	var LDname;
	if ($b.legendTextReal) { LDname = $b.legendTextReal; }
	else if ($b.title) { LDname = $b.title; }

	if (sensiNum > 0) { upNdn = "up" }
	else if (sensiNum < 0) { upNdn = "dn" }
	else if (sensiNum == 0) { upNdn = "none" }
	// return "<strong style='color: " + $b.bulletColorR + ";'>" + LDname + "</strong> : <span style='color: " + $b.bulletColorR + "; font-size: 12px;'>" + String($a.dataContext[$b["valueField"]]).addComma() + "</span> " + "<span style='color: " + $b.bulletColorR + "'>(<span class='ui_fluc before " + upNdn + " '>" + String((sensiNum)).replace("-", "").addComma() + "%</span>)</span>";
	return "<strong style='color: " + $b.bulletColorR + ";'>" + LDname + ": " + String($a.dataContext[$b["valueField"]]).addComma() + "</span> " + "<span style='font-size: 11px;'>(<span class='ui_fluc before " + upNdn + " '>" + String((sensiNum)).replace("-", "").addComma() + "%</span>)</span></strong>";
}

// Treemap
$.fn.extend({
	setTreemap: function ($data, evt) {
		var total = $data.reduce(function (a, b, c) {
			if (!a) a = 0;
			return parseInt(a) + parseInt(b.value);
		});
		var tg = $(this);
		$(this).treemap($data, {
			smallestFontSize: 12,
			startingFontSize: 12,
			nodeClass: function ($node, $box, $itemLen) {
				var result = "";

				if ($box.width() < 30 || $box.height() < 30) result = "full hide ";
				// else if( $box.width() < 70 || $box.height() < 90 ) result = "hide ";

				if ($node.fill) {
					$box.css({ "background-color": $node.fill });
					return result;
				}

				var len = $itemLen - 5;
				var lenAddCnt = len / 5;
				if ($box.index() == 0) return result + "share_1";
				else if ($box.index() == 1) return result + "share_2";
				else if ($box.index() == 2) return result + "share_3";
				else if ($box.index() == 3) return result + "share_4";
				else if ($box.index() == 4) return result + "share_5";
				else if ($box.index() > 4 && $box.index() <= 5 + (lenAddCnt * 1)) return result + "share_6";
				else if ($box.index() > 5 + (lenAddCnt * 1) && $box.index() <= 5 + (lenAddCnt * 2)) return result + "share_7";
				else if ($box.index() > 5 + (lenAddCnt * 2) && $box.index() <= 5 + (lenAddCnt * 3)) return result + "share_8";
				else if ($box.index() > 5 + (lenAddCnt * 3) && $box.index() <= 5 + (lenAddCnt * 4)) return result + "share_9";
				else return result + "share_10";
			},
			ready: function () {
				$(this.$div).find(".treemap-item").each(function (idx) {
					if ($(this).width() > $(this).parent().width() || $(this).height() > $(this).parent().height()) {
						// addPcnt( $( this ) );
						// $( this ).parent().addClass( "text-hide" );
						// $( this ).parent().addClass( "hide" );
					} else {
						// $( this ).css({
						// 	"margin-top": ( $( this ).parent().height() / 2 ) - ( $( this ).height() / 4 ) ,
						// });
						$(this).css("margin-top", (($(this).parent().height() - $(this).height()) / 2));
					}

					if ($data[idx].fontColor) {
						$(this).find(".proper_st").css({ "color": $data[idx].fontColor });
						$(this).find(".infos").css({ "color": $data[idx].fontColor });
						$(this).find(".perc").css({ "color": $data[idx].fontColor });
					}

					addVal($(this));
					function addVal(th) {
						var el = $("<span class='cnt'></span>");
						el.text(" (" + $data[idx].value + ")");
						th.find(".proper_st").append(el);
					}

				});


				this.$div.find(".treemap-node").each(function ($idx) {
					var idx = tg.attr("id") + "_" + $idx;
					var datas = $data[$idx];
					var tooltipL = ""
						+ datas.label
						+ "<span class='row'>" + datas.value
						+ "		<span class='percnt'> (" + (datas.value / total * 100).toFixed(1) + "%)</span>"
						+ "</span>"
					$(this).attr("data-bubble-id", idx);
					var bubbleBox = $("<div class=\"bubble_box is-ac\" data-bubble-for=\"" + idx + "\" data-pos=\"CT\"><span class=\"arrow\"></span><div class=\"wrap\">" + tooltipL + "</div></div>");
					bubbleBox.css({ "border-color": $data[$idx].fill });
					bubbleBox.find(".arrow:after").css({ "border-color": $data[$idx].fill });
					tg.parent().find(".treemap_bubble_wrap").append(bubbleBox);

				});
				setBubble(tg.parent());
			},
			//mouseenter: evt_treemap_mouseEnter,
			//mouseleave: evt_treemap_mouseLeave,
			//mousemove: evt_treemap_mouseMove,
			click: function (e, b) {
				if (evt) evt(e, b);
			},
			itemMargin: 1
		});

	}
});
function setBubble($tg) {
	$tg.find(".bubble_box").each(function () {
		var activeChk = false;
		var bubbleBox = $(this);
		var code = $(this).data("bubble-for");
		var btn = $(document).find("*[data-bubble-id=\"" + code + "\"]");

		if (btn.data("action") == "click") {
			btn.click(function () {
				activeChk = !activeChk;
				hndl_bubbleBox();
			});
		} else {
			btn.hover(function ($e) {
				if ($e.type == "mouseenter") activeChk = true;
				else activeChk = false;
				hndl_bubbleBox();
			});
		}

		function hndl_bubbleBox() {
			if (activeChk) {
				var pos = getPos(bubbleBox.data("pos"));
				bubbleBox.css({ "top": (pos.top) - 60, "left": pos.left }).fadeIn(120);
			} else {
				bubbleBox.fadeOut(120);
			}
		}

		function getPos($type) {
			var result = {};
			var space = 11;
			result.top = (btn.position().top + (btn.outerHeight() / 2)) - bubbleBox.outerHeight() - space + 50;
			result.left = (btn.position().left + (btn.outerWidth() / 2) - (bubbleBox.outerWidth() / 2));
			return result;
		}
	});
}

// Location Nav
function locationMove($id) {
	var top = $($id).offset().top - (($("#top_searchs").outerHeight()) + 80);
	$("html, body").animate({ "scrollTop": top }, 400, "easeInOutExpo");
}