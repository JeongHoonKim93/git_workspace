<%@page import="java.util.Arrays"%>
<%@page import="java.util.HashMap"%>
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
	String parentTypes = "1,3,4,5,6,7,8,9,10,11,12,13,14,2";
	String requisiteParentTypes = ""; //필수체크항목 (부서, 게시물유형)
	HashMap<String, ArrayList<String[]>> subDepthList = issueMgr.getSubdepthList(parentTypes); //parentTypes 하위 뎁스 리스트
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
	<form name="fSend" id="fSend" action="issue_data_prc.jsp" method="post" onsubmit="return false;">
		<input name="mode" id="mode" type="hidden" value="<%=mode%>"><!-- 모드 -->
		<input type="hidden" name="mode2" value="<%=mode2 %>">
		<input name="typeCodes" id="typeCodes" type="hidden" value="">
	<!-- 	<input name="typeCodesDetail" id="typeCodesDetail" type="hidden" value="">
		<input name="typeCodesInfo" id="typeCodesInfo" type="hidden" value="">-->
		<input name="relationkeyCode" id="relationkeyCode" type="hidden" value=""> 
		<input name="relationkeyNames" id="relationkeyNames" type="hidden" value=""> 
		<input name="relationkeyNames1" id="relationkeyNames1" type="hidden" value=""> 
		<input name="relationkeyNames2" id="relationkeyNames2" type="hidden" value=""> 
