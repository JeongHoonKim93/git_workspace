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
                	, risk.util.PageIndex
					" %>

<%
	DateUtil   du = new DateUtil();
	ParseRequest  pr = new ParseRequest(request);	
	StringUtil su = new StringUtil();
	ConfigUtil cu = new ConfigUtil();
	StatisticsTwitterMgr sMgr = new StatisticsTwitterMgr();
	StatisticsTwitterChartXml cXml = new StatisticsTwitterChartXml();
	String check_no = pr.getString("check_no","");
	
	
	int nowpage = 1;	
	int pageCnt = 12;
	int totalCnt = 0;
	int totalPage = 0;
	nowpage = pr.getInt("nowpage",1);
	
	ArrayList arrData = sMgr.getinterestData(nowpage,pageCnt);
	
	totalCnt =  sMgr.getTotalCnt();
	
	totalPage = totalCnt / pageCnt;
	if(totalCnt % pageCnt > 0){
		totalPage += 1;
	}
	String srtMsg = "수량 <b>"+totalCnt+"건</b>, "+nowpage+"/"+totalPage+" pages";
	
	
	String chart1 = cXml.XmlData_1(arrData);

	
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

	function java_all_trim(a) {
        for (; a.indexOf(" ") != -1 ;) {
          a = a.replace(" ","");
        }
        return a;
    }

	function search(){
		var f = document.SSend;
		settingTypeCode();
		f.encodingSearchKey.value = encodeURIComponent(f.searchKey.value);
		
		f.target ='';
		f.action ='';
		f.submit();
	}


	function showRegPop(url, id, name){
		var f = document.SSend;
		
		f.profile_url.value = url;
		f.user_id.value = id;
		f.user_name.value = encodeURIComponent(name);

		popup.openByPost('SSend','pop_twitter_reg_form.jsp',500,550,false,true,false,'send_issue');
	}

	//체크박스
    function checkAll(chk) {
 		var o=document.getElementsByName('tt');
 		for(i=0; i<o.length; i++) {
 			o[i].checked = chk;
 		}
 	}

    function deleteData(){
    	var o=document.getElementsByName('tt');
 		var chkedseq='';
 		var cnt = 0;
 		
 		for(i=0; i<o.length; i++) {
 			if(o[i].checked == true){
	 			if(o[i].value!=""){
	 				if(chkedseq == ''){
	 					chkedseq = String(o[i].value);
	 				}else{
	 					chkedseq = chkedseq +','+o[i].value;
	 				}
	 				cnt++;
	 			}
 			}
 		}

 		var f = document.SSend;
		if(chkedseq == '') {
			alert('선택된 정보가 없습니다.'); return;
		}
		else {
			if(confirm(cnt+'건의 정보를 삭제합니다.')) {
				
				f.mode.value = 'delete';				
				f.check_no.value = chkedseq;
				f.action="statistics_twitter_prc.jsp";
				f.target='processFrame';
				f.submit();
				
			}
		}
	}

    function showTwitterList(id, tendency, getDate){
		var f = document.SSend;

		f.user_id.value = id;
		f.mode2.value = 'writer';
		
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

	//페이징
	function pageClick( paramUrl ) {	
       	var f = document.SSend; 
       	
        f.action = 'statistics_twitter_interest.jsp' + paramUrl;
        f.target='';
        f.submit();	        
	}
    
	//XML데이터
	var chart1 = "<%=chart1%>";
	

</script>
</head>

<body style="margin-left: 15px">
<form id="SSend" name="SSend" action="" method="post">
<input type="hidden" name="profile_url" value="">
<input type="hidden" name="user_id" value="">
<input type="hidden" name="user_name" value="">
<input type="hidden" name="mode" value="update">
<input type="hidden" name="mode2" value="writer">
<input type="hidden" name="check_no" value="<%=check_no%>">
<input type="hidden" name="tendency">
<input type="hidden" name="getDate">
<input type="hidden" name="nowpage" value="<%=nowpage%>">
<table style="width:100%;height:100%;" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="body_bg_top" valign="top">
			<table width="820" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="tit_bg" style="height:67px;padding-top:20px;">
					<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td><img src="../../../images/statistics/tit_icon.gif" /><img src="../../../images/statistics/tit_0003.gif" /></td>
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
				
			</table>
	
			
			
			<table width="820" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td style="height:40px;">
					<table border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td><img src="../../../images/statistics/btn_allselect.gif" onclick="checkAll(!document.SSend.checkall.checked);" style="cursor:pointer;"/></td>							
							<td><img src="../../../images/statistics/btn_del.gif" onclick="deleteData();" style="cursor:pointer;"/></td>
						</tr>
					</table>				
					</td>
				</tr>
			  <tr>
			    <td><table width="820" border="0" cellspacing="0" cellpadding="0">
			      <tr>
			        <td width="820" valign="top"><table width="820" border="0" cellspacing="0" cellpadding="0">
			            <tr>
			              <td><table id="board_01" border="0" cellpadding="0" cellspacing="0">
			                  <tr>
			                    <th width="50"><input type="checkbox" name="tt" id="checkall" onclick="checkAll(this.checked);" value=""></th>
			                    <th width="60">사진</th>
			                    <th width="135">아이디</th>
			                    <th width="27"></th>
			                    <th width="80">이름/소속</th>
			                    <th width="60">팔로워</th>
			                    <th width="60">팔로윙</th>
			                    <th width="60">트윗</th>
			                    <th width="60">작성글</th>
			                    <th width="178"><img src="../../../images/statistics/icon_blue.gif" style="vertical-align: middle">&nbsp;긍정&nbsp;&nbsp;<img src="../../../images/statistics/icon_red.gif" style="vertical-align: middle">&nbsp;부정&nbsp;&nbsp;<img src="../../../images/statistics/icon_green.gif" style="vertical-align: middle">&nbsp;중립</th>
			                   	<th width="50">관심도</th> 
			                    
			                  </tr>
			                  <%
			                  	StatisticsTwitterSuperBean.WriterBean sBean = null;
			                  	for(int i =0; i < arrData.size(); i++){
			                  		sBean = (StatisticsTwitterSuperBean.WriterBean)arrData.get(i);
			                  		
			                  %>
			                  	<tr>
			                    <td height="48"><input name="tt" type="checkbox" value="<%=sBean.getT_user_id()%>" id="tt" ></td>
			                    
			                    <td height="48" style="padding:4px 0px 4px 0px">
			                    <%
			                    	if(!sBean.getT_profile_image().equals("")){
			                    %>
			                    	<img src="<%=sBean.getT_profile_image()%>" width="40" height="40">
			                    <%		
			                    	}
			                    %>
			                    	
			                    </td>
			                    <td height="48" class="r_text"><a style="cursor: pointer;" onClick="hrefPop('<%=sBean.getT_user_id()%>')"><strong><%=sBean.getT_name()%></strong><br>@<%=sBean.getT_user_id()%></a></td>
			                    <td height="48" align="left"><img id="inter_<%=i%>" src="../../../images/statistics/btn_modify.gif" style="cursor: pointer;" onclick="showRegPop('<%=sBean.getT_profile_image()%>','<%=sBean.getT_user_id()%>','<%=su.toHtmlString(sBean.getT_name())%>');"></td>
			                    <td height="48"><%=sBean.getT_job()%></td>
			                    <td height="48"><%=su.digitFormat(sBean.getT_followers())%></td>
			                    <td height="48"><%=su.digitFormat(sBean.getT_following())%></td>
			                    <td height="48"> <%=su.digitFormat(sBean.getT_tweet())%></td>
			                    <td height="48"><a style="cursor: pointer;" onClick="showTwitterList('<%=sBean.getT_user_id()%>')"><strong><%=su.digitFormat(sBean.getCnt())%></strong></a></td>
			                    
			                    <%if(i==0){%>
			                    <td rowspan="<%=arrData.size()%>" >
			                    	<div id="chart1" style="height: 100%">
									    <script type="text/javascript">
										   var chart = new FusionCharts("../../../fusionChart/swf/StackedBar2D.swf", "ChartId", "100%", "100%", "0", "0");
										   chart.setDataXML(chart1);
										   chart.render("chart1");
										</script>
									</div>
			                    </td>
			                    <%} %>
			                    <%
			                    	if(sBean.getT_import().equals("1")){
			                    %>
			                    	<td height="48"><dd><img src="../../../images/statistics/statusbar_03.gif" /></dd><dd><img src="../../../images/statistics/statusbar_03.gif" /></dd><dd><img src="../../../images/statistics/statusbar_03.gif" /></dd></td>
			                    <%		
			                    	}else if(sBean.getT_import().equals("2")){ 
			                    %>
			                    	<td height="48"><dd><img src="../../../images/statistics/statusbar_03.gif" /></dd><dd><img src="../../../images/statistics/statusbar_03.gif" /></dd><dd><img src="../../../images/statistics/statusbar_01.gif" /></dd></td>
			                    
			                    <%
			                    	}else if(sBean.getT_import().equals("3")){
			                    %>
			                    	<td height="48"><dd><img src="../../../images/statistics/statusbar_03.gif" /></dd><dd><img src="../../../images/statistics/statusbar_01.gif" /></dd><dd><img src="../../../images/statistics/statusbar_01.gif" /></dd></td>
			                    <%
			                    	}
			                    %>
			                    
<!--			                    <td height="48"><strong>수정</strong></td>-->
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
			  	<td>
			  		<!-- 페이징 -->
					<table align="center" style="padding-top:10px;" border="0" cellpadding="0" cellspacing="1">
						<tr>
							<td>
								<table id="paging" border="0" cellpadding="0" cellspacing="2">
									<tr>
										<%=PageIndex.getPageIndex(nowpage, totalPage,"","" )%>
									</tr>
								</table>
							</td>
						</tr>
					</table>
					<!-- 페이징 -->	
			  	
			  	</td>
			  </tr>
			  	
			
			</table>
			<br>
			<br>
			
		</td>
	</tr>
</table>
</form>
<iframe id="processFrame" name="processFrame" height="0" style="display: none;"></iframe>
</body>
</html>