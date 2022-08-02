/**
	이슈관리 - 관련정보
*/

$(function(){
	$("#searchKey").focus();
	
	search();
});

function ajaxSelectBox(selectType, selectCode, mainType, $target){
	$.ajax({
		type : "POST"
		,url: "./aj_selectbox_type.jsp"
		,timeout: 30000
		,data : "selectType="+selectType+"&selectCode="+selectCode+"&mainType="+mainType
		,dataType : 'html'
		,success : function( data ){
			$target.html( data );
		}
	});
}

function getSubTypeCode(selectType){
	
	var selected_value = $("#typeCode_"+selectType+" option:selected").val();
	
	if(selected_value == '') return false;
	
	var split_value = selected_value.split(",");
	
	ajaxSelectBox(split_value[0], split_value[1], 9, $("#sentiCode_"+selectType));
	
	if(selectType != 8){
		ajaxSelectBox(split_value[0], split_value[1], 13, $("#infoCode_"+selectType));	
	} else {
		ajaxSelectBox(split_value[0], split_value[1], -1, $("#relKeyword_"+selectType));
	}
	
}

function search(){
	settingDate();
	settingTypeCode();
	settingKeyword();
	//callChart("chartdiv1");
	callIssueData();
}

function settingDate(){
	$("#param_sDate").val($("#sDateFrom").val());
	$("#param_eDate").val($("#sDateTo").val());
	$("#param_sTime").val($("[name=ir_stime] option:selected").val());
	$("#param_eTime").val($("[name=ir_etime] option:selected").val());
}

function settingKeyword(){
	$("#param_searchKey").val(encodeURIComponent($("#searchKey").val()));
	$("#param_keyType").val($("[name=keyType] option:selected").val());
}

function settingTypeCode(){
	
	var typeCodeSelect = '';
	var typeCodeSenti = '';
	var typeCodeInfo = '';
	var typeCodeRelKey = '';
	var register = '';
	
	$("[name=typeCodeSelect] option:selected").each(function(){
		var split_val = '';
		
		if($(this).val() != ''){
			if(typeCodeSelect == '') {
				typeCodeSelect = $(this).val();
			} else {
				typeCodeSelect += '@' + $(this).val();
			}
			
			split_val = $(this).val().split(',');
			
			var senti_val = $("#sentiCode_"+split_val[0]+" option:selected").val();
			if(senti_val != '' && typeof senti_val != 'undefined'){
				if(typeCodeSenti == ''){
					typeCodeSenti = senti_val;
				} else {
					typeCodeSenti += '@' + senti_val;
				}
			}
			
			var info_val = $("#infoCode_"+split_val[0]+" option:selected").val();
			if(info_val != '' && typeof info_val != 'undefined'){
				if(typeCodeInfo == ''){
					typeCodeInfo = info_val;
				} else {
					typeCodeInfo += '@' + info_val;
				}
			}
			
			var relkey_val = $("#relKeyword_"+split_val[0]+" option:selected").val();
			if(relkey_val != '' && typeof relkey_val != 'undefined'){
				if(typeCodeRelKey == ''){
					typeCodeRelKey = relkey_val;
				} else {
					typeCodeRelKey += '@' + relkey_val;
				}
			}
		}
	});
	
	register = $("#registerSelect option:selected").val();
	
	$("#typeCodeSelect").val(typeCodeSelect);
	$("#typeCodeSenti").val(typeCodeSenti);
	$("#typeCodeInfo").val(typeCodeInfo);
	$("#relationKeyword").val(typeCodeRelKey);
	$("#registerInput").val(register);
}

function callIssueData(paramUrl){
	
	var param = $("#fSend").serialize();
	var go_url = "./aj_issue_list.jsp"
	
	if(typeof paramUrl != 'undefined'){
		go_url += paramUrl;
	}
	
	$.ajax({
		type : "POST"
		,url: go_url
		,timeout: 30000
		,data : param
		,dataType : 'html'
		,success : function( data ){
			$("#board_01").html(data);
		}
	});
	
}

function deleteIssueData(){
	var checked_seqs = '';
	var cnt = 0;
	
	$("[name=id_seqs]:checked").each(function(){
		if(checked_seqs == ''){
			checked_seqs = $(this).val();
		} else {
			checked_seqs += ',' + $(this).val();
		}
		cnt++;
	});
	
	if(checked_seqs == '') {
		alert('선택된 정보가 없습니다.'); 
		return;
	} else {
		if(confirm(cnt + '건의 이슈를 삭제합니다.')) {
			$.ajax({
				type : "POST"
				,url: "./issue_data_prc.jsp"
				,timeout: 30000
				,data : "id_seqs="+checked_seqs+"&mode=delete"
				,dataType : 'text'
				,success : function( data ){
					alert("이슈정보가 삭제되었습니다.");
					search();
				}
			});
		}
	}
}

