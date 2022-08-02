var statistics;
$( document ).ready( function(){
	
	statistics = new function(){
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
			
			set_search();
			hndl_search();
			
			
		})();

		// 검색
		var _searchOpt;
		
		function set_search(){
			$("#btn_search" ).click( hndl_search );
		}
		function hndl_search(){
			settingTypeCode();
			
			//파라미터 셋팅
			_searchOpt = $( "#searchs_frm" ).serialize();
			_searchOpt = strToJson(_searchOpt);
			_searchOpt[ "sDate" ] = $( "#searchs_frm .searchs_dp_start" ).attr( "data-date" );
			_searchOpt[ "eDate" ] = $( "#searchs_frm .searchs_dp_end" ).attr( "data-date" );
			_searchOpt[ "PsDate" ] = $( "#searchs_frm #searchs_date_range_02_cal_s" ).attr( "data-date" );
			_searchOpt[ "PeDate" ] = $( "#searchs_frm #searchs_date_range_02_cal_e" ).attr( "data-date" );
			
			
			
			/*console.log( "**********	검색 옵션		***********" );
			console.log( _searchOpt );
			console.log( "*************************************" );*/

			
			setPage();			
			updatePage();
			
		}
		
		
		/*******		Handnler			********************************************************************************/
		function strToJson( $str ){
			var result = {};
			var tmpData = $str.split( "&" );
			$.each( tmpData, function(){
				if( this.length > 0 ) result[ decodeURIComponent(this.split( "=" )[ 0 ]) ] = decodeURIComponent(this.split( "=" )[ 1 ]);
			});
			return result;
		}
		function showChart( $tg ){
			if( !$( $tg ).attr( "data-first-ani" ) ){
				$( $tg ).attr( "data-first-ani", true );
				$( $tg ).css( { opacity : 0 } ).delay( 100 ).animate( { opacity : 1 }, 300 );
			}
		}
		/*******		Handnler			********************************************************************************/


		/*******		Set			********************************************************************************/
		function setPage(){
			setAjax_menuBar_01();
			setAjax_menuBar_02();
			setAjax_menuBar_03();
			setAjax_con_10();
			setAjax_con_11();
			setAjax_con_12();
			setAjax_con_21();
			setAjax_con_31();
		}
	
		function setAjax_menuBar_01(){
			//제품군 11,12,13
			var product_code = ['11','12','13'];
			var product_name = ['제품군','제품범주1','제품범주2'];
			var settingCode = $("#settingCode").val();
			var type_code = '';
			
			var innerHtml = '';
			
			var target = "depart_product";
			
		/*	if(null!=settingCode && '' != settingCode){
				settingCode = settingCode.split('@');
				
				$(product_code).each(function(idx){
					
					$(settingCode).each(function(){					
						type_code = this.split(',');
						
						if(product_code[idx] == type_code[0] ){
							innerHtml += '<option value="'+product_code[idx]+'">'+product_name[idx]+'</option>';
							return false;
						}
						
					});
				});
				
				
			}else{*/
				$(product_code).each(function(idx){
					innerHtml += '<option value="'+product_code[idx]+'">'+product_name[idx]+'</option>';
				});
				
			/*}*/
			
			
			$("#"+target).empty().append( innerHtml );
			
			$("#depart_product" ).on("change",function(){
				updateAjax_con_10();
			});
			
			$("#depart_product_orderBy" ).on("change",function(){
				updateAjax_con_10();
			});
			
		}
		
		

		function setAjax_menuBar_02(){
			//게시물 유형  6,7,8
			var product_code = ['6','7','8'];
			var product_name = ['게시물 유형','대분류','중분류'];
			var settingCode = $("#settingCode").val();
			var type_code = '';
			
			var innerHtml = '';
			
			var target = "depart_case";
			
			/*if(null!=settingCode && '' != settingCode){
				settingCode = settingCode.split('@');
				
				$(product_code).each(function(idx){
					
					$(settingCode).each(function(){					
						type_code = this.split(',');
						
						if(product_code[idx] == type_code[0] ){
							innerHtml += '<option value="'+product_code[idx]+'">'+product_name[idx]+'</option>';
							return false;
						}
						
					});
				});
				
				
			}else{*/
				$(product_code).each(function(idx){
					innerHtml += '<option value="'+product_code[idx]+'">'+product_name[idx]+'</option>';
				});
				
			/*}*/
			
			
			$("#"+target).empty().append( innerHtml );
			
			$("#depart_case").on("change",function(){
				updateAjax_con_11($("#section1_type").val(), $("#section1_code").val(), $("#section1_name").val());
			});
			
			$("#depart_case_orderBy").on("change",function(){
				updateAjax_con_11($("#section1_type").val(), $("#section1_code").val(), $("#section1_name").val());
			});
			
		}
		
		function setAjax_menuBar_03(){			
			$("#reltaion_keyword_orderBy").on("change",function(){				
				updateAjax_con_12($("#section1_type").val(), $("#section1_code").val(), $("#section1_name").val());
			});
		}
		
		function settingTypeCode(){
			var checkboxListArr = $("#mainCode").val().split(",");
			var typeCodeSelect = '';
			
			var pcode = '';
			var target = '';		
			$(checkboxListArr).each(function(idx){		
				pcode = checkboxListArr[idx];
				$(".subType_"+pcode).each(function(depth){
		 		    	target = $("[name=typeCodeSelect_"+pcode+"_"+(depth+1)+"] option:selected").val();
						
						if( target != ''){
							if(typeCodeSelect == '') {
								typeCodeSelect = target;
							} else {
								typeCodeSelect += '@' + target;
							}
						}
						
				});
			});
			
			
			$("#settingCode").val(typeCodeSelect);
		}
		
		
	function setAjax_con_10(){
			
			var id = "section_10";
			$("#"+id).find(".download").off("click");
			$("#"+id).find(".download").on("click",function(){
				var inputParams = $.extend({}, _searchOpt);		
				inputParams.mode = id; 
				inputParams.type = $("#depart_product option:selected").val();
				inputParams.typeName = $("#depart_product option:selected").text();
				
				inputParams.orderBy = $("#depart_product_orderBy option:selected").val();
				inputParams.orderByName = $("#depart_product_orderBy option:selected").text();
				inputParams.title = "제품 순위"; 
				
				var $target_btn  = $("#"+id).find(".ui_btn");
				
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
						/*console.log("responseUrl");
						console.log(responseUrl );*/
						$target_btn.removeClass("loading");
						var result = responseUrl ;
						if(result.indexOf('no_data')>-1){
							alert("해당 데이터가 존재하지 않습니다.");
						}else{
							$("#processFrm" ).attr( "src", decodeURIComponent( responseUrl ) );
						}
					}
					, error : function(){
						$target_btn.removeClass("loading");
						alert("엑셀 다운로드중 오류가 발생하였습니다.\n재시도 부탁드립니다. 감사합니다.");
					}
				});
			});
			
		}
		
	
	function setAjax_con_11(){
		
		var id = "section_11";
		$("#"+id).find(".download").off("click");
		$("#"+id).find(".download").on("click",function(){
			var inputParams = $.extend({}, _searchOpt);		
			inputParams.mode = id; 
			
			inputParams.type = $("#depart_case option:selected").val();
			inputParams.typeName = $("#depart_case option:selected").text();
			inputParams.orderBy = $("#depart_case_orderBy option:selected").val();
			inputParams.orderByName = $("#depart_case_orderBy option:selected").text();
			inputParams.type2 = $("#section1_type").val();;
			inputParams.code = $("#section1_code").val();
			
			inputParams.title = "세부 내용("+$("#section1_name").val()+") - 분류"; 
			
			var $target_btn  = $("#"+id).find(".ui_btn");
			
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
					/*console.log("responseUrl");
					console.log(responseUrl );*/
					$target_btn.removeClass("loading");
					var result = responseUrl ;
					if(result.indexOf('no_data')>-1){
						alert("해당 데이터가 존재하지 않습니다.");
					}else{
						$("#processFrm" ).attr( "src", decodeURIComponent( responseUrl ) );
					}
				}
				, error : function(){
					$target_btn.removeClass("loading");
					alert("엑셀 다운로드중 오류가 발생하였습니다.\n재시도 부탁드립니다. 감사합니다.");
				}
			});
		});
		
	}
	
	function setAjax_con_12(){
		
		var id = "section_12";
		$("#"+id).find(".download").off("click");
		$("#"+id).find(".download").on("click",function(){
			var inputParams = $.extend({}, _searchOpt);		
			inputParams.mode = id; 
			
			inputParams.type = $("#section1_type").val();
			inputParams.code = $("#section1_code").val();
			inputParams.orderBy = $("#reltaion_keyword_orderBy option:selected").val();
			inputParams.orderByName = $("#reltaion_keyword_orderBy option:selected").text();
			
			inputParams.title = "세부 내용("+$("#section1_name").val()+") - 연관키워드"; 
			
			var $target_btn  = $("#"+id).find(".ui_btn");
			
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
					/*console.log("responseUrl");
					console.log(responseUrl );*/
					$target_btn.removeClass("loading");
					var result = responseUrl ;
					if(result.indexOf('no_data')>-1){
						alert("해당 데이터가 존재하지 않습니다.");
					}else{
						$("#processFrm" ).attr( "src", decodeURIComponent( responseUrl ) );
					}
				}
				, error : function(){
					$target_btn.removeClass("loading");
					alert("엑셀 다운로드중 오류가 발생하였습니다.\n재시도 부탁드립니다. 감사합니다.");
				}
			});
		});
		
	}
	
	function setAjax_con_21(){
		
		var id = "section_21";
		$("#"+id).find(".download").off("click");
		$("#"+id).find(".download").on("click",function(){
			var inputParams = $.extend({}, _searchOpt);		
			inputParams.mode = id; 
			
			inputParams.type = $("#section1_type").val();
			inputParams.code =  $("#section1_code").val();
			inputParams.type2 = $("#section2_type").val();
			inputParams.code2 =  $("#section2_code").val();
			
			
			inputParams.title = "기간별 분석("+$("#section1_name").val()+"-"+$("#section2_name").val()+")"; 
			
			var $target_btn  = $("#"+id).find(".ui_btn");
			
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
					/*console.log("responseUrl");
					console.log(responseUrl );*/
					$target_btn.removeClass("loading");
					var result = responseUrl ;
					if(result.indexOf('no_data')>-1){
						alert("해당 데이터가 존재하지 않습니다.");
					}else{
						$("#processFrm" ).attr( "src", decodeURIComponent( responseUrl ) );
					}
				}
				, error : function(){
					$target_btn.removeClass("loading");
					alert("엑셀 다운로드중 오류가 발생하였습니다.\n재시도 부탁드립니다. 감사합니다.");
				}
			});
		});
		
	}
	
	
	function setAjax_con_31(){
		
		var id = "section_31";
		$("#"+id).find(".download").off("click");
		$("#"+id).find(".download").on("click",function(){
			var inputParams = $.extend({}, _searchOpt);		
			inputParams.mode = id; 
			
			inputParams.type = $("#section1_type").val();
			inputParams.code =  $("#section1_code").val();
			inputParams.type2 =  $("#section2_type").val();
			inputParams.code2 =   $("#section2_code").val();
			
			
			inputParams.title = "데이터 리스트("+$("#section1_name").val()+"-"+$("#section2_name").val()+")"; 
			
			var $target_btn  = $("#"+id).find(".ui_btn");
			
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
					/*console.log("responseUrl");
					console.log(responseUrl );*/
					$target_btn.removeClass("loading");
					var result = responseUrl ;
					if(result.indexOf('no_data')>-1){
						alert("해당 데이터가 존재하지 않습니다.");
					}else{
						$("#processFrm" ).attr( "src", decodeURIComponent( responseUrl ) );
					}
				}
				, error : function(){
					$target_btn.removeClass("loading");
					alert("엑셀 다운로드중 오류가 발생하였습니다.\n재시도 부탁드립니다. 감사합니다.");
				}
			});
		});
		
	}
		
		/*******		Set			********************************************************************************/

		
		

		/*******		Update			********************************************************************************/
		function updatePage(){
			updateAjax_con_10();
			//updateAjax_con_11();
			//updateAjax_con_12();
			//updateAjax_con_21();
			//updateAjax_con_31();
			
		}
		

	
		
		
		function updateAjax_con_10(){
			var id = "ajax_con_10";
			$("#"+id+" > div.box_content").removeClass("ui_nodata");
			var inputParams = $.extend({}, _searchOpt);
			inputParams.mode = "product";
			inputParams.type = $("#depart_product option:selected").val();
			inputParams.orderBy = $("#depart_product_orderBy option:selected").val();
			
			var $loadingObj = $("#"+id);
			
			$.ajax({
				type : "POST"
				,async : true
				,url: "./data/getDataList.jsp"
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
					
					$("#"+id).find("#product_list").empty().html($result);	
					
					//제품순위 클릭
					$("#section1_type").val($("#param_type").val());
					$("#section1_code").val($("#param_code").val());
					$("#section1_name").val($("#param_name").val());
					
					updateAjax_con_11($("#section1_type").val(), $("#section1_code").val(), $("#section1_name").val());
					//연관도 분석
					updateAjax_con_12($("#section1_type").val(), $("#section1_code").val(), $("#section1_name").val());
				}
				
									
			});
			
			
			
			
		}
		
		function updateAjax_con_11($type, $code, $name){
			var id = "ajax_con_11";
			$(".detail_hearder").html($name);
			
			$("#"+id+" > div.box_content").removeClass("ui_nodata");
			var inputParams = $.extend({}, _searchOpt);	
			inputParams.mode = "category";
			inputParams.type = $("#depart_case option:selected").val();
			inputParams.orderBy = $("#depart_case_orderBy option:selected").val();
			inputParams.type2 = $type;
			inputParams.code = $code;
			
			var $loadingObj = $("#"+id);
			
			$.ajax({
				type : "POST"
				,async : true
				,url: "./data/getDataList2.jsp"
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
					
					$("#"+id).find("#case_list").empty().html($result);
					//제품순위 클릭
					$("#section1_type").val($type);
					$("#section1_code").val($code);
					$("#section1_name").val($name);
					
					$("#section2_type").val($("#param_type2").val());
					$("#section2_code").val($("#param_code2").val());
					$("#section2_name").val($("#param_name2").val());
					
					
					updateAjax_con_21($("#section2_type").val(), $("#section2_code").val(), $("#section2_name").val());
					
				}
				
									
			});
			
			
		}
		
		
		function updateAjax_con_12($ic_type, $ic_code, $ic_name){
			var id = "ajax_con_12";
			
			$("#"+id+" > div.box_content").removeClass("ui_nodata");
			var inputParams = $.extend({}, _searchOpt);	
			inputParams.type = $ic_type;
			inputParams.code = $ic_code;
			inputParams.orderBy = $("#reltaion_keyword_orderBy option:selected").val();
			
			
			var $loadingObj = $("#"+id);
			
			$.ajax({
				type : "POST"
				,async : true
				,url: "./data/getRelationKeywordList.jsp"
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
					$("#"+id).find("#relation_list").empty().html($result);
				}
									
			});
			
		}
		
		
		function updateAjax_con_21($type, $code, $name){
			var id = "ajax_con_21";
			$(".detail_hearder2").html($name);
			
			$("#"+id ).removeClass("ui_nodata");
			var inputParams = $.extend({}, _searchOpt);	
			inputParams.type = $("#section1_type").val();
			inputParams.code =  $("#section1_code").val();
			inputParams.type2 = $type;
			inputParams.code2 =  $code;
			
			
			$("#section2_type").val($type);
			$("#section2_code").val($code);
			$("#section2_name").val($name);
			
			var $loadingObj = $("#"+id);
			
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
					//라벨셋팅
					var graphs =[];
					var $title;
					var tmp_info;                    	
					$title = $name;
					tmp_info = {
							"balloonText": "<strong>[[title]]</strong> : [[value]]건",
		                    "bullet": "round",
		                    "bulletSize": 10,
		                    "id": "AmGraph-1",
		                    "title": $title,
		                    "type": "smoothedLine",
		                     "valueField": "column-1"
					 }
					
                    graphs.push(tmp_info);
						
						
					
					var chartData =[];
					var tmpCnt = 0;
					$($result.data).each(function(i,e){
						e["column-1"] = e.CNT;
						chartData.push(e);
						tmpCnt+=parseInt(e.CNT);						
					});
					
					
					var chart = devel.chart.getChartById(id);
					chart.dataProvider = chartData;
					chart.graphs = graphs;
					chart.validateData();
					chart.invalidateSize();
					if(tmpCnt>0){
						$("#"+id ).removeClass( "ui_nodata" );
					}else{
						$("#"+id).addClass( "ui_nodata" );	
					}
					showChart( chart.div );		
					
					updateAjax_con_31(1);
				}
									
			});
			
		}
		
		function updateAjax_con_31($pageNum){
			var id = "ajax_con_31";
			
			if(null == $pageNum  || undefined == $pageNum ){
				$pageNum = 1 ;
			}
			
			var inputParams = $.extend({}, _searchOpt);	
			inputParams.type = $("#section1_type").val();
			inputParams.code =  $("#section1_code").val();
			inputParams.type2 =  $("#section2_type").val();
			inputParams.code2 =   $("#section2_code").val();
			inputParams.pageNum = $pageNum;
			
			var $loadingObj = $("#"+id);
			
			$.ajax({
				type : "POST"
				,async : true
				,url: "./data/getRowDataList.jsp"
				,timeout: 30000
				,data : inputParams
				,dataType : 'html'
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
		
		this.updateAjax_con_11 = function( $ic_type, $ic_code, $ic_name ){
			updateAjax_con_11( $ic_type, $ic_code, $ic_name);
		};

		this.updateAjax_con_12 = function($ic_type, $ic_code, $ic_name){
			updateAjax_con_12($ic_type, $ic_code, $ic_name);
		};
		
		this.updateAjax_con_21 = function( $ic_type, $ic_code, $ic_name ){
			updateAjax_con_21( $ic_type, $ic_code, $ic_name);
		};
		
		this.updateAjax_con_31 = function( $pageNum ){
			updateAjax_con_31( $pageNum );
		};
		
		
		/*******		외부 연결 함수			********************************************************************************/
		
		
	};
});