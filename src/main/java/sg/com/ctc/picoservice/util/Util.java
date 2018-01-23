package sg.com.ctc.picoservice.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.imageio.ImageIO;

public class Util {
	public static void saveImage(String imageUrl, String destinationFile) throws IOException {
		URL url = new URL(imageUrl);
		InputStream is = url.openStream();
		OutputStream os = new FileOutputStream(destinationFile);

		byte[] b = new byte[2048];
		int length;

		while ((length = is.read(b)) != -1) {
			os.write(b, 0, length);
		}

		is.close();
		os.close();
	}
	
	public static void resize(String inputImagePath,
            String outputImagePath, int scaledWidth, int scaledHeight)
            throws IOException {
        // reads input image
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);
 
        // creates output image
        BufferedImage outputImage = new BufferedImage(scaledWidth,
                scaledHeight, inputImage.getType());
 
        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();
 
        // extracts extension of output file
        String formatName = outputImagePath.substring(outputImagePath
                .lastIndexOf(".") + 1);
 
        // writes to output file
        ImageIO.write(outputImage, formatName, new File(outputImagePath));
    }
 
    /**
     * Resizes an image by a percentage of original size (proportional).
     * @param inputImagePath Path of the original image
     * @param outputImagePath Path to save the resized image
     * @param percent a double number specifies percentage of the output image
     * over the input image.
     * @throws IOException
     */
    public static void resize(String inputImagePath,
            String outputImagePath, double percent) throws IOException {
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);
        int scaledWidth = (int) (inputImage.getWidth() * percent);
        int scaledHeight = (int) (inputImage.getHeight() * percent);
        resize(inputImagePath, outputImagePath, scaledWidth, scaledHeight);
    }
    
    public static String rebuildTime(int input){
    	String time = "";
		if(String.valueOf(input).length()==1){
			time = String.valueOf(0) + String.valueOf(input);
		}else{
			time = String.valueOf(input);
		}
		return time;
    }
    
    public static String buildSpace(int num){
    	switch(num){
    		case 1: return " ";
    		case 2: return "  ";
    		case 3: return "   ";
    		case 4: return "    ";
    		case 5: return "     ";
    		case 6: return "      ";
    		case 7: return "       ";
    		case 8: return "        ";
    		case 9: return "         ";
    		case 10: return "          ";
    		case 11: return "           ";
    		case 12: return "            ";
    		case 13: return "             ";
    		case 14: return "              ";
    		case 15: return "               ";
    		case 16: return "                ";
    		case 17: return "                 ";
    		case 18: return "                  ";
    		case 19: return "                   ";
    		case 20: return "                    ";
    		default: return "";
    	}
    }
}
