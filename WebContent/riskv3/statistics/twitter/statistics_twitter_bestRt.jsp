<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="../../inc/sessioncheck.jsp" %>    
<%@ page import="	java.util.ArrayList
					,java.awt.Color
					,java.awt.*
					,risk.util.ParseRequest
					,risk.util.ConfigUtil
					,risk.util.StringUtil
					,risk.util.DateUtil
					, risk.statistics.StatisticsTwitterMgr
					, risk.statistics.StatisticsTwitterSuperBean
					, risk.statistics.StatisticsTwitterChartXml
					, risk.issue.IssueCodeMgr
                	, risk.issue.IssueCodeBean
                	, java.net.URLDecoder
					" %>

<%
	
	DateUtil   du = new DateUtil();
	ParseRequest  pr = new ParseRequest(request);	
	StringUtil su = new StringUtil();
	ConfigUtil cu = new ConfigUtil();
	StatisticsTwitterMgr sMgr = new StatisticsTwitterMgr();
	StatisticsTwitterChartXml cXml = new StatisticsTwitterChartXml();
	
	String sDateFrom ="";
	String sDateTo ="";
	String stime = pr.getString("stime","0");
	String etime = pr.getString("etime","24");
	
	
	
	
	
	String setStime= stime.equals("0") ? "00:00:00" : stime + ":00:00";
	String setEtime= etime.equals("24") ? "23:59:59" : etime + ":00:00";
	
	//날짜 셋팅 후 최소,최대  문서 값을 가져온다.
	sDateFrom = pr.getString("sDateFrom","");
	sDateTo = pr.getString("sDateTo","");
	if(sDateFrom.equals("")){
		sDateTo = du.getCurrentDate("yyyy-MM-dd");
		sDateFrom = du.addDay(sDateTo,-6);
	}
	
	String typeCode = pr.getString("typeCode","");
	String searchKey = URLDecoder.decode(pr.getString("encodingSearchKey",""),"UTF-8");
	
	String first = du.getDate(sDateFrom,"yyyyMMdd");
	String last = du.getDate(sDateTo,"yyyyMMdd");
	int diffDay = (int)du.DateDiff("yyyyMMdd",last,first);
	
	
	
	//일자 마스터 가져오기~~
	String[] weekMaster = new String[diffDay +1];
	for(int i = 0; i < weekMaster.length; i++){
		weekMaster[i] = (du.addDay_v2(sDateTo, -((weekMaster.length - 1) - i))).replaceAll("-","");	
	}
	
	
	
	String chart1 = "";
	String chart2 = "";
	
	//트위터 리스트 가져오기~ 
	ArrayList arrData = null;
	
	ArrayList arrData_user = null;
	ArrayList arrData_date = null;
	
	
	arrData = sMgr.getBestRt(sDateFrom,sDateTo,setStime,setEtime, typeCode, searchKey);
	chart1 = cXml.XmlData_1(arrData);
	
	if(arrData.size() > 0){
		arrData_date = sMgr.getBestRt_date(arrData, sDateFrom,sDateTo,setStime,setEtime, typeCode, searchKey);
		chart2 = cXml.XmlData_2(arrData_date, weekMaster);
	}
	
	//IssueCodeMgr 인스턴스 생성
	
	
	IssueCodeMgr icMgr = IssueCodeMgr.getInstance();
	IssueCodeBean icBean = null;	
	icMgr.init(0);		
	ArrayList arrIcBean = null;
	ArrayList arrIdBean = null;
	ArrayList arrIdcBean =  null;
	
	
	
	//그래프 html5로 변환
	String ua=request.getHeader("User-Agent").toLowerCase();
	String script = "";
	if(ua.indexOf("compatible") > 0 || /*윈도우*/
       ua.indexOf("firefox") > 0    || /*파이어폭스*/
       (ua.indexOf("windows nt") > 0 && ua.indexOf("chrome") > 0)){/*크롬*/
		script = "";
	}else{
		script = "FusionCharts.setCurrentRenderer('javascript');";
	}
	
