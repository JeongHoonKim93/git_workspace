<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList
				,risk.util.ParseRequest
				,risk.util.DateUtil
				,risk.util.StringUtil
				,risk.voc.*
				,java.util.List
				,java.util.Iterator
				,risk.sms.AddressBookDao
				,risk.sms.AddressBookBean
				,risk.sms.AddressBookGroupBean
				,risk.issue.IssueDataBean	
				"%>
<%@include file="../inc/sessioncheck.jsp"%>
<%
		ParseRequest pr = new ParseRequest(request);		
		DateUtil	 du = new DateUtil();
		StringUtil		su = new StringUtil();
		pr.printParams();
		
		String v_seq = pr.getString("v_seq");
		String nowpage = pr.getString("nowpage");
		
		String mode = pr.getString("mode");

		VocDataMgr mgr = new VocDataMgr();
		VocBean.VocProBean mbean = mgr.getVocProMasterList(v_seq);
		
		
		String sDate = "";
		String eDate = "";
		String fDate = "";

		if(!mbean.getSdate().equals("")){
			sDate = mbean.getSdate().substring(0,4) + "-" + mbean.getSdate().substring(4,6) + "-" + mbean.getSdate().substring(6,8);
		}
		if(!mbean.getEdate().equals("")){
			eDate = mbean.getEdate().substring(0,4) + "-" + mbean.getEdate().substring(4,6) + "-" + mbean.getEdate().substring(6,8);
		}
		if(!mbean.getFdate().equals("")){
			fDate = mbean.getFdate().substring(0,4) + "-" + mbean.getFdate().substring(4,6) + "-" + mbean.getFdate().substring(6,8);
		}
		
		ArrayList arr_data = mgr.getVocProDetailList(v_seq);
		VocBean.VocProDetailBean dbean = (VocBean.VocProDetailBean)arr_data.get(0);
		
		//승인권한 있는 유저 가져오기~
		ArrayList app_user = mgr.getCategoryUser("APP","");
		
		
		
		String altTitle = "";
		if(mbean.getStatus().equals("2") && (mgr.getLastNo() % 2 == 0)){
			altTitle = "VOC 승인요청이 접수";
		}else if(mbean.getStatus().equals("2") && (mgr.getLastNo() % 2 == 1)){
			altTitle = "VOC 처리요청이 접수";
		}else if(mbean.getStatus().equals("3")){
			altTitle = "VOC 완료";
		}
		
		
%>


<%@page import="risk.voc.VocBean.VocMBean"%>
<%@page import="risk.voc.VocBean.VocMBean"%><html>
<head>
<title><%=SS_TITLE%></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="css/base.css" />
<style>
iframe.hide { border:0px solid red; position:absolute; top:0px; left:0px; z-index:-99; width:148px; height:150px; filter: alpha(opacity=0);}
</style>
<script src="<%=SS_URL%>js/Calendar.js" type="text/javascript"></script>
<SCRIPT LANGUAGE="JavaScript">

function loding(){
	 var imgObj = document.getElementById("sending");
	 imgObj.style.left = (document.body.clientWidth / 2) - (imgObj.width / 2);
	 imgObj.style.top = (document.body.clientHeight / 2) - (imgObj.height / 2);
	 imgObj.style.display = 'none'; 
}

 function showPrc(mode){

		var f = document.fSend;

		//파일 업로드
		/*
		if(file_up.iSend.upfile.value != ""){
			alert("Add an attachment");
			return;
		}
		if(file_up.iSend.fname){
			f.fname.value= file_up.iSend.mfilename.value;
			f.fOname.value= file_up.iSend.original_mname.value;
		}
		*/

		var img_app = document.getElementById('img_app');
		var img_reapp = document.getElementById('img_reapp');
		var img_req = document.getElementById('img_req');

		if(img_app){
			img_app.style.display = 'none';
		}
		if(img_reapp){
			img_reapp.style.display = 'none';
		}
		if(img_req){
			img_req.style.display = 'none';
		}

		document.getElementById("sending").style.display = '';

		f.mode.value = mode;
		f.target='if_samelist';
		f.action='voc_pro_data_prc.jsp';
    	f.submit();

	}

 	/*
 	function goFileDown(str){

		var v = document.fSend;
		v.getFile.value = str;
		v.target="if_samelist";
		v.action="inc_file_down.jsp";
		v.submit();
		
	}
	*/
	
</SCRIPT>
</head>

<body onload="loding();" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<iframe id="if_samelist" name="if_samelist" width="100%" height="0" src="about:blank" style="display: none"></iframe>
<form name="fSend" id="fSend" action="issue_prc.jsp" method="post" onsubmit="return false;">
<input type="hidden" name="mode">
<input type="hidden" name="v_seq" value="<%=v_seq%>">
<input type="hidden" name="nowpage" value="<%=nowpage%>">
<input type="hidden" name="send" value="<%=SS_M_NO%>">
<input type="hidden" name="send_name" value="<%=SS_M_NAME%>">
<input type="hidden" name="resend" value="<%=mbean.getPro_user()%>">
<input type="hidden" name="resend_name" value="<%=mbean.getPro_user_name()%>">
<input type="hidden" name="md_title" value="<%=mbean.getMd_title()%>">


