/*
	이슈등록 팝업창 스크립트
*/

$(function(){
	loding();
	
	if($("#mode").val() != "update_multi" ){
		$('[name=txt_relationkey]').autocomplete($("#streamKey").val().split(','));
	}
	

	if($("#mode").val() == "new" ){
		$('[name=md_site_name]').autocomplete($("#autoCompleteSite").val().split(','));
	}
	
	if($("#typeCode1_1").is(":checked")){
		$("[name=hotel]").css("display","");
	}else{
		$("[name=hotel]").css("display","none");
	}		
			
	if($("#typeCode1_2").is(":checked")){
		$("[name=TR]").css("display","");
	}else{
		$("[name=TR]").css("display","none");
	}	
	
	if($("#typeCode1_3").is(":checked")){
		$("[name=SHP]").css("display","");
	}else{
		$("[name=SHP]").css("display","none");
	}	
		
	if($("#typeCode1_4").is(":checked")){
		$("[name=SBTM]").css("display","");
	}else{
		$("[name=SBTM]").css("display","none");
	}	
		
	if($("#typeCode1_5").is(":checked")){
		$("[name=CEO]").css("display","");
	}else{
		$("[name=CEO]").css("display","none");
	}	
		
	if($("#typeCode1_6").is(":checked")){
		$("[name=IR]").css("display","");
	}else{
		$("[name=IR]").css("display","none");	
	}	
		
	if($("#typeCode1_7").is(":checked")){
		$("[name=ESG]").css("display","");
	}else{
		$("[name=ESG]").css("display","none");	
	}	

	/* if($("[name=typeCodeCheckbox_5]").val("5,2").is(":checked")){
	 $("#commenttext").css("display","table-row");
		}else{
	 $("#commenttext").css("display","none");
	}*/
	
});

function loding(){
	 var imgObj = document.getElementById("sending");
	 imgObj.style.left = (document.body.clientWidth / 2) - (imgObj.width / 2);
	 imgObj.style.top = (document.body.clientHeight / 2) - (imgObj.height / 2);
	 imgObj.style.display = 'none'; 

	remakeRelationword();
}

function getTrend(type){
	if($("#tr_typeCode"+type+"_trend").css("display") == "none"){
		$("#tr_typeCode"+type+"_trend").show();	
	} else {
		$("#tr_typeCode"+type+"_trend").hide();
	}
}

function getInfoAttr(type){
	if($("#tr_typeCode"+type+"_infoAttr").css("display") == "none"){
		$("#tr_typeCode"+type+"_infoAttr").show();	
	} else {
		$("#tr_typeCode"+type+"_infoAttr").hide();
	}
}


function changeDepth(pType, depth){
	var tr_value;
	
	$("[name=tr_typeCode_"+pType+"]").each(function(){
		tr_value = $(this).attr("value").split(",");
		if(tr_value[1]>depth){
			$("#tr_typeCode"+pType+"_subDepth_"+tr_value[1]).hide();
			$("#td_typeCode"+pType+"_subDepth_"+tr_value[1]).empty();
			
		}
	
	});
	
}

function chk_selected(type, code, depth, pType){
	var reuslt = false;
	var focus;
	var target;
	
	//1뎁스일경우
	if(depth=="1"){
		focus = "#focus_"+pType+"_"+depth;
		target = "typeCodeCheckbox_"+type;
		//체크해제
		if($(focus).val() == type+","+code ){
			$("input:radio[name='"+target+"']").attr("checked", false);	
			$(focus).val("");		
			reuslt = true;
		}else{
			$(focus).val(type+","+code);
			
		}
	//1뎁스 이상
	}else{
		focus = "#focus_"+pType+"_"+depth;
		target = "input_infoAttr_"+type;
		//체크해제
		if($(focus).val() == type+","+code ){
			$("input:radio[name='"+target+"']").attr("checked", false);	
			$(focus).val("");		
			reuslt = true;
		}else{
			$(focus).val(type+","+code);
			
		}
	}
	
	//뎁스 닫기
	changeDepth(pType, depth)
	return reuslt;
	
}

//성향 코드 체크해제 여부
function chk_senti(param){
	var focus = "#focus_senti";
	var target = "typeCodeCheckbox_senti";
	//체크해제
	if($(focus).val() == param ){			
		$("input:radio[name='"+target+"']").attr("checked", false);	
		$(focus).val("");		
		reuslt = true;
	}else{
		$(focus).val(param);
		
	}
}

