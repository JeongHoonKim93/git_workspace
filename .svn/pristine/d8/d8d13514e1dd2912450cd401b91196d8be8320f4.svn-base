<%@page import="java.util.Arrays"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Map"%>
<%@ page import="risk.util.ParseRequest"%>
<%@ page import="risk.util.DateUtil"%>
<%@ page import="risk.util.StringUtil"%>
<%@ page import="risk.util.RsnAnalysis"%>
<%@ page import="risk.search.MetaBean"%>
<%@ page import="risk.search.MetaMgr"%>
<%@ page import="risk.search.DomainKeywordMgr"%>
<%@ page import="risk.issue.IssueCodeMgr"%>
<%@ page import="risk.issue.IssueDataBean"%>
<%@ page import="risk.issue.IssueCommentBean"%>
<%@ page import="risk.issue.IssueMgr"%>
<%@ page import="risk.issue.IssueCodeBean"%>
<%@include file="../inc/sessioncheck.jsp"%>
<%
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	
	DateUtil	 du = new DateUtil();
	StringUtil	 su = new StringUtil();
	MetaMgr  metaMgr = new MetaMgr();
	DomainKeywordMgr  dkMgr = new DomainKeywordMgr();
	IssueCodeMgr icm = new IssueCodeMgr();
	IssueMgr issueMgr = new IssueMgr();
	RsnAnalysis ana = new RsnAnalysis();
	
	// Parameter
	String txtcmt = pr.getString("txtcmt");
	
	
	//카드,캐피탈,커머셜 코멘트
/*  String hycard_txtcmt = pr.getString("hycard_txtcmt");
	String hycap_txtcmt = pr.getString("hycap_txtcmt");
	String hycom_txtcmt = pr.getString("hycom_txtcmt");
 */	
 
	String txtcmt2 = pr.getString("txtcmt2");
	String mode = pr.getString("mode");
	String mode2 = pr.getString("mode2","");
	String subMode = pr.getString("subMode","");
	String md_seq = pr.getString("md_seq");
	String md_seqs = pr.getString("md_seqs");
	String id_seqs = pr.getString("id_seqs");
	String child = pr.getString("child", "");
	String menu = pr.getString("menu","");
	String rp_count = pr.getString("rp_count");
	String rp_count2 = pr.getString("rp_count2");
	
	
	// Bean
	IssueDataBean idBean = new IssueDataBean();
	MetaBean metaBean = new MetaBean();
	IssueCommentBean icmBean = null;
	int ic_seq = 0;
	String hytype = "";
	String hycode = "";
	ArrayList arrIcBean = null;
	ArrayList subArrIcBean = null;
	//분류체계코드 (0:분류항목 포함, 1:분류항목 제외)
	icm.init(0);		
	Map issueCodeMap = null;
	Map issueCodeDetailMap = null;
	Map relationKeywordR_Map = null;
	String relationkeywordR_str = "";
	
	//Depth가 1Depth이상인 기준 IC_TYPE (고객사마다 다름) 
	//String parentTypes = "1,11,12,13,14,18,17,16,15,19,21,22,23,24,28,27,26,25,29,31,32,33,34,38,37,36,35,39"; 
	String parentTypes = "1,3,4,5,6,7,8,9,10,11,12,13,14,2"; 
	//String requisiteParentTypes = "12,13,14,22,23,24,32,33,34"; //필수체크항목 (주제, 게시물 유형, 성향)
	
	//String hycard_requisiteParentTypes ="12,13,14"; // 현카드 필수체크항목 (주제, 게시물 유형, 성향)
	//String hycap_requisiteParentTypes = "22,23,24"; // 현캐피 필수체크항목 (주제, 게시물 유형, 성향)
	//String hycom_requisiteParentTypes = "32,33,34"; // 현커머 필수체크항목 (주제, 게시물 유형, 성향)
	
	HashMap<String, ArrayList<String[]>> subDepthList = issueMgr.getSubdepthList(parentTypes); //parentTypes 하위 뎁스 리스트
	ArrayList<String[]> autoLastDepthCodeList;
	HashMap<String, String> autoCheckMap = new HashMap<String, String>();

	//자동롤링용 연관키워드 한줄로 만들기
	ArrayList relationKeyAll = issueMgr.getRelationKey();

	StringBuffer relKey_buff = null;
	String streamKey = "";
	if (relationKeyAll.size() > 0) {
		for (int i = 0; i < relationKeyAll.size(); i++) {

	if (relKey_buff == null) {
		relKey_buff = new StringBuffer((String) relationKeyAll.get(i));
	} else {
		relKey_buff.append(",").append((String) relationKeyAll.get(i));
	}
		}

		if (relKey_buff != null) {
	streamKey = relKey_buff.toString();
		}
	}

	String autoCompleteSite = "";

	if (mode.equals("insert")) {
		//메타 정보
		if (subMode.equals("solr")) {
			metaBean.setMd_seq(pr.getString("md_seq"));
			metaBean.setS_seq(pr.getString("s_seq"));
			metaBean.setSg_seq(pr.getString("sg_seq"));
			metaBean.setMd_site_name(pr.getString("md_site_name"));
			metaBean.setMd_site_menu("SOLR");
			metaBean.setMd_date(du.getDate(pr.getString("md_date"), "yyyy-MM-dd HH:mm:ss"));
			metaBean.setMd_title(su.dbString(pr.getString("md_title")));
			metaBean.setMd_url(su.dbString(pr.getString("md_url")));
			metaBean.setL_alpha("KOR");
		} else if (subMode.equals("TOP")) {
			metaBean = metaMgr.getTopData(md_seq);
			//ic_seq = icm.getSiteMapCode(metaBean.getSg_seq());  //사이트 이슈 코드 맵핑 정보 코드 시퀀스로 가져옴
		} else if (subMode.equals("warning")) {
			metaBean = metaMgr.getWarningMetaData(md_seq, mode2);
			//ic_seq = icm.getSiteMapCode(metaBean.getSg_seq());  //사이트 이슈 코드 맵핑 정보 코드 시퀀스로 가져옴
			autoLastDepthCodeList = metaMgr.getLastDepthMapList(metaBean.getMd_seq());
			autoCheckMap = metaMgr.getAutoMap(autoLastDepthCodeList);

		} else if(subMode.equals("domain")) {
			metaBean = dkMgr.getMetaData(md_seq, mode2);
			autoLastDepthCodeList = dkMgr.getLastDepthMapList(metaBean.getMd_seq()); 
			autoCheckMap = dkMgr.getAutoMap(autoLastDepthCodeList);	
		}else {
			metaBean = metaMgr.getMetaData(md_seq, mode2);
			//ic_seq = icm.getSiteMapCode(metaBean.getSg_seq());  //사이트 이슈 코드 맵핑 정보 코드 시퀀스로 가져옴
			autoLastDepthCodeList = metaMgr.getLastDepthMapList(metaBean.getMd_seq());
			autoCheckMap = metaMgr.getAutoMap(autoLastDepthCodeList);

		}
	} else if (mode.equals("update")) {
		
		//이슈 정보
		idBean = issueMgr.getIssueDataBean_V2(md_seq);
		issueCodeMap = issueMgr.getIssueDataCodeMap(idBean.getId_seq());
		//relationKeywordR_Map = issueMgr.getRelationKeywordR_Map(idBean.getId_seq());
		relationkeywordR_str  = issueMgr.getRelationKeyword_str(idBean.getId_seq());
		icmBean = issueMgr.SelectIssueComment(idBean.getId_seq());
	} else if (mode.equals("new")) {
		ArrayList arAutoCompleteSite = issueMgr.getAutoCompleteSite();
		StringBuffer strBuffer = null;
		for (int i = 0; i < arAutoCompleteSite.size(); i++) {
	if (strBuffer == null) {
		strBuffer = new StringBuffer((String) arAutoCompleteSite.get(i));
	} else {
		strBuffer.append(",").append((String) arAutoCompleteSite.get(i));
	}
		}
		if (relKey_buff != null) {
			autoCompleteSite = strBuffer.toString();
		}

	}
	//자동 맵핑
