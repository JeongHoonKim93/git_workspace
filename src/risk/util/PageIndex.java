package risk.util;

public class PageIndex {
	
	public static String getPageIndex(int nowpage, int totpage) {
        String rtnStr="";
        int idx = 0;
        int i = 0;
        int idxpage = 0;
        int tmp = 0;
        int tmp2 = 0;
        int tmp3 = 0;
        int nUpdown = 0;

        idx = ( nowpage - 1) / 10 + 1;
        for( i = 1 ; i <= 10 ; i++ )
        {
            idxpage = (idx - 1) * 10 + i;

            if( idxpage != 0 && idxpage <= totpage ) {
                if( idxpage == nowpage ) {
                    if ( i == 1 && idx != 1 ) {
                        tmp = idxpage - 1;
                        rtnStr = ""; //"<FONT size=2>";

                        rtnStr += "<a HREF=\"javascript:pageClick('" + tmp + "');\" ";
                        rtnStr += "><img src=\"./images/con_prev.gif\" border=\"0\" align=\"absmiddle\"></A>&nbsp;&nbsp;";
                        rtnStr += "<B><font color=\"000\">" +  idxpage + "</font></B> &nbsp; ";
                    } else if ( i == 10 ) {
                        tmp = idxpage + 1;
                        tmp2 = idxpage - 1;
                        tmp3 = totpage;
                        rtnStr += "";
                        rtnStr += "<B><font color=\"000\">" +  idxpage + "</font></B> &nbsp; ";

                        if ( totpage >= tmp ) {
                            if ( idx != totpage / 10 ){
                                rtnStr += "<a HREF=\"javascript:pageClick('" + tmp + "');\" ";
                            } else {
                                rtnStr += "<a HREF=\"javascript:pageClick('" + tmp + "');\" ";
                            }
                           rtnStr += "><img src=\"./images/con_next.gif\" border=\"0\" align=\"absmiddle\"></A>&nbsp;";
                        }
                    } else {
                        rtnStr += "<B><font color=\"000\">" +  idxpage + "</font></B> &nbsp; ";
                    }

                } else {
                    if ( i == 1 && idx != 1 ) {
                        tmp = idxpage - 1;
                        rtnStr += "";
                        //rtnStr += "<a HREF='" + gourl + "?nowpage=1" + add_tag + "'> ";
                        //rtnStr += "<img src=\"./images/next2.gif\" border=\"0\" align=\"absmiddle\"></A>&nbsp;";
                        rtnStr += "<a HREF=\"javascript:pageClick('"+ tmp +"');\" ";
                        rtnStr += "><img src=\"./images/con_prev.gif\" border=\"0\" align=\"absmiddle\"></A>&nbsp;&nbsp;";
                        rtnStr += "<a HREF=\"javascript:pageClick('" + idxpage + "');\"";
                        rtnStr += ">" +  idxpage + "</A> &nbsp; ";
                    } else if ( i == 10 ) {
                        tmp = idxpage + 1;
                        tmp2 = totpage;
                        rtnStr += "";
                        rtnStr += "<a HREF=\"javascript:pageClick('" +  idxpage + "');\" ";
                        rtnStr += ">" +  idxpage + "</A> &nbsp; ";

                        if ( totpage >= tmp ) {
                            if ( idx != totpage / 10) {
                                rtnStr += "<a HREF=\"javascript:pageClick('" + tmp + "');\"";
                            } else {
                                rtnStr += "<a HREF=\"javascript:pageClick('" + tmp + "');\"";
                            }
                            rtnStr += "><img src=\"./images/con_next.gif\" border=\"0\" align=\"absmiddle\"></A>&nbsp;";
                            //rtnStr += "<a HREF='" + gourl + "?nowpage=" + tmp2 + add_tag + "&nUpdown=-1'> ";
                            //rtnStr += "<img src=\"./images/prev2.gif\" border=\"0\" align=\"absmiddle\"></A>&nbsp;";

                        }
                    } else {
                        rtnStr += "";
                        rtnStr += "<a HREF=\"javascript:pageClick('" + idxpage + "');\" ";
                        rtnStr += ">" +  idxpage + "</A> &nbsp; ";
                    }
                }
            }
        }

        return rtnStr;
    }
	
