package org.iesalandalus.programacion.alquilervehiculos.modelo.dominio;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cliente {
    private static final String ER_NOMBRE = "^[A-ZÁÉÍÓÚÑ][a-záéíóúñ]+(?: [A-ZÁÉÍÓÚÑ][a-záéíóúñ]+)*$";
    private static final String ER_DNI = "\\d{8}[A-Z]";
    private static final String ER_TELEFONO = "\\d{9}";
    private String nombre;
    private String dni;
    private String telefono;

    public Cliente(String nombre, String dni, String telefono) {
        setNombre(nombre);
        setDni(dni);
        setTelefono(telefono);
    }

    public Cliente(Cliente cliente) {
        Objects.requireNonNull(cliente, "ERROR: No es posible copiar un cliente nulo.");
        this.nombre = cliente.getNombre();
        this.dni = cliente.getDni();
        this.telefono = cliente.getTelefono();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        Objects.requireNonNull(nombre, "ERROR: El nombre no puede ser nulo.");
        Pattern patron = Pattern.compile(ER_NOMBRE);
        Matcher comparador = patron.matcher(nombre);
        if (!comparador.matches()) {
            throw new IllegalArgumentException("ERROR: El nombre no tiene un formato válido.");
        }
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    private void setDni(String dni) {
        Objects.requireNonNull(dni, "ERROR: El DNI no puede ser nulo.");
        Pattern patron = Pattern.compile(ER_DNI);
        Matcher comparador = patron.matcher(dni);
        if (!comparador.matches()) {
            throw new IllegalArgumentException("ERROR: El DNI no tiene un formato válido.");
        }
        if (!comprobarLetraDni(dni)) {
            throw new IllegalArgumentException("ERROR: La letra del DNI no es correcta.");
        }
        this.dni = dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        Objects.requireNonNull(telefono, "ERROR: El teléfono no puede ser nulo.");
        Pattern patron = Pattern.compile(ER_TELEFONO);
        Matcher comparador = patron.matcher(telefono);
        if (!comparador.matches()) {
            throw new IllegalArgumentException("ERROR: El teléfono no tiene un formato válido.");
        }
        this.telefono = telefono;
    }

    private boolean comprobarLetraDni(String dni) {
        char[] letrasDni = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};
        char letraEntradaDni = dni.charAt(dni.length() - 1);
        int numeroEntradaDni = Integer.parseInt(dni.substring(0, 8));
        int indiceLetraDni = numeroEntradaDni % 23;
        return (letraEntradaDni == letrasDni[indiceLetraDni]);
    }

    public static Cliente getClienteConDni(String dni) {
        Objects.requireNonNull(dni, "ERROR: El DNI no puede ser nulo.");
        return new Cliente("Cliente", dni, "000000000");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(dni, cliente.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni);
    }

    @Override
    public String toString() {
        return String.format("%s - %s (%s)", this.nombre, this.dni, this.telefono);
    }
}