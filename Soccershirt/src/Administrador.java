import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
public class Administrador{

	// atributos
      private Long id;
      private String username;
      private String senha;

    //construtor
      public Administrador(Long id, String username, String senha){
        this.id=id;
        this.username=username;
        this.senha=senha;
      }

    // métodos get e set
    public String getUsername() {
      return this.username;
    }

    public void setUsername(String novo) {
      this.username = novo;
    }

    public String getSenha() {
      return this.senha;
    }

    public void setSenha(String novo) {
      this.senha = novo;
    }

}