%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!--<link rel="stylesheet" href="../css/basic.css" type="text/css">-->
<link rel="stylesheet" href="../../../css/base.css" type="text/css">
<link rel="stylesheet" href=../css/calendar.css" type="text/css">
<style>
iframe.hide { border:0px solid red; position:absolute; top:0px; left:0px; z-index:-99; width:148px; height:150px; filter: alpha(opacity=0);}
</style>
<script src="<%=SS_URL%>js/Calendar.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/jquery.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/ajax.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/common.js"  type="text/javascript"></script>
<script src="<%=SS_URL%>js/popup.js" type="text/javascript"></script>
<%if(script.equals("")){%>
	<script src="<%=SS_URL%>js/FusionCharts.js" type="text/javascript"></script>
<%	}else{%>
	<script src="<%=SS_URL%>js_trial/FusionCharts.js" type="text/javascript"></script>
<%	}%>
<script language="JavaScript" type="text/JavaScript">

	<%=script%>

	sCurrDate = '<%=du.getCurrentDate("yyyy-MM-dd")%>';
	sDateFrom = '<%=sDateFrom%>';
	sDateTo = '<%=sDateTo%>';

	function setDateTerm( day ) {
       var f = document.SSend;
       f.sDateFrom.value   = AddDate( day );
       f.sDateTo.value     = sCurrDate;

	}

	function java_all_trim(a) {
        for (; a.indexOf(" ") != -1 ;) {
          a = a.replace(" ","");
        }
        return a;
    }

	function AddDate( day ) {

        var newdate = new Date();
        sdate = newdate.getTime();

        edate = sdate - ( day * 24 * 60 * 60 * 1000);
        newdate.setTime(edate);

        last_ndate = newdate.toLocaleString();

        last_date = java_all_trim(last_ndate);
        last_year = last_date.substr(0,4);
        last_month = last_date.substr(5,2);
        last_mon = last_month.replace("월","");

        if(last_mon < 10) {

            last_m = 0+last_mon;
            last_day = last_date.substr(7,2);
            last_da = last_day.replace("일","");

            if(last_da < 10) {
                last_d = 0+last_da;
            }else{
                last_d = last_da;
            }

        }else{
            last_m = last_mon;

            last_day = last_date.substr(8,2);
            last_da = last_day.replace("일","");

            if(last_da < 10) {
                last_d = 0+last_da;
            }else{
                last_d = last_da;
            }

        }

        last_time = last_year + '-' + last_m + '-' + last_d;

        return last_time;
    }


	function search(){
		var f = document.SSend;
	
		settingTypeCode();
		f.encodingSearchKey.value = encodeURIComponent(f.searchKey.value);
		
		f.target ='';
		f.action ='statistics_twitter_bestRt.jsp';
		f.submit();
	}

	//타입코드 셋팅
	function settingTypeCode()
	{
		var form = document.SSend;

		form.typeCode.value = '';
		//10,4,9,16,12,8,7,15

		for(var i=0;i<form.typeCode10.length;i++){
			if(form.typeCode10[i].selected)
			{
				if(form.typeCode10[i].value!=''){
					form.typeCode.value += form.typeCode.value=='' ? form.typeCode10[i].value : '@'+form.typeCode10[i].value ;
				}
			}	
		}
		for(var i=0;i<form.typeCode4.length;i++){
			if(form.typeCode4[i].selected)
			{
				if(form.typeCode4[i].value!=''){
					form.typeCode.value += form.typeCode.value=='' ? form.typeCode4[i].value : '@'+form.typeCode4[i].value ;
				}
			}	
		}
		for(var i=0;i<form.typeCode9.length;i++){
			if(form.typeCode9[i].selected)
			{
				if(form.typeCode9[i].value!=''){
					form.typeCode.value += form.typeCode.value=='' ? form.typeCode9[i].value : '@'+form.typeCode9[i].value ;
				}
			}	
		}
		
		for(var i=0;i<form.typeCode16.length;i++){
			if(form.typeCode16[i].selected)
			{
				if(form.typeCode16[i].value!=''){
					form.typeCode.value += form.typeCode.value=='' ? form.typeCode16[i].value : '@'+form.typeCode16[i].value ;
				}
			}	
		}
		
		for(var i=0;i<form.typeCode11.length;i++){
			if(form.typeCode11[i].selected)
			{
				if(form.typeCode11[i].value!=''){
					form.typeCode.value += form.typeCode.value=='' ? form.typeCode11[i].value : '@'+form.typeCode11[i].value ;
				}
			}	
		}
		
		for(var i=0;i<form.typeCode7.length;i++){
			if(form.typeCode7[i].selected)
			{
				if(form.typeCode7[i].value!=''){
					form.typeCode.value += form.typeCode.value=='' ? form.typeCode7[i].value : '@'+form.typeCode7[i].value ;
				}
			}	
		}

		for(var i=0;i<form.typeCode15.length;i++){
			if(form.typeCode15[i].selected)
			{
				if(form.typeCode15[i].value!=''){
					form.typeCode.value += form.typeCode.value=='' ? form.typeCode15[i].value : '@'+form.typeCode15[i].value ;
				}
			}	
		}
		
	}

	function showRegPop(url, id, name, idx){
		var f = document.SSend;
		
		f.profile_url.value = url;
		f.user_id.value = id;
		f.user_name.value = encodeURIComponent(name);
		f.idx.value = idx;

		popup.openByPost('SSend','pop_twitter_reg_form.jsp',500,550,false,true,false,'send_issue');
	}

	function showTwitterList(id, tendency, getDate){
		var f = document.SSend;

		f.user_id.value = id;
		if(tendency){
			f.tendency.value = tendency;
		}else{
			f.tendency.value = '';
		}
		
		if(getDate){
			f.getDate.value = getDate;
		}else{
			f.getDate.value = '';
		}

		f.html.value = '';
		
		popup.openByPost('SSend','pop_twitter_list.jsp',817,420,false,true,false,'send_issue');
	}

	function showTwitterList_html(html){
		var f = document.SSend;

		f.user_id.value = '';
		f.tendency.value = '';
		f.getDate.value = '';
		f.html.value = html;
		popup.openByPost('SSend','pop_twitter_list.jsp',817,420,false,true,false,'send_issue');
	}

	

	
	/**
	* URL열기
	*/
	var chkPop = 1;
	function hrefPop(key){
		window.open('http://twitter.com/#!/' + key,'hrefPop'+chkPop,'');
		chkPop++;
	}
	var link =1;
	function Link(url){
		//window.open(url,'link'+link,'');
		window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'link'+link,'');
		link++;
	}

	//XML데이터
	
	var chart1 = "<%=chart1%>";
	var chart2 = "<%=chart2%>";

