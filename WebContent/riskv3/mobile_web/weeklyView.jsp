<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@include file="../inc/sessioncheck.jsp" %>
<%@ page import="java.util.ArrayList
                 ,risk.util.Log
                 ,risk.util.StringUtil
                 ,risk.util.ParseRequest
                 ,risk.util.DateUtil
                 ,risk.util.ConfigUtil
                 ,risk.dashboard.dashboardMgr
                 ,risk.dashboard.dashboardSuperBean
                 ,risk.dashboard.dashboard_chartXml
                 ,risk.dashboard.dashboardFunction
                 "
%>
<%
	ParseRequest pr = new ParseRequest(request);
	DateUtil du = new DateUtil();
	ConfigUtil cu = new ConfigUtil();
	
	String url = cu.getConfig("URL");
	String imgUrl = url+"images/mobile/";
	
	
	
	dashboardMgr dMgr = new dashboardMgr();
	dashboard_chartXml xml = new dashboard_chartXml();
	
	
	dashboardSuperBean.mainBean mBean = null;
	
	dashboardFunction func = new dashboardFunction();
	
	
	//시간세팅
	String sDate = pr.getString("sDateFrom");
	String eDate = pr.getString("sDateTo");
	String sTime = pr.getString("sTime","00:00:00");
	String eTime = pr.getString("eTime","23:59:59");
	String sCurrDate    = du.getCurrentDate();
	if (eDate.equals("")) eDate = du.getCurrentDate();
	if (sDate.equals("")) {
		du.addDay(-1);
		sDate = du.getDate();
	}
	
	String todayType = pr.getString("todayType");
	String type = "";
	int tier = 0;
	if(todayType.equals("1")){
		type = "1";
		tier = 1;
	}else if(todayType.equals("2")){
		type = "2,3,7,4";
		tier = 0;
	}
	
	//7일 날짜 마스터 가져오기
	String[] weekMaster = new String[7];
	for(int i = 0; i < weekMaster.length; i++){
		weekMaster[i] = (du.addDay_v2(eDate, -((weekMaster.length - 1) - i))).replaceAll("-","");	
	}
	
	System.out.println("포스코 우호도 (금일)");
	ArrayList arr_tendency = dMgr.getMainData1(sDate,sTime,eDate,eTime, type,sDate ,tier);
	
	System.out.println("포스코 우호도 (주간보기)");
	ArrayList arr_tendency2 = dMgr.getMainData1(du.addDay_v2(sDate, -6),sTime,eDate,eTime, type,"",tier);
	