//보고서 전송여부 코드 체크해제 여부
function chk_trans(param){
	var focus = "#focus_trans";
	var target = "typeCodeCheckbox_trans";
	
	//체크해제
	if($(focus).val() == param ){			
		$("input:radio[name='"+target+"']").attr("checked", false);	
		$(focus).val("");		
		reuslt = true;
	}else{
		$(focus).val(param);
		
	}
}

//영향력 코드 체크해제 여부
function chk_clout(param){
	var focus = "#focus_clout";
	var target = "typeCodeCheckbox_clout";
	
	//체크해제
	if($(focus).val() == param ){			
		$("input:radio[name='"+target+"']").attr("checked", false);	
		$(focus).val("");		
		reuslt = true;
	}else{
		$(focus).val(param);
		
	}
}

//출처 코드 체크해제 여부
function chk_sitegroup(param){
	var focus = "#focus_sitegroup";
	var target = "typeCodeCheckbox_sitegroup";
	
	//체크해제
	if($(focus).val() == param ){			
		$("input:radio[name='"+target+"']").attr("checked", false);	
		$(focus).val("");		
		reuslt = true;
	}else{
		$(focus).val(param);
		
	}
}

function getSubDepth(type, code, depth, pType){
	var sub_depth = parseInt(depth)+parseInt(1);
	var fix_type = '18';
	//var target = "typeCodeCheckbox_6";
	if(!chk_selected(type, code, depth, pType)){
	
		$.ajax({
			url: "./aj_issue_sub_option.jsp"
		,	type : "POST"
		,	dataType : "html"
		,	data : {
					"parentDepth":depth,
					"parentType":pType,
					"targetType":type,
					"targetCode" :code				
					}
		, 	success: function(data){
				if(""!=data.trim()){
					$("#tr_typeCode"+pType+"_subDepth_"+sub_depth).show();
					$("#td_typeCode"+pType+"_subDepth_"+sub_depth).empty();
					$("#td_typeCode"+pType+"_subDepth_"+sub_depth).html(data);
				}
			}
		});
	}
	
	/*
	//고객사 요청 - 게시물 유형 하위 depth 클레임 or 문의를 선택했을 때, 고정연관어를 필수값으로 지정
	if($("#mode").val() == "update_multi" ){
	//멀티 이슈 수정 일 경우
		if(type==6){
			
			if(code==3 || code==4){
				$("#requisiteParentTypes").val("18");
				if($('input:radio[name=typeCodeCheckbox_6]:checked').length == 0){
					$("#typeTitle_"+fix_type).css("font-weight","normal");
					$("#requisiteParentTypes").val("");
					$("#Type_Check_18").attr("checked",false);
				}else{
					$("#typeTitle_"+fix_type).css("font-weight","bold");	
					$("#requisiteParentTypes").val("18");
					$("#Type_Check_18").attr("checked",true);

				}
			}else if(code!=3 && code!=4 || !$("[name=typeCodeCheckbox_6]").is(":checked")){
				$("#typeTitle_"+fix_type).css("font-weight","normal");
				$("#requisiteParentTypes").val("");
				$("#Type_Check_18").attr("checked",false);
			}
		}
	//멀티 이슈 수정 외
	}else{
		if(type==6){
			
			if(code==3 || code==4){
				$("#requisiteParentTypes").val("5,6,18");
				if($('input:radio[name=typeCodeCheckbox_6]:checked').length == 0){
					$("#typeTitle_"+fix_type).css("font-weight","normal");
					$("#requisiteParentTypes").val("5,6");
				}else{
					$("#typeTitle_"+fix_type).css("font-weight","bold");	
					$("#requisiteParentTypes").val("5,6,18");
				}
			}else if(code!=3 && code!=4 || !$("[name=typeCodeCheckbox_6]").is(":checked")){
				$("#typeTitle_"+fix_type).css("font-weight","normal");
				$("#requisiteParentTypes").val("5,6");
			}
		}
		
	}
	*/
}

