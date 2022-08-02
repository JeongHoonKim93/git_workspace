package risk.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.*;

public class PersonalInfoUtil {
	
	
	
	
	public static void main(String[] args) {
	
		
		PersonalInfoUtil piUtil = new PersonalInfoUtil();
		
		//String targetTitle = "111-111-1111 matt.seo@realsn.com 생일 : 08.20  mattseo@realsn.com #서영진,#서영진1 08.20 #mattwww #matt,#mattseq @abcdefg,@abcdefg @abcd_efg @abcde-fg @abss333cdefg @abcdefg222  58.180.17.2 01055052570 010-5505-2570 031)965-6978";
		String targetTitle ="서울시 광진구 구의동, 광주시 오포읍 양벌1리 612, 일산동구 중산동 하늘마을4단지, 경기도 광주시 송정동, 경기도 김포시 태장로 845 김포한강센트럴자이";
		//String targetTitle ="3710-020001-03561, 5409-2605-5046-6078 57991040828507";
		
		
		System.out.println(targetTitle);
		
		//System.out.println(piUtil.replacePInfo(targetTitle));
		
//		System.out.println(piUtil.replaceNearInfo(targetTitle));
	
		//System.out.println(piUtil.removeTwitterID(targetTitle));
		//System.out.println(piUtil.removeHashTag(targetTitle));
		
		System.out.println(piUtil.replaceAddressInfo(targetTitle));
		
		//System.out.println(piUtil.replaceMD5(targetTitle));
		
	}
	
	public String replaceAddressInfo(String text) {
		
		String [] addressPattern = {
			 "([가-힣]{2,6})(시|군|구)\\s*([가-힣]{2,6})(읍|면|구)\\s*([가-힣0-9]{2,20})(리|로|길|동)"
			,"([가-힣]{2,6})(시|군|구)\\s*([가-힣]{2,6})(로|길)"
			,"([가-힣]{2,6})(시|군|구)\\s*([가-힣]{2,6})(동|리)"
		};
		
		String [] addressPattern2 = {
				 "([가-힣]{2,6})(시|군|구)\\s*([가-힣]{2,6})(읍|면|구)"
				,"([가-힣]{2,6})(시|군|구)"
				,"([가-힣]{2,6})(시|군|구)"
			};

		String character = "";
		String result = "";

		for (int i=0 ; i<addressPattern.length; i++) {
			Pattern regex = Pattern.compile(addressPattern[i], Pattern.MULTILINE);
			Pattern regex2 = Pattern.compile(addressPattern2[i], Pattern.MULTILINE);
			Matcher matcher = regex.matcher(text);
			Matcher matcher2 = null;
			
			result = "";	
			
			while(matcher.find()){
				matcher2 = regex2.matcher(matcher.group());
				matcher2.find();				
				result = matcher.group().replaceAll(matcher2.group(), "");
				character = "";
				for(int j =0 ; j < result.length(); j++) {
					character += "*"; 
				}
				text = replace(text, result, character);
			}	
		}
		return text;
	}
	
	
	private String[] regexpStr = null;
	private String[] regexpReplaceStr = null;
	
	private String[][] regexpNearStr = null;
	private String[] regexpReplaceNearStr = null;
	
	
	
