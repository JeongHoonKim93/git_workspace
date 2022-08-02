<%@page import="java.sql.Array"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../inc/sessioncheck.jsp" %>
<%@ page import="risk.issue.IssueMgr"%>
<%@ page import="risk.issue.IssueCodeMgr"%>
<%@ page import="risk.issue.IssueCodeBean"%>
<%@ page import="risk.issue.IssueSuperBean"%>
<%@ page import="risk.search.userEnvInfo"%>
<%@ page import="risk.admin.member.MemberBean"%>
<%@ page import="risk.util.DateUtil"%>
<%@ page import="risk.util.ParseRequest"%>
<%@ page import="risk.util.PageIndex"%>
<%@ page import="java.net.URLDecoder"%>
<%@ page import="java.util.ArrayList"%>
<%
	ParseRequest pr	= new ParseRequest(request);
	pr.printParams();
	DateUtil du = new DateUtil();
	//IssueCodeMgr 인스턴스 생성
	IssueCodeMgr icMgr = IssueCodeMgr.getInstance();
	icMgr.init(0);
	//IssueMgr
	IssueMgr issueMgr = new IssueMgr();
	// Parameter
	String keyType = pr.getString("keyType", "1");
	String searchKey = URLDecoder.decode(pr.getString("encodingSearchKey",""),"UTF-8");
	String sDateFrom = pr.getString("sDateFrom");
	String sDateTo = pr.getString("sDateTo");
	String ir_stime = pr.getString("ir_stime","0");
	String ir_etime = pr.getString("ir_etime","23");
	String register = pr.getString("register");
	
	String main_type_list = "1,11,6,9,5,4,16";
	HashMap<String, ArrayList<String[]>> subDepthList = issueMgr.getSubdepthList(main_type_list); //하위뎁스리스트
	
	int max_td_cnt = 8;
	int tmp_chk_td = 0;
	int tmp_colspan_size = 0;
	//검색날짜 설정 : 기본 7일간 검색한다.
	String sCurrDate = du.getCurrentDate();
	if (sDateTo.equals("")) sDateTo = du.getCurrentDate();
	if (sDateFrom.equals("")) {
		//du.addDay(-1);
		sDateFrom = du.getDate();
	}	
	
	String setStime = "";
	String setEtime = "";
	if(ir_stime.equals("24")){
		setStime = "23:59:59";	
	}else{
		setStime = ir_stime + ":00:00";
	}
	if(ir_etime.equals("24")){
		setEtime = "23:59:59";	
	}else{
		setEtime = ir_etime + ":59:59";
	}
	
	//세션정보 가져오기~~
	userEnvInfo uei = null;
	uei = (userEnvInfo) session.getAttribute("ENV");
	
	// 데이터 정렬
	String sOrder       = pr.getString("sOrder","MD_DATE");
    String sOrderAlign  = pr.getString("sOrderAlign","DESC"); 
	
	// 데이터 정렬
	uei.setOrder( sOrder );
    uei.setOrderAlign( sOrderAlign );
	
    /**
    * 필드별 정렬
    */
   	String sOrderImg    = "";
    String sOrderMark1 = "";
    String sOrderMark2 = "";
    String sOrderMark3 = "";
    String sOrderMark4 = "";
    String sOrderMark5 = "";
    
 	// 사이트 그룹 정렬관련
 	ArrayList arrSiteGroup = issueMgr.getSiteGroup_order();
 	String SiteGroups = "";
 	for(int i =0; i < arrSiteGroup.size(); i++){
 		
 		if(SiteGroups.equals("")){
 			SiteGroups = "[" + ((String[])arrSiteGroup.get(i))[1] + "]"; 
 		}else{
 			SiteGroups += " " + "[" + ((String[])arrSiteGroup.get(i))[1] + "]";
 		}
 	}
 	
 	/* 연관키워드 셀렉트박스 목록 */
	//ArrayList relationKeyAll =  new ArrayList();
 	
	boolean ipChk = false;
	//원문보기 권한 부여
	if(SS_M_ORGVIEW_USEYN.equals("Y")){
		ipChk = true;
	} 