function HyDisplay(type, code, depth, pType){
	
	//업데이트 멀티일 경우 '구분' 라디오 선택 시, '구분' 하위 체크박스 부분 전부 false
	if($("#mode").val() == "update_multi"){
		$("input:checkbox[name='hotel_chk']").attr("checked", false);	
		$("input:checkbox[name='TR_chk']").attr("checked", false);	
		$("input:checkbox[name='SHP_chk']").attr("checked", false);	
		$("input:checkbox[name='SBTM_chk']").attr("checked", false);	
		$("input:checkbox[name='CEO_chk']").attr("checked", false);	
		$("input:checkbox[name='IR_chk']").attr("checked", false);	
		$("input:checkbox[name='ESG_chk']").attr("checked", false);	
	}
	
	chk_selected(type, code, depth, pType);
	
	if($("#typeCode1_1").is(":checked")){
		$("[name=hotel]").css("display","");
	}else{
		$("input:radio[name='typeCodeCheckbox_3']").attr("checked", false);	
		$("input:radio[name='typeCodeCheckbox_4']").attr("checked", false);	
		$("input:radio[name='typeCodeCheckbox_5']").attr("checked", false);	
		$("input:radio[name='typeCodeCheckbox_6']").attr("checked", false);	
		$("[name=hotel]").css("display","none");	
	}		
			
	if($("#typeCode1_2").is(":checked")){
		$("[name=TR]").css("display","");
	}else{
		$("input:radio[name='typeCodeCheckbox_7']").attr("checked", false);	
		$("input:radio[name='typeCodeCheckbox_8']").attr("checked", false);	
		$("[name=TR]").css("display","none");	
	}	
	
	if($("#typeCode1_3").is(":checked")){
		$("[name=SHP]").css("display","");
	}else{
		$("input:radio[name='typeCodeCheckbox_9']").attr("checked", false);	
		$("input:radio[name='typeCodeCheckbox_10']").attr("checked", false);	
		$("[name=SHP]").css("display","none");	
	}	
		
	if($("#typeCode1_4").is(":checked")){
		$("[name=SBTM]").css("display","");
	}else{
		$("input:radio[name='typeCodeCheckbox_11']").attr("checked", false);	
		$("[name=SBTM]").css("display","none");	
	}	
		
	if($("#typeCode1_5").is(":checked")){
		$("[name=CEO]").css("display","");
	}else{
		$("input:radio[name='typeCodeCheckbox_12']").attr("checked", false);	
		$("[name=CEO]").css("display","none");	
	}	
		
	if($("#typeCode1_6").is(":checked")){
		$("[name=IR]").css("display","");
	}else{
		$("input:radio[name='typeCodeCheckbox_13']").attr("checked", false);	
		$("[name=IR]").css("display","none");	
	}	
		
	if($("#typeCode1_7").is(":checked")){
		$("[name=ESG]").css("display","");
	}else{
		$("input:radio[name='typeCodeCheckbox_14']").attr("checked", false);	
		$("[name=ESG]").css("display","none");	
	}	
}

String.prototype.trim = function(){
	return this.replace(/^\s\s*/,'').replace(/\s\s*$/,'');
};


function remakeRelationword() {
	//ajsx로 보내기
	$("#typeCode10").val($("select[name=typeCode10]").val());
	ajax.post("relationKeywordGroup.jsp", "fSend", "select_keyword");
}


//연관키워드 추가
var key_seq = 0;
function addKeyword(){
	//var typeCode = $("select[name=typeCode10]").val().split(",")[1];
	var keyword = $('#txt_relationkey').val().trim();	
	key_seq = $(".keyNm").length;

	var AddHtml = '';
	if(keyword != ''){
		key_seq++;
		
		AddHtml = "<span id='keyword_0_"+key_seq+"' class='keyNm'>"+keyword + "&nbsp;<img src=\"../../images/issue/delete_btn_01.gif\" style=\"vertical-align: middle; cursor:pointer;\" onclick=\"delKeyword('"+ key_seq +"',0);\">&nbsp;</span>";
		
		/*
		if(typeCode == '0') {
			AddHtml = "<span id='keyword_0_"+key_seq+"' class='keyNm'>"+keyword + "&nbsp;<img src=\"../../images/issue/delete_btn_01.gif\" style=\"vertical-align: middle; cursor:pointer;\" onclick=\"delKeyword('"+ key_seq +"',0);\">&nbsp;</span>";
		} else {
			AddHtml = "<span id='keyword_"+typeCode+"_"+key_seq+"' class='keyNm'>"+keyword + "&nbsp;<img src=\"../../images/issue/delete_btn_01.gif\" style=\"vertical-align: middle; cursor:pointer;\" onclick=\"delKeyword('"+ key_seq +"','"+typeCode+"');\">&nbsp;</span>";
		}
		*/
		
		//var AddHtml = "<span id='keyword_0_"+key_seq+"' class='keyNm'>"+keyword + "&nbsp;<img src=\"../../images/issue/delete_btn_01.gif\" style=\"vertical-align: middle; cursor:pointer;\" onclick=\"delKeyword('"+ key_seq +"',0);\">&nbsp;</span>";
		$("#remakeRelationwordText").append(AddHtml);
	}
	//$('#txt_relationkey').val('');
	$('#txt_relationkey').val('');
}

