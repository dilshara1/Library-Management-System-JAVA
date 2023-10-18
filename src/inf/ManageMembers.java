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
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;
import textfield.SearchOptinEvent;
import textfield.SearchOption;
import java.lang.String;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import javax.swing.JTable;
/**
 *
 * @author Dilshara Mithum
 */




public class ManageMembers extends javax.swing.JInternalFrame {

    /**
     * Creates new form IssueBooks
     */
    String memName,memEmail;
    int memID,memNum;
    
    // TableModel that uses a Vector of Vectors to store the cell value objects
    DefaultTableModel model;
    
    
    

    Connection conn=null;
    PreparedStatement pst=null;
    ResultSet rs=null;
    
    public ManageMembers() {
        initComponents();
        
        conn=DbConnect.getConnection();
        setMemDetailsToTable();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        BasicInternalFrameUI ui=(BasicInternalFrameUI)this.getUI();
        ui.setNorthPane(null);
        
        
          try {
            //add search bar icons
        
      
        text_searchmem.addEventOptionSelected(new SearchOptinEvent() {
            @Override
            
            
            public void optionSelected(SearchOption option, int index) {
                text_searchmem.setHint("Search by " + option.getName() + "...");
            }
        });
        
        
         text_searchmem.addOption(new SearchOption("MemberID", new ImageIcon(getClass().getResource("id.png"))));
        text_searchmem.addOption(new SearchOption("Name", new ImageIcon(getClass().getResource("people.png"))));
        text_searchmem.addOption(new SearchOption("Email", new ImageIcon(getClass().getResource("email.png"))));
       text_searchmem.addOption(new SearchOption("ContactNumber", new ImageIcon(getClass().getResource("cnum.png"))));
     text_searchmem.setSelectedIndex(0);
        
       
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
        
    }
//load data in search bar
    
    
    private void loadData(String where,Object...search){
        
        try {
     conn=DbConnect.getConnection();
     pst=conn.prepareStatement("select * from members"+where);
     model.setRowCount(0);
            
            for(int i=0;i<search.length;i++){
           pst.setObject(i+1,search[i]);      
            }
            
            rs=pst.executeQuery();
            while(rs.next()){
              
                 String memID=rs.getString("MemberID");  
                String memName=rs.getString("Name");
               String  memEmail=rs.getString("Email");
                String memNum=rs.getString("ContactNum");
                 
                model =(DefaultTableModel)tbl_memDetails.getModel();
            
            
                model.addRow(new Object[]{tbl_memDetails.getRowCount(),memID,memName,memEmail,memNum});
            
            
            }
            
            rs.close();
            pst.close();
               
            
            
        } catch (Exception e) {
            
          //  JOptionPane.showMessageDialog(null,e);
         
          e.printStackTrace();
          
        }
        
        
        
        
    }
          
        
    
    
  
    
    
public void setMemDetailsToTable(){
        
        try {
        
         Statement st=conn.createStatement();
            ResultSet rs = st.executeQuery("Select * from members");
            
         while(rs.next()){
             String memID=rs.getString("MemberID");
             String memName=rs.getString("Name");
             String memEmail=rs.getString("Email");
             int memNum=rs.getInt("ContactNum");
             
              
             
             
             Object[] obj={memID,memName,memEmail,memNum,};
             model =(DefaultTableModel)tbl_memDetails.getModel();
             model.addRow(obj);
         }
            
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null,"Add Correct Details");      
        }
     
    }
    
    //memName,memEmail;
    // memID,memNum;
    