%>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title></title>
	<link rel="stylesheet" type="text/css" href="../../css/base.css" />
	<link href="../../css/jquery.autocomplete.css" rel="stylesheet"type="text/css" />
	<script src="../../js/jquery.js" type="text/javascript"></script>
	<script src="../../js/ajax.js" type="text/javascript"></script>
	<script src="../../js/popup.js" type="text/javascript"></script>
	<script src="../issue/js/issue_popup.js" type="text/javascript"></script>
	<script src="../../js/common.js"  type="text/javascript"></script>
	<script src="../../js/jquery.autocomplete.js" type="text/javascript"></script>
</head>
<body>
	<iframe id="if_samelist" name="if_samelist" width="100%" height="0" src="about:blank"></iframe>
	<img  id="sending" src="../../images/search/saving.gif" style="position: absolute;  " >
	<input name="streamKey" id="streamKey" type="hidden" value="<%=streamKey%>">
	<input name="autoCompleteSite" id="autoCompleteSite" type="hidden" value="<%=autoCompleteSite%>">
	<form name="fSend" id="fSend" action="issue_data_prc.jsp" method="post" onsubmit="return false;">
		<input name="mode" id="mode" type="hidden" value="<%=mode%>"><!-- 모드 -->	
		<input name="rp_count" id="rp_count" type="hidden" value="<%=rp_count%>">
		<input name="txtcmt" id="txtcmt" type="hidden" value="<%=txtcmt%>">
		<input type="hidden" name="mode2" value="<%=mode2 %>">
		<input name="menu" id="menu" type="hidden" value="<%=menu%>">
		<input name="typeCodes" id="typeCodes" type="hidden" value="">
		<input name="subMode" id="subMode" type="hidden" value="<%=subMode %>">
	<!-- 	<input name="typeCodesDetail" id="typeCodesDetail" type="hidden" value="">
		<input name="typeCodesInfo" id="typeCodesInfo" type="hidden" value="">-->
		<input name="relationkeyCode" id="relationkeyCode" type="hidden" value=""> 
		<input name="relationkeyNames" id="relationkeyNames" type="hidden" value=""> 
		<input name="relationkeyNames1" id="relationkeyNames1" type="hidden" value=""> 
		<input name="relationkeyNames2" id="relationkeyNames2" type="hidden" value=""> 
		<input name="relationkeyAutoNames" id="relationkeyAutoNames" type="hidden" value=""> 
		<input name="param_ic_type" id="param_ic_type" type="hidden" value="">
		<input name="parentTypes" id="parentTypes" type="hidden" value="<%=parentTypes%>">
		
		
