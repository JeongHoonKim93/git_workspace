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
                 "
%>
<%
	ParseRequest pr = new ParseRequest(request);
	DateUtil du = new DateUtil();
	ConfigUtil cu = new ConfigUtil();
	pr.printParams();
	
	String url = cu.getConfig("URL");
	String imgUrl = url+"images/mobile/";
	
	
	
	dashboardMgr dMgr = new dashboardMgr();
	dashboard_chartXml xml = new dashboard_chartXml();
	
	
	dashboardSuperBean superBean = new dashboardSuperBean(); 
	dashboardSuperBean.mainBean mBean = null;
	
	
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
	System.out.println("탑기사");
	ArrayList getTopName = dMgr.getTopKeyword(sDate,sTime,eDate,eTime,"", type, "5",tier);
	
 	if(getTopName.size() < 10){
 		int getSize = getTopName.size();
 		for(int i = 0; i < 10 - getSize; i++){
 			mBean = superBean.new mainBean();
 			mBean.setMain_type("");
 			mBean.setMain_type_name("");
 			getTopName.add(mBean);
 		}
 	}
 	
	
	String imgName = "";
	String imgCode = "";
	String typeName = "";
	int taWidth = 0;
	
	
	
%>
<style>
.white_s {
	font-size: 11px;
	color: #FFFFFF;
	line-height: normal;
	font-family: Dotum;
}

