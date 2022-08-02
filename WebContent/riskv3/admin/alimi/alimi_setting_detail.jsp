<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%@ page import="	java.util.ArrayList
                 	,risk.mobile.AlimiSettingBean
					,risk.mobile.AlimiSettingDao
					,risk.util.ParseRequest
					,risk.admin.site.SitegroupBean
				 	,risk.admin.site.SiteMng
				 	,risk.search.userEnvMgr
				 	,risk.search.keywordInfo
				 	,risk.admin.classification.classificationMgr
				 	,risk.admin.classification.clfBean
				 	,java.util.List
                    ,risk.search.MetaMgr_warning
				 	,risk.search.userEnvMgr
                    ,risk.search.userEnvInfo
					" 
%>
<%
	ParseRequest pr = new ParseRequest(request);
	AlimiSettingDao asDao = new AlimiSettingDao();
	AlimiSettingBean asBean = null;	
		
	
	String mode = pr.getString("mode","INSERT");
	String ts_seq        = pr.getString("ts_seq","");
	String ab_seq = null;
	String nowpage = pr.getString("nowpage","");
		
	//System.out.println("ts_seq는 ------------>"+ts_seq);
	
	
	String styleDisplay1 = "";
	String styleDisplay2 = "";
	String styleDisplay3 = "";
	String styleDisplay4 = "";
	
	
	ArrayList alimiSetList = new ArrayList();
	List xpList = null;
	List warningList = null;
	List sgList = null;
	
	
/* 	String[] mtType = null;
	String mtTypeCheck1 = null;
	String mtTypeCheck2 = null;
 */	
	//pr.printParams();
	String[] sdGsnList = null;
	int sdGsnCnt = 0;
	
	if(mode.equals("UPDATE") && ts_seq.length()>0)
	{
		//ab_seq = asDao.getReceiverSeq(ts_seq);
		//System.out.println("ab_seq:"+ab_seq);
		//alimiSetList = asDao.getAlimiSetList(1,0,ts_seq,"Y");
		alimiSetList = asDao.getTelegramSetList(ts_seq);
		asBean = (AlimiSettingBean)alimiSetList.get(0);
		
				
		if(asBean.getS_seqs()!=null && !asBean.getS_seqs().trim().equals(""))
		{
			sdGsnList = asBean.getS_seqs().split(",");
			sdGsnCnt = sdGsnList.length;
		}
				
		
/* 		if(asBean.getMt_types()!=null && !asBean.getMt_types().equals(""))
		{
			mtType = asBean.getMt_types().split(",");
			
  			if(mtType!=null)
  			{
  				for(int i=0;i<mtType.length;i++)
  				{
  					if(mtType[i].equals("1"))mtTypeCheck1="checked";
  					if(mtType[i].equals("2"))mtTypeCheck2="checked";  					
  				}
  			}
		}
 */		
	}
	
	userEnvMgr uemng = new userEnvMgr();
	MetaMgr_warning smgr_w = new MetaMgr_warning();
	userEnvInfo uei = null;
    uei     = (userEnvInfo) session.getAttribute("ENV");
	xpList = uemng.getKeywordGroup(uei.getMg_xp());	
	//warningList = smgr_w.getWarningGroup();
	SiteMng sitemng = new SiteMng();
	sgList = sitemng.getSGList();
	
	
	
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

	//$('document').ready(initMain);
	
	function initMain()
	{
		var f = document.alimi_detail;
		for(var i=0; i < f.k_xp.length; i++){
			if(f.k_xp[i].checked)
			{		
				changeTopKeywordGroup();
			}		
		}

		//changeTopWarningGroup();
	}

	function checkAsTypeValue(){
		var f =  document.alimi_detail;
		var asType;
		for( var i = 0; i<f.as_type.length; i++)
		{
			if(f.as_type[i].checked)
			{			
				asType =f.as_type[i].value;			
			}
		}
		return asType;
	}
						
	function checkAllKxp(chk) {
		var o=document.all.k_xp;
		for(i=0; i<o.length; i++) {
			o[i].checked = chk;
		}
	}
	
	function checkXpValue(){
		var f =  document.alimi_detail;
		var check = false;
		f.k_xps.value='';
		if(f.k_xp.length){
			for( var i = 0; i<f.k_xp.length; i++)
			{
					
				if(f.k_xp[i].checked)
				{		
					f.k_xps.value =f.k_xp[i].value;							
				}
			}
		}else{
			f.k_xps.value = f.k_xp.value;
		}
		if(f.k_xps.value.length>0)check =true;
		return check;
			
	}

	function checkYpValue(){
		var f =  document.alimi_detail;
		var check = false;
		f.k_yps.value='';
		if(f.k_yp){
			if(f.k_yp.length){
				for( var i = 0; i<f.k_yp.length; i++)
				{
					if(f.k_yp[i].checked)
					{			
						if(f.k_yps.value=='')
						{
							f.k_yps.value =f.k_yp[i].value;
						}else{
							f.k_yps.value = f.k_yps.value+','+ f.k_yp[i].value;
						}	
						if(f.keywords.value=='')
						{
							f.keywords.value = f.k_xp.value+'@'+ f.k_yp[i].value;
						}else{
							f.keywords.value = f.keywords.value+','+f.k_xp.value+'@'+ f.k_yp[i].value;
						}
					}
				}
			}else{
				if(f.k_yp.checked)
				{		
				f.k_yps.value = f.k_yp.value;
				f.keywords.value = f.k_xp.value+'@'+ f.k_yp.value;
				}	
			}
		}
		//alert(f.k_yps.value);
		if(f.k_yps.value.length>0)check =true;
		return check;
			
	}
		
