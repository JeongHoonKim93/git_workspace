package risk.issue;

import java.net.URLEncoder;
import java.util.ArrayList;

import risk.util.DateUtil;
import risk.util.StringUtil;

public class IssueDataBean {

	private StringUtil su = new StringUtil();
	private DateUtil du = new DateUtil();	
	private String id_seq;				//이슈데이터번호
	private String i_seq;				//이슈번호
	private String it_seq;				//이슈타이틀번호
	private String md_seq;				//기사번호
	private String s_seq;				//사이트번호
	private String sg_seq;				//사이트그룹번호
	private String sg_name;				//사이트그룹번호
	private String md_site_menu;		//사이트 메뉴
	private String md_site_name;        //사이트이름
	private String md_date;				//수집일시
	private String id_title;			//제목
	private String id_url;				//사이트URL
	private String id_writter;          //글쓴이
	private String id_content;			//기사내용
	private String id_regdate;			//이슈등록일
	private String id_mailyn;			//메일여부
	private String id_useyn;			//이슈사용여부
	private String md_same_ct; 			//유사 개수
	private String m_seq;				//등록자번호
	private String m_name;				//등록자이름
	private ArrayList arrCodeList;		//이슈 코드리스트
	private ArrayList arrCommentList;	//이슈 코멘트리스트
	private String l_alpha;				//언어코드
	private String md_type;            	//정보유형
	private String md_pseq;            	//모기사 데이터 번호
	private String id_reportyn;        	//보고서 포함 여부
	private String id_news;				//지면 여부(면,단)
	private String md_writer;			//보도자료 기자명
	
	
	private String k_xp;   			
	private String k_yp;
	private String media_info;
	private String f_news;
	private String id_mobile;
	private String md_same_ct_check; 			//최초 기사인지 확인
	private String relationkeys;  			    //연관키워드
	private String relationkeysCode;  			//연관키워드 코드 (구분)
	private String cardrelationkeys;  			//연관키워드 - 현대카드
	private String caprelationkeys;  			//연관키워드 - 현대캐피탈
	private String comrelationkeys;  			//연관키워드 - 현대커머셜
	private String autorelationkeys;  			//연관키워드 - 자동
	private String id_senti; 					//성향 
	private String id_clout;					//영향력
	private String id_trans_useyn;				//데이터 전송여부
	private String id_comment = "";				//품질안전센터 커멘트
	private String hycard_comment = "";				//현대카드 주요이슈 커멘트
	private String hycap_comment = "";				//현대캐피탈 주요이슈 커멘트
	private String hycom_comment = "";				//현대커머셜 주요이슈 커멘트
	private String hycard_senti = "";				//현대카드 감성
	private String hycap_senti = "";				//현대캐피탈 감성
	private String hycom_senti = "";				//현대커머셜 감성
	private String reply_count = "";				//댓글 수량
	
	





	private int p_cnt;
	private int n_cnt;
	private int g_cnt;
	private int[] cnt;
	
	private String v_seq;             // VOC코드
	
	private String h_seq;
	
	//hot keyword
	private String keywordInfo;
	
	//연관어
	private String rk_seq;
	private String rk_name;
	private String rk_type;
	
	
	// 임시 변수 
	private String temp1 ="";
	private String temp2 ="";
	private String temp3 ="";
	private String temp4 ="";
	
	
	
	//엑셀관련
	private String date;
	private String a8;
	private String a9_2;
	private String a14_2;
	private String a20_2;
	private String rk2;
	private String a10;
	private String a9_3;
	private String a14_3;
	private String a20_3;
	private String rk3;
	private String a11;
	private String a9_4;
	private String a14_4;
	private String a20_4;
	private String rk4;
	private String a12;
	private String a9_5;
	private String a15_5;
	private String a20_5;
	private String rk5;
	private String a13;
	private String a9_7;
	private String a15_7;
	private String a20_7;
	private String rk7;
	private String a16;
	private String a6;
	private String a17;
	private String a18;
	private String a19;
	private String senti;
	
