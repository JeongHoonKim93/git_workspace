/*
	이슈등록 팝업창 스크립트
*/

$(function(){
	loding();
	$('[name=txt_relationkey]').autocomplete($("#streamKey").val().split(','));
});

function loding(){
	 var imgObj = document.getElementById("sending");
	 imgObj.style.left = (document.body.clientWidth / 2) - (imgObj.width / 2);
	 imgObj.style.top = (document.body.clientHeight / 2) - (imgObj.height / 2);
	 imgObj.style.display = 'none'; 
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


function changeDepth(type, depth){
	var tr_value;
	
	$("[name=tr_typeCode_"+type+"]").each(function(){
		tr_value = $(this).attr("value").split(",");
		if(tr_value[1]>depth){			
			$("#tr_typeCode"+tr_value[0]+"_subDepth_"+tr_value[1]).hide();
		}
	});
	
}

function getSubDepth(type, code, depth, pType){
	var sub_depth = parseInt(depth)+parseInt(1);
	changeDepth(type, depth);
	
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

String.prototype.trim = function(){
	return this.replace(/^\s\s*/,'').replace(/\s\s*$/,'');
};

var key_seq = 0;
//연관키워드 추가
function addKeyword(obj, ptype){
	var keyword = $('#txt_relationkey_'+ptype).val().trim();
	if(keyword != ''){
		key_seq++;
		var AddHtml = "<span id='keyword_"+ptype+"_"+key_seq+"' class='keyNm'>"+keyword + "&nbsp;<img src=\"../../images/issue/delete_btn_01.gif\" style=\"vertical-align: middle; cursor:pointer;\" onclick=\"delKeyword('"+ptype+"','"+ key_seq +"');\"></span>";
		$("#relKeyList_"+ptype).append(AddHtml);
	}
	$('#txt_relationkey_'+ptype).val('');
}

//연관키워드 삭제
function delKeyword(ptype, idx){
	var target = "keyword_"+ptype+"_"+idx;
	
	$("#"+target).remove();
	$("#"+target).find(".keyNm").text('');		
	
}

function vaildate_check(){
	var final_check = true;
	// 기본 체크박스 검사
	$("[name=typeCodeCheckbox]:checked").each(function(){
		final_check = false;
		var typecodeArr = $(this).val().split(",");
		var sub_check = true;
		
		/*$("[name=input_trend_"+typecodeArr[0]+"]").each(function(){
			sub_check = false;
			if($(this).is(":checked")){
				sub_check = true;
				return false;
			}
		});*/
		
		if(!sub_check){
			final_check = false;
			return false;
		} else {
			final_check = true;
		}
		console.log("typecodeArr[0]>>"+typecodeArr[0]);
		//if(typecodeArr[0] != "8" && typecodeArr[0] != "2"){
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
}

function settingTypeCode(parentTypes){
	var checkboxListArr = parentTypes.split(",");
	var form_typeCodes = '';
	//var form_typeCodesDetail = '';
	//var form_typeCodesInfo = '';
	//var form_relationkeyNames = '';
	$(checkboxListArr).each(function(idx){
		$("[name=typeCodeCheckbox_"+checkboxListArr[idx]+"]:checked").each(function(){
			alert("1:"+$(this).val());
			if(form_typeCodes == ''){
				form_typeCodes = $(this).val();
			} else {
				form_typeCodes += "@" + $(this).val();
			}
			//var typeCode = $(this).val();
			var typecodeArr = $(this).val().split(",");
			
			$("[name=input_infoAttr_"+typecodeArr[0]+"]:checked").each(function(){
				alert("2:"+$(this).val());
				if(form_typeCodes == ''){
					form_typeCodes = $(this).val();
				} else {
					form_typeCodes += "@" + $(this).val();
				}
			});
			
			// 성향 코드 바인딩
			/*$("[name=input_trend_"+typecodeArr[0]+"]:checked").each(function(){
				if(form_typeCodesDetail == ''){
					form_typeCodesDetail = typeCode +","+$(this).val();
				} else {
					form_typeCodesDetail += "@" + typeCode +","+$(this).val();
				}
			});*/
		
			// 정보속성 코드 바인딩
			/*$("[name=input_infoAttr_"+typecodeArr[0]+"]:checked").each(function(){
				if(form_typeCodesInfo == ''){
					form_typeCodesInfo = typeCode + "," + $(this).val();
				} else {
					form_typeCodesInfo += "@" + typeCode + "," + $(this).val();
				}
			});*/
			
			// 연관키워드 바인딩
			/*var names = '';
			$("#relKeyList_"+typecodeArr[0]).each(function(){
				$(this).find(".keyNm").each(function(){
					var inputVal = $(this).text().trim();
					if(names != ''){
						names += ','+inputVal;
					} else {
						names = inputVal;
					}
				})
			});*/
			
			/*if(names != ''){
				if(form_relationkeyNames == ''){
					form_relationkeyNames = typeCode + '/' + names;
				} else {
					form_relationkeyNames += '@' + typeCode + '/' + names;
				}
			}*/
		});
		
		// 셀렉트 박스
		/*$("[name=typeCodeSelect] option:selected").each(function(){
			if(form_typeCodes == ''){
				form_typeCodes = $(this).val();
			} else {
				form_typeCodes += "@" + $(this).val();
			}
		});*/
	});
	
	$("#typeCodes").val(form_typeCodes);
	//$("#typeCodesDetail").val(form_typeCodesDetail);
	//$("#typeCodesInfo").val(form_typeCodesInfo);
	//$("#relationkeyNames").val(form_relationkeyNames);
}

function settingBasisInfo(){
	$("#param_id_title").val($("[name=id_title]").val());
	$("#param_id_url").val($("[name=id_url]").val());
	$("#param_md_site_name").val($("[name=md_site_name]").val());
	$("#param_id_regdate").val($("[name=id_regdate]").val());
	$("#param_id_content").val($("[name=id_content]").text());
	
}

function save_issue(mode){
	var f  = document.fSend;	
	var regChk = false;
	var parentTypes = $("#parentTypes").val();
	
	if(!vaildate_check()){
		alert('선택한 하위 항목 중 하나를 반드시 선택하십시오.');
		return false;
	}
	settingBasisInfo();
	settingTypeCode(parentTypes);
	
	if(!regChk){

		document.getElementById("sending").style.display = '';
		$("#mode").val(mode);
		f.target='if_samelist';
		f.action='issue_data_prc.jsp';
        f.submit();
	}else{
		alert('등록중입니다.. 잠시만 기다려주세요.');
	}
}

function add_IC(type) {
	$("#param_ic_type").val(type);
	popup.openByPost('fSend','pop_issue_code_form.jsp', 550, 250,false,false,false,'add_IC');
}