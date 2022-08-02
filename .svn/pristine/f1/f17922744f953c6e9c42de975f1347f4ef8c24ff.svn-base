<%@ page contentType = "text/html; charset=utf-8"%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%@ page import="risk.admin.alimi.AlimiLogMgr
              	,risk.admin.alimi.AlimiLogSuperBean
              	,risk.mobile.AlimiSettingBean
				,risk.util.ParseRequest
				,java.util.ArrayList
				,java.util.*				
				" %>
<%@page import="risk.util.PageIndex"%>
<%
	
	ParseRequest pr = new ParseRequest(request);	
	pr.printParams();
	DateUtil du = new DateUtil();
	
	
	String searchKey = pr.getString("searchKey");
	String sDateFrom = pr.getString("sDateFrom");
	String sDateTo = pr.getString("sDateTo");
	String tl_code = pr.getString("tl_code");
	
	//검색날짜 설정 : 기본 30일간 검색한다.
	String sCurrDate    = du.getCurrentDate();
	if (sDateTo.equals("")) sDateTo = du.getCurrentDate();
	if (sDateFrom.equals("")) {
		du.addDay(-30);
		sDateFrom = du.getDate();
	}
	
	String mal_type = pr.getString("mal_type","");
	
	
	int rowCnt = 10;
	int iNowPage  = pr.getInt("nowpage",1);	
	
	
	AlimiLogMgr lMgr = new AlimiLogMgr();
	
	//ArrayList reData = lMgr.getAlimiLogList(iNowPage , rowCnt, sDateFrom, sDateTo, mal_type, searchKey, as_type);
	ArrayList reData = lMgr.getTelegramLogList(iNowPage , rowCnt, sDateFrom, sDateTo, mal_type, searchKey, tl_code);
	
	int iTotalPage      = lMgr.getFullCnt() / rowCnt;
    if ( ( lMgr.getFullCnt() % rowCnt ) > 0 ) iTotalPage++;
	
%>

<%@page import="risk.util.DateUtil"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>RISK</title>
<link rel="stylesheet" type="text/css" href="../../../css/base.css" />
<style>
iframe.hide { border:0px solid red; position:absolute; top:0px; left:0px; z-index:-99; width:148px; height:150px; filter: alpha(opacity=0);}
</style>
<script src="<%=SS_URL%>js/jquery.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/ajax.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/popup.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/Calendar.js" type="text/javascript"></script>
<script language="javascript">
<!--
	
	function pageClick( paramUrl ) {

		var f = document.fSend;
		//f.nowpage.value = paramUrl.substr(paramUrl.indexOf("=")+1, paramUrl.length);
		f.action='alimi_log_list.jsp'+ paramUrl;
		f.submit();
    }

	//Url 링크
 	var chkPop = 1;
	function hrefPop(url){
		//window.open(url,'hrefPop'+chkPop,'');
		window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop'+chkPop,'');
		chkPop++;
	}

	//검색
 	function search(){ 		
 		var f = document.fSend;
 				
		f.nowpage.value = '1';
 		f.action = 'alimi_log_list.jsp';
 		f.target = '';
 		f.submit(); 	
 	}
	
