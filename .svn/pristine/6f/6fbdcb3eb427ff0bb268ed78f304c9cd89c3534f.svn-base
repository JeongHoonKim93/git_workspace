package risk.demon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.zip.GZIPInputStream;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import risk.DBconn.DBconn;
import risk.json.JSONArray;
import risk.json.JSONObject;
import risk.util.DateUtil;
import risk.util.Log;
import risk.util.PersonalInfoUtil;
import risk.util.StringUtil;
import rsn.analysis.job.RSN_AutoAnalysis;
import rsn.analysis.vo.SuccessForm;


public class HOTELSHILLA_01_migrationData_v3{
	
	String className = this.getClass().getSimpleName();
	DBconn dbconn1 = null;
	
	long preUseMemory;
	long aftUseMemory;
	
	//PROPERTY
	String apiUrl = "";
	String testApiUrl = "";
	String telegramUrl = "";
	String pastApiUrl = ""; //과거API URL
	String pastApiTableNum = "892"; // 과거데이터 Table번호
	boolean useSame;//유사기능 실행여부
	String[] useSameChanel = null;//유사기능 실행 채널
	boolean useCatagory;//업무분류 키워드 실행여부
	boolean useCatagoryReply;//댓글에 대한 업무분류 키워드 실행여부
	boolean useDataTans;//데이터 공급 실행여부
	boolean useAutoAnalysis_meta;//메인 자동분석 실행여부
	boolean useAutoAnalysis_warning;//워닝 자동분석 실행여부
	boolean useAutoAnalysis_section;//영역 지정 자동분석 실행여부
	boolean useAutoAnalysis_reply;//댓글 자동분석 실행여부
	boolean useAutoAnalysis_top;//포탈탑 자동분석 실행여부
	boolean useMemoryCheck; //메모리 체크
	boolean usePersonalInfo;//개인정보 아스타처리 유무
	boolean useTelegram;//텔레그램 발송 유무
	boolean usePastData;//과거데이터 전송 유무
	
	
	
	//마스터 선언
	JSONArray jProperty = null;			/*설정정보*/
	JSONArray jChannel = null;			/*채널정보*/
	JSONArray jSite = null;				/*사이트*/
	JSONArray jTierSite = null;			/*티어사이트*/
	JSONArray jExSite = null;			/*제외사이트*/
	JSONArray jExSiteUrl = null;		/*제외URL*/
	JSONArray jKeyword = null;			/*키워드*/
	JSONArray jKeyword_category = null;	/*업무뷴류 키워드*/
	JSONArray jKeyword_top = null;	    /*포탈탑 키워드*/
	JSONArray jAllExKeyword = null;		/*전체제외키우드*/
	JSONArray[] jChannelBox = null;		/*채널박스*/
	JSONArray jTelegram = null;			/*텔레그램 설정정보*/
	

	//프리페어 스테이트먼트 선언
	PreparedStatement PS_INSERT_META_SEQ = null;
	PreparedStatement PS_INSERT_META = null;
	PreparedStatement PS_INSERT_REPLY = null;
	PreparedStatement PS_INSERT_TOP = null;
	PreparedStatement PS_INSERT_IDX = null;
	PreparedStatement PS_INSERT_EXCEPTION_META = null;
	PreparedStatement PS_INSERT_EXCEPTION_IDX = null;
	PreparedStatement PS_UPDATE_CHANNEL = null;
	PreparedStatement PS_SELECT_SAME_FILTER = null;
	PreparedStatement PS_INSERT_SAME_FILTER = null;
	PreparedStatement PS_UPDATE_SAME_FILTER = null;
	PreparedStatement PS_UPDATE_META = null;
	PreparedStatement PS_INSERT_TRANS_DATA = null;
	PreparedStatement PS_INSERT_IDX_CATEGORY = null;
	PreparedStatement PS_SELECT_META_SEQ_REPLY = null;
	PreparedStatement PS_SELECT_META_SEQ_MD_SEQ = null;
	PreparedStatement PS_SELECT_TOP_URL = null;
	PreparedStatement PS_UPDATE_META_ANA_KEYWORD = null;
	PreparedStatement PS_INSERT_META_ANA_KEYWORD = null;
	PreparedStatement PS_UPDATE_META_ANA_KEYWORD_CATEGORY = null;
	PreparedStatement PS_INSERT_META_ANA_KEYWORD_CATEGORY = null;
	PreparedStatement PS_INSERT_IDX_TOP = null;
	PreparedStatement PS_INSERT_META_REPLY_CNT = null;
	PreparedStatement PS_UPDATE_META_REPLY_CNT = null;
	PreparedStatement PS_INSERT_REPLY_RELATION_KEYWORD = null;
	PreparedStatement PS_INSERT_TELEGRAM_LOG = null;
	PreparedStatement PS_SELECT_TELEGRAM_LOG = null;
	
	
	RSN_AutoAnalysis  rw = null;/*자동분석*/
	DateUtil du = new DateUtil();/*일자*/
	PersonalInfoUtil pu = new PersonalInfoUtil(); /*개인정보 */	
	
    public static void main( String[] args ){
    	HOTELSHILLA_01_migrationData_v3 prc = new HOTELSHILLA_01_migrationData_v3();
    	Log.crond(prc.className, prc.className + "Migration_v3 START ...");
    	
    	String k_prc = args[0].split("_")[1];
    	//String k_prc = "2";
    	
    	if(k_prc == null || k_prc.equals("")) {
    		Log.crond(prc.className, prc.className + "Migration_v3 No number is available k_prc. ...");	
    	}else {
    		prc.Execute(k_prc);
    	}
    	
    	Log.crond(prc.className, prc.className + "Migration_v3 END ...");
    }
   

    void Execute(String k_prc){
    	
    	try{
    		
    		
    		dbconn1 = new DBconn();
        	dbconn1.getSubDirectConnection();
        	
        	
        	//프리페어 스테이트먼트 생성(반복적인 쿼리만 생성)
        	PS_INSERT_META_SEQ = dbconn1.createPStatement(getQuery("INSERT_META_SEQ"));
        	PS_SELECT_META_SEQ_MD_SEQ = dbconn1.createPStatement(getQuery("SELECT_META_SEQ_MD_SEQ"));
        	PS_UPDATE_CHANNEL = dbconn1.createPStatement(getQuery("UPDATE_CHANNEL"));
        	PS_INSERT_TELEGRAM_LOG = dbconn1.createPStatement(getQuery("INSERT_TELEGRAM_LOG"));       
        	PS_SELECT_TELEGRAM_LOG = dbconn1.createPStatement(getQuery("SELECT_TELEGRAM_LOG"));
        	
        	if(k_prc.equals("1")) {
        		PS_INSERT_META = dbconn1.createPStatement(getQuery("INSERT_META"));
            	PS_INSERT_IDX = dbconn1.createPStatement(getQuery("INSERT_IDX"));
            	PS_INSERT_EXCEPTION_META = dbconn1.createPStatement(getQuery("INSERT_EXCEPTION_META"));
            	PS_INSERT_EXCEPTION_IDX = dbconn1.createPStatement(getQuery("INSERT_EXCEPTION_IDX"));
            	PS_UPDATE_META = dbconn1.createPStatement(getQuery("UPDATE_META"));
            	PS_INSERT_TRANS_DATA = dbconn1.createPStatement(getQuery("INSERT_TRANS_DATA"));
            	PS_INSERT_IDX_CATEGORY = dbconn1.createPStatement(getQuery("INSERT_IDX_CATEGORY"));
        		PS_SELECT_SAME_FILTER = dbconn1.createPStatement(getQuery("SELECT_SAME_FILTER"));
            	PS_INSERT_SAME_FILTER = dbconn1.createPStatement(getQuery("INSERT_SAME_FILTER"));
            	PS_UPDATE_SAME_FILTER = dbconn1.createPStatement(getQuery("UPDATE_SAME_FILTER"));
            	PS_UPDATE_META_ANA_KEYWORD = dbconn1.createPStatement(getQuery("UPDATE_META_ANA_KEYWORD"));
            	PS_INSERT_META_ANA_KEYWORD = dbconn1.createPStatement(getQuery("INSERT_META_ANA_KEYWORD"));
            	PS_UPDATE_META_ANA_KEYWORD_CATEGORY = dbconn1.createPStatement(getQuery("UPDATE_META_ANA_KEYWORD_CATEGORY"));
            	PS_INSERT_META_ANA_KEYWORD_CATEGORY = dbconn1.createPStatement(getQuery("INSERT_META_ANA_KEYWORD_CATEGORY"));
            	PS_INSERT_META_REPLY_CNT = dbconn1.createPStatement(getQuery("INSERT_META_REPLY_CNT"));
        	}else if(k_prc.equals("2")) {
        		PS_SELECT_SAME_FILTER = dbconn1.createPStatement(getQuery("SELECT_SAME_FILTER_WARNING"));
            	PS_INSERT_SAME_FILTER = dbconn1.createPStatement(getQuery("INSERT_SAME_FILTER_WARNING"));
            	PS_UPDATE_SAME_FILTER = dbconn1.createPStatement(getQuery("UPDATE_SAME_FILTER_WARNING"));
            	PS_INSERT_META = dbconn1.createPStatement(getQuery("INSERT_WARNING"));
            	PS_UPDATE_META = dbconn1.createPStatement(getQuery("UPDATE_WARNING"));
            	PS_INSERT_IDX = dbconn1.createPStatement(getQuery("INSERT_IDX_WARNING"));
            	PS_UPDATE_META_ANA_KEYWORD = dbconn1.createPStatement(getQuery("UPDATE_WARNING_ANA_KEYWORD"));
            	PS_INSERT_META_ANA_KEYWORD = dbconn1.createPStatement(getQuery("INSERT_WARNING_ANA_KEYWORD"));
        	}else if(k_prc.equals("3")) { //특정 영역 지정 키워드 
        		PS_SELECT_SAME_FILTER = dbconn1.createPStatement(getQuery("SELECT_SAME_FILTER_SECTION"));
            	PS_INSERT_SAME_FILTER = dbconn1.createPStatement(getQuery("INSERT_SAME_FILTER_SECTION"));
            	PS_UPDATE_SAME_FILTER = dbconn1.createPStatement(getQuery("UPDATE_SAME_FILTER_SECTION"));
            	PS_INSERT_META = dbconn1.createPStatement(getQuery("INSERT_SECTION"));
            	PS_UPDATE_META = dbconn1.createPStatement(getQuery("UPDATE_SECTION"));
            	PS_INSERT_IDX = dbconn1.createPStatement(getQuery("INSERT_IDX_SECTION"));
            	PS_UPDATE_META_ANA_KEYWORD = dbconn1.createPStatement(getQuery("UPDATE_SECTION_ANA_KEYWORD"));
            	PS_INSERT_META_ANA_KEYWORD = dbconn1.createPStatement(getQuery("INSERT_SECTION_ANA_KEYWORD"));
        	}else if(k_prc.equals("4")) {
        		PS_SELECT_SAME_FILTER = dbconn1.createPStatement(getQuery("SELECT_SAME_FILTER_WARNING_CAPITAL"));
            	PS_INSERT_SAME_FILTER = dbconn1.createPStatement(getQuery("INSERT_SAME_FILTER_WARNING_CAPITAL"));
            	PS_UPDATE_SAME_FILTER = dbconn1.createPStatement(getQuery("UPDATE_SAME_FILTER_WARNING_CAPITAL"));
            	PS_INSERT_META = dbconn1.createPStatement(getQuery("INSERT_WARNING_CAPITAL"));
            	PS_UPDATE_META = dbconn1.createPStatement(getQuery("UPDATE_WARNING_CAPITAL"));
            	PS_INSERT_IDX = dbconn1.createPStatement(getQuery("INSERT_IDX_WARNING_CAPITAL"));
            	PS_UPDATE_META_ANA_KEYWORD = dbconn1.createPStatement(getQuery("UPDATE_WARNING_ANA_KEYWORD_CAPITAL"));
            	PS_INSERT_META_ANA_KEYWORD = dbconn1.createPStatement(getQuery("INSERT_WARNING_ANA_KEYWORD_CAPITAL"));
        	}else if(k_prc.equals("90")) {
        		PS_INSERT_TOP = dbconn1.createPStatement(getQuery("INSERT_TOP"));	
        		PS_SELECT_TOP_URL = dbconn1.createPStatement(getQuery("SELECT_TOP_URL"));
        		PS_INSERT_IDX_TOP = dbconn1.createPStatement(getQuery("INSERT_IDX_TOP"));
        	}else if(k_prc.equals("91")) {
        		PS_INSERT_REPLY = dbconn1.createPStatement(getQuery("INSERT_REPLY"));
        		PS_SELECT_META_SEQ_REPLY = dbconn1.createPStatement(getQuery("SELECT_META_SEQ_REPLY"));
            	PS_UPDATE_META_REPLY_CNT = dbconn1.createPStatement(getQuery("UPDATE_META_REPLY_CNT"));
            	PS_INSERT_REPLY_RELATION_KEYWORD = dbconn1.createPStatement(getQuery("INSERT_REPLY_RELATION_KEYWORD"));
        	} 
        	        	
        	//마스터 조회
        	jChannel = getExcuteQueryArr(getQuery("CHANNEL",k_prc));//채널
        	
        	//설정정보 적용
        	jProperty = getExcuteQueryArr(getQuery("PROPERTY"));//설정정보
        	setProperty();
        	
        	jSite = getExcuteQueryArr(getQuery("SITE"));//사이트
        	jTierSite = getExcuteQueryArr(getQuery("TIER_SITE"));//주요사이트
        	jExSite = reExSite(getExcuteQueryArr(getQuery("EX_SITE")));//제외사이트
        	jExSiteUrl = getExcuteQueryArr(getQuery("EX_URL"));//제외URL        	
        	JSONObject jSg_seqs = getExcuteQueryObj(getQuery("SG_SEQS"));//사이트그룹
        	jAllExKeyword = getExcuteQueryArr(getQuery("ALL_EX_KEYWORD"));//전체제외키워드 정보
        	jTelegram = reTelegramInfo(getExcuteQueryArr(getQuery("SELECT_TELEGRAM_SETTING")));//텔레그램 설정정보
        	
        	if(k_prc.equals("1")) {
        		JSONArray jTmpKeyword = getExcuteQueryArr(getQuery("KEYWORD", jSg_seqs.getString("SG_SEQS") + "@" + k_prc));//키워드 정보
            	JSONArray jTmpExKeyword = getExcuteQueryArr(getQuery("EX_KEYWORD"));//제외 키워드정보
            	//jKeyword = reKeyword(jTmpKeyword, jTmpExKeyword);
            	jKeyword = reKeywordOp(jTmpKeyword, jTmpExKeyword);
            	JSONArray jTmpKeyword_category = getExcuteQueryArr(getQuery("KEYWORD_CATEGORY",jSg_seqs.getString("SG_SEQS")));
            	JSONArray jTmpExKeyword_category = getExcuteQueryArr(getQuery("EX_KEYWORD_CATEGORY"));
            	jKeyword_category = reKeyword(jTmpKeyword_category, jTmpExKeyword_category);//분류 키워드 정보	
        	}else if(k_prc.equals("2")) {
            	JSONArray jTmpKeyword = getExcuteQueryArr(getQuery("KEYWORD_WARNING",jSg_seqs.getString("SG_SEQS")));
            	JSONArray jTmpExKeyword = getExcuteQueryArr(getQuery("EX_KEYWORD_WARNING"));
            	jKeyword = reKeyword(jTmpKeyword, jTmpExKeyword);//분류 키워드 정보	
            	
        	}else if(k_prc.equals("3")) {
        		JSONArray jTmpKeyword = getExcuteQueryArr(getQuery("KEYWORD_SECTION"));
        		JSONArray jTmpExKeyword = getExcuteQueryArr(getQuery("EX_KEYWORD_SECTION"));
        		jKeyword = reSectionKeyword(jTmpKeyword, jTmpExKeyword);//분류 키워드 정보	
        	}else if(k_prc.equals("4")) {
            	JSONArray jTmpKeyword = getExcuteQueryArr(getQuery("KEYWORD_WARNING_CAPITAL",jSg_seqs.getString("SG_SEQS")));
            	JSONArray jTmpExKeyword = getExcuteQueryArr(getQuery("EX_KEYWORD_WARNING_CAPITAL"));
            	jKeyword = reKeyword(jTmpKeyword, jTmpExKeyword);//분류 키워드 정보	
        	}else if(k_prc.equals("90")) {
        		JSONArray jTmpKeyword_top = getExcuteQueryArr(getQuery("KEYWORD_TOP"));
            	JSONArray jTmpExKeyword_top = getExcuteQueryArr(getQuery("EX_KEYWORD_TOP"));
            	jKeyword_top = reKeyword(jTmpKeyword_top, jTmpExKeyword_top);//포탈탑 키워드 정보	
        	}
        		
        	if(useMemoryCheck){
    			preUseMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();	
    		}
        	

        	if(useSame){
            	//유사필터 삭제        		   		
        		if(k_prc.equals("1")) {
        			delSameFilter("DELETE_SAME_FILTER_DATE"); //META 유사	        		
        		}else if(k_prc.equals("2")){
        			delSameFilter("DELETE_SAME_FILTER_WARNING_DATE");//WARING 유사
        		}else if(k_prc.equals("3")){
        			delSameFilter("DELETE_SAME_FILTER_SECTION_DATE");//SECTION 유사
        		}else if(k_prc.equals("4")){
        			delSameFilter("DELETE_SAME_FILTER_WARNING_DATE_CAPITAL");//WARNING_CAPITAL 유사
        		}
        	}
        	
        	if(useAutoAnalysis_meta || useAutoAnalysis_reply || useAutoAnalysis_top || useAutoAnalysis_warning || useAutoAnalysis_section){
        		rw = new RSN_AutoAnalysis();
        	}
        	
        	//오류데이터 제거
        	//delData();
        	
        	//System.exit(1);
        	
        	//채널박스의 공간을 생성
        	jChannelBox = new JSONArray[jChannel.length()];
        	
        	JSONObject jRow = null;
        	String type = "";
        
        	
        	while((jRow = getRow()) != null){
        		
        		type = jRow.getString("TYPE");
        		
        		if(k_prc.equals("91")){
        			//댓글
        			replyFilter(type, jRow);	
        		}else if(k_prc.equals("90")){
        			//포탈탑
        			TopFilter(type, jRow);	
        		}else if(k_prc.equals("2") || k_prc.equals("4")){
        			//워닝키워드 || 워닝(케피탈)키워드 
        			WarningKeywordFilter(type, jRow);	
        		}else if(k_prc.equals("3")){
        			//영역지정키워드 - 제외룰 적용은 키워드별제외키워드만 적용
        			SectionKeywordFilter(type, jRow);	
        		}else{
        			//일반
        			KeywordFilter(type, jRow);
        		}
        	}
        	
        	
        	
    	}catch(Exception ex){
    		writeExceptionLog(ex);
    	}finally{
    		if (PS_INSERT_META_SEQ != null) try { PS_INSERT_META_SEQ.close(); } catch(SQLException ex) {}
    		if (PS_INSERT_META  != null) try { PS_INSERT_META.close(); } catch(SQLException ex) {}
    		if (PS_INSERT_REPLY  != null) try { PS_INSERT_REPLY.close(); } catch(SQLException ex) {}
    		if (PS_INSERT_TOP  != null) try { PS_INSERT_TOP.close(); } catch(SQLException ex) {}
    		if (PS_INSERT_IDX  != null) try { PS_INSERT_IDX.close(); } catch(SQLException ex) {}
    		if (PS_INSERT_EXCEPTION_META  != null) try { PS_INSERT_EXCEPTION_META.close(); } catch(SQLException ex) {}
    		if (PS_INSERT_EXCEPTION_IDX  != null) try { PS_INSERT_EXCEPTION_IDX.close(); } catch(SQLException ex) {}
    		if (PS_UPDATE_CHANNEL  != null) try { PS_UPDATE_CHANNEL.close(); } catch(SQLException ex) {}
    		if (PS_SELECT_SAME_FILTER  != null) try { PS_SELECT_SAME_FILTER.close(); } catch(SQLException ex) {}
    		if (PS_INSERT_SAME_FILTER  != null) try { PS_INSERT_SAME_FILTER.close(); } catch(SQLException ex) {}
    		if (PS_UPDATE_SAME_FILTER   != null) try { PS_UPDATE_SAME_FILTER.close(); } catch(SQLException ex) {}
    		if (PS_UPDATE_META   != null) try { PS_UPDATE_META.close(); } catch(SQLException ex) {}
    		if (PS_INSERT_TRANS_DATA   != null) try { PS_INSERT_TRANS_DATA.close(); } catch(SQLException ex) {}
    		if (PS_INSERT_IDX_CATEGORY   != null) try { PS_INSERT_IDX_CATEGORY.close(); } catch(SQLException ex) {}
    		if (PS_SELECT_META_SEQ_REPLY   != null) try { PS_SELECT_META_SEQ_REPLY.close(); } catch(SQLException ex) {}
    		if (PS_SELECT_META_SEQ_MD_SEQ   != null) try { PS_SELECT_META_SEQ_MD_SEQ.close(); } catch(SQLException ex) {}
    		if (PS_SELECT_TOP_URL   != null) try { PS_SELECT_TOP_URL.close(); } catch(SQLException ex) {}
    		if (PS_UPDATE_META_ANA_KEYWORD   != null) try { PS_UPDATE_META_ANA_KEYWORD.close(); } catch(SQLException ex) {}
    		if (PS_INSERT_META_ANA_KEYWORD   != null) try { PS_INSERT_META_ANA_KEYWORD.close(); } catch(SQLException ex) {}
    		if (PS_UPDATE_META_ANA_KEYWORD_CATEGORY   != null) try { PS_UPDATE_META_ANA_KEYWORD_CATEGORY.close(); } catch(SQLException ex) {}
    		if (PS_INSERT_META_ANA_KEYWORD_CATEGORY   != null) try { PS_INSERT_META_ANA_KEYWORD_CATEGORY.close(); } catch(SQLException ex) {}
    		if (PS_INSERT_IDX_TOP   != null) try { PS_INSERT_IDX_TOP.close(); } catch(SQLException ex) {}
    		if (PS_INSERT_META_REPLY_CNT   != null) try { PS_INSERT_META_REPLY_CNT.close(); } catch(SQLException ex) {}
    		if (PS_UPDATE_META_REPLY_CNT   != null) try { PS_UPDATE_META_REPLY_CNT.close(); } catch(SQLException ex) {}
    		if (PS_INSERT_REPLY_RELATION_KEYWORD   != null) try { PS_INSERT_REPLY_RELATION_KEYWORD.close(); } catch(SQLException ex) {}
    		if (PS_INSERT_TELEGRAM_LOG   != null) try { PS_INSERT_TELEGRAM_LOG.close(); } catch(SQLException ex) {}    		
    		if (PS_SELECT_TELEGRAM_LOG   != null) try { PS_SELECT_TELEGRAM_LOG.close(); } catch(SQLException ex) {}
    		
    		if (dbconn1 != null) try { dbconn1.close(); } catch(SQLException ex) {}
    	}
 	}

    
    boolean replyFilter(String type, JSONObject jRow){
    	
    	boolean result = false;
    	String s_seq = "";
    	String d_seq = "";
    	String rd_seq = "";
    	String ch_seq = "";
    	String newSeq = "0";
    	JSONObject jObjMeta = null;
    	ArrayList<JSONObject> arIdxCate = new ArrayList<JSONObject>();
    	JSONArray jCateotryIdxInfo = new JSONArray();
    	SuccessForm autoVo = null;
    	String text = "";
    	String senti = "3";
    	String relationKey = "";
    	JSONObject jObjIdx = null;
    	String reply_p_code = "";
    	
    	
    	JSONObject jObjReplyCnt = null;
    	
    	
    	ArrayList<JSONObject> arReplyRelationKey = new ArrayList<JSONObject>();
    	
    	
    
    	try{

    		//사이트 체크
    		s_seq = jRow.getString("COMMON.SITE.Seq");
    		if(!s_seq.equals("2196") && !s_seq.equals("2199")){
    			return result;	
    		}
			
    		//원문조회
    		d_seq = jRow.getString("MAIN.DATA.Seq");
    		JSONObject jObj = new JSONObject();
        	jObj.put("1_d_Seq", d_seq);
        	
        	JSONObject jChk = getExcuteQueryObj(PS_SELECT_META_SEQ_REPLY, jObj);
        	
        	
        	
        	reply_p_code = "";
        	if(jChk.isNull("MD_SEQ")){
        		return result;
        	}else{
        		reply_p_code = jChk.getString("MD_SEQ"); 
        	}
        	
        	//시퀀스 생성
        	//ch_seq = jRow.getString("CH_SEQ");
        	rd_seq = jRow.getString("REPLY.DATA.Seq");
        	newSeq = getGenerateSeq(PS_INSERT_META_SEQ, type, rd_seq);
        	
        	if(!newSeq.equals("")) {
        		//메타 데이터 생성
            	String[] tier = new String[2];
            	tier[0] = "4";
            	tier[1] = "999";
            	
            	//자동분석
    			if(useAutoAnalysis_reply){
    				text = jRow.getString(type + ".DATA.Content");
    				autoVo = rw.AutoAnalysis(1, text, "","KOR","");
    				senti = getAutoSentiment(autoVo.getJsonStr());
    				arReplyRelationKey.clear();
    				arReplyRelationKey = makeAutoReplyRelation(reply_p_code, newSeq, autoVo.getJsonStr());
    				setExcuteUpdate(PS_INSERT_REPLY_RELATION_KEYWORD, arReplyRelationKey, true);
    			}
    			
    			jObjMeta = makeMeta(jRow, newSeq, "59", s_seq, tier, "", null, reply_p_code, senti,"");
    			

    			setExcuteUpdate(PS_INSERT_REPLY, jObjMeta);
    			
    			
    			jObjReplyCnt = new JSONObject();
    			jObjReplyCnt.put("1_md_reply_total_cnt", 1);
    			
    			if(senti.equals("1")) {
    				jObjReplyCnt.put("2_md_reply_pos_cnt", 1);
    				jObjReplyCnt.put("3_md_reply_neg_cnt", 0);
    				jObjReplyCnt.put("4_md_reply_neu_cnt", 0);
    			}else if(senti.equals("2")) {
    				jObjReplyCnt.put("2_md_reply_pos_cnt", 0);
    				jObjReplyCnt.put("3_md_reply_neg_cnt", 1);
    				jObjReplyCnt.put("4_md_reply_neu_cnt", 0);
    			}else if(senti.equals("3")) {
    				jObjReplyCnt.put("2_md_reply_pos_cnt", 0);
    				jObjReplyCnt.put("3_md_reply_neg_cnt", 0);
    				jObjReplyCnt.put("4_md_reply_neu_cnt", 1);
    			}
    			
    			jObjReplyCnt.put("5_md_seq", reply_p_code);
    			
    			setExcuteUpdate(PS_UPDATE_META_REPLY_CNT, jObjReplyCnt);
    			
    			
    			
    			if(useCatagoryReply){
    				/*
    				arIdxCate.clear();

    				//업무분류사전 인텍싱
        			jCateotryIdxInfo = null;
        			jCateotryIdxInfo = getCategoryIndexing(text, text);
        			
        			//카테고리 색인데이터 생성
        			for(int i =0; i < jCateotryIdxInfo.length(); i++){
        				jObjIdx = new JSONObject();
        				jObjIdx = jCateotryIdxInfo.getJSONObject(i);
        				jObjIdx.put("1_md_seq", newSeq);
        				arIdxCate.add(jObjIdx);
        			}	
        			
        			setExcuteUpdate(PS_INSERT_IDX_CATEGORY, arIdxCate, true);
        			*/
    			}
    			
    			if(useDataTans){
    				JSONArray jIdxInfo = new JSONArray();
    				JSONObject jObjTrans = makeTransData(type, jObjMeta, jIdxInfo, jCateotryIdxInfo, Integer.parseInt(senti) , relationKey);
    				setExcuteUpdate(PS_INSERT_TRANS_DATA, jObjTrans);
    			}
        	}
        	
    		result = true;
    	}catch(Exception ex){
    		writeExceptionLog(ex);
    	}
    	return result; 
    }
    