/***************라인차트 가져오기~~~*********************/
	
	/* String dateMaster = "";
	
	ArrayList chartData = null;
	long diffSecond = du.DateDiffSecond("yyyy-MM-dd HH", sDateTo + " " + ir_etime, sDateFrom + " " + ir_stime);
	
	int diffHour = (int)(diffSecond /(60*60));  
	
	String dateType = "";
	
	if(diffHour > 24){
		
		dateType = "day";
		
		//일자 마스터 가져오기~~
		int diffDay = (int)du.DateDiff("yyyy-MM-dd",sDateTo,sDateFrom);
		//dateMaster = new String[diffDay +1];
		for(int i = 0; i < (diffDay +1); i++){
			if(dateMaster.equals("")){
				dateMaster = (du.addDay_v2(sDateTo, -(diffDay - i))).replaceAll("-","");	
			}else{
				dateMaster += "," + (du.addDay_v2(sDateTo, -(diffDay - i))).replaceAll("-","");
			}
		}
		
	}else{
		dateType = "time";
			
		//시간 마스터 가져오기~~
		int diffDay = (int)(du.DateDiffSecond("yyyy-MM-dd HH", sDateTo + " " + ir_etime, sDateFrom + " " + ir_stime)  / (60*60));
		//dateMaster = new String[diffDay +1];
		for(int i = 0; i < (diffDay +1); i++){
			if(dateMaster.equals("")){
				dateMaster = (du.addHour(sDateTo + " " + ir_etime, -(diffDay - i), "yyyy-MM-dd HH", "yyyy-MM-dd HH")).replaceAll("-","");	
			}else{
				dateMaster += "," + (du.addHour(sDateTo + " " + ir_etime, -(diffDay - i), "yyyy-MM-dd HH", "yyyy-MM-dd HH")).replaceAll("-","");
			}
		}
	}
	
	//chartData = issueMgr.getIssueChart1(sDateFrom,setStime,sDateTo,setEtime, typeCode, dateType , searchKey, uei.getMg_company(),keyType);
	//계열사별로 권한을 부여해서 데이터를 조회 할 필요가 없어서 관련 내용 공백으로 넘김
	//chartData = issueMgr.getIssueChart1(sDateFrom,setStime,sDateTo,setEtime, typeCode, dateType , searchKey, "",keyType);
	chartData = issueMgr.getChart(sDateFrom, setStime, sDateTo, setEtime, "", "", dateType, searchKey, keyType, "", register);
	
	// 스크립트로 넘기기 위한 작업~~
	String md_date = "";
	String positive = "";
	String negative = "";
	String neutral = "";
	IssueSuperBean.Issue_chartBean chartBean = null;
	for(int i =0; i < chartData.size(); i++){
		chartBean = (IssueSuperBean.Issue_chartBean) chartData.get(i);
		if(i == 0){
			md_date = chartBean.getMd_date();
			positive = chartBean.getPositive();
			negative = chartBean.getNegative();
			neutral = chartBean.getNeutral();
		}else{
			md_date += "," +  chartBean.getMd_date();
			positive += "," +  chartBean.getPositive();
			negative += "," +  chartBean.getNegative();
			neutral += "," +  chartBean.getNeutral();
		}
	}
	 */
	 
	// 사용자 그룹별 버튼 숨기기용
	String Jnone = "";
	//if(uei.getMg_seq().equals("2") || uei.getMg_seq().equals("3") || uei.getMg_seq().equals("9")){
		Jnone = "";
	//}else{
	//	Jnone = "none";
	//}
	
	/* 등록자 목록 */
	ArrayList memberList = new ArrayList();
	memberList = issueMgr.getIssueRegMember(); // 2,3 RSN관리자 및 정보관리자
	
	// 보도자료는 별도 표시
   	//ArrayList pressDataList = icMgr.getIssueCode("12", "IC_REGDATE DESC, IC_NAME ASC");
	

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>RISK</title>
<link rel="stylesheet" type="text/css" href="../../css/base.css" />
<link rel="stylesheet" type="text/css" href="../../axisj/ui/arongi/AXJ.min.css"/>
<style type="text/css">
iframe.hide { border:0px solid red; position:absolute; top:0px; left:0px; z-index:-99; width:148px; height:150px; filter: alpha(opacity=0);}
.contextMenuBody{ height:300px; overflow :auto; }
</style>
<script src="<%=SS_URL%>js/Calendar.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/ajax.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/jquery.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/popup.js" type="text/javascript"></script>
<script src="<%=SS_URL%>riskv3/issue/js/amcharts.js" type="text/javascript"></script>
<script src="<%=SS_URL%>riskv3/issue/js/serial.js" type="text/javascript"></script>
<script src="<%=SS_URL%>riskv3/issue/js/pie.js" type="text/javascript"></script>
<script src="<%=SS_URL%>riskv3/issue/js/radar.js" type="text/javascript"></script>
<script src="<%=SS_URL%>riskv3/issue/js/xy.js" type="text/javascript"></script>
<script src="<%=SS_URL%>riskv3/issue/js/issue_data_list.js" type="text/javascript"></script>
<%-- <script src="<%=SS_URL%>riskv3/issue/js/issue_data_amchart.js" type="text/javascript"></script> --%>
</head>
<body style="margin-left: 15px">
<form name="fSend" id="fSend" action="" method="post">
	<input type="hidden" id="typeCodeSelect" name="typeCodeSelect" />
	<input type="hidden" id="typeCodeSenti" name="typeCodeSenti" />
	<input type="hidden" id="typeCodeInfo" name="typeCodeInfo" />
	<input type="hidden" id="relationKeyword" name="relationKeyword" />
	<input type="hidden" id="registerInput" name="register" />
	<input type="hidden" id="param_sDate" name="param_sDate" />
	<input type="hidden" id="param_eDate" name="param_eDate" />
	<input type="hidden" id="param_sTime" name="param_sTime" />
	<input type="hidden" id="param_eTime" name="param_eTime" />
	<input type="hidden" id="param_searchKey" name="param_searchKey" />
	<input type="hidden" id="param_keyType" name="param_keyType" />
	<input type="hidden" id="nowPage" name="nowPage" value="1"/>
	<input type="hidden" id="md_seq" name="md_seq" />
	<input type="hidden" id="id_seqs" name="id_seqs" />
	<input type="hidden" id="mode" name="mode" />
	<input type="hidden" id="child" name="child" />
	<input type="hidden" id="sOrder" name="sOrder" />
	<input type="hidden" id="sOrderAlign" name="sOrderAlign" />
	<input type="hidden" id="sameListId" name="sameListId" />
	<input type="hidden" id="sameListSite" name="sameListSite" />
	