	private String code1;
	private String code1_senti;
	private String code1_info;
	private String code2;
	private String code2_senti;
	private String code2_info;
	private String code3;
	private String code3_senti;
	private String code3_info;
	private String code4;
	private String code4_senti;
	private String code4_info;
	private String code5;
	private String code5_senti;
	private String code5_info;
	private String code6;
	private String code7;
	private String code7_senti;
	private String code7_info;
	private String code8;
	private String code8_senti;
	private String code8_relKey;
	private String code9;
	private String code10;
	private String code11;
	private String code12;
	private String code13;
	private String code14;
	private String code15;
	private String code16;
	private String code17;
	private String code18;
	private String code19;
	private String code20;
	private String code21;
	private String code22;
	private String code23;
	private String code24;
	private String code25;
	private String code26;
	private String code27;
	private String code28;
	private String code29;
	private String code31;
	private String code32;
	private String code33;
	private String code34;
	private String code35;
	private String code36;
	private String code37;
	private String code38;
	private String code39;
	
	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public String getA8() {
		return a8;
	}


	public void setA8(String a8) {
		this.a8 = a8;
	}


	public String getA9_2() {
		return a9_2;
	}


	public void setA9_2(String a9_2) {
		this.a9_2 = a9_2;
	}


	public String getA14_2() {
		return a14_2;
	}


	public void setA14_2(String a14_2) {
		this.a14_2 = a14_2;
	}


	public String getA20_2() {
		return a20_2;
	}


	public void setA20_2(String a20_2) {
		this.a20_2 = a20_2;
	}


	public String getRk2() {
		return rk2;
	}


	public void setRk2(String rk2) {
		this.rk2 = rk2;
	}


	public String getA10() {
		return a10;
	}


	public void setA10(String a10) {
		this.a10 = a10;
	}


	public String getA9_3() {
		return a9_3;
	}


	public void setA9_3(String a9_3) {
		this.a9_3 = a9_3;
	}


	public String getA14_3() {
		return a14_3;
	}


	public void setA14_3(String a14_3) {
		this.a14_3 = a14_3;
	}


	public String getA20_3() {
		return a20_3;
	}


	public void setA20_3(String a20_3) {
		this.a20_3 = a20_3;
	}


	public String getRk3() {
		return rk3;
	}


	public void setRk3(String rk3) {
		this.rk3 = rk3;
	}


	public String getA11() {
		return a11;
	}


	public void setA11(String a11) {
		this.a11 = a11;
	}


	public String getA9_4() {
		return a9_4;
	}


	public void setA9_4(String a9_4) {
		this.a9_4 = a9_4;
	}


	public String getA14_4() {
		return a14_4;
	}


	public void setA14_4(String a14_4) {
		this.a14_4 = a14_4;
	}


	public String getA20_4() {
		return a20_4;
	}


	public void setA20_4(String a20_4) {
		this.a20_4 = a20_4;
	}


	public String getRk4() {
		return rk4;
	}


	public void setRk4(String rk4) {
		this.rk4 = rk4;
	}


	public String getA12() {
		return a12;
	}


	public void setA12(String a12) {
		this.a12 = a12;
	}


	public String getA9_5() {
		return a9_5;
	}


	public void setA9_5(String a9_5) {
		this.a9_5 = a9_5;
	}


	public String getA15_5() {
		return a15_5;
	}


	public void setA15_5(String a15_5) {
		this.a15_5 = a15_5;
	}


	public String getA20_5() {
		return a20_5;
	}


	public void setA20_5(String a20_5) {
		this.a20_5 = a20_5;
	}


	public String getRk5() {
		return rk5;
	}


	public void setRk5(String rk5) {
		this.rk5 = rk5;
	}


	public String getA13() {
		return a13;
	}


	public void setA13(String a13) {
		this.a13 = a13;
	}


	public String getA9_7() {
		return a9_7;
	}


	public void setA9_7(String a9_7) {
		this.a9_7 = a9_7;
	}


	public String getA15_7() {
		return a15_7;
	}


	public void setA15_7(String a15_7) {
		this.a15_7 = a15_7;
	}


	public String getA20_7() {
		return a20_7;
	}


	public void setA20_7(String a20_7) {
		this.a20_7 = a20_7;
	}


	public String getRk7() {
		return rk7;
	}


	public void setRk7(String rk7) {
		this.rk7 = rk7;
	}


	public String getA16() {
		return a16;
	}


	public void setA16(String a16) {
		this.a16 = a16;
	}


	public String getA6() {
		return a6;
	}


	public void setA6(String a6) {
		this.a6 = a6;
	}


	public String getA17() {
		return a17;
	}


	public void setA17(String a17) {
		this.a17 = a17;
	}


	public String getA18() {
		return a18;
	}


	public void setA18(String a18) {
		this.a18 = a18;
	}

	public String getA19() {
		return a19;
	}

