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
		
		
		
		String sDateFrom = "";
		String sDateTo = "";
		
		String nowpage = pr.getString("nowpage");
		
		//검색날짜 설정 기본 7일간 검색한다.
		String sCurrDate = du.getCurrentDate();
		
		if(sDateFrom.equals("")){
			sDateFrom = du.getDate("yyyy-MM-dd");
		}
		if(sDateTo.equals("")){
			du.addDay(7);
			sDateTo = du.getDate();
		}
		
		//카테고리 해당 멤버목록 가져오기~
		VocDataMgr vMgr = new VocDataMgr();
		
		VocBean.AssignBean cbean = vMgr.getAssData(v_seq);
		
		
		//배정자 그룹
		ArrayList arr_data1 = vMgr.getVocProGroup();
		//배정자
		ArrayList arr_data2 = vMgr.getCategoryUser();
		
		
		//ArrayList arr_neg = vMgr.getNegReasons(v_seq);
		
		
		
		//System.out.println("sDateFrom:"+sDateFrom+"/sDateTo:"+sDateTo);
		
%>


<%@page import="java.net.URLEncoder"%><html>
<head>
<title><%=SS_TITLE%></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="css/base.css" />
<link rel="stylesheet" href="css/Calendar.css" type="text/css">
<style>
iframe.hide { border:0px solid red; position:absolute; top:0px; left:0px; z-index:-99; width:148px; height:150px; filter: alpha(opacity=0);}
</style>
<script src="<%=SS_URL%>js/Calendar.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/jquery.js" type="text/javascript"></script>

<SCRIPT LANGUAGE="JavaScript">


	var key_seq = 0;

	function loding(){
		 var imgObj = document.getElementById("sending");
		 imgObj.style.left = (document.body.clientWidth / 2) - (imgObj.width / 2);
		 imgObj.style.top = (document.body.clientHeight / 2) - (imgObj.height / 2);
		 imgObj.style.display = 'none'; 
	}

	function issueSave(){

		var f = document.fSend;

		f.resend_name.value = f.resend.options[f.resend.selectedIndex].innerHTML; 
		
		if(getByteLength(f.summary.value) > 2048){
			alert("2048Byte를 초과 할 수 없습니다.");
			f.savebtn1.style.display='';
			f.savebtn2.style.display='';
			return;
		}


		
		if(!f.user_val){
			alert("처리자를 선택하세요.");
			return;
		}

		document.getElementById("sending").style.display = '';
		
		f.mode.value='write';
		f.target='if_samelist';
		f.action='voc_data_prc.jsp';
        f.submit();

	}

	function getByteLength(input){
		var iByteLength = 0;
		for(var i = 0; i < input.length; i++){
			var sChar = escape(input.charAt(i));
			if(sChar.length == 1){
				iByteLength++;
			}else if(sChar.indexOf("%u") != -1){
				iByteLength +=2;
			}else if(sChar.indexOf("%") != -1){
				iByteLength += sChar.length/3;	
			}
		}

		return iByteLength;
	}

	//var sCurrDate = '<%//=sDateFrom%>';
	var tempDateTime = 0;
	
	function setDate( day ) {
        var f = document.fSend;        
        //f.sDateFrom.value   = sCurrDate;       
        f.sDateTo.value   = AddDate( day );       
    }

	function java_all_trim(a) {
        for (; a.indexOf(" ") != -1 ;) {
          a = a.replace(" ","");
        }
        return a;
	}
	
	function AddDate( day ){
		var newdate = new Date();
		var resultDateTime;
		var tempDateTime = 0;
		
		if(tempDateTime==0)
		{
			tempDateTime = newdate.getTime();
		}  
              
		resultDateTime = tempDateTime + ( day * 24 * 60 * 60 * 1000);      
        newdate.setTime(resultDateTime);

        var year;
        var month;
        var day;
		var result_date;
		year = newdate.getFullYear();
		month = newdate.getMonth()+1;
		day = newdate.getDate();
		if(month < 10){
			month = '0'+month;
		}
		if(day < 10){
			day = '0'+day;
		}
        result_date = year + '-' + month + '-' + day;
        return result_date;
	}

	function getProUser(){
		var resend = fSend.resend;

		if(document.fSend.group_resend.value == ""){
			resend.options.length = 0;
			resend.options.add(new Option("처리자 선택", ""));
		}else{
			$.ajax({
				dataType: "json",
				url: "selectbox_pro_user.jsp",
				data: "resend=" + document.fSend.group_resend.value,
				success: function(data){
						resend.options.length = 0;
								
						resend.options.add(new Option("처리자 선택", ""));
						
						for(var i =0; i <data.length; i++){
							resend.options.add(new Option(data[i].m_name, data[i].m_seq));		
						} 
						}
			});
		}
		
		
	}


	
	
	function addUser(){

		var f = document.fSend;

		
		var group_resend = f.group_resend.options[f.group_resend.selectedIndex].innerHTML;
		var resend = f.resend.options[f.resend.selectedIndex].innerHTML;
		var resend_val = f.resend.options[f.resend.selectedIndex].value;
			
		
		if(resend_val != ''){

			var name = group_resend + "(" + resend + ")";
			var t_obj = null;
			//User체크
			for(var i = 1; i <= key_seq; i++){
				t_obj = document.getElementById('td_user_' + i);
				if(t_obj){
					if(name == t_obj.innerHTML.split('&')[0]){
						alert(name + "은(는) 이미 추가 되었습니다.");
						return;
					}
				}
			}

			key_seq++;
			
			var AddHtml = group_resend + "(" + resend + ")" + "&nbsp;<img src=\"../../images/voc/delete_btn_01.gif\" style=\"vertical-align: middle\" onclick=\"delUser('"+ key_seq +"');\">&nbsp;&nbsp;";
			AddHtml += "<input type='hidden' name='user_val' value='"+resend_val+"'>";
			AddHtml += "<input type='hidden' name='user_name' value='"+resend+"'>";
			var table = document.getElementById('tb_userList'); 
			var row = table.rows[0];
			var col = row.insertCell(-1);

			col.id = 'td_user_'+ key_seq;
			col.innerHTML = AddHtml;	
		}
	}

	function delUser(idx){

		var obj = document.getElementById('td_user_' + idx);

		if(obj){
			var table = document.getElementById('tb_userList');
			table.rows[0].deleteCell(obj.cellIndex);
		}

	}

	
