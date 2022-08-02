//Ajsx 관리자(
var ajaxs = {
	nAjax : null,
	orderLoad : true,		// 순서대로 하나씩 로드할 경우 true, 대기 없이 로드일 경우 false
	loadChk : false,
	stack : [],
	add : function( $ajax ){
		if( !ajaxs.orderLoad ){
			$.ajax( $ajax );
		}
		ajaxs.stack.push( $ajax );
		ajaxs.hasChk();
	},
	remove : function(){
		ajaxs.loadChk = false;
		ajaxs.stack.shift();
		ajaxs.hasChk();
	},
	hasChk : function(){
		if( ajaxs.loadChk ) return;
		if( ajaxs.stack.length > 0 ){
			ajaxs.loadChk = true;
			ajaxs.load();
		}
	},
	load : function(){
		ajaxs.stack[ 0 ].complete = function(){
			ajaxs.loadChk = false;
			ajaxs.remove();
		}
		ajaxs.nAjax = $.ajax( ajaxs.stack[ 0 ] );
	},
	destroy : function(){
		ajaxs.loadChk = false;
		ajaxs.stack.splice(0, ajaxs.stack.length);
	}
}

//Ajax 매니저
var ajaxMngr = {
	open : function( $tg, $lnk, $data, $comFunc ){
		var lnk = $lnk.split( "?" )[ 0 ];
		var param;
		if( $lnk.split( "?" ).length > 1 ) param = $lnk.split( "?" )[ 1 ].paramToJson();
		if( $data ) param = $data;
		
		ajaxs.add({
			type : "POST",
			dataType : "json",
			url : lnk,
			data : param,
			timeout : 1000 * 60 * 3,
			cache : false,
			success : function( $result ){
				if( $comFunc ) $comFunc( $result );
			},
			beforeSend : function(){
				$( $tg ).addClass("loading");
			},
			complete : function(){

				setTimeout(function(){
					$( $tg ).removeClass("loading");
				},400);
			},
			error : function($err){
				//if(chk_session2 == 0){
				//	alert("sesstion time out. go to login page.1");
				//	chk_session2++;
				//}
				//gotoPage(GLB_SITE_URL+"/view/member/login.jsp");
				//return false;
				//if( $err.statusText != "abort" ) msgMngr.send( $err.status + "<br>url : " + $lnk, "Ajax Error", 0, 1 );
			}
		});
	},
	openHtml : function( $tg, $lnk, $data ){
		var lnk = $lnk.split( "?" )[ 0 ];
		var param;
		if( $lnk.split( "?" ).length > 1 ) param = $lnk.split( "?" )[ 1 ].paramToJson();
		if( $data ) param = $data;

		ajaxs.add({
			type : "POST",
			dataType : "html",
			url : lnk,
			data : param,
			timeout : 1000 * 60 * 3,
			cache : false,
			success : function( $result ){
				$( $tg ).html($result);
			},
			beforeSend : function(){
				$( $tg ).addClass("loading");
			},
			complete : function(){

				setTimeout(function(){
					$( $tg ).removeClass("loading");
				},400);
			},
			error : function($err){
				//if(chk_session2 == 0){
				//	alert("sesstion time out. go to login page.1");
				//	chk_session2++;
				//}
				//gotoPage(GLB_SITE_URL+"/view/member/login.jsp");
				//return false;
				//if( $err.statusText != "abort" ) msgMngr.send( $err.status + "<br>url : " + $lnk, "Ajax Error", 0, 1 );
			}
		});
	},
	popup : function( $lnk, $data, $comFunc ){
		var lnk = $lnk.split( "?" )[ 0 ];
		var param;
		if( $lnk.split( "?" ).length > 1 ) param = $lnk.split( "?" )[ 1 ].paramToJson();
		if( $data ) param = $data;

		ajaxs.add({
			type : "POST",
			dataType : "json",
			url : lnk,
			data : param,
			timeout : 1000 * 60 * 3,
			cache : false,
			success : function( $result ){
				if( $comFunc ) $comFunc( $result );
			},
			beforeSend : function(){
				//$( $tg ).addClass("loading");
			},
			complete : function(){
				/*
				setTimeout(function(){
					$( $tg ).removeClass("loading");
				},400);
				*/
			},
			error : function($err){
				alert('팝업 에러입니다.');
//				console.log($err);
				//if(chk_session2 == 0){
				//	alert("sesstion time out. go to login page.1");
				//	chk_session2++;
				//}
				//gotoPage(GLB_SITE_URL+"/view/member/login.jsp");
				//return false;
				//if( $err.statusText != "abort" ) msgMngr.send( $err.status + "<br>url : " + $lnk, "Ajax Error", 0, 1 );
			}
		});
	}
};

//Excel Download - 기존 HTML방식
function getExcel( $e, $url, $data, $title){

	var dataParams = $data;
	if( String( typeof( dataParams ) ).toLowerCase() == "string" && dataParams.indexOf( "{" ) > -1 ) dataParams = $.parseJSON( dataParams );
	$.ajax({ type : "POST"
		,url : $url
		,timeout : 60000
		,dataType : "text"
		,data : dataParams
		,success : function( data ){
			var frm_excel = document.proceeExcel;
//			console.log(document.proceeExcel);
			frm_excel.target = 'processFrm';
			frm_excel.action = '../common/excel_down_prc.jsp' + "?title=" + (encodeURIComponent($title));
			frm_excel.dataToDisplay.value = data;
			frm_excel.submit();
		}
		,beforeSend : function(){
			$e.addClass("loading");
		}
		,complete : function(){
			$e.removeClass("loading");
		}
		,error : function( $err ){
			//messageBox( $err.status + "<br>url : " + $url, "Ajax Error", 0, 1 );
		}
	});
}

