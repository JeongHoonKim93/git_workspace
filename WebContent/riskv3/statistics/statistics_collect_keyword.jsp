<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../inc/sessioncheck.jsp" %>    
<%@ page import="	java.util.ArrayList
					,java.awt.Color
					,java.io.File
					,java.awt.*
					,risk.util.ParseRequest
					,risk.util.ConfigUtil
					,risk.util.StringUtil
					,risk.util.DateUtil
					,risk.search.MetaMgr 
					,risk.statistics.StatisticsBean
					,risk.statistics.StatisticsMgr
					,risk.admin.site.SiteBean
					,risk.JfreeChart.MakeTypeChart	             
					" %>

<%
	DateUtil   du = new DateUtil();
	ParseRequest  pr = new ParseRequest(request);	
	StatisticsMgr staticMgr = new StatisticsMgr();
	StringUtil su = new StringUtil();
	ConfigUtil cu = new ConfigUtil();
	
	pr.printParams();
		
	ArrayList Keyword_data = new ArrayList();
	ArrayList Str_Keyword_name = new ArrayList();
	ArrayList Site_group = new ArrayList();
	ArrayList Site_Gname = new ArrayList();
	
	String Keyword_name = "";
	String sDateFrom ="";
	String sDateTo ="";
	String minNo = "";
	String maxNo = "";
	
	String Keyword_K_xps = "";
	String str_chart_legend = "";
	String get_site_group_data_sum = "";
	
	//날짜 셋팅 후 최소,최대  문서 값을 가져온다.
	sDateFrom = pr.getString("sDateFrom","");
	sDateTo = pr.getString("sDateTo","");
	if(sDateFrom.equals("")){
		sDateTo = du.getCurrentDate("yyyy-MM-dd");
		sDateFrom = du.addDay(sDateTo,-6);
	}
	MetaMgr  mMgr = new MetaMgr();
	/*
	mMgr.getMaxMinNo(sDateFrom,sDateTo);
	minNo = mMgr.msMinNo;
	maxNo = mMgr.msMaxNo;
	*/
	
	MakeTypeChart mkChart = new MakeTypeChart();
	
	//키워드 주간 정보 건수
	String filePath = cu.getConfig("CHARTPATH")+"statistics/";
	String chartUrl = cu.getConfig("CHARTURL")+"statistics/";
	HashMap chartData = staticMgr.getKeywordChartData(sDateFrom,"00:00:00",sDateTo,"23:59:59");
	String lineChart = mkChart.makeLine(1,(ArrayList)chartData.get("A"),"KeywordStatisticLine",filePath,chartUrl,516,252);
	
	//키워드별 정보 건수 TOP 10
	Keyword_data = staticMgr.getKeywordData(sDateFrom,sDateTo);	
	
	//사이트 그룹별 키워드 그룹별 일일 동향
	Str_Keyword_name = staticMgr.get_Keyword_name();
	Site_Gname = staticMgr.get_siteG_name();
	str_chart_legend = staticMgr.get_chart_legend_name(Keyword_K_xps);

%>


