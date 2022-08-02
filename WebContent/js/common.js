//********************************
//  common.js
//  용도   : 공용 javascript library
//  작성자 : 임승철
//  작성일 : 2010.09.28
//********************************


function common(){};


common.version = "version 1.0";

var selectedIndex = 0;

var itemLength = 0;

var formIdName = '';

var viewIdName ='';

var textIdName ='';

var queryIdName = '';

//*********************************************** AutoText :자동 완성 네임 반환 ***********************************************

var autoText = common.autoText;

autoText = {
		
	findName : function(formId,viewId,textId,QeuryId,keyCode)
	{
		formIdName = formId;
		viewIdName = viewId;
		textIdName = textId;
		queryIdName = QeuryId;
		
		$('#queryId').val(queryIdName);	
		if(keyCode>47 || keyCode==8){ //특수키를 제외한 키와 백스페이시 조회
			$('#'+viewId).show();	
			ajax.post('/riskv3/autotaglib/auto_text_view.jsp',formIdName,viewIdName); //자동완성 페이지 경로
		}
	},
	
	keyEvent :  function(formId,viewId,textId,keyCode)
	{
		formIdName = formId;
		viewIdName = viewId;
		textIdName = textId;
				
		itemLength = $('#'+viewIdName).find('li').length;
		
		if(keyCode==13){  //엔터시
			$('#'+textIdName).val($.trim($('#'+viewIdName).find('li').eq(selectedIndex).text()));
			$('#'+viewIdName).hide();
			 selectedIndex = -1;
		}else if(keyCode==38){//방향 위		
			
			if(itemLength > 0 && selectedIndex>0){			
				selectedIndex--;
				
				$('#'+viewIdName).find('li').removeClass('selected').eq(selectedIndex).addClass('selected');			
			}
			
		}else if(keyCode==40){ //방향 아래
			
			if(itemLength > 0 && selectedIndex < itemLength-1){		
				selectedIndex++;
							
				$('#'+viewIdName).find('li').removeClass('selected').eq(selectedIndex).addClass('selected');			
			}
			
		}else{ //기타
			selectedIndex = 0;
			
			if($('#'+textIdName).val()=='' || itemLength == 0){
				$('#'+viewIdName).hide();
			}
		}
	},

	mouseOver :  function(index)
	{
		selectedIndex = index;
		$('#'+viewIdName).find('li').removeClass('selected').eq(index).addClass('selected');	
	},
	
	mouseDown :  function(index)
	{		
		$('#'+textIdName).val($.trim($('#'+viewIdName).find('li').eq(index).text()));
		$('#'+viewIdName).hide();
	},
	
	blur :  function()
	{	
		if(viewIdName!=''){
			$('#'+viewIdName).hide();
		}
	}
}
//*****************************************************************************************************************


//*********************************************** AutoText :자동 완성  네임 및 코드 반환***********************************************

var autoText2 = common.autoText2;

autoText2 = {
		
	findName : function(formId,viewId,textId,QeuryId,keyCode)
	{
		formIdName = formId;
		viewIdName = viewId;
		textIdName = textId;
		queryIdName = QeuryId;
		
		$('#queryId').val(queryIdName);	
		if(keyCode>47 || keyCode==8){ //특수키를 제외한 키와 백스페이시 조회			
			$('#'+viewId).show();	
			ajax.post('/riskv3/autotaglib/auto_text_view2.jsp',formIdName,viewIdName); //자동완성 페이지 경로
		}
	},
	
	keyEvent :  function(formId,viewId,textId,keyCode)
	{
		formIdName = formId;
		viewIdName = viewId;
		textIdName = textId;
				
		itemLength = $('#'+viewIdName).find('li').length;
		
		if(keyCode==13){  //엔터시
			$('#'+textIdName).val($.trim($('#'+viewIdName).find('li').eq(selectedIndex).text()));
			if(itemLength > 0){
				$('#typeCode7').val($('input[name=cd]:nth('+selectedIndex+')').val());
			}			
			
			$('#'+viewIdName).hide();
			 selectedIndex = -1;
		}else if(keyCode==38){//방향 위		
			
			if(itemLength > 0 && selectedIndex>0){			
				selectedIndex--;
				
				$('#'+viewIdName).find('li').removeClass('selected').eq(selectedIndex).addClass('selected');			
			}
			
		}else if(keyCode==40){ //방향 아래
			
			if(itemLength > 0 && selectedIndex < itemLength-1){		
				selectedIndex++;
							
				$('#'+viewIdName).find('li').removeClass('selected').eq(selectedIndex).addClass('selected');			
			}
			
		}else{ //기타
			selectedIndex = 0;
			$('#typeCode7').val('');
			if($('#'+textIdName).val()=='' || itemLength == 0){
				$('#'+viewIdName).hide();
			}
		}
	},

	mouseOver :  function(index)
	{
		selectedIndex = index;
		$('#'+viewIdName).find('li').removeClass('selected').eq(index).addClass('selected');	
	},
	
	mouseDown :  function(index)
	{		
		$('#'+textIdName).val($.trim($('#'+viewIdName).find('li').eq(index).text()));
		if(itemLength > 0){
			$('#typeCode7').val($('input[name=cd]:nth('+selectedIndex+')').val());		
		}
		$('#'+viewIdName).hide();
	},
	
	blur :  function()
	{	
		if(viewIdName!=''){
			$('#'+viewIdName).hide();
		}
	}
}
//*****************************************************************************************************************