	public void setA19(String a19) {
		this.a19 = a19;
	}	
	
	public String getSenti() {
		return senti;
	}


	public void setSenti(String senti) {
		this.senti = senti;
	}

	//보고서 원문 기사 링크 URL encode 처리
	public String getId_urlEncoding() {
		  String result = null;
			    
		  try {
			    result = URLEncoder.encode(id_url, "UTF-8");
			  } catch(Exception ex) {
			    ex.printStackTrace();
			  }
			    
		  return result;
	}
	
	
	public String getId_mobile() {
		return id_mobile;
	}
	public void setId_mobile(String id_mobile) {
		this.id_mobile = id_mobile;
	}
	public String getH_seq() {
		return h_seq;
	}
	public void setH_seq(String h_seq) {
		this.h_seq = h_seq;
	}
	public String getKeywordInfo() {
		return keywordInfo;
	}
	public void setKeywordInfo(String keywordInfo) {
		this.keywordInfo = keywordInfo;
	}
	public int[] getCnt() {
		return cnt;
	}
	public void setCnt(int[] cnt) {
		this.cnt = cnt;
	}
	public String getMd_type() {
		return md_type;
	}
	public void setMd_type(String mdType) {
		md_type = mdType;
	}
	public String getId_seq() {
		return id_seq;
	}
	public void setId_seq(String iSeq) {
		id_seq = iSeq;
	}	
	public String getI_seq() {
		return i_seq;
	}
	public void setI_seq(String iSeq) {
		i_seq = iSeq;
	}
	public String getIt_seq() {
		return it_seq;
	}
	public void setIt_seq(String itSeq) {
		it_seq = itSeq;
	}
	public String getMd_seq() {
		return md_seq;
	}
	public void setMd_seq(String mdSeq) {
		md_seq = mdSeq;
	}
	public String getS_seq() {
		return s_seq;
	}
	public void setS_seq(String sSeq) {
		s_seq = sSeq;
	}	
	public String getMd_site_menu() {
		return md_site_menu;
	}
	public void setMd_site_menu(String mdSiteMenu) {
		md_site_menu = mdSiteMenu;
	}
	public String getMd_site_name() {
		return md_site_name;
	}
	public void setMd_site_name(String mdSiteName) {
		md_site_name = mdSiteName;
	}
	public String getMd_date() {
		return md_date;
	}
	public void setMd_date(String mdDate) {
		md_date = mdDate;
	}
	//날짜 포멧
	public String getFormatMd_date(String dateFormat) {
		return du.getDate(this.md_date, dateFormat);
	}	
	public String getId_writter() {
		return id_writter;
	}
	public void setId_writter(String idWritter) {
		id_writter = idWritter;
	}
	public String getId_content() {
		return id_content;
	}
	public void setId_content(String iContent) {
		id_content = iContent;
	}	
	public String getId_regdate() {
		return id_regdate;
	}
	public void setId_regdate(String iRegdate) {
		id_regdate = iRegdate;
	}
	//날짜 포멧
	public String getFormatId_regdate(String dateFormat) {
		return du.getDate(this.id_regdate, dateFormat);
	}
	public String getId_mailyn() {
		return id_mailyn;
	}
	public void setId_mailyn(String iMailyn) {
		id_mailyn = iMailyn;
	}	
	public String getSg_seq() {
		return sg_seq;
	}
	public void setSg_seq(String sgSeq) {
		sg_seq = sgSeq;
	}
	public String getId_title() {
		return su.toHtmlString(id_title);
	}
	public String getId_title_p() {
		return su.ChangeString_bak(id_title).replaceAll("\"", "").replaceAll("'", "").replaceAll("\n", "");
	}
	
	public String getId_title_replace() {
		String tmp_title = getId_title_p();
		tmp_title = tmp_title.replaceAll("\"", "").replaceAll("'", "");
		return su.toHtmlString(tmp_title);
	}
	
