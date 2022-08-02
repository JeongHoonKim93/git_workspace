package risk.report;

import java.util.*;

public class MapComparator  implements Comparator {
    String key;
    
    public MapComparator(String key) {
        this.key = key;
    }

	public int compare(Object first, Object second) {
		Map f_map = (Map) first;
		Map s_map = (Map) second;
		if(Integer.parseInt(f_map.get(key).toString()) >= Integer.parseInt(s_map.get(key).toString())) {
			return -1;
		} else {
			return 1;
		}
	}

}
