var press_release;
var temp;
gnbIDX = "02";



$( document ).ready( function(){
	// 검색 조건
	var _searchOpt;		
	var pressname;
			
	press_release = new function(){
		// Init
		(function(){
			// Default Date Setting
			//var today = new Date();
			//var sDate = new Date();
			setPage();			
			hndl_search();
			
		})();
			   
			
		function hndl_search(){
			_searchOpt = {};
			_searchOpt[ "i_sdate" ] = JSON.parse($( "#dp11" ).attr( "data-date" ))["sDate"];	//검색기간 시작일 !
			_searchOpt[ "i_edate" ] = JSON.parse($( "#dp11" ).attr( "data-date" ))["eDate"];	//검색기간 마지막일 !
			/******* 검색 결과창 반영 ( searchs_result 설정하기) ***********************/
			
			updatePage(); 
			
		}
		
		/**************************** 검색버튼 클릭 액션 ****************************/
		$(".btn_search ").on("click", function() {
			hndl_search();
		});
		/**************************** 검색버튼 클릭 액션 ****************************/
			
				
		/*******		Handnler			********************************************************************************/
		function strToJson( $str ){
			var result = {};
			var tmpData = $str.split( "&" );
			$.each( tmpData, function(){
				if( this.length > 0 ) result[ this.split( "=" )[ 0 ] ] = this.split( "=" )[ 1 ];
			});
			return result;
		}		
		/*******		Handnler			********************************************************************************/			


		/*******		Set			********************************************************************************/
		function setPage(){		
			/*section별 엑셀 다운로드*/
			setAjax_con_10();	//보도자료 목록 및 확산 추적
			setPageDetail();
		}
		
		function setPageDetail() {
			setAjax_con_20();	//보도자료 확산 채널 추적
			setAjax_con_30();	//보도자료 확산 추이		
			setAjax_con_40();	//보도자료 커버링 데이터 목록
		}
		
		function setAjax_con_10() {
			var id = "qid_01";
			
			$(".download_top").on("click",function(){
				var inputParams = $.extend({}, _searchOpt);		
				inputParams.subject = "보도자료 목록 및 확산 추적"; 
				inputParams.mode = "dataList";
				
				var $target_btn  = $("#"+id).find(".download");
				$.ajax({ 
					url : "./excel/getExcelData.jsp"
					,type : "POST"
					,timeout : 1800000
					,dataType : "text"
					,data : inputParams
					,beforeSend: function(){
						$target_btn.addClass("is-loading");
					}	
					,success : function( responseUrl ){
						//console.log("responseUrl");
						//console.log(responseUrl );
						$target_btn.removeClass("is-loading");
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
		
		function setAjax_con_20() {
			var id = "qid_01_01";
			
			$("#"+id).find(".download").on("click",function(){
				var inputParams = $.extend({}, _searchOpt);		
				inputParams.subject = "보도자료 확산 채널 추적"; 
				inputParams.mode = "channel"; 
				inputParams.ic_code = $("#pressReleaseIc_code").val();
				inputParams.i_sdate = $("#pressReleaseSDate").val();
				inputParams.i_edate = $("#pressReleaseEDate").val();
				
				
				var $target_btn  = $("#"+id).find(".download");
				$.ajax({ 
					url : "./excel/getExcelData.jsp"
					,type : "POST"
					,timeout : 1800000
					,dataType : "text"
					,data : inputParams
					,beforeSend: function(){
						$target_btn.addClass("is-loading");
					}	
					,success : function( responseUrl ){
						//console.log("responseUrl");
						//console.log(responseUrl );
						$target_btn.removeClass("is-loading");
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
		
		function setAjax_con_30() {
			var id = "qid_01_02";
			
			$("#"+id).find(".download").on("click",function(){
				var inputParams = $.extend({}, _searchOpt);		
				inputParams.subject = "보도자료 확산 추이"; 
				inputParams.mode = "amount";
				inputParams.ic_code = $("#pressReleaseIc_code").val();
				inputParams.i_sdate = $("#pressReleaseSDate").val();
				inputParams.i_edate = $("#pressReleaseEDate").val();
				
				var $target_btn  = $("#"+id).find(".download");
				$.ajax({ 
					url : "./excel/getExcelData.jsp"
					,type : "POST"
					,timeout : 1800000
					,dataType : "text"
					,data : inputParams
					,beforeSend: function(){
						$target_btn.addClass("is-loading");
					}	
					,success : function( responseUrl ){
						//console.log("responseUrl");
						//console.log(responseUrl );
						$target_btn.removeClass("is-loading");
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
		
		function setAjax_con_40() {
			var id = "qid_01_03";
			
			$("#"+id).find(".download").on("click",function(){
				var inputParams = $.extend({}, _searchOpt);		
				inputParams.subject = "보도자료 커버링 데이터 목록"; 
				inputParams.mode = "coveringDataList"; 
				inputParams.ic_code = $("#pressReleaseIc_code").val();
				inputParams.i_sdate = $("#pressReleaseSDate").val();
				inputParams.i_edate = $("#pressReleaseEDate").val();
				
				var sg_seq = '';
				$("#qid_01_03_01_chart_sel_01 option:selected").each(function (idx, item) {
					if(sg_seq == '') {
						sg_seq = $(this).val();
					} else {
						sg_seq += ',' + $(this).val();
					}
	      		});
				inputParams.sg_seq = sg_seq;
				
				var senti = '';
				$("#qid_01_03_01_chart_sel_02 option:selected").each(function (idx, item) {
					if(senti == '') {
						senti = $(this).val();
					} else {
						senti += ',' + $(this).val();
					}
	      		});
				inputParams.senti = senti;
				
				var $target_btn  = $("#"+id).find(".download");
				$.ajax({ 
					url : "./excel/getExcelData.jsp"
					,type : "POST"
					,timeout : 1800000
					,dataType : "text"
					,data : inputParams
					,beforeSend: function(){
						$target_btn.addClass("is-loading");
					}	
					,success : function( responseUrl ){
						//console.log("responseUrl");
						//console.log(responseUrl );
						$target_btn.removeClass("is-loading");
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
		
		
		
		/*******		Update			********************************************************************************/
		
		function updatePage() {
			updateAjax_con_10();	//보도자료 목록 및 확산 추적
			
			//updatePageDetail();
		}
		
		
		//맨 위에 데이터리스트 클릭시 밑에 그래프들 다시 그리기
		function updatePageDetail() {
			updateAjax_con_20();	//보도자료 확산 채널 추적
			updateAjax_con_30();	//보도자료 확산 추이	
			updateAjax_con_40();	//보도자료 커버링 데이터 목록
		}
		
		function updateAjax_con_10(pageNum) {
			var page = pageNum;
			if(null == pageNum  || undefined == pageNum ){
				page = 1 ;
			}
			
			var id = "qid_01_div";
			$("#"+id).parent().removeClass("ui_no_data");
			var inputParams = $.extend({}, _searchOpt);	
			inputParams.mode="dataList";
			inputParams.pageNum=page;
			
			var $loadingObj = $("#"+id).parent().parent();
			
			$.ajax({
				type : "POST"
				,async : true
				,url: "./data/getPressDataList.jsp"
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
					getIc_code();
					//updatePageDetail();
				}
					
			});	
		}
		
		function getIc_code() {
			var temp = $("tr[class=active]").find("#ic_code").val();
			var inputParams = {};
			inputParams.ic_code = temp;
			console.log(JSON.stringify(inputParams));
			
			$.ajax({
				type : "POST"
				,async : true
				,url: "./data/getIc_code.jsp"
				,timeout: 30000
				,data : inputParams
				,dataType : 'json'
				,async: true
				,beforeSend: function(){
					
				}
				,success : function($result){
					console.log($result);

					$("#pressReleaseIc_code").val($result.ic_code);	
					$("#pressReleaseSDate").val($result.sdate);	
					$("#pressReleaseEDate").val($result.edate);	
					
					//setPageDetail(); 
					updatePageDetail();
					
				}
					
			});	
		}
		
		function updateAjax_con_20(){
			
			var id = "qid_01_01_div";
			$("#"+id+" > div.ui_chart_wrap").removeClass("ui_no_data");
			var inputParams = $.extend({}, _searchOpt);
			inputParams.mode = 'channel';
			inputParams.ic_code = $("#pressReleaseIc_code").val();
			inputParams.i_sdate = $("#pressReleaseSDate").val();
			inputParams.i_edate = $("#pressReleaseEDate").val();	
			
			
			var $loadingObj = $("#"+id).parent();
			
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
					
					$("#qid_01_01").find("em").empty().text($("tr[class=active]").find(".lnk").find(".txt").text());
					
					var chartData =[];
					var tmpCnt = 0;
					$($result.data).each(function(i,e){
						e["category"] = e.CHANNEL;
						e["sg_seq"] = e.SG_SEQ;
						e["column-1"] = e.POS;
						e["column-2"] = e.NEG;
						e["column-3"] = e.NEU;
						chartData.push(e);
						tmpCnt+=(parseInt(e.POS)+parseInt(e.NEG)+parseInt(e.NEU));
					});
					var chart = devel.chart.getChartById("qid_01_01_chart_01");
					chart.dataProvider = chartData;
					chart.validateData();
					chart.invalidateSize();
					if(tmpCnt>0){
						$( chart.div ).removeClass( "ui_no_data" );
					}else{
						$( chart.div ).addClass( "ui_no_data" );	
					}
					showChart( chart.div );		
					
					chart.addListener( "clickGraphItem", function( $e ){
                        //popupMngr.open( '#popup_rel_info_detail' );
						var inputParams = $.extend({}, _searchOpt);
						var senti= "AmGraph-1" == $e.target.id ? "1" : "AmGraph-2" == $e.target.id ? "2" : "AmGraph-3" == $e.target.id ? "3" : "";
						var sg_seq = $e.item.dataContext.sg_seq;
						
						getPopup(('i_sdate='+$("#pressReleaseSDate").val()+'&i_edate='+$("#pressReleaseEDate").val()+'&senti='+senti+'&sg_seq='+sg_seq+'&ic_code='+$("#pressReleaseIc_code").val()), 'open');
                    })
					
				}
					
			});	
			
		}
		
		
		function updateAjax_con_30(){
			var id = "qid_01_02_div";
			$("#"+id+" > div.ui_chart_wrap").removeClass("ui_no_data");
			var inputParams = $.extend({}, _searchOpt);
			inputParams.mode = 'amount';
			inputParams.ic_code = $("#pressReleaseIc_code").val();
			inputParams.i_sdate = $("#pressReleaseSDate").val();
			inputParams.i_edate = $("#pressReleaseEDate").val();	
			
			var $loadingObj = $("#"+id).parent();
						
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
					$("#qid_01_02").find("em").empty().text($("tr[class=active]").find(".lnk").find(".txt").text());
					
					var chartData =[];
					
					var tmpCnt = 0;
					$($result.data).each(function(i,e){
						e["category"] = e.MD_DATE;
						e["column-1"] = e.POS;
						e["column-2"] = e.NEG;
						e["column-3"] = e.NEU;
						chartData.push(e);
						tmpCnt+=(parseInt(e.POS)+parseInt(e.NEG)+parseInt(e.NEU));
					});
					var chart = devel.chart.getChartById("qid_01_02_chart_01");
					chart.dataProvider = chartData;
					chart.validateData();
					chart.invalidateSize();
					if(tmpCnt>0){
						$( chart.div ).removeClass( "ui_no_data" );
					}else{
						$( chart.div ).addClass( "ui_no_data" );	
					}
					showChart( chart.div );		
				    
					chart.addListener( "clickGraphItem", function( $e ){
                        //popupMngr.open( '#popup_rel_info_detail' );
						var inputParams = $.extend({}, _searchOpt);
						var senti= "AmGraph-1" == $e.target.id ? "1" : "AmGraph-2" == $e.target.id ? "2" : "AmGraph-3" == $e.target.id ? "3" : "";
						var i_sdate = $e.item.dataContext.category;
						var i_edate = $e.item.dataContext.category;
						
						getPopup(('i_sdate='+i_sdate+'&i_edate='+i_edate+'&senti='+senti+'&ic_code='+$("#pressReleaseIc_code").val()), 'open');
                    })
					
				}
					
			});	
			
		}
		
		function updateAjax_con_40(pageNum) {
			if(null == pageNum  || undefined == pageNum ){
				pageNum = 1 ;
			}
			
			var id = "qid_01_03_div";
			$("#"+id).removeClass("ui_no_data");
			var inputParams = $.extend({}, _searchOpt);	
			inputParams.mode="coveringDataList";
			inputParams.pageNum=pageNum;
			inputParams.ic_code = $("#pressReleaseIc_code").val();
			inputParams.i_sdate = $("#pressReleaseSDate").val();
			inputParams.i_edate = $("#pressReleaseEDate").val();	
			
			var sg_seq = '';
			$("#qid_01_03_01_chart_sel_01 option:selected").each(function (idx, item) {
				if(sg_seq == '') {
					sg_seq = $(this).val();
				} else {
					sg_seq += ',' + $(this).val();
				}
      		});
			inputParams.sg_seq = sg_seq;
			
			var senti = '';
			$("#qid_01_03_01_chart_sel_02 option:selected").each(function (idx, item) {
				if(senti == '') {
					senti = $(this).val();
				} else {
					senti += ',' + $(this).val();
				}
      		});
			inputParams.senti = senti;
			
			var $loadingObj = $("#"+id).parent().parent();
			
			$.ajax({
				type : "POST"
				,async : true
				,url: "./data/getCoveringDataList.jsp"
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
					$("#qid_01_03").find("em").empty().text($("tr[class=active]").find(".lnk").find(".txt").text());
					
					$("#"+id).empty().html($result);		
				}
					
			});		
		}
		
		
		
		//로딩 추가,삭제시			
		function loading($e,on){
			//로딩 추가할 위치 찾기.
			var $target =  $($e);  
			
			//로딩  on off
			if(!on){
				$target.removeClass("is-loading");
				
			}else{
				
				if($($e).hasClass("ui_loader_container")){
					$target = $($e); 
				}else{
					$target = $($e).parents( ".ui_loader_container" ).first();
				}
				
				//로딩관련 html 처리.
				$target.addClass("is-loading");
			}
		}	
		
		function showChart( $tg ){
			if( !$( $tg ).attr( "data-first-ani" ) ){
				$( $tg ).attr( "data-first-ani", true );
				$( $tg ).css( { opacity : 0 } ).delay( 100 ).animate( { opacity : 1 }, 300 );
			}
		}
					
		/*******		Update			********************************************************************************/
		
		/*******		외부 연결 함수			********************************************************************************/
		
		this.updateAjax_con_10 = function($page) {
			updateAjax_con_10($page);
		};
		
		this.updateAjax_con_40 = function($page) {
			updateAjax_con_40($page);
		};
		
		this.updatePageDetail = function(){
			updatePageDetail();
		};
		
		this.getIc_code = function(){
			getIc_code();
		};
		
		/*******		외부 연결 함수			********************************************************************************/
	};
	

	//보도자료 커버링 데이터 목록 - 체널 선택 이벤트
	$("#qid_01_03_01_chart_sel_01").change(function() {
		press_release.updateAjax_con_40();
	}); 
	
	//보도자료 커버링 데이터 목록 - 성향 선택 이벤트
	$("#qid_01_03_01_chart_sel_02").change(function() {
		press_release.updateAjax_con_40();
	}); 
	
});




	