<%-- 		
		<input name="hycard_requisiteParentTypes" id="hycard_requisiteParentTypes" type="hidden" value="<%=hycard_requisiteParentTypes%>">
		<input name="hycap_requisiteParentTypes" id="hycap_requisiteParentTypes" type="hidden" value="<%=hycap_requisiteParentTypes%>">
		<input name="hycom_requisiteParentTypes" id="hycom_requisiteParentTypes" type="hidden" value="<%=hycom_requisiteParentTypes%>">
 --%>		
		
		
		<input name="cloutType" id="cloutType" type="hidden" value="">
		<input name="sentiType" id="sentiType" type="hidden" value="">
		<input name="transType" id="transType" type="hidden" value="">
		<%if(mode.equals("insert")){ %>
			<input name="md_seq" id="md_seq" type="hidden" value="<%=metaBean.getMd_seq()%>"><!-- 기사번호 -->
			<input name="md_pseq" id="md_pseq" type="hidden" value="<%=metaBean.getMd_pseq()%>"><!-- 모 기사번호 -->
			<input name="s_seq" id="s_seq" type="hidden" value="<%=metaBean.getS_seq()%>"><!-- 사이트번호 -->
			<input name="sg_seq" id="sg_seq" type="hidden" value="<%=metaBean.getSg_seq()%>"><!-- 사이트 그룹 -->
			<input name="md_date" id="md_date" type="hidden" value="<%=metaBean.getMd_date()%>"><!-- 수십 시간-->
			<input name="md_site_menu" id="md_site_menu" type="hidden" value="<%=metaBean.getMd_site_menu()%>"><!-- 사이트 메뉴 -->
			<input name="md_same_ct" id="md_same_ct" type="hidden" value="<%=metaBean.getMd_same_count()%>"><!-- 유사개수 -->
			<input name="l_alpha" id="l_alpha" type="hidden" value="<%=metaBean.getL_alpha()%>"><!--언어코드 -->
			<input name="param_id_title" id="param_id_title" type="hidden" value="">
			<input name="param_id_content" id="param_id_content" type="hidden" value="">
			<input name="param_id_url" id="param_id_url" type="hidden" value="">
			<input name="param_md_site_name" id="param_md_site_name" type="hidden" value=""> 
			<input name="param_id_regdate" id="param_id_regdate" type="hidden" value="">
			<input name="param_md_type" id="param_md_type" type="hidden" value="<%=metaBean.getMd_type()%>">			
		<%} else if(mode.equals("multi")){ %>
			<input type="hidden" name="md_seqs" value="<%=md_seqs %>">
		<%} else if(mode.equals("update_multi")){ 	
			if(icmBean.getId_comment() != null){%>
			<input name="txtcmt2" id="txtcmt2" type="hidden" value="<%=icmBean.getId_comment()%>">
			<%}%>
			<input type="hidden" name="id_seqs" value="<%=id_seqs %>">
			<input name="child" id="child" type="hidden" value="<%=child%>">
		<%} else if(mode.equals("update")){
			if(idBean.getReply_count() != null){%>
			<input name="rp_count2" id="rp_count2" type="hidden" value="<%=idBean.getReply_count()%>">
			<%}					
			if(icmBean.getId_comment() != null){%>
			<input name="txtcmt2" id="txtcmt2" type="hidden" value="<%=icmBean.getId_comment()%>">
			<%}%>
 			<input name="id_seq" id="id_seq" type="hidden" value="<%=idBean.getId_seq()%>"><!-- 기사번호 -->
			<input name="md_seq" id="md_seq" type="hidden" value="<%=idBean.getMd_seq()%>"><!-- 기사번호 -->
			<input name="md_pseq" id="md_pseq" type="hidden" value="<%=idBean.getMd_pseq()%>"><!-- 모 기사번호 -->
			<input name="s_seq" id="s_seq" type="hidden" value="<%=idBean.getS_seq()%>"><!-- 사이트번호 -->
			<input name="sg_seq" id="sg_seq" type="hidden" value="<%=idBean.getSg_seq()%>"><!-- 사이트 그룹 -->
			<input name="reg_type" id="reg_type" type="hidden" value="<%=idBean.getTemp1()%>"><!-- 사이트 그룹 -->
			<input name="md_date" id="md_date" type="hidden" value="<%=idBean.getMd_date()%>"><!-- 수십 시간-->
			<input name="md_site_menu" id="md_site_menu" type="hidden" value="<%=idBean.getMd_site_menu()%>"><!-- 사이트 메뉴 -->
			<input name="id_mailyn" id="id_mailyn" type="hidden" value="<%=idBean.getId_mailyn()%>"><!-- 사이트 메뉴 -->
			<input name="child" id="child" type="hidden" value="<%=child%>">
			<input name="param_id_title" id="param_id_title" type="hidden" value="">
			<input name="param_id_content" id="param_id_content" type="hidden" value="">
			<input name="param_id_url" id="param_id_url" type="hidden" value="">
			<input name="param_md_site_name" id="param_md_site_name" type="hidden" value="">
			<input name="param_id_regdate" id="param_id_regdate" type="hidden" value="">
			<input name="param_md_type" id="param_md_type" type="hidden" value="<%=idBean.getMd_type()%>">
		<%} else if(mode.equals("new")){%>
			<input name="md_seq" id="md_seq" type="hidden" value="0"><!-- 기사번호 -->
			<input name="md_pseq" id="md_pseq" type="hidden" value="0"><!-- 모 기사번호 -->
			<input name="s_seq" id="s_seq" type="hidden" value=""><!-- 사이트번호 -->
			<input name="sg_seq" id="sg_seq" type="hidden" value=""><!-- 사이트 그룹 -->
			<input name="md_date" id="md_date" type="hidden" value=""><!-- 수십 시간-->
			<input name="md_site_menu" id="md_site_menu" type="hidden" value=""><!-- 사이트 메뉴 -->
			<input name="md_same_ct" id="md_same_ct" type="hidden" value="0"><!-- 유사개수 -->
			<input name="l_alpha" id="l_alpha" type="hidden" value="<%="KOR"%>"><!--언어코드 -->
			<input name="param_id_title" id="param_id_title" type="hidden" value="">
			<input name="param_id_content" id="param_id_content" type="hidden" value="">
			<input name="param_id_url" id="param_id_url" type="hidden" value="">
			<input name="param_md_site_name" id="param_md_site_name" type="hidden" value=""> 
			<input name="param_id_regdate" id="param_id_regdate" type="hidden" value="">
			<input name="param_md_type" id="param_md_type" type="hidden" value="">
			
		
		<%} %>
	</form>
	<div id="pop_head">
	<%if(mode.equals("multi")){ %>
	<p>멀티 센싱</p>
	<%}else{ %>
	<p>정보 센싱</p>
	<%} %>
	
	<span><a href="javascript:close();"><img src="../../images/search/pop_tit_close.gif"></a></span>
	</div>
	<%String mainStyle = "width:100%;float: right;"; %>
	<%
					if(mode.equals("insert") || mode.equals("update") || mode.equals("new")){ 
				%>
	<div style="width:50%;float: left;position:fixed;">
			<br>
			
			<div>
				<div>
					<table style="width:90%; margin-left: 20px;" border="0" cellpadding="0" cellspacing="0">
				
					<tr>
						<td style="height:30px;"><span class="sub_tit">기본정보</span></td>
					</tr>
					<tr>
						<td>
						<table id="board_write" border="0" cellspacing="0">
							<tr>
								<th><span class="board_write_tit">제목</span></th>
								<td colspan="3"><input style="width:100%;" type="text" class="textbox2" name="id_title" value="<%if(mode.equals("insert")){out.print(su.ChangeString(metaBean.getMd_title()));}else if(mode.equals("new")){out.print("");} else{out.print(su.ChangeString(idBean.getId_title()));}%>"></td>
							</tr>
							
							<tr>
								<th><span class="board_write_tit">URL</span></th>
								<td colspan="3"><input style="width:100%;" type="text" class="textbox2" name="id_url" value="<%if(mode.equals("insert")){out.print(metaBean.getMd_url());}else if(mode.equals("new")){out.print("");} else{out.print(idBean.getId_url());}%>"></td>
							</tr>
							
							 <tr>
								<th><span class="board_write_tit">출처</span></th>
								<td>
									<div style="width:140px;" class="textbox2" name="id_sitegroup"><%if(mode.equals("insert")){if(metaBean.getSg_name() !=null){ out.print(metaBean.getSg_name());}}else if(mode.equals("new")){out.print("자동등록");} else{if(idBean.getSg_name() != null){out.print(idBean.getSg_name());}}%></div>
								</td>
								<th><span class="board_write_tit">사이트이름</span></th>
								<td><input style="width:150px;" type="text" class="textbox2" name="md_site_name" value="<%if(mode.equals("insert")){out.print(metaBean.getMd_site_name());}else if(mode.equals("new")){out.print("");} else{out.print(idBean.getMd_site_name());}%>"></td>						
							</tr>
							
							 <tr>
								<th><span class="board_write_tit">수집시간</span></th>
								<td>
									<input style="width:140px;" type="text" class="textbox2" value="<%if(mode.equals("insert")){out.print(metaBean.getFormatMd_date("yyyy-MM-dd HH:mm:ss"));}else if(mode.equals("new")){out.print(du.getCurrentDate("yyyy-MM-dd HH:mm:ss"));} else{out.print(idBean.getFormatMd_date("yyyy-MM-dd HH:mm:ss"));}%>" readonly="readonly">
								</td>
								<th><span class="board_write_tit">이슈등록시간</span></th>
								<td>
									<input style="width:150px;" type="text" class="textbox2" name="id_regdate" value="<%if(mode.equals("insert")){out.print(du.getCurrentDate("yyyy-MM-dd HH:mm:ss"));}else if(mode.equals("new")){out.print(du.getCurrentDate("yyyy-MM-dd HH:mm:ss"));} else{out.print(idBean.getFormatId_regdate("yyyy-MM-dd HH:mm:ss"));}%>" readonly="readonly">
								</td>
							</tr>							
							
						<%-- 	<tr>
								<th><span class="board_write_tit">주요내용</span></th>
								<td colspan="3"><textarea style="width:100%;height:180px;" name="id_content"><%if(mode.equals("insert")){if(metaBean.getMd_content() !=null){ out.print(su.ChangeString(metaBean.getMd_content()));}}else{if(idBean.getId_content() != null){out.print(su.ChangeString(idBean.getId_content()));}}%></textarea></td>
							</tr> --%>
												
						</table>
						</td>
					</tr>
					</table>
				
				</div>
			</div>
			<br>
			<div style="width:85%; float: left;">
					<!-- <div style="font-size: x-large; text-align:center; "> 본문 내용</div> -->
					<span class="sub_tit" style="margin-left: 20px;">주요내용</span>
			</div>
			<div style="padding: 30px 20px 30px 20px;">
			<%
				String html = "";
				if(mode.equals("insert")){
					html = metaBean.getMd_content();
				}else{
					html = idBean.getId_content();
				}
				
			%>
				<textarea id="originalView" cols="86" rows="24" style="font-size:14px; border: 1px solid; padding:5px;;" name="id_content" ><%if(mode.equals("insert")){if(html !=null){if(metaBean.getK_value()!=null){out.print(su.cutKey(su.ChangeString(html),metaBean.getK_value(),300).trim());}else{out.print(su.cutKey(su.ChangeString(html),"",300).trim());}}}else if(mode.equals("new")){out.print("");}else{if(idBean.getId_content() != null){out.print(su.ChangeString(html));}}%></textarea> 
				<%-- <div contenteditable="true" id="originalView" style="font-size:14px; line-height:120% ; padding: 30px 20px 30px 20px; border: 1px solid; overflow-y:scroll; Height :400px;" name="id_content" >
					<%
					if(mode.equals("insert")){if(metaBean.getMd_content() !=null){if(metaBean.getK_value()!=null){out.print(su.cutKey(su.ChangeString(metaBean.getMd_content()),metaBean.getK_value(),300,"").trim());}else{out.print(su.cutKey(su.ChangeString(metaBean.getMd_content()),"",300,"").trim());}}}else{if(idBean.getId_content() != null){out.print(su.ChangeString(idBean.getId_content()));}}
					%>
				</div> --%>				
			</div>
	</div>
			<%
					}
				%>
				