a.bl:link    {color:#002F48;text-decoration:none;}
a.bl:visited {color:#002F48;text-decoration:none;}
a.bl:active  {color:#002F48;text-decoration:none;}
a.bl:hover   {color:#002F48;text-decoration:none;}

</style>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td align="right"><table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="120"><img  src="../../images/mobile/dashboard_title_<%if(todayType.equals("1")){out.print("003");}else{out.print("03");}%>.gif"></td>
				<td align="right" valign="top" background="../../images/mobile/dashboard_title_bg_18.gif" style="padding:1px 0px 0px 0px">
					<img src="../../images/mobile/today_btn.gif" hspace="3" onclick="todayKeyword('<%=todayType%>')" style="cursor:pointer">
					<img src="../../images/mobile/weekly_btn.gif"  onclick="weeklyView('<%=todayType%>')" style="cursor:pointer">
				</td>
				<td width="9"><img src="../../images/mobile/dashboard_title_bg_19.gif"></td>
			</tr>
		</table></td>
	</tr>
	<tr>
		<td align="right"><table width="100%" border="0" cellspacing="0" cellpadding="0" id="img_back<%=todayType%>">
			<tr>
				<td width="9"><img src="../../images/mobile/dashboard_title_bg_20.gif" width="9" height="131"></td>
				<td align="center" bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="65%" align="center" style="padding:0px 10px 0px 5px">




<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
               <tr>
                 <td height="131" background="../../images/mobile/keyword_bg_04.gif">
                 <%
	               		mBean = (dashboardSuperBean.mainBean)getTopName.get(0);
	               	
	               		if(mBean.getMain_type().equals("1")){
	               			imgName = "icon_smile.png";
	               		}else if(mBean.getMain_type().equals("2")){
	               			imgName = "icon_cry.png";
	               		}else if(mBean.getMain_type().equals("3")){
	               			imgName = "icon_sc.png";
	               		}else{
	               			imgName = "icon_sc.png";
	               		}
	               		
	               		if(mBean.getMain_type_name().length() > 4){
	               			typeName = mBean.getMain_type_name().substring(0,5);
	               		}else{
	               			typeName = mBean.getMain_type_name();
	               		}
	               		
	               		typeName = typeName.trim(); 
	                 	taWidth = 15 + (typeName.length() * 12);
	               	%>
                 <div id="apDiv1_<%=todayType%>" style="position:absolute; left:32px; top:<%if(todayType.equals("1")){out.print("180");}else if(todayType.equals("2")){out.print("396");}%>px;  height:20px;  visibility:visible;" onclick="popupList_today('<%=typeName%>','<%=todayType%>')">
				  <table border="0" cellspacing="0" cellpadding="0">
				    <tr>
				    
				      <td width="6"><img src="../../images/dashboard/key_table_bg_04.gif" width="6" height="25" /></td>
				      <td valign="top" background="../../images/dashboard/key_table_bg_06.gif"><table width="<%=taWidth%>" border="0" cellspacing="0" cellpadding="0">
				        <tr>
				          <td width="15"  style="padding:2px 0px 0px 0px" ><img src="../../images/dashboard/<%=imgName%>" width="15" height="16" /></td>
				          <td class="white_s"  style="padding:3px 0px 0px 0px"><strong><%=typeName%></strong></td>
				        </tr>
				      </table></td>
				      <td width="9"><img src="../../images/dashboard/key_table_bg_07.gif" width="9" height="25" /></td>
				    </tr>
				  </table>
				</div>
				<%
                       		mBean = (dashboardSuperBean.mainBean)getTopName.get(1);
                       	
                       		if(mBean.getMain_type().equals("1")){
                       			imgName = "icon_smile.png";
                       		}else if(mBean.getMain_type().equals("2")){
                       			imgName = "icon_cry.png";
                       		}else if(mBean.getMain_type().equals("3")){
                       			imgName = "icon_sc.png";
                       		}else{
                       			imgName = "icon_sc.png";
                       		}
                       		
                       		if(mBean.getMain_type_name().length() > 4){
                       			typeName = mBean.getMain_type_name().substring(0,5);
                       		}else{
                       			typeName = mBean.getMain_type_name();
                       		}
                       		
                       		typeName = typeName.trim(); 
                       		taWidth = 15 + (typeName.length() * 12);
                       		
                       	%>
                 <div id="apDiv2_<%=todayType%>" style="position:absolute; left:128px; top:<%if(todayType.equals("1")){out.print("178");}else if(todayType.equals("2")){out.print("394");}%>px;   visibility:visible;">
                   <table  border="0" cellspacing="0" cellpadding="0">
                     <tr>
                       <td width="6"><img src="../../images/dashboard/key_table_bg_01.gif" width="6" height="25" /></td>
                       <td valign="top" background="../../images/dashboard/key_table_bg_02.gif"><table width="<%=taWidth%>" border="0" cellspacing="0" cellpadding="0">
                           <tr>
                             <td width="15" style="padding:2px 0px 0px 0px" ><img src="../../images/dashboard/<%=imgName%>" width="15" height="16" /></td>
                             <td class="white_s" style="padding:3px 0px 0px 0px"><strong><%=typeName%></strong></td>
                           </tr>
                       </table></td>
                       <td width="9"><img src="../../images/dashboard/key_table_bg_03.gif" width="9" height="25" /></td>
                     </tr>
                   </table>
                 </div>
                 <%
                   		mBean = (dashboardSuperBean.mainBean)getTopName.get(2);
                   	
                   		if(mBean.getMain_type().equals("1")){
                   			imgName = "icon_smile.png";
                   		}else if(mBean.getMain_type().equals("2")){
                   			imgName = "icon_cry.png";
                   		}else if(mBean.getMain_type().equals("3")){
                   			imgName = "icon_sc.png";
                   		}else{
                   			imgName = "icon_sc.png";
                   		}
                   		
                   		if(mBean.getMain_type_name().length() > 4){
                   			typeName = mBean.getMain_type_name().substring(0,5);
                   		}else{
                   			typeName = mBean.getMain_type_name();
                   		}
                   		
                   		typeName = typeName.trim(); 
                     		taWidth = 15 + (typeName.length() * 12);
                   	%>
                 <div id="apDiv3_<%=todayType%>" style="position:absolute; left:150px; top:<%if(todayType.equals("1")){out.print("238");}else if(todayType.equals("2")){out.print("454");}%>px;  visibility:visible; height: 22px;" onclick="popupList_today('<%=typeName%>','<%=todayType%>')">
                   <table border="0" cellspacing="0" cellpadding="0">
                     <tr>
                       <td width="6"><img src="../../images/dashboard/key_table_bg_01.gif" width="6" height="25" /></td>
                       <td valign="top" background="../../images/dashboard/key_table_bg_02.gif"><table width="<%=taWidth%>" border="0" cellspacing="0" cellpadding="0">
                         <tr>
                           <td width="15" style="padding:2px 0px 0px 0px" ><img src="../../images/dashboard/<%=imgName%>" width="15" height="16" /></td>
                           <td class="white_s" style="padding:3px 0px 0px 0px"><strong><%=typeName%></strong></td>
                         </tr>
                       </table></td>
                       <td width="9"><img src="../../images/dashboard/key_table_bg_03.gif" width="9" height="25" /></td>
                     </tr>
                   </table>
                 </div>
                 <%
                     		mBean = (dashboardSuperBean.mainBean)getTopName.get(3);
                     	
                     		if(mBean.getMain_type().equals("1")){
                     			imgName = "icon_smile.png";
                     		}else if(mBean.getMain_type().equals("2")){
                     			imgName = "icon_cry.png";
                     		}else if(mBean.getMain_type().equals("3")){
                     			imgName = "icon_sc.png";
                     		}else{
                     			imgName = "icon_sc.png";
                     		}
                     		
                     		if(mBean.getMain_type_name().length() > 4){
                     			typeName = mBean.getMain_type_name().substring(0,5);
                     		}else{
                     			typeName = mBean.getMain_type_name();
                     		}
                     		
                     		typeName = typeName.trim(); 
                       		taWidth = 15 + (typeName.length() * 12);
                     	%>
                 <div id="apDiv4_<%=todayType%>" style="position:absolute; left:120px; top:<%if(todayType.equals("1")){out.print("272");}else if(todayType.equals("2")){out.print("498");}%>px;   visibility:visible;" onclick="popupList_today('<%=typeName%>','<%=todayType%>')">
                   <table  border="0" cellspacing="0" cellpadding="0">
                     <tr>
                       <td width="6"><img src="../../images/dashboard/key_table_bg_01.gif" width="6" height="25" /></td>
                       <td valign="top" background="../../images/dashboard/key_table_bg_02.gif"><table width="<%=taWidth%>" border="0" cellspacing="0" cellpadding="0">
                           <tr>
                             <td width="15" style="padding:2px 0px 0px 0px" ><img src="../../images/dashboard/<%=imgName%>" width="15" height="16" /></td>
                             <td class="white_s" style="padding:3px 0px 0px 0px"><strong><%=typeName%></strong></td>
                           </tr>
                       </table></td>
                       <td width="9"><img src="../../images/dashboard/key_table_bg_03.gif" width="9" height="25" /></td>
                     </tr>
                   </table>
                 </div>
                 <%
                         		mBean = (dashboardSuperBean.mainBean)getTopName.get(4);
                         	
                         		if(mBean.getMain_type().equals("1")){
                         			imgName = "icon_smile.png";
                         		}else if(mBean.getMain_type().equals("2")){
                         			imgName = "icon_cry.png";
                         		}else if(mBean.getMain_type().equals("3")){
                         			imgName = "icon_sc.png";
                         		}else{
                         			imgName = "icon_sc.png";
                         		}
                         		
                         		if(mBean.getMain_type_name().length() > 4){
                         			typeName = mBean.getMain_type_name().substring(0,5);
                         		}else{
                         			typeName = mBean.getMain_type_name();
                         		}
                         		
                         		typeName = typeName.trim(); 
                           		taWidth = 15 + (typeName.length() * 12);
                         	%>
                 <div id="apDiv5_<%=todayType%>" style="position:absolute; left:30px; top:<%if(todayType.equals("1")){out.print("273");}else if(todayType.equals("2")){out.print("489");}%>px; visibility:visible;" onclick="popupList_today('<%=typeName%>','<%=todayType%>')">
                   <table  border="0" cellspacing="0" cellpadding="0">
                     <tr>
                       <td width="6"><img src="../../images/dashboard/key_table_bg_01.gif" width="6" height="25" /></td>
                       <td valign="top" background="../../images/dashboard/key_table_bg_02.gif"><table width="<%=taWidth%>" border="0" cellspacing="0" cellpadding="0">
                           <tr>
                             <td width="15" style="padding:2px 0px 0px 0px" ><img src="../../images/dashboard/<%=imgName%>" width="15" height="16" /></td>
                             <td class="white_s" style="padding:3px 0px 0px 0px"><strong><%=typeName%></strong></td>
                           </tr>
                       </table></td>
                       <td width="9"><img src="../../images/dashboard/key_table_bg_03.gif" width="9" height="25" /></td>
                     </tr>
                   </table>
                 </div>
                 </td>
               </tr>
             </table>


						</td>
						<td width="35%" align="right"><table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="8"><img src="../../images/mobile/keyword_bg_01.gif" width="8" height="131"></td>
								<td background="../../images/mobile/keyword_bg_02.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="22" height="24" style="padding:0px 5px 0px 0px"><img src="../../images/mobile/number_01.gif" width="22" height="14"></td>
										<%String name = ((dashboardSuperBean.mainBean)getTopName.get(0)).getMain_type_name(); %>
										<td valign="top" class="BIG_title" style="padding:5px 0px 0px 0px"><strong><a class="bl" href="javascript:popupList_today('<%=name%>','<%=todayType%>')"><%=name%></a></strong></td>
									</tr>
									<tr>
										<td height="24"><span style="padding:0px 5px 0px 0px"><img src="../../images/mobile/number_02.gif" width="22" height="14"></span></td>
										<%name = ((dashboardSuperBean.mainBean)getTopName.get(1)).getMain_type_name(); %>
										<td valign="top" class="BIG_title" style="padding:5px 0px 0px 0px"><strong><a class="bl" href="javascript:popupList_today('<%=name%>','<%=todayType%>')"><%=name%></a></strong></td>
									</tr>
									<tr>
										<td height="24"><span style="padding:0px 5px 0px 0px"><img src="../../images/mobile/number_03.gif" width="22" height="14"></span></td>
										<%name = ((dashboardSuperBean.mainBean)getTopName.get(2)).getMain_type_name(); %>
										<td  valign="top" class="BIG_title" style="padding:5px 0px 0px 0px"><strong><a class="bl" href="javascript:popupList_today('<%=name%>','<%=todayType%>')"><%=name%></a></strong></td>
									</tr>
									<tr>
										<td height="24"><span style="padding:0px 5px 0px 0px"><img src="../../images/mobile/number_04.gif" width="22" height="14"></span></td>
										<%name = ((dashboardSuperBean.mainBean)getTopName.get(3)).getMain_type_name(); %>
										<td  valign="top" class="BIG_title" style="padding:5px 0px 0px 0px"><strong><a class="bl" href="javascript:popupList_today('<%=name%>','<%=todayType%>')"><%=name%></a></strong></td>
									</tr>
									<tr>
										<td height="24"><span style="padding:0px 5px 0px 0px"><img src="../../images/mobile/number_05.gif" width="22" height="14"></span></td>
										<%name = ((dashboardSuperBean.mainBean)getTopName.get(4)).getMain_type_name(); %>
										<td  valign="top" class="BIG_title" style="padding:5px 0px 0px 0px"><strong><a class="bl" href="javascript:popupList_today('<%=name%>','<%=todayType%>')"><%=name%></a></strong></td>
									</tr>
								</table></td>
								<td width="8"><img src="../../images/mobile/keyword_bg_03.gif" width="8" height="131"></td>
							</tr>
						</table></td>
					</tr>
				</table></td>
				<td width="9"><img src="../../images/mobile/dashboard_title_bg_21.gif" width="9" height="131"></td>
			</tr>
		</table></td>
	</tr>
	<tr>
		<td align="right"><table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="9" valign="top"><img src="../../images/mobile/dashboard_title_bg_22.gif" width="9" height="29"></td>
				<td align="right" background="../../images/mobile/dashboard_title_bg_23.gif" style="padding:4px 0px 0px 0px"><img src="../../images/mobile/icon_01.gif" width="139" height="17"></td>
				<td width="9"><img src="../../images/mobile/dashboard_title_bg_24.gif" width="9" height="29"></td>
			</tr>
		</table></td>
	</tr>
</table>
<script type="text/javascript">
	var obj = null;

	if(document.getElementById('img_back1')){
		obj = document.getElementById('img_back1').getBoundingClientRect();

		document.getElementById('apDiv1_1').style.top = document.body.scrollTop + obj.top + 9;
		document.getElementById('apDiv2_1').style.top = document.body.scrollTop + obj.top + 4;	
		document.getElementById('apDiv3_1').style.top = document.body.scrollTop + obj.top + 64;
		document.getElementById('apDiv4_1').style.top = document.body.scrollTop + obj.top + 98;
		document.getElementById('apDiv5_1').style.top = document.body.scrollTop + obj.top + 99;
	}
	
	if(document.getElementById('img_back2')){
		obj = document.getElementById('img_back2').getBoundingClientRect();

		document.getElementById('apDiv1_2').style.top = document.body.scrollTop + obj.top + 9;
		document.getElementById('apDiv2_2').style.top = document.body.scrollTop + obj.top + 4;	
		document.getElementById('apDiv3_2').style.top = document.body.scrollTop + obj.top + 64;
		document.getElementById('apDiv4_2').style.top = document.body.scrollTop + obj.top + 98;
		document.getElementById('apDiv5_2').style.top = document.body.scrollTop + obj.top + 99;
	}
		
</script>