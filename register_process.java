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
import java.sql.*;
import java.util.*;



public class register_process extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Register</title>");
            out.println("<link rel='stylesheet' href='style1.css' type='text/css'>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div id='header'>\n" +
"            <img class='logo' src='images/logo.png' alt='logo'>\n" +
"            <h1 class='name'><span style='color:#2cfc03;'>P</span>ark <span style='color:#fce303;'>S</span>mart</h1>\n" +
"            \n" +
"        </div>");
            out.println("<div id='container'>");
            String username=request.getParameter("username");   
    String useremail=request.getParameter("useremail");
    String userno=request.getParameter("userno"); 
    String buildingname=request.getParameter("buildingname"); 
    String address=request.getParameter("address"); 
    String state=request.getParameter("state");
    String password=request.getParameter("password");
    
    try{
       Connection con;
        
        Class.forName("com.mysql.cj.jdbc.Driver");
                System.out.println("Driver established");
        con=DriverManager.getConnection("jdbc:Mysql://localhost:3306/parkingmanagement","root","priyank8141");      
                System.out.println("connection established");
        con.setAutoCommit(false);
        
        
         PreparedStatement st = con.prepareStatement("select * from register_user where name = ? ");
    st.setString(1, username);
    ResultSet r1=st.executeQuery();
    if(r1.next()){
         out.println("<p style='font-size:17px;'>username already exist in database,it must be unique!</p>");
    }
    else{
     
         PreparedStatement stmt=con.prepareStatement("INSERT INTO register_user(name,email,mob_no,building_name,address,state,password) VALUES(?,?,?,?,?,?,?);");
         
         
         stmt.setString(1,username);
         stmt.setString(2,useremail);
         stmt.setString(3,userno);
         stmt.setString(4,buildingname);
         stmt.setString(5,address);
         stmt.setString(6,state);
         stmt.setString(7,password);
         stmt.execute();
         stmt.close();
         
          CallableStatement stmt0=con.prepareCall("call createunpark(?)");
         stmt0.setString(1,username);
         stmt0.execute();
         stmt0.close();
         
         
         
         CallableStatement stmt1=con.prepareCall("call createpark(?)");
         stmt1.setString(1,buildingname);
         stmt1.execute();
         stmt1.close();
         
         con.close();
         System.out.println("connection closed");
         out.println("<p style='font-size:17px;'> Successfully Register..</p>");
     }   
    }
    catch(Exception e){
        System.out.println(e);
    }


            out.println("<br>\n" +
"<p style='font-size:20px;'><a href='register_form'>Register</a> / <a href='login_form'>Login</a></p>");
            out.println("</div>");      
            
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