function popupExcel(){

	$("#popid_excel").addClass("loading");
	//if($( '#popup_rel_info .serviceChk').css('display') == 'none') {
		var titleArry = ["문서번호","날짜","출처","제목","URL","메시지","감성"];
		var titlekey = ["ID_SEQ","MD_DATE","MD_SITE_NAME","TITLE","ID_URL","ID_CONTENT","ID_SENTIMENT"];
	//} else {
	//	var titleArry = ["문서번호","날짜","출처","서비스","제목","URL","메시지","감성"];
		//var titlekey = ["ID_SEQ","MD_DATE","MD_SITE_NAME","SERVICE","TITLE","ID_URL","ID_CONTENT","ID_SENTIMENT"];
	//}
	$("#proceeExcel").find("[name=subject]").val( $("#popup_rel_info").find("div.header").find("h2").text());
	$("#proceeExcel").find("[name=titleArray]").val(titleArry);
	$("#proceeExcel").find("[name=titlekey]").val(titlekey);
	$("#proceeExcel").find("[name=sql]").val(g_sql);

	//2018-08-21 Main엑셀 실행 후 action변경으로 action도 설정.
	$("#proceeExcel")[0].action = window.location.protocol + "//" + window.location.host+'/excelexport';
	$("#proceeExcel").submit(function(){
		setTimeout(function(){ $("#popid_excel").removeClass("loading"); }, 1000);
	}).submit();
}
// 안쓰는 함수
function popupSearch(){
	getPopup('serach',g_parm);
}

function pageClick(page){
	$('#nowPage').val(page);
	$('#senti_option').val($("#rel_info_brd_sort_01 option:selected").val());
	$('#text_value').val(document.getElementById("set_site_search").value);
	getPopup('search',g_parm);
}
// VOC 팝업 관련
function pageClick_voc(page){
	$('#nowPage_voc').val(page);
	$('#senti_option_voc').val($("#rel_info_brd_sort_voc option:selected").val());
	$('#text_value_voc').val(document.getElementById("set_site_search_voc").value);
	getPopup_voc('search',g_parm_voc);
}

function showPopup(parm){
	$.ajax({
		type : "POST"
		,url: "../common/sessionChkMethod.jsp"
		,dataType : 'text'
		,success : function($result){
			var isAccessSession = $result.trim();
			if(isAccessSession == 'false'){
				window.location.href = '../../../riskv3/error/sessionerror.jsp';
			}else{
//				console.log(parm);
				$("#set_site_search").val("");
				$('#text_value').val("");
				$('#senti_option').val("0");
				$("#rel_info_brd_sort_01").val("0").prop("selected", true);
//				console.log($("#rel_info_brd_sort_01 option:selected").val());
//				console.log($("#rel_info_brd_sort_01 option:selected").text());
				$("#nowPage").val("1");
				getPopup("open",parm);
			}
			
		}			
	});
	
	
}

function showPopup_voc(parm){
	$.ajax({
		type : "POST"
		,url: "../common/sessionChkMethod.jsp"
		,dataType : 'text'
		,success : function($result){
			var isAccessSession = $result.trim();
			if(isAccessSession == 'false'){
				window.location.href = '../../../riskv3/error/sessionerror.jsp';
			}else{
//				console.log(parm);
				$("#set_site_search_voc").val("");
				$('#text_value_voc').val("");
				$('#senti_option_voc').val("0");
				$("#rel_info_brd_sort_voc").val("0").prop("selected", true);
				$('input[name="popup_voc_tab"]:checked').val("1");
				$("#popup_voc_tab_01").prop("checked", true);
//				console.log($("#rel_info_brd_sort_01 option:selected").val());
//				console.log($("#rel_info_brd_sort_01 option:selected").text());
				$("#nowPage_voc").val("1");
				$('#action_only').val("0");
				$("#popup_voc_issue_only").prop('checked', false);
				getPopup_voc("open",parm);
			}
			
		}			
	});
	
	
}

//엑셀다운로드
function excelDownload(event, url, param, title){
	param += '&ExcelTitle='+title;
	getExcel(event,url,param,title);
}

function excelDownload_V2(event, url, param, title){
	getExcel(event,url,param,title);
}

	// 감성 옵션 변경했을때 이벤트 - 공통 팝업
	$('#rel_info_brd_sort_01').change(function(){
		$("#nowPage").val("1");
		$('#senti_option').val($("#rel_info_brd_sort_01 option:selected").val());
		$('#text_value').val(document.getElementById("set_site_search").value);
		getPopup('', g_parm);
		//$('#senti_option').val("0");
		//$('#text_value').val("");
		//$("#rel_info_brd_sort_01 option:eq(0)").prop("selected", true);
      	 });	

	// 검색 버튼 및 텍스트값 엔터키 눌렀을때 이벤트 - 공통 팝업
	function text_search(){
		$("#nowPage").val("1");
		$('#senti_option').val($("#rel_info_brd_sort_voc option:selected").val());
		$('#text_value').val(document.getElementById("set_site_search").value);
		getPopup('', g_parm);
		$('#senti_option').val("0");
		$('#text_value').val("");
		startPage = 1;
		//$("#rel_info_brd_sort_01 option:eq(0)").prop("selected", true);
	}

	// 감성 옵션 변경했을때 이벤트 - VOC 팝업 - function radio_click()으로 변경