    boolean TopFilter(String type, JSONObject jRow){
    	
    	boolean result = false;
    	String s_seq = "";
    	String d_seq = "";
    	String d_url = "";
    	String newSeq = "0";
    	JSONObject jObjMeta = null;
    	ArrayList<JSONObject> arIdx = new ArrayList<JSONObject>();
    	
    	SuccessForm autoVo = null;
    	String text = "";
    	String senti = "3";
    	String relationKeys = "";
    	JSONObject jObjIdx = null;
    
    	text = "";
		String regexpText = "";
		String regexpStr = "[',|@#$%\\^&*\\s\\[\\](){}‘’<>\"+-./:=?_`´~§±·]";
		JSONArray jIdxInfo = new JSONArray();
    	
    	try{
    		
    		//사이트 체크
    		s_seq = jRow.getString("COMMON.SITE.Seq");
    		

    		if(!jRow.isNull(type + ".DATA.Title")){
				text = jRow.getString("SECTION.DATA.Title");	
			}
			text += " ";
			if(!jRow.isNull(type + ".DATA.Content")){
				text += jRow.getString("SECTION.DATA.Content");	
			}
			
    		text = text.toLowerCase();
    		regexpText = text.replaceAll(regexpStr, " ");
    		
    		
    		d_seq = jRow.getString(type + ".DATA.Seq");
    		
    		jIdxInfo = null;
    		//키워드 추출
    		jIdxInfo = getTopIndexing(text, regexpText);
    		
    		if(jIdxInfo.length() > 0){
    			//URL조회
        		d_url = jRow.getString(type + ".DATA.Link");
        		JSONObject jObj = new JSONObject();
            	jObj.put("1_d_url", d_url);

            	JSONObject jChk = getExcuteQueryObj(PS_SELECT_TOP_URL, jObj);
            	
            	if(!jChk.isNull("MD_SEQ")){
            		return result;
            	}
            	
            	//시퀀스 생성
            	//ch_seq = jRow.getString("CH_SEQ");
            	//rd_seq = jRow.getString("REPLY.DATA.Seq");
            	newSeq = getGenerateSeq(PS_INSERT_META_SEQ, type, d_seq);
            	
            	
            	
            	
            	if(!newSeq.equals("")) {
            		//메타 데이터 생성
                	String[] tier = new String[2];
                	tier[0] = "4";
                	tier[1] = "999";

                	//색인데이터 생성
                	arIdx.clear();
        			for(int i =0; i < jIdxInfo.length(); i++){
        				jObjIdx = new JSONObject(jIdxInfo.getJSONObject(i).toString());
        				jObjIdx.put("1_md_seq", newSeq);
        				arIdx.add(jObjIdx);
        			}
        			setExcuteUpdate(PS_INSERT_IDX_TOP, arIdx, true);
        			
                	
        			//자동분석
        			if(useAutoAnalysis_top){
        				text = jRow.getString(type + ".DATA.Content");
        				autoVo = rw.AutoAnalysis(1, text, "","KOR","");
        				senti = getAutoSentiment(autoVo.getJsonStr());
        				relationKeys = makeAutoRelationWords(autoVo.getJsonStr());
        			}
                	
                	jObjMeta = makeMeta(jRow, newSeq, "90", s_seq, tier, "", null, "", senti, relationKeys);
            		

        			setExcuteUpdate(PS_INSERT_TOP, jObjMeta);
        			
        			
        			
        			
        			if(useCatagoryReply){
        				
        				//아직 개발 안됨
        				/*
        				arIdxCate.clear();

        				//업무분류사전 인텍싱
            			jCateotryIdxInfo = null;
            			jCateotryIdxInfo = getCategoryIndexing(text, text);
            			
            			//카테고리 색인데이터 생성
            			for(int i =0; i < jCateotryIdxInfo.length(); i++){
            				jObjIdx = new JSONObject();
            				jObjIdx = jCateotryIdxInfo.getJSONObject(i);
            				jObjIdx.put("1_md_seq", newSeq);
            				arIdxCate.add(jObjIdx);
            			}	
            			
            			setExcuteUpdate(PS_INSERT_IDX_CATEGORY, arIdxCate, true);
            			*/
        			}
        			
        			
        			if(useDataTans){
        				//아직 개발 안됨
        				/*
        				JSONArray jIdxInfo = new JSONArray();
        				JSONObject jObjTrans = makeTransData(type, jObjMeta, jIdxInfo, jCateotryIdxInfo, senti, relationKey);
        				setExcuteUpdate(PS_INSERT_TRANS_DATA, jObjTrans);
        				*/
        			}
            	}
    		}
    		
    		result = true;
    	}catch(Exception ex){
    		writeExceptionLog(ex);
    	}
    	return result; 
    }
    
    boolean KeywordFilter(String type, JSONObject jRow){
    	
    	boolean result = false;
    	boolean PASSAGE = false;
    	
    	String sg_seq = "";
    	String lang3 = "";
    	
    	//String ch_seq = "";
    	String s_seq = "";
    	String link = "";
    	String text = "";
    	String regexpText = "";
    	String title = "";
    	JSONArray jIdxInfo = new JSONArray();
    	JSONArray jCateotryIdxInfo = new JSONArray();
    	String newSeq = "0";
    	String cDate = "";
    	String d_seq = "";
    	String exKeyword = "";
    	String[] tier = null; 
    	JSONObject jSameObj = null;
    	JSONObject jReSameObj = null;
    	String regexpStr = "[',|@#$%\\^&*\\s\\[\\](){}‘’<>\"+-./:=?_`´~§±·]";
    	String senti = "3";
    	String relationKeys = "";
    	SuccessForm autoVo = null;
    	JSONObject indexJObj = null;
    	
    	
    	JSONObject jReplyCntObj = null;
    	
    	int[] batchResult = null; 
    	
    	try{
    		
        	
        	JSONObject jObjMeta = null;
        	JSONObject jObjIdx = null;
        	ArrayList<JSONObject> arIdx = new ArrayList<JSONObject>();
        	ArrayList<JSONObject> arIdxCate = new ArrayList<JSONObject>();
        	
        	JSONObject jObjTrans = null;
        	
        	
        	
        	//트위터는 한국어만
    		if(type.equals("TWITTER")){
    			lang3 = jRow.getString(type + ".DATA.Lang3");
    			if(!lang3.equals("KOR")){
    				return result;
    			}
    		}
    		
    		//사이트 추출
    		if(type.equals("TWITTER")){
    			s_seq = "10464";	
    		}else if(type.equals("FACEBOOK")){
    			s_seq = "11377";
    		}else{
    			s_seq = jRow.getString("COMMON.SITE.Seq");
    		}
    		
    		//사이트 그룹추출
    		sg_seq = getSiteGroup(s_seq,"SG_SEQ");
    		if(sg_seq.equals("")){
    			return result;
    		}
    		
    		if(!jRow.isNull(type + ".DATA.Link")){
    			link = jRow.getString(type + ".DATA.Link");
    		}
    		
    		//제외사이트 체크
    		if(type.equals("MAIN")){
    			PASSAGE = chkExSite(type, s_seq, link);
    			if(PASSAGE){
    				return result;
        		}	
    		}
    		//제외URL 체크
			if(exSiteUrlFilter(s_seq, link)){
				
				return result;
    		}	
    		
    		
    		//내용 길이 체크
    		if(jRow.getString(type + ".DATA.Content").length() > 5000) {
    			return result;
    		}
    	
    		//키워드 체크
    		text = "";
    		regexpText = "";
    		if(type.equals("MAIN") || type.equals("SECTION") || type.equals("STORE")){
    			if(!jRow.isNull(type + ".DATA.Title")){
    				text = jRow.getString(type + ".DATA.Title");	
    			}
    			text += " ";
    			if(!jRow.isNull(type + ".DATA.Content")){
    				text += jRow.getString(type + ".DATA.Content");	
    			}
    		}else{
    			if(!jRow.isNull(type + ".DATA.Content")){
    				text = jRow.getString(type + ".DATA.Content");	
    			}
    		}
    		text = text.toLowerCase();
    		regexpText = text.replaceAll(regexpStr, " ");
    		
    		
    		d_seq = jRow.getString(type + ".DATA.Seq");
    		
    		jIdxInfo = null;
    		//키워드 추출
    		//jIdxInfo = getIndexing(text, regexpText, sg_seq, d_seq);
    		jIdxInfo = getIndexingOp(text, regexpText, sg_seq, d_seq); //키워드별 제외키워드 검색 옵션추가
    		
    		if(jIdxInfo.length() > 0){
    			
    			cDate = stampToDateTime(jRow.getString(type + ".DATA.CrawlStamp"), "yyyyMMdd");
    			
    			//시퀀스 생성
    			//ch_seq = jRow.getString("CH_SEQ");
    			newSeq = getMdSeq(PS_SELECT_META_SEQ_MD_SEQ, type, d_seq);
    			
    			if(newSeq.equals("")) {
    				newSeq = getGenerateSeq(PS_INSERT_META_SEQ, type, d_seq);
    			}
    			
    			if(!newSeq.equals("")) {
    				
    				//전체제외키워드 추출
        			exKeyword = getExKeyword(text, regexpText);
        			
        			//티어 추출
        			tier = getTier(s_seq);
    				
    				//if(useSame){
        			//유사 처리 채널 설정
        			if(useSame && binarySearch(useSameChanel, sg_seq)>= 0){        			
        				//유사필터 조회
            			if(type.equals("MAIN") || type.equals("SECTION") || type.equals("STORE")){
        					title = jRow.getString(type + ".DATA.Title");
        				}else{
        					title = jRow.getString(type + ".DATA.Content");
        				}
            			
            			if(title.length() >500) {
            				title = title.substring(0, 500); 
            			}
            			
            			jSameObj = null;
            			if(exKeyword.equals("")){
                			jSameObj = getSameFilter(title);	
            			}	
        			}
        			
    				//자동분석
        			if(useAutoAnalysis_meta){
        				//text = jRow.getString(type + ".DATA.Content");
        				autoVo = rw.AutoAnalysis(1, text, "","KOR","");
        				senti = getAutoSentiment(autoVo.getJsonStr());
        				relationKeys = makeAutoRelationWords(autoVo.getJsonStr());
        			}
    				
        			//메타 데이터 생성
        			jObjMeta = makeMeta(jRow, newSeq, sg_seq, s_seq, tier, exKeyword, jSameObj,"", senti, relationKeys);
        			
        			//색인데이터 생성
        			for(int i =0; i < jIdxInfo.length(); i++){
        				jObjIdx = new JSONObject(jIdxInfo.getJSONObject(i).toString());
        				jObjIdx.put("1_md_seq", newSeq);
        				arIdx.add(jObjIdx);
        			}
        			
        			//메타 데이터 저장
        			if(exKeyword.equals("")){
        				
        			
        				
        				//META테이블 저장
            			setExcuteUpdate(PS_INSERT_META, jObjMeta);
            			//IDX테이블 저장
        				setExcuteUpdate(PS_INSERT_IDX, arIdx, true);
        				
        				if(s_seq.equals("2196") || s_seq.equals("2199")){	
        					jReplyCntObj = new JSONObject();
        					jReplyCntObj.put("1_md_seq", newSeq);
        					jReplyCntObj.put("2_md_date", cDate);
        					setExcuteUpdate(PS_INSERT_META_REPLY_CNT, jReplyCntObj);	
        	    		}
        				
        				
        				//색인 통계 생성
            			arIdx.clear();
            			arIdx = getStatisticsIdx(jIdxInfo, cDate);
            			batchResult = setExcuteUpdate(PS_UPDATE_META_ANA_KEYWORD, arIdx, true);
            			
            			for(int i =0; i < batchResult.length; i++){
            				if(batchResult[i] == 0) {
            					setExcuteUpdate(PS_INSERT_META_ANA_KEYWORD, arIdx.get(i));
            				}
            			}
            			
            			
        				//분류 키워드 저장
        				if(useCatagory){
        					arIdxCate.clear();
        					
        					//업무분류사전 인텍싱
                			jCateotryIdxInfo = null;
                			jCateotryIdxInfo = getCategoryIndexing(text, regexpText);
                			
                			//카테고리 색인데이터 생성
                			for(int i =0; i < jCateotryIdxInfo.length(); i++){
                				jObjIdx = new JSONObject(jCateotryIdxInfo.getJSONObject(i).toString());
                				jObjIdx.put("1_md_seq", newSeq);
                				arIdxCate.add(jObjIdx);
                			}	
                			
                			
                			if(arIdxCate.size() > 0) {
                				setExcuteUpdate(PS_INSERT_IDX_CATEGORY, arIdxCate, true);
                    			
                    			//카테고리 색인 통계 생성
                    			arIdxCate.clear();
                    			
                    			arIdxCate = getStatisticsIdx(jCateotryIdxInfo, cDate);
                    			
                    			
                    			batchResult = setExcuteUpdate(PS_UPDATE_META_ANA_KEYWORD_CATEGORY, arIdxCate, true);
                    			
                    			for(int i =0; i < batchResult.length; i++){
                    				if(batchResult[i] == 0) {
                    					setExcuteUpdate(PS_INSERT_META_ANA_KEYWORD_CATEGORY, arIdxCate.get(i));
                    				}
                    			}	
                			}
                			
                			
                			
                			
        				}
        				//텔레그램 발송여부
        				if(useTelegram) {
        					SendTelegram_v2(jIdxInfo, jCateotryIdxInfo, jObjMeta);
        				} 
        				
        				if(useDataTans){
        					jObjTrans = makeTransData(type, jObjMeta, jIdxInfo, jCateotryIdxInfo, Integer.parseInt(senti) , relationKeys);
        					setExcuteUpdate(PS_INSERT_TRANS_DATA, jObjTrans);
        				}
        				
        				//if(useSame){
        				//유사 처리 채널 설정
            			if(useSame && binarySearch(useSameChanel, sg_seq)>= 0){  
        					//유사필터 저장
            				if(jSameObj == null){
            					jReSameObj = null;
            					jReSameObj = new JSONObject();
            					jReSameObj.put("1_md_seq", newSeq);
            					jReSameObj.put("2_md_title", title);
            					jReSameObj.put("3_md_date", jObjMeta.getString("05_md_date"));
            					jReSameObj.put("4_md_same_count", "0");
            					setExcuteUpdate(PS_INSERT_SAME_FILTER,jReSameObj);	
            				}else{
            					jReSameObj = null;
            					jReSameObj = new JSONObject();
            					jReSameObj.put("1_md_same_count", jSameObj.getString("MD_SAME_COUNT"));
            					jReSameObj.put("2_md_seq", jSameObj.getString("MD_PSEQ"));
            					
            					setExcuteUpdate(PS_UPDATE_SAME_FILTER,jReSameObj);
            					setExcuteUpdate(PS_UPDATE_META,jReSameObj);
            				}	
        				}
        				
        			}else{
        				setExcuteUpdate(PS_INSERT_EXCEPTION_META, jObjMeta);
        				setExcuteUpdate(PS_INSERT_EXCEPTION_IDX, arIdx, true);

        			}
    			}
    			
    			//초기화
    			arIdx.clear();
    			
    			result = true;
    			
    		}
    	}catch(Exception ex){
    		writeExceptionLog(ex);
    	}
    	return result;
    }
    
    boolean WarningKeywordFilter(String type, JSONObject jRow){
    	
    	boolean result = false;
    	boolean PASSAGE = false;
    	
    	String sg_seq = "";
    	String lang3 = "";
    	
    	//String ch_seq = "";
    	String s_seq = "";
    	String link = "";
    	String text = "";
    	String regexpText = "";
    	String title = "";
    	JSONArray jIdxInfo = new JSONArray();
    	JSONArray jCateotryIdxInfo = new JSONArray();
    	String newSeq = "0";
    	String cDate = "";
    	String d_seq = "";
    	//String exKeyword = "";
    	String[] tier = null; 
    	JSONObject jSameObj = null;
    	JSONObject jReSameObj = null;
    	String regexpStr = "[',|@#$%\\^&*\\s\\[\\](){}‘’<>\"+-./:=?_`´~§±·]";
    	String relationKeys = "";
    	String senti = "3";
    	String relationKey = "";
    	SuccessForm autoVo = null;
    	JSONObject indexJObj = null;
    	
    	
    	JSONObject jReplyCntObj = null;
    	
    	int[] batchResult = null; 
    	
    	try{
    		
        	
        	JSONObject jObjMeta = null;
        	JSONObject jObjIdx = null;
        	ArrayList<JSONObject> arIdx = new ArrayList<JSONObject>();
        	ArrayList<JSONObject> arIdxCate = new ArrayList<JSONObject>();
        	
        	JSONObject jObjTrans = null;
        	
        	
        	
        	//트위터는 한국어만
    		if(type.equals("TWITTER")){
    			lang3 = jRow.getString(type + ".DATA.Lang3");
    			if(!lang3.equals("KOR")){
    				return result;
    			}
    		}
    		
    		//사이트 추출
    		if(type.equals("TWITTER")){
    			s_seq = "10464";	
    		}else if(type.equals("FACEBOOK")){
    			s_seq = "11377";
    		}else{
    			s_seq = jRow.getString("COMMON.SITE.Seq");
    		}
    		
    		//사이트 그룹추출
    		sg_seq = getSiteGroup(s_seq,"SG_SEQ");
    		if(sg_seq.equals("")){
    			return result;
    		}
    		
    		//제외사이트 체크
    		if(type.equals("MAIN")){
    			PASSAGE = chkExSite(type, s_seq, link);
    			if(PASSAGE){
    				return result;
        		}	
    		}
    		
    		
    		//내용 길이 체크
    		if(jRow.getString(type + ".DATA.Content").length() > 5000) {
    			return result;
    		}
    		
    		//키워드 체크
    		text = "";
    		regexpText = "";
    		if(type.equals("MAIN") || type.equals("SECTION") || type.equals("STORE")){
    			if(!jRow.isNull(type + ".DATA.Title")){
    				text = jRow.getString(type + ".DATA.Title");	
    			}
    			text += " ";
    			if(!jRow.isNull(type + ".DATA.Content")){
    				text += jRow.getString(type + ".DATA.Content");	
    			}
    		}else{
    			if(!jRow.isNull(type + ".DATA.Content")){
    				text = jRow.getString(type + ".DATA.Content");	
    			}
    		}
    		text = text.toLowerCase();
    		regexpText = text.replaceAll(regexpStr, " ");
    		
    		
    		d_seq = jRow.getString(type + ".DATA.Seq");
    		
    		jIdxInfo = null;
    		//키워드 추출
    		jIdxInfo = getIndexing(text, regexpText, sg_seq, d_seq);
    		
    		if(jIdxInfo.length() > 0){
    			
    			cDate = stampToDateTime(jRow.getString(type + ".DATA.CrawlStamp"), "yyyyMMdd");
    			
    			//시퀀스 생성
    			//ch_seq = jRow.getString("CH_SEQ");
    			
    			newSeq = getMdSeq(PS_SELECT_META_SEQ_MD_SEQ, type, d_seq);
    			
    			if(newSeq.equals("")) {
    				newSeq = getGenerateSeq(PS_INSERT_META_SEQ, type, d_seq);
    			}
    			
    			if(!newSeq.equals("")) {
    				
    				//전체제외키워드 추출
        			//exKeyword = getExKeyword(text, regexpText);
        			
        			//티어 추출
        			tier = getTier(s_seq);
    				
    				//if(useSame){
        			//유사 처리 채널 설정
        			if(useSame && binarySearch(useSameChanel, sg_seq)>= 0){  
        				//유사필터 조회
            			if(type.equals("MAIN") || type.equals("SECTION") || type.equals("STORE")){
        					title = jRow.getString(type + ".DATA.Title");
        				}else{
        					title = jRow.getString(type + ".DATA.Content");
        				}
            			if(title.length() >500) {
            				title = title.substring(0, 500); 
            			}
            			
            			
            			jSameObj = null;
            			//if(exKeyword.equals("")){
                			jSameObj = getSameFilter(title);	
            			//}	
        			}
        			
    				//자동분석
        			if(useAutoAnalysis_warning){
        				//text = jRow.getString(type + ".DATA.Content");
        				autoVo = rw.AutoAnalysis(1, text, "","KOR","");
        				senti = getAutoSentiment(autoVo.getJsonStr());
        				relationKeys = makeAutoRelationWords(autoVo.getJsonStr());
        			}
    				
        			//메타 데이터 생성
        			jObjMeta = makeMeta(jRow, newSeq, sg_seq, s_seq, tier, "", jSameObj,"", senti, relationKeys);
        			
        			
        			//색인데이터 생성
        			for(int i =0; i < jIdxInfo.length(); i++){
        				jObjIdx = new JSONObject(jIdxInfo.getJSONObject(i).toString());
        				jObjIdx.put("1_md_seq", newSeq);
        				arIdx.add(jObjIdx);
        			}
        			
        			
        			//META테이블 저장
        			setExcuteUpdate(PS_INSERT_META, jObjMeta);
        			//IDX테이블 저장
    				setExcuteUpdate(PS_INSERT_IDX, arIdx, true);
    				
    				/*
    				if(s_seq.equals("2196") || s_seq.equals("2199")){	
    					jReplyCntObj = new JSONObject();
    					jReplyCntObj.put("1_md_seq", newSeq);
    					jReplyCntObj.put("2_md_date", cDate);
    					setExcuteUpdate(PS_INSERT_META_REPLY_CNT, jReplyCntObj);	
    	    		}
    	    		*/
    				
    				
    				//색인 통계 생성
        			arIdx.clear();
        			arIdx = getStatisticsIdx(jIdxInfo, cDate);
        			batchResult = setExcuteUpdate(PS_UPDATE_META_ANA_KEYWORD, arIdx, true);
        			
        			for(int i =0; i < batchResult.length; i++){
        				if(batchResult[i] == 0) {
        					setExcuteUpdate(PS_INSERT_META_ANA_KEYWORD, arIdx.get(i));
        				}
        			}
        			
        			
    				//분류 키워드 저장
        			/*
    				if(useCatagory){
    					arIdxCate.clear();
    					
    					//업무분류사전 인텍싱
            			jCateotryIdxInfo = null;
            			jCateotryIdxInfo = getCategoryIndexing(text, regexpText);
            			
            			//카테고리 색인데이터 생성
            			for(int i =0; i < jCateotryIdxInfo.length(); i++){
            				jObjIdx = new JSONObject(jCateotryIdxInfo.getJSONObject(i).toString());
            				jObjIdx.put("1_md_seq", newSeq);
            				arIdxCate.add(jObjIdx);
            			}	
            			
            			
            			if(arIdxCate.size() > 0) {
            				setExcuteUpdate(PS_INSERT_IDX_CATEGORY, arIdxCate, true);
                			
                			//카테고리 색인 통계 생성
                			arIdxCate.clear();
                			
                			arIdxCate = getStatisticsIdx(jCateotryIdxInfo, cDate);
                			
                			
                			batchResult = setExcuteUpdate(PS_UPDATE_META_ANA_KEYWORD_CATEGORY, arIdxCate, true);
                			
                			for(int i =0; i < batchResult.length; i++){
                				if(batchResult[i] == 0) {
                					setExcuteUpdate(PS_INSERT_META_ANA_KEYWORD_CATEGORY, arIdxCate.get(i));
                				}
                			}	
            			}
    				}
    				*/
    				
    				//텔레그램 발송여부_v2
        			/*
    				if(useTelegram) {
    					SendTelegram_v2(jIdxInfo, jCateotryIdxInfo, jObjMeta);
    				}
    				*/
    				
        			/*
    				if(useDataTans){
    					jObjTrans = makeTransData(type, jObjMeta, jIdxInfo, jCateotryIdxInfo, Integer.parseInt(senti) , relationKey);
    					setExcuteUpdate(PS_INSERT_TRANS_DATA, jObjTrans);
    				}
    				*/
    				
    				//if(useSame){
        			//유사 처리 채널 설정
        			if(useSame && binarySearch(useSameChanel, sg_seq)>= 0){  
    					//유사필터 저장
        				if(jSameObj == null){
        					jReSameObj = null;
        					jReSameObj = new JSONObject();
        					jReSameObj.put("1_md_seq", newSeq);
        					jReSameObj.put("2_md_title", title);
        					jReSameObj.put("3_md_date", jObjMeta.getString("05_md_date"));
        					jReSameObj.put("4_md_same_count", "0");
        					setExcuteUpdate(PS_INSERT_SAME_FILTER,jReSameObj);	
        				}else{
        					jReSameObj = null;
        					jReSameObj = new JSONObject();
        					jReSameObj.put("1_md_same_count", jSameObj.getString("MD_SAME_COUNT"));
        					jReSameObj.put("2_md_seq", jSameObj.getString("MD_PSEQ"));
        					
        					setExcuteUpdate(PS_UPDATE_SAME_FILTER,jReSameObj);
        					setExcuteUpdate(PS_UPDATE_META,jReSameObj);
        				}	
    				}
    			}
    			
    			//초기화
    			arIdx.clear();
    			
    			result = true;
    			
    		}
    	}catch(Exception ex){
    		writeExceptionLog(ex);
    	}
    	return result;
    }
    
    
    
    
    
