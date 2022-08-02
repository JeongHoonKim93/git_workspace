<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ include file="../../inc/sessioncheck.jsp" %>

<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="../../../css/base.css" type="text/css">

<script language="JavaScript" type="text/JavaScript">
<!--
 function add_sg() {
		frm = tg_sitemng.tg_site;
		var url = "pop_sitegroup.jsp?mode=ins";
		window.open(url, "pop_sitegroup", "width=400,height=156");
}

function right_move() {
	lfrm = sc_sitemng;
	rfrm = tg_sitemng;
	if( !lfrm.sg_mng.gsn.value ) {
		alert('대상을 선택하여야 합니다.');
	} else if( rfrm.tg_site.sg_seq.value > 0 ) {
		lfrm.sg_mng.sgseq.value = rfrm.tg_site.sg_seq.value;
		
		if( frames.sc_sitemng.sg_change.code1.value ) {
			source_form.code1.value = frames.sc_sitemng.sg_change.code1.value;
		}
		if( frames.sc_sitemng.sg_change.language.value ) {
			source_form.language.value = frames.sc_sitemng.sg_change.language.value;
		}

		if( frames.tg_sitemng.tg_site.sg_seq.value ) {
			target_form.sg_seq.value = frames.tg_sitemng.tg_site.sg_seq.value;
		}
		if( frames.tg_sitemng.tg_site.language.value ) {
			target_form.language.value = frames.tg_sitemng.tg_site.language.value;
		}
		
		lfrm.sg_mng.submit();
	} else {
		alert("우측 사이트 그룹을 선택하여 주십시요.");
	}
}

function left_move() {
	lfrm = sc_sitemng;
	rfrm = tg_sitemng;
	if( !rfrm.sg_mng.gsn.value ) {
		alert('대상을 선택하여야 합니다.');
	} else {
		rfrm.sg_mng.sgseq.value = rfrm.tg_site.sg_seq.value;
		if( frames.sc_sitemng.sg_change.code1.value ) {
			source_form.code1.value = frames.sc_sitemng.sg_change.code1.value;
		}
		if( frames.sc_sitemng.sg_change.language.value ) {
			source_form.language.value = frames.sc_sitemng.sg_change.language.value;
		}
		


		
		if( frames.tg_sitemng.tg_site.sg_seq.value ) {
			target_form.sg_seq.value = frames.tg_sitemng.tg_site.sg_seq.value;
		}
		if( frames.tg_sitemng.tg_site.language.value ) {
			target_form.language.value = frames.tg_sitemng.tg_site.language.value;
		}

		
		
		rfrm.sg_mng.submit();
	}
}

function search_SourceSite()
{
	if( frames.sc_sitemng.sg_change.code1.value ) {
		source_form.code1.value = frames.sc_sitemng.sg_change.code1.value;
	}
	if( frames.sc_sitemng.sg_change.language.value ) {
		source_form.language.value = frames.sc_sitemng.sg_change.language.value;
	}
	source_form.submit();
}

	function search_TargetSite()
	{
		if( frames.tg_sitemng.tg_site.sg_seq.value ) {
			target_form.sg_seq.value = frames.tg_sitemng.tg_site.sg_seq.value;
		}
		if( frames.tg_sitemng.tg_site.language.value ) {
			target_form.language.value = frames.tg_sitemng.tg_site.language.value;
		}
		target_form.submit();
	}
	
	function siteSave()
	{
		document.frm_site_get.location = 'site_download_excel.jsp';
	}
	
	function siteAllSave()
	{
		document.frm_site_get.location = 'site_download_excel.jsp?mode=all';
	}
