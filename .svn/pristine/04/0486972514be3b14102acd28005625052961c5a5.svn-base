package risk.mail;

import java.io.*;
import java.util.Properties;

import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;

import risk.util.ConfigUtil;
import risk.util.Log;

public class GoogleSmtp{
	
	private Log log;
	private Properties props = new Properties();
	private Session session;
	private MimeMessage msg;
	
    public GoogleSmtp(){
		log = new Log();
		setSmtpHost();
    }
    
	public void setSmtpHost(){
		ConfigUtil cu = new ConfigUtil();
		
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.debug","ture");  			//javax mail 을 디버깅 모드로 설정
        props.put("mail.smtp.auth", "true"); 		//smtp 인증 을 설정
        props.put("mail.smtp.port", "465"); 		//gmail smtp 서비스 포트 설정
        props.put("type", "javax.mail.Session"); 	// 해도 그만 안해도 그만
        props.put("auth", "Application");			// 해도 그만 안해도 그만
        
        // 로그인할때 Transport Layer Security (TLS) 를 사용할것인지 설정 gmail 에선  tls가 필수가 아니므로 해도그만 안해도 그만이다.
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.transport.protocol", "smtp"); //프로토콜 설정
        
        // gmail Secure Sockets Layer (SSL) 인증용설정 (필수)
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        Authenticator auth = null;
        //auth = new MyAuthenticator("mail.risl.cj@gmail.com", "dkfdptmdps2025");
        //auth = new MyAuthenticator("mail.report.lliten@gmail.com", "dkfdptmdps2025");
        //auth = new MyAuthenticator("mail.report.llisten@gmail.com", "dkfdptmdps2025");
        //auth = new MyAuthenticator("report.moel@gmail.com", "dkfdptmdps2025");
        //auth = new MyAuthenticator("sk@buzzms.co.kr", "tomorrow2015");
        auth = new MyAuthenticator("bing@buzzms.co.kr", "tomorrow2022");
        
        session = Session.getInstance(props, auth);
        session.setDebug(false); // 위에서 "mail.debug" 로 디버그 설정을 해도 여기서 세션 디버깅을 막으면 보이지  않는다.
        
		msg = new MimeMessage(session);

		log.writeExpt("MAILLOG", "SMTP setting :: smtp.gmail.com");   
	}
	
	
	/**
	*	메일을 발송한다.
	*
	*	@param		toaddr		받는사람 email주소
	*	@param		fromaddr	보내는사람 email주소
	*	@param		subject		제목
	*	@param		body		본문
	*	@param		ishtml		true : html, false : text
	*	@return		true - 성공, false - 실패
	 * @throws UnsupportedEncodingException 
    */
	public boolean sendmessage(String toaddr, String subject, String body, boolean ishtml) throws UnsupportedEncodingException{
    	return sendmessage(toaddr,"", subject,body, ishtml);
    }
	
	
    /**
	*	메일을 발송한다.
	*
	*	@param		toaddr		받는사람 email주소
	*	@param		fromaddr	보내는사람 email주소
	*	@param		subject		제목
	*	@param		body		본문
	*	@param		ishtml		true : html, false : text
	*	@return		true - 성공, false - 실패
     * @throws UnsupportedEncodingException 
    */
    public boolean sendmessage(String toaddr, String fromaddr, String subject, String body, boolean ishtml) throws UnsupportedEncodingException{
    	
    	ConfigUtil cu = new ConfigUtil();
        try
		{
        	/*if(fromaddr != null && !fromaddr.equals("")){
        		InternetAddress from = new InternetAddress(fromaddr);
        		msg.setFrom(from);
        	}*/
        	InternetAddress from = new InternetAddress("bing@buzzms.co.kr", "BING", "UTF-8");
    		msg.setFrom(from);
            InternetAddress[] to = InternetAddress.parse(toaddr);
            Address[] toAddress = to;
            msg.setRecipients(Message.RecipientType.TO, toAddress);
            
            msg.setSubject(subject, "utf-8");

            if(ishtml)
                msg.setContent(body, "text/html;charset=utf-8");
            else
                msg.setContent(body, "text/plain;charset=utf-8");

            log.writeExpt("MAILLOG","mail send to," + toaddr);
            System.out.println(msg);
			Transport.send(msg);

			
			
			log.writeExpt("MAILLOG","send OK," + toaddr);

			return true;
        }
        catch(MessagingException mex)
		{
            mex.printStackTrace();
            log.writeExpt("MAILLOG","Mail send fail," + mex.getMessage());

            return false;
        }
    }
    
    
    /**
  	*	메일을 발송한다.
  	*
  	*	@param		toaddr		받는사람 email주소
  	*	@param		fromaddr	보내는사람 email주소
  	*	@param		subject		제목
  	*	@param		body		본문
  	*	@param		pathImg		첨부이미지 경로
  	*	@param		imgName		첨부파일 이미지
  	*	@param		ishtml		true : html, false : text
  	*	@return		true - 성공, false - 실패
      */
      public boolean sendmessage_img(String toaddr, String fromaddr, String subject, String body, String pathImg, String imgName, boolean ishtml){
      	
      	FileDataSource fds = null;
      	MimeBodyPart mbp1 = new MimeBodyPart();
      	MimeBodyPart mbp2 = new MimeBodyPart();
      	Multipart mp = new MimeMultipart();

      	setSmtpHost();
          try
  		{
          	if(fromaddr != null && !fromaddr.equals("")){
          		System.out.println(fromaddr);
          		InternetAddress from = new InternetAddress(fromaddr);
          		msg.setFrom(from);
          	}

              InternetAddress[] to = InternetAddress.parse(toaddr);
              Address[] toAddress = to;
              msg.setRecipients(Message.RecipientType.TO, toAddress);
              
              msg.setSubject(subject, "utf-8");

              if(ishtml)
              	mbp1.setContent(body, "text/html;charset=utf-8");
              else
              	mbp1.setContent(body, "text/plain;charset=utf-8");
              
              fds = new FileDataSource(pathImg+"/"+imgName);
              mbp2.setDataHandler(new DataHandler(fds));         
              mbp2.setFileName(MimeUtility.encodeText("BING_REPORT_"+fds.getName(), "KSC5601", "B"));
              mbp2.setHeader("Content-ID", "<image>");
              mp.addBodyPart(mbp1);
              mp.addBodyPart(mbp2);
              msg.setContent(mp);
              log.writeExpt("MAILLOG","mail send to," + toaddr);
  			Transport.send(msg);
  			
  			log.writeExpt("MAILLOG","send OK," + toaddr);

  			return true;
          }
          catch(MessagingException mex)
  		{
              mex.printStackTrace();
              log.writeExpt("MAILLOG","Mail send fail," + mex.getMessage());

              return false;
        
  		} catch (UnsupportedEncodingException e) {
          e.printStackTrace();
          log.writeExpt("MAILLOG","Mail send fail," + e.getMessage());
          return false;
		}
      }
    
