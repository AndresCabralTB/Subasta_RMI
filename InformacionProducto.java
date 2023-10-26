import java.io.Serializable;

public class InformacionProducto implements Serializable{

    String vendedor;
    String producto;
    String descripcion;
    float precioInicial;
    float precioActual;

    public InformacionProducto( String v, String p, float pi, String des ) {
        vendedor = v;
        producto = p;
        precioInicial = pi;
        precioActual = pi;
        descripcion = des;
    }

    public boolean actualizaPrecio( float monto ) {

        if( monto > precioActual ) {
            precioActual = monto;
            System.out.println("Usuario: " + vendedor + " - Oferta: " + monto + " - Producto: " + producto + " - Descripción: " + descripcion);
            return true;
        } else
        return false;
    }

}
