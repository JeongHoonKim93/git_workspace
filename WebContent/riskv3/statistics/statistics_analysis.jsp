<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/sessioncheck.jsp" %>    
<%@ page import="	risk.issue.IssueDataBean
					,risk.issue.IssueMgr
					,java.util.ArrayList
					,risk.util.ParseRequest
					,risk.util.ConfigUtil
					,risk.util.StringUtil
					,risk.util.DateUtil 
					,risk.issue.IssueCodeMgr
					,risk.issue.IssueCodeBean
                 	,risk.issue.IssueMgr
                 	,risk.issue.IssueReportMgr
                 	,risk.issue.IssueReportBean
                 	,risk.issue.IssueStaticMgr
                 	,risk.issue.IssueStaticBean 
                 	,risk.JfreeChart.*
                 	,risk.issue.GraphMgr	              
	                ,risk.issue.GraphDataInfo
	                ,org.jfree.chart.JFreeChart
	                ,org.jfree.chart.ChartFactory
	                ,org.jfree.data.category.DefaultCategoryDataset
	                ,org.jfree.data.time.TimeSeriesCollection
	                ,org.jfree.chart.axis.ValueAxis
	                ,org.jfree.chart.axis.DateTickUnit
	                ,org.jfree.chart.axis.NumberAxis
	                ,org.jfree.chart.ChartRenderingInfo
	                ,org.jfree.chart.entity.StandardEntityCollection
	                ,org.jfree.chart.ChartUtilities
	                ,org.jfree.chart.plot.CategoryPlot
	                ,org.jfree.chart.plot.PlotOrientation
	                ,org.jfree.chart.renderer.category.LineAndShapeRenderer
	                ,org.jfree.chart.renderer.xy.XYItemRenderer
	                ,org.jfree.chart.renderer.xy.XYLineAndShapeRenderer
                 	" %>
<%@ page import="java.awt.*"%>        	
<%@ page import="java.io.*"%>                  	
                 	