	public PersonalInfoUtil() {
		int index = 0;
		regexpStr = new String[54];
		regexpReplaceStr = new String[54];
		
		regexpStr[index] = "\\d{18}";											
		regexpReplaceStr[index]  = "******************"; //상품권번호
		index++;
		
		regexpStr[index] = "\\d{16}";											
		regexpReplaceStr[index]  = "****************";								// 하나은행 요청
		index++;
		
		regexpStr[index] = "\\d{14}";											
		regexpReplaceStr[index]  = "**************";								// 하나은행
		index++;
		
		
		
		regexpStr[index] = "[(]\\d{3}[)][\\s-:.]\\d{4}[\\s-:.]\\d{4}";			
		regexpReplaceStr[index]  = "(***)-****-****";								//전화번호
		index++;
		
		regexpStr[index] = "[(]\\d{4}[\\s]\\d{2}[)][\\s-:.]\\d{5}";				
		regexpReplaceStr[index]  = "(**** **)-*****";
		index++;
		
		regexpStr[index] = "\\d{2}[\\s-:.]\\d{2}[\\s-:.]\\d{6}[\\s-:.]\\d{2}";	
		regexpReplaceStr[index]  = "**-**-******-**";								// 운전면허 번호
		index++;
		
		regexpStr[index] = "\\d{15}";											
		regexpReplaceStr[index]  = "***************";
		index++;
		
		regexpStr[index] = "\\d{1}[\\s-:.]\\d{3}[\\s-:.]\\d{3}[\\s-:.]\\d{4}";	
		regexpReplaceStr[index]  = "*-***-***-****";
		index++;
		
		regexpStr[index] = "[(]\\d{3}[)][\\s-:.]\\d{3}[\\s-:.]\\d{4}";			
		regexpReplaceStr[index]  = "(***)-***-****";
		index++;
		
		regexpStr[index] = "[(]\\d{5}[)][\\s-:.]\\d{6}";							
		regexpReplaceStr[index]  = "(*****)-******";
		index++;
		
		regexpStr[index] = "[(]\\d{4}[\\s]\\d{2}[)][\\s-:.]\\d{4}";				
		regexpReplaceStr[index]  = "(**** **)-****";
		index++;
		
		regexpStr[index] = "[(]\\d{2}[)][\\s-:.]\\d{4}[\\s-:.]\\d{4}";			
		regexpReplaceStr[index]  = "(**)-****-****";
		index++;
		
		regexpStr[index] = "\\d{6}[\\s-:.]\\d{7}";									// 주민등록번호, 외국인 등록번호
		regexpReplaceStr[index]  = "******-*******";
		index++;
		
		regexpStr[index] = "\\d{4}[\\s-:.]\\d{4}[\\s-:.]\\d{1}[\\s-:.]\\d{2}";					
		regexpReplaceStr[index] = "****-****-*-**";								// 계좌번호
		index++;
		
		regexpStr[index] = "\\d{3}[\\s-:.]\\d{4}[\\s-:.]\\d{4}";					
		regexpReplaceStr[index] = "***-****-****";
		index++;
		
		regexpStr[index] = "[(]\\d{5}[)][\\s-:.]\\d{5}";							
		regexpReplaceStr[index] = "(*****)-*****";
		index++;
		
		regexpStr[index] = "\\d{3}[\\s-:.]\\d{4}[\\s-:.]\\d{4}";					
		regexpReplaceStr[index] = "*** **** ****";
		index++;
		
		regexpStr[index] = "\\d{13}";											
		regexpReplaceStr[index] = "*************";
		index++;
		
		regexpStr[index] = "\\d{2}[\\s-:.]\\d{2}[\\s-:.]\\d{5}[\\s-:.]\\d{1}";					
		regexpReplaceStr[index] = "**-**-*****-*";								// 계좌번호
		index++;
		
		regexpStr[index] = "\\d{1}[\\s-:.]\\d{6}[\\s-:.]\\d{2}[\\s-:.]\\d{1}";					
		regexpReplaceStr[index] = "*-******-**-*";								// 계좌번호
		index++;
		
		regexpStr[index] = "\\d{1}[\\s-:.]\\d{11}";											
		regexpReplaceStr[index] = "*-***********";								// 계좌번호
		index++;
		
		regexpStr[index] = "\\d{3}[\\s-:.]\\d{3}[\\s-:.]\\d{4}";						
		regexpReplaceStr[index] = "***-***-****";								// 계좌번호
		index++;
		
		regexpStr[index] = "\\d{2}[\\s-:.]\\d{4}[\\s-:.]\\d{4}";					
		regexpReplaceStr[index] = "**-****-****";
		index++;
		
		regexpStr[index] = "\\d{5}[\\s-:.]\\d{6}";								
		regexpReplaceStr[index] = "*****-******";
		index++;
		
		regexpStr[index] = "\\d{4}[\\s-:.]\\d{3}[\\s-:.]\\d{3}";					
		regexpReplaceStr[index] = "****-***-***";
		index++;
		
		regexpStr[index] = "\\d{2}[\\s-:.]\\d{2}[\\s-:.]\\d{6}";					
		regexpReplaceStr[index] = "**-**-******";								// 계좌번호
		index++;
		
		regexpStr[index] = "\\d{12}";											
		regexpReplaceStr[index] = "************";
		index++;
		
		regexpStr[index] = "\\d{3}[\\s-:.]\\d{2}[\\s-:.]\\d{5}";								
		regexpReplaceStr[index] = "***-**-*****";								// 사업자 등록번호
		index++;
		
		regexpStr[index] = "\\d{7}[\\s-:.]\\d{1}[\\s-:.]\\d{2}";								
		regexpReplaceStr[index] = "*******-*-**";								// 계좌번호
		index++;
		
		regexpStr[index] = "\\d{8}[\\s-:.]\\d{3}";								
		regexpReplaceStr[index] = "********-***";								// 계좌번호
		index++;
		
		regexpStr[index] = "\\d{3}[\\s-:.]\\d{8}";								
		regexpReplaceStr[index] = "***-********";								// 계좌번호
		index++;
		
		regexpStr[index] = "\\d{4}[\\s-:.]\\d{4}[\\s-:.]\\d{2}";								
		regexpReplaceStr[index] = "****-****-**";								// 계좌번호
		index++;
		
		regexpStr[index] = "\\d{1}[\\s-:.]\\d{10}";											
		regexpReplaceStr[index] = "*-**********";								// 건강보험번호
		index++;
		
		regexpStr[index] = "\\d{2}[\\s-:.]\\d{3}[\\s-:.]\\d{4}";					
		regexpReplaceStr[index] = "**-***-****";
		index++;
		
		regexpStr[index] = "\\d{4}[\\s-:.]\\d{6}";								
		regexpReplaceStr[index] = "****-******";
		index++;
		
		regexpStr[index] = "\\d{1}[\\s-:.]\\d{4}[\\s-:.]\\d{4}";					
		regexpReplaceStr[index] = "*-****-****";
		index++;
		
		regexpStr[index] = "\\d{3}[\\s-:.]\\d{3}[\\s-:.]\\d{3}";					
		regexpReplaceStr[index] = "***-***-***";
		index++;
		
		regexpStr[index] = "\\d{11}";											
		regexpReplaceStr[index] = "***********";									// 의료기관번호, 계좌번호
		index++;
		
		regexpStr[index] = "\\d{2}[\\s-:.]\\d{8}";								
		regexpReplaceStr[index] = "**-********";									// 군번
		index++;
		
		regexpStr[index] = "\\d{3}[\\s-:.]\\d{3}[\\s-:.]\\d{3}";								
		regexpReplaceStr[index] = "***-***-***";									// 계좌번호
		index++;
		
		regexpStr[index] = "\\d{1}[\\s-:.]\\d{9}";								
		regexpReplaceStr[index] = "*-*********";									// 계좌번호
		index++;
		
		regexpStr[index] = "\\d{3}[\\s-:.]\\d{2}[\\s-:.]\\d{4}";								
		regexpReplaceStr[index] = "***-**-****";									// 계좌번호
		index++;
		
		regexpStr[index] = "\\d{8}[\\s-:.]\\d{2}";								
		regexpReplaceStr[index] = "********-**";									// 계좌번호
		index++;
		
		regexpStr[index] = "\\d{6}[\\s-:.]\\d{4}";								
		regexpReplaceStr[index] = "******-****";
		index++;
		
		regexpStr[index] = "\\d{10}";											
		regexpReplaceStr[index] = "**********";									// 계좌번호
		index++;
		
		regexpStr[index] = "\\d{3}[\\s-:.]\\d{6}";											
		regexpReplaceStr[index] = "***-******";									// 계좌번호
		index++;
		
		regexpStr[index] = "\\d{4}[\\s-:.]\\d{4}";								
		regexpReplaceStr[index] = "****-****";									// 계좌번호
		index++;
		
		regexpStr[index] = "\\d{9}";												
		regexpReplaceStr[index] = "*********";									// 계좌번호
		index++;
		
		regexpStr[index] = "\\d{7}[\\s-:.]\\d{1}";												
		regexpReplaceStr[index] = "*******-*";									// 계좌번호
		index++;
		
		regexpStr[index] = "\\d{3}[\\s-:.]\\d{4}";                              //전화번호								
		regexpReplaceStr[index] = "***-****";
		index++;
		
		regexpStr[index] = "\\d{8}";												
		regexpReplaceStr[index] = "********";									// 여권번호, 계좌번호									
		index++;
		
		regexpStr[index] = "\\d{7}";												
		regexpReplaceStr[index] = "*******";										// 여권번호
		index++;
		
		regexpStr[index] = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";			
		regexpReplaceStr[index] = "***.***.***.***";								//ip
		index++;
		
		regexpStr[index] = "\\d{2}[가-힣]{1}\\d{4}";			
		regexpReplaceStr[index] = "**가****";										//자동차번호
		index++;
		
		/*
		regexpStr[index] = "([a-zA-Z0-9]{1,2}.)([a-zA-Z0-9]{1,2}.)([a-zA-Z0-9]{1,2}.)([a-zA-Z0-9]{1,2})";			
		regexpReplaceStr[index] = "**-**-**-**-**-**";										//MAC주소
		index++;
		*/

		
		index = 0;
		
		regexpNearStr = new String[15][2];
		regexpReplaceNearStr = new String[15];
		
		regexpNearStr[index][0] = "성명";
		regexpNearStr[index][1] = "[가-힣]{2,4}";
		regexpReplaceNearStr[index] = "***";
		index++;
		
		regexpNearStr[index][0] = "이름";
		regexpNearStr[index][1] = "[가-힣]{2,4}";
		regexpReplaceNearStr[index] = "***";
		index++;
		
		regexpNearStr[index][0] = "생년월일";
		regexpNearStr[index][1] = "(\\d{1,4}\\.)(\\d{1,2}\\.)(\\d{1,2})";
		regexpReplaceNearStr[index] = "****.**.**";
		index++;
		
		regexpNearStr[index][0] = "생년월일";
		regexpNearStr[index][1] = "(\\d{6})";
		regexpReplaceNearStr[index] = "******";
		index++;
		
		regexpNearStr[index][0] = "생년월일";
		regexpNearStr[index][1] = "(\\d{1,4}년)\\s*(\\d{1,2}월)\\s*(\\d{1,2}일)";
		regexpReplaceNearStr[index] = "****년**월**일";
		index++;
		
		regexpNearStr[index][0] = "생일";
		regexpNearStr[index][1] = "(\\d{1,2}\\.)\\s*(\\d{1,2})";
		regexpReplaceNearStr[index] = "**.**";
		index++;
		
		regexpNearStr[index][0] = "생일";
		regexpNearStr[index][1] = "(\\d{1,2}월)\\s*(\\d{1,2}일)";
		regexpReplaceNearStr[index] = "**월**일";
		index++;
		
		regexpNearStr[index][0] = "결혼기념일";
		regexpNearStr[index][1] = "(\\d{1,2}\\.)\\s*(\\d{1,2})";
		regexpReplaceNearStr[index] = "**.**";
		index++;
		
		regexpNearStr[index][0] = "결혼기념일";
		regexpNearStr[index][1] = "(\\d{1,2}월)\\s*(\\d{1,2}일)";
		regexpReplaceNearStr[index] = "**월**일";
		index++;		
		
		regexpNearStr[index][0] = "환갑";
		regexpNearStr[index][1] = "(\\d{1,2}\\.)\\s*(\\d{1,2})";
		regexpReplaceNearStr[index] = "**.**";
		index++;
		
		regexpNearStr[index][0] = "환갑";
		regexpNearStr[index][1] = "(\\d{1,2}월)\\s*(\\d{1,2}일)";
		regexpReplaceNearStr[index] = "**월**일";
		index++;
		
		regexpNearStr[index][0] = "백일";
		regexpNearStr[index][1] = "(\\d{1,2}\\.)\\s*(\\d{1,2})";
		regexpReplaceNearStr[index] = "**.**";
		index++;
		
		regexpNearStr[index][0] = "백일";
		regexpNearStr[index][1] = "(\\d{1,2}월)\\s*(\\d{1,2}일)";
		regexpReplaceNearStr[index] = "**월**일";
		index++;		
		
		regexpNearStr[index][0] = "취득";
		regexpNearStr[index][1] = "(\\d{1,2}\\.)\\s*(\\d{1,2})";
		regexpReplaceNearStr[index] = "**.**";
		index++;
		
		regexpNearStr[index][0] = "취득";
		regexpNearStr[index][1] = "(\\d{1,2}월)\\s*(\\d{1,2}일)";
		regexpReplaceNearStr[index] = "**월**일";
		index++;			
	}
	
