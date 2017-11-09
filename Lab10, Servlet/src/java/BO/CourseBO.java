package BO;

import DAO.CourseDAO;
import DB.Database;
import Model.Course;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luisf
 */
public class CourseBO implements CourseDAO{

    @Override
    public List<Course> getCourseList() throws SQLException, ClassNotFoundException {
        List<Course> list = new ArrayList<>();
        Database db = new Database();
        ResultSet rs = db.executeQuery("select *from course");
        while(rs.next()){
            list.add(
                    new Course(
                            rs.getInt("id"),
                            rs.getString("cName"),
                            rs.getString("descr"),
                            rs.getInt("credits")
                    )
            );
        }
        db.close();
        return list;
    }

    @Override
    public void saveCourse(Course c) throws SQLException, ClassNotFoundException {
        Database db = new Database();
        db.executeUpdate(
                new StringBuilder("insert into course (cName, descr, credits) values(")
                    .append(String.format("'%s',",c.getName()))
                    .append(String.format("'%s',",c.getDescr()))
                    .append(c.getCredits())
                    .append(")")
                    .toString()
        );
        db.close();
    }
    
}
