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
import java.util.*;
import java.sql.*;
import javax.servlet.http.*;



public class login_process extends HttpServlet {

   
     
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>login_process</title>");            
            out.println("</head>");
            out.println("<body>");
            
           




            
            String uname=request.getParameter("username");   
            String pword=request.getParameter("password");
            
            
    try{
       Connection con;
        
        Class.forName("com.mysql.cj.jdbc.Driver");
                System.out.println("Driver established");
        con=DriverManager.getConnection("jdbc:Mysql://localhost:3306/parkingmanagement","root","priyank8141");      
                System.out.println("connection established");
    
    PreparedStatement stmt=con.prepareStatement("select name,password from register_user where name=? and password=? ");
    stmt.setString(1,uname);
    stmt.setString(2, pword);
    
    ResultSet rs=stmt.executeQuery();
    if(rs.next()){
     out.println("<p>loggedin</p>");
      HttpSession session=request.getSession();
     session.setAttribute("user",uname);
     session.setAttribute("psswd",pword);
     response.sendRedirect("profile");
    }
    else{
        response.sendRedirect("invalid_login_form");  
    }
    stmt.close();
    con.close();
    System.out.println("connection closed");
   }
    catch(Exception e){
        System.out.println(e);
     }
            
            
            
            
            
            out.println("</body>");
            out.println("</html>");
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
