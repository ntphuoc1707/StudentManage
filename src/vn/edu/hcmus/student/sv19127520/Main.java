package vn.edu.hcmus.student.sv19127520;

/**
 * vn.edu.hcmus.student.sv19127520;
 * Created by Phuoc -19127520
 * Date 25/11/2021 - 08:07 CH
 * Description: ...
 */

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.*;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Main {
    static Connection con;
    static Statement st;
    static JTable table;
    static SpringLayout layout=new SpringLayout();
    static int h=400;
    static int w=600;
    public static String randomName(int len) {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijk"
                +"lmnopqrstuvwxyz ";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();
    }
    public static float randomMark() {
        String chars = "0123456789";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(2);
        return Float.parseFloat(sb.append(chars.charAt(rnd.nextInt(chars.length())))+"."+sb.append(chars.charAt(rnd.nextInt(chars.length()))));

    }
    public static void createData(){
        Student[] r=new Student[100];
        for (int i=0;i<100;i++){
            r[i]=new Student(String.valueOf(i+100),randomName(7),randomMark(),"none","none","none");
        }
        ListStudents t=new ListStudents(r);
        try{
        String str="DELETE FROM STUDENTS";
        st.executeUpdate(str);
        for(int i=0;i<t.size();i++) {
            Student k = t.getStudent(i);
            String strsql = "insert into STUDENTS(id, name, mark, image, address, note) values('"
                    + k.getid() + "', '"
                    + k.getName() + "', "
                    + (double) Math.round(k.getMark()*10)/10 + ", '"
                    + k.getImage() + "', '"
                    + k.getAddress() + "', '"
                    + k.getNote() + "')";
            st.executeUpdate(strsql);
        }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    public static ListStudents Load(){
        ListStudents t=new ListStudents();
        try{
            ResultSet rs=st.executeQuery("SELECT * FROM STUDENTS");
            Vector<Student> v= new Vector<>();
            while(rs.next()){
                Student k=new Student(rs.getString(1),
                        rs.getString(2),
                        (double) Math.round(rs.getFloat(3)*10)/10,
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6)
                        );
                v.add(k);
            }
            Student[] q=new Student[v.size()];
            for(int i=0;i<q.length;i++)
                q[i]= v.get(i);
            t= new ListStudents(q);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return t;
    }
    static ActionListener y=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String s=e.getActionCommand();
            if(s.equals("List Students")){
                h=f.getBounds().height;
                w=f.getBounds().width;
                f.dispose();
                createAndShowList();
            }
            if(s.equals("Return")){
                h=f2.getBounds().height;
                w=f2.getBounds().width;
                f2.dispose();
                createAndShowMenu();
            }
            if(s.equals("Add Student")){
                h=f.getBounds().height;
                w=f.getBounds().width;
                f.dispose();
                createAndShowAddStudent();
            }
            if(s.equals("Update Student")){
                h=f.getBounds().height;
                w=f.getBounds().width;
                f.dispose();
                createAndShowUpdateStudent();
            }
            if(s.equals("Delete Student")){
                h=f.getBounds().height;
                w=f.getBounds().width;
                f.dispose();
                createAndShowDeleteStudent();
            }
            if(s.equals("Exit")){
                try {
                    con.close();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                f.dispose();
            }
            if(s.equals("Export to CSV")){
                exportToExcel();
                JOptionPane.showMessageDialog(f2,"Export Successfully","",JOptionPane.INFORMATION_MESSAGE);
            }

        }
    };
    static JPanel middle=new JPanel();
    static JFrame f=new JFrame("Students management");

    public static void createAndShowMenu(){
        f2.dispose();
        f3.dispose();
        JFrame.setDefaultLookAndFeelDecorated(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setMinimumSize(new Dimension(w,h));

        middle.removeAll();
        middle.repaint();

        JPanel p=new JPanel();
        p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
        p.setAlignmentY(Component.CENTER_ALIGNMENT);
        JLabel j=new JLabel("STUDENTS MANAGEMENT");
        j.setFont(new Font("Verdana",Font.BOLD,30));
        j.setForeground(Color.red);
        j.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(j);
        p.add(Box.createRigidArea(new Dimension(0,100)));
        p.setBackground(Color.getHSBColor(0.5f,0.591f,0.922f));


        JButton button1=new JButton("List Students");
        button1.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(button1);
        p.add(Box.createRigidArea(new Dimension(0,5)));

        JButton button2=new JButton("Add Student");
        button2.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(button2);
        p.add(Box.createRigidArea(new Dimension(0,5)));

        JButton button3=new JButton("Update Student");
        button3.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(button3);
        p.add(Box.createRigidArea(new Dimension(0,5)));

        JButton button4=new JButton("Delete Student");
        button4.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(button4);
        p.add(Box.createRigidArea(new Dimension(0,5)));

        JButton button5=new JButton("Exit");
        button5.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(button5);

        button1.addActionListener(y);
        button2.addActionListener(y);
        button3.addActionListener(y);
        button4.addActionListener(y);
        button5.addActionListener(y);
        middle.setLayout(layout);
        middle.add(p);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER,p,0,SpringLayout.HORIZONTAL_CENTER,middle);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER,p,0,SpringLayout.VERTICAL_CENTER,middle);
        middle.setBackground(Color.getHSBColor(0.5f,0.591f,0.922f));
        f.add(middle);
        f.pack();
        f.setVisible(true);
    }

    public static void exportToExcel(){

        Workbook workbook=new XSSFWorkbook();
        Sheet sheet=workbook.createSheet("List Students");
        Row headerRow = sheet.createRow(0);
        CellStyle headerCellStyle = workbook.createCellStyle();

        String[] columns={"ID","Name","Mark","Image","Address","Note"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }
        int rowNum = 1;
        for(int i=0;i<table.getRowCount();i++){
            Row row=sheet.createRow(rowNum++);
            row.createCell(0).setCellValue((String) table.getValueAt(i,1));
            row.createCell(1).setCellValue((String) table.getValueAt(i,2));
            row.createCell(2).setCellValue((String) table.getValueAt(i,3));
            row.createCell(3).setCellValue((String) table.getValueAt(i,4));
            row.createCell(4).setCellValue((String) table.getValueAt(i,5));
            row.createCell(5).setCellValue((String) table.getValueAt(i,6));
        }
        try {
            FileOutputStream fileOut = new FileOutputStream("ListStudent.xlsx");
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
    static JFrame f2=new JFrame("List Students");
    public static void createAndShowList(){
        f.dispose();
        f2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f2.setMinimumSize(new Dimension(w,h));
        middle.removeAll();
        middle.repaint();
        ListStudents l=Load();
        String[][]data=new String[l.size()][7];
        for(int i=0;i<l.size();i++){
            data[i][0]=String.valueOf(i+1);
            data[i][1]=l.getStudent(i).getid();
            data[i][2]=l.getStudent(i).getName();
            data[i][3]=String.valueOf(l.getStudent(i).getMark());
            data[i][4]=l.getStudent(i).getImage();
            data[i][5]=l.getStudent(i).getAddress();
            data[i][6]=l.getStudent(i).getNote();
        }
        String[] columnNames={"Serial","ID","Name","Mark","Image","Address","Note"};

        table=new JTable(data,columnNames);
        table.setBounds(30,40,200,100);
        JScrollPane scrollPane=new JScrollPane(table);
        table.setAutoCreateRowSorter(true);
        table.setRowHeight(30);
        TableColumnModel columnModel=table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(10);
        columnModel.getColumn(1).setPreferredWidth(30);
        columnModel.getColumn(2).setPreferredWidth(100);
        columnModel.getColumn(3).setPreferredWidth(40);
        columnModel.getColumn(6).setPreferredWidth(200);

        JPanel p=new JPanel();
        middle.setLayout(layout);
        middle.add(scrollPane);
        JButton export=new JButton("Export to CSV");
        JButton cancel=new JButton("Return");
        export.addActionListener(y);
        cancel.addActionListener(y);
        p.setSize(new Dimension(200,200));
        p.add(export);
        p.add(Box.createRigidArea(new Dimension(5,0)));
        p.add(cancel);
        p.setBackground(Color.getHSBColor(0.5f,0.591f,0.922f));
        JPanel x=new JPanel();
        x.setLayout(new BoxLayout(x,BoxLayout.PAGE_AXIS));
        JPanel k=new JPanel();
        k.setBackground(Color.getHSBColor(0.5f,0.591f,0.922f));
        JPanel kk=new JPanel();
        kk.setBackground(Color.getHSBColor(0.5f,0.591f,0.922f));
        x.add(k);
        x.add(p);
        x.add(kk);
        middle.add(x);
        layout.putConstraint(SpringLayout.WEST,scrollPane,5,SpringLayout.WEST,middle);
        layout.putConstraint(SpringLayout.EAST,middle,5,SpringLayout.EAST,scrollPane);
        layout.putConstraint(SpringLayout.NORTH,scrollPane,5,SpringLayout.NORTH,middle);
        layout.putConstraint(SpringLayout.SOUTH,scrollPane,5,SpringLayout.NORTH,x);
        layout.putConstraint(SpringLayout.SOUTH,x,5,SpringLayout.SOUTH,middle);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER,x,5,SpringLayout.HORIZONTAL_CENTER,middle);
        f2.add(middle);
        f2.pack();
        f2.setVisible(true);
    }


    static JFrame f3=new JFrame("Add Student");
    public static void createAndShowAddStudent(){
        middle.removeAll();
        middle.repaint();
        f3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f3.setMinimumSize(new Dimension(w,h));
        f3.setLayout(new GridLayout(8,0));

        //Profile
        JPanel pro=new JPanel();
        JLabel label=new JLabel("PROFILE");
        label.setFont(new Font("",Font.BOLD,20));
        pro.add(label);
        pro.setBackground(Color.getHSBColor(0.5f,0.591f,0.922f));
        //id
        JPanel id=new JPanel();
        id.setLayout(layout);
        JLabel _id=new JLabel("ID: ");
        JTextField _textId=new JTextField("",15);
        _textId.setPreferredSize(new Dimension(100,30));
        id.add(_id);
        id.add(_textId);
        layout.putConstraint(SpringLayout.WEST,_id,5,SpringLayout.WEST,id);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER,_id,0,SpringLayout.VERTICAL_CENTER,id);
        layout.putConstraint(SpringLayout.WEST,_textId,5,SpringLayout.EAST,_id);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER,_textId,0,SpringLayout.VERTICAL_CENTER,id);
        id.setBackground(Color.getHSBColor(0.5f,0.591f,0.922f));

        //name
        JPanel name=new JPanel();
        name.setLayout(layout);
        JLabel _name=new JLabel("Name: ");
        JTextField _textName=new JTextField("",40);
        _textName.setPreferredSize(new Dimension(100,30));
        name.add(_name);
        name.add(_textName);
        layout.putConstraint(SpringLayout.WEST,_name,5,SpringLayout.WEST,name);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER,_name,0,SpringLayout.VERTICAL_CENTER,name);
        layout.putConstraint(SpringLayout.WEST,_textName,5,SpringLayout.EAST,_name);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER,_textName,0,SpringLayout.VERTICAL_CENTER,name);
        name.setBackground(Color.getHSBColor(0.5f,0.591f,0.922f));

        //Mark
        JPanel mark=new JPanel();
        mark.setLayout(layout);
        JLabel _mark=new JLabel("Mark: ");
        JTextField _textMark=new JTextField("",5);
        mark.add(_mark);
        mark.add(_textMark);
        layout.putConstraint(SpringLayout.WEST,_mark,5,SpringLayout.WEST,mark);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER,_mark,0,SpringLayout.VERTICAL_CENTER,mark);
        layout.putConstraint(SpringLayout.WEST,_textMark,5,SpringLayout.EAST,_mark);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER,_textMark,0,SpringLayout.VERTICAL_CENTER,mark);
        mark.setBackground(Color.getHSBColor(0.5f,0.591f,0.922f));

        //Image
        JPanel image=new JPanel();
        image.setLayout(layout);
        JLabel _image=new JLabel("Image: ");
        JTextField _textImage=new JTextField("",15);
        image.add(_image);
        image.add(_textImage);
        layout.putConstraint(SpringLayout.WEST,_image,5,SpringLayout.WEST,image);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER,_image,0,SpringLayout.VERTICAL_CENTER,image);
        layout.putConstraint(SpringLayout.WEST,_textImage,5,SpringLayout.EAST,_image);
        layout.putConstraint(SpringLayout.NORTH,_textImage,5,SpringLayout.NORTH,image);
        layout.putConstraint(SpringLayout.SOUTH,image,5,SpringLayout.SOUTH,_textImage);
        layout.putConstraint(SpringLayout.EAST,image,5,SpringLayout.EAST,_textImage);
        image.setBackground(Color.getHSBColor(0.5f,0.591f,0.922f));

        //Address
        JPanel address=new JPanel();
        address.setLayout(layout);
        JLabel _address=new JLabel("Address: ");
        JTextField _textAddress=new JTextField("",15);
        address.add(_address);
        address.add(_textAddress);
        layout.putConstraint(SpringLayout.WEST,_address,5,SpringLayout.WEST,address);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER,_address,0,SpringLayout.VERTICAL_CENTER,address);
        layout.putConstraint(SpringLayout.WEST,_textAddress,5,SpringLayout.EAST,_address);
        layout.putConstraint(SpringLayout.NORTH,_textAddress,5,SpringLayout.NORTH,address);
        layout.putConstraint(SpringLayout.SOUTH,address,5,SpringLayout.SOUTH,_textAddress);
        layout.putConstraint(SpringLayout.EAST,address,5,SpringLayout.EAST,_textAddress);
        address.setBackground(Color.getHSBColor(0.5f,0.591f,0.922f));

        //Note
        JPanel note=new JPanel();
        note.setLayout(layout);
        JLabel _note=new JLabel("Note: ");
        JTextField _textNote=new JTextField("",15);
        note.add(_note);
        note.add(_textNote);
        layout.putConstraint(SpringLayout.WEST,_note,5,SpringLayout.WEST,note);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER,_note,0,SpringLayout.VERTICAL_CENTER,note);
        layout.putConstraint(SpringLayout.WEST,_textNote,5,SpringLayout.EAST,_note);
        layout.putConstraint(SpringLayout.NORTH,_textNote,5,SpringLayout.NORTH,note);
        layout.putConstraint(SpringLayout.SOUTH,note,5,SpringLayout.SOUTH,_textNote);
        layout.putConstraint(SpringLayout.EAST,note,5,SpringLayout.EAST,_textNote);
        note.setBackground(Color.getHSBColor(0.5f,0.591f,0.922f));

        //2button
        JPanel buttons=new JPanel();
        JButton ok=new JButton("OK");
        JButton cancel=new JButton("CANCEL");
        buttons.add(ok);
        buttons.add(cancel);
        buttons.setBackground(Color.getHSBColor(0.5f,0.591f,0.922f));
        ActionListener x= e -> {
            String s=e.getActionCommand();
            if(s.equals("OK")){
                if(ListStudents.checkExis(con,_textId.getText()) ){
                    JOptionPane.showMessageDialog(f3,"ID existed!","",JOptionPane.ERROR_MESSAGE);
                }
                else
                    if(_textName.getText().equals("")|| _textId.getText().equals(""))
                    JOptionPane.showMessageDialog(f3,"ID or Name can't be blank!","",JOptionPane.ERROR_MESSAGE);
                    else
                        if(!Student.checkMark(_textMark.getText()))
                            JOptionPane.showMessageDialog(f3,"Mark invalid!","",JOptionPane.ERROR_MESSAGE);
                        else
                        {
                            ListStudents.add(con,
                                    _textId.getText(),
                                    _textName.getText(),
                                    _textMark.getText(),
                                    _textImage.getText(),
                                    _textAddress.getText(),
                                    _textNote.getText());
                            JOptionPane.showMessageDialog(f3,"Successfull");
                            w=f3.getBounds().width;
                            h=f3.getBounds().height;
                            f3.getContentPane().removeAll();
                            createAndShowMenu();
                        }
            }
            if(s.equals("CANCEL")){
                w=f3.getBounds().width;
                h=f3.getBounds().height;
                f3.getContentPane().removeAll();
                f3.dispose();
                createAndShowMenu();
            }
        };
        ok.addActionListener(x);
        cancel.addActionListener(x);
        f3.add(pro);
        f3.add(id);
        f3.add(name);
        f3.add(mark);
        f3.add(image);
        f3.add(address);
        f3.add(note);
        f3.add(buttons);
        f3.pack();
        f3.setVisible(true);
    }

    public static JPanel createForm(String x){
        Student t=new Student();
        try{
            st=con.createStatement();
            ResultSet rs=st.executeQuery("select * from STUDENTS where id='"+x+"'");
            while(rs.next()){
                t.setid(rs.getString(1));
                t.setName(rs.getString(2));
                t.setMark((double) Math.round(rs.getFloat(3)*10)/10);
                t.setImage(rs.getString(4));
                t.setAddress(rs.getString(5));
                t.setNote(rs.getString(6));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        JPanel m=new JPanel();
        //m.setBackground(Color.getHSBColor(0.5f,0.591f,0.922f));
        m.setLayout(new GridLayout(6,0));
        //Profile
        JPanel pro=new JPanel();
        JLabel label=new JLabel("PROFILE");
        label.setFont(new Font("",Font.BOLD,20));
        pro.add(label);
        pro.setBackground(Color.getHSBColor(0.5f,0.591f,0.922f));
        //name
        JPanel name=new JPanel();
        name.setLayout(layout);
        JLabel _name=new JLabel("Name: ");
        JTextField _textName=new JTextField(t.getName(),40);
        _textName.setPreferredSize(new Dimension(100,30));
        name.add(_name);
        name.add(_textName);
        layout.putConstraint(SpringLayout.WEST,_name,5,SpringLayout.WEST,name);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER,_name,0,SpringLayout.VERTICAL_CENTER,name);
        layout.putConstraint(SpringLayout.WEST,_textName,5,SpringLayout.EAST,_name);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER,_textName,0,SpringLayout.VERTICAL_CENTER,name);
        name.setBackground(Color.getHSBColor(0.5f,0.591f,0.922f));

        //Mark
        JPanel mark=new JPanel();
        mark.setLayout(layout);
        JLabel _mark=new JLabel("Mark: ");
        JTextField _textMark=new JTextField(String.valueOf(t.getMark()),5);
        mark.add(_mark);
        mark.add(_textMark);
        layout.putConstraint(SpringLayout.WEST,_mark,5,SpringLayout.WEST,mark);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER,_mark,0,SpringLayout.VERTICAL_CENTER,mark);
        layout.putConstraint(SpringLayout.WEST,_textMark,5,SpringLayout.EAST,_mark);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER,_textMark,0,SpringLayout.VERTICAL_CENTER,mark);
        mark.setBackground(Color.getHSBColor(0.5f,0.591f,0.922f));

        //Image
        JPanel image=new JPanel();
        image.setLayout(layout);
        JLabel _image=new JLabel("Image: ");
        JTextField _textImage=new JTextField(t.getImage(),15);
        _textMark.add(_image);
        _textMark.add(_textImage);
        layout.putConstraint(SpringLayout.WEST,_image,5,SpringLayout.WEST,_textImage);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER,_image,0,SpringLayout.VERTICAL_CENTER,image);
        layout.putConstraint(SpringLayout.WEST,_textImage,5,SpringLayout.EAST,_image);
        layout.putConstraint(SpringLayout.NORTH,_textImage,5,SpringLayout.NORTH,image);
        layout.putConstraint(SpringLayout.SOUTH,image,5,SpringLayout.SOUTH,_textImage);
        layout.putConstraint(SpringLayout.EAST,image,5,SpringLayout.EAST,_textImage);
        image.setBackground(Color.getHSBColor(0.5f,0.591f,0.922f));

        //Address
        JPanel address=new JPanel();
        address.setLayout(layout);
        JLabel _address=new JLabel("Address: ");
        JTextField _textAddress=new JTextField(t.getAddress(),15);
        address.add(_address);
        address.add(_textAddress);
        layout.putConstraint(SpringLayout.WEST,_address,5,SpringLayout.WEST,address);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER,_address,0,SpringLayout.VERTICAL_CENTER,address);
        layout.putConstraint(SpringLayout.WEST,_textAddress,5,SpringLayout.EAST,_address);
        layout.putConstraint(SpringLayout.NORTH,_textAddress,5,SpringLayout.NORTH,address);
        layout.putConstraint(SpringLayout.SOUTH,address,5,SpringLayout.SOUTH,_textAddress);
        layout.putConstraint(SpringLayout.EAST,address,5,SpringLayout.EAST,_textAddress);
        address.setBackground(Color.getHSBColor(0.5f,0.591f,0.922f));

        //Note
        JPanel note=new JPanel();
        note.setLayout(layout);
        JLabel _note=new JLabel("Note: ");
        JTextField _textNote=new JTextField(t.getNote(),15);
        note.add(_note);
        note.add(_textNote);
        layout.putConstraint(SpringLayout.WEST,_note,5,SpringLayout.WEST,note);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER,_note,0,SpringLayout.VERTICAL_CENTER,note);
        layout.putConstraint(SpringLayout.WEST,_textNote,5,SpringLayout.EAST,_note);
        layout.putConstraint(SpringLayout.NORTH,_textNote,5,SpringLayout.NORTH,note);
        layout.putConstraint(SpringLayout.SOUTH,note,5,SpringLayout.SOUTH,_textNote);
        layout.putConstraint(SpringLayout.EAST,note,5,SpringLayout.EAST,_textNote);
        note.setBackground(Color.getHSBColor(0.5f,0.591f,0.922f));

        //2button
        JPanel buttons=new JPanel();
        JButton ok=new JButton("OK");
        buttons.add(ok);
        buttons.setBackground(Color.getHSBColor(0.5f,0.591f,0.922f));
        ActionListener z= e -> {
            String s=e.getActionCommand();
            if(s.equals("OK")){
                if(_textName.getText().equals(""))
                    JOptionPane.showMessageDialog(f4,"Name can't be blank!","",JOptionPane.ERROR_MESSAGE);
                else
                if(!Student.checkMark(_textMark.getText()))
                    JOptionPane.showMessageDialog(f4,"Mark invalid!","",JOptionPane.ERROR_MESSAGE);
                else
                {
                    try{
                        st=con.createStatement();
                        String strsql="update STUDENTS set name=N'"+_textName.getText()
                                +"', mark="+_textMark.getText()
                                +",image=N'"+_textImage.getText()
                                +"', address=N'"+_textAddress.getText()
                                +"',note=N'"+_textNote.getText()+"' "
                                +"where id='"+x+"'";
                        st.executeUpdate(strsql);
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(f4,"Successfull");
                    w=f4.getBounds().width;
                    h=f4.getBounds().height;
                    f4.dispose();
                    f4.getContentPane().removeAll();
                    createAndShowMenu();
                }
            }
        };
        ok.addActionListener(z);
        m.add(name);
        m.add(mark);
        m.add(address);
        m.add(note);
        m.add(buttons);
        JPanel p=new JPanel();
        p.setBackground(Color.getHSBColor(0.5f,0.591f,0.922f));
        m.add(p);
        return m;
    }
    static JFrame f4=new JFrame("Update Student");
    public static void createAndShowUpdateStudent(){

        f4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f4.setMinimumSize(new Dimension(w,h));
        f4.setLayout(new BorderLayout());
        f4.setBackground(Color.getHSBColor(0.5f,0.591f,0.922f));
        JPanel panel=new JPanel();
        JTextField textField=new JTextField("",15);
        JButton t=new JButton("Find");
        panel.setFont(new Font("",Font.BOLD, 30));
        panel.add(new JLabel("ID: "));
        panel.add(textField);
        panel.add(t);
        JButton cancel=new JButton("Return");
        panel.add(cancel);
        panel.setBackground(Color.getHSBColor(0.5f,0.591f,0.922f));
        f4.add(panel,BorderLayout.PAGE_START);
        AtomicReference<JPanel> panel1= new AtomicReference<>(new JPanel());
        panel1.get().setBackground(Color.getHSBColor(0.5f,0.591f,0.922f));
        f4.add(panel1.get(),BorderLayout.CENTER);
        ActionListener x= e -> {
            String s=e.getActionCommand();
            panel1.set(createForm(textField.getText()));
            if(s.equals("Find")){
                if(ListStudents.checkExis(con,textField.getText())) {
                    f4.getContentPane().removeAll();
                    f4.add(panel,BorderLayout.PAGE_START);
                    f4.remove(panel1.get());
                    f4.add(panel1.get(), BorderLayout.CENTER);
                    f4.pack();
                    f4.setVisible(true);

                }
                else
                    if(textField.getText().equals(""))
                        JOptionPane.showMessageDialog(f4,"ID can't be blank!","",JOptionPane.ERROR_MESSAGE);
                    else{
                        JOptionPane.showMessageDialog(f4,"ID not existed!","",JOptionPane.ERROR_MESSAGE);
                    }
            }
            if(s.equals("Return")){
                w=f4.getBounds().width;
                h=f4.getBounds().height;
                panel1.get().removeAll();
                f4.getContentPane().removeAll();
                f4.dispose();
                createAndShowMenu();
            }
        };
        t.addActionListener(x);
        cancel.addActionListener(x);
        f4.pack();
        f4.setVisible(true);
    }
    public static JPanel createFormForDelete(String x){
        Student t=new Student();
        try{
            st=con.createStatement();
            ResultSet rs=st.executeQuery("select * from STUDENTS where id='"+x+"'");
            while(rs.next()){
                t.setid(rs.getString(1));
                t.setName(rs.getString(2));
                t.setMark((double) Math.round(rs.getFloat(3)*10)/10);
                t.setImage(rs.getString(4));
                t.setAddress(rs.getString(5));
                t.setNote(rs.getString(6));
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        JPanel m=new JPanel();
        m.setLayout(new BoxLayout(m,BoxLayout.Y_AXIS));
        m.setBackground(Color.getHSBColor(0.5f,0.591f,0.922f));
        JLabel label=new JLabel("ID: "+t.getid());
        label.setFont(new Font("",Font.ITALIC,15));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel label1=new JLabel("Name: "+t.getName());
        label1.setFont(new Font("",Font.ITALIC,15));
        label1.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel label2=new JLabel("Mark: "+t.getMark());
        label2.setFont(new Font("",Font.ITALIC,15));
        label2.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel label3=new JLabel("Image: "+t.getMark());
        label3.setFont(new Font("",Font.ITALIC,15));
        label3.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel label4=new JLabel("Address: "+ t.getAddress());
        label4.setFont(new Font("",Font.ITALIC,15));
        label4.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel label5=new JLabel("Note:"+t.getNote());
        label5.setFont(new Font("",Font.ITALIC,15));
        label5.setAlignmentX(Component.CENTER_ALIGNMENT);
        m.add(label);
        m.add(label1);
        m.add(label2);
        m.add(label3);
        m.add(label4);
        m.add(label5);
        m.add(Box.createRigidArea(new Dimension(0,20)));
        JButton button=new JButton("Delete");
        m.add(button);
        ActionListener z= e -> {
            String s=e.getActionCommand();
            if(s.equals("Delete")){
                int result = JOptionPane.showConfirmDialog(f5,
                        "Do you wanna delete this student?",
                        "",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if(result==JOptionPane.YES_OPTION){
                    try{
                        st=con.createStatement();
                        st.executeUpdate("delete from STUDENTS where id='"+x+"'");
                        JOptionPane.showMessageDialog(f5,"Delete Successfull","",JOptionPane.INFORMATION_MESSAGE);
                        w=f5.getBounds().width;
                        h=f5.getBounds().height;
                        f5.getContentPane().removeAll();
                        f5.dispose();
                        createAndShowMenu();
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }

                }
            }
        };
        button.addActionListener(z);
        return m;
    }
    static JFrame f5=new JFrame("Delete Student");
    public static void createAndShowDeleteStudent(){
        middle.removeAll();
        middle.repaint();
        f5.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f5.setMinimumSize(new Dimension(w,h));
        f5.setLayout(new BorderLayout());
        JPanel panel=new JPanel();
        JTextField textField=new JTextField("",15);
        JButton t=new JButton("Find");
        panel.setFont(new Font("",Font.BOLD, 30));
        panel.add(new JLabel("ID: "));
        panel.add(textField);
        panel.add(t);
        JButton cancel=new JButton("Return");
        panel.add(cancel);
        panel.setBackground(Color.getHSBColor(0.5f,0.591f,0.922f));
       JPanel panel1= new JPanel();
        panel1.setBackground(Color.getHSBColor(0.5f,0.591f,0.922f));
        f5.add(panel,BorderLayout.PAGE_START);
        f5.add(panel1,BorderLayout.CENTER);
        ActionListener x= e -> {
            String s=e.getActionCommand();
            if(s.equals("Find")){
                if(ListStudents.checkExis(con,textField.getText())) {
                    JPanel p=createFormForDelete(textField.getText());
                    f5.getContentPane().removeAll();
                    f5.add(panel,BorderLayout.PAGE_START);
                    f5.add(p,BorderLayout.CENTER);
                    f5.setVisible(true);
                }
                else
                if(textField.getText().equals(""))
                    JOptionPane.showMessageDialog(f5,"ID can't be blank!","",JOptionPane.ERROR_MESSAGE);
                else{
                    JOptionPane.showMessageDialog(f5,"ID not existed!","",JOptionPane.ERROR_MESSAGE);
                }
            }
            if(s.equals("Return")){
                w=f5.getBounds().width;
                h=f5.getBounds().height;
                f5.getContentPane().removeAll();
                f5.dispose();
                createAndShowMenu();
            }
        };
        t.addActionListener(x);
        cancel.addActionListener(x);
        f5.pack();
        f5.setVisible(true);
    }

    static JFrame f6=new JFrame("Login");
    public static void createAndShowLogin(){
        f6.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f6.setMinimumSize(new Dimension(w,h));

        JPanel panel=new JPanel();
        panel.setLayout(new GridLayout(4,0));


        JLabel _user=new JLabel("User(guest): ");
        JTextField _textUser=new JTextField("",15);
        JPanel user=new JPanel();
        user.add(_user);
        user.add(_textUser);
        user.setBackground(Color.getHSBColor(0.5f,0.591f,0.922f));
        panel.add(user);

        JLabel _pass=new JLabel("Password(123456789): ");
        JPasswordField _textPass=new JPasswordField("",15);
        JPanel pass=new JPanel();
        pass.setBackground(Color.getHSBColor(0.5f,0.591f,0.922f));
        pass.add(_pass);
        pass.add(_textPass);
        panel.add(pass);

        JLabel _port=new JLabel("Port(1433): ");
        JTextField _textPort=new JTextField("",10);
        JPanel port=new JPanel();
        port.setBackground(Color.getHSBColor(0.5f,0.591f,0.922f));
        port.add(_port);
        port.add(_textPort);
        panel.add(port);

        JPanel log=new JPanel();
        JButton login=new JButton("Login");
        log.setBackground(Color.getHSBColor(0.5f,0.591f,0.922f));
        log.add(login);
        panel.add(log);


        f6.setLayout(new GridLayout(3,0));
        JPanel panel1=new JPanel();
        panel1.setLayout(layout);

        JLabel label=new JLabel("LOGIN");
        label.setFont(new Font("",Font.BOLD,30));
        panel1.add(label);
        panel1.setBackground(Color.getHSBColor(0.5f,0.591f,0.922f));
        layout.putConstraint(SpringLayout.VERTICAL_CENTER,label,0,SpringLayout.VERTICAL_CENTER,panel1);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER,label,0,SpringLayout.HORIZONTAL_CENTER,panel1);

        f6.add(panel1);
        f6.add(panel);
        JPanel k=new JPanel();
        k.setBackground(Color.getHSBColor(0.5f,0.591f,0.922f));
        f6.add(k);

        ActionListener x=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s=e.getActionCommand();
                if(s.equals("Login")){
                    try {
                        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                        String url="jdbc:sqlserver://localhost:"+_textPort.getText()
                                +";databaseName=Quan_li_sinh_vien;user="+_textUser.getText()
                                +";password="+new String(_textPass.getPassword())+";";

                        if(_textPort.getText().equals("1433")&&_textUser.getText().equals("guest")&&new String(_textPass.getPassword()).equals("123456789")) {
                            con = DriverManager.getConnection(url);
                            if (con != null) {
                                st = con.createStatement();
                                JOptionPane.showMessageDialog(f6, "Login Successfull", "", JOptionPane.INFORMATION_MESSAGE);
                                w = f6.getBounds().width;
                                h = f6.getBounds().height;
                                f6.getContentPane().removeAll();
                                f6.dispose();
                                createAndShowMenu();
                                return;
                            }
                        }
                            JOptionPane.showMessageDialog(f6,"Somthing wrong!","",JOptionPane.ERROR_MESSAGE);
                    }
                    catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        };
        login.addActionListener(x);
        f6.pack();
        f6.setVisible(true);
    }
    public static void main(String[] args) {
        try{

        }
        catch (Exception e){
            e.printStackTrace();
        }
        //createData(); // Create Data
        //createAndShowMenu();
        createAndShowLogin();
    }
}