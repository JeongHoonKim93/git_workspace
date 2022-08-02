<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ page import="risk.issue.IssueDataBean
                ,risk.issue.IssueMgr
                ,java.util.ArrayList
                ,risk.util.ParseRequest
                ,risk.issue.IssueCodeMgr
                ,risk.issue.IssueCodeBean
                ,risk.issue.IssueBean                
                ,risk.search.MetaMgr
                ,risk.search.userEnvMgr
                ,risk.search.userEnvInfo
                ,risk.util.StringUtil
                ,risk.util.DateUtil
                ,risk.util.PageIndex
                ,risk.util.ConfigUtil
                ,risk.dashboard.dashboardMgr
                ,risk.dashboard.dashboardSuperBean
                ,risk.dashboard.dashboardFunction
                ,risk.dashboard.dashboard_chartXml
                ,java.net.URLDecoder" %>
<%@include file="../inc/sessioncheck.jsp" %>
<%
	ParseRequest pr	= new ParseRequest(request);
	pr.printParams();	
	DateUtil du	= new DateUtil();
	StringUtil	su 	= new StringUtil();
	ConfigUtil cu = new ConfigUtil();
	IssueMgr issueMgr = new IssueMgr();
	MetaMgr smgr = new MetaMgr();
	ArrayList arrIcBean = null;
	ArrayList arrIdBean = null;
	ArrayList arrIdcBean =  null;
	
	ArrayList arrSiteGroup = null;
	
	dashboardMgr dMgr = new dashboardMgr();
	dashboardSuperBean.mainBean mBean = null;
	
	dashboard_chartXml xml = new dashboard_chartXml();
	dashboardFunction func = new dashboardFunction();
	
	String url = cu.getConfig("URL");
	String imgUrl = url+"images/mobile/";
	
	//핸드폰 그래프 플래쉬로 할지 HTML5로 할지 결정
	String ua=request.getHeader("User-Agent").toLowerCase();
	
	 
	int flush  = 0;
	if(ua.matches(".*android 2.*")){
		flush = 1;
	}
	
	
	/*
	String sDateFrom = pr.getString("sDateFrom", "");
	String sDateTo = pr.getString("sDateTo", "");
	
	if(sDateTo.equals("")){
		sDateTo = du.getCurrentDate("yyyy-MM-dd");
		sDateFrom = sDateTo;
	}
	String ir_ir_stime = pr.getString("ir_ir_stime","00:00:00");
	String ir_ir_etime = pr.getString("ir_ir_etime","23:59:59");
	*/
	
	
	/*시간셋팅****************************************************************/
	func.SearchDate(pr.getString("sDateTo",""));
	String sDateFrom = func.getSDate();
	String sDateTo = func.getEDate();
	String ir_stime = "00:00:00";
	String ir_etime = "23:59:59";	
	/*************************************************************************/
	
	
	String beforDateFrom = du.addDay_v2(sDateFrom, -1);
	String beforDateTo = du.addDay_v2(sDateTo, -1);
	String nextDateFrom = du.addDay_v2(sDateFrom, 1);
	String nextDateTo = du.addDay_v2(sDateTo, 1);
	
	//String chartBackImgPath = "../../images/mobile/dashboard/graph_bg.gif";
	String chartBackImgPath = "#8ADFF6";
	
	
	//전일대비 계산을 위한 날짜
	String pre_sDateFrom = du.addDay(sDateFrom, -1);
	String pre_sDateTo = du.addDay(sDateTo, -1);
	
	System.out.println("포스코  언론 우호도 (금일)");
	ArrayList arr_tendency = dMgr.getMainData1(sDateFrom, ir_stime, sDateTo, ir_etime, "1",sDateFrom,1);
	String typeCode = "6,1@4,1@4,2";
	String charXml_posco = xml.XmlPieTendency_2D(arr_tendency, "0", 50, typeCode,"public1",chartBackImgPath);
	
	
	//한줄로 정렬
	charXml_posco =  charXml_posco.replaceAll("\n","").replaceAll((new Character((char)13)).toString(),"").replaceAll((new Character((char)9)).toString(),"").replaceAll((new Character((char)11)).toString(),"");
	
	
	ArrayList pre_arr_tendency = dMgr.getMainData1(pre_sDateFrom, ir_stime, pre_sDateTo, ir_etime,"1","",1);
	int total = 0;
	int pre_total = 0;
	
	if(arr_tendency.size() > 0){
		for(int i = 0; i < arr_tendency.size(); i++){
			mBean = (dashboardSuperBean.mainBean)arr_tendency.get(i);
			total += Integer.parseInt(mBean.getPostive());
			total += Integer.parseInt(mBean.getNegative());
			total += Integer.parseInt(mBean.getNeutral());
		}
	}
	if(pre_arr_tendency.size() > 0){
		for(int i = 0; i < pre_arr_tendency.size(); i++){
			mBean = (dashboardSuperBean.mainBean)pre_arr_tendency.get(i);
			pre_total += Integer.parseInt(mBean.getPostive());
			pre_total += Integer.parseInt(mBean.getNegative());
			pre_total += Integer.parseInt(mBean.getNeutral());
		}
	}
	

	System.out.println("포스코  개인 우호도 (전일)");
	ArrayList arr_tendency1 = dMgr.getMainData1(sDateFrom, ir_stime, sDateTo, ir_etime, "2,3,7,4",sDateFrom,0);
	ArrayList pre_arr_tendency1 = dMgr.getMainData1(pre_sDateFrom, ir_stime, pre_sDateTo, ir_etime, "2,3,7,4","",0);
	String typeCode1 = "6,2@6,3@6,7@6,4@4,1@4,2";
	String charXml_posco1 = xml.XmlPieTendency_2D(arr_tendency1, "0", 50, typeCode1,"media1",chartBackImgPath);
	
	
	
	int total1 = 0;
	int pre_total1 = 0;
	
	if(arr_tendency1.size() > 0){
		for(int i = 0; i < arr_tendency1.size(); i++){
			mBean = (dashboardSuperBean.mainBean)arr_tendency1.get(i);
			total1 += Integer.parseInt(mBean.getPostive());
			total1 += Integer.parseInt(mBean.getNegative());
			total1 += Integer.parseInt(mBean.getNeutral());
		}
	}
	if(pre_arr_tendency1.size() > 0){
		for(int i = 0; i < pre_arr_tendency1.size(); i++){
			mBean = (dashboardSuperBean.mainBean)pre_arr_tendency1.get(i);
			pre_total1 += Integer.parseInt(mBean.getPostive());
			pre_total1 += Integer.parseInt(mBean.getNegative());
			pre_total1 += Integer.parseInt(mBean.getNeutral());
		}
	}
