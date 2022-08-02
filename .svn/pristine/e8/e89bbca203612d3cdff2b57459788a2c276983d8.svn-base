<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="../inc/sessioncheck.jsp" %>    
<%@ page import="	java.util.ArrayList
					,java.awt.Color
					,java.io.File
					,java.awt.*
					,risk.util.ParseRequest
					,risk.util.ConfigUtil
					,risk.util.StringUtil
					,risk.util.DateUtil
					,risk.search.userEnvMgr
                    ,risk.search.userEnvInfo 
                    ,risk.search.keywordInfo
                    ,java.util.HashMap             
					" %>

<%
	DateUtil   du = new DateUtil();
	ParseRequest  pr = new ParseRequest(request);	
	StringUtil su = new StringUtil();
	ConfigUtil cu = new ConfigUtil();
	
	ArrayList keywordGroupList = new ArrayList();
	String sDateFrom ="";
	String sDateTo ="";
	
	//날짜 셋팅 후 최소,최대  문서 값을 가져온다.
	sDateFrom = pr.getString("sDateFrom","");
	sDateTo = pr.getString("sDateTo","");
	if(sDateFrom.equals("")){
		sDateTo = du.getCurrentDate("yyyy-MM-dd");
		sDateFrom = du.addDay(sDateTo,-6);
	}
	
	
	
	// 일자 마스터 가져오기	
	String first = du.getDate(sDateFrom,"yyyyMMdd");
	String last = du.getDate(sDateTo,"yyyyMMdd");
	
	long diffDay = -du.DateDiff("yyyyMMdd",first,last);
	int intDiffDay = Integer.parseInt(String.valueOf(diffDay));
	
	String[] date  = null;
	if(intDiffDay>0)
	{
		date  = new String[intDiffDay+1];
		for(int i = 0; i<intDiffDay+1; i++)
		{
			date[i] = du.getDate(du.addDay(first, i , "yyyyMMdd"),"MM.dd");										
		}
	}
	
	
	StatisticsMgr sMgr = new StatisticsMgr();
	ArrayList arMaster = sMgr.getIssueCode("11");
	ArrayList arData = sMgr.getStatisticsIssue(sDateFrom, sDateTo);
	
	
	//userEnvMgr uemng = new userEnvMgr();
	//userEnvInfo uei = null;
    //uei     = (userEnvInfo) session.getAttribute("ENV");
	//keywordGroupList = uemng.getKeywordGroup(uei.getMg_xp());
	
	/*
	long diffDay = 0;
	int intDiffDay = 0;
	String first = "";
	String last = "";
	
	String[] date  = null;
	HashMap tempHash = new HashMap();
	
	first = du.getDate(sDateFrom,"yyyyMMdd");
	last = du.getDate(sDateTo,"yyyyMMdd");
	
	diffDay = -du.DateDiff("yyyyMMdd",first,last);
	intDiffDay = Integer.parseInt(String.valueOf(diffDay));
	
	if(intDiffDay>0)
	{
		date  = new String[intDiffDay+1];
		for(int i = 0; i<intDiffDay+1; i++)
		{
			date[i] = du.getDate(du.addDay(first, i , "yyyyMMdd"),"MM.dd");										
		}
	}
	*/
	
	
	
	
	
%>

