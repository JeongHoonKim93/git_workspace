<%@ page language="java" contentType="text/html; charset=utf-8"   pageEncoding="utf-8"%>
<%@ include file="../inc/sessioncheck.jsp" %>
<%@ page import="	 java.util.ArrayList
					,java.util.HashMap
					,risk.util.ParseRequest
					,risk.voc.VocDataMgr
					,risk.voc.VocBean					
                 	,risk.util.StringUtil
                 	,risk.util.DateUtil
                 	,java.net.URLDecoder	
                 	,risk.util.PageIndex
                 	,risk.search.userEnvMgr
                	,risk.search.userEnvInfo
                	,risk.issue.IssueCodeMgr
                	,risk.issue.IssueCodeBean
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
	
	String typeCode = pr.getString("typeCode");
	
	//검색날짜 설정 : 기본 7일간 검색한다.
	String sCurrDate    = du.getCurrentDate();
	if (sDateTo.equals("")) sDateTo = du.getCurrentDate();
	if (sDateFrom.equals("")) {
		du.addDay(-7);
		sDateFrom = du.getDate();
	}
	
	
	//세션정보 가져오기~~
	userEnvInfo uei = null;
    uei = (userEnvInfo) session.getAttribute("ENV");
	
	
	//String searchKey = URLDecoder.decode(pr.getString("encodingSearchKey",""),"UTF-8");
	String searchKey = pr.getString("searchKey","");
	
	
	// 리스트부
	ArrayList issue_list = vMgr.getVocList(nowpage, pageCnt, sDateFrom, sDateTo, ir_stime+":00:00", ir_etime+":00:00", searchKey, "", type, "", uei.getMg_company(),"","",typeCode);

	
	//페이징 처리
	totalCnt = vMgr.getTotalCnt();	
	if (totalCnt>0) {
		totalPage = totalCnt / pageCnt;
		if ((totalCnt % pageCnt) > 0 ) {
			totalPage++;
		}
	}
	
	
	//String srtMsg = "추가해주세요~";
	String strMsg = "총 건수 : "+totalCnt+" 건, "+nowpage+"/"+totalPage+" pages";
	
	//배정권한 있는 유저 가져오기~
	ArrayList app_user = vMgr.getCategoryUser("ASS","");
	
	
	
	//세부조건검색(이슈등록에서 가져오기~)
	IssueCodeMgr icMgr = IssueCodeMgr.getInstance();
	IssueCodeBean icBean = null;	
	icMgr.init(0);		
	ArrayList arrIcBean = null;
	
	
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


	//페이징
	function pageClick( paramUrl ) {	
       	var f = document.fSend; 
        f.action = 'voc_data_list.jsp' + paramUrl;
        f.target='';
        f.submit();	        
	}
	
	//체크박스
    function checkAll(chk) {
 		var o=document.getElementsByName('tt');
 		for(i=0; i<o.length; i++) {
 			o[i].checked = chk;
 		}
 	}

  	//VOC 팝업
 	function PopVocForm(v_seq){
 		var f = document.fSend;
 		f.v_seq.value = v_seq;


		 		
 		popup.openByPost('fSend','pop_voc_data_form.jsp',700,500,false,false,false,'send_issue');
 	}

 	//VOC 팝업
 	function PopVocForm_app(v_seq){
 		var f = document.fSend;
 		f.v_seq.value = v_seq;
 		
 		popup.openByPost('fSend','pop_voc_pro_data_form.jsp',718,700,false,true,false,'send_issue');
 	
 	}

 	//Url 링크
 	var chkPop = 1;
	function hrefPop(url){
		//window.open(url,'hrefPop'+chkPop,'');
		window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop'+chkPop,'');
		chkPop++;
	}
	
	
	//타입코드 셋팅
	function settingTypeCode()
	{
		var form = document.fSend;

		form.typeCode.value = '';

		var type = ["4","5","6","7","8","9","10","11","12","13","14","15","16","17"];
		
		
		var obj = null; 
		for(var k = 0; k < type.length; k++){
			
			obj = eval("form.typeCode" + type[k]);
			
			if(obj){
				
				for(var i=0;i<obj.length;i++){
					if(obj[i].selected)
					{
						if(obj[i].value!=''){
							form.typeCode.value += form.typeCode.value=='' ? obj[i].value : '@'+obj[i].value ;
						}
					}	
				}
			}
		}
	}

	//검색하기
	function search(){
 		var f = document.fSend; 
 		
 		settingTypeCode();
 		
		f.nowpage.value = '1';
 		f.action = 'voc_data_list.jsp';
 		f.target = '';
 		f.submit(); 	
 	}

	function deleteIssueData() {
 		var o=document.all.tt;
 		var chkedseq='';
 		var cnt = 0;


		var arrVal = null;

		//asign 되어 있는지 체크~~
		for(i=0; i<o.length; i++) {
 			if(o[i].checked == true){
	 			if(o[i].value!=""){
	 				arrVal = o[i].value.split('_');
	 				if(arrVal[1] != '1'){
	 					alert('이미 배정된 VOC 입니다.');
	 					return;
	 				}
	 			}
 			}
 		}
		
 		
 		//seq를 한줄로 합체~~
 		for(i=0; i<o.length; i++) {
 			if(o[i].checked == true){
	 			if(o[i].value!=""){
	 				arrVal = o[i].value.split('_');
					
	 				if(chkedseq == ''){
	 					chkedseq = arrVal[0];
	 				}else{
	 					chkedseq = chkedseq+','+arrVal[0];
	 				}
	 				cnt++;
	 			}
 			}
 		}

 		var f = document.fSend;
		if(chkedseq == '') {
			alert('VOC를 선택하세요.'); return;
		}
		else {
			if(confirm(cnt + '건의 VOC를 삭제 할까요?')) {
				
				f.v_seq.value = chkedseq;
				f.mode.value = 'delete';
				f.action="voc_data_prc.jsp";
				f.target='issue_prc';
				f.submit();
			}
		}
 	}
 	
