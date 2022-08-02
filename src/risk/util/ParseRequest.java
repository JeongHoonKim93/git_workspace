/**
========================================================
주 시 스 템 : RSN
서브 시스템 : Request Parameter 값 체크 및 추출
프로그램 ID : ParseRequeset.class
프로그램 명 : ParseRequeset
프로그램개요	: Request객체에서 파라미터 추출한다. 값이 없는 경우 ""이나 0으로 리턴한다.
작 성 자 : 윤석준
작 성 일 : 2006. 4. 12
========================================================
수정자/수정일:
수정사유/내역:
========================================================
 */

package risk.util;

import java.util.Enumeration;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class ParseRequest {

  ConfigUtil cu = new ConfigUtil();	
  HttpServletRequest  req = null;
  MultipartRequest  req2 = null;
  String sCharSet = cu.getConfig("ENCODING");

  public ParseRequest() {

  }


  /**
   * 생성자
   * Request 객체에 인코딩 설정
   * @param req
   */
  public ParseRequest( HttpServletRequest req ) {

    try{        	
      this.req = req;
      this.req.setCharacterEncoding(sCharSet);        
    } catch (Exception ex) {
      System.out.println(ex.toString());
    }
  }

  public ParseRequest( HttpServletRequest req, String tempPath, int maxSize) {
    try {          
      this.req2 = new MultipartRequest(req, tempPath, maxSize, "UTF-8", new DefaultFileRenamePolicy());
    } catch (Exception ex) {
      System.out.println(ex.toString());
    }
  }


  /**
   * HttpServletRequest의 모든 파라미터 내용을 Vector 로 전달
   * @return
   */
  public Vector showParams() {
    Vector vcParam = new Vector();
    Enumeration em = req.getParameterNames();
    for ( int i = 0 ;em.hasMoreElements(); i++) {
      String pname = (String)em.nextElement();
      vcParam.add(i, "[" + pname + "] : [" + req.getParameter(pname) + "] ");
    }
    return vcParam;
  }

  public void printParams() {
    Enumeration em = null;
    if(req != null) {
      em = req.getParameterNames();

      System.out.println("<-----------------------Param List start----------------------->");
      for ( int i = 0 ;em.hasMoreElements(); i++) {
        String pname = (String)em.nextElement();
        System.out.println("[" + pname + "] : [" + req.getParameter(pname) + "] ");
      }
      System.out.println("<-----------------------Param List end----------------------->");


    } else {
      em = req2.getParameterNames();


      System.out.println("<-----------------------Param List start----------------------->");
      for ( int i = 0 ;em.hasMoreElements(); i++) {
        String pname = (String)em.nextElement();
        System.out.println("[" + pname + "] : [" + req2.getParameter(pname) + "] ");
      }
      System.out.println("<-----------------------Param List end----------------------->");


    }
    /*Enumeration em = req.getParameterNames();
    System.out.println("<-----------------------Param List start----------------------->");
    for ( int i = 0 ;em.hasMoreElements(); i++) {
      String pname = (String)em.nextElement();
      System.out.println("[" + pname + "] : [" + req.getParameter(pname) + "] ");
    }
    System.out.println("<-----------------------Param List end----------------------->");*/
  }

  /**
   * String 파라미터 값 가져오기
   * @param name
   * @return
   */
  public String getString(String name) {
    if(req != null){
      return req.getParameter(name) == null || req.getParameter(name).length() == 0 ? "" : req.getParameter(name).trim();
    }else{
      return req2.getParameter(name) == null || req2.getParameter(name).length() == 0 ? "" : req2.getParameter(name).trim();
    }
  }

  /**
   * String 파라미터 값 가져오기 파라미터 없는 경우 rtnStr 값 리턴
   * @param name
   * @param rtnStr
   * @return
   */
  public String getString(String name, String rtnStr) {
    if(req != null){
      return req.getParameter(name) == null || req.getParameter(name).length() == 0 ? rtnStr : req.getParameter(name).trim();
    }else{
      return req2.getParameter(name) == null || req2.getParameter(name).length() == 0 ? rtnStr : req2.getParameter(name).trim();
    }
  }


  /**
   * Int 파라미터 값 가져오기
   * @param name
   * @return
   */
  public int getInt(String name) {
    if(req != null){
      return req.getParameter(name) == null ? 0 : Integer.parseInt(req.getParameter(name).trim());
    }else{
      return req2.getParameter(name) == null ? 0 : Integer.parseInt(req2.getParameter(name).trim());
    }
  }

  /**
   * Int 파라미터 값 가져오기
   * 파라미터 없는 경우 rtnInt 값 리턴
   * @param name
   * @param rtnInt
   * @return
   */
  public int getInt(String name, int rtnInt) {
    if(req != null){
      return req.getParameter(name) == null ? rtnInt : Integer.parseInt(req.getParameter(name).trim());
    }else{
      return req2.getParameter(name) == null ? rtnInt : Integer.parseInt(req2.getParameter(name).trim());
    }
  }


  /**
   * Long 파라미터 값 가져오기
   * @param name
   * @return
   */
  public long getLong(String name) {
    if(req != null){
      return req.getParameter(name) == null ? 0 : Long.parseLong(req.getParameter(name).trim());
    }else{
      return req2.getParameter(name) == null ? 0 : Long.parseLong(req2.getParameter(name).trim());
    }
  }

  /**
   * Long 파라미터 값 가져오기
   * 파라미터 없는 경우 rtnLong 값 리턴
   * @param name
   * @param rtnLong
   * @return
   */
  public long getLong(String name, long rtnLong) {
    if(req != null){
      return req.getParameter(name) == null ? rtnLong : Long.parseLong(req.getParameter(name).trim());
    }else{
      return req2.getParameter(name) == null ? rtnLong : Long.parseLong(req2.getParameter(name).trim());
    }
  }

  /**
   * String[] 파라미터 값 가져오기
   * @param name
   * @return
   */
  public String[] getStringArr(String name) {
    String[] rtnStr = null;
    
    if(req != null){
      return req.getParameter(name) == null ? rtnStr : req.getParameterValues(name);
    }else{
      return req2.getParameter(name) == null ? rtnStr : req2.getParameterValues(name);
    }
  }

  // 기존꺼에서 추가~
  public String getOriginalFileName(String name){

    if(req2 != null){
      return req2.getOriginalFileName(name) == null ? "" : req2.getOriginalFileName(name).trim();
    }else{
      return "";
    }

  }
  public String getOriginalFileName(String name, String rtnStr){
    if(req2 != null){
      return req2.getOriginalFileName(name) == null ? rtnStr : req2.getOriginalFileName(name).trim();
    }else{
      return rtnStr;
    }
  }

  public String getFilesystemName(String name){

    if(req2 != null){
      return req2.getOriginalFileName(name) == null ? "" : req2.getFilesystemName(name).trim();
    }else{
      return "";
    }

  }
  public String getFilesystemName(String name, String rtnStr){

    if(req2 != null){
      return req2.getOriginalFileName(name) == null ? rtnStr : req2.getFilesystemName(name).trim();
    }else{
      return rtnStr;
    }

  }
}