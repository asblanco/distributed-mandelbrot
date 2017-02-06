/**
  \file
  \brief servidor

*/
package servidor;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Servidor {

    public static void main(String[] args) {
    	if(args.length == 4){
			try {
				final int width = Integer.parseInt(args[0]);
				final int height = Integer.parseInt(args[1]);
				short max = Short.parseShort(args[2]); //Numero maximo de iteraciones
				final String nombre = args[3];
				
				Mandelbrot m = new Mandelbrot(height,width,max, nombre);

				ExternalInterface c = new ExternalImp(m);
				LocateRegistry.createRegistry(1099);
				Naming.rebind("rmi://localhost:1099/MandelbrotService", c);
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
            
    	} else System.out.println("Debe introducir como parametros width, height (la resolución), numero de maximo iteraciones y el nombre de la imagen");
    }
}

/**
\mainpage Documentacion Práctica 5

\section Author 
Andrea Sánchez Blanco

*/