package servlet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.json.Json;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jclass.Database;
import jclass.FlatClass;
import jclass.Result;

/**
 *
 * @author BurakKaan
 */
@WebServlet(urlPatterns = {"/AddFlat"})
public class AddFlat extends HttpServlet {
    String name,value;
    boolean t;
    Result a;
    
    
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
        FlatClass newFlat=new FlatClass();
        Database d=new Database();
        PrintWriter out=response.getWriter();
        Gson g=new Gson(); 
        
       try{
        t=d.getCookie(request);
        if(t)
        {
            if(request.getParameter("country").equals("")){
            out.print(g.toJson(Result.FAILURE_PARAM_WRONG.setContent("ulke giriniz")));
            }
            else if(request.getParameter("city").equals("")){
            out.print(g.toJson(Result.FAILURE_PARAM_WRONG.setContent("sehir giriniz")));
            }
            else if(request.getParameter("state").equals("")){
            out.print(g.toJson(Result.FAILURE_PARAM_WRONG.setContent("ilce giriniz")));
            }
            else if(request.getParameter("street").equals("")){
            out.print(g.toJson(Result.FAILURE_PARAM_WRONG.setContent("sokak giriniz")));
            }
            else if (request.getParameter("buildingno").equals("")){
            out.print(g.toJson(Result.FAILURE_PARAM_WRONG.setContent("apartman no giriniz")));
            }
            else if(request.getParameter("flatno").equals("")){
            out.print(g.toJson(Result.FAILURE_PARAM_WRONG.setContent("daire no giriniz")));
            }
            else{
        newFlat.setCountry(request.getParameter("country"));
        newFlat.setCity(request.getParameter("city"));
        newFlat.setState(request.getParameter("state"));
        newFlat.setStreet(request.getParameter("street"));
        newFlat.setBuildingName(request.getParameter("buildingname"));
        newFlat.setBuildingNo(request.getParameter("buildingno"));
        newFlat.setFlatNo(request.getParameter("flatno"));

        if (d.checkFlat(newFlat.getCountry(),newFlat.getCity(),newFlat.getState(),
                newFlat.getStreet(),newFlat.getBuildingName(),newFlat.getBuildingNo(),
                newFlat.getFlatNo())){
                out.print(g.toJson(Result.FAILURE_DB_DUPLICATE_ENTRY.setContent("Bu daire daha once eklenmis")));   
        }
        else{
        out.print(g.toJson(d.addFlat(newFlat)));
        }
        }
        }
        else
        {
        out.print(g.toJson(Result.FAILURE_COOKIE_CREATION.setContent("token bulunamadi")));
        }
        }
       catch(Exception e){
           out.print(g.toJson(Result.FAILURE_DB.setContent(e.getMessage())));
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
        //processRequest(request, response);
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
