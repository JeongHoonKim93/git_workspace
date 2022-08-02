var main;
$( document ).ready( function(){
		
			
	main = new function(){
		// Init
		(function(){
			// Default Date Setting
			var dateRange = MODEL.getdateRange();			// 연단위 : "1y", 월단위 : "1m", 일단위 : 숫자(1이 하루)
			var today = new Date();
			var sDate = new Date();
			if( String(dateRange).toLowerCase().indexOf( "y" ) >= 0  ) sDate.setFullYear( today.getFullYear() - ( dateRange.split( "y" )[ 0 ] ) );
			else if( String(dateRange).toLowerCase().indexOf( "m" ) >= 0  ) sDate.setMonth( today.getMonth() - ( dateRange.split( "m" )[ 0 ] ) );
			else sDate.setDate( today.getDate() - ( dateRange - 1 ) );
			var eDate = today;
			$( "#searchs_frm .ui_datepicker_input_range" ).trigger( "date_relase", [ sDate.toISOString().slice(0,10).replace(/-/g,"-"), eDate.toISOString().slice(0,10).replace(/-/g,"-") ] );
			$( "#s04_dp" ).val(eDate.toISOString().slice(0,10).replace(/-/g,"-"));
			set_search();
			change_search();
			hndl_search();
			setPage();
			
		})();

		// 주요 이슈 셋팅	
		$("#issue_name").text(' - ' + $("#s09_issue option:selected").text());
		// 주요 이슈 value 변경
   		$('#s09_issue').change(function(){
			$("#issue_name").text(' - ' + $("#s09_issue option:selected").text());
			delete charts[5];
            updateAjax_con_91();
            updateAjax_con_92();
      	 });

		// 검색
		var _searchOpt;
		
		function set_search(){
			$( "#btn_search" ).click(hndl_search);
		}

		function change_search(){
			for(var i=1; i <= $(".header_chk").size(); i++){
				$( "#header_" + i ).click( hndl_search );	
			}
		}
		
		$('#sidebar_detail_more, #detail_more').change(function(){
			if($( "#detail_more" )[ 0 ].checked ){	
				updatePage_more();
			}			
		});					
			
		function set_hearTitle(){
		
		}
		
		// 항목 라디오 버튼 눌렀을때 이벤트 - VOC 팝업
/*		$("input:radio[name=info_trans_radio_group]").click(function(){
			updateAjax_con_31();
			updateAjax_con_32();
		});		
*/		
			
		function hndl_search(){
			set_hearTitle();
			
			$.ajax({
				type : "POST"
				,url: "../common/sessionChkMethod.jsp"
				,dataType : 'text'
				,success : function($result){
					var isAccessSession = $result.trim();
					if(isAccessSession == 'false'){
						window.location.href = '../../../riskv3/error/sessionerror.jsp';
					}else{
						// 채널 셋팅
						var sg_size = $("[data-grp=ts_chns]").size();
						var sg_seqs = "";
						var sg_name = "";
						for(var idx = 1; idx < sg_size ; idx++){
							if($("input:checkbox[id='ts_chns_0"+idx+"']").is(":checked") == true){
							sg_seqs += sg_seqs == '' ?  $("#ts_chns_0"+idx+"").val() : ',' + $("#ts_chns_0"+idx+"").val();	
								}		
						}
						$('input[name=sg_seqs').val(sg_seqs);
						
						if($("input:checkbox[id='ts_chns_all']").is(":checked") == true){
							sg_name = "전체";
						} else{	
							for(var idx = 1; idx < sg_size ; idx++){
								if($("input:checkbox[id='ts_chns_0"+idx+"']").is(":checked") == true){
									sg_name += sg_name == '' ?  $("label[for='ts_chns_0"+idx+"']").text() : '/ ' + $("label[for='ts_chns_0"+idx+"']").text();	
								} 			
							}
						}
						$('input[name=sg_name').val(sg_name);
						$("#inc_chns").text("채널선택 : " + sg_name);
						
						// 검색 타입 및 검색 키워드 셋팅
						var keyword_type = $("#ts_keyword_type option:selected").val();
						var search_keyword = $("#ts_keyword").val();
						
						if(search_keyword == ""){
						$("#inc_keywords").text("검색 : 없음");			
						}else{
							
						$("#inc_keywords").text("검색 : " + search_keyword);			
						
						}	
												
						$("#inc_date").text($( "#searchs_frm .searchs_dp_start" ).attr( "data-date" )+"~"+  $( "#searchs_frm .searchs_dp_end" ).attr( "data-date" ));
															
						$("#voc_attr_tab_01").val("0").prop("checked", true);									
						$('input[name="voc_attr_tab"]:checked').val("0");
						$("#info_trans_radio_group_01").val("0").prop("checked", true);									
						$('input[name="info_trans_radio_group"]:checked').val("0");

						if($("#company_code").val() == 1){
								$("#voc_attr_02").css("display","");	
								$("#voc_attr_03").css("display","");	
							} else if($("#company_code").val() == 2){
								$("#voc_attr_02").css("display","none");	
								$("#voc_attr_03").css("display","none");					
							} else if($("#company_code").val() == 3){
								$("#voc_attr_02").css("display","none");	
								$("#voc_attr_03").css("display","none");	
							} 		
															
						//파라미터 셋팅
						_searchOpt = $( "#searchs_frm" ).serialize();
						_searchOpt = strToJson(_searchOpt);
						_searchOpt[ "sDate" ] = $( "#searchs_frm .searchs_dp_start" ).attr( "data-date" );
						_searchOpt[ "eDate" ] = $( "#searchs_frm .searchs_dp_end" ).attr( "data-date" );
						_searchOpt[ "sg_seqs" ] = sg_seqs;
						_searchOpt[ "sg_name" ] = sg_name;
						_searchOpt[ "keyword_type" ] = keyword_type;
						_searchOpt[ "search_keyword" ] = search_keyword;
						_searchOpt[ "company_code" ] = $("#company_code").val();
						
						// 검색 결과창 반영
						//searchResultBar.apply();
						searchResultMngr.destroy();						// 검색 결과 초기화
						searchResultMngr.addItem( $( "#searchs_frm .searchs_dp_start" ).attr( "data-date" )+"~"+  $( "#searchs_frm .searchs_dp_end" ).attr( "data-date" ), "검색기간" );		// 검색 결과 아이템 추가
						
					/*	console.log( "**********	검색 옵션		***********" );
						console.log( _searchOpt );
						console.log( "*************************************" );	*/

						updatePage();						
						if($( "#detail_more" )[ 0 ].checked){
							updatePage_more();
						} 
					}
					
				}			
			});
			// 차트 초기화
			charts.length = 0;		
			
	}
		
		
				
		/*******		Handnler			********************************************************************************/
		function strToJson( $str ){
			var result = {};
			var tmpData = $str.split( "&" );
			$.each( tmpData, function(){
				if( this.length > 0 ) result[ this.split( "=" )[ 0 ] ] = this.split( "=" )[ 1 ];
			});
			return result;
		}
		function showChart( $tg ){
			if( !$( $tg ).attr( "data-first-ani" ) ){
				$( $tg ).attr( "data-first-ani", true );
				$( $tg ).css( { opacity : 0 } ).delay( 100 ).animate( { opacity : 1 }, 300 );
			}
		}
		
		function chk_session(){			
			var isAccessSession = "true";
			$.ajax({
				type : "POST"
				,url: "../common/sessionChkMethod.jsp"
				,dataType : 'text'
				//,async: false
				,success : function($result){
					isAccessSession = $result.trim();
					//alert("@@>>>" + isAccessSession);
					
				}			
			});
			//alert("@@>>"+isAccessSession);
			//console.log(isAccessSession);
			return isAccessSession;
		}	
		/*******		Handnler			********************************************************************************/			

		//다운로드 소스 넣을것

		/*******		Set			********************************************************************************/
		function setPage(){
			/*section별 엑셀 다운로드*/		
			setAjax_con_10();	/*Daily VOC TOP5*/			
			setAjax_con_20();	/*연관어 분석*/			
			setAjax_con_30();	/*정보추이*/	
			setAjax_con_40();	/*주제별 현황*/	
			setAjax_con_50();	/*VOC 속성정보*/	
			setAjax_con_60();	/*전체 VOC 리스트*/	
			setAjax_con_70();	/*실시간 포탈 TOP 노출 현황*/
			setAjax_con_80();	/*포탈 뉴스 댓글 현황*/
			setAjax_con_81();	/*포탈 뉴스 댓글 분석 - 감성별 점유율*/
			setAjax_con_82();	/*포탈 뉴스 댓글 분석 - 연관어 클라우드*/
			setAjax_con_91();	/*주요 이슈*/
			setAjax_con_92();	/*주요 이슈 관련정보*/
		}
		
		function setAjax_con_10(){
			var id = "navi_01";
			$("#"+id).find(".download").off("click");
			$("#"+id).find(".download").on("click",function(){
				$.ajax({
					type : "POST"
					,url: "../common/sessionChkMethod.jsp"
					,dataType : 'text'
					,success : function($result){
						var isAccessSession = $result.trim();
						if(isAccessSession == 'false'){
							window.location.href = '../../../riskv3/error/sessionerror.jsp';
						}else{
							var inputParams = $.extend({}, _searchOpt);		
							inputParams.rowLimit = "5";
							inputParams.mode = id; 
							inputParams.subject = "Daily VOC TOP5"; 
							var $target_btn  = $("#"+id).find(".ui_btn");
							//if( String( typeof( dataParams ) ).toLowerCase() == "string" && dataParams.indexOf( "{" ) > -1 ) dataParams = $.parseJSON( dataParams );
							$.ajax({ 
								url : "./excel/getExcelData.jsp"
								,type : "POST"
								,timeout : 1800000
								,dataType : "text"
								,data : inputParams
								,beforeSend: function(){
									$target_btn.addClass("loading");
								}	
								,success : function( responseUrl ){
								//	console.log("responseUrl");
								//	console.log(responseUrl );
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
						
					}			
				});
				
				
			});		
		
		}
		
		function setAjax_con_20(){
		
			var id = "navi_02";
			$("#"+id).find(".download").off("click");
			$("#"+id).find(".download").on("click",function(){
				
				$.ajax({
					type : "POST"
					,url: "../common/sessionChkMethod.jsp"
					,dataType : 'text'
					,success : function($result){
						var isAccessSession = $result.trim();
						if(isAccessSession == 'false'){
							window.location.href = '../../../riskv3/error/sessionerror.jsp';
						}else{

							var inputParams = $.extend({}, _searchOpt);		
							inputParams.rowLimit = "30";
							inputParams.mode = id; 
							inputParams.subject = "연관어 분석"; 
							var $target_btn  = $("#"+id).find(".ui_btn");
							//if( String( typeof( dataParams ) ).toLowerCase() == "string" && dataParams.indexOf( "{" ) > -1 ) dataParams = $.parseJSON( dataParams );
							$.ajax({ 
								url : "./excel/getExcelData.jsp"
								,type : "POST"
								,timeout : 1800000
								,dataType : "text"
								,data : inputParams
								,beforeSend: function(){
									$target_btn.addClass("loading");
								}	
								,success : function( responseUrl ){
								//	console.log("responseUrl");
								//	console.log(responseUrl );
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
						
					}			
				});
					
			});		
		
		}
		
		function setAjax_con_30(){
			var mode = "";
			var id = "navi_03";
			$("#"+id).find(".download").off("click");
			$("#"+id).find(".download").on("click",function(){
				
			if($("#info_trans_radio_group_01").is(":checked") == true){
				mode = "weekly";	 // Weekly 클릭 시
			} else if($("#info_trans_radio_group_02").is(":checked") == true){
				mode = "monthly";	 // monthly 클릭 시
			}
				$.ajax({
					type : "POST"
					,url: "../common/sessionChkMethod.jsp"
					,dataType : 'text'
					,success : function($result){
						var isAccessSession = $result.trim();
						if(isAccessSession == 'false'){
							window.location.href = '../../../riskv3/error/sessionerror.jsp';
						}else{

							var inputParams = $.extend({}, _searchOpt);		
							inputParams.rowLimit = "30";
							inputParams.mode = mode; 
							inputParams.subject = "정보추이"; 
							var $target_btn  = $("#"+id).find(".ui_btn");
							//if( String( typeof( dataParams ) ).toLowerCase() == "string" && dataParams.indexOf( "{" ) > -1 ) dataParams = $.parseJSON( dataParams );
							$.ajax({ 
								url : "./excel/getExcelData.jsp"
								,type : "POST"
								,timeout : 1800000
								,dataType : "text"
								,data : inputParams
								,beforeSend: function(){
									$target_btn.addClass("loading");
								}	
								,success : function( responseUrl ){
								//	console.log("responseUrl");
								//	console.log(responseUrl );
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
						
					}			
				});
			});		
		
		}
		
		function setAjax_con_40(){
		
			var id = "navi_04";
			$("#"+id).find(".download").off("click");
			$("#"+id).find(".download").on("click",function(){
				$.ajax({
					type : "POST"
					,url: "../common/sessionChkMethod.jsp"
					,dataType : 'text'
					,success : function($result){
						var isAccessSession = $result.trim();
						if(isAccessSession == 'false'){
							window.location.href = '../../../riskv3/error/sessionerror.jsp';
						}else{

							var inputParams = $.extend({}, _searchOpt);		
							inputParams.mode = id; 
							inputParams.subject = "주제별 현황"; 
							var $target_btn  = $("#"+id).find(".ui_btn");
							//if( String( typeof( dataParams ) ).toLowerCase() == "string" && dataParams.indexOf( "{" ) > -1 ) dataParams = $.parseJSON( dataParams );
							$.ajax({ 
								url : "./excel/getExcelData.jsp"
								,type : "POST"
								,timeout : 1800000
								,dataType : "text"
								,data : inputParams
								,beforeSend: function(){
									$target_btn.addClass("loading");
								}	
								,success : function( responseUrl ){
								//	console.log("responseUrl");
								//	console.log(responseUrl );
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
						
					}			
				});
				
			});		
		
		}
		
		function setAjax_con_50(){
		
			var id = "navi_05";
			$("#"+id).find(".download").off("click");
			$("#"+id).find(".download").on("click",function(){
				
				$.ajax({
					type : "POST"
					,url: "../common/sessionChkMethod.jsp"
					,dataType : 'text'
					,success : function($result){
						var isAccessSession = $result.trim();
						if(isAccessSession == 'false'){
							window.location.href = '../../../riskv3/error/sessionerror.jsp';
						}else{
							var inputParams = $.extend({}, _searchOpt);		
							inputParams.mode = id; 

							if($("#voc_attr_tab_01").is(":checked") == true){	// 전체 클릭 시
								inputParams.product_code = "0";	 
								inputParams.subject = "VOC 속성정보 - 전체"; 
							} else if($("#voc_attr_tab_02").is(":checked") == true){	// 프리미엄 클릭 시
								inputParams.product_code = "1";	 
								inputParams.subject = "VOC 속성정보 - 프리미엄"; 
							} else if($("#voc_attr_tab_03").is(":checked") == true){	// PLCC 클릭 시
								inputParams.product_code = "5";	 
								inputParams.subject = "VOC 속성정보 - PLCC"; 
							}
							var $target_btn  = $("#"+id).find(".ui_btn");
							//if( String( typeof( dataParams ) ).toLowerCase() == "string" && dataParams.indexOf( "{" ) > -1 ) dataParams = $.parseJSON( dataParams );
							$.ajax({ 
								url : "./excel/getExcelData.jsp"
								,type : "POST"
								,timeout : 1800000
								,dataType : "text"
								,data : inputParams
								,beforeSend: function(){
									$target_btn.addClass("loading");
								}	
								,success : function( responseUrl ){
								//	console.log("responseUrl");
								//	console.log(responseUrl );
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
						
					}			
				});
				
			});		
		
		}

		function setAjax_con_60(){
		
			var id = "navi_06";
			$("#"+id).find(".download").off("click");
			$("#"+id).find(".download").on("click",function(){
				
				$.ajax({
					type : "POST"
					,url: "../common/sessionChkMethod.jsp"
					,dataType : 'text'
					,success : function($result){
						var isAccessSession = $result.trim();
						if(isAccessSession == 'false'){
							window.location.href = '../../../riskv3/error/sessionerror.jsp';
						}else{
							var inputParams = $.extend({}, _searchOpt);		
							inputParams.mode = id; 
							inputParams.subject = "전체 VOC 리스트"; 
							var $target_btn  = $("#"+id).find(".ui_btn");
							//if( String( typeof( dataParams ) ).toLowerCase() == "string" && dataParams.indexOf( "{" ) > -1 ) dataParams = $.parseJSON( dataParams );
							$.ajax({ 
								url : "./excel/getExcelData.jsp"
								,type : "POST"
								,timeout : 1800000
								,dataType : "text"
								,data : inputParams
								,beforeSend: function(){
									$target_btn.addClass("loading");
								}	
								,success : function( responseUrl ){
								//	console.log("responseUrl");
								//	console.log(responseUrl );
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
						
					}			
				});
				
			});		
		
		}
				
		function setAjax_con_70(){
		
			var id = "navi_07";
			$("#"+id).find(".download").off("click");
			$("#"+id).find(".download").on("click",function(){
				
				$.ajax({
					type : "POST"
					,url: "../common/sessionChkMethod.jsp"
					,dataType : 'text'
					,success : function($result){
						var isAccessSession = $result.trim();
						if(isAccessSession == 'false'){
							window.location.href = '../../../riskv3/error/sessionerror.jsp';
						}else{

							var inputParams = $.extend({}, _searchOpt);		
							inputParams.mode = id; 
							inputParams.subject = "실시간 포탈 TOP 노출 현황"; 
							var $target_btn  = $("#"+id).find(".ui_btn");
							//if( String( typeof( dataParams ) ).toLowerCase() == "string" && dataParams.indexOf( "{" ) > -1 ) dataParams = $.parseJSON( dataParams );
							$.ajax({ 
								url : "./excel/getExcelData.jsp"
								,type : "POST"
								,timeout : 1800000
								,dataType : "text"
								,data : inputParams
								,beforeSend: function(){
									$target_btn.addClass("loading");
								}	
								,success : function( responseUrl ){
								//	console.log("responseUrl");
								//	console.log(responseUrl );
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
						
					}			
				});
				
			});		
		
		}		
		
		function setAjax_con_80(){
		
			var id = "navi_08";
			$("#"+id).find(".download").off("click");
			$("#"+id).find(".download").on("click",function(){
				$.ajax({
					type : "POST"
					,url: "../common/sessionChkMethod.jsp"
					,dataType : 'text'
					,success : function($result){
						var isAccessSession = $result.trim();
						if(isAccessSession == 'false'){
							window.location.href = '../../../riskv3/error/sessionerror.jsp';
						}else{
							var inputParams = $.extend({}, _searchOpt);		
							inputParams.mode = id; 
							inputParams.subject = "포탈 뉴스 댓글 현황"; 
							inputParams.date=$( "#dp_01" ).attr( "data-date" );
							var $target_btn  = $("#"+id).find(".ui_btn");
							//if( String( typeof( dataParams ) ).toLowerCase() == "string" && dataParams.indexOf( "{" ) > -1 ) dataParams = $.parseJSON( dataParams );
							$.ajax({ 
								url : "./excel/getExcelData.jsp"
								,type : "POST"
								,timeout : 1800000
								,dataType : "text"
								,data : inputParams
								,beforeSend: function(){
									$target_btn.addClass("loading");
								}	
								,success : function( responseUrl ){
								//	console.log("responseUrl");
								//	console.log(responseUrl );
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
						
					}			
				});
				
			});		
		
		}		
		
		function setAjax_con_81(){
		
			var id = "senti_count";
			$("#"+id).find(".download").off("click");
			$("#"+id).find(".download").on("click",function(){
				
				
				$.ajax({
					type : "POST"
					,url: "../common/sessionChkMethod.jsp"
					,dataType : 'text'
					,success : function($result){
						var isAccessSession = $result.trim();
						if(isAccessSession == 'false'){
							window.location.href = '../../../riskv3/error/sessionerror.jsp';
						}else{
							var inputParams = $.extend({}, _searchOpt);		
							inputParams.mode = id; 
							inputParams.md_seq = $("input[name=md_seq]").val(); //댓글 기사 번호
							inputParams.date=$( "#dp_01" ).attr( "data-date" );
							inputParams.subject = "포탈 뉴스 댓글 분석 - 감성별 점유율"; 
							var $target_btn  = $("#"+id).find(".ui_btn");
							//if( String( typeof( dataParams ) ).toLowerCase() == "string" && dataParams.indexOf( "{" ) > -1 ) dataParams = $.parseJSON( dataParams );
							$.ajax({ 
								url : "./excel/getExcelData.jsp"
								,type : "POST"
								,timeout : 1800000
								,dataType : "text"
								,data : inputParams
								,beforeSend: function(){
									$target_btn.addClass("loading");
								}	
								,success : function( responseUrl ){
								//	console.log("responseUrl");
								//	console.log(responseUrl );
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
						
					}			
				});
				
			});		
		
		}		
		
		function setAjax_con_82(){
		
			var id = "rel_cloud";
			$("#"+id).find(".download").off("click");				
			$("#"+id).find(".download").on("click",function(){
				
				$.ajax({
					type : "POST"
					,url: "../common/sessionChkMethod.jsp"
					,dataType : 'text'
					,success : function($result){
						var isAccessSession = $result.trim();
						if(isAccessSession == 'false'){
							window.location.href = '../../../riskv3/error/sessionerror.jsp';
						}else{
							var inputParams = $.extend({}, _searchOpt);		
							inputParams.mode = id; 
							inputParams.date=$( "#dp_01" ).attr( "data-date" );
							inputParams.subject = "포탈 뉴스 댓글 분석 - 연관어 클라우드"; 
							inputParams.md_seq = $("input[name=md_seq]").val(); //댓글 기사 번호
							var $target_btn  = $("#"+id).find(".ui_btn");
							//if( String( typeof( dataParams ) ).toLowerCase() == "string" && dataParams.indexOf( "{" ) > -1 ) dataParams = $.parseJSON( dataParams );
							$.ajax({ 
								url : "./excel/getExcelData.jsp"
								,type : "POST"
								,timeout : 1800000
								,dataType : "text"
								,data : inputParams
								,beforeSend: function(){
									$target_btn.addClass("loading");
								}	
								,success : function( responseUrl ){
								//	console.log("responseUrl");
								//	console.log(responseUrl );
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
						
					}			
				});
				
			});		
		
		}		

		function setAjax_con_91(){
		
			var id = "issue_info";
			$("#"+id).find(".download").off("click");
			$("#"+id).find(".download").on("click",function(){
				$.ajax({
					type : "POST"
					,url: "../common/sessionChkMethod.jsp"
					,dataType : 'text'
					,success : function($result){
						var isAccessSession = $result.trim();
						if(isAccessSession == 'false'){
							window.location.href = '../../../riskv3/error/sessionerror.jsp';
						}else{
							var inputParams = $.extend({}, _searchOpt);		
							inputParams.mode = id; 
							inputParams.issue_code = $("#s09_issue option:selected").val(); 
							inputParams.subject = "주요 이슈 - 정보추이"; 
							inputParams.md_seq = $("input[name=md_seq]").val(); //댓글 기사 번호
							var $target_btn  = $("#"+id).find(".ui_btn");
							//if( String( typeof( dataParams ) ).toLowerCase() == "string" && dataParams.indexOf( "{" ) > -1 ) dataParams = $.parseJSON( dataParams );
							$.ajax({ 
								url : "./excel/getExcelData.jsp"
								,type : "POST"
								,timeout : 1800000
								,dataType : "text"
								,data : inputParams
								,beforeSend: function(){
									$target_btn.addClass("loading");
								}	
								,success : function( responseUrl ){
								//	console.log("responseUrl");
								//	console.log(responseUrl );
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
						
					}			
				});
			});		
		
		}		

		function setAjax_con_92(){
		
			var id = "rel_info";
			$("#"+id).find(".download").off("click");
			$("#"+id).find(".download").on("click",function(){
				
				$.ajax({
					type : "POST"
					,url: "../common/sessionChkMethod.jsp"
					,dataType : 'text'
					,success : function($result){
						var isAccessSession = $result.trim();
						if(isAccessSession == 'false'){
							window.location.href = '../../../riskv3/error/sessionerror.jsp';
						}else{
							var inputParams = $.extend({}, _searchOpt);		
							inputParams.mode = id; 
							inputParams.issue_code = $("#s09_issue option:selected").val(); 
							inputParams.subject = "주요 이슈 - 관련정보"; 
							inputParams.md_seq = $("input[name=md_seq]").val(); //댓글 기사 번호
							var $target_btn  = $("#"+id).find(".ui_btn");
							//if( String( typeof( dataParams ) ).toLowerCase() == "string" && dataParams.indexOf( "{" ) > -1 ) dataParams = $.parseJSON( dataParams );
							$.ajax({ 
								url : "./excel/getExcelData.jsp"
								,type : "POST"
								,timeout : 1800000
								,dataType : "text"
								,data : inputParams
								,beforeSend: function(){
									$target_btn.addClass("loading");
								}	
								,success : function( responseUrl ){
								//	console.log("responseUrl");
								//	console.log(responseUrl );
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
					}			
				});
			});		
		}		
		
		/*******		Update			********************************************************************************/
		function updatePage(){
			/*Daily VOC TOP5*/
			updateAjax_con_11();
			updateAjax_con_12();
			updateAjax_con_13();
			updateAjax_con_14();
			updateAjax_con_15();
			updateAjax_con_16();
			updateAjax_con_17();
			updateAjax_con_18();
			/*연관어 분석*/
  			updateAjax_con_21();
			updateAjax_con_22();
			/*정보추이*/		
			updateAjax_con_31();
			updateAjax_con_32();
			/*주제별 현황*/
			updateAjax_con_41();
			updateAjax_con_42();
	}	
		function updatePage_more(){
			/*VOC 속성정보*/
			updateAjax_con_50();
			updateAjax_con_51();
			/*전체 VOC 리스트*/
			updateAjax_con_61();
			updateAjax_con_62();
			updateAjax_con_63();
			/*실시간 포탈 TOP 노출 현황*/
			updateAjax_con_70();
			/*포탈 뉴스 댓글 현황*/
			updateAjax_con_80();
			/*주요 이슈*/
			updateAjax_con_91();
			/*주요 이슈 관련정보*/			
			updateAjax_con_92();          
	}	
		
		function getVocList(){
			var inputParams = $.extend({}, _searchOpt);			
				
			showPopup_voc("rowCnt=10&mode=voc_list&company_code="+inputParams.company_code+"&sDate="+inputParams.sDate+"&eDate="+inputParams.eDate+'&sg_seqs='+inputParams.sg_seqs+'&keyword_type='+inputParams.keyword_type+'&search_keyword='+inputParams.search_keyword);			
		}			
		
		// Daily VOC TOP5 각각 옵션 바꿀때 사용 (대응내역, 대응여부, 진행상황)
		function updateAjax_con_10( issue_text, proceed_code, action_code, select_id_seq, mode ){
			var inputParams = $.extend({}, _searchOpt);	
		
			inputParams.mode = mode;
			inputParams.issue_text = issue_text;
			inputParams.proceed_code = proceed_code;
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
							,url: "./data/UpdateIssueData.jsp"
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
		function updateAjax_con_11(){
			var id = "ajax_con_11";
			$("#"+id+" > div.box_content").removeClass("ui_nodata");
			var inputParams = $.extend({}, _searchOpt);	
			
			inputParams.code = "1";
			inputParams.rowLimit = "5";
			var $loadingObj = $("#"+id);
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
							,url: "./data/getVocTopList.jsp"
							,timeout: 30000
							,data : inputParams
							,dataType : 'html'
							,async: true
							,beforeSend: function(){
								if($loadingObj != null){
									loading($loadingObj, true);
								}
							}				
							,success : function($result){
								if($loadingObj != null){
									loading($loadingObj, false);
								}			
								$("#"+id).empty().html($result);
												
							}				
						});	

					}
					
				}			
			});
			
			
			
		}	
		function updateAjax_con_12(){
			var id = "ajax_con_12";
			$("#"+id+" > div.box_content").removeClass("ui_nodata");
			var inputParams = $.extend({}, _searchOpt);	
			
			inputParams.code = "2";
			inputParams.rowLimit = "5";
			var $loadingObj = $("#"+id);			
			
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
							,url: "./data/getVocTopList.jsp"
							,timeout: 30000
							,data : inputParams
							,dataType : 'html'
							,async: true
							,beforeSend: function(){
								if($loadingObj != null){
									loading($loadingObj, true);
								}
							}			
							,success : function($result){
								if($loadingObj != null){
									loading($loadingObj, false);
								}				
								$("#"+id).empty().html($result);					
							}
						});	

					}
					
				}			
			});
			
			
			
		}	
		function updateAjax_con_13(){
			var id = "ajax_con_13";
			$("#"+id+" > div.box_content").removeClass("ui_nodata");
			var inputParams = $.extend({}, _searchOpt);	

			inputParams.code = "3";	
			inputParams.rowLimit = "5";
			var $loadingObj = $("#"+id);
			
			
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
							,url: "./data/getVocTopList.jsp"
							,timeout: 30000
							,data : inputParams
							,dataType : 'html'
							,async: true
							,beforeSend: function(){
								if($loadingObj != null){
									loading($loadingObj, true);
								}
							}
							,success : function($result){
								if($loadingObj != null){
									loading($loadingObj, false);
								}				
								$("#"+id).empty().html($result);					
							}
						});	

					}
					
				}			
			});
			
			
		}	
		
		function updateAjax_con_14(){
			var id = "ajax_con_14";
			$("#"+id+" > div.box_content").removeClass("ui_nodata");
			var inputParams = $.extend({}, _searchOpt);	
			
			inputParams.code = "4";			
			inputParams.rowLimit = "5";
			var $loadingObj = $("#"+id);			
			
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
							,url: "./data/getVocTopList.jsp"
							,timeout: 30000
							,data : inputParams
							,dataType : 'html'
							,async: true
							,beforeSend: function(){
								if($loadingObj != null){
									loading($loadingObj, true);
								}
							}				
							,success : function($result){
								if($loadingObj != null){
									loading($loadingObj, false);
								}				
								$("#"+id).empty().html($result);					
							}
						});	
					}
					
				}			
			});
				
			
		}	
		
		function updateAjax_con_15(){
			var id = "ajax_con_15";
			$("#"+id+" > div.box_content").removeClass("ui_nodata");
			var inputParams = $.extend({}, _searchOpt);	

			inputParams.code = "5";			
			inputParams.rowLimit = "5";
			var $loadingObj = $("#"+id);			
			
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
							,url: "./data/getVocTopList.jsp"
							,timeout: 30000
							,data : inputParams
							,dataType : 'html'
							,async: true
							,beforeSend: function(){
								if($loadingObj != null){
									loading($loadingObj, true);
								}
							}				
							,success : function($result){
								if($loadingObj != null){
									loading($loadingObj, false);
								}					
								$("#"+id).empty().html($result);					
							}
						});	

					}
					
				}			
			});
			
		}	
		
		
		function updateAjax_con_16(){
			var id = "ajax_con_16";
			$("#"+id+" > div.box_content").removeClass("ui_nodata");
			var inputParams = $.extend({}, _searchOpt);	
			
			inputParams.code = "6";			
			inputParams.rowLimit = "5";
			var $loadingObj = $("#"+id);			
			
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
							,url: "./data/getVocTopList.jsp"
							,timeout: 30000
							,data : inputParams
							,dataType : 'html'
							,async: true
							,beforeSend: function(){
								if($loadingObj != null){
									loading($loadingObj, true);
								}
							}				
							,success : function($result){
								if($loadingObj != null){
									loading($loadingObj, false);
								}
								
								$("#"+id).empty().html($result);					
							}			
						});	

					}
					
				}			
			});
			
			
			
		}	
		function updateAjax_con_17(){
			var id = "ajax_con_17";
			$("#"+id+" > div.box_content").removeClass("ui_nodata");
			var inputParams = $.extend({}, _searchOpt);	
			
			inputParams.code = "7";			
			inputParams.rowLimit = "5";
			var $loadingObj = $("#"+id);			

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
							,url: "./data/getVocTopList.jsp"
							,timeout: 30000
							,data : inputParams
							,dataType : 'html'
							,async: true
							,beforeSend: function(){
								if($loadingObj != null){
									loading($loadingObj, true);
								}
							}				
							,success : function($result){
								if($loadingObj != null){
									loading($loadingObj, false);
								}
								
								$("#"+id).empty().html($result);					
							}			
						});	

					}
					
				}			
			});
			
			
		}

		function updateAjax_con_18(){
			var data = null;
			var id = "ajax_con_18";
			$("#"+id+" > div.box_content").removeClass("ui_nodata");
			var inputParams = $.extend({}, _searchOpt);	
			inputParams.code = "8";			
			inputParams.rowLimit = "5";
			var $loadingObj = $("#"+id);			

			if(inputParams.company_code == "1"){
				$("#fraud_html").css("display","");
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
								,url: "./data/getVocTopList.jsp"
								,timeout: 30000
								,data : inputParams
								,dataType : 'html'
								,async: true
								,beforeSend: function(){
									if($loadingObj != null){
										loading($loadingObj, true);
									}
								}				
								,success : function($result){
									if($loadingObj != null){
										loading($loadingObj, false);
									}
									
									$("#"+id).empty().html($result);					
								}			
							});	
	
						}
						
					}			
				});
			}else{
				$("#fraud_html").css("display","none");
				
			}
		}
				
		function updateAjax_con_21(){
			var id = "s02_cloud";			
			var loading_id = "navi_02";			
			$( "#"+id ).empty();	
			$("#"+id).removeClass("ui_nodata");
			var inputParams = $.extend({}, _searchOpt);		
			
			inputParams.company_type = "1";	 // 1로 고정		
			inputParams.rowLimit = "20";
			var $loadingObj = $("#"+loading_id);
			
			$.ajax({
				type : "POST"
				,async : true
				,url: "./data/getReltaionKeywordList.jsp"
				,timeout: 30000
				,data : inputParams
				,dataType : 'json'				 
				,beforeSend: function(){
					if($loadingObj != null){
						loading($loadingObj, true);	
					}
				}
				,success : function($result){
					if($loadingObj != null){
						loading($loadingObj, false);
					}
					if($result.data.length>0){	
						var weight = 70;
						var item = "";
						$($result.data).each(function(i,e){
							item += "<span data-weight="+weight+" style='color:"+e.WORD_COLOR+"' title="+e.CNT+"건><a href=\"javascript:showPopup('rowCnt=10&sDate="+inputParams.sDate+"&eDate="+inputParams.eDate+'&company_code='+inputParams.company_code+'&sg_seqs='+inputParams.sg_seqs+'&keyword_type='+inputParams.keyword_type+'&search_keyword='+inputParams.search_keyword+'&rk_seq='+e.RK_SEQ+'&color='+e.WORD_COLOR+'&rel_cnt=('+e.CNT+'건)&Dtitle=연관어 분석&head_title='+e.WORD+"');\">"+e.WORD+"</a></span>";
							weight -= 2;							
						});				
						$( "#"+id ).append(item);		
						$("#"+id).awesomeCloud({
							"size" : {
								"grid" : 1,
								"factor" : 1,
								"normalize" : false
							},
							"options" : {
								"rotationRatio" : 0.2
							},
							"font" : "Malgun Gothic,'맑은고딕','돋움',Dotum,AppleGothic,Sans-serif,Tahoma",
							"shape" : "circle"
						});				
					//없을경우
					}else{
						$("#"+id).addClass("ui_nodata");
					}
				}					
			});				
		}
			
		function updateAjax_con_22(){
			var id = "s02_cloud_list";			
			var loading_id = "navi_02";			
			var inputParams = $.extend({}, _searchOpt);		
			
			inputParams.company_type = "1";	 // 1로 고정		
			inputParams.rowLimit = "10";
			var $loadingObj = $("#"+loading_id);
			
			inputParams.rowLimit = 10;
			
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
							,url: "./data/getReltaionKeywordTopList.jsp"
							,timeout: 30000
							,data : inputParams
							,dataType : 'html'
							,async: true
							,beforeSend: function(){
								if($loadingObj != null){
									loading($loadingObj, true);						
								}
							}
							,success : function($result){
								if($loadingObj != null){
									loading($loadingObj, false);
								}
								$("#"+id).find(".content_body").empty().html($result);					
							}
								
						});	
						
					}
					
				}			
			});
		
			
		}
		
		function updateAjax_con_31(){
			var id = "information_cnt";			
			var loading_id = "navi_03";			
			$("#"+id+" > div.box_content").removeClass("ui_nodata");
			var inputParams = $.extend({}, _searchOpt);		

			inputParams.company_type = "1";	 // 1로 고정		
			if($("#info_trans_radio_group_01").is(":checked") == true){
				inputParams.mode = "weekly_cnt";	 // Weekly 클릭 시
			} else if($("#info_trans_radio_group_02").is(":checked") == true){
				inputParams.mode = "monthly_cnt";	 // monthly 클릭 시
			}
			var param_typeCode = "1:"+inputParams.company_code;
			var $loadingObj = $("#"+loading_id);
			
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
							,url: "./data/getTotalNPreCount.jsp"
							,timeout: 30000
							,data : inputParams
							,dataType : 'html'
							,async: true
							,beforeSend: function(){
								if($loadingObj != null){
									loading($loadingObj, true);						
								}
							}
							,success : function($result){
								if($loadingObj != null){
									loading($loadingObj, false);
								}
								
							$("#"+id).find(".info_trend").empty().html($result);								
							$("#"+id).find(".dv").on("click",function(){	
							//팝업
							showPopup('rowCnt=10&sDate='+inputParams.sDate+'&eDate='+inputParams.eDate+'&company_code='+inputParams.company_code+'&TypeCode='+param_typeCode+'&sg_seqs='+inputParams.sg_seqs+'&keyword_type='+inputParams.keyword_type+'&mode='+inputParams.mode+'&search_keyword='+inputParams.search_keyword);
								
								});										
							}			
						});	
						

					}
					
				}			
			});
			
		}	
		
		
		function updateAjax_con_32(){
			var id = "s03_chart";
			var loading_id = "navi_03";			
			var target_mode = "";
			
			$("#"+id).removeClass("ui_nodata");
			var inputParams = $.extend({}, _searchOpt);

			inputParams.company_type = "1";	 // 1로 고정		
			if($("#info_trans_radio_group_01").is(":checked") == true){
				inputParams.mode = "weekly";	 // Weekly 클릭 시
				target_mode = "weekly";
			} else if($("#info_trans_radio_group_02").is(":checked") == true){
				inputParams.mode = "monthly";	 // monthly 클릭 시
				target_mode = "monthly";
			}
			var $loadingObj = $("#"+loading_id);
			
			
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
							,url: "./data/getEachTypeCountSerial.jsp"
							,timeout: 30000
							,data : inputParams
							,dataType : 'json'
							,async: true
							,beforeSend: function(){
								if($loadingObj != null){
									loading($loadingObj, true);
								}
							}
							,success : function($result){	
								var listener = function($e){
									var param_typeCode = "1:"+inputParams.company_code;
										if(target_mode == "weekly"){
											showPopup('rowCnt=10&sDate='+inputParams.sDate+'&eDate='+inputParams.eDate+'&target_date='+$e.item.dataContext.category+'&company_code='+inputParams.company_code+'&TypeCode='+param_typeCode+'&sg_seqs='+inputParams.sg_seqs+'&keyword_type='+inputParams.keyword_type+'&search_keyword='+inputParams.search_keyword);		
										} else if(target_mode == "monthly"){
											showPopup('rowCnt=10&sDate='+$e.item.dataContext.SDATE+'&eDate='+$e.item.dataContext.EDATE+'&company_code='+inputParams.company_code+'&TypeCode='+param_typeCode+'&sg_seqs='+inputParams.sg_seqs+'&keyword_type='+inputParams.keyword_type+'&search_keyword='+inputParams.search_keyword);													
										}	
								}	
								
									var chartData =[];
									var tmpCnt = 0;
									$($result.data).each(function(i,e){
										e["category"] = e.DATE;
										e["column-1"] = e.TOTAL;	
										e["column-2"] = e.NEG;
										chartData.push(e);
										tmpCnt+=Number(e.TOTAL);
									});
								
								if(tmpCnt>0){
								   (function(){
	                                    var chartOption = {
	                                        "export": {  
	                                            "enabled": true,  
	                                            "menu": []  
	                                        },
	                                        "type": "serial",
	                                        "categoryField": "category",
	                                        "columnSpacing": 0,
	                                        "columnWidth": 0.2,
	                                        "marginBottom": 0,
	                                        "marginRight": 0,
	                                        "marginTop": 15,
	                                        "colors": [
	                                            "#8a8a8a",
	                                            "#ea7070",
	                                            "#ccffcc",
	                                            "#000000"
	                                        ],
	                                        "addClassNames": true,
	                                        "fontSize": 12,
	                                        "pathToImages": "https://design.realsn.com/design_asset/img/amchart/",
	                                        "percentPrecision": 1,
	                                        
	                                        "categoryAxis": {
	                                            "autoRotateAngle": 0,
	                                            "autoRotateCount": 4,
	                                            "autoWrap": true,
	                                             "dateFormats": [
	                                                {
	                                                    "period": "fff",
	                                                    "format": "JJ:NN:SS"
	                                                },
	                                                {
	                                                    "period": "ss",
	                                                    "format": "JJ:NN:SS"
	                                                },
	                                                {
	                                                    "period": "mm",
	                                                    "format": "JJ:NN"
	                                                },
	                                                {
	                                                    "period": "hh",
	                                                    "format": "JJ:NN"
	                                                },
	                                                {
	                                                    "period": "DD",
	                                                    "format": "MM/DD"
	                                                },
	                                                {
	                                                    "period": "WW",
	                                                    "format": "MMM DD"
	                                                },
	                                                {
	                                                    "period": "MM",
	                                                    "format": "MMM"
	                                                },
	                                                {
	                                                    "period": "YYYY",
	                                                    "format": "YYYY"
	                                                }
	                                            ],
	                                            "equalSpacing": true,
	                                            "gridPosition": "start",
	                                          //"parseDates": true,
	                                            "twoLineMode": true,
	                                            "axisColor": "#D8D8D8",
	                                            "centerLabels": true,
	                                            "centerRotatedLabels": true,
	                                            "color": "#444444",
	                                            "gridAlpha": 0,
	                                          //"labelFrequency": 0,
	                                            "markPeriodChange": false,
	                                            "minHorizontalGap": 10,
	                                            "minVerticalGap": 0,
	                                            "tickLength": 0,
	                                            "titleFontSize": 12,
	                                            "titleRotation": 0
	                                        },
	                                        "chartCursor": {
	                                            "enabled": true,
	                                          //"categoryBalloonDateFormat": "YYYY-MM-DD",
	                                            "cursorColor": "#000000"
	                                        },
	                                        "chartScrollbar": {
	                                            "enabled": true,
	                                            "backgroundColor": "#ADADAD",
	                                            "dragIconHeight": 14,
	                                            "dragIconWidth": 14,
	                                            "graphFillAlpha": 0,
	                                            "gridAlpha": 0,
	                                            "offset": 15,
	                                            "scrollbarHeight": 4,
	                                            "selectedBackgroundColor": "#D8D8D8",
	                                            "selectedGraphLineColor": "",
	                                            "updateOnReleaseOnly": true
	                                        },
	                                        "trendLines": [],
	                                        "graphs": [
	                                            {
	                                                "balloonText": "<strong>[[title]]</strong> : [[value]]건 ([[percents]]%)",
	                                                "showHandOnHover": true,
	                                                "bulletBorderThickness": 0,
	                                                "color": "#FFFFFF",
	                                                "fillAlphas": 1,
	                                                "id": "AmGraph-1",
	                                                "title": "정보량",
	                                                "type": "column",
	                                                "valueAxis": "ValueAxis-1",
	                                                "valueField": "column-1"
	                                            },
	                                            {
	                                                "balloonText": "<strong>[[title]]</strong> : [[value]]%", /* percents */
	                                                "bullet": "round",
	                                                "bulletBorderAlpha": 1,
	                                                "bulletBorderColor": "#FFFFFF",
	                                                "bulletSize": 12,
	                                                "color": "#FFFFFF",
	                                                "id": "AmGraph-2",
	                                                "lineThickness": 2,
	                                                "title": "부정률",
	                                                "valueAxis": "ValueAxis-2",
	                                                "valueField": "column-2"
	                                            }
	                                        ],
	                                        "guides": [],
	                                        "valueAxes": [
	                                            {
	                                                "id": "ValueAxis-1",
	                                                "stackType": "regular",
	                                                "autoGridCount": false,
	                                                "axisThickness": 0,
	                                                "color": "#c0c0c0",
	                                                "dashLength": 2,
	                                                "gridAlpha": 1,
	                                                "gridColor": "#D8D8D8",
	                                                "tickLength": 0,
	                                                "title": ""
	                                            },
	                                            {
	                                                "id": "ValueAxis-2",
	                                                "position": "right",
	                                                "stackType": "regular",
	                                                "unit": "%",
	                                                "autoGridCount": false,
	                                                "axisThickness": 0,
	                                                "color": "#c0c0c0",
	                                                "dashLength": 2,
	                                                "gridAlpha": 1,
	                                                "gridColor": "#D8D8D8",
	                                                "gridThickness": 0,
	                                                "tickLength": 0,
	                                                "title": "",
	                                             	"maximum": 100,
	                                                "minimum": 0
	                                            }
	                                        ],
	                                        "allLabels": [],
	                                        "balloon": {},
	                                        "legend": {
	                                            "enabled": true,
	                                            "align": "center",
	                                            "autoMargins": false,
	                                            "color": "#444444",
	                                            "equalWidths": false,
	                                            "marginLeft": 0,
	                                            "marginRight": 0,
	                                            "marginTop": 0,
	                                            "markerLabelGap": 6,
	                                            "markerSize": 11,
	                                            "markerType": "circle",
	                                            "periodValueText": "",
	                                            "spacing": 20,
	                                            "valueText": "",
	                                            "valueWidth": 0,
	                                            "verticalGap": 0
	                                        },
	                                        "titles": [],
	                                        "dataProvider": chartData
	                                    };
	                                    var chart = AmCharts.makeChart( "s03_chart", chartOption );
	                                    
										chart.addListener( "clickGraphItem", listener);
										
											charts[0] = {id: id, chart: chart};
										
										
									//	charts[1] = {id: id, chart: chart};
										
/*										$("input:radio[name=info_trans_radio_group]").click(function(){	
										charts[1] = {id: id, chart: chart};
                                   		});	
*/
	                                })();
								} else{
									$("#s03_chart").addClass("ui_nodata");
								}
							}		
						});	
					}		
				}			
			});			
			
		}		
		
		function updateAjax_con_41(){		
			var id = "s04_01_chart";
			var loading_id = "navi_04";
			$("#"+id).removeClass("ui_nodata");
			var inputParams = $.extend({}, _searchOpt);

			inputParams.mode = "circle"; 

			var $loadingObj = $("#"+loading_id);
			
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
							,url: "./data/getEachTitleCountSerial.jsp"
							,timeout: 30000
							,data : inputParams
							,dataType : 'json'
							,async: true
							,beforeSend: function(){
								if($loadingObj != null){
									loading($loadingObj, true);
								}
							}
							,success : function($result){
								if($loadingObj != null){
									loading($loadingObj, false);
								}							
								var listener = function($e){
									if(inputParams.company_code == 1){
										var topic_type = '12';
									} else if(inputParams.company_code == 2){
										var topic_type = '22';				
									} else if(inputParams.company_code == 3){
										var topic_type = '32';	
									}									
									var param_typeCode = topic_type + ':' + $e.dataItem.dataContext.TOPIC_CODE;
									showPopup('rowCnt=10&sDate='+inputParams.sDate+'&eDate='+inputParams.eDate+'&company_code='+inputParams.company_code+'&TypeCode='+param_typeCode+'&sg_seqs='+inputParams.sg_seqs+'&keyword_type='+inputParams.keyword_type+'&search_keyword='+inputParams.search_keyword);								
								}				
								var chartData =[];
								var tmpCnt = 0;
								$($result.data).each(function(i,e){
									e["category"] = e.NAME;
									e["column-1"] = e.CNT;
									chartData.push(e);
									tmpCnt+=e.CNT;				
								});
								
								if(inputParams.company_code == "1"){
									if(tmpCnt>0){								
		                                (function(){
		                                    var chartOption = {
		                                        "export": {  
		                                            "enabled": true,  
		                                            "menu": []  
		                                        },
		                                        "type": "pie",
		                                        "balloonText": "<strong>[[category]]</strong> : [[value]]건 ([[percents]]%)",
		                                        "pieX": 95,
		                                        "innerRadius": 74,
		                                        "labelRadius": -20,
		                                        "labelText": "",
		                                        "pullOutRadius": "0%",
		                                        "radius": 95,
		                                        "startAngle": 90,
		                                        "startRadius": "0%",
		                                        "colors": [
		                                            "#737cda",
		                                            "#a698ed",
		                                            "#50c7d3",
		                                            "#92ddb3",
		                                            "#e2e250",
		                                            "#f49541",
													"#e66335",
		                                            "#cbcbcb"
		                                        ],
		                                        "hideLabelsPercent": 5,
		                                        "marginBottom": 0,
		                                        "marginTop": 0,
		                                        "maxLabelWidth": 100,
		                                        "outlineThickness": 3,
		                                        "pullOutDuration": 0,
		                                        "startDuration": 0,
		                                        "titleField": "category",
		                                        "valueField": "column-1",
		                                        "accessible": false,
		                                        "addClassNames": true,
		                                        "autoResize": false,
		                                        "fontSize": 12,
		                                        "percentPrecision": 1,
		                                        "allLabels": [],
		                                        "balloon": {},
		                                        "legend": {
		                                            "enabled": true,
		                                            "align": "center",
		                                            "autoMargins": false,
		                                            "color": "#444444",
		                                            "marginLeft": 0,
		                                            "marginRight": 0,
		                                            "markerLabelGap": 6,
		                                            "markerSize": 11,
		                                            "markerType": "circle",
		                                            "periodValueText": "",
		                                            "position": "right",
		                                            "spacing": 20,
		                                            "valueText": "[[percents]]%",
		                                            "valueWidth": 70,
		                                            "verticalGap": 3
		                                        },
		                                        "titles": [],
		                                        "dataProvider": chartData
		                                    };
		                                    
		                                    var chart = AmCharts.makeChart( id , chartOption );
											chart.addListener( "clickSlice", listener);										
											//charts.push( { id: id, chart: chart } );	
												charts[1] = {id: id, chart: chart};
		                                })(); 
									} else{
										$("#"+id).addClass("ui_nodata");
									}			
								}else{
									if(tmpCnt>0){								
		                                (function(){
		                                    var chartOption = {
		                                        "export": {  
		                                            "enabled": true,  
		                                            "menu": []  
		                                        },
		                                        "type": "pie",
		                                        "balloonText": "<strong>[[category]]</strong> : [[value]]건 ([[percents]]%)",
		                                        "pieX": 95,
		                                        "innerRadius": 74,
		                                        "labelRadius": -20,
		                                        "labelText": "",
		                                        "pullOutRadius": "0%",
		                                        "radius": 95,
		                                        "startAngle": 90,
		                                        "startRadius": "0%",
		                                        "colors": [
		                                            "#737cda",
		                                            "#a698ed",
		                                            "#50c7d3",
		                                            "#92ddb3",
		                                            "#e2e250",
		                                            "#f49541",
		                                            "#cbcbcb"
		                                        ],
		                                        "hideLabelsPercent": 5,
		                                        "marginBottom": 0,
		                                        "marginTop": 0,
		                                        "maxLabelWidth": 100,
		                                        "outlineThickness": 3,
		                                        "pullOutDuration": 0,
		                                        "startDuration": 0,
		                                        "titleField": "category",
		                                        "valueField": "column-1",
		                                        "accessible": false,
		                                        "addClassNames": true,
		                                        "autoResize": false,
		                                        "fontSize": 12,
		                                        "percentPrecision": 1,
		                                        "allLabels": [],
		                                        "balloon": {},
		                                        "legend": {
		                                            "enabled": true,
		                                            "align": "center",
		                                            "autoMargins": false,
		                                            "color": "#444444",
		                                            "marginLeft": 0,
		                                            "marginRight": 0,
		                                            "markerLabelGap": 6,
		                                            "markerSize": 11,
		                                            "markerType": "circle",
		                                            "periodValueText": "",
		                                            "position": "right",
		                                            "spacing": 20,
		                                            "valueText": "[[percents]]%",
		                                            "valueWidth": 70,
		                                            "verticalGap": 3
		                                        },
		                                        "titles": [],
		                                        "dataProvider": chartData
		                                    };
		                                    
		                                    var chart = AmCharts.makeChart( id , chartOption );
											chart.addListener( "clickSlice", listener);										
											//charts.push( { id: id, chart: chart } );	
												charts[1] = {id: id, chart: chart};
		                                })(); 
									} else{
										$("#"+id).addClass("ui_nodata");
									}			
								}
							}		
						});	
					}
					
				}			
			});
			
				
		}
		
		function updateAjax_con_42(){
			var id = "s04_02_chart";
			var loading_id = "navi_04";
			$("#"+id).removeClass("ui_nodata");
			var inputParams = $.extend({}, _searchOpt);

			inputParams.mode = "senti"; 

			var $loadingObj = $("#"+loading_id);
			
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
							,url: "./data/getEachTitleCountSerial.jsp"
							,timeout: 30000
							,data : inputParams
							,dataType : 'json'
							,async: true
							,beforeSend: function(){
								if($loadingObj != null){
									loading($loadingObj, true);
								}
							}
							,success : function($result){
								if($loadingObj != null){
									loading($loadingObj, false);
								}
								var listener = function($e){								
									var senti_code= "AmGraph-1" == $e.target.id ? "1" : "AmGraph-2" == $e.target.id ? "2" : "AmGraph-3" == $e.target.id ? "3" : "";  		
									if(inputParams.company_code == 1){
										var topic_type = '12';
										var senti_type = '14';
									} else if(inputParams.company_code == 2){
										var topic_type = '22';				
										var senti_type = '24';
									} else if(inputParams.company_code == 3){
										var topic_type = '32';	
										var senti_type = '34';
									}
									var param_typeCode = topic_type + ':' + $e.item.dataContext.CODE + '@' + senti_type + ':' + senti_code;
									showPopup('rowCnt=10&sDate='+inputParams.sDate+'&eDate='+inputParams.eDate+'&company_code='+inputParams.company_code+'&TypeCode='+param_typeCode+'&sg_seqs='+inputParams.sg_seqs+'&keyword_type='+inputParams.keyword_type+'&search_keyword='+inputParams.search_keyword);									
								}				                                    								
								var chartData =[];
								var tmpCnt = 0;
								$($result.data).each(function(i,e){
									e["category"] = e.NAME;
									e["column-1"] = e.POS_CNT; // 긍정 카운트
									e["column-2"] = e.NEG_CNT; // 부정 카운트
									e["column-3"] = e.NEU_CNT; // 중립 카운트
									chartData.push(e);
									tmpCnt+=Number(e.POS_CNT)+Number(e.NEG_CNT)+Number(e.NEU_CNT);				
								});								
								if(tmpCnt>0){								
	                                (function(){
	                                    var chartOption = {
	                                        "export": {  
	                                            "enabled": true,  
	                                            "menu": []  
	                                        },
	                                        "type": "serial",
	                                        "categoryField": "category",
	                                        "columnSpacing": 0,
	                                        "columnWidth": 0.2,
	                                        "marginBottom": 0,
	                                        "marginRight": 0,
	                                        "marginTop": 15,
	                                        "colors": [
	                                            "#5ea3e1",
	                                            "#ea7070",
	                                            "#a9a9a9",
	                                            "#000000"
	                                        ],
	                                        "addClassNames": true,
	                                        "fontSize": 12,
	                                        "pathToImages": "https://design.realsn.com/design_asset/img/amchart/",
	                                        "percentPrecision": 1,
	                                        "categoryAxis": {
	                                            "autoRotateAngle": 0,
	                                            "autoWrap": true,
	                                            "gridPosition": "start",
	                                            "axisColor": "#D8D8D8",
	                                            "boldPeriodBeginning": false,
	                                            "centerLabelOnFullPeriod": false,
	                                            "centerLabels": true,
	                                            "centerRotatedLabels": true,
	                                            "color": "#444444",
	                                            "gridAlpha": 0,
	                                            "markPeriodChange": false,
	                                            "minHorizontalGap": 0,
	                                            "minVerticalGap": 0,
	                                            "tickLength": 0,
	                                            "titleFontSize": 12,
	                                            "titleRotation": 0
	                                        },
	                                        "chartCursor": {
	                                            "enabled": true,
	                                            "categoryBalloonDateFormat": "YYYY-MM-DD",
	                                            "cursorColor": "#000000"
	                                        },
	                                        "trendLines": [],
	                                        "graphs": [
	                                            {
	                                                "balloonText": "<strong>[[title]]</strong> : [[value]]건 ([[percents]]%)",
	                                                "bulletBorderThickness": 0,
	                                                "color": "#FFFFFF",
	                                                "fillAlphas": 1,
	                                                "id": "AmGraph-1",
	                                                "title": "긍정",
	                                                "type": "column",
	                                                "valueAxis": "ValueAxis-1",
	                                                "valueField": "column-1"
	                                            },
	                                            {
	                                                "balloonText": "<strong>[[title]]</strong> : [[value]]건 ([[percents]]%)",
	                                                "bulletBorderThickness": 0,
	                                                "color": "#FFFFFF",
	                                                "fillAlphas": 1,
	                                                "id": "AmGraph-2",
	                                                "title": "부정",
	                                                "type": "column",
	                                                "valueAxis": "ValueAxis-2",
	                                                "valueField": "column-2"
	                                            },
	                                            {
	                                                "balloonText": "<strong>[[title]]</strong> : [[value]]건 ([[percents]]%)",
	                                                "bulletBorderThickness": 0,
	                                                "color": "#FFFFFF",
	                                                "fillAlphas": 1,
	                                                "id": "AmGraph-3",
	                                                "title": "중립",
	                                                "type": "column",
	                                                "valueAxis": "ValueAxis-3",
	                                                "valueField": "column-3"
	                                            }
	                                        ],
	                                        "guides": [],
	                                        "valueAxes": [
	                                            {
	                                                "id": "ValueAxis-1",
	                                                "stackType": "regular",
	                                                "autoGridCount": false,
	                                                "axisThickness": 0,
	                                                "color": "#c0c0c0",
	                                                "dashLength": 2,
	                                                "gridAlpha": 1,
	                                                "gridColor": "#D8D8D8",
	                                                "tickLength": 0,
	                                                "title": ""
	                                            }
	                                        ],
	                                        "allLabels": [],
	                                        "balloon": {},
	                                        "legend": {
	                                            "enabled": true,
	                                            "align": "center",
	                                            "autoMargins": false,
	                                            "color": "#444444",
	                                            "equalWidths": false,
	                                            "marginLeft": 0,
	                                            "marginRight": 0,
	                                            "markerLabelGap": 6,
	                                            "markerSize": 11,
	                                            "markerType": "circle",
	                                            "periodValueText": "",
	                                            "spacing": 20,
	                                            "valueText": "",
	                                            "valueWidth": 0,
	                                            "verticalGap": 0
	                                        },
	                                        "titles": [],
	                                        "dataProvider": chartData
	                                    }
	                                    var chart = AmCharts.makeChart( id , chartOption );
										chart.addListener( "clickGraphItem", listener);										
										//charts.push( { id: id, chart: chart } );								
											charts[2] = {id: id, chart: chart};
																									
	                                })();
								} else{
									$("#"+id).addClass("ui_nodata");									
								}
							}		
						});	

					}
					
				}			
			});
				
		}
		
		function updateAjax_con_50(){
			var loading_id = "navi_05";
			var id = "s_detail_01_chart";
			var inputParams = $.extend({}, _searchOpt);
			inputParams.company_type = "1";	 // 1로 고정		
			inputParams.mode = "voc_information"; 
			
			if($("#voc_attr_tab_01").is(":checked") == true){	// 전체 클릭 시
				inputParams.mode = "voc_information";	 
			} else if($("#voc_attr_tab_02").is(":checked") == true){	// 프리미엄 클릭 시
				inputParams.mode = "pr_information";	 
				inputParams.product_code = "1";	 
			} else if($("#voc_attr_tab_03").is(":checked") == true){	// PLCC 클릭 시
				inputParams.mode = "plcc_information";	 
				inputParams.product_code = "5";	 
			}

			var $loadingObj = $("#"+loading_id);
			
//			$("#"+id+" > div.box_content").removeClass("ui_nodata");
			$("#"+id).removeClass("ui_nodata");
			$("#voc_content").removeClass("box_content ui_nodata");

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
							,url: "./data/getEachTypeCountSerial.jsp"
							,timeout: 30000
							,data : inputParams
							,dataType : 'json'
							,async: true
							,beforeSend: function(){
								if($loadingObj != null){
									loading($loadingObj, true);
								}
							}
							,success : function($result){
								if($loadingObj != null){
									loading($loadingObj, false);
								}								
								var listener = function($e){					
									var category_code= "AmGraph-1" == $e.target.id ? "1" : "AmGraph-2" == $e.target.id ? "5" : "AmGraph-3" == $e.target.id ? "6" : "AmGraph-4" == $e.target.id ? "4" : "AmGraph-5" == $e.target.id ? "2" : "AmGraph-6" == $e.target.id ? "3" : "AmGraph-7" == $e.target.id ? "7" : "";  
									var product_type = $e.item.dataContext.TYPE;
									var product_code = $e.item.dataContext.CODE;											
									if(inputParams.company_code == 1){
										var category_type = '13';
									} else if(inputParams.company_code == 2){
										var category_type = '23';			
									} else if(inputParams.company_code == 3){
										var category_type = '33';				
									}									
									var param_typeCode= category_type + ':' + category_code + '@' + product_type + ':' + product_code; 
									//팝업
									showPopup('rowCnt=10&sDate='+inputParams.sDate+'&eDate='+inputParams.eDate+'&company_code='+inputParams.company_code+'&TypeCode='+param_typeCode+'&sg_seqs='+inputParams.sg_seqs+'&keyword_type='+inputParams.keyword_type+'&search_keyword='+inputParams.search_keyword);																
								}						
								var chartData =[];
								var tmpCnt = 0;
								$($result.data).each(function(i,e){
								if(inputParams.company_code == "1"){	
									e["category"] = e.IC_NAME;
									e["column-1"] = e.TYPE1;
									e["column-2"] = e.TYPE5;
									e["column-3"] = e.TYPE6;
									e["column-4"] = e.TYPE4;
									e["column-5"] = e.TYPE2;						
									e["column-6"] = e.TYPE3;						
									e["column-7"] = e.TYPE7;						
									chartData.push(e);
									tmpCnt+=(parseInt(e.TYPE1)+parseInt(e.TYPE2)+parseInt(e.TYPE3)+parseInt(e.TYPE4)+parseInt(e.TYPE5)+parseInt(e.TYPE6)+parseInt(e.TYPE7));
									} else if(inputParams.company_code == "2"){
									e["category"] = e.IC_NAME;
									e["column-1"] = e.TYPE1;
									e["column-2"] = e.TYPE5;
									e["column-3"] = e.TYPE6;
									e["column-4"] = e.TYPE4;
									e["column-5"] = e.TYPE2;						
									e["column-6"] = e.TYPE3;						
									e["column-7"] = e.TYPE7;						
									chartData.push(e);
									tmpCnt+=(parseInt(e.TYPE1)+parseInt(e.TYPE2)+parseInt(e.TYPE3)+parseInt(e.TYPE4)+parseInt(e.TYPE5)+parseInt(e.TYPE6)+parseInt(e.TYPE7));
									} else if(inputParams.company_code == "3"){
									e["category"] = e.IC_NAME;
									e["column-1"] = e.TYPE1;
									e["column-2"] = e.TYPE5;
									e["column-3"] = e.TYPE6;
									e["column-4"] = e.TYPE4;
									e["column-5"] = e.TYPE2;	
									e["column-6"] = e.TYPE3;						
									e["column-7"] = e.TYPE7;											
									chartData.push(e);
									tmpCnt+=(parseInt(e.TYPE1)+parseInt(e.TYPE2)+parseInt(e.TYPE3)+parseInt(e.TYPE4)+parseInt(e.TYPE5)+parseInt(e.TYPE6)+parseInt(e.TYPE7));
									}															
								});
									if(tmpCnt>0){
	                                    (function(){
	                                        var chartOption = {
	                                            "export": {  
	                                                "enabled": true,  
	                                                "menu": []  
	                                            },
	                                            "type": "serial",
	                                            "categoryField": "category",
	                                            "columnSpacing": 0,
	                                            "columnWidth": 0.54,
	                                            "rotate": true,
	                                            "marginBottom": 0,
	                                            "marginRight": 0,
	                                            "marginTop": 0,
	                                            "colors": [
	                                                "#c8a6ee",
	                                                "#f58a9e",
	                                                "#9dcefa",
	                                                "#92a5eb",
	                                                "#bcda7c",
	                                                "#80ceaa",
	                                                "#cbcbcb"
	                                            ],
	                                            "addClassNames": true,
	                                            "fontSize": 12,
	                                            "pathToImages": "http://design.realsn.com/design_asset/img/amchart/",
	                                            "percentPrecision": 1,
	                                            "categoryAxis": {
	                                                "autoRotateAngle": 0,
	                                                "autoWrap": true,
	                                                "gridPosition": "start",
	                                                "axisColor": "#D8D8D8",
	                                                "axisThickness": 0,
	                                                "boldPeriodBeginning": false,
	                                                "centerLabelOnFullPeriod": false,
	                                                "centerLabels": true,
	                                                "centerRotatedLabels": true,
	                                                "color": "#444444",
	                                                "gridAlpha": 0,
	                                                "gridThickness": 0,
	                                                "markPeriodChange": false,
	                                                "minHorizontalGap": 0,
	                                                "minVerticalGap": 0,
	                                                "tickLength": 0,
	                                                "titleFontSize": 12,
	                                                "titleRotation": 0
	                                            },
	                                            "chartCursor": {
	                                                "enabled": true,
	                                                "categoryBalloonDateFormat": "YYYY-MM-DD",
	                                                "cursorColor": "#000000"
	                                            },
	                                            "trendLines": [],
	                                            "graphs": [
	                                                {
	                                                    "balloonText": "<strong>[[title]]</strong> : [[value]]건 ([[percents]]%)",
	                                                    "bulletBorderThickness": 0,
	                                                    "color": "#FFFFFF",
	                                                    "fillAlphas": 1,
	                                                    "id": "AmGraph-1",
	                                                    "labelText": "[[percents]]%",
	                                                    "title": "기사",
	                                                    "type": "column",
	                                                    "valueAxis": "ValueAxis-1",
	                                                    "valueField": "column-1"
	                                                },
	                                                {
	                                                    "balloonText": "<strong>[[title]]</strong> : [[value]]건 ([[percents]]%)",
	                                                    "bulletBorderThickness": 0,
	                                                    "color": "#FFFFFF",
	                                                    "fillAlphas": 1,
	                                                    "id": "AmGraph-2",
	                                                    "labelText": "[[percents]]%",
	                                                    "title": "클레임",
	                                                    "type": "column",
	                                                    "valueAxis": "ValueAxis-2",
	                                                    "valueField": "column-2"
	                                                },
	                                                {
	                                                    "balloonText": "<strong>[[title]]</strong> : [[value]]건 ([[percents]]%)",
	                                                    "bulletBorderThickness": 0,
	                                                    "color": "#FFFFFF",
	                                                    "fillAlphas": 1,
	                                                    "id": "AmGraph-3",
	                                                    "labelText": "[[percents]]%",
	                                                    "title": "칭찬/제안",
	                                                    "type": "column",
	                                                    "valueAxis": "ValueAxis-3",
	                                                    "valueField": "column-3"
	                                                },
	                                                {
	                                                    "balloonText": "<strong>[[title]]</strong> : [[value]]건 ([[percents]]%)",
	                                                    "bulletBorderThickness": 0,
	                                                    "color": "#FFFFFF",
	                                                    "fillAlphas": 1,
	                                                    "id": "AmGraph-4",
	                                                    "labelText": "[[percents]]%",
	                                                    "title": "후기",
	                                                    "type": "column",
	                                                    "valueAxis": "ValueAxis-4",
	                                                    "valueField": "column-4"
	                                                },
	                                                {
	                                                    "balloonText": "<strong>[[title]]</strong> : [[value]]건 ([[percents]]%)",
	                                                    "bulletBorderThickness": 0,
	                                                    "color": "#FFFFFF",
	                                                    "fillAlphas": 1,
	                                                    "id": "AmGraph-5",
	                                                    "labelText": "[[percents]]%",
	                                                    "title": "문의",
	                                                    "type": "column",
	                                                    "valueAxis": "ValueAxis-5",
	                                                    "valueField": "column-5"
	                                                },
	                                                {
	                                                    "balloonText": "<strong>[[title]]</strong> : [[value]]건 ([[percents]]%)",
	                                                    "bulletBorderThickness": 0,
	                                                    "color": "#FFFFFF",
	                                                    "fillAlphas": 1,
	                                                    "id": "AmGraph-6",
	                                                    "labelText": "[[percents]]%",
	                                                    "title": "단순언급",
	                                                    "type": "column",
	                                                    "valueAxis": "ValueAxis-6",
	                                                    "valueField": "column-6"
	                                                },
	                                                {
	                                                    "balloonText": "<strong>[[title]]</strong> : [[value]]건 ([[percents]]%)",
	                                                    "bulletBorderThickness": 0,
	                                                    "color": "#FFFFFF",
	                                                    "fillAlphas": 1,
	                                                    "id": "AmGraph-7",
	                                                    "labelText": "[[percents]]%",
	                                                    "title": "기타",
	                                                    "type": "column",
	                                                    "valueAxis": "ValueAxis-7",
	                                                    "valueField": "column-7"
	                                                }
	                                            ],
	                                            "guides": [],
	                                            "valueAxes": [
	                                                {
	                                                    "id": "ValueAxis-1",
	                                                    "stackType": "100%",
	                                                    "zeroGridAlpha": 0,
	                                                    "autoGridCount": false,
	                                                    "axisThickness": 0,
	                                                    "color": "#c0c0c0",
	                                                    "dashLength": 2,
	                                                    "gridAlpha": 1,
	                                                    "gridColor": "#D8D8D8",
	                                                    "gridThickness": 0,
	                                                    "labelsEnabled": false,
	                                                    "tickLength": 0,
	                                                    "title": ""
	                                                }
	                                            ],
	                                            "allLabels": [],
	                                            "balloon": {},
	                                            "legend": {
	                                                "enabled": true,
	                                                "align": "center",
	                                                "autoMargins": false,
	                                                "color": "#444444",
	                                                "equalWidths": false,
	                                                "marginLeft": 0,
	                                                "marginRight": 0,
	                                                "markerLabelGap": 6,
	                                                "markerSize": 11,
	                                                "markerType": "circle",
	                                                "periodValueText": "",
	                                                "spacing": 20,
	                                                "valueText": "",
	                                                "valueWidth": 0,
	                                                "verticalGap": 0
	                                            },
	                                            "titles": [],
	                                            "dataProvider": chartData
	                                        }
	                                    	var chart = AmCharts.makeChart( id , chartOption );
											chart.addListener( "clickGraphItem", listener);										
										//	charts.push( { id: id, chart: chart } );			
											
												charts[3] = {id: id, chart: chart};
																																							
	                                    })();
									} else{
										$("#"+id).addClass("ui_nodata");
										$("#voc_content").addClass("box_content ui_nodata");										
									}			
					   		 }

					  });

					}					
				}			
			});		
		}	
		
		function updateAjax_con_51(){
			var id = "info_senti";
			var loading_id = "navi_05";
			$("#"+id+" > div.box_content").removeClass("ui_nodata");
			var inputParams = $.extend({}, _searchOpt);	
			
			if($("#voc_attr_tab_01").is(":checked") == true){	// 전체 클릭 시
				inputParams.mode = "voc_information";	 
			} else if($("#voc_attr_tab_02").is(":checked") == true){	// 프리미엄 클릭 시
				inputParams.mode = "pr_information";	 
			} else if($("#voc_attr_tab_03").is(":checked") == true){	// PLCC 클릭 시
				inputParams.mode = "plcc_information";	 
			} else if(inputParams.mode == undefined){
				$("#voc_attr_tab_01").val("0").prop("checked", true);									
				$('input[name="voc_attr_tab"]:checked').val("0");
				inputParams.mode = "voc_information";	 				
			}						
			inputParams.product_code = $('input[name="voc_attr_tab"]:checked').val();
			inputParams.company_type = "1";	 // 1로 고정		
			
			if(inputParams.company_code == 1){
				inputParams.senti_type = '14';
			} else if(inputParams.company_code == 2){
				inputParams.senti_type = '24';			
			} else if(inputParams.company_code == 3){
				inputParams.senti_type = '34';				
			}

			var $loadingObj = $("#"+loading_id);
				
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
							,url: "./data/getInfoList.jsp"
							,timeout: 30000
							,data : inputParams
							,dataType : 'html'
							,async: true
							,beforeSend: function(){
								if($loadingObj != null){
									loading($loadingObj, true);
								}
							}				
							,success : function($result){
								if($loadingObj != null){
									loading($loadingObj, false);
								}						
								$("#"+id).find("#info_sentilist").empty().html($result);					
							}										
						});	
					}					
				}			
			});	
		}
		
		function updateAjax_con_61(pageNum){
			if(null == pageNum  || undefined == pageNum ){
				pageNum = 1 ;
			}
			
			var id = "voc_pos";
			var loading_id = "navi_06";
			$("#"+id+" > div.box_content").removeClass("ui_nodata");
			var inputParams = $.extend({}, _searchOpt);	
			inputParams.mode="pos";
			inputParams.company_type = "1";	 // 1로 고정		
			inputParams.pageNum=pageNum;
			var $loadingObj = $("#"+loading_id);
			
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
							,url: "./data/getVOCsenti.jsp"
							,timeout: 30000
							,data : inputParams
							,dataType : 'html'
							,async: true
							,beforeSend: function(){
								if($loadingObj != null){
									loading($loadingObj, true);
								}
							}
							,success : function($result){
								if($loadingObj != null){
									loading($loadingObj, false);
								}			
								$("#"+id).empty().html($result);
								
							$("#"+id).find(".download").off("click");	
							$("#"+id).find(".download").on("click",function(){
							var inputParams = $.extend({}, _searchOpt);		
							inputParams.mode = id; 
							inputParams.subject = "전체 VOC 리스트 : 긍정"; 
							var $target_btn  = $("#"+id).find(".ui_btn");
							//if( String( typeof( dataParams ) ).toLowerCase() == "string" && dataParams.indexOf( "{" ) > -1 ) dataParams = $.parseJSON( dataParams );
							$.ajax({ 
								url : "./excel/getExcelData.jsp"
								,type : "POST"
								,timeout : 1800000
								,dataType : "text"
								,data : inputParams
								,beforeSend: function(){
									$target_btn.addClass("loading");
								}	
								,success : function( responseUrl ){
								//	console.log("responseUrl");
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
							});				
							}		
						});	

					}
					
				}			
			});
			
			
				
		}	
		function updateAjax_con_62(pageNum){
			if(null == pageNum  || undefined == pageNum ){
				pageNum = 1 ;
			}
				
			var id = "voc_neg";
			var loading_id = "navi_06";
			$("#"+id+" > div.box_content").removeClass("ui_nodata");
			var inputParams = $.extend({}, _searchOpt);
			inputParams.mode="neg";
			inputParams.company_type = "1";	 // 1로 고정		
			inputParams.pageNum=pageNum;
			var $loadingObj = $("#"+loading_id);
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
							,url: "./data/getVOCsenti.jsp"
							,timeout: 30000
							,data : inputParams
							,dataType : 'html'
							,async: true
							,beforeSend: function(){
								if($loadingObj != null){
									loading($loadingObj, true);
								}
							}
							,success : function($result){
								if($loadingObj != null){
									loading($loadingObj, false);
								}			
								$("#"+id).empty().html($result);
								
							$("#"+id).find(".download").off("click");	
							$("#"+id).find(".download").on("click",function(){
							var inputParams = $.extend({}, _searchOpt);		
							inputParams.mode = id; 
							inputParams.subject = "전체 VOC 리스트 : 부정"; 
							var $target_btn  = $("#"+id).find(".ui_btn");
							//if( String( typeof( dataParams ) ).toLowerCase() == "string" && dataParams.indexOf( "{" ) > -1 ) dataParams = $.parseJSON( dataParams );
							$.ajax({ 
								url : "./excel/getExcelData.jsp"
								,type : "POST"
								,timeout : 1800000
								,dataType : "text"
								,data : inputParams
								,beforeSend: function(){
									$target_btn.addClass("loading");
								}	
								,success : function( responseUrl ){
								//	console.log("responseUrl");
								//	console.log(responseUrl );
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
							});				
							}	
						});	

					}
					
				}			
			});
				
		}	

		function updateAjax_con_63(pageNum){
			if(null == pageNum  || undefined == pageNum ){
				pageNum = 1 ;
			}
				
			var id = "voc_neu";
			var loading_id = "navi_06";
			$("#"+id+" > div.box_content").removeClass("ui_nodata");
			var inputParams = $.extend({}, _searchOpt);
			inputParams.mode="neu";
			inputParams.company_type = "1";	 // 1로 고정		
			inputParams.pageNum=pageNum;
			var $loadingObj = $("#"+loading_id);
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
							,url: "./data/getVOCsenti.jsp"
							,timeout: 30000
							,data : inputParams
							,dataType : 'html'
							,async: true
							,beforeSend: function(){
								if($loadingObj != null){
									loading($loadingObj, true);
								}
							}
							,success : function($result){
								if($loadingObj != null){
									loading($loadingObj, false);
								}			
								$("#"+id).empty().html($result);
								
							$("#"+id).find(".download").off("click");	
							$("#"+id).find(".download").on("click",function(){
							var inputParams = $.extend({}, _searchOpt);		
							inputParams.mode = id; 
							inputParams.subject = "전체 VOC 리스트 : 중립"; 
							var $target_btn  = $("#"+id).find(".ui_btn");
							//if( String( typeof( dataParams ) ).toLowerCase() == "string" && dataParams.indexOf( "{" ) > -1 ) dataParams = $.parseJSON( dataParams );
							$.ajax({ 
								url : "./excel/getExcelData.jsp"
								,type : "POST"
								,timeout : 1800000
								,dataType : "text"
								,data : inputParams
								,beforeSend: function(){
									$target_btn.addClass("loading");
								}	
								,success : function( responseUrl ){
								//	console.log("responseUrl");
								//	console.log(responseUrl );
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
							});				
							}	
						});	

					}
					
				}			
			});
				
		}	
		
		function updateAjax_con_70(pageNum){
			if(null == pageNum  || undefined == pageNum ){
				pageNum = 1 ;
			}
			var id = "potaltop";
			var loading_id = "navi_07";
			$("#"+id+" > div.box_content").removeClass("ui_nodata");
			var inputParams = $.extend({}, _searchOpt);	
			inputParams.mode="portalTop";
			inputParams.pageNum=pageNum;
			var $loadingObj = $("#"+loading_id);
			
			
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
							,url: "./data/getPortalDataList.jsp"
							,timeout: 30000
							,data : inputParams
							,dataType : 'html'
							,async: true
							,beforeSend: function(){
								if($loadingObj != null){
									loading($loadingObj, true);
								}
							}
							,success : function($result){
								if($loadingObj != null){
									loading($loadingObj, false);
								}
								
								$("#"+id).empty().html($result);
								
							}
								
						});	
						
					}
					
				}			
			});
			
			
		}			
		
		function updateAjax_con_80(pageNum){
			if(null == pageNum  || undefined == pageNum ){
				pageNum = 1 ;
			}
			var id = "potal_reply";
			var loading_id = "navi_08";
			$("#"+id+" > div.box_content").removeClass("ui_nodata");
			var inputParams = $.extend({}, _searchOpt);	
			inputParams.mode="portalReply";
			inputParams.pageNum=pageNum;
			inputParams.date=$( "#dp_01" ).attr( "data-date" );
			
			var $loadingObj = $("#"+loading_id);
			
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
							,url: "./data/getPortalReplyList.jsp"
							,timeout: 30000
							,data : inputParams
							,dataType : 'html'
							,async: true
							,beforeSend: function(){
								if($loadingObj != null){
									loading($loadingObj, true);
								}
							}
							,success : function($result){
								if($loadingObj != null){
									loading($loadingObj, false);
								}
								
								$("#"+id).empty().html($result);		
								
								$("#"+id).find(".reply_popup").on("click",function(){	
								var inputParams = $.extend({}, _searchOpt);
			    				inputParams.date=$( "#dp_01" ).attr( "data-date" );
								var select_mdseq = $(this).attr('value');
								//alert(inputParams.date);
			                    //팝업
			            		showPopup("mode=portal_reply&rowCnt=10&sDate="+inputParams.sDate+"&eDate="+inputParams.eDate+"&target_date="+inputParams.date+"&md_seq="+select_mdseq+"&head_title=전체");
								
								});											
								$("#s_detail_05_cloud").attr("style", "height: 260px; width: 430px;");		
								
								//기사를 클릭하면 포탈 뉴스 댓글 분석을 뿌려줌
								updateAjax_con_800($("#reply_md_seq").val(), $("#reply_title").val(), $("#reply_site_name").val());
							}
								
						});

					}
					
				}			
			});
			
				
			
		}	
		function updateAjax_con_800($md_seq, md_title, _md_site){
			
			$.ajax({
				type : "POST"
				,url: "../common/sessionChkMethod.jsp"
				,dataType : 'text'
				,success : function($result){
					var isAccessSession = $result.trim();
					
					if(isAccessSession == 'false'){
						window.location.href = '../../../riskv3/error/sessionerror.jsp';
					}else{
						//값이 없을 경우, 예외처리
						if(jQuery.type($md_seq)=="undefined" || $md_seq == "" ){
							$("#s_detail_05_chart").addClass("ui_nodata");				
							$("#s_detail_05_cloud").addClass("ui_nodata");
							return
						}
						//댓글 기사 선택시
						$("input[name=md_seq]").val($md_seq);
						$("#potalreply_list").find(".active").attr('class','title');
						$('#reply_'+$md_seq+'').attr('class','active');
						updateAjax_con_81($md_seq);
						updateAjax_con_82($md_seq);
						
					//	$("#article_name").text(" - " + "[" + _md_site + "] " + "'"+ md_title + "'");

					}
					
				}			
			});
			
		}
		
		
		function updateAjax_con_81($md_seq, md_title, _md_site){
			var id = "s_detail_05_chart";
			var loading_id = "navi_08";
			$("#"+id).removeClass("ui_nodata");
			var inputParams = $.extend({}, _searchOpt);
			inputParams.md_seq = $md_seq; //댓글 기사 번호
			inputParams.mode = "portal_reply"; //주제구분
			//값이 없을 경우, 예외처리
			if(jQuery.type($md_seq) == "undefined"  || $md_seq == ""){
				$("#"+id).addClass("ui_nodata");
				return
			}
			$("#"+id).empty();

		//	$("#article_name").text(" - " + "[" + _md_site + "] " + "'"+ md_title + "'");
			var $loadingObj = $("#"+loading_id);
			
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
							,url: "./data/getEachTypeCountPie.jsp"
							,timeout: 30000
							,data : inputParams
							,dataType : 'json'
							,async: true
							,beforeSend: function(){
								if($loadingObj != null){
									loading($loadingObj, true);						
								}
							}
							,success : function($result){
								if($loadingObj != null){
									loading($loadingObj, false);
								}
								
								var chartData =[];
								var tmpCnt = 0;
								$($result.data).each(function(i,e){
									e["category"] = e.TYPE; 
									e["column-1"] = e.CNT; 
									chartData.push(e);
									tmpCnt += e.CNT;
								});
								
								
								
							   (function(){
			                       var chartOption = {
			 						"export": {  
			                        "enabled": true,  
			                        "menu": []  
			                        },	
			                       	"id":"s_detail_05_chart",
			                        "type": "pie",
			                        "balloonText": "<strong>[[category]]</strong> : [[value]]건 ([[percents]]%)",
			                        "innerRadius": 74,
			                        "labelRadius": -20,
			                        "labelText": "",
			                        "pullOutRadius": "0%",
			                        "radius": 95,
			                        "startAngle": 0,
			                        "startRadius": "0%",
			                        "colors": [
			                            "#5ea3e1",
			                            "#ea7070",
			                            "#a9a9a9"
			                        ],
			                        "hideLabelsPercent": 5,
			                        "marginBottom": 0,
			                        "marginLeft": 20,
			                        "marginRight": 20,
			                        "marginTop": 0,
			                        "maxLabelWidth": 100,
			                        "outlineThickness": 3,
			                        "pullOutDuration": 0,
			                        "startDuration": 0,
			                        "titleField": "category",
			                        "valueField": "column-1",
			                        "accessible": false,
			                        "addClassNames": true,
			                        "fontSize": 12,
			                        "percentPrecision": 1,
			                        "allLabels": [
			                            {
			                                "align": "center",
			                                "color": "#999999",
			                                "id": "Label-1",
			                                "size": 11,
			                                "text": "VOLUME",
			                                "y": "40%"
			                            },
			                            {
			                                "align": "center",
			                                "color": "#000000",
			                                "id": "Label-2",
			                                "size": 30,
			                                "text": $result.TOTAL,
			                                "y": "45%"
			                            }
			                        ],
			                        "balloon": {},
			                        "legend": {
			                            "enabled": true,
			                            "align": "center",
			                            "autoMargins": false,
			                            "bottom": 0,
			                            "color": "#444444",
			                            "labelWidth": 100,
			                            "marginLeft": 0,
			                            "marginRight": 40,
			                            "markerLabelGap": 6,
			                            "markerSize": 11,
			                            "markerType": "circle",
			                            "periodValueText": "",
			                            "position": "right",
			                            "spacing": 0,
			                            "valueText": "[[percents]]%",
			                            "valueWidth": 60
			                        },
			                           "titles": [],
			                           "dataProvider": chartData
			                       }
			                       var chart = AmCharts.makeChart( "s_detail_05_chart", chartOption );

			                     	  chart.addListener("clickSlice", function( $e ){
			                    	  var inputParams = $.extend({}, _searchOpt);
									  var senti_name = $e.dataItem.dataContext.TYPE;		
			           				 // var md_seq =  $("input[name=md_seq]").val(); //댓글 기사 번호;
			           				  //팝업
			           				  showPopup("mode=portal_reply&rowCnt=10&sDate="+inputParams.sDate+"&eDate="+inputParams.eDate+"&md_seq="+$md_seq+"&senti="+$e.dataItem.dataContext.SENTI+"&head_title="+senti_name);
			                       });
			                       //파이그래프 내 전체 클릭시  팝업 띄우기 위해 변경- 2020.10 배태환 차장님 요청
			                       $( "#"+id+" text.amcharts-label.amcharts-label-Label-2" ).css( { "pointer-events": "all", "cursor": "pointer" } );
			                       $( "#"+id+" text.amcharts-label.amcharts-label-Label-2" ).css( { "pointer-events": "all", "cursor": "pointer" } );
			                       $( "#"+id+" text.amcharts-label.amcharts-label-Label-2" ).click( function(){

			                    	 //팝업
			            			showPopup("mode=portal_reply&rowCnt=10&sDate="+inputParams.sDate+"&eDate="+inputParams.eDate+"&md_seq="+$md_seq+"&head_title=전체");
			                       })
			                	    //charts.push( { id: id, chart: chart } );
											charts[4] = {id: id, chart: chart};
			                       
			                   })();
													
							   if(tmpCnt>0){
								$( "#"+id).removeClass( "ui_nodata" );
							   }else{
								$( "#"+id ).addClass( "ui_nodata" );
							   }
							}			
						});	

					}
					
				}			
			});
			
					
	}		
	function updateAjax_con_82($md_seq, md_title, _md_site){		
			var id = "s_detail_05_cloud";			
			var loading_id = "navi_08";			
			var item = ""; 		
			$("#"+id).find("div.item").remove();			
			$("#"+id).removeClass("ui_nodata");
			//값이 없을 경우, 예외처리
			if(jQuery.type($md_seq) == "undefined" || $md_seq == ""){
				$("#"+id).addClass("ui_nodata");
				return
			}
			
		//	$("#article_name").text(" - " + "[" + _md_site + "] " + "'"+ md_title + "'");
			
			var inputParams = $.extend({}, _searchOpt);
			inputParams.md_seq = $md_seq; //댓글 기사 번호

			var $loadingObj = $("#"+loading_id);
			
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
							,url: "./data/getRelatedKeywordList.jsp"
							,timeout: 30000
							,data : inputParams
							,dataType : 'json'
							,async: true
							,beforeSend: function(){
								if($loadingObj != null){
									loading($loadingObj, true);						
								}
							}
							,success : function($result){
								if($loadingObj != null){
									loading($loadingObj, false);
								}
								
								//연관키워드 중앙 텍스트
								//item ="<div class='item all'><a href='#'><strong>전체</strong><span></div>";
								item =  $('<div />', {
									'class' : 'item all'
								});
								item.append($('<a />',{
									'href' : '#',
									'click' : function(){
										//팝업
										showPopup("mode=relatedKey_reply_total&rowCnt=10&sDate="+inputParams.sDate+"&eDate="+inputParams.eDate+"&md_seq="+$md_seq+"&pat_seq="+$result.SEQS+"&head_title=연관어 - 전체");
										return false;
									}
								}));
								var temp = "";
								var temp2 = "";
								temp = "<strong>댓글연관어</strong>";
								temp2 = "<span style='color: #9d9d9d;'>총"+$result.TOTAL+"건</span>";
								temp+=temp2;
								item.find("a").append(temp);	
								$( "#"+id ).append(item);
								
								if($result.data.length > 0 ){				
									$($result.data).each(function(i,e){
										if(e.SENTI==1){
											item =  $('<div />', {
												'class' : 'item postive'
											});
										}else if(e.SENTI==2){
											item =  $('<div />', {
												'class' : 'item negative'
											});
										}else if(e.SENTI==3){
											item =  $('<div />', {
												'class' : 'item neutral'
											});
										}else{
											item =  $('<div />', {
												'class' : 'item neutral'
											});
										}
												
										item.append($('<a />',{
											'href' : '#',
											'title': (e.WORD+" ("+e.CNT+")" ),
											'click' : function(){
												//inputParams.rkSeq = e.SEQ;								
												var md_seq =  $("input[name=md_seq]").val(); //댓글 기사 번호;
												//팝업
												showPopup("mode=relatedKey_reply&rowCnt=10&sDate="+inputParams.sDate+"&eDate="+inputParams.eDate+"&md_seq="+md_seq+"&pat_seq="+e.SEQ+"&head_title="+e.WORD);
												return false;
											}
										}));	
										
										var temp = "";
										var temp2 = "";
										temp = "<strong>"+e.WORD+"</strong>";
										temp2 = "<span style='color: #9d9d9d;'>총"+e.CNT+"건</span>";
										temp+=temp2;
										item.find("a").append(temp);	
										$( "#"+id ).append(item);
										
									});	
									$("#"+id).data("elm_cloud").update();
									
									
								//반환 데이터 없을 경우
								}else{
									$("#"+id).find("div.item").remove();			
									$("#"+id).addClass("ui_nodata");
								}				
							}			
						});	


					}
					
				}			
			});			
						
		}
	
	
		function updateAjax_con_91(){
			var id = "s09_chart";
			var loading_id = "navi_09";
			$("#"+id).removeClass("ui_nodata");

			//이슈명, 이슈코드 셋팅
			var issue_name = $("#s09_issue option:selected").text();
			var issue = $("#s09_issue option:selected").val();
			var inputParams = $.extend({}, _searchOpt);

			inputParams.company_type = "1";	 // 1로 고정		
			inputParams.issue_code = $("#s09_issue option:selected").val(); 
			inputParams.mode = "issue_information"; 

			var $loadingObj = $("#"+loading_id);
			
			
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
							,url: "./data/getEachTypeCountSerial.jsp"
							,timeout: 30000
							,data : inputParams
							,dataType : 'json'
							,async: true
							,beforeSend: function(){
								if($loadingObj != null){
									loading($loadingObj, true);
								}
							}
							,success : function($result){
								if($loadingObj != null){
									loading($loadingObj, false);
								}
								var listener = function($e){						
									var target_date =  $e.item.dataContext.category;
									var select_sg_seq= "AmGraph-1" == $e.target.id ? "93" : "AmGraph-2" == $e.target.id ? "94" : "AmGraph-3" == $e.target.id ? "95" : "AmGraph-4" == $e.target.id ? "97" : "AmGraph-5" == $e.target.id ? "99" : "AmGraph-5" == $e.target.id ? "100" : "AmGraph-5" == $e.target.id ? "102" : "";  									
									if(inputParams.company_code == 1){
										var issue_type = '18';
									} else if(inputParams.company_code == 2){
										var issue_type = '28';				
									} else if(inputParams.company_code == 3){
										var issue_type = '38';			
									}		
									var param_typeCode = issue_type + ':' + $("#s09_issue option:selected").val();
									//팝업				
									showPopup('rowCnt=10&sDate='+inputParams.sDate+'&eDate='+inputParams.eDate+'&target_date='+target_date+'&company_code='+inputParams.company_code+'&TypeCode='+param_typeCode+'&select_sg_seq='+select_sg_seq+'&sg_seqs='+inputParams.sg_seqs+'&keyword_type='+inputParams.keyword_type+'&search_keyword='+inputParams.search_keyword);
								}												
								var chartData =[];
								var tmpCnt = 0;
								$($result.data).each(function(i,e){
									e["category"] = e.DATE;
									e["column-1"] = e.SG_TYPE1;
									e["column-2"] = e.SG_TYPE2;
									e["column-3"] = e.SG_TYPE3;
									e["column-4"] = e.SG_TYPE4;
									e["column-5"] = e.SG_TYPE5;						
									e["column-6"] = e.SG_TYPE6;						
									e["column-7"] = e.SG_TYPE7;						
									chartData.push(e);
									tmpCnt+=Number(e.SG_TYPE1)+Number(e.SG_TYPE2)+Number(e.SG_TYPE3)+Number(e.SG_TYPE4)+Number(e.SG_TYPE5)+Number(e.SG_TYPE6)+Number(e.SG_TYPE7);				
								});
								
								if(tmpCnt>0){
	                                (function(){
	                                    var chartOption = {
	                                        "type": "serial",
	                                        "categoryField": "category",
	                                        "columnSpacing": 0,
	                                        "columnWidth": 0.2,
	                                        "marginBottom": 0,
	                                        "marginRight": 10,
	                                        "marginTop": 5,
	                                        "colors": [
	                                            "#e8d112",
	                                            "#4dba57",
	                                            "#e95ca8",
	                                            "#8d66d4",
	                                            "#66bdda",
	                                            "#4968b7",
	                                            "#d54040"
	                                        ],
	                                        "addClassNames": true,
	                                        "fontSize": 12,
	                                        "pathToImages": "https://design.realsn.com/design_asset/img/amchart/",
	                                        "percentPrecision": 1,
	                                        "export": {
	                                            "enabled": true,
	                                            "menu": []
	                                        },
	                                        "categoryAxis": {
	                                            "autoRotateAngle": 0,
	                                            "autoRotateCount": 4,
	                                            "autoWrap": true,
	                                            "dateFormats": [
	                                                {
	                                                    "period": "fff",
	                                                    "format": "JJ:NN:SS"
	                                                },
	                                                {
	                                                    "period": "ss",
	                                                    "format": "JJ:NN:SS"
	                                                },
	                                                {
	                                                    "period": "mm",
	                                                    "format": "JJ:NN"
	                                                },
	                                                {
	                                                    "period": "hh",
	                                                    "format": "JJ:NN"
	                                                },
	                                                {
	                                                    "period": "DD",
	                                                    "format": "MM-DD"
	                                                },
	                                                {
	                                                    "period": "WW",
	                                                    "format": "MMM DD"
	                                                },
	                                                {
	                                                    "period": "MM",
	                                                    "format": "MMM"
	                                                },
	                                                {
	                                                    "period": "YYYY",
	                                                    "format": "YYYY"
	                                                }
	                                            ],
	                                            "equalSpacing": true,
	                                            "gridPosition": "start",
	                                            "parseDates": true,
	                                            "twoLineMode": true,
	                                            "axisColor": "#D8D8D8",
	                                            "centerLabels": true,
	                                            "centerRotatedLabels": true,
	                                            "color": "#444444",
	                                            "gridAlpha": 0,
	                                            "labelFrequency": 0,
	                                            "markPeriodChange": false,
	                                            "minHorizontalGap": 10,
	                                            "minVerticalGap": 0,
	                                            "tickLength": 0,
	                                            "titleFontSize": 12,
	                                            "titleRotation": 0
	                                        },
	                                        "chartCursor": {
	                                            "enabled": true,
	                                            "categoryBalloonDateFormat": "YYYY-MM-DD",
	                                            "cursorColor": "#000000"
	                                        },
	                                        "chartScrollbar": {
	                                            "enabled": true,
	                                            "backgroundColor": "#ADADAD",
	                                            "dragIconHeight": 14,
	                                            "dragIconWidth": 14,
	                                            "graphFillAlpha": 0,
	                                            "gridAlpha": 0,
	                                            "offset": 15,
	                                            "scrollbarHeight": 4,
	                                            "selectedBackgroundColor": "#D8D8D8",
	                                            "selectedGraphLineColor": "",
	                                            "updateOnReleaseOnly": true
	                                        },
	                                        "trendLines": [],
	                                        "graphs": [
	                                            {
	                                                "balloonText": "<strong>[[title]]</strong> : [[value]]([[percents]])%",
	                                                "bullet": "round",
	                                                "bulletBorderAlpha": 1,
	                                                "bulletBorderColor": "#FFFFFF",
	                                                "bulletSize": 12,
	                                                "color": "#FFFFFF",
	                                                "id": "AmGraph-1",
	                                                "lineThickness": 2,
	                                                "title": "언론",
	                                                "valueAxis": "ValueAxis-1",
	                                                "valueField": "column-1",
	                                                "showHandOnHover": true
	                                            },
	                                            {
	                                                "balloonText": "<strong>[[title]]</strong> : [[value]]([[percents]])%",
	                                                "bullet": "round",
	                                                "bulletBorderAlpha": 1,
	                                                "bulletBorderColor": "#FFFFFF",
	                                                "bulletSize": 12,
	                                                "color": "#FFFFFF",
	                                                "id": "AmGraph-2",
	                                                "lineThickness": 2,
	                                                "title": "블로그",
	                                                "valueAxis": "ValueAxis-2",
	                                                "valueField": "column-2",
	                                                "showHandOnHover": true
	                                            },
	                                            {
	                                                "balloonText": "<strong>[[title]]</strong> : [[value]]([[percents]])%",
	                                                "bullet": "round",
	                                                "bulletBorderAlpha": 1,
	                                                "bulletBorderColor": "#FFFFFF",
	                                                "bulletSize": 12,
	                                                "color": "#FFFFFF",
	                                                "id": "AmGraph-3",
	                                                "lineThickness": 2,
	                                                "title": "카페",
	                                                "valueAxis": "ValueAxis-3",
	                                                "valueField": "column-3",
	                                                "showHandOnHover": true
	                                            },
	                                            {
	                                                "balloonText": "<strong>[[title]]</strong> : [[value]]([[percents]])%",
	                                                "bullet": "round",
	                                                "bulletBorderAlpha": 1,
	                                                "bulletBorderColor": "#FFFFFF",
	                                                "bulletSize": 12,
	                                                "color": "#FFFFFF",
	                                                "id": "AmGraph-4",
	                                                "lineThickness": 2,
	                                                "title": "커뮤니티",
	                                                "valueAxis": "ValueAxis-4",
	                                                "valueField": "column-4",
	                                                "showHandOnHover": true
	                                            },
	                                            {
	                                                "balloonText": "<strong>[[title]]</strong> : [[value]]([[percents]])%",
	                                                "bullet": "round",
	                                                "bulletBorderAlpha": 1,
	                                                "bulletBorderColor": "#FFFFFF",
	                                                "bulletSize": 12,
	                                                "color": "#FFFFFF",
	                                                "id": "AmGraph-5",
	                                                "lineThickness": 2,
	                                                "title": "트위터",
	                                                "valueAxis": "ValueAxis-5",
	                                                "valueField": "column-5",
	                                                "showHandOnHover": true
	                                            },
	                                            {
	                                                "balloonText": "<strong>[[title]]</strong> : [[value]]([[percents]])%",
	                                                "bullet": "round",
	                                                "bulletBorderAlpha": 1,
	                                                "bulletBorderColor": "#FFFFFF",
	                                                "bulletSize": 12,
	                                                "color": "#FFFFFF",
	                                                "id": "AmGraph-6",
	                                                "lineThickness": 2,
	                                                "title": "페이스북",
	                                                "valueAxis": "ValueAxis-6",
	                                                "valueField": "column-6",
	                                                "showHandOnHover": true
	                                            },
	                                            {
	                                                "balloonText": "<strong>[[title]]</strong> : [[value]]([[percents]])%",
	                                                "bullet": "round",
	                                                "bulletBorderAlpha": 1,
	                                                "bulletBorderColor": "#FFFFFF",
	                                                "bulletSize": 12,
	                                                "color": "#FFFFFF",
	                                                "id": "AmGraph-7",
	                                                "lineThickness": 2,
	                                                "title": "유튜브",
	                                                "valueAxis": "ValueAxis-7",
	                                                "valueField": "column-7",
	                                                "showHandOnHover": true
	                                            }
	                                        ],
	                                        "guides": [],
	                                        "valueAxes": [
	                                            {
	                                                "id": "ValueAxis-1",
	                                                "autoGridCount": false,
	                                                "axisThickness": 0,
	                                                "color": "#c0c0c0",
	                                                "dashLength": 2,
	                                                "gridAlpha": 1,
	                                                "gridColor": "#D8D8D8",
	                                                "tickLength": 0,
	                                                "title": ""
	                                            }
	                                        ],
	                                        "allLabels": [],
	                                        "balloon": {},
	                                        "legend": {
	                                            "enabled": true,
	                                            "align": "center",
	                                            "autoMargins": false,
	                                            "color": "#444444",
	                                            "equalWidths": false,
	                                            "marginLeft": 0,
	                                            "marginRight": 0,
	                                            "markerLabelGap": 6,
	                                            "markerSize": 11,
	                                            "markerType": "circle",
	                                            "periodValueText": "",
	                                            "spacing": 20,
	                                            "valueText": "",
	                                            "valueWidth": 0,
	                                            "verticalGap": 0
	                                        },
	                                        "titles": [],
	                                        "dataProvider": chartData
	                                    }
	                                    var chart = AmCharts.makeChart( id , chartOption );
										chart.addListener( "clickGraphItem", listener);										
										//charts.push( { id: id, chart: chart } );		
											charts[5] =  { id: id, chart: chart } ;		
											
/*										if(charts[5] != undefined){
											alert("데이터 o ");
											charts[5] =  { id: id, chart: chart } ;		
										} else if(charts[5] == undefined){
											alert("데이터 x ");
											delete charts[5]; 																						
										}					
*/																														
	                                })();
								} else{
									$("#"+id).addClass("ui_nodata");									
								}						
							}		
						});	
					}					
				}			
			});				
		}	
		
		function updateAjax_con_92(pageNum){
			if(null == pageNum  || undefined == pageNum ){
				pageNum = 1 ;
			}
			var id = "relation_information";
			var loading_id = "navi_09";			
			$("#"+id+" > div.box_content").removeClass("ui_nodata");
			var inputParams = $.extend({}, _searchOpt);	
			inputParams.mode="relation_information";
			inputParams.company_type = "1";	 // 1로 고정		
			inputParams.issue_code = $("#s09_issue option:selected").val(); 
			inputParams.pageNum=pageNum;
			var $loadingObj = $("#"+loading_id);
			
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
							,url: "./data/getIssueDataList.jsp"
							,timeout: 30000
							,data : inputParams
							,dataType : 'html'
							,async: true
							,beforeSend: function(){
								if($loadingObj != null){
									loading($loadingObj, true);
								}
							}
							,success : function($result){
								if($loadingObj != null){
									loading($loadingObj, false);
								}
								
								$("#"+id).empty().html($result);		
							}			
						});	

					}
					
				}			
			});
			
			
			
			
		}	
