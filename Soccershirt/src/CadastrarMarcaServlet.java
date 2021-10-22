import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.stream.Collectors;

public class CadastrarMarcaServlet extends HttpServlet {
  //@Consumes(MediaType.APPLICATION_JSON)
      public  void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.setContentType("text/html");
            PrintWriter pw=response.getWriter();//get the stream to write the data
            String requestData = request.getReader().lines().collect(Collectors.joining());
            Json js= new Json();
            Marca marca= js.retornaMarca(requestData);
            pw.println(""+marca.getNome());
            pw.close();//closing the stream
            MarcaDAO bd= new MarcaDAO();
            if(marca.getId()==0){
              bd.adiciona(marca);
            }
            else{
              bd.editaMarca(marca.getId(), marca);
            }
          //PrintWriter
      }
}
