/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BO;

import DAO.StudentDAO;
import Model.Student;
import DB.Database;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luisf
 */
public class StudentBO implements StudentDAO{

    public StudentBO() {
        
    }

    @Override
    public List<Student> getStudentList() throws SQLException, ClassNotFoundException {
        List<Student> list = new ArrayList<>();
        Database db = new Database();
        ResultSet rs = db.executeQuery("select *from student");
        while(rs.next()){
            list.add(
                    new Student(
                            rs.getString("id"),
                            rs.getString("uName"),
                            rs.getString("lastN1"),
                            rs.getString("lastN2"),
                            rs.getInt("age")
                    )
            );
        }
        db.close();
        return list;
    }

    @Override
    public void saveStudent(Student s) throws SQLException, ClassNotFoundException {
        Database db = new Database();
        db.executeUpdate(
                new StringBuilder("insert into student values(")
                    .append(String.format("'%s',",s.getId()))
                    .append(String.format("'%s',",s.getName()))
                    .append(String.format("'%s',",s.getLastN1()))
                    .append(String.format("'%s',",s.getLastN2()))
                    .append(s.getAge())
                    .append(")")
                    .toString()
        );
        db.close();
    }
    
}
