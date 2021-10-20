import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

public class SelecionarAllNoticiasServlet extends HttpServlet {
  //@Consumes(MediaType.APPLICATION_JSON)
      public  void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw=response.getWriter();//get the stream to write the data
        NoticiaDAO bd= new NoticiaDAO();
        List<Noticia> noticias= bd.getNoticias();
        Json js= new Json();
        pw.print("[");
        int cont=0;
        for(Noticia noticia: noticias){
          pw.print(js.enviaNoticia(noticia));
          if(cont==noticias.size()-1) continue;
          pw.print(",");
          ++cont;
        }
        pw.print("]");
        pw.close();
      }
}
