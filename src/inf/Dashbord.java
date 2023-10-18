/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package inf;

import com.raven.chart.ModelChart;
import java.sql.*;
import database.DbConnect;
import java.awt.Color;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author Dilshara Mithum
 */
public class Dashbord extends javax.swing.JInternalFrame {

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form IssueBooks
     */
    public Dashbord() {
        initComponents();

        setDateToCard();
        allTimeReaderName();
        allTimeReaderBCount();

        LMReaderBCount();
        LMReaderName();

        AllFines();
          LastMFines();
                  
        chart1();
       // chart2();

        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
    }
//chart 1

    public void chart1() {

        
        
        getContentPane().setBackground(new Color(250, 250, 250));
        chart1.addLegend("Issue Books", new Color(135, 189, 245));
   
        try {

            conn = DbConnect.getConnection();
            pst = conn.prepareStatement("SELECT * FROM (SELECT IssueDate, COUNT(`id`) AS cd FROM issuebooks GROUP BY `IssueDate` \n" +
" ORDER BY `issuebooks`.`IssueDate` DESC LIMIT 15) AS AR ORDER BY AR.IssueDate ASC");
            rs = pst.executeQuery();
          
            while (rs.next()) {
                
                double cd=rs.getDouble("cd");
                 
               String dt=rs.getString("IssueDate");
                
              
               
               
               
    
               
              LocalDate currentDate = LocalDate.parse(dt);
 
                
        // Get day from date
        
    
       int day1 = currentDate.getDayOfMonth();
        int month = currentDate.getMonthValue();   
              
               
         
         String ChartDate=Integer.toString(day1);
         String ChartMonth=Integer.toString(month);
         
         // String ChartMonth=Integer.toString(month);
          
                chart1.addData(new ModelChart(ChartMonth+"-"+ChartDate, new double[]{cd}));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }
//Chatr2

    
    /*
    public void chart2() {

        getContentPane().setBackground(new Color(250, 250, 250));
        chart2.addLegend("New Books", new Color(245, 189, 135));
        chart2.addLegend("New Members", new Color(135, 189, 245));

        chart2.addData(new ModelChart("Mon", new double[]{500, 200}));
        chart2.addData(new ModelChart("Tue", new double[]{600, 750}));
        chart2.addData(new ModelChart("Wed", new double[]{200, 350}));
        chart2.addData(new ModelChart("Thu", new double[]{480, 150}));
        chart2.addData(new ModelChart("Fri", new double[]{350, 540}));
        chart2.addData(new ModelChart("Sat", new double[]{190, 280}));
        chart2.addData(new ModelChart("Sun", new double[]{190, 280}));

    }
*/
    public void setDateToCard() {

        Statement st = null;
        long l = System.currentTimeMillis();
        Date todaysDate = new Date(l);

        try {
            conn = DbConnect.getConnection();
            st = conn.createStatement();
            rs = st.executeQuery("select * from bookdetails");
            rs.last();
            lbl_books.setText(Integer.toString(rs.getRow()));

            rs = st.executeQuery("select * from members");
            rs.last();
            lbl_mem.setText(Integer.toString(rs.getRow()));

            rs = st.executeQuery("Select BookID AS b from issueBooks where Status='" + "Pending" + "'");
            rs.last();
            lbl_ib.setText(Integer.toString(rs.getRow()));

            rs = st.executeQuery("Select id from issueBooks where ReturnDate<'" + todaysDate + "' and Status='" + "Pending" + "'");
            rs.last();
            lbl_db.setText(Integer.toString(rs.getRow()));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                st.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    //Update All time Reader Name and book count
    public void allTimeReaderName() {

        try {

            conn = DbConnect.getConnection();
            pst = conn.prepareStatement("SELECT MemberName FROM issueBooks GROUP BY MemberID ORDER BY COUNT(*) DESC LIMIT 1");
            rs = pst.executeQuery();

            while (rs.next()) {
                lbl_ratimeName.setText(rs.getString("MemberName"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //     rs.close();
                //  pst.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public void allTimeReaderBCount() {

        try {

            conn = DbConnect.getConnection();
            pst = conn.prepareStatement("SELECT COUNT(id) FROM issueBooks GROUP BY MemberID ORDER BY COUNT(*) DESC LIMIT 1");
            rs = pst.executeQuery();

            while (rs.next()) {
                lbl_ratimeNum.setText(rs.getString("COUNT(id)"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //     rs.close();
                //  pst.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

//Update LAst Month Reader Details
    public void LMReaderName() {

        try {

            conn = DbConnect.getConnection();
            pst = conn.prepareStatement("SELECT MemberName FROM `issuebooks` WHERE MONTH(IssueDate)=MONTH(DATE_ADD(NOW(),INTERVAL -1 MONTH))AND YEAR(IssueDate)=YEAR(DATE_ADD(NOW(),INTERVAL -1 MONTH)) GROUP BY MemberID LIMIT 1 ;");
            rs = pst.executeQuery();

            while (rs.next()) {
                lbl_LMName.setText(rs.getString("MemberName"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //     rs.close();
                //  pst.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public void LMReaderBCount() {

        try {

            conn = DbConnect.getConnection();
            pst = conn.prepareStatement("SELECT COUNT(*) FROM `issuebooks` WHERE MONTH(IssueDate)=MONTH(DATE_ADD(NOW(),INTERVAL -1 MONTH))AND YEAR(IssueDate)=YEAR(DATE_ADD(NOW(),INTERVAL -1 MONTH)) GROUP BY MemberID LIMIT 1 ;");
            rs = pst.executeQuery();

            while (rs.next()) {
                lbl_LMB.setText(rs.getString("COUNT(*)"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //     rs.close();
                //  pst.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

   
    





//Calculate Fines Details
    
    public void LastMFines() {

        try {

            conn = DbConnect.getConnection();
            pst = conn.prepareStatement("SELECT SUM(`FinesAmount`) as tm FROM fines  WHERE MONTH(Date)=MONTH(DATE_ADD(NOW(),INTERVAL -1 MONTH))AND YEAR(Date)=YEAR(DATE_ADD(NOW(),INTERVAL -1 MONTH));");
            rs = pst.executeQuery();

            while (rs.next()) {
                lbl_LMFines.setText(rs.getString("tm"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //     rs.close();
                //  pst.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
    
    
    
    
     public void AllFines() {

        try {

            conn = DbConnect.getConnection();
            pst = conn.prepareStatement("SELECT SUM(`FinesAmount`) as tt FROM fines;");
            rs = pst.executeQuery();

            while (rs.next()) {
                lbl_ATFines.setText(rs.getString("tt"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //     rs.close();
                //  pst.close();
            } catch (Exception e) {
                e.printStackTrace();
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
        chart1 = new com.raven.chart.Chart();
        jPanel16 = new javax.swing.JPanel();
        lbl_db = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        lbl_ib = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        lbl_mem = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        lbl_books9 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        lbl_books10 = new javax.swing.JLabel();
        lbl_ATFines = new javax.swing.JLabel();
        lbl_books12 = new javax.swing.JLabel();
        lbl_books13 = new javax.swing.JLabel();
        lbl_LMFines = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        lbl_LMB = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        lbl_books6 = new javax.swing.JLabel();
        lbl_LMName = new javax.swing.JLabel();
        lbl_books8 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        lbl_ratimeNum = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        lbl_books3 = new javax.swing.JLabel();
        lbl_ratimeName = new javax.swing.JLabel();
        lbl_books5 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        lbl_books = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 0, 0));
        setMaximumSize(new java.awt.Dimension(990, 670));
        setMinimumSize(new java.awt.Dimension(990, 670));
        setPreferredSize(new java.awt.Dimension(990, 670));

        jPanel1.setPreferredSize(new java.awt.Dimension(990, 670));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        chart1.setBackground(new java.awt.Color(255, 214, 183));
        chart1.setForeground(new java.awt.Color(102, 51, 0));
        chart1.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        jPanel1.add(chart1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 930, 300));

        jPanel16.setBackground(new java.awt.Color(153, 51, 0));
        jPanel16.setBorder(javax.swing.BorderFactory.createMatteBorder(15, 0, 0, 0, new java.awt.Color(255, 102, 0)));
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_db.setFont(new java.awt.Font("Poppins", 1, 36)); // NOI18N
        lbl_db.setForeground(new java.awt.Color(255, 255, 255));
        lbl_db.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icons/adminIcons/adminIcons/icons8_people_50px_1.png"))); // NOI18N
        lbl_db.setText("10");
        jPanel16.add(lbl_db, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, 100, 50));

        jLabel18.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Defaulter List");
        jPanel16.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        jPanel1.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 30, 220, 110));

        jPanel15.setBackground(new java.awt.Color(153, 51, 0));
        jPanel15.setBorder(javax.swing.BorderFactory.createMatteBorder(15, 0, 0, 0, new java.awt.Color(255, 102, 0)));
        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_ib.setFont(new java.awt.Font("Poppins", 1, 36)); // NOI18N
        lbl_ib.setForeground(new java.awt.Color(255, 255, 255));
        lbl_ib.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icons/adminIcons/adminIcons/icons8_give_50px.png"))); // NOI18N
        lbl_ib.setText("10");
        jPanel15.add(lbl_ib, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, 100, 50));

        jLabel16.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Issued Books");
        jPanel15.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        jPanel1.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 30, 220, 110));

        jPanel14.setBackground(new java.awt.Color(153, 51, 0));
        jPanel14.setBorder(javax.swing.BorderFactory.createMatteBorder(15, 0, 0, 0, new java.awt.Color(255, 102, 0)));
        jPanel14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_mem.setFont(new java.awt.Font("Poppins", 1, 36)); // NOI18N
        lbl_mem.setForeground(new java.awt.Color(255, 255, 255));
        lbl_mem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icons/adminIcons/adminIcons/icons8_People_50px.png"))); // NOI18N
        lbl_mem.setText("10");
        jPanel14.add(lbl_mem, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, 100, 50));

        jLabel14.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("No Of Members");
        jPanel14.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        jPanel1.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 30, 220, 110));

        jPanel19.setBackground(new java.awt.Color(153, 51, 0));
        jPanel19.setBorder(javax.swing.BorderFactory.createMatteBorder(40, 0, 0, 0, new java.awt.Color(255, 102, 0)));
        jPanel19.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_books9.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        lbl_books9.setForeground(new java.awt.Color(255, 255, 255));
        lbl_books9.setText("Rs");
        jPanel19.add(lbl_books9, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 120, 40, 30));

        jLabel22.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 153, 0));
        jLabel22.setText("All thime");
        jPanel19.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, 290, -1));

        jLabel23.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("Fines Revenue");
        jPanel19.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 290, 40));

        jLabel24.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 153, 0));
        jLabel24.setText("Last Month");
        jPanel19.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, 290, -1));

        lbl_books10.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        lbl_books10.setForeground(new java.awt.Color(255, 255, 255));
        lbl_books10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icons/icons8_money_bag_40px.png"))); // NOI18N
        jPanel19.add(lbl_books10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 50, 50));

        lbl_ATFines.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        lbl_ATFines.setForeground(new java.awt.Color(255, 255, 255));
        lbl_ATFines.setText("10000");
        jPanel19.add(lbl_ATFines, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 120, 170, 30));

        lbl_books12.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        lbl_books12.setForeground(new java.awt.Color(255, 255, 255));
        lbl_books12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icons/icons8_money_40px.png"))); // NOI18N
        jPanel19.add(lbl_books12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 60, 40));

        lbl_books13.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        lbl_books13.setForeground(new java.awt.Color(255, 255, 255));
        lbl_books13.setText("Rs");
        jPanel19.add(lbl_books13, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, 40, 40));

        lbl_LMFines.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        lbl_LMFines.setForeground(new java.awt.Color(255, 255, 255));
        lbl_LMFines.setText("10000");
        jPanel19.add(lbl_LMFines, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 60, 180, 40));

        jPanel1.add(jPanel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 480, 290, 160));

        jPanel18.setBackground(new java.awt.Color(153, 51, 0));
        jPanel18.setBorder(javax.swing.BorderFactory.createMatteBorder(40, 0, 0, 0, new java.awt.Color(255, 102, 0)));
        jPanel18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_LMB.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        lbl_LMB.setForeground(new java.awt.Color(255, 255, 255));
        lbl_LMB.setText("100");
        jPanel18.add(lbl_LMB, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 120, 210, 30));

        jLabel19.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 153, 0));
        jLabel19.setText("Number of Books");
        jPanel18.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, 290, -1));

        jLabel20.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Reader of Last Month");
        jPanel18.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 290, 40));

        jLabel21.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 153, 0));
        jLabel21.setText("Name");
        jPanel18.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, 290, -1));

        lbl_books6.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        lbl_books6.setForeground(new java.awt.Color(255, 255, 255));
        lbl_books6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icons/icons8_books_40px.png"))); // NOI18N
        jPanel18.add(lbl_books6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 50, 50));

        lbl_LMName.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        lbl_LMName.setForeground(new java.awt.Color(255, 255, 255));
        lbl_LMName.setText("Dilshara Mithum");
        jPanel18.add(lbl_LMName, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, 220, 40));

        lbl_books8.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        lbl_books8.setForeground(new java.awt.Color(255, 255, 255));
        lbl_books8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icons/icons8_trophy_50px.png"))); // NOI18N
        jPanel18.add(lbl_books8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 50, 40));

        jPanel1.add(jPanel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 480, 290, 160));

        jPanel17.setBackground(new java.awt.Color(153, 51, 0));
        jPanel17.setBorder(javax.swing.BorderFactory.createMatteBorder(40, 0, 0, 0, new java.awt.Color(255, 102, 0)));
        jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_ratimeNum.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        lbl_ratimeNum.setForeground(new java.awt.Color(255, 255, 255));
        lbl_ratimeNum.setText("100");
        jPanel17.add(lbl_ratimeNum, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 120, 220, 30));

        jLabel13.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 153, 0));
        jLabel13.setText("Number of Books");
        jPanel17.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, 290, -1));

        jLabel15.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Reader of All Time");
        jPanel17.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 290, 40));

        jLabel17.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 153, 0));
        jLabel17.setText("Name");
        jPanel17.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, 290, -1));

        lbl_books3.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        lbl_books3.setForeground(new java.awt.Color(255, 255, 255));
        lbl_books3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icons/icons8_books_40px.png"))); // NOI18N
        jPanel17.add(lbl_books3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 50, 50));

        lbl_ratimeName.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        lbl_ratimeName.setForeground(new java.awt.Color(255, 255, 255));
        lbl_ratimeName.setText("Dilshara Mithum");
        jPanel17.add(lbl_ratimeName, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, 220, 40));

        lbl_books5.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        lbl_books5.setForeground(new java.awt.Color(255, 255, 255));
        lbl_books5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icons/icons8_trophy_50px.png"))); // NOI18N
        jPanel17.add(lbl_books5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 50, 40));

        jPanel1.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 480, 290, 160));

        jPanel13.setBackground(new java.awt.Color(153, 51, 0));
        jPanel13.setBorder(javax.swing.BorderFactory.createMatteBorder(15, 0, 0, 0, new java.awt.Color(255, 102, 0)));
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_books.setFont(new java.awt.Font("Poppins", 1, 36)); // NOI18N
        lbl_books.setForeground(new java.awt.Color(255, 255, 255));
        lbl_books.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Icons/adminIcons/adminIcons/icons8_Book_Shelf_50px.png"))); // NOI18N
        lbl_books.setText("10");
        jPanel13.add(lbl_books, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, 100, 50));

        jLabel12.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("No Of Books");
        jPanel13.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        jPanel1.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 220, 110));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/main/mback.png"))); // NOI18N
        jLabel2.setMaximumSize(new java.awt.Dimension(990, 670));
        jLabel2.setMinimumSize(new java.awt.Dimension(990, 670));
        jLabel2.setPreferredSize(new java.awt.Dimension(990, 670));
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, -1));

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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.chart.Chart chart1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JLabel lbl_ATFines;
    private javax.swing.JLabel lbl_LMB;
    private javax.swing.JLabel lbl_LMFines;
    private javax.swing.JLabel lbl_LMName;
    private javax.swing.JLabel lbl_books;
    private javax.swing.JLabel lbl_books10;
    private javax.swing.JLabel lbl_books12;
    private javax.swing.JLabel lbl_books13;
    private javax.swing.JLabel lbl_books3;
    private javax.swing.JLabel lbl_books5;
    private javax.swing.JLabel lbl_books6;
    private javax.swing.JLabel lbl_books8;
    private javax.swing.JLabel lbl_books9;
    private javax.swing.JLabel lbl_db;
    private javax.swing.JLabel lbl_ib;
    private javax.swing.JLabel lbl_mem;
    private javax.swing.JLabel lbl_ratimeName;
    private javax.swing.JLabel lbl_ratimeNum;
    // End of variables declaration//GEN-END:variables

}