%>
<html>
<head>
<title>RIS-K Mobile</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, target-densitydpi=medium-dpi" />
<link rel="stylesheet" href="css/dash_basic.css" type="text/css">
<link rel="stylesheet" type="text/css" href="../../css/mobile/basic.css">
<style type="text/css">
<!--
.t_gray {
	font-family: Tahoma, "돋움", "돋움체";
	font-size: 11px;
	color: #9F9F9F;
}
.input {
	BORDER-RIGHT: #E1E2E1 1px solid; BORDER-TOP: #E1E2E1 1px solid; BORDER-LEFT: #E1E2E1 1px solid; BORDER-BOTTOM: #E1E2E1 1px solid; COLOR: #767676;  HEIGHT: 20px; FONT-SIZE: 12px; PADDING-TOP: 3px; BACKGROUND-COLOR: #ffffff
}
body {
	background-image: url(<%=imgUrl%>login_bg01.gif);
	background-color: #384b5a;
}

.select_he{height: 27px; width: 130px}

img{max-width:100%}
-->
</style>
<script src="<%=SS_URL%>js/jquery.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/ajax.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/popup.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/FusionCharts.js" type="text/javascript"></script>
<script>
	function setDate(sDate, eDate){
		var f = document.fSend;
		
		f.sDateFrom.value = sDate;
		f.sDateTo.value = eDate;
		f.target = '';
		f.action = 'mobile_dashboard.jsp';
		f.submit();
	}

	function goPcVersion(){
		document.location = '../main.jsp';		
	}

	function logOut(){
		document.location = '../logout.jsp?mType=m';
	}

	function todayKeyword(type){
		var f = document.fSend;
		f.todayType.value = type;
		var area = '';
		if(type == '1'){
			area = 'Area1';
		}else if(type == '2'){
			area = 'Area2';
		}
		ajax.post('todayKeyword.jsp', 'fSend', area);
	}

	function weeklyView(type){
		var f = document.fSend;
		f.todayType.value = type;
		var area = '';
		if(type == '1'){
			area = 'Area1';
		}else if(type == '2'){
			area = 'Area2';
		}
		ajax.post('weeklyView.jsp', 'fSend', area);
	}

	function getMenu(menu){
		var f = document.empty;
		var url;
		if(menu == 'search'){
			document.search.action = 'mobile_content_list.jsp';
			document.search.target = '';
			document.search.submit();
			return;
		}else if(menu == 'issue'){
			url = 'mobile_issue_list.jsp';
		}else if(menu == 'report'){
			url = 'mobile_report_list.jsp';
		}else if(menu == 'dashboard'){
			
			url = 'mobile_dashboard.jsp';
			
		}
		f.action = url;
		f.target = '';
		f.submit();
	}

	function popupList(typeCode, menuType){

		var f = document.fSend;
		f.typeCode.value = typeCode;
		f.menuType.value = menuType;
		ajax.post('aj_mobile_issue_list.jsp','fSend','dashboard_area');
	}

	function popupList_today(todaykey, type){

		var f = document.fSend;
		f.todayKey.value = todaykey;
		if(type == '1'){
			f.typeCode.value = "6,1@4,1@4,2";
			f.menuType.value = 'public1';
		}else if(type == '2'){
			f.typeCode.value = "6,2@6,3@6,7@6,4@4,1@4,2";
			f.menuType.value = 'media1';
		}
		ajax.post('aj_mobile_issue_list.jsp','fSend','dashboard_area');
		//popup.openByPost('fSend','pop_issue_data_list.jsp',817,420,false,true,false,'send_issue');
	}


	//XML데이터
	var charXml_posco = "<%=charXml_posco%>";
	var charXml_posco1 = "<%=charXml_posco1%>";

	
	