    /*
     * 영역 지정 키워드 검사 - 2021.05 고객사 요청
     * - 특정 사이트/수집원/URL 영역 지정 검사
     * - 제외룰은 키워드별 제외키워드만 적용     
     * */
    boolean SectionKeywordFilter(String type, JSONObject jRow){
    	
    	boolean result = false;
    	boolean PASSAGE = false;
    	
    	String sg_seq = "";
    	String s_seq = "";
    	String sb_seq = "0";
    	String lang3 = "";
    	
    	String s_name = "";
    	String sb_name = "";
    	//String ch_seq = "";
    	String link = "";
    	String text = "";
    	String regexpText = "";
    	String url = "";
    	String title = "";
    	JSONArray jIdxInfo = new JSONArray();
    	JSONArray jCateotryIdxInfo = new JSONArray();
    	String newSeq = "0";
    	String cDate = "";
    	String d_seq = "";
    	//String exKeyword = "";
    	String[] tier = null; 
    	JSONObject jSameObj = null;
    	JSONObject jReSameObj = null;
    	String regexpStr = "[',|@#$%\\^&*\\s\\[\\](){}‘’<>\"+-./:=?_`´~§±·]";
    	String relationKeys = "";
    	String senti = "3";
    	String relationKey = "";
    	SuccessForm autoVo = null;
    	JSONObject indexJObj = null;
    	
    	
    	JSONObject jReplyCntObj = null;
    	
    	int[] batchResult = null; 
    	
    	try{
    		
    		
    		JSONObject jObjMeta = null;
    		JSONObject jObjIdx = null;
    		ArrayList<JSONObject> arIdx = new ArrayList<JSONObject>();
    		ArrayList<JSONObject> arIdxCate = new ArrayList<JSONObject>();
    		
    		JSONObject jObjTrans = null;
    		
    		
    		
    		//트위터는 한국어만
    		if(type.equals("TWITTER")){
    			lang3 = jRow.getString(type + ".DATA.Lang3");
    			if(!lang3.equals("KOR")){
    				return result;
    			}
    		}
    		
    		//사이트 추출
    		if(type.equals("TWITTER")){
    			s_seq = "10464";	
    		}else if(type.equals("FACEBOOK")){
    			s_seq = "11377";
    		}else{
    			s_seq = jRow.getString("COMMON.SITE.Seq");
    		}
    		
    		//사이트 그룹추출
    		sg_seq = getSiteGroup(s_seq,"SG_SEQ");
    		if(sg_seq.equals("")){
    			return result;
    		}
    		
			/*
			 * //제외사이트 체크 if(type.equals("MAIN")){ PASSAGE = chkExSite(type, s_seq, link);
			 * if(PASSAGE){ return result; } }
			 */
    		
    		//내용 길이 체크
    		if(jRow.getString(type + ".DATA.Content").length() > 5000) {
    			return result;
    		}
    		
    		//키워드 체크
    		text = "";
    		regexpText = "";
    		if(type.equals("MAIN") || type.equals("SECTION") || type.equals("STORE")){
    			if(!jRow.isNull(type + ".DATA.Title")){
    				text = jRow.getString(type + ".DATA.Title");	
    			}
    			text += " ";
    			if(!jRow.isNull(type + ".DATA.Content")){
    				text += jRow.getString(type + ".DATA.Content");	
    			}
    		}else{
    			if(!jRow.isNull(type + ".DATA.Content")){
    				text = jRow.getString(type + ".DATA.Content");	
    			}
    		}
    		text = text.toLowerCase();
    		regexpText = text.replaceAll(regexpStr, " ");
    		
    		
    		//URL / 사이트명 / 수집원명    		
    		if(type.equals("MAIN") || type.equals("SECTION")){
    			if(!jRow.isNull(type + ".DATA.Link")){
    				url = jRow.getString(type + ".DATA.Link");    		    
    		     }
    			
        		if(!jRow.isNull(type + ".BOARD.Seq")){
        			sb_seq = jRow.getString(type + ".BOARD.Seq");
        		}
    			
				if(s_seq.equals("2196") || s_seq.equals("2199") || s_seq.equals("2201") || s_seq.equals("3883")| s_seq.equals("2195")){
        			if(!jRow.isNull(type + ".BOARD.Name")){
        				s_name = jRow.getString(type + ".BOARD.Name");
            			
        			}
        			if(!jRow.isNull("COMMON.SITE.Name")){
        				sb_name = jRow.getString("COMMON.SITE.Name");                		
        			}
        		}else{
        			if(!jRow.isNull("COMMON.SITE.Name")){
        				s_name = jRow.getString("COMMON.SITE.Name");            			
        			}
        			if(!jRow.isNull(type + ".BOARD.Name")){
        				sb_name = jRow.getString(type + ".BOARD.Name");                		
        			}
        		}	
				
    		}else if(type.equals("TWITTER")){
    			if(!jRow.isNull(type + ".DATA.Link")){
    				url = jRow.getString(type + ".DATA.Link");
    		    }
    			s_name = "Twitter";
				if(!jRow.isNull(type + ".DATA.WriterName")){
					sb_name = jRow.getString(type + ".DATA.WriterName");            		
        		}
    		        		
    		}else if(type.equals("FACEBOOK")){
    			if(!jRow.isNull(type + ".DATA.ID")){
    				url = convertFacebookURL(jRow.getString(type + ".DATA.ID"));
    		    }
    		      
    			s_name = "Facebook";
        		
        		if(!jRow.isNull(type + ".DATA.WriterName")){
        			sb_name = jRow.getString(type + ".DATA.WriterName");        			
        		}				  
    		}else if(type.equals("REPLY")){
    			if(!jRow.isNull("MAIN.DATA.Link")){
    				url = jRow.getString("MAIN.DATA.Link");            		
    		    }
				if(!jRow.isNull("COMMON.SITE.Name")){
					s_name = jRow.getString("COMMON.SITE.Name");        		
        		}
        		
				sb_name = "";
    		}else if(type.equals("STORE")){
    			if(!jRow.isNull(type + ".ITEM.Link")){
    				url = jRow.getString(type + ".ITEM.Link");            		
    		    }   		        		
				
        		if(!jRow.isNull(type + ".ITEM.Title")){
        			s_name = jRow.getString(type + ".ITEM.Title");
        			
    			}
        		sb_name = "";
    		}
    		
    		
    		
    		d_seq = jRow.getString(type + ".DATA.Seq");
    		
    		jIdxInfo = null;
    		//키워드 추출
    		
    		jIdxInfo = getSectionIndexing(text, regexpText, sg_seq, d_seq, s_seq, url, s_name, sb_name, sb_seq);
    		
    		if(jIdxInfo.length() > 0){
    			
    			cDate = stampToDateTime(jRow.getString(type + ".DATA.CrawlStamp"), "yyyyMMdd");
    			
    			//시퀀스 생성
    			//ch_seq = jRow.getString("CH_SEQ");
    			newSeq = getMdSeq(PS_SELECT_META_SEQ_MD_SEQ, type, d_seq);
    			
    			if(newSeq.equals("")) {
    				newSeq = getGenerateSeq(PS_INSERT_META_SEQ, type, d_seq);
    			}
    			
    			if(!newSeq.equals("")) {
    				
    				//전체제외키워드 추출
    				//exKeyword = getExKeyword(text, regexpText);
    				
    				//티어 추출
    				tier = getTier(s_seq);
    				
    				//if(useSame){
    				//유사 처리 채널 설정
    				if(useSame && binarySearch(useSameChanel, sg_seq)>= 0){  
    					//유사필터 조회
    					if(type.equals("MAIN") || type.equals("SECTION") || type.equals("STORE")){
    						title = jRow.getString(type + ".DATA.Title");
    					}else{
    						title = jRow.getString(type + ".DATA.Content");
    					}
    					if(title.length() >500) {
    						title = title.substring(0, 500); 
    					}
    					
    					
    					jSameObj = null;
    					//if(exKeyword.equals("")){
    					jSameObj = getSameFilter(title);	
    					//}	
    				}
    				
    				//자동분석
    				if(useAutoAnalysis_section){
    					//text = jRow.getString(type + ".DATA.Content");
    					autoVo = rw.AutoAnalysis(1, text, "","KOR","");
    					senti = getAutoSentiment(autoVo.getJsonStr());
    					relationKeys = makeAutoRelationWords(autoVo.getJsonStr());
    				}
    				
    				//메타 데이터 생성
    				jObjMeta = makeMeta(jRow, newSeq, sg_seq, s_seq, tier, "", jSameObj,"", senti, relationKeys);
    				
    				
    				//색인데이터 생성
    				for(int i =0; i < jIdxInfo.length(); i++){
    					jObjIdx = new JSONObject(jIdxInfo.getJSONObject(i).toString());
    					jObjIdx.put("1_md_seq", newSeq);
    					arIdx.add(jObjIdx);
    				}
    				
    				
    				//META테이블 저장
    				setExcuteUpdate(PS_INSERT_META, jObjMeta);
    				//IDX테이블 저장
    				setExcuteUpdate(PS_INSERT_IDX, arIdx, true);
    				
    				/*
    				if(s_seq.equals("2196") || s_seq.equals("2199")){	
    					jReplyCntObj = new JSONObject();
    					jReplyCntObj.put("1_md_seq", newSeq);
    					jReplyCntObj.put("2_md_date", cDate);
    					setExcuteUpdate(PS_INSERT_META_REPLY_CNT, jReplyCntObj);	
    	    		}
    				 */
    				
    				
    				//색인 통계 생성
    				arIdx.clear();
    				arIdx = getStatisticsIdx(jIdxInfo, cDate);
    				batchResult = setExcuteUpdate(PS_UPDATE_META_ANA_KEYWORD, arIdx, true);
    				
    				for(int i =0; i < batchResult.length; i++){
    					if(batchResult[i] == 0) {
    						setExcuteUpdate(PS_INSERT_META_ANA_KEYWORD, arIdx.get(i));
    					}
    				}
    				
    				//유사 처리 채널 설정
    				if(useSame && binarySearch(useSameChanel, sg_seq)>= 0){  
    					//유사필터 저장
    					if(jSameObj == null){
    						jReSameObj = null;
    						jReSameObj = new JSONObject();
    						jReSameObj.put("1_md_seq", newSeq);
    						jReSameObj.put("2_md_title", title);
    						jReSameObj.put("3_md_date", jObjMeta.getString("05_md_date"));
    						jReSameObj.put("4_md_same_count", "0");
    						setExcuteUpdate(PS_INSERT_SAME_FILTER,jReSameObj);	
    					}else{
    						jReSameObj = null;
    						jReSameObj = new JSONObject();
    						jReSameObj.put("1_md_same_count", jSameObj.getString("MD_SAME_COUNT"));
    						jReSameObj.put("2_md_seq", jSameObj.getString("MD_PSEQ"));
    						
    						setExcuteUpdate(PS_UPDATE_SAME_FILTER,jReSameObj);
    						setExcuteUpdate(PS_UPDATE_META,jReSameObj);
    					}	
    				}
    			}
    			
    			//초기화
    			arIdx.clear();
    			
    			result = true;
    			
    		}
    	}catch(Exception ex){
    		writeExceptionLog(ex);
    	}
    	return result;
    }
    
    public ArrayList<JSONObject> getStatisticsIdx(JSONArray jIdxInfo, String cDate){
    	ArrayList<JSONObject> result = new ArrayList<JSONObject>();
    	JSONObject jObjIdx1 = null;
    	JSONObject jObjIdx2 = null;
    	ArrayList<String> strTmp = new ArrayList<String>();
    	
    	try {
    		
    		//전체 카운트
    		jObjIdx2 = new JSONObject();
			jObjIdx2.put("1_mak_date", cDate);
			jObjIdx2.put("2_K_XP", "0");
			jObjIdx2.put("3_K_YP", "0");
			jObjIdx2.put("4_K_ZP", "0");
			
			result.add(jObjIdx2);
    		
    		//대그룹 카운트
        	strTmp.clear();
    		for(int i =0; i < jIdxInfo.length(); i++){
    			jObjIdx1 = jIdxInfo.getJSONObject(i);
    			if(!strTmp.contains(jObjIdx1.getString("2_K_XP"))) {
    				strTmp.add(jObjIdx1.getString("2_K_XP"));
    				
    				jObjIdx2 = new JSONObject();
    				jObjIdx2.put("1_mak_date", cDate);
    				jObjIdx2.put("2_K_XP", jObjIdx1.getString("2_K_XP"));
    				jObjIdx2.put("3_K_YP", "0");
    				jObjIdx2.put("4_K_ZP", "0");
    				result.add(jObjIdx2);
    			}
    		}
    		
    		//중그룹 카운트
        	strTmp.clear();
    		for(int i =0; i < jIdxInfo.length(); i++){
    			jObjIdx1 = jIdxInfo.getJSONObject(i);
    			if(!strTmp.contains(jObjIdx1.getString("2_K_XP") + "_" + jObjIdx1.getString("3_K_YP"))) {
    				strTmp.add(jObjIdx1.getString("2_K_XP") + "_" + jObjIdx1.getString("3_K_YP"));
    				
    				jObjIdx2 = new JSONObject();
    				jObjIdx2.put("1_mak_date", cDate);
    				jObjIdx2.put("2_K_XP", jObjIdx1.getString("2_K_XP"));
    				jObjIdx2.put("3_K_YP", jObjIdx1.getString("3_K_YP"));
    				jObjIdx2.put("4_K_ZP", "0");
    				result.add(jObjIdx2);
    			}
    		}
    		
    		
    		strTmp.clear();
    		
    		//키워드 카운트
    		for(int i =0; i < jIdxInfo.length(); i++){
    			jObjIdx2 = new JSONObject(jIdxInfo.getJSONObject(i).toString());
    			jObjIdx2.put("1_mak_date", cDate);
    			
    			result.add(jObjIdx2);        				
    		}
    		

    		
    	}catch (Exception ex) {
    		writeExceptionLog(ex);
		}
    
    	return result;
    }
    
    
    void setProperty(){
    	try{
    		//설정정보 적용
        	for(int i =0; i < jProperty.length(); i++){
        		
        		
        		if(jProperty.getJSONObject(i).getString("P_KEY").equals("apiUrl")){
        			apiUrl = jProperty.getJSONObject(i).getString("P_VALUE");
        		}else if(jProperty.getJSONObject(i).getString("P_KEY").equals("testApiUrl")){
        			testApiUrl = jProperty.getJSONObject(i).getString("P_VALUE");
        		}else if(jProperty.getJSONObject(i).getString("P_KEY").equals("useSame")){
        			if(jProperty.getJSONObject(i).getString("P_VALUE").equals("Y")){
        				useSame = true;	
        			}else{
        				useSame = false;
        			}
        		}else if(jProperty.getJSONObject(i).getString("P_KEY").equals("useCatagory")){
        			if(jProperty.getJSONObject(i).getString("P_VALUE").equals("Y")){
        				useCatagory = true;	
        			}else{
        				useCatagory = false;
        			}
        		}else if(jProperty.getJSONObject(i).getString("P_KEY").equals("useCatagoryReply")){
        			if(jProperty.getJSONObject(i).getString("P_VALUE").equals("Y")){
        				useCatagoryReply = true;	
        			}else{
        				useCatagoryReply = false;
        			}
        		}else if(jProperty.getJSONObject(i).getString("P_KEY").equals("useDataTans")){
        			if(jProperty.getJSONObject(i).getString("P_VALUE").equals("Y")){
        				useDataTans = true;	
        			}else{
        				useDataTans = false;
        			}
        		}else if(jProperty.getJSONObject(i).getString("P_KEY").equals("useAutoAnalysis_meta")){
        			if(jProperty.getJSONObject(i).getString("P_VALUE").equals("Y")){
        				useAutoAnalysis_meta = true;	
        			}else{
        				useAutoAnalysis_meta = false;
        			}
        		}else if(jProperty.getJSONObject(i).getString("P_KEY").equals("useAutoAnalysis_warning")){
        			if(jProperty.getJSONObject(i).getString("P_VALUE").equals("Y")){
        				useAutoAnalysis_warning = true;	
        			}else{
        				useAutoAnalysis_warning = false;
        			}
        		}else if(jProperty.getJSONObject(i).getString("P_KEY").equals("useAutoAnalysis_section")){
        			if(jProperty.getJSONObject(i).getString("P_VALUE").equals("Y")){
        				useAutoAnalysis_section = true;	
        			}else{
        				useAutoAnalysis_section = false;
        			}
        		}else if(jProperty.getJSONObject(i).getString("P_KEY").equals("useAutoAnalysis_reply")){
        			if(jProperty.getJSONObject(i).getString("P_VALUE").equals("Y")){
        				useAutoAnalysis_reply = true;	
        			}else{
        				useAutoAnalysis_reply = false;
        			}
        		}else if(jProperty.getJSONObject(i).getString("P_KEY").equals("useAutoAnalysis_top")){
        			if(jProperty.getJSONObject(i).getString("P_VALUE").equals("Y")){
        				useAutoAnalysis_top = true;	
        			}else{
        				useAutoAnalysis_top = false;
        			}
        		}else if(jProperty.getJSONObject(i).getString("P_KEY").equals("useMemoryCheck")){
        			if(jProperty.getJSONObject(i).getString("P_VALUE").equals("Y")){
        				useMemoryCheck = true;	
        			}else{
        				useMemoryCheck = false;
        			}
        		}else if(jProperty.getJSONObject(i).getString("P_KEY").equals("usePersonalInfo")){
        			if(jProperty.getJSONObject(i).getString("P_VALUE").equals("Y")){
        				usePersonalInfo = true;	
        			}else{
        				usePersonalInfo = false;
        			}
        		}else if(jProperty.getJSONObject(i).getString("P_KEY").equals("telegramUrl")){
        			telegramUrl = jProperty.getJSONObject(i).getString("P_VALUE");
        		}else if(jProperty.getJSONObject(i).getString("P_KEY").equals("useTelegram")){
        			if(jProperty.getJSONObject(i).getString("P_VALUE").equals("Y")){
        				useTelegram = true;	
        			}else{
        				useTelegram = false;
        			}
    			// 추가 2019.07.25 김용현
        		}else if(jProperty.getJSONObject(i).getString("P_KEY").equals("pastApiUrl")){
        			pastApiUrl = jProperty.getJSONObject(i).getString("P_VALUE");
        		}else if(jProperty.getJSONObject(i).getString("P_KEY").equals("usePastData")){
        			if(jProperty.getJSONObject(i).getString("P_VALUE").equals("Y")){
        				usePastData = true;	
        			}else{
        				usePastData = false;
        			}
        		}else if(jProperty.getJSONObject(i).getString("P_KEY").equals("useSameChanel")){
        			useSameChanel = (jProperty.getJSONObject(i).getString("P_VALUE")).split(",");
        		}
        		
        		
        		
        		
        	}
    	} catch(Exception ex) {
        	writeExceptionLog(ex);
        }
    }
    
    
    
    
    void writeExceptionLog(Exception ex){
    	writeExceptionLog(ex, true );
    }
    void writeExceptionLog(Exception ex, boolean chkExit){
    	String print = "";
    	print += "[에러]" + "\n";
    	print += ex.toString() + "\n";
    	for(int i =0; i< ex.getStackTrace().length; i++){
    		print+=ex.getStackTrace()[i] + "\n";
    	}
    	Log.writeExpt(className + "_Exception",print);
    	
    	if(chkExit) {
    		System.exit(1);	
    	}
    }
    
    JSONObject getSameFilter(String title){
    	JSONObject result = null;
    	try{
    		
    		JSONArray jSameData = null;
    		
    		JSONObject jObj = new JSONObject();
        	jObj.put("1_md_title", title);
        	jObj.put("2_md_title", title);
        	
        	jSameData = getExcuteQueryArr(PS_SELECT_SAME_FILTER, jObj);
        	
        	
        	if(jSameData.length() > 0){
        		
        		
        		JSONObject jRow = null;
        		boolean sameChk = false;
        		for(int i =0; i < jSameData.length(); i++ ){
        			jRow = jSameData.getJSONObject(i); 
        			sameChk = false;
        			
        			if(StrCompare(jRow.getString("MD_TITLE"), title) > 75){
        				sameChk = true;
        				break;
        			}
        		}
        		
        		if(sameChk){
        			result = new JSONObject();
        			result.put("MD_PSEQ", jRow.getString("MD_SEQ"));
        			result.put("MD_SAME_COUNT", jRow.getInt("MD_SAME_COUNT") + 1);
        		}
        		
        	}
        	
    	}catch(Exception ex) {
    		writeExceptionLog(ex);
    	}
    	
    	return result;
    	
    }
    /*
    void delData(){
    	
    	try{
    		JSONObject jObj = new JSONObject();
    		JSONArray jReArr = new JSONArray();
    		String md_seqs = "";
    		
    		
    		String pTmp = "";
        	for(int i =0; i < jChannel.length(); i++){
        		jObj = jChannel.getJSONObject(i);
        		
        		pTmp = jObj.getString("CH_SEQ") + "_" + jObj.getString("CH_LAST_DOCID");  
        		jReArr = getExcuteQueryArr(getQuery("SELECT_META_SEQ", pTmp));
        		System.out.println("pTmp : " + pTmp);
        		
        		
        		for(int j =0; j < jReArr.length(); j++){	
        			if(md_seqs.equals("")){
        				md_seqs = jReArr.getJSONObject(j).getString("MD_SEQ");
        			}else{
        				md_seqs += "," + jReArr.getJSONObject(j).getString("MD_SEQ");
        			}
        		}
        	}
        	
        	if(!md_seqs.equals("")){
        		setExcuteUpdate(getQuery("DELETE_META",md_seqs));
        		setExcuteUpdate(getQuery("DELETE_IDX",md_seqs));
        		setExcuteUpdate(getQuery("DELETE_EXCEPTION_META",md_seqs));
        		setExcuteUpdate(getQuery("DELETE_EXCEPTION_IDX",md_seqs));
        		
        		if(useSame){
        			setExcuteUpdate(getQuery("DELETE_SAME_FILTER",md_seqs));	
        		}
        		if(useCatagory){
        			setExcuteUpdate(getQuery("DELETE_IDX_CATEGORY",md_seqs));	
        		}
        		if(useDataTans){
        			setExcuteUpdate(getQuery("DELETE_TRANS_DATA",md_seqs));
        		}
        		
        		setExcuteUpdate(getQuery("DELETE_META_SEQ",md_seqs));
        	}
        	
    	} catch(Exception ex) {
    		writeExceptionLog(ex);
    	}
    	
    }
    */
    
    
    void delSameFilter(){
    	
    	try{
    		
    		//DateUtil du = new DateUtil();
    		
    		String date =  du.addDay_v2(du.getDate("yyyy-MM-dd"), -1) + du.getDate(" HH:mm:dd"); 
    		
    		setExcuteUpdate(getQuery("DELETE_SAME_FILTER_DATE",date));
    		
    	} catch(Exception ex) {
    		writeExceptionLog(ex);
    	}
    	
    }
    
    
    void delSameFilter(String delete_same_filter_sql){
    	
    	try{
    		
    		String date =  du.addDay_v2(du.getDate("yyyy-MM-dd"), -1) + du.getDate(" HH:mm:dd"); 
    		
    		setExcuteUpdate(getQuery(delete_same_filter_sql, date));
    		
    	} catch(Exception ex) {
    		writeExceptionLog(ex);
    	}
    	
    }
    