	public static String getPageIndex(int nowpage, int totpage,int funcType, String url) {
        String rtnStr="";
        int idx = 0;
        int i = 0;
        int idxpage = 0;
        int tmp = 0;
        int tmp2 = 0;
        int tmp3 = 0;
        int nUpdown = 0;

        idx = ( nowpage - 1) / 10 + 1;
        for( i = 1 ; i <= 10 ; i++ )
        {
            idxpage = (idx - 1) * 10 + i;

            if( idxpage != 0 && idxpage <= totpage ) {
                if( idxpage == nowpage ) {
                    if ( i == 1 && idx != 1 ) {
                        tmp = idxpage - 1;
                        rtnStr = ""; //"<FONT size=2>";

                        rtnStr += "<a HREF=\"javascript:pageClick('" + tmp + "','"+url+"',"+funcType+");\" ";
                        rtnStr += "><img src=\"./images/con_prev.gif\" border=\"0\" align=\"absmiddle\"></A>&nbsp;&nbsp;";
                        rtnStr += "<B><font color=\"000\">" +  idxpage + "</font></B> &nbsp; ";
                    } else if ( i == 10 ) {
                        tmp = idxpage + 1;
                        tmp2 = idxpage - 1;
                        tmp3 = totpage;
                        rtnStr += "";
                        rtnStr += "<B><font color=\"000\">" +  idxpage + "</font></B> &nbsp; ";

                        if ( totpage >= tmp ) {
                            if ( idx != totpage / 10 ){
                                rtnStr += "<a HREF=\"javascript:pageClick('" + tmp + "','"+url+"',"+funcType+");\" ";
                            } else {
                                rtnStr += "<a HREF=\"javascript:pageClick('" + tmp + "','"+url+"',"+funcType+");\" ";
                            }
                           rtnStr += "><img src=\"./images/con_next.gif\" border=\"0\" align=\"absmiddle\"></A>&nbsp;";
                        }
                    } else {
                        rtnStr += "<B><font color=\"000\">" +  idxpage + "</font></B> &nbsp; ";
                    }

                } else {
                    if ( i == 1 && idx != 1 ) {
                        tmp = idxpage - 1;
                        rtnStr += "";
                        //rtnStr += "<a HREF='" + gourl + "?nowpage=1" + add_tag + "'> ";
                        //rtnStr += "<img src=\"./images/next2.gif\" border=\"0\" align=\"absmiddle\"></A>&nbsp;";
                        rtnStr += "<a HREF=\"javascript:pageClick('"+ tmp +"','"+url+"',"+funcType+");\" ";
                        rtnStr += "><img src=\"./images/con_prev.gif\" border=\"0\" align=\"absmiddle\"></A>&nbsp;&nbsp;";
                        rtnStr += "<a HREF=\"javascript:pageClick('" + idxpage + "','"+url+"',"+funcType+");\"";
                        rtnStr += ">" +  idxpage + "</A> &nbsp; ";
                    } else if ( i == 10 ) {
                        tmp = idxpage + 1;
                        tmp2 = totpage;
                        rtnStr += "";
                        rtnStr += "<a HREF=\"javascript:pageClick('" +  idxpage + "','"+url+"',"+funcType+");\" ";
                        rtnStr += ">" +  idxpage + "</A> &nbsp; ";

                        if ( totpage >= tmp ) {
                            if ( idx != totpage / 10) {
                                rtnStr += "<a HREF=\"javascript:pageClick('" + tmp + "','"+url+"',"+funcType+");\"";
                            } else {
                                rtnStr += "<a HREF=\"javascript:pageClick('" + tmp + "','"+url+"',"+funcType+");\"";
                            }
                            rtnStr += "><img src=\"./images/con_next.gif\" border=\"0\" align=\"absmiddle\"></A>&nbsp;";
                            //rtnStr += "<a HREF='" + gourl + "?nowpage=" + tmp2 + add_tag + "&nUpdown=-1'> ";
                            //rtnStr += "<img src=\"./images/prev2.gif\" border=\"0\" align=\"absmiddle\"></A>&nbsp;";

                        }
                    } else {
                        rtnStr += "";
                        rtnStr += "<a HREF=\"javascript:pageClick('" + idxpage + "','"+url+"',"+funcType+");\" ";
                        rtnStr += ">" +  idxpage + "</A> &nbsp; ";
                    }
                }
            }
        }

        return rtnStr;
    }
	
	
	public static String getPageIndex(int nowpage, int totpage, String gourl, String add_tag) {
		ConfigUtil cu = new ConfigUtil();
		String url =cu.getConfig("URL")+"/images/common/";
        String rtnStr="";
        int idx = 0;
        int i = 0;
        int idxpage = 0;
        int tmp = 0;
        int tmp2 = 0;
        int tmp3 = 0;
        int nUpdown = 0;

        if(add_tag == null){
        	add_tag = "";
        }
        idx = (nowpage - 1) / 10 + 1;
        for(i = 1 ; i <= 10 ; i++){
            idxpage = (idx - 1) * 10 + i;
            if(idxpage != 0 && idxpage <= totpage){
                if(idxpage == nowpage){
                    if(i == 1 && idx != 1){
                        tmp = idxpage - 1;
                        rtnStr = "";
                        rtnStr += "<td class=\"img\"><a HREF=\"javascript:pageClick('" + gourl + "?nowpage=" + tmp + add_tag + "&nUpdown=-1');\" ";
                        rtnStr += "><img src=\""+url+"btn_paging_prev.gif\" border=\"0\" align=\"absmiddle\"></a></td>";
                        rtnStr += "<th><B><font color=\"000\">" +  idxpage + "</font></B></th>";
                    }else if( i == 10 ){
                        tmp = idxpage + 1;
                        tmp2 = idxpage - 1;
                        tmp3 = totpage;
                        rtnStr += "";
                        rtnStr += "<th><B><font color=\"000\">" +  idxpage + "</font></B></th>";

                        if ( totpage >= tmp ) {
                            if ( idx != totpage / 10 ){
                                rtnStr += "<td class=\"img\"><a HREF=\"javascript:pageClick('" + gourl + "?nowpage=" + tmp + add_tag + "&nUpdown=1');\" ";
                            } else {
                                rtnStr += "<td class=\"img\"><a HREF=\"javascript:pageClick('" + gourl + "?nowpage=" + tmp + add_tag + "&nUpdown=-1');\" ";
                            }
                            rtnStr += "><img src=\""+url+"btn_paging_next.gif\" border=\"0\" align=\"absmiddle\"></a></td>";
                        }
                    }else{
                        rtnStr += "<th><B><font color=\"000\">" +  idxpage + "</font></B></th>";
                    }
                }else{
                    if(i == 1 && idx != 1){
                        tmp = idxpage - 1;
                        rtnStr += "";
                        rtnStr += "<td class=\"img\"><a HREF=\"javascript:pageClick('" + gourl + "?nowpage=" + tmp + add_tag + "&nUpdown=-1');\" ";
                        rtnStr += "><img src=\""+url+"btn_paging_prev.gif\" border=\"0\" align=\"absmiddle\"></a></td>";
                        rtnStr += "<td><a HREF=\"javascript:pageClick('" + gourl + "?nowpage=" +  idxpage + add_tag + "');\"";
                        rtnStr += ">" +  idxpage + "</a></td>";
                    }else if( i == 10 ){
                        tmp = idxpage + 1;
                        tmp2 = totpage;
                        rtnStr += "";
                        rtnStr += "<td><a HREF=\"javascript:pageClick('" + gourl + "?nowpage=" +  idxpage + add_tag + "');\" ";
                        rtnStr += ">" +  idxpage + "</a></td>";
                        
                        if(totpage >= tmp){
                            if(idx != totpage / 10){
                                rtnStr += "<td class=\"img\"><a HREF=\"javascript:pageClick('" + gourl + "?nowpage=" + tmp + add_tag + "&nUpdown=1');\"";
                            } else {
                                rtnStr += "<td class=\"img\"><a HREF=\"javascript:pageClick('" + gourl + "?nowpage=" + tmp + add_tag + "&nUpdown=-1');\"";
                            }
                            rtnStr += "><img src=\""+url+"btn_paging_next.gif\" border=\"0\" align=\"absmiddle\"></a></td>";
                        }
                    }else{
                        rtnStr += "";
                        rtnStr += "<td><a HREF=\"javascript:pageClick('" + gourl + "?nowpage=" +  idxpage + add_tag + "');\" ";
                        rtnStr += ">" +  idxpage + "</a></td>";
                    }
                }
            }
        }
        return rtnStr;
    }
	
	
	public static String getVipPageIndex(int nowpage, int totpage, String gourl, String add_tag) {
    	ConfigUtil cu = new ConfigUtil();
		
    	String url =cu.getConfig("URL")+"riskv3/vip/";
        String rtnStr="";
        int idx = 0;
        int i = 0;
        int idxpage = 0;
        int tmp = 0;
        int tmp2 = 0;
        int tmp3 = 0;
        int nUpdown = 0;

        if (add_tag == null)
            add_tag = "";

        idx = ( nowpage - 1) / 10 + 1;
        for( i = 1 ; i <= 10 ; i++ )
        {
            idxpage = (idx - 1) * 10 + i;

            if( idxpage != 0 && idxpage <= totpage ) {
                if( idxpage == nowpage ) {
                    if ( i == 1 && idx != 1 ) {
                        tmp = idxpage - 1;
                        rtnStr = ""; //"<FONT size=2>";

                        //rtnStr += "<a HREF='" +gourl+ "?nowpage=1" + add_tag + "'>";
                        //rtnStr += "<img src=\"./images/next2.gif\" border=\"0\" align=\"absmiddle\"></A>&nbsp;";

                        rtnStr += "<a HREF=\"javascript:pageClick('"+ tmp +"');\" ";
                        rtnStr += "><img src=\""+url+"images/btn_prev.gif\" border=\"0\" align=\"absmiddle\"></A>&nbsp;&nbsp;";
                        rtnStr += "<B><font size=\"2\" color=\"000\">" +  idxpage + "</font></B> &nbsp; ";
                    } else if ( i == 10 ) {
                        tmp = idxpage + 1;
                        tmp2 = idxpage - 1;
                        tmp3 = totpage;
                        rtnStr += "";
                        rtnStr += "<B><font size=\"2\" color=\"000\">" +  idxpage + "</font></B> &nbsp; ";

                        if ( totpage >= tmp ) {
                            if ( idx != totpage / 10 ){
                                rtnStr += "<a HREF=\"javascript:pageClick('"+ tmp +"');\" ";
                            } else {
                                rtnStr += "<a HREF=\"javascript:pageClick('"+ tmp +"');\" ";
                            }
                            rtnStr += "><img src=\""+url+"images/btn_next.gif\" border=\"0\" align=\"absmiddle\"></A>&nbsp;";
                            //rtnStr += "&nbsp;<a HREF='" + gourl + "?nowpage=" + tmp3 + add_tag + "&nUpdown=-1'> ";
                            //rtnStr += "<img src=\"./images/prev2.gif\" border=\"0\" align=\"absmiddle\"></A>&nbsp;";
                        }
                    } else {
                        rtnStr += "<B><font size=\"2\" color=\"000\">" +  idxpage + "</font></B> &nbsp; ";
                    }

                } else {
                    if ( i == 1 && idx != 1 ) {
                        tmp = idxpage - 1;
                        rtnStr += "";
                        //rtnStr += "<a HREF='" + gourl + "?nowpage=1" + add_tag + "'> ";
                        //rtnStr += "<img src=\"./images/next2.gif\" border=\"0\" align=\"absmiddle\"></A>&nbsp;";
                        rtnStr += "<a HREF=\"javascript:pageClick('"+ tmp +"');\" ";
                        rtnStr += "><img src=\""+url+"images/btn_prev.gif\" border=\"0\" align=\"absmiddle\"></A>&nbsp;&nbsp;";
                        rtnStr += "<a HREF=\"javascript:pageClick('"+  idxpage +"');\"";
                        rtnStr += "><font size=\"2\">" +  idxpage + "</font></A> &nbsp; ";
                    } else if ( i == 10 ) {
                        tmp = idxpage + 1;
                        tmp2 = totpage;
                        rtnStr += "";
                        rtnStr += "<a HREF=\"javascript:pageClick('"+  idxpage + "');\" ";
                        rtnStr += "><font size=\"2\">" +  idxpage + "</font></A> &nbsp; ";

                        if ( totpage >= tmp ) {
                            if ( idx != totpage / 10) {
                                rtnStr += "<a HREF=\"javascript:pageClick('"+ tmp +"');\"";
                            } else {
                                rtnStr += "<a HREF=\"javascript:pageClick('"+ tmp +"');\"";
                            }
                            rtnStr += "><img src=\""+url+"images/btn_next.gif\" border=\"0\" align=\"absmiddle\"></A>&nbsp;";
                            //rtnStr += "<a HREF='" + gourl + "?nowpage=" + tmp2 + add_tag + "&nUpdown=-1'> ";
                            //rtnStr += "<img src=\"./images/prev2.gif\" border=\"0\" align=\"absmiddle\"></A>&nbsp;";

                        }
                    } else {
                        rtnStr += "";
                        rtnStr += "<a HREF=\"javascript:pageClick('"+ idxpage + "');\" ";
                        rtnStr += "><font size=\"2\">" +  idxpage + "</font></A> &nbsp; ";
                    }
                }
            }
        }

        return rtnStr;
    }
	
	
	public static String getPagination(int nowpage, int totpage) {
		ConfigUtil cu = new ConfigUtil();
		String url =cu.getConfig("URL")+"/images/dashboard2/";
        String rtnStr="";
        int idx = 0;
        int i = 0;
        int idxpage = 0;
        int tmp = 0;
        int tmp2 = 0;
        int tmp3 = 0;
        int nUpdown = 0;

        idx = (nowpage - 1) / 10 + 1;
        for(i = 1 ; i <= 10 ; i++){
            idxpage = (idx - 1) * 10 + i;
            if(idxpage != 0 && idxpage <= totpage){
                if(idxpage == nowpage){
                    if(i == 1 && idx != 1){
                        tmp = idxpage - 1;
                        rtnStr = "";
                        rtnStr += "<td width=\"18\" class=\"img\"><a HREF=\"javascript:pageClick("+ tmp + ");\" ";
                        rtnStr += "><img src=\""+url+"num_arrow_L.gif\" border=\"0\" align=\"absmiddle\"></a></td>";
                        rtnStr += "<td width=\"1\" bgcolor=\"#E2E2E2\"></td>";
                        rtnStr += "<td width=\"18\" align=\"center\"><B><font color=\"#518fd8\">" +  idxpage + "</font></B></td>";
                    }else if( i == 10 ){
                        tmp = idxpage + 1;
                        tmp2 = idxpage - 1;
                        tmp3 = totpage;
                        rtnStr += "";
                        rtnStr += "<td width=\"18\" align=\"center\"><B><font color=\"#518fd8\">" +  idxpage + "</font></B></td>";

                        if ( totpage >= tmp ) {
                        	rtnStr += "<td width=\"1\" bgcolor=\"#E2E2E2\"></td>";
                            if ( idx != totpage / 10 ){
                                rtnStr += "<td width=\"18\" class=\"img\"><a HREF=\"javascript:pageClick("+ tmp + ");\" ";
                            } else {
                                rtnStr += "<td width=\"18\" class=\"img\"><a HREF=\"javascript:pageClick("+ tmp + ");\" ";
                            }                            
                            rtnStr += "><img src=\""+url+"num_arrow_R.gif\" border=\"0\" align=\"absmiddle\"></a></td>";
                        }
                    }else{
                    	rtnStr += "<td width=\"18\"><img src=\""+url+"num_arrow_L.gif\" alt=\"\" width=\"18\" height=\"15\" /></td>";
                    	rtnStr += "<td width=\"1\" bgcolor=\"#E2E2E2\"></td>";
                    	rtnStr += "<td width=\"18\" align=\"center\"><B><font color=\"#518fd8\">" +  idxpage + "</font></B></td>";
                    }
                }else{
                    if(i == 1 && idx != 1){
                        tmp = idxpage - 1;
                        rtnStr += "";
                        rtnStr += "<td width=\"18\" class=\"img\"><a HREF=\"javascript:pageClick("+ tmp + ");\" ";
                        rtnStr += "><img src=\""+url+"num_arrow_L.gif\" border=\"0\" align=\"absmiddle\"></a></td>";
                        rtnStr += "<td width=\"18\" align=\"center\"><a HREF=\"javascript:pageClick(" + idxpage + ");\"";
                        rtnStr += ">" +  idxpage + "</a></td>";
                    }else if( i == 10 ){                    	
                        tmp = idxpage + 1;
                        tmp2 = totpage;
                        //rtnStr += "";
                        rtnStr += "<td width=\"1\" bgcolor=\"#E2E2E2\"></td>";
                        rtnStr += "<td width=\"18\" align=\"center\"><a HREF=\"javascript:pageClick(" + idxpage + ");\" ";
                        rtnStr += ">" +  idxpage + "</a></td>";
                        
                        if(totpage >= tmp){
                        	rtnStr += "<td width=\"1\" bgcolor=\"#E2E2E2\"></td>";
                            if(idx != totpage / 10){
                                rtnStr += "<td width=\"18\" class=\"img\" align=\"center\"><a HREF=\"javascript:pageClick(" + idxpage + ");\"";
                                rtnStr += "><img src=\""+url+"num_arrow_R.gif\" border=\"0\" align=\"absmiddle\"></a></td>";
                            } else {
                                rtnStr += "<td width=\"18\" class=\"img\" align=\"center\"><a HREF=\"javascript:pageClick(" + idxpage + ");\"";
                                rtnStr += "><img src=\""+url+"num_arrow_R.gif\" border=\"0\" align=\"absmiddle\"></a></td>";
                            }
                            
                        }
                    }else{                    	
                        //rtnStr += "";
                        rtnStr += "<td width=\"1\" bgcolor=\"#E2E2E2\"></td>";
                        rtnStr += "<td width=\"18\" align=\"center\"><a HREF=\"javascript:pageClick(" + idxpage + ");\" ";
                        rtnStr += ">" +  idxpage + "</a></td>";
                    }
                }
            }
        }
        return rtnStr;
    }
	
	
	public static String getPagination(int nowpage, int totpage, String fn_name) {
		ConfigUtil cu = new ConfigUtil();
		String url =cu.getConfig("URL")+"images/dashboard3/";
        String rtnStr="";
        int idx = 0;
        int i = 0;
        int idxpage = 0;
        int tmp = 0;
        int tmp2 = 0;
        int tmp3 = 0;
        int nUpdown = 0;

        idx = (nowpage - 1) / 10 + 1;
        for(i = 1 ; i <= 10 ; i++){
            idxpage = (idx - 1) * 10 + i;
            if(idxpage != 0 && idxpage <= totpage){
                if(idxpage == nowpage){
                    if(i == 1 && idx != 1){
                        tmp = idxpage - 1;
                        rtnStr = "";
                        rtnStr += "<td width=\"18\" class=\"img\"><a HREF=\"javascript:pageClick"+fn_name+"("+ tmp + ");\" ";
                        rtnStr += "><img src='"+url+"num_arrow_L.gif' border=\"0\" align=\"absmiddle\"></a></td>";
                        rtnStr += "<td width=\"1\" bgcolor=\"#E2E2E2\"></td>";
                        rtnStr += "<td width=\"18\" align=\"center\"><B><font color=\"#518fd8\">" +  idxpage + "</font></B></td>";
                    }else if( i == 10 ){
                        tmp = idxpage + 1;
                        tmp2 = idxpage - 1;
                        tmp3 = totpage;
                        rtnStr += "";
                        rtnStr += "<td width=\"18\" align=\"center\"><B><font color=\"#518fd8\">" +  idxpage + "</font></B></td>";

                        if ( totpage >= tmp ) {
                        	rtnStr += "<td width=\"1\" bgcolor=\"#E2E2E2\"></td>";
                            if ( idx != totpage / 10 ){
                                rtnStr += "<td width=\"18\" class=\"img\"><a HREF=\"javascript:pageClick"+fn_name+"("+ tmp + ");\" ";
                            } else {
                                rtnStr += "<td width=\"18\" class=\"img\"><a HREF=\"javascript:pageClick"+fn_name+"("+ tmp + ");\" ";
                            }                            
                            rtnStr += "><img src='"+url+"num_arrow_R.gif' border=\"0\" align=\"absmiddle\"></a></td>";
                        }
                    }else{
                    	//rtnStr += "<td width=\"18\"><img src=\""+url+"num_arrow_L.gif\" alt=\"\" width=\"18\" height=\"15\" /></td>";
                    	rtnStr += "<td width=\"1\" bgcolor=\"#E2E2E2\"></td>";
                    	rtnStr += "<td width=\"18\" align=\"center\"><B><font color=\"#518fd8\">" +  idxpage + "</font></B></td>";
                    }
                }else{
                    if(i == 1 && idx != 1){
                        tmp = idxpage - 1;
                        rtnStr += "";
                        rtnStr += "<td width=\"18\" class=\"img\"><a HREF=\"javascript:pageClick"+fn_name+"("+ tmp + ");\" ";
                        rtnStr += "><img src='"+url+"num_arrow_L.gif' border=\"0\" align=\"absmiddle\"></a></td>";
                        rtnStr += "<td width=\"18\" align=\"center\"><a HREF=\"javascript:pageClick"+fn_name+"(" + idxpage + ");\"";
                        rtnStr += ">" +  idxpage + "</a></td>";
                    }else if( i == 10 ){
                        tmp = idxpage + 1;
                        tmp2 = totpage;
                        //rtnStr += "";
                        rtnStr += "<td width=\"1\" bgcolor=\"#E2E2E2\"></td>";
                        rtnStr += "<td width=\"18\" align=\"center\"><a HREF=\"javascript:pageClick"+fn_name+"(" + idxpage + ");\" ";
                        rtnStr += ">" +  idxpage + "</a></td>";
                        
                        if(totpage >= tmp){                        	
                        	rtnStr += "<td width=\"1\" bgcolor=\"#E2E2E2\"></td>";
                            if(idx != totpage / 10){
                                rtnStr += "<td width=\"18\" class=\"img\" align=\"center\"><a HREF=\"javascript:pageClick"+fn_name+"(" + idxpage + ");\"";
                                rtnStr += "><img src='"+url+"num_arrow_R.gif' border=\"0\" align=\"absmiddle\"></a></td>";
                            } else {
                                rtnStr += "<td width=\"18\" class=\"img\" align=\"center\"><a HREF=\"javascript:pageClick"+fn_name+"(" + idxpage + ");\"";
                                rtnStr += "><img src='"+url+"num_arrow_R.gif' border=\"0\" align=\"absmiddle\"></a></td>";
                            }
                            
                        }
                    }else{	
                        //rtnStr += "";
                        rtnStr += "<td width=\"1\" bgcolor=\"#E2E2E2\"></td>";
                        rtnStr += "<td width=\"18\" align=\"center\"><a HREF=\"javascript:pageClick"+fn_name+"(" + idxpage + ");\" ";
                        rtnStr += ">" +  idxpage + "</a></td>";
                    }
                }
            }
        }
        return rtnStr;
    }
    
