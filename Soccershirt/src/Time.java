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
public class Time{
	// atributos
      private int id;
      private String nome;
      private String camiseta;
     
    // métodos get e set
    public String getNome() {
      return this.nome;
    }
    public void setId(int id){
      this.id=id;
    }
    /*public void setIdMarca(int id){
      this.idMarca=id;
    }*/
    public void setNome(String novo) {
      this.nome = novo;
    }
    public void setCamiseta(String novo) {
      this.camiseta = novo;
    }
    public String getCamiseta() {
      return this.camiseta;
    }
    public int getId(){
      return this.id;
    }
    /*public int getIdMarca(){
      return this.id;
    }*/
}
