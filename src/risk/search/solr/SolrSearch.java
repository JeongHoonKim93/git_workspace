package risk.search.solr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.activation.FileDataSource;

import org.apache.commons.beanutils.BeanUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import risk.util.*;



public class SolrSearch {
	
	DateUtil du = new DateUtil();

	public static String queryUrl="";
	public static String q="";
	public static int hitCnt = 0;
	
	public String startDate = ""; 
	public String endDate = "";
	public ArrayList arr_list = new ArrayList();
	
	//String sgroup = "sgroup:(1 OR 2 OR 3 OR 4 OR 5) AND ";
	//String serverURL = "http://s.realsn.com/api";
	String serverURL = "http://lucyapi.realsn.com/SmisAPI";
	String serverPORT = "8983";
	
	//////////////////////////////API error Start
	
	//API key
	ConfigUtil cu = new ConfigUtil();
	String lucyKey = cu.getConfig("LUCY_API");
	//API error
	String errorCode ="";
	String errorMsg ="";
	String errorAlertMsg ="";
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getErrorAlertMsg() {
		return errorAlertMsg;
	}

	public void setErrorAlertMsg(String errorAlertMsg) {
		this.errorAlertMsg = errorAlertMsg;
	}
	
	//////////////////////////////API error END
	
	public ArrayList getDataList(String keyword, int page, int pLength, String sdate, String sgroup,  String sort, String sort_order, String s_seq, String userid) throws IOException, JDOMException{
		
		System.out.println("===========================");
		System.out.println(keyword);
		System.out.println("===========================");
		
		ArrayList dataList = new ArrayList();
		
		/**
		 * sgroup : 1 ~ 6
		 * 1 : 언론
		 * 2 : 블로그
		 * 3 : 카페
		 * 4 : 커뮤니티
		 * 5 : 기타사이트
		 * 6 : 중국어
		 */
		
		//http://localhost:8080/solr/db/select/?q=sgroup:(1 OR 2 OR 3 OR 4 OR 5) &version=2.2&start=0&rows=10&indent=on
		
//		다음 단어 모두 포함   : AND  ex) 아이폰 갤럭시 아이패드
//		다음 문구 정확하게 포함   : 구문 ex) "아이폰 갤럭시 아이패드"
//		다음 단어 적어도 하나 포함   OR ex) 아이폰 OR 갤럭시 OR 아이패드
//		다음 단어 제외 : NOT  ex) (아이폰 갤럭시) NOT(아이패드)
// 		다음 단어가 근접하게 포함 : NEER ex) "아이폰 갤럭시 아이패드"~10
		
		
		String ecd_keyword = changeEncode(keyword, "utf-8");
		
		if(ecd_keyword.length() <= 0){
			ecd_keyword = "";
		}
		
		page = (page - 1) * pLength;
		//http://s.realsn.com/api?type=Search&q=iphone&sdate=20111001&start=0&rows=10&sort=docid&returntype=xml		
		queryUrl = serverURL+"?type=Search&language=KOR";
		q = "&systemkey="+lucyKey+"&userid="+userid+"&q="+ ecd_keyword+"&"+sgroup+"&sdate=" + sdate + "&start=" + page + "&rows=" + pLength + "&sort="+ sort+"&sort_order="+sort_order+ "&s_seq=" + s_seq +"&returntype=xml";
		
		
		//http://58.180.17.6:8983/solr/db/select/?q=test&rows=0&facet=true&facet.field=sdate&facet.sort=false
		
		System.out.println("[ecd_keyword]" + ecd_keyword);
		System.out.println("[sdate]" + sdate);
		System.out.println("[page]" + Integer.toString(page));
		System.out.println("[pLength]" + Integer.toString(pLength));
		System.out.println("[queryUrl]" + queryUrl);
		System.out.println("[q]" + q);
		
		dataList =loadXmlParser(queryUrl, q);
		
		return dataList;
	}
	
	
	public ArrayList getDataExcel(String keyword,  String sdate, String sgroup,  String sort, String sort_order, int limit, String userid) throws IOException, JDOMException{
		
		ArrayList allDataList = new ArrayList();
		ArrayList dataList = new ArrayList();
		
		
		String ecd_keyword = changeEncode(keyword, "utf-8");
		
		queryUrl = serverURL+"?type=Search&language=KOR";
		
		if(ecd_keyword.length() <= 0){
			ecd_keyword = "";
		}
		
		int pageLangth = 100;
		
		int part = limit / pageLangth;
		int start = 0;
		
		ArrayList ar_dataList = new ArrayList();
		
		// 루푸돌려서 데이터 가져오기~
		for(int i = 0; i < part; i++){
			q = "&systemkey="+lucyKey+"&userid="+userid+"&q="+ ecd_keyword+"&"+sgroup+"&sdate=" + sdate + "&start=" + start + "&rows=" + pageLangth + "&sort="+ sort+"&sort_order="+sort_order+"&returntype=xml";
			
			System.out.println(queryUrl +q );
			
			dataList =loadXmlParser(queryUrl, q);
			
			System.out.println(dataList.size());
			
			if(dataList.size() > 0){
				
				ar_dataList.add(dataList);
				
				if(dataList.size() < pageLangth){
					break;
				}
				
				start += pageLangth;
			}else{
				break;
			}
			
		}
		
		System.out.println(ar_dataList.size());
		
		
		
		//데이터 합치기
		//SearchForm et = null;
		for(int i =0; i < ar_dataList.size(); i++){
			dataList = (ArrayList)ar_dataList.get(i);
			for(int j = 0; j < dataList.size(); j++){
				
				allDataList.add((SearchForm)dataList.get(j));
			}
		}
		
		return allDataList;
	}
	
	
	
	
	public String getChartXml(String keyword, String sdate, String edate, String searchDate, String sgroup, String s_seq, String userid) throws IOException, JDOMException, ParseException{
		ArrayList cntList = new ArrayList();
		
		String ecd_keyword = changeEncode(keyword, "utf-8");
		

		if(ecd_keyword.length() <= 0){
			ecd_keyword ="";
		}
		
		queryUrl = serverURL+"?type=Facet&language=KOR";
		q = "&systemkey="+lucyKey+"&userid="+userid+"&q="+ ecd_keyword +"&"+sgroup+"&sdate="+sdate+"&edate="+edate+ "&s_seq=" + s_seq +"&rows=0&returntype=xml";
		
		
		System.out.println("[queryUrl]" + queryUrl);
		System.out.println("[q]" + q);
		
		arr_list.clear();
		cntList =loadChartData(queryUrl, q);
		arr_list.add(cntList);
		
		
		String[] color = Constants.COLOR;
		String xml = Constants.STICK_CHARTXML_TOP;
		String chColor1 = "#E8E8E8";
		String chColor2 = "#6495ED";
		String chColor = "";
		SubForm sf;
		
		int chkcnt=0;
		int cnt=0;
		int Avg=0;
		 
		String date = "";
		
		for(int i=0 ; i< cntList.size();i++){
			sf = (SubForm)cntList.get(i);
						
			if(i == 0){
				startDate = sf.getDate();
			}else if (i == cntList.size() -1){
				endDate = sf.getDate();
			}
			
			if(!searchDate.equals("")){
				if (sf.getDate().equals(searchDate)) {
					chColor = chColor2;
				} else {
					chColor = chColor1;
				}
			}else{
				if (sf.getDate().equals(edate)) {
					chColor = chColor2;
				} else {
					chColor = chColor1;
				}
			}
			

			date = du.getDate(sf.getDate(), "MM/dd");
			
			if(i%2 == 0){
				xml +=" <set name='"+date+"' color='" + chColor + "' value='"+sf.getCnt()+"'  			  link='javascript:parent.searchDataList("+sf.getDate()+"); ' /> \n";
			}else{
				xml +=" <set name='"+date+"' color='" + chColor + "' value='"+sf.getCnt()+"' showName='0' link='javascript:parent.searchDataList("+sf.getDate()+"); ' /> \n";
			}
			
			if(sf.getCnt() == 0){chkcnt++;}
			cnt += sf.getCnt();
		}
		Avg = cnt/cntList.size();
		xml += "  <trendlines>\n";
		xml += "    <line startvalue='"+Avg+"' displayValue=' ' color='FF0000' thickness='1' isTrendZone='0'/>\n";
		xml += "  </trendlines>\n";
		xml +="</graph>\n";
		
		if(cntList.size() == chkcnt){
			xml = "";
		}
	
		return xml.replaceAll("\n","").replaceAll((new Character((char)13)).toString(),"").replaceAll((new Character((char)9)).toString(),"").replaceAll((new Character((char)11)).toString(),"");
	}
	
