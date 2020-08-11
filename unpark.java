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
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.sql.*;
import javax.servlet.http.*;
import java.io.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.servlet.*;


@WebServlet(name = "unpark", urlPatterns = {"/unpark"})
public class unpark extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
         int park_ID=Integer.parseInt(request.getParameter("park_ID"));
     System.out.println(park_ID);
      HttpSession session=request.getSession();
     String username=(String)session.getAttribute("user");
     String vehicle_no = null;
     String vehicle_type=null;
     String email=null;
     String allot_time=null;
     String slot_occupied=null;
     String building_name=(String)session.getAttribute("building_name");    
     System.out.println(building_name); 
    
     DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss"); 
      LocalDateTime time = LocalDateTime.now();
     String unallot_time=dtf.format(time);
     System.out.println(unallot_time);
     
     SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
     Date d1 = null;
     Date d2 = null;
     
    DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/YYYY");  
    LocalDateTime now = LocalDateTime.now();  
     String date=df.format(now);
     System.out.println(date);
     
     String min=null;
     String amount=null;
    try{
             Connection con;
        
        Class.forName("com.mysql.cj.jdbc.Driver");
                System.out.println("Driver established");
        con=DriverManager.getConnection("jdbc:Mysql://localhost:3306/parkingmanagement","root","priyank8141");      
                System.out.println("connection established");
     
         CallableStatement stmt6=con.prepareCall("call selectid(?,?)");
                      stmt6.setString(1,building_name);
                     
                    stmt6.setInt(2,park_ID);
                     ResultSet rs=stmt6.executeQuery();
                     while(rs.next()){
                         vehicle_no=rs.getString("vehicle_no");
                         System.out.println(vehicle_no);
                         vehicle_type=rs.getString("vehicle_type");
                         System.out.println(vehicle_type);
                         email=rs.getString("email");
                         System.out.println(email);
                         allot_time=rs.getString("allot_time");
                         System.out.println(allot_time);
                     }
                     
                    
                     
                d1 = format.parse(unallot_time);
		d2 = format.parse(allot_time);
                long diff = d1.getTime() - d2.getTime();
                long sec=diff/1000;
                long minutes=sec/60;
                System.out.println(minutes); 
                 min=String.valueOf(minutes);
                
                double amt=minutes*0.3;
                int i=(int)amt; 
                amount=String.valueOf(i);
                    
       CallableStatement stmt7=con.prepareCall("call unpark(?,?,?,?,?,?,?,?,?,?)");
                stmt7.setString(1,username);
                stmt7.setString(2,date);
                stmt7.setInt(3,park_ID);
                stmt7.setString(4,vehicle_no);
                stmt7.setString(5,vehicle_type);
                stmt7.setString(6,email);
                stmt7.setString(7,allot_time);
                stmt7.setString(8,unallot_time);
                stmt7.setString(9,min);
                stmt7.setString(10,amount);
                stmt7.execute();
                stmt7.close();
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>unpark</title>");            
            out.println("<link href='style1.css' type='text/css' rel='stylesheet'>");
            out.println("</head>");
            out.println("<body>");
            out.println("\n" +
"        <div id='header'>\n" +
"            <img class='logo' src='images/logo.png' alt='logo'>\n" +
"            <h1 class='name'><span style='color:#2cfc03;'>P</span>ark <span style='color:#fce303;'>S</span>mart</h1>\n" +
"            \n" +
"        </div>");
            out.println("\n" +
"        <div id='container'>\n" +
"            \n" +
"              <table style='margin-left:10px;'>  \n" +
"                   <h3 style='text-align:center;'>RECEIPT</h3>\n" +
"                   <tr><td><b>Date </b></td><td>:</td><td>"+date+"</td></tr>\n" +
"                   <tr><td><b>ID </b></td><td>:</td><td>"+park_ID+"</td></tr>\n" +
"                   <tr><td><b>Vehicle No </b></td><td>:</td><td>"+ vehicle_no +"</td></tr>\n" +
"                   <tr><td><b>Vehicle Type </b></td><td>:</td><td>"+vehicle_type+"</td></tr>\n" +
"                   <tr><td><b>Email </b></td><td>:</td><td>"+ email +"</td></tr>\n" +
"                   <tr><td><b>Allot Time </b></td><td>:</td><td>"+ allot_time +"</td></tr>\n" +
"                   <tr><td><b>Unallot Time </b></td><td>:</td><td>"+ unallot_time+"</td></tr>\n" +
"                   <tr><td><b>Charges per hour </b></td><td>:</td><td>20 Rs</td></tr>\n" +
"                   <tr><td><b>Total Minutes </b></td><td>:</td><td>"+ min +"</td></tr>\n" +
"                   <tr><td><b>Total </b></td><td>:</td><td><b>"+ amount +"</b></td></tr>\n" +
"                \n" +
"              </table>\n" +
"                   \n" +
"                   <form action='profile'><input class='park' type='submit' value='Done'></form>\n" +
"        </div>");
            
            out.println("</body>");
            out.println("</html>");
        
            
                 CallableStatement stmt8=con.prepareCall("call dropid(?,?)");
                      stmt8.setString(1,building_name);
                      stmt8.setInt(2,park_ID);
                      stmt8.execute();
                      stmt8.close();
       con.close();
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

   try{
      // Create a default MimeMessage object.
        MimeMessage message = new MimeMessage(mailsession);
      
        message.setFrom(new InternetAddress(from));  

        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

        // Set Subject: header field
        message.setSubject("parksmart");
      
        // Now set the actual message
         System.out.println(building_name); 
        String msg="Thank you for parking at "+building_name+"\n"+"Details "+vehicle_no+" ("+vehicle_type+" ) has \n parked for "+min+" minutes & \n it charges "+amount+"Rs \n Thank you! \n Have a safe drive!";
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
