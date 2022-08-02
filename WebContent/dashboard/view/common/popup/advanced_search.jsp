<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="popupBox">
	<div class="bg"></div>
	<section id="popup_keyword_set" class="popup_item" style="width:780px;">
		<div class="header">
			<h2>고급 검색어 설정</h2>
			<button type="button" title="팝업닫기" class="close" onclick="popupMngr.close( $( this ).parents( '.popup_item' ).eq( 0 ) )">팝업닫기</button>
		</div>
		<div class="content ui_loader_container">
			<div class="wrap">
				<div class="ui_tab page_tab">
					<ul>
						<li>
							<input id="keyword_grp_01" name="keyword_grp" type="radio" value="1" checked="">
							<label for="keyword_grp_01" class="color_01">
								<span class="input">아이템</span>
								<span class="dummy"></span>
								<a href="#" class="ui_icon btn_edit" title="아이템명 수정"><span class="icon">&#xe008;</span></a>
								<a href="#" class="ui_icon btn_confirm" title="아이템명 저장"><span class="icon">&#xe053;</span></a>
							</label>
						</li>
						<li>
							<input id="keyword_grp_02" name="keyword_grp" type="radio" value="2">
							<label for="keyword_grp_02" class="color_02">
								<span class="input">아이템</span>
								<span class="dummy"></span>
								<a href="#" class="ui_icon btn_edit" title="아이템명 수정"><span class="icon">&#xe008;</span></a>
								<a href="#" class="ui_icon btn_confirm" title="아이템명 저장"><span class="icon">&#xe053;</span></a>
							</label>
						</li>
						<li>
							<input id="keyword_grp_03" name="keyword_grp" type="radio" value="2">
							<label for="keyword_grp_03" class="color_03">
								<span class="input">아이템</span>
								<span class="dummy"></span>
								<a href="#" class="ui_icon btn_edit" title="아이템명 수정"><span class="icon">&#xe008;</span></a>
								<a href="#" class="ui_icon btn_confirm" title="아이템명 저장"><span class="icon">&#xe053;</span></a>
								<!-- <a href="#" class="btn_delete" title="아이템 삭제"><span class="icon">삭제</span></a> -->
							</label>
						</li>
					</ul>
				</div>
				<div class="box_content">
					<!-- Basic Content -->
					<div class="tab_content" data-for="1">
						<!-- 키워드 입력 -->
						<div class="inputs">
							<div class="input_keyword">
								<div class="title_wrap">
									<span class="type">일반조건</span>
								</div>
								<textarea id="common_keyword_set_basic_ta_01" class="ta" placeholder="검색어를 입력해 주세요."></textarea><label for="common_keyword_set_basic_ta_01" class="ui_invisible">조건 키워드 입력</label>
							</div>
							<div class="input_keyword">
								<div class="title_wrap">
									<span class="type">구문조건</span>
								</div>
								<textarea id="common_keyword_set_basic_ta_02" class="ta" placeholder="검색어를 입력해 주세요."></textarea><label for="common_keyword_set_basic_ta_02" class="ui_invisible">구문 키워드 입력</label>
							</div>
							<div class="input_keyword">
								<div class="title_wrap">
									<span class="type">인접조건</span>
								</div>
								<textarea id="common_keyword_set_basic_ta_03" class="ta" placeholder="검색어를 입력해 주세요."></textarea><label for="common_keyword_set_basic_ta_03" class="ui_invisible">인접 키워드 입력</label>
							</div>
							<div class="input_keyword">
								<div class="title_wrap">
									<span class="type">제외조건</span>
									<!-- <button data-bubble-id="common_keyword_set_bubble_02" class="ui_btn xsmall only_icon ui_icon" title="도움말"><span class="icon help_small">-</span></button>
									<div class="ui_bubble_box" data-bubble-for="common_keyword_set_bubble_02" data-pos="CB" data-arrowcenter="true" style="width:350px;">
										<span class="arrow" >-</span>
										<div class="tip">
											<strong class="title">구문 조건 키워드</strong>
											<span class="txt">한 줄 내에 띄어쓰기로 구분된 단어들이 정확히 포함(구문 조건)된 경우 검색합니다.<br>줄바꿈 / <span class="ui_keycap">Enter</span> 로 구분된 키워드들 중 적어도 한 개가 포함(OR 조건)되면 검색합니다.</span>
											<div class="txt_v3">
												<p>
													<span>ex) 구문 조건 키워드 : </span>
													<span> 첫번째 단어<br>두번째 단어</span>
												</p>
												<p class="result"><span class="title">&#10132; 조합결과 :</span><span>("첫번째 단어") OR ("두번째 단어")</span></p>
												<p class="result"><span class="title">&#10132; 검색결과 :</span><span>'첫번째 단어', '첫번째단어', '두번째 단어' 또는 '두번째단어'가 포함된 문서</span></p>
											</div>
										</div>
									</div> -->
								</div>
								<textarea id="common_keyword_set_basic_ta_04" class="ta" placeholder="검색어를 입력해 주세요."></textarea><label for="common_keyword_set_basic_ta_04" class="ui_invisible">제외 키워드 입력</label>
							</div>
						</div>
						<!-- // 키워드 입력 -->
						<button class="ui_btn hl_blk save"><span class="txt">저장 및 닫기</span></button>
						<!-- 조합완료시 버튼 변경
						<button class="ui_btn complete"><span class="txt">키워드 조합 및 저장 완료</span></button>
						-->
						<!-- 도움말 -->
						<div class="ui_helper">
							<ul>
								<li>검색어를 여러개 입력시 줄바꿈으로 구분해주세요.</li>
								<li>
									조건설명
									<ul>
										<li>일반조건 : 입력한 “검색어”가 포함 될 경우 검색합니다.</li>
										<li>구문조건 : 입력한 “구문”이 포함 될 경우 검색합니다.</li>
										<li>인접조건 : 15글자 내에 입력한 “검색어”가 포함 될 경우 검색합니다.</li>
										<li>제외조건 : 입력한 “검색어”가 포함 될 경우 검색에서 제외합니다.</li>
									</ul>
								</li>
								<li>조건별 검색어는 최대 200자까지 입력 가능합니다.</li>
							</ul>
						</div>
						<!-- // 도움말 -->
					</div>
					<!-- // Basic Content -->
				</div>
			</div>
			<!-- <div class="footer">
				<div class="btns">
					<a href="#" class="ui_btn xlarge hl_point" onclick="saveFunc();return false;"><span class="txt">저장</span></a>
					<button class="ui_btn xlarge" onclick="popupMngr.close( $( this ).parents( '.popup_item' ).eq( 0 ) )"><span class="txt">취소</span></button>
				</div>
			</div> -->
		</div>
	</section>
</div>