-->
</script>
</head>
<body style="margin-left: 15px;">
<form name="fSend" id="fSend" action="" method="post">
<input type="hidden" name="nowpage" value="<%=nowpage%>">
<input type="hidden" name="mode" value="write">
<input type="hidden" name="v_seq">
<input type="hidden" name="type" value="<%=type%>"> 
<input type="hidden" name="typeCode" value="<%=typeCode %>">
<table style="width:100%;height:100%;" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="body_bg_top" valign="top">
			<table style="width:820px;" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="tit_bg" style="height:67px;padding-top:20px;">
					<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<%
								String titleImg = "";
								String subTitle = "";
								if(type.equals("1")){
									subTitle = "VOC배정";
									titleImg = "tit_0307.gif";
								}else if(type.equals("2")){
									subTitle = "VOC처리";
									titleImg = "tit_0308.gif";
								}else if(type.equals("3")){
									subTitle = "VOC완료";
									titleImg = "tit_00000.gif";
								}
							%>
							<td>
								<img src="../../images/voc/tit_icon.gif" /><img src="../../images/voc/<%=titleImg%>" />
							</td>
							<td align="right">
							<table border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td class="navi_home">HOME</td>
									<td class="navi_arrow">VOC관리</td>
									<td class="navi_arrow2"><%=subTitle%></td>
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
					            <td width="17"><img src="../../images/issue/table_icon_01.gif" width="16" height="12" /></td>
					            <td width="62" class="b_text"><strong>검색단어 </strong></td>
					            <td width="213"><input type="text" class="textbox3" style="width:200px;" name="searchKey" value="<%=searchKey%>" onkeydown="Javascript:if(event.keyCode == 13){search();}"/></td>
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
					          </tr>
					        </table></td>
					        <td width="12"><img src="../../images/issue/table_bg_03.gif" width="12" height="35" /></td>
					      </tr>
					    </table></td>
					  </tr>
					  
					  
					  
					  
					  <tr>
					    <td><table width="820" border="0" cellspacing="0" cellpadding="0">
					      <tr>
					        <td width="1" bgcolor="AEC6CE"></td>
					        <td width="818" bgcolor="E6E6CF" style="padding:5px 0px 5px 0px"><table width="798" border="0" align="center" cellpadding="0" cellspacing="0">
					        
					        <%
					        	int conlen = 4;
					        	String[] types = {"4","5","6","7","8","9","10","11","12","13","14","15","16","17"};
					        
					        	String[][] re_types = new String[ (types.length % conlen > 0) ? (types.length / conlen + 1) : (types.length / conlen)][conlen];
					        	
					        	for(int i =0; i < types.length; i++){
					        	
					        		re_types[i/conlen][i%conlen] = types[i];
					        	}
					        	
					        
					        	
					        	for(int k  =0 ; k < re_types.length; k++){
					        %>		
					        		
					        		<tr>
					            <td height="24" style="padding:5px 0px 5px 7px"><table width="790" border="0" cellspacing="0" cellpadding="0">
					              <tr>
					              <%
					              	for(int t = 0; t < re_types[k].length; t++){
					              		
					              		if(re_types[k][t] != null){
					              		
					              		String selected = "";
										String codeTypeName = "";
										arrIcBean = icMgr.GetType(Integer.parseInt(re_types[k][t]));
										codeTypeName = icMgr.GetCodeName(arrIcBean,0);
					            	
								%>
					                <td width="77" class="b_text"><img src="../../images/issue/icon_search_bullet.gif" width="9" height="9" /><strong title="<%=codeTypeName%>"><%=su.cutString(codeTypeName, 4, "") %></strong></td>
					                <td width="120"><select name="typeCode<%=re_types[k][t]%>" id="typeCode8" class="textbox3" style="width: 105px">
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
					              			}else{
					              		%>
					              			<td width="77"></td>
					                		<td width="120"></td>
					              		<%		
					              			}
					              		}
					                  	%>
					                
					              </tr>
					              
					              
					              
					            </table></td>
					          </tr>
					          
				        		<%
				              	if(k != re_types.length-1){
				              %>
				              <tr>
					            <td><img src="../../images/issue/dotline.gif" width="798" height="3" /></td>
					          </tr>
					          <%
				              	}
					          %>
					        		
					        <%		
					        	}
					        %>
					      
					        </table></td>
					        <td width="1" bgcolor="AEC6CE"></td>
					      </tr>
					    </table></td>
					  </tr>
					  
					  
					  
					  
					  <tr>
					    <td height="1" bgcolor="AEC6CE"></td>
					  </tr>
					</table>
					</td>
				</tr>
				
				<tr>
					<td style="height:40px;">
					<table width="820" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td style="padding-right: 5px"><img src="../../images/issue/btn_allselect.gif" onclick="checkAll(!document.fSend.checkall.checked);" style="cursor:pointer;display: <%if(!type.equals("1")){out.print("none");}%>;"/></td>
							<td><img src="../../images/issue/btn_del.gif" onclick="deleteIssueData();" style="cursor:pointer; display: <%if(!type.equals("1")){out.print("none");}%>;"/></td>
							<td width="100%" align="right" style="padding-right: 10px; padding-top: 15px"><img src="../../images/search/icon_search_bullet.gif"><%=strMsg%></td>
						</tr>
					</table>
					</td>
				</tr>
				
				<tr>
					<td>
					<table id="board_01" border="0" cellpadding="0" cellspacing="0" style="table-layout: fixed;">
					<col width="5%"><col width="15%"><col width="40%"><col width="10%"><col width="10%"><col width="10%"><col width="10%">
						<tr>
							<th><input type="checkbox" name="tt" id="checkall" onclick="checkAll(this.checked);" value=""></th>
							<th>출처</th>
							<th>제목</th>
							<th>수집시간</th>
							<th>등록시간</th>
							<th>회사</th>
							<th>상태</th>
						</tr>
						
						<%
							VocBean.VocMBean mbean = null;
							String colDate = "";
							String reDate = "";
							String img = "";
							String enter = (new Character((char)13)).toString();
							for(int i =0; i < issue_list.size(); i++){
								mbean = (VocBean.VocMBean)issue_list.get(i);
								
								colDate = ""+mbean.getMd_date().substring(5,10)+" "+mbean.getMd_date().substring(11,16);
								reDate = ""+mbean.getI_regdate().substring(5,10)+" "+mbean.getI_regdate().substring(11,16);
								
								
								
								
								
								//색상변경
								String title = "";
								String aTeg = "";
								
								if(mbean.getStatus().equals("2") || mbean.getStatus().equals("3")){
									
									title = su.ChangeString(mbean.getMd_title()).replaceAll(enter, "");
									aTeg = "<a onClick=\"PopVocForm_app('"+ mbean.getV_seq() +"');\"  href=\"javascript:void(0);\">" + title + "</a>";
											
								}else{
									title = su.ChangeString(mbean.getMd_title()).replaceAll(enter, "");
									aTeg = "<a onClick=\"hrefPop('"+ mbean.getMd_url() +"');\"  href=\"javascript:void(0);\">" + title + "</a>";	
								}
								
								
								
								
								img = "<strong>" +  mbean.getStatus_name() + "</strong>";
								if(mbean.getStatus().equals("1")){
									
									if(vMgr.searchUser(app_user,SS_M_NO)){
										img = "<img src=\"../../images/voc/assign.gif\" onclick=\"PopVocForm('"+mbean.getV_seq()+"');\" style=\"cursor:pointer;\">";	
									}
								}
								
								
								
								
						%>
							<tr>						
								<td><input name="tt" type="checkbox" value="<%=mbean.getV_seq()%>_<%=mbean.getStatus()%>" id="tt"></td>
								<td><p style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;" class="board01_category" title="<%=mbean.getMd_site_name()%>"><%=mbean.getMd_site_name()%></p></td>
								<td><p style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;" class="board_01_tit_01" title="<%=mbean.getMd_title()%>"><%=aTeg%></p></td>
								<td><%=colDate%></td>
								<td><%=colDate%></td>
								<td><%=mbean.getCategory()%></td>
								<td><%=img%></td>
							</tr>
						<%		
							}
						%>
						
					</table>
					</td>
				</tr>
				
					<tr>
						<td style="height:40px;">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td style="padding-right: 5px"><img src="../../images/issue/btn_allselect.gif" onclick="checkAll(!document.fSend.checkall.checked);" style="cursor:pointer; display: <%if(!type.equals("1")){out.print("none");}%>;"/></td>
								<td><img src="../../images/issue/btn_del.gif" onclick="deleteIssueData();" style="cursor:pointer; display: <%if(!type.equals("1")){out.print("none");}%>;"/></td>
								
							</tr>
						</table>
						</td>
					</tr>
				
				<!-- 페이징 -->
				<tr>
					<td>
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
					</td>
				</tr>
				<!-- 페이징 -->
				
				
				
				
				
				
				
				
				
				
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


<iframe id="selectSite" name ="selectSite" width="0" height="0" ></iframe>
<iframe id="if_samelist" name="if_samelist" width="0" height="0" src="about:blank"></iframe>
<iframe id="issue_prc" name="issue_prc" width="0" height="0" src="about:blank"></iframe>
</body>
</html>

