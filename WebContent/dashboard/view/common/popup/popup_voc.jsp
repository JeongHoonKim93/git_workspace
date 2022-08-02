<%@page import="risk.dashboard.common.Common"%>
<%@page import="risk.util.ParseRequest"%>
<%@page import="com.sun.org.apache.xalan.internal.xsltc.runtime.Parameter"%>
<%@page import="risk.json.JSONObject"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
JSONObject rp = new Common().toJSONObject(request);
%>
<form method="post" id="fvoc" name="fvoc" >
<input type="hidden" name="nowPage_voc" id="nowPage_voc" value="1"/>
<input type="hidden" name="senti_option_voc" id="senti_option_voc" value=""/>
<input type="hidden" name="text_value_voc" id="text_value_voc" value=""/>
<input type="hidden" name="action_only" id="action_only" value="0"/>
<section id="popup_voc" class="popup_item">
	<div class="header">
        <h2><span id = 'voc_company'>현대카드 Daily VOC</span></h2>
        <a href="#" class="close" onclick="popupMngr.close( $( this ).parents( '.popup_item' ).eq( 0 ) );return false;" title="닫기">팝업닫기</a>
    </div>
    <div class="ui_tab" id="popup_radio">
        <ul id="sub_title">
            <li><input type="radio" id="popup_voc_tab_01" name="popup_voc_tab" value="1" checked><label for="popup_voc_tab_01"><span>상품&amp;서비스</span></label></li>
            <li><input type="radio" id="popup_voc_tab_02" name="popup_voc_tab" value="2"><label for="popup_voc_tab_02"><span>영업</span></label></li>
            <li><input type="radio" id="popup_voc_tab_03" name="popup_voc_tab" value="3"><label for="popup_voc_tab_03"><span>디지털</span></label></li>
            <li><input type="radio" id="popup_voc_tab_04" name="popup_voc_tab" value="4"><label for="popup_voc_tab_04"><span>Operation</span></label></li>
            <li><input type="radio" id="popup_voc_tab_05" name="popup_voc_tab" value="5"><label for="popup_voc_tab_05"><span>금소법</span></label></li>
            <li><input type="radio" id="popup_voc_tab_07" name="popup_voc_tab" value="7"><label for="popup_voc_tab_07"><span>발급/한도</span></label></li>
            <li><input type="radio" id="popup_voc_tab_06" name="popup_voc_tab" value="6"><label for="popup_voc_tab_06"><span>기타</span></label></li>
        </ul>        
    </div>
	<div class="content ui_loader_container" id="pop_voclist">
		<div class="wrap">
			<div class="ui_brd_list">
				<div class="header no_bg">
					<div class="rc">
                        <div class="ui_in_search"><input id="set_site_search_voc" type="text"><label for="set_site_search_voc" class="ui_invisible">검색어 입력</label><button class="btn_search" onclick="btn_search(); return false;"><span>검색</span></button></div>
                        <hr>
						<div class="dcp">
							<select id="rel_info_brd_sort_voc" class="ui_select" onchange="selectSenti();">
								<option value="0">전체</option>
								<option value="1">긍정</option>
								<option value="2">부정</option>
								<option value="3">중립</option>
							</select><label for="rel_info_brd_sort_voc"></label>
                        </div>
                        <hr>
                        <div class="issue_only">
                            <input type="checkbox" id="popup_voc_issue_only" onchange="voc_action_only()"><label for="popup_voc_issue_only">이슈만 보기</label>
                        </div>
                        <hr>
						<button type="button" class="ui_btn" title="Excel 다운로드"><span><span class="icon download" onclick="excel_download_voc(); return false;">Excel 다운로드</span></span><span class="in_loader">Loading</span></button>
					</div>
				</div>
				<div class="wrap">
					<table>
                        <colgroup>
                            <col style="width:40px">
                            <col>
                            <col style="width:36px">
                            <col style="width:90px">
                            <col style="width:80px">
                            <col style="width:124px">
                            <col style="width:30px">
                            <col style="width:84px">
                        </colgroup>
                        <thead>
                        <tr>
                            <th scope="col"><span>감성</span></th>
                            <th scope="col"><span>제목</span></th>
                            <th scope="col"><span>링크</span></th>
                            <th scope="col"><span>출처</span></th>
                            <th scope="col"><span>일시</span></th>
                            <th scope="col"><span>대응내역</span></th>
                            <th scope="col"><span>이슈체크</span></th>
                            <th scope="col"><span>진행상황</span></th>
                        </tr>
                        </thead>
                        <tbody id="voclist">
                            <!-- 데이터 없는 경우
                            <tr><td colspan="8" class="no_over"></td></tr>
                            <tr><td colspan="8" class="no_over"></td></tr>
                            <tr><td colspan="8" class="no_over"></td></tr>
                            <tr><td colspan="8" class="no_over"></td></tr>
                            <tr><td colspan="8" class="no_over no_data in_list"><span class="ui_no_data_txt">데이터가 없습니다.</span></td></tr>
                            <tr><td colspan="8" class="no_over"></td></tr>
                            <tr><td colspan="8" class="no_over"></td></tr>
                            <tr><td colspan="8" class="no_over"></td></tr>
                            <tr><td colspan="8" class="no_over"></td></tr>
                            <tr><td colspan="8" class="no_over"></td></tr>
                            -->
                            <tr>
                                <td><span class="ui_status_box is-positive">긍정</span></td>
                                <td class="title is-default-pad">
                                    <a href="#" class="lnk" target="_blank" title="현대카드 상담사 잘만났네요"><span>현대카드 상담사 잘만났네요</span></a>
                                </td>
                                <td>
                                    <a href="#" class="btn_cafe_p" title="PC 버전 링크">PC 버전 링크</a>
                                    <a href="#" class="btn_cafe_m" title="Mobile 버전 링크">Mobile 버전 링크</a>
                                </td>
                                <td>디시인사이드</td>
                                <td>01/01 11:33</td>
                                <td>
                                    <div class="ui_in_search is-confirm"><input id="popup_voc_brd_txt_01_01" type="text" placeholder="대응내역"><label for="voc_brd_txt_01_01" class="ui_invisible">이슈 입력</label><button title="저장" onmousedown="alert('저장')"><span>저장</span></button></div>
                                </td>
                                <td>
                                    <input id="popup_voc_brd_issue_01_01" type="checkbox" class="toggle_issue"><label for="popup_voc_brd_issue_01_01"></label>
                                </td>
                                <td class="ui_ar">
                                    <div class="dcp ui_ac" style="width:80px">
                                       <select id="popup_voc_brd_state_01_01" style="width:80px">
                                            <option value="0" selected>진행상황</option>
                                            <option value="1">개선완료</option>
                                            <option value="2">검토중</option>
                                            <option value="3">Drop</option>
                                        </select><label for="popup_voc_brd_state_01_01"></label>
                                    </div>
                                </td>
                            </tr>
                            <tr><td colspan="8" class="no_over"></td></tr>
                            <tr><td colspan="8" class="no_over"></td></tr>
                            <tr><td colspan="8" class="no_over"></td></tr>
                            <tr><td colspan="8" class="no_over"></td></tr>
                            <tr><td colspan="8" class="no_over"></td></tr>
                            <tr><td colspan="8" class="no_over"></td></tr>
                            <tr><td colspan="8" class="no_over"></td></tr>
                            <tr><td colspan="8" class="no_over"></td></tr>
                            <tr><td colspan="8" class="no_over"></td></tr>
                        </tbody>
					</table>
				</div>
				<div class="footer">
					<div class="ui_paginate">
						<div class="in_wrap" id="popvoc_paging">
							<a href="#" class="page_prev ui_disabled" onclick="return false">이전페이지</a>
							<a href="#" class="" onclick="return false;">1</a>
							<a href="#" class="active" onclick="return false;">2</a>
							<a href="#" onclick="return false;">3</a>
							<a href="#" onclick="return false;">4</a>
							<a href="#" onclick="return false;">5</a>
							<a href="#" onclick="return false;">6</a>
							<a href="#" onclick="return false;">7</a>
							<a href="#" onclick="return false;">8</a>
							<a href="#" onclick="return false;">9</a>
							<a href="#" onclick="return false;">10</a>
							<a href="#" class="page_next disabled" onclick="return false;">다음페이지</a>
						</div>
					</div>
				</div>
			</div>

		</div>

		<!-- Loader -->
		<div class="ui_loader"><span class="loader">Load</span></div>
		<!-- // Loader -->
	</div>
</section>
</form>