</script>
</head>
<iframe id="processFrame" name="processFrame" height="0"></iframe>
<body style="margin-left: 15px">
<form id="SSend" name="SSend" action="" method="post">
<input type="hidden" name="typeCode" value="<%=typeCode %>">
<input type="hidden" name="encodingSearchKey" value="<%=searchKey%>">
<input type="hidden" name="profile_url" value="">
<input type="hidden" name="user_id" value="">
<input type="hidden" name="user_name" value="">
<input type="hidden" name="mode" value="insert">
<input type="hidden" name="mode2" value="RT">
<input type="hidden" name="idx">
<input type="hidden" name="tendency">
<input type="hidden" name="getDate">
<input type="hidden" name="html" >
<table style="width:100%;height:100%;" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="body_bg_top" valign="top">
			<table width="820" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="tit_bg" style="height:67px;padding-top:20px;">
					<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td><img src="../../../images/statistics/tit_icon.gif" /><img src="../../../images/statistics/tit_0002.gif" /></td>
							<td align="right">
							<table border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td class="navi_home">HOME</td>
									<td class="navi_arrow">통계관리</td>
									<td class="navi_arrow2">트위터 분석</td>
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
			</table>

			
			<table width="820" border="0" cellspacing="0" cellpadding="0">
				  <tr>
				    <td><table width="820" border="0" cellspacing="0" cellpadding="0">
				      <tr>
				        <td width="15"><img src="../../../images/issue/table_bg_01.gif" width="15" height="35" /></td>
				        <td width="793" background="../../../images/issue/table_bg_02.gif"><table width="790" border="0" cellspacing="0" cellpadding="0">
				          <tr>
				            <td width="17"><img src="../../../images/issue/table_icon_01.gif" width="16" height="12" /></td>
				            <td width="62" class="b_text"><strong>검색단어 </strong></td>
				            <td width="213"><input type="text" class="textbox3" style="width:200px;" name="searchKey" value="<%=searchKey%>" onkeydown="Javascript:if(event.keyCode == 13){search();}"/></td>
				            <td width="16"><img src="../../../images/issue/table_icon_01.gif" width="16" height="12" /></td>
				            <td width="60" class="b_text"><strong>검색기간</strong></td>
				            <td width="422"><table border="0" cellpadding="0" cellspacing="0">
				              <tr>
				                <td width="86"><input style="width:90px;text-align:center" class="textbox" type="text" id="sDateFrom" name="sDateFrom" value="<%=sDateFrom%>"></td>
				                <td width="27"><img src="../../../images/issue/btn_calendar.gif" style="cursor:pointer" onclick="calendar_sh(document.getElementById('sDateFrom'))"/></td>
				                <%
				                	String sBasics  = stime.equals("") ? "16" :  stime;
				                	String eBasics  = etime.equals("") ? "16" :  etime;
								%>
				                
				                <td width="55"><select name="stime"><%for(int i=0; i<24; i++){ if(i==Integer.parseInt(sBasics)){out.print("<option value="+i+" selected>"+i+"시</option>");}else{out.print("<option value="+i+">"+i+"시</option>");}} %></select></td>
				                <td width="11" align="center">~</td>
				                <td width="86"><input style="width:90px;text-align:center" class="textbox" type="text" id="sDateTo" name="sDateTo" value="<%=sDateTo%>"></td>
				                <td width="27"><img src="../../../images/issue/btn_calendar.gif" style="cursor:pointer" onclick="calendar_sh(document.getElementById('sDateTo'))"/></td>
				                <td width="55"><select name="etime"><%for(int i=0; i<=24; i++){ if(i==Integer.parseInt(eBasics)){out.print("<option value="+i+" selected>"+i+"시</option>");}else{out.print("<option value="+i+">"+i+"시</option>");}} %></select></td>
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
				        <td width="1" bgcolor="AEC6CE"></td>
				        <td width="818" bgcolor="E6E6CF" style="padding:10px 0px 5px 0px"><table width="798" border="0" align="center" cellpadding="0" cellspacing="0">
				          <tr>
				            <td height="24" style="padding:0px 0px 5px 7px"><table width="790" border="0" cellspacing="0" cellpadding="0">
				              <tr>
