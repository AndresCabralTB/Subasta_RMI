import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class Principal implements Client_interface {
    public Principal(){}
    public void update(String message){
        System.out.println("Update received: " + message);
    }

    public static void main( String args[] ) {

        try {

            SubastaVista vista;
            SubastaControlador controlador;
            SubastaModelo modelo;

            //Crea la vista para el cliente

            vista = new SubastaVista();
            modelo = new SubastaModelo();
            controlador = new SubastaControlador(vista, modelo);

            vista.asignarActionListener(controlador);
            vista.asignarListSelectionListener(controlador);

        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }


    }
}
