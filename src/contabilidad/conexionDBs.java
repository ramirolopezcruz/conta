
package contabilidad;

import java.sql.*;
import java.sql.Statement;



public class conexionDBs {
    Connection cin;
    
    
    public Connection conexion(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            cin = DriverManager.getConnection("jdbc:mysql://localhost/contab","root","");
            System.out.println("Se hizo la conecxion exitosa");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }return cin;
    }
    Statement createStatement(){
        throw new UnsupportedOperationException("No soportado");
    }   

    
  
}