//-->
</script>
<body style="margin-left: 15px">
<table style="width:100%;height:100%;" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="body_bg_top" valign="top">
			<table width="820" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="tit_bg" style="height:67px;padding-top:20px;">
					<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td><img src="../../../images/admin/site_type/tit_icon.gif" /><img src="../../../images/admin/site_type/tit_666.gif" /></td>
							<td align="right">
							<table border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td class="navi_home">HOME</td>
									<td class="navi_arrow">관리자</td>
									<td class="navi_arrow2">분류사이트관리</td>
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
              <td height="50" align="left"> 분류된 사이트를 관리합니다 .&#13;<br>
              좌측의 사이트 리스트에서 수집을 원하는 사이트를 우측리스트로 이동하시면 ,&#13;대상 사이트에서 분류됩니다.&#13;&#13;</td>
            </tr>
            <tr>
              <td><img src="../../../images/admin/site_type/brank.gif" width="1" height="5"></td>
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
                    			<td align="left"><img src="../../../images/admin/site_type/admin_ico01.gif" width="12" height="10" hspace="2"><strong>수집대상 사이트 리스트</strong></td>
                    			<td align="right"><img src="../../../images/admin/site_type/excel_save.gif" width="94" height="24" hspace="2" onclick="siteAllSave();" style="cursor:hand;"></td>
                    		</tr>
                    	</table>
                    </td>
                  </tr>
				  <form name="source_form" action="ifram_source.jsp" method="post" target="sc_sitemng">
				  <input name="code1" type="hidden">
				  <input name="language" type="hidden">
                  <tr>
                    <td height="30"><input  name="sch_key" type="text" size="27" style="border:1 solid; border-color:#CCCCCC" OnKeyDown="Javascript:if (event.keyCode == 13) { search_SourceSite();}">
                    <img src="../../../images/admin/site_type/search_btn.gif" width="41" height="19" align="absmiddle" onclick="search_SourceSite();" style="cursor:hand;"></td>
                  </tr>
				  </form>
                  <tr>
                    <td><iframe name="sc_sitemng" src="ifram_source.jsp" width="335" height="310" scrolling="auto" frameborder="0" style="border:1px solid; border-color:#CCCCCC"></iframe></td>
                  </tr>
                </table></td>
                <td width="30" align="center" valign="middle"><img src="../../../images/admin/site_type/btn_left.gif" width="18" height="18" onclick="left_move();" style="cursor:hand;"><br>
                <img src="../../../images/admin/site_type/btn_right.gif" width="18" height="18" vspace="6" onclick="right_move();" style="cursor:hand;"></td>
                <td width="335" align="left" valign="top">
				<table width="335"  border="0" cellspacing="0" cellpadding="0">
				<form name="target_form" action="ifram_target.jsp" method="get" target="tg_sitemng">
				<input type="hidden" name="sg_seq" value="0">
				<input type="hidden" name="language" value="0">
                  <tr>
                    <td height="25" align="left" class="textBbig" >
                    	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
                    		<tr>
                    			<td align="left"><img src="../../../images/admin/site_type/admin_ico01.gif" width="12" height="10" hspace="2"><strong>분류 사이트 리스트</strong></td>
                    			<td align="right"><img src="../../../images/admin/site_type/excel_save.gif" width="94" height="24" hspace="2" onclick="siteSave();" style="cursor:pointer;"></td>
                    		</tr>
                    	</table>
                    </td>
                  </tr>
                  <tr>
                    <td height="30">
					<table width="100%"  border="0" cellspacing="0" cellpadding="0">
					<tr>
					<td><input name="tg_sch" type="text" size="27" style="border:1 solid; border-color:#CCCCCC" OnKeyDown="Javascript:if (event.keyCode == 13) { search_TargetSite();}"></td>
                    <td width="64" align="left"><img src="../../../images/admin/site_type/search_btn.gif" width="41" height="19" align="absmiddle" onclick="search_TargetSite();" style="cursor:hand;"></td>
					<td width="99" align="right"><img src="../../../images/admin/site_type/btn_groupadd.gif" width="94" height="24" onclick="add_sg();" style="cursor:hand;"></td>
					</td>
					</tr>
					</table>
					</td>
                  </tr>
                  <tr>
                    <td><iframe name="tg_sitemng" src="ifram_target.jsp" width="335" height="310" scrolling="auto" frameborder="0" style="border:1px solid; border-color:#CCCCCC"></iframe></td>
                  </tr>
				</form>
                </table></td>
                <td align="left">&nbsp;</td>
              </tr>
            </table>
            <table width="750" height="30" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td align="left" style="padding-left:30px;">* Ctrl, Shift + 마우스 클릭으로 복수 선택이 가능합니다 .&#13;&#13;</td>
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
