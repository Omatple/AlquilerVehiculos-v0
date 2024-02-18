package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Turismos {
    List<Turismo> coleccionTurismos;

    public Turismos() {
        this.coleccionTurismos = new ArrayList<>();
    }

    public List<Turismo> get() {
        return new ArrayList<>(coleccionTurismos);
    }

    public int getCantidad() {
        return coleccionTurismos.size();
    }

    public void insertar(Turismo turismo) throws OperationNotSupportedException {
        Objects.requireNonNull(turismo, "ERROR: No se puede insertar un turismo nulo.");
        if (coleccionTurismos.contains(turismo)) {
            throw new OperationNotSupportedException("ERROR: Ya existe un turismo con esa matrícula.");
        }
        this.coleccionTurismos.add(turismo);
    }

    public Turismo buscar(Turismo turismo) {
        Objects.requireNonNull(turismo, "ERROR: No se puede buscar un turismo nulo.");
        return (coleccionTurismos.contains(turismo)) ? coleccionTurismos.get(coleccionTurismos.indexOf(turismo)) : null;
    }

    public void borrar(Turismo turismo) throws OperationNotSupportedException {
        Objects.requireNonNull(turismo, "ERROR: No se puede borrar un turismo nulo.");
        if (!coleccionTurismos.contains(turismo)) {
            throw new OperationNotSupportedException("ERROR: No existe ningún turismo con esa matrícula.");
        }
        this.coleccionTurismos.remove(coleccionTurismos.get(coleccionTurismos.indexOf(turismo)));
    }
}