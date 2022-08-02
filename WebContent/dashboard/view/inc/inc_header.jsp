 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 
<script type="text/javascript">
function gotoLucy(id){
	$("#formLucy").remove();
	var form = $('<form></form>');
	form.attr('id','formLucy');
	form.attr('action','https://lucy2search.realsn.com/?key=3725d7f18e3042e98f7ab64fb745d666&home_type=1&default_search_word=빙그레');
	form.attr('method','post');
	form.attr('target','_blank');
	
	$(document.body).append(form);
	$("#formLucy").submit();
}

</script>
 
 <header>
	<div class="wrap">
		<h1>
            <a href="../member/login.jsp"><img src="../../asset/img/h1_logo.png" alt="빙그레"></a>
        </h1>
        
		<!-- Include HEADER UTIL -->
		<jsp:include page="../inc/inc_header_util.jsp" flush="false" />
        <!-- // Include HEADER UTIL -->
        
        <div class="pins">
            <div class="header_fix_pin">
                <input id="pin_chk_00" type="checkbox" autocomplete="off">
                <label class="ui_btn is-icon-only" for="pin_chk_00"><span class="icon">&#xe062;</span></label>
            </div>
        </div>
    
		<nav>
			<ul>
                <li><a href="../summary" data-idx="01"><span class="txt" lang="en">Summary</span></a></li>
                <li><a href="../press_release" data-idx="02"><span class="txt" lang="ko">보도자료 확산 분석</span></a></li>
                <!-- <li><a href="https://lucy2.realsn.com/" target="_blank" ><span class="txt" lang="ko">온라인 동향분석</span></a></li> -->
                <li><a href="javascript:void(0);" onclick="gotoLucy(); return false;" target="_blank" ><span class="txt" lang="ko">온라인 동향분석</span></a></li> 
            </ul>
        </nav>
	</div>
</header>

