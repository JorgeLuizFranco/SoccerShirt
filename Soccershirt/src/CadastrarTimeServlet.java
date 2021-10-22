import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.stream.Collectors;

public class CadastrarTimeServlet extends HttpServlet {
  //@Consumes(MediaType.APPLICATION_JSON)
      public  void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw=response.getWriter();//get the stream to write the data
        String requestData = request.getReader().lines().collect(Collectors.joining());
        Json js= new Json();
        Time time= js.retornaTime(requestData);
        pw.println(""+time.getNome());
        pw.close();//closing the stream
        TimeDAO bd= new TimeDAO();
        if(time.getId()==0){
          bd.adiciona(time);
        }
        else{
          bd.editaTime(time.getId(), time);
        }
      }
}
