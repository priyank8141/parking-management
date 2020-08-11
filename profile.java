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



public class profile extends HttpServlet {

   
     
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
         HttpSession session=request.getSession();
        String username=(String)session.getAttribute("user");         /* for getting user name & password from session */
        String password=(String)session.getAttribute("psswd");
         try{
             Connection con;
        
        out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>profile</title>"); 
            out.println("<link href='style1.css' type='text/css' rel='stylesheet'>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div id='header'>\n" +
"            <img class='logo' src='images/logo.png' alt='logo'>\n" +
"            <h1 class='name'><span style='color:#2cfc03;'>P</span>ark <span style='color:#fce303;'>S</span>mart</h1>\n" +
"            \n" +
"        </div>\n" +
"\n" +
"        \n" +
"        \n" +
"        <div id='container'>  ");
        Class.forName("com.mysql.cj.jdbc.Driver");
                System.out.println("Driver established");
        con=DriverManager.getConnection("jdbc:Mysql://localhost:3306/parkingmanagement","root","priyank8141");      
                System.out.println("connection established");
                
         Statement s=con.createStatement();
             ResultSet rst=s.executeQuery("select * from register_user where name='"+username+"' and password='"+password+"'");
             
          while(rst.next()){
              String building_name=rst.getString("building_name");
               session.setAttribute("building_name",building_name);   
               
               out.println("<h1 class='heading' style='text-align:left;'>"+building_name+" </h1>");
               out.println("<table id='data' border='1' width='90%'>  \n" +
"                  <tr>\n" +
"                      <th>ID</th>\n" +
"                      \n" +
"                      <th>Vehicle No</th>\n" +
"                      <th>Vehicle Type</th>\n" +
"                      <th>Email</th>\n" +
"                      <th>Allot Time</th>\n" +
"                      <th>Slot Occupied</th>\n" +
"                      \n" +
"                  </tr>");
               CallableStatement stmt1=con.prepareCall("call selecttbl(?)"); /* for setting building name in stored procedure  */
                    stmt1.setString(1,building_name);
                    ResultSet rs=stmt1.executeQuery();
                    
                    while(rs.next()){
                        out.println("<tr><td>"+ rs.getInt(1)+"</td><td>"+ rs.getString(2)+"</td><td>"+rs.getString(3)+"</td><td>"+rs.getString(4)+"</td><td>"+rs.getString(5)+"</td><td>"+rs.getString(6)+"</td></tr>"); 
                    }
                    out.println("</table><br><br>");
                    
                    out.println("<div id='park'>\n" +
"              <form action='park' method='post' >\n" +
"                  <h3 class='heading' style='text-align:left;'> Park It </h3>\n" +
"                  <table><tr>\n" +
"                          <td><input type='text' name='vehicle_no' placeholder='vehicle no'></td>\n" +
"                          <td><input type='text' name='vehicle_type' placeholder='vehicle type' ></td>\n" +
"                          <td><input type='text' name='email' placeholder='email' ></td>\n" +
"                          <td><input type='text' name='slot_occupied' placeholder='slot occupied' ></td>\n" +
"                          <td><input class='park' type='submit' value='Park' ></td>\n" +
"                  </tr></table>\n" +
"              </form>\n" +
"              </div>\n" +
"              <br>");
                    
               out.println("<div id='unpark'>\n" +
"                  <form action='unpark' method='post'>\n" +
"                      <h3 class='heading' style='text-align:left;'> Un Park </h3>\n" +
"                      <input type='text' name='park_ID' placeholder='Park Id'>\n" +
"                      <input class='unpark' type='submit' value='Un Park'>\n" +
"                  </form>\n" +
"                  \n" +
"              </div>"); 
               
               out.println(" <div id='edit'>\n" +
"              <form action='edit' method='post' >\n" +
"                  <h3 class='heading' style='text-align:left;'> Edit </h3>\n" +
"                   <table><tr>\n" +
"                           <td><input type='text' name='park_ID' placeholder='park Id'></td>\n" +
"                           <td><input type='text' name='vehicle_no' placeholder='vehicle no'></td>\n" +
"                           <td><input type='text' name='vehicle_type' placeholder='vehicle type' ></td>\n" +
"                           <td><input type='text' name='email' placeholder='email' ></td>\n" +
"                           <td><input type='text' name='slot_occupied' placeholder='slot occupied' ></td>\n" +
"                           <td><input class='edit' type='submit' value='Edit' ></td>\n" +
"                  </tr></table>\n" +
"                  <p style='color:red'>you can't change park id & it must be same as in the table </p>\n" +
"              </form>\n" +
"              </div>");
               
              out.println("<div id='records'>\n" +
"              <form action='record' method='post' >\n" +
"                  <h3 class='heading' style='text-align:left;'> Records </h3>\n" +
"                  <table>\n" +
"                      <tr><td>From</td><td>To</td></tr>\n" +
"                      <tr>\n" +
"                          <td><input type='date' id='from' name='from' placeholder='From'></td>\n" +
"                          <td><input type='date' id='to' name='to' placeholder='To'></td>\n" +
"                          <td><input class='search' type='submit' value='search'></td>\n" +
"                      </tr>\n" +
"                  </table>\n" +
"                      \n" +
"              </form>    \n" +
"              </div>"); 
                     stmt1.close(); 
          }
           s.close();
           con.close(); 
           System.out.println("connection closed");

       
          
         }  catch(Exception e){
                  System.out.println(e);
                  } 
         out.println("<br><br>\n" +
"           <div id='logout'>\n" +
"            <form action='logout.jsp' >\n" +
"             <input class='logout' type='submit' value='Logout'>\n" +
"            </form>\n" +
"           </div>");
         out.println("</div>");
         out.println("</body>");
            out.println("</html>");
    
    }

    
}   
         
                  