    JSONObject makeMeta(JSONObject jArData, String md_seq, String sg_seq, String s_seq, String[] tier, String ex_seq, JSONObject jSameObj, String reply_p_code, String senti, String relationKeys){
    	
    	JSONObject result = new JSONObject();
    	
    	try{
    		
    		JSONObject jCopyArData = new JSONObject(jArData.toString());
    		
    		String type = jArData.getString("TYPE");
        	String md_site_name = "";
        	String md_menu_name = "";
        	String md_type = "";
        	String md_date = "";
        	String md_title = "";
        	int md_title_length = 0;
        	String md_url = "";
        	String md_img = "0";
        	String l_alpha = "";
        	String sb_seq = "";
        	String md_content = "";
        	String md_pseq = md_seq;
        	String md_same_count = "0";
        	String issue_check = "N";
        	
        	if(ex_seq.equals("")){
        		ex_seq = "0";
        	}
        	
        	
        	if(type.equals("MAIN") || type.equals("SECTION")){
        		if(s_seq.equals("2196") || s_seq.equals("2199") || s_seq.equals("2201") || s_seq.equals("3883")| s_seq.equals("2195")){
        			if(!jArData.isNull(type + ".BOARD.Name")){
        				md_site_name = jArData.getString(type + ".BOARD.Name");
            			jCopyArData.remove(type + ".BOARD.Name");	
        			}
        			if(!jArData.isNull("COMMON.SITE.Name")){
        				md_menu_name = jArData.getString("COMMON.SITE.Name");
                		jCopyArData.remove("COMMON.SITE.Name");	
        			}
        		}else{
        			if(!jArData.isNull("COMMON.SITE.Name")){
        				md_site_name = jArData.getString("COMMON.SITE.Name");
            			jCopyArData.remove("COMMON.SITE.Name");	
        			}
        			if(!jArData.isNull(type + ".BOARD.Name")){
        				md_menu_name = jArData.getString(type + ".BOARD.Name");
                		jCopyArData.remove(type + ".BOARD.Name");	
        			}
        		}	
        		
        		if(!jArData.isNull(type + ".DATA.InfoType")){
        			md_type = jArData.getString(type + ".DATA.InfoType");
            		jCopyArData.remove(type + ".DATA.InfoType");	
        		}
        		
        		if(!jArData.isNull(type + ".DATA.CrawlStamp")){
        			md_date = stampToDateTime(jArData.getString(type + ".DATA.CrawlStamp"), "yyyy-MM-dd HH:mm:ss");
            		jCopyArData.remove(type + ".DATA.CrawlStamp");	
        		}
        		
        		if(!jArData.isNull(type + ".DATA.Title")){
        			
        			md_title = jArData.getString(type + ".DATA.Title");
        			if(usePersonalInfo){
        				md_title = pu.replacePInfo(md_title);	
        			}
            		jCopyArData.remove(type + ".DATA.Title");	
        		}
        		
        		if(!jArData.isNull(type + ".DATA.Link")){
        			md_url = jArData.getString(type + ".DATA.Link");
            		jCopyArData.remove(type + ".DATA.Link");	
        		}
        		
        		if(!jArData.isNull(type + ".DATA.ImgIs")){
        			md_img = jArData.getString(type + ".DATA.ImgIs");
            		jCopyArData.remove(type + ".DATA.ImgIs");	
        		}
        		
        		if(!jArData.isNull(type + ".DATA.Lang3")){
        			l_alpha = jArData.getString(type + ".DATA.Lang3");
            		jCopyArData.remove(type + ".DATA.Lang3");	
        		}
        		
        		if(!jArData.isNull(type + ".BOARD.Seq")){
        			sb_seq = jArData.getString(type + ".BOARD.Seq");
            		jCopyArData.remove(type + ".BOARD.Seq");	
        		}
        		
        		if(!jArData.isNull(type + ".DATA.Content")){
        			
        			md_content = jArData.getString(type + ".DATA.Content");	
        			if(usePersonalInfo && md_content.length() <= 100000){
        				md_content = pu.replacePInfo(md_content);	
        			}
            		jCopyArData.remove(type + ".DATA.Content");	
        		}
        		
        	}else if(type.equals("TWITTER")){
        		md_site_name = "Twitter";
        		
        		if(!jArData.isNull(type + ".DATA.WriterName")){
        			md_menu_name = jArData.getString(type + ".DATA.WriterName");
            		jCopyArData.remove(type + ".DATA.WriterName");	
        		}
        		
        		md_type = "2";
        		
        		if(!jArData.isNull(type + ".DATA.CrawlStamp")){
        			md_date = stampToDateTime(jArData.getString(type + ".DATA.CrawlStamp"), "yyyy-MM-dd HH:mm:ss");
            		jCopyArData.remove(type + ".DATA.CrawlStamp");	
        		}
        		
        		if(!jArData.isNull(type + ".DATA.Content")){
        			md_title_length = jArData.getString(type + ".DATA.Content").length();
            		if(md_title_length > 40){
            			md_title_length = 40;
            		}
            		md_title = jArData.getString(type + ".DATA.Content").substring(0, md_title_length);
            		if(usePersonalInfo){
        				md_title = pu.replacePInfoForTwitter(md_title);	
        			}
            		jCopyArData.remove(type + ".DATA.Content");	
        		}
        		
        		if(!jArData.isNull(type + ".DATA.Link")){
        			md_url = jArData.getString(type + ".DATA.Link");
            		jCopyArData.remove(type + ".DATA.Link");	
        		}
        		
        		md_img = "0";
        		
        		if(!jArData.isNull(type + ".DATA.Lang3")){
        			l_alpha = jArData.getString(type + ".DATA.Lang3");
            		jCopyArData.remove(type + ".DATA.Lang3");	
        		}
        		
        		sb_seq = "0";
        		
        		if(!jArData.isNull(type + ".DATA.Content")){
        			md_content = jArData.getString(type + ".DATA.Content");
        			if(usePersonalInfo){
        				md_content = pu.replacePInfoForTwitter(md_content);	
        			}
            		jCopyArData.remove(type + ".DATA.Content");	
        		}
        		
        	}else if(type.equals("FACEBOOK")){
        		md_site_name = "Facebook";
        		
        		if(!jArData.isNull(type + ".DATA.WriterName")){
        			md_menu_name = jArData.getString(type + ".DATA.WriterName");
        			jCopyArData.remove(type + ".DATA.WriterName");	
        		}
    			
    			md_type = "2";
    			
    			if(!jArData.isNull(type + ".DATA.CrawlStamp")){
    				md_date = stampToDateTime(jArData.getString(type + ".DATA.CrawlStamp"), "yyyy-MM-dd HH:mm:ss");
        			jCopyArData.remove(type + ".DATA.CrawlStamp");	
    			}
    			
    			if(!jArData.isNull(type + ".DATA.Content")){
    				md_title_length = jArData.getString(type + ".DATA.Content").length();
            		if(md_title_length > 40){
            			md_title_length = 40;
            		}
            		md_title = jArData.getString(type + ".DATA.Content").substring(0, md_title_length);
            		if(usePersonalInfo){
        				md_title = pu.replacePInfo(md_title);	
        			}
            		jCopyArData.remove(type + ".DATA.Content");	
    			}
    			
    			if(!jArData.isNull(type + ".DATA.ID")){
    				md_url = convertFacebookURL(jArData.getString(type + ".DATA.ID"));
            		jCopyArData.remove(type + ".DATA.ID");	
    			}
        		
        		
        		md_img = "0";
        		l_alpha = "KOR";
        		sb_seq = "0";
        		
        		if(!jArData.isNull(type + ".DATA.Content")){
        			md_content = jArData.getString(type + ".DATA.Content");
        			if(usePersonalInfo){
        				md_content = pu.replacePInfo(md_content);	
        			}
            		jCopyArData.remove(type + ".DATA.Content");	
        		}
        	}else if(type.equals("REPLY")){
        		if(!jArData.isNull("COMMON.SITE.Name")){
        			md_site_name = jArData.getString("COMMON.SITE.Name");
        			jCopyArData.remove("COMMON.SITE.Name");	
        		}
        		
    			md_menu_name = "";
    			md_type = "2";
    			
    			if(!jArData.isNull(type + ".DATA.CrawlStamp")){
    				md_date = stampToDateTime(jArData.getString(type + ".DATA.CrawlStamp"), "yyyy-MM-dd HH:mm:ss");
        			jCopyArData.remove(type + ".DATA.CrawlStamp");	
    			}
    			
    			if(!jArData.isNull("MAIN.DATA.Title")){
        			md_title = jArData.getString("MAIN.DATA.Title");
        			if(usePersonalInfo){
        				md_title = pu.replacePInfo(md_title);	
        			}
            		jCopyArData.remove("MAIN.DATA.Title");	
        		}
    			
    			if(!jArData.isNull("MAIN.DATA.Link")){
        			md_url = jArData.getString("MAIN.DATA.Link");
            		jCopyArData.remove("MAIN.DATA.Link");	
        		}
        		
        		
        		md_img = "0";
        		l_alpha = "KOR";
        		sb_seq = "0";
        		
        		if(!jArData.isNull(type + ".DATA.Content")){
        			md_content = jArData.getString(type + ".DATA.Content");
        			if(usePersonalInfo){
        				md_content = pu.replacePInfo(md_content);	
        			}
            		jCopyArData.remove(type + ".DATA.Content");	
        		}
        		
        		md_pseq = reply_p_code; 
        	}else if(type.equals("STORE")){
        			
        		
        		if(!jArData.isNull(type + ".ITEM.Title")){
    				md_site_name = jArData.getString(type + ".ITEM.Title");
        			jCopyArData.remove(type + ".ITEM.Title");	
    			}
        		md_menu_name = "";
        		md_type = "2";
        		
        		if(!jArData.isNull(type + ".DATA.CrawlStamp")){
        			md_date = stampToDateTime(jArData.getString(type + ".DATA.CrawlStamp"), "yyyy-MM-dd HH:mm:ss");
            		jCopyArData.remove(type + ".DATA.CrawlStamp");	
        		}
        		
        		if(!jArData.isNull(type + ".DATA.Title")){
        			
        			md_title = jArData.getString(type + ".DATA.Title");
        			if(usePersonalInfo){
        				md_title = pu.replacePInfo(md_title);	
        			}
            		jCopyArData.remove(type + ".DATA.Title");	
        		}
        		
        		if(!jArData.isNull(type + ".ITEM.Link")){
        			md_url = jArData.getString(type + ".ITEM.Link");
            		jCopyArData.remove(type + ".ITEM.Link");
        		}
        		
        		md_img = "0";
        		
        		if(!jArData.isNull(type + ".DATA.Lang3")){
        			l_alpha = jArData.getString(type + ".DATA.Lang3");
            		jCopyArData.remove(type + ".DATA.Lang3");	
        		}
        		
        		sb_seq = "0";
        		
        		if(!jArData.isNull(type + ".DATA.Content")){
        			
        			md_content = jArData.getString(type + ".DATA.Content");	
        			if(usePersonalInfo && md_content.length() <= 100000){
        				md_content = pu.replacePInfo(md_content);	
        			}
            		jCopyArData.remove(type + ".DATA.Content");	
        		}
        	}
        	
        	if(jSameObj != null){
        		md_same_count = jSameObj.getString("MD_SAME_COUNT");
        		md_pseq = jSameObj.getString("MD_PSEQ");
        		
        	}
        	
    		result.put("01_md_seq", md_seq);
    		result.put("02_md_site_name", md_site_name);
    		result.put("03_md_menu_name", md_menu_name);
    		
    		if(md_type.equals("")) {
    			md_type = "2";
    		}
    		
    		result.put("04_md_type", md_type);
    		
    		result.put("05_md_date", md_date);
    		result.put("06_md_title", md_title);
    		result.put("07_md_url", md_url);
    		result.put("08_md_same_count", md_same_count);
    		result.put("09_md_pseq", md_pseq);
    		result.put("10_s_seq", s_seq);
    		result.put("11_md_img", md_img);
    		result.put("12_l_alpha", l_alpha);
    		result.put("13_ts_type", tier[0]);
    		result.put("14_ts_rank", tier[1]);
    		result.put("15_sb_seq", sb_seq);
    		result.put("16_md_content", md_content);
    		result.put("17_sg_seq", sg_seq);
    		result.put("18_issue_check", issue_check);
    		result.put("19_json_data", jCopyArData.toString());
    		result.put("20_ex_seq", ex_seq);
    		result.put("21_md_senti", senti);
    		result.put("22_md_relation", relationKeys);
    		
    	} catch(Exception ex) {
    		writeExceptionLog(ex);
    	}
    	
    	
    	return result;
    }
    
    
    JSONObject makeTransData(String type, JSONObject jMeta, JSONArray jIdx, JSONArray jCate, int senti, String relationKeys){
    	
    	JSONObject result = new JSONObject();
    	JSONObject jTmp = new JSONObject();
    	String t_xml = "";
    	String t_file_name = "";
    	
    	String xml10pattern = "[^"
	            + "\u0009\r\n"
	            + "\u0020-\uD7FF"
	            + "\uE000-\uFFFD"
	            + "\ud800\udc00-\udbff\udfff"
	            + "]";
    	
    	try{
    		
    		Element root = new Element("row");
    		Document doc = new Document(root);
    		
    		//시퀀스
    		Element seq =new Element("seq");
    		seq.setText(jMeta.getString("01_md_seq"));
    		root.addContent(seq);
    		
    		//채널
    		String channel = "";
    		Element category =new Element("channel");
    		if(type.equals("REPLY")){
    			channel = "portal_reply";
    		}else{
    			channel = getSiteGroup(jMeta.getString("10_s_seq"),"SG_NAME");	
    		}
    		category.setText(channel);
    		root.addContent(category);
    		
    		//사이트명
    		Element site_nm =new Element("site_nm");
    		site_nm.setText(jMeta.getString("02_md_site_name"));
    		root.addContent(site_nm);
    		
    		//수집시간
    		Element crawling_time =new Element("crawling_time");
    		crawling_time.setText(jMeta.getString("05_md_date"));
    		root.addContent(crawling_time);
    		
    		
    		//작성시간
    		JSONObject original_data = new JSONObject(jMeta.getString("19_json_data"));
    		String tmpWriteTime = "";
    		if("MAIN".equals(type)){
    			if(!original_data.isNull(type + ".DATA.WriteTime")){
    				tmpWriteTime = original_data.getString(type + ".DATA.WriteTime");
        		} 
    		} else if("TWITTER".equals(type) || "FACEBOOK".equals(type) || "REPLY".equals(type)){
    			if(!original_data.isNull(type+".DATA.WriteStamp")){
        			String write_stamp = original_data.getString(type+".DATA.WriteStamp");
        			if(!"".equals(write_stamp)){
        				tmpWriteTime = stampToDateTime(write_stamp, "yyyy-MM-dd HH:mm:ss");
        			}
        		} 
    		}
			Element write_time =new Element("write_time");
			write_time.setText(tmpWriteTime);
    		root.addContent(write_time);
    		
    		
    		//제목
    		Element title =new Element("title");
    		title.setContent(new org.jdom.CDATA(jMeta.getString("06_md_title").replaceAll(xml10pattern, "")));
    		root.addContent(title);
    		
    		//링크
    		Element url =new Element("url");
    		url.setContent(new org.jdom.CDATA(jMeta.getString("07_md_url").replaceAll(xml10pattern, "")));
    		root.addContent(url);
    		
    		//수집키워드
    		Element crawling_keyword = new Element("crawling_keyword");
    		Element crawling_keyword_ar = new Element("sub");
    		Element crawling_keyword_sub = null;
    		String xpypzp = "";
    		String keyword = "";
    		String mainKeyword = "";
    		
    		for(int i =0; i< jIdx.length(); i++){
    			jTmp = jIdx.getJSONObject(i);
    			
    			xpypzp = String.format("%03d",jTmp.getInt("2_K_XP")) 
    				   + String.format("%03d",jTmp.getInt("3_K_YP")) 
    				   + String.format("%03d",jTmp.getInt("4_K_ZP")); 
    			
    			crawling_keyword_sub =new Element("row");
    			
    			keyword = getKeywordName(xpypzp, "K_XP_NAME");
        		crawling_keyword_sub.addContent(new Element("crawling_keyword_xp").setText(keyword));
        		
        		keyword = getKeywordName(xpypzp, "K_YP_NAME");
        		crawling_keyword_sub.addContent(new Element("crawling_keyword_yp").setText(keyword));
    			
    			keyword = getKeywordName(xpypzp, "K_VALUE");
    			
    			if(i == 0) {
    				mainKeyword = keyword; 
    			}
    			
        		crawling_keyword_sub.addContent(new Element("crawling_keyword_zp").setText(keyword));
        		
        		
        		crawling_keyword_ar.addContent(crawling_keyword_sub);
    		}
    		crawling_keyword.setContent(crawling_keyword_ar);
    		root.addContent(crawling_keyword);
    		
    		//업무분류키워드
    		Element category_keyword = new Element("category");
    		Element category_keyword_ar = new Element("sub");
    		Element category_keyword_sub = null;
    		xpypzp = "";
    		keyword = "";
    		for(int i =0; i< jCate.length(); i++){
    			jTmp = jCate.getJSONObject(i);
    			
    			xpypzp = String.format("%03d",jTmp.getInt("2_K_XP")) 
     				   + String.format("%03d",jTmp.getInt("3_K_YP")) 
     				   + String.format("%03d",jTmp.getInt("4_K_ZP")); 
     			
     			
    			
    			category_keyword_sub = new Element("row");
    			
    			keyword = getKeywordCategoryName(xpypzp, "K_XP_NAME");
        		category_keyword_sub.addContent(new Element("category_xp").setText(keyword));
        		
        		keyword = getKeywordCategoryName(xpypzp, "K_YP_NAME");
        		category_keyword_sub.addContent(new Element("category_yp").setText(keyword));
        		
        		keyword = getKeywordCategoryName(xpypzp, "K_VALUE");
        		category_keyword_sub.addContent(new Element("category_zp").setText(keyword));
        		
        		category_keyword_ar.addContent(category_keyword_sub);
    		}
    		
    		category_keyword.setContent(category_keyword_ar);
    		root.addContent(category_keyword);
    		
    		//연관키워드 분석
    		Element relation_keyword =new Element("relation_keyword");
    		Element relation_keyword_ar = new Element("sub");
    		Element relation_keyword_sub = null;
    		
    		if(!relationKeys.equals("")){
    			String[] arRelationKey = relationKeys.split(",");
        		for(int i =0; i< arRelationKey.length; i++){
        			relation_keyword_sub = new Element("row");
            		relation_keyword_sub.addContent(arRelationKey[i]);
            		relation_keyword_ar.addContent(relation_keyword_sub);
        		}	
    		}
    		relation_keyword.setContent(relation_keyword_ar);
    		root.addContent(relation_keyword);
    		
    		//성향분석
    		String sentiNm = "";
    		if(senti == 1){
    			sentiNm = "긍정";
    		}else if(senti == 2){
    			sentiNm = "부정";
    		}else if(senti == 3){
    			sentiNm = "중립";
    		}else{
    			sentiNm = "판단불가";
    		}
    		Element sentiment =new Element("sentiment");
    		sentiment.setText(sentiNm);
    		root.addContent(sentiment);
    	
    		
    		//원글번호
    		if(type.equals("REPLY")){
    			Element p_seq =new Element("p_seq");
        		p_seq.setText(jMeta.getString("09_md_pseq"));
        		root.addContent(p_seq);	
    		}
    		
    		
    		//본문
    		Element content =new Element("content");
    		
    		if(!mainKeyword.equals("")) {
    			if(getSiteGroup(jMeta.getString("10_s_seq"),"SG_MAIN_CONTENT").equals("1")) {
        			content.setContent(new org.jdom.CDATA(cutKey(jMeta.getString("16_md_content"),mainKeyword).replaceAll(xml10pattern, "")));		
        		}else {
        			content.setContent(new org.jdom.CDATA(jMeta.getString("16_md_content").replaceAll(xml10pattern, "")));
        		}	
    		}else {
    			content.setContent(new org.jdom.CDATA(jMeta.getString("16_md_content").replaceAll(xml10pattern, "")));
    		}
    		
    		
    		root.addContent(content);
    		
    		
    		XMLOutputter xout=new XMLOutputter();
            Format f=xout.getFormat();
            f.setEncoding("utf-8");
            f.setIndent("\t");
            f.setLineSeparator("\r\n");
            f.setTextMode(Format.TextMode.TRIM);
            xout.setFormat(f);
            
            t_xml = xout.outputString(doc);
    		
    		
    		//파일명에서 채널이 SNS이면 사이트명으로 치환
    		String fileChannel = "";
    		if(jMeta.getString("10_s_seq").equals("7615")){
    			fileChannel = "youtube";
    		}else if(jMeta.getString("10_s_seq").equals("10464")){
    			fileChannel = "twitter";
    		}else if(jMeta.getString("10_s_seq").equals("11377")){
    			fileChannel = "facebook";
    		}else if(jMeta.getString("10_s_seq").equals("5026142")){
    			fileChannel = "instagram";
    		} else{
    			fileChannel = channel;
    		}
    		
    		
    		//t_file_name = fileChannel +"_"+jMeta.getString("01_md_seq")+ "_" + System.currentTimeMillis() +".xml";
    		t_file_name = fileChannel + "_" + System.currentTimeMillis() +".xml";
    		
    		
    		
    		result.put("01_MD_SEQ", jMeta.getString("01_md_seq"));
    		result.put("02_T_XML", t_xml);
    		//result.put("03_T_FILE_NAME", fileChannel+ "\\\\" + t_file_name);
    		result.put("03_T_FILE_NAME", t_file_name);
    		
    		
    		
    	} catch(Exception ex) {
    		writeExceptionLog(ex);
    	}
    	
    	
    	return result;
    }
    
    JSONArray getIndexing(String text, String regexpText, String sg_seq, String d_seq){
    	JSONArray result = new JSONArray();
    	JSONObject jRow = null;
    	
    	try{
    		if(jKeyword != null){
    			
    			JSONObject jObj = null;
    			int[] arSeqs = null;
    			String[] arkey = null;
    			String[] exKeyword = null;
    			String[] exKeyword_bit = null;
    	    	String word = "";
    	    	int op = 0;
    	    	int near_len = 0;
    			int idx = -1;
    			boolean chkExKey = false;
    			
    			for(int i =0; i < jKeyword.length(); i++){
    				jObj = jKeyword.getJSONObject(i);
    				
    				//키워드별 출처그룹 체크
    				if(!sg_seq.equals("")){
    					if(jObj.getString("SG_SEQ_CHK").equals("N")){
        					arSeqs = (int[])jObj.get("SG_SEQS");
        					idx = binarySearch(arSeqs, sg_seq);
        					if(idx == -1){
        						continue;
        					}
        				}	
    				}
    				
    				

    				arkey = null;
    				arkey = (String[])jObj.get("K_VALUE_BIT"); 
    				word = jObj.getString("K_VALUE");
    				op = jObj.getInt("K_OP");
    				near_len = jObj.getInt("K_NEAR_LEN");
    				//키워드 검사
    				if(searchKeyword(text, regexpText, arkey, word, op, near_len)){
    					
    					chkExKey = false;

    					if(!jObj.isNull("EX_KEYWORD")){
    						
    						exKeyword = null;
        					exKeyword = (String[])jObj.get("EX_KEYWORD");
        					
        					for(int j =0; j < exKeyword.length; j++){
        						exKeyword_bit = exKeyword[j].split(" ");
        						//제외키워드 검사
        						if(searchKeyword(text, regexpText, exKeyword_bit, exKeyword[j], op, near_len)){
        							Log.crond(className,"[제외키워드 걸림]d_seq : " + d_seq + " / 제외키워드 : " + exKeyword[j]);
        							chkExKey = true;    							
        							break;
        						}
        					}	
    					}
    					
    					
    					if(!chkExKey){
    						jRow = new JSONObject();
        					jRow.put("2_K_XP", jObj.getString("K_XP"));
        					jRow.put("3_K_YP", jObj.getString("K_YP"));
        					jRow.put("4_K_ZP", jObj.getString("K_ZP"));
        					result.put(jRow);
    					}
    				}
    			}
    		}
    		
    	} catch(Exception ex) {
    		writeExceptionLog(ex);
    	}
    	
    	
    	return result;
    }
    JSONArray getIndexingOp(String text, String regexpText, String sg_seq, String d_seq){
    	JSONArray result = new JSONArray();
    	JSONObject jRow = null;
    	
    	try{
    		if(jKeyword != null){
    			
    			JSONObject jObj = null;
    			int[] arSeqs = null;
    			String[] arkey = null;
    			//String[] exKeyword = null;
    			String[][]exKeyword = null;
    			String[] exKeyword_bit = null;
    			String word = "";
    			int op = 0;
    			int near_len = 0;
    			int idx = -1;
    			boolean chkExKey = false;
    			
    			for(int i =0; i < jKeyword.length(); i++){
    				jObj = jKeyword.getJSONObject(i);
    				
    				//키워드별 출처그룹 체크
    				if(!sg_seq.equals("")){
    					if(jObj.getString("SG_SEQ_CHK").equals("N")){
    						arSeqs = (int[])jObj.get("SG_SEQS");
    						idx = binarySearch(arSeqs, sg_seq);
    						if(idx == -1){
    							continue;
    						}
    					}	
    				}
    				
    				
    				
    				arkey = null;
    				arkey = (String[])jObj.get("K_VALUE_BIT"); 
    				word = jObj.getString("K_VALUE");
    				op = jObj.getInt("K_OP");
    				near_len = jObj.getInt("K_NEAR_LEN");
    				//키워드 검사
    				if(searchKeyword(text, regexpText, arkey, word, op, near_len)){
    					
    					chkExKey = false;
    					
    					if(!jObj.isNull("EX_KEYWORD")){
    						
    						exKeyword = null;
    						exKeyword = (String[][])jObj.get("EX_KEYWORD");
    						
    						for(int j =0; j < exKeyword.length; j++){
    							exKeyword_bit = exKeyword[j][0].split(" ");
    							//제외키워드 검사
    							//if(searchKeyword(text, regexpText, exKeyword_bit, exKeyword[j], op, near_len)){
    							if(searchKeyword(text, regexpText, exKeyword_bit, exKeyword[j][0], Integer.parseInt(exKeyword[j][1]), near_len)){
    								Log.crond(className,"[제외키워드 걸림]d_seq : " + d_seq + " / 제외키워드 : " + exKeyword[j][0]+"(검색옵션:"+exKeyword[j][1]+")");
    								chkExKey = true;    							
    								break;
    							}
    						}	
    					}
    					
    					
    					if(!chkExKey){
    						jRow = new JSONObject();
    						jRow.put("2_K_XP", jObj.getString("K_XP"));
    						jRow.put("3_K_YP", jObj.getString("K_YP"));
    						jRow.put("4_K_ZP", jObj.getString("K_ZP"));
    						result.put(jRow);
    					}
    				}
    			}
    		}
    		
    	} catch(Exception ex) {
    		writeExceptionLog(ex);
    	}
    	
    	
    	return result;
    }
    JSONArray getSectionIndexing(String text, String regexpText, String sg_seq, String d_seq, String s_seq, String url, String site_name, String board_name, String sb_seq){
    	JSONArray result = new JSONArray();
    	JSONObject jRow = null;
    	Log.crond(className,"getSectionIndexing Start");
    	try{
    		if(jKeyword != null){
    			Log.crond(className,"jKeyword>>"+jKeyword.length());
    			JSONObject jObj = null;
    			int[] arSeqs = null;
    			String[] arkey = null;
    			String[] exKeyword = null;
    			String[] exKeyword_bit = null;
    			String word = "";
    			int op = 0;
    			int near_len = 0;
    			int idx = -1;
    			boolean chkExKey = false;
    			boolean chkKeyword = false;
    			
    			String section_type = "";
    			String section_detail_type = "";
    			String search_section_keyword = "";
    			
    			
    			for(int i =0; i < jKeyword.length(); i++){
    				jObj = jKeyword.getJSONObject(i);
    				section_type = "";
    				section_detail_type = "";
    				chkKeyword = false;
    				search_section_keyword = "";
    				
    				
    				/****검색 영역 검사*****/    				
    				// [1] 지정 사이트그룹/사이트 검사
    				// [2] 상세 영역 지정 검사 - 전체, 사이트명, 수집원명, url 등
    				section_type = jObj.getString("K_SECTION_TYPE"); // 1:사이트그룹, 2:사이트
    				section_detail_type = jObj.getString("SKR_TYPE");  // 0:전체, 1:사이트명 , 2:수집원명, 3:url, 4:수집원 번호
    				search_section_keyword = jObj.getString("SKR_KEYWORD"); //검색
    				
    				if("".equals(section_type)) {
    					continue;
    				}
    				
    				
    				
    				//[1] 지정 사이트그룹/사이트 검사
    				//사이트그룹
    				if("1".equals(section_type)) {
    					if("".equals(sg_seq)) {
    						continue;
    					} 
    					
						arSeqs = (int[])jObj.get("SG_SEQS");
						idx = binarySearch(arSeqs, sg_seq);
						if(idx == -1){
							continue;
						}else {
							chkKeyword = true;
						}
						
    				//사이트
    				}else if("2".equals(section_type)) {
    					if("".equals(s_seq)) {
    						continue;    					
    					}
					
    					arSeqs = (int[])jObj.get("S_SEQS");
						idx = binarySearch(arSeqs, s_seq);
						if(idx == -1){
							continue;
						}else {
							chkKeyword = true;
						}
    					
    				}
    				
    				
    				
    				//[2] 상세 영역 지정 검사 - 전체, 사이트명, 수집원명, url , 수집원 번호 등
    				if(chkKeyword) {
    					
    					chkKeyword = false;
    					
    					if("".equals(section_detail_type)) {
    						continue;
    					}
    					
    					//검색 영역 상세가 전체일 경우
    					if("0".equals(section_detail_type)) {
    						chkKeyword = true;
    					}else {    					
    						if(null == search_section_keyword || "".equals(search_section_keyword)) {
    							continue;
    						}
	    					//사이트명
	    					if("1".equals(section_detail_type)) {    						 	
	    						if(site_name.indexOf(search_section_keyword) > -1){
	    							chkKeyword = true;
	    						}
	    						
	    					//수집원명
	    					}else if("2".equals(section_detail_type)) {
	    						if(board_name.indexOf(search_section_keyword) > -1){
	    							chkKeyword = true;
	    						}
	    					//URL
	    					}else if("3".equals(section_detail_type)) {
	    						if(url.indexOf(search_section_keyword) > -1){
	    							chkKeyword = true;
	    						}
	    					//수집원 번호
	    					}else if("4".equals(section_detail_type)) {
	    						if(sb_seq.equals(search_section_keyword)) {
	    							chkKeyword = true;
	    						}
	    							
	    					}
    					}
    					
    				}
    				
    				
    				//검색 영역 통과되어야 키워드 검사 가능
    				if(chkKeyword) {   					
    					
        				arkey = null;
        				arkey = (String[])jObj.get("K_VALUE_BIT"); 
        				word = jObj.getString("K_VALUE");
        				op = jObj.getInt("K_OP");
        				near_len = jObj.getInt("K_NEAR_LEN");
        				
        	
        				//키워드 검사
        				if(searchKeyword(text, regexpText, arkey, word, op, near_len)){
        					
        					chkExKey = false;
        					
        					if(!jObj.isNull("EX_KEYWORD")){
        						
        						exKeyword = null;
        						exKeyword = (String[])jObj.get("EX_KEYWORD");
        						
        						for(int j =0; j < exKeyword.length; j++){
        							exKeyword_bit = exKeyword[j].split(" ");
        							//제외키워드 검사
        							if(searchKeyword(text, regexpText, exKeyword_bit, exKeyword[j], op, near_len)){
        								Log.crond(className,"[제외키워드 걸림]d_seq : " + d_seq + " / 제외키워드 : " + exKeyword[j]);
        								chkExKey = true;    							
        								break;
        							}
        						}	
        					}
        					
        					
        					if(!chkExKey){
        						jRow = new JSONObject();
        						jRow.put("2_K_XP", jObj.getString("K_XP"));
        						jRow.put("3_K_YP", jObj.getString("K_YP"));
        						jRow.put("4_K_ZP", jObj.getString("K_ZP"));
        						result.put(jRow);
        					}
        				}
    					
    					
    				}
    			
    			}
    		}
    		
    	} catch(Exception ex) {
    		writeExceptionLog(ex);
    	}
    	
    	
    	return result;
    }
    
    JSONArray getCategoryIndexing(String text, String regexpText){
    	JSONArray result = new JSONArray();
    	JSONObject jRow = null;
    	
    	try{
    		if(jKeyword_category != null){
    			
    			JSONObject jObj = null;
    			String[] arSeqs = null;
    			String[] arkey = null;
    			//ArrayList<String> exKeyword = new ArrayList<String>();
    			String[] exKeyword = null;
    			String[] exKeyword_bit = null;
    	    	String word = "";
    	    	int op = 0;
    	    	int near_len = 0;
    			int idx = -1;
    			boolean chkExKey = false;
    			
    			for(int i =0; i < jKeyword_category.length(); i++){
    				jObj = jKeyword_category.getJSONObject(i);
    				
    				//키워드별 출처그룹 체크
    				/*
    				if(jObj.getString("SG_SEQ_CHK").equals("N")){
    					arSeqs = (String[])jObj.get("SG_SEQS");
    					idx = binarySearch(arSeqs, sg_seq);
    					if(idx == -1){
    						continue;
    					}
    				}
    				*/

    				arkey = null;
    				arkey = (String[])jObj.get("K_VALUE_BIT"); 
    				word = jObj.getString("K_VALUE");
    				op = jObj.getInt("K_OP");
    				near_len = jObj.getInt("K_NEAR_LEN");
    				//키워드 검사
    				if(searchKeyword(text, regexpText, arkey, word, op, near_len)){
    					
    					chkExKey = false;
    					
    					if(!jObj.isNull("EX_KEYWORD")){
    						exKeyword = null;
        					exKeyword = (String[])jObj.get("EX_KEYWORD");
        					
        					for(int j =0; j < exKeyword.length; j++){
        						exKeyword_bit = exKeyword[j].split(" ");
        						//제외키워드 검사
        						if(searchKeyword(text, regexpText, exKeyword_bit, exKeyword[j], op, near_len)){
        							chkExKey = true;    							
        							break;
        						}
        					}	
    					}
    					
    					
    					if(!chkExKey){
    						jRow = new JSONObject();
        					jRow.put("2_K_XP", jObj.getString("K_XP"));
        					jRow.put("3_K_YP", jObj.getString("K_YP"));
        					jRow.put("4_K_ZP", jObj.getString("K_ZP"));
        					result.put(jRow);
    					}
    				}
    			}
    		}
    		
    	} catch(Exception ex) {
    		writeExceptionLog(ex);
    	}
    	
    	
    	return result;
    }
    
    JSONArray getTopIndexing(String text, String regexpText){
    	JSONArray result = new JSONArray();
    	JSONObject jRow = null;
    	
    	try{
    		if(jKeyword_top != null){
    			
    			JSONObject jObj = null;
    			String[] arSeqs = null;
    			String[] arkey = null;
    			ArrayList<String> exKeyword = new ArrayList<String>();
    			String[] exKeyword_bit = null;
    	    	String word = "";
    	    	int op = 0;
    	    	int near_len = 0;
    			int idx = -1;
    			boolean chkExKey = false;
    			
    			for(int i =0; i < jKeyword_top.length(); i++){
    				jObj = jKeyword_top.getJSONObject(i);
    				
    				//키워드별 출처그룹 체크
    				/*
    				if(jObj.getString("SG_SEQ_CHK").equals("N")){
    					arSeqs = (String[])jObj.get("SG_SEQS");
    					idx = binarySearch(arSeqs, sg_seq);
    					if(idx == -1){
    						continue;
    					}
    				}
    				*/

    				arkey = null;
    				arkey = (String[])jObj.get("K_VALUE_BIT"); 
    				word = jObj.getString("K_VALUE");
    				op = jObj.getInt("K_OP");
    				near_len = jObj.getInt("K_NEAR_LEN");
    				//키워드 검사
    				if(searchKeyword(text, regexpText, arkey, word, op, near_len)){
    					
    					chkExKey = false;
    					
    					if(!jObj.isNull("EX_KEYWORD")){
    						exKeyword.clear();
        					exKeyword = (ArrayList<String>)jObj.get("EX_KEYWORD");
        					
        					for(int j =0; j < exKeyword.size(); j++){
        						exKeyword_bit = exKeyword.get(j).split(" ");
        						//제외키워드 검사
        						if(searchKeyword(text, regexpText, exKeyword_bit, exKeyword.get(j), op, near_len)){
        							chkExKey = true;    							
        							break;
        						}
        					}	
    					}
    					
    					
    					if(!chkExKey){
    						jRow = new JSONObject();
        					jRow.put("2_K_XP", jObj.getString("K_XP"));
        					jRow.put("3_K_YP", jObj.getString("K_YP"));
        					jRow.put("4_K_ZP", jObj.getString("K_ZP"));
        					result.put(jRow);
    					}
    				}
    			}
    		}
    		
    	} catch(Exception ex) {
    		writeExceptionLog(ex);
    	}
    	
    	
    	return result;
    }
    
