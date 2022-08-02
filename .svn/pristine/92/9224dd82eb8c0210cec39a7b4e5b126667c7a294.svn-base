<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Map"%>
<%@ page import="risk.util.ParseRequest"%>
<%@ page import="risk.util.DateUtil"%>
<%@ page import="risk.util.StringUtil"%>
<%@ page import="risk.search.MetaBean"%>
<%@ page import="risk.search.MetaMgr"%>
<%@ page import="risk.issue.IssueCodeMgr"%>
<%@ page import="risk.issue.IssueDataBean"%>
<%@ page import="risk.issue.IssueMgr"%>
<%@ page import="risk.issue.IssueCodeBean"%>
<%@include file="../inc/sessioncheck.jsp"%>
<%
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	
	DateUtil	 du = new DateUtil();
	StringUtil	 su = new StringUtil();
	MetaMgr  metaMgr = new MetaMgr();
	IssueCodeMgr icm = new IssueCodeMgr();
	IssueMgr issueMgr = new IssueMgr();
	
	// Parameter
	String mode = pr.getString("mode");
	String mode2 = pr.getString("mode2");
	String subMode = pr.getString("subMode","");
	String md_seq = pr.getString("md_seq");
	String md_seqs = pr.getString("md_seqs");
	String id_seqs = pr.getString("id_seqs");
	String child = pr.getString("child", "");
	
	// Bean
	IssueDataBean idBean = new IssueDataBean();
	MetaBean metaBean = new MetaBean();
	int ic_seq = 0;
	ArrayList arrIcBean = null;
	//분류체계코드 (0:분류항목 포함, 1:분류항목 제외)
	icm.init(0);		
	Map issueCodeMap = null;
	Map issueCodeDetailMap = null;
	Map relationKeywordR_Map = null;
	
	//자동롤링용 연관키워드 한줄로 만들기
	ArrayList relationKeyAll = issueMgr.getRelationKey();
	
	StringBuffer relKey_buff = null;
	String streamKey = "";
   	if(relationKeyAll.size() > 0){
   		for(int i =0; i < relationKeyAll.size(); i++){
	   		
	   		if(relKey_buff == null){
	   			relKey_buff = new StringBuffer((String)relationKeyAll.get(i));
	   		}else{
	   			relKey_buff.append(",").append((String)relationKeyAll.get(i));
	   		}
	   	}
   		
   		if(relKey_buff != null){
   			streamKey = relKey_buff.toString();
   		}
   	}
   	
   	// 보도자료는 별도 표시
   	ArrayList pressDataList = icm.getIssueCode("12", "IC_REGDATE DESC, IC_NAME ASC");
	
	if(mode.equals("insert")){
		//메타 정보
		if(subMode.equals("solr")){
			metaBean.setMd_seq(pr.getString("md_seq"));
			metaBean.setS_seq(pr.getString("s_seq"));
			metaBean.setSg_seq("");
			metaBean.setMd_site_name(pr.getString("md_site_name"));
			metaBean.setMd_site_menu("SOLR");
			metaBean.setMd_date(du.getDate(pr.getString("md_date"), "yyyy-MM-dd HH:mm:ss"));
			metaBean.setMd_title(su.dbString(pr.getString("md_title")));
			metaBean.setMd_url(su.dbString(pr.getString("md_url")));
			metaBean.setL_alpha("KOR");
		} else if(subMode.equals("TOP")){
			metaBean = metaMgr.getTopData(md_seq);	
			ic_seq = icm.getSiteMapCode(metaBean.getSg_seq());  //사이트 이슈 코드 맵핑 정보 코드 시퀀스로 가져옴
		} else{
			metaBean = metaMgr.getMetaData(md_seq, mode2);	
			ic_seq = icm.getSiteMapCode(metaBean.getSg_seq());  //사이트 이슈 코드 맵핑 정보 코드 시퀀스로 가져옴
		}
	} else if(mode.equals("update")){
		//이슈 정보
		idBean = issueMgr.getIssueDataBean_V2(md_seq);
		issueCodeMap = issueMgr.getIssueDataCodeMap(idBean.getId_seq());
		issueCodeDetailMap = issueMgr.getIssueDataCodeDetailMap(idBean.getId_seq());
		relationKeywordR_Map = issueMgr.getRelationKeywordR_Map(idBean.getId_seq());
	}
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
	<form name="fSend" id="fSend" action="issue_data_prc.jsp" method="post" onsubmit="return false;">
		<input name="mode" id="mode" type="hidden" value="<%=mode%>"><!-- 모드 -->
		<input type="hidden" name="mode2" value="<%=mode2 %>">
		<input name="typeCodes" id="typeCodes" type="hidden" value="">
		<input name="typeCodesDetail" id="typeCodesDetail" type="hidden" value="">
		<input name="typeCodesInfo" id="typeCodesInfo" type="hidden" value="">
		<input name="relationkeyNames" id="relationkeyNames" type="hidden" value="">
		<input name="param_ic_type" id="param_ic_type" type="hidden" value="">
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
		<%} else if(mode.equals("update_multi")){ %>
			<input type="hidden" name="id_seqs" value="<%=id_seqs %>">
			<input name="child" id="child" type="hidden" value="<%=child%>">
		<%} else if(mode.equals("update")){ %>
			<input name="id_seq" id="id_seq" type="hidden" value="<%=idBean.getId_seq()%>"><!-- 기사번호 -->
			<input name="md_seq" id="md_seq" type="hidden" value="<%=idBean.getMd_seq()%>"><!-- 기사번호 -->
			<input name="md_pseq" id="md_pseq" type="hidden" value="<%=idBean.getMd_pseq()%>"><!-- 모 기사번호 -->
			<input name="s_seq" id="s_seq" type="hidden" value="<%=idBean.getS_seq()%>"><!-- 사이트번호 -->
			<input name="sg_seq" id="sg_seq" type="hidden" value="<%=idBean.getSg_seq()%>"><!-- 사이트 그룹 -->
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
		<%}%>
	</form>
	
	<table style="width:100%;" border="0" cellpadding="0" cellspacing="10">
	<tr>
		<td id="pop_head">
			<p>이슈등록</p>
			<span><a href="javascript:close();"><img src="../../images/search/pop_tit_close.gif"></a></span>
		</td>
	</tr>
	<tr>
		<td>
		<!-- 게시판 시작 -->
		<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
		<%
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
						<th><span class="board_write_tit">사이트이름</span></th>
						<td><input style="width:150px;" type="text" class="textbox2" name="md_site_name" value="<%if(mode.equals("insert")){out.print(metaBean.getMd_site_name());}else{out.print(idBean.getMd_site_name());}%>"></td>
						<th><span class="board_write_tit">정보분류시간</span></th>
						<td><input style="width:140px;" type="text" class="textbox2" name="id_regdate" value="<%if(mode.equals("insert")){out.print(du.getCurrentDate("yyyy-MM-dd HH:mm:ss"));}else{out.print(idBean.getFormatId_regdate("yyyy-MM-dd HH:mm:ss"));}%>" readonly="readonly"></td>
					</tr>
					<%--
					<tr style="display: none;">
						<th><span class="board_write_tit">정보종류</span></th>
						<td colspan="3">
							<select name="md_type" id="md_type">
								<option value="1" <%if(mode.equals("insert")){if(metaBean.getMd_type().equals("1")){out.print("selected");}}else{if(idBean.getMd_type().equals("1")){out.print("selected");}}%>>언론</option>
								<option value="2" <%if(mode.equals("insert")){if(Integer.parseInt(metaBean.getMd_type()) > 1){out.print("selected");}}else{if(idBean.getMd_type().equals("2")){out.print("selected");}}%>>개인</option>
							</select>
						</td>
					</tr> --%>
					<tr>
						<th><span class="board_write_tit">주요내용</span></th>
						<td colspan="3"><textarea style="width:100%;height:180px;" name="id_content"><%if(mode.equals("insert")){if(metaBean.getMd_content() !=null){ out.print(su.ChangeString(metaBean.getMd_content()));}}else{if(idBean.getId_content() != null){out.print(su.ChangeString(idBean.getId_content()));}}%></textarea></td>
					</tr>
				</table>
				</td>
			</tr>
		<%
			}
		%>
		<tr>
			<td style="padding-top:15px;height:45px;"><span class="sub_tit">정보분류 항목</span></td>
		</tr>
		<tr>
			<td>
				<table id="board_write" border="0" cellspacing="0">
				<%
					String[] checkboxCodeArr = {"2", "3", "4", "5", "7", "8"};
					arrIcBean = null;
					IssueCodeBean icBean = new IssueCodeBean();
					
					for(String type : checkboxCodeArr){
						arrIcBean = icm.GetType(Integer.parseInt(type));
						String checked = "";
						String block = "none";
						String codes = "";
						String detailCodes = "";
						boolean typeCheck = false;
						
						if(arrIcBean != null){
							icBean = (IssueCodeBean) arrIcBean.get(0);	
						}
						if("update".equals(mode)){
							codes = (String)issueCodeMap.get(type);
							detailCodes = (String)issueCodeDetailMap.get(type);
						}
						
				%>
					<tr>
					<th><span class="board_write_tit"><%=icBean.getIc_name() %></span></th>
					<td colspan="3">
					<%
						for (int i = 1; i < arrIcBean.size(); i++) {
							
							IssueCodeBean subIcBean = (IssueCodeBean) arrIcBean.get(i);
							checked = "";
							
							if("update".equals(mode)){
								if(codes != null && !"".equals(codes)){
									typeCheck = true;
									String[] codeArr = codes.split(",");
									for(String codeElem : codeArr){
										if(codeElem.equals(String.valueOf(subIcBean.getIc_code()))){
											checked = "checked";
											break;
										}
									}
								}
							}
					%>
						<input type="checkbox" id="typeCode<%=subIcBean.getIc_type()%>_<%=subIcBean.getIc_code() %>" name="typeCodeCheckbox" value="<%=subIcBean.getIc_type()%>,<%=subIcBean.getIc_code() %>" onclick="getTrend('<%=type%>');getInfoAttr('<%=type %>')" <%=checked %> />
						<label for="typeCode<%=subIcBean.getIc_type()%>_<%=subIcBean.getIc_code() %>" style="cursor:pointer"><%=subIcBean.getIc_name() %></label> 
					<%
							if( i != 0 && i==4 ) {
								out.print("<br>");
							}
						}
					%>
					</td>
					</tr>
					<%
					String colsapn="3";
					if("8".equals(type)){
						colsapn = "2";
					}
					
					block = "none";
					String sentiCode = "";
					String pastSentiCode = "";
					String relationKeywordCode = "";
					
					if("update".equals(mode)){
						if(codes != null){
							String[] codeArr = detailCodes.split("@");
							for(String codeSplit : codeArr){
								String[] codeArr2 = codeSplit.split(",");
								if(codeArr2[0].equals("9")){
									block = "";
									sentiCode = codeArr2[1];
								} else if(codeArr2[0].equals("20")){
									pastSentiCode = codeArr2[1];
								}
							}
						}
					}
					
					%>
					<!-- 성향 -->
					<tr id="tr_typeCode<%=type%>_trend" style="display:<%=block%>;">
						<th style="background-color: #F8F8F8;"><span style="padding-left:10px;"><img alt="하위분류" src="../../images/issue/icon_bot_02.gif">성향*</span></th>
						<td class="content" width="185px" colspan="<%=colsapn%>">
							<div class='radioChk' style='margin-left:5px;display:block;'>
								<%
									arrIcBean = icm.GetType(9);
									for(int i=1; i<arrIcBean.size(); i++){
										icBean = (IssueCodeBean) arrIcBean.get(i);
										checked = "";
										String past_checked = "";
										if("update".equals(mode)){
											if(sentiCode.equals(String.valueOf(icBean.getIc_code()))){
												checked = "checked";	
											}
											// 과거 성향
											if(pastSentiCode.equals(String.valueOf(icBean.getIc_code()))){
												past_checked = "checked";
											}
										}
								%>
								<div class="dcp">
									<input id="input_trend<%=type%>_<%=icBean.getIc_code()%>" name="input_trend_<%=type%>" type="radio" class="ui_hidden" value="<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>" <%=checked%> /><label for="input_trend<%=type%>_<%=icBean.getIc_code()%>">
									<span class="radio_0<%=i-1 %>"></span></label>
									<input id="input_pastTrend<%=type %>_<%=icBean.getIc_code()%>" name="input_pastTrend<%=type%>" type="checkbox" class="ui_hidden" disabled <%=past_checked %>><label for="input_pastTrend<%=type %>_<%=icBean.getIc_code()%>"></label>
								</div>
								&nbsp;
								<%
									}
								%>
							</div>
						</td>
						<!-- 연관키워드 -->
						<td id="relKeyList_<%=type%>" valign="top" align="left">
						<input type="text" class="input_keyword" id="txt_relationkey_<%=type %>" name="txt_relationkey" onkeypress="javascript:if(event.keyCode == 13){ addKeyword(this, '<%=type %>');}" placeholder="연관키워드 입력" value="">
						<img src="../../images/issue/plus_btn.gif" onclick="addKeyword(this, '<%=type %>');" style="vertical-align: bottom">
						<%		 	
						 	if ("update".equals(mode)) {
						 		relationKeywordCode = (String) relationKeywordR_Map.get(type);
						 		if(relationKeywordCode != null){
						 			String[] relKeyArr = relationKeywordCode.split("@");
						 			for(int i=0; i < relKeyArr.length; i++){
						 				String[] relKeySplit = relKeyArr[i].split(",");
						%>
							<span id='keyword_<%=type%>_<%=relKeySplit[0]%>' class='keyNm'><%=relKeySplit[1]%>&nbsp;<img src="../../images/issue/delete_btn_01.gif" style="vertical-align: middle; cursor:pointer;" onclick="delKeyword('<%=type%>', '<%=relKeySplit[0]%>')"></span>
						<%			
						 			}
						 		}
						 	}
						%>
					</tr>
					
					<!-- 정보속성 -->
					<%
						block = "none";
						if(!"8".equals(type)){
							if("update".equals(mode)){
								if(detailCodes != null){
									String[] codeArr = detailCodes.split("@");
									if(detailCodes.indexOf("13,") > -1 ||  typeCheck){
										block = "";
									}
								}
							}
					%>
					<tr id="tr_typeCode<%=type%>_infoAttr" style='display:<%=block%>;'>
						<th style="background-color: #F8F8F8;"><span style="padding-left:10px;"><img alt="하위분류" src="../../images/issue/icon_bot_02.gif">정보 속성*</span></th>
						<td colspan="<%=colsapn%>">
						<%
							arrIcBean = icm.GetType(13); 
							for (int i = 1; i < arrIcBean.size(); i++) {
								icBean = (IssueCodeBean) arrIcBean.get(i);
								if(("5".equals(type) || "7".equals(type)) && icBean.getIc_code() != 2){
									continue;
								}
								
								checked = "";
								if("update".equals(mode)){
									if(detailCodes != null){
										String[] codeArr = detailCodes.split("@");
										for(String codeSplit : codeArr){
											String[] codeArr2 = codeSplit.split(",");
											if(codeArr2[0].equals(String.valueOf(icBean.getIc_type())) && codeArr2[1].equals(String.valueOf(icBean.getIc_code()))){
												checked = "checked";
												break;
											}
										}
									}
								}
						%>
							<input type='checkbox' id="input_info<%=type%>_<%=icBean.getIc_code()%>" name="input_infoAttr_<%=type%>" value="<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>" <%=checked %>/>
							<label for="input_info<%=type%>_<%=icBean.getIc_code()%>" style="cursor:pointer"><%=icBean.getIc_name() %></label>
							<%
								if( i != 0 && i == 6) {
									out.print("<br>");
								}
							}
						 %>
						</td>
					</tr>
				<%					
						}
					}
				%>
				
				<!-- 지면 -->
				<%
					arrIcBean = icm.GetType(10);
					icBean = (IssueCodeBean) arrIcBean.get(0);
				%>
				<tr>                             			
				<th><span class="board_write_tit"><%=icBean.getIc_name() %></span></th>
				<td colspan="3">
				<%
					for (int i = 1; i < arrIcBean.size(); i++) {
						icBean = (IssueCodeBean) arrIcBean.get(i);
						String checked = "";
						if("update".equals(mode)){
							String etcCode = (String)issueCodeMap.get("10");
							if(etcCode != null && etcCode.equals(String.valueOf(icBean.getIc_code()))){
								checked = "checked";
							}
						}
				%>
					<input type="radio" id="typeCode<%=icBean.getIc_type()%>_<%=icBean.getIc_code() %>" name="typeCodeCheckbox" value = "<%=icBean.getIc_type()%>,<%=icBean.getIc_code() %>" <%=checked %>/>
					<label for="typeCode<%=icBean.getIc_type()%>_<%=icBean.getIc_code() %>" style="cursor:pointer"><%=icBean.getIc_name() %></label>
					<%
						if( i != 0 && i==6 ) {
							out.print("<br>"); 
						}
					}
				%>
				</td>
				</tr>
				
				<!-- 온라인 뉴스 -->	
				<%
					arrIcBean = icm.GetType(11);
					icBean = (IssueCodeBean) arrIcBean.get(0);
				%>
				<tr>
				<th><span class="board_write_tit"><%=icBean.getIc_name() %></span></th>
				<td colspan="3">
					<span>
						<select name='typeCodeSelect' style='width: 105px'>
						<option value=''>선택하세요</option>
						<%
							for (int i = 1; i < arrIcBean.size(); i++) {
								icBean = (IssueCodeBean) arrIcBean.get(i);
								String selected = "";
								if("update".equals(mode)){
									String etcCode = (String)issueCodeMap.get("11");
									if(etcCode != null && etcCode.equals(String.valueOf(icBean.getIc_code()))){
										selected = "selected";
									}
								}
						%>
						<option value='<%=icBean.getIc_type() %>,<%=icBean.getIc_code() %>' <%=selected %>><%=icBean.getIc_name() %></option>
						<%
							}
						%>
						</select>
						&nbsp;&nbsp;
						<img src='../../images/issue/plus_btn.gif' onclick='add_IC(11);' style='vertical-align: bottom;cursor:pointer;'>
					</span>
				</td>
				</tr>
				
				<!-- 보도자료 -->	
				<tr>
				<th><span class="board_write_tit">보도자료</span></th>
				<td colspan="3">
					<span>
						<select name='typeCodeSelect' style='width: 105px'>
						<option value=''>선택하세요</option>
						<%
							String ic_name_format = "";
							for (int i = 0; i < pressDataList.size(); i++) {
								icBean = (IssueCodeBean) pressDataList.get(i);
								
								String selected = "";
								if("update".equals(mode)){
									String etcCode = (String)issueCodeMap.get("12");
									if(etcCode != null && etcCode.equals(String.valueOf(icBean.getIc_code()))){
										selected = "selected";
									}
								}
								
								ic_name_format = "("+du.getDate(icBean.getIc_regdate(), "yyyy-MM-dd") + ") - " + icBean.getIc_name();
						%>
						<option value='<%=icBean.getIc_type() %>,<%=icBean.getIc_code() %>' <%=selected %>><%=ic_name_format%></option>
						<%
							}
						%>
						</select>
						&nbsp;&nbsp;
						<img src='../../images/issue/plus_btn.gif' onclick='add_IC(12);' style='vertical-align: bottom;cursor:pointer;'>
					</span>
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
</body>
</html>