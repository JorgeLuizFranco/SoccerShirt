import java.util.ArrayList;
import java.util.Arrays;
public class Json{
  char aspas = '"';
  String strJson;

  public String enviaNoticia(Noticia noticia){
    strJson = "{" + aspas + "id" + aspas + ":" + aspas + Integer.toString(noticia.getId()) + aspas + ", " + aspas + "titulo" + aspas + " : " + aspas + noticia.getTitulo() + aspas + ", " + aspas + "subtitulo" +aspas+" : " + aspas + noticia.getSubtitulo() + aspas + ", " + aspas + "texto" + aspas + " : " + aspas + noticia.getTexto() + aspas + ", " + aspas + "hora" + aspas + ":" + aspas + noticia.getHora().toString() + aspas + "," + aspas + "date" + aspas + ":" + aspas + noticia.getDate().toString() + aspas + "}";
    return strJson;
  }

  public String enviaTime(Time objeto){
    strJson = "{" + aspas + "id" + aspas + ":" + aspas + Integer.toString(objeto.getId()) + aspas + ", " + aspas + "nome" + aspas + " : " + aspas + objeto.getNome() + aspas + "}";
    return strJson;
  }

  public String enviaMarca(Marca objeto){
    strJson = "{" + aspas + "id" + aspas + ":" + aspas + Integer.toString(objeto.getId()) + aspas + ", " + aspas + "nome" + aspas + " : " + aspas + objeto.getNome() + aspas + "}";
    return strJson;
  }

  public String enviaLiga(Liga objeto){
    strJson = "{" + aspas + "id" + aspas + ":" + aspas + Integer.toString(objeto.getId()) + aspas + ", " + aspas + "nome" + aspas + " : " + aspas + objeto.getNome() + aspas + "}";
    return strJson;
  }

  public Noticia retornaNoticia(String requestData){
	  String[] words= {"id","titulo","subtitulo","conteudo","times","marcas","ligas","imagens"};
    String[] ans= new String[8];
    ArrayList<String> times= new ArrayList<String>(), marcas=new ArrayList<String>(),ligas=new ArrayList<String>(), imagens= new ArrayList<String>;
    int cont=0;
    for(String x: words){
      int idx= requestData.indexOf(x);
      int end= requestData.indexOf("&");
      StringBuilder sb= new StringBuilder(requestData);
      if(end!=-1){
    	   sb.setCharAt(end, '*');
      }
      else {
        end=requestData.length();
      }
      requestData= sb.toString();
      char[] ch = new char[requestData.length()];
      for (int i = 0; i < requestData.length(); i++) {
        ch[i] = requestData.charAt(i);
      }
      StringBuilder aux= new StringBuilder();
      while(ch[idx]!='=') ++idx;
      for(int i=idx+1; i<end; i++){
        aux.append(ch[i]);
      }
      switch(x){
        case "times": times=new ArrayList( Arrays.asList(aux.toString().split("KQVSUQ")) );
        case "marcas": marcas=new ArrayList( Arrays.asList(aux.toString().split("KQVSUQ")) );
        case "ligas": ligas=new ArrayList( Arrays.asList(aux.toString().split("KQVSUQ")) );
        case "imagens": imagens= new ArrayList( Arrays.asList(aux.toString().split("gabriella2503jorge2505mauricio1106")));
      }
      ans[cont]=aux.toString();
      ++cont;
    }
    int id= Integer.valueOf(ans[0]);
    String titulo = ans[1];
    String subtitulo = ans[2];
    String conteudo = ans[3];
    Noticia noticia= new Noticia(titulo, subtitulo, conteudo);
    noticia.setId(id);
    noticia.setTimes(times);
    noticia.setLigas(ligas);
    noticia.setMarcas(marcas);
    noticia.setImagens(imagens);
    return noticia;
  }

public Time retornaTime(String requestData){
  String[] words= {"id","nome"};
  String[] ans= new String[2];
  int cont=0;
  for(String x: words){
    int idx= requestData.indexOf(x);
    int end= requestData.indexOf("&");
    StringBuilder sb= new StringBuilder(requestData);
    if(end!=-1){
       sb.setCharAt(end, '*');
    }
    else {
      end=requestData.length();
    }
    requestData= sb.toString();
    char[] ch = new char[requestData.length()];
    for (int i = 0; i < requestData.length(); i++) {
      ch[i] = requestData.charAt(i);
    }
    String aux= new String();
    while(ch[idx]!='=') ++idx;
    for(int i=idx+1; i<end; i++){
      aux+=ch[i];
    }
    ans[cont]=aux.toString();
    ++cont;
  }
  int id= Integer.valueOf(ans[0]);
  String nome = ans[1];
  Time time= new Time();
  time.setNome(nome);
  time.setId(id);
  return time;
  }


public Marca retornaMarca(String requestData){
  String[] words= {"id","nome"};
  String[] ans= new String[2];
  int cont=0;
  for(String x: words){
    int idx= requestData.indexOf(x);
    int end= requestData.indexOf("&");
    StringBuilder sb= new StringBuilder(requestData);
    if(end!=-1){
       sb.setCharAt(end, '*');
    }
    else {
      end=requestData.length();
    }
    requestData= sb.toString();
    char[] ch = new char[requestData.length()];
    for (int i = 0; i < requestData.length(); i++) {
      ch[i] = requestData.charAt(i);
    }
    String aux= new String();
    while(ch[idx]!='=') ++idx;
    for(int i=idx+1; i<end; i++){
      aux+=ch[i];
    }
    ans[cont]=aux.toString();
    ++cont;
  }
  int id= Integer.valueOf(ans[0]);
  String nome = ans[1];
  Marca marca= new Marca();
  marca.setNome(nome);
  marca.setId(id);
  return marca;
  }


public Liga retornaLiga(String requestData){
  String[] words= {"id","nome","paisOrigem"};
  String[] ans= new String[3];
  int cont=0;
  for(String x: words){
    int idx= requestData.indexOf(x);
    int end= requestData.indexOf("&");
    StringBuilder sb= new StringBuilder(requestData);
    if(end!=-1){
       sb.setCharAt(end, '*');
    }
    else {
      end=requestData.length();
    }
    requestData= sb.toString();
    char[] ch = new char[requestData.length()];
    for (int i = 0; i < requestData.length(); i++) {
      ch[i] = requestData.charAt(i);
    }
    String aux= new String();
    while(ch[idx]!='=') ++idx;
    for(int i=idx+1; i<end; i++){
      aux+=ch[i];
    }
    ans[cont]=aux.toString();
    ++cont;
  }
  int id= Integer.valueOf(ans[0]);
  String nome = ans[1];
  String pais= ans[2];
  Liga liga= new Liga();
  liga.setNome(nome);
  liga.setPaisOrigem(pais);
  liga.setId(id);
  return liga;
}
}
