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

// opa git

    public void removerNoticia(int id) {
        String sql = "delete from noticia where idUnica=?";
        try(Connection conn = ConnectionFactory.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
