package servlet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jclass.Database;
import jclass.Result;
import jclass.UserClass;


/**
 *
 * @author BurakKaan
 */
@WebServlet(urlPatterns = {"/Signup"})
public class Signup extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Gson g=new Gson();
        try{
        
        UserClass newUser=new UserClass(); 
        if((request.getParameter("name").equals(""))){
        out.print(g.toJson(Result.FAILURE_PARAM_WRONG.setContent("isim giriniz!")));}
        else if(request.getParameter("surname").equals("")){
        out.print(g.toJson(Result.FAILURE_PARAM_WRONG.setContent("soyad giriniz")));}
        else if(request.getParameter("email").equals("")){
        out.print(g.toJson(Result.FAILURE_PARAM_WRONG.setContent("email giriniz")));}
        else if(request.getParameter("username").equals("")){
        out.print(g.toJson(Result.FAILURE_PARAM_WRONG.setContent("kullanici adi giriniz")));}
        else if(request.getParameter("password").equals("")){
        out.print(g.toJson(Result.FAILURE_PARAM_WRONG.setContent("sifre giriniz")));}
        else if(request.getParameter("birthday").equals("")){
        out.print(g.toJson(Result.FAILURE_PARAM_WRONG.setContent("dogum tarihi giriniz")));}
         else if(request.getParameter("sex").equals("")){
        out.print(g.toJson(Result.FAILURE_PARAM_WRONG.setContent("cinsiyet giriniz")));}
        else if(request.getParameter("url").equals("")){
        out.print(g.toJson(Result.FAILURE_PARAM_WRONG.setContent("resim giriniz")));}
        else{
        newUser.setName(request.getParameter("name"));          
        newUser.setSurname(request.getParameter("surname"));        
        newUser.setEmail(request.getParameter("email"));        
        newUser.setUsername(request.getParameter("username"));        
        newUser.setPassword(request.getParameter("password")); 
        newUser.setBirthday(request.getParameter("birthday"));       
        newUser.setSex(request.getParameter("sex"));
        newUser.setImage(request.getParameter("url"));
            if(Database.checkUserbyUsername(newUser.getUsername())){
            out.print(g.toJson(Result.FAILURE_DB_DUPLICATE_ENTRY.setContent("bu kullanici adi alinmis")));
            }
            else if(Database.checkUserbyEmail(newUser.getEmail())){
            out.print(g.toJson(Result.FAILURE_DB_DUPLICATE_ENTRY.setContent("bu email ile daha once kaydolunmus")));
            }
            else{
            Result result = Database.signup(newUser);
            Gson gs = new Gson();
            out.print(gs.toJson(result));
            }
        }
        }
        catch(Exception e){
            out.print(g.toJson(e.getMessage()));   
        }
        }
        

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
    }
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>


 /*private Result connectDB(UserClass user){
        try{
         Class.forName("com.mysql.jdbc.Driver");
         Connection  con=DriverManager.getConnection
                     ("jdbc:mysql://localhost:3306/stajtubitak","root","root");
        PreparedStatement ps=con.prepareStatement
                  ("insert into user(Name, Surname, Email, Username, Password,Birthday,Gender,Image) values(?,?,?,?,?,?,?,?)");
        
        ps.setString(1, user.getName());
        ps.setString(2, user.getSurname());
        ps.setString(3, user.getEmail());
        ps.setString(4, user.getUsername());
        ps.setString(5, user.getPassword());
        ps.setString(6, user.getBirthday());
        ps.setString(8, user.getImage());
        ps.setString(7, user.getSex());

        int i=ps.executeUpdate();
        
          if(i>0)
          {
              Result success = Result.SUCCESS.setContent(user);
              return success;
          }
        }
        catch(Exception se)
        {
            se.printStackTrace();
            return Result.FAILURE_DB.setContent(se.getMessage());
        }finally{
            //con.
        }
	return Result.FAILURE_DB;
      }*/
}