<%@page import="org.jfree.chart.title.LegendTitle"%>
<%@page import="org.jfree.chart.renderer.xy.XYItemRenderer"%>
<%@page import="org.jfree.chart.renderer.AbstractRenderer"%>
<%@page import="org.jfree.chart.labels.StandardCategoryItemLabelGenerator"%>
<%@page import="org.jfree.chart.imagemap.URLTagFragmentGenerator"%>
<%@page import="org.jfree.chart.urls.XYURLGenerator"%>
<%@page import="java.util.HashMap"%>
<html>
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
<script language="JavaScript" type="text/JavaScript">
<!--
	sCurrDate = '<%=du.getCurrentDate("yyyy-MM-dd")%>'
	sDateFrom = '<%=sDateFrom%>';
	sDateTo = '<%=sDateTo%>';
	
	function GoStatics(){

		SSend.action='statistics_collect_keyword.jsp';
		SSend.target='';
		SSend.submit();
	}
	function midKey(val1,val2,val3){
		SSend.k_xp.value=val1;
		SSend.k_yp.value=val2;
		SSend.k_zp.value=val3;
		
		SSend.action='inc_keyword_collect.jsp';
		SSend.target='midle_key';
		SSend.submit();
	
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
    
    function openList( xp, yp, zp)
	{
		// 통계별 구분을 위한 변수
		var staticType = 1;
		frm = document.frmPop;
		var url = 'pop_static_list.jsp?staticType='+staticType+'&sDateFrom='+frm.sDateFrom.value+'&sDateTo='+frm.sDateTo.value+'&xp='+xp+'&yp='+yp+'&zp='+zp;
		window.open(url, 'pop_staticList', 'width=650,height=530');
	
	}

    function pop_list_view( xp, yp, zp, sg_seq, sDateFrom, sDateTo ){
		var f = document.SSend;
		var view = window.open("pop_list_view.jsp?xp="+xp+"&yp="+yp+"&zp="+zp+"&sg_seq="+sg_seq+"&sDateFrom="+sDateFrom+"&sDateTo="+sDateTo,'pop_list_view', 'width=820,height=790,scrollbars=yes');
	}

    function downExcel(arg1)
	{
		var f = document.SSend;
		f.target ='';		
		f.action='statistics_keyword_collect_excel'+arg1+'.jsp';		
		f.submit();
	}

	function printPop()
	{
		var view = window.open('','pop_print_view', 'width=790,height=900,scrollbars=yes');
		var f = document.SSend;
		f.target ='pop_print_view';		
		f.action='static_keyword_collect_print.jsp';		
		f.submit();
	}
	
	
//-->
</script>
</head>
<body style="margin-left: 15px">
<form name="SSend" action="" method="post">
<table style="width:100%;height:100%;" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="body_bg_top" valign="top">
			<table width="820" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="tit_bg" style="height:67px;padding-top:20px;">
					<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td><img src="../../images/statistics/tit_icon.gif" /><img src="../../images/statistics/tit_0402.gif" /></td>
							<td align="right">
							<table border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td class="navi_home">HOME</td>
									<td class="navi_arrow">통계관리</td>
									<td class="navi_arrow2">키워드관리</td>
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
							<td align="right"><img style="cursor:hand;" onclick="GoStatics();" src="../../images/statistics/statis_btn02.gif" width="80" height="24">&nbsp;<!-- <img src="../../images/statistics/print_btn.gif" width="60" height="24" style="cursor:hand;padding: 0 0 0 5;" onclick="javascript:printPop();">--></td>
						</tr>
					</table></td>
				</tr>       
				<tr>
					<td style="padding: 5px 0px 0px 0px;"></td>
				</tr>
			</table>
			<br>
			<table width="750" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="530" valign="top"><table width="530" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="200" height="30"><img src="../../images/statistics/admin_ico01.gif" width="12" height="10" align="absmiddle"><span class="menu_black"><strong> 키워드그룹별 차트 </strong></span></td>
						</tr>
						<tr>
							<td bgcolor="#CFCFCF"><table width="530" border="0" cellpadding="1" cellspacing="1">
								<tr>
									<td align="center" bgcolor="F6F6F6" style="padding:15px 0px 15px 0px"><img src="<%=lineChart%>" width="516" height="252"></td>
								</tr>
							</table></td>
						</tr>
					</table></td>
					<td width="15">&nbsp;</td>
					<td width="205" valign="top"><table width="205" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="100" height="30"><img src="../../images/statistics/admin_ico01.gif" width="12" height="10" align="absmiddle"><span class="menu_black"><strong> 주요키워드 </strong></span></td>
							<td width="105" align="right" height="30"><img style="cursor:hand;" onclick="downExcel('1');" src="../../images/statistics/excel_save.gif"  height="24"></td>
						</tr>
					     <tr>
							<td colspan="2"><table width="205" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td colspan="2" bgcolor="#7CA5DD"><img src="../../images/statistics/brank.gif" width="1" height="2"></td>
								</tr>
								<tr>
									<td bgcolor="#CFCFCF"><table width="205"  border="0" cellspacing="1" cellpadding="1">
										<tr class="menu_black">
											<td width="135" height="30" align="center" background="../../images/statistics/statis_table_bg01.gif" style="padding: 3px 0px 0px 0px;"> <strong>키워드</strong> </td>
											<td width="70" align="center" background="../../images/statistics/statis_table_bg01.gif" style="padding: 3px 0px 0px 0px;"> <strong>건수</strong> </td>
										</tr>
			<%
			for(int i = 0; i < Keyword_data.size(); i++){
				StatisticsBean sb = (StatisticsBean)Keyword_data.get(i);
			%>                
										<tr>
											<td height="24" align="center" bgcolor="#FFFFFF" class="menu_black" style="padding: 3px 0px 0px 0px;"><%=sb.getK_value()%></td>
											<td align="center" bgcolor="#FFFFFF" class="menu_black" style="padding: 3px 0px 0px 0px;"><%=sb.getK_cnt()%><!-- a href="javascript:pop_list_view(<%=sb.getK_xp()%>,<%=sb.getK_yp()%>,<%=sb.getK_zp()%>,'',<%=sDateFrom.replaceAll("-","")%>,<%=sDateTo.replaceAll("-","")%>);"><u><%=sb.getK_cnt()%></u></a--></td>
										</tr>
			<%
			}
			%>                
									</table></td>
								</tr>
							</table></td>
						</tr>
					</table></td>
				</tr>
			</table>
			<br>
			<table width="750" border="0" cellspacing="0" cellpadding="0">
				<tr>    
					<td width="535"><img src="../../images/statistics/admin_ico01.gif" width="12" height="10" align="absmiddle"><span class="menu_black"><strong>키워드그룹별 통계</strong>&nbsp;&nbsp;&nbsp;&nbsp; * 키워드 그룹별 통계는 금일만 적용됩니다.</span></td>
					<td width="215" align="right"><img style="cursor:hand;" onclick="downExcel('2');" src="../../images/statistics/excel_save.gif"  height="24"></td>
				</tr>
				<tr>
					<td colspan="2"><table width="750" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td colspan="2" bgcolor="#7CA5DD"><img src="../../images/statistics/brank.gif" width="1" height="2"></td>
						</tr>
						<tr>
							<td colspan="2" bgcolor="#CFCFCF"><table width="750"  border="0" cellspacing="1" cellpadding="1">
								<tr>
									<td width="150" height="30" align="center" background="../../images/statistics/statis_table_bg01.gif" style="padding: 3px 0px 0px 0px;">&nbsp;</td>
			<%
			for(int i = 0; i < Site_Gname.size(); i++){
				StatisticsBean sb = (StatisticsBean)Site_Gname.get(i);
			%>            
									<td width="75" align="center" background="../../images/statistics/statis_table_bg01.gif" class="menu_black" style="padding: 3px 0px 0px 0px; font-weight: bold;"><%=sb.getSg_name()%></td>
			<%
			}
			%>              
									<td width="75" align="center" background="../../images/statistics/statis_table_bg01.gif" class="menu_black" style="padding: 3px 0px 0px 0px; font-weight: bold;">계</td>
								</tr>
			<%
			
			//금일만 적용
			sDateTo = du.getCurrentDate("yyyy-MM-dd");
			sDateFrom = du.getCurrentDate("yyyy-MM-dd");
			mMgr.getMaxMinNo(sDateFrom,sDateTo);
			minNo = mMgr.msMinNo;
			maxNo = mMgr.msMaxNo;
			
			for(int i = 0; i < Str_Keyword_name.size(); i++){
				StatisticsBean sb = (StatisticsBean)Str_Keyword_name.get(i);
			%>            
								<tr>
									<td height="28" align="center" bgcolor="#FFFFFF" class="menu_black" style="padding: 3px 0px 0px 0px;"><%=sb.getK_value()%></td>
			<%
				Site_group = staticMgr.get_site_group_data(minNo,maxNo,sb.getK_xp());
				int cnt = 0;
				for(int j = 0; j < Site_group.size(); j++){
					StatisticsBean sb1 = (StatisticsBean)Site_group.get(j);
			%>              
									<td align="center" bgcolor="#FFFFFF" style="padding: 3px 0px 0px 0px;">
			<%
			if(sb1.getK_cnt().equals("0")){
			%>              
			              	<%=sb1.getK_cnt()%>
			<%
			}else{
			%>
			              	<%//out.println("<a href=\"javascript:pop_list_view("+sb.getK_xp()+",'','',"+sb1.getSg_seq()+",'"+sDateFrom.replaceAll("-","")+"','"+sDateTo.replaceAll("-","")+"');\"><u>"+sb1.getK_cnt()+"</u></a>");%>
			              	<%out.println(sb1.getK_cnt());%>
			<%
			}
			%>          	
									</td>
			<%
				}
				get_site_group_data_sum = staticMgr.get_site_group_data_sum(minNo,maxNo,sb.getK_xp());	
			%>
									<td align="center" bgcolor="#FFFFFF" style="padding: 3px 0px 0px 0px;">
			<%
			if(get_site_group_data_sum.equals("0")){
			%>			  
						  	<%=get_site_group_data_sum%>
			<%
			}else{
			%>
						  	<%//out.println("<a href=\"javascript:pop_list_view("+sb.getK_xp()+",'','','','"+sDateFrom.replaceAll("-","")+"','"+sDateTo.replaceAll("-","")+"');\"><u>"+get_site_group_data_sum+"</u></a>");%>
						  	<%out.println(get_site_group_data_sum);%>
						  	
			<%
			
			}
			%>	  	
									</td>
								</tr>
			<%            
			}
			%>            
							</table></td>
						</tr>
					</table></td>
				</tr>
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