/* 	$('#rel_info_brd_sort_voc').change(function(){
		if($("#popup_voc_issue_only").is(":checked") == true){
			$('#action_only').val("1");			
		} else{
			$('#action_only').val("0");
		}		
		$('#nowPage_voc').val("1");
		$('#senti_option_voc').val($("#rel_info_brd_sort_voc option:selected").val());
		$('#text_value_voc').val(document.getElementById("set_site_search_voc").value);
		getPopup_voc('start', g_parm_voc);
		$('#senti_option_voc').val("0");
		$('#text_value_voc').val("");		
		//$('#senti_option').val("0");
		//$('#text_value').val("");
		//$("#rel_info_brd_sort_01 option:eq(0)").prop("selected", true);
    });	 */

	// 검색 버튼 및 텍스트값 엔터키 눌렀을때 이벤트 - VOC 팝업
	function btn_search(){
		if($("#popup_voc_issue_only").is(":checked") == true){
			$('#action_only').val("1");			
		} else{
			$('#action_only').val("0");
		}		
		$('#nowPage_voc').val("1");
		$('#senti_option_voc').val($("#rel_info_brd_sort_voc option:selected").val());
		$('#text_value_voc').val(document.getElementById("set_site_search_voc").value);
		getPopup_voc('start', g_parm_voc);
		$('#senti_option_voc').val("0");
		$('#text_value_voc').val("");
		startPage = 1;
		//$("#rel_info_brd_sort_01 option:eq(0)").prop("selected", true);
	}
	
	// 항목 라디오 버튼 눌렀을때 이벤트 - VOC 팝업
	$("input:radio[name=popup_voc_tab]").click(function(){
		$('#nowPage_voc').val("1");		
		$('#senti_option_voc').val("0");
		$('#text_value_voc').val("");
		$('#set_site_search_voc').val("");
		$("#rel_info_brd_sort_voc").val("0").prop("selected", true);
		$("#popup_voc_issue_only").prop('checked', false);		
		getPopup_voc('start', g_parm_voc);
		startPage = 1;		
	});

	// 이슈만 보기 체크박스 눌렀을때 이벤튼 - VOC 팝업
	function voc_action_only(){
		if($("#popup_voc_issue_only").is(":checked") == true){
			$('#action_only').val("1");			
		} else{
			$('#action_only').val("0");
		}
		$('#nowPage_voc').val("1");
		$('#senti_option_voc').val($("#rel_info_brd_sort_voc option:selected").val());
		$('#text_value_voc').val(document.getElementById("set_site_search_voc").value);
		getPopup_voc('start', g_parm_voc);
		$('#senti_option_voc').val("0");
		$('#text_value_voc').val("");		
	}
	
	function excel_download(){							
		var excel_parm = g_parm + "&senti_option="+$("#rel_info_brd_sort_01 option:selected").val() + "&text_value="+$("#set_site_search").val() + "&subject=관련정보";  	
		
		var $target_btn  = $("#popid_fulllist").find(".ui_btn");
		
			$.ajax({ 
				url : "./excel/getExcelData_pop.jsp"
				,type : "POST"
				,timeout : 1800000
				,dataType : "text"
				,data : excel_parm
				,beforeSend: function(){
					$target_btn.addClass("loading");
				}	
				,success : function( responseUrl ){
//					console.log("responseUrl");
//					console.log(responseUrl );
					$target_btn.removeClass("loading");
					var result = responseUrl ;
					if(result.indexOf('no_data')>-1){
						alert("해당 데이터가 존재하지 않습니다.");
					}else{
						$("#processFrm" ).show();
						$("#processFrm" ).attr( "src", decodeURIComponent( responseUrl ) );
					}
					var interval = setInterval(function () {
					   iframe = document.getElementById('processFrm');
					   var iframeDoc = iframe.contentDocument || iframe.contentWindow.document;
			              if ( iframeDoc.readyState  == 'complete' || iframeDoc.readyState == 'interactive' ) {
			                  clearInterval(interval);
						$("#processFrm" ).attr( "src","");
						$("#processFrm" ).hide();
						 return;
			              }
			          }, 1000);					
				}
				, error : function(){
					$target_btn.removeClass("loading");
					alert("엑셀 다운로드중 오류가 발생하였습니다.\n재시도 부탁드립니다. 감사합니다.");
				}
			});
	}

	// VOC 팝업 엑셀 다운로드 기능 --> 아직 개발 진행중 (id값부터 셋팅 진행해야함)
	function excel_download_voc(){
		var company_code_excel = '';
		var select_topic = $('input[name="popup_voc_tab"]:checked').val();
		var select_topic_name = $("label[for='popup_voc_tab_0"+select_topic+"']").text();
		select_topic_name = select_topic_name.replace("&", "_");
		var splitArr = g_parm_voc.split('&');
		for(var i = 0; i < splitArr.length; i++) {
 			if(splitArr[i].split('=')[0] == 'company_code') {
				company_code_excel = splitArr[i].split('=')[1]; 
		    }  
		}
		
		var company_name_excel = company_code_excel == '1' ? '현대카드 Daily VOC - '+select_topic_name : company_code_excel == '2' ? '현대캐피탈 Daily VOC - '+select_topic_name : '';
		
		var excel_parm_voc = g_parm_voc + "&senti_option_voc="+$("#rel_info_brd_sort_voc option:selected").val() + "&text_value_voc="+$("#set_site_search_voc").val() + "&action_only="+$("#action_only").val() + "&topic_code="+select_topic + "&subject="+company_name_excel+"";  	

		var $target_btn  = $("#pop_voclist").find(".ui_btn");
		
			$.ajax({ 
				url : "./excel/getExcelData_pop.jsp"
				,type : "POST"
				,timeout : 1800000
				,dataType : "text"
				,data : excel_parm_voc
				,beforeSend: function(){
					$target_btn.addClass("loading");
				}	
				,success : function( responseUrl ){
//					console.log("responseUrl");
//					console.log(responseUrl );
					$target_btn.removeClass("loading");
					var result = responseUrl ;
					if(result.indexOf('no_data')>-1){
						alert("해당 데이터가 존재하지 않습니다.");
					}else{
						$("#processFrm" ).show();
						$("#processFrm" ).attr( "src", decodeURIComponent( responseUrl ) );
					}
					var interval = setInterval(function () {
					   iframe = document.getElementById('processFrm');
					   var iframeDoc = iframe.contentDocument || iframe.contentWindow.document;
			              if ( iframeDoc.readyState  == 'complete' || iframeDoc.readyState == 'interactive' ) {
			                  clearInterval(interval);
						$("#processFrm" ).attr( "src","");
						$("#processFrm" ).hide();
						 return;
			              }
			          }, 1000);					
				}
				, error : function(){
					$target_btn.removeClass("loading");
					alert("엑셀 다운로드중 오류가 발생하였습니다.\n재시도 부탁드립니다. 감사합니다.");
				}
			});
			
			
	}

