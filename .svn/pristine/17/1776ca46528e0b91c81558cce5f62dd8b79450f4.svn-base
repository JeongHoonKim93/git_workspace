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