<%
	String selected = "";
	String codeTypeName = "";
	arrIcBean = icMgr.GetType(10);
	codeTypeName = icMgr.GetCodeName(arrIcBean,0);
%>
				                <td width="77" class="b_text" align="left"><img src="../../../images/issue/icon_search_bullet.gif" width="9" height="9" /> <strong><%=codeTypeName%></strong></td>
				                <td width="120"><select name="typeCode10" class="textbox3" style="width: 105px">
				                  <option value="">선택하세요</option>
<%
	for(int i = 1; i < arrIcBean.size(); i++){
		icBean = (IssueCodeBean) arrIcBean.get(i);
		if(icMgr.isTypeCode(typeCode,icBean.getIc_type()+","+icBean.getIc_code())) selected = "selected";
		else selected = "";
%>
												<option value="<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>" <%=selected%>><%=icBean.getIc_name()%></option>
<%
	}
%>				                  
				                                </select></td>
<%
	selected = "";
	codeTypeName = "";
	arrIcBean = icMgr.GetType(4);
	codeTypeName = icMgr.GetCodeName(arrIcBean,0);
%>				                                
				                <td width="77" align="left"><span class="b_text"><img src="../../../images/issue/icon_search_bullet.gif" width="9" height="9" /> <strong><%=codeTypeName%></strong></span></td>
				                <td width="120"><select name="typeCode4" class="textbox3" style="width: 105px">
				                  <option value="">선택하세요</option>