//연관키워드 추가
var key_seq = 0;
function addKeyword2(obj, typeCode, rkName){
	var keyword = rkName;	
	key_seq = $(".keyNm").length;
	if(keyword != ''){
		key_seq++;
		var AddHtml = "<span id='keyword_"+typeCode+"_"+key_seq+"' class='keyNm'>"+keyword + "&nbsp;<img src=\"../../images/issue/delete_btn_01.gif\" style=\"vertical-align: middle; cursor:pointer;\" onclick=\"delKeyword('"+ key_seq +"','"+typeCode+"');\">&nbsp;</span>";
		$("#remakeRelationwordText").append(AddHtml);
	}
	//$('#txt_relationkey').val('');
	$('#txt_relationkey').val('');
}


//연관키워드 삭제
function delKeyword(idx , typeCode){
	var target = "keyword_"+typeCode+"_"+idx;
	
	$("#"+target).remove();
	//$("#"+target).find(".keyNm").text('');		
	$("#"+target).find(".keyNm"+typeCode).text('');		
	
}

function delKeywordAuto(idx){
	var target = "keywordAuto_"+idx;
	
	$("#"+target).remove();
	$("#"+target).find(".keyNm3").text('');		
}

/*function vaildate_check(){
	var final_check = true;
	// 기본 체크박스 검사
	$("[name=typeCodeCheckbox]:checked").each(function(){
		final_check = false;
		var typecodeArr = $(this).val().split(",");
		var sub_check = true;
		
		if(!sub_check){
			final_check = false;
			return false;
		} else {
			final_check = true;
		}
		$("[name=input_infoAttr_"+typecodeArr[0]+"]").each(function(){
			sub_check = false;
			if($(this).is(":checked")){
				sub_check = true;
				return false;
			}
		});
		
		if(!sub_check){
			final_check = false;
			return false;
		} else {
			final_check = true;
		}
		//}
	});
	
	return final_check;
}*/

//항목 체크
function vaildate_check($requisiteParentTypes){
	
	
	var final_check = true;
	
	if ($("#mode").val() == "new" ){
		
		if($("[name=id_title]").val() == ""){
			alert("제목을 입력하세요.");
			final_check = false;
		}else if($("[name=id_url]").val() == ""){
			alert("URL을 입력하세요.");
			final_check = false;
		}else if($("[name=md_site_name]").val() == ""){
			alert("사이트이름을 입력하세요.");
			final_check = false;
		}else if($("[name=id_content]").val() == ""){
			alert("내용을 입력하세요.");
			final_check = false;
		}
		
		if(!final_check){
			return final_check;	
		}
		
	}
	
	var checkboxListArr = $requisiteParentTypes.split(",");
	//필수 항목 체크
	$(checkboxListArr).each(function(idx){
		//1뎁스
		if(!$("[name=typeCodeCheckbox_"+checkboxListArr[idx]+"]").is(":checked") ){
			var title = $("#typeTitle_"+checkboxListArr[idx]).text().replace("*","").trim();
			if(null != title && title !=""){
				alert("필수 체크 항목 '"+title+"'을 선택해주세요.");
				final_check = false;
				return final_check;
			}
		}
/*		
		if(checkboxListArr[idx] == '4' && !$("[name=input_infoAttr_5]").is(":checked")) {
			alert("수정 체크된  항목 '주제/속성2'의 값을 선택해주세요.");
			final_check = false;
			return final_check;
		}
*/		
	});	
	
	//성향 필수 항목
	if ( typeof $("input[name=typeCodeCheckbox_senti]:checked").val() == "undefined" ){
		alert("필수 체크 항목 '성향'을 선택해주세요.");
		final_check = false;
		return final_check;
	}
	
	//보고서 전송여부 필수 항목
	if ( typeof $("input[name=typeCodeCheckbox_trans]:checked").val() == "undefined" ){
		alert("필수 체크 항목 '보고서'를 선택해주세요.");
		final_check = false;
		return final_check;
	}
	
	//출처 필수 항목
	/*
	if ( typeof $("input[name=typeCodeCheckbox_sitegroup]:checked").val() == "undefined" ){
		alert("필수 체크 항목 '출처'를 선택해주세요.");
		final_check = false;
		return final_check;
	}
	*/
	
	//연관어 필수 항목
	if ($(".keyNm").length == 0 ){
		alert("필수 항목 '연관어'를 작성해주세요.");
		final_check = false;
		return final_check;
	}
	
	
	return final_check;
}

