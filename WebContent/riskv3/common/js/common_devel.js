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
				console.log($err);
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
			console.log(document.proceeExcel);
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

function popupSearch(){
	getPopup('serach',g_parm);
}

function pageClick(page){
	$('#nowPage').val(page);
	getPopup('serach',g_parm);
}

function showPopup(parm){
	$("#set_site_search").val("");
	$("#nowPage").val("1");
	//$("#rel_info_brd_sort_01").val( "" );
	getPopup("open",parm);
	//$("#rel_info_brd_sort_01").trigger( "change" );
}

//엑셀다운로드
function excelDownload(event, url, param, title){
	param += '&ExcelTitle='+title;
	getExcel(event,url,param,title);
}

function excelDownload_V2(event, url, param, title){
	getExcel(event,url,param,title);
}

//공통팝업 호출
var g_parm = '';
var g_sql = '';
function getPopup(type, parm){
	//alert(parm);
	 
	g_parm = parm;
	if(parm == ""){
		alert('파라미터가 없습니다.');
		return;
	}

	var param = $('#fSend').serialize();
	if(param == '') param = $('#searchs_frm').serialize();

	var param2 = $('#fPopup').serialize();
	parm += "&" + param;
	parm += "&" + param2;
	
	//팝업오픈
	popupMngr.open( '#popup_rel_info' );

	ajaxMngr.open($("#popid_fulllist"),'../common/popup/data/popupDao.jsp', parm, function( data ){
		console.log(data);
		//상단
		var rowCnt = data.rowCnt;
		var totalCnt = data.count;
		var pageCnt = 1;
		var nowPage = data.nowPage;
		var head_title ="";
		if( totalCnt > rowCnt) pageCnt =  Math.ceil(totalCnt / rowCnt);
		$('#popid_totalCnt').empty().html(totalCnt);
		$('#popid_totalPage').empty().html(pageCnt);
		$('#popid_nowPage').empty().html(nowPage);
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
		}
		$("#if_type").text(" : " + head_title);
		
		
		/*if(totalChk) {
			$("#popup_rel_info").find("div.header").find("h2").text(data.title.split('>')[0] + ' > 전체');
		} else {
			$("#popup_rel_info").find("div.header").find("h2").text(data.title);
		}*/

		//팝업오픈
		if(type == 'open'){
			//성향설정
			/*if(data.sentiment != ""){
				$('#rel_info_brd_sort_01').val(data.SENTI_CODE);
				$("#rel_info_brd_sort_01").trigger( "change" );
			}*/
		}

		//리스트
		var idx = rowCnt;
		var innerHtml = '';
		var cafeHeml = '';
		var senti_class = '';
		
		$(data.list).each(function(i) {
			--idx;
			if(data.list[i].S_SEQ == '3555' || data.list[i].S_SEQ == '4943'
				|| data.list[i].SITE.contains("네이버카페") || data.list[i].SITE.contains("다음카페") 
			){
				cafeHeml = '<a href="#" class="ui_bullet" target="_blank" title="카페 바로가기" onclick="portalSearch(\''+data.list[i].S_SEQ+'\', \''+encodeURIComponent(data.list[i].TITLE)+'\',\''+data.list[i].SITE+'\'); return false;\"><span class="cafe">카페</span></a>';
			}else{
				cafeHeml = '';
			}

			if("1"== data.list[i].SENTI_CODE){
				senti_class = 'fc_postive';
			}else if("2"==data.list[i].SENTI_CODE){
				senti_class = 'fc_negative';
			}else if("3"==data.list[i].SENTI_CODE){
				senti_class = 'fc_neutral';
			}

			innerHtml += '<tr>';
			innerHtml += '<td>'+ data.list[i].DATE +'</td>';
			innerHtml += '<td>'+ data.list[i].SITE +'</td>';
		
			//if(data.list[i].hasOwnProperty('SERVICE')) {
				//$( '#popup_rel_info .serviceChk' ).css('display','');
				//innerHtml += '<td title="'+ data.list[i].SERVICE +' ">'+ data.list[i].SERVICE +' </td>';
			//} else {
			//	$( '#popup_rel_info .serviceChk' ).css('display','none');
			//}
			innerHtml += '<td class="ui_al">'+cafeHeml+'<a href="http://hub.buzzms.co.kr?url='+ encodeURIComponent(data.list[i].URL) +'" target="_blank">'+ data.list[i].TITLE +'</a></td>';
			innerHtml += '<td><span class="'+senti_class+'">'+ data.list[i].SENTI +'</span></td>';
			innerHtml += '</tr>';
		});

		//빈값처리
		for(var i =0; i<idx; i++){
			//if($( '#popup_rel_info .serviceChk').css('display') == 'none') {
				innerHtml += '<tr><td colspan="4" class="no_over"></td></tr>';
			//} else {
			//	innerHtml += '<tr><td colspan="5" class="no_over"></td></tr>';
			//}
		}
		$('#poplist').empty().html(innerHtml);

		//페이징처리
		var endPage = (  Math.floor((nowPage-1) / rowCnt) + 1 ) * 10;
		var startPage =  endPage - 9;
		if(pageCnt < endPage){endPage = pageCnt};
		innerHtml = "";

		//이전버튼
		if(nowPage > 10){
			innerHtml += '<a href="#" class="page_prev " onclick="pageClick(\''+(endPage-10)+'\'); return false;">이전페이지</a>';
		}else{
			innerHtml += '<a href="#" class="page_prev ui_disabled" >이전페이지</a>';
		}

		//페이징넘버
		for(var i = startPage; i<=endPage; i++){
			if(nowPage == i){
				innerHtml += '<a href="#" class="active">'+i+'</a>';
			}else{
				innerHtml += '<a href="#" onclick="pageClick(\''+i+'\'); return false;">'+i+'</a>';
			}
		}

		//다음버튼
		if(pageCnt <= 10 || pageCnt <= endPage){
			innerHtml += '<a href="#" class="page_next ui_disabled">다음페이지</a>';
		}else{
			innerHtml += '<a href="#" class="page_next" onclick="pageClick(\''+(endPage+1)+'\'); return false;">다음페이지</a>';
		}
		$('#popid_paging').empty().html(innerHtml);

		$('#popid_fulllist').removeClass('loading');
	});
}

