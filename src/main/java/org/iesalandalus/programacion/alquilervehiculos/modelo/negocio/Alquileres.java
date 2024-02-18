package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Alquileres {
    List<Alquiler> coleccionAlquileres;

    public Alquileres() {
        this.coleccionAlquileres = new ArrayList<>();
    }

    public List<Alquiler> get() {
        return new ArrayList<>(coleccionAlquileres);
    }

    public List<Alquiler> get(Cliente cliente) {
        List<Alquiler> coleccionAlquileresCliente = new ArrayList<>();
        for (Alquiler alquilerCliente : coleccionAlquileres) {
            if (alquilerCliente.getCliente().equals(cliente)) {
                coleccionAlquileresCliente.add(alquilerCliente);
            }
        }
        return coleccionAlquileresCliente;
    }

    public List<Alquiler> get(Turismo turismo) {
        List<Alquiler> coleccionAlquilerTurismo = new ArrayList<>();
        for (Alquiler alquilerTurismo : coleccionAlquileres) {
            if (alquilerTurismo.getTurismo().equals(turismo)) {
                coleccionAlquilerTurismo.add(alquilerTurismo);
            }
        }
        return coleccionAlquilerTurismo;
    }

    public int getCantidad() {
        return coleccionAlquileres.size();
    }

    private void comprobarAlquiler(Cliente cliente, Turismo turismo, LocalDate fechaAlquiler) throws OperationNotSupportedException {
        for (Alquiler alquiler : coleccionAlquileres) {
            if (alquiler.getCliente().equals(cliente)) {
                if (alquiler.getFechaDevolucion() == null) {
                    throw new OperationNotSupportedException("ERROR: El cliente tiene otro alquiler sin devolver.");
                } else if (!fechaAlquiler.isAfter(alquiler.getFechaDevolucion())) {
                    throw new OperationNotSupportedException("ERROR: El cliente tiene un alquiler posterior.");
                }
            }
            if (alquiler.getTurismo().equals(turismo)) {
                if (alquiler.getFechaDevolucion() == null) {
                    throw new OperationNotSupportedException("ERROR: El turismo está actualmente alquilado.");
                } else if (!fechaAlquiler.isAfter(alquiler.getFechaDevolucion())) {
                    throw new OperationNotSupportedException("ERROR: El turismo tiene un alquiler posterior.");
                }
            }
        }
    }

    public void insertar(Alquiler alquiler) throws OperationNotSupportedException {
        Objects.requireNonNull(alquiler, "ERROR: No se puede insertar un alquiler nulo.");
        comprobarAlquiler(alquiler.getCliente(), alquiler.getTurismo(), alquiler.getFechaAlquiler());
        this.coleccionAlquileres.add(alquiler);
    }

    public void devolver(Alquiler alquiler, LocalDate fechaDevolucion) throws OperationNotSupportedException {
        Objects.requireNonNull(alquiler, "ERROR: No se puede devolver un alquiler nulo.");
        Objects.requireNonNull(fechaDevolucion, "ERROR: La fecha de devolución no puede ser nula.");
        if (!coleccionAlquileres.contains(alquiler)) {
            throw new OperationNotSupportedException("ERROR: No existe ningún alquiler igual.");
        }
        this.coleccionAlquileres.get(coleccionAlquileres.indexOf(alquiler)).devolver(fechaDevolucion);
    }

    public Alquiler buscar(Alquiler alquiler) {
        Objects.requireNonNull(alquiler, "ERROR: No se puede buscar un alquiler nulo.");
        return (coleccionAlquileres.contains(alquiler)) ? coleccionAlquileres.get(coleccionAlquileres.indexOf(alquiler)) : null;
    }

    public void borrar(Alquiler alquiler) throws OperationNotSupportedException {
        Objects.requireNonNull(alquiler, "ERROR: No se puede borrar un alquiler nulo.");
        if (!coleccionAlquileres.contains(alquiler)) {
            throw new OperationNotSupportedException("ERROR: No existe ningún alquiler igual.");
        }
        this.coleccionAlquileres.remove(coleccionAlquileres.get(coleccionAlquileres.indexOf(alquiler)));
    }
}
