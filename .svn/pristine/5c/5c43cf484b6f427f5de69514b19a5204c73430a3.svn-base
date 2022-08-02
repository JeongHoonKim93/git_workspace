package risk.mobile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import risk.util.DateUtil;

public class AlimiSettingBean {
	private String ts_seq = null;	
	private String ts_name = null;	
	private String ts_id = null;	
	private String ts_key = null;	
	private String ts_preview = null;	
	private String as_seq  = null;
	private String as_title = null;
	private String as_chk = null;	
	private String as_type = null;
	private String as_infotype = null;		
	private String kg_xps= null;
	private String kg_yps= null;
	private String as_sitetype = null;	
	private String sd_gsns = null;
	private String sg_seqs = null;	
	private String s_seqs = null;	
	private String keywords = null;	
	private String keywords_categorys = null;	
	private String operate_yn = null;	
	private String similar = null;	
	private String mt_types = null;
	private String as_sms_key = null;
	private String as_sms_exkey = null;
	private String as_sms_time = null;	
	private String as_interval = null;
	private String as_last_sendtime = null;
	private int as_same_percent = 0;
	private String as_last_num = "";
	private int startMtNo = 0;
	private int endMtNo = 0;
	private String sendchk = "";
	private String last_send_date = "";
	
	private String tempMtNo = null;	
	private ArrayList arrReceiver = null;
	
	private String as_sms_day = null;	
	private String as_sms_stime = null;	
	private String as_sms_etime = null;	
	private int as_same_cnt;
	private String as_same_type = null;
	
	private String as_tran_num = "";
	
	DateUtil du = new DateUtil();
	
	
	
