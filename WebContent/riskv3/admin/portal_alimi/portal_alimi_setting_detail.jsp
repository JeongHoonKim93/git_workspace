<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%@ page import="	java.util.ArrayList
                 	,risk.mobile.PortalAlimiSettingBean
					,risk.mobile.PortalAlimiSettingDao
					,risk.util.ParseRequest
					,risk.admin.site.SitegroupBean
				 	,risk.admin.site.SiteMng
				 	,risk.search.userEnvMgr
				 	,risk.search.keywordInfo
				 	,risk.admin.classification.classificationMgr
				 	,risk.admin.classification.clfBean
				 	,java.util.List
				 	,risk.search.userEnvMgr
                    ,risk.search.userEnvInfo
					" 
%>
<%
	ParseRequest pr = new ParseRequest(request);
	PortalAlimiSettingDao pasDao = new PortalAlimiSettingDao();
	PortalAlimiSettingBean pasBean = null;	
		
	String mode = pr.getString("mode","INSERT");
	String pas_seq = pr.getString("pas_seq","");
	String ab_seq = null;
	String nowpage = pr.getString("nowpage","");
	
	String styleDisplay1 = "";
	String styleDisplay2 = "";
	String styleDisplay3 = "";
	String styleDisplay4 = "";
	
	ArrayList alimiSetList = new ArrayList();
	
	String[] smsTime = null;
	String smsTimeCheck1 =null;
	String smsTimeCheck2 =null;
	String smsTimeCheck3 =null;
	String smsTimeCheck4 =null;
	String smsTimeCheck5 =null;
	
	//pr.printParams();
	
	if(mode.equals("UPDATE") && pas_seq.length()>0)
	{
		ab_seq = pasDao.getReceiverSeq(pas_seq);
		//System.out.println("ab_seq:"+ab_seq);
		alimiSetList = pasDao.getPortalAlimiSetList(1,0,pas_seq,"Y");
		pasBean = (PortalAlimiSettingBean)alimiSetList.get(0);

		if(pasBean.getPas_sms_time()!=null && !pasBean.getPas_sms_time().equals(""))
		{
			smsTime = pasBean.getPas_sms_time().split(",");
  			if(smsTime!=null)
  			{
  				for(int i=0;i<smsTime.length;i++)
  				{
  					if(smsTime[i].equals("1"))smsTimeCheck1="checked";
  					if(smsTime[i].equals("2"))smsTimeCheck2="checked";
  					if(smsTime[i].equals("3"))smsTimeCheck3="checked";
  					if(smsTime[i].equals("4"))smsTimeCheck4="checked";
  					if(smsTime[i].equals("5"))smsTimeCheck5="checked";
  				}
  			}
		}
			
	}
	
	userEnvMgr uemng = new userEnvMgr();
	userEnvInfo uei = null;
    uei     = (userEnvInfo) session.getAttribute("ENV");

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>RISK</title>
<link rel="stylesheet" type="text/css" href="../../../css/base.css" />
<script src="../../../js/jquery.js"  type="text/javascript"></script>
<script src="../../../js/ajax.js"  type="text/javascript"></script>
<script src="../../../js/common.js"  type="text/javascript"></script>
<script src="../../../js/calendar.js" type="text/javascript"></script>
<script language="javascript">
function save()
{
	var f = document.alimi_detail;
	var checkValue = false;
	//alert(asType);

	if(f.pas_title.value.length==0){alert('제목을 입력하여 주십시오.'); f.pas_title.focus(); return;}			
	f.pas_sms_time.value=checkedSmsTimeValue();		
	checkAbAeqValue();			
	f.target = '';
	f.action ='portal_alimi_setting_prc.jsp';
	f.submit();
	
}

function checkedSmsTimeValue()
{	
	var obj = eval('document.alimi_detail.pas_sms_time1');
	var pasSmsTime = '';
	//alert(obj.length);
	if(obj.length)
	{
		for(var i =0 ; i<obj.length; i++)
		{
			if(obj[i].checked)
			{
				if(pasSmsTime=='')
				{				
					pasSmsTime = obj[i].value;
				}else{
					pasSmsTime +=',' + obj[i].value;
				}
			}
		}
	}else{
		if(obj.checked)
		{			
			pasSmsTime = obj.value;
		}
	}	
	return pasSmsTime;
}