	public String replaceNumberInfo(String text) {
		// 전화번호, 주민번호 등등...
		for(int i = 0; i < regexpStr.length; i++){
			Pattern regex = Pattern.compile(regexpStr[i], Pattern.MULTILINE);
			Matcher matcher = regex.matcher(text);
			while(matcher.find()){
				text = replace(text, matcher.group(), regexpReplaceStr[i]);
			}
		}
	
		return text;
	}
	
	public String replaceNearInfo(String text) {
		int nearLength = 20;
		
		// 전화번호, 주민번호 등등...
		for(int i = 0; i < regexpNearStr.length; i++){
			
			int fromIndex = 0;
			String target = "";
			String result = "";
			
			do {
				target = "";
				result = "";
				
				fromIndex = text.indexOf(regexpNearStr[i][0], fromIndex-1);
				
				if(fromIndex > -1){
					
					System.out.println(i);
					System.out.println(fromIndex);
					
					if( text.length() >= fromIndex+nearLength ){
						target = text.substring(fromIndex, fromIndex+nearLength);
					}else{
						target = text.substring(fromIndex);
					}
					
					Pattern regex = Pattern.compile(regexpNearStr[i][1], Pattern.MULTILINE);
					Matcher matcher = regex.matcher(target);		
					
					while(matcher.find()){
						result = replace(target, matcher.group(), regexpReplaceNearStr[i]);
					}
					
					System.out.println(target);
					System.out.println(result);
					
					
					if(!"".equals(result) ){
						text = replace(text, target, result);
					}
					
					System.out.println(text);
					
					fromIndex += regexpNearStr[i][0].length();
				}
				
				
			}while(fromIndex > -1);
			
		}
	
		return text;
	}
	
	
	
