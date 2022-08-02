package risk.statistics;

import java.util.ArrayList;

import risk.dashboard.dashboardSuperBean;
import risk.util.DateUtil;

public class StatisticsTwitterChartXml {

	public final boolean LINK_OK = true;
	public final boolean LINK_NO = false;
	public final boolean ACCRUE_OK = true;
	public final boolean ACCRUE_NO = false;
	
	
	
	DateUtil du = new DateUtil();
	StringBuffer resultXml = null;
	StringBuffer sb = null;
	
	//챠트 컬러
	public String[] COLOR1 = new String[]{"5098D7", "CB4C4C", "B0DB5B", "A66EDD", "F6BD0F", "0080C0", "008040", "808080", "800080","FF8040", "FFFF00","FF0080","DB9D9D"};
	public String[] COLOR2 = new String[]{"FF0080", "FFFF00", "FF8040", "800080", "808080", "008040","0080C0"};
	
	public String[] COLOR_ten = new String[]{"5098D7", "CB4C4C", "B0DB5B"};
	public String[] COLOR_ten2 = new String[]{"92B0E5", "D99ECE", "82C6D5", "DBD289"};
	public String[] COLOR_ten3 = new String[]{"2152C3", "C629B2", "00A368", "E26A12"};
	
	
	public String[] COLOR_ten4 = new String[]{"2ACAD8", "E7BF45", "95BA29", "E458A4","40B862", "B24CCB", "70A2FF", "ECAB6B","EC6E6B", "00AECE", "00AFAD", "C6A32F", "CE6676", "7F8E2B", "746DDA"};
	
	public String XmlData_1(ArrayList data) throws Exception{
	
		
		StatisticsTwitterSuperBean.WriterBean wbean = null;
		
		String result = "";
		
		if(data != null && data.size() > 0){
			sb = new StringBuffer();
		
			sb.append("<chart plotSpacePercent='45' canvasborderalpha='0' showalternatehgridcolor='0' numdivlines='0' showyaxisvalues='0' showPercentInToolTip = '1' showLabels='0' showLegend='0' canvasBorderColor='666666' canvasBorderThickness='1' plotGradientColor=' '  bgColor='FFFFFF'  showBorder='0' showValues ='1' stack100Percent = '1' showPercentValues = '0' chartTopMargin='-11' chartBottomMargin='-11' chartRightMargin='0' chartLeftMargin='0'>\n");
			
			sb.append("<categories>\n");
			
			for(int i =0; i < data.size(); i++){
				wbean = (StatisticsTwitterSuperBean.WriterBean)data.get(i);
				sb.append("<category label='"+wbean.getT_user_id()+"' />\n");
				sb.append("<vLine color='666666' thickness='0.5' dashed='1' dashLen='1' dashGap='1' alpha='45'/>\n");
			}
			
			
			
			sb.append("</categories>\n");
			
			sb.append("<dataset seriesName='긍정' color='"+COLOR_ten[0]+"' >\n");
			for(int i =0; i < data.size(); i++){
				wbean = (StatisticsTwitterSuperBean.WriterBean)data.get(i);
				sb.append("<set value='"+(wbean.getPositive().equals("0") ? "" : wbean.getPositive())+"' link='javascript:showTwitterList(&apos;"+wbean.getT_user_id()+"&apos;, &apos;"+"1"+"&apos;)' />\n");
			}
			sb.append("</dataset>\n");
			
			sb.append("<dataset seriesName='부정' color='"+COLOR_ten[1]+"' >\n");
			for(int i =0; i < data.size(); i++){
				wbean = (StatisticsTwitterSuperBean.WriterBean)data.get(i);
				sb.append("<set value='"+(wbean.getNegative().equals("0") ? "" : wbean.getNegative())+"' link='javascript:showTwitterList(&apos;"+wbean.getT_user_id()+"&apos;, &apos;"+"2"+"&apos;)' />\n");
			}
			sb.append("</dataset>\n");
			
			sb.append("<dataset seriesName='중립' color='"+COLOR_ten[2]+"' >\n");
			for(int i =0; i < data.size(); i++){
				wbean = (StatisticsTwitterSuperBean.WriterBean)data.get(i);
				sb.append("<set value='"+(wbean.getNeutral().equals("0") ? "" : wbean.getNeutral())+"' link='javascript:showTwitterList(&apos;"+wbean.getT_user_id()+"&apos;, &apos;"+"3"+"&apos;)' />\n");
			}
			sb.append("</dataset>\n");
			
			sb.append("<styles>\n");      
			sb.append("<definition>\n");         
			sb.append("<style name='myValuesFont' type='font' size='11' color='FFFFFF' bold='1'/>\n");
			sb.append("<style name='myLabelsFont' type='font' size='11' color='666666' bold='1'/>\n");
			sb.append("<style name='myLegendFont' type='font' size='12' color='666666' bold='1'/>\n");
			sb.append("</definition>\n");
			
			sb.append("<application>\n");         
			sb.append("<apply toObject='DataValues' styles='myValuesFont' />\n");
			sb.append("<apply toObject='DataLabels' styles='myLabelsFont' />\n");
			sb.append("<apply toObject='Legend' styles='myLegendFont' />\n");
			sb.append("</application>\n");
			
			sb.append("</styles>\n");
			
			sb.append("</chart> \n");
			
			result = sb.toString();
			
			//System.out.println(result);
		}
		
		return result.replaceAll("\n","").replaceAll((new Character((char)13)).toString(),"").replaceAll((new Character((char)9)).toString(),"").replaceAll((new Character((char)11)).toString(),"");
	}
	
