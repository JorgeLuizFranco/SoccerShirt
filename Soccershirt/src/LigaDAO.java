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
public class LigaDAO {
    public static void adiciona(Liga liga) {
        String sql = "insert into liga (nome, simbolo, paisOrigem) values (?, ?, ?)";
        try(Connection conn = ConnectionFactory.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, liga.getNome());
            ps.setString(2, liga.getSimbolo());
            ps.setString(3, liga.getPaisOrigem());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Liga> getLigas() {
        String sql = "select * from liga";
        try(Connection conn = ConnectionFactory.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Liga> ligas = new ArrayList<Liga>();
            while(rs.next()) {
              Liga liga = new Liga();
              liga.setId(rs.getInt("idUnica"));
              liga.setSimbolo(rs.getString("simbolo"));
              liga.setNome(rs.getString("nome"));
              liga.setPaisOrigem(rs.getString("paisOrigem"));
              ligas.add(liga);
            }
            return ligas;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Liga getLiga(int id) {
        String sql = "select * from liga where idUnica=?";
        try(Connection conn = ConnectionFactory.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            Liga liga = null;
            if(rs.next()) {
              liga = new Liga();
              liga.setId(rs.getInt("idUnica"));
              liga.setSimbolo(rs.getString("simbolo"));
              liga.setNome(rs.getString("nome"));
              liga.setPaisOrigem(rs.getString("paisOrigem"));
            }
            return liga;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void removerLiga(int id) {
      String sql ="delete from ligaNoticia where idliga=?;";
      try(Connection conn = ConnectionFactory.getConnection()) {
          PreparedStatement ps = conn.prepareStatement(sql);
          ps.setInt(1, id);
          ps.executeUpdate();
      } catch (SQLException e) {
          throw new RuntimeException(e);
      }
      sql = "delete from liga where idUnica=?;";
      try(Connection conn = ConnectionFactory.getConnection()) {
          PreparedStatement ps = conn.prepareStatement(sql);
          ps.setInt(1, id);
          ps.executeUpdate();
      } catch (SQLException e) {
          throw new RuntimeException(e);
      }
    }

    public void editaLiga(int id, Liga liga){
      String sql="update liga set nome=? , paisOrigem=?  where idUnica=?";
      try(Connection conn = ConnectionFactory.getConnection()) {
          PreparedStatement ps = conn.prepareStatement(sql);
          ps.setString(1, liga.getNome());
          ps.setString(2, liga.getPaisOrigem());
          ps.setInt(3, id);
          ps.executeUpdate();
      } catch (SQLException e) {
          throw new RuntimeException(e);
      }
    }

    public ArrayList<Integer> filtraNoticias(int idLiga){
      String sql = "select idNoticia from ligaNoticia where idLiga=?";
      ArrayList<Integer> ids= new ArrayList<Integer>();
      try(Connection conn = ConnectionFactory.getConnection()) {
          PreparedStatement ps = conn.prepareStatement(sql);
          ps.setInt(1, idLiga);
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
