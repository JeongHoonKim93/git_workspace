<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ page import="risk.admin.classification.classificationMgr,
				 risk.admin.classification.clfBean,
				 risk.util.DateUtil,
                 risk.util.ParseRequest,
                 java.util.List" 
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%

    DateUtil        du = new DateUtil();
    ParseRequest    pr = new ParseRequest(request);
    
    classificationMgr cm = new classificationMgr();
    List cmList = null;
    String script_str = "";
	
	int itype = pr.getInt("itype",0);
	int icode = pr.getInt("icode",0);
	String clf_name = pr.getString("clf_name","");
	String mode = pr.getString("mode","");
	String icSeqList = pr.getString("icSeqList","");
	
	

	if( mode.equals("add") ) {
		//out.println("itype = "+itype);
		//out.println("icode = "+icode);
		//out.println("clf_name = "+clf_name);
		
		if( itype > 0 ) {
			if( cm.InsertClf( itype, icode, clf_name, SS_M_NO ) ) {
				script_str = "parent.frm_menu.location = 'frm_classification.jsp?itype="+itype+"&icode="+icode+"';	\n"
							 +"parent.frm_detail.location = 'frm_classification_detail.jsp?itype="+itype+"&icode="+icode+"';	\n";
				out.println("  ");
			}else {
				script_str = "parent.frm_menu.location = 'frm_classification_detail.jsp?itype="+itype+"&icode="+icode+"';	\n";
				out.println(" 입력 실패 ");
			}
		}
	}else if( mode.equals("del") ) {
		//out.println("icSeqList = "+icSeqList);
		if( cm.DelClf(icSeqList) ) {
			script_str = "parent.frm_menu.location = 'frm_classification.jsp?itype="+itype+"&icode="+icode+"';	\n"
			 			 +"parent.frm_detail.location = 'frm_classification_detail.jsp?itype="+itype+"&icode="+icode+"';	\n";
			out.println("  ");
		}else {
			out.println(" 삭제 실패 ");
		}
		
	}else if( mode.equals("up") ) {
		
		if( cm.MoveClf( mode, itype, icode) ) {
			script_str = "parent.frm_menu.location = 'frm_classification.jsp?itype="+itype+"&icode="+icode+"';	\n"
						 +"parent.frm_detail.location = 'frm_classification_detail.jsp?itype="+itype+"&icode="+icode+"';	\n";
			out.println("  ");
		}else {
			script_str = "parent.frm_menu.location = 'frm_classification_detail.jsp?itype="+itype+"&icode="+icode+"';	\n";
			out.println(" 이동 실패 ");
		}
	}else if( mode.equals("down") ) {
		if( cm.MoveClf( mode, itype, icode) ) {
			script_str = "parent.frm_menu.location = 'frm_classification.jsp?itype="+itype+"&icode="+icode+"';	\n"
						 +"parent.frm_detail.location = 'frm_classification_detail.jsp?itype="+itype+"&icode="+icode+"';	\n";
			out.println("  ");
		}else {
			script_str = "parent.frm_menu.location = 'frm_classification_detail.jsp?itype="+itype+"&icode="+icode+"';	\n";
			out.println(" 이동 실패 ");
		}
	}else if( mode.equals("update") ) {
		String ic_seq = pr.getString("ic_seq");
		String ic_name = pr.getString("ic_name");
		
		cm.ModifyName(ic_seq, ic_name);
		script_str = "parent.opener.parent.frm_detail.location = 'frm_classification_detail.jsp?itype="+itype+"&icode="+icode+"';	parent.close();\n";
		
	}
%>
<SCRIPT LANGUAGE="JavaScript">
<!--
	<%=script_str%>
//-->
</SCRIPT>