//항목 체크
function multi_vaildate_check($requisiteParentTypes, $check_type){
	var final_check = true;
	if(""!=$requisiteParentTypes){
		var checkboxListArr = $requisiteParentTypes.split(",");
		//필수 항목 체크
		$(checkboxListArr).each(function(idx){
			//1뎁스
			if(!$("[name=typeCodeCheckbox_"+checkboxListArr[idx]+"]").is(":checked")){
				alert("필수 체크 항목 '"+$("#typeTitle_"+checkboxListArr[idx]).text().replace("*","").trim()+"'을 선택해주세요.");
				final_check = false;
				return final_check;
			}
			
		});	
	}
	
	if(!final_check){
		return final_check;
	}
	
	if(""!=$check_type){
		var checkboxListArr = $check_type.split(",");
		//필수 항목 체크
		$(checkboxListArr).each(function(idx){
			//1뎁스
			if(!$("[name=typeCodeCheckbox_"+checkboxListArr[idx]+"]").is(":checked")){
				alert("수정 체크된  항목 '"+$("#typeTitle_"+checkboxListArr[idx]).text().replace("*","").trim()+"'의 값을 선택해주세요.");
				final_check = false;
				return final_check;
			}
/*			
			if(checkboxListArr[idx] == '4' && !$("[name=input_infoAttr_5]").is(":checked")) {
				alert("수정 체크된  항목 '주제/속성2'의 값을 선택해주세요.");
				final_check = false;
				return final_check;
			}
*/			
		});
	}
	
	
	//성향 필수 항목
	if ( $("input[id=Type_Check_clout]").is(":checked") && typeof $("input[name=typeCodeCheckbox_clout]:checked").val() == "undefined" ){
		alert("수정 체크된 항목 '영향력'을 선택해주세요.");
		final_check = false;
		return final_check;
	}

	//성향 필수 항목
	if ( $("input[id=Type_Check_senti]").is(":checked") && typeof $("input[name=typeCodeCheckbox_senti]:checked").val() == "undefined" ){
		alert("수정 체크된 항목 '성향'을 선택해주세요.");
		final_check = false;
		return final_check;
	}
	
	//보고서 전송여부 필수 항목
	if ( $("input[id=Type_Check_trans]").is(":checked") && typeof $("input[name=typeCodeCheckbox_trans]:checked").val() == "undefined" ){
		alert("수정 체크된 항목 '보고서'를 선택해주세요.");
		final_check = false;
		return final_check;
	}
	
	//출처 필수 항목
	/*
	if ( $("input[id=Type_Check_sitegroup]").is(":checked") && typeof $("input[name=typeCodeCheckbox_sitegroup]:checked").val() == "undefined" ){
		alert("필수 체크 항목 '출처'를 선택해주세요.");
		final_check = false;
		return final_check;
	}
	*/
	
	//연관어 필수 항목
	if ( $("input[id=Type_Check_relationkeyword]").is(":checked") && $(".keyNm").length == 0 ){
		alert("필수 항목 '연관어'를 작성해주세요.");
		final_check = false;
		return final_check;
	}
	
	return final_check;
}

