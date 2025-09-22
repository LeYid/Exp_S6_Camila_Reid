

package Exp_S6_Camila_Reid;

public class Teatro {
    private static final double PRECIO_VIP = 30000;
    private static final double PRECIO_PLATEA = 20000;
    private static final double PRECIO_GENERAL = 15000;

    public static double getPrecio(String ubicacion) {
        return switch (ubicacion.toLowerCase()) {
            case "vip" -> PRECIO_VIP;
            case "platea" -> PRECIO_PLATEA;
            case "general" -> PRECIO_GENERAL;
            default -> 0;
        }; 
    }
}