function getPopup_voc(type, parm){
	g_parm_voc = parm;
	if(parm == ""){
		alert('파라미터가 없습니다.');
		return;
	} 
	// 주제별 코드 중, 선택된 부분 추가시키기  > 나가서는 클릭 이벤트로 다시 getPopup_v2로 들어가게 해야함
	parm += "&topic_code=" + $('input[name="popup_voc_tab"]:checked').val();
	
	var param = $('#fSend').serialize();
	if(param == '') param = $('#searchs_frm').serialize();

	var param2 = $('#fvoc').serialize();
	parm += "&" + param;
	parm += "&" + param2;

	//라디오버튼 셋팅 파라미터
	var temp = '&temp=radio';
	radio_parm = parm + temp;
	//팝업오픈
	popupMngr.open( '#popup_voc' );
	
	var div = "pop_voclist";
	var innerHtml_radio = '';		
	$.ajax({ type : "POST"
	, async : true
	, url : "../common/popup/data/popupDao.jsp"
	, timeout : 350000
	, dataType : "text"
	, data : radio_parm
	, success : function($result){
		
		var list = $result.trim();
		var temp = JSON.parse(list);
		
		var value = $('input[name="popup_voc_tab"]:checked').val();
		var code_list = temp.IC_CODE.split(",");
		var name_list = temp.IC_NAME.split(",");
		var checked = '';
	
		innerHtml_radio += '<div class="ui_tab" id="popup_radio">';
		innerHtml_radio += '<ul id="sub_title">';
		for(var i = 0; i < code_list.length; i++) {
			//라디오버튼 선택값
 			if(code_list[i] == value) {
				checked = 'checked';
			} else {
				checked = '';
			}
			innerHtml_radio += '<li><input type="radio" id="popup_voc_tab_0'+code_list[i]+'" name="popup_voc_tab" value="'+code_list[i]+'" '+checked+' onclick="radio_click();"><label for="popup_voc_tab_0'+code_list[i]+'"><span>'+name_list[i]+'</span></label></li>';
		}
		innerHtml_radio += '</ul>';
		innerHtml_radio += '</div>';

		$('#popup_radio').empty().html(innerHtml_radio); 
	}
});

	ajaxMngr.open($("#pop_voclist"),'../common/popup/data/popupDao.jsp', parm, function(data){		
		//상단
		var rowCnt = data.rowCnt;
		var totalCnt = data.count;
		var target_date = data.target_date;
		var search_date = data.DATE;
		var pageCnt = 1;
		var nowPage = data.nowPage;			
		var rel_color ="";
		var rel_cnt ="";
		var Dtitle ="";
		var head_title ="";
		var company_name ="";
		var company_code ="";
		var senti_select ="";
		var select_code = $('input[name="popup_voc_tab"]:checked').val();
		if( totalCnt > rowCnt) pageCnt =  Math.ceil(totalCnt / rowCnt);
	//	$('#popid_totalCnt').empty().html(totalCnt);
	//	$('#popid_totalPage').empty().html(pageCnt);
	//	$('#popid_nowPage').empty().html(nowPage);
		// 날짜 관련
		if(target_date != ''){
		$('#search_date').empty().html(target_date);
		} else {	
		$('#search_date').empty().html(search_date);
		}

		g_sql = data.sql;
		
		var splitArr = parm.split('&');
		var totalChk = false;
		for(var i = 0; i < splitArr.length; i++) {
			if(splitArr[i].split('=')[0] == 'totalOption') {
				totalChk = true; 
		    }  
			if(splitArr[i].split('=')[0] == 'head_title') {
				head_title = splitArr[i].split('=')[1]; 
		    } 
			if(splitArr[i].split('=')[0] == 'color') {
				rel_color = splitArr[i].split('=')[1]; 
		    }  
 			if(splitArr[i].split('=')[0] == 'rel_cnt') {
				rel_cnt = splitArr[i].split('=')[1]; 
		    }  
 			if(splitArr[i].split('=')[0] == 'Dtitle') {
				Dtitle = splitArr[i].split('=')[1]; 
		    }  
 			if(splitArr[i].split('=')[0] == 'company_code') {
				company_code = splitArr[i].split('=')[1]; 
		    }  
 			if(splitArr[i].split('=')[0] == 'senti_select') {
				senti_select = splitArr[i].split('=')[1]; 
		    }  
		}
		if(company_code == '1'){
			company_name = '현대카드';
		} else if(company_code == '2'){
			company_name = '현대캐피탈';			
		} 
		/*else if(company_code == '3'){
			company_name = '현대커머셜';				
		}*/
		
		if(rel_color != "" && head_title != "" && Dtitle != "" && company_name != ""){
		$("#companyname").text(Dtitle + ' : ' + company_name);
		$("#if_type").text(" '"+head_title+"'");
		$("#if_type").css({"color": rel_color});
		$("#rel_cnt").text(rel_cnt);
//		console.log(document.getElementById("rel_info_brd_sort_01").value);
		} else {
		$("#companyname").text('관련정보 : ' + company_name);
		$("#if_type").text("");
		$("#if_type").removeAttr("style");
		$("#rel_cnt").text("");				
		}				
		$("#voc_company").text(company_name + ' Daily VOC');
			
/* 		if(company_code == "2"){	
			$("label[for='popup_voc_tab_07']").text("한도");

			} */
	//	$("#total_cnt").text(totalCnt);
		
		
		/*if(totalChk) {
			$("#popup_rel_info").find("div.header").find("h2").text(data.title.split('>')[0] + ' > 전체');
		} else {
			$("#popup_rel_info").find("div.header").find("h2").text(data.title);
		}*/

		//팝업오픈
		if(type == 'open'){
			$("#rel_info_brd_sort_voc option:selected").val("0");				
			//성향설정
			/*if(data.sentiment != ""){
				$('#rel_info_brd_sort_01').val(data.SENTI_CODE);
				$("#rel_info_brd_sort_01").trigger( "change" );
			}*/
		}
		//리스트
				
		var idx = rowCnt;
		var innerHtml = '';
		var cafeHtml = '';
		var senti_class = '';		
		$(data.list).each(function(i) {
			--idx;
			if(data.list[i].S_SEQ == '3555' || data.list[i].S_SEQ == '4943' || data.list[i].S_SEQ == '5032785'
			|| (data.list[i].SITE).includes("네이버카페") || (data.list[i].SITE).includes("다음카페")
			){
				cafeHtml = '<a href="#" class="btn_cafe_p" style="padding-right:3px;" title="PC 버전 링크" onclick="portalSearch_pc(\''+data.list[i].S_SEQ+'\', \''+encodeURIComponent(data.list[i].TITLE)+'\',\''+data.list[i].SITE+'\'); return false;\">PC 버전 링크</a>' + '<a href="#" class="btn_cafe_m" title="Mobile 버전 링크" onclick="portalSearch_mobile(\''+data.list[i].S_SEQ+'\', \''+encodeURIComponent(data.list[i].TITLE)+'\',\''+data.list[i].SITE+'\'); return false;\">Mobile 버전 링크</a>';
			}else{
				cafeHtml = '';
			}

			if("1"== data.list[i].SENTI_CODE){
				senti_class = 'ui_status_box is-positive';
			}else if("2"==data.list[i].SENTI_CODE){
				senti_class = 'ui_status_box is-negative';
			}else if("3"==data.list[i].SENTI_CODE){
				senti_class = 'ui_status_box is-neutral';
			}
			
			innerHtml += '<tr>';
			innerHtml += '<td><span class="'+senti_class+'">'+ data.list[i].SENTI +'</span></td>';
			if(cafeHtml == ''){
				innerHtml += '<td class="title is-default-pad"><a href="http://hub.buzzms.co.kr?url='+ encodeURIComponent(data.list[i].URL) +'" target="_blank"><span>'+ data.list[i].TITLE +'</span></a></td>';
			} else{
				innerHtml += '<td class="title is-default-pad"><a href="#" style="color: #333333; text-decoration: none !important" target="_blank" class="lnk" title="원문 글 연결은 PC에서는 PC버전 링크 아이콘, 모바일에서는 Mobile버전 링크 아이콘을 클릭하세요." onclick="showAlert(); return false;"><span class="txt">'+ data.list[i].TITLE +'</span></a></td></td>';
			}
			innerHtml += '<td>'+cafeHtml+'</td>';
			innerHtml += '<td>'+ data.list[i].SITE +'</td>';
			innerHtml += '<td>'+ data.list[i].DATE +'</td>';
			innerHtml += '<td>';
			if(data.list[i].COMMENT != ""){
				innerHtml += '<div class="ui_in_search is-confirm"><input id="popup_voc_brd_txt_'+nowPage+select_code+'_0'+i+'" type="text" onkeydown="javascript:if(event.keyCode == 13){voc_comment_save('+nowPage+','+ data.list[i].ID_SEQ +','+company_code+','+select_code+','+i+'); return false;}" placeholder="대응내역" value="'+ data.list[i].COMMENT +'"><label for="popup_voc_brd_txt_'+nowPage+select_code+'_0'+i+'" class="ui_invisible">이슈 입력</label><button title="저장" onmousedown="voc_comment_save('+nowPage+','+ data.list[i].ID_SEQ +','+company_code+','+select_code+','+i+');" ><span>저장</span></button></div>';
			} else{
				innerHtml += '<div class="ui_in_search is-confirm"><input id="popup_voc_brd_txt_'+nowPage+select_code+'_0'+i+'" type="text" onkeydown="javascript:if(event.keyCode == 13){voc_comment_save('+nowPage+','+ data.list[i].ID_SEQ +','+company_code+','+select_code+','+i+'); return false;}" placeholder="대응내역"><label for="popup_voc_brd_txt_'+nowPage+select_code+'_0'+i+'" class="ui_invisible">이슈 입력</label><button title="저장" onmousedown="voc_comment_save('+nowPage+','+ data.list[i].ID_SEQ +','+company_code+','+select_code+','+i+');" ><span>저장</span></button></div>';				
			}
			innerHtml += '</td>';
			innerHtml += '<td>';	
			if(data.list[i].ACTION == "1"){
				innerHtml += '<input id="popup_voc_brd_issue_'+nowPage+select_code+'_0'+i+'" type="checkbox" class="toggle_issue" onchange="voc_action_yn('+nowPage+','+select_code+','+i+','+ data.list[i].ID_SEQ +','+company_code+')" checked><label for="popup_voc_brd_issue_'+nowPage+select_code+'_0'+i+'"></label>';
			} else{
				innerHtml += '<input id="popup_voc_brd_issue_'+nowPage+select_code+'_0'+i+'" type="checkbox" class="toggle_issue" onchange="voc_action_yn('+nowPage+','+select_code+','+i+','+ data.list[i].ID_SEQ +','+company_code+')"><label for="popup_voc_brd_issue_'+nowPage+select_code+'_0'+i+'"></label>';				
			}		
			innerHtml += '</td>';
			innerHtml += '<td class="ui_ar">';
			innerHtml += '<div class="dcp ui_ac" style="width:70px">';
			innerHtml += '<select id="popup_voc_brd_state_'+nowPage+select_code+'_0'+i+'" onchange="voc_change_issue('+nowPage+','+select_code+','+i+','+ data.list[i].ID_SEQ +','+company_code+')" style="width:80px">';
			innerHtml += '<option value="0"';if(data.list[i].PROCEED == "" || data.list[i].PROCEED == "0" ){ innerHtml += ' selected'} innerHtml += '>진행상황</option>';
			innerHtml += '<option value="1"';if(data.list[i].PROCEED == "1"){ innerHtml += ' selected'} innerHtml += '>개선완료</option>';
			innerHtml += '<option value="2"';if(data.list[i].PROCEED == "2"){ innerHtml += ' selected'} innerHtml += '>검토중</option>';
			innerHtml += '<option value="3"';if(data.list[i].PROCEED == "3"){ innerHtml += ' selected'} innerHtml += '>Drop</option>';
			innerHtml += '</select><label for="popup_voc_brd_state_'+nowPage+select_code+'_0'+i+'"></label>';
			innerHtml += '</div>';
			innerHtml += '</td>';			
			innerHtml += '</tr>';	
		});
		//아예 데이터가 없는 경우 빈값 처리
		if(totalCnt == 0 ){
                innerHtml += '<tr><td colspan="8" class="no_over"></td></tr>';
                innerHtml += '<tr><td colspan="8" class="no_over"></td></tr>';
                innerHtml += '<tr><td colspan="8" class="no_over"></td></tr>';
                innerHtml += '<tr><td colspan="8" class="no_over"></td></tr>';
                innerHtml += '<tr><td colspan="8" class="no_over no_data in_list"><span class="ui_no_data_txt">데이터가 없습니다.</span></td></tr>';
                innerHtml += '<tr><td colspan="8" class="no_over"></td></tr>';
                innerHtml += '<tr><td colspan="8" class="no_over"></td></tr>';
                innerHtml += '<tr><td colspan="8" class="no_over"></td></tr>';
                innerHtml += '<tr><td colspan="8" class="no_over"></td></tr>';
                innerHtml += '<tr><td colspan="8" class="no_over"></td></tr>';
		} else{			
		//데이터가 있긴 하지만 10개 목록이 아닌 경우 빈값처리
		for(var i =0; i<idx; i++){
			//if($( '#popup_rel_info .serviceChk').css('display') == 'none') {
				innerHtml += '<tr><td colspan="8" class="no_over"></td></tr>';
			//} else {
			//	innerHtml += '<tr><td colspan="5" class="no_over"></td></tr>';
			//}
			}		
		}
				
		$('#voclist').empty().html(innerHtml);
		
		// Design SelectBox 셋팅
		$tg.find( ".dcp > select" ).each( function(){
			var tg = $( this );
			tg.change( selectSet );
			selectSet();
			new MutationObserver( function( $e ) {
				if( tg.attr( "value" ) ) {
					tg[ 0 ].value = tg.attr( "value" );
					tg.removeAttr( "value" );
					tg.trigger( "change" );
				} else {
					selectSet();
				}
			}).observe( tg[ 0 ], { attributes: true, childList: true, characterData: true, subtree: true, attributeOldValue: true, characterDataOldValue: true });
	
			function selectSet() {
				tg.find( "+ label" ).html( tg.find( "> option:selected" ).html() );
			}
		});
		
		//페이징처리
		var endPage = (  Math.floor((nowPage-1) / rowCnt) + 1 ) * 10;
		var startPage =  endPage - 9;			
	
		if(pageCnt < endPage){endPage = pageCnt};
		innerHtml = "";

		//이전버튼
		if(nowPage > 10){
			innerHtml += '<a href="#" class="page_prev " onclick="pageClick_voc(\''+(endPage-10)+'\'); return false;">이전페이지</a>';
		}else{
			innerHtml += '<a href="#" class="page_prev ui_disabled" >이전페이지</a>';
		}
		//페이징넘버
		for(var i = startPage; i<=endPage; i++){
			if(nowPage == i){
				innerHtml += '<a href="#" class="active">'+i+'</a>';
			}else{
				innerHtml += '<a href="#" onclick="pageClick_voc(\''+i+'\'); return false;">'+i+'</a>';
			}
		}

		if(pageCnt <= 10 || pageCnt <= endPage){
			innerHtml += '<a href="#" class="page_next ui_disabled">다음페이지</a>';
		}else{
			innerHtml += '<a href="#" class="page_next" onclick="pageClick_voc(\''+(endPage+1)+'\'); return false;">다음페이지</a>';
		}
		$('#popvoc_paging').empty().html(innerHtml);
		$('#pop_voclist').removeClass('loading');		
	});	
	
}