	/**
	 * 신규 페이징 
	 * @author Alexseong
	 * @param 현재페이지, 전체Row 수, 한페이지당 보여질ROW 수
	 */
	public static String paging(int nowpage, int totalCnt, int rowCnt, String link){
		ConfigUtil cu = new ConfigUtil();
		String url =cu.getConfig("URL")+"images/dashboard3/";
        String rtnStr="";
        
        int totalPage = 0;
    	int quotient = totalCnt/rowCnt;
    	int remainder = totalCnt%rowCnt;
    	
    	totalPage = quotient;
    	if(remainder > 0){
    		totalPage += 1;
    	}
    	
    	int pageIndex = totalPage/rowCnt;
    	int namuge = totalPage%rowCnt;
    	if(namuge > 0 ){
    		pageIndex = pageIndex + 1;
    	}   	
    	
    	int pageBlockSize = 10;	//페이징 블럭 사이즈
    	int endPage = ( ((nowpage-1)/pageBlockSize) + 1 ) * rowCnt;
    	int startPage =  endPage -4;
    	
    	if(totalPage < pageBlockSize){
    		endPage = totalPage;
    		startPage = 1;
    	}
    	
    	if(nowpage > 10){ 
    		rtnStr += "<a class=\"page_prev\" href=\"javascript:goPage"+link+"('"+(endPage-10)+"');\"><span class=\"icon\">이전</span></a>";
    	}else{ 
    		rtnStr +="<a disabled=\"\" class=\"page_prev\" href=\"#\"><span class=\"icon\">이전</span></a>";
    	} 
    	if(totalPage < endPage){ 
		
			for(int i = 1; i <= totalPage; i++){
				if(nowpage == i){
					rtnStr +="<a class=\"active\" href=\"#\" style=\"cursor:default !important;pointer-events:none !important\">"+i+"</a>";
				}else{ 
					rtnStr +="<a href=\"javascript:goPage"+link+"('"+i+"');\" style=\"cursor:pointer\">"+i+"</a>";
				}
			}
		
			rtnStr +="<a disabled=\"\" class=\"page_next\" href=\"#\"><span class=\"icon\">다음</span></a>";
    	}else{ 
		
			for(int i = 1; i <= endPage; i++){
				if(nowpage == i){
					rtnStr +="<a class=\"active\" href=\"#\" style=\"cursor:default !important;pointer-events:none !important\">"+i+"</a>";
				}else{ 
					rtnStr +="<a href=\"javascript:goPage"+link+"('"+i+"');\" style=\"cursor:pointer\">"+i+"</a>";
				}
			}
		
			if(totalPage > 10){
				if(totalPage > nowpage){
					rtnStr +="<a class=\"page_next\" href=\"javascript:goPage"+link+"('"+(endPage+1)+"');\"><span class=\"icon\">다음</span></a>";
				}else{
					rtnStr +="<a disabled=\"\" class=\"page_next\" href=\"#\"><span class=\"icon\">다음</span></a>";
				}
			}else{
				rtnStr +="<a disabled=\"\" class=\"page_next\" href=\"#\"><span class=\"icon\">다음</span></a>";
			}
		
    	}
    	
        return rtnStr;
	}
	
	
	/**
	 * 신규 페이징 
	 * @author Alexseong
	 * @param 현재페이지, 전체Row 수, 한페이지당 보여질ROW 수
	 */
	public static String paging_twitter(int nowpage, int totalCnt, int rowCnt, String link){
		ConfigUtil cu = new ConfigUtil();
		String url =cu.getConfig("URL")+"images/dashboard3/";
        String rtnStr="";
        
        System.out.println(nowpage+" "+totalCnt+" "+rowCnt+ "   ----- check" );
        
        int totalPage = 0;
    	int quotient = totalCnt/rowCnt;
    	int remainder = totalCnt%rowCnt;
    	
    	totalPage = quotient;
    	if(remainder > 0){
    		totalPage += 1;
    	}
    	
    	int pageIndex = totalPage/rowCnt;
    	int namuge = totalPage%rowCnt;
    	if(namuge > 0 ){
    		pageIndex = pageIndex + 1;
    	}   	
    	
    	
    	int pageBlockSize = 5;	//페이징 블럭 사이즈
    	int idx = ( ((nowpage-1)/rowCnt) + 1 );
    	int idxpage = (idx-1) * rowCnt + 1;
    	int startPage =  idxpage;
    	int endPage = idxpage+(pageBlockSize-1);
    	/*int startPage =  nowpage;*/
    	
    	if(nowpage > pageBlockSize){ 
    		rtnStr += "<a class=\"page_prev\" href=\"javascript:goPage"+link+"('"+(endPage-pageBlockSize)+"');\"><span class=\"icon\">이전</span></a>";
    	}else{ 
    		rtnStr +="<a disabled=\"\" class=\"page_prev\" href=\"#\"><span class=\"icon\">이전</span></a>";
    	} 
    	if(totalPage < endPage){ 
		
			for(int i = startPage; i <= totalPage; i++){
				if(nowpage == i){
					rtnStr +="<a class=\"active\" href=\"#\" style=\"cursor:default !important;pointer-events:none !important\">"+i+"</a>";
				}else{ 
					rtnStr +="<a href=\"javascript:goPage"+link+"('"+i+"');\" style=\"cursor:pointer\">"+i+"</a>";
				}
			}
		
			rtnStr +="<a disabled=\"\" class=\"page_next\" href=\"#\"><span class=\"icon\">다음</span></a>";
    	}else{ 
		
			for(int i = startPage; i <= endPage; i++){
				if(nowpage == i){
					rtnStr +="<a class=\"active\" href=\"#\" style=\"cursor:default !important;pointer-events:none !important\">"+i+"</a>";
				}else{ 
					rtnStr +="<a href=\"javascript:goPage"+link+"('"+i+"');\" style=\"cursor:pointer\">"+i+"</a>";
				}
			}
		
			if(totalPage > pageBlockSize){
				if(totalPage > nowpage){
					rtnStr +="<a class=\"page_next\" href=\"javascript:goPage"+link+"('"+(endPage+1)+"');\"><span class=\"icon\">다음</span></a>";
				}else{
					rtnStr +="<a disabled=\"\" class=\"page_next\" href=\"#\"><span class=\"icon\">다음</span></a>";
				}
			}else{
				rtnStr +="<a disabled=\"\" class=\"page_next\" href=\"#\"><span class=\"icon\">다음</span></a>";
			}
    	}
    	
        return rtnStr;
	}
}
