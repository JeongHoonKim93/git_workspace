<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<section id="popup_rel_info" class="popup_item">
	<div class="header">
        <h2>관련정보</h2>
        <a href="#" class="close" onclick="popupMngr.close( $( this ).parents( '.popup_item' ).eq( 0 ) );return false;" title="닫기">팝업닫기</a>
	</div>
	<div class="content ui_loader_container">
		<div class="wrap">
            <div class="ui_function">
                <div class="rc">
                    <div class="dcp">
                        <input type="text" id="popup_search_02">
                    </div>
                    <button type="button" class="ui_btn is-icon-only"><span class="icon">&#xe005;</span></button>
                </div>
            </div>
			<div class="ui_brd_list">
				<div class="header no_bg">
					<div class="rc">
						<!-- <span class="page_info"><strong><span class="count">0</span> 건</strong>, <span><span class="count">1</span>/<span class="count">1</span> Page</span></span> -->
						<span class="page_info">Total : 115 (<em>1</em> / 29p)</span>
                    </div>
				</div>
				<div class="wrap">
					<table>
                        <colgroup>
                            <col style="width:90px">
                            <col>
                            <col style="width:130px">
                            <col style="width:60px">
                        </colgroup>
                        <thead>
                        <tr>
                            <th scope="col"><span>출처</span></th>
                            <th scope="col"><span>제목</span></th>
                            <th scope="col"><span>수집일시</span></th>
                            <th scope="col"><span>감성</span></th>
                        </tr>
                        </thead>
                        <tbody>
                            <!-- 데이터 없는 경우
                            <tr><td colspan="4" class="no_over"></td></tr>
                            <tr><td colspan="4" class="no_over"></td></tr>
                            <tr><td colspan="4" class="no_over"></td></tr>
                            <tr><td colspan="4" class="no_over"></td></tr>
                            <tr><td colspan="4" class="no_over no_data in_list"><span class="ui_no_data_txt">키워드가 없습니다.</span></td></tr>
                            <tr><td colspan="4" class="no_over"></td></tr>
                            <tr><td colspan="4" class="no_over"></td></tr>
                            <tr><td colspan="4" class="no_over"></td></tr>
                            <tr><td colspan="4" class="no_over"></td></tr>
                            <tr><td colspan="4" class="no_over"></td></tr>
                            -->
                            <tr>
                                <td>헤럴드경제</td>
                                <td class="title is-default-pad">
                                    <a href="#" class="lnk clr_00" target="_blank" title="상담사 잘만났네요"><span>상담사 잘만났네요</span></a>
                                </td>
                                <td>2021-07-21</td>
                                <td><span class="ui_ico_senti is-positive"></span></td>
                            </tr>
                            <tr>
                                <td>헤럴드경제</td>
                                <td class="title is-default-pad">
                                    <a href="#" class="lnk clr_00" target="_blank" title="상담사 잘만났네요"><span>상담사 잘만났네요</span></a>
                                </td>
                                <td>2021-07-21</td>
                                <td><span class="ui_ico_senti is-negative"></span></td>
                            </tr>
                            <tr>
                                <td>헤럴드경제</td>
                                <td class="title is-default-pad">
                                    <a href="#" class="lnk clr_00" target="_blank" title="상담사 잘만났네요"><span>상담사 잘만났네요</span></a>
                                </td>
                                <td>2021-07-21</td>
                                <td><span class="ui_ico_senti is-neutral"></span></td>
                            </tr>
                            <tr><td colspan="4" class="no_over"></td></tr>
                            <tr><td colspan="4" class="no_over"></td></tr>
                            <tr><td colspan="4" class="no_over"></td></tr>
                            <tr><td colspan="4" class="no_over"></td></tr>
                            <tr><td colspan="4" class="no_over"></td></tr>
                            <tr><td colspan="4" class="no_over"></td></tr>
                            <tr><td colspan="4" class="no_over"></td></tr>
                        </tbody>
					</table>
				</div>
				<div class="footer">
					<div class="ui_paginate">
						<div class="in_wrap">
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
		<div class="ui_loader"><span class="loader"></span></div>
		<!-- // Loader -->
	</div>
</section>