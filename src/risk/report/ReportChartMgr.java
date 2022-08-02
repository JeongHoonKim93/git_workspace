package risk.report;

import java.awt.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.List;

import org.jfree.chart.*;
import org.jfree.chart.annotations.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.labels.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.category.*;
import org.jfree.chart.renderer.xy.*;
import org.jfree.chart.title.*;
import org.jfree.data.category.*;
import org.jfree.data.time.*;
import org.jfree.data.xy.*;
import org.jfree.ui.*;
import org.jfree.util.*;

import risk.util.*;
import risk.util.Log;

public class ReportChartMgr {

	private GradientPaint gp_dataLine = new GradientPaint(0.0f, 0.0f, new Color(182, 0, 0),     0.0f, 0.0f, new Color(182, 0, 0));
	private GradientPaint gp_avgLine = new GradientPaint(0.0f, 0.0f, new Color(86, 147, 202),     0.0f, 0.0f, new Color(86, 147, 202));
	private GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, new Color(165, 165, 165),     0.0f, 0.0f, new Color(165, 165, 165)); // 언론
	private GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, new Color(157, 195, 230), 0.0f, 0.0f, new Color(157, 195, 230)); // 트위터
	private GradientPaint gp2 = new GradientPaint(0.0f, 0.0f, new Color(68, 114, 196),     0.0f, 0.0f, new Color(68, 114, 196)); // 페이스북
	private GradientPaint gp3 = new GradientPaint(0.0f, 0.0f, new Color(112, 173, 71),     0.0f, 0.0f, new Color(112, 173, 71)); // 블로그
	private GradientPaint gp4 = new GradientPaint(0.0f, 0.0f, new Color(237, 125, 49),     0.0f, 0.0f, new Color(237, 127, 49)); // 카페
	private GradientPaint gp5 = new GradientPaint(0.0f, 0.0f, new Color(255, 192, 0),     0.0f, 0.0f, new Color(255, 192, 0)); // 커뮤니티
	private GradientPaint gp6 = new GradientPaint(0.0f, 0.0f, new Color(112, 48, 160),     0.0f, 0.0f, new Color(112, 48, 160)); // 기타
	
	// categoryType 1: 날짜별 키워드,  2: 날짜별 출처
		public CategoryTableXYDataset setDateLineDataset(int categoryType, List chartData)
		{
			CategoryTableXYDataset dataset = new CategoryTableXYDataset();	
			Iterator it = null;			
			HashMap chartDataHm = new HashMap();
			DateUtil du = new DateUtil();
			org.jfree.data.time.Day day = null;
	        org.jfree.data.time.Week week = null;
	        org.jfree.data.time.Month month = null;
	        org.jfree.data.time.Hour hour = null;
			
			if(categoryType ==1){			
			
				if(chartData!=null)
				{
					it = chartData.iterator();				
					while(it.hasNext())
					{
						 chartDataHm = new HashMap();
						 chartDataHm = (HashMap)it.next();
						 
						 day = new Day(du.getTime((String)chartDataHm.get("1")));							 					
						 if(chartDataHm.get("2")!=null && !chartDataHm.get("2").equals("0"))dataset.add(day.getFirstMillisecond(), Integer.parseInt((String)chartDataHm.get("2")), (String)chartDataHm.get("3"));
						 if(chartDataHm.get("4")!=null && !chartDataHm.get("4").equals("0"))dataset.add(day.getFirstMillisecond(), new Double((String)chartDataHm.get("4")).doubleValue(), (String)chartDataHm.get("5"));
						 if(chartDataHm.get("6")!=null && !chartDataHm.get("6").equals("0"))dataset.add(day.getFirstMillisecond(), new Double((String)chartDataHm.get("6")).doubleValue(), (String)chartDataHm.get("7"));
						 if(chartDataHm.get("8")!=null && !chartDataHm.get("8").equals("0"))dataset.add(day.getFirstMillisecond(), new Double((String)chartDataHm.get("8")).doubleValue(), (String)chartDataHm.get("9"));
						 if(chartDataHm.get("10")!=null && !chartDataHm.get("10").equals("0")) dataset.add(day.getFirstMillisecond(), new Double((String)chartDataHm.get("10")).doubleValue(), (String)chartDataHm.get("11"));
						 if(chartDataHm.get("12")!=null && !chartDataHm.get("12").equals("0")) dataset.add(day.getFirstMillisecond(), new Double((String)chartDataHm.get("12")).doubleValue(), (String)chartDataHm.get("13"));
						 if(chartDataHm.get("14")!=null && !chartDataHm.get("14").equals("0")) dataset.add(day.getFirstMillisecond(), new Double((String)chartDataHm.get("14")).doubleValue(), (String)chartDataHm.get("15"));
					}									
				}
				
			}else if(categoryType ==2){
				if(chartData!=null)
				{
					it = chartData.iterator();				
					while(it.hasNext())
					{
						 chartDataHm = new HashMap();
						 chartDataHm = (HashMap)it.next();
						 
						 day = new Day(du.getTime((String)chartDataHm.get("CATEGORY")));							 					
						 if(chartDataHm.get("CNT1")!=null && chartDataHm.get("NAME1")!=null)dataset.add(day.getFirstMillisecond(), Integer.parseInt((String)chartDataHm.get("CNT1")), (String)chartDataHm.get("NAME1"));
						 if(chartDataHm.get("CNT2")!=null && chartDataHm.get("NAME2")!=null)dataset.add(day.getFirstMillisecond(), Integer.parseInt((String)chartDataHm.get("CNT2")), (String)chartDataHm.get("NAME2"));
						 if(chartDataHm.get("CNT3")!=null && chartDataHm.get("NAME3")!=null)dataset.add(day.getFirstMillisecond(), Integer.parseInt((String)chartDataHm.get("CNT3")), (String)chartDataHm.get("NAME3"));
						 if(chartDataHm.get("CNT4")!=null && chartDataHm.get("NAME4")!=null)dataset.add(day.getFirstMillisecond(), Integer.parseInt((String)chartDataHm.get("CNT4")), (String)chartDataHm.get("NAME4"));
						 if(chartDataHm.get("CNT5")!=null && chartDataHm.get("NAME5")!=null)dataset.add(day.getFirstMillisecond(), Integer.parseInt((String)chartDataHm.get("CNT5")), (String)chartDataHm.get("NAME5"));
						 if(chartDataHm.get("CNT6")!=null && chartDataHm.get("NAME6")!=null)dataset.add(day.getFirstMillisecond(), Integer.parseInt((String)chartDataHm.get("CNT6")), (String)chartDataHm.get("NAME6"));
					}									
				}
			}else if(categoryType ==3){
				if(chartData!=null)
				{
					it = chartData.iterator();
					while(it.hasNext())
					{
						 chartDataHm = new HashMap();
						 chartDataHm = (HashMap)it.next();
						
						 day = new Day(du.getTime((String)chartDataHm.get("CATEGORY")));
						 if(chartDataHm.get("CNT")!=null){
							 dataset.add(day.getFirstMillisecond(), Integer.parseInt((String)chartDataHm.get("CNT")), "CNT");
							 dataset.add(day.getFirstMillisecond(), Integer.parseInt((String)chartDataHm.get("AVG")), "AVG");
						 }
					}									
				}

			}else if(categoryType ==4){
				if(chartData.size() > 0)
				{
					for(int i=0; i < chartData.size() ; i++){
						String[] data = (String[])chartData.get(i);

						day = new Day(du.getTime(data[0]));
						dataset.add(day.getFirstMillisecond(), Integer.parseInt(data[data.length-1]), "");
					}
				}
			}
			
			return	dataset;	
		}
		// categoryType 1: 출처별 성향
		public DefaultCategoryDataset setStackBarDataSet(int categoryType, List chartData)
		{
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();	
			Iterator it = null;			
			HashMap chartDataHm = new HashMap();		
			
			if(categoryType == 1){
				
				if(chartData!=null)
				{
					it = chartData.iterator();
					while(it.hasNext()){
						chartDataHm = (HashMap) it.next();
						dataset.addValue(new Double((String)chartDataHm.get("SG_CNT")).doubleValue(), (String)chartDataHm.get("SG_SEQ"), "");
						//dataset.addValue(new Double("10").doubleValue(), (String)chartDataHm.get("SG_SEQ"), "");
					}
				}
			}
			
			return	dataset;	
		}
	/*
	 * SK 그룹
	 * 2017.01.19
	 * 라인 차트 
	 * 날짜별 정보량 구하기
	 * 
	 */
	public String makeLineChart(int categoryType, List chartData, String chartName, String filePath, String urlPath, int width, int height)
	{
		String pullPath = "";
		String fullChartName = "";
		String lastSiteName = "";
		HashMap Hm = new HashMap();
		DateUtil du = new DateUtil();
		Iterator it = null;				
		String date;
		int term;
		long peorid;
						
		DateAxis xAxis = null; 
		NumberAxis yAxis = null;
		XYPlot plot = null;
		XYLineAndShapeRenderer renderer = null;
		JFreeChart chart = null;
		
		CategoryTableXYDataset dataset = new CategoryTableXYDataset();
		
		try{			
			fullChartName = chartName+du.getCurrentDate("yyyyMMddHHmmss")+".png";				
			dataset = setDateLineDataset(categoryType,chartData);
			//라인 객체 생성및 속성지정
			renderer = new XYLineAndShapeRenderer();			
			renderer.setSeriesStroke(0,new BasicStroke(2)); //선라인
			renderer.setSeriesStroke(1,new BasicStroke(3)); //선라인
			renderer.setSeriesPaint(0, gp_dataLine,true); //선 색깔
			renderer.setSeriesPaint(1, gp_avgLine,true); //선 색깔
			//renderer.setSeriesShape(0, new Ellipse2D.Double(-3.0,-3.0,6.0,6.0));
			renderer.setSeriesShape(0, ShapeUtilities.createDiamond(4));
			renderer.setSeriesShapesVisible(0, true); //꼭지점 생성여부
			renderer.setSeriesShapesVisible(1, false); //꼭지점 생성여부
			
			//renderer.setBaseItemLabelsVisible(true);
			//renderer.setSeriesItemLabelGenerator(0, new StandardXYItemLabelGenerator());
			//renderer.setSeriesItemLabelFont(0, new Font("맑은 고딕", 0, 10));
			
			//X축객체 생성 및 속성지정
			DateAxis xAsis = new DateAxis();
			xAsis.setTickMarksVisible(false);
			xAsis.setTickLabelFont(new Font("맑은 고딕", 0, 10)) ;//X축 폰트 , 사이즈
			//xAsis.setDateFormatOverride(new SimpleDateFormat("M/d"));	//X축 Date 출력방식 설정
			DateTickUnit tick = new DateTickUnit(DateTickUnit.DAY, 1, (new SimpleDateFormat("M/d")));
			xAsis.setTickUnit(tick);
			
		    //Y축 객체  생성 및 속성지정
			NumberAxis yAsis = new NumberAxis();			   	
			yAsis.setTickLabelFont(new Font("맑은 고딕", 0, 10)); //Y축 폰트 , 사이즈
			yAsis.setStandardTickUnits(NumberAxis.createIntegerTickUnits()); //Y축 소수점 없이 ..
			yAsis.setTickMarkOutsideLength(4);
			yAsis.setUpperMargin(0.25);
			yAsis.setAxisLineVisible(false);
			yAsis.setTickMarksVisible(false);
			NumberFormat nf = NumberFormat.getInstance();
			yAsis.setNumberFormatOverride(nf);
			
			//XYPlot객체 생성 및 속성 지정
			plot = new XYPlot(dataset, xAsis, yAsis, renderer);
			plot.setOutlineVisible(false);
			plot.setRangeGridlinesVisible(true);
			plot.setDomainGridlinesVisible(false);
			plot.setNoDataMessage("No Data");			
			plot.setRangeZeroBaselineVisible(true);
			plot.setRangeGridlineStroke(new BasicStroke(1));
			plot.setRangeGridlinePaint(new Color(213,213,213));
			plot.setBackgroundPaint(new Color(248,248,248));
			// 마지막 값만 내놓으라는 SK!!!!!!!!!!!!!!!!!!!!!!
			
			StandardXYItemLabelGenerator itemLabel = new StandardXYItemLabelGenerator();
			double yp = Double.parseDouble(itemLabel.generateLabel(dataset, 0, dataset.getItemCount()-1).replaceAll(",", ""));
			XYPointerAnnotation xypointerannotation = new XYPointerAnnotation(itemLabel.generateLabel(dataset, 0, dataset.getItemCount()-1), dataset.getXValue(0, dataset.getItemCount()-1), yp, -1.9561944901923448D);
			xypointerannotation.setArrowLength(0);
			xypointerannotation.setBaseRadius(9);
			xypointerannotation.setArrowPaint(new Color(248,248,248));
			xypointerannotation.setFont(new Font("맑은 고딕", Font.BOLD, 14));
			plot.addAnnotation(xypointerannotation);
			// SK...
			
			//차트 객체 생성 및 속성지정
		    chart = new JFreeChart(plot);
		    chart.setBackgroundPaint(new Color(248,248,248)); //차트 백그라운 색깔
			chart.setBorderVisible(false); //차트 보더 보임 여부
			/*TextTitle title = new TextTitle();
			title.setPosition(RectangleEdge.BOTTOM );
			title.setText("<최근 10일간 SK관련 Buzz량>");
			title.setFont(new Font("맑은 고딕", Font.BOLD, 14));
			chart.setTitle(title);*/
			chart.removeLegend();	

			//차트 만들기
			if(!new File(filePath).isDirectory()) new File(filePath).mkdirs();
			ChartUtilities.saveChartAsPNG(new File(filePath+fullChartName),chart,width,height);
			pullPath = urlPath+fullChartName;
			
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			
		}
		return pullPath;
	}
	
	//StackBar
	public String makeStackBar(int categoryType, List chartData, String chartName, String filePath, String urlPath, int width, int height, String vertical, String percentage)
	{
		String pullPath = "";
		String fullChartName = "";
		DateUtil du = new DateUtil();	
		CategoryPlot plot = null;
		JFreeChart chart = null;
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        		
		try{			
			fullChartName = chartName+du.getCurrentDate("yyyyMMddHHmmss")+".png";		
			dataset = setStackBarDataSet(categoryType, chartData);						
			//
			
			//X축객체 생성 및 속성지정
			CategoryAxis xAsis = new CategoryAxis();	
			xAsis.setAxisLineVisible(false);
			xAsis.setTickMarksVisible(false);
			//Y축 객체  생성 및 속성지정
			NumberAxis yAsis = new NumberAxis();	
			yAsis.setVisible(false);
			yAsis.setTickMarksVisible(false);
			//yAsis.setRange(0, 100);
			//yAsis.setAutoRange(true);
			//바 객체 생성 및 속성 지정
			BarRenderer  barRenderer = new StackedBarRenderer();			
			for(int i=0; i < dataset.getRowCount(); i++){
				barRenderer.setSeriesStroke(i, new BasicStroke(1)); //선두깨
				GradientPaint tmpPaint = gp0;
				if("42".equals(dataset.getRowKey(i).toString())){
					tmpPaint = gp0;
				}else if("17".equals(dataset.getRowKey(i).toString())){
					tmpPaint = gp1;
				}else if("18".equals(dataset.getRowKey(i).toString())){
					tmpPaint = gp3;
				}else if("19".equals(dataset.getRowKey(i).toString())){
					tmpPaint = gp5;
				}else if("20".equals(dataset.getRowKey(i).toString())){
					tmpPaint = gp6;
				}else if("25".equals(dataset.getRowKey(i).toString())){
					tmpPaint = gp4;
				}else if("43".equals(dataset.getRowKey(i).toString())){
					tmpPaint = gp2;
				}
				
				barRenderer.setSeriesPaint(i, tmpPaint); //선 색깔
				
			}				
			//barRenderer.setItemMargin(-0.35);
			//barRenderer.setMaximumBarWidth(1.2);
			barRenderer.setShadowVisible(false);
			barRenderer.setBarPainter(new StandardBarPainter());
		    //barRenderer.setAutoPopulateSeriesStroke(false);
		    barRenderer.setBaseItemLabelFont(new Font("맑은고딕",0,11));
		    barRenderer.setBaseItemLabelsVisible(true);
		    barRenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			//XYPlot객체 생성 및 속성 지정
			plot = new CategoryPlot();		
			plot.setDataset(0, dataset);		
			plot.setRenderer(0, barRenderer);			
			plot.setDomainAxis(0, xAsis);
			plot.setRangeAxis(1, yAsis);
			plot.mapDatasetToRangeAxis(0, 1);					
			//plot.setAxisOffset(offset);
			plot.setOutlineVisible(false);
			//plot.setBackgroundAlpha(0);
			//plot.setBackgroundPaint(Color.WHITE); 
			plot.setRangeGridlinesVisible(false);
			plot.setDomainGridlinesVisible(false);
			//plot.setRangeGridlinePaint(Color.blue);
			//plot.setDomainGridlinePaint(Color.blue);
			plot.setNoDataMessage("No Data");			
			plot.setRangeZeroBaselineVisible(false);
			plot.setDomainGridlinesVisible(false);
			plot.setRangeGridlinesVisible(false);
			//plot.setAxisOffset(new RectangleInsets(0,0,0,0));
			if(vertical.equals("")){
				plot.setOrientation(PlotOrientation.HORIZONTAL);
			}else{
				plot.setOrientation(PlotOrientation.VERTICAL);
			}
			
			//차트 객체 생성 및 속성지정
		    chart = new JFreeChart(plot);
		    chart.setBackgroundPaint(Color.WHITE); //차트 백그라운 색깔
			chart.setBorderVisible(false); //차트 보더 보임 여부
			chart.removeLegend();
			chart.setPadding(new RectangleInsets(-5.0,-21.5,-5.0,-40.0));
			//chart.setBorderStroke(new BasicStroke(0));
			//chart.setBorderPaint(new Color(225,225,225));
			//chart.setBorderStroke(new BasicStroke(3));						
				
			//차트 만들기
			if(!new File(filePath).isDirectory()) new File(filePath).mkdirs();
			ChartUtilities.saveChartAsPNG(new File(filePath+fullChartName),chart,width,height);
			pullPath = urlPath+fullChartName;
			
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			
		}       
		return pullPath;
	}
}
