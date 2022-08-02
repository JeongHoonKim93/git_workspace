<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>모니터링</title>
<style type="text/css">
.wrap {width:1000px;margin:0 auto}
</style>
<script src="./js/jquery.js" type="text/javascript"></script>
<script src="./js/ajax.js" type="text/javascript"></script>
<script type="text/javascript">
function getReply(){
	
	$.ajax({
		type : "POST"
		,async : false
		,url: "getLastNumber.jsp"
		//,timeout: 30000
		,data : {msg:"F"}
		,dataType : 'text'
		,success : function(data){
				   	//$(".msg").text(data);
				   }
		,beforeSend:function(){    // ajax 통신을 시작하기 전에 콜백
			$(".msg").text("수집 중...");
		   }
		,complete:function(){       //완료 후 실행할 콜백 함수 정의
			$(".msg").text("수집 완료!");
		   }
		});
}


function excel(){
	$("#frm").attr("action","excel.jsp");
	$("#frm").submit();
}
</script>
</head>
<body>
<form method="post" id="frm" name="frm" target="ifr"></form>

<iframe id="ifr" name="ifr" width="0" hidden="true"></iframe>
<div class="wrap">
<!-- <button onclick="getReply();">댓글 수집</button> -->
<button onclick="excel();">엑셀다운</button>
<div class="msg">

</div>
</div>
</body>
</html>