<%
	for(int i = 1; i < arrIcBean.size(); i++){
		icBean = (IssueCodeBean) arrIcBean.get(i);
		if(icMgr.isTypeCode(typeCode,icBean.getIc_type()+","+icBean.getIc_code())) selected = "selected";
		else selected = "";
%>
												<option value="<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>" <%=selected%>><%=icBean.getIc_name()%></option>
<%
	}
%>				                  
				                </select></td>	
				                			                
<%
	selected = "";
	codeTypeName = "";
	arrIcBean = icMgr.GetType(9);
	codeTypeName = icMgr.GetCodeName(arrIcBean,0);
%>				                                
				                <td width="77" align="left"><span class="b_text"><img src="../../../images/issue/icon_search_bullet.gif" width="9" height="9" /> <strong><%=codeTypeName%></strong></span></td>
				                <td width="120"><select name="typeCode9" class="textbox3" style="width: 105px">
				                  <option value="">선택하세요</option>
<%
	for(int i = 1; i < arrIcBean.size(); i++){
		icBean = (IssueCodeBean) arrIcBean.get(i);
		if(icMgr.isTypeCode(typeCode,icBean.getIc_type()+","+icBean.getIc_code())) selected = "selected";
		else selected = "";
%>
												<option value="<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>" <%=selected%>><%=icBean.getIc_name()%></option>
<%
	}
%>				                  
				                </select></td>	



<%
	selected = "";
	codeTypeName = "";
	arrIcBean = icMgr.GetType(7);
	codeTypeName = icMgr.GetCodeName(arrIcBean,0);
%>				                
				                <td width="77" align="left"><span class="b_text"><img src="../../../images/issue/icon_search_bullet.gif" width="9" height="9" /> <strong><%=codeTypeName%></strong></span></td>
				                <td width="120"><select name="typeCode7" class="textbox3" style="width: 105px">
				                    <option value="">선택하세요</option>
<%
	for(int i = 1; i < arrIcBean.size(); i++){
		icBean = (IssueCodeBean) arrIcBean.get(i);
		if(icMgr.isTypeCode(typeCode,icBean.getIc_type()+","+icBean.getIc_code())) selected = "selected";
		else selected = "";
%>
												<option value="<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>" <%=selected%>><%=icBean.getIc_name()%></option>
<%
	}
%>				                    
				                </select></td>



				              </tr>
				            </table></td>
				          </tr>
				          <tr>
				            <td><img src="../../../images/issue/dotline.gif" width="798" height="3" /></td>
				          </tr>
				          <tr>
				            <td style="padding:5px 0px 5px 7px"><table width="790" border="0" cellspacing="0" cellpadding="0">
				              <tr>
<%
	selected = "";
	codeTypeName = "";
	arrIcBean = icMgr.GetType(11);
	codeTypeName = icMgr.GetCodeName(arrIcBean,0);
%>				                
				                <td width="77" align="left"><span class="b_text"><img src="../../../images/issue/icon_search_bullet.gif" width="9" height="9" /> <strong><%=codeTypeName%></strong></span></td>
				                <td width="120"><select name="typeCode11" class="textbox3" style="width: 105px">
				                    <option value="">선택하세요</option>
<%
	for(int i = 1; i < arrIcBean.size(); i++){
		icBean = (IssueCodeBean) arrIcBean.get(i);
		if(icMgr.isTypeCode(typeCode,icBean.getIc_type()+","+icBean.getIc_code())) selected = "selected";
		else selected = "";
%>
												<option value="<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>" <%=selected%>><%=icBean.getIc_name()%></option>
<%
	}
%>				                    
				                </select></td>
				                
				                
<%
	selected = "";
	codeTypeName = "";
	arrIcBean = icMgr.GetType(16);
	codeTypeName = icMgr.GetCodeName(arrIcBean,0);
