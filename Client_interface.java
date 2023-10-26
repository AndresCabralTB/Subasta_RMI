import java.rmi.Remote;
import java.rmi.RemoteException;


public interface Client_interface extends Remote {
    void update(String message) throws RemoteException;
}