	public String replaceEmailInfo(String text) {
		Pattern regex = Pattern.compile("([\\.0-9a-zA-Z_-]+)@([0-9a-zA-Z_-]+).([0-9a-zA-Z_-]+)", Pattern.MULTILINE);
		Matcher matcher = regex.matcher(text);
		while(matcher.find()){
			int posAt = matcher.group().indexOf("@");
			int posDot = matcher.group().indexOf(".", posAt);
			
			String tmpID = "";
			String tmpDomain1 = "";
			String tmpDomain2 = "";
			
			for(int i=0 ; i < posAt ; i++){
				tmpID += "*";
			}
			
			for(int i=posAt+1 ; i < posDot ; i++){
				tmpDomain1 += "*";
			}
			
			for(int i=posDot+1 ; i < matcher.group().length() ; i++){
				tmpDomain2 += "*";
			}
			
			text = replace(text, matcher.group(), tmpID+"@"+tmpDomain1+"."+tmpDomain2);
		}
		
		return text;
	}
	
	public String replaceTwitterID(String text) {
		Pattern regex = Pattern.compile("@[\\d\\w_-]+\\s+", Pattern.MULTILINE);
		Matcher matcher = regex.matcher(text);
		int chk = text.length();
		while(matcher.find()){
			if(text.length() > (chk * 2)){
				break;
			}
			text = replace(text, matcher.group(), "@"+replaceMD5(matcher.group()));
		}
		
		return text;
	}
	
