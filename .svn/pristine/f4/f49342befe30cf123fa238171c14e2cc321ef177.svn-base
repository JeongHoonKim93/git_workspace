/*
	이슈관리 - 관련정보
	Amchart
		
*/

var $issueTrendLineChart = '';

function drawChart(targetId, parseDatesTF, $data){
	
	var chartOption = {
		"type": "serial",
		"categoryField": "date",
		"pathToImages": "http://www.amcharts.com/lib/images",
		"dataProvider": $data,
		"marginRight": 20,
		"marginLeft": 1,
		"marginTop": 30,
		"backgroundColor": "#000000",
		"startEffect": "elastic",
		"legend" : {
			"markerType" : "circle"
		},
		"categoryAxis": {
			"parseDates" : parseDatesTF,
			"dashLength" : 5,
			"equalSpacing" : true,
			"startOnAxis" : true,
			"axisAlpha" : 0,
			"fillAlpha" : 1,
			"fillColor" : "#FAFAFA",
			"gridAlpha" : 0.07,
			"gridPosition" : "start",
			"autoGridCount" : true
		},
		"valueAxes" : [
		    {
		    	"id": "ValueAxis-1",
		    	"axisAlpha" : 0,
		    	"dashLength" : 5
		    }
		],
		"graphs" : [
		    {
		    	"id" : "AmGraph-1",
		    	"bullet" : "round",
		    	"bulletSize" : 8,
		    	"valueField" : "POS",
		    	"lineThickness" : 3,
		    	"lineColor" : "#2d70bf",
		    	"title" : "긍정",
		    	"balloonText" : "[[category]]: [[value]]건"
		    },
		    {
		    	"id" : "AmGraph-2",
		    	"bullet" : "round",
		    	"bulletSize" : 8,
		    	"valueField" : "NEG",
		    	"lineThickness" : 3,
		    	"lineColor" : "#c02d27",
		    	"title" : "부정",
		    	"balloonText" : "[[category]]: [[value]]건"
		    },
		    {
		    	"id" : "AmGraph-3",
		    	"bullet" : "round",
		    	"bulletSize" : 8,
		    	"valueField" : "NEU",
		    	"lineThickness" : 3,
		    	"lineColor" : "#92bf35",
		    	"title" : "중립",
		    	"balloonText" : "[[category]]: [[value]]건"
		    }
		]
	};
	
	$issueTrendLineChart = AmCharts.makeChart(targetId, chartOption);
}

function callChart(targetId){
	var param = $("#fSend").serialize();
	if($( "#"+targetId).html() == ''){
		$.ajax({
			type : "POST"
			,url: "./aj_issue_chart_json.jsp"
			,timeout: 30000
			,data : param
			,dataType : 'json'
			,success : function( result ){
				drawChart(targetId, result.parseDatesTF, result.data);
			}
		});
	} else {
		$.ajax({
			type : "POST"
			,url: "./aj_issue_chart_json.jsp"
			,timeout: 30000
			,data : param
			,dataType : 'json'
			,success : function( result ){
				$issueTrendLineChart.dataProvider = result.data;
				$issueTrendLineChart.categoryAxis.parseDates = result.parseDatesTF;
				$issueTrendLineChart.validateData();
			}
		});
	}
}