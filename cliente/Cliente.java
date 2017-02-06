/**
  \file
  \brief cliente

*/
package cliente;

import java.rmi.Naming;
import servidor.ExternalInterface;
import servidor.Fila;

public class Cliente{
	
	public static void main(String[] args){
		if(args.length == 2){
			String url=args[0];
			String port=args[1];
			
			try{
				ExternalInterface c = (ExternalInterface) Naming.lookup("rmi://"+url+":"+port+"/MandelbrotService");
				
				while(true){
					Fila fila = c.pedirTrabajo();
					System.out.println(fila.getPosicion());
					
					if(fila.getPosicion() == -1) return;
					else{
						fila.calcularFila();
						c.enviarDatos(fila);
					}
				}
			}
			catch(Exception e){
				System.out.println(e.toString());
			}
		} else System.out.println("Debe introducir como parametros la URL y el puerto");
	}
}