<%
	String ir_title = null;		// 리포트 타이틀
	String ir_schSdate = null;	// 검색 시작일 (YYYY-MM-DD)
	String ir_schEdate = null;	// 검색 종료일 (YYYY-MM-DD)
	String ir_schCode2 = null;	// 검색 부서코드
	String ir_summary = null;	// 온라인 동향
	String ir_charUnit = null;	// 그래프 단위 (d : 일별,w : 주별,m : 월별)
	String[] ir_form = null;		// 통계보고서 항목 선택 ( 1 : 온라인 동향, 2 : 부서별 현황, 3 : RISK 유형별 현황, 4 : 기간별 RISK유형 추이, 5 : 중요이슈 정보 )
	String sDateFrom = null;		
	String sDateTo = null;		
	
	String imgUrl = null;		// 이미지 url
	ArrayList arrSchCode = new ArrayList();		// 검색 코드 배열
	ArrayList unitCodeStatic = null;	// 단일코드 통계 카운트
	ArrayList dualCodeStatic1 = null;	// RISK유형 / 불만강도 통계 카운트
	ArrayList dualCodeStatic3 = null;	// RISK유형 / 불만강도 통계 카운트
	
	ArrayList dateStaic = null;			// 기간별 카운트 그래프용 통계
	ArrayList arrTemp = null;
	ArrayList allist = null;
	allist = new ArrayList();

	ConfigUtil cu = new ConfigUtil();
	StringUtil su = new StringUtil();
	DateUtil   du = new DateUtil();
	ParseRequest pr = new ParseRequest(request);
	IssueReportMgr irm = new IssueReportMgr();
	IssueReportBean irBean = null;
	IssueStaticMgr ism = new IssueStaticMgr();
	IssueCodeMgr ICMgr = new IssueCodeMgr();
	IssueCodeBean ICBean = null;
	IssueCodeBean ICBean2 = null;
	IssueCodeBean isb = null;
	ICMgr.init(0);   // 이슈코드 초기화
	
	pr.printParams();
	
	String Url = "";
	Url = cu.getConfig("URL");
	String popUrl = Url+"riskv3/issue/";

	ArrayList arrIDBean = new ArrayList();
	IssueMgr IDMgr = new IssueMgr();
	IssueDataBean IDBean = new IssueDataBean();	
	
	String siteUrl = cu.getConfig("URL");
	String graphPath = cu.getConfig("CHARTPATH")+"/statistics/";
	
	imgUrl = siteUrl+"riskv3/issue/images/";
	
	ir_title = pr.getString("ir_title","test");
	
	sDateFrom = pr.getString("sDateFrom");
	sDateTo = pr.getString("sDateTo");

	String typecode = pr.getString("typecode","1,1");
	ir_summary = pr.getString("ir_summary","");
	ir_charUnit = pr.getString("ir_chartUnit","d");
	ir_schCode2 = pr.getString("rowType","0");

		
	String sCurrDate    = du.getCurrentDate();
	if (sDateTo.equals("")) sDateTo = du.getCurrentDate();
	if (sDateFrom.equals("")) {
		du.addDay(-7);
		sDateFrom = du.getDate();
	}
		
	// 검색 코드조건 배열을 생성
	String[] tmpStrArr = null;
		String[] tmpStrArr2 = null;
		int j=0;
		int k=0;
	if( ir_schCode2.length() > 0 )
	{
		tmpStrArr = ir_schCode2.split("@");
		
		
		for(j=0; j<tmpStrArr.length; j++) {
			tmpStrArr2 = tmpStrArr[j].split(",");
			arrTemp = new ArrayList();
	 		if(tmpStrArr2.length >1){
				arrTemp.add(tmpStrArr2[0]);
				arrTemp.add(tmpStrArr2[1]);
	 		}else{
	 			arrTemp.add(tmpStrArr2[0]);
	 		}
			
			arrSchCode.add(arrTemp);
		}
	}

	// 데이터 생성
	if( (sDateFrom != null) && (sDateTo != null) )
	{
		MakeChart mc = new MakeChart();
		GetChartData gcd = new GetChartData();

		ArrayList colArrIC = new ArrayList();
		ArrayList colArrCon = new ArrayList();
		ArrayList rowArrIC = new ArrayList();
		ArrayList rowArrCon = new ArrayList();
		ArrayList dateArrCon = new ArrayList();
		ArrayList colArrAvg = new ArrayList();
		
		ArrayList arrTimelist = new ArrayList();
		
		int colType = 0;
		int rowType = 0;
		String viewType = pr.getString("viewType","cnt");
		String dateType = pr.getString("dateType","d");

		colType = pr.getInt("colType", 4);
		if(colType==0){
			colType=1;
		}
		//System.out.println("가로세로축설정시작");
		
//		out.println("rowType = "+ pr.getInt("rowType") +"<br>");
//		out.println("viewType = "+ viewType +"<br>");
//		out.println("colType = "+ colType +"<br>");
//		out.println("dateType = "+ dateType +"<br>");
//		out.println("======================================");
		if(pr.getInt("rowType")>0 && viewType.equals("cnt")){	
			// 세로축
			
			
			
			colArrIC = ICMgr.GetType(colType);		// 세로축  제목
			colArrCon = gcd.getDateStatic(sDateFrom, sDateTo,colType,dateType, arrSchCode,typecode); //내용
			//가로축		 
			rowType = pr.getInt("rowType");
			rowArrIC = ICMgr.GetType(rowType);		// 가로축 제목
			dualCodeStatic1 = ism.getDualCodeStatic( sDateFrom, sDateTo, arrSchCode, pr.getString("colType"), pr.getString("rowType"));
			rowArrCon = gcd.getDateStatic(sDateFrom, sDateTo,rowType,dateType, arrSchCode,typecode); //내용
			dateArrCon = gcd.getXvalDate(sDateFrom.replaceAll("-", ""), sDateTo.replaceAll("-", ""),dateType,"yyyyMMdd");
			
			
		}else if(pr.getInt("rowType")>0 && viewType.equals("per")){
			// 세로축
			colArrIC = ICMgr.GetType(colType);
			colArrCon = gcd.getDateStatic(sDateFrom, sDateTo,colType,dateType, arrSchCode,typecode); //내용
			// 가로축
			rowType = pr.getInt("rowType");
			rowArrIC = ICMgr.GetType(rowType);		// 가로축 제목
			dualCodeStatic1 = ism.getDualCodeStatic( sDateFrom, sDateTo, arrSchCode, pr.getString("colType"), pr.getString("rowType"));
			colArrAvg = ism.makeAvg(dualCodeStatic1,"code");// per ViewType
			
			
		}else{	//가로축이 날짜이고 표시단위 카운트
			//세로
			colArrIC = ICMgr.GetType(colType);
			colArrCon = gcd.getDateStatic(sDateFrom, sDateTo,colType,dateType, arrSchCode,typecode); //내용
			rowArrIC = gcd.getXvalDate(sDateFrom.replaceAll("-", ""), sDateTo.replaceAll("-", ""),dateType,"yyyyMMdd");
			arrTimelist = gcd.getTimeStatic(sDateFrom, sDateTo,colType,dateType); //
			if(viewType.equals("per")){	//가로축이 날짜이고 표시단위 비율
				colArrAvg = ism.makeAvg(colArrCon,"date");
			}else{
			//가로
			}
		}

//		 ==============================================================================차트 데이터셋 설정===============================================================================
	DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	TimeSeriesCollection Time_dataset = new TimeSeriesCollection();
	
	
	String chartName = "";
	String tempTime = "";
	int xx = 0;
	
	
	//차트 이미지 저장 경로
	String filePath = cu.getConfig("CHARTPATH")+"statistics/";
	
	//차트 이미지 저장 폴더
	String filedir = "chartImg";
	ArrayList arrBrendData = null;
	ArrayList arrTopicData = null;
	ArrayList arrSourceData = null;
	ArrayList arrSaleData = null;
	
	// 그래프 작성 변수 선언
	tempTime = du.getCurrentDate("yyyyMMddHHmmss");
	chartName = "analysis"+dateType+rowType+colType+tempTime+".png";
	String chartUrl = cu.getConfig("CHARTURL")+"statistics/";	
	
	GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, new Color(55,124,195), 0.0f, 0.0f, new Color(55,124,195));
	GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, new Color(220,157,67), 0.0f, 0.0f, new Color(220,157,67));
	GradientPaint gp2 = new GradientPaint(0.0f, 0.0f, new Color(153,184,45),     0.0f, 0.0f, new Color(153,184,45));
	GradientPaint gp3 = new GradientPaint(0.0f, 0.0f, new Color(156,125,192),     0.0f, 0.0f, new Color(156,125,192));
	GradientPaint gp4 = new GradientPaint(0.0f, 0.0f, new Color(45,163,193),     0.0f, 0.0f, new Color(45,163,193));
	GradientPaint gp5 = new GradientPaint(0.0f, 0.0f, new Color(92,155,209),     0.0f, 0.0f, new Color(92,155,209));
	GradientPaint gp6 = new GradientPaint(0.0f, 0.0f, new Color(146,209,92),     0.0f, 0.0f, new Color(146,209,92));
	GradientPaint gp7 = new GradientPaint(0.0f, 0.0f, new Color(92,209,205),     0.0f, 0.0f, new Color(92,209,205));
	GradientPaint gp8 = new GradientPaint(0.0f, 0.0f, new Color(209,188,92),     0.0f, 0.0f, new Color(209,188,92));
	if( pr.getInt("rowType") > 0 ){	// 가로축 내용분류,성향분류일경우
				
		// 코드가 2가지 일때
		dualCodeStatic3 = ism.getDualCodeStatic2( sDateFrom, sDateTo, arrSchCode, pr.getString("colType"), pr.getString("rowType") );
				
		// 2가지 코드로 통계
		dataset = mc.SetValue2(dualCodeStatic3);
		JFreeChart chart = ChartFactory.createLineChart("","", "", dataset, PlotOrientation.VERTICAL, true, true, false);
		//차트 폰트
		chart.getLegend().setItemFont(new java.awt.Font("LucidaBrightDemiBold",0,12));
	 	chart.setTitle("");
		chart.setBackgroundPaint(Color.WHITE);
		 
		 ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
		 CategoryPlot plot = (CategoryPlot)chart.getCategoryPlot(); 
		 plot.setDomainCrosshairVisible(true);
		 plot.setRangeCrosshairVisible(true);
		 
		// Y축 정수만 표시하도록 설정하기
		NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
		yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
				
		CategoryAxis Xaxis = plot.getDomainAxis();
		Xaxis.setTickLabelFont(new java.awt.Font("LucidaBrightDemiBold",0,12));
		 // 차트 BG COLOR 변경
		 plot.setDomainGridlinesVisible(true);
		 plot.setBackgroundPaint(Color.WHITE); 
		 plot.setRangeGridlinePaint(Color.lightGray);
		 plot.setDomainGridlinePaint(Color.lightGray);
		 
		 //String tmpFileName =  du.getCurrentDate("yyyyMMddhhmmss");
		 //String fileName = "D:/WEB/RSM/TEMP/" +tmpFileName+".png";
		 
		 // 그래프 라인 색 속성
		 
		 LineAndShapeRenderer renderer0 = (LineAndShapeRenderer) plot.getRenderer();
		 renderer0.setSeriesShapesVisible(0,true);
		 renderer0.setSeriesShapesVisible(1,true);
		 renderer0.setSeriesShapesVisible(2,true);
		 renderer0.setSeriesShapesVisible(3,true);
		 renderer0.setSeriesShapesVisible(4,true);
		 renderer0.setSeriesShapesVisible(5,true);
		 
		
		 
		 renderer0.setSeriesPaint(0, gp0);
		 renderer0.setSeriesPaint(1, gp1);
		 renderer0.setSeriesPaint(2, gp2);
		 renderer0.setSeriesPaint(3, gp3);
		 renderer0.setSeriesPaint(4, gp4);
		 renderer0.setSeriesPaint(5, gp5);
		 
		 
		 // 그래프 라인 두께
		 renderer0.setSeriesStroke(0,new BasicStroke(1));
		 renderer0.setSeriesStroke(1,new BasicStroke(1));
		 renderer0.setSeriesStroke(2,new BasicStroke(1));
		 renderer0.setSeriesStroke(3,new BasicStroke(1));
		 renderer0.setSeriesStroke(4,new BasicStroke(1));
		 renderer0.setSeriesStroke(5,new BasicStroke(1));
	    
		ChartRenderingInfo info2 = new ChartRenderingInfo(new StandardEntityCollection());
		 
		 // 파일을 PNG로 생성 합니다.
		 // JPEG생성은 saveChartAsJPEG
		 if(!new File(filePath).isDirectory() ) new File(filePath).mkdirs(); 
		 ChartUtilities.saveChartAsPNG(new File(filePath+chartName),chart,745,330,info);
			 
	}else if( pr.getInt("rowType") == 0 ){	//가로축 날짜일경우	
				
		String datecheck = "";
		Time_dataset = mc.setChartDataKeywordCount(sDateFrom, sDateTo, arrTimelist,dateType);
		
		JFreeChart chart2 = ChartFactory.createTimeSeriesChart("","", "", Time_dataset, true, true, false);
		//차트 폰트
		chart2.getLegend().setItemFont(new java.awt.Font("LucidaBrightDemiBold",0,12));
		org.jfree.chart.plot.XYPlot plot2 = chart2.getXYPlot();
		plot2.setDomainCrosshairVisible(true);
		plot2.setRangeCrosshairVisible(true);
	    org.jfree.chart.axis.DateAxis xAxis = (org.jfree.chart.axis.DateAxis)plot2.getDomainAxis();
	    //X축 Date 출력방식 설정.
	    if(dateType.equals("w")){
	    	xAxis.setDateFormatOverride(new java.text.SimpleDateFormat("yyyy/ww주"));
	    	//x축 라벨
	    	//xAxis.setLabel("년/주");
	    	//xAxis.setAutoTickUnitSelection(false,true);
	    	//xAxis.setTickUnit(new DateTickUnit(DateTickUnit.DAY,7));
	    	//xAxis.setTickLabelsVisible(true);
	    	datecheck = String.valueOf(du.DateDiff(sDateTo.replaceAll("-", ""), sDateFrom.replaceAll("-", "")));
	    	if(Integer.parseInt(datecheck)<35){
	    	//x축 한주간격
	    	xAxis.setTickUnit(new DateTickUnit(DateTickUnit.DAY,7));
			//xAxis.setAutoTickUnitSelection(true);
			//xAxis.setStandardTickUnits(DateAxis.createStandardDateTickUnits());
	    	}
	    }else if(dateType.equals("m")){
	    	xAxis.setDateFormatOverride(new java.text.SimpleDateFormat("yyyy/MM월"));
	    	//x축 라벨
	    	//xAxis.setLabel("년/월");
	    	datecheck = String.valueOf(du.DateDiff(sDateTo.replaceAll("-", ""), sDateFrom.replaceAll("-", "")));
	    	
	    	xAxis.setTickUnit(new DateTickUnit(DateTickUnit.MONTH,1));	    	
	    	if(Integer.parseInt(datecheck)<210){
	    	//x축 한달간격
	    	xAxis.setTickUnit(new DateTickUnit(DateTickUnit.MONTH,1));
	    	}
	    }else if(dateType.equals("d")){
	    	xAxis.setDateFormatOverride(new java.text.SimpleDateFormat("MM/dd"));
	    	//x축 라벨
	    	//xAxis.setLabel("월/일");
	    	datecheck = String.valueOf(du.DateDiff(sDateTo.replaceAll("-", ""), sDateFrom.replaceAll("-", "")));
	    	if(Integer.parseInt(datecheck)<14){
	    	//x축 하루간격
	    	xAxis.setTickUnit(new DateTickUnit(DateTickUnit.DAY,1));
	    	}
	    }
	    //x축 폰트 , 사이즈
	    //xAxis.setTickLabelFont(new Font("gulim", Font.PLAIN, 10));
	    xAxis.setTickLabelFont(new java.awt.Font("LucidaBrightDemiBold",0,12));
	
	    //x축 안쪽눈금 크기
	    xAxis.setTickMarkInsideLength(3);
	    
	    //x축 라벨 폰트,사이즈
	    xAxis.setLabelFont(new java.awt.Font("LucidaBrightDemiBold",0,12));
	
	    // x축 범위 
	    //xAxis.setAutoRange(true); 
	    //xAxis.setFixedAutoRange(554400000.0);  // 단위-밀리세컨드 //1시간 범위(3300000.0)1일(79200000.0) 1주일(554400000.0) (시간축 ) 
	  	
	    //그래프 간격
		//xAxis.setLowerMargin(0.50);
	    
	   	//차트 Bg color
		plot2.setBackgroundPaint(Color.WHITE); 
		plot2.setRangeGridlinePaint(Color.lightGray);
		plot2.setDomainGridlinePaint(Color.lightGray);
		//Y축 소수점 없이 ..
	   	NumberAxis yAxis = (NumberAxis) plot2.getRangeAxis();
	    yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
	    //Y축 안쪽 눈금크기
	    yAxis.setTickMarkInsideLength(3);
	    
	 	// 그래프 라인 색 속성
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesShapesVisible(0, true);
		renderer.setSeriesShapesVisible(0, true);
		renderer.setSeriesShapesVisible(0, true);
		renderer.setSeriesShapesVisible(0, true);
		plot2.setRenderer(renderer);
		 
		XYItemRenderer renderer1 = plot2.getRenderer();
		
		renderer1.setSeriesPaint(0, gp0);
		renderer1.setSeriesPaint(1, gp1);
		renderer1.setSeriesPaint(2, gp2);
		renderer1.setSeriesPaint(3, gp3);
		renderer1.setSeriesPaint(4, gp4);
		renderer1.setSeriesPaint(5, gp5);
		 
		 
		// 그래프 라인 두께
		renderer1.setSeriesStroke(0,new BasicStroke(1));
		renderer1.setSeriesStroke(1,new BasicStroke(1));
		renderer1.setSeriesStroke(2,new BasicStroke(1));
		renderer1.setSeriesStroke(3,new BasicStroke(1));
		renderer1.setSeriesStroke(4,new BasicStroke(1));
		renderer1.setSeriesStroke(5,new BasicStroke(1));
		   
		ChartRenderingInfo info2 = new ChartRenderingInfo(new StandardEntityCollection());
		//System.out.println("filePathL:"+filePath);
		if(!new File(filePath).isDirectory()) new File(filePath).mkdirs(); 
		ChartUtilities.saveChartAsPNG(new File(filePath+chartName),chart2,745,330,info2);
	
	}
