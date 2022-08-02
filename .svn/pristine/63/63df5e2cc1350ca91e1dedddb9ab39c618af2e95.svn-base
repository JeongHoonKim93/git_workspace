/**
========================================================
주 시 스 템 : RSN
서브 시스템 : 정보검색
프로그램 ID : metaInfo
프로그램 명 : 메타 데이터 class
프로그램개요 : 메타 Data Beans
작 성 자 : 윤석준
작 성 일 : 2006. 04. 11
========================================================
수정자/수정일:
수정사유/내역:
========================================================
*/
package risk.search;
import risk.util.DateUtil;
import risk.util.StringUtil;

public class PortalBean {
	
	private StringUtil su = new StringUtil();
	private DateUtil du = new DateUtil();

	private int pm_seq; 		
	private String pm_date;     
	private String pm_title;   
	private String pm_url;      
	private String pm_site;     
	private String pm_board;       
	private int pm_s_seq;		
	private int pm_sb_seq;		
	private String l_alpha;         
	private int if_seq; 
	private long d_seq;
	
	public int getPm_seq() {
		return pm_seq;
	}
	public void setPm_seq(int pm_seq) {
		this.pm_seq = pm_seq;
	}
	public String getPm_date() {
		return pm_date;
	}
	//날짜 포멧
	public String getFormatPm_date(String dateFormat) {
			return du.getDate(this.pm_date, dateFormat);
	}
	public void setPm_date(String pm_date) {
		this.pm_date = pm_date;
	}
	public String getPm_title() {
		return pm_title;
	}
	public String getHtmlPm_title() {
		return su.toHtmlString(this.pm_title);
	}
	public void setPm_title(String pm_title) {
		this.pm_title = pm_title;
	}
	public String getPm_url() {
		return pm_url;
	}
	public void setPm_url(String pm_url) {
		this.pm_url = pm_url;
	}
	public String getPm_site() {
		return pm_site;
	}
	public void setPm_site(String pm_site) {
		this.pm_site = pm_site;
	}
	public String getPm_board() {
		return pm_board;
	}
	public void setPm_board(String pm_board) {
		this.pm_board = pm_board;
	}
	public int getPm_s_seq() {
		return pm_s_seq;
	}
	public void setPm_s_seq(int pm_s_seq) {
		this.pm_s_seq = pm_s_seq;
	}
	public int getPm_sb_seq() {
		return pm_sb_seq;
	}
	public void setPm_sb_seq(int pm_sb_seq) {
		this.pm_sb_seq = pm_sb_seq;
	}
	public String getL_alpha() {
		return l_alpha;
	}
	public void setL_alpha(String l_alpha) {
		this.l_alpha = l_alpha;
	}
	public int getIf_seq() {
		return if_seq;
	}
	public void setIf_seq(int if_seq) {
		this.if_seq = if_seq;
	}
	public long getD_seq() {
		return d_seq;
	}
	public void setD_seq(long d_seq) {
		this.d_seq = d_seq;
	}
	
}