</SCRIPT>
</head>
<body onload="loding();" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<iframe id="if_samelist" name="if_samelist" width="100%" height="0" src="about:blank" style="display: none"></iframe>
<form name="fSend" id="fSend" action="issue_prc.jsp" method="post" onsubmit="return false;">

<input type="hidden" name="v_seq" value="<%=v_seq%>">
<input type="hidden" name="send" value="<%=SS_M_NO%>">
<input type="hidden" name="send_name" value="<%=SS_M_NAME%>">
<input type="hidden" name="resend_name">
<input type="hidden" name="nowpage" value="<%=nowpage%>">
<input type="hidden" name="mode">
<input type="hidden" name="md_title" value="<%=cbean.getMd_title()%>">

<img  id="sending" src="../../images/issue/sending.gif" style="position: absolute;" >

<table width="700" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><img src="../../images/voc/mail_top.gif" width="700" height="59" /></td>
  </tr>
  <tr>
    <td><table width="700" border="0" cellpadding="0" cellspacing="1" bgcolor="E5E5E5">
      <tr>
        <td bgcolor="#FFFFFF"><table width="670" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td height="35" style="padding:0px 0px 0px 3px"><img src="../../images/voc/arrow.gif" width="7" height="10" /><span class="gray_s"> CJ 시스템에서 </span><span class="orange_s"><strong>VOC 배정 요청이 접수</strong></span><span class="gray_s">되었습니다. </span></td>
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
                    <td colspan="3" bgcolor="#FFFFFF" style="padding:0px 0px 0px 5px"><a href="<%=cbean.getMd_url()%>" target="_blank"><%=su.ChangeString(su.cutString(cbean.getMd_title(), 45, "..."))%></a></td>
                    </tr>
                  <tr>
                    <td width="100" height="30" bgcolor="#F5F5F5" style="padding:0px 0px 0px 10px"><strong>출처</strong></td>
                    <td width="235" bgcolor="#FFFFFF" style="padding:0px 0px 0px 5px"><%=cbean.getMd_site_name()%></td>
                    <td width="100" bgcolor="#F5F5F5" style="padding:0px 0px 0px 10px"><strong>수집시간</strong></td>
                    <td width="235" bgcolor="#FFFFFF" style="padding:0px 0px 0px 5px"><%=du.getDate(cbean.getMd_date(),"yyyy-MM-dd HH:mm")%></td>
                  </tr>
                  <tr>
                    <td width="100" height="30" bgcolor="#F5F5F5" style="padding:0px 0px 0px 10px"><strong>등록자</strong></td>
                    <td bgcolor="#FFFFFF" style="padding:0px 0px 0px 5px"><%=cbean.getM_name()%></td>
                    <td bgcolor="#F5F5F5" style="padding:0px 0px 0px 10px"><strong>등록시간</strong></td>
                    <td bgcolor="#FFFFFF" style="padding:0px 0px 0px 5px"><%=du.getDate(cbean.getI_regdate(),"yyyy-MM-dd HH:mm")%></td>
                  </tr>
                  <tr>
                    <td width="100" height="30" bgcolor="#F5F5F5" style="padding:0px 0px 0px 10px"><strong>요청자</strong></td>
                    <td bgcolor="#FFFFFF" style="padding:0px 0px 0px 5px"><%=SS_M_NAME%></td>
                    <td bgcolor="#F5F5F5" style="padding:0px 0px 0px 10px"><strong>요청시간</strong></td>
                    <td bgcolor="#FFFFFF" style="padding:0px 0px 0px 5px"><%=du.getDate("yyyy-MM-dd HH:mm")%></td>
                  </tr>
                  <tr>
                    <td width="100" height="30" bgcolor="#F5F5F5" style="padding:0px 0px 0px 10px"><strong>처리자</strong></td>
                    <td colspan="3" bgcolor="#FFFFFF" style="padding:5px 0px 0px 5px">
                    	<select id="group_resend" name="group_resend" style="width: 100px; vertical-align: middle;" onchange="getProUser();" >
							<option value="">그룹 선택</option>
						<%
							VocBean.proGroupBean pBean = null;
							for(int i = 0; i < arr_data1.size(); i++){					
								pBean = (VocBean.proGroupBean)arr_data1.get(i);
						%>
							<option value="<%=pBean.getMg_seq()%>"><%=pBean.getMg_name()%></option>
						<%	}%>	
						</select>
                    
                    	<select name="resend" style="width: 100px; vertical-align: middle;" >
                    	<option value="">처리자 선택</option>
						<%
						/*
							VocBean.CategoryUserBean cBean = null;
							for(int i = 0; i < arr_data2.size(); i++){					
								cBean = (VocBean.CategoryUserBean)arr_data2.get(i);
								*/
						%>
