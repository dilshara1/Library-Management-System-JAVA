/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package inf;
import database.DbConnect;
import java.sql.*;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import textfield.SearchOptinEvent;
import textfield.SearchOption;
/**
 *
 * @author Dilshara Mithum
 */

/*
BookDetails  Colum Names

book_id  Book ID
book_name Book Name
author   Author 
quantity  Quantity
bookshelf  Book Shelf
*/

public class ManageBooks extends javax.swing.JInternalFrame {

    /**
     * Creates new form IssueBooks
     */

  //Create Variables for Data Store Temport and use to update databese
     String bookName,author;
    int bookId,quantity,bookShelf;
    
    // TableModel that uses a Vector of Vectors to store the cell value objects
    DefaultTableModel model;
  
    
    
    Connection conn=null;
    PreparedStatement pst=null;
    ResultSet rs=null;
    
    
    public ManageBooks() {
        initComponents();
          conn=DbConnect.getConnection();
        
          setBookDetailsToTable();
        
    
   
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        BasicInternalFrameUI ui=(BasicInternalFrameUI)this.getUI();
        ui.setNorthPane(null);
        
        try {
            
        //add search bar icons
        
        
        text_searchOption.addEventOptionSelected(new SearchOptinEvent() {
            @Override
            
            
            public void optionSelected(SearchOption option, int index) {
                text_searchOption.setHint("Search by " + option.getName() + "...");
            }
        });
        
        
         text_searchOption.addOption(new SearchOption("ID", new ImageIcon(getClass().getResource("id.png"))));
        text_searchOption.addOption(new SearchOption("Name", new ImageIcon(getClass().getResource("book.png"))));
        text_searchOption.addOption(new SearchOption("Author", new ImageIcon(getClass().getResource("Author.png"))));
       text_searchOption.addOption(new SearchOption("BookShelf", new ImageIcon(getClass().getResource("shelf.png"))));
     text_searchOption.setSelectedIndex(0);
        
        
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
        
    }
//load data in search bar
    
    
    private void loadData(String where,Object...search){
        
        try {
     conn=DbConnect.getConnection();
     pst=conn.prepareStatement("select * from BookDetails"+where);
     model.setRowCount(0);
            
            for(int i=0;i<search.length;i++){
           pst.setObject(i+1,search[i]);      
            }
            
            rs=pst.executeQuery();
            while(rs.next()){
              
                 String BookId=rs.getString("BookID");  
                String BookName=rs.getString("BookName");
               String  Author=rs.getString("Author");
                String BookShelf=rs.getString("BookShelf");
                 
                model =(DefaultTableModel)tbl_bookDetails.getModel();
            
            
                model.addRow(new Object[]{tbl_bookDetails.getRowCount(),BookId,BookName,Author,BookShelf});
            
            
            }
            
            rs.close();
            pst.close();
               
            
            
        } catch (Exception e) {
            
          //  JOptionPane.showMessageDialog(null,e);
         
          e.printStackTrace();
          
        }
        
        
        
        
    }
    
    //to set the books in to the table
   
    public void setBookDetailsToTable(){
        
        try {
        
            conn=DbConnect.getConnection();
         Statement st=conn.createStatement();
            ResultSet rs = st.executeQuery("Select * from BookDetails");
            
         while(rs.next()){
             String BookId=rs.getString("BookID");
             String BookName=rs.getString("BookName");
             String Author=rs.getString("Author");
             int Quality=rs.getInt("Quantity");
             int BookShelf=rs.getInt("BookShelf");
               
             Object[] obj={BookId,BookName,Author,Quality,BookShelf};
             model =(DefaultTableModel)tbl_bookDetails.getModel();
             model.addRow(obj);
         }
            
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null,"Add Correct Details");      
        }
     
    }
    

    
    //Add Books to database
    public boolean addBook(){
        boolean isAdded=false;
        try{
      bookId=Integer.parseInt(txt_bookid.getText());
      bookName=txt_bookname.getText();
      author=txt_authorname.getText();
      quantity=Integer.parseInt(txt_bookquantity.getText());
     bookShelf=Integer.parseInt(txt_bookshelf.getText()); 
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Add Correct Details");
            //If Books Details incorrect return false
            return false;
        }
     
        try {
            conn=DbConnect.getConnection();
           String sql="insert into BookDetails values(?,?,?,?,?)";
           pst=conn.prepareStatement(sql);
           pst.setInt(1,bookId);
           pst.setString(2,bookName);
           pst.setString(3,author);
           pst.setInt(4,quantity);
           pst.setInt(5,bookShelf);
           
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
    
    
    //Update Book Tabel
    public boolean updatebook(){
        boolean isUpdated=false;
        
        try{
      bookId=Integer.parseInt(txt_bookid.getText());
      bookName=txt_bookname.getText();
      author=txt_authorname.getText();
      quantity=Integer.parseInt(txt_bookquantity.getText());
     bookShelf=Integer.parseInt(txt_bookshelf.getText()); 
  
    }catch(Exception e){
         JOptionPane.showMessageDialog(null,"Add Correct Details");
            //If Books Details incorrect return false
            return false;
    }
        
        try {
           conn=DbConnect.getConnection();
           String sql="update BookDetails set BookName=?,Author=?,Quantity=?,BookShelf=?  where BookID=?";
           pst=conn.prepareStatement(sql);
           
           pst.setString(1,bookName);
           pst.setString(2,author);
           pst.setInt(3,quantity);
           pst.setInt(4,bookShelf);
           pst.setInt(5,bookId);
           
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
    
    
    
    //Method to Delete Books
    
    public boolean deleteBook(){
        boolean isDeleted=false;
  
         try{
     bookId=Integer.parseInt(txt_bookid.getText());
     
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Wrong BookID");
            //If Books Details incorrect return false
            return false;
        }
         
         try {
           conn=DbConnect.getConnection();
           String sql="delete from BookDetails where BookID=?";
           pst=conn.prepareStatement(sql);
                   pst.setInt(1,bookId);
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
    
    

    
   //Clear Book Table
    
    public void clerTable(){
        DefaultTableModel model=(DefaultTableModel) tbl_bookDetails.getModel();
        model.setRowCount(0);
    }
    
    
  
    
    //Search by ID
    public void sbid(){
                
         int srch=Integer.parseInt(text_searchOption.getText());
         
         
        
        try {
         
           String sql="select * from BookDetails where BookID LIKE '%"+srch+"%'   "; 
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
        tbl_bookDetails.setModel(DbUtils.resultSetToTableModel(rs));
         
           
        
           
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    
    //search by name 1
    public void sbname(){
                
         String srch=text_searchOption.getText();
        
        try {
         
           String sql="select * from BookDetails where BookName LIKE '%"+srch+"%'   "; 
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
        tbl_bookDetails.setModel(DbUtils.resultSetToTableModel(rs));
         
           
        
           
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null,e);
        }
    }  
    
    
    //Search by Author
    public void sbauthor(){
                
         String srch=text_searchOption.getText();
        
        try {
         
           String sql="select * from BookDetails where Author LIKE '%"+srch+"%'   "; 
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
        tbl_bookDetails.setModel(DbUtils.resultSetToTableModel(rs));
         
           
        
           
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null,e);
        }
    }  
    
  public void sbshelf(){
                
         String srch=text_searchOption.getText();
        
        try {
         
           String sql="select * from BookDetails where BookShelf LIKE '%"+srch+"%'   "; 
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
        tbl_bookDetails.setModel(DbUtils.resultSetToTableModel(rs));
         
           
        
           
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null,e);
        }
    }    
    
    
    
    /*
    public void search(){
   
        String srch=searchbox.getText();
        
        try {
          // String sql="select * from BookDetails where book_id LIKE '%"+srch+"%'   OR  book_name LIKE '%"+srch+"%' OR  author LIKE '%"+srch+"%' OR    quantity   LIKE '%"+srch+"%' OR   bookshelf  LIKE '%"+srch+"%'        "; 
          
          
           String sql="select * from BookDetails where book_id LIKE ?   OR  book_name LIKE ? OR  author LIKE  ? OR    quantity   LIKE ? OR   bookshelf  LIKE ?    "; 
           pst=conn.prepareStatement(sql);
          
          
           pst.setString(1,srch);
           pst.setString(2,srch);
           pst.setString(3,srch);
           pst.setString(4,srch);
           pst.setString(5,srch);
           
        rs=pst.executeQuery();
        pst.executeUpdate();
        tbl_bookDetails.setModel(DbUtils.resultSetToTableModel(rs));
         
           
        
           
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null,e);
        }
    
    }
    
    */
    
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
        text_searchOption = new textfield.TextFieldSearchOption();
        btn_update1 = new rojerusan.RSMaterialButtonRectangle();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_bookDetails = new rojeru_san.complementos.RSTableMetro();
        jPanel16 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txt_bookid = new javax.swing.JTextField();
        txt_bookname = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        btn_delete = new rojerusan.RSMaterialButtonRectangle();
        jLabel27 = new javax.swing.JLabel();
        txt_authorname = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        txt_bookquantity = new javax.swing.JTextField();
        btn_add = new rojerusan.RSMaterialButtonRectangle();
        btn_update = new rojerusan.RSMaterialButtonRectangle();
        jLabel15 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        txt_bookshelf = new javax.swing.JTextField();
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

        jPanel17.setBackground(new java.awt.Color(51, 0, 0));
        jPanel17.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel17.setForeground(new java.awt.Color(51, 0, 0));
        jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        text_searchOption.setBackground(new java.awt.Color(153, 51, 0));
        text_searchOption.setForeground(new java.awt.Color(255, 255, 255));
        text_searchOption.setCaretColor(new java.awt.Color(255, 102, 0));
        text_searchOption.setColorOverlay1(new java.awt.Color(153, 51, 0));
        text_searchOption.setColorOverlay2(new java.awt.Color(51, 0, 0));
        text_searchOption.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        text_searchOption.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        text_searchOption.setSelectedTextColor(new java.awt.Color(255, 255, 255));
        text_searchOption.setSelectionColor(new java.awt.Color(255, 153, 0));
        text_searchOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_searchOptionActionPerformed(evt);
            }
        });
        text_searchOption.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_searchOptionKeyReleased(evt);
            }
        });
        jPanel17.add(text_searchOption, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 460, 40));

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

        tbl_bookDetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Book Id", "Name", "Author", "Quantity", "Book Shelf"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_bookDetails.setColorBackgoundHead(new java.awt.Color(51, 0, 0));
        tbl_bookDetails.setColorFilasBackgound2(new java.awt.Color(247, 230, 220));
        tbl_bookDetails.setColorFilasForeground1(new java.awt.Color(153, 51, 0));
        tbl_bookDetails.setColorFilasForeground2(new java.awt.Color(153, 51, 0));
        tbl_bookDetails.setColorSelBackgound(new java.awt.Color(153, 51, 0));
        tbl_bookDetails.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        tbl_bookDetails.setFuenteFilas(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        tbl_bookDetails.setFuenteFilasSelect(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        tbl_bookDetails.setFuenteHead(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        tbl_bookDetails.setGrosorBordeFilas(0);
        tbl_bookDetails.setGrosorBordeHead(0);
        tbl_bookDetails.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_bookDetailsMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_bookDetails);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 110, 570, 540));

        jPanel16.setBackground(new java.awt.Color(153, 51, 0));
        jPanel16.setBorder(javax.swing.BorderFactory.createMatteBorder(80, 0, 0, 0, new java.awt.Color(51, 0, 0)));
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icons/pic40/icons8_books_40px.png"))); // NOI18N
        jLabel13.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel13.setFocusable(false);
        jLabel13.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel13.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel16.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 50, 50));

        jLabel14.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Book ID :");
        jPanel16.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, -1, -1));

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
        jPanel16.add(txt_bookid, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 260, 30));

        txt_bookname.setBackground(new java.awt.Color(51, 0, 0));
        txt_bookname.setForeground(new java.awt.Color(255, 255, 255));
        txt_bookname.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_bookname.setToolTipText("");
        txt_bookname.setBorder(null);
        txt_bookname.setCaretColor(new java.awt.Color(255, 255, 255));
        txt_bookname.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txt_bookname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_booknameActionPerformed(evt);
            }
        });
        jPanel16.add(txt_bookname, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 260, 30));

        jLabel26.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("Book Name :");
        jPanel16.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 180, -1, -1));

        btn_delete.setBackground(new java.awt.Color(204, 51, 0));
        btn_delete.setText("Delete");
        btn_delete.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        btn_delete.setRippleColor(new java.awt.Color(51, 0, 0));
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });
        jPanel16.add(btn_delete, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 550, 110, 50));

        jLabel27.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Author Name :");
        jPanel16.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 260, -1, -1));

        txt_authorname.setBackground(new java.awt.Color(51, 0, 0));
        txt_authorname.setForeground(new java.awt.Color(255, 255, 255));
        txt_authorname.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_authorname.setToolTipText("");
        txt_authorname.setBorder(null);
        txt_authorname.setCaretColor(new java.awt.Color(255, 255, 255));
        txt_authorname.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txt_authorname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_authornameActionPerformed(evt);
            }
        });
        jPanel16.add(txt_authorname, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, 260, 30));

        jLabel28.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Quantity :");
        jPanel16.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 330, -1, -1));

        txt_bookquantity.setBackground(new java.awt.Color(51, 0, 0));
        txt_bookquantity.setForeground(new java.awt.Color(255, 255, 255));
        txt_bookquantity.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_bookquantity.setToolTipText("");
        txt_bookquantity.setBorder(null);
        txt_bookquantity.setCaretColor(new java.awt.Color(255, 255, 255));
        txt_bookquantity.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txt_bookquantity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_bookquantityActionPerformed(evt);
            }
        });
        jPanel16.add(txt_bookquantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 360, 260, 30));

        btn_add.setBackground(new java.awt.Color(204, 51, 0));
        btn_add.setText("Add");
        btn_add.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        btn_add.setRippleColor(new java.awt.Color(51, 0, 0));
        btn_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addActionPerformed(evt);
            }
        });
        jPanel16.add(btn_add, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 490, 110, 50));

        btn_update.setBackground(new java.awt.Color(204, 51, 0));
        btn_update.setText("Update");
        btn_update.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        btn_update.setRippleColor(new java.awt.Color(51, 0, 0));
        btn_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt);
            }
        });
        jPanel16.add(btn_update, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 490, 110, 50));

        jLabel15.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Manage Books");
        jLabel15.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel15.setFocusable(false);
        jLabel15.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel15.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel16.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 260, 50));

        jLabel29.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("Book Shelf");
        jPanel16.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 410, -1, -1));

        txt_bookshelf.setBackground(new java.awt.Color(51, 0, 0));
        txt_bookshelf.setForeground(new java.awt.Color(255, 255, 255));
        txt_bookshelf.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_bookshelf.setToolTipText("");
        txt_bookshelf.setBorder(null);
        txt_bookshelf.setCaretColor(new java.awt.Color(255, 255, 255));
        txt_bookshelf.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txt_bookshelf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_bookshelfActionPerformed(evt);
            }
        });
        jPanel16.add(txt_bookshelf, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 440, 260, 30));

        btn_export.setBackground(new java.awt.Color(204, 51, 0));
        btn_export.setText("Export ");
        btn_export.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        btn_export.setRippleColor(new java.awt.Color(51, 0, 0));
        btn_export.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_exportActionPerformed(evt);
            }
        });
        jPanel16.add(btn_export, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 550, 110, 50));

        jPanel1.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, 310, 610));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/main/Artboard 3.png"))); // NOI18N
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

    private void txt_bookidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_bookidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_bookidActionPerformed

    private void txt_booknameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_booknameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_booknameActionPerformed

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        // TODO add your handling code here:
        
         if(deleteBook()==true){
            JOptionPane.showMessageDialog(this,"Book Deleted");
            clerTable();
            setBookDetailsToTable();
        }else{
            JOptionPane.showMessageDialog(this,"Book Delete Failed");
        }
        
        
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void txt_authornameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_authornameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_authornameActionPerformed

    private void txt_bookquantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_bookquantityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_bookquantityActionPerformed

    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addActionPerformed
       
        if(addBook()==true){
            JOptionPane.showMessageDialog(this,"Book Added");
            clerTable();
            setBookDetailsToTable();
        }else{
            JOptionPane.showMessageDialog(this,"Book Addition Failed");
        }
        
