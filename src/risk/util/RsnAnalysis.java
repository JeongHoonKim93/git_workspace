package risk.util;

import java.util.ArrayList;

import risk.json.JSONArray;
import risk.json.JSONObject;
import rsn.analysis.job.RSN_AutoAnalysis;
import rsn.analysis.vo.SuccessForm;
import java.util.HashMap;

public class RsnAnalysis {
//public ArrayList<JSONObject> makeAutoReplyRelation(String md_seq_meta, String md_seq_reply, String strJson) {
public ArrayList makeAutoReplyRelation(String text) {
    	
    	ArrayList result = new ArrayList();
    	
    	
    	JSONObject autoJobj = null;
    	JSONArray autoArry = null;
    	JSONObject autoSubJobj = null;
    	
    	String pat_seqs = "";
    	String[] ar_pat_seq = null;
    	
    	
    	RSN_AutoAnalysis  rw = null;
    	SuccessForm autoVo = null;
    	
    	try {
    		
    		rw = new RSN_AutoAnalysis();	
    		autoVo = rw.AutoAnalysis(1, text, "","KOR","");
    		autoJobj = new JSONObject(autoVo.getJsonStr());
    		
    		
    		if(!autoJobj.isNull("success_type") &&  autoJobj.getBoolean("success_type")){

    			//연관키워드
    			if(!autoJobj.isNull("3")){
    				autoArry = autoJobj.getJSONArray("3"); 
    				for(int i = 0; i < autoArry.length(); i++) {
    					autoSubJobj = autoArry.getJSONObject(i);
    					if(pat_seqs.equals("")) {
    						pat_seqs = autoSubJobj.getString("words");	
    					}else {
    						pat_seqs += "," + autoSubJobj.getString("words");
    					}
    				}
    				
    				
    				
        			ar_pat_seq = pat_seqs.split(",");
        			
        			if(ar_pat_seq != null && !ar_pat_seq[0].equals("")) {
        				for(int i =0; i<ar_pat_seq.length; i++) {
            				result.add(ar_pat_seq[i]);
            			}	
        			}
        			
    			}    			
    		}
    	}catch (Exception ex) {
    		ex.printStackTrace();
		}
    	
    	return result; 
    }

	public HashMap<String, Integer> getGroupBy(String keyCodes, String strChar){
		HashMap<String, Integer> result= new HashMap<String, Integer>();
		String[] arKeyCode = keyCodes.split(strChar);
		
		for(int i = 0; i < arKeyCode.length; i++){
			if(result.containsKey(arKeyCode[i])){
				result.put(arKeyCode[i], result.get(arKeyCode[i]) + 1);
			}else{
				result.put(arKeyCode[i], 1);
			}
		}
		
		return result;
	}
}
