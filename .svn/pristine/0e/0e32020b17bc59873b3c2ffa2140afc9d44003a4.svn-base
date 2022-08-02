<%@ page contentType = "text/html; charset=utf-8"%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="risk.util.ParseRequest"%>
<%@ page import="risk.admin.alarm.alarmMgr"%>
<%@ page import="risk.admin.alarm.alarmBean"%>
<%
	ParseRequest pr = new ParseRequest(request);
	alarmMgr am = new alarmMgr();
	alarmBean ab = new alarmBean();

	String k_seq = pr.getString("k_seq");
	String ar_type = pr.getString("ar_type");
	String ar_time = pr.getString("ar_time", "null");
	String ar_value = pr.getString("ar_value", "null");
	String ar_avg_cnt = pr.getString("ar_avg_cnt");
	String ar_sendtype = pr.getString("ar_sendtype");
	String ar_receiver = pr.getString("ar_receiver");
	String mode = pr.getString("mode");
	String ar_seqs = pr.getString("ar_seqs");
	
	ab.setK_seq(k_seq);
	ab.setAr_type(ar_type);
	ab.setAr_time(ar_time);
	ab.setAr_value(ar_value);
	ab.setAr_avg_cnt(ar_avg_cnt);
	ab.setAr_sendtype(ar_sendtype);
	ab.setAr_receiver(ar_receiver);
	
	if(mode.equals("insert")){
		am.regAlarmData(ab);
		out.print("<script>alert('저장되었습니다.');parent.document.location.href = 'alarmMain.jsp';</script>");
	}else if(mode.equals("update")){
		ab.setAr_seq(ar_seqs);
		am.updateAlarmData(ab);
		out.print("<script>alert('수정되었습니다.');parent.document.location.href = 'alarmMain.jsp';</script>");
	}else if(mode.equals("delete")){
		am.delAlarmData(ar_seqs);
		out.print("<script>alert('삭제되었습니다.');parent.document.location.href = 'alarmMain.jsp';</script>");
	}
%>