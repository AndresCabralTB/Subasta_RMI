import javax.swing.*;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionListener;

public class SubastaVista {

    JFrame principal;
    JTextField usuario;
    JTextField producto;
    JTextField precioInicial;
    JTextField monto;
    DefaultComboBoxModel productos;
    JLabel precioActual;
    JList lista;
    JButton conectar;
    JButton salir;
    JButton ponerALaVenta;
    JButton obtenerLista;
    JButton ofrecer;

    JLabel descripcionLBL;
    JTextArea descripcion;

    JLabel newDes;
    JLabel newDesBox;

    public SubastaVista() {

        JFrame.setDefaultLookAndFeelDecorated( true );
	Container panel;

	principal = new JFrame( "Cliente Subasta" );
	panel = principal.getContentPane();
	panel.setLayout( new GridLayout(0,2) );
	usuario = new JTextField();
	panel.add( new JLabel("Nombre del usuario") );
	panel.add( usuario );

	conectar = new JButton( "Conectar" );
	salir = new JButton( "Salir" );
	panel.add( conectar );
	panel.add( salir );

	producto = new JTextField();
	precioInicial = new JTextField();
	panel.add( new JLabel("Producto a ofrecer") );
	panel.add( producto );
	panel.add( new JLabel("Precio inicial") );
	panel.add( precioInicial );

    descripcion = new JTextArea("Agregar una descripción (max 200 characteres)");
    descripcion.setWrapStyleWord(true);
    descripcion.setLineWrap(true);
    JScrollPane scrollPane = new JScrollPane(descripcion);

    descripcionLBL = new JLabel("Agregar Descripción");
    panel.add(descripcionLBL);
    panel.add(scrollPane);

	ponerALaVenta = new JButton( "Poner a la venta" );
	panel.add( ponerALaVenta );
	panel.add( new JLabel() );

	productos = new DefaultComboBoxModel();
	lista = new JList( productos ); //data has type Object[]
	lista.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
	lista.setLayoutOrientation( JList.VERTICAL );
	JScrollPane listaScroller = new JScrollPane( lista );
	listaScroller.setPreferredSize( new Dimension(250, 80) );
	obtenerLista = new JButton( "Obtener lista" );
	panel.add( obtenerLista );
	panel.add( listaScroller );

	precioActual = new JLabel();
	panel.add( new JLabel("Precio actual") );
	panel.add( precioActual );

    newDes = new JLabel("Descripción");
    newDesBox = new JLabel();
    panel.add(newDes);
    panel.add((newDesBox));

	monto = new JTextField();
	ofrecer = new JButton( "Ofrecer" );
	panel.add( ofrecer );
	panel.add( monto );

	principal.setSize( 400, 400 );
	principal.setVisible( true );
    }

    public void asignarActionListener(ActionListener controlador) {
	conectar.addActionListener( controlador );
	salir.addActionListener( controlador );
	ponerALaVenta.addActionListener( controlador );
	obtenerLista.addActionListener( controlador );
	ofrecer.addActionListener( controlador );
    }

    public void asignarListSelectionListener( ListSelectionListener controlador ) {
	lista.addListSelectionListener( controlador );
    }

    public String getUsuario() { //Get the text in the username textbox
        return usuario.getText();
    }

    public String getProducto() { //Get name of the product entered
        return producto.getText();
    }
    public String getDescription() { //Get the product descripcion
        return descripcion.getText();
    }

    public float getPrecioInicial() { //Get initial price
        float resultado = 0.0f;
        try {

            resultado = Float.parseFloat( precioInicial.getText() );

        } catch( Exception e ) {

            System.out.println( "Hay problemas con el precio inicial" );
        }

        return resultado;
    }
    public void reinicializaListaProductos() {

        productos.removeAllElements();
    }

    public void agregaProducto( String prod ) {

        productos.addElement( prod );
    }
    public void desplegarPrecio(String precio) {
        precioActual.setText(precio);
    }

    public void desplagarDescripcion(String des) {
        newDesBox.setText(des);
    }

    public float getMontoOfrecido() {

        float resultado = 0.0f;

        try {
            resultado = Float.parseFloat( monto.getText() );

        } catch( Exception e ) {

            System.out.println( "Hay problemas con el monto ofrecido" );
        }
        return resultado;
    }
    public String getProductoSeleccionado() {
        return (String)lista.getSelectedValue();
    }
}
