import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;
import java.util.Vector;
import java.util.Enumeration;

import javax.swing.JList;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;


public class SubastaControlador implements ActionListener, ListSelectionListener {
    SubastaVista vista;
    SubastaModelo modelo;
    Hashtable listaConPrecios;
    Hashtable listaConDes;
    String host = null;
    Client_interface client_stub = null;
    Hello stub = null;
    private boolean productAdded = false;
    InformacionProducto info = null;
    public SubastaControlador( SubastaVista v, SubastaModelo m ) {
        vista = v;
        modelo = m;
    }
    public Hello returnStub;

    public void actionPerformed( ActionEvent evento ) {
        String usuario;
        String producto;
        String descripcion;

        float monto = 0.0f;
        float oferta;

        Vector list = null;
        Enumeration it = null;
        Principal client = new Principal();


        try {

            Registry registry = LocateRegistry.getRegistry(host);

            if (evento.getActionCommand().equals("Salir")) {

                System.exit(1);

            } else if( evento.getActionCommand().equals("Conectar") ) {
                //Crea el stub de ambos en caso de que se conecte
                stub = (Hello) registry.lookup("Hello");
                client_stub = (Client_interface)
                        UnicastRemoteObject.exportObject(client, 0);
                registry = LocateRegistry.getRegistry(host);

                System.out.println( "<<" + evento.getActionCommand() + ">>" );

                usuario = vista.getUsuario();
                stub.registraUsuario(usuario);
                stub.subscribe(client_stub);

            } else if( evento.getActionCommand().equals("Poner a la venta") ) {

                usuario = vista.getUsuario();
                producto = vista.getProducto();
                monto = vista.getPrecioInicial();
                descripcion = vista.getDescription();

                stub.agregaProductoALaVenta(usuario, producto, monto, descripcion);
                productAdded = true;

                if(productAdded){
                    list = stub.obtieneCatalogo();
                    //Enumeration it
                    listaConPrecios = new Hashtable();
                    listaConDes = new Hashtable();

                    vista.reinicializaListaProductos();
                    it = list.elements();

                    while (it.hasMoreElements()) {
                        info = (InformacionProducto) it.nextElement();
                        listaConPrecios.put( info.producto, String.valueOf(info.precioActual) );
                        listaConDes.put(info.producto, String.valueOf(info.descripcion));
                        vista.agregaProducto( info.producto );
                    }
                } else{
                    //System.out.println("El producto no se ha agregado, espere un momento");
                }

            } else if( evento.getActionCommand().equals("Obtener lista") ) {

                if(productAdded){
                    //list = stub.obtieneCatalogo();
                    //Enumeration it;
                    //listaConPrecios = new Hashtable();
                    //listaConDes = new Hashtable();

                    vista.reinicializaListaProductos();
                    it = list.elements();

                    while (it.hasMoreElements()) {
                        info = (InformacionProducto) it.nextElement();
                        listaConPrecios.put( info.producto, String.valueOf(info.precioActual) );
                        listaConDes.put(info.producto, String.valueOf(info.descripcion));
                        vista.agregaProducto( info.producto );
                    }
                } else{
                    System.out.println("El producto no se ha agregado, espere un momento");
                }

            } else if( evento.getActionCommand().equals("Ofrecer") ) {

                producto = vista.getProductoSeleccionado();
                oferta = vista.getMontoOfrecido();
                usuario = vista.getUsuario();
                descripcion = vista.getDescription();

                if(oferta < info.precioActual){
                    System.out.println("La oferta debe ser mayor que el precio incial");
                }else{
                    stub.agregaOferta(usuario, producto, oferta, descripcion);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void valueChanged(ListSelectionEvent e) {
	if (e.getValueIsAdjusting() == false) {
	    JList lista = (JList)e.getSource();
            String item = (String)lista.getSelectedValue();
            if (item != null) {
	        System.out.println(item);
                String precio = (String)listaConPrecios.get(item);
                String des = (String)listaConDes.get(item);
                vista.desplegarPrecio( precio );
                vista.desplagarDescripcion(des);

            }
	}
    }
}
