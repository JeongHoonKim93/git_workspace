<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%@ page import="	java.util.ArrayList
                 	,risk.mobile.AlimiSettingBean                
					,risk.mobile.AlimiSettingDao
					,risk.util.ParseRequest
					,risk.admin.site.SitegroupBean
				 	,risk.admin.site.SiteMng
				 	,risk.search.userEnvMgr
				 	,risk.search.keywordInfo
				 	,risk.admin.classification.classificationMgr
				 	,risk.admin.classification.clfBean
				 	,java.util.List
					" 
%>
<%	

	ParseRequest pr = new ParseRequest(request);
	AlimiSettingBean asBean = new AlimiSettingBean();
	AlimiSettingDao asDao = new AlimiSettingDao();	
	
/* 	int result = 0;
	String mode = null;
	String nowpage = null;
	String as_seq = null;
	String as_seqs = null;
	String ts_seq = null;
	String ts_seqs = null;
	String as_title = null;
	String as_chk = null;
	String as_infotype = null;
	String as_type = null;
	String k_xps = null;
	String k_yps = null;
	String sg_seqs = null;
	String sd_gsns = null;
	String mt_types = null;
	String as_sms_key = null;
	String as_sms_exkey = null;
	String as_sms_time = null;
	String as_interval = null;
	int as_same_percent	 = 0;
	String as_tran_num = "";
	String as_same_type = null;
 */	
	
	String ab_seqs = null;	
	int result = 0;
	String mode = null;
	String nowpage = null;
	String ts_seq = null;
	String ts_seqs = null;
	String ts_name = null;
	String ts_key = null;
	String ts_id = null;
	String sg_seqs = null;
	String sd_gsns = null;
	String k_xp = null;
	String k_yp = null;
	String keywords = null;
	String warning_yp = null;
	String warning_zp = null;
	String warningkeywords = null;
	String as_chk = null;
	String preview_yn = null;
	String as_same_type = null;

	
	pr.printParams();
	mode = pr.getString("mode");
	nowpage = pr.getString("nowpage","1");
/* 	as_seq = pr.getString("as_seq");
	as_seqs = pr.getString("as_seqs");
	ts_seq = pr.getString("ts_seq");
	ts_seqs = pr.getString("ts_seqs");
	as_title = pr.getString("as_title");
	as_chk = pr.getString("as_chk");
	as_type = pr.getString("as_type");
	as_infotype = pr.getString("as_infotype");
	k_xps = pr.getString("k_xps");
	k_yps = pr.getString("k_yps");
	sg_seqs = pr.getString("sg_seqs");
	sd_gsns = pr.getString("sd_gsns");
	mt_types = pr.getString("mt_types");
	as_sms_key = pr.getString("as_sms_key");
	as_sms_exkey = pr.getString("as_sms_exkey");
	as_sms_time = pr.getString("as_sms_time");
	as_interval = pr.getString("as_interval");
	as_same_percent = pr.getInt("as_same_percent");
	as_same_type = pr.getString("as_same_type");
	as_tran_num = pr.getString("as_tran_num");
 */	
	ab_seqs = pr.getString("ab_seqs");
	ts_seq = pr.getString("ts_seq");
	ts_seqs = pr.getString("ts_seqs");
	ts_name = pr.getString("ts_name");
	ts_key = pr.getString("ts_key");
	ts_id = pr.getString("ts_id");
	sg_seqs = pr.getString("sg_seqs");
	sd_gsns = pr.getString("sd_gsns");
	k_xp = pr.getString("k_xp");
	k_yp = pr.getString("k_yp");
	keywords = pr.getString("keywords");
	
	warning_yp = pr.getString("warning_yp");
	warning_zp = pr.getString("warning_zp");
//	warningkeywords = pr.getString("warningkeywords");
	as_chk = pr.getString("as_chk");
	preview_yn = pr.getString("preview_yn");
	as_same_type = pr.getString("as_same_type");
	asBean = new AlimiSettingBean();

	
/* 	asBean.setAs_seq(as_seq);
	asBean.setAs_title(as_title);	
	asBean.setAs_chk(as_chk);
	asBean.setAs_type(as_type);
	asBean.setAs_infotype(as_infotype);
	asBean.setKg_xps(k_xps);
	asBean.setKg_yps(k_yps);	
	asBean.setSg_seqs(sg_seqs);
	asBean.setSd_gsns(sd_gsns);
	asBean.setMt_types(mt_types);
	asBean.setAs_sms_key(as_sms_key);
	asBean.setAs_sms_exkey(as_sms_exkey);
	asBean.setAs_sms_time(as_sms_time);
	asBean.setAs_interval(as_interval);
	asBean.setAs_sms_day("");
	asBean.setAs_sms_stime("");
	asBean.setAs_sms_etime("");
	asBean.setAs_same_cnt(0);
	asBean.setAs_same_percent(as_same_percent);
	asBean.setAs_same_type(as_same_type);
	asBean.setAs_tran_num(as_tran_num.replaceAll("-", "").replaceAll(" ", "").replaceAll("\\.", "").replaceAll("/", ""));
 */		
	
	asBean.setTs_name(ts_name);
	//asBean.setTs_key(ts_key);
	//asBean.setTs_id(ts_id);
	asBean.setSg_seqs(sg_seqs);
	asBean.setS_seqs(sd_gsns);
	asBean.setKeywords(keywords);
//	asBean.setKeywords_categorys(warningkeywords);
	asBean.setOperate_yn(as_chk);
	asBean.setTs_preview(preview_yn);
	asBean.setSimilar(as_same_type);
	asBean.setTs_seq(ts_seq);
	

/* 	if(mode.equals("INSERT"))
	{		
		result = asDao.insertAlimisSet(asBean , ab_seqs);
		
	} */
	if(mode.equals("UPDATE")){
		
		result = asDao.updateTelegramSet(asBean);
		
		
		System.out.println("keywords는 --------------------------" +keywords);
	}
	
	
/* 	else if(mode.equals("DELETE")){
		result = asDao.deleteReportSet(as_seqs);
	}
 */	
	
%>
<script language="javascript">
<!--
	parent.contentsFrame.location.href = 'alimi_setting_list.jsp?nowpage=<%=nowpage%>';
//-->
</script>