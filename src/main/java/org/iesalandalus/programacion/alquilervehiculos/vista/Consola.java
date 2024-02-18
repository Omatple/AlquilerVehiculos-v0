package org.iesalandalus.programacion.alquilervehiculos.vista;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.utilidades.Entrada;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Consola {

    private static final String PATRON_FECHA = "^(?:(?:1[6-9]|[2-9]\\d)?\\d{2})-(?:(?:(?:0?[1-9]|1[0-2])-(?:0?[1-9]|1\\d|2[0-8]))|(?:(?:0?[13-9]|1[0-2])-(?:29|30))|(?:0?[13578]|1[02])-31)$|^(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))-02-29$";
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private Consola() {
    }

    public static void mostrarCabezera(String mensaje) {
        StringBuilder mostrarCabezera = new StringBuilder();
        char guion = '-';
        mostrarCabezera.append(String.format("%s%n", String.valueOf(guion).repeat(mensaje.length())));
        mostrarCabezera.append(String.format("%s%n", mensaje));
        mostrarCabezera.append(String.format("%s", String.valueOf(guion).repeat(mensaje.length())));
        System.out.println(mostrarCabezera);
    }

    public static void mostrarMenu() {
        mostrarCabezera("Nuestra aplicación ofrece una visión general de las operaciones, facilitando la gestión y supervisión de las reservas de vehículos");
        int numeroOpcion = 1;
        System.out.println("Menú de opciones: ");
        for (Opcion opcion : Opcion.values()) {
            System.out.printf(String.format("%s.- %s.\n", numeroOpcion, opcion.toString()));
            numeroOpcion++;
        }
    }

    private static String leerCadena(String mensaje) {
        System.out.print(mensaje);
        return Entrada.cadena();
    }

    private static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        return Entrada.entero();
    }

    private static LocalDate leerFecha(String mensaje) {
        System.out.print(mensaje);
        String entradaFecha = Entrada.cadena();
        Pattern patron = Pattern.compile(PATRON_FECHA);
        Matcher comparador = patron.matcher(entradaFecha);
        while (!comparador.matches()) {
            System.out.println("ERROR: Formato de fecha(aaaa-MM-dd) no valido.");
            System.out.print(mensaje);
            entradaFecha = Entrada.cadena();
            comparador = patron.matcher(entradaFecha);
        }
        return LocalDate.parse(entradaFecha);
    }

    public static Opcion elegirOpcion() {
        Opcion opcionElegida = Opcion.SALIR;
        boolean opcionEsValida = false;
        while (!opcionEsValida) {
            try {
                int numeroOpcion = leerEntero("Ingrese el número correspondiente a su opción elegida del menú: ");
                opcionElegida = Opcion.get(numeroOpcion);
                opcionEsValida = true;
            } catch (OperationNotSupportedException e) {
                System.out.println(e.getMessage());
            }
        }
        return opcionElegida;
    }

    public static Cliente leerCliente() {
        Cliente cliente = leerClienteDni();
        cliente.setNombre(leerNombre());
        cliente.setTelefono(leerTelefono());
        return new Cliente(cliente);
    }

    public static Cliente leerClienteDni() {
        return Cliente.getClienteConDni(leerCadena("Ingrese el DNI: "));
    }

    public static String leerNombre() {
        return leerCadena("Ingrese el nombre: ");
    }

    public static String leerTelefono() {
        return leerCadena("Ingrese el teléfono: ");
    }

    public static Turismo leerTurismo() {
        return new Turismo(leerCadena("Ingrese la marca: "), leerCadena("Ingrese el modelo: "), leerEntero("Ingrese la cilindrada: "), leerCadena("Ingrese la matricula: "));
    }

    public static Turismo leerTurismoMatricula() {
        return Turismo.getTurismoConMatricula(leerCadena("Ingrese la matricula: "));
    }

    public static Alquiler leerAlquiler() {
        return new Alquiler(leerClienteDni(), leerTurismoMatricula(), leerFecha("Ingrese la fecha de alquiler: "));
    }

    public static LocalDate leerFechaDevolucion() {
        return leerFecha("Ingrese la fecha de devolución: ");
    }
}