	public String removeTwitterID(String text) {
		//Pattern regex = Pattern.compile("@[\\d\\w_-]+[\\s+|,|:]", Pattern.MULTILINE);
		Pattern regex = Pattern.compile("(?<=@)((\\w+))", Pattern.MULTILINE);
		Matcher matcher = regex.matcher(text);
		int chk = text.length();
		while(matcher.find()){
			if(text.length() > (chk * 2)){
				break;
			}
			String target = "@"+matcher.group()+" ";
			String target2 = "@"+matcher.group()+":";
			String target3 = "@"+matcher.group()+",";
			text = text.replace(target, "");
			text = text.replace(target2, "");
			text = text.replace(target3, "");
			
		}
		
		return text;
	}
	
	public String removeUrl(String text) {
    	String pattern = "(http|https|ftp)://[^\\s^\\.]+(\\.[^\\s^\\.^\"^\']+)*";
		Pattern regex = Pattern.compile(pattern, Pattern.MULTILINE);
		Matcher matcher = regex.matcher(text);
		int chk = text.length();
		while(matcher.find()){
			if(text.length() > (chk * 2)){
				break;
			}
			text = replace(text, matcher.group(), "");
		}
		
		return text;
    }
	
	
	
	public String removeHashTag(String text) {
		Pattern regex = Pattern.compile("#[\\d\\wㄱ-힣_-]+[\\s+|,]", Pattern.MULTILINE);
		Matcher matcher = regex.matcher(text);
		int chk = text.length();
		while(matcher.find()){
			if(text.length() > (chk * 2)){
				break;
			}
			text = replace(text, matcher.group(), " ");
		}
		
		return text;
	}