	public String getLineChartXml(String keyword, String sdate, String edate, String searchDate, String sgroup, String sg_name, String s_seq, String userid) throws IOException, JDOMException, ParseException{
		ArrayList cntList = new ArrayList();
		
		String ecd_keyword = changeEncode(keyword, "utf-8");

		String[] arrdate = du.arrDay(du.formatDate(sdate, "yyyyMMdd", "yyyy-MM-dd"), du.formatDate(edate, "yyyyMMdd", "yyyy-MM-dd"));
		
		sgroup = sgroup.substring(sgroup.indexOf("s_code=(")+8, sgroup.length()-2);
		
		if(ecd_keyword.length() <= 0){
			ecd_keyword ="";
		}
		ArrayList result = new ArrayList();
		
		arr_list.clear();
		
		for(int i = 0; i < sgroup.split(" OR ").length; i++){
			queryUrl = serverURL+"?type=Facet&language=KOR";
			q = "&systemkey="+lucyKey+"&userid="+userid+"&q="+ ecd_keyword +"&s_code=("+sgroup.split(" OR ")[i]+")&sdate="+sdate+"&edate="+edate+ "&s_seq=" + s_seq + "&rows=0&returntype=xml";
			
			System.out.println("[queryUrl]" + queryUrl);
			System.out.println("[q]" + q);
			
			cntList =loadChartData(queryUrl, q);
			arr_list.add(cntList);
			
			
			for(int j = 0; j < cntList.size(); j++){
				SubForm sf = (SubForm)cntList.get(j);
				if(sgroup.split(" OR ")[i].equals("N")){
					sf.setName("N");
				}else if(sgroup.split(" OR ")[i].equals("K")){
					sf.setName("K");
				}else if(sgroup.split(" OR ")[i].equals("B")){
					sf.setName("B");
				}else if(sgroup.split(" OR ")[i].equals("C")){
					sf.setName("C");
				}else if(sgroup.split(" OR ")[i].equals("T")){
					sf.setName("T");
				}else if(sgroup.split(" OR ")[i].equals("E")){
					sf.setName("E");
				}
				result.add(sf);
				System.out.println("result.size() : "+result.size());
			}
		}
		
		String chartLink = "true";
		String linkScript = "";
		StringBuffer strXml = new StringBuffer();
		boolean chk = false;
		int cnt = 0;
		
		strXml.append("<chart baseFont='Dotum' BaseFontSize='12' outCnvBaseFontSize='12' bgColor='FFFFFF, FFFFFF' showBorder='0' divLineAlpha='30' labelPadding ='10' yAxisValuesPadding ='10' showValues='0' rotateValues='1' valuePosition='Below' canvaspadding='10'>\n");
		strXml.append("	<categories>\n");
		for(int i = 0; i < arrdate.length; i++){
			strXml.append("		<category label='"+du.getDate(arrdate[i], "MM/dd")+"' />\n");
		}
		strXml.append("	</categories>\n");
			for(int j = 0; j < sgroup.split(" OR ").length; j++){
				if(sgroup.split(" OR ")[j].equals("N")){
					strXml.append("	<dataset seriesName='언론'>\n");
				}else if(sgroup.split(" OR ")[j].equals("K")){
					strXml.append("	<dataset seriesName='커뮤니티'>\n");
				}else if(sgroup.split(" OR ")[j].equals("B")){
					strXml.append("	<dataset seriesName='블로그'>\n");
				}else if(sgroup.split(" OR ")[j].equals("C")){
					strXml.append("	<dataset seriesName='카페'>\n");
				}else if(sgroup.split(" OR ")[j].equals("T")){
					strXml.append("	<dataset seriesName='트위터'>\n");
				}else if(sgroup.split(" OR ")[j].equals("E")){
					strXml.append("	<dataset seriesName='기타'>\n");
				}
				
				
				for(int i = 0; i < arrdate.length; i++){
					for(int k = 0; k < result.size(); k++){
						SubForm sf = (SubForm)result.get(k);
						//System.out.println(arrdate[i]+" = "+sf.getDate()+", "+sgroup.split(" OR ")[j]+" = "+sf.getName());
						if(du.formatDate(arrdate[i], "yyyy-MM-dd", "yyyyMMdd").equals(sf.getDate()) && sgroup.split(" OR ")[j].equals(sf.getName())){
							if(chartLink.equals("true")){
								//linkScript = "link=\"JavaScript: isJavaScriptCall=true; alert('test');\"";
								
								if(sf.getDate().equals(searchDate) && sf.getName().equals(sg_name)){
									linkScript = "link='javascript:parent.searchDataList_source("+sf.getDate()+",&apos;"+sf.getName()+"&apos;);' anchorBorderThickness='6'";
								}else{
									linkScript = "link='javascript:parent.searchDataList_source("+sf.getDate()+",&apos;"+sf.getName()+"&apos;);'";
								}
								
								
							}
							cnt = sf.getCnt();
							chk = true;
							break;
						}
					}
					if(chk){
						strXml.append("		<set value='"+cnt+"' "+linkScript+" />\n");				
					}else{
						strXml.append("		<set value='0' />\n");
					}
					chk = false;
					cnt = 0;
				}
				strXml.append("	</dataset>\n");
			}
		strXml.append("</chart>\n");
		
		return strXml.toString().replaceAll("\n","").replaceAll((new Character((char)13)).toString(),"").replaceAll((new Character((char)9)).toString(),"").replaceAll((new Character((char)11)).toString(),"");
	}
	