%>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td align="right"><table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="120"><img src="<%=imgUrl%>dashboard_title_<%if(todayType.equals("1")){out.print("01");}else{out.print("001");}%>.gif"></td>
				<td align="right" valign="top" background="<%=imgUrl%>dashboard_title_bg_01.gif" style="padding:1px 0px 0px 0px">
					<img src="<%=imgUrl%>today_btn.gif" hspace="3" onclick="todayKeyword('<%=todayType%>')" style="cursor:pointer">
					<img src="<%=imgUrl%>weekly_btn.gif" onclick="weeklyView('<%=todayType%>')" style="cursor:pointer">
				</td>
				<td width="12"><img src="../../images/mobile/dashboard_title_bg_001.gif"></td>
			</tr>
		</table></td>
	</tr>

	 <tr>
         <td align="right" bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
           <tr>
             <td width="2" bgcolor="#4C3F29"></td>
             <td width="3" bgcolor="#FFFFFF"></td>
             <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                 <tr>
                   <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                       <tr>
                         <td width="12"><img src="<%=imgUrl%>bg_14.gif" width="12" height="133"></td>
                         <td bgcolor="#8ADFF6"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                             <tr>
                             <%
								String poscoImg = "";
								if(arr_tendency.size() > 0){
									mBean = (dashboardSuperBean.mainBean)arr_tendency.get(0);
									poscoImg = func.weatherImg(mBean.getPercent_int(), dashboardFunction.E_ImgType.Mobile);
								}else{
									poscoImg = func.NoneImg(dashboardFunction.E_ImgType.Mobile);
								}
							%>
                             
                             
                               <td width="50%" align="center"><img src="<%=imgUrl%><%=poscoImg%>" width="151" height="117"></td>
                               <!--해구름<img src="images/n_suncloud_b_01.gif" width="151" height="117">-->
                               <!--비<img src="images/n_rain_b_01.gif" width="151" height="117">-->
                               <!--없음<img src="images/n_none_b_01.gif" width="151" height="117">-->
                               </tr>
                         </table></td>
                         <td width="12"><img src="<%=imgUrl%>bg_15.gif" width="12" height="133"></td>
                       </tr>
                   </table></td>
                 </tr>
                 <tr>
                   <td height="5" bgcolor="#FFFFFF"></td>
                 </tr>
                 <tr>
                   <td bgcolor="#FFFFFF"><table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="347DC5">
                     <tr>
                     <%
                     	int cnt = 0;
                 		String poscoImg_w = "";
                 		for(int i =0; i < weekMaster.length; i++){
                     %>
                     
	                     <%if(i==0){ %>
	                     	<td width="7" height="50" bgcolor="529AE1"><img src="../../images/mobile/table_bg_01.gif" width="7" height="50"></td>
	                     <% }else{ %>
	                     	<td width="1" bgcolor="#347DC5"></td>
	                     <%}%>
                     
                       
                       <td background="../../images/mobile/table_bg_02.gif" bgcolor="529AE1"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                           <tr>
                             <td height="20" align="center" class="text_w1" style="padding:4px 0px 0px 0px"><%=du.getDate(weekMaster[i],"MM-dd")%></td>
                           </tr>
                           <tr>
                           	<%
                           		mBean = null;
                    			cnt = 0;
                    			for(int j = 0; j < arr_tendency2.size(); j++){
                    				mBean = (dashboardSuperBean.mainBean)arr_tendency2.get(j);
                    				
                    				if(weekMaster[i].replaceAll("-","").equals(mBean.getMd_date())){
                    					cnt++;
                    					poscoImg_w = func.weatherImg(mBean.getPercent_int(), dashboardFunction.E_ImgType.Mobile_s);
                    					
                    		%>			
                    					<td height="30" align="center"><img src="../../images/mobile/<%=poscoImg_w%>"></td>			
                    		<%		
                    				}
                    			}	
                    			
                    			if(cnt == 0){
                    				poscoImg_w = func.NoneImg(dashboardFunction.E_ImgType.Mobile_s);                    				
                    		%>
                          
                          		<td height="30" align="center"><img src="../../images/mobile/<%=poscoImg_w%>"></td>
                            <% }%> 
                             
                           </tr>
                       </table></td>
                       
                       <%
             			}
                       %>
                       
                       
                       
                       <td width="7" bgcolor="#529AE1"><img src="../../images/mobile/table_bg_03.gif" width="7" height="50"></td>
                     </tr>
                   </table></td>
                 </tr>
             </table></td>
             <td width="3" bgcolor="#FFFFFF"></td>
             <td width="2" bgcolor="#4C3F29"></td>
           </tr>
         </table></td>
       </tr>
	 
	 
	 
	
	<tr>
         <td align="right"><table width="100%" border="0" cellspacing="0" cellpadding="0">
             <tr>
               <td width="9" valign="top"><img src="<%=imgUrl%>dashboard_title_bg_22.gif" width="9" height="29"></td>
               <td align="right" background="<%=imgUrl%>dashboard_title_bg_23.gif" style="padding:4px 0px 0px 0px"><img src="<%=imgUrl%>icon_02.gif" width="171" height="22"></td>
               <td width="9"><img src="<%=imgUrl%>dashboard_title_bg_24.gif" width="9" height="29"></td>
             </tr>
         </table></td>
       </tr>
</table>