	public String XmlData_2(ArrayList data, String[] dateMaster) throws Exception{
		
		String result = "";
		//StatisticsTwitterSuperBean.WriterBean data = null;
		String[] mBean = null;
		
		int chkCnt = 0; 
		if (data != null && data.size() > 0){
			
			sb = new StringBuffer();
			
			sb.append("<chart legendIconScale='1' legendPosition='right' lineThickness='2' anchorBorderThickness='5' animation='1' showLegend='1' showLabels='1' BorderColor='FFFFFF' bgColor='FFFFFF' numdivlines='5' showValues='1' anchorRadius='3' anchorBgAlpha='50' numVDivLines='23' showAlternateVGridColor='1' alternateVGridAlpha='3'  chartBottomMargin='5' chartTopMargin='5' chartRightMargin='5' chartLeftMargin='5'>\n");
			
			sb.append("<categories>\n");
			for(int i = 0; i < dateMaster.length; i++){
				sb.append("<category label='"+ du.getDate(dateMaster[i],"MM/dd")+"' />\n");
			}
			sb.append("</categories>\n");
			
			String[] arr_user = (String[])data.get(0);
			
			for(int k = 1; k < arr_user.length; k++){
			
				sb.append("<dataset seriesName='No."+k+"  @"+arr_user[k]+"' color='"+COLOR1[k-1]+"' showValues='0'>\n");
				for(int i = 0; i < dateMaster.length; i++){
					chkCnt = 0;
					for(int j=1; j < data.size(); j++){
						mBean = (String[])data.get(j); 
						if(dateMaster[i].equals(mBean[0])){
							chkCnt++;
							sb.append("<set value='"+mBean[k]+"' link='javascript:showTwitterList(&apos;"+arr_user[k]+"&apos;, &apos;"+""+"&apos;, &apos;"+mBean[0]+"&apos;)'/>\n");
							break;
						}
					}
					if(chkCnt == 0){
						sb.append("<set value='0' />\n");
					}	
				}
				sb.append("</dataset>\n");
			}
			
			sb.append("<styles>\n");      
			sb.append("<definition>\n");         
			sb.append("<style name='myLegendFont' type='font' size='12' color='666666' bold='1'/>\n");
			sb.append("<style name='myValuesFont' type='font' size='11' color='666666' bold='0'/>\n");
			sb.append("</definition>\n");
			sb.append("<application>\n");         
			sb.append("<apply toObject='Legend' styles='myLegendFont' />\n");
			sb.append("<apply toObject='DataValues' styles='myValuesFont' />\n");
			sb.append("<apply toObject='DataLabels' styles='myValuesFont' />\n");
			sb.append("</application>\n");
			sb.append("</styles>\n");
			
			
			sb.append("</chart>\n");
			
			result = sb.toString();
			
			System.out.println(result);
		}
		return result.replaceAll("\n","").replaceAll((new Character((char)13)).toString(),"").replaceAll((new Character((char)9)).toString(),"").replaceAll((new Character((char)11)).toString(),"");
	}
	
