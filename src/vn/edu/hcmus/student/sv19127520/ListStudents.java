package vn.edu.hcmus.student.sv19127520;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.util.Vector;
/**
 * vn.edu.hcmus.student.sv19127520;
 * Created by Phuoc -19127520
 * Date 25/11/2021 - 08:09 CH
 * Description: ...
 */


public class ListStudents implements Serializable {
    private static Student[] students;
    public ListStudents(){
        students=null;
    }
    public ListStudents(Student...st){
        Student[] t=new Student[st.length];
        for (int i=0;i<st.length;i++){
            t[i]=st[i];
        }
        students=t;
    }
    public int size(){
        return students.length;
    }
    public Student getStudent(int k){
        if(k<0 || k>=students.length)
            return null;
        else
            return students[k];
    }

    public static ListStudents Load(Connection con){
        ListStudents t=new ListStudents();
        try{
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("SELECT * FROM STUDENTS");
            Vector<Student> v=new Vector<Student >();
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
            ListStudents p=new ListStudents(q);
            t=p;
        }
        catch (Exception e){
        }
        return t;
    }
    public static boolean checkExis(Connection con,String x){
        try{
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("Select id from STUDENTS where id='"+x+"'");
            if(!rs.next()){
                return false;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }
    public static void add(Connection con,String a, String b, String c, String d, String e, String f){
        try {
            Statement st=con.createStatement();
            String strsql = "insert into STUDENTS(id, name, mark, image, address, note) values('"
                    + a + "', N'"
                    + b + "', "
                    + c + ", '"
                    + d + "', N'"
                    + e + "', N'"
                    + f + "')";
            st.executeUpdate(strsql);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void delete(Connection con){
        try {
            Scanner scan = new Scanner(System.in);
            System.out.print("      Enter ID: ");
            String a = scan.next();
            Student[] t = new Student[students.length - 1];
            Vector<Student> p = new Vector<>();
            for (int i = 0; i < students.length; i++)
                if (a.compareTo(students[i].getid()) != 0) {
                    p.add(students[i]);
                }
            if (p.size() == students.length) {
                System.out.println("        Don't have this student!");
                return;
            } else {
                for (int i = 0; i < p.size(); i++) {
                    t[i] = p.elementAt(i);
                }
                students = t;
            }
            Statement st=con.createStatement();
            st.executeUpdate("delete from STUDENTS where id='"+a+"'");
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    public void show(){
        if(students.length==0){
            System.out.println("Empty");
        }
        else{
            System.out.println("-----------------LIST STUDENTS-------------");
            for(int i=0;i<students.length;i++){
                System.out.println("id: "+students[i].getid());
                System.out.println("  Name: "+students[i].getName());
                System.out.println("    Mark: "+students[i].getMark());
                System.out.println("      Image: "+students[i].getImage());
                System.out.println("        Address: "+students[i].getAddress());
                System.out.println("          Note: "+students[i].getNote());
            }
        }
    }
    public void update(Connection con){
        try {
            Statement st=con.createStatement();
            String strsql="update STUDENTS set ";
            Scanner scan = new Scanner(System.in);
            System.out.print("    Enter ID: ");
            String a = scan.next();
            for (int i = 0; i < students.length; i++) {
                if (a.compareTo(students[i].getid()) == 0) {
                    while (true) {
                        System.out.println("        1. Name");
                        System.out.println("        2. Mark");
                        System.out.println("        3. Image");
                        System.out.println("        4. Address");
                        System.out.println("        5.Note");
                        System.out.println("        6. Return");
                        System.out.print("       Your choice: ");
                        int k = scan.nextInt();
                        switch (k) {
                            case 1: {
                                System.out.print("        Enter Name:");
                                String n = scan.next();
                                strsql+="name='"+n+"' where id='"+students[i].getid()+"'";
                                st.executeUpdate(strsql);
                                students[i].setName(n);
                                break;
                            }
                            case 2: {
                                System.out.print("        Enter Mark:");
                                double n = scan.nextDouble();
                                strsql+="mark="+n+" where id='"+students[i].getid()+"'";
                                st.executeUpdate(strsql);
                                students[i].setMark(n);
                                break;
                            }
                            case 3: {
                                System.out.print("        Enter Link Image:");
                                String n = scan.next();
                                strsql+="image='"+n+"' where id='"+students[i].getid()+"'";
                                st.executeUpdate(strsql);
                                students[i].setImage(n);
                                break;
                            }
                            case 4: {
                                System.out.print("        Enter Address:");
                                String n = scan.next();
                                strsql+="address='"+n+"' where id='"+students[i].getid()+"'";
                                st.executeUpdate(strsql);
                                students[i].setAddress(n);
                                break;
                            }
                            case 5: {
                                System.out.print("        Enter Note:");
                                String n = scan.next();
                                strsql+="note='"+n+"' where id='"+students[i].getid()+"'";
                                st.executeUpdate(strsql);
                                students[i].setNote(n);
                                break;
                            }
                            case 6:
                                return;
                            default: {
                                System.out.println("      Invalid!");
                            }
                        }
                    }
                }
            }
            System.out.println("Don't have this student!");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void sortAscID(){
        for(int i=0;i<students.length-1;i++){
            for (int j=i+1;j<students.length;j++){
                if(students[i].getid().compareTo(students[j].getid())>0){
                    Student t=students[i];
                    students[i]=students[j];
                    students[j]=t;
                }
            }
        }
    }
    public void sortDesID(){
        for(int i=0;i<students.length-1;i++){
            for (int j=i+1;j<students.length;j++){
                if(students[i].getid().compareTo(students[j].getid())<0){
                    Student t=students[i];
                    students[i]=students[j];
                    students[j]=t;
                }
            }
        }
    }
    public void sortAscMark(){
        for(int i=0;i<students.length-1;i++){
            for (int j=i+1;j<students.length;j++){
                if(students[i].getMark()>students[j].getMark()){
                    Student t=students[i];
                    students[i]=students[j];
                    students[j]=t;
                }
            }
        }
    }
    public void sortDesMark(){
        for(int i=0;i<students.length-1;i++){
            for (int j=i+1;j<students.length;j++){
                if(students[i].getMark()<students[j].getMark()){
                    Student t=students[i];
                    students[i]=students[j];
                    students[j]=t;
                }
            }
        }
    }
    public void export() {
        try {
            FileOutputStream fos = new FileOutputStream("list.txt");
            DataOutputStream d=new DataOutputStream(fos);
            d.writeBytes("Total: "+students.length+"\n");
            for (Student i: students){
                d.writeBytes("ID: "+i.getid()
                        +" ,Name: "+i.getName()
                        +" ,Mark "+i.getMark()
                        +" ,Image: "+i.getImage()
                        +" ,Address: "+i.getAddress()
                        +" ,Note: "+i.getNote()
                        +"\n");
            }
        }
        catch (Exception e){}
    }
}