function checkAbAeqValue(){
	var f =  frmReceiver.document.ifr_receiver;
	var pf = document.alimi_detail;

	pf.ab_seqs.value = '';

	if(f.chkNum)
	{
		if(f.chkNum.length)
		{
			for( var i = 0; i<f.chkNum.length; i++)
			{
				if(f.chkNum[i].checked)
				{
					if(pf.ab_seqs.value=='')
					{
						pf.ab_seqs.value =f.chkNum[i].value;
					}else{
						pf.ab_seqs.value = pf.ab_seqs.value+','+ f.chkNum[i].value;
					}
				}
			}
		}else{
			if( f.chkNum.checked)
			{
				pf.ab_seqs.value = f.chkNum.value;
			}
		}	
	}
	//alert(pf.ab_seqs.value);
}
</script>
</head>
<body style="margin-left: 15px">
<form id="alimi_detail" name="alimi_detail" method="post">
<input type="hidden" name="mode" value="<%=mode%>">
<input type="hidden" name="nowpage" value="<%=nowpage%>">
<input type="hidden" name="pas_seq" value="<%=pas_seq%>">
<input type="hidden" name="ab_seqs">
<input type="hidden" name="pas_sms_time">

<table style="width:100%;height:100%;" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="body_bg_top" valign="top">
		<table style="width:820px;" border="0" cellpadding="0" cellspacing="0">
			<!-- 타이틀 시작 -->
			<tr>
				<td class="tit_bg" style="height:67px;padding-top:20px;">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="../../../images/admin/portal_alimi/tit_icon.gif" /><img src="../../../images/admin/portal_alimi/tit_0509.gif" /></td>
						<td align="right">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="navi_home">HOME</td>
								<td class="navi_arrow">관리자</td>
								<td class="navi_arrow2">포탈 알리미 설정관리</td>
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
			<!-- 타이틀 끝 -->
			<!-- 게시판 시작 -->
			<tr>
				<td>
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td style="height:30px;"><span class="sub_tit">기본 설정</span></td>
					</tr>
					<tr>
						<td>
						<table id="board_write" border="0" cellspacing="0">
							<tr>
								<th><span class="board_write_tit">제목</span></th>
								<td><input style="width:80%;" name="pas_title" type="text" class="textbox2" value="<%if(mode.equals("UPDATE")){out.print(pasBean.getPas_title());}%>"></td>
							</tr>
							<tr>
								<th><span class="board_write_tit">발송매체</span></th>
								<td>
								<ul>
									<li style="padding-right:20px;"><input name="pas_type" type="radio" value="3" onclick="" checked>R-rimi 발송</li>
								</ul>		
								</td>
							</tr>
							<%
								String chked1 = "checked";
								String chked2 = "";
								if(mode.equals("UPDATE")){
									String chkNum = pasBean.getPas_chk();
									
									if(chkNum.equals("1")){
										chked1 = "checked";
										chked2 = "";
									}else{
										chked1 = "";
										chked2 = "checked";
									}
								}
							%>
							<tr>
								<th><span class="board_write_tit">정보 발송 여부</span></th>
								<td>
								<ul>
									<li style="padding-right:20px;"><input name="pas_chk" type="radio" value="1" <%=chked1 %>>발송</li>
									<li style="padding-right:20px;"><input name="pas_chk" type="radio" value="2" <%=chked2 %>>발송중지</li>
								</ul>		
								</td>
							</tr>
						</table>
						</td>
					</tr>
					<tr>
						<td style="padding-top:15px;height:45px;"><span class="sub_tit">발송 조건</span></td>
					</tr>
					<tr>
						<td>
						<table id="board_write" border="0" cellspacing="0">

							<tr id="smsTimeFilter">
								<th><span class="board_write_tit">발송시간</span></th>
								<td>
									<input name="pas_sms_time1" type="checkbox" value="1" <%=smsTimeCheck1 %>>평일 근무시간 ( 06:00 ~ 22:00 )<br>
									<input name="pas_sms_time1" type="checkbox" value="2" <%=smsTimeCheck2 %>>평일 근무외시간  ( 22:00 ~ 명일 06:00)<br>
									<input name="pas_sms_time1" type="checkbox" value="3" <%=smsTimeCheck3 %>>주말 근무시간1 ( 06:00 ~ 22:00 )<br>
									<input name="pas_sms_time1" type="checkbox" value="4" <%=smsTimeCheck4 %>>주말 근무시간2 ( 09:00 ~ 21:00 )<br>
									<input name="pas_sms_time1" type="checkbox" value="5" <%=smsTimeCheck5 %>>주말 근무외시간 ( 22:00 ~ 명일 06:00 )<br>
								</td>
							</tr>

							<tr>
								<th><span class="board_write_tit">발신번호</span></th>
								<td>
									<input type="text" maxlength="15" size="15" name="pas_tran_num" value="07043760221">
								</td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				<iframe name="frmReceiver" src="ifrm_receiver_list.jsp?ab_seq=<%=ab_seq%>" frameborder="0" scrolling="no" width="820" height="470"></iframe>
				</td>
			</tr>
			<!-- 게시판 끝 -->
			<tr>
				<td style="height:40px;" align="center"><img src="../../../images/admin/portal_alimi/btn_save2.gif" onclick="save();" style="cursor:pointer;"/><img src="../../../images/admin/alimi/btn_cancel.gif" onclick="goList();" style="cursor:pointer;"/></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</form>
</body>
</html>