    String getExKeyword(String text, String regexpText){
    	String result = "";
    	JSONObject jObj = new JSONObject();
    	
    	String ekValue = "";
    	int ekOp = 3;
    	int ekLen = 0;
    	
    	try{
    		if(jAllExKeyword != null){
        		for(int i =0; i < jAllExKeyword.length(); i++){
        			jObj = jAllExKeyword.getJSONObject(i);
        			
        			ekValue = jObj.getString("EK_VALUE");
        			
        			if(!jObj.isNull("EK_OP")) {
        				ekOp = jObj.getInt("EK_OP");
        			}
        			
        			
        			if(!jObj.isNull("EK_NEAR_LEN")) {
        				ekLen = jObj.getInt("EK_NEAR_LEN");
        			}
        			
        			
        			//전체 제외키워드 체크
        			//if(searchKeyword(text, regexpText, null, ekValue, 3, 0)){
        			if(searchKeyword(text, regexpText, null, ekValue, ekOp, ekLen)){
        				result = jObj.getString("EK_SEQ");
        				break;
        			}
            	}	
        	}	
    	} catch(Exception ex) {
    		writeExceptionLog(ex);
    	}
    	return result;
    }
    
    String[] getTier(String sSeq){
    	
    	String[] result = new String[2];
    	result[0] = "4";
    	result[1] = "999";
    	try{
    		int idx = binarySearch(jTierSite, "S_SEQ", sSeq);
    		if(idx >= 0){
    			result[0] = jTierSite.getJSONObject(idx).getString("TS_TYPE");
            	result[1] = jTierSite.getJSONObject(idx).getString("TS_RANK");	
    		}
    	}catch(Exception ex) {
    		writeExceptionLog(ex);
    	}
    	return result;
    }
    
    String getSiteGroup(String sSeq, String key){
    	
    	String result = "";
    	
    	try{
			if(jSite != null){
				//사이트그룹 체크
        		int idx = binarySearch(jSite, "S_SEQ", sSeq);
        		if(idx >= 0){
        			result = jSite.getJSONObject(idx).getString(key);	
        		}
			}
    	} catch(Exception ex) {
    		writeExceptionLog(ex);
    	}
    	return result;
    }
    
    String getKeywordName(String xpypzp, String key){
    	String result = "";
    	try{
			if(jKeyword != null){
				//키워드 체크
        		int idx = binarySearch(jKeyword, "XPYPZP", xpypzp);
        		if(idx >= 0){
        			result = jKeyword.getJSONObject(idx).getString(key);	
        		}
			}
    	} catch(Exception ex) {
    		writeExceptionLog(ex);
    	}
    	return result;
    }
    
    String getKeywordCategoryName(String xpypzp, String key){
    	String result = "";
    	try{
			if(jKeyword_category != null){
				//키워드  카테고리  체크
        		int idx = binarySearch(jKeyword_category, "XPYPZP", xpypzp);
        		if(idx >= 0){
        			result = jKeyword_category.getJSONObject(idx).getString(key);	
        		}
			}
    	} catch(Exception ex) {
    		writeExceptionLog(ex);
    	}
    	return result;
    }
    
    JSONArray reExSite(JSONArray jArr){
    	String[] arExSite = null;
    	try{
    		for(int i =0; i< jArr.length(); i++){
    			arExSite = jArr.getJSONObject(i).getString("EX_VALUE").split("\n");
    			jArr.getJSONObject(i).put("EX_KEYWORD", arExSite);
        	}	
    	} catch(Exception ex) {
    		writeExceptionLog(ex);
        }
    	return jArr;
    }
    
    JSONArray reKeyword(JSONArray jArr, JSONArray jExArr){
    	String[] arSgSeq = null;
    	int[] intArSgSeq = null;
    	ArrayList<String> tmpExKeyword = new ArrayList<String>();
    	String[] tmpExArr = new String[2];
    	String[] arTmpExKeyword = null;
    	
    	try{
    		for(int i =0; i< jArr.length(); i++){
    			arSgSeq = null;
    			arSgSeq = jArr.getJSONObject(i).getString("SG_SEQS").split(",");
    			
    			intArSgSeq = null;
    			intArSgSeq = new int[arSgSeq.length];
    			
    			for(int j= 0; j < intArSgSeq.length; j++){
    				intArSgSeq[j] = Integer.parseInt(arSgSeq[j]);
    			}
    			Arrays.sort(intArSgSeq);
    			
    			jArr.getJSONObject(i).put("SG_SEQS", intArSgSeq);
    			
    			arSgSeq = null;
    			arSgSeq = jArr.getJSONObject(i).getString("K_VALUE").split(" ");
    			jArr.getJSONObject(i).put("K_VALUE_BIT", arSgSeq);
    			
    			tmpExKeyword.clear();
    			
    			for(int j =0; j < jExArr.length(); j++){
    				
    				if(jArr.getJSONObject(i).getString("K_XP").equals(jExArr.getJSONObject(j).getString("K_XP"))
    				   && jArr.getJSONObject(i).getString("K_YP").equals(jExArr.getJSONObject(j).getString("K_YP"))
    				   && jArr.getJSONObject(i).getString("K_ZP").equals(jExArr.getJSONObject(j).getString("K_ZP"))
    				  ){
    					tmpExKeyword.add(jExArr.getJSONObject(j).getString("K_VALUE"));
    				}
    			}
    			
    			if(tmpExKeyword.size() > 0){
    				
    				arTmpExKeyword = null;
    				arTmpExKeyword = new String[tmpExKeyword.size()];
    				
    				for(int j = 0; j < tmpExKeyword.size(); j++){
    					arTmpExKeyword[j] = tmpExKeyword.get(j);
    				}
    				
    				jArr.getJSONObject(i).put("EX_KEYWORD", arTmpExKeyword);	
    			}
        	}	
    	} catch(Exception ex) {
    		writeExceptionLog(ex);
        }
    	return jArr;
    }
    JSONArray reKeywordOp(JSONArray jArr, JSONArray jExArr){
    	String[] arSgSeq = null;
    	int[] intArSgSeq = null;
    	//ArrayList<String> tmpExKeyword = new ArrayList<String>();
    	ArrayList<String[]> tmpExKeyword = new ArrayList<String[]>();
    	String[] tmpExArr = new String[2];
    	//String[] arTmpExKeyword = null;
    	String[][] arTmpExKeyword = null;
    	
    	try{
    		for(int i =0; i< jArr.length(); i++){
    			arSgSeq = null;
    			arSgSeq = jArr.getJSONObject(i).getString("SG_SEQS").split(",");
    			
    			intArSgSeq = null;
    			intArSgSeq = new int[arSgSeq.length];
    			
    			for(int j= 0; j < intArSgSeq.length; j++){
    				intArSgSeq[j] = Integer.parseInt(arSgSeq[j]);
    			}
    			Arrays.sort(intArSgSeq);
    			
    			jArr.getJSONObject(i).put("SG_SEQS", intArSgSeq);
    			
    			arSgSeq = null;
    			arSgSeq = jArr.getJSONObject(i).getString("K_VALUE").split(" ");
    			jArr.getJSONObject(i).put("K_VALUE_BIT", arSgSeq);
    			
    			tmpExKeyword.clear();
    			
    			for(int j =0; j < jExArr.length(); j++){
    				
    				if(jArr.getJSONObject(i).getString("K_XP").equals(jExArr.getJSONObject(j).getString("K_XP"))
    				   && jArr.getJSONObject(i).getString("K_YP").equals(jExArr.getJSONObject(j).getString("K_YP"))
    				   && jArr.getJSONObject(i).getString("K_ZP").equals(jExArr.getJSONObject(j).getString("K_ZP"))
    				  ){
    					//tmpExKeyword.add(jExArr.getJSONObject(j).getString("K_VALUE"));
    					tmpExArr = new String[]{ jExArr.getJSONObject(j).getString("K_VALUE"), jExArr.getJSONObject(j).getString("K_OP")};
    					tmpExKeyword.add(tmpExArr);
    				}
    			}
    			
    			if(tmpExKeyword.size() > 0){
    				
    				arTmpExKeyword = null;
    				//arTmpExKeyword = new String[tmpExKeyword.size()];
    				arTmpExKeyword = new String[tmpExKeyword.size()][2];
    				
    				for(int j = 0; j < tmpExKeyword.size(); j++){
    					arTmpExKeyword[j] = tmpExKeyword.get(j);
    				}
    				
    				jArr.getJSONObject(i).put("EX_KEYWORD", arTmpExKeyword);	
    			}
        	}	
    	} catch(Exception ex) {
    		writeExceptionLog(ex);
        }
    	return jArr;
    }
    
    JSONArray reSectionKeyword(JSONArray jArr, JSONArray jExArr){
    	String[] arSgSeq = null;
    	int[] intArSgSeq = null;
    	ArrayList<String> tmpExKeyword = new ArrayList<String>();
    	String[] arTmpExKeyword = null;
    	
    	try{
    		for(int i =0; i< jArr.length(); i++){
    			//사이트그룹(채널)
    			arSgSeq = null;
    			arSgSeq = jArr.getJSONObject(i).getString("SG_SEQS").split(",");
    			
    			intArSgSeq = null;
    			intArSgSeq = new int[arSgSeq.length];
    			
    			for(int j= 0; j < intArSgSeq.length; j++){
    				if(!"".equals(arSgSeq[j])) {
    					intArSgSeq[j] = Integer.parseInt(arSgSeq[j]);
    					
    				}
    			}
    			Arrays.sort(intArSgSeq);
    			
    			jArr.getJSONObject(i).put("SG_SEQS", intArSgSeq);
    			
    			
    			
    			
    			//사이트
    			arSgSeq = null;
    			arSgSeq = jArr.getJSONObject(i).getString("S_SEQS").split(",");
    			
    			intArSgSeq = null;
    			intArSgSeq = new int[arSgSeq.length];
    			
    			
    			for(int j= 0; j < intArSgSeq.length; j++){
    				if(!"".equals(arSgSeq[j])) {
    					intArSgSeq[j] = Integer.parseInt(arSgSeq[j]);
    				}
    			}
    			Arrays.sort(intArSgSeq);
    			
    			jArr.getJSONObject(i).put("S_SEQS", intArSgSeq);
    			
    			
    			//키워드
    			arSgSeq = null;
    			arSgSeq = jArr.getJSONObject(i).getString("K_VALUE").split(" ");
    			jArr.getJSONObject(i).put("K_VALUE_BIT", arSgSeq);
    			
    			tmpExKeyword.clear();
    			
    			for(int j =0; j < jExArr.length(); j++){
    				
    				if(jArr.getJSONObject(i).getString("K_XP").equals(jExArr.getJSONObject(j).getString("K_XP"))
    						&& jArr.getJSONObject(i).getString("K_YP").equals(jExArr.getJSONObject(j).getString("K_YP"))
    						&& jArr.getJSONObject(i).getString("K_ZP").equals(jExArr.getJSONObject(j).getString("K_ZP"))
    						){
    					tmpExKeyword.add(jExArr.getJSONObject(j).getString("K_VALUE"));
    				}
    			}
    			
    			if(tmpExKeyword.size() > 0){
    				
    				arTmpExKeyword = null;
    				arTmpExKeyword = new String[tmpExKeyword.size()];
    				
    				for(int j = 0; j < tmpExKeyword.size(); j++){
    					arTmpExKeyword[j] = tmpExKeyword.get(j);
    				}
    				
    				jArr.getJSONObject(i).put("EX_KEYWORD", arTmpExKeyword);	
    			}
    		}	
    	} catch(Exception ex) {
    		writeExceptionLog(ex);
    	}
    	return jArr;
    }
    
    JSONArray reTelegramInfo(JSONArray jArr){
    	
    	//사이트 그룹
    	String[] arSgSeq = null;
    	int[] intArSgSeq = null;
    	
    	//사이트
    	String[] arSSeq = null;
    	int[] intArSSeq = null;
    	
    	//키워드
    	JSONArray keyJArr = null;
    	JSONObject keyJObj = null;
    	String[] arKey = null;
    	String[] arKeySub = null;
    	
    	//카테고리
    	JSONArray cateJArr = null;
    	JSONObject cateJObj = null;
    	String[] arCate = null;
    	String[] arCateSub = null;
    	
    	try{
    		for(int i =0; i< jArr.length(); i++){
    			
    			//사이트 그룹 배열화
    			if(!jArr.getJSONObject(i).getString("TS_SG_SEQS").equals("")) {
    				arSgSeq = null;
        			arSgSeq = jArr.getJSONObject(i).getString("TS_SG_SEQS").split(",");
        			
        			intArSgSeq = null;
        			intArSgSeq = new int[arSgSeq.length];
        			
        			for(int j= 0; j < intArSgSeq.length; j++){
        				intArSgSeq[j] = Integer.parseInt(arSgSeq[j]);
        			}
        			Arrays.sort(intArSgSeq);
        			jArr.getJSONObject(i).put("TS_SG_SEQS", intArSgSeq);	
    			}
    			
    			//사이트 배열화
    			if(!jArr.getJSONObject(i).getString("TS_S_SEQS").equals("")) {
    				
        			arSSeq = null;
        			arSSeq = jArr.getJSONObject(i).getString("TS_S_SEQS").split(",");
        			
        			intArSSeq = null;
        			intArSSeq = new int[arSSeq.length];
        			
        			for(int j= 0; j < intArSSeq.length; j++){
        				intArSSeq[j] = Integer.parseInt(arSSeq[j]);
        			}
        			Arrays.sort(intArSSeq);
        			jArr.getJSONObject(i).put("TS_S_SEQS", intArSSeq);
    			}
    			
    			
    			//키워드 배열화
    			if(!jArr.getJSONObject(i).getString("TS_KEYWORDS").equals("")) {
    				
    				keyJArr = new JSONArray();
    				arKey = jArr.getJSONObject(i).getString("TS_KEYWORDS").split(",");
    				
    				for(int j = 0; j < arKey.length; j++) {
    					arKeySub = arKey[j].split("@");
    					keyJObj = new JSONObject();
    					keyJObj.put("xp", arKeySub[0]);
    					keyJObj.put("yp", arKeySub[1]);
    					keyJArr.put(keyJObj);
    				}
    				
    				jArr.getJSONObject(i).put("TS_KEYWORDS", keyJArr);
    				
    			}
    			
    			
    			//카테고리 키워드 배열화
    			if(!jArr.getJSONObject(i).getString("TS_KEYWORD_CATEGORYS").equals("")) {
    				
    				cateJArr = new JSONArray();
    				arCate = jArr.getJSONObject(i).getString("TS_KEYWORD_CATEGORYS").split(",");
    				
    				for(int j = 0; j < arCate.length; j++) {
    					arCateSub = arCate[j].split("@");
    					cateJObj = new JSONObject();
    					cateJObj.put("xp", arCateSub[0]);
    					cateJObj.put("yp", arCateSub[1]);
    					cateJArr.put(cateJObj);
    				}
    				
    				jArr.getJSONObject(i).put("TS_KEYWORD_CATEGORYS", cateJArr);
    				
    			}
    			
    			
    			
    			
        	}	
    	} catch(Exception ex) {
    		writeExceptionLog(ex);
        }
    	return jArr;
    }
    
    Boolean chkExSite(String type , String sSeq, String dUrl){
    	
    	boolean result = false;
    	String[] exkeyword = null;
    	try{
    		
    		//사이트그룹 체크
    		int idx = binarySearch(jExSite, "S_SEQ", sSeq);
    		if(idx >= 0){
    			exkeyword = (String[])jExSite.getJSONObject(idx).get("EX_KEYWORD");	
    		}
    		
    		if(exkeyword != null){
    			for(int i =0; i < exkeyword.length; i++){
    				if(dUrl.indexOf(exkeyword[i]) > -1){
    					result = true;        					
    				}
    			}
    		}
        		
        		
    	} catch(Exception ex) {
    		writeExceptionLog(ex);
    	}
    	return result;
    }
    
    
    
    //데이터 조회(복수)
    JSONArray getExcuteQueryArr(String sql){	
    	JSONArray result = new JSONArray();
    	JSONObject jObj = null;

    	Statement stmt = null;
        ResultSet rs = null;
        ResultSetMetaData rsMeta = null;
        try{
        	stmt = dbconn1.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				jObj = new JSONObject();
				rsMeta = rs.getMetaData();
				for(int i =1; i <= rsMeta.getColumnCount(); i++){
					
					jObj.put(rsMeta.getColumnName(i), rs.getString(rsMeta.getColumnName(i)));	
				}
				
				result.put(jObj);
			}
        } catch(Exception ex) {
        	writeExceptionLog(ex);
        } finally {
            if (rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
        }
        
        //System.out.println(result.toString());
        
        return result;
    }
    //데이터 조회(복수)
    JSONArray getExcuteQueryArr(PreparedStatement pstmt, JSONObject jObj){	
    	JSONArray result = new JSONArray();
    	Iterator<String> keys = null;
    	String key = "";
    	String value = "";
    	int idx = 0;
    	
    	ResultSet rs = null;
        ResultSetMetaData rsMeta = null;
    	
        try{
        	if(jObj != null){
        		pstmt.clearParameters();
        		
        		keys = jObj.sortedKeys();
        		idx = 0;
        		while(keys.hasNext()){
        			key = keys.next();
        			value = jObj.getString(key);
        			pstmt.setString(++idx, value); 
        		}	
        	}
        	rs = pstmt.executeQuery();
        	while(rs.next()){
				jObj = new JSONObject();
				rsMeta = rs.getMetaData();
				for(int i =1; i <= rsMeta.getColumnCount(); i++){
					jObj.put(rsMeta.getColumnName(i), rs.getString(rsMeta.getColumnName(i)));	
				}
				result.put(jObj);
			}
        } catch(Exception ex) {
        	writeExceptionLog(ex);
        }
        return result;
    }
    
    //데이터 조회(단일)
    JSONObject getExcuteQueryObj(String sql){	
    	
    	JSONObject result = new JSONObject();

    	Statement stmt = null;
        ResultSet rs = null;
        ResultSetMetaData rsMeta = null;
        try{
        	stmt = dbconn1.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()){
				rsMeta = rs.getMetaData();
				for(int i =1; i <= rsMeta.getColumnCount(); i++){
					result.put(rsMeta.getColumnName(i), rs.getString(rsMeta.getColumnName(i)));	
				}
			}
        } catch(Exception ex) {
        	writeExceptionLog(ex);
        } finally {
            if (rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
        }
        
        return result;
    }
    //데이터 조회(단일)
    JSONObject getExcuteQueryObj(PreparedStatement pstmt, JSONObject jObj){	
    	JSONObject result = new JSONObject();
    	Iterator<String> keys = null;
    	String key = "";
    	String value = "";
    	int idx = 0;
    	
    	ResultSet rs = null;
        ResultSetMetaData rsMeta = null;
    	
        try{
        	if(jObj != null){
        		pstmt.clearParameters();
        		
        		keys = jObj.sortedKeys();
        		idx = 0;
        		while(keys.hasNext()){
        			key = keys.next();
        			value = jObj.getString(key);
        			pstmt.setString(++idx, value); 
        		}	
        	}
        	rs = pstmt.executeQuery();
        	if(rs.next()){
				rsMeta = rs.getMetaData();
				for(int i =1; i <= rsMeta.getColumnCount(); i++){
					result.put(rsMeta.getColumnName(i), rs.getString(rsMeta.getColumnName(i)));	
				}
        	}
       
        } catch(Exception ex) {
        	writeExceptionLog(ex);
        }
        return result;
    }
    
