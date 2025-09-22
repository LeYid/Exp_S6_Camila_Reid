

package Exp_S6_Camila_Reid.modelo;


public class Entrada {
   
    private int numero; // # de entrada
    private final String ubicacion;
    private final double precioFinal;
    private final String tipoCliente; // estudiante, tercera edad, general

    
    // Constructor SIN número
    public Entrada(String ubicacion, double precioFinal, String tipoCliente) {
        this.ubicacion = ubicacion;
        this.precioFinal = precioFinal;
        this.tipoCliente = tipoCliente;
    
    }
    
    public void setNumero(int numero) {
        this.numero = numero;
    }
    
    public int getNumero() {
        return numero;
    }
    
        
    @Override
    public String toString() {
        String detalleDescuento;
        
        switch (tipoCliente) {
            case "Estudiante" -> detalleDescuento = "Descuento 10% por estudiante";
            case "Tercera Edad" -> detalleDescuento = "Descuento: 15% por tercera edad";
                    default -> detalleDescuento = "Sin descuento";
        }
        
        return  """
            -------------------------
                  ENTRADA DE TEATRO   
            -------------------------
            Número entrada: "#""" + numero + "\n" +
            "Ubicación: " + ubicacion + "\n" +
            "Tipo: " + tipoCliente + "\n" +
                detalleDescuento + "\n" +
            String.format("Precio Final: $%.0f", precioFinal) + "\n" +
            "-------------------------";
    }
}
