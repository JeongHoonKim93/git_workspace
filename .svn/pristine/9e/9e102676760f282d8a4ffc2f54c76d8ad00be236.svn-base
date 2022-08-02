<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%@ page import="java.util.ArrayList,
                 risk.util.Log,
                 risk.util.StringUtil,
                 risk.util.ParseRequest,
                 risk.util.DateUtil,                              
                 risk.admin.site.SitegroupBean,
                 risk.search.siteDataInfo,                
                 risk.search.userEnvMgr,
                 risk.search.userEnvInfo,
                 risk.search.keywordInfo,
                 risk.mobile.AlimiSettingBean,
				 risk.mobile.AlimiSettingDao
                 "
%>
<%
	ParseRequest pr  = new ParseRequest(request);
	pr.printParams();
	
	String mode = pr.getString("mode");
	String k_xp = pr.getString("k_xp","0");
	String k_yp = pr.getString("k_yp","0");
	String as_seq = pr.getString("as_seq");
	String ts_seq = pr.getString("ts_seq");
	userEnvInfo uei = null;
    uei     = (userEnvInfo) session.getAttribute("ENV");    
    userEnvMgr uemng = new userEnvMgr();
    
    AlimiSettingDao asDao = new AlimiSettingDao();
    ArrayList alimiSetList = new ArrayList();
    AlimiSettingBean asBean = null;	
    
 	 //하위 키워드 그룹
    ArrayList arrGroup = new ArrayList();    
    arrGroup = uemng.getSubKeywordGroup(k_xp);
        
	alimiSetList = asDao.getTelegramSetList(ts_seq);
	asBean = (AlimiSettingBean)alimiSetList.get(0);

	String[] yp = null;
	String[] yp2 = null;

	String[] arrSelected = null;
	String aSelected = "";
	String sSelected = "";						
	
%>
 		<th><span>&nbsp<img src="../../../images/admin/alimi/icon_bot_03.gif">키워드 중그룹</span></th>
 		
		<td>
<%

	if(!k_xp.equals("0")){		
		for( int i = 0; i < arrGroup.size() ; i++ ) {							
			keywordInfo kInfo = (keywordInfo)arrGroup.get(i);                    		
			sSelected = "";
			if(mode.equals("UPDATE")){
				yp = asBean.getKeywords().split(",");
				//arrSelected = selectedYp.split(",");					
				for(int j=0;j<yp.length; j++)
				{
					yp2 = yp[j].split("@");
					if(yp2[0].equals(k_xp)&& yp2[1].equals(String.valueOf(kInfo.getK_Yp())))sSelected ="checked";
				}
				
			}				
%>
		<div style="width:150px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;float:left" title="<%=kInfo.getK_Value()%>"><input type="checkbox" name="k_yp" value="<%=kInfo.getK_Yp()%>" <%=sSelected%>><%=kInfo.getK_Value()%></div>
<%
		}
	}
%>
		</td>	
		