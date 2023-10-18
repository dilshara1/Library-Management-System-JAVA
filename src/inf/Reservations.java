/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package inf;

import database.DbConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import javax.swing.JTable;
/**
 *
 * @author Dilshara Mithum
 */
public class Reservations extends javax.swing.JInternalFrame {

      Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    DefaultTableModel model;

    
    
    /**
     * Creates new form IssueBooks
     */
    public Reservations() {
        initComponents();
        
        setIBDetailsToTable();
        
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        BasicInternalFrameUI ui=(BasicInternalFrameUI)this.getUI();
        ui.setNorthPane(null);
    }
    
    
    //Get Reservation Details to Table
    public void setIBDetailsToTable() {

        try {
            conn = DbConnect.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("Select * from reservations where  Status='"+"Reserved"+"' ");

            while (rs.next()) {
                String id = rs.getString("ID");
                String bookID = rs.getString("BookID");
                String bookName = rs.getString("BookName");
                String memID = rs.getString("MemberID");
                String memName = rs.getString("MemberName");
                Date iDate = rs.getDate("ReserveDate");
                Date rDate = rs.getDate("ToReserveDate");
                String status = rs.getString("Status");

                Object[] obj = {id,bookID, bookName,memID, memName, iDate, rDate, status};
                model = (DefaultTableModel) tbl_rDetails.getModel();
                model.addRow(obj);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

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
           
              lbl_Bookname.setText(rs.getString("BookName"));
      
              
          }else
          {
          
             lbl_Bookname.setText("Invalid Book ID");
              
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
          
              lbl_MemberName.setText(rs.getString("Name"));
        
          }else{
              lbl_MemberName.setText("Invalid Member ID");
             
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
    

    
     //insert Reserve book details to database

    public boolean reserveBook(){
        
        boolean isreserve=false;
   
      
        int bookId=Integer.parseInt(txt_bookid.getText());
        int memID=Integer.parseInt(txt_memid.getText());
        
        String bookName=lbl_Bookname.getText();
         String memName=lbl_MemberName.getText();
    
        Date RIDate=date1.getDatoFecha();
        Date REDate=date2.getDatoFecha();
        
       Long l1=RIDate.getTime();
        Long l2=REDate.getTime();
        
        java.sql.Date RIDate1=new java.sql.Date(l1);
        java.sql.Date REDate2=new java.sql.Date(l2);
             
          
          try {
             conn=DbConnect.getConnection();
             String sql="insert into reservations(BookID,BookName,MemberID,MemberName,ReserveDate,ToReserveDate,Status) values(?,?,?,?,?,?,?)";
              pst=conn.prepareStatement(sql);
              pst.setInt(1, bookId);   
             pst.setString(2,bookName);
              pst.setInt(3,memID); 
               pst.setString(4,memName);
              pst.setDate(5,RIDate1);
              pst.setDate(6, REDate2);
              pst.setString(7,"Reserved");
              
              int rowCount=pst.executeUpdate();
              if(rowCount>0){
                  isreserve=true;
              }else{
                  isreserve=false;
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
       

        
         return isreserve;
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
    
    
    
     //Clear REserve Table
    
    public void clerTable(){
        DefaultTableModel model=(DefaultTableModel) tbl_rDetails.getModel();
        model.setRowCount(0);
    }
    
    
 
    
    //insert isseue book details to database
    
    public boolean issueBook(){
        
        boolean isissue=false;
      
      
        
      
        int bookId=Integer.parseInt(txt_bookid.getText());
        int memID=Integer.parseInt(txt_memid.getText());
        String bookName=lbl_Bookname.getText();
        String memName=lbl_MemberName.getText();
        
        Date uIssueDate=date1.getDatoFecha();
        Date uRetunDate=date2.getDatoFecha();
        
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
           
        
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e);
        }finally{
            try {
                rs.close();
            pst.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,"Enter Date Range");
            }
        }
       
        
         return isissue;
    }
    
     
    //Check Already Reserved Check
    
    //Check Already Issue Bokk for Same Member
    
    public boolean isReserved(){
        boolean isReserved=false;
        int bookId=Integer.parseInt(txt_bookid.getText());
         int memID=Integer.parseInt(txt_memid.getText());
         
         try {
         conn=DbConnect.getConnection();
         String sql="select * from reservations where BookID=? and MemberID=? and Status=? ";
         pst=conn.prepareStatement(sql);
         pst.setInt(1, bookId);
         pst.setInt(2, memID);
         pst.setString(3,"Reserved");
         
        rs=pst.executeQuery();
        
         if(rs.next())
         {
             isReserved=true;
         } else
         {
             isReserved=false;
         }
         
         
          
         
         } catch (Exception e) {
               JOptionPane.showMessageDialog(this,"Add Correct Details"+e);
        }finally{
            try {
                rs.close();
            pst.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
       
          
         return isReserved;
    }
    
    
    
    
    
    
    
    
    
    //Updating Reserve Data to Release count

    public void updateReserveToRelease(){
        int bookId=Integer.parseInt(txt_bookid.getText());
         int memID=Integer.parseInt(txt_memid.getText());
  
  
        
        try {
            conn=DbConnect.getConnection();
            String sql="update reservations set Status=? where BookID=? AND MemberID=? ";
            pst=conn.prepareStatement(sql);
            pst.setString(1,"Released");
            pst.setInt(2,bookId);
             pst.setInt(3,memID);
            int rowCount=pst.executeUpdate();
            
            if(rowCount>0){
                JOptionPane.showMessageDialog(this,"Book Releaseded ");
               
              }else{
               JOptionPane.showMessageDialog(this,"Can't Releaseded");
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
    
    
   //Updating Reserve Data to Release count when it Canseld 
    
    public boolean canseld() {
        boolean iscanseld = false;
        int bookID = Integer.parseInt(txt_bookid.getText());
        int memID = Integer.parseInt(txt_memid.getText());
        try {
            conn = DbConnect.getConnection();
            String sql = "update reservations set Status=? where MemberID=? and BookID=? ";
            pst = conn.prepareStatement(sql);
          
             pst.setString(1, "Released");
            pst.setInt(2, memID);
            pst.setInt(3, bookID);
         

            int rowCount = pst.executeUpdate();
            if (rowCount > 0) {
                iscanseld = true;
            } else {
                iscanseld = false;
            }
            
      
              
                
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return iscanseld;
        
        
        
        
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
        tbl_rDetails = new rojeru_san.complementos.RSTableMetro();
        jPanel16 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        btn_export = new rojerusan.RSMaterialButtonRectangle();
        jLabel14 = new javax.swing.JLabel();
        txt_bookid = new javax.swing.JTextField();
        txt_memid = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        date1 = new rojeru_san.componentes.RSDateChooser();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        date2 = new rojeru_san.componentes.RSDateChooser();
        btn_releaseBook = new rojerusan.RSMaterialButtonRectangle();
        jLabel17 = new javax.swing.JLabel();
        btn_ReserveBook = new rojerusan.RSMaterialButtonRectangle();
        lbl_MemberName = new javax.swing.JLabel();
        lbl_Bookname = new javax.swing.JLabel();
        btn_releaseBook1 = new rojerusan.RSMaterialButtonRectangle();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 0, 0));
        setMaximumSize(new java.awt.Dimension(990, 670));
        setMinimumSize(new java.awt.Dimension(990, 670));
        setPreferredSize(new java.awt.Dimension(990, 670));

        jPanel1.setMaximumSize(new java.awt.Dimension(990, 670));
        jPanel1.setMinimumSize(new java.awt.Dimension(990, 670));
        jPanel1.setPreferredSize(new java.awt.Dimension(990, 670));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_rDetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "BookID", "BookName", "MemberID", "MemberName", "Reserve Date", "ToReserve", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false, true, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_rDetails.setColorBackgoundHead(new java.awt.Color(51, 0, 0));
        tbl_rDetails.setColorFilasBackgound2(new java.awt.Color(247, 230, 220));
        tbl_rDetails.setColorFilasForeground1(new java.awt.Color(153, 51, 0));
        tbl_rDetails.setColorFilasForeground2(new java.awt.Color(153, 51, 0));
        tbl_rDetails.setColorSelBackgound(new java.awt.Color(153, 51, 0));
        tbl_rDetails.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        tbl_rDetails.setFuenteFilas(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        tbl_rDetails.setFuenteFilasSelect(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        tbl_rDetails.setFuenteHead(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        tbl_rDetails.setGrosorBordeFilas(0);
        tbl_rDetails.setGrosorBordeHead(0);
        tbl_rDetails.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_rDetailsMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_rDetails);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 950, 360));

        jPanel16.setBackground(new java.awt.Color(153, 51, 0));
        jPanel16.setBorder(javax.swing.BorderFactory.createMatteBorder(80, 0, 0, 0, new java.awt.Color(51, 0, 0)));
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icons/pic40/icons8_book_40px.png"))); // NOI18N
        jLabel13.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel13.setFocusable(false);
        jLabel13.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel13.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel16.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 40, 50));

        btn_export.setBackground(new java.awt.Color(204, 51, 0));
        btn_export.setText("Export ");
        btn_export.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        btn_export.setRippleColor(new java.awt.Color(51, 0, 0));
        btn_export.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_exportActionPerformed(evt);
            }
        });
        jPanel16.add(btn_export, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 10, 120, 60));

        jLabel14.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Book ID :");
        jPanel16.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, -1, -1));

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
        jPanel16.add(txt_bookid, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 260, 30));

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
        jPanel16.add(txt_memid, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 260, 30));

        jLabel15.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText(" Date:");
        jPanel16.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 80, -1, -1));

        date1.setBackground(new java.awt.Color(51, 0, 0));
        date1.setForeground(new java.awt.Color(51, 0, 0));
        date1.setToolTipText("");
        date1.setColorBackground(new java.awt.Color(204, 51, 0));
        date1.setColorButtonHover(new java.awt.Color(255, 102, 0));
        date1.setColorForeground(new java.awt.Color(51, 0, 0));
        date1.setColorSelForeground(new java.awt.Color(51, 0, 0));
        date1.setColorTextDiaActual(new java.awt.Color(51, 0, 0));
        date1.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        date1.setPlaceholder("     Select date");
        jPanel16.add(date1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 110, 260, -1));

        jLabel26.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("Member ID :");
        jPanel16.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, -1, -1));

        jLabel27.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Due Date");
        jPanel16.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 160, -1, -1));

        date2.setBackground(new java.awt.Color(51, 0, 0));
        date2.setForeground(new java.awt.Color(51, 0, 0));
        date2.setColorBackground(new java.awt.Color(204, 51, 0));
        date2.setColorButtonHover(new java.awt.Color(255, 102, 0));
        date2.setColorForeground(new java.awt.Color(51, 0, 0));
        date2.setColorSelForeground(new java.awt.Color(51, 0, 0));
        date2.setColorTextDiaActual(new java.awt.Color(51, 0, 0));
        date2.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        date2.setPlaceholder("     Select date");
        jPanel16.add(date2, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 190, 260, -1));

        btn_releaseBook.setBackground(new java.awt.Color(204, 51, 0));
        btn_releaseBook.setText("Cansel R");
        btn_releaseBook.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        btn_releaseBook.setRippleColor(new java.awt.Color(51, 0, 0));
        btn_releaseBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_releaseBookActionPerformed(evt);
            }
        });
        jPanel16.add(btn_releaseBook, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 180, 130, 50));

        jLabel17.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Reservation");
        jLabel17.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel17.setFocusable(false);
        jLabel17.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel17.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel16.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 260, 50));

        btn_ReserveBook.setBackground(new java.awt.Color(204, 51, 0));
        btn_ReserveBook.setText("Reserve Book");
        btn_ReserveBook.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        btn_ReserveBook.setRippleColor(new java.awt.Color(51, 0, 0));
        btn_ReserveBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ReserveBookActionPerformed(evt);
            }
        });
        jPanel16.add(btn_ReserveBook, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 110, 260, 50));

        lbl_MemberName.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        lbl_MemberName.setForeground(new java.awt.Color(255, 204, 0));
        jPanel16.add(lbl_MemberName, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 170, 160, 30));

        lbl_Bookname.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        lbl_Bookname.setForeground(new java.awt.Color(255, 204, 0));
        jPanel16.add(lbl_Bookname, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 90, 160, 30));

        btn_releaseBook1.setBackground(new java.awt.Color(204, 51, 0));
        btn_releaseBook1.setText("Issue RBook");
        btn_releaseBook1.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        btn_releaseBook1.setRippleColor(new java.awt.Color(51, 0, 0));
        btn_releaseBook1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_releaseBook1ActionPerformed(evt);
            }
        });
        jPanel16.add(btn_releaseBook1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 180, 130, 50));

        jPanel1.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 950, 270));

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
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_bookidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_bookidActionPerformed
        // TODO add your handling code here:
        
      
    }//GEN-LAST:event_txt_bookidActionPerformed

    private void btn_releaseBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_releaseBookActionPerformed
        // TODO add your handling code here:
        
        
      if(canseld()==true){
           JOptionPane.showMessageDialog(this,"Book Canceld Successfully");
  
           clerTable();
           setIBDetailsToTable();
          }else{
           JOptionPane.showMessageDialog(this,"Can't Canceld Book");
           }
        
    }//GEN-LAST:event_btn_releaseBookActionPerformed

    private void btn_ReserveBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ReserveBookActionPerformed
        // TODO add your handling code here:
        
       if(isReserved()==false){
           if(reserveBook()==true){
           JOptionPane.showMessageDialog(this,"Book Reserve Successfully");
           
           updateBookCount();
           clerTable();
           setIBDetailsToTable();
          }else{
           JOptionPane.showMessageDialog(this,"Can't Reserve Book");
           }                                             
       }else{
           JOptionPane.showMessageDialog(this,"Already Reserved this book for this member");
       }
   
       
    }//GEN-LAST:event_btn_ReserveBookActionPerformed

    private void tbl_rDetailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_rDetailsMouseClicked
        // TODO add your handling code here:
          int rowNo=tbl_rDetails.getSelectedRow();
        TableModel model=tbl_rDetails.getModel();

        //aset text areas
        txt_bookid.setText(model.getValueAt(rowNo,1).toString());
        
        txt_memid.setText(model.getValueAt(rowNo, 3).toString());
        
       
    }//GEN-LAST:event_tbl_rDetailsMouseClicked

    private void txt_memidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_memidActionPerformed
        // TODO add your handling code here:
        
    
    }//GEN-LAST:event_txt_memidActionPerformed

    private void txt_bookidFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_bookidFocusLost
        // TODO add your handling code here:
          getBookDetails();
    }//GEN-LAST:event_txt_bookidFocusLost

    private void txt_memidFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_memidFocusLost
        // TODO add your handling code here:
            getMemDetails();
    }//GEN-LAST:event_txt_memidFocusLost

    private void btn_releaseBook1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_releaseBook1ActionPerformed
        // TODO add your handling code here:]
        
 if(isReserved()==true){
        
           if(issueBook()==true){
               updateReserveToRelease();
               clerTable();
               setIBDetailsToTable();
           JOptionPane.showMessageDialog(this,"Book Issued Successfully");
 
           
          }else{
           JOptionPane.showMessageDialog(this,"Can't Issue Book");
              }                                             
    
}else{
JOptionPane.showMessageDialog(this,"This Book Not Reserved for This Member");
}

    }//GEN-LAST:event_btn_releaseBook1ActionPerformed

    private void btn_exportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_exportActionPerformed

        // TODO add your handling code here:

    
         
        MessageFormat header=new MessageFormat("Reservation Reort From");
        MessageFormat footer=new MessageFormat("page{0,number,integer}");
        try {
            tbl_rDetails.print(JTable.PrintMode.FIT_WIDTH,header,footer);
        } catch (Exception e) {
            e.getMessage();
        }
    }//GEN-LAST:event_btn_exportActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojerusan.RSMaterialButtonRectangle btn_ReserveBook;
    private rojerusan.RSMaterialButtonRectangle btn_export;
    private rojerusan.RSMaterialButtonRectangle btn_releaseBook;
    private rojerusan.RSMaterialButtonRectangle btn_releaseBook1;
    private rojeru_san.componentes.RSDateChooser date1;
    private rojeru_san.componentes.RSDateChooser date2;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbl_Bookname;
    private javax.swing.JLabel lbl_MemberName;
    private rojeru_san.complementos.RSTableMetro tbl_rDetails;
    private javax.swing.JTextField txt_bookid;
    private javax.swing.JTextField txt_memid;
    // End of variables declaration//GEN-END:variables
}
