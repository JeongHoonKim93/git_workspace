<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>

<%@ page import="risk.util.*,
				 java.util.List,
				 risk.search.userEnvMgr,
				 risk.search.keywordInfo,
				 risk.admin.site.SitegroupBean,
				 risk.admin.site.SiteMng,
				 risk.admin.membergroup.membergroupBean,
				 risk.admin.membergroup.membergroupMng,
				 risk.issue.IssueCodeMgr,
				 risk.issue.IssueCodeBean,
				 java.util.ArrayList,
				 risk.admin.usergroup.UserGroupMgr,
				 risk.admin.usergroup.UserGroupSuperBean,
				 risk.admin.usergroup.UserGroupSuperBean.MenuBean
				 "%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
	int i;
	List KGlist = null;
	List sglist = null;
	String mgseq = null;
	String[] menufd = null;
	String[] companyfd = null;
	String[] xpfd = null;
	String[] sgfd = null;
	membergroupBean mginfo = null;

	StringUtil su = new StringUtil();
	userEnvMgr uemng = new userEnvMgr();
	KGlist = uemng.getKeywordGroup();

	SiteMng sitemng = new SiteMng();
	membergroupMng mgmng = membergroupMng.getInstance();

	if( request.getParameter("mgseq") != null ) {
		mgseq = request.getParameter("mgseq");
		mginfo = (membergroupBean)mgmng.getMGBean(mgseq);
		if( mginfo.getMGmenu() != null ) menufd = mginfo.getMGmenu().split(",");
		if( mginfo.getMGcompany() != null ) companyfd = mginfo.getMGcompany().split(",");
		if( mginfo.getMGxp() != null ) xpfd = mginfo.getMGxp().split(",");
		if( mginfo.getMGsite() != null ) sgfd = mginfo.getMGsite().split(",");
	}
	
	//IssueCodeMgr 인스턴스 생성
	IssueCodeMgr icMgr = new IssueCodeMgr();
	IssueCodeBean icBean = null;	
	ArrayList arrIcBean = null;
	arrIcBean = icMgr.GetTypeOrderName(7); 
	
	//메뉴 가져오기~~
	UserGroupMgr uMgr = new UserGroupMgr();
	ArrayList menuDataAll = uMgr.getMenu();
	ArrayList menuDataXp = uMgr.getDetalMenu(menuDataAll, "xp");
	//ArrayList menuDataYp = uMgr.getDetalMenu(menuDataAll, "yp");
