<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ page import="risk.util.*,
				 risk.admin.tier.TierSiteMgr,
				 risk.admin.tier.TierSiteBean
				 "
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
    ParseRequest    pr = new ParseRequest(request);
	pr.printParams();
	TierSiteMgr tMgr = new TierSiteMgr();


	String mode	= pr.getString("mode","");
	String sg_seq	= pr.getString("sg_seq","");
	String ts_type	= pr.getString("ts_type","");
	String gsns = pr.getString("gsns","");
	String gsn1 = pr.getString("gsn1","");
	String gsn2 = pr.getString("gsn2","");
	
	String html = "";
	
	String tier_name = pr.getString("tier_name", "");
	
	if( mode.equals("left") ) {
		tMgr.LeftMove(gsns);
		
		html += "parent.source_form.sg_seq.value = " + sg_seq + ";";
		html += "parent.source_form.submit();";
		html += "parent.target_form.ts_type.value = " + ts_type + ";";
		html += "parent.target_form.submit();";
	}else if( mode.equals("right") ) {
		tMgr.RightMove(gsns , ts_type);
		
		html += "parent.source_form.sg_seq.value = " + sg_seq + ";";
		html += "parent.source_form.submit();";
		html += "parent.target_form.ts_type.value = " + ts_type + ";";
		html += "parent.target_form.submit();";
	}else if( mode.equals("up") ||  mode.equals("down")) {
		
		if(!gsn1.equals("") && !gsn2.equals("")){
			tMgr.ChangeOrder(gsn1,gsn2);	
		}
		
		html =  "var obj = parent.tg_sitemng.fSend.gsn;";
		
		if(mode.equals("up")){
			html +=  "doMoveUp(1,obj);";	
		}else if(mode.equals("down")){
			html +=  "doMoveUp(2,obj);";
		}
	} else if(mode.equals("ins")) {
		String result = tMgr.InsertTierGroup(tier_name, SS_M_NO);
		
		if(!"".equals(result)){
			html = "alert('"+result+"');";
		}
		
		html += "parent.target_form.submit();";
	} else if(mode.equals("del")){

		String result = tMgr.deleteTierGroup(ts_type);
		if("".equals(result)) {
			result = "삭제를 실패하였습니다.";
		}
		html = "alert('"+result+"');";
		html += "parent.source_form.submit();";
		html += "parent.target_form.submit();";
	}
	
	System.out.println(html);

%>
<SCRIPT LANGUAGE="JavaScript">
<!--
	//parent.sc_sitemng.location.href = 'ifram_source.jsp';
	//parent.tg_sitemng.location.href = 'ifram_target.jsp';
	

	
	// select된 index 증감
	function getReOrder(moveUp, ListBox){
		var rtnIdx = null;
		 
		if(moveUp == 1) {
			rtnIdx = ListBox.selectedIndex - 1; //up
		} else if(moveUp == 2) {
			rtnIdx = ListBox.selectedIndex + 1; //down
		}
		
		return rtnIdx;
	}

	function doMoveUp(moveUp, list) {
		var selFrm = document.selFrm;
		var ListBox = list;
		var len  = 0;
		var nowIdx = ListBox.selectedIndex;
		 
		// 선택한 Option이 없을 경우
		if(ListBox.selectedIndex == -1) {
			return;
		}
		 
		// 최상위일 경우
		if(moveUp==1 && ListBox.selectedIndex == 0) {
			return;
		}
		 
		// 최하위일 경우
		if(moveUp==2 && ListBox.selectedIndex == ListBox.options.length-1) {
			return;
		}
		 
		var orgList = new Array(ListBox.options.length);
		
		for(len = 0; len < ListBox.options.length; len++) {
			orgList[len] = new Option(
			ListBox.options[len].text,
			ListBox.options[len].value,
			ListBox.options[len].defaultSelected,
			ListBox.options[len].selected);
		}
		 
		var rtnIdx = getReOrder(moveUp, ListBox);
		 
		if(rtnIdx != null) {
			ListBox.options[rtnIdx] = orgList[nowIdx];
			ListBox.options[nowIdx] = orgList[rtnIdx];
		}
	}
		 
		
	<%=html%>


			
//-->
</SCRIPT>