	/**
	 * replacePInfo
	 * 전화번호, 아이피 주소등의 개인정보를 *처리한다.
	 * 이메일주소를 *처리한다
	 * @param text
	 * @return 개인정보 처리된 text
	 */
	public String replacePInfo(String text) {
		text = " "+text+" ";
		text = replaceNumberInfo(text);
		text = replaceEmailInfo(text);
		text = replaceAddressInfo(text);

		return text;
	}
	
	/**
	 * replacePInfoForTwitter
	 * 전화번호, 아이피 주소등의 개인정보를 *처리한다.
	 * 이메일주소를 *처리한다
	 * 트위터 아이디를 암화화(MD5) 한다.
	 * 
	 * @param text
	 * @return 개인정보 처리된 text
	 */
	public String replacePInfoForTwitter(String text) {
		text = " "+text+" ";
		text = replaceNumberInfo(text);
		text = replaceEmailInfo(text);
		text = replaceTwitterID(text);
		text = replaceAddressInfo(text);
		return text;
	}
	
	private String replace(String text, String searchString, String replacement) {
        return replace(text, searchString, replacement, -1);
    }
    
	private String replace(String text, String searchString, String replacement, int max) {
        if (isEmpty(text) || isEmpty(searchString) || replacement == null || max == 0) {
            return text;
        }
        int start = 0;
        int end = text.indexOf(searchString, start);
        if (end == -1) {
            return text;
        }
        int replLength = searchString.length();
        int increase = replacement.length() - replLength;
        increase = (increase < 0 ? 0 : increase);
        increase *= (max < 0 ? 16 : (max > 64 ? 64 : max));
        StringBuffer buf = new StringBuffer(text.length() + increase);
        while (end != -1) {
            buf.append(text.substring(start, end)).append(replacement);
            start = end + replLength;
            if (--max == 0) {
                break;
            }
            end = text.indexOf(searchString, start);
        }
        buf.append(text.substring(start));
        return buf.toString();
    }
    
	private boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

	private String replaceMD5(String str){
		String MD5 = ""; 
		try{
			MessageDigest md = MessageDigest.getInstance("MD5"); 
			md.update(str.getBytes()); 
			byte byteData[] = md.digest();
			StringBuffer sb = new StringBuffer(); 
			for(int i = 0 ; i < byteData.length ; i++){
				sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
			}
			MD5 = sb.toString();
		}catch(NoSuchAlgorithmException e){
			e.printStackTrace(); 
			MD5 = null; 
		}
		return MD5;
	}
}
