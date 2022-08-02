var MODEL;
var MODEL_CLASS = function(){

	// Site Name
	var siteName = "CJ제일제당";
	this.getSiteName = function(){
		return siteName;
	}

	// Page Name
	var pageName = "";
	this.setPageName = function( $pageName ){
		pageName = $pageName;
		document.title = siteName + " - " + pageName;
	}
	this.getPageName = function(){
		return pageName;
	}
	
	
	// Search Date Range
	var dateLimit = "30";			// 기간 범위   >>  연단위 : "1y", 월단위 : "1m", 일단위 : 숫자(1이 하루)
	var dateRange = "7";			// 기간 범위   >>  연단위 : "1y", 월단위 : "1m", 일단위 : 숫자(1이 하루)
	var sDateTime = "00";			// 시작날짜 시간
	var eDateTime = "23";			// 종료날짜 시간
	this.getdateLimit = function(){
		return dateLimit;
	}
	this.getdateRange = function(){
		return dateRange;
	}
	this.getStartDateTime = function(){
		return sDateTime;
	}
	this.getEndDateTime = function(){
		return eDateTime;
	}

	// Dataset
	this.dataSetChk = false;								// First Load Check
	var dataSet;
	this.get_dataSet = function( $ic_type ){				// Get Data( IC_TYPE )
		var result;
		$.each( dataSet, function(){
			if( this.ic_type == $ic_type ) {
				result = this;
				return false;
			}
		});
		return result;
	}
	this.set_dataSet = function( $value ){
		dataSet = $value;
	}

	// Page Ready Chk
	this.pageReadyChk = false;
}
if( !MODEL ) MODEL = new MODEL_CLASS();