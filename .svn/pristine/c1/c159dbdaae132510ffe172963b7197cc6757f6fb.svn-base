<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@include file="../../inc/sessioncheck.jsp" %>
<%@ page import="risk.admin.classification.classificationMgr,
                 risk.util.DateUtil,
                 risk.util.ParseRequest" 
%>
<%
/* 
    DateUtil        du = new DateUtil();
    ParseRequest    pr = new ParseRequest(request);
    
    classificationMgr cm = new classificationMgr();
	
	int itype = pr.getInt("itype",0);
	int icode = pr.getInt("icode",0);

	
	String sCurrentDate = du.getCurrentDate("yyyyMMdd");
	System.out.println("sCurrentDate : " + sCurrentDate );
	
	String sDateFrom = pr.getString("sDateFrom",sCurrentDate );
	String sDateTo   = pr.getString("sDateFrom",sCurrentDate );
	
	//kg.setSelected(xp,yp,zp);
	
	//kg.setBaseTarget("parent.keyword_right");
	//kg.setBaseURL("admin_keyword_right.jsp?mod=");
	
	cm.setBaseTarget("parent.frm_detail");
	cm.setBaseURL("frm_classification_test_detail.jsp?mod=");
	cm.setSelected( itype, icode );
	
	String cmHtml   = cm.GetHtml( itype,icode );
	//String kgScript = kg.GetScript();
	//String kgStyle  = kg.GetStyle();
	
	System.out.println("itype = "+itype);
	System.out.println("icode = "+icode);
 */	
 
 DateUtil        du = new DateUtil();
 ParseRequest    pr = new ParseRequest(request);
 
 classificationMgr cm = new classificationMgr();
	
	int itype = pr.getInt("itype",0);
	int icode = pr.getInt("icode",0);

	cm.setBaseTarget("parent.frm_detail");
	cm.setBaseURL("frm_classification_test_detail.jsp?mod=");
 
%>
<html>
<head>
<title>TREE</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" href="../../tree/css/tree.css" type="text/css"/>
<script src="<%=SS_URL%>js/jquery.js"  type="text/javascript"></script>
<script src="<%=SS_URL%>js/ajax.js"  type="text/javascript"></script>	

