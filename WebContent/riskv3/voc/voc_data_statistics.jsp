<%@ page language="java" contentType="text/html; charset=utf-8"   pageEncoding="utf-8"%>
<%@ include file="../inc/sessioncheck.jsp" %>
<%@ page import="	 java.util.ArrayList
					,java.util.HashMap
					,risk.util.ParseRequest
					,risk.voc.VocDataMgr
					,risk.voc.VocBean
					,risk.voc.VocStatisticsMgr
					,risk.voc.VocGraph	
                 	,risk.util.StringUtil
                 	,risk.util.DateUtil
                 	,java.net.URLDecoder	
                 	,risk.util.PageIndex
                 	" %>
<%

	ParseRequest 	pr 		= new ParseRequest(request);	
	DateUtil 		du 		= new DateUtil();
	StringUtil		su 		= new StringUtil();
	
	VocDataMgr vMgr = new VocDataMgr(); 

	int nowpage = 1;	
	int pageCnt = 20;
	int totalCnt = 0;
	int totalPage = 0;
	
	nowpage = pr.getInt("nowpage",1);

	String type = pr.getString("type","1"); 
	
	String i_seq = pr.getString("i_seq","");
	String it_seq = pr.getString("it_seq","");
	String sDateFrom = pr.getString("sDateFrom");
	String sDateTo = pr.getString("sDateTo");
	
	String ir_stime = pr.getString("ir_stime","16");
	String ir_etime = pr.getString("ir_etime","16");
	
	//검색날짜 설정 : 기본 7일간 검색한다.
	String sCurrDate    = du.getCurrentDate();
	if (sDateTo.equals("")) sDateTo = du.getCurrentDate();
	if (sDateFrom.equals("")) {
		du.addDay(-7);
		sDateFrom = du.getDate();
	}
	
	VocStatisticsMgr vmgr = new VocStatisticsMgr();
	ArrayList issue_list = vmgr.getStatusList(sDateFrom, sDateTo, ir_stime+":00:00", ir_etime+":00:00", "");	

%>
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="../../css/base.css" />
<style>
iframe.hide { border:0px solid red; position:absolute; top:0px; left:0px; z-index:-99; width:148px; height:150px; filter: alpha(opacity=0);}
</style>
<script src="<%=SS_URL%>js/jquery.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/ajax.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/popup.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/Calendar.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/amcharts.js" type="text/javascript"></script>
<script language="JavaScript" type="text/JavaScript">
<!--
	sDateFrom = '<%=sDateFrom%>';
	sDateTo = '<%=sDateTo%>';

	//검색하기
	function search(){
 		var f = document.fSend; 
		f.nowpage.value = '1';
 		f.action = 'voc_data_statistics.jsp';
 		f.target = '';
 		f.submit(); 	
 	}

	function showPop(arg1, arg2){

		var p = document.popup;
		p.state.value = arg1;
		p.company.value = arg2;
		popup.openByPost('popup','pop_voc_data_list.jsp',817,420,false,true,false,'send_voc');

	}
 	
-->
</script>
</head>
<body style="margin-left: 15px">
<form id="popup" name="popup" action="" method="post">
<input type="hidden" name="company" value="">
<input type="hidden" name="state" value="">
<input type="hidden" name="sDateFrom" value="<%=sDateFrom%>">
<input type="hidden" name="sDateTo" value="<%=sDateTo%>">
<input type="hidden" name="ir_stime" value="<%=ir_stime%>">
<input type="hidden" name="ir_etime" value="<%=ir_etime%>">
</form>

