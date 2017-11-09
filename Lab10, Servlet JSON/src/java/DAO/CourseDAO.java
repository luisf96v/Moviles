/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Course;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author luisf
 */
public interface CourseDAO {
    public List<Course> getCourseList() throws SQLException,  ClassNotFoundException;
    public void saveCourse(Course s) throws SQLException, ClassNotFoundException;
 }