	public void setId_title(String idTitle) {
		id_title = idTitle;
	}	
	public String getId_url() {
		return id_url;
	}
	public void setId_url(String iUrl) {
		id_url = iUrl;
	}
	public String getId_useyn() {
		return id_useyn;
	}
	public void setId_useyn(String iUseyn) {
		id_useyn = iUseyn;
	}	
	public String getM_seq() {
		return m_seq;
	}
	public void setM_seq(String mSeq) {
		m_seq = mSeq;
	}	
	public String getM_name() {
		return m_name;
	}
	public void setM_name(String mName) {
		m_name = mName;
	}
	public ArrayList getArrCodeList() {
		return arrCodeList;
	}
	public void setArrCodeList(ArrayList arrCodeList) {
		this.arrCodeList = arrCodeList;
	}	
	public ArrayList getArrCommentList() {
		return arrCommentList;
	}
	public void setArrCommentList(ArrayList arrCommentList) {
		this.arrCommentList = arrCommentList;
	}
	public String getMd_same_ct() {
		return md_same_ct;
	}
	public void setMd_same_ct(String mdSameCt) {
		md_same_ct = mdSameCt;
	}
	public int getP_cnt() {
		return p_cnt;
	}
	public void setP_cnt(int pCnt) {
		p_cnt = pCnt;
	}
	public int getN_cnt() {
		return n_cnt;
	}
	public void setN_cnt(int nCnt) {
		n_cnt = nCnt;
	}
	public int getG_cnt() {
		return g_cnt;
	}
	public void setG_cnt(int gCnt) {
		g_cnt = gCnt;
	}			
	public String getL_alpha() {
		return l_alpha;
	}
	public void setL_alpha(String l_alpha) {
		this.l_alpha = l_alpha;
	}
	public String getMd_pseq() {
		return md_pseq;
	}
	public void setMd_pseq(String md_pseq) {
		this.md_pseq = md_pseq;
	}
	public String getId_reportyn() {
		return id_reportyn;
	}
	public void setId_reportyn(String id_reportyn) {
		this.id_reportyn = id_reportyn;
	}
	
	public String getK_xp() {
		return k_xp;
	}
	public void setK_xp(String k_xp) {
		this.k_xp = k_xp;
	}
	public String getK_yp() {
		return k_yp;
	}
	public void setK_yp(String k_yp) {
		this.k_yp = k_yp;
	}
	public String getMedia_info() {
		return media_info;
	}
	public void setMedia_info(String media_info) {
		this.media_info = media_info;
	}
	public String getF_news() {
		return f_news;
	}
	public void setF_news(String f_news) {
		this.f_news = f_news;
	}
	public String getMd_same_ct_check() {
		return md_same_ct_check;
	}
	public void setMd_same_ct_check(String md_same_ct_check) {
		this.md_same_ct_check = md_same_ct_check;
	}
	
	public String getTemp1() {
		return temp1;
	}
	public void setTemp1(String temp1) {
		this.temp1 = temp1;
	}
	public String getTemp2() {
		return temp2;
	}
	public void setTemp2(String temp2) {
		this.temp2 = temp2;
	}
	public String getTemp3() {
		return temp3;
	}
	public void setTemp3(String temp3) {
		this.temp3 = temp3;
	}
	public String getTemp4() {
		return temp4;
	}
	public void setTemp4(String temp4) {
		this.temp4 = temp4;
	}
	public String getV_seq() {
		return v_seq;
	}
	public void setV_seq(String v_seq) {
		this.v_seq = v_seq;
	}
	public void setSg_name(String sg_name) {
		this.sg_name = sg_name;
	}
	public String getSg_name() {
		return sg_name;
	}
	//연관키워드
	public String getRelationkeys() {
		return relationkeys;
	}
	public void setRelationkeys(String relationkeys) {
		this.relationkeys = relationkeys;
	}
	//연관키워드 코드 (구분)
	public String getRelationkeysCode() {
		return relationkeysCode;
	}
	public void setRelationkeysCode(String relationkeysCode) {
		this.relationkeysCode = relationkeysCode;
	}


	public String getCardrelationkeys() {
		return cardrelationkeys;
	}


	public void setCardrelationkeys(String cardrelationkeys) {
		this.cardrelationkeys = cardrelationkeys;
	}


	public String getCaprelationkeys() {
		return caprelationkeys;
	}


	public void setCaprelationkeys(String caprelationkeys) {
		this.caprelationkeys = caprelationkeys;
	}


	public String getComrelationkeys() {
		return comrelationkeys;
	}


	public void setComrelationkeys(String comrelationkeys) {
		this.comrelationkeys = comrelationkeys;
	}

	public String getAutorelationkeys() {
		return autorelationkeys;
	}


	public void setAutorelationkeys(String autorelationkeys) {
		this.autorelationkeys = autorelationkeys;
	}


	public String getId_news() {
		return id_news;
	}
	public void setId_news(String id_news) {
		this.id_news = id_news;
	}

