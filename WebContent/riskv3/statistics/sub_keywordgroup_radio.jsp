<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
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
	String k_yp = pr.getString("k_yp","");
	String as_seq = pr.getString("as_seq");
	String selectedXp = "";
	String selectedYp = "";
	
	userEnvInfo uei = null;
    uei     = (userEnvInfo) session.getAttribute("ENV");    
    userEnvMgr uemng = new userEnvMgr();
    
    AlimiSettingDao asDao = new AlimiSettingDao();
    ArrayList alimiSetList = new ArrayList();
    AlimiSettingBean asBean = null;	
    
	
 	 //하위 키워드 그룹
    ArrayList arrGroup = new ArrayList();    
    arrGroup = uemng.getSubKeywordGroup(k_xp, k_yp);
        
    if(mode.equals("UPDATE") && as_seq.length()>0)
	{	    		
   		alimiSetList = asDao.getAlimiSetList(1,0,as_seq,"Y");
   		asBean = (AlimiSettingBean)alimiSetList.get(0);
    	
   		selectedXp = asBean.getKg_xps();
		selectedYp = asBean.getKg_yps();
	}
    
    
    
    int maxCnt = 0;
    int width = 20;
	int colspanCnt = 0;
	int topTotalCnt = 0;
	int subTotalCnt = 0;
	String element = "";
	String resultTag = "";
	String[] arrSelected = null;
	String aSelected = "";
	String sSelected = "";						
	
	int tempCnt = 0;
	int childCnt = 0;
	
	//if(!uei.getSg_Seq().equals(""))arrSelected = String.valueOf(uei.getSg_Seq()).split(",");
	resultTag = "";
	
	maxCnt =4;
	
	if(!k_xp.equals("0")){		
				
		resultTag += "<table width=\"100%\">\n";
			//resultTag += "  <td><input type=\"radio\" name=\"topSiteGroup\" value=\"0\" onclick=\"changeTopSiteGroup();\" "+aSelected+"><strong><strong>전체("+topTotalCnt+")</strong></strong></td>";
			//resultTag += "  <td><input type=\"radio\" name=\"topSiteGroup\" value=\"0\" onclick=\"changeTopSiteGroup(); doSearch();\" "+aSelected+"><strong><strong>전체</strong></strong></td>";
		for( int i = 0; i < arrGroup.size() ; i++ ) {							
			
			keywordInfo kInfo = (keywordInfo)arrGroup.get(i);                    		
			
			sSelected = "";
			if(mode.equals("UPDATE")){
				arrSelected = selectedYp.split(",");					
				for(int j=0;j<arrSelected.length; j++)
				{
					if(selectedXp.equals(k_xp)&& arrSelected[j].equals(String.valueOf(kInfo.getK_Yp())))sSelected ="checked";
				}
				
			}				
			
			if(i==arrGroup.size()-1){
				colspanCnt = (maxCnt - arrGroup.size()%maxCnt) +1;
				if(colspanCnt>0)width = colspanCnt * width;
			}
			
			//resultTag += "  <td><input type=\"radio\" name=\"topSiteGroup\" value=\""+sgGroup.get_seq()+"\" onclick=\"changeTopSiteGroup();\" "+sSelected+">"+sgGroup.get_name()+"("+sgGroup.getCnt()+")"+"</td>";
			if(k_yp.equals("")){
				element = "  <td width=\""+width+"%\" colspan=\""+colspanCnt+"\"><input type=\"radio\" name=\"k_yp\" onclick=\"getSubKeywordGroup2();\"  value=\""+kInfo.getK_Yp()+"\"  "+sSelected+">"+kInfo.getK_Value()+"</td>\n";
			}else{
				element = "  <td width=\""+width+"%\" colspan=\""+colspanCnt+"\"><input type=\"radio\" name=\"k_zp\" value=\""+kInfo.getK_Zp()+"\"  "+sSelected+">"+kInfo.getK_Value()+"</td>\n";
			}
			
			if(i==0) resultTag +="<tr>\n";
			
			resultTag += element;
			
			if(i==arrGroup.size()-1){
				resultTag +="<tr>\n";
			}else{
				if((i%maxCnt)==(maxCnt-1)) {
					resultTag +="</tr>\n<tr>\n";
				}
			}
		}
		resultTag += "</table>\n";
	}
    
    
    	
 %>
<%=resultTag%>