	public String demo4() throws Exception{
		
		String result = "";
		sb = new StringBuffer();
	
		
		sb.append("<chart legendPosition='RIGHT' canvasBorderColor='666666' canvasBorderThickness='1' plotGradientColor=' '  bgColor='FFFFFF'  showBorder='0' showValues='1' stack100Percent='1' showPercentValues='1' chartTopMargin='0' chartBottomMargin='0' chartRightMargin='5' chartLeftMargin='0'>\n");
		
		sb.append("<categories>\n");
		sb.append("<category label='RT한 사람' />\n");
		sb.append("<category label='고유노출' />\n");
		sb.append("<category label='총 노출' />\n");
		sb.append("</categories>\n");
		
		sb.append("<dataset seriesName='상위10명' color='5098D7' >\n");
		sb.append("<set value='1.7' />\n");
		sb.append("<set value='29.6'/>\n");
		sb.append("<set value='1.6' />\n");
		sb.append("</dataset>\n");
		
		sb.append("<dataset seriesName='나머지' color='CB4C4C' >\n");
		sb.append("<set value='98.3' />\n");
		sb.append("<set value='70.4' />\n");
		sb.append("<set value='98.4'/>\n");
		sb.append("</dataset>\n");
		
		sb.append("<styles>\n");      
		sb.append("<definition>\n");         
		sb.append("<style name='myValuesFont' type='font' size='11' color='FFFFFF' bold='1'/>\n");
		sb.append("<style name='myLabelsFont' type='font' size='11' color='666666' bold='1'/>\n");
		sb.append("<style name='myLegendFont' type='font' size='12' color='666666' bold='1'/>\n");
		sb.append("</definition>\n");
		
		sb.append("<application>\n");         
		sb.append("<apply toObject='DataValues' styles='myValuesFont' />\n");
		sb.append("<apply toObject='DataLabels' styles='myLabelsFont' />\n");
		sb.append("<apply toObject='Legend' styles='myLegendFont' />\n");
		sb.append("</application>\n");
		
		sb.append("</styles>\n");
		
		
		
		
		sb.append("</chart> \n");
		
		result = sb.toString();
		
		return result.replaceAll("\n","").replaceAll((new Character((char)13)).toString(),"").replaceAll((new Character((char)9)).toString(),"").replaceAll((new Character((char)11)).toString(),"");
	}
	
	
	public String test() throws Exception{
	
		String result = "";
		sb = new StringBuffer();
	
		//sb.append("<chart showPercentInToolTip = '1' showLabels='0' showLegend='0' canvasBorderColor='666666' canvasBorderThickness='1' plotGradientColor=' '  bgColor='FFFFFF'  showBorder='0' showValues ='1' stack100Percent = '1' showPercentValues = '0' chartTopMargin='50' chartBottomMargin='0' chartRightMargin='0' chartLeftMargin='0'>\n");
		sb.append("<chart plotBorderDashGap='30' canvasborderalpha='0' showalternatehgridcolor='0' numdivlines='0' showyaxisvalues='0' showPercentInToolTip = '1' showLabels='0' showLegend='0' canvasBorderColor='666666' canvasBorderThickness='1' plotGradientColor=' '  bgColor='FFFFFF'  showBorder='0' showValues ='1' stack100Percent = '1' showPercentValues = '0' chartTopMargin='50' chartBottomMargin='0' chartRightMargin='0' chartLeftMargin='0'>\n");
		sb.append("<categories>\n");
		sb.append("<category label='pyein2' />\n");
		sb.append("<category label='saramimeonjeoda' />\n");
		sb.append("<category label='kor_Heinrich' />\n");
		sb.append("<category label='xx_ioi_xx' />\n");
		sb.append("<category label='mediatodaybot' />\n");
		sb.append("<category label='qweenofright' />\n");
		sb.append("<category label='appstark' />\n");
		sb.append("<category label='tb7yagu' />\n");
		sb.append("<category label='heinfo123' />\n");
		sb.append("<category label='qnehdtks1234' />\n");
		sb.append("</categories>\n");
		sb.append("<dataset seriesName='긍정' color='5098D7' >\n");
		sb.append("<set value='' />\n");
		sb.append("<set value='' />\n");
		sb.append("<set value='' />\n");
		sb.append("<set value='' />\n");
		sb.append("<set value='' />\n");
		sb.append("<set value='' />\n");
		sb.append("<set value='' />\n");
		sb.append("<set value='' />\n");
		sb.append("<set value='' />\n");
		sb.append("<set value='' />\n");
		sb.append("</dataset>\n");
		sb.append("<dataset seriesName='부정' color='CB4C4C' >\n");
		sb.append("<set value='71' />\n");
		sb.append("<set value='' />\n");
		sb.append("<set value='' />\n");
		sb.append("<set value='' />\n");
		sb.append("<set value='' />\n");
		sb.append("<set value='' />\n");
		sb.append("<set value='1' />\n");
		sb.append("<set value='1' />\n");
		sb.append("<set value='1' />\n");
		sb.append("<set value='' />\n");
		sb.append("</dataset>\n");
		sb.append("<dataset seriesName='중립' color='B0DB5B' >\n");
		sb.append("<set value='' />\n");
		sb.append("<set value='4' />\n");
		sb.append("<set value='4' />\n");
		sb.append("<set value='3' />\n");
		sb.append("<set value='2' />\n");
		sb.append("<set value='1' />\n");
		sb.append("<set value='' />\n");
		sb.append("<set value='' />\n");
		sb.append("<set value='' />\n");
		sb.append("<set value='1' />\n");
		sb.append("</dataset>\n");
		sb.append("<styles>\n");
		sb.append("<definition>\n");
		sb.append("<style name='myValuesFont' type='font' size='11' color='FFFFFF' bold='1'/>\n");
		sb.append("<style name='myLabelsFont' type='font' size='11' color='666666' bold='1'/>\n");
		sb.append("<style name='myLegendFont' type='font' size='12' color='666666' bold='1'/>\n");
		
		sb.append("</definition>\n");
		sb.append("<application>\n");
		sb.append("<apply toObject='DataValues' styles='myValuesFont' />\n");
		sb.append("<apply toObject='DataLabels' styles='myLabelsFont' />\n");
		sb.append("<apply toObject='Legend' styles='myLegendFont' />\n");
		
		sb.append("</application>\n");
		sb.append("</styles>\n");
		sb.append("</chart> \n");
		
		result = sb.toString();
		
		return result.replaceAll("\n","").replaceAll((new Character((char)13)).toString(),"").replaceAll((new Character((char)9)).toString(),"").replaceAll((new Character((char)11)).toString(),"");
	}
}