%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="../../../css/base.css" type="text/css">
<script language="JavaScript" type="text/JavaScript">

	function mnu_click(obj, index){

		var table = document.getElementById('pop_mail_group');

		var t = null;
				
		for(var i = 0; i < table.rows[0].cells.length; i++){

			t = document.getElementById('tabImg'+ (i+1));

			if(i == 0){
				t.innerHTML = '메뉴';
			}else if(i == 1){
				t.innerHTML = '키워드그룹';
			}else if(i == 2){
				t.innerHTML = '사이트그룹';
			}else if(i == 3){
				t.innerHTML = '계열사';
			}
			
			t.className = 'tab_off';
			document.getElementById('tab'+(i+1)).style.display = 'none';
			
		}

		obj.className = 'tab_on';
		if(index == 0){
			obj.innerHTML = '<span class="tab_on_txt">메 뉴</span>';
			document.getElementById('tab'+(index+1)).style.display = '';
		}else if(index == 1){
			obj.innerHTML = '<span class="tab_on_txt">키워드그룹</span>';
			document.getElementById('tab'+(index+1)).style.display = '';
		}else if(index == 2){
			obj.innerHTML = '<span class="tab_on_txt">사이트그룹</span>';
			document.getElementById('tab'+(index+1)).style.display = '';
		}else if(index == 3){
			obj.innerHTML = '<span class="tab_on_txt">계열사</span>';
			document.getElementById('tab'+(index+1)).style.display = '';
		}
	}


	function curr_save() {
		if(confirm("변경 내용을 저장하시면 사용권한이 변경됩니다.\n "))
			if( mgset.mgseq.value ) {

				var obj = document.mgset.mg_menu;
				var strIdx = ""; 
				
				if(obj.length){
					
					
					for(var i = 0; i < obj.length; i++){

						if(obj[i].checked){
							
							if(strIdx == ""){
								strIdx = String(i);
							}else{
								strIdx += "," + String(i); 
							}
						}						
					} 
				}else{
					strIdx = "0";
				}
				 document.mgset.menuIdx.value = strIdx;

				
				mgset.submit();


				

			}
	}

	function allCheckMenu(){
		var obj = document.mgset.mg_menu;

		var checked = true;
		
		if(document.mgset.mg_menu_all.checked){
			checked = true;
		} else {
			checked = false;
		}


		var obj2;
		
		for(i=0; i<obj.length ; i++){
			obj[i].checked = checked;

			obj2 = document.getElementsByName("mg_menu2_" + i);

			 for(var j = 0; j < obj2.length; j++){
				 				 
				 obj2[j].checked = checked;
			 } 			
		}
	}


	function allCheckSubMenu(pObj , idx){

		var obj;
		obj = document.getElementsByName("mg_menu2_" + idx);
		
		if(obj){
			if(obj.length){
				for(var i =0; i < obj.length; i++){
					obj[i].checked = pObj.checked;
				} 
			}else{
				obj.checked = pObj.checked;
			}
		}

	
	}
	

	function checkMenu() {
		var obj = document.mgset.mg_menu;
		var count = 0;
		
		for(i=0; i<obj.length ; i++){
			if(obj[i].checked) count++;
		}

		if(obj.length == count){
			document.mgset.mg_menu_all.checked = true;
		} else {
			document.mgset.mg_menu_all.checked = false;
		}
	}

	function allCheckCompany() {
		var obj = document.mgset.mg_company;

		var checked = true;
		
		if(document.mgset.mg_company_all.checked){
			checked = true;
		} else {
			checked = false;
		}

		for(i=0; i<obj.length ; i++){
			obj[i].checked = checked;
		}
	}

	function checkCompany() {
		var obj = document.mgset.mg_company;
		var count = 0;
		
		for(i=0; i<obj.length ; i++){
			if(obj[i].checked) count++;
		}

		if(obj.length == count){
			document.mgset.mg_company_all.checked = true;
		} else {
			document.mgset.mg_company_all.checked = false;
		}
	}

	function allCheckKeyGroup() {
		var obj = document.mgset.mg_kg;

		var checked = true;
		
		if(document.mgset.mg_kg_all.checked){
			checked = true;
		} else {
			checked = false;
		}

		for(i=0; i<obj.length ; i++){
			obj[i].checked = checked;
		}
	}

	function checkKeyGroup() {
		var obj = document.mgset.mg_kg;
		var count = 0;
		
		for(i=0; i<obj.length ; i++){
			if(obj[i].checked) count++;
		}

		if(obj.length == count){
			document.mgset.mg_kg_all.checked = true;
		} else {
			document.mgset.mg_kg_all.checked = false;
		}
	}

	function allCheckSite() {
		var obj = document.mgset.mg_site;

		var checked = true;
		
		if(document.mgset.mg_site_all.checked){
			checked = true;
		} else {
			checked = false;
		}
		
		for(i=0; i<obj.length ; i++){
			obj[i].checked = checked;
		}
	}

	function checkSite() {
		var obj = document.mgset.mg_site;
		var count = 0;
		
		for(i=0; i<obj.length ; i++){
			if(obj[i].checked) count++;
		}

		if(obj.length == count){
			document.mgset.mg_site_all.checked = true;
		} else {
			document.mgset.mg_site_all.checked = false;
		}
	}

	function visibleMenu(xp) {

		var trObj = document.getElementById("tr_menu_" + xp);
		
		if(trObj.style.display == 'none'){
			trObj.style.display = '';
		}else{
			trObj.style.display = 'none';
		}
	}
	

