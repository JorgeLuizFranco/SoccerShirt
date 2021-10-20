import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

public class SelecionarAllLigasServlet extends HttpServlet {
  //@Consumes(MediaType.APPLICATION_JSON)
        public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.setContentType("text/html");
            PrintWriter pw=response.getWriter(); //get the stream to write the data
            LigaDAO bd= new LigaDAO();
            List<Liga> ligas= bd.getLigas();
            Json js= new Json();
            pw.print("[");
            int cont=0;
            for(Liga liga: ligas){
              pw.print(js.enviaLiga(liga));
              if(cont==ligas.size()-1) continue;
              pw.print(",");
              ++cont;
            }
            pw.print("]");
            pw.close();
      }
}
