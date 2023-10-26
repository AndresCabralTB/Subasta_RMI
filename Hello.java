import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;


public interface Hello extends Remote {
    void registraUsuario(String nombre) throws RemoteException;
    void agregaProductoALaVenta(String vendedor, String producto, float precioInicial, String descripcion) throws RemoteException;
    void agregaOferta(String comprador, String producto, float monto, String des) throws RemoteException;
    Vector obtieneCatalogo() throws RemoteException;
    void subscribe(Client_interface client) throws RemoteException;
    void notifyAllClients(String message) throws RemoteException;



}