</script>
</head>
<body onload="window.scrollTo(0,1);" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">


<form name="search" id="search" action="mobile_content_list.jsp" method="post">
	<input type="hidden" name="nowpage" value="1">
	<input type="hidden" name="xp" value="">
	<input type="hidden" name="yp" value="">
	<input type="hidden" name="zp" value="">
	<input type="hidden" name="sg_seq" value="">
	<input type="hidden" name="kXp" value="">
</form>

<form name="empty" id="empty" method="post"></form>



<form name="fSend" id="fSend" method="post">
<input type="hidden" name="sDateFrom" id="sDateFrom" value="<%=sDateFrom%>">
<input type="hidden" name="sDateTo" id="sDateTo" value="<%=sDateTo%>">
<input type="hidden" name="todayType" id="todayType">
<input type="hidden" name="flush" id="flush" value="<%=flush%>">
<input type="hidden" name="typeCode">
<input type="hidden" name="menuType">
<input type="hidden" name="todayKey">

</form>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td background="../../images/mobile/bg.gif"><img src="../../images/mobile/list_top.gif"></td>
	</tr>
	<tr>
		<td height="35" background="../../images/mobile/menu_bg_03.gif"><table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="25%" align="center" background="../../images/mobile/menu_bg_01.gif" class="menu_blueTEXTover" style="cursor:pointer" onclick="getMenu('dashboard')"><strong><img src="../../images/mobile/menu_04_on.gif" width="19" height="18" align="absmiddle"> 대시보드</strong></td>
				<td width="25%" align="center" background="../../images/mobile/menu_bg_02.gif" class="menu_blueTEXTover" style="background-repeat:no-repeat; cursor: pointer;" onclick="getMenu('search')"><img src="../../images/mobile/menu_01_off.gif" width="22" height="22" align="absmiddle" ><strong> 정보검색</strong></td>
        		<td width="25%" align="center" background="../../images/mobile/menu_bg_02.gif" class="menu_blueTEXTover" style="background-repeat:no-repeat; cursor: pointer;" onclick="getMenu('issue')"><img src="../../images/mobile/menu_02_off.gif" width="19" height="18" align="absmiddle" ><strong> 이슈관리</strong></td>
        		<td width="25%" align="center" background="../../images/mobile/menu_bg_02.gif" class="menu_blueTEXTover" style="background-repeat:no-repeat; cursor: pointer;" onclick="getMenu('report')"><img src="../../images/mobile/menu_03_off.gif" width="18" height="20" align="absmiddle" > <strong>보고서</strong></td>
			</tr>
		</table></td>
	</tr>
	
	<tr>
    <td height="31" bgcolor="#B8B3AA" style="padding:10px 5px 0px 5px"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="24"><img src="../../images/mobile/bg_001.gif" width="24" height="23"></td>
        <td background="../../images/mobile/bg_002.gif" class="textw"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="85" class="textw"><strong>Data 기준시간</strong></td>
            <td width="18"><img src="../../images/mobile/bg_004.gif" width="18" height="23"></td>
            <td class="textw"><%=sDateTo+" "+ir_stime+"~"+ir_etime%></td>
          </tr>
        </table></td>
        <td width="6"><img src="../../images/mobile/bg_003.gif" width="6" height="23"></td>
      </tr>
    </table></td>
  </tr>
  
	
	
	
	<tr>
	<td id="dashboard_area">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">

	<tr>
		<td height="31" bgcolor="#B8B3AA" style="padding:10px 5px 0px 5px" id="Area1">
		<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td align="right"><table width="100%" border="0" cellspacing="0" cellpadding="0">
	          <tr>
	            <td width="120"><img src="../../images/mobile/dashboard_title_01.gif" width="120" height="36"></td>
	            <td align="right" valign="top" background="../../images/mobile/dashboard_title_bg_01.gif" style="padding:1px 0px 0px 0px"><img src="../../images/mobile/today_btn.gif" hspace="3" onclick="todayKeyword('1')" style="cursor:pointer"><img src="../../images/mobile/weekly_btn.gif" onclick="weeklyView('1')" style="cursor:pointer">
	            </td>
	            <td width="12"><img src="../../images/mobile/dashboard_title_bg_001.gif" width="12" height="36"></td>
	          </tr>
	        </table></td>
	        </tr>
	        
	        
	        <tr>
		        <td align="right"><table width="100%" border="0" cellspacing="0" cellpadding="0">
		          <tr>
		            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
		              <tr>
		                <td width="2" bgcolor="#4C3F29"></td>
		                <td width="100%" bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
		                  <tr>
		                    <td width="2" bgcolor="#4C3F29"></td>
		                    <td width="3" bgcolor="#FFFFFF"></td>
		                    <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
		                      <tr>
		                        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
		                          <tr>
		                            <td width="12"><img src="../../images/mobile/bg_14.gif" width="12" height="133"></td>
		                            <td bgcolor="#8ADFF6"><table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                <%
                                        		String poscoImg = "";
                                             	
                                             		String[] arrTendency = new String[3];
                                             		for(int i =0; i < arrTendency.length; i++){
                                             			arrTendency[i] = "0";
                                             		}
                                             		
                                             		
                                        		if(arr_tendency.size() > 0){
                                        			mBean = (dashboardSuperBean.mainBean)arr_tendency.get(0);
                                        			
                                        			poscoImg = func.weatherImg(mBean.getPercent_int(), dashboardFunction.E_ImgType.Mobile); 
                                        			
                                        			arrTendency[0] = mBean.getPostive();
                                        			arrTendency[1] = mBean.getNegative();
                                        			arrTendency[2] = mBean.getNeutral();
                                        			
                                        		}else{
                                        			poscoImg = func.NoneImg(dashboardFunction.E_ImgType.Mobile);
                                        		}
                                        	%>
		                                
		                                
		                                  <td width="50%" align="center"><img src="../../images/mobile/<%=poscoImg%>" onclick="popupList('<%=typeCode%>','public1')"></td>
		                                  <td width="50%" align="center">
