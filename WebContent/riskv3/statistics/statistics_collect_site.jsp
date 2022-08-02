<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ include file="../inc/sessioncheck.jsp" %>    
<%@ page import="	java.util.ArrayList
					,risk.util.ParseRequest
					,risk.util.ConfigUtil
					,risk.util.StringUtil
					,risk.util.DateUtil
					,risk.search.MetaMgr 
					,risk.statistics.StatisticsBean
					,risk.statistics.StatisticsMgr
					,risk.admin.site.SiteBean
                 	" %>

<%
	DateUtil   du = new DateUtil();
	ParseRequest  pr = new ParseRequest(request);
	StatisticsMgr staticMgr = new StatisticsMgr();
	StringUtil su = new StringUtil();
	ArrayList arrStatic01 = new ArrayList();
	ArrayList arrStatic02 = new ArrayList();
	
	String nowTime = du.getCurrentDate("yyyy.MM.dd HH:mm");
	
	String sCurrDate = du.getCurrentDate("yyyy-MM-dd");
	String sDateFrom ="";
	String sDateTo ="";
	
	sDateFrom = pr.getString("sDateFrom");
	sDateTo = pr.getString("sDateTo");
	
	
	if(sDateFrom.length()<1){
		sDateFrom = du.getCurrentDate("yyyy-MM-dd");
		sDateTo = sDateFrom;
	}
	
	// 사이트그룹별 수집 건수를 가져온다
	arrStatic01 = staticMgr.getSiteGroupAnalysis(sDateFrom,sDateTo);
		
%>

