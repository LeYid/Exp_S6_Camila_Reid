

package Exp_S5_Camila_Reid;

import java.util.Scanner;
import Exp_S5_Camila_Reid.modelo.Entrada;
import Exp_S5_Camila_Reid.servicio.GestorEntradas;

public class Menu {
    private final GestorEntradas gestor = new GestorEntradas();
    private final Scanner sc = new Scanner(System.in);

    // Asientos
    private boolean A1, A2, A3, A4, A5, A6;
    private boolean B1, B2, B3, B4, B5, B6;
    private boolean C1, C2, C3, C4, C5, C6;

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.iniciar();
    }

    public void iniciar() {
        int opcion = 0;

        do {
            System.out.println("\n=== MENÚ TEATRO MORO ====");
            System.out.println("1. Comprar entrada");
            System.out.println("2. Promociones");
            System.out.println("3. Buscar entrada");
            System.out.println("4. Eliminar entrada");
            System.out.println("5. Ver estadísticas");
            System.out.println("0. Salir");
            System.out.print("Opción: ");

            if (!sc.hasNextInt()) {
                System.out.println("Error: Debes ingresar un número válido.");
                sc.next();
                continue;
            }

            opcion = sc.nextInt();
            sc.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1 -> comprarEntrada();
                case 2 -> mostrarPromociones();
                case 3 -> buscarEntrada();
                case 4 -> eliminarEntrada();
                case 5 -> verEstadisticas();
                default -> {
                    if (opcion != 0) {
                        System.out.println("Opción inválida.");
                    }
                }
            }
        } while (opcion != 0);
    }
       

    // ==================== MÉTODOS ====================

    private void comprarEntrada() {
        System.out.println("\n=== RESERVA DE ASIENTOS ===");
        System.out.println("Leyenda: [ ] = Libre | [X] = Ocupado\n");
        System.out.print("    ");
        for (int i = 1; i <= 6; i++) System.out.print(" " + i + " ");
        System.out.println();

        System.out.println("A | " + (A1 ? "[X]" : "[ ]") + (A2 ? "[X]" : "[ ]") + (A3 ? "[X]" : "[ ]") + (A4 ? "[X]" : "[ ]") + (A5 ? "[X]" : "[ ]") + (A6 ? "[X]" : "[ ]") + " ($" + Teatro.getPrecio("General") + ")");
        System.out.println("B | " + (B1 ? "[X]" : "[ ]") + (B2 ? "[X]" : "[ ]") + (B3 ? "[X]" : "[ ]") + (B4 ? "[X]" : "[ ]") + (B5 ? "[X]" : "[ ]") + (B6 ? "[X]" : "[ ]") + " ($" + Teatro.getPrecio("General") + ")");
        System.out.println("C | " + (C1 ? "[X]" : "[ ]") + (C2 ? "[X]" : "[ ]") + (C3 ? "[X]" : "[ ]") + (C4 ? "[X]" : "[ ]") + (C5 ? "[X]" : "[ ]") + (C6 ? "[X]" : "[ ]") + " ($" + Teatro.getPrecio("General") + ")");

        System.out.print("Ingresa asiento (ej. A4):");
        String asiento = sc.nextLine().toUpperCase().trim();
        
        //Validar formato: debe tener 2 caracteres, letra A-C y número 1-6
        if (!asiento.matches("[ABC][1-6]")) {
            System.out.println("Asiento inválido. Usa el formato A1, B3, C6, etc.");
            return;
        }
        
        // Separar fila y número
        char fila = asiento.charAt(0);
        int num = Character.getNumericValue(asiento.charAt(1));
        
        // Verificar si ya está ocupado
        if (revisarOcupado(fila,num)) {
            System.out.println("Asiento ya ocupado.");
            return;
        }

        // Solicitar edad
        System.out.print("Ingresa tu edad: ");
        
        boolean esTerceraEdad = false; 
        
        if (!sc.hasNextInt()) {
            System.out.println("Ingresa un número válido");
            sc.nextLine(); // limpia buffer
        } else {
            int edad = sc.nextInt();
            esTerceraEdad = edad >= 65;
            sc.nextLine(); // limpia buffer
        }

        // Preguntar si es estudiante
        boolean esEstudiante = false;
        if (!esTerceraEdad) {
            System.out.print("¿Eres estudiante? (s/n): ");
            String resp = sc.nextLine();
            esEstudiante = resp.equalsIgnoreCase("s");
        }

        // Preguntar si es segunda entrada
        System.out.print("¿Es la segunda entrada? (s/n): ");
        String resp2 = sc.nextLine();
        boolean segundaEntrada = resp2.equalsIgnoreCase("s");

        double precioBase = Teatro.getPrecio("General");
        double precioFinal = calcularPrecioPromocion(precioBase, segundaEntrada, esEstudiante, esTerceraEdad);
        
        System.out.println("\nAsiento reservado con éxito: ");
        System.out.println("Fila: " + fila + ", Número: " + num);
        System.out.println("Precio final: $" + precioFinal);
        
        
        // Mostrar tipo de descuento
        if (esEstudiante) {
            System.out.println("Descuento aplicado: 10% por estudiante");
        } else if (esTerceraEdad) {
            System.out.println("Descuento aplicado: 15% por tercera edad");
        } else if (segundaEntrada) {
            System.out.println("Descuento aplicado: 20% por tercera edad");
        } else {
            System.out.println("Descuento aplicado: Ninguno");
        }
        
        // Confirmar compra
        System.out.println("\n¿Deseas confirmar tu compra?");
        System.out.println("1) Sí");
        System.out.println("2) No");
        System.out.println("Opción:");
        int confirmarCompra = sc.nextInt();
        sc.nextLine(); // limpiar buffer
        
        switch (confirmarCompra) {
            case 1 -> {
                // Marcar asiento ocupado
                ocuparAsiento(fila, num);
                // Crear y guardar entrada
                Entrada entrada = new Entrada(String.valueOf(fila), precioFinal, 
                        esEstudiante ? "Estudiante" : esTerceraEdad ? "Tercera Edad" : "Normal");
                
                // Vender entrada (número asignado automáticamente)
                gestor.venderEntrada(entrada);
                
                // Mostrar entrada
                System.out.printf("Compra confirmada. Aquí está tu entrada: \n");
                System.out.println(entrada); // toString()
                sc.nextLine(); /// limpiar buffer
            }
            case 2 -> {
                System.out.println("Compra anulada");
            }

            default -> System.out.println("Opción inválida");
        }
    }

    private void mostrarPromociones() {
        System.out.println("\n=== PROMOCIONES ===");
        System.out.println("1) Compra 1 y lleva la 2da entrada con 20% DSCTO (no acumulable)");
        System.out.println("2) Estudiante: 10% de descuento");
        System.out.println("3) Tercera Edad: 15% de descuento");
        System.out.println("Promociones NO acumulables");
        System.out.println("¿Deseas simular la compra? (1 = Sí / 2 = No)");
        int simular = sc.nextInt();
        sc.nextLine();

        if (simular == 1) {
            simularPromociones();
        } else if (simular != 2) {
            System.out.println("Opción inválida. Intente nuevamente.");
        }
    }

    private void simularPromociones() {
        System.out.println("\n=== SIMULACIÓN DE PROMOCIONES ===");

        System.out.print("Ingresa ubicación de las entradas (VIP, Platea, General): ");
        String ubicacion = sc.nextLine();
        double precioBase = Teatro.getPrecio(ubicacion);
        
        if (precioBase == 0) {
            System.out.println("Ubicación inválida");
            return;
        }

        System.out.print("¿Cuántas entradas deseas simular? (1 o 2): ");
        int cantidad = sc.nextInt();
        sc.nextLine();
        if (cantidad < 1 || cantidad > 2) {
            System.out.println("Solo puedes simular 1 o 2 entradas");
            return;
        }

        double total = 0;

        for (int i = 1; i <= cantidad; i++) {
            System.out.printf("\nEntrada %d:\n", i);
            System.out.print("Tipo de cliente (Normal, Estudiante, Tercera Edad): ");
            String tipoCliente = sc.nextLine();

            boolean esEstudiante = tipoCliente.equalsIgnoreCase("Estudiante");
            boolean esTerceraEdad = tipoCliente.equalsIgnoreCase("Tercera Edad");
            boolean segundaEntrada = (i == 2);

            double precioFinal = calcularPrecioPromocion(precioBase, segundaEntrada, esEstudiante, esTerceraEdad);
            System.out.printf("Precio final entrada %d: $%.0f\n", i, precioFinal);
            total += precioFinal;
        }

        System.out.printf("\nTotal a pagar por %d entradas: $%.0f\n", cantidad, total);
    }

    private void buscarEntrada() {
        System.out.print("Escribe el # de la entrada que quieres buscar: ");
        int numero = sc.nextInt();
        sc.nextLine();

        Entrada e = gestor.buscarEntrada(numero);
        if (e != null) {
            System.out.println("Entrada encontrada:");
            System.out.println(e); // toString de Entrada
        } else {
            System.out.println("No se encontró la entrada.");
        }
    }

    private void eliminarEntrada() {
        System.out.print("Ingresa el número de la entrada a eliminar: ");
        
        if (!sc.hasNextInt()) { // validar que sea un número
            System.out.println("Opción inválida. Debes ingresar un número.");
            sc.nextLine(); // limpiar buffer
            return; // salir del método
    }
        int numero = sc.nextInt();
        sc.nextLine();
        
        Entrada e = gestor.buscarEntrada(numero);
        if (e != null) {
            System.out.println("Entrada encontrada: ");
            System.out.println(e); // toString() de Entrada
            
            System.out.print("¿Desea eliminarla? (s/n): ");
            String resp = sc.nextLine();
            
            if (resp.equalsIgnoreCase("s")) {
                boolean eliminado = gestor.eliminarEntrada(numero);
                if (eliminado) {
                    System.out.println("Entrada eliminada con éxito.");
                } else {
                    System.out.println("No se pudo eliminar la entrada.");
                }
            } else {
                System.out.println("Operación cancelada.");
            }
        } else {
            System.out.println("No se encontró la entrada.");
        }
    }

   private void verEstadisticas() {
    System.out.println("Entradas vendidas:");

    if (gestor.getEntradasVendidas().isEmpty()) {
        System.out.println("0");
    } else {
        gestor.getEntradasVendidas().forEach(System.out::println);
    }
}

    // ================= Métodos auxiliares ==================

    private boolean revisarOcupado(char fila, int num) {
        return switch (fila) {
            case 'A' -> switch (num) {
                case 1 -> A1; case 2 -> A2; case 3 -> A3;
                case 4 -> A4; case 5 -> A5; case 6 -> A6;
                default -> false;
            };
            case 'B' -> switch (num) {
                case 1 -> B1; case 2 -> B2; case 3 -> B3;
                case 4 -> B4; case 5 -> B5; case 6 -> B6;
                default -> false;
            };
            case 'C' -> switch (num) {
                case 1 -> C1; case 2 -> C2; case 3 -> C3;
                case 4 -> C4; case 5 -> C5; case 6 -> C6;
                default -> false;
            };
            default -> false;
        };
    }

    private void ocuparAsiento(char fila, int num) {
        switch (fila) {
            case 'A' -> { if (num==1) A1=true; if (num==2) A2=true; if (num==3) A3=true; if (num==4) A4=true; if (num==5) A5=true; if (num==6) A6=true; }
            case 'B' -> { if (num==1) B1=true; if (num==2) B2=true; if (num==3) B3=true; if (num==4) B4=true; if (num==5) B5=true; if (num==6) B6=true; }
            case 'C' -> { if (num==1) C1=true; if (num==2) C2=true; if (num==3) C3=true; if (num==4) C4=true; if (num==5) C5=true; if (num==6) C6=true; }
        }
    }

    private double calcularPrecioPromocion(double precioBase, boolean segundaEntrada, boolean esEstudiante, boolean esTerceraEdad) {
        double descuento = 0;
        if (esEstudiante) descuento = 0.10;
        else if (esTerceraEdad) descuento = 0.15;
        else if (segundaEntrada) descuento = 0.20;
        return precioBase * (1 - descuento);
    }
}