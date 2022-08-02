/*
 * 
 *AJAX 컨트롤
 *2019.01.09
 *
 **/
var $formId = "fSend";

function getAjaxLoadingHtml($target, $url, $callBack, $addParam){
	return getAjaxLoading($target, "html", $url, $callBack, $addParam);
}

function getAjaxLoadingJSON($target, $url, $callBack, $addParam){
	return getAjaxLoading($target, "json", $url, $callBack, $addParam);
}

function getAjaxLoading($target, $dataTypeParam, $url, $callBack, $addParam, loading){
	
	var dataFormat = "html";
	if($dataTypeParam) dataFormat = $dataTypeParam;
	
	var lnk = $url.split( "?" )[ 0 ];
	var param = $("#"+$formId).serialize();
	
	if(typeof $addParam != "undefined"){
		param += $addParam;
	}
	
	var xhr;
	xhr = $.ajax({
		type : "POST"
		,url: $url
		,timeout: 60000
		,data : param
		,dataType : dataFormat
		,cache : false
		,beforeSend : function(){
			if( $target.find( "> .ui_loader" ).length > 0 ) {
				$target.addClass("loading");
			}
		}
		,success : $callBack
		,complete: function(){
			if( $target.find( "> .ui_loader" ).length > 0 ) {
				$target.removeClass("loading");
			}
		}
		,error : function($err){
			console.log($err);
			if( $err.statusText == "timeout" ) messageBox( "일시적으로 많은 요청이 발생하여 이용할 수 없습니다. 다시 시도해주세요.", "공지", 0, 3 );
			console.log( $err.status + "<br>url : " + lnk);
		}
	});	
	
	return xhr;
}

function getAjax($dataTypeParam, $url, $callBack, $beforeSend, $complete, $addParam){
	var dataFormat = "html";
	if($dataTypeParam) dataFormat = $dataTypeParam;
	
	var lnk = $url.split( "?" )[ 0 ];
	var param = $("#"+$formId).serialize();
	
	if(typeof $addParam != "undefined"){
		param += $addParam;
	}
	
	var xhr;
	xhr = $.ajax({
		type : "POST"
		,url: $url
		,timeout: 60000
		,data : param
		,dataType : dataFormat
		,cache : false
		,beforeSend : $beforeSend
		,success : $callBack
		,complete: $complete
		,error : function($err){
			console.log($err);
			if( $err.statusText == "timeout" ) messageBox( "일시적으로 많은 요청이 발생하여 이용할 수 없습니다. 다시 시도해주세요.", "공지", 0, 3 );
			console.log( $err.status + "<br>url : " + lnk);
		}
	});	
	
	return xhr;
}
