package vn.edu.hcmus.student.sv19127520;

/**
 * vn.edu.hcmus.student.sv19127520;
 * Created by Phuoc -19127520
 * Date 25/11/2021 - 08:08 CH
 * Description: ...
 */
import java.io.Serializable;
import java.util.Scanner;

/**
 * vn.edu.hcmus.student.sv19127520.Lab03;
 * Created by Phuoc -19127520
 * Date 11/3/2021 - 8:09 PM
 * Description: ...
 */
public class Student implements Serializable {
    private String id;
    private String name;
    private double mark;
    private String image;
    private String address;
    private String note;
    public Student(){
        id="";
        name="";
        mark=0.0;
        image="";
        address="";
        note="";
    }
    public Student(String id, String name, double mark, String image, String address, String note){
        this.id=id;
        this.name=name;
        this.mark=mark;
        this.image=image;
        this.address=address;
        this.note=note;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setImage(String image) {
        this.image = image;
    }

    public void setid(String id) {
        this.id = id;
    }

    public static boolean checkMark(String x){
        for(char c: x.toCharArray()){
            if(!Character.isDigit(c)&& c!='.')
                return false;
        }
        if(Float.valueOf(x)>10||Float.valueOf(x)<0)
            return false;
        return true;

    }
    public void setMark(double mark) {
        this.mark = mark;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public double getMark() {
        return mark;
    }

    public String getAddress() {
        return address;
    }

    public String getImage() {
        return image;
    }

    public String getid() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNote() {
        return note;
    }
}