//	 ==============================================================================차트 데이터셋 설정===============================================================================
	
	
	
	


%>


<%@page import="org.jfree.chart.axis.CategoryAxis"%>
<%@page import="java.util.logging.SimpleFormatter"%>
<%@page import="java.text.DecimalFormat"%>
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="../css/basic.css" type="text/css">
<link rel="stylesheet" href="../../css/base.css" type="text/css">
<style>
iframe.hide { border:0px solid red; position:absolute; top:0px; left:0px; z-index:-99; width:148px; height:150px; filter: alpha(opacity=0);}
</style>
<script src="<%=SS_URL%>js/Calendar.js" type="text/javascript"></script>
<script language="JavaScript" type="text/JavaScript">
<!--

	sDateFrom = '<%=sDateFrom%>';
	sDateTo = '<%=sDateTo%>';
	
	function GoStatics(val){
		
		SSend.action='statistics_analysis.jsp';
		SSend.submit();
	}
	function SaveExcel(){

		document.SSend.action='issue_static_excel.jsp';
       	document.SSend.target = 'selectSite';
        document.SSend.submit();
		document.SSend.action = 'statistics_analysis.jsp';
		document.SSend.target = '';
		
	}
	function getList(type1, code1, type2, code2, sDateForm, sDateTo, mode){
    	var url = "<%=popUrl%>pop_detail_list.jsp?type1="+type1+"&code1="+code1+"&type2="+type2+"&code2="+code2+"&sdate="+sDateFrom+"&edate="+sDateTo+"&mode="+mode;
 		window.open(url,'getList', 'width=650,height=530,scrollbars=no');
	}
	function getListDate(type1, code1, sDate, eDate, mode){
    	var url = "<%=popUrl%>pop_detail_list.jsp?type1="+type1+"&code1="+code1+"&sdate="+sDate+"&edate="+eDate+"&mode="+mode;
 		window.open(url,'getList', 'width=650,height=530,scrollbars=no');
	}