	public String getCode1() {
		return code1;
	}

	public void setCode1(String code1) {
		this.code1 = code1;
	}


	public String getCode1_senti() {
		return code1_senti;
	}


	public void setCode1_senti(String code1_senti) {
		this.code1_senti = code1_senti;
	}


	public String getCode1_info() {
		return code1_info;
	}


	public void setCode1_info(String code1_info) {
		this.code1_info = code1_info;
	}


	public String getCode2() {
		return code2;
	}


	public void setCode2(String code2) {
		this.code2 = code2;
	}


	public String getCode2_senti() {
		return code2_senti;
	}


	public void setCode2_senti(String code2_senti) {
		this.code2_senti = code2_senti;
	}


	public String getCode2_info() {
		return code2_info;
	}


	public void setCode2_info(String code2_info) {
		this.code2_info = code2_info;
	}


	public String getCode3() {
		return code3;
	}


	public void setCode3(String code3) {
		this.code3 = code3;
	}


	public String getCode3_senti() {
		return code3_senti;
	}


	public void setCode3_senti(String code3_senti) {
		this.code3_senti = code3_senti;
	}


	public String getCode3_info() {
		return code3_info;
	}


	public void setCode3_info(String code3_info) {
		this.code3_info = code3_info;
	}


	public String getCode4() {
		return code4;
	}


	public void setCode4(String code4) {
		this.code4 = code4;
	}


	public String getCode4_senti() {
		return code4_senti;
	}


	public void setCode4_senti(String code4_senti) {
		this.code4_senti = code4_senti;
	}


	public String getCode4_info() {
		return code4_info;
	}


	public void setCode4_info(String code4_info) {
		this.code4_info = code4_info;
	}


	public String getCode5() {
		return code5;
	}


	public void setCode5(String code5) {
		this.code5 = code5;
	}


	public String getCode5_senti() {
		return code5_senti;
	}


	public void setCode5_senti(String code5_senti) {
		this.code5_senti = code5_senti;
	}


	public String getCode5_info() {
		return code5_info;
	}


	public void setCode5_info(String code5_info) {
		this.code5_info = code5_info;
	}

	public String getCode6() {
		return code6;
	}


	public void setCode6(String code6) {
		this.code6 = code6;
	}

	public String getCode7() {
		return code7;
	}


	public void setCode7(String code7) {
		this.code7 = code7;
	}


	public String getCode7_senti() {
		return code7_senti;
	}


	public void setCode7_senti(String code7_senti) {
		this.code7_senti = code7_senti;
	}


	public String getCode7_info() {
		return code7_info;
	}


	public void setCode7_info(String code7_info) {
		this.code7_info = code7_info;
	}


	public String getCode8() {
		return code8;
	}


	public void setCode8(String code8) {
		this.code8 = code8;
	}


	public String getCode8_senti() {
		return code8_senti;
	}


	public void setCode8_senti(String code8_senti) {
		this.code8_senti = code8_senti;
	}


	public String getCode8_relKey() {
		return code8_relKey;
	}


	public void setCode8_relKey(String code8_relKey) {
		this.code8_relKey = code8_relKey;
	}


	public String getCode9() {
		return code9;
	}


	public void setCode9(String code9) {
		this.code9 = code9;
	}
	
	public String getCode10() {
		return code10;
	}


	public void setCode10(String code10) {
		this.code10 = code10;
	}


	public String getCode11() {
		return code11;
	}


	public void setCode11(String code11) {
		this.code11 = code11;
	}


	public String getCode12() {
		return code12;
	}


	public void setCode12(String code12) {
		this.code12 = code12;
	}
	

	public String getCode13() {
		return code13;
	}


	public void setCode13(String code13) {
		this.code13 = code13;
	}
	

	public String getCode14() {
		return code14;
	}


	public void setCode14(String code14) {
		this.code14 = code14;
	}
	

	public String getCode15() {
		return code15;
	}


	public void setCode15(String code15) {
		this.code15 = code15;
	}
	

	public String getCode16() {
		return code16;
	}
	
	public void setCode16(String code16) {
		this.code16 = code16;
	}
	
	public String getCode17() {
		return code17;
	}

	public void setCode17(String code17) {
		this.code17 = code17;
	}
	

	public String getCode18() {
		return code18;
	}


	public void setCode18(String code18) {
		this.code18 = code18;
	}
	

	public String getCode19() {
		return code19;
	}


	public void setCode19(String code19) {
		this.code19 = code19;
	}
	