    /**
	*	메일을 발송한다.
	*
	*	@param		toaddr		받는사람 email주소
	*	@param		fromaddr	보내는사람 email주소
	*	@param		subject		제목
	*	@param		body		본문
	*	@param		fileName	첨부파일 이름
	*	@param		file		첨부할 파일
	*	@param		ishtml		true : html, false : text
	*	@return		true - 성공, false - 실패
    */
    public boolean sendmessage(String toaddr, String fromaddr, String subject, String body, String fileName, File file, boolean ishtml){
    	
    	FileDataSource fds = null;
    	MimeBodyPart mbp1 = new MimeBodyPart();
    	MimeBodyPart mbp2 = new MimeBodyPart();
    	Multipart mp = new MimeMultipart();

    	setSmtpHost();
        try
		{
        	if(fromaddr != null && !fromaddr.equals("")){
        		System.out.println(fromaddr);
        		InternetAddress from = new InternetAddress(fromaddr);
        		msg.setFrom(from);
        	}

            InternetAddress[] to = InternetAddress.parse(toaddr);
            Address[] toAddress = to;
            msg.setRecipients(Message.RecipientType.TO, toAddress);
            
            msg.setSubject(subject, "utf-8");

            if(ishtml)
            	mbp1.setContent(body, "text/html;charset=utf-8");
            else
            	mbp1.setContent(body, "text/plain;charset=utf-8");
            
            fds = new FileDataSource(file);
            mbp2.setDataHandler(new DataHandler(fds));         
            mbp2.setFileName(MimeUtility.encodeText(fds.getName(), "KSC5601", "B"));
            mp.addBodyPart(mbp1);
            mp.addBodyPart(mbp2);
            msg.setContent(mp);
            log.writeExpt("MAILLOG","mail send to," + toaddr);
			Transport.send(msg);
			
			log.writeExpt("MAILLOG","send OK," + toaddr);

			return true;
        }
        catch(MessagingException mex)
		{
            mex.printStackTrace();
            log.writeExpt("MAILLOG","Mail send fail," + mex.getMessage());

            return false;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            log.writeExpt("MAILLOG","Mail send fail," + e.getMessage());
            return false;
		}
    }
}