/*!
 *
 * @author: RSN R&D Team LHS(GUNI)
 *			h2dlhs@realsn.com
 *
 *
 **/


// 오버되는 자릿수 K/M으로 대체 및 3자리마다 콤마(,) 추가
Number.prototype.lengthLimitComma = function( $limit ) {				// $limit : 오버되는 자릿수
	var result = 0;
	var arrLimit = [ "", "k", "M", "G", "T", "P", "E", "Z", "Y" ];
	var strNum = String( this );
	var spelIdx = Math.ceil( ( strNum.length - $limit ) / 3 );
	strNum = strNum.addComma();
	if( this > 0 && strNum.length > 4 ) result = strNum.substr( 0, strNum.length - ( 4 * spelIdx ) ) + arrLimit[ spelIdx ];
	else result = this;
	return result;
}

// 오버되는 자릿수 제한
Number.prototype.lengthLimit = function( $limit ) {							
	var txtNum = '' + this;
	var result = txtNum;
	if( txtNum.length > $limit ){
		result = txtNum.substr( 0, txtNum.length - $limit );
	}
	return parseInt( result );
}

// ,추가(3자리 기준)
String.prototype.addComma = function() {
    var txtNumber = '' + this;
    if (isNaN(txtNumber) || txtNumber == "") {
        //alert("숫자만 입력 하세요");
        return;
    }
    else {
        var rxSplit = new RegExp('([0-9])([0-9][0-9][0-9][,.])');
        var arrNumber = txtNumber.split('.');
        arrNumber[0] += '.';
        do {
            arrNumber[0] = arrNumber[0].replace(rxSplit, '$1,$2');
        } while (rxSplit.test(arrNumber[0]));
 
        if (arrNumber.length > 1) {
            return arrNumber.join('');
        }
        else {
            return arrNumber[0].split('.')[0];
        }
    }
}

// 10이하 0붙임
Number.prototype.numToStr_addZero  = function( $len ){
	if( !$len ) $len = 2;
	var result = String( this );

	if( result.length < $len ) {
		var len = $len - result.length;
		for( var Loop1 = 0 ; Loop1 < len ; ++Loop1 ){
			result = "0" + result;
		}
	}


	return result;
	/*
	if( this < 10 ) result = "0" + result;
	return result;
	*/
}


Number.prototype.getWeekDay = function( date ){
	/************
	getDay() : 1 - 월요일부터 시작, 0 - 일요일
	*************/
	/************
		day 의 입력 양식 yyyy-mm-dd
	*************/

	// var day = document.getElementById("day").value;
	var day = date;

	var splitDay = day.split("-");

	var startYearDay = '1/1/'+splitDay[0];
	var today = splitDay[1] + '/' + splitDay[2] + '/' + splitDay[0];

	var dt = new Date(startYearDay);
	var tDt = new Date(today);

	var diffDay = (tDt-dt) / 86400000;    // Date 함수 기준 하루를 뭔 초로 나눴는지 모르겠음.

	// 1월 1일부터 현재날자까지 차이에서 7을 나눠서 몇주가 지났는지 확인을 함
	var weekDay = parseInt(diffDay / 7) + 1;

	// 요일을 기준으로 1월 1일보다 이전 요일이라면 1주가 더 늘었으므로 +1 시켜줌.
	if( tDt.getDay() < dt.getDay() )
		weekDay += 1;
	
	return weekDay;
}
