package servidor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.io.IOException;

public class ExternalImp  extends UnicastRemoteObject implements ExternalInterface {
	private Mandelbrot mandelbrot;
	static final long serialVersionUID = 1;
	
	public ExternalImp(Mandelbrot m) throws RemoteException {
		mandelbrot = m;
	}
	
	@Override
	public Fila pedirTrabajo() throws IOException {
		return mandelbrot.getFila();
	}
	
	@Override
    public void enviarDatos (Fila s) throws IOException {
		mandelbrot.guardarFila(s);
    }
}