<img  id="sending" src="../../images/issue/sending.gif" style="position: absolute;" >

<table width="700" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
    	<%
    		
    		if(mbean.getStatus().equals("2")){
    			out.println("<img src=\"../../images/voc/mail_top_01.gif\"  />");
    		}else if(mbean.getStatus().equals("3")){
    			out.println("<img src=\"../../images/voc/mail_top_02.gif\"  />");
    		}else{
    			out.println("<img src=\"../../images/voc/mail_top.gif\"  />");
    		}
    	%>
    
    	
    </td>
  </tr>
  <tr>
    <td><table width="700" border="0" cellpadding="0" cellspacing="1" >
      <tr>
        <td bgcolor="#FFFFFF"><table width="670" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
          
          
          
            <td height="35" style="padding:0px 0px 0px 3px"><img src="../../images/voc/arrow.gif" width="7" height="10" /><span class="gray_s"> CJ 시스템에서 </span><span class="orange_s"><strong><%=altTitle%></strong></span><span class="gray_s">되었습니다. </span></td>
          </tr>
          <tr>
            <td><table width="670" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="2" bgcolor="A09375"></td>
              </tr>
              <tr>
                <td><table width="670" border="0" cellpadding="0" cellspacing="1" bgcolor="#cccccc">
                  <tr>
                    <td width="100" height="30" bgcolor="#F5F5F5" style="padding:0px 0px 0px 10px"><strong>제목</strong></td>
                    <td colspan="3" bgcolor="#FFFFFF" style="padding:0px 0px 0px 5px"><a href="<%=mbean.getMd_url()%>" target="_blank"><%=su.ChangeString(su.cutString(mbean.getMd_title(), 45, "..."))%></a></td>
                    </tr>
                  <tr>
                    <td width="100" height="30" bgcolor="#F5F5F5" style="padding:0px 0px 0px 10px"><strong>출처</strong></td>
                    <td width="235" bgcolor="#FFFFFF" style="padding:0px 0px 0px 5px"><%=mbean.getMd_site_name()%></td>
                    <td width="100" bgcolor="#F5F5F5" style="padding:0px 0px 0px 10px"><strong>수집시간</strong></td>
                    <td width="235" bgcolor="#FFFFFF" style="padding:0px 0px 0px 5px"><%=du.getDate(mbean.getMd_date(),"yyyy-MM-dd HH:mm")%></td>
                  </tr>
                  <tr>
                    <td width="100" height="30" bgcolor="#F5F5F5" style="padding:0px 0px 0px 10px"><strong>등록자</strong></td>
                    <td bgcolor="#FFFFFF" style="padding:0px 0px 0px 5px"><%=mbean.getM_name()%></td>
                    <td bgcolor="#F5F5F5" style="padding:0px 0px 0px 10px"><strong>등록시간</strong></td>
                    <td bgcolor="#FFFFFF" style="padding:0px 0px 0px 5px"><%=du.getDate(mbean.getI_regdate(),"yyyy-MM-dd HH:mm")%></td>
                  </tr>
                  <tr>
                    <td width="100" height="30" bgcolor="#F5F5F5" style="padding:0px 0px 0px 10px"><strong>요청자</strong></td>
                    <td bgcolor="#FFFFFF" style="padding:0px 0px 0px 5px"><%=mbean.getAss_user_name()%></td>
                    <td bgcolor="#F5F5F5" style="padding:0px 0px 0px 10px"><strong>요청시간</strong></td>
                    <td bgcolor="#FFFFFF" style="padding:0px 0px 0px 5px"><%=du.getDate("yyyy-MM-dd HH:mm")%></td>
                  </tr>
                  <tr>
                    <td width="100" height="30" bgcolor="#F5F5F5" style="padding:0px 0px 0px 10px"><strong>처리자</strong></td>
                    <td colspan="3" bgcolor="#FFFFFF" style="padding:0px 0px 0px 5px"><%=mbean.getMg_name() + " > " + mbean.getPro_user_name()%></td>
                    </tr>
                  <tr>
                    <td width="100" height="30" bgcolor="#F5F5F5" style="padding:0px 0px 0px 10px"><strong>처리기간</strong></td>
                    <%
						if(mbean.getStatus().equals("3")){
					%>
						<td colspan="3" bgcolor="#FFFFFF" style="padding:0px 0px 0px 5px"><%=sDate%>  ~  <%=eDate%></td>
					<%		
						}else{
					%>
					<td colspan="3" bgcolor="#FFFFFF" style="padding:0px 0px 0px 5px">
						<input name="sDateFrom" id="sDateFrom" type="text" size="10"  maxlength="10" class="txtbox" style="vertical-align:middle;" onChange="chkDate(this)" onFocus="this.select();" onClick="this.select();" value="<%=sDate%>">
			        	<img src="../../images/issue/btn_calendar.gif" width="19" height="19" style="vertical-align:middle;cursor:pointer" onclick="calendar_sh(document.getElementById('sDateFrom'));">~ 
	                    	<input name="sDateTo" id="sDateTo" type="text" size="10"  maxlength="10" class="txtbox" style="vertical-align:middle;" onChange="chkDate(this)" onFocus="this.select();" onClick="this.select();" value="<%=eDate%>" size="11">
			        	<img src="../../images/issue/btn_calendar.gif" width="19" height="19" style="vertical-align:middle;cursor:pointer" onclick="calendar_sh(document.getElementById('sDateTo'));">
					</td>
					
					<%		
						}
                    %>
                    
                    
                    
                    </tr>
                  <tr>
                    <td width="100" height="30" bgcolor="#F5F5F5" style="padding:0px 0px 0px 10px"><strong>요청내용</strong></td>
                    <td colspan="3" bgcolor="#FFFFFF" style="padding:0px 0px 0px 5px"><%=dbean.getSummary()%></td>
                    </tr>
				<%
					VocBean.VocProDetailBean sbean = null; 
					String title = "";
					for(int i = 1; i < arr_data.size(); i++){
						sbean = (VocBean.VocProDetailBean) arr_data.get(i);
						if(i != arr_data.size() -1){
							if(Integer.parseInt(sbean.getVs_seq()) % 2 == 0){
								title = "처리내용";
							}else{
								title = "재요청내용";
							}
						}else {
							if(mbean.getStatus().equals("3")){
								title = "승인내용";
							}else{
								if(Integer.parseInt(sbean.getVs_seq()) % 2 == 0){
									title = "처리내용";
								}else{
									title = "재요청내용";
								}
							}			
						}
				%>		
						
					<tr>
	                    <td width="100" height="30" bgcolor="#F5F5F5" style="padding:0px 0px 0px 10px"><strong><%=title%></strong></td>
	                    <td colspan="3" bgcolor="#FFFFFF" style="padding:0px 0px 0px 5px"><%=sbean.getSummary()%></td>
                    </tr>
						
				<%		
					}	
                 %> 
                  
                  
                 <%
                 	Boolean check = false;
					if(mbean.getStatus().equals("2") && mode.equals("write")){
						
						if(mgr.searchUser(app_user,SS_M_NO) && (mgr.getLastNo() % 2 == 0)){
							title = "승인내용";
							check = true;
				%>
							<tr>
			                    <td width="100" height="30" bgcolor="#F5F5F5" style="padding:0px 0px 0px 10px"><strong><%=title%></strong></td>
			                    <td colspan="3" bgcolor="#FFFFFF" style="padding:5px 5px 5px 5px"><label>
			                      <textarea name="summary" cols="70" rows="5" class="input_text02" id="summary"></textarea>
			                    </label></td>
			                  </tr>

				<%			
						}else if(mbean.getPro_user().equals(SS_M_NO) && (mgr.getLastNo() % 2 == 1)){
							title = "처리내용";
							check = true;
				%>			
							<tr>
			                    <td width="100" height="30" bgcolor="#F5F5F5" style="padding:0px 0px 0px 10px"><strong><%=title%></strong></td>
			                    <td colspan="3" bgcolor="#FFFFFF" style="padding:5px 5px 5px 5px"><label>
			                      <textarea name="summary" cols="70" rows="5" class="input_text02" id="summary"></textarea>
			                    </label></td>
			                  </tr>			
				<%			
						}
					}
				%>		 
                  
                  
                  
                </table></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td height="15"></td>
          </tr>
          <tr>
            <td align="center">
            <%
            	if(check){
            		if(mgr.getLastNo() % 2 == 0){
            %>
		            	<img id="img_app" src="../../images/voc/btn_04.gif" onclick="showPrc('app');" style="cursor: pointer;">
		            	<img id="img_reapp" src="../../images/voc/btn_05.gif" onclick="showPrc('reapp');" style="cursor: pointer;">
		            	<img src="../../images/voc/btn_02.gif" hspace="5" onclick="window.close();" style="cursor: pointer;">
            <%		}else{ %>	
		            	<img id="img_req" src="../../images/voc/btn_03.gif" onclick="showPrc('req');" style="cursor: pointer;">
		            	<img src="../../images/voc/btn_02.gif" hspace="5" onclick="window.close();" style="cursor: pointer;">
            <%
            		}
            	}
            %>	
            </td>
          </tr>
          <tr>
            <td height="60"></td>
          </tr>
        </table></td>
      </tr>
    </table></td>
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
</body>
</html>
