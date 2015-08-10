package servlet;


import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import jclass.SessionIdentifierGenerator;

/**
 * Created by wora on 01.02.2015.
 */public class CookiesServlet extends javax.servlet.http.HttpServlet {
public String a,s,t;
    
public void createCookie (HttpServletResponse response,String user) {
    SessionIdentifierGenerator token=new SessionIdentifierGenerator();
    a=token.SessionID();
    final Cookie cookie = new Cookie("token", a);
    cookie.setMaxAge(3000);
    response.addCookie(cookie);
   }

   public boolean checkCookie(HttpServletRequest request,String token){
     
     final Cookie[] cookies=request.getCookies();
     if(cookies==null)
         return false;
     else{
     for(Cookie cookie : cookies){
            String name = cookie.getName();
            String value = cookie.getValue();
            if(value.equals(token)){
            s=value;
                return true;
            }}
}
    return false;


   }

}