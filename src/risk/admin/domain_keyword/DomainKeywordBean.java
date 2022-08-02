package risk.admin.domain_keyword;

public class DomainKeywordBean {
	private String KGxp = "0";
	private String KGyp = "0";
	private String KGzp = "0";
	private String KGtype = "0";
	private String KGvalue = "";
	
	private String k_seq;
	private String k_xp;
	private String k_yp;
	private String k_zp;
	private String k_value;
	private String k_pos;
	private String k_type;
	private String k_regdate;
	private String k_useyn;
	private String k_op;
	private String k_section_type;
	
	private String 	 ic_seq;           		
	private String 	 com_name;				  //회사 뎁스 이름
	private String 	 product_name;			  //제품군 뎁스 이름
	private int 	 com_type;                //회사타입
	private int 	 product_type;            //제품군타입
	private int 	 ic_type;                 //이슈타입
	private int 	 ic_code;				  //이슈코드
	private int 	 ic_ptype;				  //부모이슈타입
	private int 	 ic_pcode;				  //부모이슈코드

	

	public int getCom_type() {
		return com_type;
	}
	public void setCom_type(int com_type) {
		this.com_type = com_type;
	}
	public int getProduct_type() {
		return product_type;
	}
	public void setProduct_type(int product_type) {
		this.product_type = product_type;
	}
	
	public String getCom_name() {
		return com_name;
	}
	public void setCom_name(String com_name) {
		this.com_name = com_name;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public int getIc_type() {
		return ic_type;
	}
	public void setIc_type(int ic_type) {
		this.ic_type = ic_type;
	}
	public int getIc_code() {
		return ic_code;
	}
	public void setIc_code(int ic_code) {
		this.ic_code = ic_code;
	}
	public int getIc_ptype() {
		return ic_ptype;
	}
	public void setIc_ptype(int ic_ptype) {
		this.ic_ptype = ic_ptype;
	}
	public int getIc_pcode() {
		return ic_pcode;
	}
	public void setIc_pcode(int ic_pcode) {
		this.ic_pcode = ic_pcode;
	}
	public String getK_seq() {
		return k_seq;
	}
	public void setK_seq(String kSeq) {
		k_seq = kSeq;
	}
	public String getK_xp() {
		return k_xp;
	}
	public void setK_xp(String kXp) {
		k_xp = kXp;
	}
	public String getK_yp() {
		return k_yp;
	}
	public void setK_yp(String kYp) {
		k_yp = kYp;
	}
	public String getK_zp() {
		return k_zp;
	}
	public void setK_zp(String kZp) {
		k_zp = kZp;
	}
	public String getK_value() {
		return k_value;
	}
	public void setK_value(String kValue) {
		k_value = kValue;
	}
	public String getK_pos() {
		return k_pos;
	}
	public void setK_pos(String kPos) {
		k_pos = kPos;
	}
	public String getK_type() {
		return k_type;
	}
	public void setK_type(String kType) {
		k_type = kType;
	}
	public String getK_regdate() {
		return k_regdate;
	}
	public void setK_regdate(String kRegdate) {
		k_regdate = kRegdate;
	}
	public String getK_useyn() {
		return k_useyn;
	}
	public void setK_useyn(String kUseyn) {
		k_useyn = kUseyn;
	}
	public String getK_op() {
		return k_op;
	}
	public void setK_op(String kOp) {
		k_op = kOp;
	}
	public String getK_section_type() {
		return k_section_type;
	}
	public void setK_section_type(String k_section_type) {
		this.k_section_type = k_section_type;
	}
	public String getKGxp() {
        return KGxp;
    }
    public void setKGxp(String KGxp) {
        this.KGxp = KGxp;
    }
    
    public String getKGyp() {
        return KGyp;
    }
    public void setKGyp(String KGyp) {
        this.KGyp = KGyp;
    }
    
    public String getKGzp() {
        return KGzp;
    }
    public void setKGzp(String KGzp) {
        this.KGzp = KGzp;
    }
    
    public String getKGtype() {
        return KGtype;
    }
    public void setKGtype(String KGtype) {
        this.KGtype = KGtype;
    }    

    public String getKGvalue() {
        return KGvalue;
    }
    public void setKGvalue(String KGvalue) {
        this.KGvalue = KGvalue;
    }     
    
	public String getIc_seq() {
		return ic_seq;
	}
	public void setIc_seq(String ic_seq) {
		this.ic_seq = ic_seq;
	}

	public enum Type{LEFT,RIGHT};
	public enum Type2{ADD,DEL};
	public enum Mode{COMPANY,PRODUCT};
	
}
