import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

public class SelecionarAllTimesServlet extends HttpServlet {
  //@Consumes(MediaType.APPLICATION_JSON)
        public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.setContentType("text/html");
            PrintWriter pw=response.getWriter(); //get the stream to write the data
            TimeDAO bd= new TimeDAO();
            List<Time> times= bd.getTimes();
            Json js= new Json();
            pw.print("[");
            int cont=0;
            for(Time time: times){
              pw.print(js.enviaTime(Time));
              if(cont==times.size()-1) continue;
              pw.print(",");
              ++cont;
            }
            pw.print("]");
            pw.close();
      }
}