<%
if(mode.equals("insert") || mode.equals("update") || mode.equals("new")){ 	
	mainStyle = "width:50%;float: right;";
}else{
	mainStyle = "width:100%";
}
%>
<!-- 종료  -->
<div id="flaging" style="<%=mainStyle%>">
	<table style="width:100%;" border="0" cellpadding="0" cellspacing="10">
	<!-- <tr>
		<td id="pop_head">
			<p>이슈등록</p>
			<span><a href="javascript:close();"><img src="../../images/search/pop_tit_close.gif"></a></span>
		</td>
	</tr> -->
	<tr>
		<td>
		<!-- 게시판 시작 -->
		<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
	<%-- 	<%
			if(mode.equals("insert") || mode.equals("update")){ 
		%>
			<tr>
				<td style="height:30px;"><span class="sub_tit">기본정보</span></td>
			</tr>
			<tr>
				<td>
				<table id="board_write" border="0" cellspacing="0">
					<tr>
						<th><span class="board_write_tit">제목</span></th>
						<td colspan="3"><input style="width:100%;" type="text" class="textbox2" name="id_title" value="<%if(mode.equals("insert")){out.print(su.ChangeString(metaBean.getMd_title()));}else{out.print(su.ChangeString(idBean.getId_title()));}%>"></td>
					</tr>
					
					<tr>
						<th><span class="board_write_tit">URL</span></th>
						<td colspan="3"><input style="width:94%;" type="text" class="textbox2" name="id_url" value="<%if(mode.equals("insert")){out.print(metaBean.getMd_url());}else{out.print(idBean.getId_url());}%>"></td>
					</tr>
					
					 <tr>
						<th><span class="board_write_tit">출처</span></th>
						<td><div style="width:140px;" class="textbox2" name="id_sitegroup"><%if(mode.equals("insert")){if(metaBean.getSg_name() !=null){ out.print(metaBean.getSg_name());}}else{if(idBean.getSg_name() != null){out.print(idBean.getSg_name());}}%></div></td>
						<th><span class="board_write_tit">사이트이름</span></th>
						<td><input style="width:150px;" type="text" class="textbox2" name="md_site_name" value="<%if(mode.equals("insert")){out.print(metaBean.getMd_site_name());}else{out.print(idBean.getMd_site_name());}%>"></td>						
					</tr>
					
					<tr>
						
						<th><span class="board_write_tit">정보분류시간</span></th>
						<td colspan="3"><input style="width:200px;" type="text" class="textbox2" name="id_regdate" value="<%if(mode.equals("insert")){out.print(du.getCurrentDate("yyyy-MM-dd HH:mm:ss"));}else{out.print(idBean.getFormatId_regdate("yyyy-MM-dd HH:mm:ss"));}%>" readonly="readonly"></td>
					</tr>
					
					<tr>
						<th><span class="board_write_tit">주요내용</span></th>
						<td colspan="3"><textarea style="width:100%;height:180px;" name="id_content"><%if(mode.equals("insert")){if(metaBean.getMd_content() !=null){ out.print(su.ChangeString(metaBean.getMd_content()));}}else{if(idBean.getId_content() != null){out.print(su.ChangeString(idBean.getId_content()));}}%></textarea></td>
					</tr>
										
				</table>
				</td>
			</tr>
		<%
			}
		%> --%>
		<tr>
			<td style="padding-top:15px;height:45px;"><span class="sub_tit">정보분류 항목</span></td>
		</tr>
		<tr>
			<td>
				<table id="board_write" border="0" cellspacing="0">
				<%
					
					arrIcBean = null;
					IssueCodeBean icBean = new IssueCodeBean();
					ArrayList<String[]> tmp_sub_type_list = null;
					String[] parentTypeArr = parentTypes.split(",");
					//String[] hycard_reqPTypeArr = hycard_requisiteParentTypes.split(",");
					//String[] hycap_reqPTypeArr = hycap_requisiteParentTypes.split(",");
					//String[] hycom_reqPTypeArr = hycom_requisiteParentTypes.split(",");
					String checked = "";
					String block = "none";
					String codes = "";
					String detailCodes = "";
					String ptype = "";
					String pcode = "";
					String requisiteSign = "";
					String typeStyle ="";
					String hycardStyle = "";
					String hycaptalStyle = "";
					String hycomStyle = "";
					String autoCheckCode  ="";
					for(String type : parentTypeArr){
						arrIcBean = icm.GetType(Integer.parseInt(type));
						checked = "";
						block = "none";
						codes = "";
						detailCodes = "";
						ptype = "";
						pcode = "";
						requisiteSign = "";
						typeStyle = "font-weight:normal;";
						hycardStyle = "font-weight:normal;color:#cc0000;";
						hycaptalStyle = "font-weight:normal;color:#0039e6";
						hycomStyle = "font-weight:normal;color:#e67300";
						
						if(arrIcBean != null){
							icBean = (IssueCodeBean) arrIcBean.get(0);	
						}
						if("update".equals(mode)){
							codes = (String)issueCodeMap.get(type);
							//detailCodes = (String)issueCodeDetailMap.get(type);
						}else if("insert".equals(mode) && autoCheckMap.size()>0 ){
							//자동맵핑 code			
							autoCheckCode = autoCheckMap.get(String.valueOf(type));
						}
/* 						
						//필수항목표시 - 현카드
						for(int r=0; r<hycard_reqPTypeArr.length; r++){
							if(hycard_reqPTypeArr[r].equals(type)){
								requisiteSign = " *";
								typeStyle = "font-weight:bold;";
							}
						}							
						//필수항목표시 - 현캐피
						for(int r=0; r<hycap_reqPTypeArr.length; r++){
							if(hycap_reqPTypeArr[r].equals(type)){
								requisiteSign = " *";
								typeStyle = "font-weight:bold;";
							}
						}							
						//필수항목표시 - 현커머
						for(int r=0; r<hycom_reqPTypeArr.length; r++){
							if(hycom_reqPTypeArr[r].equals(type)){
								requisiteSign = " *";
								typeStyle = "font-weight:bold;";
							}
						}				
 */						
						
				%>
					<% if (type.equals("3") || type.equals("4") || type.equals("5") || type.equals("6")){ %>
					<tr id="hotel" style='display:none;' name="hotel" value="" >
					<th><span class="board_write_tit" style=<%=hycaptalStyle%> id="typeTitle_<%=type%>"><%=icBean.getIc_name()%><%=requisiteSign%></span></th>
					<% } else if (type.equals("7") || type.equals("8")){ %>
					<tr id="TR" style='display:none;' name="TR" value="" >
					<th><span class="board_write_tit" style=<%=hycaptalStyle%> id="typeTitle_<%=type%>"><%=icBean.getIc_name()%><%=requisiteSign%></span></th>
					<% } else if (type.equals("9") || type.equals("10")){ %>
					<tr id="SHP" style='display:none;' name="SHP" value="" >
					<th><span class="board_write_tit" style=<%=hycaptalStyle%> id="typeTitle_<%=type%>"><%=icBean.getIc_name()%><%=requisiteSign%></span></th>
					<% } else if (type.equals("11")){ %>
					<tr id="SBTM" style='display:none;' name="SBTM" value="" >
					<th><span class="board_write_tit" style=<%=hycaptalStyle%> id="typeTitle_<%=type%>"><%=icBean.getIc_name()%><%=requisiteSign%></span></th>
					<% } else if (type.equals("12")){ %>
					<tr id="CEO" style='display:none;' name="CEO" value="" >
					<th><span class="board_write_tit" style=<%=hycaptalStyle%> id="typeTitle_<%=type%>"><%=icBean.getIc_name()%><%=requisiteSign%></span></th>
					<% } else if (type.equals("13")){ %>
					<tr id="IR" style='display:none;' name="IR" value="" >
					<th><span class="board_write_tit" style=<%=hycaptalStyle%> id="typeTitle_<%=type%>"><%=icBean.getIc_name()%><%=requisiteSign%></span></th>
					<% } else if (type.equals("14")){ %>
					<tr id="ESG" style='display:none;' name="ESG" value="" >
					<th><span class="board_write_tit" style=<%=hycaptalStyle%> id="typeTitle_<%=type%>"><%=icBean.getIc_name()%><%=requisiteSign%></span></th>
					<% } else { %>
					<tr>
					<th><span class="board_write_tit" style="font-weight:bold;" id="typeTitle_<%=type%>"><%=icBean.getIc_name()%><%=requisiteSign%></span></th>
					<% } %>
					<td colspan="3">
					<input type="hidden" id="focus_<%=type%>_1" value="">
					<%
						for (int i = 1; i < arrIcBean.size(); i++) {
							
							IssueCodeBean subIcBean = (IssueCodeBean) arrIcBean.get(i);
							checked = "";
							
							if("update".equals(mode)){
								if(codes != null && !"".equals(codes)){
									String[] codeArr = codes.split(",");
									for(String codeElem : codeArr){
										if(codeElem.equals(String.valueOf(subIcBean.getIc_code()))){
											checked = "checked";
											ptype = String.valueOf(subIcBean.getIc_type());
											pcode = String.valueOf(subIcBean.getIc_code());
											break;
										}
									}
								}
							}else if("insert".equals(mode) 
									&& null!= autoCheckCode && !"".equals(autoCheckCode) 
									&& autoCheckCode.equals(String.valueOf(subIcBean.getIc_code()))
								){
								ptype = String.valueOf(subIcBean.getIc_type());
								//ptype = String.valueOf(type);
								pcode = autoCheckCode;
								checked = "checked";
							}
							
						if (type.equals("1")){	
					%>
						<input type="radio" id="typeCode<%=subIcBean.getIc_type()%>_<%=subIcBean.getIc_code() %>" name="typeCodeCheckbox_<%=subIcBean.getIc_type()%>"  value="<%=subIcBean.getIc_type()%>,<%=subIcBean.getIc_code() %>"  onclick="HyDisplay(<%=subIcBean.getIc_type()%>,<%=subIcBean.getIc_code()%>,'1',<%=type%>);" <%=checked %> />
						<% } else if(!type.equals("1")){ %>
						<input type="radio" id="typeCode<%=subIcBean.getIc_type()%>_<%=subIcBean.getIc_code() %>" name="typeCodeCheckbox_<%=subIcBean.getIc_type()%>"  value="<%=subIcBean.getIc_type()%>,<%=subIcBean.getIc_code() %>"  onclick="getSubDepth(<%=subIcBean.getIc_type()%>,<%=subIcBean.getIc_code() %>,'1',<%=type%>);" <%=checked %> />
						<% } %>
						<label for="typeCode<%=subIcBean.getIc_type()%>_<%=subIcBean.getIc_code() %>" style="cursor:pointer"><%=subIcBean.getIc_name() %></label>
						<%  
							if( i != 1 && i%5==0 ) {
								out.print("<br>");
							}
						}
				%>
				</td>
				</tr>
				<%
				String colsapn="3";					
				block = "none";
				%>
					
					<!-- 하위 -->
					<%						
					  	tmp_sub_type_list = subDepthList.get(type);
						
						if(null!=tmp_sub_type_list && tmp_sub_type_list.size()>0){
							for(int t=0; t<tmp_sub_type_list.size();t++){
								block = "none";
								if("update".equals(mode)){
									detailCodes = (String)issueCodeMap.get(tmp_sub_type_list.get(t)[2]);
									if(null!=(detailCodes) && !"".equals(ptype)&& !"".equals(pcode)){										
										subArrIcBean = icm.getSearchIssueCode(ptype, pcode);
										if(null != subArrIcBean && subArrIcBean.size()>0){
											block = "";	
											typeStyle = "font-weight:normal;";
										}
													
									}
								}else if("insert".equals(mode) && ( null != autoCheckMap && autoCheckMap.size()>0 )){
									if(!"".equals(ptype)&& !"".equals(pcode) 
										&& null!=autoCheckMap.get(String.valueOf(tmp_sub_type_list.get(t)[2]))
										&& !"".equals(autoCheckMap.get(String.valueOf(tmp_sub_type_list.get(t)[2]))) 
									){
										autoCheckCode = autoCheckMap.get(String.valueOf(tmp_sub_type_list.get(t)[2]));
										subArrIcBean = icm.getSearchIssueCode(ptype, pcode);
										if(null != subArrIcBean && subArrIcBean.size()>0){
											block = "";	
											typeStyle = "font-weight:normal;";
										}
									}
								}
										
					%>
							    <tr id="tr_typeCode<%=type%>_subDepth_<%=t+2%>" style='display:<%=block%>;'  data-depth="<%=t+2%>" name="tr_typeCode_<%=type%>" value="<%=type%>,<%=t+2%>">
								<th style="background-color: #F8F8F8;"><span style="padding-left:10px; <%=typeStyle%>"><img alt="하위분류" src="../../images/issue/icon_bot_02.gif"><%=tmp_sub_type_list.get(t)[1] %></span></th>
								<td id="td_typeCode<%=type%>_subDepth_<%=t+2%>" colspan="<%=colsapn%>" >
								<input type="hidden" id="focus_<%=type%>_<%=t+2%>" value=""> <!-- 라디오 박스 해제용 -->
								
								<%
								if( 
									("update".equals(mode) && ( null != subArrIcBean && subArrIcBean.size()>0 ))
									|| 	("insert".equals(mode) && ( null != subArrIcBean && subArrIcBean.size()>0 )&& ( null != autoCheckMap && autoCheckMap.size()>0 ))
								 ){											
									for (int i = 0; i < subArrIcBean.size(); i++) {	
										IssueCodeBean subIcBean = (IssueCodeBean) subArrIcBean.get(i);
										checked = "";
										if("update".equals(mode)){
											if(String.valueOf(subIcBean.getIc_code()).equals(detailCodes)){											
												String[] codeArr = detailCodes.split(",");
												
													for(String codeElem : codeArr){
														if(codeElem.equals(String.valueOf(subIcBean.getIc_code()))){
															checked = "checked";
															ptype=String.valueOf(subIcBean.getIc_type());
															pcode=String.valueOf(subIcBean.getIc_code());
															break;
														}
													}
											}
										}else if ("insert".equals(mode) && null != autoCheckCode && !"".equals(autoCheckCode)){
											if(autoCheckCode.equals(String.valueOf(subIcBean.getIc_code()))){
												checked = "checked";
												ptype = String.valueOf(subIcBean.getIc_type());
												pcode = String.valueOf(subIcBean.getIc_code());		
											}
										}
										
								%>
									<input type='radio' id="input_info<%=subIcBean.getIc_type()%>_<%=subIcBean.getIc_code()%>" name="input_infoAttr_<%=subIcBean.getIc_type()%>" value="<%=subIcBean.getIc_type()%>,<%=subIcBean.getIc_code()%>"  data-group="<%=type%>" data-depth="<%=t+2%>" onclick="getSubDepth(<%=subIcBean.getIc_type()%>,<%=subIcBean.getIc_code()%>,<%=t+2%>,<%=type%>);" <%=checked %>/>
									<label for="input_info<%=subIcBean.getIc_type()%>_<%=subIcBean.getIc_code()%>" style="cursor:pointer;"><%=subIcBean.getIc_name() %></label>
									<%
										if((i+1)%5==0 && i!=0) {
											out.print("<br>");
										}
									}
								}
								 %> 
								</td>
							</tr>
				<%						
						 }
				  }
				}
				%>				
					<tr>
						<th><span class="board_write_tit">영향력</span></th>
						<td colspan="3">
							<input type="radio" id="clout_power" name="typeCodeCheckbox_clout" value="1" onclick="chk_clout(this.value)" <%if( "update".equals(mode) && "1".equals(idBean.getId_clout())){out.println("checked = checked");}%>>
							<label for="clout_power" style="cursor:pointer">파워</label>
							
							<input type="radio" id="clout_normal" name="typeCodeCheckbox_clout" value="2" onclick="chk_clout(this.value)" <%if( "update".equals(mode) && "2".equals(idBean.getId_clout())){out.println("checked = checked");}%>>
							<label for="clout_normal" style="cursor:pointer">일반</label>
							
							<!-- 라디오 박스 해제용 -->
							<input type="hidden" id="focus_clout" value="">
						</td>	
					</tr>
					
					<tr>
						<th><span class="board_write_tit">성향</span></th>
						<td colspan="3">
							<input type="radio" id="senti_pos" name="typeCodeCheckbox_senti" value="1" onclick="chk_senti(this.value)" <%if( "update".equals(mode) && "1".equals(idBean.getId_senti())){out.println("checked = checked");}%>>
							<label for="senti_pos" style="cursor:pointer">긍정</label>
							
							<input type="radio" id="senti_neg" name="typeCodeCheckbox_senti" value="2" onclick="chk_senti(this.value)" <%if( "update".equals(mode) && "2".equals(idBean.getId_senti())){out.println("checked = checked");}%>>
							<label for="senti_neg" style="cursor:pointer">부정</label>

							<input type="radio" id="senti_neu" name="typeCodeCheckbox_senti" value="3" onclick="chk_senti(this.value)" <%if( "update".equals(mode) && "3".equals(idBean.getId_senti())){out.println("checked = checked");}%>>
							<label for="senti_neu" style="cursor:pointer">중립</label>

							<!-- 라디오 박스 해제용 -->
							<input type="hidden" id="focus_senti" value="">
						</td>	
					</tr>
					
					<tr>
						<th><span class="board_write_tit">보고서</span></th>
						<td colspan="3">
							<input type="radio" id="trans_yes" name="typeCodeCheckbox_trans" value="Y" onclick="chk_trans(this.value)" <%if( "update".equals(mode) && "Y".equals(idBean.getId_reportyn())){out.println("checked = checked");}%>/>
							<label for="trans_yes" style="cursor:pointer">포함</label>
							
							<input type="radio" id="trans_no" name="typeCodeCheckbox_trans" value="N" onclick="chk_trans(this.value)" <%if( "update".equals(mode) && "N".equals(idBean.getId_reportyn()) || "insert".equals(mode) || "multi".equals(mode)|| "new".equals(mode)){out.println("checked = checked");}%>/>
							<label for="trans_no" style="cursor:pointer">미포함</label>
							
							<!-- 라디오 박스 해제용 -->
							<input type="hidden" id="focus_trans" value="">
						</td>	
					</tr>
										
					<tr>
						<th><span class="board_write_tit" style="font-weight:bold;">연관키워드</span></th>
						<td>
							<table width="500" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="500" style="border-bottom:0px #CCCCCC dotted;" id="select_keyword"></td>
								</tr>	
								<tr>
									<td colspan="3" id="remakeRelationwordText">
										<input type="text" class="input_keyword" id="txt_relationkey" name="txt_relationkey" onkeypress="javascript:if(event.keyCode == 13){ addKeyword();}" placeholder="연관키워드 입력" value="">
										<img src="../../images/issue/plus_btn.gif" onclick="addKeyword();" style="vertical-align: bottom">
										<%
										String[] relKeyArr = null;
									 	if("update".equals(mode) && null!=relationkeywordR_str && !"".equals(relationkeywordR_str)) {
									 		relKeyArr = relationkeywordR_str.split("@");
									 		String[] code_word = new String[2];  									 		
									 		for(int i=0; i < relKeyArr.length; i++){
									 			code_word = relKeyArr[i].split(":");  
										%>
											<span id='keyword_<%=code_word[1]%>_<%=i%>' class='keyNm'><%=code_word[0]%>&nbsp;<img src="../../images/issue/delete_btn_01.gif" style="vertical-align: middle; cursor:pointer;" onclick="delKeyword('<%=i%>','<%=code_word[1]%>')"></span>
										<%			
										 		}
										 	}
										%>
									</td>	
								</tr>
							</table>
						</td>	
					</tr>		
				</table>
			</td>
		</tr>	
		</table>
		</td>
	</tr>
	<tr>
		<td style="text-align:center;"><font id="fnMsg" style="color: #5364D2; font-weight: bold; font-size: 13px;" ></font></td>
	</tr>
	<tr>
		<td style="text-align:center;"><img src="../../images/search/btn_save_2.gif" onclick="save_issue('<%=mode%>');" style="cursor:pointer;"/>&nbsp;<img src="../../images/search/btn_cancel.gif" onclick="window.close();" style="cursor:pointer;"/></td>
	</tr>
	</table>
</div>
</body>
</html>