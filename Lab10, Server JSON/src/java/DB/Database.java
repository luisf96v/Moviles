/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


public final class Database {

   
    public Database() throws SQLException, ClassNotFoundException{
        Class.forName("com.mysql.jdbc.Driver");
        this.cnx = DriverManager.getConnection(BASEDATOS, USUARIO, CLAVE);
    }

    public boolean setConnectionAutoCommit(boolean autoCommit) {
        try {
            cnx.setAutoCommit(autoCommit);
        } catch (SQLException ex) {
            return false;
        }
        return true;
    }

    public Connection getConnection(String servidorArg, String usuarioArg, String claveArg) {
            return cnx;
    }

    public void executeUpdate(String statement) throws SQLException {
        cnx.createStatement().executeUpdate(statement);
    }

    public ResultSet executeUpdateWithKeys(String statement) {
        try {
            Statement stm = cnx.createStatement();
            stm.executeUpdate(statement, Statement.RETURN_GENERATED_KEYS);
            return stm.getGeneratedKeys();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public ResultSet executeQuery(String statement) throws SQLException{
        return cnx.createStatement().executeQuery(statement);
    }
    
    public PreparedStatement prepareStatement(String query) throws SQLException {
        return cnx.prepareStatement(query);
    }
    
    public void close() throws SQLException{
        cnx.close();
    }

    private static final String USUARIO = "root";
    private static final String CLAVE = "root";
    private static final String BASEDATOS = "jdbc:mysql://localhost:3306/moviles";
    private final Connection cnx;
}
