<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="../../inc/sessioncheck.jsp" %>    
<%@ page import="	java.util.ArrayList
					,java.awt.Color
					,java.awt.*
					,risk.util.ParseRequest
					,risk.util.ConfigUtil
					,risk.util.StringUtil
					,risk.util.DateUtil
					, risk.statistics.StatisticsIssueMgr
					, risk.statistics.StatisticsSuperBean
					, risk.issue.IssueCodeMgr
                	, risk.issue.IssueCodeBean
                	, java.net.URLDecoder
					" %>

<%
	DateUtil   du = new DateUtil();
	ParseRequest  pr = new ParseRequest(request);	
	StringUtil su = new StringUtil();
	ConfigUtil cu = new ConfigUtil();
	
	String sDateFrom ="";
	String sDateTo ="";
	String stime = pr.getString("stime","0");
	String etime = pr.getString("etime","24");
	
	
	String setStime= stime.equals("0") ? "00:00:00" : stime + ":00:00";
	String setEtime= etime.equals("24") ? "23:59:59" : etime + ":00:00";
	
	//날짜 셋팅 후 최소,최대  문서 값을 가져온다.
	sDateFrom = pr.getString("sDateFrom","");
	sDateTo = pr.getString("sDateTo","");
	if(sDateFrom.equals("")){
		sDateTo = du.getCurrentDate("yyyy-MM-dd");
		sDateFrom = du.addDay(sDateTo,-6);
	}
	
	//리스트 가져오기~
	StatisticsIssueMgr iMgr = new StatisticsIssueMgr();
 	ArrayList arList = iMgr.getIssueData1(sDateFrom,sDateTo,setStime,setEtime);

 	
 	String checks = pr.getString("checks");
 	String checks_name = pr.getString("checks_name");
 	ArrayList arGraphData1 = null;
 	ArrayList arGraphData2 = null;
 	String[] arCode = null;
 	String[] arName = null;
 	
 	arCode = checks.split(",");
	arName = checks_name.split(",");
	 	
	String md_date = "";
	String positive = "";
	String negative = "";
	String neutral = "";
	
 	if(!checks.equals("")){
 		//그래프1번 가져오기~
 	 	arGraphData1 = iMgr.getIssueData2(sDateFrom,sDateTo,setStime,setEtime, checks);
 	 	//그래프2번 가져오기~
 	 	arGraphData2 = iMgr.getIssueData3(sDateFrom,sDateTo,setStime,setEtime, checks);
 	 	md_date = (String)arGraphData2.get(0);
 		positive = (String)arGraphData2.get(1);
 		negative = (String)arGraphData2.get(2);
 		neutral = (String)arGraphData2.get(3); 
 	}
	
	
 	
	
%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!--<link rel="stylesheet" href="../css/basic.css" type="text/css">-->
<link rel="stylesheet" href="../../../css/base.css" type="text/css">
<link rel="stylesheet" href=../css/calendar.css" type="text/css">
<style>
iframe.hide { border:0px solid red; position:absolute; top:0px; left:0px; z-index:-99; width:148px; height:150px; filter: alpha(opacity=0);}
</style>
<script src="<%=SS_URL%>js/Calendar.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/jquery.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/ajax.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/common.js"  type="text/javascript"></script>
<script src="<%=SS_URL%>js/popup.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/amcharts.js" type="text/javascript"></script>
<script language="JavaScript" type="text/JavaScript">

	sCurrDate = '<%=du.getCurrentDate("yyyy-MM-dd")%>';
	sDateFrom = '<%=sDateFrom%>';
	sDateTo = '<%=sDateTo%>';

	function setDateTerm( day ) {
       var f = document.SSend;
       f.sDateFrom.value   = AddDate( day );
       f.sDateTo.value     = sCurrDate;

	}

	function java_all_trim(a) {
        for (; a.indexOf(" ") != -1 ;) {
          a = a.replace(" ","");
        }
        return a;
    }

	function AddDate( day ) {

        var newdate = new Date();
        sdate = newdate.getTime();

        edate = sdate - ( day * 24 * 60 * 60 * 1000);
        newdate.setTime(edate);

        last_ndate = newdate.toLocaleString();

        last_date = java_all_trim(last_ndate);
        last_year = last_date.substr(0,4);
        last_month = last_date.substr(5,2);
        last_mon = last_month.replace("월","");

        if(last_mon < 10) {

            last_m = 0+last_mon;
            last_day = last_date.substr(7,2);
            last_da = last_day.replace("일","");

            if(last_da < 10) {
                last_d = 0+last_da;
            }else{
                last_d = last_da;
            }

        }else{
            last_m = last_mon;

            last_day = last_date.substr(8,2);
            last_da = last_day.replace("일","");

            if(last_da < 10) {
                last_d = 0+last_da;
            }else{
                last_d = last_da;
            }

        }

        last_time = last_year + '-' + last_m + '-' + last_d;

        return last_time;
    }


	function search(){
		var f = document.SSend;
		
		
		var o=document.getElementsByName('tt');
		var o_name=document.getElementsByName('tt_name');
 		var chkedseq='';
 		var chkedName='';
 		
 		for(i=0; i<o.length; i++) {
 	 		
 			if(o[i].checked == true){
 				
	 			if(o[i].value!=""){
	 				if(chkedseq == ''){
	 					chkedseq = String(o[i].value);
	 					chkedseq = o[i].value;
	 					chkedName = o_name[i].value;
	 				}else{
	 					chkedseq = chkedseq +','+o[i].value;
	 					chkedName = chkedName + ',' + o_name[i].value;
	 				}
	 			}
 			}
 		}

		f.checks.value = chkedseq;
		f.checks_name.value = chkedName;
		
		f.target ='';
		f.action ='statistics_issue.jsp';
		f.submit();
		

		
	}

	//체크박스
    function checkAll(chk) {
 		var o=document.getElementsByName('tt');
 		for(i=0; i<o.length; i++) {
 			o[i].checked = chk;
 		}
 	}

 	
 	
