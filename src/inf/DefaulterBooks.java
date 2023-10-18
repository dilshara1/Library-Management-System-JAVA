/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package inf;

//import com.mysql.cj.Session;
//import com.mysql.cj.protocol.Message;
//import com.sun.jdi.connect.Transport;
import javax.mail.*;

import database.DbConnect;
import java.net.PasswordAuthentication;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.io.File;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
//import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
// import com.email.durgesh.Email;
import static java.lang.System.console;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.internet.AddressException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.security.Security;
import java.text.MessageFormat;
import javax.swing.JTable;

/**
 *
 * @author Dilshara Mithum
 */
public class DefaulterBooks extends javax.swing.JInternalFrame {

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    DefaultTableModel model;

    /**
     * Creates new form IssueBooks
     */
    public DefaulterBooks() {
        initComponents();
        setDBDetailsToTable();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
    }

    public void EmailSender() {
        try {
        //    Email email = new Email("librarymanagementpj@gmail.com", "xhbcxvzbmkepefxf");
       //     email.setFrom("librarymanagementpj@gmail.com", "Test Libray");
         //   email.setSubject("This is Testing Email");
          //  email.setContent("<h1>THIS IS CONTENENT</h>", "text/html");
          //  email.addRecipient("dilsharamithum3@gmail.com");
          //  email.send();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDBDetailsToTable() {
        long l = System.currentTimeMillis();
        // Date todaysDate=new Date(l);
        //Convert System data to SQL date formate
        java.sql.Date TD = new java.sql.Date(l);

        try {

            conn = DbConnect.getConnection();
            pst = conn.prepareStatement("Select * from issueBooks where ReturnDate<? and status=?");

            pst.setDate(1, TD);
            pst.setString(2, "Pending");
            rs = pst.executeQuery();

            while (rs.next()) {
                String id = rs.getString("id");
                String bookID = rs.getString("BookID");
                String bookName = rs.getString("BookName");
                String MemberID = rs.getString("MemberID");
                String memName = rs.getString("MemberName");
                Date iDate = rs.getDate("IssueDate");
                Date rDate = rs.getDate("ReturnDate");
                String status = rs.getString("Status");

                Object[] obj = {id, bookID, bookName, MemberID, memName, iDate, rDate, status};
                model = (DefaultTableModel) tbl_DDetails.getModel();
                model.addRow(obj);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "No Defaulter Books");

        }

    }

    //clear table
    public void clerMemTable() {
        DefaultTableModel model = (DefaultTableModel) tbl_DDetails.getModel();
        model.setRowCount(0);
    }

    //to get record with dates
    public void loadDataToEmail() {
        //aset text areas 
        int rowNo = tbl_DDetails.getSelectedRow();
        TableModel model = tbl_DDetails.getModel();

        txt_Name.setText(model.getValueAt(rowNo, 4).toString());

        //get member and book detaios to variables
        int ID = Integer.parseInt((model.getValueAt(rowNo, 0).toString()));

        try {

            conn = DbConnect.getConnection();
            pst = conn.prepareStatement("Select issuebooks.MemberName,issuebooks.BookName,members.Email,issuebooks.ReturnDate from issueBooks,members where issuebooks.ID=?");

            pst.setInt(1, ID);
            rs = pst.executeQuery();

            while (rs.next()) {
                String memName = rs.getString("issuebooks.MemberName");
                String bookName = rs.getString("issuebooks.BookName");
                String email = rs.getString("members.Email");
                String Date = rs.getString("issuebooks.ReturnDate");

                txt_email.setText(email.toString());
                txt_body.setText("Dear " + memName +"\n" 
                        
+"We are sending this email to inform you about that you have delayed to return the\n"
+"borrowed book on or before the dealine.So that you should know that the fines will be\n "
+"added according to each day." +
"\n\n" +
"If you have lost the book , you should buy that book and  bring it to the library,"
+"\nOtherwise you have to pay the price of the book and you have to pay twenty five\n"
+"percent of the price of the book with the fines for delayed dates.\n"

+"\n Book Name : " +bookName + " \nDeadline :" + Date+"\n\n" +"Thank you.\n" +
"Pallebedda Library");
            
            
            
            
            
 /*Dear ( Name ) ,

We are sending this email to inform you about that 
you have delayed to return the borrowed book on or 
before the dealine.So that you should know that the 
fines will be added according to each day.

If you have lost the book , you should buy that book 
and  bring it to the library , Otherwise you have to 
pay the price of the book and you have to pay twenty 
five percent of the price of the book with the fines 
for delayed dates.

Book Name : 
Deadline :

Thank you.
Pallebedda Library
                
                
                */
            
            
            
            
            
            
            
            
            
            
            
            
            
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //sent mail
    public String sendMail(String AEmail, String APass, String Email, String Subject, String Body) {
        String Mag = "false";
        final String username = AEmail;
        final String password = APass;

       
//Variable for gmail
       

        //get the system properties
        Properties properties = System.getProperties();
        System.out.println("PROPERTIES " + properties);

        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
         properties.put("mail.smtp.auth", "true");
       properties.put("mail.smtp.starttls.enable","true"); 
         properties.put("mail.smtp.starttls.required", "true");
             properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
                    properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.ssl.enable", "true");
       
    
 
       
        properties.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");
      
   properties.put("mail.smtp.debug", "true");
 final javax.mail.Session mailSession = javax.mail.Session.getInstance(properties);


        
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication("yourgmail@gmail.com", "yourpassword");
            }

        });

        /*
        protected PasswordAuthentication Authentication(){
            return new PasswordAuthentication("librarymanagementpj@gmail.com","xhbcxvzbmkepefxf".toCharArray());
         */
        try {
            Message message = new MimeMessage(session);
 
            
            message.setFrom(new InternetAddress(AEmail)); //user email
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(Email));

            message.setSubject(Subject);
            message.setText(Body);
            Transport.send(message);
            
               Transport transport =mailSession.getTransport("smtp");
        System.out.println("smtp");

     
        
    transport.connect("smtp.gmail.com","yourgmail@gmail.com","yourpassword");
        System.out.println("smtp connection");
    transport.sendMessage(message,message.getAllRecipients());
        System.out.println("smtp message");
        
    
    transport.close();
            
            Mag = "true";
            return Mag;
        } catch (Exception e) {

            e.printStackTrace();
        }

        return Mag;

    }
   
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btn_add2 = new rojerusan.RSMaterialButtonRectangle();
        jLabel13 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txt_Name = new javax.swing.JTextField();
        txt_email = new javax.swing.JTextField();
        txt_subject = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_body = new javax.swing.JTextArea();
        jPanel17 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_DDetails = new rojeru_san.complementos.RSTableMetro();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        btn_export = new rojerusan.RSMaterialButtonRectangle();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 0, 0));
        setMaximumSize(new java.awt.Dimension(990, 670));
        setMinimumSize(new java.awt.Dimension(990, 670));
        setPreferredSize(new java.awt.Dimension(990, 670));

        jPanel1.setMaximumSize(new java.awt.Dimension(990, 670));
        jPanel1.setMinimumSize(new java.awt.Dimension(990, 670));
        jPanel1.setPreferredSize(new java.awt.Dimension(990, 670));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel16.setBackground(new java.awt.Color(153, 51, 0));
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(51, 0, 0));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_add2.setBackground(new java.awt.Color(204, 51, 0));
        btn_add2.setText("Send mail");
        btn_add2.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        btn_add2.setRippleColor(new java.awt.Color(51, 0, 0));
        btn_add2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_add2ActionPerformed(evt);
            }
        });
        jPanel2.add(btn_add2, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 0, 130, 60));

        jLabel13.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icons/pic40/icons8_paper_plane_40px.png"))); // NOI18N
        jLabel13.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel13.setFocusable(false);
        jLabel13.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel13.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 50, 50));

        jLabel16.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Sent Remender");
        jLabel16.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel16.setFocusable(false);
        jLabel16.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel16.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 0, 370, 50));

        jPanel16.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 920, 60));

        txt_Name.setBackground(new java.awt.Color(51, 0, 0));
        txt_Name.setForeground(new java.awt.Color(255, 255, 255));
        jPanel16.add(txt_Name, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 280, 30));

        txt_email.setBackground(new java.awt.Color(51, 0, 0));
        txt_email.setForeground(new java.awt.Color(255, 255, 255));
        jPanel16.add(txt_email, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 280, 30));

        txt_subject.setBackground(new java.awt.Color(51, 0, 0));
        txt_subject.setForeground(new java.awt.Color(255, 255, 255));
        jPanel16.add(txt_subject, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, 280, 30));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Message");
        jLabel14.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel14.setFocusable(false);
        jLabel14.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel14.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel16.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 30, 370, 50));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Name");
        jLabel18.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel18.setFocusable(false);
        jLabel18.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel18.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel16.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 250, 20));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Email");
        jLabel19.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel19.setFocusable(false);
        jLabel19.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel19.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel16.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 250, 20));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Subject");
        jLabel20.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel20.setFocusable(false);
        jLabel20.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel20.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel16.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 120, 50));

        txt_body.setBackground(new java.awt.Color(51, 0, 0));
        txt_body.setColumns(20);
        txt_body.setForeground(new java.awt.Color(255, 255, 255));
        txt_body.setRows(5);
        jScrollPane1.setViewportView(txt_body);

        jPanel16.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 80, 520, 150));

        jPanel1.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 400, 920, 250));

        jPanel17.setBackground(new java.awt.Color(153, 51, 0));
        jPanel17.setBorder(javax.swing.BorderFactory.createMatteBorder(80, 0, 0, 0, new java.awt.Color(51, 0, 0)));
        jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_DDetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "BookID", "Book Name", "MemberID", "Member Name", "Issue Date", "Return Date", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, true, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_DDetails.setColorBackgoundHead(new java.awt.Color(51, 0, 0));
        tbl_DDetails.setColorFilasBackgound2(new java.awt.Color(247, 230, 220));
        tbl_DDetails.setColorFilasForeground1(new java.awt.Color(153, 51, 0));
        tbl_DDetails.setColorFilasForeground2(new java.awt.Color(153, 51, 0));
        tbl_DDetails.setColorSelBackgound(new java.awt.Color(153, 51, 0));
        tbl_DDetails.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        tbl_DDetails.setFuenteFilas(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        tbl_DDetails.setFuenteFilasSelect(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        tbl_DDetails.setFuenteHead(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        tbl_DDetails.setGrosorBordeFilas(0);
        tbl_DDetails.setGrosorBordeHead(0);
        tbl_DDetails.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_DDetailsMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbl_DDetails);

        jPanel17.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 920, 310));

        jLabel15.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icons/pic40/icons8_people_40px_1.png"))); // NOI18N
        jLabel15.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel15.setFocusable(false);
        jLabel15.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel15.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel17.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, 50, 50));

        jLabel17.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Defaulter  Records");
        jLabel17.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel17.setFocusable(false);
        jLabel17.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel17.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel17.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, 240, 50));

        btn_export.setBackground(new java.awt.Color(204, 51, 0));
        btn_export.setText("Export ");
        btn_export.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        btn_export.setRippleColor(new java.awt.Color(51, 0, 0));
        btn_export.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_exportActionPerformed(evt);
            }
        });
        jPanel17.add(btn_export, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 10, 110, 50));

        jPanel1.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 920, 380));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/main/Artboard 1.png"))); // NOI18N
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 990, 670));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    private void btn_add2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_add2ActionPerformed

        // TODO add your handling code here:
     //I'm Use Google SMTP SERVER..You Must Create Google SMTP Server with your Gmail..You can find it on Youtube.
        String AEmail="yourgmail@gmail.com";
      
      String APass="yourpassword";
        String Email=txt_email.getText();
        String Subject=txt_subject.getText();
        String Body=txt_body.getText();
        
     String Test= sendMail(AEmail,APass,Email,Subject,Body);
     
      
      
        if(Test=="true"){
        JOptionPane.showMessageDialog(this, "Email Sent Succuessfully");
        
        
        
        
        
        }else{JOptionPane.showMessageDialog(this, "Email Sent Fail");}
        
      
      
   
      
      
      
      
      
         

    }//GEN-LAST:event_btn_add2ActionPerformed


    private void tbl_DDetailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_DDetailsMouseClicked
        int rowNo = tbl_DDetails.getSelectedRow();
        TableModel model = tbl_DDetails.getModel();

        txt_Name.setText(model.getValueAt(rowNo, 4).toString());
        txt_subject.setText("Inform about Books");

        loadDataToEmail();

//aset text areas 
        //     txt_bookid.setText(model.getValueAt(rowNo,0).toString());

    }//GEN-LAST:event_tbl_DDetailsMouseClicked

    private void btn_exportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_exportActionPerformed

        // TODO add your handling code here:

        
        MessageFormat header=new MessageFormat("DefaulterList");
        MessageFormat footer=new MessageFormat("page{0,number,integer}");
        try {
            tbl_DDetails.print(JTable.PrintMode.FIT_WIDTH,header,footer);
        } catch (Exception e) {
            e.getMessage();
        }
    }//GEN-LAST:event_btn_exportActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojerusan.RSMaterialButtonRectangle btn_add2;
    private rojerusan.RSMaterialButtonRectangle btn_export;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private rojeru_san.complementos.RSTableMetro tbl_DDetails;
    private javax.swing.JTextField txt_Name;
    private javax.swing.JTextArea txt_body;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_subject;
    // End of variables declaration//GEN-END:variables
}
