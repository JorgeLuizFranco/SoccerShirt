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
public class ImagensDAO {
    public static List<String> getImagens(int idNoticia) {
        String sql = "select imagem from imagens where idNoticia=?";
        try(Connection conn = ConnectionFactory.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            ps.setInt(1,idNoticia);
            List<String> imagens = new ArrayList<String>();
            while(rs.next()) {
              imagens.add(rs.getString("imagem"));
            }
            return imagens;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
