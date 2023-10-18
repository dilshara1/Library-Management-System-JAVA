/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package inf;

import database.DbConnect;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
/**
 *
 * @author Dilshara Mithum
 */

 

public class ReturnBooks extends javax.swing.JInternalFrame {
    
     Date ReturnDate=null;
    String ReturnDate1=null;
    int fines=0;
    int memIDFines=0;
    
    
    

    
    
    
DefaultTableModel model;
 DefaultTableModel model1;
    /**
     * Creates new form IssueBooks
     */
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public ReturnBooks() {
        initComponents();

        conn = DbConnect.getConnection();
        
   
        
        setFinesToTable();
       setFinesAmounttoLable();
       
       
       
      // calDates();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
    }

    
   
    
    //Get Issue book details from db and add data to label
    public void getissueBookDetails() {
        int bookID = Integer.parseInt(txt_bookid.getText());
        int memID = Integer.parseInt(txt_memid.getText());

        try {

            conn = DbConnect.getConnection();
            String sql = "select * from issueBooks where BookID=? and MemberID=? and Status=?";

            pst = conn.prepareStatement(sql);
            pst.setInt(1, bookID);
            pst.setInt(2, memID);
            pst.setString(3, "Pending");

            rs = pst.executeQuery();

             if (rs.next()) {

            
                
                lbl_iid.setText(rs.getString("id"));
                memIDFines=rs.getInt("id");
                lbl_bookname.setText(rs.getString("BookName"));
                lbl_memName.setText(rs.getString("MemberName"));
                lbl_idate.setText(rs.getString("IssueDate"));
                lbl_returnDate.setText(rs.getString("ReturnDate"));
                ReturnDate=rs.getDate("ReturnDate");
               
                
                lbl_Error.setText("");
                   
                
                //Get Retun date for Fine Calculator
        //   Date iDate = rs.getDate("IssueDate");
         //  Date rDate = rs.getDate("ReturnDate");
                     
                //get member id for later fines pay
                
                
                
                
                
            } else {
                lbl_Error.setText("No Record Found");

                lbl_iid.setText(rs.getString(""));
                lbl_bookname.setText(rs.getString(""));
                lbl_memName.setText(rs.getString(""));
                lbl_idate.setText(rs.getString(""));
                lbl_returnDate.setText((rs.getString("")));

            }
            
            
            
            
            
            
            
            
            
            
            /*
            
            if (rs.next()) {

                //    Date iDate = rs.getDate("IssueDate");
                //    Date rDate = rs.getDate("ReturnDate");
                
                lbl_iid.setText(rs.getString("id"));
                memIDFines=rs.getInt("id");
                lbl_bookname.setText(rs.getString("BookName"));
                lbl_memName.setText(rs.getString("MemberName"));

            //    lbl_idate.setText(rs.getString("IssueDate"));
                
                 java.sql.Date sIssueDate=rs.getDate("IssueDate");
                 
                 java.util.Date d2=new java.util.Date(sIssueDate.getTime());
                 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
                 String sdate=dateFormat.format(d2);
                 
                // System.out.print(sdate);
              lbl_idate.setText(sdate.toString());
                 
        //         Date d1=new Date(sIssueDate);                             
           //     lbl_returnDate.setText((rs.getString("ReturnDate")));
                
                 java.sql.Date sReturnDate=rs.getDate("ReturnDate");
                 java.util.Date d21=new java.util.Date(sReturnDate.getTime());
                 DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");  
                 
                 String sdate2=dateFormat2.format(d21);
                lbl_returnDate.setText(sdate2.toString());

               
                
                lbl_Error.setText("");
                
                
                //Get Retun date for Fine Calculator
                ReturnDate=d21;
                ReturnDate1=sdate2.toString();
                     
                //get member id for later fines pay
                
                
                
                
                
            } else {
                lbl_Error.setText("No Record Found");

                lbl_iid.setText(rs.getString(""));
                lbl_bookname.setText(rs.getString(""));
                lbl_memName.setText(rs.getString(""));
                lbl_idate.setText(rs.getString(""));
                lbl_returnDate.setText((rs.getString("")));

            }
            
            
            
            */
           

        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                rs.close();
            pst.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        
        }

    }