<!--		                                  	<img src="../../images/mobile/n_graph_01_01.gif" width="113" height="117">-->
		                                  	<div id="chart1">
															<script type="text/javascript">
															if(Number(document.fSend.flush.value) == 0){
																FusionCharts.setCurrentRenderer('javascript');	
															}
															   var chart = new FusionCharts("../../fusionChart/swf/Doughnut2D.swf", "ChartId", "113", "117", "0", "0");
															   chart.setDataXML(charXml_posco);
															   chart.render("chart1");
															</script>
														</div>
		                                  </td>
		                                </tr>
		                            </table></td>
		                            <td width="12"><img src="../../images/mobile/bg_15.gif" width="12" height="133"></td>
		                          </tr>
		                        </table></td>
		                      </tr>
		                      <tr>
		                        <td height="5"></td>
		                      </tr>
		                      <tr>
		                        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
		                          <tr>
		                            <td width="33%" style="padding:0px 3px 0px 0px; cursor: pointer;" onclick="popupList('<%=typeCode + (typeCode.equals("") ? "9,1" : "@9,1")%>','public1')"><table width="100%" border="0" cellspacing="0" cellpadding="0">
		                              <tr>
		                                <td width="7"><img src="../../images/mobile/blue_bg_01.gif" width="7" height="85"></td>
		                                <td valign="top" background="../../images/mobile/blue_bg_02.gif"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		                                  <tr>
		                                    <td height="10"></td>
		                                  </tr>
		                                  <tr>
		                                    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                      <tr>
		                                        <td width="58" style="padding:0px 0px 0px 3px"><img src="../../images/mobile/blue_txt_01.gif" width="53" height="18"></td>
		                                        <td class="text_w1" style="padding:3px 3px 0px 2px"><strong><%=su.digitFormat(arrTendency[0])%></strong></td>
		                                      </tr>
		                                    </table></td>
		                                  </tr>
		                                  <tr>
		                                    <td height="15"></td>
		                                  </tr>
		                                  <tr>
		                                    <td class="textw" style="padding:0px 3px 0px 3px">CJ 관련 우호적인 사실/견해 보도</td>
		                                  </tr>
		                                </table></td>
		                                <td width="7"><img src="../../images/mobile/blue_bg_03.gif" width="7" height="85"></td>
		                              </tr>
		                            </table></td>
		                            <td width="33%" style="padding:0px 3px 0px 0px; cursor: pointer;" onclick="popupList('<%=typeCode + (typeCode.equals("") ? "9,2" : "@9,2")%>','public1')"><table width="100%" border="0" cellspacing="0" cellpadding="0">
		                              <tr>
		                                <td width="7"><img src="../../images/mobile/red_bg_01.gif" width="7" height="85"></td>
		                                <td valign="top" background="../../images/mobile/red_bg_02.gif"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		                                    <tr>
		                                      <td height="10"></td>
		                                    </tr>
		                                    <tr>
		                                      <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                          <tr>
		                                            <td width="59" style="padding:0px 0px 0px 3px"><img src="../../images/mobile/red_txt_01.gif" width="54" height="18"></td>
		                                            <td class="text_w1" style="padding:3px 3px 0px 2px"><strong><%=su.digitFormat(arrTendency[1])%></strong></td>
		                                          </tr>
		                                      </table></td>
		                                    </tr>
		                                    <tr>
		                                      <td height="15"></td>
		                                    </tr>
		                                    <tr>
		                                      <td class="textw" style="padding:0px 3px 0px 3px">회사 관련 악의적인 보도/오보/루머</td>
		                                    </tr>
		                                </table></td>
		                                <td width="7"><img src="../../images/mobile/red_bg_03.gif" width="7" height="85"></td>
		                              </tr>
		                            </table></td>
		                            <td width="33%" style="cursor: pointer;" onclick="popupList('<%=typeCode + (typeCode.equals("") ? "9,3" : "@9,3")%>','public1')"><table width="100%" border="0" cellspacing="0" cellpadding="0">
		                              <tr>
		                                <td width="7"><img src="../../images/mobile/green_bg_01.gif" width="7" height="85"></td>
		                                <td valign="top" background="../../images/mobile/green_bg_02.gif"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		                                    <tr>
		                                      <td height="10"></td>
		                                    </tr>
		                                    <tr>
		                                      <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                          <tr>
		                                            <td width="60" style="padding:0px 0px 0px 3px"><img src="../../images/mobile/green_txt_01.gif" width="55" height="18"></td>
		                                            <td class="text_w1" style="padding:3px 3px 0px 2px"><strong><%=su.digitFormat(arrTendency[2])%></strong></td>
		                                          </tr>
		                                      </table></td>
		                                    </tr>
		                                    <tr>
		                                      <td height="15"></td>
		                                    </tr>
		                                    <tr>
		                                      <td class="textw" style="padding:0px 3px 0px 3px">긍정/부정에 해당 되지 않는 단순 사실 보도</td>
		                                    </tr>
		                                </table></td>
		                                <td width="7"><img src="../../images/mobile/green_bg_03.gif" width="7" height="85"></td>
		                              </tr>
		                            </table></td>
		                            </tr>
		                        </table></td>
		                      </tr>
		                    </table></td>
		                    <td width="3" bgcolor="#FFFFFF"></td>
		                    <td width="2" bgcolor="#4C3F29"></td>
		                  </tr>
		                </table></td>
		                <td width="2" bgcolor="#4C3F29"></td>
		              </tr>
		              
		            </table></td>
		            </tr>
		        </table></td>
		      </tr>
		      
		      <!--완전끝~  -->


	      <tr>
        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
             <tr>
               <td width="9" valign="top"><img src="../../images/mobile/dashboard_title_bg_25.gif" width="9" height="9"></td>
               <td valign="top"><img src="../../images/mobile/dashboard_title_bg_26.gif" width="100%" height="9"></td>
               <td width="9" valign="top"><img src="../../images/mobile/dashboard_title_bg_27.gif" width="9" height="9"></td>
             </tr>
        </table></td>
      </tr>
	      
	    </table>
		
		
		
		
		
		
		
		
		
		</td>
	</tr>
	<tr style="display: none;">
		<td height="31" bgcolor="#B8B3AA" style="padding:4px 5px 0px 5px"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="right"><table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="120"><img src="../../images/mobile/dashboard_title_02.gif" width="120" height="32"></td>
						<td align="right" valign="top" background="../../images/mobile/dashboard_title_bg_08.gif">&nbsp;</td>
						<td width="9"><img src="../../images/mobile/dashboard_title_bg_09.gif" width="9" height="32"></td>
					</tr>
				</table></td>
			</tr>
			<tr>
				<td align="right"><table width="100%" border="" cellspacing="0" cellpadding="0">
					<tr>
						<td width="9"><img src="../../images/mobile/dashboard_title_bg_10.gif" width="9" height="42"></td>
						<td align="center" background="../../images/mobile/dashboard_title_bg_14.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="50%" style="padding:0px 4px 0px 0px"><table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="8"><img src="../../images/mobile/dashboard_title_bg_11.gif" width="8" height="42"></td>
										<td background="../../images/mobile/dashboard_title_bg_12.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td width="100%" align="left" valign="top" style="padding:0px 0px 0px 0px"><img src="../../images/mobile/dot.gif" width="11" height="8"><span class="text_w1"><strong>금일 총수량 </strong></span><span class="BIG_title04"><strong>&nbsp;<%=total%></strong></span><span class="text_w1"><strong>건</strong></span></td>
											</tr>
										</table></td>
										<td width="7"><img src="../../images/mobile/dashboard_title_bg_13.gif" width="7" height="42"></td>
									</tr>
								</table></td>
								<td width="49%"><table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="14"><img src="../../images/mobile/dashboard_title_bg_15.gif" width="14" height="42"></td>
										<td background="../../images/mobile/dashboard_title_bg_12.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td width="100%" align="left" valign="top" style="padding:0px 0px 0px 0px"><img src="../../images/mobile/dot.gif" width="11" height="8"><span class="text_w1"><strong>전일 대비 </strong></span><strong>&nbsp;</strong>
												<%
												int tempCnt = total-pre_total;
												if(tempCnt < 0){
													tempCnt = tempCnt * -1;
												%>
												<img src="../../images/mobile/blue_down.gif" width="16" height="8">
												<%
												}else if(tempCnt > 0){
												%>
												<img src="../../images/mobile/red_up.gif" width="16" height="8">
												<%
												}else{
													out.print("-");
												}
												%>
												<span class="BIG_title05"><strong><%=tempCnt%></strong></span><span class="text_w1"><strong>건</strong></span></td>
											</tr>
										</table></td>
										<td width="7"><img src="../../images/mobile/dashboard_title_bg_16.gif" width="7" height="42"></td>
									</tr>
								</table></td>
							</tr>
						</table></td>
						<td width="9"><img src="../../images/mobile/dashboard_title_bg_17.gif" width="9" height="42"></td>
					</tr>
				</table></td>
			</tr>
		</table></td>
	</tr>
	<tr>
		<td bgcolor="#B8B3AA" style="padding:25px 5px 0px 5px" id="Area2">
		
		
		<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td align="right"><table width="100%" border="0" cellspacing="0" cellpadding="0">
	          <tr>
	            <td width="120"><img src="../../images/mobile/dashboard_title_001.gif" ></td>
	            <td align="right" valign="top" background="../../images/mobile/dashboard_title_bg_01.gif" style="padding:1px 0px 0px 0px"><img src="../../images/mobile/today_btn.gif" hspace="3" onclick="todayKeyword('2')" style="cursor:pointer"><img src="../../images/mobile/weekly_btn.gif" onclick="weeklyView('2')" style="cursor:pointer">
	            </td>
	            <td width="12"><img src="../../images/mobile/dashboard_title_bg_001.gif" width="12" height="36"></td>
	          </tr>
	        </table></td>
	        </tr>
	        
	        
	        
	        <tr>
		        <td align="right"><table width="100%" border="0" cellspacing="0" cellpadding="0">
		          <tr>
		            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
		              <tr>
		                <td width="2" bgcolor="#4C3F29"></td>
		                <td width="100%" bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
		                  <tr>
		                    <td width="2" bgcolor="#4C3F29"></td>
		                    <td width="3" bgcolor="#FFFFFF"></td>
		                    <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
		                      <tr>
		                        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
		                          <tr>
		                            <td width="12"><img src="../../images/mobile/bg_14.gif" width="12" height="133"></td>
		                            <td bgcolor="#8ADFF6"><table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                <%
                                        		poscoImg = "";
                                             		arrTendency = new String[3];
                                             		for(int i =0; i < arrTendency.length; i++){
                                             			arrTendency[i] = "0";
                                             		}
                                             		
                                             		
                                        		if(arr_tendency1.size() > 0){
                                        			mBean = (dashboardSuperBean.mainBean)arr_tendency1.get(0);
                                        			
                                        			poscoImg = func.weatherImg(mBean.getPercent_int(), dashboardFunction.E_ImgType.Mobile); 
                                        			
                                        			arrTendency[0] = mBean.getPostive();
                                        			arrTendency[1] = mBean.getNegative();
                                        			arrTendency[2] = mBean.getNeutral();
                                        			
                                        		}else{
                                        			poscoImg = func.NoneImg(dashboardFunction.E_ImgType.Mobile);
                                        		}
                                        	%>
		                                
		                                
		                                  <td width="50%" align="center"><img src="../../images/mobile/<%=poscoImg%>" onclick="popupList('<%=typeCode1%>','media1')"></td>
		                                  <td width="50%" align="center">
