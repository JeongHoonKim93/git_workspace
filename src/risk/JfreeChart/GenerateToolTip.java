package risk.JfreeChart;

import org.jfree.chart.labels.CategoryToolTipGenerator;
import org.jfree.data.category.CategoryDataset;

/**
 * CatogoryDataset에 해당하는 tooltip Generator
 * @author lee ho yong
 *
 */
public class GenerateToolTip implements CategoryToolTipGenerator{
	
	public String generateToolTip(CategoryDataset dataset, int series, int category) {
		String result = "";
		
		Comparable seriesKey = dataset.getRowKey(series);
	    Comparable categoryKey = dataset.getColumnKey(category);
	    if(seriesKey.equals("")){
	    	result = categoryKey+" : "+String.valueOf(dataset.getValue(seriesKey, categoryKey).intValue());
	    }else{
	    	result = seriesKey+" : "+String.valueOf(dataset.getValue(seriesKey, categoryKey).intValue());
	    }
	    
		return result;
	}
	
}