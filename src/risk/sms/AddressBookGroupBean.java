package risk.sms;

import risk.util.DateUtil;

public class AddressBookGroupBean {
	
	private String ag_seq="";
	private String ag_name="";
	private String ag_send_date="";
	private String ag_L_count="";
	
	public String getAg_seq() {
		return ag_seq;
	}
	public void setAg_seq(String ag_seq) {
		this.ag_seq = ag_seq;
	}
	public String getAg_name() {
		return ag_name;
	}
	public void setAg_name(String ag_name) {
		this.ag_name = ag_name;
	}
	
	public String getAg_send_date() {
		return ag_send_date;
	}
	public void setAg_send_datae(String ag_send_date) {
		this.ag_send_date = ag_send_date;
	}
	//날짜 포멧
	DateUtil du = new DateUtil();
	public String getFormatAg_send_date(String dateFormat) {
		return du.getDate(ag_send_date, dateFormat);
	}
	public String getAg_L_count() {
		return ag_L_count;
	}
	public void setAg_L_count(String ag_L_count) {
		this.ag_L_count = ag_L_count;
	}
	
}