//-->
</script>
</head>
<body style="margin-left: 15px">
<form name="SSend" action="" method="post">
<table style="width:100%;height:100%;" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="body_bg_top" valign="top">
			<table width="820" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="tit_bg" style="height:67px;padding-top:20px;">
					<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td><img src="../../images/statistics/tit_icon.gif" /><img src="../../images/statistics/tit_0401.gif" /></td>
							<td align="right">
							<table border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td class="navi_home">HOME</td>
									<td class="navi_arrow">통계관리</td>
									<td class="navi_arrow2">분류정보통계</td>
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
			
			<table width="780" border="0" cellspacing="0" cellpadding="0">
				<tr bgcolor="#D8D8D8">
					<td colspan="2" height="3" style="padding: 0px 0px 0px 0px;"></td>
				</tr>
			</table>
			<table width="780" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td background="../../images/statistics/issue_t_bg01.gif"><img src="../../images/statistics/issue_t_img01.gif" width="15" height="7"></td>
					<td align="right" background="../../images/statistics/issue_t_bg01.gif"><img src="../../images/statistics/issue_t_img02.gif" width="15" height="7"></td>
				</tr>
			<%
				String[] Title = (ICMgr.getCodeTitle()).split(",");
				ArrayList codeList = (ArrayList)ICMgr.arrAllType;
			%>   
				<tr align="center">
					<td colspan="2" background="../../images/statistics/issue_t_bg03.gif"><table width="766" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="32" style="padding: 3px 0px 0px 10px;"><table width="740" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="80"><img src="../../images/statistics/issue_t_ico01.gif" width="11" height="15" align="absmiddle"><strong>세로축 :</strong></td>
									<td width="115"><select name="colType">
			<%
					String selected = "";
					for(int i = 0; i < codeList.size(); i++){
						IssueCodeBean icb = (IssueCodeBean)((ArrayList)codeList.get(i)).get(0);
						if(icb.getIc_type() == 4 || icb.getIc_type() == 7 || icb.getIc_type() == 6 || icb.getIc_type() == 9 || icb.getIc_type() == 10){
							selected = "";
							if(colType == icb.getIc_code()){
								selected ="selected";				
							}
			%>
								<option value="<%=icb.getIc_type()%>" <%=selected%>><%=icb.getIc_name()%></option>
			<%
						}
					}
			%>
									</select></td>
									<td width="70"><img src="../../images/statistics/issue_t_ico01.gif" width="11" height="15" align="absmiddle"><strong>가로축 :</strong></td>
									<td> 날짜<input type=hidden name="rowType" value="0"></td>
									<td align="right"><img style="cursor:hand;" onclick="GoStatics('static');" src="../../images/statistics/statis_btn01.gif" width="77" height="24"></td>
								</tr>
							</table></td>
						</tr>
						<tr>
							<td background="../../images/statistics/issue_t_line01.gif"><img src="../../images/statistics/brank.gif" width="1" height="2"></td>
						</tr>
						<tr>
							<td height="32" style="padding: 3px 0px 0px 10px;"><table width="740" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="80"><img src="../../images/statistics/issue_t_ico01.gif" width="11" height="15" align="absmiddle"><strong>검색기간  :</strong></td>
									<td width="230"><input name="sDateFrom" type="text" size="10" class="txtbox" maxlength="10" value="<%=sDateFrom%>">
									<img src="../../images/search/btn_calendar.gif" style="cursor:pointer;vertical-align: middle" onclick="calendar_sh(document.getElementById('sDateFrom'))"/>
										<input name="sDateTo" type="text" size="10"  maxlength="10" class="txtbox" value="<%=sDateTo%>" size="11">
										<img src="../../images/search/btn_calendar.gif" style="cursor:pointer;vertical-align: middle" onclick="calendar_sh(document.getElementById('sDateTo'))"/>
									</td>
									<td width="80"><img src="../../images/statistics/issue_t_ico01.gif" width="11" height="15" align="absmiddle"><strong>표시단위 :</strong></td>
									<td width="80">
										<select name="viewType">
											<option value="cnt" <%if(viewType.equals("cnt")){out.print("selected");} %>>카운트</option>
											<option value="per" <%if(viewType.equals("per")){out.print("selected");} %>>비율</option>
										</select>
									</td>
									<td width="80"><img src="../../images/statistics/issue_t_ico01.gif" width="11" height="15" align="absmiddle"><strong>날짜단위 :</strong></td>
									<td>
										<select name="dateType">
											<option value="d" <%if(dateType.equals("d")){out.print("selected");} %>>일별</option>
											<option value="m" <%if(dateType.equals("m")){out.print("selected");} %>>월별</option>
										</select>
									</td>
								</tr>
							</table></td>
						</tr>
					</table></td>
				</tr>
				<tr>
					<td background="../../images/statistics/issue_t_bg02.gif"><img src="../../images/statistics/issue_t_img03.gif" width="15" height="7"></td>
					<td align="right" background="../../images/statistics/issue_t_bg02.gif"><img src="../../images/statistics/issue_t_img04.gif" width="15" height="7"></td>
				</tr>
				<tr bgcolor="#D8D8D8">
					<td colspan="2"><table width="100%"  border="0" cellspacing="1" cellpadding="1">
						<tr>
							<td align="center" bgcolor="#FFFFFF"><table width="740" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td></td>
								</tr>
								<tr>
									<td>&nbsp;</td>
								</tr>
							</table>
							<table width="740" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td height="25"><table border="0">
										<tr>
											<td width="600"><img src="../../images/statistics/statis_title02.gif" width="69" height="17"></td>
											<td width="110" style="padding: 0px 0px 0px 40px;"></td>
										</tr>
									</table></td>
			            		</tr>
								<tr>
									<td>
										<div id="aa" style="width:750; overflow-y:no; overflow-x:auto; padding: 0px 0px 20px 0px;">
										<table width="750" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td bgcolor="#7CA5DD"><img src="../../images/statistics/brank.gif" width="1" height="2"></td>
											</tr>
											<tr>
												<td bgcolor="#CFCFCF"><table width="100%" border="0" cellspacing="1" cellpadding="1">
													<tr bgcolor="#FFFFFF">
														<td width="138" height="28" align="center" background="../../images/statistics/statis_table_bg01.gif" style="padding: 3px 0px 0px 0px;">&nbsp;</td>
			<%	
				//날짜로 통계작성시 날짜간의 차이값	
				if(rowType==0){
					for(int i=0; i<rowArrIC.size(); i++){ 
						String date = (String)rowArrIC.get(i);	//.substring(5,10);
						if(date.length()==8){
							date = date.substring(4,6)+"/"+date.substring(6,8);	
						}else{
							date = date.substring(0,4)+"/"+date.substring(4,6);
							if(dateType.equals("w")){
								date = date+"주";
							}else{
								date = date+"월";
							}
						}
			%>                        
														<td  background="../../images/statistics/statis_table_bg01.gif" style="padding: 3px 3px 0px 3px;" align="center"><%=date%></td>
			<%
					}
				}else{
					for(int i=1; i<rowArrIC.size(); i++){ 
						ICBean = (IssueCodeBean)rowArrIC.get(i);
			%>
														<td  background="../../images/statistics/statis_table_bg01.gif" style="padding: 3px 3px 0px 3px;" align="center"><%=ICBean.getIc_name()%></td>
			<%
					}
				}
			%>
														<td  background="../../images/statistics/statis_table_bg01.gif" style="padding: 3px 3px 0px 3px;" align="center"><nobr>합 계</nobr></td>
													</tr>
			<%
				ChartBean chbean = new ChartBean();
				IssueStaticBean isbean = new IssueStaticBean();
				for (int i = 1; i < colArrIC.size(); i++){
					ICBean = (IssueCodeBean) colArrIC.get(i); 
					int rowTotal = 0;
			%>
													<tr height="30" align="center" >
														<td bgcolor="#FFFFFF" style="padding: 3px 3px 0px 3px;"><nobr><strong><%=ICBean.getIc_name()%></strong></nobr></td>
			<% 
					int verCnt = 0;
					double verCntD = 0;
					if(rowType==0){j=0;}else{j=1;}	
					for (; j < rowArrIC.size(); j++){
						int cnt = 0 ;
						double cntD =0;
						
						if(rowType==0 && viewType.equals("cnt")){
							String date =	(String)rowArrIC.get(j);
							for(int l=0; l<colArrCon.size(); l++){
								chbean =(ChartBean) colArrCon.get(l);
								if(chbean.getMsyVal() == ICBean.getIc_code() && (chbean.getMsxVal()).equals(date)  ){
									cnt = chbean.getMiVal();
									verCnt += cnt;
								}
							}
						}else if(rowType==0 && viewType.equals("per")){
							String date =	(String)rowArrIC.get(j);
							for(int l=0; l<colArrAvg.size(); l++){
								isbean =(IssueStaticBean) colArrAvg.get(l);
								if(isbean.getMsyVal() == ICBean.getIc_code() && (isbean.getMsxVal()).equals(date)  ){
									cntD = isbean.getMiVal();
									verCntD += cntD;
								}
							}
						}else if(viewType.equals("per")){
							ICBean2 = (IssueCodeBean)rowArrIC.get(j);
														
							double[] arrDouble = null;
							for (int m = 0; m < colArrAvg.size(); m++){
								arrDouble = (double[])colArrAvg.get(m);
								if(ICBean.getIc_code()==Integer.parseInt((Double.toString(arrDouble[0])).substring(0,1)) && ICBean2.getIc_code()==Integer.parseInt((Double.toString(arrDouble[1])).substring(0,1)) ) {	// 세로,가로
									cntD = arrDouble[2];
									verCntD += cntD;
								}
							}
						}else{
							ICBean2 = (IssueCodeBean)rowArrIC.get(j);
														
							int[] arrInt = null;
							for (int m = 0; m < dualCodeStatic1.size(); m++){
								arrInt = (int[])dualCodeStatic1.get(m);
								if(ICBean.getIc_code()==arrInt[0] && ICBean2.getIc_code()==arrInt[1] ) {	// 세로,가로
									cnt = arrInt[2];
									verCnt += cnt;
								}
							}
						}
						if( !viewType.equals("per") ) {
			%>
														<td bgcolor="#FFFFFF" style="padding: 3px 3px 0px 3px;">
			<%
							if(rowType==0){
			%>
				  
			<%
							}else{
							}
			%>
															<%=cnt%>
														</td>
			<%
						}else{
							String cntS = su.convertFloor(cntD,1);
			%>
														<td bgcolor="#FFFFFF" style="padding: 3px 3px 0px 3px;">
			<%
							if(rowType==0){
							}else{
							}
			%>
															<%=cntS%>
														</td>
			<%
						}
					}
					rowTotal += verCnt;
					if( !viewType.equals("per") ){
			%>
														<td bgcolor="#FFFFFF" style="padding: 3px 3px 0px 3px;"><strong>
			<%
						if(rowType==0){
			%>
			<%
						}else{
			%>
			<%
						}
			%>
															<%=verCnt%>
														</strong></td>
			<%  
					}else{
						String verCntS = su.convertFloor(verCntD,1);
			%>
														<td bgcolor="#FFFFFF" style="padding: 3px 3px 0px 3px;"><strong>
			<%
						if(rowType==0){
			%>
			<%
						}else{
			%>
			<%
						}
			%>
															<%=verCntS%>
														</strong></td>
			<%
					}
			%>
													</tr>
			<%
				}
			%>
													<tr bgcolor="#FFFFFF" align="center">
														<td  height="30" background="../../images/statistics/statis_table_bg02.gif" style="padding: 3px 0px 0px 0px;">합계</td>
			<% 
				int TotalCnt =0;
				double TotalCntD =0;
				DecimalFormat df1 = new DecimalFormat();
				DecimalFormat df2 = new DecimalFormat();
				df1.applyPattern("###,###,###");
				df2.applyPattern("###");
				
				if(rowType==0){j=0;}else{j=1;}	
				for (; j < rowArrIC.size(); j++){
					int cnt = 0 ;
					double cntD = 0;
					
					if(rowType==0 && viewType.equals("cnt")){
						String date =	(String)rowArrIC.get(j);
						for(int l=0; l<colArrCon.size(); l++){
							chbean =(ChartBean) colArrCon.get(l);
							if((chbean.getMsxVal()).equals(date)){
								cnt += chbean.getMiVal();
							}
						}	
					}else if(rowType==0 && viewType.equals("per")){
						
						String date =	(String)rowArrIC.get(j);
						for(int l=0; l<colArrAvg.size(); l++){
							isbean =(IssueStaticBean) colArrAvg.get(l);
							if((isbean.getMsxVal()).equals(date)  ){
								cntD += isbean.getMiVal();
							}
						}
						TotalCntD += cntD;
					}else if(viewType.equals("per")){
						//System.out.println("");
						ICBean2 = (IssueCodeBean)rowArrIC.get(j);
						
						double[] arrDouble = null;
						for (int i = 1; i < colArrIC.size(); i++)
						{	
						ICBean = (IssueCodeBean) colArrIC.get(i); 
							for (int m = 0; m < colArrAvg.size(); m++)
							{
								arrDouble = (double[])colArrAvg.get(m);
									if(ICBean.getIc_code()==Integer.parseInt((Double.toString(arrDouble[0])).substring(0,1)) && ICBean2.getIc_code()==Integer.parseInt((Double.toString(arrDouble[1])).substring(0,1)) ) {	// 세로,가로
										cntD += arrDouble[2];
									}
							}
						}
						TotalCntD += cntD;
					}else{
						ICBean2 = (IssueCodeBean)rowArrIC.get(j);
						int[] arrInt = null;
						for (int i = 1; i < colArrIC.size(); i++)
						{
						ICBean = (IssueCodeBean) colArrIC.get(i); 
							for (int m = 0; m < dualCodeStatic1.size(); m++)
							{
								arrInt = (int[])dualCodeStatic1.get(m);
									if(ICBean.getIc_code()==arrInt[0] && ICBean2.getIc_code()==arrInt[1] ) {	// 세로,가로
										cnt += arrInt[2];
									}
							}
						}
					}
					TotalCnt += cnt;
						
					if( !viewType.equals("per") ){
			%>
			          <td background="<%=imgUrl%>statis_table_bg02.gif" style="padding: 4px 0px 0px 0px;"><strong>
			          <%if(rowType==0){ %>
					  <%}else{ %>
					  <%} %>
			          <%=cnt%></strong></td>
			<% 
					}else {
					String cntS = su.convertFloor(cntD,1);
			%>
			          <td background="<%=imgUrl%>statis_table_bg02.gif" style="padding: 4px 0px 0px 0px;"><strong>
			          <%if(rowType==0){ %>
					  <%}else{ %>
					  <%} %>
			          <%=cntS %></strong></td>
			<% 
					}
				}
				
				if( !viewType.equals("per") ) {
			%>
			          									<td background="<%=imgUrl%>statis_table_bg02.gif" style="padding: 4px 0px 0px 0px;"><strong>
			          <%if(rowType==0){ %>
					  <%}else{ %>
					  <%} %>
			          <%=TotalCnt%></strong></td>
			<%
				}else{
						TotalCntD = Math.round(TotalCntD);
			%>
														<td background="<%=imgUrl%>statis_table_bg02.gif" style="padding: 4px 0px 0px 0px;"><strong>
			          <%if(rowType==0){ %>
					  <%}else{ %>
					  <%} %>
			          <%=TotalCntD%></strong></td>
			<%
				}
			%>
			          									<td background="<%=imgUrl%>statis_table_bg02.gif" style="padding: 4px 0px 0px 0px;"></td>
													</tr>
												</table></td>
											</tr>
										</table>
			              				</div>
			              			</td>
			            		</tr>
							</table></td>
						</tr>
					</table></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<%-- 달력 테이블 --%>
<table width="162" border="0" cellspacing="0" cellpadding="0" id="calendar_conclass" style="position:absolute;display:none;">
	<tr>
		<td><img src="../../images/calendar/menu_bg_004.gif" width="162" height="2"></td>
	</tr>
	<tr>
		<td align="center" background="../../images/calendar/menu_bg_005.gif"><table width="148" border="0" cellspacing="0" cellpadding="0">
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
		<td><img src="../../images/calendar/menu_bg_006.gif" width="162" height="2"></td>
	</tr>
</table>
</form>
<iframe id="selectSite" name ="selectSite" width="0" height="0" style="display: none;" ></iframe>
</body>
</html>
<%}%>