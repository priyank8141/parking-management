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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.*;
import javax.servlet.http.*;

import java.io.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.servlet.*;

public class park extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
   HttpSession session=request.getSession();
     String vehicle_no=request.getParameter("vehicle_no");
     System.out.println(vehicle_no);
     String vehicle_type=request.getParameter("vehicle_type");
     System.out.println(vehicle_type);
     String email=request.getParameter("email");
     System.out.println(email);
     
     DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss"); 
      LocalDateTime time = LocalDateTime.now();
     String allot_time=dtf.format(time);
     System.out.println(allot_time);
    
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
     
                   
     CallableStatement stmt3=con.prepareCall("call park(?,?,?,?,?,?)");
     
                    stmt3.setString(1,building_name);
                    stmt3.setString(2,vehicle_no);
                    stmt3.setString(3,vehicle_type);
                    stmt3.setString(4,email);
                    stmt3.setString(5,allot_time);
                    stmt3.setString(6,slot_occupied);
                     System.out.println(building_name); 
                    stmt3.execute();
                   System.out.println(building_name); 
                   stmt3.close(); 
                   response.sendRedirect("profile");
                    
            con.close();
            System.out.println("connection closed");
 }
catch(Exception e){
    System.out.println(e);
}







   

   // Get system properties object
        Properties props = System.getProperties();

   // Setup mail server
        String host = "smtp.gmail.com";
        String from = "parksmart92@gmail.com";
        String pass = "smartpark@92";
        String to = email;
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");     //try 465, 25, 587
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.timeout", "25000");

   // Get the default Session object.
        Session mailsession = Session.getDefaultInstance(props);
        
        System.out.println(to);

   try{
      // Create a default MimeMessage object.
        MimeMessage message = new MimeMessage(mailsession);
      
        message.setFrom(new InternetAddress(from));  

        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

        // Set Subject: header field
        message.setSubject("parksmart");
      
        // Now set the actual message
         System.out.println(building_name); 
        String msg="Welcome to "+building_name+"\n"+"Details "+vehicle_no+" ("+vehicle_type+" ) has been \n parked at "+slot_occupied+".";
        message.setText(msg);
      //  if you want to send HTML page use  setContent(msg, "text/html");
      // message.setContent("<h1>This is actual message</h1>", "text/html" );
        // Send message
        Transport transport = mailsession.getTransport("smtp");
        transport.connect(host, from, pass);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
        System.out.println("Sent message successfully....");
    }catch (MessagingException mex) {
      mex.printStackTrace();
      System.out.println("Error: unable to send message....");
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