    //Return Book
    public boolean returnBook() {
        boolean isReturned = false;
        int bookID = Integer.parseInt(txt_bookid.getText());
        int memID = Integer.parseInt(txt_memid.getText());
        try {
            conn = DbConnect.getConnection();
            String sql = "update issueBooks set Status=? where MemberID=? and BookID=? and Status=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, "Returned");
            pst.setInt(2, memID);
            pst.setInt(3, bookID);
            pst.setString(4, "Pending");

            int rowCount = pst.executeUpdate();
            if (rowCount > 0) {
                isReturned = true;
            } else {
                isReturned = false;
            }
            
      
              
                
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isReturned;
        
        
        
        
    }

    //Update Book Count
    public void updateBookCount() {
        int bookId = Integer.parseInt(txt_bookid.getText());
        try {
            conn = DbConnect.getConnection();
            String sql = "update BookDetails set Quantity=Quantity+1 where BookID=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, bookId);
            int rowCount = pst.executeUpdate();

            if (rowCount > 0) {
                JOptionPane.showMessageDialog(this, "Book Count Updated");

            } else {
                JOptionPane.showMessageDialog(this, "Can't Updated Book Count");
            }
          //  rs.close();
          //  pst.close();

        } catch (Exception e) {
        }

    }

    
    //Add Fines details to table
   public void setFinesToTable() {

        try {
            conn = DbConnect.getConnection();
            Statement st = conn.createStatement();
            rs = st.executeQuery("Select ID,FinesAmount,MemberID,Date from Fines");

            while (rs.next()) {
                String id = rs.getString("ID");
                String fAM = rs.getString("FinesAmount");
                String memID = rs.getString("MemberID");
                Date iDate = rs.getDate("Date");
                
               

                Object[] obj = {id, fAM, memID, iDate};
                model = (DefaultTableModel) tbl_FDetails.getModel();
                model.addRow(obj);
            }

            
         //   rs.close();
         //   pst.close();
           
        } catch (Exception e) {
            e.printStackTrace();
        }

    } 
    
   //Update Fines Label
   public void setFinesAmounttoLable(){
       
          try {
           conn=DbConnect.getConnection();
       String sql="Select Fines from dayfines where ID=1";
          pst=conn.prepareStatement(sql);
       
          rs=pst.executeQuery();
         if(rs.next()){ 
             lbl_fday1.setText(rs.getString("Fines"));
             fines=rs.getInt("Fines");
         }
      
          
       } catch (Exception e) {
           e.printStackTrace();
       }finally{
            try {
                rs.close();
            pst.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        
        }
   }
   
   
   

   
   
   
    
   
  //Calculate Late Days
   public void calDates(){
     
    
  
       ZoneId z=ZoneId.of("Asia/Colombo");
        LocalDate today=LocalDate.now(z);

        String currentDate=today.toString();

        SimpleDateFormat myformat=new SimpleDateFormat("yyyy-MM-dd");

        String ReDate = myformat.format(ReturnDate);  


        try{
       
   int fine=0;
   

            
//Date CAlculator
            Date firstDate = myformat.parse(ReDate);
            Date secondDate = myformat.parse(currentDate);

            long dif = secondDate.getTime()- firstDate.getTime();

            int daysBetween =(int) (dif/(1000*60*60*24));

             int Fine=daysBetween*fines;
            //answer wil be the difference between your current date and the date you provided in the jTextField1
            
            
            
            if(daysBetween>0){
                lbl_ldays.setText(String.valueOf(daysBetween));
                lbl_tf.setText(String.valueOf(Fine));
                memIDFines=Fine;
             
                
            }else{
                lbl_ldays.setText("0");
            }
            
      
       }catch(Exception e){
          e.printStackTrace();
        }
      
      
      
   } 
    
   
   
    //Pay Fines
   public boolean pay(){
   //fines
   //memIDFines
   boolean ispay=false;
   
   if(memIDFines>0){
       Date d=new Date();
       SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
       Long l2=d.getTime();

        java.sql.Date tdate=new java.sql.Date(l2);
       
       try {
            conn=DbConnect.getConnection();
       String sql="insert into Fines (FinesAmount,MemberID,Date) values (?,?,?)";
          pst=conn.prepareStatement(sql);
          
           pst.setInt(1, fines);
           pst.setInt(2,memIDFines);
           pst.setDate(3,tdate);
           int rowCount=pst.executeUpdate();
          
          fines=0;
          ispay=true;
           
           
       } catch (Exception e) {
           e.printStackTrace();
       }
   }else{
       ispay= false;
   }
          
        return ispay;       
   };
   
   
   
   
   
   
   
   
   
   
   
   
    //Clear Fine Table
    public void clerFineTable(){
        DefaultTableModel model=(DefaultTableModel) tbl_FDetails.getModel();
        model.setRowCount(0);
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
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_FDetails = new rojeru_san.complementos.RSTableMetro();
        jPanel15 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        lbl_fday = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        lbl_tf = new javax.swing.JLabel();
        lbl_ldays = new javax.swing.JLabel();
        lbl_fday1 = new javax.swing.JLabel();
        Finespay = new rojerusan.RSMaterialButtonRectangle();
        jPanel16 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        txt_bookid = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        rSMaterialButtonRectangle1 = new rojerusan.RSMaterialButtonRectangle();
        jLabel15 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        lbl_Error = new javax.swing.JLabel();
        txt_memid = new javax.swing.JTextField();
        rSMaterialButtonRectangle2 = new rojerusan.RSMaterialButtonRectangle();
        jPanel14 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        lbl_returnDate = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        lbl_iid = new javax.swing.JLabel();
        lbl_bookname = new javax.swing.JLabel();
        lbl_memName = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        lbl_idate = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 0, 0));
        setMaximumSize(new java.awt.Dimension(990, 670));
        setMinimumSize(new java.awt.Dimension(990, 670));
        setPreferredSize(new java.awt.Dimension(990, 670));

        jPanel1.setMaximumSize(new java.awt.Dimension(990, 670));
        jPanel1.setMinimumSize(new java.awt.Dimension(990, 670));
        jPanel1.setPreferredSize(new java.awt.Dimension(990, 670));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_FDetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Fines Amount", "MemberID", "Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_FDetails.setColorBackgoundHead(new java.awt.Color(51, 0, 0));
        tbl_FDetails.setColorFilasBackgound2(new java.awt.Color(247, 230, 220));
        tbl_FDetails.setColorFilasForeground1(new java.awt.Color(153, 51, 0));
        tbl_FDetails.setColorFilasForeground2(new java.awt.Color(153, 51, 0));
        tbl_FDetails.setColorSelBackgound(new java.awt.Color(153, 51, 0));
        tbl_FDetails.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        tbl_FDetails.setFuenteFilas(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        tbl_FDetails.setFuenteFilasSelect(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        tbl_FDetails.setFuenteHead(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        tbl_FDetails.setGrosorBordeFilas(0);
        tbl_FDetails.setGrosorBordeHead(0);
        jScrollPane2.setViewportView(tbl_FDetails);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 330, 550, 320));

        jPanel15.setBackground(new java.awt.Color(153, 51, 0));
        jPanel15.setBorder(javax.swing.BorderFactory.createMatteBorder(50, 1, 1, 1, new java.awt.Color(51, 0, 0)));
        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel18.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Fines Cal");
        jLabel18.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel18.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel18.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel15.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 160, 50));

