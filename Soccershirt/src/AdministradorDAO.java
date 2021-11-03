import java.util.Calendar;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
public class AdministradorDAO {

    public boolean existe( String username, String senha) {
        String sql = "select * from administrador where username=? and senha=?";
        try(Connection conn = ConnectionFactory.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, senha);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
              return true;
            }
            else{
              return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