    public boolean addMem(){
        boolean isAdded=false;
        try{
      memID=Integer.parseInt(txt_memid.getText());
      memName=txt_memname.getText();
      memEmail=txt_memmail.getText();
      memNum=Integer.parseInt(txt_memcn.getText());
     
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Add Correct Details");
            //If Books Details incorrect return false
            return false;
        }
     
        try {
            conn=DbConnect.getConnection();
           String sql="insert into members values(?,?,?,?)";
           pst=conn.prepareStatement(sql);
           pst.setInt(1,memID);
           pst.setString(2,memName);
           pst.setString(3,memEmail);
           pst.setInt(4,memNum);
      
           
           int rowCount =pst.executeUpdate();
           if(rowCount>0){
               isAdded=true;
           }else{
               isAdded=false;
           }
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null,e);
        }
      return isAdded;
    }
    
    
     public boolean updateMem(){
        boolean isUpdated=false;
        
        try{
      memID=Integer.parseInt(txt_memid.getText());
      memName=txt_memname.getText();
      memEmail=txt_memmail.getText();
      memNum=Integer.parseInt(txt_memcn.getText());
     
  
    }catch(Exception e){
         JOptionPane.showMessageDialog(null,"Add Correct Details");
            //If Books Details incorrect return false
            return false;
    }
        
        try {
           conn=DbConnect.getConnection();
           String sql="update members set Name=?,Email=?,ContactNum=?  where MemberID=?";
           pst=conn.prepareStatement(sql);
           
           pst.setString(1,memName);
           pst.setString(2,memEmail);
           pst.setInt(3,memNum);
           pst.setInt(4,memID);
         
           
           int rowCount =pst.executeUpdate();
           if(rowCount>0){
               isUpdated=true;
           }else{
               isUpdated=false;
           }
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null,e);
        }
      return isUpdated;
    }
    
    
    
    public boolean deleteMem(){
        boolean isDeleted=false;
  
         try{
     memID=Integer.parseInt(txt_memid.getText());
     
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Wrong Member ID");
            //If Books Details incorrect return false
            return false;
        }
         
         try {
           conn=DbConnect.getConnection();
           String sql="delete from members where MemberID=?";
           pst=conn.prepareStatement(sql);
                   pst.setInt(1,memID);
           int rowCount =pst.executeUpdate();
           if(rowCount>0){
               isDeleted=true;
           }else{
               isDeleted=false;
           }
           
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e);
        }
         
           return isDeleted;
    }
    
    
    public void clerMemTable(){
        DefaultTableModel model=(DefaultTableModel) tbl_memDetails.getModel();
        model.setRowCount(0);
    }
    
    
    
    
    
    
    
    //Search by ID
    public void sbid(){
                
         int srch=Integer.parseInt(text_searchmem.getText());
         
         
        
        try {
         
           String sql="select * from members where MemberID LIKE '%"+srch+"%'   "; 
           pst=conn.prepareStatement(sql);
          
          /*
           pst.setString(1,srch);
           pst.setString(2,srch);
           pst.setString(3,srch);
           pst.setString(4,srch);
           pst.setString(5,srch);
           */
        rs=pst.executeQuery();
      //  pst.executeUpdate();
        tbl_memDetails.setModel(DbUtils.resultSetToTableModel(rs));
         
           
        
           
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    //Search by Name
   public void sbname(){
                
         String srch=text_searchmem.getText();
        
        try {
         
           String sql="select * from members where Name LIKE '%"+srch+"%'   "; 
           pst=conn.prepareStatement(sql);
          
          /*
           pst.setString(1,srch);
           pst.setString(2,srch);
           pst.setString(3,srch);
           pst.setString(4,srch);
           pst.setString(5,srch);
           */
        rs=pst.executeQuery();
      //  pst.executeUpdate();
        tbl_memDetails.setModel(DbUtils.resultSetToTableModel(rs));
         
           
        
           
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null,e);
        }
    }  
    
    //Search by Email
    public void sbmail(){
                
         String srch=text_searchmem.getText();
        
        try {
         
           String sql="select * from members where Email LIKE '%"+srch+"%'   "; 
           pst=conn.prepareStatement(sql);
          
          /*
           pst.setString(1,srch);
           pst.setString(2,srch);
           pst.setString(3,srch);
           pst.setString(4,srch);
           pst.setString(5,srch);
           */
        rs=pst.executeQuery();
      //  pst.executeUpdate();
        tbl_memDetails.setModel(DbUtils.resultSetToTableModel(rs));
         
           
        
           
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null,e);
        }
    }  
    
     //Search by Contact Number
    public void sbnum(){
                
         String srch=text_searchmem.getText();
        
        try {
         
           String sql="select * from members where ContactNum LIKE '%"+srch+"%'   "; 
           pst=conn.prepareStatement(sql);
          
          /*
           pst.setString(1,srch);
           pst.setString(2,srch);
           pst.setString(3,srch);
           pst.setString(4,srch);
           pst.setString(5,srch);
           */
        rs=pst.executeQuery();
      //  pst.executeUpdate();
        tbl_memDetails.setModel(DbUtils.resultSetToTableModel(rs));
         
           
        
           
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null,e);
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
        jPanel17 = new javax.swing.JPanel();
        text_searchmem = new textfield.TextFieldSearchOption();
        btn_update1 = new rojerusan.RSMaterialButtonRectangle();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_memDetails = new rojeru_san.complementos.RSTableMetro();
        jPanel16 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        btn_export = new rojerusan.RSMaterialButtonRectangle();
        txt_memid = new javax.swing.JTextField();
        txt_memname = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        btn_memDelete = new rojerusan.RSMaterialButtonRectangle();
        jLabel27 = new javax.swing.JLabel();
        txt_memmail = new javax.swing.JTextField();
        btn_memAdd = new rojerusan.RSMaterialButtonRectangle();
        btn_memUpdate = new rojerusan.RSMaterialButtonRectangle();
        txt_memcn = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 0, 0));
        setMaximumSize(new java.awt.Dimension(990, 670));
        setMinimumSize(new java.awt.Dimension(990, 670));
        setPreferredSize(new java.awt.Dimension(990, 670));

        jPanel1.setMaximumSize(new java.awt.Dimension(990, 670));
        jPanel1.setMinimumSize(new java.awt.Dimension(990, 670));
        jPanel1.setPreferredSize(new java.awt.Dimension(990, 670));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel17.setBackground(new java.awt.Color(51, 0, 0));
        jPanel17.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel17.setForeground(new java.awt.Color(51, 0, 0));
        jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        text_searchmem.setBackground(new java.awt.Color(153, 51, 0));
        text_searchmem.setForeground(new java.awt.Color(255, 255, 255));
        text_searchmem.setCaretColor(new java.awt.Color(255, 102, 0));
        text_searchmem.setColorOverlay1(new java.awt.Color(153, 51, 0));
        text_searchmem.setColorOverlay2(new java.awt.Color(51, 0, 0));
        text_searchmem.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        text_searchmem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        text_searchmem.setSelectedTextColor(new java.awt.Color(255, 255, 255));
        text_searchmem.setSelectionColor(new java.awt.Color(255, 153, 0));
        text_searchmem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_searchmemActionPerformed(evt);
            }
        });
        text_searchmem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_searchmemKeyReleased(evt);
            }
        });
        jPanel17.add(text_searchmem, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 460, 40));

        btn_update1.setBackground(new java.awt.Color(204, 51, 0));
        btn_update1.setText("Clear");
        btn_update1.setFont(new java.awt.Font("Poppins", 1, 10)); // NOI18N
        btn_update1.setRippleColor(new java.awt.Color(51, 0, 0));
        btn_update1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_update1ActionPerformed(evt);
            }
        });
        jPanel17.add(btn_update1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 10, 80, 40));

        jPanel1.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 40, 570, 60));

        tbl_memDetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "MemberID", "Name", "Email", "ContactNum"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_memDetails.setColorBackgoundHead(new java.awt.Color(51, 0, 0));
        tbl_memDetails.setColorFilasBackgound2(new java.awt.Color(247, 230, 220));
        tbl_memDetails.setColorFilasForeground1(new java.awt.Color(153, 51, 0));
        tbl_memDetails.setColorFilasForeground2(new java.awt.Color(153, 51, 0));
        tbl_memDetails.setColorSelBackgound(new java.awt.Color(153, 51, 0));
        tbl_memDetails.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        tbl_memDetails.setFuenteFilas(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        tbl_memDetails.setFuenteFilasSelect(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        tbl_memDetails.setFuenteHead(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        tbl_memDetails.setGrosorBordeFilas(0);
        tbl_memDetails.setGrosorBordeHead(0);
        tbl_memDetails.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_memDetailsMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_memDetails);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 110, 570, 480));

        jPanel16.setBackground(new java.awt.Color(153, 51, 0));
        jPanel16.setBorder(javax.swing.BorderFactory.createMatteBorder(80, 0, 0, 0, new java.awt.Color(51, 0, 0)));
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icons/pic40/icons8_people_40px_1.png"))); // NOI18N
        jLabel13.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel13.setFocusable(false);
        jLabel13.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel13.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel16.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 50, 50));

        jLabel14.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Member ID :");
        jPanel16.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, -1, -1));

        btn_export.setBackground(new java.awt.Color(204, 51, 0));
        btn_export.setText("Export ");
        btn_export.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        btn_export.setRippleColor(new java.awt.Color(51, 0, 0));
        btn_export.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_exportActionPerformed(evt);
            }
        });
        jPanel16.add(btn_export, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 490, 110, 50));

        txt_memid.setBackground(new java.awt.Color(51, 0, 0));
        txt_memid.setForeground(new java.awt.Color(255, 255, 255));
        txt_memid.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_memid.setToolTipText("");
        txt_memid.setBorder(null);
        txt_memid.setCaretColor(new java.awt.Color(255, 255, 255));
        txt_memid.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txt_memid.setMargin(new java.awt.Insets(2, 10, 2, 6));
        txt_memid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_memidActionPerformed(evt);
            }
        });
        jPanel16.add(txt_memid, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 260, 30));

        txt_memname.setBackground(new java.awt.Color(51, 0, 0));
        txt_memname.setForeground(new java.awt.Color(255, 255, 255));
        txt_memname.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_memname.setToolTipText("");
        txt_memname.setBorder(null);
        txt_memname.setCaretColor(new java.awt.Color(255, 255, 255));
        txt_memname.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txt_memname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_memnameActionPerformed(evt);
            }
        });
        jPanel16.add(txt_memname, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 260, 30));

        jLabel26.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("Member Name :");
        jPanel16.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, -1, -1));

        btn_memDelete.setBackground(new java.awt.Color(204, 51, 0));
        btn_memDelete.setText("Delete");
        btn_memDelete.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        btn_memDelete.setRippleColor(new java.awt.Color(51, 0, 0));
        btn_memDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_memDeleteActionPerformed(evt);
            }
        });
        jPanel16.add(btn_memDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 490, 110, 50));

        jLabel27.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Email :");
        jPanel16.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, -1, -1));

        txt_memmail.setBackground(new java.awt.Color(51, 0, 0));
        txt_memmail.setForeground(new java.awt.Color(255, 255, 255));
        txt_memmail.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_memmail.setToolTipText("");
        txt_memmail.setBorder(null);
        txt_memmail.setCaretColor(new java.awt.Color(255, 255, 255));
        txt_memmail.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txt_memmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_memmailActionPerformed(evt);
            }
        });
        jPanel16.add(txt_memmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, 260, 30));

        btn_memAdd.setBackground(new java.awt.Color(204, 51, 0));
        btn_memAdd.setText("Add");
        btn_memAdd.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        btn_memAdd.setRippleColor(new java.awt.Color(51, 0, 0));
        btn_memAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_memAddActionPerformed(evt);
            }
        });
        jPanel16.add(btn_memAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 430, 110, 50));

        btn_memUpdate.setBackground(new java.awt.Color(204, 51, 0));
        btn_memUpdate.setText("Update");
        btn_memUpdate.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        btn_memUpdate.setRippleColor(new java.awt.Color(51, 0, 0));
        btn_memUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_memUpdateActionPerformed(evt);
            }
        });
        jPanel16.add(btn_memUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 430, 110, 50));

        txt_memcn.setBackground(new java.awt.Color(51, 0, 0));
        txt_memcn.setForeground(new java.awt.Color(255, 255, 255));
        txt_memcn.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_memcn.setToolTipText("");
        txt_memcn.setBorder(null);
        txt_memcn.setCaretColor(new java.awt.Color(255, 255, 255));
        txt_memcn.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txt_memcn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_memcnActionPerformed(evt);
            }
        });
        jPanel16.add(txt_memcn, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 360, 260, 30));

        jLabel28.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Contact Number :");
        jPanel16.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, -1, -1));

        jLabel15.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Manage Members");
        jLabel15.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel15.setFocusable(false);
        jLabel15.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel15.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel16.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 260, 50));

        jPanel1.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 310, 550));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/main/Artboard 4.png"))); // NOI18N
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 990, 670));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_memidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_memidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_memidActionPerformed

    private void txt_memnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_memnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_memnameActionPerformed

    private void btn_memDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_memDeleteActionPerformed
        // TODO add your handling code here:
        
        
         if(deleteMem()==true){
            JOptionPane.showMessageDialog(this,"Member Deleted");
            clerMemTable();
            setMemDetailsToTable();
        }else{
            JOptionPane.showMessageDialog(this,"Member Delete Failed");
        }
        
    }//GEN-LAST:event_btn_memDeleteActionPerformed

    private void txt_memmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_memmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_memmailActionPerformed

    private void btn_memAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_memAddActionPerformed
       if(addMem()==true){
            JOptionPane.showMessageDialog(this,"Member Added");
            clerMemTable();
            setMemDetailsToTable();
        }else{
            JOptionPane.showMessageDialog(this,"Member Addition Failed");
        }        

