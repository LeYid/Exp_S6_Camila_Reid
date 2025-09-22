

package Exp_S6_Camila_Reid.servicio;

import java.util.ArrayList;
import java.util.List;
import Exp_S6_Camila_Reid.modelo.Entrada;

public class GestorEntradas {
    private final List<Entrada> entradasVendidas = new ArrayList<>();
    private int contadorEntradas = 1; 
    
    public void venderEntrada(Entrada e) {
        e.setNumero(contadorEntradas++);
        entradasVendidas.add(e);
    }
    
    public List<Entrada> getEntradasVendidas() {
        return entradasVendidas;
    }
    
    public Entrada buscarEntrada(int numero) {
        for (Entrada e: entradasVendidas) {
            if (e.getNumero() == numero) {
                return e;
            }
        }
        return null; // no se encontró 
    }
    
    public boolean eliminarEntrada(int numero) {
        Entrada e = buscarEntrada(numero);
        if (e != null) return entradasVendidas.remove(e);
            return false;
       
    }
} 