// TODO add your handling code here:
    }//GEN-LAST:event_btn_addActionPerformed

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed
        // TODO add your handling code here:
         //add table data to textareas
        
        if(updatebook()==true){
            JOptionPane.showMessageDialog(this,"Book Updated");
            clerTable();
            setBookDetailsToTable();
        }else{
            JOptionPane.showMessageDialog(this,"Book Update Failed");
        }
        
        
           
    }//GEN-LAST:event_btn_updateActionPerformed

    private void tbl_bookDetailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_bookDetailsMouseClicked
       
        //add table data to textareas
        
        int rowNo=tbl_bookDetails.getSelectedRow();
        TableModel model=tbl_bookDetails.getModel();
        
        //aset text areas 
        txt_bookid.setText(model.getValueAt(rowNo,0).toString());
        txt_bookname.setText(model.getValueAt(rowNo, 1).toString());
        txt_authorname.setText(model.getValueAt(rowNo, 2).toString());
        txt_bookquantity.setText(model.getValueAt(rowNo, 3).toString());
        txt_bookshelf.setText(model.getValueAt(rowNo, 4).toString());
        
// TODO add your handling code here:
    }//GEN-LAST:event_tbl_bookDetailsMouseClicked

    private void txt_bookshelfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_bookshelfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_bookshelfActionPerformed

    private void text_searchOptionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_searchOptionKeyReleased
        // TODO add your handling code here:
        
        if(text_searchOption.isSelected()){
            int option=text_searchOption.getSelectedIndex();
            
           // String text=text_searchOption.getText().trim();
            //    "%" +    +"%"       
            if(option==0){
              sbid();
                //Search by ID      where 'book_id' like ?
             // loadData("where 'book_id' LIKE %",text);
            } else if(option==1){
               
                sbname();
                //search by Name   where 'book_name' like ?"
              //  loadData("where 'book_name' LIKE %",text);
            }else if(option ==2){
                sbauthor();
                //Search by Author
             //   loadData("where 'author' like ?",text);
            }else if(option==3){
                sbshelf();
                //Book Shelf
             //  loadData("where 'bookshelf' like ?",text);
            }
        }
        
    }//GEN-LAST:event_text_searchOptionKeyReleased

    
    
    
    
    private void text_searchOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_searchOptionActionPerformed
        // TODO add your handling code here:
        
        
        
        
    }//GEN-LAST:event_text_searchOptionActionPerformed

    private void btn_update1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_update1ActionPerformed
        // TODO add your handling code here:
            clerTable();
            setBookDetailsToTable();
    }//GEN-LAST:event_btn_update1ActionPerformed

    private void btn_exportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_exportActionPerformed
       
        // TODO add your handling code here:
        
      //  SimpleDateFormat Date_Formate=new SimpleDateFormat("yyy,MM,dd");
       // String dateform=Date_Format.format(da)
        
       MessageFormat header=new MessageFormat("Reort of Books");
       MessageFormat footer=new MessageFormat("page{0,number,integer}");
        try {
            tbl_bookDetails.print(JTable.PrintMode.FIT_WIDTH,header,footer);
        } catch (Exception e) {
            e.getMessage();
        }
    }//GEN-LAST:event_btn_exportActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojerusan.RSMaterialButtonRectangle btn_add;
    private rojerusan.RSMaterialButtonRectangle btn_delete;
    private rojerusan.RSMaterialButtonRectangle btn_export;
    private rojerusan.RSMaterialButtonRectangle btn_update;
    private rojerusan.RSMaterialButtonRectangle btn_update1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JScrollPane jScrollPane2;
    private rojeru_san.complementos.RSTableMetro tbl_bookDetails;
    private textfield.TextFieldSearchOption text_searchOption;
    private javax.swing.JTextField txt_authorname;
    private javax.swing.JTextField txt_bookid;
    private javax.swing.JTextField txt_bookname;
    private javax.swing.JTextField txt_bookquantity;
    private javax.swing.JTextField txt_bookshelf;
    // End of variables declaration//GEN-END:variables
}