function settingTypeCode(parentTypes){
	var radioListArr = parentTypes.split(",");
	//var checkboxListArr = parentChkboxTypes.split(",");
	var form_typeCodes = '';
	var tmp_type_n_depth ='';
	var tmp_type_n_depth_val = [];
	var form_relationkeyNames = '';
	/* 라디오 */
	//기존 라디오 박스, 중복없이
	$(radioListArr).each(function(idx){
		//1뎁스 
		$("[name=typeCodeCheckbox_"+radioListArr[idx]+"]:checked").each(function(){
			if(form_typeCodes == ''){
				form_typeCodes = $(this).val();
			} else {
				form_typeCodes += "@" + $(this).val();
			}
			
			//2뎁스 이상
			var typecodeArr = $(this).val().split(",");			
			//$("[name=input_infoAttr_"+typecodeArr[0]+"]:checked").each(function(){
			$("[data-group='"+typecodeArr[0]+"']:checked").each(function(){
				tmp_type_n_depth = $(this).data(["data-depth"]).val();
				console.log(tmp_type_n_depth);
				tmp_type_n_depth.split(",");
				tmp_type_n_depth_val = tmp_type_n_depth.split(",");
				if($("#tr_typeCode"+tmp_type_n_depth_val[0]+"_subDepth_"+tmp_type_n_depth_val[1]).css("display") != "none"){
					if(form_typeCodes == ''){
						form_typeCodes = $(this).val();
					} else {
						form_typeCodes += "@" + $(this).val();
					}
				}
			});
		});
		
	});	
	/* 라디오 end  */
	
	/* 체크박스 */
	//고객사 요청 중복허용 체크박스 - 2021.07
	/*
	$(checkboxListArr).each(function(idx){
		//1뎁스 
		$("[name=typeCodeCheckbox_"+checkboxListArr[idx]+"]:checked").each(function(){
			if(form_typeCodes == ''){
				form_typeCodes = $(this).val();
			} else {
				form_typeCodes += "@" + $(this).val();
			}
			
			//2뎁스 이상
			var typecodeArr = $(this).val().split(",");			
			//$("[name=input_infoAttr_"+typecodeArr[0]+"]:checked").each(function(){
			$("[data-group='"+typecodeArr[0]+"']:checked").each(function(){
				tmp_type_n_depth = $(this).data(["data-depth"]).val();
				tmp_type_n_depth.split(",");
				tmp_type_n_depth_val = tmp_type_n_depth.split(",");
				if($("#tr_typeCode"+tmp_type_n_depth_val[0]+"_subDepth_"+tmp_type_n_depth_val[1]).css("display") != "none"){
					if(form_typeCodes == ''){
						form_typeCodes = $(this).val();
					} else {
						form_typeCodes += "@" + $(this).val();
					}
				}
			});
		});
		
		
		// 연관키워드 바인딩
		$(".keyNm_pos_"+checkboxListArr[idx]).each(function(){
			var inputVal = $(this).text().trim();
			if(form_relationkeyNames != ''){
				form_relationkeyNames += '@'+checkboxListArr[idx]+":"+inputVal;
			} else {
				form_relationkeyNames = checkboxListArr[idx]+":"+inputVal;
			}
		});
		
		
		// 연관키워드(부정) 바인딩
		$(".keyNm_neg_"+checkboxListArr[idx]).each(function(){
			var inputVal = $(this).text().trim();
			if(form_relationkeyNames_neg != ''){
				form_relationkeyNames_neg += '@'+checkboxListArr[idx]+":"+inputVal;
			} else {
				form_relationkeyNames_neg = checkboxListArr[idx]+":"+inputVal;
			}
		});
		
	});	*/
	
	
	/* 체크박스 end  */
	
	
	/*// 연관키워드 바인딩
	$(".keyNm").each(function(){
		var inputVal = $(this).text().trim();
		if(form_relationkeyNames != ''){
			form_relationkeyNames += '@'+inputVal;
		} else {
			form_relationkeyNames = inputVal;
		}
	});
	
	// 연관키워드(부정) 바인딩
	$(".keyNm_neg").each(function(){
		var inputVal = $(this).text().trim();
		if(form_relationkeyNames_neg != ''){
			form_relationkeyNames_neg += '@'+inputVal;
		} else {
			form_relationkeyNames_neg = inputVal;
		}
	});*/
	
	// 연관키워드 바인딩
	$(".keyNm").each(function(){
		var inputVal = $(this).text().trim();
		//var inputVal = $(this).text().trim() + ":" + $(this).attr("id").split("_")[1];
		if(form_relationkeyNames != ''){
			form_relationkeyNames += '@'+inputVal;
		} else {
			form_relationkeyNames = inputVal;
		}
	});
	
	$("#cloutType").val($("input[name=typeCodeCheckbox_clout]:checked").val()); //영향력
	$("#sentiType").val($("input[name=typeCodeCheckbox_senti]:checked").val()); //성향
	$("#transType").val($("input[name=typeCodeCheckbox_trans]:checked").val()); //보고서 전송여부
	//$("#siteType").val($("input[name=typeCodeCheckbox_sitegroup]:checked").val()); //출처
	$("#typeCode14").val($("select[name=typeCode14]").val()); //주요이슈
	$("#typeCode7").val($("select[name=typeCode7]").val()); //보도자료명
	$("#typeCodes").val(form_typeCodes);
	$("#product_name").val($("#input_product_name").val());
	$("#relationkeyCode").val($("[name=typeCodeCheckbox_1]:checked").val());//연관키워드 코드 (구분)
	$("#relationkeyNames").val(form_relationkeyNames);//연관키워드
	//$("#relationkeyNames_neg").val(form_relationkeyNames_neg);//연관키워드(부정)
}

