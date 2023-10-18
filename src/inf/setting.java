/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package inf;

import com.mysql.cj.util.StringUtils;
import com.sun.mail.iap.Response;
import database.DbConnect;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.Action;
import javax.swing.JOptionPane;



public class setting extends javax.swing.JFrame {
     Connection conn=null;
    PreparedStatement pst=null;
    ResultSet rs=null;
    
    /**
     * Creates new form login
     */
    public setting() {
        initComponents();
     loadCurrentFines();
       
    }

    //Load Current Fiens to label
    public void loadCurrentFines(){
        
       try {
              conn = DbConnect.getConnection();
              String sql1="select Fines from dayfines where ID=1 ";
             pst=conn.prepareStatement(sql1);
              
            rs = pst.executeQuery();
            
            
             while(rs.next()){
            
            String cb = rs.getString("Fines");
            lbl_crfines.setText(cb);
           
             }
           
           
        } 
       catch (Exception e) {
                e.printStackTrace();
                }finally{
            try {
            rs.close();
            pst.close();
            } catch (Exception e) {
                
            }
        }
        
        
        
    }
    
    
    //Update Fines Amount

    public void ufines(){
        int newfine=Integer.parseInt(txt_ufines.getText());
        try {
            conn=DbConnect.getConnection();
          pst=conn.prepareStatement("UPDATE dayfines SET Fines=? where ID=1 ");
         pst.setInt(1, newfine);
          pst.executeUpdate();
          
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
            rs.close();
            pst.close();
            } catch (Exception e) {
               
            }
        }
    }
    
    
    
    
    
    
    
    //check username
    
    public boolean userCheck(String username){
        boolean isUC=false;
  
        
        
    boolean A=(username == null||username.trim().isEmpty()|| username.isBlank()||((username.matches("^\\S*$")))); 
   
 
    
  if(A == false) {
lbl_username1.setText("Enter Vaild UserName");
    isUC=false;
}else{ 
 isUC=true;
}
  
      
    return isUC;
    }
    
    
    
    
    
    
    
    
    //check emails equals
    public boolean checkEmails(String E1,String E2){
      String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";  
          Pattern pattern = Pattern.compile(regex);  
           Matcher matcher = pattern.matcher(E1);  
        
           
           boolean isvaild=false;
           
   if(matcher.matches()==true){
       
       isvaild= E1.equals(E2);
       
       if (isvaild==true){
           
          
             return isvaild;
    
         }else{
           lbl_email.setText("Not Match");
              return isvaild;
         }
       
       
   }else{
       lbl_email.setText("Not Valid Email");
   }
         return isvaild;
        
         
        

    }
    
    //check Password equals
    public boolean checkPass(String P1,String P2){
        
          boolean iseq= P1.equals(P2);
         if (iseq==true){
            iseq=true;
         }else{
             
             lbl_isup.setText("Not match");
             iseq=false;
         }
         
        return iseq;
         
    }
    
    
    //Update Account Details
    public void updatedetail(){
        String username=txt_unamebox2.getText();
         String E1=txt_E1.getText();
         String E2=txt_E2.getText(); 
         String P1=txt_P1.getText(); 
         String P2=txt_P2.getText(); 
         
         
      boolean UC=userCheck(username);   
      boolean EC=checkEmails(E1,E2);
      boolean PC=checkPass(P1,P2);
      
      if(UC==true&&EC==true&&PC==true){
         
          boolean isAdded=false;
          
          
          try {
           conn=DbConnect.getConnection();
           String sql="update admin set username=?,password=?,email=? where id=1";
           pst=conn.prepareStatement(sql);
           
           pst.setString(1,username);
           pst.setString(2,P1);
           pst.setString(3,E1);
             
         int rowCount =pst.executeUpdate();
           if(rowCount>0){
               isAdded=true;
           }else{
               isAdded=false;
           }
           
      
          } catch (Exception e) {
              
              e.printStackTrace();
          }
          
          
         if(isAdded==true){
             lbl_isup.setText("Update Successfully");
         } else{
             lbl_isup.setText("Update Error");
         }
          
          
          
         
      }
    
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
        txt_ufines = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        lbl_crfines = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lbl_email = new javax.swing.JLabel();
        lbl_isup = new javax.swing.JLabel();
        lbl_username1 = new javax.swing.JLabel();
        txt_E1 = new javax.swing.JTextField();
        txt_E2 = new javax.swing.JTextField();
        txt_unamebox2 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        rSMaterialButtonRectangle2 = new rojerusan.RSMaterialButtonRectangle();
        txt_P1 = new javax.swing.JPasswordField();
        jLabel12 = new javax.swing.JLabel();
        txt_P2 = new javax.swing.JPasswordField();
        btn_Update = new rojerusan.RSMaterialButtonRectangle();
        jLabel14 = new javax.swing.JLabel();
        close2 = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lbl_pss2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("LoginPage");
        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setUndecorated(true);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_ufines.setBackground(new java.awt.Color(51, 0, 0));
        txt_ufines.setForeground(new java.awt.Color(255, 255, 255));
        txt_ufines.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txt_ufines.setToolTipText("");
        txt_ufines.setBorder(null);
        txt_ufines.setCaretColor(new java.awt.Color(255, 255, 255));
        txt_ufines.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txt_ufines.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ufinesActionPerformed(evt);
            }
        });
        jPanel1.add(txt_ufines, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 390, 220, 30));

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icons/icons8_money_32px.png"))); // NOI18N
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 380, -1, -1));

        lbl_crfines.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        lbl_crfines.setForeground(new java.awt.Color(255, 255, 255));
        lbl_crfines.setText("56");
        jPanel1.add(lbl_crfines, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 320, 120, 50));

        jLabel7.setFont(new java.awt.Font("Poppins", 1, 36)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("Update Fines");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 260, 550, 50));

        jLabel6.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("User Name");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 330, -1, -1));

        lbl_email.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        lbl_email.setForeground(new java.awt.Color(255, 153, 0));
        jPanel1.add(lbl_email, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 400, 140, 20));

        lbl_isup.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        lbl_isup.setForeground(new java.awt.Color(255, 153, 0));
        jPanel1.add(lbl_isup, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 570, 260, 20));

        lbl_username1.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        lbl_username1.setForeground(new java.awt.Color(255, 153, 0));
        jPanel1.add(lbl_username1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 332, 160, 20));

        txt_E1.setBackground(new java.awt.Color(51, 0, 0));
        txt_E1.setForeground(new java.awt.Color(255, 255, 255));
        txt_E1.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txt_E1.setToolTipText("");
        txt_E1.setBorder(null);
        txt_E1.setCaretColor(new java.awt.Color(255, 255, 255));
        txt_E1.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txt_E1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_E1ActionPerformed(evt);
            }
        });
        jPanel1.add(txt_E1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 420, 260, 30));

        txt_E2.setBackground(new java.awt.Color(51, 0, 0));
        txt_E2.setForeground(new java.awt.Color(255, 255, 255));
        txt_E2.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txt_E2.setToolTipText("");
        txt_E2.setBorder(null);
        txt_E2.setCaretColor(new java.awt.Color(255, 255, 255));
        txt_E2.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txt_E2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_E2ActionPerformed(evt);
            }
        });
        jPanel1.add(txt_E2, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 420, 260, 30));

        txt_unamebox2.setBackground(new java.awt.Color(51, 0, 0));
        txt_unamebox2.setForeground(new java.awt.Color(255, 255, 255));
        txt_unamebox2.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txt_unamebox2.setToolTipText("");
        txt_unamebox2.setBorder(null);
        txt_unamebox2.setCaretColor(new java.awt.Color(255, 255, 255));
        txt_unamebox2.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txt_unamebox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_unamebox2ActionPerformed(evt);
            }
        });
        jPanel1.add(txt_unamebox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 350, 260, 30));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icons/icons/icons/icons8_user_32px.png"))); // NOI18N
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 350, -1, -1));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icons/icons/icons/icons8_mail_32px.png"))); // NOI18N
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 410, 40, 50));

        jLabel8.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Email Address");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 400, -1, -1));

        jLabel15.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Email Confirm ");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 400, -1, -1));

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icons/icons/icons/icons8_email_open_32px.png"))); // NOI18N
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 410, 50, 50));

        jLabel11.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Enter New Password");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 480, -1, -1));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icons/icons/icons/icons8_lock_32px.png"))); // NOI18N
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 480, 50, 60));

        rSMaterialButtonRectangle2.setBackground(new java.awt.Color(255, 255, 255));
        rSMaterialButtonRectangle2.setForeground(new java.awt.Color(204, 51, 0));
        rSMaterialButtonRectangle2.setText("Update Fine");
        rSMaterialButtonRectangle2.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        rSMaterialButtonRectangle2.setRippleColor(new java.awt.Color(51, 0, 0));
        rSMaterialButtonRectangle2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle2ActionPerformed(evt);
            }
        });
        jPanel1.add(rSMaterialButtonRectangle2, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 430, 220, 50));

        txt_P1.setBackground(new java.awt.Color(51, 0, 0));
        txt_P1.setForeground(new java.awt.Color(255, 255, 255));
        txt_P1.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txt_P1.setBorder(null);
        jPanel1.add(txt_P1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 500, 260, 30));

        jLabel12.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Re-Enter New Password");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 480, 250, -1));

        txt_P2.setBackground(new java.awt.Color(51, 0, 0));
        txt_P2.setForeground(new java.awt.Color(255, 255, 255));
        txt_P2.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txt_P2.setBorder(null);
        jPanel1.add(txt_P2, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 500, 260, 30));

        btn_Update.setBackground(new java.awt.Color(255, 255, 255));
        btn_Update.setForeground(new java.awt.Color(204, 51, 0));
        btn_Update.setText("Update Account Details");
        btn_Update.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        btn_Update.setRippleColor(new java.awt.Color(51, 0, 0));
        btn_Update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_UpdateActionPerformed(evt);
            }
        });
        jPanel1.add(btn_Update, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 590, 290, 50));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icons/icons/icons/icons8_lock_32px.png"))); // NOI18N
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 490, 30, 40));

        close2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/main/cancel (2).png"))); // NOI18N
        close2.setToolTipText("Close");
        close2.setBorder(null);
        close2.setBorderPainted(false);
        close2.setContentAreaFilled(false);
        close2.setRequestFocusEnabled(false);
        close2.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/main/cancel (3).png"))); // NOI18N
        close2.setVerifyInputWhenFocusTarget(false);
        close2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                close2ActionPerformed(evt);
            }
        });
        jPanel1.add(close2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1170, 80, 40, 50));

        jLabel18.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Fines for Day");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 370, -1, -1));

        jLabel19.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Current Fines for Day  RS:");
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 330, -1, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/main/logo.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 80, 280, 210));

        jLabel9.setFont(new java.awt.Font("Poppins", 1, 36)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("Update Account Details");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 260, 550, 50));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/main/Capture.jpg"))); // NOI18N
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 720));

        lbl_pss2.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        lbl_pss2.setForeground(new java.awt.Color(255, 153, 0));
        jPanel1.add(lbl_pss2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 480, 120, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(1280, 720));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void close2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_close2ActionPerformed
        mainfm obj4=new mainfm();
        this.dispose();
        
        obj4.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_close2ActionPerformed

    private void txt_E1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_E1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_E1ActionPerformed

    private void rSMaterialButtonRectangle2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle2ActionPerformed
        // TODO add your handling code here:
        ufines();
        loadCurrentFines();
    }//GEN-LAST:event_rSMaterialButtonRectangle2ActionPerformed

    private void txt_ufinesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ufinesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_ufinesActionPerformed

    private void btn_UpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_UpdateActionPerformed
        // TODO add your handling code here:
        
      int response= JOptionPane.showConfirmDialog(this, "Do check check your Details is correct or not?","Confirm Before Update", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE );
        if(response==JOptionPane.YES_OPTION){
           updatedetail(); 
        }
    }//GEN-LAST:event_btn_UpdateActionPerformed

    private void txt_E2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_E2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_E2ActionPerformed

    private void txt_unamebox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_unamebox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_unamebox2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(setting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(setting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(setting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(setting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new setting().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojerusan.RSMaterialButtonRectangle btn_Update;
    private javax.swing.JButton close2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbl_crfines;
    private javax.swing.JLabel lbl_email;
    private javax.swing.JLabel lbl_isup;
    private javax.swing.JLabel lbl_pss2;
    private javax.swing.JLabel lbl_username1;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle2;
    private javax.swing.JTextField txt_E1;
    private javax.swing.JTextField txt_E2;
    private javax.swing.JPasswordField txt_P1;
    private javax.swing.JPasswordField txt_P2;
    private javax.swing.JTextField txt_ufines;
    private javax.swing.JTextField txt_unamebox2;
    // End of variables declaration//GEN-END:variables
}
