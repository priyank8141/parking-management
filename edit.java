/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.sql.*;
import javax.servlet.http.*;
/**
 *
 * @author ombharti
 */
@WebServlet(name = "edit", urlPatterns = {"/edit"})
public class edit extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
       
    int park_ID=Integer.parseInt(request.getParameter("park_ID"));
     System.out.println(park_ID);
     String vehicle_no=request.getParameter("vehicle_no");
     System.out.println(vehicle_no);
     String vehicle_type=request.getParameter("vehicle_type");
     System.out.println(vehicle_type);
     String email=request.getParameter("email");
     System.out.println(email);
     HttpSession session=request.getSession();
     String slot_occupied=request.getParameter("slot_occupied");
     System.out.println(slot_occupied);
     String building_name=(String)session.getAttribute("building_name");    
         

     
try{
             Connection con;
        
        Class.forName("com.mysql.cj.jdbc.Driver");
                System.out.println("Driver established");
        con=DriverManager.getConnection("jdbc:Mysql://localhost:3306/parkingmanagement","root","priyank8141");      
                System.out.println("connection established");
  
     System.out.println(building_name);           
     
     CallableStatement stmt5=con.prepareCall("call updat(?,?,?,?,?,?)");
     
                   stmt5.setString(1,building_name);
                     
                    stmt5.setInt(2,park_ID);
                    
                     stmt5.setString(3,vehicle_no);
                    
                    stmt5.setString(4,vehicle_type);
                    
                    stmt5.setString(5,email);
                    
                   
                    
                    stmt5.setString(6,slot_occupied);
                    
                    stmt5.execute();
                    
                    
                    stmt5.close(); 
                   response.sendRedirect("profile");
                   con.close();
                   System.out.println("connection closed");
}
catch(Exception e){
   
    System.out.println(e);
 
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
        processRequest(request, response);
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
