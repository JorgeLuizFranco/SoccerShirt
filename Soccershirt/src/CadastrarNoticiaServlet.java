import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.stream.Collectors;

public class CadastrarNoticiaServlet extends HttpServlet {
  //@Consumes(MediaType.APPLICATION_JSON)
      public  void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.setContentType("text/html");
            PrintWriter pw=response.getWriter();//get the stream to write the data
            String requestData = request.getReader().lines().collect(Collectors.joining());
            Json js= new Json();
            Noticia noticia= js.retornaNoticia(requestData);
            pw.println(""+noticia.getTitulo()+" "+noticia.getSubtitulo()+" "+noticia.getTexto());
            pw.close();//closing the stream
            NoticiaDAO bd= new NoticiaDAO();
            if(noticia.getId()==0){
              bd.adiciona(noticia);
            }
            else{
              bd.editaNoticia(noticia.getId(),noticia);
            }
          //PrintWriter
      }
}
