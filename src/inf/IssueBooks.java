/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package inf;

import database.DbConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Dilshara Mithum
 */
public class IssueBooks extends javax.swing.JInternalFrame {

    /**
     * Creates new form IssueBooks
     */
    
    String memName,memEmail;
    int memID,memNum;
    
    
    String bookName,author;
    int bookId,quantity,bookShelf;
    
    // TableModel that uses a Vector of Vectors to store the cell value objects
    DefaultTableModel model;
    
    
    

    Connection conn=null;
    PreparedStatement pst=null;
    ResultSet rs=null;
    
    
    public IssueBooks() {
        initComponents();
             
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        BasicInternalFrameUI ui=(BasicInternalFrameUI)this.getUI();
        ui.setNorthPane(null);
    }
    
    
    //get database books data to labels 
    public void getBookDetails(){
        int bookId=Integer.parseInt(txt_bookid.getText()); //convert to integer
        try {
          conn=DbConnect.getConnection();
          pst=conn.prepareStatement("Select * from bookdetails where BookID=?");
          pst.setInt(1, bookId);
          rs=pst.executeQuery();
          if(rs.next()){
              lbl_bookid.setText(rs.getString("BookID"));
              lbl_bookname.setText(rs.getString("BookName"));
              lbl_author.setText(rs.getString("Author"));
              lbl_quantity.setText(rs.getString("Quantity"));
              
          }else
          {
          
             lbl_validbook.setText("Invalid Book ID");
              
          }
           
          
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
       
    }
   
    
    //get database member data to labels 
    public void getMemDetails(){
         long l = System.currentTimeMillis();
        java.sql.Date TD = new java.sql.Date(l);
        
        
        
        
        
        int memID=Integer.parseInt(txt_memid.getText()); //convert to integer
        try {
          conn=DbConnect.getConnection();
          pst=conn.prepareStatement("Select * from members where MemberID=?");
          pst.setInt(1, memID);
          rs=pst.executeQuery();
          if(rs.next()){
              lbl_memid.setText(rs.getString("MemberID"));
              lbl_memname.setText(rs.getString("Name"));
              
          
          
          }else{
              lbl_validmem.setText("Invalid Member ID");
             
          }

       //show number of Issued Books

       /*
            
            
      */
      
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }finally{
            try {
                rs.close();
            pst.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        
        }
        
        
        try {
            pst = conn.prepareStatement("Select COUNT(BookID) from issueBooks where Status='" + "Pending" + "' and MemberID=?  ");
            pst.setInt(1, memID);
            
            
            rs = pst.executeQuery();
          
          if(rs.next()){
            
            
            String ci = rs.getString("COUNT(BookID)");
            lbl_nibooks.setText(ci);
          }
          
          
          

      } catch (Exception e) {
                 JOptionPane.showMessageDialog(null, e);
            }finally{
            try {
                rs.close();
            pst.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        
        }
      //set Defaulter book count
      
        
        try {
      pst = conn.prepareStatement("Select COUNT(id) AS ci from issueBooks where ReturnDate<? and status=? and MemberID=?");
            pst.setDate(1, TD);
            pst.setString(2, "Pending");
            pst.setInt(3, memID);
      
            rs = pst.executeQuery();
            if(rs.next()){
            
            
            String cd = (rs.getString("ci"));
            lbl_ndbooks.setText(cd);
          }
          
           
           
            
            } catch (Exception e) {
                 JOptionPane.showMessageDialog(null, e);
            }finally{
            try {
                rs.close();
            pst.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        
        }
        
        
        
        
       
    }
    
    //insert isseue book details to database
    
    public boolean issueBook(){
        
        boolean isissue=false;
      
      
        
      
        int bookId=Integer.parseInt(txt_bookid.getText());
        int memID=Integer.parseInt(txt_memid.getText());
        String bookName=lbl_bookname.getText();
        String memName=lbl_memname.getText();
        
        Date uIssueDate=issuedate.getDatoFecha();
        Date uRetunDate=returndate.getDatoFecha();
        
       Long l1=uIssueDate.getTime();
        Long l2=uRetunDate.getTime();
        
        java.sql.Date sIssueDate=new java.sql.Date(l1);
        java.sql.Date sRetunDate=new java.sql.Date(l2);
       
       
    
        
     try {
             conn=DbConnect.getConnection();
             String sql="insert into issueBooks(BookId,BookName,MemberId,MemberName,IssueDate,ReturnDate,Status) values(?,?,?,?,?,?,?)";
              pst=conn.prepareStatement(sql);
              pst.setInt(1, bookId);
              pst.setString(2, bookName);
              pst.setInt(3,memID);
              pst.setString(4,memName);
              pst.setDate(5,sIssueDate);
              pst.setDate(6, sRetunDate);
              pst.setString(7,"Pending");
              
              int rowCount=pst.executeUpdate();
              if(rowCount>0){
                  isissue=true;
              }else{
                  isissue=false;
              }
              rs.close();
            pst.close();  
        
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e);
        }finally{
            try {
                rs.close();
            pst.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
       
        
         return isissue;
    }
    
    
    
    //Updating book count
    
    public void updateBookCount(){
        int bookId=Integer.parseInt(txt_bookid.getText());
        try {
            conn=DbConnect.getConnection();
            String sql="update bookdetails set Quantity=Quantity-1 where BookID=?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,bookId);
            int rowCount=pst.executeUpdate();
            
            if(rowCount>0){
                JOptionPane.showMessageDialog(this,"Book Count Updated");
                int inicount=Integer.parseInt(lbl_quantity.getText());
                lbl_quantity.setText(Integer.toString(inicount-1));
              }else{
                     JOptionPane.showMessageDialog(this,"Can't Updated Book Count");
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
    
    
    //Check Already Issue Bokk for Same Member
    
    public boolean isAIssue(){
        boolean isAIssued=false;
        int bookId=Integer.parseInt(txt_bookid.getText());
         int memID=Integer.parseInt(txt_memid.getText());
         
         try {
         conn=DbConnect.getConnection();
         String sql="select * from issueBooks where BookID=? and MemberID=? and Status=? ";
         pst=conn.prepareStatement(sql);
         pst.setInt(1, bookId);
         pst.setInt(2, memID);
         pst.setString(3,"Pending");
         
        rs=pst.executeQuery();
        
         if(rs.next())
         {
             isAIssued=true;
         } else
         {
             isAIssued=false;
         }
         
         
          
         
         } catch (Exception e) {
             JOptionPane.showMessageDialog(this,e);
        }finally{
            try {
                rs.close();
            pst.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
       
          
         return isAIssued;
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
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        lbl_memid = new javax.swing.JLabel();
        lbl_ndbooks = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        lbl_memname = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        lbl_nibooks = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        lbl_quantity = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        lbl_bookid = new javax.swing.JLabel();
        lbl_bookname = new javax.swing.JLabel();
        lbl_author = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txt_bookid = new javax.swing.JTextField();
        txt_memid = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        issuedate = new rojeru_san.componentes.RSDateChooser();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        returndate = new rojeru_san.componentes.RSDateChooser();
        btn_issuebook = new rojerusan.RSMaterialButtonRectangle();
        jLabel36 = new javax.swing.JLabel();
        lbl_validmem = new javax.swing.JLabel();
        lbl_validbook = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 0, 0));
        setMaximumSize(new java.awt.Dimension(990, 670));
        setMinimumSize(new java.awt.Dimension(990, 670));
        setPreferredSize(new java.awt.Dimension(990, 670));

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 570, 480, -1));
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 570, 480, -1));

        jPanel15.setBackground(new java.awt.Color(153, 51, 0));
        jPanel15.setBorder(javax.swing.BorderFactory.createMatteBorder(50, 1, 1, 1, new java.awt.Color(51, 0, 0)));
        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel25.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText(" Member Details");
        jLabel25.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel25.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel25.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel15.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 0, 260, 50));

        jLabel28.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("No of Issued Books :");
        jPanel15.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, -1, -1));

        jLabel30.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText("Member ID :");
        jPanel15.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, -1, -1));

        lbl_memid.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        lbl_memid.setForeground(new java.awt.Color(255, 255, 255));
        jPanel15.add(lbl_memid, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 80, 290, 20));

        lbl_ndbooks.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        lbl_ndbooks.setForeground(new java.awt.Color(255, 255, 255));
        jPanel15.add(lbl_ndbooks, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 200, 290, 20));

        jLabel29.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("Member Name :");
        jPanel15.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 120, -1, -1));

        lbl_memname.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        lbl_memname.setForeground(new java.awt.Color(255, 255, 255));
        jPanel15.add(lbl_memname, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 120, 290, 20));

        jLabel34.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 255, 255));
        jLabel34.setText("No of Defaulter Books :");
        jPanel15.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 200, -1, -1));

        lbl_nibooks.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        lbl_nibooks.setForeground(new java.awt.Color(255, 255, 255));
        jPanel15.add(lbl_nibooks, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 160, 290, 20));

        jLabel16.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icons/adminIcons/adminIcons/icons8_people_40px.png"))); // NOI18N
        jLabel16.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel16.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel16.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel15.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(-40, 0, 190, 50));

        jPanel1.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 300, 510, 250));

        jPanel14.setBackground(new java.awt.Color(153, 51, 0));
        jPanel14.setBorder(javax.swing.BorderFactory.createMatteBorder(50, 1, 1, 1, new java.awt.Color(51, 0, 0)));
        jPanel14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel17.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Quantity :");
        jPanel14.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 200, -1, -1));

        lbl_quantity.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        lbl_quantity.setForeground(new java.awt.Color(255, 255, 255));
        jPanel14.add(lbl_quantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 200, 320, 20));

        jLabel19.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Book Name :");
        jPanel14.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 120, -1, -1));

        jLabel20.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Author :");
        jPanel14.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, -1, -1));

        jLabel21.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Book ID :");
        jPanel14.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, -1, -1));

        lbl_bookid.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        lbl_bookid.setForeground(new java.awt.Color(255, 255, 255));
        jPanel14.add(lbl_bookid, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 290, 20));

        lbl_bookname.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        lbl_bookname.setForeground(new java.awt.Color(255, 255, 255));
        jPanel14.add(lbl_bookname, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 120, 290, 20));

        lbl_author.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        lbl_author.setForeground(new java.awt.Color(255, 255, 255));
        jPanel14.add(lbl_author, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 160, 320, 20));

        jLabel37.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setText(" Book Details");
        jLabel37.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel37.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel37.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel14.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 0, 190, 50));

        jLabel38.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icons/adminIcons/adminIcons/icons8_copybook_40px.png"))); // NOI18N
        jLabel38.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel38.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel38.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel14.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(-40, 0, 190, 50));

        jPanel1.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 30, 510, 250));

        jPanel16.setBackground(new java.awt.Color(153, 51, 0));
        jPanel16.setBorder(javax.swing.BorderFactory.createMatteBorder(80, 0, 0, 0, new java.awt.Color(51, 0, 0)));
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icons/adminIcons/adminIcons/icons8_give_50px.png"))); // NOI18N
        jLabel13.setText(" ");
        jLabel13.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel13.setFocusable(false);
        jLabel13.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel13.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel16.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, 60, 50));

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
        txt_bookid.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_bookidFocusLost(evt);
            }
        });
        txt_bookid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_bookidActionPerformed(evt);
            }
        });
        jPanel16.add(txt_bookid, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 260, 30));

        txt_memid.setBackground(new java.awt.Color(51, 0, 0));
        txt_memid.setForeground(new java.awt.Color(255, 255, 255));
        txt_memid.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_memid.setToolTipText("");
        txt_memid.setBorder(null);
        txt_memid.setCaretColor(new java.awt.Color(255, 255, 255));
        txt_memid.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txt_memid.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_memidFocusLost(evt);
            }
        });
        txt_memid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_memidActionPerformed(evt);
            }
        });
        jPanel16.add(txt_memid, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 260, 30));

        jLabel15.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Issue Date:");
        jPanel16.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, -1, -1));

        issuedate.setBackground(new java.awt.Color(51, 0, 0));
        issuedate.setForeground(new java.awt.Color(51, 0, 0));
        issuedate.setToolTipText("");
        issuedate.setColorBackground(new java.awt.Color(204, 51, 0));
        issuedate.setColorButtonHover(new java.awt.Color(255, 102, 0));
        issuedate.setColorForeground(new java.awt.Color(51, 0, 0));
        issuedate.setColorSelForeground(new java.awt.Color(51, 0, 0));
        issuedate.setColorTextDiaActual(new java.awt.Color(51, 0, 0));
        issuedate.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        issuedate.setPlaceholder("     Select date");
        jPanel16.add(issuedate, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 260, -1));

        jLabel26.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("Member ID :");
        jPanel16.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, -1, -1));

        jLabel27.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Return Date:");
        jPanel16.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, -1, -1));

        returndate.setBackground(new java.awt.Color(51, 0, 0));
        returndate.setForeground(new java.awt.Color(51, 0, 0));
        returndate.setColorBackground(new java.awt.Color(204, 51, 0));
        returndate.setColorButtonHover(new java.awt.Color(255, 102, 0));
        returndate.setColorForeground(new java.awt.Color(51, 0, 0));
        returndate.setColorSelForeground(new java.awt.Color(51, 0, 0));
        returndate.setColorTextDiaActual(new java.awt.Color(51, 0, 0));
        returndate.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        returndate.setPlaceholder("     Select date");
        jPanel16.add(returndate, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, 260, -1));

        btn_issuebook.setBackground(new java.awt.Color(204, 51, 0));
        btn_issuebook.setText("Issue Book");
        btn_issuebook.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        btn_issuebook.setRippleColor(new java.awt.Color(51, 0, 0));
        btn_issuebook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_issuebookActionPerformed(evt);
            }
        });
        jPanel16.add(btn_issuebook, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 440, 220, 50));

        jLabel36.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setText("    Issue Book");
        jLabel36.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel36.setFocusable(false);
        jLabel36.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel36.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel16.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, 180, 50));

        lbl_validmem.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        lbl_validmem.setForeground(new java.awt.Color(255, 204, 0));
        jPanel16.add(lbl_validmem, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 190, 140, 30));

        lbl_validbook.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        lbl_validbook.setForeground(new java.awt.Color(255, 204, 0));
        jPanel16.add(lbl_validbook, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 110, 160, 30));

        jPanel1.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 300, 510));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/main/Artboard 1.png"))); // NOI18N
        jLabel2.setText("jLabel2");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 990, 690));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_bookidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_bookidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_bookidActionPerformed

    private void txt_memidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_memidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_memidActionPerformed

    private void btn_issuebookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_issuebookActionPerformed
        // TODO add your handling code here:
        
    if (lbl_quantity.getText().equals("0")){
        JOptionPane.showMessageDialog(this,"This Book is not Available");
    } else{  
        
        
        
        
      if(isAIssue()==false){
        
           if(issueBook()==true){
           JOptionPane.showMessageDialog(this,"Book Issued Successfully");
           
           updateBookCount();
          }else{
           JOptionPane.showMessageDialog(this,"Can't Issue Book");
    }//GEN-LAST:event_btn_issuebookActionPerformed
   } else{
JOptionPane.showMessageDialog(this,"This Member Already has this Book");
}
   
 }

    }

    private void txt_bookidFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_bookidFocusLost
        // TODO add your handling code here:
        if(!txt_bookid.getText().equals("")){
        getBookDetails(); 
        }
    }//GEN-LAST:event_txt_bookidFocusLost

    private void txt_memidFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_memidFocusLost
        // TODO add your handling code here:
         if(!txt_memid.getText().equals("")){
        getMemDetails(); 
        }
        
        
    }//GEN-LAST:event_txt_memidFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojerusan.RSMaterialButtonRectangle btn_issuebook;
    private rojeru_san.componentes.RSDateChooser issuedate;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JLabel lbl_author;
    private javax.swing.JLabel lbl_bookid;
    private javax.swing.JLabel lbl_bookname;
    private javax.swing.JLabel lbl_memid;
    private javax.swing.JLabel lbl_memname;
    private javax.swing.JLabel lbl_ndbooks;
    private javax.swing.JLabel lbl_nibooks;
    private javax.swing.JLabel lbl_quantity;
    private javax.swing.JLabel lbl_validbook;
    private javax.swing.JLabel lbl_validmem;
    private rojeru_san.componentes.RSDateChooser returndate;
    private javax.swing.JTextField txt_bookid;
    private javax.swing.JTextField txt_memid;
    // End of variables declaration//GEN-END:variables
}