</script>
</head>
<body>
<form name="mgset" action="ifram_usergroup_right_prc.jsp" method="post">
<input type="hidden" name="mgseq" value="<%=mgseq%>">
<input type="hidden" name="menuIdx" value= "">
<table id="pop_mail_group" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<th style="width:25%; cursor: pointer;" id="tabImg1" class="tab_on" onclick="mnu_click(this, 0)"><span class="tab_on_txt">메 뉴</span></th>
		<th style="width:25%; cursor: pointer;" class="tab_off" id="tabImg2"  onclick="mnu_click(this, 1)">키워드그룹</th>
		<th style="width:25%; cursor: pointer;" class="tab_off" id="tabImg3"  onclick="mnu_click(this, 2)">사이트그룹</th>
		<!-- <th style="width:25%; cursor: pointer;" class="tab_off" id="tabImg4"  onclick="mnu_click(this, 3)">계열사</th> -->
	</tr>
	<tr>
		<td id="tab1" colspan="4" style="padding:10px;" valign="top">
		<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
		
			<tr>
				<td class="pop_mail_group_td"><input type="checkbox" name="mg_menu_all" onclick="allCheckMenu(<%%>);" <%if( menufd != null )if(menufd.length == 6)out.println("checked");%>><strong>전체</strong></td>
			</tr>
<!--			<tr>-->
<!--				<td class="pop_mail_group_td"><input type="checkbox" name="mg_menu" value="6" onclick="checkMenu();" <%if( menufd != null ){ if( su.inarray(menufd, "6") ) out.println("checked");}%>>대시보드</td>-->
<!--			</tr>-->
<!--			<tr>-->
<!--				<td class="pop_mail_group_td"><input type="checkbox" name="mg_menu" value="1" onclick="checkMenu();" <%if( menufd != null ){ if( su.inarray(menufd, "1") ) out.println("checked");}%>>정보검색</td>-->
<!--			</tr>-->
<!--			<tr>-->
<!--				<td class="pop_mail_group_td"><input type="checkbox" name="mg_menu" value="2" onclick="checkMenu();" <%if( menufd != null ){ if( su.inarray(menufd, "2") ) out.println("checked");}%>>이슈관리</td>-->
<!--			</tr>-->
<!--			<tr>-->
<!--				<td class="pop_mail_group_td"><input type="checkbox" name="mg_menu" value="3" onclick="checkMenu();" <%if( menufd != null ){ if( su.inarray(menufd, "3") ) out.println("checked");}%>>보고서관리</td>-->
<!--			</tr>-->
<!--			<tr>-->
<!--				<td class="pop_mail_group_td"><input type="checkbox" name="mg_menu" value="4" onclick="checkMenu();" <%if( menufd != null ){ if( su.inarray(menufd, "4") ) out.println("checked");}%>>통계분석</td>-->
<!--			</tr>-->
<!--			<tr>-->
<!--				<td class="pop_mail_group_td"><input type="checkbox" name="mg_menu" value="5" onclick="checkMenu();" <%if( menufd != null ){ if( su.inarray(menufd, "5") ) out.println("checked");}%>>관리자</td>-->
<!--			</tr>-->

<!--			</tr>-->
<%
UserGroupSuperBean.MenuBean mb_bean = null;
UserGroupSuperBean.MenuBean mb_bean2 = null;
ArrayList detalData = null;
String checked = "";
	for( i=0 ; i < menuDataXp.size() ; i++ ) {
		mb_bean = (UserGroupSuperBean.MenuBean)menuDataXp.get(i);
		
		//System.out.println(mginfo.getMGmenu() + " / " + mb_bean.getMe_seq());
		checked = "";
		if( menufd != null ){ 
			if( su.inarray(menufd, mb_bean.getMe_seq())){
				checked = "checked";
			}
		}
		
%>
			<tr>
				<td class="pop_mail_group_td"><input type="checkbox" name="mg_menu"  onclick="allCheckSubMenu(this, '<%=i%>');" value="<%=mb_bean.getMe_seq()%>" <%=checked%>><span id="sp_menu1" style="cursor: pointer;" onclick="visibleMenu('<%=mb_bean.getMe_xp()%>')"><%=mb_bean.getMe_name()%></span></td>
			</tr>
			
			<tr id="tr_menu_<%=mb_bean.getMe_xp()%>" style="display: <%= checked.equals("") ? "none" : ""%>;">
					<td>
						<table  width="100%" border="0" cellpadding="0" cellspacing="0">
			<%
				detalData = uMgr.getMenuYp(menuDataAll, mb_bean.getMe_xp());
				for(int j = 0; j <detalData.size(); j++){
					mb_bean2 = (UserGroupSuperBean.MenuBean)detalData.get(j);
			%>
				
				
							<tr>
								<td class="pop_mail_group_td_on">&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" name="mg_menu2_<%=i%>" id="mg_menu2_<%=i%>" value="<%=mb_bean2.getMe_seq()%>" <%if( menufd != null ){ if( su.inarray(menufd, mb_bean2.getMe_seq()) ) out.println("checked");}%>><%=mb_bean2.getMe_name()%></td>
							</tr> 
					
				<%}%>
						</table>
							</td>
						</tr>
			
			
<%
	}
