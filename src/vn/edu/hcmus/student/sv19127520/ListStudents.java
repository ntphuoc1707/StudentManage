package vn.edu.hcmus.student.sv19127520;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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
        System.arraycopy(st, 0, t, 0, st.length);
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
}