//-->
</script>
</head>
<body style="margin-left: 15px">
<form name=fSend method="post">
<input type="hidden" name="mode">
<input type="hidden" name="nowpage" value="<%=iNowPage%>">
<input type="hidden" name="as_seq">
<input type="hidden" name="as_seqs">
<table style="width:100%;height:100%;" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="body_bg_top" valign="top">
		<table style="width:820px;" border="0" cellpadding="0" cellspacing="0">
			<!-- 타이틀 시작 -->
			<tr>
				<td class="tit_bg" style="height:67px;padding-top:20px;">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="../../../images/admin/alimi/tit_icon.gif" /><img src="../../../images/admin/alimi/tit_1207.gif" /></td>
						<td align="right">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="navi_home">HOME</td>
								<td class="navi_arrow">관리자</td>
								<td class="navi_arrow2">텔레그램 알리미 로그관리</td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td height="15"></td>
			</tr>
			<!-- 타이틀 끝 -->
			<tr style="display: none">
				<td style="height:40px;">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td style="width:76px;"><img src="../../../images/admin/alimi/btn_allselect.gif" onclick="allselect();" style="cursor:pointer;"/></td>
						<td><img src="../../../images/admin/alimi/btn_del.gif" onclick="delList();" style="cursor:pointer;"/></td>
					</tr>
				</table>
				</td>
			</tr>
			
			
			
			
			
			
			<tr>
				<td>
				
				<table width="820" border="0" cellspacing="0" cellpadding="0">
				  <tr>
				    <td><table width="820" border="0" cellspacing="0" cellpadding="0">
				      <tr>
				        <td width="15"><img src="../../../images/issue/table_bg_01.gif" width="15" height="35" /></td>
				        <td width="793" background="../../../images/issue/table_bg_02.gif"><table width="790" border="0" cellspacing="0" cellpadding="0">
				          <tr>
				            <td width="17"><img src="../../../images/issue/table_icon_01.gif" width="16" height="12" /></td>
				            <td width="62" class="b_text"><strong>검색단어 </strong></td>
				            <td width="323"><input type="text" class="textbox3" style="width:310px;" name="searchKey" value="<%=searchKey%>" onkeydown="Javascript:if(event.keyCode == 13){search();}"/></td>
				            <td width="16"><img src="../../../images/issue/table_icon_01.gif" width="16" height="12" /></td>
				            <td width="60" class="b_text"><strong>검색기간</strong></td>
				            <td width="312"><table border="0" cellpadding="0" cellspacing="0">
				              <tr>
				                <td width="86"><input style="width:90px;text-align:center" class="textbox" type="text" id="sDateFrom" name="sDateFrom" value="<%=sDateFrom%>"></td>
				                <td width="27"><img src="../../../images/issue/btn_calendar.gif" style="cursor:pointer" onclick="calendar_sh(document.getElementById('sDateFrom'))"/></td>
				                <td width="11" align="center">~</td>
				                <td width="86"><input style="width:90px;text-align:center" class="textbox" type="text" id="sDateTo" name="sDateTo" value="<%=sDateTo%>"></td>
				                <td width="27"><img src="../../../images/issue/btn_calendar.gif" style="cursor:pointer" onclick="calendar_sh(document.getElementById('sDateTo'))"/></td>
				                <td width="75"><img src="../../../images/issue/search_btn.gif" width="61" height="22" hspace="5" align="absmiddle" onclick="search()" style="cursor:pointer"/></td>
				              </tr>
				            </table></td>
				          </tr>
				        </table></td>
				        <td width="12"><img src="../../../images/issue/table_bg_03.gif" width="12" height="35" /></td>
				      </tr>
				    </table></td>
				  </tr>
				  <tr>
				    <td><table width="820" border="0" cellspacing="0" cellpadding="0">
				      <tr>
				        <td width="1" bgcolor="D3D3D3"></td>
				        <td width="818" bgcolor="F7F7F7" style="padding:10px 0px 5px 0px"><table width="798" border="0" align="center" cellpadding="0" cellspacing="0">
				          <tr>
				            <td height="24" style="padding:0px 0px 5px 7px"><table width="790" border="0" cellspacing="0" cellpadding="0">
				              <tr>
				             	<td width="60px" class="b_text"><img src="../../../images/issue/icon_search_bullet.gif" width="9" height="9" /> <strong>그     룹</strong></td>
				                <td width="150px"><select name="tl_code" class="textbox3" style="width: 105px">
								                  <option value="">선택하세요</option>
								                  <option value="1" <%if(tl_code.equals("1")){out.print("selected");}%>>그룹방1</option>
								                  <option value="2" <%if(tl_code.equals("2")){out.print("selected");}%>>그룹방2</option>
								                  <option value="3" <%if(tl_code.equals("3")){out.print("selected");}%>>그룹방3</option>
								                  <option value="4" <%if(tl_code.equals("4")){out.print("selected");}%>>그룹방4</option>
				                                </select></td>
			           
				                <td width="60px" class="b_text"><img src="../../../images/issue/icon_search_bullet.gif" width="9" height="9" /> <strong>상     태</strong></td>
				                <td><select name="mal_type" class="textbox3" style="width: 105px">
								                  <option value="">선택하세요</option>
								                  <option value="1" <%if(mal_type.equals("1")){out.print("selected");}%>>발송</option>
								                  <option value="2" <%if(mal_type.equals("2")){out.print("selected");}%>>실패</option>
								                  <option value="3" <%if(mal_type.equals("3")){out.print("selected");}%>>유사</option>
				                                </select></td>
				              </tr>
				            </table></td>
				          </tr>
				        
				        </table></td>
				        <td width="1" bgcolor="D3D3D3"></td>
				      </tr>
				    </table></td>
				  </tr>
				  <tr>
				    <td height="1" bgcolor="D3D3D3"></td>
				  </tr>
				</table>
				
				</td>
			</tr>
			
			<tr height="30px"><td></td></tr>
			
			
			
			<!-- 게시판 시작 -->
			<tr>
				<td>       
				<table id="board_01" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
				<col width="15%"><col width="60%"><col width="5%"><col width="10%"><col width="10%">
					<tr>           
						<th>그     룹</th>
						<th>제     목</th>
						<th>상 태</th>
						<th>수집시간</th>
						<th>발송시간</th>
					</tr>
