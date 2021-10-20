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
    String[] words= {"titulo","subtitulo","conteudo"};
    String[] ans= new String[3];
    int cont=0;
    for(String x: words){
      int idx= requestData.indexOf(x);
      int end= requestData.indexOf("&");
      StringBuilder sb= new StringBuilder(requestData);
      sb.setCharAt(end, '*');
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
    String titulo = ans[0];
    String subtitulo = ans[1];
    String conteudo = ans[2];
    Noticia noticia= new Noticia(titulo, subtitulo, conteudo);
    return noticia;
  }
}
