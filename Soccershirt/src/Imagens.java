import java.util.ArrayList;
import java.util.Arrays;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;

public class Imagens{
  public static ArrayList<String> salvar(ArrayList<String> imagens, int idNoticia){
    ArrayList<String> nomes= new ArrayList<String>();
    int idx=0;
    for(String imagem: imagens){
      BufferedImage image = null;
      byte[] imageByte;
      imagem= imagem.replace("image%2Fjpeg%3Bbase64","");
      try {
          Base64.Decoder decoder = Base64.getMimeDecoder();
          String imageBase64 = imagem;
          imageByte = decoder.decode(imageBase64);
          ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
          image = ImageIO.read(bis);
          bis.close();
          String nome=""+idNoticia+""+(++idx)+".jpeg";
          System.out.println(nome);
          File outputfile = new File("C:/Tomcat/tomcat/webapps/Soccershirt/imgs/"+nome);
          ImageIO.write(image, "jpeg", outputfile);
          nomes.add(nome);
      } catch (Exception e) {
          e.printStackTrace();
      }
    }
    return nomes;
  }
}
