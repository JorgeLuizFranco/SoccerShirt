import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.stream.Collectors;

public class CadastrarLigaServlet extends HttpServlet {
  //@Consumes(MediaType.APPLICATION_JSON)
      public  void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw=response.getWriter();//get the stream to write the data
        String requestData = request.getReader().lines().collect(Collectors.joining());
        Json js= new Json();
        Liga liga= js.retornaLiga(requestData);
        pw.println(""+liga.getNome()+" "+liga.getPaisOrigem());
        pw.close();//closing the stream
        LigaDAO bd= new LigaDAO();
        if(liga.getId()==0){
          bd.adiciona(liga);
        }
        else{
          bd.editaLiga(liga.getId(), liga);
        }
      }
}
