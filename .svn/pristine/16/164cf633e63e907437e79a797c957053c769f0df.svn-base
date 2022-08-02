
function makeChart(){}

makeChart.barChart = function(period, area, category, data, link, selectDate, basicColor, selectColor, fontsize, legend){
	var chart;
	var tempCategory = new Array;
	var tempData = new Array;
	
	if(selectColor == ''){
		selectColor = basicColor;
	}

	for(var i = 0; i < category.length; i++){
		if(period == 'd'){
			tempCategory[i] = category[i].split('-')[1]+'/'+category[i].split('-')[2];
		}else if(period == 'w'){
			tempCategory[i] = category[i].substring(3, 5)+'/'+category[i].substring(5, 7);
		}else if(period == 'm'){
			tempCategory[i] = category[i].substring(1, 5)+'/'+category[i].substring(5, 7);
		}
	}
	
	if(link){
		for(var i = 0; i < data.length; i++){
			if(selectDate == ''){
				if(i == data.length-1){
					tempData[i] = {y: Number(data[i]), drilldown: {data: category[i], data1: Number(data[i])}, color: selectColor};
				}else{
					tempData[i] = {y: Number(data[i]), drilldown: {data: category[i], data1: Number(data[i])}, color: basicColor};
				}
			}else{
				if(selectDate == category[i]){
					tempData[i] = {y: Number(data[i]), drilldown: {data: category[i], data1: Number(data[i])}, color: selectColor};
				}else{
					tempData[i] = {y: Number(data[i]), drilldown: {data: category[i], data1: Number(data[i])}, color: basicColor};
				}
			}
		}
	}else{
		tempData = data;
	}

    chart = new Highcharts.Chart({
        chart: {
            renderTo: area,
            width:763,
            type: 'column',
            margin: [ 30, 30, 50, 50]
        },
        title: {
            text: ''
        },
        xAxis: {
            categories: tempCategory,
            labels: {
                rotation: 0,
                align: 'center',
                style: {
                    fontSize: fontsize+'px',
                    fontFamily: 'Verdana, sans-serif'
                }
            }
        },
        yAxis: {
            min: 0,
            allowDecimals: false,
            title: {
                text: ''
            }
        },
        legend: {
        	enabled: legend,
            layout: 'vertical',
            backgroundColor: '#FFFFFF',
            align: 'left',
            verticalAlign: 'top',
            x: 605,
            y: 0,
            floating: true,
            shadow: true
        },
        tooltip: {
            formatter: function() {
                return '<b>'+ this.x +'</b>'+
                    ' : '+ Highcharts.numberFormat(this.y,0);
            }
        },
        credits: {
			text: ""
		},
        plotOptions: {
            column: {
                cursor: 'pointer',
                point: {
                    events: {
                        click: function() {
                            var drilldown = this.drilldown;
                            if (drilldown) { // drill down
                                chartLink(drilldown.data, drilldown.data1);
                            }
                        }
                    }
                },
                dataLabels: {
                    enabled: true,
                    color: '#CCCCCC',
                    style: {
                        fontWeight: 'bold'
                    },
                    formatter: function() {
                        return this.y +'%';
                    }
                }
            }
        },
        series: [{
        	/* animation: false, */
            name: 'Population',
            data: tempData,
            dataLabels: {
                enabled: false,
                rotation: -90,
                color: '#FFFFFF',
                align: 'right',
                x: -3,
                y: 10,
                formatter: function() {
                    return this.y;
                },
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif'
                }
            }
        }]
        , exporting: {
            enabled: false 
        }
    });
};

makeChart.categoryBarChart = function(area, category1, data1, category2, data2, link, selectDate){
	var tempCategory = new Array;
	
	for(var i = 0; i < category1.length; i++){
		tempCategory[i] = category1[i].substring(4, 6)+'월';
	}
	
	var tempData1 = new Array;
	if(link){
		for(var i = 0; i < data1.length; i++){
			tempData1[i] = {y: Number(data1[i]), drilldown: {data: category1[i]}, color: '#4AA801'};
		}
	}else{
		tempData1 = data1;
	}
	
	var tempData2 = new Array;
	if(link){
		for(var i = 0; i < data2.length; i++){
			tempData2[i] = {y: Number(data2[i]), drilldown: {data: category2[i]}, color: '#EB8A00'};
		}
	}else{
		tempData2 = data2;
	}
	
    var chart;
    chart = new Highcharts.Chart({
        chart: {
            renderTo: area,
            type: 'column'
        },
        title: {
            text: ''
        },
        xAxis: {
            categories: tempCategory
        },
        yAxis: {
            min: 0,
            allowDecimals: false,
            title: {
                text: ''
            }
        },
        credits: {
			text: ""
		},
       	colors: [
      	     	'#4AA801', 
      	        '#EB8A00'
      	     ], 
        legend: {
            layout: 'vertical',
            backgroundColor: '#FFFFFF',
            align: 'left',
            verticalAlign: 'top',
            x: 705,
            y: 0,
            floating: true,
            shadow: true
        },
        tooltip: {
            formatter: function() {
                return ''+
                    this.x +': '+ this.y;
            }
        },
        plotOptions: {
            column: {
        		cursor: 'pointer',
                pointPadding: 0.2,
                borderWidth: 0,
                point: {
	                events: {
	                    click: function() {
	                        var drilldown = this.drilldown;
	                        if (drilldown) { // drill down
	                            chartLink(drilldown.data);
	                        }
	                    }
	                }
	            }
            }
        },
            series: [{
            name: '전년',
            data: tempData1

        }, {
            name: '금년(단위:건,%)',
            data: tempData2

        }]
        , exporting: {
            enabled: false 
        }
    });
}