%>				                
				                <td width="77" align="left"><span class="b_text"><img src="../../../images/issue/icon_search_bullet.gif" width="9" height="9" /><strong> <%=codeTypeName%></strong></span></td>
				                <td width="120"><select name="typeCode16" class="textbox3" style="width: 105px">
				                  <option value="">선택하세요</option>
<%
	for(int i = 1; i < arrIcBean.size(); i++){
		icBean = (IssueCodeBean) arrIcBean.get(i);
		if(icMgr.isTypeCode(typeCode,icBean.getIc_type()+","+icBean.getIc_code())) selected = "selected";
		else selected = "";
%>
												<option value="<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>" <%=selected%>><%=icBean.getIc_name()%></option>
<%
	}
%>				                  
				                </select></td>				                
				                
				                

<%
	selected = "";
	codeTypeName = "";
	arrIcBean = icMgr.GetType(15);
	codeTypeName = icMgr.GetCodeName(arrIcBean,0);
%>				                
				                <td width="77" align="left"><span class="b_text"><img src="../../../images/issue/icon_search_bullet.gif" width="9" height="9" /><strong> <%=codeTypeName%></strong></span></td>
				                <td width="120"><select name="typeCode15" class="textbox3" style="width: 105px">
				                    <option value="">선택하세요</option>
<%
	for(int i = 1; i < arrIcBean.size(); i++){
		icBean = (IssueCodeBean) arrIcBean.get(i);
		if(icMgr.isTypeCode(typeCode,icBean.getIc_type()+","+icBean.getIc_code())) selected = "selected";
		else selected = "";
%>
												<option value="<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>" <%=selected%>><%=icBean.getIc_name()%></option>
<%
	}
