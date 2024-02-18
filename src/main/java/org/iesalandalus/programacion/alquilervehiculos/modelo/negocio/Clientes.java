package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Clientes {
    List<Cliente> coleccionClientes;

    public Clientes() {
        this.coleccionClientes = new ArrayList<>();
    }

    public List<Cliente> get() {
        return new ArrayList<>(coleccionClientes);
    }

    public int getCantidad() {
        return coleccionClientes.size();
    }

    public void insertar(Cliente cliente) throws OperationNotSupportedException {
        Objects.requireNonNull(cliente, "ERROR: No se puede insertar un cliente nulo.");
        if (coleccionClientes.contains(cliente)) {
            throw new OperationNotSupportedException("ERROR: Ya existe un cliente con ese DNI.");
        }
        this.coleccionClientes.add(cliente);
    }

    public Cliente buscar(Cliente cliente) {
        Objects.requireNonNull(cliente, "ERROR: No se puede buscar un cliente nulo.");
        return (coleccionClientes.contains(cliente)) ? coleccionClientes.get(coleccionClientes.indexOf(cliente)) : null;
    }

    public void borrar(Cliente cliente) throws OperationNotSupportedException {
        Objects.requireNonNull(cliente, "ERROR: No se puede borrar un cliente nulo.");
        if (!coleccionClientes.contains(cliente)) {
            throw new OperationNotSupportedException("ERROR: No existe ningún cliente con ese DNI.");
        }
        this.coleccionClientes.remove(coleccionClientes.get(coleccionClientes.indexOf(cliente)));
    }

    public void modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException {
        Objects.requireNonNull(cliente, "ERROR: No se puede modificar un cliente nulo.");
        if (!coleccionClientes.contains(cliente)) {
            throw new OperationNotSupportedException("ERROR: No existe ningún cliente con ese DNI.");
        }
        Cliente clienteAModificar = buscar(cliente);
        if (nombre != null && !nombre.isBlank()) {
            coleccionClientes.get(coleccionClientes.indexOf(clienteAModificar)).setNombre(nombre);
        }
        if (telefono != null && !telefono.isBlank()) {
            coleccionClientes.get(coleccionClientes.indexOf(clienteAModificar)).setTelefono(telefono);
        }
    }
}