</script>


</head>
<iframe id="processFrame" name="processFrame" height="0"></iframe>
<body style="margin-left: 15px">
<form id="SSend" name="SSend" action="" method="post">
<input type="hidden" name="checks">
<input type="hidden" name="checks_name">
<table style="width:100%;height:100%;" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="body_bg_top" valign="top">
			<table width="820" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="tit_bg" style="height:67px;padding-top:20px;">
					<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td><img src="../../../images/statistics/tit_icon.gif" /><img src="../../../images/statistics/tit_0529_01.gif" /></td>
							<td align="right">
							<table border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td class="navi_home">HOME</td>
									<td class="navi_arrow">통계관리</td>
									<td class="navi_arrow2">관심 이슈 통계</td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td height="15"></td>
				</tr>
			</table>
			
			<table width="820" border="0" cellspacing="0" cellpadding="0">
				  <tr>
				    <td><table width="820" border="0" cellspacing="0" cellpadding="0">
				      <tr>
				        <td width="15"><img src="../../../images/issue/table_bg_01.gif" width="15" height="35" /></td>
				        <td width="793" background="../../../images/issue/table_bg_02.gif"><table width="790" border="0" cellspacing="0" cellpadding="0">
				          <tr>
				            <td width="17" style="display: none;"><img src="../../../images/issue/table_icon_01.gif" width="16" height="12" /></td>
				            <td width="62" class="b_text" style="display: none;"><strong>검색단어 </strong></td>
				            <td width="213" style="display: none;"><input type="text" class="textbox3" style="width:200px;" name="searchKey" value="" onkeydown="Javascript:if(event.keyCode == 13){search();}"/></td>
				            <td width="16"><img src="../../../images/issue/table_icon_01.gif" width="16" height="12" /></td>
				            <td width="60" class="b_text"><strong>검색기간</strong></td>
				            <td width="*"><table border="0" cellpadding="0" cellspacing="0">
				              <tr>
				                <td width="86"><input style="width:90px;text-align:center" class="textbox" type="text" id="sDateFrom" name="sDateFrom" value="<%=sDateFrom%>"></td>
				                <td width="27"><img src="../../../images/issue/btn_calendar.gif" style="cursor:pointer" onclick="calendar_sh(document.getElementById('sDateFrom'))"/></td>
				                <%
				                	String sBasics  = stime.equals("") ? "16" :  stime;
				                	String eBasics  = etime.equals("") ? "16" :  etime;
								%>
				                
				                <td width="55"><select name="stime"><%for(int i=0; i<24; i++){ if(i==Integer.parseInt(sBasics)){out.print("<option value="+i+" selected>"+i+"시</option>");}else{out.print("<option value="+i+">"+i+"시</option>");}} %></select></td>
				                <td width="11" align="center">~</td>
				                <td width="86"><input style="width:90px;text-align:center" class="textbox" type="text" id="sDateTo" name="sDateTo" value="<%=sDateTo%>"></td>
				                <td width="27"><img src="../../../images/issue/btn_calendar.gif" style="cursor:pointer" onclick="calendar_sh(document.getElementById('sDateTo'))"/></td>
				                <td width="55"><select name="etime"><%for(int i=0; i<=24; i++){ if(i==Integer.parseInt(eBasics)){out.print("<option value="+i+" selected>"+i+"시</option>");}else{out.print("<option value="+i+">"+i+"시</option>");}} %></select></td>
				              </tr>
				            </table></td>
				            <td width="75"><img src="../../../images/issue/search_btn.gif" width="61" height="22" hspace="5" align="absmiddle" onclick="search()" style="cursor:pointer"/></td>
				          </tr>
				        </table></td>
				        <td width="12"><img src="../../../images/issue/table_bg_03.gif" width="12" height="35" /></td>
				      </tr>
				    </table></td>
				  </tr>
				 
				  <tr>
				    <td height="1" bgcolor="AEC6CE"></td>
				  </tr>
				</table>
			
			
			
			
			
			
			
			
			
			
			<br>
			<br>
			
			
			
			
			
			
			
			
			<table width="820" border="0" cellspacing="0" cellpadding="0">
			  <tr>
			    <td><table width="820" border="0" cellspacing="0" cellpadding="0">
			      <tr>
			        <td width="820" valign="top"><table width="820" border="0" cellspacing="0" cellpadding="0">
			          
			            <tr>
			              <td height="5"></td>
			            </tr>
			            <tr>
			              <td><table id="board_01" border="0" cellpadding="0" cellspacing="0">
			                  <tr>
			                    <th width="50"><input type="checkbox" name="checkall" onclick="checkAll(this.checked);" value=""></th>
			                    <th width="450">이슈</th>
			                    <th style="color: #5198d8" width="80">긍정</th>
			                    <th style="color: #ca4c4d" width="80">부정</th>
			                    <th style="color: green;" width="80">중립</th>
			                    <th width="80">전체합계</th>
			                    
			                  </tr>
			                  <%
			                  	StatisticsSuperBean.StatisticsIssue sBean = null;
			                  	String checked  = "";
			                  	for(int i =0; i < arList.size(); i++){
			                  		sBean = (StatisticsSuperBean.StatisticsIssue)arList.get(i);
			                  		
			                  		checked  = "";
			                  		if(!checks.equals("")){
			                  			if(su.inarray(arCode, sBean.getIc_code())){
			                  				checked  = "checked";			                  					
			                  			}
			                  		}
			                  		
			                  %>
			                  	<tr>
			                    	<td height="48"><input name="tt" type="checkbox" value="<%=sBean.getIc_code()%>" id="tt" <%=checked%>><input name="tt_name" type="hidden" value="<%=sBean.getIc_name()%>" id="tt_name" ></td>
			                    	<td><%=sBean.getIc_name()%></td>
			                    	<td><%=sBean.getPositive()%></td>
			                    	<td><%=sBean.getNegative()%></td>
			                    	<td><%=sBean.getNeutral()%></td>
			                    	<td style="font-weight: bold"><%=sBean.getSum()%></td>
			                    </tr>
			                  		
			                  <%		
			                  	}
			                  %>
			                  
			                
			              </table></td>
			            </tr>
			        </table></td>
			        <td width="20"></td>
			        <td valign="top" style="padding:31px 0px 0px 0px">