/* 	function checkWarningValue(){
		var f =  document.alimi_detail;
		var check = false;
		f.warningkeywords.value='';
		if(f.warning_yp){
			if(f.warning_yp.length){
				for( var i = 0; i<f.warning_yp.length; i++)
				{
					if(f.warning_yp[i].checked)
					{			
						if(f.warningkeywords.value=='')
						{
							f.warningkeywords.value = f.warning_xp.value+'@'+ f.warning_yp[i].value;
						}else{
							f.warningkeywords.value = f.warningkeywords.value+','+f.warning_xp.value+'@'+ f.warning_yp[i].value;
						}
					}
				}
			}else{
				f.warningkeywords.value = f.warningkeywords.value;
			}
		}
		if(f.warningkeywords.value.length>0)check =true;
		return check;
			
	}
 */
	function checkAllSgSeq(chk) {
		var o=document.all.sg_seq;
		for(i=0; i<o.length; i++) {
			o[i].checked = chk;
		}
		
	}
	function checkSgSeqValue(){
		var f =  document.alimi_detail;
		var check = false;
		f.sg_seqs.value = '';
		for( var i = 0; i<f.sg_seq.length; i++)
		{
			if(f.sg_seq[i].checked)
			{
				if(f.sg_seqs.value=='')
				{
					f.sg_seqs.value =f.sg_seq[i].value;
				}else{
					f.sg_seqs.value = f.sg_seqs.value+','+ f.sg_seq[i].value;
				}
			}
		}
		if(f.sg_seqs.value.length>0)check =true;
		return check;	
	}
	