function settingTypeCode_multi(parentTypes){
	var radioListArr = parentTypes.split(",");
	//var checkboxListArr = parentChkboxTypes.split(",");
	var form_typeCodes = '';
	var tmp_type_n_depth ='';
	var tmp_type_n_depth_val = [];
	var form_relationkeyNames = '';
	
	$(radioListArr).each(function(idx){
		//1뎁스 
		$("[name=typeCodeCheckbox_"+radioListArr[idx]+"]:checked").each(function(){
			if(form_typeCodes == ''){
				form_typeCodes = $(this).val();
			} else {
				form_typeCodes += "@" + $(this).val();
			}
			
			//2뎁스 이상
			var typecodeArr = $(this).val().split(",");			
			//$("[name=input_infoAttr_"+typecodeArr[0]+"]:checked").each(function(){
			$("[data-group='"+typecodeArr[0]+"']:checked").each(function(){
				tmp_type_n_depth = $(this).data(["data-depth"]).val();
				tmp_type_n_depth.split(",");
				tmp_type_n_depth_val = tmp_type_n_depth.split(",");
				if($("#tr_typeCode"+tmp_type_n_depth_val[0]+"_subDepth_"+tmp_type_n_depth_val[1]).css("display") != "none"){
					if(form_typeCodes == ''){
						form_typeCodes = $(this).val();
					} else {
						form_typeCodes += "@" + $(this).val();
					}
				}
			});
		});
		
	});	
	
	$("#typeCodes").val(form_typeCodes);
	
	// 연관키워드 바인딩
	if ( $("input[id=Type_Check_relationkeyword]").is(":checked") ){
		
		
		$(".keyNm").each(function(){
			var inputVal = $(this).text().trim();
			if(form_relationkeyNames != ''){
				form_relationkeyNames += '@'+inputVal;
			} else {
				form_relationkeyNames = inputVal;
			}
		});
		$("#relationkeyNames").val(form_relationkeyNames);//연관키워드
	} else {
		$("#relationkeyNames").val("");//연관키워드
	}
	
	//영향력 항목
	if ( $("input[id=Type_Check_clout]").is(":checked") ){
		$("#cloutType").val($("input[name=typeCodeCheckbox_clout]:checked").val()); 
	}else{
		$("#cloutType").val("");
	}
	
	//성향 전송 필수 항목
	if ( $("input[id=Type_Check_senti]").is(":checked") ){
		$("#sentiType").val($("input[name=typeCodeCheckbox_senti]:checked").val()); 
	}else{
		$("#sentiType").val("");
	}
		
	//보고서 필수 항목
	if ( $("input[id=Type_Check_trans]").is(":checked") ){
		$("#transType").val($("input[name=typeCodeCheckbox_trans]:checked").val()); 
	}else{
		$("#transType").val(""); 
	}
	
	//주요이슈 항목
	if ( $("input[id=Type_Check_14]").is(":checked") ){
		$("#typeCode14").val($("select[name=typeCode14]").val()); 
	}else{
		$("#typeCode14").val(""); 
	}
	
	//보도자료명 항목
	if ( $("input[id=Type_Check_7]").is(":checked") ){
		$("#typeCode7").val($("select[name=typeCode7]").val()); 
	}else{
		$("#typeCode7").val(""); 
	}
	
	//연관키워드 코드 항목
	if ( $("input[id=Type_Check_1]").is(":checked") ){
		$("#relationkeyCode").val($("[name=typeCodeCheckbox_1]:checked").val());//연관키워드 코드 (구분)
	}else{
		$("#relationkeyCode").val(""); 
	}
	
	//$("#typeCode14").val($("select[name=typeCode14]").val()); //주요이슈
	//$("#typeCode7").val($("select[name=typeCode7]").val()); //보도자료명
	
}

function settingBasisInfo(){
	$("#param_id_title").val($("[name=id_title]").val());
	$("#param_id_url").val($("[name=id_url]").val());
	$("#param_md_site_name").val($("[name=md_site_name]").val());
	$("#param_id_regdate").val($("[name=id_regdate]").val());
	//$("#param_id_content").val($("[name=id_content]").text());
	$("#param_id_content").val($("[name=id_content]").val());
}