var chkOriginal = 1;
function portalSearch(s_seq, md_title, s_name){

	if(s_seq == '3555'){
		//네이버까페
		//url = "https://section.cafe.naver.com/cafe-home/search/articles?query=\""+md_title + "\"";
		url = "https://cafe.naver.com/ca-fe/home/search/articles?pr=2&se=1&et="+ md_title;
		//window.open(url,'hrefPop'+chkOriginal,'');
		window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop'+chkOriginal,'');
		//window.open('http://hub.buzzms.co.kr?url=' + url,'hrefPop'+chkOriginal,'');
	}else if(s_seq == '4943'){
		//다음까페
		url = "http://search.daum.net/search?w=cafe&nil_search=btn&enc=utf8&ASearchType=1&lpp=10&rlang=0&q=" + md_title;
		//url = "http://search.daum.net/search?w=cafe&nil_search=btn&enc=utf8&ASearchType=1&lpp=10&rlang=0&q=" + md_title;
		//window.open(url,'hrefPop'+chkOriginal,'');
		window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop'+chkOriginal,'');
		//네이버카페    			
		}else if(s_name.includes('네이버카페')){
			url = "https://cafe.naver.com/ca-fe/home/search/articles?pr=2&se=1&et="+ md_title;
		//window.open(url,'hrefPop'+chkOriginal,'');
		window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop'+chkOriginal,'');	
		
		//다음카페    			
		}else if(s_name.includes('다음카페')){
			url = "http://search.daum.net/search?w=cafe&nil_search=btn&enc=utf8&ASearchType=1&lpp=10&rlang=0&q=" + md_title;
			//url = "http://search.daum.net/search?w=cafe&nil_search=btn&enc=utf8&ASearchType=1&lpp=10&rlang=0&q=" + md_title;
			//window.open(url,'hrefPop'+chkOriginal,'');
			window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop'+chkOriginal,'');
		}

	chkOriginal ++;
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