	public String getAs_same_type() {
		return as_same_type;
	}
	public void setAs_same_type(String as_same_type) {
		this.as_same_type = as_same_type;
	}
	public String getAs_tran_num() {
		return as_tran_num;
	}
	public void setAs_tran_num(String as_tran_num) {
		this.as_tran_num = as_tran_num;
	}
	public int getAs_same_percent() {
		return as_same_percent;
	}
	public void setAs_same_percent(int as_same_percent) {
		this.as_same_percent = as_same_percent;
	}
	public String getSendchk() {
		return sendchk;
	}
	public void setSendchk(String sendchk) {
		this.sendchk = sendchk;
	}
	public String getAs_last_num() {
		return as_last_num;
	}
	public void setAs_last_num(String asLastNum) {
		as_last_num = asLastNum;
	}
	public int getAs_same_cnt() {
		return as_same_cnt;
	}
	public void setAs_same_cnt(int as_same_cnt) {
		this.as_same_cnt = as_same_cnt;
	}
	public String getAs_sms_day() {
		return as_sms_day;
	}
	public void setAs_sms_day(String as_sms_day) {
		this.as_sms_day = as_sms_day;
	}
	public String getAs_sms_stime() {
		return as_sms_stime;
	}
	public void setAs_sms_stime(String as_sms_stime) {
		this.as_sms_stime = as_sms_stime;
	}
	public String getAs_sms_etime() {
		return as_sms_etime;
	}
	public void setAs_sms_etime(String as_sms_etime) {
		this.as_sms_etime = as_sms_etime;
	}
	public String getAs_seq() {
		return as_seq;
	}
	public void setAs_seq(String as_seq) {
		this.as_seq = as_seq;
	}
	public String getAs_title() {
		return as_title;
	}
	public void setAs_title(String as_title) {
		this.as_title = as_title;
	}
	public String getAs_type() {
		return as_type;
	}
	public String getAsTypeName() {
		String asTypeName = "";
		if(as_type!=null)
		{
			if(as_type.equals("1"))
			{
				asTypeName="이메일";
			}else if(as_type.equals("2")){
				asTypeName="SMS";
			}else if(as_type.equals("3")){
				asTypeName="R-rimi";
			}else if(as_type.equals("4")){
				asTypeName="수동 메일";
			}else if(as_type.equals("5")){
				asTypeName="수동 SMS";
			}else if(as_type.equals("6")){
				asTypeName="모바일 앱";
			}else if(as_type.equals("7")){
				asTypeName="유사 메일";
			}else if(as_type.equals("8")){
				asTypeName="유사 SMS";
			}
		}
		return asTypeName;
	}
	public void setAs_type(String as_type) {
		this.as_type = as_type;
	}
	public String getAs_infotype() {
		return as_infotype;
	}
	public void setAs_infotype(String as_infotype) {
		this.as_infotype = as_infotype;
	}
	public String getKg_xps() {
		return kg_xps;
	}
	public void setKg_xps(String kg_xps) {
		this.kg_xps = kg_xps;
	}		
	public String getKg_yps() {
		return kg_yps;
	}
	public void setKg_yps(String kgYps) {
		kg_yps = kgYps;
	}
	public String getMt_types() {
		return mt_types;
	}
	public void setMt_types(String mt_types) {
		this.mt_types = mt_types;
	}
	public String getAs_sms_key() {
		return as_sms_key;
	}
	public void setAs_sms_key(String as_sms_key) {
		this.as_sms_key = as_sms_key;
	}
	public String getAs_sms_exkey() {
		return as_sms_exkey;
	}
	public void setAs_sms_exkey(String as_sms_exkey) {
		this.as_sms_exkey = as_sms_exkey;
	}
	public String getAs_interval() {
		return as_interval;
	}
	public String getasIntervalName(){
		String name = "-";
		if(as_interval!=null)
		{
			if(as_interval.equals("0"))
			{
				name="건별";
			}else if(as_interval.equals("30")){
				name="30분";
			}else if(as_interval.equals("60")){
				name="1시간";
			}else if(as_interval.equals("180")){
				name="3시간";
			}
		}
		return name;
	}
	public void setAs_interval(String as_interval) {
		this.as_interval = as_interval;
	}
	public String getAs_last_sendtime() {
		return as_last_sendtime;
	}
	public void setAs_last_sendtime(String as_last_sendtime) {
		this.as_last_sendtime = as_last_sendtime;
	}
	public String getLastSendDate()
	{
		String lastSendDate = "-";
		if(as_last_sendtime!=null)
		{
			try{
				lastSendDate = du.getDate(as_last_sendtime, "yyyy-MM-dd");
			}catch(Exception e){
				System.out.println(e);
			}
		}
		return lastSendDate;
	}
	public String getLastSendDate(String format)
	{
		String lastSendDate = "-";
		if(as_last_sendtime!=null)
		{
			try{
				lastSendDate = du.getDate(as_last_sendtime, format);
			}catch(Exception e){
				System.out.println(e);
			}
		}
		return lastSendDate;
	}	
	public String getAs_sitetype() {
		return as_sitetype;
	}
	public void setAs_sitetype(String as_sitetype) {
		this.as_sitetype = as_sitetype;
	}
	public String getSd_gsns() {
		return sd_gsns;
	}
	public void setSd_gsns(String sd_gsns) {
		this.sd_gsns = sd_gsns;
	}
	public String getSg_seqs() {
		return sg_seqs;
	}
	public void setSg_seqs(String sg_seqs) {
		this.sg_seqs = sg_seqs;
	}
	public String getAs_chk() {
		return as_chk;
	}
	public void setAs_chk(String as_chk) {
		this.as_chk = as_chk;
	}
	public String getTempMtNo() {
		return tempMtNo;
	}
	public void setTempMtNo(String tempMtNo) {
		this.tempMtNo = tempMtNo;
	}
	public ArrayList getArrReceiver() {
		return arrReceiver;
	}
	public void setArrReceiver(ArrayList arrReceiver) {
		this.arrReceiver = arrReceiver;
	}
	public int getStartMtNo() {
		return startMtNo;
	}
	public void setStartMtNo(int startMtNo) {
		this.startMtNo = startMtNo;
	}
	public int getEndMtNo() {
		return endMtNo;
	}
	public void setEndMtNo(int endMtNo) {
		this.endMtNo = endMtNo;
	}
	public String getAs_sms_time() {
		return as_sms_time;
	}
	public void setAs_sms_time(String as_sms_time) {
		this.as_sms_time = as_sms_time;
	}
	public String getTs_seq() {
		return ts_seq;
	}
	public void setTs_seq(String ts_seq) {
		this.ts_seq = ts_seq;
	}
	public String getTs_name() {
		return ts_name;
	}
	public void setTs_name(String ts_name) {
		this.ts_name = ts_name;
	}
	public String getTs_id() {
		return ts_id;
	}
	public void setTs_id(String ts_id) {
		this.ts_id = ts_id;
	}
	public String getTs_key() {
		return ts_key;
	}
	public void setTs_key(String ts_key) {
		this.ts_key = ts_key;
	}
	public String getTs_preview() {
		return ts_preview;
	}
	public void setTs_preview(String ts_preview) {
		this.ts_preview = ts_preview;
	}
	public String getS_seqs() {
		return s_seqs;
	}
	public void setS_seqs(String s_seqs) {
		this.s_seqs = s_seqs;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getKeywords_categorys() {
		return keywords_categorys;
	}
	public void setKeywords_categorys(String keywords_categorys) {
		this.keywords_categorys = keywords_categorys;
	}
	public String getOperate_yn() {
		return operate_yn;
	}
	public void setOperate_yn(String operate_yn) {
		this.operate_yn = operate_yn;
	}
	public String getSimilar() {
		return similar;
	}
	public void setSimilar(String similar) {
		this.similar = similar;
	}
	public String getLast_send_date() {
		return last_send_date;
	}
	public void setLast_send_date(String last_send_date) {
		this.last_send_date = last_send_date;
	}
	
}
