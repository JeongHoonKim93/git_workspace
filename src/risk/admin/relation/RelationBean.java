package risk.admin.relation;

public class RelationBean {
	
	private String rk_seq = "";
	private String rk_name = "";
	private String rk_use = "";
	private String id_seq = "";
	private String rk_name_sum = "";
	
	public String getRk_name_sum() {
		return rk_name_sum;
	}
	public void setRk_name_sum(String rk_name_sum) {
		this.rk_name_sum = rk_name_sum;
	}
	public String getRk_seq() {
		return rk_seq;
	}
	public void setRk_seq(String rk_seq) {
		this.rk_seq = rk_seq;
	}
	public String getRk_name() {
		return rk_name;
	}
	public void setRk_name(String rk_name) {
		this.rk_name = rk_name;
	}
	public String getRk_use() {
		return rk_use;
	}
	public void setRk_use(String rk_use) {
		this.rk_use = rk_use;
	}
	public String getId_seq() {
		return id_seq;
	}
	public void setId_seq(String id_seq) {
		this.id_seq = id_seq;
	}
	
	public enum Type{LEFT,RIGHT};
	public enum Type2{ADD,DEL};
	public enum Mode{KEYWORD,SITE};
	
}