<%@page import="risk.statistics.StatisticsMgr"%>
<%@page import="risk.statistics.StatisticsSuperBean"%><html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="css/basic.css" type="text/css">
<link rel="stylesheet" href="../../css/base.css" type="text/css">
<link rel="stylesheet" href=css/calendar.css" type="text/css">
<style>
iframe.hide { border:0px solid red; position:absolute; top:0px; left:0px; z-index:-99; width:148px; height:150px; filter: alpha(opacity=0);}
</style>
<script src="<%=SS_URL%>js/Calendar.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/jquery.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/ajax.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/common.js"  type="text/javascript"></script>
<script src="<%=SS_URL%>js/popup.js" type="text/javascript"></script>
<script language="JavaScript" type="text/JavaScript">
<!--
	sCurrDate = '<%=du.getCurrentDate("yyyy-MM-dd")%>'
	sDateFrom = '<%=sDateFrom%>';
	sDateTo = '<%=sDateTo%>';

	/*
	function getSubKeywordGroup()
	{
		

		var obj = document.SSend.k_yp;
		if(obj){
			if(obj.length){
				for(var i =0; i < obj.length; i++){
					obj[i].value = '';
				} 
			}else{
				obj.value = '';
			}	
		}
		
		ajax.post('sub_keywordgroup_radio.jsp','SSend','subKeywordGroup');
		document.getElementById("subKeywordGroup2").innerHTML = ''; 
	}
	*/
	/*
	function getSubKeywordGroup2()
	{
		ajax.post('sub_keywordgroup_radio.jsp','SSend','subKeywordGroup2');
	}
	*/

	function showStatisiticView()
	{

 	 	var f =	document.SSend;

		//날짜 차이 구하기 (client)
		/*				
		var diff = checkTerm(f.sDateTo.value.replace(/-/gi,""), f.sDateFrom.value.replace(/-/gi,""));
		if(diff > 6){
			alert('검색기간은 최대 일주일 입니다.');
			return;
		}
		*/

		f.target ='';		
		f.action='statistics_collect_issue.jsp';		
		f.submit();

	}

	function downExcel()
	{
		var f =	document.SSend;
		//날짜 차이 구하기 (client)
		/*
		var diff = checkTerm(f.sDateTo.value.replace(/-/gi,""), f.sDateFrom.value.replace(/-/gi,""));
		if(diff > 6){
			alert('검색기간은 최대 일주일 입니다.');
			return;
		}
		*/
		
		f.target ='processFrame';		
		f.action='statistics_keyword_collect_excel4.jsp';		
		f.submit();
	}

	    
    function setDateTerm( day ) {
       var f = document.SSend;
       f.sDateFrom.value   = AddDate( day );
       f.sDateTo.value     = sCurrDate;
       //f.Period.value      = day + 1;

    }
    
    function java_all_trim(a) {
            for (; a.indexOf(" ") != -1 ;) {
              a = a.replace(" ","")
            }
            return a;
        }
        
    function AddDate( day ) {

        var newdate = new Date();
        sdate = newdate.getTime();

        edate = sdate - ( day * 24 * 60 * 60 * 1000);
        newdate.setTime(edate);

        last_ndate = newdate.toLocaleString();

        last_date = java_all_trim(last_ndate)
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
    

    //두날자 차이를 구하는 함수
    function checkTerm(eDate, sDate)
    {
        var eDateYear  = parseInt(eDate.substring(1,4),10);
        var eDateMonth = parseInt(eDate.substring(4,6),10);
        var eDateDate  = parseInt(eDate.substring(6,8),10);

        //if ( eDateYear <  )


        var sDateYear  = parseInt(sDate.substring(1,4),10);
        var sDateMonth = parseInt(sDate.substring(4,6),10);
        var sDateDate  = parseInt(sDate.substring(6,8),10);


        var eDate = new Date(eDateYear, eDateMonth-1, eDateDate);
        var sDate = new Date(sDateYear, sDateMonth-1, sDateDate);

        var differ = (((((eDate - sDate)/1000)/60)/60)/24)+1;
        return differ - 1;
    }
	
	
//-->
</script>
</head>
<iframe id="processFrame" name="processFrame" height="0"></iframe>
<body leftmargin="10" topmargin="10" marginwidth="0" marginheight="0">
<form id="SSend" name="SSend" action="" method="post">
<table style="width:100%;height:100%;" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="body_bg_top" valign="top">
			<table width="820" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="tit_bg" style="height:67px;padding-top:20px;">
					<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td><img src="../../images/statistics/tit_icon.gif" /><img src="../../images/statistics/tit_0529_01.gif" /></td>
							<td align="right">
							<table border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td class="navi_home">HOME</td>
									<td class="navi_arrow">통계관리</td>
									<td class="navi_arrow2">이슈 관리</td>
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
			<table width="750" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="37" align="center" background="../../images/statistics/collect_tbg02.gif"><table width="720" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="12" style="padding: 2px 0px 0px 0px;"><img src="../../images/statistics/issue_t_ico02.gif" width="7" height="13" align="absmiddle"></td>
							<td width="70" style="padding: 5px 0px 0px 0px;"><strong>검색기간 :</strong></td>
							<td width="220">
							<input name="sDateFrom" type="text" size="10"  maxlength="10" class="txtbox" value="<%=sDateFrom%>">
							<img src="../../images/search/btn_calendar.gif" style="cursor:pointer;vertical-align: middle" onclick="calendar_sh(document.getElementById('sDateFrom'))"/>
							~
							<input name="sDateTo" type="text" size="10"  maxlength="10" class="txtbox" value="<%=sDateTo%>" size="11">
							<img src="../../images/search/btn_calendar.gif" style="cursor:pointer;vertical-align: middle" onclick="calendar_sh(document.getElementById('sDateTo'))"/>
							</td>
							<td>
								<img style="cursor:hand" onclick="setDateTerm(0)" src="../../images/statistics/con_data01.gif" width="32" height="18">
								<img style="cursor:hand" onclick="setDateTerm(2)" src="../../images/statistics/con_data02.gif" width="32" height="18" hspace="4">
								<img style="cursor:hand" onclick="setDateTerm(6)" src="../../images/statistics/con_data03.gif" width="32" height="18">
							</td>
							<td align="right"><img style="cursor:hand;" onclick="showStatisiticView();" src="../../images/statistics/statis_btn02.gif" width="80" height="24">&nbsp;<!-- <img src="../../images/statistics/print_btn.gif" width="60" height="24" style="cursor:hand;padding: 0 0 0 5;" onclick="javascript:printPop();">--></td>
						</tr>
					</table></td>
				</tr>       
				<tr>
					<td style="padding: 5px 0px 0px 0px;"></td>
				</tr>
			</table>
			<br>
			
			
			<table align="left" width="<%=(intDiffDay*75+200)%>" border="0" cellspacing="0" cellpadding="0" style="padding-left: 55px"> 		
			  <tr>    
			  	<td><img src="../../images/statistics/admin_ico01.gif" width="12" height="10" align="absmiddle"><span class="menu_black"><strong>개인미디어 유형별 통계</strong>&nbsp;&nbsp;&nbsp;&nbsp;</span><img style="cursor:hand;" align="absmiddle" onclick="downExcel();" src="../../images/statistics/excel_save.gif"></td>
			    <td align="left"></td>
			  </tr>
			  <tr>
			    <td colspan="2" height="3">&nbsp;</td>
			  </tr> 
			  <tr>
			    <td colspan="2">
				    <table width="<%=(intDiffDay*65+200)%>" border="0" cellspacing="0" cellpadding="0">
				      <tr>
				        <td colspan="2" bgcolor="#7CA5DD"><img src="../../images/statistics/brank.gif" width="1" height="2"></td>
				      </tr>
				      <tr>
				        <td colspan="2" bgcolor="#CFCFCF"><table width="<%=(intDiffDay*65+200)%>"  border="0" cellspacing="1" cellpadding="1">
				            <tr>
				              <td width="150" height="30" align="center" background="../../images/statistics/statis_table_bg01.gif" style="padding: 3px 0px 0px 0px;font-weight: bold;">키워드</td>
				<%
							for(int i=0; i<date.length; i++){
				%>            
				              <td width="75" align="center" background="../../images/statistics/statis_table_bg01.gif" class="menu_black" style="padding: 3px 0px 0px 0px; font-weight: bold;"><%=date[i]%></td>
				<%
							}
				%>                        
				            </tr>
				<%
							StatisticsSuperBean.CodeNameBean masterBean = null;
							StatisticsSuperBean.CodeNameBean dataBean = null;
							
							for(int i=0;i<arMaster.size();i++){
								masterBean = (StatisticsSuperBean.CodeNameBean)arMaster.get(i);
				%>       
				            <tr>
				              <td width="150" align="center" bgcolor="#FFFFFF" class="menu_black" style="padding: 3px 0px 0px 0px;"><%=masterBean.getName()%></td>
				          	  
				<%				
								String cnt = "0";
								for(int j=0; j<date.length; j++){
								
									for(int k=0; k < arData.size(); k++){
										dataBean = (StatisticsSuperBean.CodeNameBean)arData.get(k);
										
										cnt = "0";
										
										if(date[j].equals(dataBean.getDate()) && masterBean.getCode().equals(dataBean.getCode())){
											cnt = dataBean.getCnt(); 
											break;
										}				
									}
									
									out.print("<td width=\"75\" height=\"30\" align=\"center\" bgcolor=\"#FFFFFF\" style=\"padding: 3px 0px 0px 0px;\">"+cnt+"</td>");
									
								}
				%>              
					  		  <td align="center" bgcolor="#FFFFFF" style="padding: 3px 0px 0px 0px;"></td>
				            </tr>   
				<%
							}
				%>       
				        </table></td>
				      </tr>
					</table>
			    </td>
			  </tr>
			  <tr height="50px"><td></td></tr>
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
</form>
</body>
</html>