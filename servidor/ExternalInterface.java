package servidor;
import java.io.IOException;
import java.rmi.*;

public interface ExternalInterface extends Remote {

    Fila pedirTrabajo() throws IOException;
    
    void enviarDatos(Fila s) throws IOException;

}