/* 	function checkMtTypeValue(){
		var f =  document.alimi_detail;
		var check = false;
		f.mt_types.value = '';
		for( var i = 0; i<f.mt_type.length; i++)
		{
			if(f.mt_type[i].checked)
			{			
				if(f.mt_types.value=='')
				{
					f.mt_types.value =f.mt_type[i].value;
				}else{
					f.mt_types.value = f.mt_types.value+','+ f.mt_type[i].value;
				}			
			}
		}
		if(f.mt_types.value.length>0)check =true;
		return check;
	}
 */	
		
	//저장 기능 ---> 마지막에 수정할 것
	function save()
	{
		var f = document.alimi_detail;
		var checkValue = false;
			if(f.ts_name.value.length==0){alert('그룹방 명을 입력하여 주십시오.'); f.ts_name.focus(); return;}
			/* if(f.ts_id.value.length==0){alert('ID 명을 입력하여 주십시오.'); f.ts_id.focus(); return;} */
			checkValue=checkXpValue();
			checkValue=checkYpValue();
			//return;
			if(checkValue==false){alert('키워드 그룹을 체크하여주십시오.'); return;}
			//checkValue=checkWarningValue();
			//if(checkValue==false){alert('키워드 카테고리를 체크하여주십시오.'); return;}			
			checkValue=checkSgSeqValue();
			if(checkValue==false && f.sd_gsns.value.length==0){alert('하나의 사이트 그룹이나 사이트를 지정해주십시오.'); return;}		
		
		f.target = '';
		f.action ='alimi_setting_prc.jsp';
		f.submit();
		
		
		
	}
	// 저장하면 처음 화면으로 넘어가는 기능 -----> save 이후에 실시
	function goList()
	{
		var f = document.alimi_detail;
		f.target='';
		f.action = 'alimi_setting_list.jsp';
		f.submit();
	}
	// 사이트 리스트 생성 버튼 ---> 수정해야함
	function popSiteList()
	{
		var f = document.alimi_detail;
		
/* 		 if(f.sd_gsns.value.length>0)
		{
			window.open("pop_sitelist.jsp?selectedSdGsn="+f.sd_gsns.value+"&mode="+f.mode.value, "siteList", "width=850,height=600,scrollbars=no");
		}else{			
			window.open("pop_sitelist.jsp", "siteList", "width=850,height=600,scrollbars=no");
		} 	
 */		
		var pop_title = "siteList" ;
        
        window.open("", pop_title, "width=850,height=600,scrollbars=no") ;
         
        f.target = pop_title ;
        f.action = "pop_sitelist.jsp" ;
         
        f.submit() ;

	}
	
	function changeTopKeywordGroup(val)
	{
   		var f = document.alimi_detail;
   		
 			if(f.radio_xp.value == val){				
				document.getElementById('k_xp_'+val).checked = false;
 				f.radio_xp.value = "";
			} else if(f.radio_xp.value != val){
					f.radio_xp.value = val;
					}
	   		ajax.post('sub_keywordgroup_radio.jsp','alimi_detail','subKewyrodGroupView'); 
	}
	
/* 	function changeTopWarningGroup()
	{
	    ajax.post('sub_warninggroup_radio.jsp','alimi_detail','subWarningGroupView');
	}
 */	
	function ApproveDecimal(keyCode){

		var keyChar;
		
		if(keyCode){
			keyChar = keyCode = String.fromCharCode(keyCode);
		}else{
			return true;
		}

		if(("0123456789").indexOf(keyChar) > -1){		
			event.returnValue = true; 	
		}else{
			event.returnValue = false;
		}
	}

