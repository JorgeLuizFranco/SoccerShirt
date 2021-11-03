import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.stream.Collectors;

public class VerificaADMServlet extends HttpServlet {
  //@Consumes(MediaType.APPLICATION_JSON)
      public  void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw=response.getWriter();//get the stream to write the data
        String requestData = request.getReader().lines().collect(Collectors.joining());
        Json js= new Json();
        Liga adm= js.retornaLiga(requestData);
        String username=adm.getNome();
        String senha=adm.getPaisOrigem();
        AdministradorDAO bd= new AdministradorDAO();
        if(bd.existe(username, senha)){
          pw.print(true);
        }
        else{
          pw.print(false);
        }
        pw.close();//closing the stream
      }
  }