	public ArrayList loadXmlParser(String queryUrl, String q) throws IOException ,JDOMException{
		boolean chk_error = false;
		ArrayList dataList = new ArrayList();	
		DateUtil du = new DateUtil();
		System.out.println("queryUrl:"+queryUrl);
		Element root = rootReturn(queryUrl,q);
		
		Element lst = root.getChild("lst");
		List lst_elm = lst.getChildren();

		//에러코드 확인
		if(lst_elm.size() > 0){
			for(int l=0; l< lst_elm.size(); l++){
				Element element = (Element)lst_elm.get(l);
				if(element.getAttributeValue("name").equals("code")){
					if(  element.getValue().toString().equals("1") ){
						chk_error = true;
						break;
					}else{
						chk_error = false;
					}
				}
			}
		}
		//에러 일경우 에러 메시지 가져오기		
		if(chk_error){
		
			for(int l2=0; l2< lst_elm.size(); l2++){
				Element strElement = (Element)lst_elm.get(l2);
				
				if(strElement.getAttributeValue("name").equals("errorMessage")){
					String msg = strElement.getValue().toString();
					setErrorCode( msg );//에러 메시지
					
				}else if( strElement.getAttributeValue("name").equals("alertMessage") ){
					String alert = strElement.getValue().toString();
					setErrorAlertMsg(alert);//알림창 메시지
					
				}
			}
		} else {	//정상인 경우 데이터 파싱	
			List docs = null;
			Element result = root.getChild("result");
			docs = result.getChildren();
			hitCnt = Integer.parseInt(result.getAttributeValue("numFound"));
			SearchForm et = null;
			
			for(int i=0;i<docs.size();i++){
				Element doc=(Element)docs.get(i);
				
				List docFields = doc.getChildren();
				
				Iterator iter = docFields.iterator();
				et = new SearchForm();
				while (iter.hasNext()) {
					Element field = (Element) iter.next();
					try {
						//BeanUtils.setProperty(et, field.getAttributeValue("name").toLowerCase(), field.getValue());
						//System.out.println(field.getAttributeValue("name")+"     " + field.getValue());
						if(field.getAttributeValue("name").equals("docid")){
							et.setDocid(Integer.parseInt(field.getValue()));
						} else if(field.getAttributeValue("name").equals("gsn")){
							et.setGsn(Integer.parseInt(field.getValue()));
						} else if(field.getAttributeValue("name").equals("html")){
							et.setHtml(field.getValue());
						} else if(field.getAttributeValue("name").equals("img_name")){
							et.setImg_name(field.getValue());
						} else if(field.getAttributeValue("name").equals("isp")){
							et.setIsp(Integer.parseInt(field.getValue()));
						} else if(field.getAttributeValue("name").equals("menu")){
							et.setMenu(field.getValue());
						} else if(field.getAttributeValue("name").equals("name")){
							et.setName(field.getValue());
						} else if(field.getAttributeValue("name").equals("pid")){
							et.setPid(Integer.parseInt(field.getValue()));
						} else if(field.getAttributeValue("name").equals("sdate")){
							et.setSdate(field.getValue());
						} else if(field.getAttributeValue("name").equals("sgroup")){
							et.setSgroup(Integer.parseInt(field.getValue()));
						} else if(field.getAttributeValue("name").equals("sn")){
							et.setSn(Integer.parseInt(field.getValue()));
						} else if(field.getAttributeValue("name").equals("stime")){
							et.setStime(field.getValue());
						} else if(field.getAttributeValue("name").equals("subid")){
							et.setSubid(Integer.parseInt(field.getValue()));
						} else if(field.getAttributeValue("name").equals("title")){
							et.setTitle(field.getValue());
						} else if(field.getAttributeValue("name").equals("type")){
							et.setType(Integer.parseInt(field.getValue()));
						} else if(field.getAttributeValue("name").equals("url")){
							et.setUrl(field.getValue());
						}
					} catch (Exception e) {
					}
				}
				dataList.add(et);
			}
		}
		return dataList;
	}

	
	//서버에 쿼리를 보낸후 응답으로 데이터 리스트를 가져온다
	public ArrayList loadChartData(String queryUrl, String q) throws IOException ,JDOMException{
		ArrayList cntList = new ArrayList();
		DateUtil du = new DateUtil();
		Element root = rootReturn(queryUrl,q);
		List lsts = root.getChildren();
		Element lst = null;
		Iterator iter = lsts.iterator();
		String date = "";
		while(iter.hasNext()){
			lst = (Element) iter.next();
			if(lst.getAttributeValue("name").equals("facet_counts")){
				break;
			}
		}
		
		List hlts = lst.getChildren();
		iter = hlts.iterator();
		
		while (iter.hasNext()) {
			lst = (Element) iter.next();
			if(lst.getAttributeValue("name").equals("facet_fields")){
				break;
			}
		}
		
		hlts = lst.getChildren();
		iter = hlts.iterator();
		lst = (Element) iter.next();
		
		hlts = lst.getChildren();
		iter = hlts.iterator();
		SubForm sf = null;
		while(iter.hasNext()){
			Element field = (Element) iter.next();
			sf = new SubForm();
			
			date =field.getAttributeValue("name");
			
			if(date.length() > 2){
				sf.setDate(field.getAttributeValue("name"));
				sf.setCnt(Integer.parseInt(field.getValue()));
				
				cntList.add(sf);
			}
		}
		
		return cntList;
	}
	
	//Element root를 리턴해준다
	public Element rootReturn(String queryUrl, String q)throws IOException ,JDOMException{
		Document xmlDoc = null;
		SAXBuilder sax = null;
		Element root=null;
		sax = new SAXBuilder();
		URL url = new URL(queryUrl);
		URLConnection conn;
		conn = url.openConnection();
		((HttpURLConnection)conn).setRequestMethod("POST");
		conn.setDoOutput(true);
	    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	    wr.write(q);
	    wr.flush();
		
	    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
	    
	    Document tempXMLDoc = sax.build(rd);
		
		if (tempXMLDoc != null) {
			xmlDoc = tempXMLDoc;
			root=xmlDoc.getRootElement();
		}
		return root;
	}
	
	/**
	 * <p>str을 URLIncoding 하여 반환한다</p>
	 * @param str code
	 * @return String 인코딩 결과
	 */
    public static final String changeEncode(String str, String code){
		try {
			return  URLEncoder.encode(str, code);
		} catch (UnsupportedEncodingException e) {
			return str;
		}
	}

}