<!--		                                  	<img src="../../images/mobile/n_graph_01_01.gif" width="113" height="117">-->
		                                  	<div id="chart2">
															<script type="text/javascript">
															if(Number(document.fSend.flush.value) == 0){
																FusionCharts.setCurrentRenderer('javascript');	
															}
															   var chart = new FusionCharts("../../fusionChart/swf/Doughnut2D.swf", "ChartId", "113", "117", "0", "0");
															   chart.setDataXML(charXml_posco1);
															   chart.render("chart2");
															</script>
														</div>
		                                  </td>
		                                </tr>
		                            </table></td>
		                            <td width="12"><img src="../../images/mobile/bg_15.gif" width="12" height="133"></td>
		                          </tr>
		                        </table></td>
		                      </tr>
		                      <tr>
		                        <td height="5"></td>
		                      </tr>
		                      <tr>
		                        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
		                          <tr>
		                            <td width="33%" style="padding:0px 3px 0px 0px; cursor: pointer;" onclick="popupList('<%=typeCode1 + (typeCode1.equals("") ? "9,1" : "@9,1")%>','media1')"><table width="100%" border="0" cellspacing="0" cellpadding="0">
		                              <tr>
		                                <td width="7"><img src="../../images/mobile/blue_bg_01.gif" width="7" height="85"></td>
		                                <td valign="top" background="../../images/mobile/blue_bg_02.gif"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		                                  <tr>
		                                    <td height="10"></td>
		                                  </tr>
		                                  <tr>
		                                    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                      <tr>
		                                        <td width="58" style="padding:0px 0px 0px 3px"><img src="../../images/mobile/blue_txt_01.gif" width="53" height="18"></td>
		                                        <td class="text_w1" style="padding:3px 3px 0px 2px"><strong><%=su.digitFormat(arrTendency[0])%></strong></td>
		                                      </tr>
		                                    </table></td>
		                                  </tr>
		                                  <tr>
		                                    <td height="15"></td>
		                                  </tr>
		                                  <tr>
		                                    <td class="textw" style="padding:0px 3px 0px 3px">CJ 관련 우호적인 사실/견해 보도</td>
		                                  </tr>
		                                </table></td>
		                                <td width="7"><img src="../../images/mobile/blue_bg_03.gif" width="7" height="85"></td>
		                              </tr>
		                            </table></td>
		                            <td width="33%" style="padding:0px 3px 0px 0px; cursor: pointer;" onclick="popupList('<%=typeCode1 + (typeCode1.equals("") ? "9,2" : "@9,2")%>','media1')"><table width="100%" border="0" cellspacing="0" cellpadding="0">
		                              <tr>
		                                <td width="7"><img src="../../images/mobile/red_bg_01.gif" width="7" height="85"></td>
		                                <td valign="top" background="../../images/mobile/red_bg_02.gif"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		                                    <tr>
		                                      <td height="10"></td>
		                                    </tr>
		                                    <tr>
		                                      <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                          <tr>
		                                            <td width="59" style="padding:0px 0px 0px 3px"><img src="../../images/mobile/red_txt_01.gif" width="54" height="18"></td>
		                                            <td class="text_w1" style="padding:3px 3px 0px 2px"><strong><%=su.digitFormat(arrTendency[1])%></strong></td>
		                                          </tr>
		                                      </table></td>
		                                    </tr>
		                                    <tr>
		                                      <td height="15"></td>
		                                    </tr>
		                                    <tr>
		                                      <td class="textw" style="padding:0px 3px 0px 3px">회사 관련 악의적인 보도/오보/루머</td>
		                                    </tr>
		                                </table></td>
		                                <td width="7"><img src="../../images/mobile/red_bg_03.gif" width="7" height="85"></td>
		                              </tr>
		                            </table></td>
		                            <td width="33%" style="cursor: pointer;" onclick="popupList('<%=typeCode1 + (typeCode1.equals("") ? "9,3" : "@9,3")%>','media1')"><table width="100%" border="0" cellspacing="0" cellpadding="0">
		                              <tr>
		                                <td width="7"><img src="../../images/mobile/green_bg_01.gif" width="7" height="85"></td>
		                                <td valign="top" background="../../images/mobile/green_bg_02.gif"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		                                    <tr>
		                                      <td height="10"></td>
		                                    </tr>
		                                    <tr>
		                                      <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                          <tr>
		                                            <td width="60" style="padding:0px 0px 0px 3px"><img src="../../images/mobile/green_txt_01.gif" width="55" height="18"></td>
		                                            <td class="text_w1" style="padding:3px 3px 0px 2px"><strong><%=su.digitFormat(arrTendency[2])%></strong></td>
		                                          </tr>
		                                      </table></td>
		                                    </tr>
		                                    <tr>
		                                      <td height="15"></td>
		                                    </tr>
		                                    <tr>
		                                      <td class="textw" style="padding:0px 3px 0px 3px">긍정/부정에 해당 되지 않는 단순 사실 보도</td>
		                                    </tr>
		                                </table></td>
		                                <td width="7"><img src="../../images/mobile/green_bg_03.gif" width="7" height="85"></td>
		                              </tr>
		                            </table></td>
		                            </tr>
		                        </table></td>
		                      </tr>
		                    </table></td>
		                    <td width="3" bgcolor="#FFFFFF"></td>
		                    <td width="2" bgcolor="#4C3F29"></td>
		                  </tr>
		                </table></td>
		                <td width="2" bgcolor="#4C3F29"></td>
		              </tr>
		              
		            </table></td>
		            </tr>
		        </table></td>
		      </tr>
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	      <tr>
        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
             <tr>
               <td width="9" valign="top"><img src="../../images/mobile/dashboard_title_bg_25.gif" width="9" height="9"></td>
               <td valign="top"><img src="../../images/mobile/dashboard_title_bg_26.gif" width="100%" height="9"></td>
               <td width="9" valign="top"><img src="../../images/mobile/dashboard_title_bg_27.gif" width="9" height="9"></td>
             </tr>
        </table></td>
      </tr>
	    </table>
		
		</td>
	</tr>
	<tr style="display: none;">
		<td height="31" bgcolor="#B8B3AA" style="padding:4px 5px 0px 5px"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="right"><table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="120"><img src="../../images/mobile/dashboard_title_04.gif" width="120" height="32"></td>
						<td align="right" valign="top" background="../../images/mobile/dashboard_title_bg_08.gif">&nbsp;</td>
						<td width="9"><img src="../../images/mobile/dashboard_title_bg_09.gif" width="9" height="32"></td>
					</tr>
				</table></td>
			</tr>
			<tr>
				<td align="right"><table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="9"><img src="../../images/mobile/dashboard_title_bg_10.gif" width="9" height="42"></td>
						<td align="center" background="../../images/mobile/dashboard_title_bg_14.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="50%" style="padding:0px 4px 0px 0px"><table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="8"><img src="../../images/mobile/dashboard_title_bg_11.gif" width="8" height="42"></td>
										<td background="../../images/mobile/dashboard_title_bg_12.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td width="100%" align="left" valign="top" style="padding:0px 0px 0px 0px"><img src="../../images/mobile/dot.gif" width="11" height="8"><span class="text_w1"><strong>금일 총수량 </strong></span><span class="BIG_title04"><strong>&nbsp;<%=total1%></strong></span><span class="text_w1"><strong>건</strong></span></td>
											</tr>
										</table></td>
										<td width="7"><img src="../../images/mobile/dashboard_title_bg_13.gif" width="7" height="42"></td>
									</tr>
								</table></td>
								<td width="49%"><table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="14"><img src="../../images/mobile/dashboard_title_bg_15.gif" width="14" height="42"></td>
										<td background="../../images/mobile/dashboard_title_bg_12.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td width="100%" align="left" valign="top"  style="padding:0px 0px 0px 0px"><img src="../../images/mobile/dot.gif" width="11" height="8"><span class="text_w1"><strong>전일 대비 </strong></span>&nbsp;
												<%
												int tempCnt1 = total1-pre_total1;
												if(tempCnt1 < 0){
													tempCnt1 = tempCnt1 * -1;
												%>
												<img src="../../images/mobile/blue_down.gif" width="16" height="8">
												<%
												}else if(tempCnt1 > 0){
												%>
												<img src="../../images/mobile/red_up.gif" width="16" height="8">
												<%
												}else{
													out.print("-");
												}
												%>
												<span class="BIG_title05"><strong><%=tempCnt1%></strong></span><span class="text_w1"><strong>건</strong></span></td>
											</tr>
										</table></td>
										<td width="7"><img src="../../images/mobile/dashboard_title_bg_16.gif" width="7" height="42"></td>
									</tr>
								</table></td>
							</tr>
						</table></td>
						<td width="9"><img src="../../images/mobile/dashboard_title_bg_17.gif" width="9" height="42"></td>
					</tr>
				</table></td>
			</tr>
		</table></td>
	</tr>
	<tr>
		<td bgcolor="#B8B3AA">&nbsp;</td>
	</tr>
	
	
	
	</table>
	</td>
	</tr>
	
	
	
	
	
	
	
	
	
	
	<tr>
		<td height="2" background="../../images/mobile/list_line01.gif" ></td>
	</tr>
	<tr>
		<td height="33" background="../../images/mobile/list_bg02.gif"><table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="33%" align="center" class="menu_blueOver" onclick="setDate('<%=beforDateFrom %>','<%=beforDateTo  %>')" style="cursor: pointer;"><%=du.getDate(beforDateTo,"M월 dd일(E)")%></td>
				<td width="33%" align="center" background="../../images/mobile/list_bar02.gif" class="menu_blueTEXTover" style="background-repeat:no-repeat; cursor: pointer;" onclick="setDate('<%=sDateFrom %>','<%=sDateTo  %>')"><%=du.getDate(sDateTo,"M월 dd일(E)")%></td>
				<td width="33%" align="center" background="../../images/mobile/list_bar02.gif" class="menu_blueTEXTover" style="background-repeat:no-repeat; cursor: pointer;" onclick="setDate('<%=nextDateFrom  %>','<%=nextDateTo   %>')"><%=du.getDate(nextDateTo,"M월 dd일(E)")%></td>
			</tr>
		</table></td>
	</tr>
	<tr>
		<td height="2" background="../../images/mobile/list_line03.gif"></td>
	</tr>
	<tr>
		<td height="38" background="../../images/mobile/list_bg03.gif"><table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="50%" align="center" class="textwhite" onclick="goPcVersion();" style="cursor: pointer;"><strong>PC버전</strong></td>
				<td width="50%" align="center" background="../../images/mobile/list_bar03.gif" class="textwhite" onclick="logOut();" style="background-repeat:no-repeat; font-weight: bold;cursor:pointer">로그아웃</td>
			</tr>
		</table></td>
	</tr>
</table>
</body>
</html>