function save_issue(mode){
	var f  = document.fSend;	
	var parentTypes = $("#parentTypes").val();
	//var parentChkboxTypes = $("#parentChkboxTypes").val();
	var parentChkboxTypes = '';
	var requisiteParentTypes = $("#requisiteParentTypes").val();
	
/*	
	//다시 원복해야 할 것
	if(vaildate_check(requisiteParentTypes)){	
		settingBasisInfo();
		settingTypeCode(parentTypes, parentChkboxTypes);
		
		//var innerText = document.getElementById("txt_comment").value;
		//$("#txtcmt").val(innerText);
		
		document.getElementById("sending").style.display = '';
		$("#mode").val(mode);
		f.target='if_samelist';
		f.action='issue_data_prc.jsp';
	    f.submit();
	}else{
		document.getElementById("save_btn").style.visibility = 'visible';
	}
*/	
	settingBasisInfo();
	settingTypeCode(parentTypes, parentChkboxTypes);
	
	//var innerText = document.getElementById("txt_comment").value;
	//$("#txtcmt").val(innerText);

	document.getElementById("sending").style.display = '';
	$("#mode").val(mode);
	f.target='if_samelist';
	f.action='issue_data_prc.jsp';
	f.submit();
	
}

function save_multi_issue_update(mode){
	
	var f  = document.fSend;	
	var check_type = "";
	var check_relword_type = "";
	var parentTypesArr = $("#parentTypes").val().split(",");;
	//var parentChkboxTypesArr = $("#parentChkboxTypes").val().split(",");;
	
	var tmp_chk_type ="";
	$(".type_check").each(function(idx){
		//라디오박스
		if( $(this).is(":checked") ){
			tmp_chk_type = $(this).val();
			
			$(parentTypesArr).each(function(idx){
				if(tmp_chk_type  == parentTypesArr[idx]){
					if("" == check_type){
						check_type =tmp_chk_type;
					}else{
						check_type += ","+tmp_chk_type;
					}
				}
			})
			
			//체크박스
			/*
			$(parentChkboxTypesArr).each(function(idx){
				if(tmp_chk_type  == parentChkboxTypesArr[idx]){
					if("" == check_relword_type){
						check_relword_type =tmp_chk_type;
					}else{
						check_relword_type += ","+tmp_chk_type;
					}
				}
			})
			*/
		}
		
	});
	
	var requisiteParentTypes = $("#requisiteParentTypes").val();	
	if(multi_vaildate_check(requisiteParentTypes, check_type, check_relword_type)){	
		settingTypeCode_multi(check_type, check_relword_type);
	
		document.getElementById("sending").style.display = '';
		$("#mode").val(mode);
		f.target='if_samelist';
		f.action='issue_data_prc.jsp';
	    f.submit();
		
	}else{
		document.getElementById("save_m_btn").style.visibility = 'visible';
	}
}


function add_IC(type) {
	$("#param_ic_type").val(type);
	popup.openByPost('fSend','pop_issue_code_form.jsp', 550, 250,false,false,false,'add_IC');
}

/*
function change_trans(val1, val2) {
	var company_code = $("[name=typeCodeCheckbox_1]:checked").val();
	var board_type = $("[name=typeCodeCheckbox_6]:checked").val();
	
	if(val1 == 1) {
		if(val2 == 1 || val2 == 6) {
			$("#trans_yes").attr("checked",true);
			$("#trans_no").attr("checked",false);
			/*
			if(board_type != 'undefined' && board_type != '6,3' && board_type != '6,4' && board_type != '6,5') {
				$("#trans_yes").attr("checked",false);
				$("#trans_no").attr("checked",true);
			}
			*/
		/*	
		} else {
			//회사구분 - 대상, 풀무원, 오뚜기, 동원 선택시 데이터전송여부 '아니오'로 변경
			$("#trans_yes").attr("checked",false);
			$("#trans_no").attr("checked", true);			
		}
	} 
	
	if((company_code == '1,1' || company_code == '1,6')) {
		if(val1 == 6) {
			if(val2 == 3 || val2 == 4 || val2 == 5) {
				$("#trans_yes").attr("checked",true);
				$("#trans_no").attr("checked",false);
			} else {
				$("#trans_yes").attr("checked",false);
				$("#trans_no").attr("checked", true);	
			}
		}
	}
	
	//클레임 선택 시 성향 부정 디폴트값
	if(val1 == 6) {
		if(val2 == 3) {
			$("#senti_negative").attr("checked",true);
		} else {
			$("#senti_negative").attr("checked",false);
		}
	} 
}
*/