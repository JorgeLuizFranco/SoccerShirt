import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

public class SelecionarAllMarcasServlet extends HttpServlet {
  //@Consumes(MediaType.APPLICATION_JSON)
        public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.setContentType("text/html");
            PrintWriter pw=response.getWriter(); //get the stream to write the data
            MarcaDAO bd= new MarcaDAO();
            List<Marca> marcas= bd.getMarcas();
            Json js= new Json();
            pw.print("[");
            int cont=0;
            for(Marca marca: marcas){
              pw.print(js.enviaMarca(marca));
              if(cont==marcas.size()-1) continue;
              pw.print(",");
              ++cont;
            }
            pw.print("]");
            pw.close();
      }
}
