import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.time.LocalTime;
  public class Noticia {
      // atributos
      private int id;
      private String titulo;
      private String subtitulo;
      private String texto;
      private LocalDate data;
      private LocalTime hora;
      private ArrayList<Integer> times;
      private ArrayList<Integer> marcas;
      private ArrayList<Integer> ligas;
      //public File foto;
      //construtor
      public Noticia( String titulo, String subtitulo, String texto){
        this.titulo=titulo;
        this.subtitulo=subtitulo;
        this.texto=texto;
        this.data=java.time.LocalDate.now();
        this.hora= java.time.LocalTime.now();
        this.times= new ArrayList<Integer>();
        this.marcas= new ArrayList<Integer>();
        this.ligas= new ArrayList<Integer>();
      }
    // métodos get e set para id, nome, email, endereço e dataNascimento
    public void setId(int novo){
      this.id=novo;
    }

    public String getTitulo() {
      return this.titulo;
    }

    public void setTitulo(String novo) {
      this.titulo = novo;
    }

    public String getSubtitulo() {
      return this.subtitulo;
    }

    public void setSubtitulo(String novo) {
      this.subtitulo = novo;
    }

    public void setData(LocalDate data){
      this.data=data;
    }

    public void setHora(LocalTime hora){
      this.hora=hora;
    }
    public String getTexto() {
      return this.texto;
    }

    public void setTexto(String novo) {
      this.texto = novo;
    }

    public int getId() {
      return this.id;
    }
    public LocalTime getHora() {
      return this.hora;
    }

    public LocalDate getDate() {
      return this.data;
    }
    public void setTimes(ArrayList<String> strTimes){
      for(String x: strTimes){
        this.times.add(Integer.valueOf(x));
      }
    }
    public void setLigas(ArrayList<String> strLigas){
      for(String x: strLigas){
        this.ligas.add(Integer.valueOf(x));
      }
    }
    public void setMarcas(ArrayList<String> strMarcas){
      for(String x: strMarcas){
        this.marcas.add(Integer.valueOf(x));
      }
    }
    public ArrayList<Integer> getMarcas(){
      return this.marcas;
    }
    public ArrayList<Integer> getTimes(){
      return this.times;
    }
    public ArrayList<Integer> getLigas(){
      return this.ligas;
    }
    /*public void setData(Calendar data) {
      this.data = data;
    }*/

  }
