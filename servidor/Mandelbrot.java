package servidor;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Mandelbrot {
	private int height, width;
	private short max;
	private calculationState[] state;
	private boolean end;
	BufferedImage result;  //Mandelbrot png image
	Graphics g; //2D map to write the calculated rows
	String nombre;
	
	public enum calculationState{free,doing,done}
	
	public Mandelbrot(int he, int wi, short ma, String n){
		this.height = he;
		this.width = wi;
		this.max = ma;
		this.state = new calculationState[height];
		this.nombre = n;
		Arrays.fill(this.state, calculationState.free);
		this.end = false;
		
		result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		g = result.createGraphics();
	}

	public boolean isEnd() { return end; }

	public int getHeight() { return height; }

	public int getWidth() { return width; }
	
	public synchronized Fila getFila() throws IOException{
		if(!isEnd()){
			int doing = -1;
			for(int i = 0; i < height ; i++){
				if(this.state[i] == calculationState.free){
					end = false;
					this.state[i] = calculationState.doing;
					return new Fila(i, width, height, max);
				}else if(this.state[i] == calculationState.doing)
					doing = i;
			}
			
			/* Si no hay ninguna fila libre, pero si hay por lo menos una en ejecucion, se reasigna a la nueva peticion */
			if(doing != -1)
				return new Fila(doing, width, height, max);
						
			end = true;
			guardarPNG();
			return new Fila(-1,0,0,(short)0);	
		}else return new Fila(-1,0,0,(short)0);
		
	}
	
	public synchronized void guardarFila(Fila s) throws IOException{
		int numFila = s.getPosicion();
		if(numFila < height && numFila >= 0){
			state[numFila] = calculationState.done;
			BufferedImage bi = ImageIO.read(new File("img/"+ numFila +".png"));
	        g.drawImage(bi, 0, numFila, null);
		}
	}
	
	private void guardarPNG() throws IOException{
		ImageIO.write(result, "png",new File(nombre + ".png"));
	}
}
