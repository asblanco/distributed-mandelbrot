package servidor;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

public class Fila implements Serializable{
	private int posicion, width, height;
	private short max;
	private int black;
    private int[] colors;
    static final long serialVersionUID = 0;
	
	public Fila(int p, int w, int h, short m){
		this.posicion = p;
		this.width = w;
		this.height = h;
		this.max = m;
		
		black = 0;
		colors = new int[max];
		
		for (int i = 0; i < max; i++) {
	        colors[i] = Color.HSBtoRGB(i/256f, 1, i/(i+8f));
	    }
	}
	
	public int getPosicion() { return posicion; }
	
	public void calcularFila() throws IOException {
		BufferedImage image = new BufferedImage(width, 1, BufferedImage.TYPE_INT_RGB);
		
        for (int col = 0; col < width; col++) {
            double c_re = (col - width/2)*4.0/width;
            double c_im = (posicion - height/2)*4.0/width;
            double x = 0, y = 0;
            int iteration = 0;
            
            while (x*x+y*y < 4 && iteration < max) {
                double x_new = x*x-y*y+c_re;
                y = 2*x*y+c_im;
                x = x_new;
                iteration++;
            }
            if (iteration < max) image.setRGB(col, 0, colors[iteration]);
            else image.setRGB(col, 0, black);
        }
        
        ImageIO.write(image, "png", new File("img/"+ posicion +".png"));
	}
}