<!--	<input name="hycard_txtcmt" id="hycard_txtcmt" type="hidden" value="">
		<input name="hycap_txtcmt" id="hycap_txtcmt" type="hidden" value="">
		<input name="hycom_txtcmt" id="hycom_txtcmt" type="hidden" value=""> -->		
		<input name="param_ic_type" id="param_ic_type" type="hidden" value="">
		<input name="parentTypes" id="parentTypes" type="hidden" value="<%=parentTypes%>">
		<input name="requisiteParentTypes" id="requisiteParentTypes" type="hidden" value="<%=requisiteParentTypes%>">
		<input name="cloutType" id="cloutType" type="hidden" value="">
		<input name="sentiType" id="sentiType" type="hidden" value="">
		<input name="transType" id="transType" type="hidden" value="">
		<%if(mode.equals("update_multi")){ %>
			<input type="hidden" name="id_seqs" value="<%=id_seqs %>">
			<input name="child" id="child" type="hidden" value="<%=child%>">
		<%} %>
	</form>
	<div id="pop_head">
	<p>멀티 센싱</p>
	<span><a href="javascript:close();"><img src="../../images/search/pop_tit_close.gif"></a></span>
	</div>
	<%String mainStyle = "width:100%;float: right;"; %>
				
	<div id="flaging" style="width:100%">
	<table style="width:100%;" border="0" cellpadding="0" cellspacing="10">
	<tr>
		<td>
		<!-- 게시판 시작 -->
		<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
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
					String[] reqPTypeArr = requisiteParentTypes.split(",");
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
						
							
				%>
					<% if (type.equals("3") || type.equals("4") || type.equals("5") || type.equals("6")){ %>
					<tr id="hotel" style='display:none;' name="hotel" value="" >
					<th style="width: 200px;"><input type="checkbox" name= "hotel_chk" id="Type_Check_<%=type%>" class="type_check" value="<%=type%>"><label for="Type_Check_<%=type%>"></label><span class="board_write_tit" style=<%=hycaptalStyle%> id="typeTitle_<%=type%>"><%=icBean.getIc_name()%><%=requisiteSign%></span></th>
					<% } else if (type.equals("7") || type.equals("8")){ %>
					<tr id="TR" style='display:none;' name="TR" value="" >
					<th style="width: 200px;"><input type="checkbox" name= "TR_chk" id="Type_Check_<%=type%>" class="type_check" value="<%=type%>"><label for="Type_Check_<%=type%>"></label><span class="board_write_tit" style=<%=hycaptalStyle%> id="typeTitle_<%=type%>"><%=icBean.getIc_name()%><%=requisiteSign%></span></th>
					<% } else if (type.equals("9") || type.equals("10")){ %>
					<tr id="SHP" style='display:none;' name="SHP" value="" >
					<th style="width: 200px;"><input type="checkbox" name= "SHP_chk" id="Type_Check_<%=type%>" class="type_check" value="<%=type%>"><label for="Type_Check_<%=type%>"></label><span class="board_write_tit" style=<%=hycaptalStyle%> id="typeTitle_<%=type%>"><%=icBean.getIc_name()%><%=requisiteSign%></span></th>
					<% } else if (type.equals("11")){ %>
					<tr id="SBTM" style='display:none;' name="SBTM" value="" >
					<th style="width: 200px;"><input type="checkbox" name= "SBTM_chk" id="Type_Check_<%=type%>" class="type_check" value="<%=type%>"><label for="Type_Check_<%=type%>"></label><span class="board_write_tit" style=<%=hycaptalStyle%> id="typeTitle_<%=type%>"><%=icBean.getIc_name()%><%=requisiteSign%></span></th>
					<% } else if (type.equals("12")){ %>
					<tr id="CEO" style='display:none;' name="CEO" value="" >
					<th style="width: 200px;"><input type="checkbox" name= "CEO_chk" id="Type_Check_<%=type%>" class="type_check" value="<%=type%>"><label for="Type_Check_<%=type%>"></label><span class="board_write_tit" style=<%=hycaptalStyle%> id="typeTitle_<%=type%>"><%=icBean.getIc_name()%><%=requisiteSign%></span></th>
					<% } else if (type.equals("13")){ %>
					<tr id="IR" style='display:none;' name="IR" value="" >
					<th style="width: 200px;"><input type="checkbox" name= "IR_chk" id="Type_Check_<%=type%>" class="type_check" value="<%=type%>"><label for="Type_Check_<%=type%>"></label><span class="board_write_tit" style=<%=hycaptalStyle%> id="typeTitle_<%=type%>"><%=icBean.getIc_name()%><%=requisiteSign%></span></th>
					<% } else if (type.equals("14")){ %>
					<tr id="ESG" style='display:none;' name="ESG" value="" >
					<th style="width: 200px;"><input type="checkbox" name= "ESG_chk" id="Type_Check_<%=type%>" class="type_check" value="<%=type%>"><label for="Type_Check_<%=type%>"></label><span class="board_write_tit" style=<%=hycaptalStyle%> id="typeTitle_<%=type%>"><%=icBean.getIc_name()%><%=requisiteSign%></span></th>
					<% } else { %>
					<tr>
					<th style="width: 200px;"><input type="checkbox" id="Type_Check_<%=type%>" class="type_check" value="<%=type%>"><label for="Type_Check_<%=type%>"></label><span class="board_write_tit" style=<%=typeStyle%> id="typeTitle_<%=type%>"><%=icBean.getIc_name()%><%=requisiteSign%></span></th>					
					<% } %>					
					<td colspan="3">
 					<input type="hidden" id="focus_<%=type%>_1" value="">
					<%
						for (int i = 1; i < arrIcBean.size(); i++) {
							
							IssueCodeBean subIcBean = (IssueCodeBean) arrIcBean.get(i);
							
							if (type.equals("1")){	
								%>
									<input type="radio" id="typeCode<%=subIcBean.getIc_type()%>_<%=subIcBean.getIc_code() %>" name="typeCodeCheckbox_<%=subIcBean.getIc_type()%>"  value="<%=subIcBean.getIc_type()%>,<%=subIcBean.getIc_code() %>"  onclick="HyDisplay(<%=subIcBean.getIc_type()%>,<%=subIcBean.getIc_code()%>,'1',<%=type%>);" <%=checked %> />
									<% } else if(!type.equals("1")){ %>
									<%-- <input type="radio" id="typeCode<%=subIcBean.getIc_type()%>_<%=subIcBean.getIc_code() %>" name="typeCodeCheckbox" value="<%=subIcBean.getIc_type()%>,<%=subIcBean.getIc_code() %>" onclick="getTrend('<%=type%>');getInfoAttr('<%=type %>')" <%=checked %> /> --%>	
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
										
					%>
							    <tr id="tr_typeCode<%=type%>_subDepth_<%=t+2%>" style='display:<%=block%>;'  data-depth="<%=t+2%>" name="tr_typeCode_<%=type%>" value="<%=type%>,<%=t+2%>">
								<th style="background-color: #F8F8F8;"><span style="padding-left:10px; <%=typeStyle%>"><img alt="하위분류" src="../../images/issue/icon_bot_02.gif"><%=tmp_sub_type_list.get(t)[1] %></span></th>
								<td id="td_typeCode<%=type%>_subDepth_<%=t+2%>" colspan="<%=colsapn%>" >
								<input type="hidden" id="focus_<%=type%>_<%=t+2%>" value=""> <!-- 라디오 박스 해제용 -->
								</td>
							</tr>
				<%		
				
						 }
					}
				}
				%>
					<tr>
					<th style="width: 200px;">
						<input type="checkbox" id="Type_Check_clout" class="type_check"><label for="Type_Check_clout"></label>
						<span class="board_write_tit" style="font-weight:normal;">영향력</span>
					</th>
					<td colspan="3">
						<input type="radio" id="clout_power" name="typeCodeCheckbox_clout"  value="1" onclick="chk_clout(this.value)"/>
						<label for="clout_power" style="cursor:pointer">파워</label>
						
						<input type="radio" id="clout_normal" name="typeCodeCheckbox_clout"  value="2" onclick="chk_clout(this.value)"/>
						<label for="clout_normal" style="cursor:pointer">일반</label>
												
						<!-- 라디오 박스 해제용 -->
						<input type="hidden" id="focus_clout" value="">		
					</td>	
					</tr>
					<tr>
					<th style="width: 200px;">
						<input type="checkbox" id="Type_Check_senti" class="type_check"><label for="Type_Check_senti"></label>
						<span class="board_write_tit" style="font-weight:normal;">성향</span>
					</th>
					<td colspan="3">
						<input type="radio" id="senti_pos" name="typeCodeCheckbox_senti"  value="1" onclick="chk_senti(this.value)"/>
						<label for="senti_pos" style="cursor:pointer">긍정</label>
						
						<input type="radio" id="senti_neg" name="typeCodeCheckbox_senti"  value="2" onclick="chk_senti(this.value)"/>
						<label for="senti_neg" style="cursor:pointer">부정</label>
												
						<input type="radio" id="senti_neu" name="typeCodeCheckbox_senti"  value="3" onclick="chk_senti(this.value)"/>
						<label for="senti_neu" style="cursor:pointer">중립</label> 
						<!-- 라디오 박스 해제용 -->
						<input type="hidden" id="focus_senti" value="">		
					</td>	
					</tr>
					<tr>
						<th style="width: 200px;">
							<input type="checkbox" id="Type_Check_trans" class="type_check"><label for="Type_Check_trans"></label>
							<span class="board_write_tit" style="font-weight:normal;">보고서</span>
						</th>
						<td colspan="3">
							<input type="radio" id="trans_yes" name="typeCodeCheckbox_trans" value="Y" onclick="chk_trans(this.value)"/>
							<label for="trans_yes" style="cursor:pointer">포함</label>
							
							<input type="radio" id="trans_no" name="typeCodeCheckbox_trans" value="N" onclick="chk_trans(this.value)"/>
							<label for="trans_no" style="cursor:pointer">미포함</label>
							<!-- 라디오 박스 해제용 -->
							<input type="hidden" id="focus_trans" value="">
						</td>	
					</tr>
					<tr>
						<th style="width: 200px;">
							<input type="checkbox" id="Type_Check_relationkeyword" class="type_check"><label for="Type_Check_relationkeyword"></label>
							<span class="board_write_tit" style="font-weight:normal;">연관키워드</span>
						</th>
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
		<td style="text-align:center;"><img src="../../images/search/btn_save_2.gif" onclick="save_multi_issue_update('<%=mode%>');" style="cursor:pointer;"/>&nbsp;<img src="../../images/search/btn_cancel.gif" onclick="window.close();" style="cursor:pointer;"/></td>
	</tr>
	</table>
</div>
</body>
</html>