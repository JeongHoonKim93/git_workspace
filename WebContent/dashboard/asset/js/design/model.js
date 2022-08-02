var MODEL;
var MODEL_CLASS = function(){

	// Site Name
	var siteName = "Dashboard";
	this.getSiteName = function(){
		return siteName;
	}

	// Page Name
	var pageName = "";
	this.setPageName = function( $pageName ){
		pageName = $pageName;
		// document.title = siteName + " - " + pageName;
	}
	this.getPageName = function(){
		return pageName;
	}
}
if( !MODEL ) MODEL = new MODEL_CLASS();