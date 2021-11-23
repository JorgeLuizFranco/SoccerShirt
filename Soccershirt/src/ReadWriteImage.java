import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
 
public class ReadWriteImage {   
    public static void main( String[] args ) {
        BufferedImage image;
        try {
           
            URL url = new URL("http://garbosoftware.com.br/resources/images/garbo-software.png");
            image = ImageIO.read(url);
             
            ImageIO.write(image, "jpg",new File("..imgs\\out.jpg"));
            ImageIO.write(image, "gif",new File("..imgs\\out.jpg"));
            ImageIO.write(image, "png",new File("..imgs\\out.jpg"));
             
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Done");
    }
}