<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<section id="add_issue" class="inPopup_item">
	<div class="header">
		<h3>이슈 상세관리</h3>
		<button type="button" class="close" onclick="inPopupMngr.close( $( this ).parents( '.inPopup_item' ).eq( 0 ) )">팝업닫기</button>
	</div>
	<div class="content ui_loader_container">
		<div class="wrap">
			<div class="ui_brd_inputs on_top_bd input_grp">
				<table>
				<caption>입력</caption>
				<colgroup>
				<col style="width:80px">
				<col>
				</colgroup>
				<tbody>
				<tr>
				<th><span>날짜</span></th>
				<td>
					<div class="ui_datepicker"><div class="ui_datepicker_input"><input id="add_issue_dp" type="text" readonly><label for="add_issue_dp"></label></div></div>
				</td>
				</tr>
				<tr>
				<th><span>유형</span></th>
				<td>
					<div class="dcp">
						<select id="add_issue_type" class="ui_select">
							<option>영업</option>
							<option>서비스</option>
							<option>품질</option>
						</select><label for="add_issue_type"></label>
					</div>
				</td>
				</tr>
				<tr>
				<th class="ui_vt"><span>이슈</span></th>
				<td>
					<textarea id="add_issue_ta" class="ui_textarea" rows="3"></textarea><label for="add_issue_ta" class="ui_invisible">이슈내용 입력</label>
				</td>
				</tr>
				</tbody>
				</table>
			</div>
		</div>
		<div class="footer">
			<div class="btns">
				<button type="button" class="ui_btn big hl_black" onclick=""><span>저장</span></button>
				<button type="button" class="ui_btn big" onclick="inPopupMngr.close( $( this ).parents( '.inPopup_item' ).eq( 0 ) )"><span>취소</span></button>
			</div>
		</div>

		<!-- Loader -->
		<div class="ui_loader"><span class="loader">Load</span></div>
		<!-- // Loader -->
	</div>
</section>