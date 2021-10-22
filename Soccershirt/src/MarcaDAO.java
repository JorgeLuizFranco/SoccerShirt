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
public class MarcaDAO {

    public static void adiciona(Marca marca) {
        String sql = "insert into marca (nome, logo) values (?, ?)";
        try(Connection conn = ConnectionFactory.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,marca.getNome());
            ps.setString(2, marca.getLogo());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Marca> getMarcas() {
        String sql = "select * from marca";
        try(Connection conn = ConnectionFactory.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Marca> marcas = new ArrayList<Marca>();
            while(rs.next()) {
              Marca marca = new Marca();
              marca.setId(rs.getInt("idUnica"));
              marca.setLogo(rs.getString("logo"));
              marca.setNome(rs.getString("nome"));
              marcas.add(marca);
            }
            return marcas;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Marca getMarca(int id) {
        String sql = "select * from marca where idUnica=?";
        try(Connection conn = ConnectionFactory.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            Marca marca = null;
            if(rs.next()) {
              marca = new Marca();
              marca.setId(rs.getInt("idUnica"));
              marca.setLogo(rs.getString("logo"));
              marca.setNome(rs.getString("nome"));
            }
            return marca;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public void removerMarca(int id) {
        String sql = "delete from marca where idUnica=?";
        try(Connection conn = ConnectionFactory.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void editaMarca(int id, Marca marca){
      String sql="update marca set nome=?  where idUnica=?";
      try(Connection conn = ConnectionFactory.getConnection()) {
          PreparedStatement ps = conn.prepareStatement(sql);
          ps.setString(1, marca.getNome());
          ps.setInt(2, id);
          ps.executeUpdate();
      } catch (SQLException e) {
          throw new RuntimeException(e);
      }
    }

}
