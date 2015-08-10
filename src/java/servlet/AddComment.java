package servlet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jclass.CommentClass;
import jclass.Database;
import jclass.Result;

/**
 *
 * @author BurakKaan
 */
@WebServlet(urlPatterns = {"/AddComment"})
public class AddComment extends HttpServlet {
    private boolean j;
    Database d=new Database();
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
            throws ServletException, IOException, SQLException {
        response.setContentType("application/json");
       CommentClass newComment=new CommentClass();
       Gson g=new Gson();
       PrintWriter out=response.getWriter();
       
       j=d.getCookie(request);
       
        if(j){
        d.getUser(d.nameforEdit);
        newComment.setUserId(d.userId);
        if(request.getParameter("flatid").equals("")){
        out.print(g.toJson(Result.FAILURE_PARAM_WRONG.setContent("Daire girmediniz")));
        }
        else if(request.getParameter("comment").equals("")){
        out.print(g.toJson(Result.FAILURE_PARAM_WRONG.setContent("Yorum girmediniz")));
        }
        else{
        int flatno=Integer.parseInt(request.getParameter("flatid"));
        newComment.setFlatId(flatno);
        newComment.setContent(request.getParameter("comment"));
        out.print(g.toJson(d.addComment(newComment)));
        }
        }
  else
        {
        out.print(g.toJson(Result.FAILURE_COOKIE_CREATION.setContent("token bulunamadi")));
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(AddComment.class.getName()).log(Level.SEVERE, null, ex);
        }
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

}
