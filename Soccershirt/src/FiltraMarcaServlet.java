import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.stream.Collectors;
import java.util.ArrayList;
public class FiltraMarcaServlet extends HttpServlet {
  //@Consumes(MediaType.APPLICATION_JSON)
      public  void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.setContentType("text/html");
            PrintWriter pw=response.getWriter();//get the stream to write the data
            //writing html in the stream=
            String requestData = request.getReader().lines().collect(Collectors.joining());
            String[] parts = requestData.split("/");
            int id=Integer.parseInt(parts[0]);
            MarcaDAO bd= new MarcaDAO();
            ArrayList<Integer> idNoticias=bd.filtraNoticias(id);
            pw.print("[");
            int cont=0;
            for(int idNoticia: idNoticias){
              pw.print(idNoticia);
              if(cont==idNoticias.size()-1) continue;
              pw.print(",");
              ++cont;
            }
            pw.print("]");
            pw.close();
      }
}
