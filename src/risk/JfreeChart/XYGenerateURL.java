package risk.JfreeChart;

import org.jfree.chart.urls.PieURLGenerator;
import org.jfree.chart.urls.XYURLGenerator;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.xy.XYDataset;

public class XYGenerateURL implements XYURLGenerator{
	public String dateType = "";
	public String type = "";
	public XYGenerateURL(String dateType, String type){
		this.dateType = dateType;
		this.type = type;
	}
	
	public String generateURL(XYDataset dataset, int series, int item) {
		String url = "";
	    Comparable seriesKey = dataset.getSeriesKey(series);
	    Comparable categoryKey = (Comparable)dataset.getX(series,item);
	    
	    String company = "";
	    if(dataset.getSeriesCount() > 0){
		    for(int i = 0; i < dataset.getSeriesCount(); i++){
		    	if(company.equals("")){
		    		company = (String)dataset.getSeriesKey(i);
		    	}else{
		    		company += ","+(String)dataset.getSeriesKey(i);
		    	}
		    }
	    }
	    url = "javascript:PopLinkList('"+dateType+"', '"+type+"', '"+categoryKey+"', '', '"+company+"');";
	    return url;
	}
	
}