<style>
<!--
body {
	scrollbar-face-color: #FFFFFF; 
	scrollbar-shadow-color:#B3B3B3; 
	scrollbar-highlight-color:#B3B3B3; 
	scrollbar-3dlight-color: #FFFFFF; 
	scrollbar-darkshadow-color: #EEEEEE; 
	scrollbar-track-color: #F6F6F6; 
	scrollbar-arrow-color: #8B9EA6;
}
.kgmenu { font-size:12px; height:18px; color:#333333; padding:3 3 1 3; cursor:hand; border:1px solid #FFFFFF; }
.kgmenu_cnt { FONT-SIZE: 10px; COLOR: navy; FONT-FAMILY: "tahoma"; padding:0 0 0 3 }
.kgmenu_selected { font-size:12px; height:18px; color:#333333; padding:3 3 1 3; border:1px solid #999999; background-color:#F3F3F3; cursor:hand;}
.kgmenu_over { font-size:12px; height:18px; color:#333333; padding:3 3 1 3; cursor:hand; border:1px solid #999999; }
.kgmenu_img { cursor:hand; }
-->
</style>

<script language="javascript" type="text/javascript">
$(document).ready(initLoadTree);

function initLoadTree(){			
	ajax.post('../../tree/tree.jsp','treeForm','tree');		
}

function clickItem(ic_seq, itemId, imgId, type, code, ptype, pcode, childcount, level)
{
	
	$('#level').val(level);
	$('#type').val(type);
	$('#code').val(code);
	$('#ptype').val(ptype);
	$('#pcode').val(pcode);			
	$('#tmpItemId').val(itemId);								

	
	if(level=='2'){

		//선택 라인을 초기화 하고 그려준다.
		$('#tree').find('p').attr('style','background-color:#ffffff; border: dashed 1px #ffffff;');
		$('#'+imgId).attr('style','background-color:#f0f0f0; border: dashed 1px #ccc;');
		
		if(childcount>0){				
			//키워드 그룹  트리 생성 및 삭제 
			if($('#'+imgId).attr('class')=='closedFolder')
			{						
				$('#'+imgId).attr('class','openFolder');
			
				addItem(itemId);
				
			}else{
				$('#'+imgId).attr('class','closedFolder');
				removeItem(itemId);
			}		
		}	
			
	}else if(level=='3'){

		//선택 라인을 초기화 하고 그려준다.
		$('#tree').find('p').attr('style','background-color:#ffffff; border: dashed 1px #ffffff;');
		$('#'+imgId).attr('style','background-color:#f0f0f0; border: dashed 1px #ccc;');

		if(childcount>0){
			//키워드 트리 생성 및 삭제
			if($('#'+imgId).attr('class')=='closedFolder')
			{						
				$('#'+imgId).attr('class','openFolder');
				
				addItem(itemId);
			}else{
				$('#'+imgId).attr('class','closedFolder');
				removeItem(itemId);
			}		
		}
		
	}else if(level=='4'){

		//선택 라인을 초기화 하고 그려준다.
		$('#tree').find('p').attr('style','background-color:#ffffff; border: dashed 1px #ffffff;');
		$('#'+imgId).attr('style','background-color:#f0f0f0; border: dashed 1px #ccc;');

		if(childcount>0){
			//키워드 트리 생성 및 삭제
			if($('#'+imgId).attr('class')=='closedFolder')
			{						
				$('#'+imgId).attr('class','openFolder');
				
				addItem(itemId);
			}else{
				$('#'+imgId).attr('class','closedFolder');
				removeItem(itemId);
			}		
		}
	
	}else if(level=='5'){

		//선택 라인을 초기화 하고 그려준다.
		$('#tree').find('p').attr('style','background-color:#ffffff; border: dashed 1px #ffffff;');
		$('#'+imgId).attr('style','background-color:#f0f0f0; border: dashed 1px #ccc;');
		
		if(childcount>0){
			//키워드 트리 생성 및 삭제
			if($('#'+imgId).attr('class')=='closedFolder')
			{						
				$('#'+imgId).attr('class','openFolder');
				
				addItem(itemId);
			}else{
				$('#'+imgId).attr('class','closedFolder');
				removeItem(itemId);
			}		
		}

	}else if(level=='6'){

		//선택 라인을 초기화 하고 그려준다.
		$('#tree').find('p').attr('style','background-color:#ffffff; border: dashed 1px #ffffff;');
		$('#'+imgId).attr('style','background-color:#f0f0f0; border: dashed 1px #ccc;');

	}			


	//우측 프레임 변경			
	$(parent.frm_detail).attr('location','frm_classification_test_detail.jsp?ic_seq='+ic_seq+'&type='+type+'&code='+code+'&ptype='+ptype+'&pcode='+pcode+'&level='+level);	

}

function addItem(itemId)
{
	
	ajax.postAppend('../../tree/tree.jsp','treeForm',itemId);
}

function removeItem(itemId)
{
	$('#'+itemId).find('ul').remove();
	//$('#treeTest').val($('#'+itemId).html());
}

function clickKeyword(itemId, imgId, arg1, arg2, arg3)
{				
	$('#xp').val(arg1);			
	$('#yp').val(arg2);			
	$('#zp').val(arg3);
	
	//선택 라인을 초기화 하고 그려준다.					
	$('#tree').find('p').attr('style','background-color:#ffffff; border: dashed 1px #ffffff;');
	$('#'+imgId).attr('style','background-color:#f0f0f0; border: dashed 1px #ccc;');

	//우측 프레임 변경			
	$(parent.keyword_right).attr('location','admin_keyword_right.jsp?xp='+arg1+'&yp='+arg2+'&zp='+arg3);						
}

function proccessAfterReload()
{			
	removeItem($('#tmpItemId').val());
	addItem($('#tmpItemId').val());
}


/* 	function toggleme(object, subo) {

var obj = document.getElementById(object);						
var subObj = eval("document.all."+subo);		
var i = 0;										
var subcnt = subObj.length;						
if (obj.src.indexOf('ico03')>0) {				
	if (subObj)	{								
		obj.src = '../../../images/search/left_ico04.gif';		
		subObj.style.display='';				
	}											
} else {											
	if (subObj)	{								
		obj.src = '../../../images/search/left_ico03.gif';		
		subObj.style.display='none';			
	}											
}												
}													
function kg_over(obj) {							
tmpspan.className=obj.className;				
obj.className='kgmenu_over';					
}													
function kg_out(obj){								
obj.className=tmpspan.className;				
}													
function kg_click(obj){							
var prvObj = eval('document.all.'+document.all.kgmenu_id.value);	
if (prvObj)										
{												
	prvObj.className = 'kgmenu';				
}												
document.all.kgmenu_id.value = obj.id;			
obj.className='kgmenu_selected';				
tmpspan.className=obj.className;				
}
function clk_total()
{
parent.keyword_right.location.href = 'admin_keyword_right.jsp';
}
*/	
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" leftmargin="8" topmargin="7">
<form  name="treeForm" id="treeForm">
	<input type="hidden"  name="queryId" value="CLASSIFICATION">
	<input type="hidden" id="level" name="level" value="1">
	<input type="hidden" id="xp" name="xp" value="">
	<input type="hidden" id="yp" name="yp" value="">
	<input type="hidden" id="zp" name="zp" value="">
	
	<input type="hidden" id="type" name="type" value="">
	<input type="hidden" id="code" name="code" value="">
	<input type="hidden" id="ptype" name="ptype" value="">
	<input type="hidden" id="pcode" name="pcode" value="">
	
	<input type="hidden" id="tmpItemId" name="tmpItemId" value="">
</form>
	<div id="tree">
	</div>
</body>
</html>