// TODO add your handling code here:
    }//GEN-LAST:event_btn_memAddActionPerformed

    private void btn_memUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_memUpdateActionPerformed
        // TODO add your handling code here:
        if(updateMem()==true){
            JOptionPane.showMessageDialog(this,"Member Updated");
            clerMemTable();
            setMemDetailsToTable();
        }else{
            JOptionPane.showMessageDialog(this,"Member Update Failed");
        }
        
    }//GEN-LAST:event_btn_memUpdateActionPerformed

    private void txt_memcnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_memcnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_memcnActionPerformed

    private void text_searchmemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_searchmemActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_text_searchmemActionPerformed

  
    private void text_searchmemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_searchmemKeyReleased
        // TODO add your handling code here:

         if(!text_searchmem.getText().equals("")){
             
             
             
             if(text_searchmem.isSelected()){
            int option=text_searchmem.getSelectedIndex();

            // String text=text_searchOption.getText().trim();
            //    "%" +    +"%"
            if(option==0){
                
            //  if(!text_searchmem.getText().equals()){
             
        //      }
                   sbid();      
                        
                //Search by ID      where 'book_id' like ?
                // loadData("where 'book_id' LIKE %",text);
            } else if(option==1){

                sbname();
                //search by Name   where 'book_name' like ?"
                //  loadData("where 'book_name' LIKE %",text);
            }else if(option ==2){
                sbmail();
                //Search by Author
                //   loadData("where 'author' like ?",text);
            }else if(option==3){
                sbnum();
                //Book Shelf
                //  loadData("where 'bookshelf' like ?",text);
            }
        }
             
             
             
             
             
             
         }
        
        

    }//GEN-LAST:event_text_searchmemKeyReleased

    private void btn_update1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_update1ActionPerformed
        // TODO add your handling code here:
        clerMemTable();
        setMemDetailsToTable();
    }//GEN-LAST:event_btn_update1ActionPerformed

    private void tbl_memDetailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_memDetailsMouseClicked

        //add table data to textareas

        int rowNo=tbl_memDetails.getSelectedRow();
        TableModel model=tbl_memDetails.getModel();

        //aset text areas
        txt_memid.setText(model.getValueAt(rowNo,0).toString());
        txt_memname.setText(model.getValueAt(rowNo, 1).toString());
        txt_memmail.setText(model.getValueAt(rowNo, 2).toString());
        txt_memcn.setText(model.getValueAt(rowNo, 3).toString());
       

        // TODO add your handling code here:
    }//GEN-LAST:event_tbl_memDetailsMouseClicked

    private void btn_exportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_exportActionPerformed

        // TODO add your handling code here:


        MessageFormat header=new MessageFormat("Reort of Members");
        MessageFormat footer=new MessageFormat("page{0,number,integer}");
        try {
            tbl_memDetails.print(JTable.PrintMode.FIT_WIDTH,header,footer);
        } catch (Exception e) {
            e.getMessage();
        }
    }//GEN-LAST:event_btn_exportActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojerusan.RSMaterialButtonRectangle btn_export;
    private rojerusan.RSMaterialButtonRectangle btn_memAdd;
    private rojerusan.RSMaterialButtonRectangle btn_memDelete;
    private rojerusan.RSMaterialButtonRectangle btn_memUpdate;
    private rojerusan.RSMaterialButtonRectangle btn_update1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JScrollPane jScrollPane2;
    private rojeru_san.complementos.RSTableMetro tbl_memDetails;
    private textfield.TextFieldSearchOption text_searchmem;
    private javax.swing.JTextField txt_memcn;
    private javax.swing.JTextField txt_memid;
    private javax.swing.JTextField txt_memmail;
    private javax.swing.JTextField txt_memname;
    // End of variables declaration//GEN-END:variables
}
