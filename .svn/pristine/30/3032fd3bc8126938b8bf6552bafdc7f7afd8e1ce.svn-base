/*!
 *
 * @author: RSN R&D Team LHS(GUNI)
 *			h2dlhs@realsn.com
 *
 *
 **/



// Replace
String.prototype.replaceAll = function( $rgExp, $replaceText ){
	var oStr = this;
	while (oStr.indexOf($rgExp) > -1)
	oStr = oStr.replace($rgExp, $replaceText);
	return oStr;
}

// 말줄임
String.prototype.lengthLimit = function( $limit ) {
	var result = this;
	if( result.length > $limit ) result = result.substr( 0, $limit ) + "...";
    return result;
}


// Parameter형태를 Json형태로 변환
String.prototype.paramToJson = function() {
	var param = this;
    var hash;
    var result = {};
    var hashes = param.slice( param.indexOf( "?" ) + 1 ).split( "&" );
    for( var Loop1 = 0 ; Loop1 < hashes.length ; ++Loop1 ) {
        hash = hashes[ Loop1 ].split('=');
        result[ hash[ 0 ] ] = hash[ 1 ];
    }
    return result;
}

// String to Array
String.prototype.strToArr = function() {
    return this.split( "," )
}

// Date to String
Date.prototype.dateToStr = function() {
    return this.getFullYear() + "-" + Number( this.getMonth() + 1 ).numToStr_addZero() + "-" + Number( this.getDate() ).numToStr_addZero();
}


// Date Replace With IE
String.prototype.dateReplaceIE = function(){
    return String( this ).replaceAll( "-", "/" );
}

//날짜 연도 내 주차 계산
String.prototype.getWeekDay = function( $day ){
	/************
	getDay() : 1 - 월요일부터 시작, 0 - 일요일
	*************/
	/************
		day 의 입력 양식 yyyy-mm-dd
	*************/

	// var day = this;

	// var splitDay = day.split("-");

	// var startYearDay = '1/4/'+splitDay[0];
	// var today = splitDay[1] + '/' + splitDay[2] + '/' + splitDay[0];

	// var dt = new Date(startYearDay);
	// var tDt = new Date(today);

	// var diffDay = (tDt-dt) / 86400000;

	// // 1월 1일부터 현재날자까지 차이에서 7을 나눠서 몇주가 지났는지 확인을 함
	// var weekDay = parseInt(diffDay / 7) + 1;

	// // 요일을 기준으로 1월 1일보다 이전 요일이라면 1주가 더 늘었으므로 +1 시켜줌.
	// if( tDt.getDay() < dt.getDay() )
	// 	weekDay += 1;
	
    // return weekDay;
    
    var date = new Date( this.replaceAll( "-", "/" ) );
    var dowOffset = $day ? $day : 0;
    var newYear = new Date(date.getFullYear(),0,1);
    var day = newYear.getDay() - dowOffset; //the day of week the year begins on
    day = (day >= 0 ? day : day + 7);
    var daynum = Math.floor((date.getTime() - newYear.getTime() -
        (date.getTimezoneOffset()-newYear.getTimezoneOffset())*60000)/86400000) + 1;
    var weeknum;
    //if the year starts before the middle of a week
    if(day < 4) {
        weeknum = Math.floor((daynum+day-1)/7) + 1;
        if(weeknum > 52) {
        let nYear = new Date(date.getFullYear() + 1,0,1);
        let nday = nYear.getDay() - dowOffset;
        nday = nday >= 0 ? nday : nday + 7;
        /*if the next year starts before the middle of
            the week, it is week #1 of that year*/
        weeknum = nday < 4 ? 1 : 53;
        }
    }
    else {
        weeknum = Math.floor((daynum+day-1)/7);
    }
    return weeknum;
}

// 해당날짜 포함 주의 요일로 날짜 구하기(기본:0(일요일))
String.prototype.getDayDate = function( $day ) {
    $day = $day ? $day : 0;
    var paramDate = new Date( this + " 00:00:00" );
    while( String( paramDate.getDay() ) != String( $day ) ){
        paramDate.setDate( paramDate.getDate() - 1 );    
    }
    return paramDate.dateToStr();
}