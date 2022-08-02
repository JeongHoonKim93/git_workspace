<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="
				 java.util.ArrayList
				 ,risk.util.ParseRequest
				 ,risk.util.ConfigUtil
				 ,risk.util.StringUtil
				 ,risk.util.DateUtil
				 ,risk.issue.IssueMgr
				 ,risk.issue.IssueDataBean
				 " 
%>
<%@include file="../inc/sessioncheck.jsp" %>
<%
	ParseRequest pr = new ParseRequest(request);
	IssueMgr iMgr = new IssueMgr();
	StatisticsMgr sMgr = new StatisticsMgr();
	DateUtil du = new DateUtil();
	ConfigUtil cu = new ConfigUtil();
	StringUtil su = new StringUtil();
		
	pr.printParams();

	String imgUrl = "";
	String siteUrl = cu.getConfig("URL");
	imgUrl = siteUrl+"riskv3/statistics/images/";
	
	String sDateFrom = "";
	String sDateFromM1 = "";
	String sDateFromM2 = "";	
	
	String DateM1 = "";
	String DateM2 = "";
	
	String sg_seq = "1,2,3,4,5,6,7,8,9,10,11,12,13";
	
	String goobun2 = "1,2,3,4,5,6,7,8,9";
	
	sDateFrom = pr.getString("sDateFrom","").trim();
	if(sDateFrom.equals("")){
		sDateFrom = du.getCurrentDate("yyyy-MM-dd");
	}
		sDateFromM1 = du.addDay(sDateFrom,-1); //어제 날짜
		sDateFromM2 = du.addDay(sDateFrom,-2); //그제 날짜

	//분류된 최근 주요 기사를 가져온다.
	ArrayList arrIssueData = iMgr.getIssueDataList(1,5,"","","","","","2",sDateFrom,"",sDateFrom,"","","Y");	

	//기상도를 가져온다
	ArrayList weather01 = sMgr.getDailyIssueWeatherAll(sDateFrom, sg_seq); //금일
	ArrayList weather02 = sMgr.getDailyIssueWeatherAll(sDateFromM1, sg_seq); //어제
	ArrayList weather03 = sMgr.getDailyIssueWeatherAll(sDateFromM2, sg_seq);//그제
	
	DateM1 = sDateFromM1.substring(5,sDateFromM1.length()).replaceAll("-","/");
	DateM2 = sDateFromM2.substring(5,sDateFromM2.length()).replaceAll("-","/");
%>

<%@page import="risk.statistics.StatisticsMgr"%><html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="css/basic.css" type="text/css">
<link rel="stylesheet" href=css/calendar.css" type="text/css">
<style type="text/css">
.title_blueBIG {

	FONT-SIZE: 26px;
	COLOR: #106BEA;
	LINE-HEIGHT: normal;
	FONT-FAMILY: Arial, Helvetica, sans-serif;
	font-weight: bold;
}
.title_redBIG {
	FONT-SIZE: 26px;
	COLOR: #FF0000;
	LINE-HEIGHT: normal;
	FONT-FAMILY: Arial, Helvetica, sans-serif;
	font-weight: bold;
}
.title_blueBIG01 {

	FONT-SIZE: 20px;
	COLOR: #106BEA;
	LINE-HEIGHT: normal;
	FONT-FAMILY: Arial, Helvetica, sans-serif;
	font-weight: bold;
}
.title_redBIG01 {

	FONT-SIZE: 20px;
	COLOR: #FF0000;
	LINE-HEIGHT: normal;
	FONT-FAMILY: Arial, Helvetica, sans-serif;
	font-weight: bold;
}
.title_blueBIG02 {

	FONT-SIZE: 14px;
	COLOR: #106BEA;
	LINE-HEIGHT: normal;
	FONT-FAMILY: Arial, Helvetica, sans-serif;
	font-weight: bold;
}
.title_redBIG02 {

	FONT-SIZE: 14px;
	COLOR: #FF0000;
	LINE-HEIGHT: normal;
	FONT-FAMILY: Arial, Helvetica, sans-serif;
	font-weight: bold;
}
.title_blueBIG03 {

	FONT-SIZE: 12px;
	COLOR: #106BEA;
	LINE-HEIGHT: normal;
	FONT-FAMILY: Arial, Helvetica, sans-serif;
	font-weight: bold;
}
.title_redBIG03 {

	FONT-SIZE: 12px;
	COLOR: #FF0000;
	LINE-HEIGHT: normal;
	FONT-FAMILY: Arial, Helvetica, sans-serif;
	font-weight: bold;
}
</style>
<script language="JavaScript" type="text/JavaScript">
<!--
    function GoSubmit(){
		document.fSend.action = '';
		document.fSend.target = '';
		document.fSend.submit();
    }

	function openListMore(sDateFrom, important)
	{
		var f = document.fSend;
		var url = '../issue/issue_data_list.jsp?sDateFrom='+sDateFrom+'&sDateTo='+sDateFrom+'&important='+important;
		window.open(url, 'openList', 'width=670,height=530');
	
	}
	
	function openList(sDateFrom, sunghyang, guboon2)
	{
		/*
		//alert('sDateFrom:'+sDateFrom+'/sunghyang:'+sunghyang+'/guboon2:'+guboon2);
		var f = document.fSend;
		var url = '../issue/issue_data_list?sDateFrom='+sDateFrom+'&sDateTo='+sDateFrom+'&sunghyang='+sunghyang+'&guboon2='+guboon2+'&sg_seq='+f.sg_seq.value;
		window.open(url, 'openList', 'width=670,height=530');
	*/
	}

