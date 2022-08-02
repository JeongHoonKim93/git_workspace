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

function ajaxSelectBox2(selectType, selectCode, mainType, $target, targetDepth){	
	$.ajax({
		type : "POST"
		,url: "./aj_selectbox_type.jsp"
		,timeout: 30000
		,data : "selectType="+selectType+"&selectCode="+selectCode+"&mainType="+mainType+"&depth="+targetDepth
		,dataType : 'html'
		,success : function( data ){
			$target.html( data );			
		}
	});
}

function pop_send_telegram(md_seq){
	//테스트 파라미터 ID_SEQ 넘어가는지
	var f = document.fSend; 
	f.md_seq.value = md_seq;
	popup.openByPost('fSend','pop_issue_send_telegrama_form.jsp',708,700,false,true,false,'send_telegram');
	
}

function getSubTypeCode(selectType, depth){
	
	var division_name = ["hotel","tr","shp","sbtm","ceo","ir","esg"];
	var division_code = ["1,1","1,2","1,3","1,4","1,5","1,6","1,7"];
	var targetDepth =  parseInt(depth)+parseInt(1);
	var selected_value = $("[name=typeCodeSelect_"+selectType+"_"+depth+"] option:selected").val();	

	if(selectType == 1){
		//옵션 셀렉트 부분 초기화
		for(var idx=0; idx<division_name.length;idx++){
			$('#'+division_name[idx]).find("select").each(function() {
				if(this.value != ''){
					this.value = '';				
				}
			});			
		}
		//
		
		//구분 셀렉트 시, 셀렉트한 옵션 관련 항목 보이게 표시
		for(var idx=0; idx<division_code.length;idx++){
			if(selected_value != ''){
				if(selected_value == division_code[idx]) {
					$(".detail").css("display", "none");
					$("#"+division_name[idx]).css("display", "");
					$("#"+division_name[idx]+"_dotline").css("display", "");
				}
			}else{
				$(".detail").css("display", "none");				
			}
		}
		//
		
/*		if(selected_value == '1,1') {
			$(".detail").css("display", "none");
			$("#hotel").css("display", "");
			$("#hotel_dotline").css("display", "");

		}else if(selected_value == '1,2') {
			$(".detail").css("display", "none");
			$("#tr").css("display", "");
			$("#tr_dotline").css("display", "");
			
		}else if(selected_value == '1,3') {
			$(".detail").css("display", "none");
			$("#shp").css("display", "");
			$("#shp_dotline").css("display", "");
			
		}else if(selected_value == '1,4') {
			$(".detail").css("display", "none");
			$("#sbtm").css("display", "");
			$("#sbtm_dotline").css("display", "");
			
		}else if(selected_value == '1,5') {
			$(".detail").css("display", "none");
			$("#ceo").css("display", "");
			$("#ceo_dotline").css("display", "");
			
		}else if(selected_value == '1,6') {
			$(".detail").css("display", "none");
			$("#ir").css("display", "");
			$("#ir_dotline").css("display", "");
			
		}else if(selected_value == '1,7') {
			$(".detail").css("display", "none");
			$("#esg").css("display", "");
			$("#esg_dotline").css("display", "");
		
		}else if(selected_value == '') {
			$(".detail").css("display", "none");
			
		}
*/		
		
		
	}
	
	if(selected_value == '') {
		selected_value = '0,0';
	}
	
	var split_value = selected_value.split(",");
	checkCloseChildDepth(selectType, depth);
	//ajaxSelectBox(split_value[0], split_value[1], selectType, $("#subTypeCode_"+split_value[0]+"_"+targetDepth));	
	ajaxSelectBox2(split_value[0], split_value[1], selectType, $("#td_subTypeCode_"+selectType+"_"+targetDepth), targetDepth);
}

function checkCloseChildDepth(selectType, depth){
	var innerHtml = "<option value=''>선택하세요</option>";
	$(".subType_"+selectType).each(function(idx){
		if($(this).data(["data-depth"]).context.dataset['depth']>depth){
			$(this).empty().append(innerHtml);
		}		
	});
	
}

