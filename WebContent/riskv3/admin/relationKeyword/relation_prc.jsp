<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,
				 risk.admin.relation.RelationBean,
                 risk.admin.relation.RelationMgr,
                 risk.util.ParseRequest" 
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	ParseRequest    pr = new ParseRequest(request);
	RelationMgr rMgr = new RelationMgr();
	
	pr.printParams();
	
	String mode = pr.getString("mode");
	String rk_name = pr.getString("rk_name");
	String rkseq = pr.getString("rk_seq");
	
	RelationBean rBean = new RelationBean();
	
	//rBean.setId_seq(pr.getString("id_seq"));
	//rBean.setRk_name(pr.getString("rk_name"));
	//rBean.setRk_seq(pr.getString("rk_seq"));
	//rBean.setRk_use(pr.getString("rk_use"));
	
	
	if( mode.equals("add")){
		if(rMgr.InsertKeyword(rk_name))
		{
%>
			<script type="text/javascript">
					alert('저장하였습니다.');
					opener.Search();
					window.close();
					opener.window.close();
			</script>
<%		
		}
	}
	else if( mode.equals("update")){
		if(rMgr.UpdateKeyword(rk_name, rkseq))
		{
%>
			<script type="text/javascript">
					alert('수정하였습니다.');
					opener.Search();
					window.close();
					opener.window.close();
			</script>
<% 
		}
	}
	else if( mode.equals("del")){
		rMgr.DeleteKeyword(rkseq);
%>		
		<script type="text/javascript">
		parent.location.reload();//='relation_keyword.jsp';
		</script>
<%
	}
	
	else if( mode.equals("sum")){
		String aa = rMgr.relationkeywordMerge(rk_name,rkseq);
		
		if(aa.equals("success")){
			%>
			<script type="text/javascript">
				opener.Search();
				window.close();
				opener.window.close();
			</script>
			<%
		}else{
			%>
			<script type="text/javascript">
				alert("합치기 실패! 다시 시도해 주세요.");
				window.close();
			</script>
			<%
		}

	}
%>