<form name="fSend" action="" method="post">
<input type="hidden" name="nowpage" value="<%=nowpage%>">
<input type="hidden" name="mode" value="write">
<input type="hidden" name="v_seq">
<input type="hidden" name="type" value="<%=type%>"> 
<table style="width:100%;height:100%;" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="body_bg_top" valign="top">
			<table style="width:820px;" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="tit_bg" style="height:67px;padding-top:20px;">
					<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<img src="../../images/voc/tit_icon.gif" /><img src="../../images/voc/tit_0309.gif" />
							</td>
							<td align="right">
							<table border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td class="navi_home">HOME</td>
									<td class="navi_arrow">VOC관리</td>
									<td class="navi_arrow2">VOC통계</td>
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
				

				
				<tr>
					<td>
					
					<table width="820" border="0" cellspacing="0" cellpadding="0">
					  <tr>
					    <td><table width="820" border="0" cellspacing="0" cellpadding="0">
					      <tr>
					        <td width="15"><img src="../../images/issue/table_bg_01.gif" width="15" height="35" /></td>
					        <td width="793" background="../../images/issue/table_bg_02.gif"><table width="790" border="0" cellspacing="0" cellpadding="0">
					          <tr>
					            <td width="16"><img src="../../images/issue/table_icon_01.gif" width="16" height="12" /></td>
					            <td width="60" class="b_text"><strong>검색기간</strong></td>
					            <td width="422"><table border="0" cellpadding="0" cellspacing="0">
					              <tr>
					                <td width="86"><input style="width:90px;text-align:center" class="textbox" type="text" id="sDateFrom" name="sDateFrom" value="<%=sDateFrom%>"></td>
					                <td width="27"><img src="../../images/issue/btn_calendar.gif" style="cursor:pointer" onclick="calendar_sh(document.getElementById('sDateFrom'))"/></td>
					                <%
					                	String sBasics  = ir_stime.equals("") ? "16" :  ir_stime;
					                	String eBasics  = ir_etime.equals("") ? "16" :  ir_etime;
									%>
					                
					                <td width="55"><select name="ir_stime"><%for(int i=0; i<24; i++){ if(i==Integer.parseInt(sBasics)){out.print("<option value="+i+" selected>"+i+"시</option>");}else{out.print("<option value="+i+">"+i+"시</option>");}} %></select></td>
					                <td width="11" align="center">~</td>
					                <td width="86"><input style="width:90px;text-align:center" class="textbox" type="text" id="sDateTo" name="sDateTo" value="<%=sDateTo%>"></td>
					                <td width="27"><img src="../../images/issue/btn_calendar.gif" style="cursor:pointer" onclick="calendar_sh(document.getElementById('sDateTo'))"/></td>
					                <td width="55"><select name="ir_etime"><%for(int i=0; i<24; i++){ if(i==Integer.parseInt(eBasics)){out.print("<option value="+i+" selected>"+i+"시</option>");}else{out.print("<option value="+i+">"+i+"시</option>");}} %></select></td>
					                <td width="75"><img src="../../images/issue/search_btn.gif" width="61" height="22" hspace="5" align="absmiddle" onclick="search()" style="cursor:pointer"/></td>
					              </tr>
					            </table></td>
					            <td width="292" ></td>
					          </tr>
					        </table></td>
					        <td width="12"><img src="../../images/issue/table_bg_03.gif" width="12" height="35" /></td>
					      </tr>
					    </table></td>
					  </tr>
					  <tr>
					    <td height="1" bgcolor="AEC6CE"></td>
					  </tr>
					</table>
					</td>
				</tr>
				
				<tr height="30px"><td></td></tr>
				
				<tr align="center">
			    <td><table width="783" border="0" cellspacing="0" cellpadding="0">
			      <tr>
			        <td height="2" bgcolor="A09375"></td>
			      </tr>
			      <tr>
			        <td><table width="783" border="0" cellpadding="0" cellspacing="1" bgcolor="#cccccc">
			          <tr>
			            <td width="200" rowspan="2" align="center" bgcolor="#F5F5F5"><strong>계열사</strong></td>
			            <td height="30" colspan="4" align="center" bgcolor="F5F5F5"><strong>상태</strong></td>
			            </tr>
			          <tr>
			            <td width="120" height="30" align="center" bgcolor="#FFFFFF"><font style="color:#BBB355;"><strong>정상 완료</strong></font></td>
			            <td width="120" align="center" bgcolor="#FFFFFF"><font style="color:#6872E8;"><strong>처리 중</strong></font></td>
			            <td width="120" align="center" bgcolor="#FFFFFF"><font style="color:#59AF4E;"><strong>지연 후 완료</strong></font></td>
			            <td width="120" align="center" bgcolor="#FFFFFF"><font style="color:#FF4848;"><strong>처리 지연</strong></font></td>
			            </tr>
			          
			          
			          <%
			          
			          	String[] tmpStatus = new String[4];
				      	tmpStatus[0] = "정상완료";
				      	tmpStatus[1] = "처리중";
				      	tmpStatus[2] = "지연 후 완료";
				      	tmpStatus[3] = "처리 지연";
				      	
				      	String[] data = null;
				      	String preCate = "";
				      	int cateCnt = 0;
				      	int cateCnt2 = 0;
				      	int cateRow = 0;
				      	
				      	for(int i = 0; i < issue_list.size(); i++){
				      		
				      		data = (String[])issue_list.get(i);
				      		
				      		if(!preCate.equals(data[1])){
				      			cateCnt++;
				      		}
				      		preCate=data[1];
				      	}
				      	System.out.println("cateCnt:"+cateCnt);
				      	
				      	
				      	
				      	preCate = "";
				      	int[][] arrInt = new int[cateCnt][4];
				      	String[] cateName = new String[cateCnt];
				      	String[] cateCode = new String[cateCnt];
				      	int[] rowCnt = new int[cateCnt];
				      	
				      	for(int k=0; k<cateCnt; k++){
				      		for(int j=0; j<4; j++){
				      			arrInt[k][j] = 0;
				      		}
				      		
				      		preCate="";
				      		cateCnt2 = 0;
				      		
				      		for(int i = 0; i < issue_list.size(); i++){
				      			data = (String[])issue_list.get(i);
				      			if(!preCate.equals(data[1])){
				      				cateCnt2++;
				      				if(k==cateCnt2-1){
				      					cateName[k] = data[1];
				      					cateCode[k] = data[2];
				      				}
				      		
				      			}
				      			
				      			
				      			if(k==cateCnt2-1){
				      				//stand by, progress, finish
				      				//5:grade 6:status
				      				if(data[6].equals("완료") && data[3].equals("On-Time")){
				      					arrInt[k][0] += Integer.parseInt(data[4]);  }
				      				if(data[6].equals("처리") && data[3].equals("On-Time")){
				      					arrInt[k][1] += Integer.parseInt(data[4]);  }
				      				if(data[6].equals("완료") && data[3].equals("Delayed")){
				      					arrInt[k][2] += Integer.parseInt(data[4]); }
				      				if(data[6].equals("처리") && data[3].equals("Delayed")){
				      					arrInt[k][3] += Integer.parseInt(data[4]); }
				      			}
				      			preCate=data[1];
				      			
				      		}
				      	}
			  
				      	String value = ""; 
				      	for(int k=0; k<cateCnt; k++){
				    		if(cateName[k]==null || cateName[k].equals("null")){
				    			cateName[k]="";
				    			cateCode[k]="";
				    		}
				    		
				    		
				    		
				    		
			          %>
			          
			          <tr>
			            <td height="30" align="center" bgcolor="#FFFFFF"><%=cateName[k]%></td>
			            <td align="center" bgcolor="#FFFFFF" ><%=arrInt[k][0] != 0 ? "<span onclick=\"showPop(0," + cateCode[k]+")\" style=\"cursor: pointer;\"><strong><u>"+arrInt[k][0]+"</u></strong></span>" : arrInt[k][0]%></td>
			            <td align="center" bgcolor="#FFFFFF" ><%=arrInt[k][1] != 0 ? "<span onclick=\"showPop(1," + cateCode[k]+")\" style=\"cursor: pointer;\"><strong><u>"+arrInt[k][1]+"</u></strong></span>" : arrInt[k][1]%></td>
			            <td align="center" bgcolor="#FFFFFF" ><%=arrInt[k][2] != 0 ? "<span onclick=\"showPop(2," + cateCode[k]+")\" style=\"cursor: pointer;\"><strong><u>"+arrInt[k][2]+"</u></strong></span>" : arrInt[k][2]%></td>
			            <td align="center" bgcolor="#FFFFFF" ><%=arrInt[k][3] != 0 ? "<span onclick=\"showPop(3," + cateCode[k]+")\" style=\"cursor: pointer;\"><strong><u>"+arrInt[k][3]+"</u></strong></span>" : arrInt[k][3]%></td>
			          </tr>
			          
			          <%
							}
				      	VocGraph mkGrpah = new VocGraph();
				      	

      					
				      	
				      	String chart1_1 = mkGrpah.getGroupBarGraph(issue_list, cateName, cateCode, arrInt, tmpStatus, 780, 240, true);
					%>
			          
			        </table></td>
			      </tr>
			    </table></td>
			  </tr>
			  
			  <tr align="center">
				<td  colspan="5">
				<table width="780"  border="0" cellspacing="0" cellpadding="0">
					<tr>
					<td colspan="6" height="300" align="center"><img src="<%=chart1_1%>" usemap="#chart1_1"> </td>
					</tr>
				</table>
				</td>
			</tr> 
				
			</table>
		</td>
	</tr>
</table>
</form>
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
<%
ArrayList map = mkGrpah.arrImageMap;
if(map.size() > 0){
	for(int i = 0; i < map.size(); i++){
		if(i != 3){
			
			out.print((String)map.get(i));
		}
	}
}
%>

<iframe id="selectSite" name ="selectSite" width="0" height="0" ></iframe>
<iframe id="if_samelist" name="if_samelist" width="0" height="0" src="about:blank"></iframe>
<iframe id="issue_prc" name="issue_prc" width="0" height="0" src="about:blank"></iframe>
</body>
</html>

