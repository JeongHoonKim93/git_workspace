<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<section id="popup_issue" class="popup_item">
	<div class="header">
		<h2>관련정보</h2>
		<a href="#" class="close" onclick="popupMngr.close( $( this ).parents( '.popup_item' ).eq( 0 ) );return false;">팝업닫기</a>
	</div>
	<div class="content ui_loader_container">
		<div class="wrap">
			<div class="ui_brd_list">
				<div class="header no_bg">
					<div class="rc">
						<div class="dcp">
							<select id="issue_brd_sort_01" class="ui_select">
								<option>전체</option>
								<option>영업</option>
								<option>서비스</option>
								<option>품질</option>
							</select><label for="issue_brd_sort_01"></label>
						</div>
						<a href="#" class="ui_btn small" title="이슈등록" onclick="inPopupMngr.open( '#add_issue' );return false;"><span>이슈등록</span></a>
						<button type="button" class="ui_btn small" title="Excel 다운로드"><span><span class="icon download">Excel 다운로드</span></span><span class="in_loader">Loading</span></button>
					</div>
				</div>
				<div class="infos">
					<div class="rc">
						<span class="page_info"><strong><span class="ui_numeric">0</span>건</strong>, <span><span class="ui_numeric">1</span>/<span class="ui_numeric">1</span> Page</span></span>
					</div>
				</div>
				<div class="wrap">
					<table>
					<caption>연관키워드 목록</caption>
					<colgroup>
					<col style="width:90px">
					<col style="width:120px">
					<col>
					<col style="width:50px">
					</colgroup>
					<thead>
					<tr>
					<th scope="col"><span>날짜</span></th>
					<th scope="col"><span>구분</span></th>
					<th scope="col"><span>상세내용</span></th>
					<th scope="col"><span>삭제</span></th>
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
					<td>2017-01-01</td>
					<td>영업</td>
					<td class="ui_al"><a href="#" class="lnk"><span>상세내용</span></a></td>
					<td><button type="button" class="ui_mini_btn" onclick="msgMngr.send( '선택한 항목을 삭제하시겠습니까?', '삭제', 1, 2, function( $result ){console.log( '사용자 리턴 값 : ' + $result );})" title="삭제"><span class="icon del">삭제</span></button></td>
					</tr>
					<tr>
					<td>2017-01-01</td>
					<td>서비스</td>
					<td class="ui_al"><a href="#" class="lnk"><span>상세내용</span></a></td>
					<td><button type="button" class="ui_mini_btn" onclick="msgMngr.send( '선택한 항목을 삭제하시겠습니까?', '삭제', 1, 2, function( $result ){console.log( '사용자 리턴 값 : ' + $result );})" title="삭제"><span class="icon del">삭제</span></button></td>
					</tr>
					<tr>
					<td>2017-01-01</td>
					<td>품질</td>
					<td class="ui_al"><a href="#" class="lnk"><span>상세내용</span></a></td>
					<td><button type="button" class="ui_mini_btn" onclick="msgMngr.send( '선택한 항목을 삭제하시겠습니까?', '삭제', 1, 2, function( $result ){console.log( '사용자 리턴 값 : ' + $result );})" title="삭제"><span class="icon del">삭제</span></button></td>
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
		<div class="ui_loader"><span class="loader">Load</span></div>
		<!-- // Loader -->

		<!-- Inner 팝업 -->
		<div id="inPopup_container">
			<div class="bg"></div>

			<!-- 이슈 상세관리 -->
			<jsp:include page="./inPopup_add_issue.jsp" flush="false" />
			<!-- // 이슈 상세관리 -->
		</div>
		<!-- // Inner 팝업 -->
	</div>
</section>