<%
	
	if(reData.size()>0){
		AlimiLogSuperBean.AlimiLogList abean = null;
		String type = "";
		String status = "";
	  	for(int i=0; i < reData.size(); i++)
		{
	  		abean = 	(AlimiLogSuperBean.AlimiLogList)reData.get(i);
	  		ArrayList arrReceiverList = new ArrayList();
	  		
/* 	  		if(abean.getAs_type().equals("1")){
	  			type = "ico_email.gif";
	  		}else if(abean.getAs_type().equals("2")){
	  			type = "ico_sms.gif";
	  		}else if(abean.getAs_type().equals("3")){
	  			type = "ico_Rrimi.gif";
	  		}
 */	  		
	  		if(abean.getTl_send_status().equals("1")){
	  			status = "발송";
	  		}else if(abean.getTl_send_status().equals("2")){
	  			status = "실패";
	  		}else if(abean.getTl_send_status().equals("3")){
	  			status = "유사";
	  		}else{
	  			status = "";
	  		}
%>
					<tr>
						<td>그룹방<%=abean.getTl_code()%></td>
						<td style="text-align: left;"><%=abean.getTl_message()%></td>
						<td style=""><%=status%></td>
						<td><%= du.getDate(abean.getMd_date(), "MM-dd HH:mm")%></td>
						<td><%= du.getDate(abean.getTl_send_date(), "MM-dd HH:mm")%></td>
					</tr>
<%
		}
	}else{
%>
					<tr>
						<td colspan="6" style="font-weight:bold;height:40px" align="center">조건에 맞는 데이터가 없습니다.</td>
					</tr>
<%		
	}
%>
				</table>
				</td>
			</tr>
			<!-- 게시판 끝 -->
			<tr>
				<td style="height:40px;" align="center">
				<table style="padding-top:10px;" border="0" cellpadding="0" cellspacing="1">
					<tr>
						<td>
							<table id="paging" border="0" cellpadding="0" cellspacing="2">
								<tr>
									<%=PageIndex.getPageIndex(iNowPage, iTotalPage,"","" )%>
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
</table>
</form>
<%-- 달력 테이블 --%>
<table width="162" border="0" cellspacing="0" cellpadding="0" id="calendar_conclass" style="position:absolute;display:none;">
	<tr>
		<td><img src="../../../images/calendar/menu_bg_004.gif" width="162" height="2"></td>
	</tr>
	<tr>
		<td align="center" background="../../../images/calendar/menu_bg_005.gif"><table width="148" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="6"></td>
			</tr>                                		
			<tr>
				<td id="calendar_calclass">
				</td>
			</tr>
			<tr>
				<td height="5"></td>
			</tr>
		</table></td>
	</tr>
	<tr>
		<td><img src="../../../images/calendar/menu_bg_006.gif" width="162" height="2"></td>
	</tr>
</table>
</body>
</html>