//-->
</script>
</head>
<body leftmargin="10" topmargin="10" marginwidth="0" marginheight="0">
<DIV id="div_show" style="FONT-SIZE: 12px; PADDING-RIGHT: 5px; DISPLAY: none; PADDING-LEFT: 5px; LEFT: 140px;
                        width:500px;height:50px;
                        PADDING-BOTTOM: 5px; PADDING-TOP: 5px; POSITION: absolute; BACKGROUND-COLOR: #FFFFCC; border: #ff6600 2px solid;">
</DIV>
<form name="fSend" method="post">
<input name="sg_seq" type="hidden" value="<%=sg_seq%>">
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
<table width="780" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="40" background="images/top_title_bg.gif"><img src="images/issue_top_title04_001.gif"></td>
  </tr>
  <tr>
    <td><img src="images/brank.gif" width="1" height="15"></td>
  </tr>
</table>
<table width="750" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="37" align="center" background="images/collect_tbg02_b.gif"><table width="720" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="12" style="padding: 2px 0px 0px 0px;"><img src="images/issue_t_ico02.gif" width="7" height="13" align="absmiddle"></td>
              <td width="70" style="padding: 5px 0px 0px 0px;"><strong>검색기간 :</strong></td>
              <td width="220">
              <input name="sDateFrom" type="text" size="10"  maxlength="10" class="calendar" onChange="chkDate(this)" onFocus="this.select();" onClick="this.select();" value="<%=sDateFrom%>">
              <td>
              </td>
              <td align="right"><img style="cursor:hand;" onclick="GoSubmit();" src="images/statis_btn02_b.gif" width="80" height="24" align="absmiddle">&nbsp;</td>
            </tr>
          </table></td>
        </tr>       
        <tr>
          <td style="padding: 5px 0px 0px 0px;"></td>
        </tr>
        
      </table>
      
  <tr>
    <td style="padding: 5px 0px 0px 0px;" valign="top" background="images/p_bg01.gif">
    <table width="730" border="0" cellspacing="0" cellpadding="0">
    	<tr>
          <td align="center" valign="top"><table width="690" border="0" cellspacing="0" cellpadding="0">
          </table>
            <table width="690" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><img src="images/brank.gif" width="1" height="15"></td>
              </tr>
            </table>
            <table width="690" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="22" colspan="4" >
                <table width="690" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td ><font style="font-size:11px;COLOR:#999999;"><img src="<%=imgUrl%>weather2_s_01.gif" align="absmiddle">긍정80% 이상 &nbsp;&nbsp;<img src="<%=imgUrl%>weather2_s_02.gif" align="absmiddle">긍정60%~80% &nbsp;&nbsp;<img src="<%=imgUrl%>weather2_s_03.gif" align="absmiddle">긍정50%~60% &nbsp;&nbsp;<img src="<%=imgUrl%>weather2_s_04.gif" align="absmiddle">긍정30%~50% &nbsp;&nbsp;<img src="<%=imgUrl%>weather2_s_05.gif" align="absmiddle">긍정30% 이하 &nbsp;&nbsp;<img src="<%=imgUrl%>weather2_s_06.gif" align="absmiddle">5건이하 판단유보</font></td>
                  </tr>
                  <tr>
                   <td><img src="images/brank.gif" width="1" height="5"></td>
                  </tr>
                </table></td>
              </tr>
              <%
              String[] total = null;
              String tCnt = "";
              String positive = "";
              String negative = "";
              String posPer = "";
              String weatherImg = ""; 
              
              String[] sWeather1 = null;
              String[] sWeather2 = null;
              String sWeatherImg1 = "";
              String sWeatherImg2 = "";
              String sPositive1 = "";
              String sPositive2 = "";
              String sNegative1 = "";
              String sNegative2 = "";
              String sWeatherper1 = "";
              String sWeatherper2 = "";

              if(weather01!=null && weather01.size()>0){
                  
              	total = (String[])weather01.get(weather01.size()-1);
              	 
              	if(total!=null){
              		
              		tCnt = total[3];
              		positive = total[0];
              		negative = total[1];
              		posPer = total[4];
              		if(posPer.equals("")){
              			posPer = "0";	
              		}
						double perCent = Double.parseDouble(posPer);

						if(Integer.parseInt(tCnt)<6){weatherImg = "weather2_06.gif";}
						else if(perCent>80){weatherImg = "weather2_01.gif";}
						else if(perCent>60){weatherImg = "weather2_02.gif";}
						else if(perCent>50){weatherImg = "weather2_03.gif";}
						else if(perCent>30){weatherImg = "weather2_04.gif";}
						else{weatherImg = "weather2_05.gif";}

              	}
              	
              }
             
              	if(weather02!=null && weather02.size()>0){
              		sWeather1 = (String[])weather02.get(weather02.size()-1);
              		if(sWeather1!=null){
              			sPositive1 = sWeather1[0];
              			sNegative1 = sWeather1[1];
              			sWeatherper1 = sWeather1[4];
              			
              			if(sWeatherper1.equals("")){
              				sWeatherper1 = "0";	
                  		}
                  		
    						double perCent = Double.parseDouble(sWeatherper1);
    						if(Integer.parseInt(sWeather1[3])<6){sWeatherImg1 = "weather2_s_06.gif";}
    						else if(perCent>80){sWeatherImg1 = "weather2_s_01.gif";}
    						else if(perCent>60){sWeatherImg1 = "weather2_s_02.gif";}
    						else if(perCent>50){sWeatherImg1 = "weather2_s_03.gif";}
    						else if(perCent>30){sWeatherImg1 = "weather2_s_04.gif";}
    						else{sWeatherImg1 = "weather2_s_05.gif";}
                  	}
              	}
              	
              	if(weather03!=null && weather03.size()>0){
              		sWeather2 = (String[])weather03.get(weather03.size()-1);
              		if(sWeather2!=null){
              			sPositive2 = sWeather2[0];
              			sNegative2 = sWeather2[1];
              			sWeatherper2 = sWeather2[4];
              			
              			if(sWeatherper2.equals("")){
              				sWeatherper2 = "0";	
                  		}
                  		
    						double perCent = Double.parseDouble(sWeatherper2);
    						if(Integer.parseInt(sWeather2[3])<6){sWeatherImg2 = "weather2_s_06.gif";}
    						else if(perCent>80){sWeatherImg2 = "weather2_s_01.gif";}
    						else if(perCent>60){sWeatherImg2 = "weather2_s_02.gif";}
    						else if(perCent>50){sWeatherImg2 = "weather2_s_03.gif";}
    						else if(perCent>30){sWeatherImg2 = "weather2_s_04.gif";}
    						else{sWeatherImg2 = "weather2_s_05.gif";}
                  	}
              	}
              %>
              <tr valign="top">
                <td><table width="310" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td height="26" background="images/weather_tbg01.gif" class="title_blackB" style="padding: 4px 0px 0px 25px; cursor:hand;" onClick="openList('<%=sDateFrom%>',0,'<%=goobun2%>');"><strong>전체 (<%=tCnt%>)</strong></td>
                  </tr>
                  <tr>
                    <td><img src="images/weather_timg01.gif" width="310" height="5"></td>
                  </tr>
                  <tr>
                    <td align="center" valign="top" background="images/weather_tbg02.gif"><table width="270" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="110" align="center"><table width="100" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td align="center"><img src="<%=imgUrl+weatherImg%>" width="85" height="70" style="cursor:hand;" onClick="openList('<%=sDateFrom%>',0,'<%=goobun2%>');"></td>
                          </tr>
                          <tr>
                            <td height="25" align="center">긍정 <%=posPer%>%</td>
                          </tr>
                        </table></td>
                        <td width="160" align="right"><table width="140" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td bgcolor="#DCDDE0"><table width="140" border="0" cellspacing="1" cellpadding="1">
                              <tr align="center">
                                <td bgcolor="#F9FAFB">긍정</td>
                                <td width="70" bgcolor="#F3F5F7" class="title_blueBIG02" style="cursor:hand;" onClick="openList('<%=sDateFrom%>',1,'<%=goobun2%>');"><%=positive%></td>
                              </tr>
                              <tr align="center">
                                <td bgcolor="#F9FAFB">부정</td>
                                <td width="70" bgcolor="#F3F5F7" class="title_redBIG02"  style="cursor:hand;" onClick="openList('<%=sDateFrom%>',2,'<%=goobun2%>');"><%=negative%></td>
                              </tr>
                            </table></td>
                          </tr>
                        </table></td>
                      </tr>
                      <tr>
                        <td colspan="2"><table width="270" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td colspan="5"><img src="images/weather_tline01.gif" width="270" height="1"></td>
                          </tr>
                          
                          <tr>
                            <td width="10" align="center"><img src="images/cen01_ico06.gif" width="5" height="8"></td>
                            <td width="50"  style="padding: 3px 0px 0px 0px;cursor:hand;"  onClick="openList('<%=sDateFromM1%>',0,'<%=goobun2%>');" > <%=DateM1%></td>
                            <td width="67"><img src="<%=imgUrl+sWeatherImg1%>" width="30" height="23" alt="긍정 : <%=sWeatherper1%>%" style="cursor:hand;"  onClick="openList('<%=sDateFromM1%>',0,0);"></td>
                            <td width="143" align="center" style="padding: 3px 0px 0px 0px;" >
                            <a href="#" onClick="openList('<%=sDateFromM1%>',1,'<%=goobun2%>');"><font color="blue"><%=sPositive1%></font></a>/<a href="#" onClick="openList('<%=sDateFromM1%>',2,'<%=goobun2%>');"><font color="red"><%=sNegative1%></font></a></td>
                          </tr>
                          <tr>
                            <td colspan="5"><img src="images/weather_tline01.gif" width="270" height="1"></td>
                          </tr>
                            
                          <tr>
                            <td width="10" align="center"><img src="images/cen01_ico06.gif" width="5" height="8"></td>
                            <td width="50"  style="padding: 3px 0px 0px 0px;cursor:hand;"  onClick="openList('<%=sDateFromM2%>',0,'<%=goobun2%>');"> <%=DateM2%></td>
                            <td width="67"><img src="<%=imgUrl+sWeatherImg2%>" width="30" height="23" alt="긍정 : <%=sWeatherper2%>%" style="cursor:hand;"  onClick="openList('<%=sDateFromM2%>',0,0);"></td>
                            <td width="143" align="center" style="padding: 3px 0px 0px 0px;" >
                            <a href="#" onClick="openList('<%=sDateFromM2%>',1,'<%=goobun2%>');"><font color="blue"><%=sPositive2%></font></a>/<a href="#" onClick="openList('<%=sDateFromM2%>',2,'<%=goobun2%>');"><font color="red"><%=sNegative2%></font></a></td>
                          </tr>
                        </table></td>
                      </tr>
                    </table></td>
                  </tr>
                  <tr>
                    <td><img src="images/weather_timg02.gif" width="310" height="9"></td>
                  </tr>
                </table></td>
                <td align="left">
                
                <!-- 포털 초기면 레이어 -->
                <div id="list_layer01" style="DISPLAY: yes;">
                <table width="357" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td>
					<table width="100%" height="31" border="0" cellpadding="0" cellspacing="0">
                      <tr>
                        <td align="left" background="images/cen01_tab01_on.gif"  style="padding: 10px 0px 0px 10px;">
                        <table width="135" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="10"><img src="images/cen01_ico05.gif" width="7" height="4"></td>
                            <td class="title_blackB" style="cursor:hand;"><strong>최근 주요기사</strong></td>
                          </tr>
                        </table></td>
                      </tr>
                    </table>
                    </td>
                  </tr>
                  <tr align="center" valign="top">
                    <td colspan="3" background="images/cen01_table_bg02.gif">
                    <table width="317" border="0" cellspacing="0" cellpadding="0">
                    	<!-- tr>
                    		<td align="right" colspan="3" style="padding: 0 26 0 0;"><img src="images/subcen_more.gif" width="38" height="12" style="cursor:hand;" onClick="openListMore('<%=sDateFrom%>','1');"></td>
                    	</tr-->
                   <%
                   //최근 주요 기사
                   IssueDataBean idb = null;
                   if(arrIssueData!=null){
                	   for(int i=0; i<arrIssueData.size(); i++){
                		   if(i==6){ break; }

                		   idb = (IssueDataBean)arrIssueData.get(i);
                		   
                   %> 	
                    <tr>
                        <td width="7" height="20" style="padding: 0px 0px 3px 0px;"><img src="images/cen01_ico07.gif" width="3" height="3"></td>
                        <td width="240" ><nobr style ="text-overflow:ellipsis; overflow:hidden; width:230" title="<%=idb.getId_title()%>">[<%=idb.getMd_site_name()%>]<a href="<%=idb.getId_url()%>" target="_blank"><%=idb.getId_title()%></a></nobr></td>
                        <td width="70" align="right"><span class="t">|<%=idb.getFormatId_regdate("MM:dd HH:mm")%></span> </td>
                      </tr>
                      
                      <%if(i==arrIssueData.size()-1 || i==5){ %>
                      <%}else{ %>
                      <tr>
                        <td colspan="3"><img src="images/cen01_table_line01.gif" width="317" height="1"></td>
                      </tr>
                      <%} %>
                      <%
                	   }
                   }else{
                      %>
                      <tr>
                        <td width="7" height="20" style="padding: 0px 0px 3px 0px;"><img src="images/cen01_ico07.gif" width="3" height="3"></td>
                        <td colspan="2">검색된 내용이 없습니다.</td>
                      </tr>
                      <%
                   }
                      %>
                      </table>
                    </td>
                  </tr>
                  <tr valign="bottom">
                    <td colspan="3"  background="images/cen01_table_bg02.gif"><img src="images/brank.gif" width="1" height="2"></td>
                  </tr>
                  <tr valign="bottom">
                    <td colspan="3"  background="images/cen01_table_bg02.gif"><img src="images/cen01_table_img03.gif" width="357" height="13"></td>
                  </tr>
                </table>
                </div>
                <!-- 포털 초기면 레이어 끝 -->
                </td>
              </tr>
              <tr>
                <td colspan="2">&nbsp;</td>
              </tr>
            </table>
            <table width="690" border="0" cellspacing="0" cellpadding="0">
              <tr>
              
              <!--  각각의 이슈 기상도 시작 -->
              <%
			%>
               
                
                <%
                	String[] sWeather01 = null;
                	String[] sWeather02 = null;
                	String[] sWeather03 = null;
                	
                	for(int i=0; i<weather01.size()-1; i++){
                		weatherImg = "";
                		sWeatherImg1 = "";
                        sWeatherImg2 = "";
                        
                		if(i!=0 && i%3==0){out.print("</tr>");
                		 if(i!=weather01.size()-1){out.print("<tr height='10'><td>&nbsp;</td></tr> <tr>");}
                		}
                		
                		sWeather01 = (String[])weather01.get(i);
                		
                		if(sWeather01!=null){
                			
                			double perCent = Double.parseDouble(sWeather01[3]);
    						if(Integer.parseInt(sWeather01[4])<6){weatherImg = "weather2_06.gif";}
    						else if(perCent>80){weatherImg = "weather2_01.gif";}
    						else if(perCent>60){weatherImg = "weather2_02.gif";}
    						else if(perCent>50){weatherImg = "weather2_03.gif";}
    						else if(perCent>30){weatherImg = "weather2_04.gif";}
    						else{weatherImg = "weather2_05.gif";}
                		}
                		
                		//어제
                		sWeather02 = (String[])weather02.get(i);
						if(sWeather02!=null){
                			
							double perCent = Double.parseDouble(sWeather02[3]);
    						if(Integer.parseInt(sWeather02[4])<6){sWeatherImg1 = "weather2_s_06.gif";}
    						else if(perCent>80){sWeatherImg1 = "weather2_s_01.gif";}
    						else if(perCent>60){sWeatherImg1 = "weather2_s_02.gif";}
    						else if(perCent>50){sWeatherImg1 = "weather2_s_03.gif";}
    						else if(perCent>30){sWeatherImg1 = "weather2_s_04.gif";}
    						else{sWeatherImg1 = "weather2_s_05.gif";}
                		}
                		//그제
						//어제
                		sWeather03 = (String[])weather03.get(i);
						if(sWeather03!=null){
                			
							double perCent = Double.parseDouble(sWeather03[3]);
    						if(Integer.parseInt(sWeather03[4])<6){sWeatherImg2 = "weather2_s_06.gif";}
    						else if(perCent>80){sWeatherImg2 = "weather2_s_01.gif";}
    						else if(perCent>60){sWeatherImg2 = "weather2_s_02.gif";}
    						else if(perCent>50){sWeatherImg2 = "weather2_s_03.gif";}
    						else if(perCent>30){sWeatherImg2 = "weather2_s_04.gif";}
    						else{sWeatherImg2 = "weather2_s_05.gif";}
                		}
                		
                		
                %>
                 <td>
                <table width="220" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td height="26" background="images/weather_tbg03.gif" class="title_blackB" style="padding: 4px 0px 0px 27px;cursor:hand;" onClick="openList('<%=sDateFrom%>',0,<%=sWeather01[5]%>);" ><strong><%=sWeather01[2]%> (<%=sWeather01[4]%>)</strong></td>
                  </tr>
                  <tr>
                    <td><img src="images/weather_timg03.gif" width="220" height="5"></td>
                  </tr>
                  <tr>
                    <td align="center" valign="top" background="images/weather_tbg04.gif"><table width="192" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="92" align="center" valign="top"><table width="90" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td align="center"><img src="<%=imgUrl+weatherImg%>" width="85" height="70" style="cursor:hand;" onClick="openList('<%=sDateFrom%>',0,<%=sWeather01[5]%>);"></td>
                          </tr>
                          <tr>
                            <td height="25" align="center">긍정 <%=sWeather01[3]%>%</td>
                          </tr>
                        </table></td>
                        <td width="100"><table width="100" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td bgcolor="#DCDDE0">
                            <table width="100" border="0" cellspacing="1" cellpadding="1">
                                <tr valign="middle">
                                  <td align="center" width="30" bgcolor="#F9FAFB" style="font-size:11px;font-color:#666666;">긍정</td>
                                  <td align="center" bgcolor="#F3F5F7">
                                  <span  class="title_blueBIG02" style="cursor:hand" onClick="openList('<%=sDateFrom%>','1','<%=sWeather01[5]%>');"><%=sWeather01[0]%></span></td>
                                </tr>
                                <tr valign="middle">
                                  <td align="center" width="30" bgcolor="#F9FAFB" style="font-size:11px;font-color:#666666;">부정</td>
                                  <td align="center" bgcolor="#F3F5F7" >
                                  <span  class="title_redBIG02" style="cursor:hand" onClick="openList('<%=sDateFrom%>','2','<%=sWeather01[5]%>');"><%=sWeather01[1]%></span></td>
                                </tr>
                            </table></td>
                          </tr>
                        </table></td>
                      </tr>
                      <tr align="center">
                        <td colspan="2"><table width="192" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td colspan="4"><img src="images/weather_tline02.gif" width="192" height="1"></td>
                          </tr>
<%
%>                            
                          <tr>
                            <td width="10" align="center"><img src="images/cen01_ico06.gif" width="5" height="8"></td>
                            <td width="40"  style="padding: 3px 0px 3px 0px;cursor:hand;" onClick="openList('<%=sDateFromM1%>',0,<%=sWeather01[5]%>);"><%=DateM1%></td>
                            <td width="30"><img src="<%=imgUrl+sWeatherImg1%>" width="30" height="23" alt="긍정 : <%=sWeather02[3]%>%" style="cursor:hand;" onClick="openList('<%=sDateFromM1%>',0,<%=sWeather01[5]%>);"></td>
                            <td width="153" align="center" style="padding: 3px 0px 0px 3px;"  align="center">
                            <a href="#" onClick="openList('<%=sDateFromM1%>',1,<%=sWeather01[5]%>);"><font color="blue"><%=sWeather02[0]%></font></a>/<a href="#" onClick="openList('<%=sDateFromM1%>',2,<%=sWeather01[5]%>);"><font color="red"><%=sWeather02[1]%></font></a></td>
                          </tr>
                            <tr>
                            <td colspan="4"><img src="images/weather_tline02.gif" width="192" height="1"></td>
                          </tr>
<%
%>                            
                          <tr>
                            <td width="10" align="center"><img src="images/cen01_ico06.gif" width="5" height="8"></td>
                            <td width="40"  style="padding: 3px 0px 3px 0px;cursor:hand;" onClick="openList('<%=sDateFromM2%>',0,<%=sWeather01[5]%>);"><%=DateM2%></td>
                            <td width="30"><img src="<%=imgUrl+sWeatherImg2%>" width="30" height="23" alt="긍정 : <%=sWeather03[3]%>%"  style="cursor:hand;" onClick="openList('<%=sDateFromM2%>',0,<%=sWeather01[5]%>);"></td>
                            <td width="153" align="center" style="padding: 3px 0px 0px 3px;" align="center" >
                            <a href="#" onClick="openList('<%=sDateFromM2%>',1,<%=sWeather01[5]%>);"><font color="blue"><%=sWeather03[0]%></font></a>/<a href="#" onClick="openList('<%=sDateFromM2%>',2,<%=sWeather01[5]%>);"><font color="red"><%=sWeather03[1]%></font></a></td>
                          </tr>
                        </table></td>
                      </tr>
                    </table></td>
                  </tr>
                  <tr>
                    <td colspan="4"><img src="images/weather_timg04.gif" width="220" height="10"></td>
                  </tr>
                </table>
                </td>
                
                <%
                	
                }
                %>
                
                
			</tr>
			<tr>
                    <td colspan="4" align="right" style="padding:5 10 0 0;"><font style="font-size:12px;COLOR:#999999;">※기상도의 카운트는 유사기사를 포함한 카운트 입니다.</font></td>
                  </tr>
			<tr>
				<td colspan="3" height="10">&nbsp;</td>
			</tr>
			<tr>
					<td></td>
                <!--  각각의 이슈 기상도 끝 -->
              </tr>
            </table>
            </td>
        </tr>
        
      </table>
      <table width="730" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="20" >&nbsp;</td>
        </tr>
        <tr>
          <td></td>
        </tr>
      </table></td>
  </tr>
</table>
</form>
</body>
</html>
