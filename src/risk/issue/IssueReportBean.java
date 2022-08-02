package risk.issue;

import risk.util.DateUtil;
import risk.util.StringUtil;

public class IssueReportBean {
	
	private StringUtil su = new StringUtil();
	private DateUtil du = new DateUtil();		
	private String ir_seq;
	private String ir_type;
	private String ir_title;
	private String ir_html;
	private String ir_memo;
	private String ir_regdate;
	private String ir_maildate;
	private String ir_mailyn;
	private String ir_mailcnt;
	private String id_seq = "";
	private String ir_capture_img_name = "";
	private String ir_sdate = "";
	private String ir_edate = "";
	
	public String getId_seq() {
		
		if(id_seq.equals("")) {
			id_seq = "0";
		}
		
		return id_seq;
	}
	public void setId_seq(String id_seq) {
		this.id_seq = id_seq;
	}
	public String getIr_seq() {
		return ir_seq;
	}
	public void setIr_seq(String irSeq) {
		ir_seq = irSeq;
	}
	public String getIr_type() {
		return ir_type;
	}
	public void setIr_type(String irType) {
		ir_type = irType;
	}
	public String getIr_title() {
		return ir_title;
	}
	public void setIr_title(String irTitle) {
		ir_title = irTitle;
	}
	public String getIr_html() {
		return ir_html;
	}
	public void setIr_html(String irHtml) {
		ir_html = irHtml;
	}
	public String getIr_memo() {
		return ir_memo;
	}
	public void setIr_memo(String irMemo) {
		ir_memo = irMemo;
	}
	public String getIr_regdate() {
		return ir_regdate;
	}
	//날짜 포멧
	public String getFormatIr_regdate(String dateFormat) {
		return du.getDate(this.ir_regdate, dateFormat);
	}
	public void setIr_regdate(String irRegdate) {
		ir_regdate = irRegdate;
	}	
	public String getIr_maildate() {
		return ir_maildate;
	}
	//날짜 포멧
	public String getFormatIr_maildate(String dateFormat) {
		return du.getDate(this.ir_maildate, dateFormat);
	}
	public void setIr_maildate(String irMaildate) {
		ir_maildate = irMaildate;
	}	
	public String getIr_mailyn() {
		return ir_mailyn;
	}
	public void setIr_mailyn(String irMailyn) {
		ir_mailyn = irMailyn;
	}
	public String getIr_mailcnt() {
		return ir_mailcnt;
	}
	public void setIr_mailcnt(String irMailcnt) {
		ir_mailcnt = irMailcnt;
	}	
	public String getIr_capture_img_name() {
		return ir_capture_img_name;
	}
	public void setIr_capture_img_name(String ir_capture_img_name) {
		this.ir_capture_img_name = ir_capture_img_name;
	}
	public String getIr_sdate() {
		return ir_sdate;
	}
	public void setIr_sdate(String ir_sdate) {
		this.ir_sdate = ir_sdate;
	}
	public String getIr_edate() {
		return ir_edate;
	}
	public void setIr_edate(String ir_edate) {
		this.ir_edate = ir_edate;
	}
	
	
}
