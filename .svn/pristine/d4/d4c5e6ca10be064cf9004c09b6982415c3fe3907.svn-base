<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ include file="../../inc/sessioncheck.jsp" %>

<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="../../../css/base.css" type="text/css">

<script language="JavaScript" type="text/JavaScript">
<!--




function right_move() {
	lfrm = sc_sitemng;
	rfrm = tg_sitemng;
	if( !lfrm.fSend.gsn.value ) {
		alert('대상을 선택하여야 합니다.');
	} else if( rfrm.fSend.ts_type.value > 0 ) {

		fMove.sg_seq.value = lfrm.fSend.sg_seq.value;
		fMove.ts_type.value = rfrm.fSend.ts_type.value;

		var gsns = '';
		for(var i = 0; i < lfrm.fSend.gsn.length; i++){
			if(lfrm.fSend.gsn.options[i].selected){
				if(gsns == ""){
					gsns = lfrm.fSend.gsn.options[i].value;
				}else{
					gsns += "," + lfrm.fSend.gsn.options[i].value;
				}
			}
		}
		fMove.gsns.value = gsns;

		fMove.mode.value = 'right';
		fMove.target = 'processFrm';
		fMove.action = 'tier_prc.jsp';
		fMove.submit();
	
	} else {
		alert("우측 매체순위를 선택하여 주십시요.");
	}
}

function left_move() {
	lfrm = sc_sitemng;
	rfrm = tg_sitemng;
	if( !rfrm.fSend.gsn.value ) {
		alert('대상을 선택하여야 합니다.');
	} else {

		fMove.sg_seq.value = lfrm.fSend.sg_seq.value;
		fMove.ts_type.value = rfrm.fSend.ts_type.value;
		
		var gsns = '';
		for(var i = 0; i < rfrm.fSend.gsn.length; i++){
			if(rfrm.fSend.gsn.options[i].selected){
				if(gsns == ""){
					gsns = rfrm.fSend.gsn.options[i].value;
				}else{
					gsns += "," + rfrm.fSend.gsn.options[i].value;
				}
			}
		}
		fMove.gsns.value = gsns;

		fMove.mode.value = 'left';
		fMove.target = 'processFrm';
		fMove.action = 'tier_prc.jsp';
		fMove.submit();
	}
}


function updown_move(mode) {
	lfrm = sc_sitemng;
	rfrm = tg_sitemng;
	if( !rfrm.fSend.gsn.value ) {
		alert('대상을 선택하여야 합니다.');
	} else {

		var gsn1 = '';
		var gsn2 = '';
		for(var i = 0; i < rfrm.fSend.gsn.length; i++){
			if(rfrm.fSend.gsn.options[i].selected){
				gsn1 = rfrm.fSend.gsn.options[i].value;

				if(mode == 'up'){
					if(i > 0){
						gsn2 = rfrm.fSend.gsn.options[i-1].value;			
					}
				}else if(mode == 'down'){
					if(i < (rfrm.fSend.gsn.length-1)){
						gsn2 = rfrm.fSend.gsn.options[i+1].value;			
					}
				}

				break;
			}
		}
		fMove.gsn1.value = gsn1;
		fMove.gsn2.value = gsn2;
		fMove.mode.value = mode;
		fMove.target = 'processFrm';
		fMove.action = 'tier_prc.jsp';
		fMove.submit();
		
	}
}

function search_SourceSite()
{
	source_form.sg_seq.value = frames.sc_sitemng.fSend.sg_seq.value;
	source_form.submit();	
}


function search_TargetSite()
{
	target_form.ts_type.value = frames.tg_sitemng.fSend.ts_type.value;
	target_form.submit();
}
	
//-->
</script>
<body style="margin-left: 15px">

<form name="fMove" method="post">
<input type="hidden" name="mode">
<input type="hidden" name="ts_type">
<input type="hidden" name="sg_seq">
<input type="hidden" name="gsns">
<input type="hidden" name="gsn1">
<input type="hidden" name="gsn2">
</form> 
<iframe id="processFrm" name ="processFrm" width="0" height="0" style="display: none;" ></iframe>


