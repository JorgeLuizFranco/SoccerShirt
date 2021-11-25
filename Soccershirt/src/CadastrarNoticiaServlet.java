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
            /*pw.print(requestData);
            pw.close();*/
            Json js= new Json();
            Noticia noticia= js.retornaNoticia(requestData);
            pw.print(""+noticia.getTitulo()+" "+noticia.getSubtitulo()+" "+noticia.getTexto()+" ");
            pw.print("times ");
            for(int i: noticia.getTimes()){
              pw.print(i+" ");
            }
            pw.print("marcas ");
            for(int i: noticia.getMarcas()){
                pw.print(i+" ");
            }
            pw.print("ligas ");
            for(int i: noticia.getLigas()){
                pw.print(i+" ");
            }
            pw.print("imagens ");
            for(String i: noticia.getImagens()){
                pw.print(i+" ");
            }
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