</form>

<table style="width:100%;height:100%;" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="body_bg_top" valign="top">
		<table id="main_table" style="width:820px;" border="0" cellpadding="0" cellspacing="0">
			<!-- 타이틀 시작 -->
			<tr>
				<td class="tit_bg" style="height:67px;padding-top:20px;">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="../../images/issue/tit_icon.gif" /><img src="../../images/issue/tit_0201.gif" /></td>
						<td align="right">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="navi_home">HOME</td>
								<td class="navi_arrow">이슈관리</td>
								<td class="navi_arrow2">관련정보</td>
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
			<!-- 검색 시작 -->
			<tr>
				<td>
					<table width="890" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td><table width="890" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="15"><img src="../../images/issue/table_bg_01.gif" width="15" height="35" /></td>
							<td width="863" background="../../images/issue/table_bg_02.gif"><table width="862" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="17"><img src="../../images/issue/table_icon_01.gif" width="16" height="12" /></td>
								<td width="105" class="b_text">
									<select name="keyType" style="width: 100px; height: 20px;">
					            		<option value="1" <%if(keyType.equals("1")){out.print("selected");}%>>제목검색</option>
					            		<option value="2" <%if(keyType.equals("2")){out.print("selected");}%>>제목+내용검색</option>
					            	</select>
								</td>
								<td width="250"><input type="text" class="textbox3" style="width:180px;" id="searchKey" name="searchKey" value="<%=searchKey%>" onkeydown="Javascript:if(event.keyCode == 13){search();}"/></td>
								<td width="16"><img src="../../images/issue/table_icon_01.gif" width="16" height="12" /></td>
								<td width="60" class="b_text"><strong>검색기간</strong></td>
								<td width="402"><table border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td width="76"><input style="width:100%;text-align:center" class="textbox" type="text" id="sDateFrom" name="sDateFrom" value="<%=sDateFrom%>"></td>
										<td width="27"><img src="../../images/issue/btn_calendar.gif" style="cursor:pointer" onclick="calendar_sh(document.getElementById('sDateFrom'))"/></td>
										<%
						                	String sBasics  = ir_stime.equals("") ? "16" :  ir_stime;
						                	String eBasics  = ir_etime.equals("") ? "15" :  ir_etime;
										%>
										<td width="55"><select name="ir_stime"><%for(int i=0; i<24; i++){ if(i==Integer.parseInt(sBasics)){out.print("<option value="+i+" selected>"+i+"시</option>");}else{out.print("<option value="+i+">"+i+"시</option>");}} %></select></td>
										<td width="11" align="center">~</td>
										<td width="76"><input style="width:100%;text-align:center" class="textbox" type="text" id="sDateTo" name="sDateTo" value="<%=sDateTo%>"></td>
										<td width="27"><img src="../../images/issue/btn_calendar.gif" style="cursor:pointer" onclick="calendar_sh(document.getElementById('sDateTo'))"/></td>
										<td width="55"><select name="ir_etime"><%for(int i=0; i<24; i++){ if(i==Integer.parseInt(eBasics)){out.print("<option value="+i+" selected>"+i+"시</option>");}else{out.print("<option value="+i+">"+i+"시</option>");}} %></select></td>
										<td width="85"><img src="../../images/issue/search_btn.gif" width="61" height="22" hspace="5" align="absmiddle" onclick="search()" style="cursor:pointer"/></td>
									</tr>
								</table></td>
							</tr>
							</table></td>
							<td width="12"><img src="../../images/issue/table_bg_03.gif" width="12" height="35" /></td>
						</tr>
						</table></td>
					</tr>
					
					 <!-- 여기부터 -->
					<tr>
				    <td><table width="890" border="0" cellspacing="0" cellpadding="0">
				    <tr>
				    	<td width="1" bgcolor="D3D3D3"></td>
				    	<td width="888" bgcolor="F7F7F7" style="padding:5px 0px 5px 0px"><table width="880" border="0" align="center" cellpadding="0" cellspacing="0">
				    	<%
				    		
				    		ArrayList arrIcBean = null;
				    		IssueCodeBean icBean = null;
				    		IssueCodeBean subIcBean = null;
				    		
				    		String[] main_code_list = main_type_list.split(",");
				    		ArrayList<String[]> tmp_code_list = null;
				    		String[] tmp_sub_info = null;
				    		for(String main_code : main_code_list){
				    			arrIcBean = new ArrayList();
				    			arrIcBean = icMgr.GetType(Integer.parseInt(main_code));
				    			if(arrIcBean == null) continue;
				    			
				    			icBean = (IssueCodeBean) arrIcBean.get(0);
				    			
				    	%>
				    		<tr>
				    			<td height="24" style="padding:5px 0px 5px 7px"><table width="880" border="0" cellspacing="0" cellpadding="0">
				    			<tr>
				    				<td width="70" class="b_text"><img src="../../images/issue/icon_search_bullet.gif" width="9" height="9" /><strong title="<%=icBean.getIc_name()%>"><%=icBean.getIc_name()%></strong></td>
									<td width="150">
									<select id="typeCode_<%=main_code%>" name="typeCodeSelect" class="textbox3" style="width:115px; margin-right: 20px" onchange="getSubTypeCode(<%=main_code%>);">
										<option value="">선택하세요</option>
										 <%
										 for (int i = 1; i < arrIcBean.size(); i++) {
											 icBean = (IssueCodeBean) arrIcBean.get(i);
										 %>  
											 <option value="<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>"><%=icBean.getIc_name() %></option>
										 <%
										 }
										 %>
									</select>
									</td>
									 <%
									 tmp_code_list = subDepthList.get(main_code);
									 for (int i = 0; i < tmp_code_list.size(); i++) {	
										 tmp_sub_info = tmp_code_list.get(i);
										// subIcBean = icm.getSearchIssueCode(ptype, pcode);
									%> 
									<td width="70" class="b_text"><img src="../../images/issue/icon_search_bullet.gif" width="9" height="9" /><strong title="<%=tmp_sub_info[1]%>"><%=tmp_sub_info[1]%></strong></td>
									<td width="150">
									<select id="subType_<%=main_code %>" name="subTypeInfo" class="textbox3" style="width:115px;  margin-right: 20px">
										<option value="" >선택하세요</option>
									</select>
									</td> 
									 <%
										 }
									%>
								<%-- 	<%
										if(!"8".equals(main_code)){
									%>
									<td width="70" class="b_text"><img src="../../images/issue/icon_search_bullet.gif" width="9" height="9" /><strong title="정보속성">정보속성</strong></td>
									<td width="150">
									<select id="infoCode_<%=main_code %>" name="infoSelect" class="textbox3" style="width:115px;  margin-right: 20px">
										<option value="" >선택하세요</option>
									</select>
									</td>
									<%
										} else {
									%>
									<td width="70" class="b_text"><img src="../../images/issue/icon_search_bullet.gif" width="9" height="9" /><strong title="연관키워드">연관키워드</strong></td>
									<td width="150">
									
									<select id="relKeyword_<%=main_code %>" name="relkeySelect" class="textbox3" style="width:115px;">
										<option value="">선택하세요</option>
									</select>
									</td>
									<%
										}
									%> --%>
									
				    			</tr>
				    			</table></td>
				    		</tr>
				    		<tr>
				            	<td><img src="../../images/issue/dotline.gif" width="880" height="3" /></td>
				      		</tr>
				    	<%
				    		}
				    	%>
				    	<tr>
				    		<% tmp_chk_td = 0; %>
				    		<td height="24" style="padding:5px 0px 5px 7px"><table width="880" border="0" cellspacing="0" cellpadding="0">
					    	<tr>
					    	<%--	<%
						    		arrIcBean = icMgr.GetType( 10 );
									icBean = (IssueCodeBean) arrIcBean.get(0);
					    		%>
					    	 	<td width="80" class="b_text"><img src="../../images/issue/icon_search_bullet.gif" width="9" height="9" /><strong title="<%=icBean.getIc_name()%>"><%=icBean.getIc_name()%></strong></td>
									<td width="140">
									<select id="typeCode_10" name="typeCodeSelect" class="textbox3" style="width:105px; margin-right: 20px" onchange="">
										<option value="">선택하세요</option>
										 <%
										 for (int i = 1; i < arrIcBean.size(); i++) {
											 icBean = (IssueCodeBean) arrIcBean.get(i);
										 %>  
											 <option value="<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>"><%=icBean.getIc_name() %></option>
										 <%
										 }
										 %>
									</select>
								</td> --%>
								
								<%
						    		//arrIcBean = icMgr.GetType( 11 );
									//icBean = (IssueCodeBean) arrIcBean.get(0);
					    		%>
					    		<td width="80" class="b_text"><img src="../../images/issue/icon_search_bullet.gif" width="9" height="9" /><strong title="성향">성향</strong></td>
					    		<% tmp_chk_td++;%>
									<td width="140">
									<% tmp_chk_td++;%>
									<select id="typeCode_11" name="typeCodeSelect" class="textbox3" style="width:105px; margin-right: 20px" onchange="">
										<option value="">선택하세요</option>
										<option value="1">긍정</option>
										<option value="2">부정</option>
										<option value="3">중립</option>										
									</select>
								</td>
								
					    	<%-- 	<td width="80" class="b_text"><img src="../../images/issue/icon_search_bullet.gif" width="9" height="9" /><strong title="보도자료">보도자료</strong></td>
									<td width="140">
									<select id="typeCode_12" name="typeCodeSelect" class="textbox3" style="width:105px; margin-right: 20px" onchange="">
										<option value="">선택하세요</option>
										 <%
										 String ic_name_format = "";
										 for (int i = 0; i < pressDataList.size(); i++) {
											 icBean = (IssueCodeBean) pressDataList.get(i);
											 ic_name_format = "("+du.getDate(icBean.getIc_regdate(), "yyyy-MM-dd") + ") - " + icBean.getIc_name();
										 %>  
											 <option value="<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>"><%=ic_name_format %></option>
										 <%
										 }
										 %>
									</select>
								</td> --%>
																
					    		<td width="80" class="b_text"><img src="../../images/issue/icon_search_bullet.gif" width="9" height="9" /><strong title="등록자">등록자</strong></td>
					    		<% tmp_chk_td++;%>
									<td width="140">
									<% tmp_chk_td++;%>
									<select id="registerSelect" name="registerSelect" class="textbox3" style="width:105px; margin-right: 20px" onchange="">
										<option value="">선택하세요</option>
										 <%
											for (int i = 0; i < memberList.size(); i++) {
												MemberBean mBean = (MemberBean)memberList.get(i);
												
												if(register.equals(mBean.getM_seq())){
										%>
													<option value="<%=mBean.getM_seq()%>" selected="selected"><%= mBean.getM_name()%></option>
										<%		
												}else{
										%>
													<option value="<%=mBean.getM_seq()%>"><%= mBean.getM_name()%></option>
										<%
												}
											}
										%>
									</select>
								</td>
								<%if(tmp_chk_td<max_td_cnt){
									tmp_colspan_size = (max_td_cnt-tmp_chk_td);
									%>
										<td colspan="<%=tmp_colspan_size%>"></td>
									<%
								} %>
								
					    	</tr>
				    		</table>
				    		</td>
				    	</tr>
				    	
				    	<!-- <tr>
							<td><img src="../../images/issue/dotline.gif" width="880" height="3" /></td>
				      	</tr>
				    	
				    	<tr>
				    		<td height="24" style="padding:5px 0px 5px 7px"><table width="880" border="0" cellspacing="0" cellpadding="0">
					    	<tr>
					    	</tr>
					    	</table></td>
					    </tr>
				    	 -->
				    	</table>
				    	<td width="1" bgcolor="D3D3D3"></td>
				    </tr>
				    
				    </table></td>
				    </tr>
					 <!-- 선택 박스 -->
					
					</table></td>
				</tr>
			<!-- 여기까지 -->
			<tr>
				<td height="1" bgcolor="D3D3D3">
				</td>
			</tr>
			<tr>
		<td style="height:10px;">		
			<!-- <div id="chartdiv1" style="width: 890px; height: 300px; float: left;"></div> -->
		</td> 
	</tr>
	<tr>
		<td id="count_td" width="100%" align="left"></td>
	</tr>
	<tr>
		<td style="height:40px;">
		<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td style="display: <%=Jnone%>"><img src="../../images/issue/btn_allselect.gif" onclick="checkSeq($('#id_seqs_all'), 1);" style="cursor:pointer;"/></td>
				<td style="display: <%=Jnone%>">&nbsp;</td>
				<%-- <td style="display: <%=Jnone%>"><img src="../../images/issue/btn_mailsend.gif" onclick="goMailTo();" style="cursor:pointer;"/></td>
				<td style="display: <%=Jnone%>">&nbsp;</td> --%>
				<%-- <td style="display: <%=Jnone%>"><img src="../../images/issue/btn_quickexcelsave.gif" onclick="goExcelTo();" style="cursor:pointer;"/></td>
				<td style="display: <%=Jnone%>">&nbsp;</td> --%>
				<!-- 2016-04-12 일반 엑셀 및 다운 기능 업그레이드 추가 -->
				<td style="display: <%=Jnone%>"><img src="../../images/issue/btn_excelsave.gif" onclick="goExcelTo();" style="cursor:pointer;"/></td>
				<td style="display: <%=Jnone%>">&nbsp;</td>
				<!-- 2017-02-09 엑셀 다운 간소화 추가 -->
				<%-- <td style="display: <%=Jnone%>"><img src="../../images/issue/btn_excel_new.gif" onclick="PopExcelForm(2);" style="cursor:pointer;"/></td>
				<td style="display: <%=Jnone%>">&nbsp;</td> --%>
				
				<td style="display: <%=Jnone%>"><img src="../../images/issue/btn_del.gif" onclick="deleteIssueData();" style="cursor:pointer;"/></td>
				<td style="display: <%=Jnone%>">&nbsp;</td>
				<td style="display: <%=Jnone%>"><img src="../../images/search/btn_same.gif" onclick="samePackage();" style="cursor:pointer;"/></td>
				<td style="display: <%=Jnone%>">&nbsp;</td>
				<td style="display: <%=Jnone%>"><img src="../../images/issue/btn_multi.gif" onclick="PopIssueDataForm_multi();" style="cursor:pointer;"/></td>
				<!-- 2017-03-21 SK 담당자들 전용 엑셀 추가 (본문 절대 넣지 말것!) -->
				<td style="display: <%=Jnone%>">&nbsp;</td>
				<!-- <td><img src="../../images/issue/btn_excelsave.gif" onclick="goExcelTo_SK();" style="cursor:pointer;"/></td> -->
				<td>&nbsp;</td>
				<td width="100%" align="right" style="padding-right: 10px; padding-top: 15px"></td>
			</tr>
		</table>				
		</td>
	</tr>
	<!-- 게시판 시작 -->
	<tr>
		<td>
		<table id="board_01" border="0" cellpadding="0" cellspacing="0" style="table-layout: fixed;">
			<%-- <colgroup>
				<col width="5%"><col width="13%"><col width="40%"><col width="5%"><col width="5%"><col width="11%"><col width="11%"><col width="10%">
			</colgroup>
			<tr>
				<th><input type="checkbox" name="seqCheck" id="checkall" onclick="checkSeq(this.checked);" value=""></th>
				<th style="cursor:pointer" onclick="setOrder('MD_SITE_NAME');">출처<%=sOrderMark1%></th>
				<th style="cursor:pointer" onclick="setOrder('ID_TITLE');">제목<%=sOrderMark2%></th>
				<th> </th>
				<th> </th>
				<th style="cursor:pointer" onclick="setOrder('MD_SAME_CT');" title="<%=SiteGroups%>">유사<%=sOrderMark3%></th>
				<th style="cursor:pointer" onclick="setOrder('MD_DATE');">수집일시<%=sOrderMark4%></th>
				<th style="cursor:pointer" onclick="setOrder('M_NAME');">등록자<%=sOrderMark5%></th>
				<!-- <th>중요도</th> -->
			</tr> --%>
			<%-- <tr>
				<td><input name="tt" type="checkbox" value=""/></td>
				<td><span style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;" class="board01_category" title=""></span></td>
				<td style="text-align: left;"><div style="width:310px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;" class="board_01_tit_01" title="">
					<a href="javascript:PopIssueDataForm('','Y');"></a>
				</div></td>
				<td><%if(ipChk){%><a onClick="originalView('');" href="javascript:void(0);" ><img src="../../images/issue/icon00.png" id="img_future1" align="absmiddle" /></a><%}%></td>
				<td><a onClick="hrefPop('');" href="javascript:void(0);" ><img src="../../images/issue/ico_original.gif" align="absmiddle" /></a></td>
				<td title="">  </td>
				<td></td>
				<td></td>
			</tr> --%>
			<!-- <tr>
				<td class="same" colspan="10">
					<div id="SameList_" style="display:none;"></div>
				</td>
			</tr> -->
		</table>
		</td>
	</tr>
	<!-- 게시판 끝 -->
	<tr>
		<td style="height:40px;">
		<table border="0" cellpadding="0" cellspacing="0" style="display: <%=Jnone%>">
			<tr>
				<td style="display: <%=Jnone%>"><img src="../../images/issue/btn_allselect.gif" onclick="checkSeq($('#id_seqs_all'), 1);" style="cursor:pointer;"/></td>
				<td style="display: <%=Jnone%>">&nbsp;</td>
				<%-- <td style="display: <%=Jnone%>"><img src="../../images/issue/btn_mailsend.gif" onclick="goMailTo();" style="cursor:pointer;"/></td>
				<td style="display: <%=Jnone%>">&nbsp;</td> --%>
				<%-- <td style="display: <%=Jnone%>"><img src="../../images/issue/btn_quickexcelsave.gif" onclick="goExcelTo();" style="cursor:pointer;"/></td>
				<td style="display: <%=Jnone%>">&nbsp;</td> --%>
				<!-- 2016-04-12 일반 엑셀 및 다운 기능 업그레이드 추가 -->
				<td style="display: <%=Jnone%>"><img src="../../images/issue/btn_excelsave.gif" onclick="goExcelTo();" style="cursor:pointer;"/></td>
				<td style="display: <%=Jnone%>">&nbsp;</td>
				<!-- 2017-02-09 엑셀 다운 간소화 추가 -->
				<%-- <td style="display: <%=Jnone%>"><img src="../../images/issue/btn_excel_new.gif" onclick="PopExcelForm(2);" style="cursor:pointer;"/></td>
				<td style="display: <%=Jnone%>">&nbsp;</td> --%>
				
				<td style="display: <%=Jnone%>"><img src="../../images/issue/btn_del.gif" onclick="deleteIssueData();" style="cursor:pointer;"/></td>
				<td style="display: <%=Jnone%>">&nbsp;</td>
				<td style="display: <%=Jnone%>"><img src="../../images/search/btn_same.gif" onclick="samePackage();" style="cursor:pointer;"/></td>
				<td style="display: <%=Jnone%>">&nbsp;</td>
				<td style="display: <%=Jnone%>"><img src="../../images/issue/btn_multi.gif" onclick="PopIssueDataForm_multi();" style="cursor:pointer;"/></td>
				<!-- 2017-03-21 SK 담당자들 전용 엑셀 추가 (본문 절대 넣지 말것!) -->
				<td style="display: <%=Jnone%>">&nbsp;</td>
				<!-- <td><img src="../../images/issue/btn_excelsave.gif" onclick="goExcelTo_SK();" style="cursor:pointer;"/></td> -->
				<td>&nbsp;</td>
				<td width="100%" align="right" style="padding-right: 10px; padding-top: 15px"></td>
			</tr>
		</table>				
		</td>
	</tr>
	<!-- 페이징 -->
	<tr>
	<td>
	<table align="center" style="padding-top:10px;" border="0" cellpadding="0" cellspacing="1">
		<tr>
			<td>
				<table id="paging" border="0" cellpadding="0" cellspacing="2">
					<tr>
						<%-- <%=PageIndex.getPageIndex(nowpage, totalPage,"","" )%> --%>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	</td>
	</tr>
<!-- 페이징 -->
	</table>
	</td>
</tr>
</table>
<%-- 달력 테이블 --%>
<table width="162" border="0" cellspacing="0" cellpadding="0" id="calendar_conclass" style="position:absolute;display:none;">
	<tr>
		<td><img src="../../images/calendar/menu_bg_004.gif" width="162" height="2"></td>
	</tr>
	<tr>
		<td align="center" background="../../images/calendar/menu_bg_005.gif"><table width="148" border="0" cellspacing="0" cellpadding="0">
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
		<td><img src="../../images/calendar/menu_bg_006.gif" width="162" height="2"></td>
	</tr>
</table>
<iframe id="processFrm" name ="processFrm" width="0" height="0" style="display: none;" ></iframe>
<iframe id="if_samelist" name="if_samelist" width="0" height="0" src="about:blank" style="display: none;"></iframe>
</body>
</html>