<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="../css/basic.css" type="text/css">
<link rel="stylesheet" href="../../css/base.css" type="text/css">
<script language="JavaScript" type="text/JavaScript">
<!--
	sDateFrom = '<%=sDateFrom%>';
	sDateTo = '<%=sDateTo%>';
	sCurrDate = '<%=sCurrDate%>';
	
	function GoStatics(){
		SSend.action='static_collect_site.jsp';
		SSend.submit();
	}

	function goSGroup(val){
		SSend.sGroup.value=val;
		SSend.action='inc_site_collect.jsp';
		SSend.target='isc';
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
    
    function openList( pisgSeq )
	{
		// 통계별 구분을 위한 변수
		var staticType = 2;
		frm = document.frmPop;
		var url = 'pop_static_list.jsp?staticType='+staticType+'&sDateFrom='+frm.sDateFrom.value+'&sDateTo='+frm.sDateTo.value+'&sgSeq='+pisgSeq;
		window.open(url, 'pop_staticList', 'width=650,height=530');
	
	}
	
//-->
</script>

</head>
<body style="margin-left: 15px">
<form name="frmPop" action="" method="post">
<input type="hidden" name="sDateFrom" value="<%=sDateFrom%>">
<input type="hidden" name="sDateTo" value="<%=sDateTo%>">
</form>
<form name="SSend" action="" method="post">
<input type="hidden" name="sGroup" value="">
<table style="width:100%;height:100%;" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="body_bg_top" valign="top">
			<table width="820" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="tit_bg" style="height:67px;padding-top:20px;">
					<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td><img src="../../images/statistics/tit_icon.gif" /><img src="../../images/statistics/tit_0403.gif" /></td>
							<td align="right">
							<table border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td class="navi_home">HOME</td>
									<td class="navi_arrow">통계관리</td>
									<td class="navi_arrow2">수집사이트통계</td>
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
			<table width="780"  border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="center">
						<table width="750" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td height="24"><img src="../../images/statistics/collect_title03.gif" width="156" height="17"></td>
								<td align="right"><%=nowTime%> 현재(수집건수는 당일 기준임)</td>
							</tr>
						</table>
						<table width="750" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td colspan="2" bgcolor="#7CA5DD"><img src="../../images/statistics/brank.gif" width="1" height="2"></td>
							</tr>
							<tr>
								<td colspan="2" bgcolor="#CFCFCF">
									<table width="100%"  border="0" cellspacing="1" cellpadding="1">
										<tr>
											<td width="150" height="28" align="center" background="../../images/statistics/statis_table_bg01.gif" style="padding: 3px 0px 0px 0px;"><strong>구분</strong></td>
											<td width="148" align="center" background="../../images/statistics/statis_table_bg01.gif" style="padding: 3px 0px 0px 0px;"><strong>사이트수</strong></td>
							                <td width="148" align="center" background="../../images/statistics/statis_table_bg01.gif" style="padding: 3px 0px 0px 0px;"><strong>관심 정보 건수*</strong></td>
							                <td width="148" align="center" background="../../images/statistics/statis_table_bg01.gif" style="padding: 3px 0px 0px 0px;"><strong>전체 수집 건수</strong></td>
							                <td width="150" align="center" background="../../images/statistics/statis_table_bg01.gif" style="padding: 3px 0px 0px 0px;"><strong>관심정보 비율</strong></td>
										</tr>
			<%
				int[] arrInt = new int[3];
				//String[] arrString;
				String avg = "";
				String Stotal = "";
				double total = 0;
				StatisticsBean staBean;
				
				if(arrStatic01.size()>0){
				
				for(int i=0; i<arrStatic01.size(); i++){
					staBean = (StatisticsBean)arrStatic01.get(i);	
					
					arrInt[0] += staBean.getSite_ct();
					arrInt[1] += staBean.getFavor_ct();
					arrInt[2] += staBean.getTotal_ct();
					
					total += staBean.getAvg();
					String[] arrString = (Double.toString(staBean.getAvg())).split("[.]");
					avg = arrString[0]+"."+arrString[1].substring(0,1);
			%>                        
										<tr>
							                <td height="23" align="center" bgcolor="#FFFFFF" style="padding: 3px 0px 0px 0px;"><a href="javascript:goSGroup('<%=staBean.getSg_seq()%>')"><%=staBean.getSg_name()%></a></td>
							                <td align="center" bgcolor="#FFFFFF" style="padding: 3px 0px 0px 0px;"><%=su.digitFormat(staBean.getSite_ct())%></td>
							                <td align="center" bgcolor="#FFFFFF" style="padding: 3px 0px 0px 0px;"><%=su.digitFormat(staBean.getFavor_ct())%></td>
							                <td align="center" bgcolor="#FFFFFF" style="padding: 3px 0px 0px 0px;"><%=su.digitFormat(staBean.getTotal_ct())%></td>
							                <td align="center" bgcolor="#FFFFFF" style="padding: 3px 0px 0px 0px;"><%=avg%>%</td>
										</tr>
			<%
				}
					total = total/arrStatic01.size();		
					
					String[] arrString = (Double.toString(total)).split("[.]");
					System.out.println("arrString[0]:"+arrString[0]);
					System.out.println("arrString[1]:"+arrString[1]);
					Stotal = arrString[0]+"."+arrString[1].substring(0,1);
					
				%>              
			            
										<tr>
							                <td width="150" height="28" align="center" background="../../images/statistics/statis_table_bg01.gif" style="padding: 3px 0px 0px 0px;"><strong>계</strong></td>
							                <td width="148" align="center" background="../../images/statistics/statis_table_bg01.gif" style="padding: 3px 0px 0px 0px;"><strong><%=su.digitFormat(arrInt[0])%></strong></td>
							                <td width="148" align="center" background="../../images/statistics/statis_table_bg01.gif" style="padding: 3px 0px 0px 0px;"><strong><%=su.digitFormat(arrInt[1])%></strong></td>
							                <td width="148" align="center" background="../../images/statistics/statis_table_bg01.gif" style="padding: 3px 0px 0px 0px;"><strong><%=su.digitFormat(arrInt[2])%></strong></td>
							                <td width="150" align="center" background="../../images/statistics/statis_table_bg01.gif" style="padding: 3px 0px 0px 0px;"><strong><%=Stotal%>%</strong></td>
										</tr>
			    <%
				}
			    %>
									</table></td>
								</tr>
								<tr>
									<td height="30">* 관심정보 건수는 수집된 문서중 키워드를 포함하고 있는 문서의 개수 입니다.</td>
									<td align="right"><!--<img src="../../images/statistics/statis_btn_excel.gif" width="94" height="24">--></td>
								</tr>
							</table>
							<br>
							<table width="750" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td height="30"><img src="../../images/statistics/collect_title04.gif" width="127" height="17"></td>
									<td align="right"><!--<img src="../../images/statistics/statis_btn_excel.gif" width="94" height="24">--></td>
								</tr>
							</table>
							<table width="750" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td bgcolor="#CFCFCF" width="100%">
										<iframe name="isc" src="inc_site_collect.jsp" frameborder="0" border="0" style="width:100%; height:215px;" scrolling="no"></iframe>
									</td>
								</tr>
								<tr>
									<td bgcolor="#CFCFCF"><img src="../../images/statistics/brank.gif" width="1" height="1"></td>
								</tr>
								<tr>
									<td>&nbsp;</td>
								</tr>
							</table>     
						</td>
					</tr>
					<tr height="50px"><td></td></tr>
			</table>
		</td>
	</tr>
</table>
</form>
<!--<blockquote>&nbsp;</blockquote>-->
</body>
</html>