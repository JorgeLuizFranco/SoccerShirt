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
}