<table style="width:100%;height:100%;" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="body_bg_top" valign="top">
			<table width="820" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="tit_bg" style="height:67px;padding-top:20px;">
					<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td><img src="../../../images/admin/site/tit_icon.gif" /><img src="../../../images/admin/site/tit_00000001.gif"/></td>
							<td align="right">
							<table border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td class="navi_home">HOME</td>
									<td class="navi_arrow">관리자</td>
									<td class="navi_arrow2">매체관리</td>
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
      <table width="770" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="right" style="padding-left:10px"><table width="750" border="0" cellspacing="0" cellpadding="0">
            
            <tr>
              <td><img src="../../../images/admin/site/brank.gif" width="1" height="5"></td>
            </tr>
          </table>
            <table width="750" border="0" cellspacing="0" cellpadding="0">
              <tr>
			    <td align="left">&nbsp;</td>
                <td width="335" align="left" valign="top">
				<table width="335"  border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td height="25" class="textBbig">
                    	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
                    		<tr>
                    			<td align="left"><img src="../../../images/admin/site/admin_ico01.gif" width="12" height="10" hspace="2"><strong>전체 사이트 리스트</strong></td>
                    			<td align="right"><img src="../../../images/admin/site/excel_save.gif" width="94" height="24" hspace="2" onclick="siteAllSave();" style="cursor:hand; display: none;" ></td>
                    		</tr>
                    	</table>
                    </td>
                  </tr>
                
                  
                  
				  <form name="source_form" action="ifram_source.jsp" method="post" target="sc_sitemng">
				  <input name="sg_seq" type="hidden">
                  <tr>
                    <td height="30"><input  name="sch_key" type="text" size="27" style="border:1 solid; border-color:#CCCCCC" OnKeyDown="Javascript:if (event.keyCode == 13) { search_SourceSite();}">
                    <img src="../../../images/admin/site/search_btn.gif" width="41" height="19" align="absmiddle" onclick="search_SourceSite();" style="cursor:hand;"></td>
                  </tr>
				  </form>
				  
                  <tr>
                    <td><iframe name="sc_sitemng" src="ifram_source.jsp" width="335" height="320" scrolling="auto" frameborder="0" style="border:1px solid; border-color:#CCCCCC"></iframe></td>
                  </tr>
                </table></td>
                <td width="30" align="center" valign="middle"><img src="../../../images/admin/site/btn_left.gif" width="18" height="18" onclick="left_move();" style="cursor:hand;"><br>
                <img src="../../../images/admin/site/btn_right.gif" width="18" height="18" vspace="6" onclick="right_move();" style="cursor:hand;"></td>
                <td width="335" align="left" valign="top">
				<table width="335"  border="0" cellspacing="0" cellpadding="0">
				
                  <tr>
                    <td height="25" align="left" class="textBbig" >
                    	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
                    		<tr>
                    			<td align="left"><img src="../../../images/admin/site/admin_ico01.gif" width="12" height="10" hspace="2"><strong>타겟 사이트 리스트</strong></td>
                    			<td align="right"><img src="../../../images/admin/site/excel_save.gif" width="94" height="24" hspace="2" onclick="siteSave();" style="cursor:pointer; display: none;"></td>
                    		</tr>
                    	</table>
                    </td>
                  </tr>
<!--                  <tr>-->
<!--                    <td height="30">-->
<!--                    <form name="target_form" action="ifram_target.jsp" method="get" target="tg_sitemng">-->
<!--                    <input name="ts_type" type="hidden">-->
<!--						<table width="100%"  border="0" cellspacing="0" cellpadding="0">-->
<!--						<tr>-->
<!--						<td><input name="sch_key" type="text" size="27" style="border:1 solid; border-color:#CCCCCC" OnKeyDown="Javascript:if (event.keyCode == 13) { search_TargetSite();}"></td>-->
<!--	                    <td width="64" align="left"><img src="../../../images/admin/site/search_btn.gif" width="41" height="19" align="absmiddle" onclick="search_TargetSite();" style="cursor:hand;"></td>-->
<!--						<td width="99" align="right"><img src="../../../images/admin/site/btn_groupadd.gif" width="94" height="24" onclick="add_sg();" style="cursor:hand; display: none;"></td>-->
<!--						</tr>-->
<!--						</table>-->
<!--					</form>-->
<!--					</td>-->
<!--                  </tr>-->
<!--                  -->
                  <form name="target_form" action="ifram_target.jsp" method="get" target="tg_sitemng">
				  <input name="ts_type" type="hidden">
                  <tr>
                    <td height="30"><input name="sch_key" type="text" size="27" style="border:1 solid; border-color:#CCCCCC" OnKeyDown="Javascript:if (event.keyCode == 13) { search_TargetSite();}">
                    <img src="../../../images/admin/site/search_btn.gif"  onclick="search_TargetSite();"  align="absmiddle" style="cursor:hand;"></td>
                  </tr>
				  </form>
                  
                  
                  <tr>
                    <td><iframe name="tg_sitemng" src="ifram_target.jsp" width="335" height="320" scrolling="auto" frameborder="0" style="border:1px solid; border-color:#CCCCCC"></iframe></td>
                  </tr>
				
                </table></td>
                <td align="left">&nbsp;</td>
              </tr>
            </table>
            <table width="750" height="30" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td align="left" style="padding-left:30px;">* Ctrl, Shift + 마우스 클릭으로 복수 선택이 가능합니다 .&#13;&#13;</td>
                <td align="right" style="padding-right:23px;">
                	<img src="../../../images/admin/site/up_btn.gif" onclick="updown_move('up');">
                	<img src="../../../images/admin/site/down_btn.gif" onclick="updown_move('down');">
				</td>
              </tr>
            </table></td>
        </tr>
      </table>
		</td>
	</tr>
</table>
<iframe name="frm_site_get" id="frm_site_get" src="" width="0" height="0" frameborder="0"></iframe>
</body>
</html>