function search(){
	settingDate();
	settingTypeCode();
	settingKeyword();
	//settingSltSiteGroups();
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
	/*
	function settingSltSiteGroups(){
		var f = document.getElementById('fSend');
		var seqs = '';
		var obj = document.getElementsByName('sltSiteGroup');
		var chk = 0;
			for(var i =0; i<obj.length; i++){
				if(obj[i].checked){
					seqs += seqs == '' ?  obj[i].value : ',' + obj[i].value;
				}else{
					chk++
				}
			}
			if(chk == obj.length){
			 	seqs = '';
				for(var i =0; i<obj.length; i++){
				seqs += seqs == '' ?  obj[i].value : ',' + obj[i].value;
				obj[i].checked = chk;
				}			
				
			}			
		//f.sltSiteGroups.value = seqs;

	}
	*/
/*function settingTypeCode(){
	
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
}*/


function settingTypeCode(){
	var checkboxListArr = $("#parentTypes").val().split(",");
	var typeCodeSelect = '';
	var typeCodeClout = '';
	var typeCodeSenti = '';
	var typeCodeTrans = '';
	var register = '';
	var sgSeqs = '';
	
	var pcode = '';
	var target = '';		
	$(checkboxListArr).each(function(idx){		
		pcode = checkboxListArr[idx];
		$(".subType_"+pcode).each(function(depth){
 		    	target = $("[name=typeCodeSelect_"+pcode+"_"+(depth+1)+"] option:selected").val();
				
				if( target != ''){
					if(typeCodeSelect == '') {
						typeCodeSelect = target;
					} else {
						typeCodeSelect += '@' + target;
					}
				}
				
		});
	});
	
	register = $("#registerSelect option:selected").val(); //등록자
	typeCodeClout = $("#cloutSelect option:selected").val(); //영향력
	typeCodeSenti = $("#sentiSelect option:selected").val(); //성향
	typeCodeTrans = $("#transSelect option:selected").val(); //보고서
	
	if($("input:checkbox[name='sltSiteGroupAll']").is(":checked") == true) {
		sgSeqs = '';
	} else {
		$("input:checkbox[name='sltSiteGroup']").each(function(){
		if($(this).is(":checked") == true) {
			if(sgSeqs == '') {
				sgSeqs = $(this).val();
			} else {
				sgSeqs += "," + $(this).val();
			}
		} 
	});
	}
	
	$("#typeCodeSelect").val(typeCodeSelect);
	$("#typeCodeClout").val(typeCodeClout);
	$("#typeCodeSenti").val(typeCodeSenti);
	$("#typeTransyn").val(typeCodeTrans);
	//$("#typeCodeInfo").val(typeCodeInfo);
	//$("#relationKeyword").val(typeCodeRelKey);
	$("#registerInput").val(register);
	$("#sltSiteGroups").val(sgSeqs);
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

	/**
	* 검색유형 전체 체크
	*/	
	function chkSiteGroupAll(chk){
 		var group = document.getElementsByName('sltSiteGroup');
 		if(group){
			if(group.length){
				for(var i = 0; i < group.length; i++){
					group[i].checked = chk;
				}
			}
		} 
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

function transIssueData(){
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
		if(confirm(cnt + '건의 데이터를 이관시키시겠습니까?')) {
			$.ajax({
				type : "POST"
				,url: "./issue_data_prc.jsp"
				,timeout: 30000
				,data : "id_seqs="+checked_seqs+"&mode=trans"
				,dataType : 'text'
				,success : function( data ){
					alert("데이터가 이관되었습니다.");
					search();
				}
			});
		}
	}
}


function PopIssueDataForm_multi(){
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
 		popup.openByPost('fSend','pop_issue_data_multi_update_form.jsp',708,1000,false,true,false,'send_issue');
	}
}

//관심정보 변경 폼
function PopIssueDataForm_new(){
	var f = document.fSend;
	f.mode.value = 'new';
	popup.openByPost('fSend','pop_issue_data_form.jsp',1400,850,false,true,false,'send_issue');
}

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
	//settingSltSiteGroups();
	
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
	
/*function getSourceData(md_pseq, ic_seq, ic_name){
	
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
	
}*/

function getSourceData(md_pseq){
	
	var sameLayerId = "SameList_"+md_pseq;
	var sameLayer = $("#"+sameLayerId);
	
	var preSameLayerId = $("#sameListId").val();
	var preSameLayerSite = $("#sameListSite").val();
	
	//if(sameLayerId == preSameLayerId && ic_seq == preSameLayerSite && $("#"+preSameLayerId).css("display") == 'block'){
	if(sameLayerId == preSameLayerId && $("#"+preSameLayerId).css("display") == 'block'){
		$("#"+preSameLayerId).css("display", "none");
		return;
	}
	
	if(preSameLayerId != "" && $("#"+preSameLayerId)){
		$("#"+preSameLayerId).css("display", "none");
	}
	
	$("#sameListId").val(sameLayerId);
	//$("#sameListSite").val(ic_seq);
	
	sameLayer.innerHTML = '로딩중...';
 	if_samelist.location.href = "inc_same_list.jsp?md_pseq=" + md_pseq 
 	                                           + "&sDateFrom=" + $("#param_sDate").val()
 	                                           + "&sDateTo=" + $("#param_eDate").val()
 	                                           + "&ir_stime=" + $("#param_sTime").val()
 	                                           + "&ir_etime=" + $("#param_eTime").val();
 	
	sameLayer.css("display", "block");
	
}


function report_save(mode, id_seq, id_title){
	
	var f = document.issueReport;
	
	f.ir_type.value = mode;
	f.ir_title.value = id_title;
	f.id_seq.value = id_seq;
	
	window.open('about:blank', 'PopUp', 'width=820,height=850,scrollbars=yes,status=no,resizable=no');		
	f.action = '../report/pop_report_editform.jsp';
	//f.action = '../report/issue_report_form.jsp';
	f.target = 'PopUp';
	f.method= 'post';
	f.submit(); 
	
	
	/*
	var report_name;
	$.ajax({
		type : "POST"
		,url: "./report_issue_prc.jsp"
		,timeout: 30000
		,data : "mode="+mode+"&id_seq="+id_seq+"&id_title="+id_title
		,dataType : "text"
		,success : function( data ){
			if("IS"==mode){
				report_name = "Issue Report";
			}else{
				report_name = "Online Issue Report";
			}
						
			if("fail"==data.trim()){
				alert(report_name+"저장에 실패하였습니다.");
			}else{				
				alert(report_name+"가 저장 되었습니다.\n보고서 관리 페이지에서 작성을 완료해주세요.");
			}
		}
	});
	*/
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