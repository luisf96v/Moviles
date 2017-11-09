import BO.CourseBO;
import BO.StudentBO;
import Model.Course;
import Model.Student;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author luisf69v@gmail.com
 */
@WebServlet(name = "rgsServlet", urlPatterns = {"/rgs"})
public final class Servlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            JSONObject jo = new JSONObject(request.getReader().lines().reduce("",String::concat));
            
            String act;
            if(Objects.nonNull(act = jo.getString("action"))){
                switch(act){
                    case "studentsList":    this.getStudentsList(response)    ;break;
                    case "courseList"  :    this.getCourseList  (response)    ;break;
                    case "saveStudent" :    this.saveStudent(response, jo.getJSONObject("data"))   ;break;
                    case "saveCourse"  :    this.saveCourse (response, jo.getJSONObject("data"))   ;break;
                    
                    default:  response.sendError(HttpServletResponse.SC_BAD_REQUEST, "The request sent by the client was syntactically incorrect.");
                }
            }
            
        }catch(JSONException | SQLException | ClassNotFoundException ex) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
        }
    }
    
    private void getStudentsList(HttpServletResponse response) 
            throws IOException, SQLException, ClassNotFoundException {
        try( PrintWriter pw = response.getWriter() )
        {
            JSONArray array = new JSONArray();
            response.setContentType("application/json");
            new StudentBO().getStudentList().stream().forEach(s -> array.put(s.toJson()));
            pw.write( ( new JSONObject().put("Students:", array)).toString() );
            pw.flush();            
        }
    }
    
    private void getCourseList(HttpServletResponse response) 
            throws IOException, SQLException, ClassNotFoundException {
        try( PrintWriter pw = response.getWriter() )
        {
            JSONArray array = new JSONArray();
            response.setContentType("application/json");
            new CourseBO().getCourseList().stream().forEach(c -> array.put(c.toJson()));
            pw.write( ( new JSONObject().put("Courses:", array)).toString() );
            pw.flush();            
        }
    }
    
    private void saveStudent(HttpServletResponse response, JSONObject data) 
            throws IOException, SQLException, ClassNotFoundException {
            try{
                PrintWriter pw = response.getWriter();
                new StudentBO().saveStudent(Student.fromJson(data));
                response.setContentType("application/json");
                pw.write("{\"msg\": \"Student created with success.\"}");
                pw.flush();                
            } catch (SQLException ex) {
                if(ex.getErrorCode() == 1062){
                    PrintWriter pw = response.getWriter();
                    pw.write("{\"msg\": \"Error: Student with id:".concat(Student.fromJson(data).getId()).concat(" already exists.\"}"));
                    pw.flush();                
                }else{
                    throw ex;
                }                
            }
    }
    
    private void saveCourse(HttpServletResponse response, JSONObject data) 
            throws IOException, SQLException, ClassNotFoundException {
            try{
                PrintWriter pw = response.getWriter();
                new CourseBO().saveCourse(Course.fromJson(data));
                response.setContentType("application/json");
                pw.write("{\"msg\": \"Course created with success.\"}");
                pw.flush();                
            } catch (SQLException ex) {
                throw ex;                              
            }
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