var chkOriginal = 1;
function portalSearch_pc(s_seq, md_title, s_name){
	if(s_seq == '3555'){
		//네이버까페
		url = "https://cafe.naver.com/ca-fe/home/search/articles?pr=2&se=1&et="+ md_title;
		window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop'+chkOriginal,'');
	}else if(s_seq == '4943'){
		//다음까페
		url = "http://search.daum.net/search?w=cafe&nil_search=btn&enc=utf8&ASearchType=1&lpp=10&rlang=0&q=" + md_title;
		window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop'+chkOriginal,'');
	}else if(s_seq == '5032785'){
		//네이버카페 - 신용카드박물관
		url = "https://cafe.naver.com/ca-fe/home/search/articles?pr=2&se=1&et="+ md_title;
		window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop'+chkOriginal,'');
		//네이버카페    			
	}else if(s_name.includes('네이버카페')){
		url = "https://m.cafe.naver.com/ca-fe/home/search/articles?q="+ encodeURIComponent(md_title);	
		window.open('http://hub.buzzms.co.kr?url=' + url,'hrefPop'+chkOriginal,'');		
	
	//다음카페    			
	}else if(s_name.includes('다음카페')){
		url = "http://search.daum.net/search?w=cafe&nil_search=btn&enc=utf8&ASearchType=1&lpp=10&rlang=0&q=" + md_title;
		window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop'+chkOriginal,'');
	}

	chkOriginal ++;
}	

