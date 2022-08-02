package risk.admin.backup;

public class BackupListBean {

	private String bl_seq;		
	private String bl_tbName;		
	private String bl_op;		
	private String bl_useYn;		
	private String bl_del_useYn;		
	private int bl_day_term;		
	private String ins_field_name;		
	private String del_date_field_name;
	private String del_date_field_type;
	private String bl_comment;
	private String engine;
	private String table_rows;
	private String create_time;
	private String update_time;
	
	
	public String getBl_seq() {
		return bl_seq;
	}
	public void setBl_seq(String bl_seq) {
		this.bl_seq = bl_seq;
	}
	public String getBl_tbName() {
		return bl_tbName;
	}
	public void setBl_tbName(String bl_tbName) {
		this.bl_tbName = bl_tbName;
	}
	public String getBl_op() {
		return bl_op;
	}
	public void setBl_op(String bl_op) {
		this.bl_op = bl_op;
	}
	
	public String getBl_useYn() {
		return bl_useYn;
	}
	public void setBl_useYn(String bl_useYn) {
		this.bl_useYn = bl_useYn;
	}
	
	public String getBl_del_useYn() {
		return bl_del_useYn;
	}
	public void setBl_del_useYn(String bl_del_useYn) {
		this.bl_del_useYn = bl_del_useYn;
	}
	public int getBl_day_term() {
		return bl_day_term;
	}
	public void setBl_day_term(int bl_day_term) {
		this.bl_day_term = bl_day_term;
	}
	public String getIns_field_name() {
		return ins_field_name;
	}
	public void setIns_field_name(String ins_field_name) {
		this.ins_field_name = ins_field_name;
	}
	public String getDel_date_field_name() {
		return del_date_field_name;
	}
	public void setDel_date_field_name(String del_date_field_name) {
		this.del_date_field_name = del_date_field_name;
	}
	public String getDel_date_field_type() {
		return del_date_field_type;
	}
	public void setDel_date_field_type(String del_date_field_type) {
		this.del_date_field_type = del_date_field_type;
	}
	public String getBl_comment() {
		return bl_comment;
	}
	public void setBl_comment(String bl_comment) {
		this.bl_comment = bl_comment;
	}
	public String getEngine() {
		return engine;
	}
	public void setEngine(String engine) {
		this.engine = engine;
	}
	public String getTable_rows() {
		return table_rows;
	}
	public void setTable_rows(String table_rows) {
		this.table_rows = table_rows;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	
	
	
}