%>	
		


		</table>
		</td>
		<td id="tab2" style="display:none;" colspan="3" style="padding:10px;" valign="top">
		<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td class="pop_mail_group_td"><input type="checkbox" name="mg_kg_all" onclick="allCheckKeyGroup();" <%if( xpfd != null ){if(xpfd.length == KGlist.size())out.println("checked");}%>><strong>전체</strong></td>
			</tr>		
<%
	for( i=0 ; i < KGlist.size() ; i++ ) {
		keywordInfo KGset = (keywordInfo)KGlist.get(i);
%>
			<tr>
				<td class="pop_mail_group_td"><input type="checkbox" name="mg_kg" onclick="checkKeyGroup();" value="<%=KGset.getK_Xp()%>" <%if( xpfd != null ){ if( su.inarray(xpfd, KGset.getK_Xp()) ) out.println("checked");}%>><%=KGset.getK_Value()%></td>
			</tr>
<%
	}

	sglist = sitemng.getSGList();
%>
		</table>
		</td>
		<td id="tab3" style="display:none;" colspan="3" style="padding:10px;" valign="top">
		<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td class="pop_mail_group_td"><input type="checkbox" name="mg_site_all" onclick="allCheckSite();" <%if( sgfd != null ){if(sgfd.length == sglist.size())out.println("checked");}%>><strong>전체</strong></td>
			</tr>		
<%
	for( i=0 ; i < sglist.size() ; i++ ) {
		SitegroupBean SGinfo = (SitegroupBean)sglist.get(i);
%>
			<tr>
				<td class="pop_mail_group_td"><input type="checkbox" name="mg_site" onclick="checkSite();" value="<%=SGinfo.get_seq()%>" <%if( sgfd != null ){ if( su.inarray(sgfd, String.valueOf(SGinfo.get_seq())) ) out.println("checked");}%>><%= SGinfo.get_name()%></td>
			</tr>
<%
	}
%>
		</table>
		</td>
		<td id="tab4" style="display:none;" colspan="3" style="padding:10px;" valign="top">
		<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td class="pop_mail_group_td"><input type="checkbox" name="mg_company_all" onclick="allCheckCompany();" <%if( companyfd != null ){if(companyfd.length == arrIcBean.size()-1)out.println("checked");}%>><strong>전체</strong></td>
			</tr>
<%
	for( i = 0; i < arrIcBean.size(); i++){
	icBean = (IssueCodeBean) arrIcBean.get(i);
	
	String bgcolor = "";
	if(icBean.getIc_ptype() == 4 && icBean.getIc_pcode() == 3) 
		bgcolor = "bgcolor=\"#eeeeee\"";
	else
		bgcolor = "bgcolor=\"#ffffff\"";
%>
			<tr>
				<td class="pop_mail_group_td" <%=bgcolor %>><input type="checkbox" name="mg_company" onclick="checkCompany();" value="<%=icBean.getIc_code()%>" <%if( companyfd != null ){ if( su.inarray(companyfd, Integer.toString(icBean.getIc_code())) ) out.println("checked");}%>><%= icBean.getIc_name()%></td>
			</tr>
<%
	}
%>
		</table>
		</td>
	</tr>
</table>
</form>
</body>
</html>