//*****모바일버전*****//
function portalSearch_mobile(s_seq, md_title, s_name){
	if(s_seq == '3555'){
		//네이버까페
		url = "https://m.cafe.naver.com/ca-fe/home/search/articles?q="+ encodeURIComponent(md_title);	
		window.open('http://hub.buzzms.co.kr?url=' + url,'hrefPop'+chkOriginal,'');
	}else if(s_seq == '4943'){
		//다음까페
		url = "http://search.daum.net/search?w=cafe&nil_search=btn&enc=utf8&ASearchType=1&lpp=10&rlang=0&q=" + md_title;
		window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop'+chkOriginal,'');
	}else if(s_seq == '5032785'){
		//네이버카페 - 신용카드박물관
		url = "https://m.cafe.naver.com/ca-fe/home/search/articles?q="+ encodeURIComponent(md_title);
		window.open('http://hub.buzzms.co.kr?url=' + url,'hrefPop'+chkOriginal,'');
	//네이버카페    			
	}else if(s_name.includes('네이버카페')){
		url = "https://m.cafe.naver.com/ca-fe/home/search/articles?q="+ encodeURIComponent(md_title);	
		window.open('http://hub.buzzms.co.kr?url=' + url,'hrefPop'+chkOriginal,'');		
	
	//다음카페    			
	}else if(s_name.includes('다음카페')){
		url = "http://search.daum.net/search?w=cafe&nil_search=btn&enc=utf8&ASearchType=1&lpp=10&rlang=0&q=" + md_title;
		window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop'+chkOriginal,'');
	}

	chkOriginal ++;
}	