	public String getCode20() {
		return code20;
	}


	public String getCode21() {
		return code21;
	}

	
	public void setCode20(String code20) {
		this.code20 = code20;
	}

	
	public String getId_senti() {
		return id_senti;
	}
	
	public String getId_clout() {
		return id_clout;
	}
	

	public void setId_clout(String id_clout) {
		this.id_clout = id_clout;
	}


	public void setCode21(String code21) {
		this.code21 = code21;
	}

	public String getCode22() {
		return code22;
	}


	public void setCode22(String code22) {
		this.code22 = code22;
	}


	public String getCode23() {
		return code23;
	}


	public void setCode23(String code23) {
		this.code23 = code23;
	}


	public String getCode24() {
		return code24;
	}


	public void setCode24(String code24) {
		this.code24 = code24;
	}


	public String getCode25() {
		return code25;
	}


	public void setCode25(String code25) {
		this.code25 = code25;
	}

	
	public String getCode26() {
		return code26;
	}


	public void setCode26(String code26) {
		this.code26 = code26;
	}


	public String getCode27() {
		return code27;
	}


	public void setCode27(String code27) {
		this.code27 = code27;
	}

	public String getCode28() {
		return code28;
	}


	public void setCode28(String code28) {
		this.code28 = code28;
	}


	public String getCode29() {
		return code29;
	}


	public void setCode29(String code29) {
		this.code29 = code29;
	}

	
	public String getCode31() {
		return code31;
	}


	public void setCode31(String code31) {
		this.code31 = code31;
	}


	public String getCode32() {
		return code32;
	}


	public void setCode32(String code32) {
		this.code32 = code32;
	}


	public String getCode33() {
		return code33;
	}


	public void setCode33(String code33) {
		this.code33 = code33;
	}


	public String getCode34() {
		return code34;
	}


	public void setCode34(String code34) {
		this.code34 = code34;
	}


	public String getCode35() {
		return code35;
	}


	public void setCode35(String code35) {
		this.code35 = code35;
	}

	
	public String getCode36() {
		return code36;
	}


	public void setCode36(String code36) {
		this.code36 = code36;
	}


	public String getCode37() {
		return code37;
	}


	public void setCode37(String code37) {
		this.code37 = code37;
	}

	
	public String getCode38() {
		return code38;
	}


	public void setCode38(String code38) {
		this.code38 = code38;
	}


	public String getCode39() {
		return code39;
	}


	public void setCode39(String code39) {
		this.code39 = code39;
	}

	
	public void setId_senti(String id_senti) {
		this.id_senti = id_senti;
	}

	public String getId_trans_useyn() {
		return id_trans_useyn;
	}
	
	
	public void setId_trans_useyn(String id_trans_useyn) {
		this.id_trans_useyn = id_trans_useyn;
	}
	
	public String getId_comment() {
		return id_comment;
	}
	
	
	public void setId_comment(String id_comment) {
		this.id_comment = id_comment;
	}


	public String getHycard_comment() {
		return hycard_comment;
	}


	public void setHycard_comment(String hycard_comment) {
		this.hycard_comment = hycard_comment;
	}


	public String getHycap_comment() {
		return hycap_comment;
	}


	public void setHycap_comment(String hycap_comment) {
		this.hycap_comment = hycap_comment;
	}


	public String getHycom_comment() {
		return hycom_comment;
	}


	public void setHycom_comment(String hycom_comment) {
		this.hycom_comment = hycom_comment;
	}


	public String getHycard_senti() {
		return hycard_senti;
	}


	public void setHycard_senti(String hycard_senti) {
		this.hycard_senti = hycard_senti;
	}


	public String getHycap_senti() {
		return hycap_senti;
	}


	public void setHycap_senti(String hycap_senti) {
		this.hycap_senti = hycap_senti;
	}


	public String getHycom_senti() {
		return hycom_senti;
	}


	public void setHycom_senti(String hycom_senti) {
		this.hycom_senti = hycom_senti;
	}


	public String getReply_count() {
		return reply_count;
	}


	public void setReply_count(String reply_count) {
		this.reply_count = reply_count;
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


	public String getRk_type() {
		return rk_type;
	}


	public void setRk_type(String rk_type) {
		this.rk_type = rk_type;
	}


	public String getMd_writer() {
		return md_writer;
	}


	public void setMd_writer(String md_writer) {
		this.md_writer = md_writer;
	}

	
	
	
}
