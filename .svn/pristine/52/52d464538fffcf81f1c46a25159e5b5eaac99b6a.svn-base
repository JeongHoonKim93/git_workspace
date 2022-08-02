package risk.JfreeChart;

import org.jfree.data.xy.XYDataset;
import org.jfree.chart.labels.XYToolTipGenerator;

public class XYGenerateLabel implements XYToolTipGenerator{	
	
	public String generateToolTip(XYDataset dataset, int series, int item) {
		String result = "";
		
	    Comparable seriesKey = dataset.getSeriesKey(series);
	    Comparable categoryKey = (Comparable)dataset.getYValue(series,item);

	    result = seriesKey+" : "+categoryKey;
	    return result;
	}
	
}
