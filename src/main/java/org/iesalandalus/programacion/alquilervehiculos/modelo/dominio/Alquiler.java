package org.iesalandalus.programacion.alquilervehiculos.modelo.dominio;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Alquiler {
    static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final int PRECIO_DIA = 20;
    private LocalDate fechaAlquiler;
    private LocalDate fechaDevolucion;
    private Turismo turismo;
    private Cliente cliente;

    public Alquiler(Cliente cliente, Turismo turismo, LocalDate fechaAlquiler) {
        setCliente(cliente);
        setTurismo(turismo);
        setFechaAlquiler(fechaAlquiler);
    }

    public Alquiler(Alquiler alquiler) {
        Objects.requireNonNull(alquiler, "ERROR: No es posible copiar un alquiler nulo.");
        this.cliente = new Cliente(alquiler.getCliente());
        this.turismo = new Turismo(alquiler.getTurismo());
        this.fechaAlquiler = alquiler.getFechaAlquiler();
        this.fechaDevolucion = alquiler.getFechaDevolucion();
    }

    public LocalDate getFechaAlquiler() {
        return fechaAlquiler;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public Turismo getTurismo() {
        return turismo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    private void setFechaAlquiler(LocalDate fechaAlquiler) {
        Objects.requireNonNull(fechaAlquiler, "ERROR: La fecha de alquiler no puede ser nula.");
        if (fechaAlquiler.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("ERROR: La fecha de alquiler no puede ser futura.");
        }
        this.fechaAlquiler = fechaAlquiler;
    }

    private void setFechaDevolucion(LocalDate fechaDevolucion) throws OperationNotSupportedException {
        Objects.requireNonNull(fechaDevolucion, "ERROR: La fecha de devolución no puede ser nula.");
        if (!fechaDevolucion.isAfter(getFechaAlquiler())) {
            throw new IllegalArgumentException("ERROR: La fecha de devolución debe ser posterior a la fecha de alquiler.");
        }
        if (fechaDevolucion.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("ERROR: La fecha de devolución no puede ser futura.");
        }
        if (fechaDevolucion.equals(getFechaDevolucion())) {
            throw new OperationNotSupportedException("ERROR: La devolución ya estaba registrada.");
        }
        this.fechaDevolucion = fechaDevolucion;
    }


    private void setTurismo(Turismo turismo) {
        Objects.requireNonNull(turismo, "ERROR: El turismo no puede ser nulo.");
        this.turismo = turismo;
    }

    private void setCliente(Cliente cliente) {
        Objects.requireNonNull(cliente, "ERROR: El cliente no puede ser nulo.");
        this.cliente = cliente;
    }

    public void devolver(LocalDate fechaDevolucion) throws OperationNotSupportedException {
        setFechaDevolucion(fechaDevolucion);
    }

    public int getPrecio() {

        return (getFechaDevolucion() == null) ? 0 : (int) ((PRECIO_DIA + (turismo.getCilindrada() / 10)) * (getFechaAlquiler().until(getFechaDevolucion(), ChronoUnit.DAYS)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alquiler alquiler = (Alquiler) o;
        return Objects.equals(fechaAlquiler, alquiler.fechaAlquiler) && Objects.equals(turismo, alquiler.turismo) && Objects.equals(cliente, alquiler.cliente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fechaAlquiler, turismo, cliente);
    }

    @Override
    public String toString() {
        return (getFechaDevolucion() == null) ? String.format("%s <---> %s, %s - Aún no devuelto (%s€)", this.cliente, this.turismo, this.fechaAlquiler.format(FORMATO_FECHA), getPrecio()) : String.format("%s <---> %s, %s - %s (%s€)", this.cliente, this.turismo, this.fechaAlquiler.format(FORMATO_FECHA), this.fechaDevolucion.format(FORMATO_FECHA), getPrecio());
    }
}