/*		function updateAjax_con_1000(){
			var id = "inc_pdf";
			var inputParams = $.extend({}, _searchOpt);	
			
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
							,url: "./data/incPdftop.jsp"
							,timeout: 30000
							,data : inputParams
							,dataType : 'html'
							,async: true
							,success : function($result){
								$("#"+id).empty().html($result);
							$("#"+id).empty().html($result);	
																				
							}				
						});	

					}
					
				}			
			});	
			
		} */
							
		function loading($e,on){
			//로딩 추가할 위치 찾기.
			var $target =  $($e);  
			
			//로딩  on off
			if(!on){
				$target.removeClass("loading");
				
			}else{
				
				if($($e).hasClass("ui_loader_container")){
					$target = $($e); 
				}else{
					$target = $($e).parents( ".ui_loader_container" ).first();
				}
				
				var html = "<div class=\"ui_loader\"><span class=\"loader\">Load</span></div>";
				$target.find(".ui_loader").first().remove();
				$target.append(html);
				
				//로딩관련 html 처리.
				$target.addClass("loading");
			}
		}				
		/*******		Update			********************************************************************************/

		
		/*******		외부 연결 함수			********************************************************************************/
		this.updateAjax_con_70 = function( $pageNum ){
			updateAjax_con_70( $pageNum );
		};
		this.updateAjax_con_61 = function( $pageNum ){
			updateAjax_con_61( $pageNum );
		};
		this.updateAjax_con_62 = function( $pageNum ){
			updateAjax_con_62( $pageNum );
		};		
		this.updateAjax_con_63 = function( $pageNum ){
			updateAjax_con_63( $pageNum );
		};			
		this.updateAjax_con_80 = function( $pageNum ){
			updateAjax_con_80( $pageNum );
		};
		this.updateAjax_con_92 = function( $pageNum ){
			updateAjax_con_92( $pageNum );
		};
		this.updateAjax_con_800 = function( $md_seq, md_title, _md_site ){
			updateAjax_con_800( $md_seq, md_title, _md_site );
		};		
		this.updateAjax_con_10 = function( issue_text, proceed_code, action_code, select_id_seq, mode ){
			updateAjax_con_10( issue_text, proceed_code, action_code, select_id_seq, mode );
		};		

		this.updateAjax_con_31 = function(){
			updateAjax_con_31();
		};

		this.updateAjax_con_32 = function(){
			updateAjax_con_32();
		};
			
		this.updateAjax_con_50 = function(){
			updateAjax_con_50();
		};

		this.updateAjax_con_51 = function(){
			updateAjax_con_51();
		};
			
		this.hndl_search = function(){
			hndl_search();
		};

		this.getVocList = function(){
			getVocList();
		};
		
		this.chk_session = function(){
			chk_session();
		};

		/*******		외부 연결 함수			********************************************************************************/

	};
});