<!--							<option value="<%//=cBean.getM_seq()%>"><%//=cBean.getM_name()%></option>-->
						<%	//}%>	
						</select>
						<img src="../../images/voc/plus_btn.gif" onclick="addUser();" style="vertical-align: middle">
						
						<table id="tb_userList" style="margin-top: 2px; margin-bottom: 2px; font-weight: bold;">
							<tr>
							
							</tr>
						</table>
						
                    </td>
                    </tr>
                  <tr>
                    <td width="100" height="30" bgcolor="#F5F5F5" style="padding:0px 0px 0px 10px"><strong>처리기간</strong></td>
                    <td colspan="3" bgcolor="#FFFFFF" style="padding:0px 0px 0px 5px">
                    	<input name="sDateFrom" id="sDateFrom" type="text" size="10"  maxlength="10" class="txtbox" style="vertical-align:middle;" onChange="chkDate(this)" onFocus="this.select();" onClick="this.select();" value="<%=sDateFrom%>">
		        	<img src="../../images/issue/btn_calendar.gif" width="19" height="19" style="vertical-align:middle;cursor:pointer" onclick="calendar_sh(document.getElementById('sDateFrom'));">~ 
                    	<input name="sDateTo" id="sDateTo" type="text" size="10"  maxlength="10" class="txtbox" style="vertical-align:middle;" onChange="chkDate(this)" onFocus="this.select();" onClick="this.select();" value="<%=sDateTo%>" size="11">
		        	<img src="../../images/issue/btn_calendar.gif" width="19" height="19" style="vertical-align:middle;cursor:pointer" onclick="calendar_sh(document.getElementById('sDateTo'));">
                    </td>
                    </tr>
                  <tr>
                    <td width="100" height="30" bgcolor="#F5F5F5" style="padding:0px 0px 0px 10px"><strong>요청내용</strong></td>
                    <td colspan="3" bgcolor="#FFFFFF" style="padding:5px 5px 5px 5px"><label>
                      <textarea name="summary" cols="70" rows="5" class="input_text02" id="summary"></textarea>
                    </label></td>
                    </tr>
                  
                </table></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td height="15"></td>
          </tr>
          <tr>
            <td align="center"><img src="../../images/voc/btn_01.gif" width="76" height="28" onclick="issueSave('save')" style="cursor: pointer;"><img src="../../images/voc/btn_02.gif" width="62" height="28" hspace="5" onclick="window.close();" style="cursor: pointer;"></td>
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
