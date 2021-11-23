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
public class TimeDAO {
    public static void adiciona(Time time) {
        String sql = "insert into time (nome, camiseta) values (?, ?)";
        try(Connection conn = ConnectionFactory.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,time.getNome());
            ps.setString(2, time.getCamiseta());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Time> getTimes() {
        String sql = "select * from time";
        try(Connection conn = ConnectionFactory.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Time> times = new ArrayList<Time>();
            while(rs.next()) {
              Time time = new Time();
              time.setId(rs.getInt("idUnica"));
              time.setCamiseta(rs.getString("camiseta"));
              time.setNome(rs.getString("nome"));
              times.add(time);
            }
            return times;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Time getTime(int id) {
        String sql = "select * from time where idUnica=?";
        try(Connection conn = ConnectionFactory.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            Time time = null;
            if(rs.next()) {
              time = new Time();
              time.setId(rs.getInt("idUnica"));
              time.setCamiseta(rs.getString("camiseta"));
              time.setNome(rs.getString("nome"));
            }
            return time;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void removerTime(int id) {
        String sql = "delete from time where idUnica=?";
        try(Connection conn = ConnectionFactory.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void editaTime(int id, Time time){
      String sql="update time set nome=?  where idUnica=?";
      try(Connection conn = ConnectionFactory.getConnection()) {
          PreparedStatement ps = conn.prepareStatement(sql);
          ps.setString(1, time.getNome());
          ps.setInt(2, id);
          ps.executeUpdate();
      } catch (SQLException e) {
          throw new RuntimeException(e);
      }
    }

    public ArrayList<Integer> filtraNoticias(int idTime){
      String sql = "select idNoticia from timeNoticia where idTime=?";
      ArrayList<Integer> ids= new ArrayList<Integer>();
      try(Connection conn = ConnectionFactory.getConnection()) {
          PreparedStatement ps = conn.prepareStatement(sql);
          ps.setInt(1, idTime);
          ResultSet rs = ps.executeQuery();
          while(rs.next()){
            ids.add(rs.getInt("idNoticia"));
          }
      } catch (SQLException e) {
          throw new RuntimeException(e);
      }
      return ids;
    }
}