        jLabel30.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icons/adminIcons/adminIcons/icons8_View_Details_26px.png"))); // NOI18N
        jLabel30.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel30.setFocusable(false);
        jLabel30.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel30.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel15.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 50, 30));

        lbl_fday.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        lbl_fday.setForeground(new java.awt.Color(255, 255, 255));
        lbl_fday.setText("RS");
        jPanel15.add(lbl_fday, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 90, 30, 40));

        jLabel23.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Fines for Day:");
        jPanel15.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, -1, 40));

        jLabel26.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("Late Days:");
        jPanel15.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, -1, -1));

        jLabel31.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setText("TOTAL FINES");
        jPanel15.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 310, -1));

        jLabel32.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setText("RS:");
        jPanel15.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 170, 30, -1));

        lbl_tf.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        lbl_tf.setForeground(new java.awt.Color(255, 255, 255));
        lbl_tf.setText("0");
        jPanel15.add(lbl_tf, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 170, 170, -1));

        lbl_ldays.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        lbl_ldays.setForeground(new java.awt.Color(255, 255, 255));
        lbl_ldays.setText("0");
        jPanel15.add(lbl_ldays, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 60, 100, -1));

        lbl_fday1.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        lbl_fday1.setForeground(new java.awt.Color(255, 255, 255));
        lbl_fday1.setText("0");
        jPanel15.add(lbl_fday1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 90, 100, 40));

        Finespay.setBackground(new java.awt.Color(204, 51, 0));
        Finespay.setText("Pay");
        Finespay.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        Finespay.setRippleColor(new java.awt.Color(51, 0, 0));
        Finespay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FinespayActionPerformed(evt);
            }
        });
        jPanel15.add(Finespay, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 0, 100, 50));

        jPanel1.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 440, 310, 210));

        jPanel16.setBackground(new java.awt.Color(153, 51, 0));
        jPanel16.setBorder(javax.swing.BorderFactory.createMatteBorder(80, 0, 0, 0, new java.awt.Color(51, 0, 0)));
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Book ID :");
        jPanel16.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, -1, -1));

        txt_bookid.setBackground(new java.awt.Color(51, 0, 0));
        txt_bookid.setForeground(new java.awt.Color(255, 255, 255));
        txt_bookid.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_bookid.setToolTipText("");
        txt_bookid.setBorder(null);
        txt_bookid.setCaretColor(new java.awt.Color(255, 255, 255));
        txt_bookid.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txt_bookid.setMargin(new java.awt.Insets(2, 10, 2, 6));
        txt_bookid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_bookidActionPerformed(evt);
            }
        });
        jPanel16.add(txt_bookid, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 260, 30));

        jLabel27.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Member ID :");
        jPanel16.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, -1, -1));

        rSMaterialButtonRectangle1.setBackground(new java.awt.Color(204, 51, 0));
        rSMaterialButtonRectangle1.setText("Return Book");
        rSMaterialButtonRectangle1.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        rSMaterialButtonRectangle1.setRippleColor(new java.awt.Color(51, 0, 0));
        rSMaterialButtonRectangle1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle1ActionPerformed(evt);
            }
        });
        jPanel16.add(rSMaterialButtonRectangle1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 330, 220, 50));

        jLabel15.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("    Return Book");
        jLabel15.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel15.setFocusable(false);
        jLabel15.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel15.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel16.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 260, 50));

        jLabel29.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icons/pic40/icons8_return_purchase_40px.png"))); // NOI18N
        jLabel29.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel29.setFocusable(false);
        jLabel29.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel29.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel16.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 50, 50));

        lbl_Error.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        lbl_Error.setForeground(new java.awt.Color(255, 204, 0));
        lbl_Error.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel16.add(lbl_Error, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 240, 30));

        txt_memid.setBackground(new java.awt.Color(51, 0, 0));
        txt_memid.setForeground(new java.awt.Color(255, 255, 255));
        txt_memid.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_memid.setToolTipText("");
        txt_memid.setBorder(null);
        txt_memid.setCaretColor(new java.awt.Color(255, 255, 255));
        txt_memid.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txt_memid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_memidActionPerformed(evt);
            }
        });
        jPanel16.add(txt_memid, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 260, 30));

        rSMaterialButtonRectangle2.setBackground(new java.awt.Color(204, 51, 0));
        rSMaterialButtonRectangle2.setText("Find");
        rSMaterialButtonRectangle2.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        rSMaterialButtonRectangle2.setRippleColor(new java.awt.Color(51, 0, 0));
        rSMaterialButtonRectangle2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle2ActionPerformed(evt);
            }
        });
        jPanel16.add(rSMaterialButtonRectangle2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 270, 220, 50));

        jPanel1.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, -1, 400));

        jPanel14.setBackground(new java.awt.Color(153, 51, 0));
        jPanel14.setBorder(javax.swing.BorderFactory.createMatteBorder(50, 1, 1, 1, new java.awt.Color(51, 0, 0)));
        jPanel14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel16.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText(" Book Details");
        jLabel16.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel16.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel16.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel14.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 210, 50));

        lbl_returnDate.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        lbl_returnDate.setForeground(new java.awt.Color(255, 255, 255));
        jPanel14.add(lbl_returnDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 240, 320, 20));

        jLabel19.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Book Name :");
        jPanel14.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 120, -1, -1));

        jLabel20.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Member Name : ");
        jPanel14.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, -1, -1));

        lbl_iid.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        lbl_iid.setForeground(new java.awt.Color(255, 255, 255));
        jPanel14.add(lbl_iid, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 290, 20));

        lbl_bookname.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        lbl_bookname.setForeground(new java.awt.Color(255, 255, 255));
        jPanel14.add(lbl_bookname, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 120, 290, 20));

        lbl_memName.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        lbl_memName.setForeground(new java.awt.Color(255, 255, 255));
        jPanel14.add(lbl_memName, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 160, 300, 20));

        jLabel25.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Issue Date :");
        jPanel14.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 200, -1, -1));

        lbl_idate.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        lbl_idate.setForeground(new java.awt.Color(255, 255, 255));
        jPanel14.add(lbl_idate, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 200, 320, 20));

        jLabel28.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icons/adminIcons/adminIcons/icons8_View_Details_26px.png"))); // NOI18N
        jLabel28.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel28.setFocusable(false);
        jLabel28.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel28.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel14.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 50, 30));

        jLabel22.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Issue ID :");
        jPanel14.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, -1, -1));

        jLabel21.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Return Date :");
        jPanel14.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 240, -1, -1));

        jPanel1.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 30, 550, 280));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/main/Artboard 2 .png"))); // NOI18N
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
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_bookidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_bookidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_bookidActionPerformed

    private void rSMaterialButtonRectangle2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle2ActionPerformed
        // TODO add your handling code here:
       getissueBookDetails() ;
       setFinesAmounttoLable();
       calDates();
       
    }//GEN-LAST:event_rSMaterialButtonRectangle2ActionPerformed

    private void txt_memidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_memidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_memidActionPerformed

    private void rSMaterialButtonRectangle1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle1ActionPerformed
        // TODO add your handling code here:
        if (returnBook() == true) {
            JOptionPane.showMessageDialog(this, "Book Return Successfully ");
            
            updateBookCount();
            setFinesAmounttoLable();  
            
        } else {
            JOptionPane.showMessageDialog(this, "Book Returned Fail");
        }
    }//GEN-LAST:event_rSMaterialButtonRectangle1ActionPerformed

    private void FinespayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FinespayActionPerformed
        // TODO add your handling code here:
        
     if(pay()==true){
        JOptionPane.showMessageDialog(this,"Payed Successfully");
          clerFineTable();
          setFinesToTable();
       }else{
          JOptionPane.showMessageDialog(this,"Payment Faild");
       }
        
        
    }//GEN-LAST:event_FinespayActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojerusan.RSMaterialButtonRectangle Finespay;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbl_Error;
    private javax.swing.JLabel lbl_bookname;
    private javax.swing.JLabel lbl_fday;
    private javax.swing.JLabel lbl_fday1;
    private javax.swing.JLabel lbl_idate;
    private javax.swing.JLabel lbl_iid;
    private javax.swing.JLabel lbl_ldays;
    private javax.swing.JLabel lbl_memName;
    private javax.swing.JLabel lbl_returnDate;
    private javax.swing.JLabel lbl_tf;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle1;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle2;
    private rojeru_san.complementos.RSTableMetro tbl_FDetails;
    private javax.swing.JTextField txt_bookid;
    private javax.swing.JTextField txt_memid;
    // End of variables declaration//GEN-END:variables
}
