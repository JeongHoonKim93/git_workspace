var devel = new Object();


/*******************************Number***********************************/
devel.number = new Object();
/**
 * @x 숫자
 * @returns 콤마찍힌 문자열 (천단위) 
 */
devel.number.numberWithCommas = function (x) {
  return x.replace(/\B(?=(\d{3})+(?!\d))/g, ",");
};

/**
 * @str 텍스트
 * @returns 숫자만 추출
 */
devel.number.getNumberOnly = function (str){
    return new String(str).replace(/[^0-9]/g, '');
};



/*******************************Chart***********************************/
devel.chart = new Object();
//차트만들때 id넣어놓으면 그놈찾아옴
devel.chart.getChartById = function (id){
	var allCharts = AmCharts.charts;
    for( var i = 0 ; i < allCharts.length ; i++ ){
        if ( id == allCharts[ i ].id ) {
            return allCharts[ i ];
        }
    }
    return null;
};

/*******************************url***********************************/

//페이지 이동시 사용할 허브
devel.chkOriginal = 1;
devel.hrefPop = function (url, s_seq, text, s_name){
	if(s_seq == '3555' ){//네이버
		//url = "http://section.cafe.naver.com/ArticleSearch.nhn?query="+text ;
		//url = "https://section.cafe.naver.com/cafe-home/search/articles?query=\""+text+"\"";
		url = "https://cafe.naver.com/ca-fe/home/search/articles?pr=2&se=1&et="+ text;
		window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop'+(devel.chkOriginal++),'');
	}else if(s_seq == '4943'){//다음
		url = "http://search.daum.net/search?w =cafe&nil_search=btn&enc=utf8&ASearchType=1&lpp=10&rlang=0&q=" + text;
		window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop'+(devel.chkOriginal++),'');
	//네이버카페    			
	}else if(s_name.includes('네이버카페')){
	url = "https://cafe.naver.com/ca-fe/home/search/articles?pr=2&se=1&et="+ text;
		window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop'+(devel.chkOriginal++),'');
	
	//다음카페    			
	}else if(s_name.includes('다음카페')){
		url = "http://search.daum.net/search?w =cafe&nil_search=btn&enc=utf8&ASearchType=1&lpp=10&rlang=0&q=" + text;
		window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop'+(devel.chkOriginal++),'');
		
	}else{
		window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'_blank','');
	}
	
};