</script>
</head>
<body style="margin-left: 15px">
<form id="alimi_detail" name="alimi_detail" method="post">
<input type="hidden" name="mode" value="<%=mode%>">
<input type="hidden" name="nowpage" value="<%=nowpage%>">
<input type="hidden" name="ts_seq" value="<%=ts_seq%>">
<input type="hidden" name="ts_seqs">
<input type="hidden" name="k_xps">
<input type="hidden" name="k_yps">
<input type="hidden" name="keywords">
<input type="hidden" name="warning_xps">
<input type="hidden" name="warning_yps">
<input type="hidden" name="warningkeywords">
<input type="hidden" name="as_sitetype">
<input type="hidden" name="sg_seqs">
<input type="hidden" name="mt_types">
<%
	if(mode.equals("INSERT")){
%>
<input type="hidden" name="sd_gsns">
<table style="width:100%;height:100%;" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="body_bg_top" valign="top">
		<table style="width:820px;" border="0" cellpadding="0" cellspacing="0">
			<!-- 타이틀 시작 -->
			<tr>
				<td class="tit_bg" style="height:67px;padding-top:20px;">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="../../../images/admin/alimi/tit_icon.gif" /><img src="../../../images/admin/alimi/tit_0509.gif" /></td>
						<td align="right">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="navi_home">HOME</td>
								<td class="navi_arrow">관리자</td>
								<td class="navi_arrow2">텔레그램 알리미 설정관리</td>
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
								<th><span class="board_write_tit">그룹방 명</span></th>
								<td><input style="width:80%;" name="ts_name" type="text" class="textbox2"></td>
							</tr>
							<tr>
								<th><span class="board_write_tit">정보 발송 여부</span></th>
								<td>
								<ul>
									<li style="padding-right:20px;"><input name="as_chk" type="radio" value="1" checked>발송</li>
									<li style="padding-right:20px;"><input name="as_chk" type="radio" value="2">발송중지</li>
								</ul>		
								</td>
							</tr>
							<!-- <tr>
								<th><span class="board_write_tit">ID 명</span></th>
								<td><input style="width:80%;" name="ts_id" type="text" class="textbox3"></td>
							</tr>	
							<tr>
								<th><span class="board_write_tit">KEY 명</span></th>
								<td><input style="width:80%;" name="ts_key" type="text" class="textbox3"></td>
							</tr>	 -->						
						</table>
						</td>
					</tr>
					<tr>
						<td style="padding-top:15px;height:45px;"><span class="sub_tit">발송 조건</span></td>
					</tr>
					<tr>
						<td>
						<table id="board_write" border="0" cellspacing="0">
							<tr class="group_hidden">
								<th><span class="board_write_tit">키워드그룹</span></th>
								<td>
<%
	for( int i=0 ; i < xpList.size() ; i++ ) {
		keywordInfo KGset = (keywordInfo)xpList.get(i);
%>
									<div style="width:150px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;float:left" title="<%=KGset.getK_Value()%>"><input type="radio" name="k_xp" value="<%=KGset.getK_Xp()%>" onclick="changeTopKeywordGroup();"><%=KGset.getK_Value()%></div>
<%
	}
%>
								</td>
							</tr>
							<tr id="subKewyrodGroupView">
							</tr>
							<tr>
								<th><span class="board_write_tit">키워드 카테고리</span></th>
								<td>
<%
	for( int i=0 ; i < warningList .size() ; i++ ) {
		keywordInfo KGset = (keywordInfo)warningList .get(i);
%>
									<div style="width:150px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;float:left" title="<%=KGset.getK_Value()%>"><input type="radio" name="warning_xp" value="<%=KGset.getK_Yp()%>" onclick="changeTopWarningGroup();"><%=KGset.getK_Value()%></div>
<%
	}
%>
							</td>
							</tr>						
							<tr id="subWarningGroupView">
							</tr>		
							<tr class="group_hidden">
								<th><span class="board_write_tit">사이트 그룹</span></th>
								<td>
									      <div style="width:150px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;float:left" title="전체"><input type="checkbox" name="sg_seqALL"  value="0" onclick="checkAllSgSeq(this.checked);">전체</div>
<%							
	for( int i=0 ; i < sgList.size() ; i++ ) {
		SitegroupBean SGinfo = (SitegroupBean)sgList.get(i);
%>		
									      <div style="width:150px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;float:left" title="<%= SGinfo.get_name()%>"><input type="checkbox" name="sg_seq" value="<%=SGinfo.get_seq()%>"><%= SGinfo.get_name()%></div>
<%
	}
%>
								</td>
							</tr>
							<tr class="group_hidden">
								<th><span class="board_write_tit">사이트 리스트</span></th>
								<td class="normal"><table border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td class="normal"><img src="../../../images/admin/alimi/btn_alrimiadd.gif" onclick="popSiteList();" style="cursor:pointer;"/></td>
										<td class="normal" id="siteListCnt"></td>
									</tr>
								</table></td>
							</tr>
							<tr id="sameProcess">
								<th><span class="board_write_tit">유사처리</span></th>
								<td>
								<ul id="sameFilter">
									<!-- <li style="padding-right:20px;"><input type="radio" name="as_same_type" value="1" checked>매체별 유사처리(포탈제외)</li>
									<li style="padding-right:20px;"><input type="radio" name="as_same_type" value="4">매체별 유사처리(포탈포함)</li> -->
									<li style="padding-right:20px;"><input type="radio" name="as_same_type" value="2" >유사처리</li>
									<li style="padding-right:20px;"><input type="radio" name="as_same_type" value="3" checked>유사처리안함</li>
								</ul>  
								</td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
<!-- 				<iframe name="frmReceiver" src="ifrm_receiver_list.jsp" frameborder="0" scrolling="no" width="820" height="270"></iframe>
 -->				</td>
			</tr>
			<!-- 게시판 끝 -->
			<tr>
				<td style="height:40px;" align="center"><img src="../../../images/admin/alimi/btn_save2.gif" onclick="save();" style="cursor:pointer;"/><img src="../../../images/admin/alimi/btn_cancel.gif" onclick="goList();" style="cursor:pointer;"/></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
<%
	}else if(mode.equals("UPDATE")){
		if(asBean!=null){
%>
<input type="hidden" name="sd_gsns" value="<%=asBean.getS_seqs()%>">
<script language="javascript">
	$('document').ready(initMain);
</script>
<table style="width:100%;height:100%;" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="body_bg_top" valign="top">
		<table style="width:820px;" border="0" cellpadding="0" cellspacing="0">
			<!-- 타이틀 시작 -->
			<tr>
				<td class="tit_bg" style="height:67px;padding-top:20px;">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="../../../images/admin/alimi/tit_icon.gif" /><img src="../../../images/admin/alimi/tit_0510.gif" /></td>
						<td align="right">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="navi_home">HOME</td>
								<td class="navi_arrow">관리자</td>
								<td class="navi_arrow2">텔레그램 알리미 설정관리</td>
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
								<th><span class="board_write_tit">그룹방 명</span></th>
								<td><input style="width:80%;" name="ts_name" type="text" class="textbox2" value="<%=asBean.getTs_name() %>"></td>
							</tr>
							<%-- <tr>
								<th><span class="board_write_tit">ID 명</span></th>
								<td><input style="width:80%;" name="ts_id" type="text" class="textbox3" value="<%=asBean.getTs_id() %>"></td>
							</tr>
							<tr>
								<th><span class="board_write_tit">KEY 명</span></th>
								<td><input style="width:80%;" name="ts_key" type="text" class="textbox3" value="<%=asBean.getTs_key()%>"></td>
							</tr>	 --%>
							<tr id="sameProcess">
								<th><span class="board_write_tit">유사처리 여부</span></th>
								<td>
								<ul id="sameFilter">
									<!-- <li style="padding-right:20px;"><input type="radio" name="as_same_type" value="1" checked>매체별 유사처리(포탈제외)</li>
									<li style="padding-right:20px;"><input type="radio" name="as_same_type" value="4">매체별 유사처리(포탈포함)</li> -->
									<li style="padding-right:20px;"><input type="radio" name="as_same_type" value="1" <%if(asBean.getSimilar().equals("1")){out.println("checked");} %>>유사처리</li>
									<li style="padding-right:20px;"><input type="radio" name="as_same_type" value="0" <%if(asBean.getSimilar().equals("0")){out.println("checked");} %>>유사처리안함</li>
								</ul>  
								</td>
							</tr>
							<tr>
								<th><span class="board_write_tit">미리보기 여부</span></th>
								<td>
								<ul>
									<li style="padding-right:20px;"><input name="preview_yn" type="radio" value="1" <%if(asBean.getTs_preview().equals("1")){out.println("checked");} %>>예</li>
									<li style="padding-right:20px;"><input name="preview_yn" type="radio" value="0" <%if(asBean.getTs_preview().equals("0")){out.println("checked");} %>>아니오</li>
								</ul>		
								</td>
							</tr>												
							<tr>
								<th><span class="board_write_tit">정보 발송 여부</span></th>
								<td>
								<ul>
									<li style="padding-right:20px;"><input name="as_chk" type="radio" value="1" <%if(asBean.getOperate_yn().equals("1")){out.println("checked");} %>>발송</li>
									<li style="padding-right:20px;"><input name="as_chk" type="radio" value="0" <%if(asBean.getOperate_yn().equals("0")){out.println("checked");} %>>발송중지</li>
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
							<tr>
								<th><span class="board_write_tit">키워드 대그룹</span></th>
								<td>
								<input type="hidden" name="radio_xp" id="radio_xp" value=""> <!-- 라디오 박스 해제용 -->														
<%
	String[] xp = asBean.getKeywords().split(",");
	String[] xp2 = null;
	String checked = "";
	for( int i=0 ; i < xpList.size() ; i++ ) {
		keywordInfo KGset = (keywordInfo)xpList.get(i);		
		checked = "";
		for(int j=0; j < xp.length; j++){
			xp2 = xp[j].split("@");
			if( xp2[0].equals(KGset.getK_Xp())){
				checked = "checked";
				break;
			}
		}
		
%>
									<div style="width:150px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;float:left" title="<%=KGset.getK_Value()%>"><input type="radio" name="k_xp" id="k_xp_<%=KGset.getK_Xp()%>" value="<%=KGset.getK_Xp()%>" onclick="changeTopKeywordGroup(this.value);" <%=checked %> ><%=KGset.getK_Value()%></div>
<%
	}
%>
							</td>
							</tr>						
							<tr id="subKewyrodGroupView">
							</tr>
<%-- 							<tr>
								<th><span class="board_write_tit">카테고리 대그룹</span></th>
								<td>
<%
	String[] warning = asBean.getKeywords_categorys().split(",");
	String[] warning2 = null;
	String checked2 = "";
	for( int i=0 ; i < warningList.size() ; i++ ) {
		keywordInfo KGset = (keywordInfo)warningList .get(i);
		checked2 = "";
		for(int j=0; j < warning.length; j++){
			warning2 = warning[j].split("@");
			if( warning2[0].equals(KGset.getK_Xp())){
				checked2="checked";
				break;
				}
			}
		
%>
									<div style="width:150px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;float:left" title="<%=KGset.getK_Value()%>"><input type="radio" name="warning_xp" value="<%=KGset.getK_Xp()%>" onclick="changeTopWarningGroup();" <%=checked2 %> ><%=KGset.getK_Value()%></div>
<%
	}
%>
							</td>
							</tr>						
							<tr id="subWarningGroupView">
							</tr>
 --%>							<tr>
								<th><span class="board_write_tit">사이트 그룹</span></th>
								<td>
									      <div style="width:150px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;float:left" title="전체"><input type="checkbox" name="sg_seqALL"  value="0" onclick="checkAllSgSeq(this.checked);">전체</div>
<%
	String[] site = asBean.getSg_seqs().split(",");
	for( int i=0 ; i < sgList.size() ; i++ ) {
		SitegroupBean SGinfo = (SitegroupBean)sgList.get(i);
%>		
									      <div style="width:150px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;float:left" title="<%= SGinfo.get_name()%>"><input type="checkbox" name="sg_seq" value="<%=SGinfo.get_seq()%>" <%for( int j=0; j < site.length; j++){if(site[j].equals(Integer.toString(SGinfo.get_seq()))){out.println("checked");}else{out.println("");}}%>><%= SGinfo.get_name()%></div>
<%
	}
%>
								</td>
							</tr>
							<tr>
								<th><span class="board_write_tit">사이트 리스트</span></th>
								<td class="normal"><table border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td class="normal"><img src="../../../images/admin/alimi/btn_alrimiadd.gif" onclick="popSiteList();" style="cursor:pointer;"/></td>
										<td class="normal" id="siteListCnt"><%=sdGsnCnt+"개의 사이트가 저장 되었습니다."%></td>
									</tr>
								</table></td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
<%-- 				<iframe name="frmReceiver" src="ifrm_receiver_list.jsp?ts_seq=<%=ts_seq%>" frameborder="0" scrolling="no" width="820" height="270"></iframe>
 --%>				</td>
			</tr>
			<!-- 게시판 끝 -->
			<tr>
				<td style="height:40px;" align="center"><img src="../../../images/admin/alimi/btn_save2.gif" onclick="save();" style="cursor:pointer;"/><img src="../../../images/admin/alimi/btn_cancel.gif" onclick="goList();" style="cursor:pointer;"/></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
<%
		}
	} 
%>
</form>
</body>
</html>