/*function PopIssueDataForm_multi(){
	var checked_seqs = '';
	
	$("[name=id_seqs]:checked").each(function(){
		if(checked_seqs == ''){
			checked_seqs = $(this).val();
		} else {
			checked_seqs += ',' + $(this).val();
		}
	});
	
	if(checked_seqs == '') {
		alert('선택된 정보가 없습니다.'); 
		return;
	} else {
		var f = document.fSend;
 		
		f.mode.value = 'update_multi';
 		f.child.value = 'Y';
 		f.id_seqs.value = checked_seqs;
 		popup.openByPost('fSend','pop_issue_data_form.jsp',708,1000,false,true,false,'send_issue');
	}
}*/

function goExcelTo(){
	var checked_seqs = '';
	
	$("[name=id_seqs]:checked").each(function(){
		if(checked_seqs == ''){
			checked_seqs = $(this).val();
		} else {
			checked_seqs += ',' + $(this).val();
		}
	});
	
	$("#id_seqs").val(checked_seqs);
	settingDate();
	settingTypeCode();
	settingKeyword();
	
	var f = document.fSend;
	f.action = 'issue_data_excel.jsp';
	f.target = 'processFrm';
	f.submit();
}

function pageClick(paramUrl){
	callIssueData(paramUrl);
}

function fillSameList(no){
 	var ly = document.getElementById('SameList_'+no);    	
	    ly.innerHTML = if_samelist.zzFilter.innerHTML;
 }

var chkPop = 1;
function hrefPop(url){
	//window.open(url,'hrefPop'+chkPop,'');
	window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop'+chkPop,'');
	chkPop++;
}

//관심정보 변경 폼
function PopIssueDataForm(md_seq, child){
	var f = document.fSend; 
	f.md_seq.value = md_seq;
	f.param_searchKey.value = encodeURIComponent(f.param_searchKey.value);
	f.mode.value = 'update';
	f.child.value = child;
	popup.openByPost('fSend','pop_issue_data_form.jsp',1400,850,false,true,false,'send_issue');

}

/**
 * 필드정렬
 */
function setOrder(order){
		
		if(order == $("#sOrder").val()){
			if($("#sOrderAlign").val() == "DESC"){
				$("#sOrderAlign").val("ASC");
			} else {
				$("#sOrderAlign").val("DESC");
			}
		} else {
			$("#sOrderAlign").val("DESC");
		}
		
    $("#sOrder").val(order);
    callIssueData();
}
	
function getSourceData(md_pseq, ic_seq, ic_name){
	
	var sameLayerId = "SameList_"+md_pseq;
	var sameLayer = $("#"+sameLayerId);
	
	var preSameLayerId = $("#sameListId").val();
	var preSameLayerSite = $("#sameListSite").val();
	
	if(sameLayerId == preSameLayerId && ic_seq == preSameLayerSite && $("#"+preSameLayerId).css("display") == 'block'){
		$("#"+preSameLayerId).css("display", "none");
		return;
	}
	
	if(preSameLayerId != "" && $("#"+preSameLayerId)){
		$("#"+preSameLayerId).css("display", "none");
	}
	
	$("#sameListId").val(sameLayerId);
	$("#sameListSite").val(ic_seq);
	
	sameLayer.innerHTML = '로딩중...';
 	if_samelist.location.href = "inc_same_list.jsp?md_pseq=" + md_pseq 
 	                                           + "&ic_seq=" + ic_seq 
 	                                           + "&ic_name=" + encodeURIComponent(ic_name) 
 	                                           + "&sDateFrom=" + $("#param_sDate").val()
 	                                           + "&sDateTo=" + $("#param_eDate").val()
 	                                           + "&ir_stime=" + $("#param_sTime").val()
 	                                           + "&ir_etime=" + $("#param_eTime").val();
 	
	sameLayer.css("display", "block");
	
}

function originalView(id_seq){
	var url = '';
	url = "originalView.jsp?id_seq="+id_seq;	
	window.open(url, "originalPop", "width=708, height=672, scrollbars=yes");		
}

function samePackage(){
	var id_seqs = "";
	$("[name=id_seqs]:checked").each(function(){
		if(id_seqs == ''){
			id_seqs = $(this).val();
		} else {
			id_seqs += ',' + $(this).val();
		}
	});
	
	$.ajax({
		type : "POST"
		,async : true
		,url: "same_package_prc.jsp"
		,timeout: 30000
		,data : {same_id_seqs:id_seqs}
		,dataType : 'text'
		,success : function(data){
					var result = data.trim();
					if(result == "success"){
						alert("유사묶기 완료 하였습니다.")
						callIssueData();
					}else{
						alert("유사묶기 실패 하였습니다.")
					}					
				  }
		});	
}