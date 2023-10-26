import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;
import java.util.List;
import java.util.LinkedList;

import java.util.Vector;

public class SubastaModelo implements Hello {

    Hashtable usuarios;
    Hashtable productos;
    Hashtable ofertas;
    public List<Client_interface> clients;

    public SubastaModelo() {
        usuarios = new Hashtable();
        productos = new Hashtable();
        ofertas = new Hashtable();
        clients = new LinkedList<>();
    }
    public void registraUsuario( String nombre ) {
        if( !usuarios.containsKey(nombre) ) {
            System.out.println( "Agregando un nuevo usuario: " + nombre );
            usuarios.put( nombre, nombre );
        }
    }
    public void agregaProductoALaVenta(String vendedor, String producto, float precioInicial, String descripcion) throws RemoteException{
        if(!productos.containsKey(producto) ) {

            System.out.println( "Nuevo producto: " + producto + " - Monto: " + precioInicial + " - Vendedor: " + vendedor + " - Descripcion: " + descripcion);
            productos.put(producto, new InformacionProducto(vendedor, producto, precioInicial, descripcion));
            notifyAllClients("Hay un nuevo producto a la venta");
        }
    }
    public void agregaOferta( String comprador, String producto, float monto, String des ) throws RemoteException {

        if( productos.containsKey(producto) ) {

            InformacionProducto infoProd;
            infoProd = (InformacionProducto) productos.get(producto);

            if( infoProd.actualizaPrecio(monto) ) {
                ofertas.put( producto + comprador, new InformacionOferta( comprador, producto, monto, des) );
                notifyAllClients("Se ha hecho una oferta de " + monto + " por el producto: " + producto);
            }
        }
    }

    public void recargaLista(Vector catalogo, SubastaVista vista){
        catalogo = new Vector(productos.values());
        vista.lista = catalogo;

    }
    public Vector obtieneCatalogo() {

	Vector resultado;

	resultado = new Vector( productos.values() );

	return resultado;
    }
    public void subscribe(Client_interface c) throws RemoteException {
        clients.add(c);
        c.update("Estas conectado al servidor");
        notifyAllClients("Hay un nuevo cliente activo");
    }

    public void notifyAllClients(String message) throws RemoteException {
        for (Client_interface client : clients) {
            client.update(message);
        }
    }

    public static void main(String args[]) {

        try {
            SubastaModelo obj = new SubastaModelo();
            Hello stub = (Hello) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Hello", stub);

            System.err.println("Server ready");

        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