<!--			        	<img src="../../../images/statistics/graph.gif" width="191" height="516">-->
						

			        </td>
			      </tr>
			    </table></td>
			  </tr>
			  <tr>
			    <td height="20"></td>
			  </tr>
			  
	 
			  <%
						if(!checks.equals("")){
					%>	
			  <tr>
			    <td><table width="820" border="0" cellspacing="0" cellpadding="0">
			      <tr>
			        <td><img src="../../../images/statistics/bg_graph_01.gif" width="820" height="5"></td>
			      </tr>
			      <tr>
			        <td align="center" background="../../../images/statistics/bg_graph_02.gif" style="padding:15px 0px 15px 0px">
							        	
						    <script type="text/javascript">
							
							var strStram = new Array;

						    var date = '';
						    var year = '';
						    var month = '';
						    var day = '';
						    
							<%
							String[] info = null;
							int colCnt = 0;
							
							for(int i = 0; i< arGraphData1.size(); i++){
								info = (String[])arGraphData1.get(i);
								
							%>
								date = '<%=info[0]%>';
								
								year = Number(date.substring(0,4));
						      	month =  Number(date.substring(4,6));
						      	day = Number(date.substring(6,8)); 

								strStram[<%=i%>] = {date : new Date(year, month-1, day)

								<%
									for(int j = 0; j < arCode.length; j++){
								%>
									, <%=arName[j].replaceAll(" ","")%> : <%=info[j+1]%>   
								<%		
									}
								%>

								}; 

							<%}%>

							 var chart1Data = strStram;

							 AmCharts.ready(function() {

			                       // LINE CHART
			                       var chart1 = new AmCharts.AmSerialChart();
			                       chart1.pathToImages = "http://www.amcharts.com/lib/images";
			                       chart1.dataProvider = chart1Data;
			                       chart1.marginRight = 20;    
			                       chart1.marginLeft = 10;    
			                       chart1.marginTop = 0;    
			                       chart1.backgroundColor = '#000000';
			                       //chart1.sequencedAnimation = true;
			                       chart1.startEffect = "elastic";
			                       //chart1.startDuration = 2;
			                       chart1.categoryField = "date";

			                       var categoryAxis = chart1.categoryAxis;

		                    	   categoryAxis.parseDates = true;
			                    	

			                       //categoryAxis.minPeriod="hh";
			                       categoryAxis.dashLength = 5;
			                       categoryAxis.equalSpacing = true;
			                       categoryAxis.startOnAxis = true;
			                       categoryAxis.axisAlpha = 0;
			                       categoryAxis.fillAlpha = 1;
			                       categoryAxis.fillColor = "#FAFAFA";
			                       categoryAxis.gridAlpha = 0.07;
			                       categoryAxis.gridPosition = "start";
			                       categoryAxis.autoGridCount = false;
			                       

			                       var valueAxis1 = new AmCharts.ValueAxis();
			                       valueAxis1.axisAlpha = 0;
			                       valueAxis1.dashLength = 5;
			                       //valueAxis1.inside = true;
			                       chart1.addValueAxis(valueAxis1);


			                       <%for(int i = 0; i < arName.length; i++){ %>
			                       var graph<%=i%> = new AmCharts.AmGraph();
			                       graph<%=i%>.bullet = "round";
			                       graph<%=i%>.bulletSize = 8;
			                       graph<%=i%>.valueField = '<%=arName[i].replaceAll(" ","")%>';
			                       graph<%=i%>.lineThickness=3;  //두께
			                       
			                       graph<%=i%>.title = '<%=arName[i].replaceAll(" ","")%>';
			                       //graph1.showBalloonAt = "low";
			                       chart1.addGraph(graph<%=i%>);
			                       <%}%>

			                       // LEGEND
			                       var legend = new AmCharts.AmLegend();
			                       legend.markerType = "circle";
			                       legend.position = "right";
			                       chart1.addLegend(legend);
			                       
			                       chart1.write("chart1");

			                   });
							 
							</script>
						
						<div id="chart1" style="width: 800px; height: 200px; float: left; padding-left: 20px;"></div>
			        </td>
			      </tr>
			      <tr>
			        <td><img src="../../../images/statistics/bg_graph_03.gif" width="820" height="5"></td>
			      </tr>
			    </table></td>
			  </tr>
			  <%} %>	
			    
			  
			    <tr>
			    <td height="20"></td>
			  </tr>
			  
			  
			  <%
						if(!checks.equals("")){
					%>	
			  <tr>
			    <td><table width="820" border="0" cellspacing="0" cellpadding="0">
			      <tr>
			        <td><img src="../../../images/statistics/bg_graph_01.gif" width="820" height="5"></td>
			      </tr>
			      <tr>
			        <td align="center" background="../../../images/statistics/bg_graph_02.gif" style="padding:15px 0px 15px 0px">
<!--			        	<img src="../../../images/statistics/graph_01.gif" width="687" height="328">-->
							        	
						    <script type="text/javascript">


		        var md_date = '<%=md_date%>';
		        var positive = '<%=positive%>';
		        var negative = '<%=negative%>';
		        var neutral = '<%=neutral%>';

	        
		        var ar_md_date = md_date.split(",");
		        var ar_positive = positive.split(",");
		        var ar_negative = negative.split(",");
		        var ar_neutral = neutral.split(",");

		        
		        var strStram2 = new Array;
		       
				for(var i = 0; i < ar_md_date.length; i++){
					
					year = Number(ar_md_date[i].substring(0,4));
			      	month =  Number(ar_md_date[i].substring(4,6));
			      	day = Number(ar_md_date[i].substring(6,8));
			      		
					strStram2[i] = {date: new Date(year, month-1, day),긍정: Number(ar_positive[i]),부정: Number(ar_negative[i]),중립: Number(ar_neutral[i])};
					
				}
				
				var chart1Data2 = strStram2;

		        

                   AmCharts.ready(function() {

                       // LINE CHART
                       var chart1 = new AmCharts.AmSerialChart();
                       chart1.pathToImages = "http://www.amcharts.com/lib/images";
                       chart1.dataProvider = chart1Data2;
                       chart1.marginRight = 20;    
                       chart1.marginLeft = 1;    
                       chart1.marginTop = 30;    
                       chart1.backgroundColor = '#000000';
                       chart1.startEffect = "elastic";
                       chart1.categoryField = "date";

                       var categoryAxis = chart1.categoryAxis;

                       
                   	   categoryAxis.parseDates = true;
                    	
                       categoryAxis.dashLength = 5;
                       categoryAxis.equalSpacing = true;
                       categoryAxis.startOnAxis = true;
                       categoryAxis.axisAlpha = 0;
                       categoryAxis.fillAlpha = 1;
                       categoryAxis.fillColor = "#FAFAFA";
                       categoryAxis.gridAlpha = 0.07;
                       categoryAxis.gridPosition = "start";
                       categoryAxis.autoGridCount = true;
                       

                       var valueAxis1 = new AmCharts.ValueAxis();
                       valueAxis1.axisAlpha = 0;
                       valueAxis1.dashLength = 5;
                       chart1.addValueAxis(valueAxis1);

                       var graph1 = new AmCharts.AmGraph();
                       graph1.bullet = "round";
                       graph1.bulletSize = 8;
                       graph1.valueField = "긍정";
                       graph1.lineThickness=3;  //두께
                       graph1.lineColor="#2d70bf"; //라인색
                       graph1.title = "긍정";
                       chart1.addGraph(graph1);

                       var graph2 = new AmCharts.AmGraph();
                       graph2.bullet = "round";
                       graph2.bulletSize = 8;    
                       graph2.valueField = "부정";
                       graph2.lineThickness=3;  //두께
                       graph2.lineColor = "#c02d27"; //라인색
                       graph2.title = "부정";
                       chart1.addGraph(graph2);

                       var graph3 = new AmCharts.AmGraph();
                       graph3.bullet = "round";
                       graph3.bulletSize = 8;    
                       graph3.valueField = "중립";
                       graph3.lineThickness=3;  //두께
                       graph3.lineColor = "#92bf35"; //라인색
                       graph3.title = "중립";

                       chart1.addGraph(graph3);

                       graph1.balloonText = "[[category]]: [[value]]건";
                       graph2.balloonText = "[[category]]: [[value]]건";
                       graph3.balloonText = "[[category]]: [[value]]건";

                       // LEGEND
                       var legend = new AmCharts.AmLegend();
                       legend.markerType = "circle";
                       legend.position = "right";
                       chart1.addLegend(legend);

                       chart1.write("chart2");

                   });

				
				</script>
						
						<div id="chart2" style="width: 800px; height: 200px; float: left; padding-left: 20px;"></div>
			        </td>
			      </tr>
			      <tr>
			        <td><img src="../../../images/statistics/bg_graph_03.gif" width="820" height="5"></td>
			      </tr>
			    </table></td>
			  </tr>
			  <%} %>
			</table>
			<br>
			<br>
			<br>
			<br>	
		</td>
	</tr>
</table>
<%-- 달력 테이블 --%>
<table width="162" border="0" cellspacing="0" cellpadding="0" id="calendar_conclass" style="position:absolute;display:none;">
	<tr>
		<td><img src="../../../images/calendar/menu_bg_004.gif" width="162" height="2"></td>
	</tr>
	<tr>
		<td align="center" background="../../../images/calendar/menu_bg_005.gif"><table width="148" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="6"></td>
			</tr>                                		
			<tr>
				<td id="calendar_calclass">
				</td>
			</tr>
			<tr>
				<td height="5"></td>
			</tr>
		</table></td>
	</tr>
	<tr>
		<td><img src="../../../images/calendar/menu_bg_006.gif" width="162" height="2"></td>
	</tr>
</table>
</form>
</body>
</html>