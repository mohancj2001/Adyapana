/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.formdev.flatlaf.FlatDarkLaf;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.ButtonModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.MySQL;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author LENOVO
 */
public class Home extends javax.swing.JFrame {

    public static HashMap<String, Integer> cityMap = new HashMap();
    public static HashMap<String, Integer> subjectMap = new HashMap();
    public static HashMap<String, Integer> teacherMap = new HashMap();
    public static HashMap<String, Integer> teacher2Map = new HashMap();
    public static HashMap<String, Integer> attendenceMap = new HashMap();
    public static HashMap<String, Integer> monthMap = new HashMap();

    /**
     * Creates new form Home
     */
    public Home() {
        initComponents();

        jPanel9.setVisible(true);
        jPanel3.setVisible(false);
        jPanel5.setVisible(false);
        jPanel7.setVisible(false);
        jPanel6.setVisible(false);
        jPanel8.setVisible(false);
        jPanel10.setVisible(false);
        jPanel11.setVisible(false);
        jPanel13.setVisible(false);
        jPanel14.setVisible(false);
        
       

    }

    private void loadSubjects() {
        try {

            ResultSet resultset = MySQL.execute("SELECT * FROM `subject`");

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            while (resultset.next()) {

                Vector<String> v = new Vector();
                v.add(resultset.getString("Subno"));
                v.add(resultset.getString("Description"));
                v.add(resultset.getString("Price"));

                model.addRow(v);
                jTable1.setModel(model);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadUsers() {
        try {

            ResultSet resultset = MySQL.execute("SELECT * FROM `user`");

            DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
            model.setRowCount(0);

            while (resultset.next()) {

                Vector<String> v = new Vector();
                v.add(resultset.getString("id"));
                v.add(resultset.getString("first_name"));
                v.add(resultset.getString("last_name"));
                v.add(resultset.getString("nic"));
                v.add(resultset.getString("mobile"));

                model.addRow(v);
                jTable2.setModel(model);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTeachers() {
        try {

            ResultSet resultset = MySQL.execute("SELECT * FROM `teacher` INNER JOIN `subject` ON"
                    + " `subject`.`Subno`=`teacher`.`subject_Subno` INNER JOIN `city` ON `city`.`id`=`teacher`.`city_id`");

            DefaultTableModel model = (DefaultTableModel) jTable4.getModel();
            model.setRowCount(0);

            while (resultset.next()) {

                Vector<String> v = new Vector();
                v.add(resultset.getString("Tno"));
                v.add(resultset.getString("first_name"));
                v.add(resultset.getString("last_name"));
                v.add(resultset.getString("nic"));
                v.add(resultset.getString("mobile"));
                v.add(resultset.getString("subject.Description"));
                v.add(resultset.getString("address"));
                v.add(resultset.getString("city.city_name"));

                model.addRow(v);
                jTable4.setModel(model);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadCitys() {
        try {

            //SEARCH
            ResultSet resultset = MySQL.execute("SELECT * FROM `city`");

            Vector v = new Vector();
            v.add("Select");

            while (resultset.next()) {
                v.add(resultset.getString("city_name"));
                cityMap.put(resultset.getString("city_name"), resultset.getInt("id"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(v);
            jComboBox1.setModel(model);

            //INSERT, UPDATE, DELETE
            //statement.executeUpdate("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void loadMonths() {
        try {

            //SEARCH
            ResultSet resultset = MySQL.execute("SELECT * FROM `month`");

            Vector v = new Vector();
            v.add("Select");

            while (resultset.next()) {
                v.add(resultset.getString("month_name"));
                monthMap.put(resultset.getString("month_name"), resultset.getInt("id"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(v);
            jComboBox6.setModel(model);

            //INSERT, UPDATE, DELETE
            //statement.executeUpdate("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTeachers1() {
        try {

            //SEARCH
            ResultSet resultset = MySQL.execute("SELECT * FROM `teacher` INNER JOIN `subject` ON"
                    + " `subject`.`Subno`=`teacher`.`subject_Subno` INNER JOIN `city` ON `city`.`id`=`teacher`.`city_id`");

            Vector v = new Vector();
            v.add("Select");

            while (resultset.next()) {
                v.add(resultset.getString("first_name"));
                teacherMap.put(resultset.getString("first_name"), resultset.getInt("Tno"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(v);
            jComboBox4.setModel(model);

            //INSERT, UPDATE, DELETE
            //statement.executeUpdate("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadSubjects1() {

        try {

            //SEARCH
            ResultSet resultset = MySQL.execute("SELECT * FROM `subject`");

            Vector v = new Vector();
            v.add("Select");

            while (resultset.next()) {
                v.add(resultset.getString("Description"));
                subjectMap.put(resultset.getString("Description"), resultset.getInt("Subno"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(v);
            jComboBox2.setModel(model);

            //INSERT, UPDATE, DELETE
            //statement.executeUpdate("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTeachers2() {

        try {
            String subject = jTextField22.getText();
            //SEARCH
            ResultSet resultset = MySQL.execute("SELECT * FROM `class` "
                    + "INNER JOIN `teacher` ON `teacher`.`Tno`=`class`.`teacher_Tno` "
                    + "INNER JOIN `subject` ON `class`.`subject_Subno`=`subject`.`Subno` WHERE `Description` LIKE '" + subject + "'");

            Vector v = new Vector();
            v.add("Select");

            while (resultset.next()) {
                v.add(resultset.getString("first_name"));
                teacher2Map.put(resultset.getString("first_name"), resultset.getInt("Tno"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(v);
            jComboBox5.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadClass() {
        String nic = jTextField19.getText();
        if (nic.length() == 12) {
            try {
                ResultSet resultSet = MySQL.execute("SELECT * FROM `student` WHERE `nic`='" + nic + "'");

                if (resultSet.next()) {
                    String sno = resultSet.getString("Sno");

                    jLabel75.setText(sno);

                    ResultSet resultset = MySQL.execute("SELECT * FROM `add_students_for_class`"
                            + "INNER JOIN `class` ON `class`.`ClassNo`=`add_students_for_class`.`class_id`"
                            + "INNER JOIN `subject` ON `subject`.`Subno`=`class`.`subject_Subno` WHERE `student_id`='" + sno + "'");

                    Vector v = new Vector();
                    v.add("Select");

                    while (resultset.next()) {
                        v.add(resultset.getString("Description"));
                        attendenceMap.put(resultset.getString("Description"), resultset.getInt("class_id"));
                    }

                    DefaultComboBoxModel model = new DefaultComboBoxModel(v);
                    jComboBox3.setModel(model);

                    Date data1 = new Date();
                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                    String stringDate1 = format1.format(data1);
                    jLabel72.setText(stringDate1);
                }

                //SEARCH
                //INSERT, UPDATE, DELETE
                //statement.executeUpdate("");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void reset1() {
        // Student Registration
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jTextField5.setText("");
        jTextField1.grabFocus();

    }

    public void reset2() {
        // Subject Registration
        jTextField6.setText("");
        jTextField7.setText("");
        jLabel47.setText(" ");
        jTextField6.grabFocus();

    }

    public void reset3() {
        // User Registration
        jTextField8.setText("");
        jTextField9.setText("");
        jTextField10.setText("");
        jTextField11.setText("");
        jTextField13.setText("");
        jPasswordField3.setText("");
        jTextField8.grabFocus();

    }

    public void reset4() {
        // Teacher Enrolment
        jTextField15.setText("");
        jTextField16.setText("");
        jTextField17.setText("");
        jTextField18.setText("");
        jTextField20.setText("");
        jComboBox1.setSelectedIndex(0);
        jComboBox2.setSelectedIndex(0);
    }

    public void reset5() {
        jLabel75.setText(" ");
        jLabel66.setText(" ");
        jLabel67.setText(" ");
        jLabel68.setText(" ");
        jLabel69.setText(" ");
        jLabel70.setText(" ");
        jLabel72.setText(" ");
        jLabel75.setText(" ");
        jComboBox3.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jButton2 = new javax.swing.JButton();
        jTextField5 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jPasswordField2 = new javax.swing.JPasswordField();
        jLabel24 = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jTextField10 = new javax.swing.JTextField();
        jTextField11 = new javax.swing.JTextField();
        jPasswordField3 = new javax.swing.JPasswordField();
        jButton4 = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel96 = new javax.swing.JLabel();
        jComboBox6 = new javax.swing.JComboBox<>();
        jLabel97 = new javax.swing.JLabel();
        jLabel98 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jTextField15 = new javax.swing.JTextField();
        jTextField16 = new javax.swing.JTextField();
        jTextField17 = new javax.swing.JTextField();
        jTextField18 = new javax.swing.JTextField();
        jLabel54 = new javax.swing.JLabel();
        jTextField20 = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton16 = new javax.swing.JButton();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel56 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jTextField19 = new javax.swing.JTextField();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jButton19 = new javax.swing.JButton();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel80 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        jButton22 = new javax.swing.JButton();
        jButton23 = new javax.swing.JButton();
        jLabel82 = new javax.swing.JLabel();
        timePicker1 = new com.github.lgooddatepicker.components.TimePicker();
        datePicker1 = new com.github.lgooddatepicker.components.DatePicker();
        jLabel83 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel76 = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        jTextField21 = new javax.swing.JTextField();
        jLabel86 = new javax.swing.JLabel();
        jTextField22 = new javax.swing.JTextField();
        jLabel87 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox<>();
        jButton20 = new javax.swing.JButton();
        jLabel88 = new javax.swing.JLabel();
        jLabel89 = new javax.swing.JLabel();
        jButton21 = new javax.swing.JButton();
        jLabel90 = new javax.swing.JLabel();
        jLabel91 = new javax.swing.JLabel();
        jLabel92 = new javax.swing.JLabel();
        jLabel93 = new javax.swing.JLabel();
        jLabel94 = new javax.swing.JLabel();
        jLabel95 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(0, 51, 255));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/3671690_education_icon (2).png"))); // NOI18N

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("Adyapana");
        jLabel14.setFont(new java.awt.Font("Vivaldi", 1, 48)); // NOI18N

        jPanel4.setLayout(new java.awt.GridLayout(0, 1));

        jButton7.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jButton7.setText("Student Registration");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton7);

        jButton8.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jButton8.setText("Subject Registration");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton8);

        jButton9.setText("Teacher Enrolment");
        jButton9.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton9);

        jButton10.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jButton10.setText("Student Payment");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton10);

        jButton11.setText("Class Registration");
        jButton11.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton11);

        jButton12.setText("Student and Teacher enrolment for Subject");
        jButton12.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton12);

        jButton13.setText("Student Attendance");
        jButton13.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton13);

        jButton1.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jButton1.setText("User Registration");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel48.setFont(new java.awt.Font("Vivaldi", 1, 64)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(255, 255, 255));
        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel48.setText("Welcome...!");
        jLabel48.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(110, 110, 110)
                .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(129, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(286, Short.MAX_VALUE)
                .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(276, 276, 276))
        );

        jPanel12.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 570, 650));

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Copperplate Gothic Light", 1, 24)); // NOI18N
        jLabel5.setText("Student Registration");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(133, 35, -1, -1));

        jLabel2.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jLabel2.setText("First Name");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 121, -1, -1));

        jLabel3.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jLabel3.setText("Email");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 197, -1, -1));

        jLabel4.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jLabel4.setText("NIC");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 235, -1, -1));

        jLabel6.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jLabel6.setText("Mobile");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 273, -1, -1));

        jLabel7.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jLabel7.setText("Gender");
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 312, -1, -1));

        jLabel8.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jLabel8.setText("Date of Birth");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 354, -1, -1));
        jPanel3.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 120, 260, -1));

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        jPanel3.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 158, 260, -1));
        jPanel3.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 196, 260, -1));
        jPanel3.add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 234, 260, -1));

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 12)); // NOI18N
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("Male");
        jRadioButton1.setActionCommand("1");
        jPanel3.add(jRadioButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 310, -1, -1));

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 12)); // NOI18N
        jRadioButton2.setText("Female");
        jRadioButton1.setActionCommand("2");
        jPanel3.add(jRadioButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(275, 310, -1, -1));
        jPanel3.add(jDateChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 351, 124, -1));

        jButton2.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jButton2.setText("Register");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 560, 416, -1));
        jPanel3.add(jTextField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 272, 260, -1));

        jLabel9.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jLabel9.setText("Last Name");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 159, 82, -1));

        jPanel12.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 570, 650));

        jPanel5.setPreferredSize(new java.awt.Dimension(476, 584));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setFont(new java.awt.Font("Copperplate Gothic Light", 1, 24)); // NOI18N
        jLabel10.setText("Subject Registration");
        jPanel5.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(132, 29, -1, -1));

        jLabel11.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jLabel11.setText("Subject");
        jPanel5.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(49, 134, -1, -1));

        jTextField6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel5.add(jTextField6, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 132, 279, -1));

        jLabel12.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jLabel12.setText("Price");
        jPanel5.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(49, 173, 60, -1));

        jTextField7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel5.add(jTextField7, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 171, 279, -1));

        jButton3.setText("Register");
        jButton3.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 220, 420, -1));

        jLabel15.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 12)); // NOI18N
        jLabel15.setText("Rs.");
        jPanel5.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(137, 175, -1, -1));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Subno", "Description", "Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel5.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 330, 420, 300));

        jButton14.setText("Update");
        jButton14.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton14, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 256, 420, -1));

        jButton15.setText("Delete");
        jButton15.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton15, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 287, 420, -1));

        jLabel46.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jLabel46.setText("Subno");
        jPanel5.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(49, 102, -1, -1));

        jLabel47.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jLabel47.setText(" ");
        jPanel5.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 102, 65, -1));

        jPanel12.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 560, 650));

        jPanel7.setPreferredSize(new java.awt.Dimension(570, 649));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel25.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jLabel25.setText("Admin Password");
        jPanel7.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 243, -1, -1));

        jPasswordField2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPasswordField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPasswordField2ActionPerformed(evt);
            }
        });
        jPanel7.add(jPasswordField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(226, 241, 256, -1));

        jLabel24.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jLabel24.setText("Admin Username");
        jPanel7.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 192, -1, -1));

        jTextField12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel7.add(jTextField12, new org.netbeans.lib.awtextra.AbsoluteConstraints(226, 190, 256, -1));

        jLabel22.setFont(new java.awt.Font("Copperplate Gothic Light", 1, 24)); // NOI18N
        jLabel22.setText("User Registration");
        jPanel7.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(146, 24, -1, -1));

        jButton5.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jButton5.setText("Sign In");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel7.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, 452, -1));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "First Name", "Last Name", "NIC", "Mobile"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jPanel7.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 343, -1, 263));

        jPanel12.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 0, 570, 650));

        jPanel6.setPreferredSize(new java.awt.Dimension(570, 649));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel16.setFont(new java.awt.Font("Copperplate Gothic Light", 1, 24)); // NOI18N
        jLabel16.setText("User Registration");
        jPanel6.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 40, -1, -1));

        jLabel17.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jLabel17.setText("First Name");
        jPanel6.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(62, 145, -1, -1));

        jLabel18.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jLabel18.setText("Last Name");
        jPanel6.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(62, 184, -1, -1));

        jLabel19.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jLabel19.setText("Username");
        jPanel6.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(62, 223, -1, -1));

        jLabel20.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jLabel20.setText("Password");
        jPanel6.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(62, 262, -1, -1));

        jLabel21.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jLabel21.setText("NIC");
        jPanel6.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(62, 301, -1, -1));

        jTextField8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel6.add(jTextField8, new org.netbeans.lib.awtextra.AbsoluteConstraints(173, 143, 269, -1));

        jTextField9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel6.add(jTextField9, new org.netbeans.lib.awtextra.AbsoluteConstraints(173, 182, 269, -1));

        jTextField10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel6.add(jTextField10, new org.netbeans.lib.awtextra.AbsoluteConstraints(173, 221, 269, -1));

        jTextField11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel6.add(jTextField11, new org.netbeans.lib.awtextra.AbsoluteConstraints(173, 299, 269, -1));

        jPasswordField3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel6.add(jPasswordField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(173, 260, 269, -1));

        jButton4.setText("Register");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 606, 460, -1));

        jLabel23.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jLabel23.setText("Mobile");
        jPanel6.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(62, 340, -1, -1));

        jTextField13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel6.add(jTextField13, new org.netbeans.lib.awtextra.AbsoluteConstraints(173, 338, 269, -1));

        jPanel12.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 560, 650));

        jLabel26.setText("Student Payment");
        jLabel26.setFont(new java.awt.Font("Copperplate Gothic Light", 1, 24)); // NOI18N

        jLabel27.setText("Student NIC");
        jLabel27.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N

        jTextField14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextField14.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField14KeyReleased(evt);
            }
        });

        jLabel28.setText("First Name");
        jLabel28.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N

        jLabel29.setText("Last Name");
        jLabel29.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N

        jLabel30.setText("Email");
        jLabel30.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N

        jLabel31.setText("Mobile");
        jLabel31.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N

        jLabel32.setText("DOB");
        jLabel32.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N

        jLabel33.setText("Address");
        jLabel33.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N

        jLabel34.setText(" ");
        jLabel34.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        jLabel35.setText(" ");
        jLabel35.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        jLabel36.setText(" ");
        jLabel36.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        jLabel37.setText(" ");
        jLabel37.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        jLabel38.setText(" ");
        jLabel38.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        jLabel39.setText(" ");
        jLabel39.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Subno", "Description", "Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable3MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable3);

        jLabel40.setText("Description");
        jLabel40.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N

        jLabel41.setText("Price");
        jLabel41.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N

        jLabel42.setText(" ");
        jLabel42.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        jLabel43.setText(" ");
        jLabel43.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        jButton6.setText("Pay");
        jButton6.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel44.setText("Subno");
        jLabel44.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N

        jLabel45.setText(" ");
        jLabel45.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel96.setText("Month");
        jLabel96.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N

        jLabel97.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jLabel97.setText("Student ID");

        jLabel98.setText(" ");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(160, 160, 160)
                .addComponent(jLabel26))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel27)
                .addGap(38, 38, 38)
                .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel29)
                .addGap(51, 51, 51)
                .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel30)
                .addGap(89, 89, 89)
                .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel31)
                .addGap(80, 80, 80)
                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel32)
                .addGap(38, 38, 38)
                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel44)
                    .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40)
                    .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41)
                    .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel33)
                    .addComponent(jLabel96))
                .addGap(65, 65, 65)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel28)
                    .addComponent(jLabel97))
                .addGap(48, 48, 48)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel98, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel26)
                .addGap(31, 31, 31)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel27))
                    .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel97)
                    .addComponent(jLabel98))
                .addGap(7, 7, 7)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel28)
                    .addComponent(jLabel34))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29)
                    .addComponent(jLabel35))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30)
                    .addComponent(jLabel36))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel31)
                    .addComponent(jLabel37)
                    .addComponent(jLabel32)
                    .addComponent(jLabel38))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel33)
                    .addComponent(jLabel39))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel96)
                    .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(55, 55, 55)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel44)
                        .addGap(6, 6, 6)
                        .addComponent(jLabel45)
                        .addGap(6, 6, 6)
                        .addComponent(jLabel40)
                        .addGap(6, 6, 6)
                        .addComponent(jLabel42)
                        .addGap(6, 6, 6)
                        .addComponent(jLabel41)
                        .addGap(6, 6, 6)
                        .addComponent(jLabel43)
                        .addGap(23, 23, 23)
                        .addComponent(jButton6))))
        );

        jPanel12.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 570, 650));

        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel49.setText("Teacher Enrolment");
        jLabel49.setFont(new java.awt.Font("Copperplate Gothic Light", 1, 24)); // NOI18N
        jPanel10.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(145, 28, -1, -1));

        jLabel13.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jLabel13.setText("First name");
        jPanel10.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 104, -1, -1));

        jLabel50.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jLabel50.setText("Last Name");
        jPanel10.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 136, -1, -1));

        jLabel51.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jLabel51.setText("NIC");
        jPanel10.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 168, -1, -1));

        jLabel52.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jLabel52.setText("Mobile");
        jPanel10.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 200, -1, -1));

        jLabel53.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jLabel53.setText("Subject");
        jPanel10.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 232, -1, -1));

        jTextField15.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel10.add(jTextField15, new org.netbeans.lib.awtextra.AbsoluteConstraints(174, 102, 272, -1));

        jTextField16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel10.add(jTextField16, new org.netbeans.lib.awtextra.AbsoluteConstraints(174, 134, 272, -1));

        jTextField17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel10.add(jTextField17, new org.netbeans.lib.awtextra.AbsoluteConstraints(174, 166, 272, -1));

        jTextField18.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel10.add(jTextField18, new org.netbeans.lib.awtextra.AbsoluteConstraints(174, 198, 272, -1));

        jLabel54.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jLabel54.setText("Address");
        jPanel10.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 264, -1, -1));

        jTextField20.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextField20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField20ActionPerformed(evt);
            }
        });
        jPanel10.add(jTextField20, new org.netbeans.lib.awtextra.AbsoluteConstraints(174, 262, 272, -1));

        jLabel55.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jLabel55.setText("City");
        jPanel10.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 296, -1, -1));

        jComboBox1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel10.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(174, 294, 272, -1));

        jButton16.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jButton16.setText("Register");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });
        jPanel10.add(jButton16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 347, 173, -1));

        jComboBox2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel10.add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(174, 230, 272, -1));

        jLabel56.setText("___________________________________________________________________________________________");
        jPanel10.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 378, -1, -1));

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tno", "First Name", "Last Name", "NIC", "Mobile", "Subject", "Address", "City"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable4MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTable4);

        jPanel10.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 403, 540, 226));

        jButton17.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jButton17.setText("Update");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });
        jPanel10.add(jButton17, new org.netbeans.lib.awtextra.AbsoluteConstraints(201, 347, 170, -1));

        jButton18.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jButton18.setText("Delete");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });
        jPanel10.add(jButton18, new org.netbeans.lib.awtextra.AbsoluteConstraints(389, 347, 171, -1));

        jLabel57.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jLabel57.setText("Tno");
        jPanel10.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 79, -1, -1));

        jLabel58.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 12)); // NOI18N
        jLabel58.setText(" ");
        jPanel10.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(174, 81, 79, -1));

        jPanel12.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 570, 650));

        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel59.setFont(new java.awt.Font("Copperplate Gothic Light", 1, 24)); // NOI18N
        jLabel59.setText("Student Attendence");
        jPanel11.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(142, 29, -1, -1));

        jLabel60.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jLabel60.setText("NIC");
        jPanel11.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 104, -1, -1));

        jTextField19.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextField19.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField19KeyReleased(evt);
            }
        });
        jPanel11.add(jTextField19, new org.netbeans.lib.awtextra.AbsoluteConstraints(142, 102, 296, -1));

        jLabel61.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jLabel61.setText("First Name");
        jPanel11.add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 182, -1, -1));

        jLabel62.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jLabel62.setText("Last Name");
        jPanel11.add(jLabel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 217, -1, -1));

        jLabel63.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jLabel63.setText("Mobile");
        jPanel11.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 252, -1, -1));

        jLabel64.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jLabel64.setText("Email");
        jPanel11.add(jLabel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 287, -1, -1));

        jLabel65.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jLabel65.setText("Address");
        jPanel11.add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 322, -1, -1));

        jLabel66.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel66.setText(" ");
        jPanel11.add(jLabel66, new org.netbeans.lib.awtextra.AbsoluteConstraints(142, 183, 296, -1));

        jLabel67.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel67.setText(" ");
        jPanel11.add(jLabel67, new org.netbeans.lib.awtextra.AbsoluteConstraints(142, 218, 296, -1));

        jLabel68.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel68.setText(" ");
        jPanel11.add(jLabel68, new org.netbeans.lib.awtextra.AbsoluteConstraints(142, 253, 296, -1));

        jLabel69.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel69.setText(" ");
        jPanel11.add(jLabel69, new org.netbeans.lib.awtextra.AbsoluteConstraints(142, 288, 296, -1));

        jLabel70.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel70.setText(" ");
        jPanel11.add(jLabel70, new org.netbeans.lib.awtextra.AbsoluteConstraints(142, 323, 296, -1));

        jLabel71.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jLabel71.setText("Date");
        jPanel11.add(jLabel71, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 449, -1, -1));

        jLabel72.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel72.setText(" ");
        jPanel11.add(jLabel72, new org.netbeans.lib.awtextra.AbsoluteConstraints(129, 450, 269, -1));

        jButton19.setText("Mark Attendence");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });
        jPanel11.add(jButton19, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 499, 408, -1));

        jComboBox3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel11.add(jComboBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(129, 417, 269, -1));

        jLabel73.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jLabel73.setText("Class");
        jPanel11.add(jLabel73, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 419, -1, -1));

        jLabel74.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jLabel74.setText("Studend Id");
        jPanel11.add(jLabel74, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 147, -1, -1));

        jLabel75.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel75.setText(" ");
        jPanel11.add(jLabel75, new org.netbeans.lib.awtextra.AbsoluteConstraints(142, 148, 296, -1));

        jPanel12.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 570, 650));

        jPanel13.setMaximumSize(new java.awt.Dimension(570, 650));
        jPanel13.setPreferredSize(new java.awt.Dimension(570, 650));

        jLabel77.setText("Teacher");
        jLabel77.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N

        jLabel78.setText("Date");
        jLabel78.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N

        jLabel79.setText("Time");
        jLabel79.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jComboBox4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox4MouseClicked(evt);
            }
        });

        jLabel80.setText("Subject");
        jLabel80.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N

        jLabel81.setText(" ");
        jLabel81.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jButton22.setText("Register");
        jButton22.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });

        jButton23.setText("Ok");
        jButton23.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 12)); // NOI18N
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });

        jLabel82.setText("...");

        jLabel83.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel83.setText("Class Registration");
        jLabel83.setFont(new java.awt.Font("Copperplate Gothic Bold", 0, 24)); // NOI18N

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addComponent(jLabel83, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel13Layout.createSequentialGroup()
                                        .addComponent(jLabel78, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(53, 53, 53)
                                        .addComponent(datePicker1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel13Layout.createSequentialGroup()
                                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(jLabel80, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel77, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE))
                                            .addComponent(jLabel79, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel13Layout.createSequentialGroup()
                                                .addGap(30, 30, 30)
                                                .addComponent(jLabel82, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel81, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel13Layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                                                .addGap(21, 21, 21)
                                                .addComponent(timePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton23, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(32528, 32528, 32528))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel83)
                .addGap(135, 135, 135)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel77)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton23))
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel80, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel82)
                    .addComponent(jLabel81, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel79, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(timePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel78, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(datePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(263, 263, 263)
                .addComponent(jButton22, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31046, 31046, 31046))
        );

        jPanel12.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 570, 650));

        jPanel14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel76.setFont(new java.awt.Font("Copperplate Gothic Light", 1, 24)); // NOI18N
        jLabel76.setText("Student And Teacher");
        jPanel14.add(jLabel76, new org.netbeans.lib.awtextra.AbsoluteConstraints(145, 28, -1, -1));

        jLabel84.setFont(new java.awt.Font("Copperplate Gothic Bold", 0, 24)); // NOI18N
        jLabel84.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel84.setText(" Enrolment For Subject");
        jPanel14.add(jLabel84, new org.netbeans.lib.awtextra.AbsoluteConstraints(128, 53, -1, 30));

        jLabel85.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jLabel85.setText("Student NIC");
        jPanel14.add(jLabel85, new org.netbeans.lib.awtextra.AbsoluteConstraints(42, 191, -1, -1));

        jTextField21.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextField21.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField21KeyReleased(evt);
            }
        });
        jPanel14.add(jTextField21, new org.netbeans.lib.awtextra.AbsoluteConstraints(205, 189, 324, -1));

        jLabel86.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jLabel86.setText("Subject");
        jPanel14.add(jLabel86, new org.netbeans.lib.awtextra.AbsoluteConstraints(42, 269, 92, -1));

        jTextField22.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextField22.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField22KeyReleased(evt);
            }
        });
        jPanel14.add(jTextField22, new org.netbeans.lib.awtextra.AbsoluteConstraints(205, 267, 324, -1));

        jLabel87.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jLabel87.setText("Teacher");
        jPanel14.add(jLabel87, new org.netbeans.lib.awtextra.AbsoluteConstraints(42, 310, -1, -1));

        jComboBox5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel14.add(jComboBox5, new org.netbeans.lib.awtextra.AbsoluteConstraints(205, 308, 263, -1));

        jButton20.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jButton20.setText("Enrolment");
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });
        jPanel14.add(jButton20, new org.netbeans.lib.awtextra.AbsoluteConstraints(42, 571, 487, -1));

        jLabel88.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jLabel88.setText("Class No");
        jPanel14.add(jLabel88, new org.netbeans.lib.awtextra.AbsoluteConstraints(42, 349, -1, -1));

        jLabel89.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel89.setText("...");
        jPanel14.add(jLabel89, new org.netbeans.lib.awtextra.AbsoluteConstraints(205, 350, 78, -1));

        jButton21.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jButton21.setText("Ok");
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });
        jPanel14.add(jButton21, new org.netbeans.lib.awtextra.AbsoluteConstraints(474, 306, -1, -1));

        jLabel90.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jLabel90.setText("Class Timeslot");
        jPanel14.add(jLabel90, new org.netbeans.lib.awtextra.AbsoluteConstraints(42, 384, -1, -1));

        jLabel91.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jLabel91.setText("Class Date");
        jPanel14.add(jLabel91, new org.netbeans.lib.awtextra.AbsoluteConstraints(42, 419, -1, -1));

        jLabel92.setText(" ");
        jPanel14.add(jLabel92, new org.netbeans.lib.awtextra.AbsoluteConstraints(205, 384, 159, -1));

        jLabel93.setText(" ");
        jPanel14.add(jLabel93, new org.netbeans.lib.awtextra.AbsoluteConstraints(205, 421, 159, -1));

        jLabel94.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jLabel94.setText("Student Id");
        jPanel14.add(jLabel94, new org.netbeans.lib.awtextra.AbsoluteConstraints(42, 228, -1, -1));

        jLabel95.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel95.setText(" ");
        jPanel14.add(jLabel95, new org.netbeans.lib.awtextra.AbsoluteConstraints(205, 229, 62, -1));

        jPanel12.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 560, 630));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // Student Payment
        jPanel8.setVisible(true);
        jPanel3.setVisible(false);
        jPanel5.setVisible(false);
        jPanel7.setVisible(false);
        jPanel6.setVisible(false);
        jPanel9.setVisible(false);
        jPanel10.setVisible(false);
        jPanel11.setVisible(false);
        jPanel13.setVisible(false);
        jPanel14.setVisible(false);

    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // Student Registration
        jPanel3.setVisible(true);
        jPanel5.setVisible(false);
        jPanel7.setVisible(false);
        jPanel6.setVisible(false);
        jPanel8.setVisible(false);
        jPanel9.setVisible(false);
        jPanel10.setVisible(false);
        jPanel11.setVisible(false);
        jPanel13.setVisible(false);
        jPanel14.setVisible(false);

    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // Subject Registration
        jPanel3.setVisible(false);
        jPanel5.setVisible(true);
        jPanel7.setVisible(false);
        jPanel6.setVisible(false);
        jPanel8.setVisible(false);
        jPanel9.setVisible(false);
        jPanel10.setVisible(false);
        jPanel11.setVisible(false);
        jPanel13.setVisible(false);
        jPanel14.setVisible(false);

        loadSubjects();


    }//GEN-LAST:event_jButton8ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // Student Registration
        try {

            String firstname = jTextField1.getText();
            String lastname = jTextField2.getText();
            String email = jTextField3.getText();
            String nic = jTextField4.getText();
            String mobile = jTextField5.getText();

            ButtonModel genderSelection = buttonGroup1.getSelection();

            Date date = jDateChooser1.getDate();

            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            String strDate = format1.format(date);

            //            System.out.println(firstname);
            //            System.out.println(lastname);
            //            System.out.println(email);
            //            System.out.println(nic);
            //            System.out.println(mobile);
            //            System.out.println(strDate);
            //            System.out.println(genderId);
            if (firstname.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Invalid First Name", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (lastname.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Invalid Last Name", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Invalid Email", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (nic.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Invalid NIC", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (mobile.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Invalid Mobile Number", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (strDate.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Invalid Date Of Birth", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                String genderId = buttonGroup1.getSelection().getActionCommand();

                try {
                    ResultSet resultset = MySQL.execute("INSERT INTO "
                            + "`student`(`first_name`,`last_name`,`email`,`nic`,`dob`,`mobile`,`gender_id`) "
                            + "VALUES('" + firstname + "','" + lastname + "','" + email + "','" + nic + "',"
                            + "'" + strDate + "','" + mobile + "','" + genderId + "')");

                    JOptionPane.showMessageDialog(this, "Success", "Information", JOptionPane.INFORMATION_MESSAGE);

                    reset1();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // Subject Registration

        try {

            String subject = jTextField6.getText();
            String price = jTextField7.getText();

//            System.out.println(subject);
//            System.out.println(price);
            if (subject.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Invalid Subject Name", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (price.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Invalid Price", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {

                try {

                    ResultSet resultSet = MySQL.execute("INSERT INTO `subject`(`Description`,`Price`) VALUES('" + subject + "','" + price + "') ");

                    JOptionPane.showMessageDialog(this, "Success", "Information", JOptionPane.INFORMATION_MESSAGE);

                    loadSubjects();
                    reset2();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // User Registration

        jPanel3.setVisible(false);
        jPanel5.setVisible(false);
        jPanel7.setVisible(true);
        jPanel6.setVisible(false);
        jPanel8.setVisible(false);
        jPanel9.setVisible(false);
        jPanel10.setVisible(false);
        jPanel11.setVisible(false);
        jPanel13.setVisible(false);
        jPanel14.setVisible(false);

        loadUsers();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // User Registration
        try {

            String username = jTextField12.getText();
            String password = String.valueOf(jPasswordField2.getPassword());

            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Invalid Username", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Invalid Password", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    ResultSet resultSet = MySQL.execute("SELECT * FROM `admin` WHERE `username`='" + username + "' AND `password`='" + password + "'");

                    if (resultSet.next()) {

                        jTextField12.setText("");
                        jPasswordField2.setText("");

                        JOptionPane.showMessageDialog(this, "Success", "Information", JOptionPane.INFORMATION_MESSAGE);

                        jPanel3.setVisible(false);
                        jPanel5.setVisible(false);
                        jPanel7.setVisible(false);
                        jPanel6.setVisible(true);

                    } else {
                        JOptionPane.showMessageDialog(this, "Invalid Username or Password", "Information", JOptionPane.WARNING_MESSAGE);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }//GEN-LAST:event_jButton5ActionPerformed

    private void jPasswordField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPasswordField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jPasswordField2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // User Registration
        try {

            String firstname = jTextField8.getText();
            String lastname = jTextField9.getText();
            String username = jTextField10.getText();
            String nic = jTextField11.getText();
            String mobile = jTextField13.getText();
            String password = String.valueOf(jPasswordField3.getPassword());

//             System.out.println(firstname);
//             System.out.println(lastname);
//             System.out.println(username);
//             System.out.println(nic);
//             System.out.println(mobile);
//             System.out.println(password);
            if (firstname.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Invalid First Name", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (lastname.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Invalid Last Name", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (username.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Invalid Username", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Invalid Password", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (nic.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Invalid NIC", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (mobile.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Invalid Mobile Number", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {

                try {
                    ResultSet resultset = MySQL.execute("INSERT INTO "
                            + "`user`(`first_name`,`last_name`,`username`,`password`,`nic`,`mobile`) "
                            + "VALUES('" + firstname + "','" + lastname + "','" + username + "','" + password + "','" + nic + "','" + mobile + "')");

                    JOptionPane.showMessageDialog(this, "Success", "Information", JOptionPane.INFORMATION_MESSAGE);

                    loadUsers();
                    reset3();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTextField14KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField14KeyReleased
        // Student Payment
        String nic = jTextField14.getText();

        if (nic.length() == 12) {
            try {
                ResultSet resultSet = MySQL.execute("SELECT * FROM `student` WHERE `nic`='" + nic + "'");

                jLabel34.setText(" ");
                jLabel35.setText(" ");
                jLabel36.setText(" ");
                jLabel37.setText(" ");
                jLabel38.setText(" ");
                jLabel39.setText(" ");
                jLabel98.setText(" ");

                if (resultSet.next()) {
                    String firstname = resultSet.getString("first_name");
                    String lastname = resultSet.getString("last_name");
                    String email = resultSet.getString("email");
                    String mobile = resultSet.getString("mobile");
                    String address = resultSet.getString("address");
                    String dob = resultSet.getString("dob");
                    String sno = resultSet.getString("sno");

                    jLabel34.setText(firstname);
                    jLabel35.setText(lastname);
                    jLabel36.setText(email);
                    jLabel37.setText(mobile);
                    jLabel38.setText(dob);
                    jLabel39.setText(address);
                    jLabel98.setText(sno);
                    loadMonths();

                    try {

                        ResultSet resultset = MySQL.execute("SELECT * FROM `subject`");

                        DefaultTableModel model = (DefaultTableModel) jTable3.getModel();
                        model.setRowCount(0);

                        while (resultset.next()) {

                            Vector<String> v = new Vector();
                            v.add(resultset.getString("Subno"));
                            v.add(resultset.getString("Description"));
                            v.add(resultset.getString("Price"));

                            model.addRow(v);
                            jTable3.setModel(model);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }//GEN-LAST:event_jTextField14KeyReleased

    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseClicked
        // Student Payment

        if (evt.getClickCount() == 2) {

            int selectedRow = jTable3.getSelectedRow();

            String subno = String.valueOf(jTable3.getValueAt(selectedRow, 0));
            jLabel45.setText(subno);

            String descriptionn = String.valueOf(jTable3.getValueAt(selectedRow, 1));
            jLabel42.setText(descriptionn);

            String price = String.valueOf(jTable3.getValueAt(selectedRow, 2));
            jLabel43.setText(price);
        }

    }//GEN-LAST:event_jTable3MouseClicked

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // Subject Registration

        if (evt.getClickCount() == 2) {

            jButton3.setEnabled(false);
            jTable1.setEnabled(false);

            int selectedRow = jTable1.getSelectedRow();

            String subno = String.valueOf(jTable1.getValueAt(selectedRow, 0));
            jLabel47.setText(subno);

            String descriptionn = String.valueOf(jTable1.getValueAt(selectedRow, 1));
            jTextField6.setText(descriptionn);

            String price = String.valueOf(jTable1.getValueAt(selectedRow, 2));
            jTextField7.setText(price);
        }

    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // Subject Registration

        int selectedRow = jTable1.getSelectedRow();

        if (selectedRow == -1) {
            //System.out.println("Please select row");
            JOptionPane.showMessageDialog(this, "Please Select Row", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            String subno = String.valueOf(jTable1.getValueAt(selectedRow, 0));

//            String subno = jLabel47.getText();
            String subject = jTextField6.getText();
            String price = jTextField7.getText();

            if (subject.isEmpty()) {
                //System.out.println("Invalid First Name");
                JOptionPane.showMessageDialog(this, "Invalid Subject", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (price.isEmpty()) {
                //System.out.println("Invalid Last Name");
                JOptionPane.showMessageDialog(this, "Invalid Price", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {

                try {
                    ResultSet resultset = MySQL.execute("UPDATE `subject` SET `Description`='" + subject + "',"
                            + " `Price`='" + price + "' WHERE `Subno`='" + subno + "'");

                    loadSubjects();
                    reset2();

                    jTable1.setEnabled(true);
                    jButton3.setEnabled(true);

                    JOptionPane.showMessageDialog(this, "Success", "Information", JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // Subject Registration

        int selectedRow = jTable1.getSelectedRow();

        if (selectedRow == -1) {
            //System.out.println("Please select row");
            JOptionPane.showMessageDialog(this, "Please Select Row", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            String id = String.valueOf(jTable1.getValueAt(selectedRow, 0));

            try {
                ResultSet resultset = MySQL.execute("DELETE FROM `subject` WHERE `Subno`='" + id + "'");

                loadSubjects();
                reset2();
                jTable1.setEnabled(true);
                jButton3.setEnabled(true);

                JOptionPane.showMessageDialog(this, "Success", "Information", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jTextField20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField20ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField20ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // Teacher Enrolment

        jPanel10.setVisible(true);
        jPanel3.setVisible(false);
        jPanel5.setVisible(false);
        jPanel7.setVisible(false);
        jPanel6.setVisible(false);
        jPanel8.setVisible(false);
        jPanel9.setVisible(false);
        jPanel11.setVisible(false);
        jPanel13.setVisible(true);
        jPanel14.setVisible(false);

        loadTeachers();
        loadCitys();
        loadSubjects1();

        jButton17.setEnabled(false);
        jButton18.setEnabled(false);

        jLabel58.setText(" ");
        jLabel57.setVisible(false);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        // Teacher Enrolment

        try {
            String fname = jTextField15.getText();
            String lname = jTextField16.getText();
            String nic = jTextField17.getText();
            String mobile = jTextField18.getText();
            String address = jTextField20.getText();
            String city = String.valueOf(jComboBox1.getSelectedItem());
            String subject = String.valueOf(jComboBox2.getSelectedItem());

//            System.out.println(fname);
//            System.out.println(lname);
//            System.out.println(nic);
//            System.out.println(mobile);
//            System.out.println(address);
//            System.out.println(city);
//            System.out.println(subject);
            if (fname.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Invalid First Name", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (lname.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Invalid Last Name", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (nic.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Invalid NIC", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (mobile.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Invalid Mobile", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (subject.equals("Select")) {
                JOptionPane.showMessageDialog(this, "Invalid Subject", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (address.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Invalid Address", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (city.equals("Select")) {
                JOptionPane.showMessageDialog(this, "Invalid City", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                int cityId = cityMap.get(city);
                int subjectId = subjectMap.get(subject);

                try {
                    ResultSet resultset = MySQL.execute("INSERT INTO `teacher`(`first_name`,`last_name`,`address`,`nic`,`mobile`,`subject_Subno`,`city_id`)"
                            + " VALUES('" + fname + "','" + lname + "','" + address + "','" + nic + "','" + mobile + "','" + subjectId + "','" + cityId + "')");

                    JOptionPane.showMessageDialog(this, "Success", "Information", JOptionPane.INFORMATION_MESSAGE);

                    reset4();
                    loadTeachers();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jTable4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable4MouseClicked
        // Teacher Enrolment
        if (evt.getClickCount() == 2) {

            jButton16.setEnabled(false);
            jButton17.setEnabled(true);
            jButton18.setEnabled(true);
            jTable4.setEnabled(false);

            int selectedRow = jTable4.getSelectedRow();

            String tno = String.valueOf(jTable4.getValueAt(selectedRow, 0));
            jLabel58.setText(tno);

            String fname = String.valueOf(jTable4.getValueAt(selectedRow, 1));
            jTextField15.setText(fname);

            String lname = String.valueOf(jTable4.getValueAt(selectedRow, 2));
            jTextField16.setText(lname);

            String nic = String.valueOf(jTable4.getValueAt(selectedRow, 3));
            jTextField17.setText(nic);

            String mobile = String.valueOf(jTable4.getValueAt(selectedRow, 4));
            jTextField18.setText(mobile);

            String subject = String.valueOf(jTable4.getValueAt(selectedRow, 5));
            jComboBox2.setSelectedItem(subject);

            String address = String.valueOf(jTable4.getValueAt(selectedRow, 6));
            jTextField20.setText(address);

            String city = String.valueOf(jTable4.getValueAt(selectedRow, 7));
            jComboBox1.setSelectedItem(city);

        }


    }//GEN-LAST:event_jTable4MouseClicked

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        // Teacher Enrolment
        jLabel57.setVisible(true);
        try {
            String tno = jLabel58.getText();
            String fname = jTextField15.getText();
            String lname = jTextField16.getText();
            String nic = jTextField17.getText();
            String mobile = jTextField18.getText();
            String address = jTextField20.getText();
            String city = String.valueOf(jComboBox1.getSelectedItem());
            String subject = String.valueOf(jComboBox2.getSelectedItem());

            if (fname.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Invalid First Name", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (lname.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Invalid Last Name", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (nic.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Invalid NIC", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (mobile.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Invalid Mobile", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (subject.equals("Select")) {
                JOptionPane.showMessageDialog(this, "Invalid Subject", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (address.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Invalid Address", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (city.equals("Select")) {
                JOptionPane.showMessageDialog(this, "Invalid City", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                int cityId = cityMap.get(city);
                int subjectId = subjectMap.get(subject);

                try {
                    ResultSet resultset = MySQL.execute("UPDATE `Teacher` SET "
                            + "`first_name`='" + fname + "',"
                            + " `last_name`='" + lname + "',"
                            + " `address`='" + address + "',"
                            + " `nic`='" + nic + "',"
                            + " `mobile`='" + mobile + "',"
                            + " `city_id`='" + cityId + "', "
                            + " `subject_Subno`='" + subjectId + "' "
                            + " WHERE `Tno`='" + tno + "'");

                    JOptionPane.showMessageDialog(this, "Success", "Information", JOptionPane.INFORMATION_MESSAGE);

                    jLabel58.setText(" ");
                    jLabel57.setVisible(false);

                    jButton16.setEnabled(true);
                    jButton17.setEnabled(false);
                    jButton18.setEnabled(false);
                    reset4();
                    loadTeachers();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        // Teacher Enrolment
        jLabel57.setVisible(true);
        try {
            String tno = jLabel58.getText();

            ResultSet resultset = MySQL.execute("DELETE FROM `teacher` WHERE `Tno`='" + tno + "'");

            JOptionPane.showMessageDialog(this, "Success", "Information", JOptionPane.INFORMATION_MESSAGE);

            jLabel58.setText(" ");
            jLabel57.setVisible(false);

            jButton16.setEnabled(true);
            jButton17.setEnabled(false);
            jButton18.setEnabled(false);
            reset4();
            loadTeachers();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jTextField19KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField19KeyReleased
        // Student Attendece

        String nic = jTextField19.getText();

        if (nic.length() == 12) {
            try {
                ResultSet resultSet = MySQL.execute("SELECT * FROM `student` WHERE `nic`='" + nic + "'");

                jComboBox3.setEnabled(true);
                jLabel75.setText(" ");
                jLabel66.setText(" ");
                jLabel67.setText(" ");
                jLabel68.setText(" ");
                jLabel69.setText(" ");
                jLabel70.setText(" ");

                if (resultSet.next()) {
                    String firstname = resultSet.getString("first_name");
                    String lastname = resultSet.getString("last_name");
                    String email = resultSet.getString("email");
                    String mobile = resultSet.getString("mobile");
                    String address = resultSet.getString("address");
                    String sno = resultSet.getString("Sno");

                    jLabel75.setText(sno);
                    jLabel66.setText(firstname);
                    jLabel67.setText(lastname);
                    jLabel68.setText(email);
                    jLabel69.setText(mobile);
                    jLabel70.setText(address);

                    loadClass();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            reset5();
        }

    }//GEN-LAST:event_jTextField19KeyReleased

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // Student Attendence
        jPanel11.setVisible(true);
        jPanel10.setVisible(false);
        jPanel3.setVisible(false);
        jPanel5.setVisible(false);
        jPanel7.setVisible(false);
        jPanel6.setVisible(false);
        jPanel8.setVisible(false);
        jPanel9.setVisible(false);
        jPanel13.setVisible(false);
        jPanel14.setVisible(false);
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        // Student Attendence

        try {
            jComboBox3.setEnabled(true);
            String sno = jLabel75.getText();
            String subject = String.valueOf(jComboBox3.getSelectedItem());
            String date = jLabel72.getText();

//            System.out.println(sno);
//            System.out.println(date);
//            System.out.println(subjectId);
            if (sno.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please Enter Student NIC First", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (subject.equals("Select")) {
                JOptionPane.showMessageDialog(this, "Invalid Subject", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                int subjectId = attendenceMap.get(subject);
                try {

                    ResultSet resultSet = MySQL.execute("INSERT INTO `attendence`(`student_id`,`class_id`,`date`) "
                            + "VALUES ('" + sno + "','" + subjectId + "','" + date + "')");

                    JOptionPane.showMessageDialog(this, "Success", "Information", JOptionPane.INFORMATION_MESSAGE);

                    reset5();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_jButton19ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // Class Registration

        jPanel13.setVisible(true);
        jPanel11.setVisible(false);
        jPanel10.setVisible(false);
        jPanel3.setVisible(false);
        jPanel5.setVisible(false);
        jPanel7.setVisible(false);
        jPanel6.setVisible(false);
        jPanel8.setVisible(false);
        jPanel9.setVisible(false);
        jPanel14.setVisible(false);

        loadTeachers1();
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jComboBox4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox4MouseClicked

    }//GEN-LAST:event_jComboBox4MouseClicked

    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed
        // Class Registration
        try {
            String teacher = String.valueOf(jComboBox4.getSelectedItem());
            int teacherId = teacherMap.get(teacher);
            ResultSet resultSet = MySQL.execute("SELECT * FROM `teacher` INNER JOIN `subject` ON"
                    + " `subject`.`Subno`=`teacher`.`subject_Subno` WHERE `Tno`='" + teacherId + "'");
            if (resultSet.next()) {
                String subject = resultSet.getString("Description");
                String subjectId = resultSet.getString("Subno");

                jLabel81.setText(subject);
                jLabel82.setText(subjectId);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_jButton23ActionPerformed

    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed
        // Class Registration
        try {
            String subject = jLabel82.getText();
            String teacher = String.valueOf(jComboBox4.getSelectedItem());
            LocalTime time = timePicker1.getTime();
            LocalDate date = datePicker1.getDate();

            if (teacher.equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please Select Teacher", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (subject.equals("...")) {
                JOptionPane.showMessageDialog(this, "Please Press Ok Button First", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {

                int teacherId = teacherMap.get(teacher);

                ResultSet resultSet = MySQL.execute("INSERT INTO `class`(`timeslot`,`date`,`teacher_Tno`,subject_Subno) "
                        + "VALUES ('" + time + "','" + date + "','" + teacherId + "','" + subject + "')");

                JOptionPane.showMessageDialog(this, "Success", "Information", JOptionPane.INFORMATION_MESSAGE);
            }

//            System.out.println(teacherId);
//            System.out.println(subject);
//            System.out.println(time);
//            System.out.println(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_jButton22ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // Student and Teacher enrolment for Subject
        jPanel14.setVisible(true);
        jPanel13.setVisible(false);
        jPanel11.setVisible(false);
        jPanel10.setVisible(false);
        jPanel3.setVisible(false);
        jPanel5.setVisible(false);
        jPanel7.setVisible(false);
        jPanel6.setVisible(false);
        jPanel8.setVisible(false);
        jPanel9.setVisible(false);

        jTextField22.setEnabled(false);
        jComboBox5.setEnabled(false);
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jTextField21KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField21KeyReleased
        // Student and Teacher enrolment for Subject

        String nic = jTextField21.getText();

        if (nic.length() == 12) {
            try {
                ResultSet resultSet = MySQL.execute("SELECT * FROM `student` WHERE `nic`='" + nic + "'");

                if (resultSet.next()) {
                    
                    String sno = resultSet.getString("Sno");

                    jLabel95.setText(sno);
                    jComboBox5.setEnabled(false);
                    jButton21.setEnabled(false);
                    jTextField22.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid Student NIC Number", "Warning", JOptionPane.WARNING_MESSAGE);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jTextField21KeyReleased

    private void jTextField22KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField22KeyReleased
        // Student and Teacher enrolment for Subject

        String subject = jTextField22.getText();

        try {
            ResultSet resultSet = MySQL.execute("SELECT * FROM `class` "
                    + "INNER JOIN `teacher` ON `teacher`.`Tno`=`class`.`teacher_Tno` "
                    + "INNER JOIN `subject` ON `class`.`subject_Subno`=`subject`.`Subno` WHERE `Description` LIKE '" + subject + "'");

            if (resultSet.next()) {
                loadTeachers2();
                jComboBox5.setEnabled(true);
                
                jButton21.setEnabled(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_jTextField22KeyReleased

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        // Student and Teacher enrolment for Subject
        String teacher = String.valueOf(jComboBox5.getSelectedItem());
        

        try {
            int teacherId = teacher2Map.get(teacher);
            ResultSet resultset = MySQL.execute("SELECT * FROM `class` "
                + "INNER JOIN `teacher` ON `teacher`.`Tno`=`class`.`teacher_Tno` "
                + "INNER JOIN `subject` ON `class`.`subject_Subno`=`subject`.`Subno` WHERE `Tno`='" + teacherId + "'");
            if (resultset.next()) {
                String classId = resultset.getString("ClassNo");
                String timeslot = resultset.getString("timeslot");
                String date = resultset.getString("date");

                jLabel89.setText(classId);
                jLabel92.setText(timeslot);
                jLabel93.setText(date);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }//GEN-LAST:event_jButton21ActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        // Student and Teacher enrolment for Subject
        String sno = jLabel95.getText();
        String classid = jLabel89.getText();
        
        if (classid.equals("...")) {
            JOptionPane.showMessageDialog(this, "Please Click OK Button First", "Warning", JOptionPane.WARNING_MESSAGE);
        }else{
            ResultSet resultSet = MySQL.execute("INSERT INTO `add_students_for_class`(`class_id`,`student_id`) VALUES ('"+sno+"','"+classid+"')");
            JOptionPane.showMessageDialog(this, "Success", "Information", JOptionPane.INFORMATION_MESSAGE);
            
            jComboBox5.setEnabled(false);
            jButton21.setEnabled(false);
            jTextField21.setText("");
            jTextField22.setText("");
            jLabel95.setText("");
            jLabel89.setText("...");
            jLabel92.setText("");
            jLabel93.setText("");
            
        }
        
    }//GEN-LAST:event_jButton20ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // Student Payment
        try {
            String sno = jLabel98.getText();
            String fname = jLabel34.getText();
            String lname = jLabel35.getText();
            String mobile = jLabel36.getText();
            String email = jLabel37.getText();
            String subno = jLabel45.getText();
            String subject = jLabel42.getText();
            String price = jLabel43.getText();
            String month = String.valueOf(jComboBox6.getSelectedItem());
            int monthId = monthMap.get(month);
            
            if(month.equals("Select")){
                JOptionPane.showMessageDialog(this, "Invalid Month", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if(subno.isEmpty()){
                JOptionPane.showMessageDialog(this, "Please Select Subject From Table", "Warning", JOptionPane.WARNING_MESSAGE);
            } else{
                ResultSet resultSet = MySQL.execute("INSERT INTO `invoice`(`month_id`,`student_Sno`,`subject_Subno`,`Value`) "
                        + "VALUES('"+monthId+"','"+sno+"','"+subno+"','"+price+"')");
                
                HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("Parameter1", "Test1");//fname
            parameters.put("Parameter2", "Test2");//lname
            parameters.put("Parameter3", "Test3");//mobile
            parameters.put("Parameter4", "Test3");//email
            parameters.put("Parameter5", "Test3");//month
            parameters.put("Parameter6", "Test3");//subject
            parameters.put("Parameter7", "Test3");//price
            
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/adyapana", "root", "20010406@Mcj");
            
            JasperPrint report = JasperFillManager.fillReport("src/reports/report_1.jasper", parameters, connection);
            JasperViewer.viewReport(report, true);
            }
        } catch (Exception e) {
        e.printStackTrace();
        }
        
        
    }//GEN-LAST:event_jButton6ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        FlatDarkLaf.setup();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private com.github.lgooddatepicker.components.DatePicker datePicker1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private com.toedter.calendar.JDateChooser jDateChooser1;
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
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPasswordField jPasswordField2;
    private javax.swing.JPasswordField jPasswordField3;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField20;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField22;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private com.github.lgooddatepicker.components.TimePicker timePicker1;
    // End of variables declaration//GEN-END:variables
}