%>				                    
				                </select></td>
				                
			                
				                <td width="77" align="left"></td>
				                <td width="120"></td>				                
				              </tr>
				            </table></td>
				          </tr>
				        
				          
				          
				        </table></td>
				        <td width="1" bgcolor="AEC6CE"></td>
				      </tr>
				    </table></td>
				  </tr>
				  <tr>
				    <td height="1" bgcolor="AEC6CE"></td>
				  </tr>
				</table>
			
			
			
			
			
			
			
			
			
			
			<br>
			<br>
			
			
			
			
			
			
			
			
			<table width="820" border="0" cellspacing="0" cellpadding="0">
			  <tr>
			    <td><table width="820" border="0" cellspacing="0" cellpadding="0">
			      <tr>
			        <td width="820" valign="top"><table width="820" border="0" cellspacing="0" cellpadding="0">
			           
			            <tr>
			              <td><table id="board_01" border="0" cellpadding="0" cellspacing="0">
			                  <tr>
			                    <th width="50">순위</th>
			                    <th width="60">사진</th>
			                    <th width="135">최초 작성자</th>
			                    <th width="27"></th>
			                    <th width="94">작성자 정보</th>
			                    
			                    <th width="70">리트윗</th>
			                    <th width="76">노출도</th>
			                    <th width="308">관련글</th>
			                    
			                  </tr>
			                  <%
			                  	StatisticsTwitterSuperBean.WriterBean sBean = null;
			                  	for(int i =0; i < arrData.size(); i++){
			                  		sBean = (StatisticsTwitterSuperBean.WriterBean)arrData.get(i);
			                  		
			                  %>
			                  	<tr>
			                    <td width="50" height="48"><%=i+1%></td>
			                    <td width="60" height="48" style="padding:4px 0px 4px 0px">
			                    <%
			                    	if(!sBean.getT_profile_image().equals("")){
			                    %>
			                    	<img src="<%=sBean.getT_profile_image()%>" width="48" height="48">
			                    <%}%>
			                    	
			                    </td>
			                    <td width="135" height="48" class="r_text"><a style="cursor: pointer;" onClick="hrefPop('<%=sBean.getT_user_id()%>')"><strong><%=sBean.getT_name()%></strong><br>@<%=sBean.getT_user_id()%></a></td>
			                    
			                     
			                    <%
			                    	if(sBean.getInterest().equals("Y")){
			                    %>
			                    
			                    	<td height="48" align="left"><img src="../../../images/statistics/icon_001.gif"></td>
			                    			
			                    <% 	}else{ %>
			                    	
			                    	<td height="48" align="left"><img id="inter_<%=i%>" src="../../../images/statistics/btn_write.gif" style="cursor: pointer;" onclick="showRegPop('<%=sBean.getT_profile_image()%>','<%=sBean.getT_user_id()%>','<%=su.toHtmlString(sBean.getT_name())%>','<%=i%>');"></td>
			                    	
			                    <% 	}%>
			                    	
			                    
			                    <td width="94" height="48" style="line-height:120%; text-align: left; padding:4px 0px 4px 10px" >팔로워 : <%=su.digitFormat(sBean.getT_followers())%> 
				                    <br> 팔로잉 : <%=su.digitFormat(sBean.getT_following())%> 
				                    <br> 트윗 : <%=su.digitFormat(sBean.getT_tweet())%></td>
			                    
			                    
			                    <td width="70" height="48"><a style="cursor: pointer;" onClick="showTwitterList_html('<%=su.toHtmlString(sBean.getT_title())%>')"><strong><%=su.digitFormat(sBean.getCnt())%></strong></a></td>
			                    <%
			                    	String imgPath = "";
			                    
			                    	if(Integer.parseInt(sBean.getPositive()) >= Integer.parseInt(sBean.getNegative())){
			                    		
			                    		if(Integer.parseInt(sBean.getPositive()) >= Integer.parseInt(sBean.getNeutral())){
			                    			imgPath = "icon_good.gif";
			                    		}else{
			                    			imgPath = "icon_gb.gif";
			                    		}
			                    		
			                    	}else{
			                    		if(Integer.parseInt(sBean.getNegative()) >= Integer.parseInt(sBean.getNeutral())){
			                    			imgPath = "icon_bad.gif";
			                    		}else{
			                    			imgPath = "icon_gb.gif";
			                    		}
			                    	}
			                    %>	
			                    <td width="76" height="48" style="line-height:200%;"> <%=su.digitFormat(Integer.parseInt(sBean.getT_followers()) + Integer.parseInt(sBean.getFull_followers()))%><br><img src="../../../images/statistics/<%=imgPath%>"> </td>
			                    <td width="308" height="48" style="line-height:120%; text-align: left; padding:8px 0px 8px 10px"><a style="cursor: pointer;" onClick="Link('<%=sBean.getId_url()%>')"><%=sBean.getT_title()%></a></td>
			                    
			                  </tr>
			                  		
			                  <%		
			                  	}
			                  %>
			                  
			                
			              </table></td>
			            </tr>
			        </table></td>
			        <td width="20"></td>
			        <td valign="top" style="padding:31px 0px 0px 0px">
<!--			        	<img src="../../../images/statistics/graph.gif" width="191" height="516">-->
						

			        </td>
			      </tr>
			    </table></td>
			  </tr>
			  <tr>
			    <td height="20"></td>
			  </tr>
			  <tr>
			    <td><table width="820" border="0" cellspacing="0" cellpadding="0">
			      <tr>
			        <td><img src="../../../images/statistics/bg_graph_01.gif" width="820" height="5"></td>
			      </tr>
			      <tr>
			        <td align="center" background="../../../images/statistics/bg_graph_02.gif" style="padding:15px 0px 15px 0px">
<!--			        	<img src="../../../images/statistics/graph_01.gif" width="687" height="328">-->
			        	<div id="chart2">
						    <script type="text/javascript">
							   var chart = new FusionCharts("../../../fusionChart/swf/MSLine.swf", "ChartId", "800", "240", "0", "0");
							   chart.setDataXML(chart2);
							   chart.render("chart2");
							</script>
						</div>
			        </td>
			      </tr>
			      <tr>
			        <td><img src="../../../images/statistics/bg_graph_03.gif" width="820" height="5"></td>
			      </tr>
			    </table></td>
			  </tr>
			</table>
			<br>
			<br>
			<br>
			<br>	
		</td>
	</tr>
</table>
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
</form>
</body>
</html>