/*!
 *
 * @author: GUNI, h2dlhs@realsn.com
 **/


(function ($) {

    var msClass = function ($el, $options) {

        var id;
        var label;
        var active = false;
        var isFirst = true;

        var $select;
        var $options;
        var $input;
        var $searchInputWrap, $searchInput;
        var $all, $list, $btns, $btn_reset, $btn_confirm;


        this.init = function () {
            build();
        }

        // **********		Build		************************************************************************************************ //
        function build() {
            id = $el.find("> select").attr("id");
            label = $el.attr("data-label") || "";

            $select = $el.find("> select");
            $options = $select.find("option");
            $input = $el.find("> label");
            $list = $("<div class='list' />");

            $searchInput = $('<input id="' + id + '_search" placeholder="검색어를 입력해주세요" required>');
            if ($el.attr("data-autocomplete")) {
                $searchInputWrap = $('<div class="search" />')
                $searchInputWrap.append($searchInput);
                $searchInputWrap.append('<button type="button" class="btn_reset">초기화</button>');
                $list.append($searchInputWrap);
                $searchInput.keyup(evt_inputKeyUp);
                $searchInputWrap.find(".btn_reset").click(evt_inputReset);
            }

            $list.append('<div class="all" />');
            $list.find(".all").append('<div class="dcp_switch is-small"><input id="' + (id + '_all') + '" type="checkbox"><label for="' + (id + '_all') + '"><span class="anchor"></span><strong class="txt">' + (label + ' 전체') + '</strong></label></div>');
            $all = $list.find(".all input");
            $all.unbind("change", evt_allChange).change(evt_allChange);

            $btns = $('<div class="btns" />');
            $btn_reset = $('<button type="button" class="ui_btn"><span class="txt">선택 초기화</span></button>');
            $btn_confirm = $('<button type="button" class="ui_btn is-color-point1" style="width: 90px"><span class="txt">확인</span></button>');
            $btns.append($btn_reset);
            $btns.append($btn_confirm);

            $btn_reset.click(evt_resetClick);
            $btn_confirm.click(evt_confirmClick);

            // $list.append($btns);
            $el.append($list);

            new MutationObserver(function ($e) {
                $e.filter(function ($evt) {
                    if ($evt.attributeName == "data-value") hndl_change();
                    if ($evt.type == "childList" && $evt.target.tagName.toLowerCase() == "select") drawList();
                })
            }).observe($el[0], { attributes: true, attributeFilter: ['data-value'], childList: true, characterData: true, subtree: true });

            $input.click(evt_inputClick);

            // 목록 최소 사이즛 설정
            $list.css("min-width", $input.outerWidth());

            drawList();

            // Scroll Event
            $(document).scroll(function () {
                active = false;
                hndl_list();
            })

            // Resize Event
            $(window).resize(function () {
                active = false;
                hndl_list();
            })
        }

        function drawList() {
            $options = $select.find("option");

            // 초기값 설정
            $options.each(function ($idx) {
                if ($el.attr("data-value") && $el.attr("data-value").strToArr().indexOf(this.value) >= 0) this.selected = true;
            })
            $list.find("ul input").each(function ($idx) {
                if ($el.attr("data-value") && $el.attr("data-value").strToArr().indexOf(this.value) >= 0) this.checked = true;
            })
            if ($options.length == $el.attr("data-value").strToArr().length) $all[0].checked = true;
            else $all[0].checked = false;

            // 기존 목록 제거
            $list.find("> ul input").unbind("change", evt_itemChange);
            $list.find("> ul").remove();

            var data = getList();

            // 목록 생성
            var ul = $('<ul class="is-dscroll" />');
            $list.find("> .all").after(ul);
            var items = drawItems(data);
            ul.append(items);
            ul.find("input").change(evt_itemChange);

            if (!isFirst) $el[0].setAttribute("data-value", $select.val() && $select.val().length > 0 ? $select.val().toString() : "");

            // 결과값 렌더
            rendered();

            isFirst = false;
        }

        function drawItems($data, $pid) {
            var result = "";
            $data.filter(function ($item, $idx) {
                result += '<li>';
                result += '<div class="dcp is-xsmall"><input id="' + (id + '_' + $idx) + '" type="checkbox" value="' + $item.code + '" ' + ($item.code && $el.attr("data-value").strToArr().indexOf($item.code) >= 0 ? "checked" : "") + '><label for="' + (id + '_' + $idx) + '"><span>' + getHilightText($item.name) + '</span></label></div>';
                result += '</li>';
            });
            return result;
        }

        function rendered() {
            var result = $select.val() ? $list.find("ul input:checked + label").eq(0).text() + ($select.val().length > 1 ? " + " + ($select.val().length - 1) : "") : label + " 선택없음";
            if ($select.val() && $select.val().length == $options.length) result = label + " 전체";
            $select.trigger("change");
            $input.text(result);
        }

        // 매칭 키워드 목록 반환
        function getList($data) {
            var result = [];
            $options.each(function ($idx) {
                if ($(this).text().indexOf($searchInput.val()) >= 0) {
                    result.push({
                        code: $(this).val(),
                        name: $(this).text()
                    })
                }
            });

            return result;
        }

        function getAllValues($type) {
            if ($type) {
                var result = [];
                $options.each(function ($idx) {
                    result.push($(this).val());
                })
                return result;
            }

            var result = "";
            $options.each(function ($idx) {
                if ($idx > 0) result += ",";
                result += $(this).val();
            })
            return result;
        }

        // 매칭 키워드 변환 후 반환
        function getHilightText($text) {
            if ($searchInput.val() == "") return $text;

            var result = String($text);
            var txt = new RegExp($searchInput.val(), "gi");
            result = result.replace(txt, function ($txt) {
                return '<span class="ui_text_hilight">' + $txt + '</span>'
            })
            return result;
        }




        // **********		Hndler		************************************************************************************************ //
        function hndl_list() {
            setRePos();
            if (active) {
                $searchInput.val("");
                drawList();
                $el.addClass("is-active");
            } else {
                $el.removeClass("is-active");
            }
        }

        function hndl_change() {
            var sel = $el.attr("data-value").strToArr();
            $options.each(function () {
                if (sel.indexOf(this.value) >= 0) this.selected = true;
                else this.selected = false;
            });
            $list.find("ul input").each(function () {
                if (sel.indexOf(this.value) >= 0) this.checked = true;
                else this.checked = false;
            });

            if (sel.length == $options.length) $all[0].checked = true;
            else $all[0].checked = false;

            rendered();

            if ($el.attr("data-change")) {
                eval($el.attr("data-change") + "('" + $el.attr("data-value") + "', " + id + ")");
            }
        }

        function setRePos() {
            if (!$input.is(":visible")) return false;

            var pos = {};
            pos.top = $input.offset().top - $(document).scrollTop() + $input.outerHeight();
            pos.left = $input.offset().left - $(document).scrollLeft();

            if (pos.top + $list.outerHeight() > $(window).height()) pos.top -= $list.outerHeight() + $input.outerHeight() + 2;
            if (pos.left + $list.outerWidth() > $(window).width()) pos.left -= $list.outerWidth() - $input.outerWidth();

            if (pos.top < 0) pos.top = $input.offset().top - $(document).scrollTop() + $input.outerHeight();

            $list.css({ top: pos.top, left: pos.left })
        }




        // **********		Event		************************************************************************************************ //
        function evt_inputClick($e) {
            active = !active;
            hndl_list();

            $(document).unbind("click", evt_docClick);
            if (active) $(document).bind("click", evt_docClick);
        }
        function evt_docClick($e) {
            if ($($e.target).closest($el).length == 0) {
                $(document).unbind("click", evt_docClick);
                active = false;
                hndl_list();
            }
        }

        // Item Change
        function evt_itemChange($e) {
            var dv = ""
            $list.find("ul input:checked").each(function ($idx) {
                if ($idx > 0) dv += ",";
                dv += this.value;
            })
            $el[0].setAttribute("data-value", dv);
        }

        function evt_allChange($e) {
            if (this.checked) {
                $el[0].setAttribute("data-value", getAllValues())
            } else {
                $el[0].setAttribute("data-value", "")
            }
        }

        // Btns Click
        function evt_resetClick($e) {
            $el[0].setAttribute("data-value", "");
        }
        function evt_confirmClick($e) {
            active = false;
            hndl_list();
        }

        // Search Input Reset
        function evt_inputReset($e) {
            $searchInput.val("");
            drawList();
        }

        // Search Input Key Event
        function evt_inputKeyUp($e) {
            if (active) {
                drawList();
            }
        }





        // **********		Out Method		************************************************************************************************ //
        this.update = function ($value) {
            console.log("update");
            //reset();
        }



    };

    $.fn.mselect = function ($options) {
        return this.each(function () {
            var ms = new msClass($(this), $options);
            $.data(this, 'mselect', ms);
            $(document).ready(ms.init);
        });
    }
})(jQuery);
