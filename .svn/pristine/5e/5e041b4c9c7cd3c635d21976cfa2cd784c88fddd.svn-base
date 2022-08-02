package risk.mobile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import risk.util.DateUtil;

public class PortalAlimiSettingBean {
	private String pas_seq  = null;
	private String pas_title = null;
	private String pas_chk = null;	
	private String pas_type = null;
	private String pas_sms_time = null;	
	private String pas_last_sendtime = null;
	private String pas_last_num = "";
	private int startMtNo = 0;
	private int endMtNo = 0;
	private String sendchk = "";
	
	private String tempMtNo = null;	
	private ArrayList arrReceiver = null;
	private String pas_tran_num = "";
	
	DateUtil du = new DateUtil();
	
	public String getPas_seq() {
		return pas_seq;
	}
	public void setPas_seq(String pas_seq) {
		this.pas_seq = pas_seq;
	}
	public String getPas_title() {
		return pas_title;
	}
	public void setPas_title(String pas_title) {
		this.pas_title = pas_title;
	}
	public String getPas_chk() {
		return pas_chk;
	}
	public void setPas_chk(String pas_chk) {
		this.pas_chk = pas_chk;
	}
	public String getPas_type() {
		return pas_type;
	}
	public void setPas_type(String pas_type) {
		this.pas_type = pas_type;
	}
	public String getPas_last_sendtime() {
		return pas_last_sendtime;
	}
	public void setPas_last_sendtime(String pas_last_sendtime) {
		this.pas_last_sendtime = pas_last_sendtime;
	}
	public String getPas_last_num() {
		return pas_last_num;
	}
	public void setPas_last_num(String pas_last_num) {
		this.pas_last_num = pas_last_num;
	}
	public String getSendchk() {
		return sendchk;
	}
	public void setSendchk(String sendchk) {
		this.sendchk = sendchk;
	}
	public String getPas_tran_num() {
		return pas_tran_num;
	}
	public void setPas_tran_num(String pas_tran_num) {
		this.pas_tran_num = pas_tran_num;
	}
	public String getPasTypeName() {
		String pasTypeName = "";
		if(pas_type!=null)
		{
			if(pas_type.equals("2")){
				pasTypeName="SMS";
			}else if(pas_type.equals("3")){
				pasTypeName="R-rimi";
			}
		}
		return pasTypeName;
	}
	public String getLastSendDate()
	{
		String lastSendDate = "-";
		if(pas_last_sendtime!=null)
		{
			try{
				lastSendDate = du.getDate(pas_last_sendtime, "yyyy-MM-dd");
			}catch(Exception e){
				System.out.println(e);
			}
		}
		return lastSendDate;
	}
	public String getLastSendDate(String format)
	{
		String lastSendDate = "-";
		if(pas_last_sendtime!=null)
		{
			try{
				lastSendDate = du.getDate(pas_last_sendtime, format);
			}catch(Exception e){
				System.out.println(e);
			}
		}
		return lastSendDate;
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
	public String getPas_sms_time() {
		return pas_sms_time;
	}
	public void setPas_sms_time(String pas_sms_time) {
		this.pas_sms_time = pas_sms_time;
	}
	
}