function voc_change_issue(page, code, number, select_id_seq, company_code){
	var mode = "proceed";
	var issue_text = $("#popup_voc_brd_txt_"+page+code+"_0"+number+"").val();
	var proceed_code = ($("#popup_voc_brd_state_"+page+code+"_0"+number+" option:selected").val());
	var action_code = "";

	if($("input:checkbox[id=popup_voc_brd_issue_"+page+code+"_0"+number+"]").is(":checked") == true){
		action_code = "1";
	} else if($("input:checkbox[id=popup_voc_brd_issue_"+page+code+"_0"+number+"]").is(":checked") == false) {
		action_code = "2";	
	}
	update_voc_list(issue_text, proceed_code, company_code, action_code, select_id_seq, mode);     		   		
}
    
function voc_comment_save(page, select_id_seq, company_code, code, number){
	var mode = "comment";
	var issue_text = $("#popup_voc_brd_txt_"+page+code+"_0"+number+"").val();
	var proceed_code = ($("#popup_voc_brd_state_"+page+code+"_0"+number+" option:selected").val());
	var action_code = "";

	//$("#comment_txt_0"+code+"_0"+number).text(issue_text);

	if($("input:checkbox[id=popup_voc_brd_issue_"+page+code+"_0"+number+"]").is(":checked") == true){
		action_code = "1";
	} else if($("input:checkbox[id=popup_voc_brd_issue_"+page+code+"_0"+number+"]").is(":checked") == false) {
		action_code = "2";	
	}
	update_voc_list(issue_text, proceed_code, company_code, action_code, select_id_seq, mode);
	   		
}        	
    
