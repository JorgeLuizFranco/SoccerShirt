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
public class NoticiaDAO {

    public static void adiciona(Noticia noticia) {
        String sql = "insert into noticia (texto, subtitulo, titulo, dataC, horaC) values (?, ?, ?, ?, ?)";
        try(Connection conn = ConnectionFactory.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            //ps.setInt(1, noticia.getId());
            ps.setString(1,noticia.getTexto());
            ps.setString(2, noticia.getSubtitulo());
            ps.setString(3, noticia.getTitulo());
            ps.setDate(4, java.sql.Date.valueOf(noticia.getDate()));
            ps.setTime(5, java.sql.Time.valueOf(noticia.getHora()));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sql = "select idUnica from noticia where dataC=? and horaC=?";
        int idNoticia=-1;
        try(Connection conn = ConnectionFactory.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDate(1, java.sql.Date.valueOf(noticia.getDate()));
            ps.setTime(2, java.sql.Time.valueOf(noticia.getHora()));
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
              idNoticia=rs.getInt("idUnica");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String[] ents= {"time","marca","liga"};
        for(String x: ents){
          ArrayList<Integer> ids;
          if(x=="time") ids=noticia.getTimes();
          else ids= (x=="marca"? noticia.getMarcas(): noticia.getLigas());
          for(int id: ids){
            sql = "insert into "+x+"Noticia values(?,?);";
            try(Connection conn = ConnectionFactory.getConnection()) {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, idNoticia);
                ps.setInt(2, id);
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
          }
        }
        //ArrayList<String> imagens=Imagens.salvar(noticia.getImagens(),idNoticia);
        ArrayList<String> imagens= noticia.getImagens();
        for(String imagem: imagens){
          sql="insert into imagens values(?,?);";
          try(Connection conn = ConnectionFactory.getConnection()) {
              PreparedStatement ps = conn.prepareStatement(sql);
              ps.setInt(1, idNoticia);
              ps.setString(2, imagem);
              ps.executeUpdate();
          } catch (SQLException e) {
              throw new RuntimeException(e);
          }
        }
   }
    public List<Noticia> getNoticias() {
        String sql = "select * from noticia";
        try(Connection conn = ConnectionFactory.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Noticia> noticias = new ArrayList<Noticia>();
            while(rs.next()) {
              Noticia noticia = new Noticia(rs.getString("titulo"),rs.getString("subtitulo"),rs.getString("texto"));
              noticia.setId(rs.getInt("idUnica")); 
              /*noticia.setTexto(rs.getString("texto"));
              noticia.setSubtitulo(rs.getString("subtitulo"));
              noticia.setTitulo(rs.getString("titulo"));*/
              noticia.setData( new java.sql.Date(rs.getDate("dataC").getTime()).toLocalDate());
              noticia.setHora( new java.sql.Time(rs.getTime("horaC").getTime()).toLocalTime());
                noticias.add(noticia);
            }
            return noticias;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Noticia getNoticia(int id) {
        String sql = "select * from noticia where idUnica=?";
        try(Connection conn = ConnectionFactory.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            Noticia noticia = null;
            if(rs.next()) {
              noticia = new Noticia(rs.getString("titulo"),rs.getString("subtitulo"),rs.getString("texto"));
              noticia.setId(rs.getInt("idUnica"));
              /*noticia.setTexto(rs.getString("texto"));
              noticia.setSubtitulo(rs.getString("subtitulo"));
              noticia.setTitulo(rs.getString("titulo"));*/
              noticia.setData( new java.sql.Date(rs.getDate("dataC").getTime()).toLocalDate());
              noticia.setHora( new java.sql.Time(rs.getTime("horaC").getTime()).toLocalTime());
            }
            return noticia;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

// opa git 2

    public void removerNoticia(int id) {
      String sql ="delete from timeNoticia where idNoticia=?;";
      try(Connection conn = ConnectionFactory.getConnection()) {
          PreparedStatement ps = conn.prepareStatement(sql);
          ps.setInt(1, id);
          ps.executeUpdate();
      } catch (SQLException e) {
          throw new RuntimeException(e);
      }
      sql ="delete from marcaNoticia where idNoticia=?;";
      try(Connection conn = ConnectionFactory.getConnection()) {
          PreparedStatement ps = conn.prepareStatement(sql);
          ps.setInt(1, id);
          ps.executeUpdate();
      } catch (SQLException e) {
          throw new RuntimeException(e);
      }
      sql ="delete from ligaNoticia where idNoticia=?;";
      try(Connection conn = ConnectionFactory.getConnection()) {
          PreparedStatement ps = conn.prepareStatement(sql);
          ps.setInt(1, id);
          ps.executeUpdate();
      } catch (SQLException e) {
          throw new RuntimeException(e);
      }
      sql ="delete from imagens where idNoticia=?;";
      try(Connection conn = ConnectionFactory.getConnection()) {
          PreparedStatement ps = conn.prepareStatement(sql);
          ps.setInt(1, id);
          ps.executeUpdate();
      } catch (SQLException e) {
          throw new RuntimeException(e);
      }
      sql = "delete from noticia where idUnica=?";
        try(Connection conn = ConnectionFactory.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void editaNoticia(int id, Noticia noticia){
        String sql ="delete from timeNoticia where idNoticia=?;";
      try(Connection conn = ConnectionFactory.getConnection()) {
          PreparedStatement ps = conn.prepareStatement(sql);
          ps.setInt(1, id);
          ps.executeUpdate();
      } catch (SQLException e) {
          throw new RuntimeException(e);
      }
      sql ="delete from marcaNoticia where idNoticia=?;";
      try(Connection conn = ConnectionFactory.getConnection()) {
          PreparedStatement ps = conn.prepareStatement(sql);
          ps.setInt(1, id);
          ps.executeUpdate();
      } catch (SQLException e) {
          throw new RuntimeException(e);
      }
      sql ="delete from ligaNoticia where idNoticia=?;";
      try(Connection conn = ConnectionFactory.getConnection()) {
          PreparedStatement ps = conn.prepareStatement(sql);
          ps.setInt(1, id);
          ps.executeUpdate();
      } catch (SQLException e) {
          throw new RuntimeException(e);
      }
      sql ="delete from imagens where idNoticia=?;";
      try(Connection conn = ConnectionFactory.getConnection()) {
          PreparedStatement ps = conn.prepareStatement(sql);
          ps.setInt(1, id);
          ps.executeUpdate();
      } catch (SQLException e) {
          throw new RuntimeException(e);
      }
      sql = "delete from noticia where idUnica=?";
        try(Connection conn = ConnectionFactory.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String[] ents= {"time","marca","liga"};
        for(String x: ents){
          ArrayList<Integer> ids;
          if(x=="time") ids=noticia.getTimes();
          else ids= (x=="marca"? noticia.getMarcas(): noticia.getLigas());
          for(int id: ids){
            sql = "insert into "+x+"Noticia values(?,?);";
            try(Connection conn = ConnectionFactory.getConnection()) {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, idNoticia);
                ps.setInt(2, id);
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
          }
        }
        //ArrayList<String> imagens=Imagens.salvar(noticia.getImagens(),idNoticia);
        ArrayList<String> imagens= noticia.getImagens();
        for(String imagem: imagens){
          sql="insert into imagens values(?,?);";
          try(Connection conn = ConnectionFactory.getConnection()) {
              PreparedStatement ps = conn.prepareStatement(sql);
              ps.setInt(1, idNoticia);
              ps.setString(2, imagem);
              ps.executeUpdate();
          } catch (SQLException e) {
              throw new RuntimeException(e);
          }
        }

      String sql="update noticia set titulo=? , subtitulo=? , texto=? where idUnica=?";
      try(Connection conn = ConnectionFactory.getConnection()) {
          PreparedStatement ps = conn.prepareStatement(sql);
          ps.setString(1, noticia.getTitulo());
          ps.setString(2, noticia.getSubtitulo());
          ps.setString(3, noticia.getTexto());
          ps.setInt(4, id);
          ps.executeUpdate();
      } catch (SQLException e) {
          throw new RuntimeException(e);
      }
    }

}