    //데이터 저장
    int[] setExcuteUpdate(PreparedStatement pstmt, ArrayList<JSONObject> arData, boolean duplicatePass){	
    	int[] result = null;
    	JSONObject jObj = new JSONObject();
    	Iterator<String> keys = null;
    	String key = "";
    	String value = "";
    	int idx = 0;
    	
        try{
        	
        	for(int i = 0; i < arData.size(); i++){
        		jObj = arData.get(i);
        	
        		pstmt.clearParameters();
        		
        		keys = jObj.sortedKeys();
        		idx = 0;
        		while(keys.hasNext()){
        			key = keys.next();
        			value = jObj.getString(key);
        			pstmt.setString(++idx, value); 
        		}
        		pstmt.addBatch();
        	}
        	result = pstmt.executeBatch();
        	//result = true;
        } catch(SQLException ex) {
        	
        	if(ex.getErrorCode() == 1062) {
        		if(duplicatePass) {
            		//그냥 넘김
            	}else {
            		writeExceptionLog(ex);	
            	}
        	}else {
        		writeExceptionLog(ex);	
        	}
        	
        }catch(Exception ex) {
        	writeExceptionLog(ex);
        }
        return result;
    }
    boolean setExcuteUpdate(PreparedStatement pstmt, JSONObject jObj){	
    	boolean result = false;
    	Iterator<String> keys = null;
    	String key = "";
    	String value = "";
    	int idx = 0;
    	
        try{
        	if(jObj != null){
        		pstmt.clearParameters();
        		
        		keys = jObj.sortedKeys();
        		idx = 0;
        		while(keys.hasNext()){
        			key = keys.next();
        			value = jObj.getString(key);
        			pstmt.setString(++idx, value); 
        		}	
        	}
    		pstmt.executeUpdate();
        	result = true;
        	
        } catch(Exception ex) {
        	writeExceptionLog(ex);
        }
        return result;
    }
    boolean setExcuteUpdate(String sql){
    	boolean result = false;
    	
    	Statement stmt = null;
        try{
        	stmt = dbconn1.createStatement();
			stmt.executeUpdate(sql);
			result = true;
        } catch(Exception ex) {
        	writeExceptionLog(ex);
        } finally {
            if (stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
        }
        
        return result;
    }
    
    //시퀀스 생성
    String getGenerateSeq(PreparedStatement pstmt, String ch_seq, String d_seq){
    	
    	ResultSet rs = null;
   		String lastKey = "";
   		
   		try{
   			
   			pstmt.clearParameters();
			pstmt.setString(1, ch_seq);
			pstmt.setString(2, d_seq);
			
			pstmt.executeUpdate();
			
			rs = pstmt.getGeneratedKeys(); 
			if(rs.next()){
				lastKey = rs.getString(1);
			}
   		}catch(SQLException ex) {
   			
   			if(ex.getErrorCode() == 1062){
  				//체널과 문서번호가 같으면 빈값으로 넘김
   			}else {
   				writeExceptionLog(ex);	
   			}
   		}catch(Exception ex){
   			writeExceptionLog(ex);
   		}finally {
            if (rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
        }
   		
   		return lastKey;
    }
    
    //시퀀스 조회
    String getMdSeq(PreparedStatement pstmt, String ch_seq, String d_seq){
    	
    	ResultSet rs = null;
   		String md_seq = "";
   		
   		try{
   			
   			pstmt.clearParameters();
			pstmt.setString(1, ch_seq);
			pstmt.setString(2, d_seq);
			
			rs = pstmt.executeQuery();
			if(rs.next()){
				md_seq = rs.getString(1);
			}
   		}catch(Exception ex){
   			writeExceptionLog(ex);
   		}finally {
            if (rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
        }
   		
   		return md_seq;
    }
    
    
    
    String getQuery(String key, String tmp){
    	StringBuffer sb = new StringBuffer();
    	if(key.equals("PROPERTY")){
    		
    		sb.append("#설정값\n");
    		sb.append(" SELECT P_KEY, P_VALUE FROM PROPERTY\n");
    		
    	}else if(key.equals("SITE")){
    		
    		sb.append("#사이트조회\n");
    		//sb.append(" SELECT SG_SEQ, S_SEQ, S_NAME FROM SG_S_RELATION ORDER BY S_SEQ 	\n");
    		sb.append("SELECT A.SG_SEQ				\n");
    		sb.append("     , A.S_SEQ				\n");
    		sb.append("     , A.S_NAME 				\n");
    		sb.append("     , B.SG_NAME				\n");
    		sb.append("     , B.SG_MAIN_CONTENT		\n");
    		sb.append("  FROM SG_S_RELATION A		\n");
    		sb.append("     , SITE_GROUP B			\n");
    		sb.append(" WHERE A.SG_SEQ = B.SG_SEQ	\n");
    		sb.append(" ORDER BY A.S_SEQ			\n");
    		
    	}else if(key.equals("TIER_SITE")){
    		
    		sb.append("#티어 사이트 조회\n");
    		sb.append(" SELECT SG_SEQ, S_SEQ, TS_TYPE, TS_RANK FROM TIER_SITE A, SG_S_RELATION B WHERE A.TS_SEQ = B.S_SEQ 	\n");
    		
    	}else if(key.equals("SG_SEQS")){
    		
    		sb.append("#출처그룹 조회\n");
    		sb.append("SELECT GROUP_CONCAT(SG_SEQ) AS SG_SEQS FROM SITE_GROUP	\n");
    		
    	}else if(key.equals("KEYWORD")){
    		
    		String[] arTmp = tmp.split("@");
    		
    		sb.append("#키워드 조회\n");
    		sb.append("SELECT A.*																												\n");
        	sb.append("     , B.SG_SEQS 																										\n");
        	sb.append("     , (SELECT COUNT(*) FROM KEYWORD WHERE K_TYPE >= 11 AND K_XP = A.K_XP AND K_YP = A.K_YP AND K_ZP = A.K_ZP) AS EX_CNT	\n");
        	sb.append("     , IF(B.SG_SEQS = '"+arTmp[0]+"','Y','N') AS SG_SEQ_CHK																	\n");
        	sb.append("     , CONCAT(LPAD(A.K_XP,3,'0'), LPAD(A.K_YP,3,'0'), LPAD(A.K_ZP,3,'0')) AS XPYPZP											\n");
        	sb.append("     , (SELECT K_VALUE FROM KEYWORD WHERE K_XP = A.K_XP AND K_YP = 0 AND K_ZP = 0 LIMIT 1) AS K_XP_NAME			\n");
        	sb.append("     , (SELECT K_VALUE FROM KEYWORD WHERE K_XP = A.K_XP AND K_YP = A.K_YP AND K_ZP = 0 LIMIT 1) AS K_YP_NAME	\n");
        	sb.append("  FROM (SELECT K_SEQ, K_XP, K_YP, K_ZP, K_OP, LOWER(K_VALUE) AS K_VALUE, K_NEAR_LEN FROM KEYWORD WHERE K_TYPE = 2 AND K_PRC = "+arTmp[1]+" ) A 									\n");
        	sb.append("     , (SELECT K_XP, IFNULL(SG_SEQS,'') AS SG_SEQS FROM KEYWORD WHERE K_YP = 0 AND K_ZP = 0) B							\n");		
        	sb.append(" WHERE A.K_XP = B.K_XP																									\n");
        	sb.append(" ORDER BY A.K_XP,A.K_YP,A.K_ZP																							\n");
        	
    	}else if(key.equals("KEYWORD_CATEGORY")){
    		
    		sb.append("#업무분류사전 조회\n");
    		sb.append("SELECT A.*																												\n");
        	sb.append("     , B.SG_SEQS 																										\n");
        	sb.append("     , (SELECT COUNT(*) FROM KEYWORD_CATEGORY WHERE K_TYPE >= 11 AND K_XP = A.K_XP AND K_YP = A.K_YP AND K_ZP = A.K_ZP) AS EX_CNT	\n");
        	sb.append("     , IF(B.SG_SEQS = '"+tmp+"','Y','N') AS SG_SEQ_CHK			\n");
        	sb.append("     , CONCAT(LPAD(A.K_XP,3,'0'), LPAD(A.K_YP,3,'0'), LPAD(A.K_ZP,3,'0')) AS XPYPZP											\n");
        	sb.append("     , (SELECT K_VALUE FROM KEYWORD_CATEGORY WHERE K_XP = A.K_XP AND K_YP = 0 AND K_ZP = 0 LIMIT 1) AS K_XP_NAME			\n");
        	sb.append("     , (SELECT K_VALUE FROM KEYWORD_CATEGORY WHERE K_XP = A.K_XP AND K_YP = A.K_YP AND K_ZP = 0 LIMIT 1) AS K_YP_NAME	\n");
        	sb.append("  FROM (SELECT K_SEQ, K_XP, K_YP, K_ZP, K_OP, LOWER(K_VALUE) AS K_VALUE, K_NEAR_LEN FROM KEYWORD_CATEGORY WHERE K_TYPE = 2 ) A 									\n");
        	sb.append("     , (SELECT K_XP, IFNULL(SG_SEQS,'') AS SG_SEQS FROM KEYWORD_CATEGORY WHERE K_YP = 0 AND K_ZP = 0) B							\n");		
        	sb.append(" WHERE A.K_XP = B.K_XP																									\n");
        	sb.append(" ORDER BY A.K_XP,A.K_YP,A.K_ZP																							\n");
        	
    	}else if(key.equals("KEYWORD_TOP")){
    		
    		sb.append("#업무분류사전 조회\n");
    		sb.append("SELECT A.*																												\n");
        	sb.append("     , B.SG_SEQS 																										\n");
        	sb.append("     , (SELECT COUNT(*) FROM KEYWORD_TOP WHERE K_TYPE >= 11 AND K_XP = A.K_XP AND K_YP = A.K_YP AND K_ZP = A.K_ZP) AS EX_CNT	\n");
        	sb.append("     , 'N' AS SG_SEQ_CHK			\n");
        	sb.append("     , CONCAT(LPAD(A.K_XP,3,'0'), LPAD(A.K_YP,3,'0'), LPAD(A.K_ZP,3,'0')) AS XPYPZP											\n");
        	sb.append("     , (SELECT K_VALUE FROM KEYWORD_TOP WHERE K_XP = A.K_XP AND K_YP = 0 AND K_ZP = 0 LIMIT 1) AS K_XP_NAME			\n");
        	sb.append("     , (SELECT K_VALUE FROM KEYWORD_TOP WHERE K_XP = A.K_XP AND K_YP = A.K_YP AND K_ZP = 0 LIMIT 1) AS K_YP_NAME	\n");
        	sb.append("  FROM (SELECT K_SEQ, K_XP, K_YP, K_ZP, K_OP, LOWER(K_VALUE) AS K_VALUE, K_NEAR_LEN FROM KEYWORD_TOP WHERE K_TYPE = 2 ) A 									\n");
        	sb.append("     , (SELECT K_XP, IFNULL(SG_SEQS,'') AS SG_SEQS FROM KEYWORD_TOP WHERE K_YP = 0 AND K_ZP = 0) B							\n");		
        	sb.append(" WHERE A.K_XP = B.K_XP																									\n");
        	sb.append(" ORDER BY A.K_XP,A.K_YP,A.K_ZP																							\n");
        	
    	}else if(key.equals("KEYWORD_WARNING")){
    		
    		sb.append("#워닝키워드 조회\n");
    		sb.append("SELECT A.*																														\n");
        	sb.append("     , B.SG_SEQS 																												\n");
        	sb.append("     , (SELECT COUNT(*) FROM KEYWORD_WARNING WHERE K_TYPE >= 11 AND K_XP = A.K_XP AND K_YP = A.K_YP AND K_ZP = A.K_ZP) AS EX_CNT	\n");
        	sb.append("     , IF(B.SG_SEQS = '"+tmp+"','Y','N') AS SG_SEQ_CHK																			\n");
        	sb.append("     , CONCAT(LPAD(A.K_XP,3,'0'), LPAD(A.K_YP,3,'0'), LPAD(A.K_ZP,3,'0')) AS XPYPZP												\n");
        	sb.append("     , (SELECT K_VALUE FROM KEYWORD_WARNING WHERE K_XP = A.K_XP AND K_YP = 0 AND K_ZP = 0 LIMIT 1) AS K_XP_NAME					\n");
        	sb.append("     , (SELECT K_VALUE FROM KEYWORD_WARNING WHERE K_XP = A.K_XP AND K_YP = A.K_YP AND K_ZP = 0 LIMIT 1) AS K_YP_NAME				\n");
        	sb.append("  FROM (SELECT K_SEQ, K_XP, K_YP, K_ZP, K_OP, LOWER(K_VALUE) AS K_VALUE, K_NEAR_LEN FROM KEYWORD_WARNING WHERE K_TYPE = 2 ) A 	\n");
        	sb.append("     , (SELECT K_XP, IFNULL(SG_SEQS,'') AS SG_SEQS FROM KEYWORD_WARNING WHERE K_YP = 0 AND K_ZP = 0) B							\n");		
        	sb.append(" WHERE A.K_XP = B.K_XP																											\n");
        	sb.append(" ORDER BY A.K_XP,A.K_YP,A.K_ZP																									\n");

    	}else if(key.equals("KEYWORD_WARNING_CAPITAL")){
    		
    		sb.append("#워닝(케피탈)키워드 조회\n");
    		sb.append("SELECT A.*																														\n");
        	sb.append("     , B.SG_SEQS 																												\n");
        	sb.append("     , (SELECT COUNT(*) FROM KEYWORD_WARNING_CAPITAL WHERE K_TYPE >= 11 AND K_XP = A.K_XP AND K_YP = A.K_YP AND K_ZP = A.K_ZP) AS EX_CNT	\n");
        	sb.append("     , IF(B.SG_SEQS = '"+tmp+"','Y','N') AS SG_SEQ_CHK																			\n");
        	sb.append("     , CONCAT(LPAD(A.K_XP,3,'0'), LPAD(A.K_YP,3,'0'), LPAD(A.K_ZP,3,'0')) AS XPYPZP												\n");
        	sb.append("     , (SELECT K_VALUE FROM KEYWORD_WARNING_CAPITAL WHERE K_XP = A.K_XP AND K_YP = 0 AND K_ZP = 0 LIMIT 1) AS K_XP_NAME					\n");
        	sb.append("     , (SELECT K_VALUE FROM KEYWORD_WARNING_CAPITAL WHERE K_XP = A.K_XP AND K_YP = A.K_YP AND K_ZP = 0 LIMIT 1) AS K_YP_NAME				\n");
        	sb.append("  FROM (SELECT K_SEQ, K_XP, K_YP, K_ZP, K_OP, LOWER(K_VALUE) AS K_VALUE, K_NEAR_LEN FROM KEYWORD_WARNING_CAPITAL WHERE K_TYPE = 2 ) A 	\n");
        	sb.append("     , (SELECT K_XP, IFNULL(SG_SEQS,'') AS SG_SEQS FROM KEYWORD_WARNING_CAPITAL WHERE K_YP = 0 AND K_ZP = 0) B							\n");		
        	sb.append(" WHERE A.K_XP = B.K_XP																											\n");
        	sb.append(" ORDER BY A.K_XP,A.K_YP,A.K_ZP																									\n");
    	
    	}else if(key.equals("KEYWORD_SECTION")){
    		
    		sb.append("#영역지정키워드 조회					                                                                                                                   \n");
    		sb.append("   SELECT  A.K_SEQ                                                                                                                                 \n");
    		sb.append("                  , A.K_XP                                                                                                                         \n");
    		sb.append("                  , A.K_YP                                                                                                                         \n");
    		sb.append("                  , A.K_ZP                                                                                                                         \n");
    		sb.append("                  , A.K_OP                                                                                                                         \n");
    		sb.append("                  , LOWER(A.K_VALUE) AS K_VALUE                                                                                                                      \n");
    		sb.append("                  , A.K_NEAR_LEN                                                                                                                   \n");
    		sb.append("                  , (SELECT COUNT(*) FROM KEYWORD_SECTION WHERE K_TYPE >= 11 AND K_XP = A.K_XP AND K_YP = A.K_YP AND K_ZP = A.K_ZP) AS EX_CNT	  \n");   
    		sb.append("                  , CONCAT(LPAD(A.K_XP,3,'0'), LPAD(A.K_YP,3,'0'), LPAD(A.K_ZP,3,'0')) AS XPYPZP													  \n");
    		sb.append("                  , (SELECT K_VALUE FROM KEYWORD_SECTION WHERE K_XP = A.K_XP AND K_YP = 0 AND K_ZP = 0 LIMIT 1) AS K_XP_NAME					      \n");
    		sb.append("                  , (SELECT K_VALUE FROM KEYWORD_SECTION WHERE K_XP = A.K_XP AND K_YP = A.K_YP AND K_ZP = 0 LIMIT 1) AS K_YP_NAME		          \n");
    		sb.append("                  , B.K_SECTION_TYPE                                                                                                               \n");
    		sb.append("                  , IFNULL(B.S_SEQS,'') AS S_SEQS                                                                                                                       \n");
    		sb.append("                  , IFNULL(B.SG_SEQS,'') AS SG_SEQS                                                                                                                     \n");
    		sb.append("                  , B.SKR_TYPE                                                                                                                     \n");
    		sb.append("                  , B.SKR_KEYWORD                                                                                                                  \n");
    		sb.append("              FROM KEYWORD_SECTION A                                                                                                               \n");
    		sb.append("              LEFT OUTER JOIN  SECTION_KEY_RELATION B ON B.K_XP = A.K_XP AND  B.K_YP = A.K_YP                                                      \n");
    		sb.append("            WHERE A.K_USEYN = 'Y'                                                                                                                  \n");
    		sb.append("              AND A.K_TYPE = 2	                                                                                                                  \n");
    		sb.append("              AND A.K_ZP > 0																														  \n");
    		sb.append("              AND B.K_SECTION_TYPE IS NOT NULL																													  \n");
    		sb.append("             ORDER BY A.K_XP, A.K_YP																												  \n");	
        	                       
    	}else if(key.equals("EX_KEYWORD")){
    		
    		sb.append("#제외키워드 조회\n");
    		sb.append(" SELECT K_SEQ, K_XP, K_YP, K_ZP, K_OP, LOWER(K_VALUE) AS K_VALUE, '' AS SG_SEQS, '' AS EX_CNT, K_NEAR_LEN, '' AS SG_SEQ_CHK FROM KEYWORD \n");
        	sb.append(" WHERE K_TYPE >= 11 AND K_XP	\n");
        	
    	}else if(key.equals("EX_KEYWORD_CATEGORY")){
    		
    		sb.append("#제외업무사전 조회\n");
    		sb.append(" SELECT K_SEQ, K_XP, K_YP, K_ZP, K_OP, LOWER(K_VALUE) AS K_VALUE, '' AS SG_SEQS, '' AS EX_CNT, K_NEAR_LEN, '' AS SG_SEQ_CHK FROM KEYWORD_CATEGORY \n");
        	sb.append(" WHERE K_TYPE >= 11 AND K_XP	\n");
        	
    	}else if(key.equals("EX_KEYWORD_WARNING")){
    		
    		sb.append("#제외 워닝키워드 조회\n");
    		sb.append(" SELECT K_SEQ, K_XP, K_YP, K_ZP, K_OP, LOWER(K_VALUE) AS K_VALUE, '' AS SG_SEQS, '' AS EX_CNT, K_NEAR_LEN, '' AS SG_SEQ_CHK FROM KEYWORD_WARNING \n");
        	sb.append(" WHERE K_TYPE >= 11 AND K_XP	\n");
    
    	}else if(key.equals("EX_KEYWORD_WARNING_CAPITAL")){
    		
    		sb.append("#제외 워닝(캐피탈)키워드 조회\n");
    		sb.append(" SELECT K_SEQ, K_XP, K_YP, K_ZP, K_OP, LOWER(K_VALUE) AS K_VALUE, '' AS SG_SEQS, '' AS EX_CNT, K_NEAR_LEN, '' AS SG_SEQ_CHK FROM KEYWORD_WARNING_CAPITAL \n");
        	sb.append(" WHERE K_TYPE >= 11 AND K_XP	\n");
    
    	}else if(key.equals("EX_KEYWORD_SECTION")){
    		
    		sb.append("#영역 지정 키워드 조회\n");
    		sb.append(" SELECT K_SEQ, K_XP, K_YP, K_ZP, K_OP, LOWER(K_VALUE) AS K_VALUE, K_NEAR_LEN  FROM KEYWORD_SECTION  \n");
    		sb.append(" WHERE K_TYPE >= 11  \n");
        	
    	}else if(key.equals("EX_KEYWORD_TOP")){
    		
    		sb.append("#제외업무사전 조회\n");
    		sb.append(" SELECT K_SEQ, K_XP, K_YP, K_ZP, K_OP, LOWER(K_VALUE) AS K_VALUE, '' AS SG_SEQS, '' AS EX_CNT, K_NEAR_LEN, '' AS SG_SEQ_CHK FROM KEYWORD_TOP \n");
        	sb.append(" WHERE K_TYPE >= 11 \n");
        	
    	}else if(key.equals("ALL_EX_KEYWORD")){
    		
    		sb.append("#전체제외키워드 조회\n");
    		//sb.append(" SELECT EK_SEQ, LOWER(EK_VALUE) AS EK_VALUE, IFNULL(K_SEQS,'') AS K_SEQS, IFNULL(S_SEQS,'') AS S_SEQS FROM EXCEPTION_KEYWORD \n");
    		sb.append(" SELECT EK_SEQ, LOWER(EK_VALUE) AS EK_VALUE, IFNULL(K_SEQS,'') AS K_SEQS, IFNULL(S_SEQS,'') AS S_SEQS, EK_OP, EK_NEAR_LEN FROM EXCEPTION_KEYWORD \n");
    		
    	}else if(key.equals("EX_SITE")){
    		
    		sb.append("#제외사이트 조회\n");
    		sb.append(" SELECT S_SEQ, EX_KEYWORD FROM EX_SITE \n");
    		
    	}else if(key.equals("EX_URL")){
    		
    		sb.append("#제외URL 조회\n");
    		sb.append(" SELECT IFNULL(S_SEQ,'') AS S_SEQ, IFNULL(EX_URL,'') AS EX_URL FROM EX_SITE_URL WHERE USEYN ='Y' \n");
    		
    	}else if(key.equals("CHANNEL")){
    		
    		sb.append("#체널조회\n");
    		sb.append("SELECT CH_SEQ, CH_KEY, CH_API_KEY, CH_LAST_DATE, CH_LAST_DOCID, CH_LIST_CNT, 0 AS IDX, 0 AS MAX_IDX, S_SEQS, K_PRC FROM CHANNEL WHERE CH_USEYN = 'Y' AND K_PRC = "+tmp+" ORDER BY CH_SEQ \n");
    		
    	}else if(key.equals("INSERT_META_SEQ")){
    		
    		sb.append("#시퀀스 생성\n");
    		sb.append("INSERT INTO META_SEQ (CH_KEY, D_SEQ) VALUES (?, ?)\n");
    		
    	}else if(key.equals("INSERT_META")){
    		
    		sb.append("#수집테이블저장\n");
    		sb.append("REPLACE INTO META (           								\n");
    		sb.append("		md_seq, md_site_name, md_menu_name, md_type, md_date,	\n");
    		sb.append("		md_title, md_url, md_same_count, md_pseq, s_seq, 		\n");
    		sb.append("		md_img, l_alpha, ts_type, ts_rank, sb_seq, 				\n");
    		sb.append("		md_content, sg_seq, issue_check, json_data, ek_seq,   	\n");
    		sb.append("		md_senti, md_relation  									\n");
    		sb.append(") VALUES (													\n");
    		sb.append("		?,?,?,?,?,												\n");
    		sb.append("		?,?,?,?,?,												\n");
    		sb.append("		?,?,?,?,?,												\n");
    		sb.append("		?,?,?,?,?,												\n");
    		sb.append("		?,?														\n");
    		sb.append(")															\n");
    		
    	}else if(key.equals("INSERT_REPLY")){
    		
    		sb.append("#수집테이블저장\n");
    		sb.append("REPLACE INTO REPLY (           								\n");
    		sb.append("		md_seq, md_site_name, md_menu_name, md_type, md_date,	\n");
    		sb.append("		md_title, md_url, md_same_count, md_pseq, s_seq, 		\n");
    		sb.append("		md_img, l_alpha, ts_type, ts_rank, sb_seq, 				\n");
    		sb.append("		md_content, sg_seq, issue_check, json_data, ek_seq,   	\n");
    		sb.append("		md_senti, md_relation 									\n");
    		sb.append(") VALUES (													\n");
    		sb.append("		?,?,?,?,?,												\n");
    		sb.append("		?,?,?,?,?,												\n");
    		sb.append("		?,?,?,?,?,												\n");
    		sb.append("		?,?,?,?,?,												\n");
    		sb.append("		?,?														\n");
    		sb.append(")															\n");
    		
    	}else if(key.equals("INSERT_TOP")){
    		
    		sb.append("#수집테이블저장\n");
    		sb.append("REPLACE INTO TOP (           								\n");
    		sb.append("		md_seq, md_site_name, md_menu_name, md_type, md_date,	\n");
    		sb.append("		md_title, md_url, md_same_count, md_pseq, s_seq, 		\n");
    		sb.append("		md_img, l_alpha, ts_type, ts_rank, sb_seq, 				\n");
    		sb.append("		md_content, sg_seq, issue_check, json_data, ek_seq,   	\n");
    		sb.append("		md_senti, md_relation  									\n");
    		sb.append(") VALUES (													\n");
    		sb.append("		?,?,?,?,?,												\n");
    		sb.append("		?,?,?,?,?,												\n");
    		sb.append("		?,?,?,?,?,												\n");
    		sb.append("		?,?,?,?,?,												\n");
    		sb.append("		?,?														\n");
    		sb.append(")															\n");
    		
    	}else if(key.equals("INSERT_WARNING")){
    		
    		sb.append("#워닝테이블저장\n");
    		sb.append("REPLACE INTO WARNING (           								\n");
    		sb.append("		md_seq, md_site_name, md_menu_name, md_type, md_date,	\n");
    		sb.append("		md_title, md_url, md_same_count, md_pseq, s_seq, 		\n");
    		sb.append("		md_img, l_alpha, ts_type, ts_rank, sb_seq, 				\n");
    		sb.append("		md_content, sg_seq, issue_check, json_data, ek_seq,   	\n");
    		sb.append("		md_senti, md_relation  									\n");
    		sb.append(") VALUES (													\n");
    		sb.append("		?,?,?,?,?,												\n");
    		sb.append("		?,?,?,?,?,												\n");
    		sb.append("		?,?,?,?,?,												\n");
    		sb.append("		?,?,?,?,?,												\n");
    		sb.append("		?,?														\n");
    		sb.append(")															\n");
    	
    	}else if(key.equals("INSERT_WARNING_CAPITAL")){
    		
    		sb.append("#워닝(케피탈)테이블저장\n");
    		sb.append("REPLACE INTO WARNING_CAPITAL (           								\n");
    		sb.append("		md_seq, md_site_name, md_menu_name, md_type, md_date,	\n");
    		sb.append("		md_title, md_url, md_same_count, md_pseq, s_seq, 		\n");
    		sb.append("		md_img, l_alpha, ts_type, ts_rank, sb_seq, 				\n");
    		sb.append("		md_content, sg_seq, issue_check, json_data, ek_seq,   	\n");
    		sb.append("		md_senti, md_relation  									\n");
    		sb.append(") VALUES (													\n");
    		sb.append("		?,?,?,?,?,												\n");
    		sb.append("		?,?,?,?,?,												\n");
    		sb.append("		?,?,?,?,?,												\n");
    		sb.append("		?,?,?,?,?,												\n");
    		sb.append("		?,?														\n");
    		sb.append(")															\n");	
    	}else if(key.equals("INSERT_SECTION")){
    		
    		sb.append("#워닝테이블저장\n");
    		sb.append("REPLACE INTO SECTION (           								\n");
    		sb.append("		md_seq, md_site_name, md_menu_name, md_type, md_date,	\n");
    		sb.append("		md_title, md_url, md_same_count, md_pseq, s_seq, 		\n");
    		sb.append("		md_img, l_alpha, ts_type, ts_rank, sb_seq, 				\n");
    		sb.append("		md_content, sg_seq, issue_check, json_data, ek_seq,   	\n");
    		sb.append("		md_senti, md_relation  									\n");
    		sb.append(") VALUES (													\n");
    		sb.append("		?,?,?,?,?,												\n");
    		sb.append("		?,?,?,?,?,												\n");
    		sb.append("		?,?,?,?,?,												\n");
    		sb.append("		?,?,?,?,?,												\n");
    		sb.append("		?,?														\n");
    		sb.append(")															\n");
    		
    	}else if(key.equals("INSERT_EXCEPTION_META")){
    		
    		sb.append("#전체제외 수집테이블저장\n");
    		sb.append("REPLACE INTO EXCEPTION_META (           						\n");
    		sb.append("		md_seq, md_site_name, md_menu_name, md_type, md_date,	\n");
    		sb.append("		md_title, md_url, md_same_count, md_pseq, s_seq, 		\n");
    		sb.append("		md_img, l_alpha, ts_type, ts_rank, sb_seq, 				\n");
    		sb.append("		md_content, sg_seq, issue_check, json_data, ek_seq,   	\n");
    		sb.append("		md_senti, md_relation  									\n");
    		sb.append(") VALUES (													\n");
    		sb.append("		?,?,?,?,?,												\n");
    		sb.append("		?,?,?,?,?,												\n");
    		sb.append("		?,?,?,?,?,												\n");
    		sb.append("		?,?,?,?,?,												\n");
    		sb.append("		?,?														\n");
    		sb.append(")															\n");
    		
    	}else if(key.equals("INSERT_IDX")){
    		
    		sb.append("#색인테이블저장\n");
    		sb.append("REPLACE INTO IDX (											\n");
    		sb.append("		MD_SEQ, K_XP, K_YP, K_ZP								\n"); 
    		sb.append(")VALUES (													\n");
    		sb.append("		?,?,?,?													\n");
    		sb.append(")															\n");
    		
    	}else if(key.equals("INSERT_IDX_CATEGORY")){
    		
    		sb.append("#색인 카테고리 테이블저장\n");
    		sb.append("REPLACE INTO IDX_CATEGORY (									\n");
    		sb.append("		MD_SEQ, K_XP, K_YP, K_ZP								\n"); 
    		sb.append(")VALUES (													\n");
    		sb.append("		?,?,?,?													\n");
    		sb.append(")															\n");
    		
    	}else if(key.equals("INSERT_IDX_TOP")){
    		
    		sb.append("#색인 카테고리 테이블저장\n");
    		sb.append("REPLACE INTO IDX_TOP (										\n");
    		sb.append("		MD_SEQ, K_XP, K_YP, K_ZP								\n"); 
    		sb.append(")VALUES (													\n");
    		sb.append("		?,?,?,?													\n");
    		sb.append(")															\n");
    		
    	}else if(key.equals("INSERT_IDX_WARNING")){
    		
    		sb.append("#색인 워닝 테이블저장\n");
    		sb.append("REPLACE INTO IDX_WARNING (									\n");
    		sb.append("		MD_SEQ, K_XP, K_YP, K_ZP								\n"); 
    		sb.append(")VALUES (													\n");
    		sb.append("		?,?,?,?													\n");
    		sb.append(")															\n");
    	
    	}else if(key.equals("INSERT_IDX_WARNING_CAPITAL")){
    		
    		sb.append("#색인 워닝(캐피탈) 테이블저장\n");
    		sb.append("REPLACE INTO IDX_WARNING_CAPITAL (									\n");
    		sb.append("		MD_SEQ, K_XP, K_YP, K_ZP								\n"); 
    		sb.append(")VALUES (													\n");
    		sb.append("		?,?,?,?													\n");
    		sb.append(")															\n");
    	}else if(key.equals("INSERT_IDX_SECTION")){
    		
    		sb.append("#색인 워닝 테이블저장\n");
    		sb.append("REPLACE INTO IDX_SECTION (									\n");
    		sb.append("		MD_SEQ, K_XP, K_YP, K_ZP								\n"); 
    		sb.append(")VALUES (													\n");
    		sb.append("		?,?,?,?													\n");
    		sb.append(")															\n");
    		
    	}else if(key.equals("INSERT_EXCEPTION_IDX")){
    		
    		sb.append("#전체제외 색인테이블저장\n");
    		sb.append("REPLACE INTO EXCEPTION_IDX (									\n");
    		sb.append("		MD_SEQ, K_XP, K_YP, K_ZP								\n");
    		sb.append(")VALUES (													\n");
    		sb.append("		?,?,?,?													\n");
    		sb.append(")															\n");
    		
    	}else if(key.equals("UPDATE_CHANNEL")){
    		
    		sb.append("#마지막 시퀀스 저장\n");
    		sb.append("UPDATE CHANNEL												\n");
    		sb.append("   SET CH_LAST_DATE = ?										\n");
    		sb.append("     , CH_LAST_DOCID = ?										\n");
    		sb.append("     , CH_REGDATE = NOW()									\n");
    		sb.append(" WHERE CH_SEQ = ?											\n");
    		
    	}else if(key.equals("SELECT_META_SEQ")){
    		
    		sb.append("#마지막값 조회\n");
    		String[] parm = tmp.split("_");
    		sb.append("SELECT MD_SEQ FROM META_SEQ WHERE CH_KEY = "+parm[0]+" AND D_SEQ > "+parm[1]+"	\n");
    		
    	}else if(key.equals("DELETE_META")){
    		
    		sb.append("DELETE FROM META WHERE MD_SEQ IN ("+tmp+")					\n");
    		
    	}else if(key.equals("DELETE_WARNING")){
    		
    		sb.append("DELETE FROM WARNING WHERE MD_SEQ IN ("+tmp+")					\n");
    		
    	}else if(key.equals("DELETE_IDX")){
    		
    		sb.append("DELETE FROM IDX WHERE MD_SEQ IN ("+tmp+")					\n");
    		
    	}else if(key.equals("DELETE_IDX_CATEGORY")){
    		
    		sb.append("DELETE FROM IDX_CATEGORY WHERE MD_SEQ IN ("+tmp+")					\n");
    		
    	}else if(key.equals("DELETE_IDX_WARNING")){
    		
    		sb.append("DELETE FROM IDX_WARNING WHERE MD_SEQ IN ("+tmp+")					\n");
    		
    	}else if(key.equals("DELETE_EXCEPTION_META")){
    		
    		sb.append("DELETE FROM EXCEPTION_META WHERE MD_SEQ IN ("+tmp+")			\n");
    		
    	}else if(key.equals("DELETE_EXCEPTION_IDX")){
    		
    		sb.append("DELETE FROM EXCEPTION_IDX WHERE MD_SEQ IN ("+tmp+")			\n");
    		
    	}else if(key.equals("DELETE_SAME_FILTER")){
    		
    		sb.append("DELETE FROM SAME_FILTER WHERE MD_SEQ IN ("+tmp+")			\n");
    		
    	}else if(key.equals("DELETE_SAME_FILTER_WARNING")){
    		
    		sb.append("DELETE FROM SAME_FILTER_WARNING WHERE MD_SEQ IN ("+tmp+")			\n");
    		
    	}else if(key.equals("DELETE_META_SEQ")){
    		
    		sb.append("DELETE FROM META_SEQ WHERE MD_SEQ IN ("+tmp+")				\n");
    		
    	}else if(key.equals("DELETE_TRANS_DATA")){
    		
    		sb.append("DELETE FROM TRANS_DATA WHERE MD_SEQ IN ("+tmp+")				\n");
    		
    	}else if(key.equals("SELECT_SAME_FILTER")){
    		
    		sb.append("#유사필터조회\n");
    		sb.append("SELECT A.MD_SEQ												\n");
    		sb.append("     , MD_TITLE												\n");
    		sb.append("     , MD_DATE												\n");
    		sb.append("     , MD_SAME_COUNT											\n");
    		sb.append("  FROM (														\n");
    		sb.append("        SELECT MD_SEQ										\n");
    		sb.append("             , MD_TITLE										\n");
    		sb.append("             , MATCH(MD_TITLE) AGAINST(?) AS SCORE			\n");	 
    		sb.append("             , MD_DATE										\n");
    		sb.append("             , MD_SAME_COUNT									\n");
    		sb.append("          FROM SAME_FILTER					 				\n");
    		sb.append("         WHERE MATCH(MD_TITLE) AGAINST (?)					\n");	
    		sb.append("         ORDER BY SCORE DESC LIMIT 20						\n");
    		sb.append("       )A 													\n");				
    		sb.append(" ORDER BY MD_DATE ASC										\n");
    		
    	}else if(key.equals("SELECT_SAME_FILTER_WARNING")){
    		
    		sb.append("#유사필터조회\n");
    		sb.append("SELECT A.MD_SEQ												\n");
    		sb.append("     , MD_TITLE												\n");
    		sb.append("     , MD_DATE												\n");
    		sb.append("     , MD_SAME_COUNT											\n");
    		sb.append("  FROM (														\n");
    		sb.append("        SELECT MD_SEQ										\n");
    		sb.append("             , MD_TITLE										\n");
    		sb.append("             , MATCH(MD_TITLE) AGAINST(?) AS SCORE			\n");	 
    		sb.append("             , MD_DATE										\n");
    		sb.append("             , MD_SAME_COUNT									\n");
    		sb.append("          FROM SAME_FILTER_WARNING			 				\n");
    		sb.append("         WHERE MATCH(MD_TITLE) AGAINST (?)					\n");	
    		sb.append("         ORDER BY SCORE DESC LIMIT 20						\n");
    		sb.append("       )A 													\n");				
    		sb.append(" ORDER BY MD_DATE ASC										\n");
    	}else if(key.equals("SELECT_SAME_FILTER_WARNING_CAPITAL")){
    		
    		sb.append("#유사필터(소비자뉴스-캐피탈)조회\n");
    		sb.append("SELECT A.MD_SEQ												\n");
    		sb.append("     , MD_TITLE												\n");
    		sb.append("     , MD_DATE												\n");
    		sb.append("     , MD_SAME_COUNT											\n");
    		sb.append("  FROM (														\n");
    		sb.append("        SELECT MD_SEQ										\n");
    		sb.append("             , MD_TITLE										\n");
    		sb.append("             , MATCH(MD_TITLE) AGAINST(?) AS SCORE			\n");	 
    		sb.append("             , MD_DATE										\n");
    		sb.append("             , MD_SAME_COUNT									\n");
    		sb.append("          FROM SAME_FILTER_WARNING_CAPITAL			 				\n");
    		sb.append("         WHERE MATCH(MD_TITLE) AGAINST (?)					\n");	
    		sb.append("         ORDER BY SCORE DESC LIMIT 20						\n");
    		sb.append("       )A 													\n");				
    		sb.append(" ORDER BY MD_DATE ASC										\n");

    	}else if(key.equals("SELECT_SAME_FILTER_SECTION")){
    		
    		sb.append("#유사필터조회\n");
    		sb.append("SELECT A.MD_SEQ												\n");
    		sb.append("     , MD_TITLE												\n");
    		sb.append("     , MD_DATE												\n");
    		sb.append("     , MD_SAME_COUNT											\n");
    		sb.append("  FROM (														\n");
    		sb.append("        SELECT MD_SEQ										\n");
    		sb.append("             , MD_TITLE										\n");
    		sb.append("             , MATCH(MD_TITLE) AGAINST(?) AS SCORE			\n");	 
    		sb.append("             , MD_DATE										\n");
    		sb.append("             , MD_SAME_COUNT									\n");
    		sb.append("          FROM SAME_FILTER_SECTION			 				\n");
    		sb.append("         WHERE MATCH(MD_TITLE) AGAINST (?)					\n");	
    		sb.append("         ORDER BY SCORE DESC LIMIT 20						\n");
    		sb.append("       )A 													\n");				
    		sb.append(" ORDER BY MD_DATE ASC										\n");
    		
    	}else if(key.equals("INSERT_SAME_FILTER")){
    		
    		sb.append("#유사필터추가\n");
    		sb.append("REPLACE INTO SAME_FILTER (								\n");
    		sb.append("		md_seq, md_title, md_date, md_same_count			\n");
    		sb.append(") VALUES (													\n");
    		sb.append("		?,?,?,?													\n");
    		sb.append(")															\n");
    		
    	}else if(key.equals("INSERT_SAME_FILTER_WARNING")){
    		
    		sb.append("#유사필터추가\n");
    		sb.append("REPLACE INTO SAME_FILTER_WARNING (						\n");
    		sb.append("		md_seq, md_title, md_date, md_same_count			\n");
    		sb.append(") VALUES (													\n");
    		sb.append("		?,?,?,?													\n");
    		sb.append(")															\n");
    	}else if(key.equals("INSERT_SAME_FILTER_WARNING_CAPITAL")){
    		
    		sb.append("#유사필터(소비자뉴스-캐피탈)추가\n");
    		sb.append("REPLACE INTO SAME_FILTER_WARNING_CAPITAL (						\n");
    		sb.append("		md_seq, md_title, md_date, md_same_count			\n");
    		sb.append(") VALUES (													\n");
    		sb.append("		?,?,?,?													\n");
    		sb.append(")															\n");
    	
    	}else if(key.equals("INSERT_SAME_FILTER_SECTION")){
    		
    		sb.append("#유사필터추가\n");
    		sb.append("REPLACE INTO SAME_FILTER_SECTION (						\n");
    		sb.append("		md_seq, md_title, md_date, md_same_count			\n");
    		sb.append(") VALUES (													\n");
    		sb.append("		?,?,?,?													\n");
    		sb.append(")															\n");
    		
    	}else if(key.equals("UPDATE_SAME_FILTER")){
    		
    		sb.append("#유사필터수정\n");
    		sb.append("UPDATE SAME_FILTER										\n");
    		sb.append("   SET MD_SAME_COUNT = ?										\n");
    		sb.append(" WHERE MD_SEQ = ?											\n");
    		
    	}else if(key.equals("UPDATE_SAME_FILTER_WARNING")){
    		
    		sb.append("#유사필터수정\n");
    		sb.append("UPDATE SAME_FILTER_WARNING									\n");
    		sb.append("   SET MD_SAME_COUNT = ?										\n");
    		sb.append(" WHERE MD_SEQ = ?											\n");
    		
    	}else if(key.equals("UPDATE_SAME_FILTER_WARNING_CAPITAL")){
    		
    		sb.append("#유사(소비자뉴스-캐피탈)필터수정\n");
    		sb.append("UPDATE SAME_FILTER_WARNING_CAPITAL									\n");
    		sb.append("   SET MD_SAME_COUNT = ?										\n");
    		sb.append(" WHERE MD_SEQ = ?											\n");
    		
    	}else if(key.equals("UPDATE_SAME_FILTER_SECTION")){
    		
    		sb.append("#유사필터수정\n");
    		sb.append("UPDATE SAME_FILTER_SECTION									\n");
    		sb.append("   SET MD_SAME_COUNT = ?										\n");
    		sb.append(" WHERE MD_SEQ = ?											\n");
    		
    	}else if(key.equals("UPDATE_META")){
    		
    		sb.append("#유사카운트수정\n");
    		sb.append("UPDATE META													\n");
    		sb.append("   SET MD_SAME_COUNT = ?										\n");
    		sb.append(" WHERE MD_PSEQ = ?											\n");
    		
    	}else if(key.equals("UPDATE_WARNING")){
    		
    		sb.append("#유사카운트수정\n");
    		sb.append("UPDATE WARNING												\n");
    		sb.append("   SET MD_SAME_COUNT = ?										\n");
    		sb.append(" WHERE MD_PSEQ = ?											\n");
    		
    	}else if(key.equals("UPDATE_WARNING_CAPITAL")){
    		
    		sb.append("#유사(소비자뉴스-캐피탈)카운트수정\n");
    		sb.append("UPDATE WARNING_CAPITAL												\n");
    		sb.append("   SET MD_SAME_COUNT = ?										\n");
    		sb.append(" WHERE MD_PSEQ = ?											\n");
    	
    	}else if(key.equals("UPDATE_SECTION")){
    		
    		sb.append("#유사카운트수정\n");
    		sb.append("UPDATE SECTION												\n");
    		sb.append("   SET MD_SAME_COUNT = ?										\n");
    		sb.append(" WHERE MD_PSEQ = ?											\n");
    		
    	}else if(key.equals("DELETE_SAME_FILTER_DATE")){
    		
    		sb.append("#유사필터 삭제\n");
    		sb.append("DELETE FROM SAME_FILTER WHERE MD_DATE < '"+tmp+"'		\n");
    		
    	}else if(key.equals("DELETE_SAME_FILTER_WARNING_DATE")){
    		
    		sb.append("#유사필터 삭제\n");
    		sb.append("DELETE FROM SAME_FILTER_WARNING WHERE MD_DATE < '"+tmp+"'		\n");
    	
    	}else if(key.equals("DELETE_SAME_FILTER_WARNING_DATE_CAPITAL")){
    		
    		sb.append("#유사필터(워닝캐피탈) 삭제\n");
    		sb.append("DELETE FROM SAME_FILTER_WARNING_CAPITAL WHERE MD_DATE < '"+tmp+"'		\n");
    		
    	}else if(key.equals("DELETE_SAME_FILTER_SECTION_DATE")){
    		
    		sb.append("#유사필터 삭제\n");
    		sb.append("DELETE FROM SAME_FILTER_SECTION WHERE MD_DATE < '"+tmp+"'		\n");
    		
    	}else if(key.equals("INSERT_TRANS_DATA")){
    		
    		sb.append("#전송데이터 저장\n");
    		sb.append("REPLACE INTO TRANS_DATA (MD_SEQ, T_XML, T_FILE_NAME) VALUES (?,?,?)		\n");
    		
    	}else if(key.equals("SELECT_META_SEQ_REPLY")){
    		
    		sb.append("#댓글 원글 유무																\n");
    		sb.append("SELECT MD_SEQ FROM META_SEQ WHERE CH_KEY = 'MAIN' AND D_SEQ = ?	\n");
    		
    	}else if(key.equals("SELECT_TOP_URL")){
    		
    		sb.append("#포탈탑 유무																\n");
    		sb.append("SELECT MD_SEQ FROM TOP WHERE MD_URL = ? LIMIT 1	\n");
    		
    	}else if(key.equals("SELECT_META_SEQ_MD_SEQ")){
    		
    		sb.append("#채널 및 문서번로 MD_SEQ 조회										\n");
    		sb.append("SELECT MD_SEQ FROM META_SEQ WHERE CH_KEY = ? AND D_SEQ = ?	\n");
    		
    	}else if(key.equals("UPDATE_META_ANA_KEYWORD")){
    		sb.append("#메타 키워드 실시간 통계											\n");
    		sb.append("UPDATE META_ANA_KEYWORD 										\n");
    		sb.append("   SET MAK_CNT = MAK_CNT + 1									\n");
    		sb.append(" WHERE MAK_DATE = ?											\n");
    		sb.append("   AND K_XP = ?												\n");
    		sb.append("   AND K_YP = ?												\n");
    		sb.append("   AND K_ZP = ?												\n");
    	}else if(key.equals("UPDATE_WARNING_ANA_KEYWORD")){
    		sb.append("#워닝 키워드 실시간 통계											\n");
    		sb.append("UPDATE WARNING_ANA_KEYWORD 										\n");
    		sb.append("   SET MAK_CNT = MAK_CNT + 1									\n");
    		sb.append(" WHERE MAK_DATE = ?											\n");
    		sb.append("   AND K_XP = ?												\n");
    		sb.append("   AND K_YP = ?												\n");
    		sb.append("   AND K_ZP = ?												\n");
    	
      	}else if(key.equals("UPDATE_WARNING_ANA_KEYWORD_CAPITAL")){
    		sb.append("#워닝(캐피탈) 키워드 실시간 통계											\n");
    		sb.append("UPDATE WARNING_ANA_KEYWORD_CAPITAL 										\n");
    		sb.append("   SET MAK_CNT = MAK_CNT + 1									\n");
    		sb.append(" WHERE MAK_DATE = ?											\n");
    		sb.append("   AND K_XP = ?												\n");
    		sb.append("   AND K_YP = ?												\n");
    		sb.append("   AND K_ZP = ?												\n");
    	
    	}else if(key.equals("UPDATE_SECTION_ANA_KEYWORD")){
    		sb.append("#메타 키워드 실시간 통계											\n");
    		sb.append("UPDATE SECTION_ANA_KEYWORD 										\n");
    		sb.append("   SET MAK_CNT = MAK_CNT + 1									\n");
    		sb.append(" WHERE MAK_DATE = ?											\n");
    		sb.append("   AND K_XP = ?												\n");
    		sb.append("   AND K_YP = ?												\n");
    		sb.append("   AND K_ZP = ?												\n");
    		
    	}else if(key.equals("INSERT_META_ANA_KEYWORD")){
    		sb.append("#메타 키워드 실시간 통계											\n");
    		sb.append("REPLACE INTO META_ANA_KEYWORD (MAK_DATE, K_XP, K_YP, K_ZP, K_VALUE, MAK_CNT) VALUES (?, ?, ?, ?, '', 1)\n");
    		
    	}else if(key.equals("INSERT_WARNING_ANA_KEYWORD")){
    		sb.append("#워닝 키워드 실시간 통계											\n");
    		sb.append("REPLACE INTO WARNING_ANA_KEYWORD (MAK_DATE, K_XP, K_YP, K_ZP, K_VALUE, MAK_CNT) VALUES (?, ?, ?, ?, '', 1)\n");
    	
    		
    	}else if(key.equals("INSERT_WARNING_ANA_KEYWORD_CAPITAL")){
    		sb.append("#워닝(케피탈) 키워드 실시간 통계											\n");
    		sb.append("REPLACE INTO WARNING_ANA_KEYWORD_CAPITAL (MAK_DATE, K_XP, K_YP, K_ZP, K_VALUE, MAK_CNT) VALUES (?, ?, ?, ?, '', 1)\n");
    	
    	}else if(key.equals("INSERT_SECTION_ANA_KEYWORD")){
    		sb.append("#메타 키워드 실시간 통계											\n");
    		sb.append("REPLACE INTO SECTION_ANA_KEYWORD (MAK_DATE, K_XP, K_YP, K_ZP, K_VALUE, MAK_CNT) VALUES (?, ?, ?, ?, '', 1)\n");
    		
    	}else if(key.equals("UPDATE_META_ANA_KEYWORD_CATEGORY")){
    		sb.append("#메타 키워드 실시간 통계											\n");
    		sb.append("UPDATE META_ANA_KEYWORD_CATEGORY 										\n");
    		sb.append("   SET MAK_CNT = MAK_CNT + 1									\n");
    		sb.append(" WHERE MAK_DATE = ?											\n");
    		sb.append("   AND K_XP = ?												\n");
    		sb.append("   AND K_YP = ?												\n");
    		sb.append("   AND K_ZP = ?												\n");
    	}else if(key.equals("INSERT_META_ANA_KEYWORD_CATEGORY")){
    		sb.append("#메타 키워드 실시간 통계											\n");
    		sb.append("REPLACE INTO META_ANA_KEYWORD_CATEGORY (MAK_DATE, K_XP, K_YP, K_ZP, K_VALUE, MAK_CNT) VALUES (?, ?, ?, ?, '', 1)\n");
    	}else if(key.equals("INSERT_META_REPLY_CNT")){
    		sb.append("#포탈 댓글 통계	추가												\n");
    		sb.append("REPLACE INTO META_REPLY_CNT (MD_SEQ,MD_DATE, MD_REPLY_TOTAL_CNT, MD_REPLY_POS_CNT, MD_REPLY_NEG_CNT, MD_REPLY_NEU_CNT)\n");
    		sb.append("VALUES (?,?,0,0,0,0) \n");
    	}else if(key.equals("UPDATE_META_REPLY_CNT")){
    		sb.append("#포탈 댓글 통계	갱신										\n");
    		sb.append("UPDATE META_REPLY_CNT								\n");
    		sb.append("   SET MD_REPLY_TOTAL_CNT = MD_REPLY_TOTAL_CNT + ?	\n");
    		sb.append("     , MD_REPLY_POS_CNT = MD_REPLY_POS_CNT + ?		\n");
    		sb.append("     , MD_REPLY_NEG_CNT = MD_REPLY_NEG_CNT + ?		\n");
    		sb.append("     , MD_REPLY_NEU_CNT = MD_REPLY_NEU_CNT + ?		\n");
    		sb.append(" WHERE MD_SEQ = ?\n");
    	}else if(key.equals("INSERT_REPLY_RELATION_KEYWORD")){
    		sb.append("#댓글 연관키워드 저장										\n");
    		sb.append("REPLACE INTO REPLY_RELATION_KEYWORD (MD_SEQ_META, MD_SEQ_REPLY, PAT_SEQ) VALUES (?, ?, ?)	\n");
    	}else if(key.equals("INSERT_TELEGRAM_LOG")){
    		sb.append("#텔레그램 로그 저장\n");
      		sb.append("REPLACE INTO TELEGRAM_LOG (TS_SEQ, MD_SEQ, TL_MESSAGE, MD_DATE, TL_SEND_DATE, TL_SEND_STATUS, MD_PSEQ)	\n");
      		sb.append("                  VALUES (?, ?, ?, ?, NOW(), ?, ?)													\n");
    	}else if(key.equals("SELECT_TELEGRAM_SETTING")){
    		
    		sb.append("#텔레그램 정보 조회					\n");
    		sb.append("SELECT TS_SEQ					\n");
    		sb.append("     , TS_NAME					\n");
    		sb.append("     , TS_KEY					\n");
    		sb.append("     , TS_ID						\n");
    		sb.append("     , TS_PREVIEW				\n");
    		sb.append("     , TS_SG_SEQS				\n");
    		sb.append("     , TS_S_SEQS					\n");
    		sb.append("     , TS_KEYWORDS				\n");
    		sb.append("     , TS_KEYWORD_CATEGORYS		\n");
    		sb.append("     , TS_SIMILAR				\n");
    		sb.append("  FROM TELEGRAM_SETTING			\n");
    		sb.append(" WHERE TS_OPERATE_YN = 1			\n");
      		
    	}else if(key.equals("SELECT_TELEGRAM_LOG")){
    		sb.append("#텔레그램 로그  조회					\n");
    		sb.append("SELECT COUNT(*) AS CNT 			\n");
    		sb.append("  FROM TELEGRAM_LOG 				\n");
    		sb.append(" WHERE MD_DATE BETWEEN DATE_FORMAT(DATE_ADD(NOW(), INTERVAL -1 DAY ), '%Y-%m-%d 00:00:00') AND DATE_FORMAT(NOW(), '%Y-%m-%d 23:59:59')\n"); 
    		sb.append("   AND TL_SEND_STATUS = 1		\n");
    		sb.append("   AND TS_SEQ = ?				\n");
    		sb.append("   AND MD_PSEQ = ?				\n");
    	}
    	
    	Log.crond(className, "[" + key + "]");
    	Log.crond(className, sb.toString());
    	
    	return sb.toString();
    }
    String getQuery(String key){
    	return getQuery(key, "");
    }
    
     
    JSONObject getRow(){
    	
    	JSONObject result = null;
    	JSONObject jData = null;
    	
    	
    	try{
    		
    		 
    	
    		//채널에 데이터 적재
    		String last_docid = "";
    		String last_date = "";
    		JSONObject tmpJObj = null;
    		JSONObject setJObj = null;
    		for(int i = 0; i < jChannel.length(); i++){
    			
    			
        		if(jChannel.getJSONObject(i).getString("IDX").equals(jChannel.getJSONObject(i).getString("MAX_IDX"))){
        			
        			//마지막값 저장
        			if(!jChannel.getJSONObject(i).getString("IDX").equals("0")){
        				tmpJObj = jChannelBox[i].getJSONObject(jChannelBox[i].length()-1);
        				
        				last_date = stampToDateTime(tmpJObj.getString(jChannel.getJSONObject(i).getString("CH_KEY") + ".DATA.CrawlStamp"), "yyyyMMdd");
        				last_docid = tmpJObj.getString(jChannel.getJSONObject(i).getString("CH_KEY") + ".DATA.Seq");
        				
        				jChannel.getJSONObject(i).put("CH_LAST_DATE", last_date);
        				jChannel.getJSONObject(i).put("CH_LAST_DOCID", last_docid);
        				
        				//DB저장
        				setJObj = new JSONObject();
        				setJObj.put("1_CH_LAST_DATE", last_date);
        				setJObj.put("2_CH_LAST_DOCID", last_docid);
        				setJObj.put("3_CH_SEQ", jChannel.getJSONObject(i).getString("CH_SEQ"));
        				setExcuteUpdate(PS_UPDATE_CHANNEL, setJObj);
        			
        			}
        			
        			
        			//채널별 데이터 조회
        			if(usePastData) {
        				jData = getChannelApi_Past(jChannel.getJSONObject(i));
        			} else {
        				//System.out.println("jsonobject는");
        				//System.out.println(jChannel.getJSONObject(i));
        				jData = getChannelApi(jChannel.getJSONObject(i));
        			}
        			
        			
        			if(useMemoryCheck){
        				aftUseMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            			System.out.println("메모리 상태 : " + ((aftUseMemory - preUseMemory ) / 1000));	
        			}
        			
        			
        			//data가 없으면 프로세스 중지
        			if(!jData.isNull("data")){
        				jChannelBox[i] =jData.getJSONArray("data");

        				jChannel.getJSONObject(i).put("IDX", "0");
        				if(jChannelBox[i].length() > 0){
        					jChannel.getJSONObject(i).put("MAX_IDX", jChannelBox[i].length());	
        				}else{
        					jChannel.getJSONObject(i).put("MAX_IDX", "-1");
        				}
        				
        			}else if(!jData.isNull("error")){
        		
        				Log.writeExpt(className ,jData.getJSONObject("error").getString("message"));
        				System.exit(1);
        			}else{
        				Log.writeExpt(className, "API에러");
        				System.exit(1);
        			}
        		}
        	}
    		
    		
    		//채널비교
    		Integer[] ar_stamp = new Integer[jChannel.length()];
    		long sum = 0;
    		for(int i = 0; i < jChannel.length(); i++){
    			
    			if(jChannel.getJSONObject(i).get("MAX_IDX").equals("-1")){
    				ar_stamp[i] = 0;
    			}else{
					ar_stamp[i] = jChannelBox[i].getJSONObject(jChannel.getJSONObject(i).getInt("IDX")).getInt(jChannel.getJSONObject(i).getString("CH_KEY") + ".DATA.CrawlStamp");	
    			}
    			
    			sum += ar_stamp[i];
    		}
    		
    		
    		
    		//데이터 추출
    		if(sum > 0){
    			//수집시간이 가장빠른 채널 idx 추출 
        		int ChannelIdx = RankTop(ar_stamp);
        		result = jChannelBox[ChannelIdx].getJSONObject(jChannel.getJSONObject(ChannelIdx).getInt("IDX"));
        		result.put("TYPE", jChannel.getJSONObject(ChannelIdx).getString("CH_KEY"));
        		result.put("CH_SEQ", jChannel.getJSONObject(ChannelIdx).getString("CH_SEQ"));
        		jChannel.getJSONObject(ChannelIdx).put("IDX",jChannel.getJSONObject(ChannelIdx).getInt("IDX") + 1);	
    		}
    		
    		
    		
    		
    	} catch(Exception ex) {
    		writeExceptionLog(ex);
    	}
    	
    	return result;
    }
    
    JSONObject getChannelApi(JSONObject jObj){
    	JSONObject result = null;
    	
    	try{
    		String param = "";
    		param += "api_key=" + jObj.getString("CH_API_KEY");
    		param += "&max_seq=" + jObj.getString("CH_LAST_DOCID");
    		param += "&search_day=" + jObj.getString("CH_LAST_DATE");
    		param += "&list_count=" + jObj.getString("CH_LIST_CNT");
    		
    		if(!jObj.isNull("S_SEQS") && !jObj.getString("S_SEQS").equals("") ){
    			
    			param += "&s_seq=" + jObj.getString("S_SEQS");	
    		}
    		
    		
    		if(System.getProperty("os.name").contains("Win")){
    			Log.crond(className,"[SEND]" + testApiUrl+ "?" +  param);
				result = new JSONObject(GetHtmlPost(testApiUrl, param));
    		}else {
    			Log.crond(className,"[SEND]" + apiUrl+ "?" +  param);
    			/*
    			System.out.println("apiurl은");
    			System.out.println(apiUrl);
    			System.out.println("param은");
    			System.out.println(param);
    			System.out.println("gethtmlpost는");
    			System.out.println(GetHtmlPost_v2(apiUrl, param));
    			*/
    			result = new JSONObject(GetHtmlPost_v2(apiUrl, param));
    		}
    		
    		/*
    		if(dbconn1.getCONST_MYSQL_SUBDB_URL().equals("localhost")) {
    			Log.crond(className,"[SEND]" + apiUrl+ "?" +  param);
    			result = new JSONObject(GetHtmlPost(apiUrl, param));	
			}else {
				Log.crond(className,"[SEND]" + testApiUrl+ "?" +  param);
				result = new JSONObject(GetHtmlPost(testApiUrl, param));	
			}*/
    		
    		
	    	Log.crond(className,"[RECEIVE]");
    		
    	} catch(Exception ex) {
    		writeExceptionLog(ex);
    	}
    	
    	return result;
    }
    
    JSONObject getChannelApi_Past(JSONObject jObj){
    	JSONObject result = null;
    	
    	try{
    		String param = "";
    		param += "channel=" + jObj.getString("CH_KEY");
    		param += "&max_seq=" + jObj.getString("CH_LAST_DOCID");
    		param += "&search_day=" + jObj.getString("CH_LAST_DATE");
    		param += "&list_count=" + jObj.getString("CH_LIST_CNT");
    		// 프로세스번호
    		param += "&p_seq="+pastApiTableNum;
    		
    		// 이부분 잘 기억나서 하게되면 다시보자.
    		if(!jObj.isNull("S_SEQS") && !jObj.getString("S_SEQS").equals("") ){
    			
    			param += "&s_seq=" + jObj.getString("S_SEQS");	
    		}
    		
    		
    		Log.crond(className,"[SEND]" + pastApiUrl+ "?" +  param);
    		result = new JSONObject(GetHtmlPost(pastApiUrl, param));
	    	Log.crond(className,"[RECEIVE]");
    		
    	} catch(Exception ex) {
    		writeExceptionLog(ex);
    	}
    	
    	return result;
    }
    
    
    String GetHtmlPost(String sUrl, String param) {
    	StringBuffer html = new StringBuffer();
    	try {
    		String line = null;
    		URL aURL = new URL(sUrl);
    		HttpURLConnection urlCon = (HttpURLConnection) aURL.openConnection();
    		urlCon.setRequestMethod("POST");

    		urlCon.setDoInput(true);
    		urlCon.setDoOutput(true);
    		OutputStream out = urlCon.getOutputStream();
    		out.write(param.getBytes());
    		out.flush();

    		out.close();
    		BufferedReader br = new BufferedReader(new InputStreamReader(urlCon.getInputStream(),"utf-8"));
    		while ((line = br.readLine()) != null) {
    			html.append(line);
    		}

    		aURL = null;
    		urlCon = null;
    		br.close();
    	} catch (Exception ex) {
    		writeExceptionLog(ex);
    	}
    	return html.toString();
    }
    
    
    String GetHtmlPost_v2(String sUrl, String param) {
    	StringBuffer html = new StringBuffer();
    	try {
    		
    		String line = null;
			URL aURL = new URL(apiUrl+"?"+param);
			HttpURLConnection urlCon = (HttpURLConnection) aURL.openConnection();
			urlCon.setRequestMethod("GET");

			urlCon.setRequestProperty("User-Agent", "Mozilla/5.0");
			urlCon.setRequestProperty("Accept-Encoding", "gzip, deflate");

			BufferedReader br = new BufferedReader(new InputStreamReader(new GZIPInputStream(urlCon.getInputStream()),"utf-8"));
			while ((line = br.readLine()) != null) {
				html.append(line);
			}

			aURL = null;
			urlCon = null;
			br.close();
    		
    	} catch (Exception ex) {
    		writeExceptionLog(ex);
    	}
    	return html.toString();
    }
    
    int RankTop(Integer[] arRank){
        
    	int reuslt = 0;
    	int cnt = 0;
    	int zeroCnt =0;
    	
    	for(int i = 0; i < arRank.length; i++){
    		if(arRank[i] == 0){
    			zeroCnt++;
    		}
    	}
    	
    	for(int i = 0; i < arRank.length; i++){
    		
    		cnt = 0;
    		
    		if(arRank[i] != 0){
    			for(int j = 0; j < arRank.length; j++){
    				if(arRank[j] != 0){
    					if(arRank[i] <= arRank[j]){
        					cnt++;
        				}
    				}
        		}
    		}
    		
    		if(arRank.length - zeroCnt == cnt){
    			reuslt = i;
    			break;
    		}
    	}
    	
    	return reuslt;
    }
    
    int binarySearch(JSONArray jArr, String keyNm, String findNo){
    	
    	try{
    		if(jArr == null){
       			return -1;
       		}
       		int left = 0;
       		int right = jArr.length() -1;
       		
       		int idx =0;
       		int f_idx =Integer.parseInt(findNo);
       		
       		while(left <= right){
       			int mid = left + (right -left) / 2;
       			idx = jArr.getJSONObject(mid).getInt(keyNm);
       			if(idx == f_idx){
       				return mid;
       			}else if(idx < f_idx){
       				left = mid +1;
       			}else if(idx > f_idx){
       				right = mid -1;
       			}
       		}	
    	} catch(Exception ex) {
    		writeExceptionLog(ex);
        }
    	
   		return -1;
   	}
    
    int binarySearch(String dataArr[], String findData){
   		if(dataArr == null){
   			return -1;
   		}
   		int left = 0;
   		int right = dataArr.length -1;
   		
   		int idx =0;
   		int f_idx =Integer.parseInt(findData);
   		
   		while(left <= right){
   			int mid = left + (right -left) / 2;
   			idx = Integer.parseInt(dataArr[mid]);
   			if(idx == f_idx){
   				return mid;
   			}else if(idx < f_idx){
   				left = mid +1;
   			}else if(idx > f_idx){
   				right = mid -1;
   			}
   		}
   		return -1;
   	}
    
    int binarySearch(int dataArr[], String findData){
   		if(dataArr == null){
   			return -1;
   		}
   		int left = 0;
   		int right = dataArr.length -1;
   		
   		int idx =0;
   		int f_idx =Integer.parseInt(findData);
   		
   		while(left <= right){
   			int mid = left + (right -left) / 2;
   			idx = dataArr[mid];
   			if(idx == f_idx){
   				return mid;
   			}else if(idx < f_idx){
   				left = mid +1;
   			}else if(idx > f_idx){
   				right = mid -1;
   			}
   		}
   		return -1;
   	}
    
    boolean searchKeyword(String html, String regexpHtml, String[] key, String word, int op, int near_len){
		boolean result = false;
		
		//String[] key = word.split(" ");
		if(key == null) {
			key =  word.split(" ");
		}
		
		String sKey = "";
		//일반 AND
		if(op==1){
			for(int i=0; i<key.length; i++)
		     {
		     	sKey = ""+key[i];
		     	int check = html.indexOf(sKey);
		     	if ( check < 0 )
		         {
		     		return false;
		         }
		     }
		     return true;
		//인접	150 글자수 OR BYTE
		}else if(op==2){
			return nearSearch(html, key, near_len);
		//구문 A,B가 한구문
		}else if(op==3){
			if(html.indexOf(word)>-1){
				return true;
			}else{
				return false;
			}
		//고유명사 한국: 앞글자 뛰어쓰기, 영어 단어기준:뒤에도 공백	
		}else if(op==4){
			//if(KorCheck(word)){
				sKey = " "+word;
			/*} else{
				sKey = " "+word+" "; //수정가능
			}*/
	    
	     	int check = regexpHtml.indexOf(sKey);
	     	if ( check < 0 )
	         {
	     		return false;
	         }
	     	
		     return true;
		//고유명사2	 단어,앞뒤 공백
		}else if(op==5){
			return englishPropernoun(regexpHtml, key);
		//구문 A,B가 한구문2 앞공백
		}else if(op==6){
			if(html.indexOf(" "+word)>-1){
				return true;
			}else{
				return false;
			}
		//구문 A,B가 한구문2 앞,뒤 공백
		}else if(op==7){
			if(html.indexOf(" "+word+" ")>-1){
				return true;
			}else{
				return false;
			}
		}
	
		return result;
	}
    
    /**
	 * <p> key[0] 이 노출된 지점에서 앞뒤로 iNearByte(byte) 이내에 key[n]이 모두 존재 하는지 검사한다.</p>
	 * 
	 * @author Ryu Seung Wan
	 */
    boolean nearSearch(String strHtml, String[] key, int iNearByte){  	
   	    	    	    	
    	int tempIndex1 = 0;
    	int lastIndex = 0;
    	int nextIndex = 1;
    	boolean bFindKey = false;
    	String[] tempKey = null;
    	String searchHtml = "";
    	String frontHtml = "";
    	String backHtml = "";
    	
    	//key = searchGumunKey(key);
    	
    	//대소문자 처리
    	/*for(int i=0;i<key.length;i++){
			key[i] = key[i].toLowerCase();			
		}*/
    	
    	tempIndex1 = nextIndexOf(strHtml, key[0], nextIndex);
		lastIndex = lastIndexOf(strHtml, key[0]);
		
		//복수 키워드
		if(key.length>1)
		{
			if(tempIndex1 != -1){
				
				tempKey = new String[key.length-1];
				
				for(int j=1;j<key.length;j++){					
					tempKey[j-1] = key[j];
				}
				
				findKeyLoop:while (tempIndex1 <= lastIndex){
					tempIndex1 = nextIndexOf(strHtml, key[0], nextIndex);
					++nextIndex;
					if(tempIndex1 != -1){
						
						// 키의 위치가 첫글짜 이면 (tempIndex1 == 0) 앞쪽은 자르지 않는다.
	
						// 키의 위치로 부터 앞쪽으로 iNearByte 만큼 자른다.
						frontHtml = (tempIndex1 - iNearByte) > 0 ? 
								strHtml.substring(tempIndex1 - iNearByte, tempIndex1) : 
								strHtml.substring(0, tempIndex1); 
	
						
						// 키의 위치로 부터 뒷쪽으로 (키.length + iNearByte) 만큼 자른다.
						backHtml = strHtml.length() - (key[0].length() + iNearByte + 1) > tempIndex1 ? 
								strHtml.substring(tempIndex1, tempIndex1 + key[0].length() + iNearByte) : 
								strHtml.substring(tempIndex1, strHtml.length());
	    				
						
						searchHtml = frontHtml + backHtml;
											
						bFindKey = indexOfAll(searchHtml, tempKey);					
	    				
	    				if(bFindKey) break findKeyLoop;
	    				
					}else{
						break findKeyLoop;
					}				
				}
			}
		//단일 키워드
		}else{	
			//key[0] = " "+key[0];					 
			
			int check = strHtml.indexOf(key[0]);
			if ( check >=0 )
			{
				//System.out.println("key[0]:"+key[0]);
				bFindKey  =  true;
			}		    		
		}
    	return bFindKey;
    }
    
    int nextIndexOf(String str, String searchStr, int nextIndex) {
        if (str == null || searchStr == null || nextIndex <= 0) {
            return -1;
        }
        if (searchStr.length() == 0) {
            return 0;
        }
        int found = 0;
        int index = -1;
        do {
            index = str.indexOf(searchStr, index + 1);
            if (index < 0) {
                return index;
            }
            found++;
        } while (found < nextIndex);
        return index;
    }
    
    int lastIndexOf(String str, String searchStr) {
        if (str == null || searchStr == null) {
            return -1;
        }
        return str.lastIndexOf(searchStr);
    }
    
    boolean indexOfAll(String str, String[] searchStrs) {
        if ((str == null) || (searchStrs == null)) {
            return false;
        }
        int sz = searchStrs.length;
        int tmp = 0;
        boolean btmp = true;

        for (int i = 0; i < sz; i++) {
            String search = searchStrs[i];
            if (search == null) {
            	btmp = false;
                continue;
            }
            tmp = str.indexOf(search);
            if (tmp == -1) {
                btmp = false;
                continue;
            }
        }
        return btmp;
    }
    
    /**
     * 키워드의 키워드 앞에 공백,특수문자가 와야함.
     * 키워드의 키워드 뒤에 공백,특수문자, 한글이 와야함.
     * @param html
     * @param key
     * @return
     */
    boolean englishPropernoun(String html, String[] key ){
    	boolean result = true;
    	
    	boolean s_char = false;
    	boolean e_char = false;
    	
    	int keyIdx  = 0;
    	String alphabet = "";
    	//String alphabet = "[a-zA-Z0-9]"; 숫자포함
    	for(int i = 0; i < key.length; i++){
    		
    		
    		//키워드가 있는지 검색
    		keyIdx  = html.indexOf(key[i]);
    		if(keyIdx<0){
    			return false;
    		}
    		
    		//앞글자 확인
    		s_char = false;
    		if(keyIdx == 0){
    			s_char = true;
    		}else{
    			//공백통과
    			if(html.subSequence(keyIdx-1, keyIdx).equals(" ")){
    				s_char = true;
    			}
    		}
    		
    		//뒷글자 확인
    		e_char = false;
    		if((keyIdx+key[i].length()+1)> html.length()){
    			e_char = true;
    		}else{
    			//공백통과
    			if(html.subSequence(keyIdx+key[i].length(), (keyIdx+key[i].length()+1)).equals(" ")){
    				e_char = true;
    			}else{
    				//한글통과
    				int c = html.subSequence(keyIdx+key[i].length(), (keyIdx+key[i].length()+1)).charAt(0);
    				if(c > 0xac00 && c < 0xd7a3){
    					e_char = true;
    				}
    			}
    		}
    		
    		if(!(s_char && e_char)){
    			return false;
    		}
    		
    	}
    	
    	return result;
    }
    
    String stampToDateTime(String stamp, String format) {
    	long unixSeconds = Long.parseLong(stamp);
    	Date date = new Date(unixSeconds*1000L); 
    	SimpleDateFormat sdf = new SimpleDateFormat(format);
    	String formattedDate = sdf.format(date);    	
    	return formattedDate;
    }
    
    String convertFacebookURL(String inputStr) {
    	String returnStr = "http://www.facebook.com/";
    	String middleURL = "/posts/";
    	
    	String[] arrStr = inputStr.split("_");
    	
    	for (int i=0; i<arrStr.length; i++) {
    		if (i == 0) {
    			returnStr += arrStr[i].toString();
    		} else if (i == 1) {
    			returnStr += middleURL + arrStr[i].toString();
    		}
    	}
    	
		return returnStr;
    }
    
  //문자열의 유사도 비교
  	public  int StrCompare(String s1, String s2)
  	{
  		int repercent = 0;
  		try{

  			//공백 제거
  			s1 = s1.replaceAll(" ","");
  			s2 = s2.replaceAll(" ","");

  			//소문자로 변환
  			s1 = s1.toLowerCase();
  			s2 = s2.toLowerCase();

  			int lcs = 0;
  			int ms = 0;
  			int i = 0;
  			int j = 0;

  			//바이트로 읽어들임
  			byte[] b1 = s1.getBytes();
  			byte[] b2 = s2.getBytes();

  			//바이트의 크기가져오기
  			int m = b1.length;
  			int n = b2.length;

  			//[128][128]의 배열 생성
  			int [][] LCS_Length_Table = new int[m+1][n+1];

  			//배열 초기화
  			for(i=1; i <  m; i++) LCS_Length_Table[i][0]=0;
  			for(j=0; j < n; j++) LCS_Length_Table[0][j]=0;

  			//루프를 돌며 바이트를 비교
  			for (i=1; i <= m; i++) {
  				for (j=1; j <= n; j++) {
  					if ((b1[i-1]) == (b2[j-1])) {
  						LCS_Length_Table[i][j] = LCS_Length_Table[i-1][j-1] + 1;
  					} else if (LCS_Length_Table[i-1][j] >= LCS_Length_Table[i][j-1]) {
  						LCS_Length_Table[i][j] = LCS_Length_Table[i-1][j];
  					} else {
  						LCS_Length_Table[i][j] = LCS_Length_Table[i][j-1];
  					}
  				}
  			}

  			//percent를 리턴
  			ms = (m + n) / 2;
  			lcs = LCS_Length_Table[m][n];
  			repercent = ((lcs*100)/ms);

  		}catch(ArithmeticException ex){
  			
  		}
  		return repercent;

  	}
  	
  	public void SendTelegram_v2(JSONArray jIdxInfo, JSONArray jCateotryIdxInfo, JSONObject jObjMeta){
  		
  		JSONObject jobj = null;
  		
  		JSONArray jsubArr = null;
  		JSONObject jsubObj = null;
  		JSONObject jsubObj2 = null;
  		
  		int[] arTsSgSeq = null;
  		int[] arTsSSeq = null;
  		boolean chkSend = true;

  		try{
  		
  			if(jTelegram != null) {
  				
  				//그룹방 별 루프
  				for(int i =0; i < jTelegram.length(); i++) {
  					
  					jobj = jTelegram.getJSONObject(i);
  					chkSend = true;
  					
  					
  				//사이트 필터링
  					if(chkSend) {
  						chkSend = false;
  						
  						
  						//사이트 그룹 체크
  	  					if(!jobj.getString("TS_SG_SEQS").equals("")) {
  	  						arTsSgSeq = (int[])jobj.get("TS_SG_SEQS");
  	  						int idx = binarySearch(arTsSgSeq, jObjMeta.getString("17_sg_seq"));
  	  					
  	  						if(idx >= 0) {
  	  							chkSend = true;	
  	  						}
  	  						
  	  					}
  	  					
  	  					if(!chkSend) {
  	  						//사이트 체크
  	  	  					if(!jobj.getString("TS_S_SEQS").equals("")) {
  	  	  						arTsSSeq = (int[])jobj.get("TS_S_SEQS");
  	  	  						int idx = binarySearch(arTsSSeq, jObjMeta.getString("10_s_seq"));
  	  	  						
  		  	  					if(idx >= 0) {
  		  	  						chkSend = true;	
  		  	  					}
  	  	  					}	
  	  					}
  	  					
  					}
  					
  					//키워드 필터링
  					if(chkSend) {
  						if(!jobj.getString("TS_KEYWORDS").equals("")) {
  							
  							chkSend = false;
  						
  							jsubArr = jobj.getJSONArray("TS_KEYWORDS");
  							
  							for(int j = 0; j < jsubArr.length(); j++) {
  								jsubObj = jsubArr.getJSONObject(j);
  								
  								for(int s =0; s < jIdxInfo.length(); s++) {
  									jsubObj2 = jIdxInfo.getJSONObject(s);
  									if(jsubObj.getString("xp").equals(jsubObj2.getString("2_K_XP")) && jsubObj.getString("yp").equals(jsubObj2.getString("3_K_YP"))) {
  										chkSend = true;
  										break;
  	  								}		
  					  	  	  	}
  								
  								if(chkSend) {
  									break;
  								}
  								
  							}
  						}
  					}
  					
  				    //카테고리 키워드 필터링
  					if(chkSend) {
  						
  						if(!jobj.getString("TS_KEYWORD_CATEGORYS").equals("")) {
  							
  							chkSend = false;
  						
  							jsubArr = jobj.getJSONArray("TS_KEYWORD_CATEGORYS");
  							
  							for(int j = 0; j < jsubArr.length(); j++) {
  								jsubObj = jsubArr.getJSONObject(j);
  								
  								for(int s =0; s < jCateotryIdxInfo.length(); s++) {
  									jsubObj2 = jCateotryIdxInfo.getJSONObject(s);
  									if(jsubObj.getString("xp").equals(jsubObj2.getString("2_K_XP")) && jsubObj.getString("yp").equals(jsubObj2.getString("3_K_YP"))) {
  										chkSend = true;
  										break;
  	  								}		
  					  	  	  	}
  								
  								if(chkSend) {
  									break;
  								}
  								
  							}
  						}
  					}
  					
  					//유사체크
  					if(chkSend) {
  						
  						if(jobj.getString("TS_SIMILAR").equals("1")) {
  							chkSend = false;
  	  						
  	  						jsubObj = new JSONObject();
  	  						jsubObj.put("01_ts_seq", jobj.getString("TS_SEQ"));
  	  						jsubObj.put("02_md_pseq", jObjMeta.getString("09_md_pseq"));
  	  						jsubObj2 = getExcuteQueryObj(PS_SELECT_TELEGRAM_LOG, jsubObj);
  	  	  					
  	  	  					if( Integer.parseInt(jsubObj2.getString("CNT")) == 0  ) {
  	  	  						chkSend = true;	
  	  	  					}	
  						}
  					}
  					
  					//발송
  					if(chkSend) {
  						Log.crond(className,"[텔레그램발송"+ jobj.getString("TS_SEQ") +"]" + jObjMeta.getString("01_md_seq"));
  						String message = URLEncoder.encode(jObjMeta.getString("06_md_title") + "\n" + jObjMeta.getString("07_md_url"),"UTF-8");
  						callTelegramAPI_v2(jobj.getString("TS_KEY"), jobj.getString("TS_ID"), message);
  						
  						JSONObject jTelObj = new JSONObject();  	  				
  	  					jTelObj.put("01_ts_seq", jobj.getString("TS_SEQ"));
  	  					jTelObj.put("02_md_seq", jObjMeta.getString("01_md_seq"));
  	  					jTelObj.put("03_tl_message", jObjMeta.getString("06_md_title"));
  	  					jTelObj.put("04_md_date", jObjMeta.getString("05_md_date"));
  	  					jTelObj.put("05_tl_send_status", "1");
  	  					jTelObj.put("06_md_pseq", jObjMeta.getString("09_md_pseq"));
  	  					setExcuteUpdate(PS_INSERT_TELEGRAM_LOG, jTelObj);
  					}
  					
  				}
  				
  			}
  			
  		}catch(Exception ex){
  			writeExceptionLog(ex, false);
  		}
  	} 
  	
  	
  	/**
  	 * 
  	 * EX_SITE_URL(제외 사이트 url)테이블에 
  	 * 설정된 사이트 번호와  수집데이터 s_seq가 일치하고, 제외 url이 수집데이터 url에 포함되어있을 경우, false반환 
  	 * @param s_seq 수집데이터 s_seq
  	 * @param url 수집데이터 url
  	 * @param md_seq 수집데이터 md_seq -로그용  	 
  	 * @return boolen (제외 설정 데이터 일 경우, fasle 반환) 
  	 *   
  	 * */
  	public boolean exSiteUrlFilter(String s_seq , String url){
  		boolean result = false;
  		try {			
	  		JSONObject jObj = null;
				for(int i=0; i<jExSiteUrl.length(); i++){
					jObj = (JSONObject)jExSiteUrl.getJSONObject(i);
					
					
					//사이트번호와 url데이터 있을 경우
					if(	null != s_seq && !"".equals(s_seq) && null != url && !"".equals(url)){
						//사이트번호와 , 제외 url이 해당 데이터 url에 포함되어있음 텔레그램 미발송 
						if(jObj.getString("S_SEQ").equals(s_seq) && url.contains(jObj.getString("EX_URL").replace("https://", "").replace("http://", ""))){
							result = true; 
							break;
						}
					}
				}
  		}catch(Exception ex){
  			writeExceptionLog(ex, false);
  		}
	  return result;
  		
  	}
  	
	public String callTelegramAPI_v2(String ts_key, String ts_id, String message ){
    	String returnData = ""; 
        StringBuffer param = new StringBuffer();
        
        param.append("type=sendRequest");
        param.append("&m_ch_id=" + ts_id);
        param.append("&message=" + message);
        param.append("&key="+ts_key);
        param.append("&preview=0");
        
        Log.crond(className,"[SEND]" + telegramUrl+ "?" +  param);
    	returnData = GetHtmlPost(telegramUrl, param.toString());
    	Log.crond(className,"[RECEIVE]" + returnData);
        return returnData;
    }
  	
  	
  	public String cutKey( String psBody, String psKeyword)
  	{
  		return cutKey(psBody, psKeyword, 100, "");
  	}
  	
  	public String cutKey( String psBody, String psKeyword, int piLen, String tColor )
    {
    	String result = null;
    	
		StringUtil su = new StringUtil();
		
		int idxPoint = -1; 

		try{
			if(psBody==null || psBody.length()==0){
				return "";
			}
				
			psBody = su.ChangeString( psBody.toLowerCase() );
			if (!su.nvl(psKeyword,"").equals("")) {
				
				String[] arrBFKey = su.getUniqeArray( psKeyword.split(" ") );			// 강조키워드 배열
				arrBFKey = searchGumunKey( arrBFKey );
				String[] arrAFKey = new String[arrBFKey.length];	// 강조키워드 html 배열
			
				for( int i=0 ; i<arrBFKey.length ; i++ )
				{
			    	//System.out.println("Keyword : "+arrBFKey[i]);
					arrBFKey[i] = arrBFKey[i].toLowerCase();
					arrAFKey[i] = "<font color='"+tColor+"'><b>"+arrBFKey[i]+"</b></font>";
					 
					
					if( idxPoint == -1 ) {
						idxPoint = psBody.indexOf(arrBFKey[i]);
					}
				}
				// piLen*2 길이만큼 본문내용 자르기
				int currPoint = 0;
				int startCut = 0;
				int endCut = 0;
				String startDot = "";
				String endDot = "";
		
				if( idxPoint >= 0 ) currPoint = idxPoint;
				// 내용자르기의 시작포인트
				if( currPoint >= piLen ) {
					startDot = "...";
					startCut = currPoint - piLen;
				} else {
					startCut = 0;
				}
				
				// 내용자르기의 마지막포인트
				if( psBody.length() >= (startCut + piLen*2) ) {
					endDot = "...";
					endCut = currPoint + piLen;
				} else {
					endCut = psBody.length();
				}
				if(startCut<0){
					startCut = 0;
				}
				if(endCut>psBody.length()-1){
					endCut = psBody.length();
				}
				result = startDot+psBody.substring(startCut, endCut)+endDot;
				
				/*
				if( idxPoint >= 0 ) {
					if (arrBFKey.length>0) {
						for( int i=0 ; i<arrBFKey.length ; i++  )
						{
							result = result.replaceAll( "(?is)"+arrBFKey[i], arrAFKey[i]);
						}
					}
				}
				*/
				
			} else {
				// piLen*2 길이만큼 본문내용 자르기
				int currPoint = 0;
				int startCut = 0;
				int endCut = 0;
				String startDot = "";
				String endDot = "";
		
				if( idxPoint >= 0 ) currPoint = idxPoint;
				// 내용자르기의 시작포인트
				if( currPoint >= piLen ) {
					startDot = "...";
					startCut = currPoint - piLen;
				} else {
					startCut = 0;
				}
				
				// 내용자르기의 마지막포인트
				if( psBody.length() >= (startCut + piLen*2) ) {
					endDot = "...";
					endCut = currPoint + piLen;
				} else {
					endCut = psBody.length()-1;
					
				}
				result = startDot+psBody.substring(startCut, endCut)+endDot;
			}
    	} catch( Exception ex ) {
    		Log.writeExpt(ex);
    		System.out.println(psBody+"/psKeyword:"+psKeyword+"/piLen:"+piLen+"/tColor:"+tColor);
		}
		
		return result;
    }
  	
  	/**
     * 구문 키워드를 찾아 키워드를 재배열한다.
     * @param key
     * @return
     */
    public String[] searchGumunKey(String[] key)
    {
    	int indexCnt = 0;
		int firstWordIndex = 0;
		int endWordIndex = 0;
		int gumunCount = 0;
		
		String notGumnunKey = "";
		String gumnunKeyword = "";
		String[] tempKey = null;
		String[] lastKey = null;
	
		
		//구문 인덱스 범위를 찾는다.
		for(int i =0 ; i<key.length ; i++){
			notGumnunKey += notGumnunKey.equals("") ? key[i] : " "+ key[i];
			
			if(key[i].indexOf("\"")>-1)
			{
					if(indexCnt==0) firstWordIndex = i;					
					if(indexCnt>0) endWordIndex = i;	
					indexCnt++;
			}			
		}
		
		if(endWordIndex>0)
		{
			gumunCount = endWordIndex -	firstWordIndex;	
			
			for(int i = firstWordIndex ; i<gumunCount+1 ; i++){
				
				gumnunKeyword += gumnunKeyword.equals("") ? key[i] : " "+ key[i];	
			}
			
			//구문 제외한 키워드
			notGumnunKey = notGumnunKey.replaceAll(gumnunKeyword,"");
			notGumnunKey = notGumnunKey.replaceAll("  ", " ");
			
						
			//구문 제외한 키워드 배열
			if(notGumnunKey.equals(" "))tempKey = notGumnunKey.split(" ");			
			if(tempKey!=null)
			{
				lastKey = new String[tempKey.length+1];
				for(int i =0 ; i<tempKey.length ; i++)
				{					
					lastKey[i] = tempKey[i];
				}
				lastKey[tempKey.length] = gumnunKeyword;
		
			}else{
				lastKey = new String[1];
				lastKey[0] = gumnunKeyword;
			}
		}else{
			lastKey = key;
		}
		for(int i =0 ; i<lastKey.length ; i++){
			lastKey[i] = lastKey[i].replaceAll("\"","");
		}
		
		
    	return lastKey;
    }
    
    public String getAutoSentiment(String strJson) {
    	String result = "3";
    	
    	JSONObject autoJobj = null;
    	JSONArray autoArry = null;
    	JSONObject autoSubJobj = null;
    	int autoPasitive = 0;
    	int autoNegative = 0;
    	double autoTrd_score = 0;
    	
    	try {
    		autoJobj = new JSONObject(strJson);
    		
    		if(!autoJobj.isNull("success_type") &&  autoJobj.getBoolean("success_type")){

    			//감성
    			if(!autoJobj.isNull("1")){
    				autoArry = autoJobj.getJSONArray("1"); 
    				
    				autoPasitive = 0;
    				autoNegative = 0;
    				
    				for(int i = 0; i < autoArry.length(); i++) {
    					autoSubJobj = autoArry.getJSONObject(i);
    					
    					if(autoSubJobj.getInt("ic_code") == 1) {
    						autoPasitive = autoSubJobj.getInt("count");
    					}else if(autoSubJobj.getInt("ic_code") == 2) {
    						autoNegative = autoSubJobj.getInt("count");
    					}
    				}
    				
    				if(autoPasitive > 0 || autoNegative > 0){
    					autoTrd_score = (double)autoPasitive / ((double)autoPasitive + autoNegative) * 100;
    					
    					if((int)Math.round(autoTrd_score) > 60  ){
    						result = "1";
    					}else if((int)Math.round(autoTrd_score) <= 40  ){
    						result = "2";
    					}else{
    						result = "3";
    					}
    				}else{
    					result = "3";
    				}
    			}
    		}
    	}catch (Exception ex) {
    		ex.printStackTrace();
		}
    	
    	return result; 
    }
    

    public ArrayList<JSONObject> makeAutoReplyRelation(String md_seq_meta, String md_seq_reply, String strJson) {
    	
    	ArrayList<JSONObject> result = new ArrayList<JSONObject>();
    	JSONObject resultObj = null;
    	
    	JSONObject autoJobj = null;
    	JSONArray autoArry = null;
    	JSONObject autoSubJobj = null;
    	
    	String pat_seqs = "";
    	String[] ar_pat_seq = null;
    	
    	try {
    		autoJobj = new JSONObject(strJson);
    		
    		if(!autoJobj.isNull("success_type") &&  autoJobj.getBoolean("success_type")){

    			//연관키워드
    			if(!autoJobj.isNull("3")){
    				autoArry = autoJobj.getJSONArray("3"); 
    				for(int i = 0; i < autoArry.length(); i++) {
    					autoSubJobj = autoArry.getJSONObject(i);
    					if(pat_seqs.equals("")) {
    						pat_seqs = autoSubJobj.getString("pat_seqs");	
    					}else {
    						pat_seqs += "," + autoSubJobj.getString("pat_seqs");
    					}
    				}
    				
        			ar_pat_seq = pat_seqs.split(",");
        			
        			for(int i =0; i < ar_pat_seq.length; i++) {
        				resultObj = new JSONObject();
        				resultObj.put("1_md_seq_meta", md_seq_meta);
        				resultObj.put("2_md_seq_reply", md_seq_reply);
        				resultObj.put("3_pat_seq", ar_pat_seq[i]);
        				result.add(resultObj);
        			}
    			}    			
    		}
    	}catch (Exception ex) {
    		ex.printStackTrace();
		}
    	
    	return result; 
    }
    
    public String makeAutoRelationWords(String strJson) {
    	
    	String result = "";
    	String words = "";
    	String[] arWord = null;
    	JSONObject autoJobj = null;
    	JSONArray autoArry = null;
    	JSONObject autoSubJobj = null;
    	
    	ArrayList<String> resultList = new ArrayList<String>();
    	
    	try {
    		autoJobj = new JSONObject(strJson);
    		
    		if(!autoJobj.isNull("success_type") &&  autoJobj.getBoolean("success_type")){

    			//연관키워드
    			if(!autoJobj.isNull("3")){
    				autoArry = autoJobj.getJSONArray("3"); 
    				for(int i = 0; i < autoArry.length(); i++) {
    					autoSubJobj = autoArry.getJSONObject(i);
    					
    					if(words.equals("")) {
    						words = autoSubJobj.getString("words");
    					}else {
    						words += "," + autoSubJobj.getString("words");
    					}
    				}
    				
    				if(!words.equals("")) {
    					arWord = words.split(",");
    					
    					for(int i =0; i < arWord.length; i++) {
    						if (!resultList.contains(arWord[i])) {
        	                    resultList.add(arWord[i]);
        	                }		
    					}
    					
        				for(int i =0; i < resultList.size(); i++) {
        					 
        					if(result.equals("")) {
        						result = resultList.get(i);
        					}else {
        						result += "," + resultList.get(i);
        					}
        				}
        				
        				
    				}
    			}    			
    		}
    	}catch (Exception ex) {
    		ex.printStackTrace();
		}
    	
    	return result; 
    }
    
}