function voc_action_yn(page, code, number, select_id_seq, company_code){
	var mode = "action";
	var issue_text = $("#popup_voc_brd_txt_"+page+code+"_0"+number+"").val();
	var proceed_code = ($("#popup_voc_brd_state_"+page+code+"_0"+number+" option:selected").val());
	var action_code = "";

	if($("input:checkbox[id=popup_voc_brd_issue_"+page+code+"_0"+number+"]").is(":checked") == true){
		action_code = "1";
	} else if($("input:checkbox[id=popup_voc_brd_issue_"+page+code+"_0"+number+"]").is(":checked") == false) {
		action_code = "2";	
	}       			
	update_voc_list(issue_text, proceed_code, company_code, action_code, select_id_seq, mode);	   		
}

function showAlert() {
   	alert("원문 글 연결은 PC에서는 PC버전 링크 아이콘, \n모바일에서는 Mobile버전 링크 아이콘을 클릭하세요.");
}   

//숫자 컴마 정규식
function makeNumber(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

//비교기간 날짜변경
function compareDatepicker_changeDate() {
	$( "#tmp").bind("change", function(){
		$( "#compare_sDate" ).data( "c_datepicker" ).setMinDate( null );
		$( "#compare_sDate" ).data( "c_datepicker" ).setMaxDate( null );
		$( "#compare_eDate" ).data( "c_datepicker" ).setMinDate( null );
		$( "#compare_eDate" ).data( "c_datepicker" ).setMaxDate( null );


		var date = new Date($( "#sDate" ).attr( "data-date" ));
		date.setDate(date.getDate()-1)

		$('#compare_eDate').trigger('update', date.toISOString().slice(0,10));
		$( "#compare_eDate" ).data( "c_datepicker" ).setMaxDate( date.toISOString().slice(0,10) );

		date.setDate(date.getDate() -(dateDiff($( "#sDate" ).attr( "data-date" ), $( "#eDate" ).attr( "data-date" )))  );

		$('#compare_sDate').trigger('update', date.toISOString().slice(0,10));
		$("#compare_sDate" ).data( "c_datepicker" ).setMaxDate( date.toISOString().slice(0,10) );


	});
}

function dateDiff(_date1, _date2) {
    var diffDate_1 = _date1 instanceof Date ? _date1 : new Date(_date1);
    var diffDate_2 = _date2 instanceof Date ? _date2 : new Date(_date2);

    diffDate_1 = new Date(diffDate_1.getFullYear(), diffDate_1.getMonth()+1, diffDate_1.getDate());
    diffDate_2 = new Date(diffDate_2.getFullYear(), diffDate_2.getMonth()+1, diffDate_2.getDate());

    var diff = Math.abs(diffDate_2.getTime() - diffDate_1.getTime());
    diff = Math.ceil(diff / (1000 * 3600 * 24));

    return diff;
}

function cloud_Keyword_Word(total_volumn, keyword_Cnt, idx) {
	var word_weight = [5, 8, 12, 16, 17, 20, 25, 30, 35, 40, 40];
	var per = (keyword_Cnt / total_volumn * 100);

	if(idx == 0) {
		 rate = (9/per);
	}

	return word_weight[Math.floor(per*rate)];
}

	var _searchOpt;

// Daily VOC TOP5 각각 옵션 바꿀때 사용 (대응내역, 대응여부, 진행상황)
function update_voc_list( issue_text, proceed_code, company_code, action_code, select_id_seq, mode ){
	var inputParams = $.extend({}, _searchOpt);	

	inputParams.mode = mode;
	inputParams.issue_text = issue_text;
	inputParams.proceed_code = proceed_code;
	inputParams.company_code = company_code;
	inputParams.action_code = action_code;
	inputParams.select_id_seq = select_id_seq;
	
	$.ajax({
		type : "POST"
		,url: "../common/sessionChkMethod.jsp"
		,dataType : 'text'
		,success : function($result){
			var isAccessSession = $result.trim();
			if(isAccessSession == 'false'){
				window.location.href = '../../../riskv3/error/sessionerror.jsp';
			}else{
				$.ajax({
					type : "POST"
					,async : true
					,url: "../main/data/UpdateIssueData.jsp"
					,timeout: 30000
					,data : inputParams
					,dataType : 'text'
					,async: true
					,success : function($result){
						
					}				
					
				});	
			}
		}			
	});				
}

//Daily VOC 팝업 - 메뉴토글 변경 펑션
function radio_click() {

	if($("#popup_voc_issue_only").is(":checked") == true){
		$('#action_only').val("1");			
	} else{
		$('#action_only').val("0");
	}
		
	$('#nowPage_voc').val("1");
	$('#senti_option_voc').val($("#rel_info_brd_sort_voc option:selected").val());
	$('#text_value_voc').val(document.getElementById("set_site_search_voc").value);
	getPopup_voc('start', g_parm_voc);
	$('#senti_option_voc').val("0");
	$('#text_value_voc').val("");		
	//$('#senti_option').val("0");
	//$('#text_value').val("");
	//$("#rel_info_brd_sort_01 option:eq(0)").prop("selected", true);
}

//감성 셀렉트박스 클릭시 이벤트
function selectSenti() {
	btn_search();
}