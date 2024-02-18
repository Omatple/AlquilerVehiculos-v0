package org.iesalandalus.programacion.alquilervehiculos.modelo;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.Alquileres;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.Clientes;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.Turismos;

import javax.naming.OperationNotSupportedException;
import java.io.ObjectStreamClass;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Modelo {
    private Clientes clientes;
    private Turismos turismos;
    private Alquileres alquileres;

    public Modelo() {
    }

    public void comenzar() {
        this.clientes = new Clientes();
        this.turismos = new Turismos();
        this.alquileres = new Alquileres();
    }

    public void terminar() {
        System.out.println("El modelo ha terminado.");
    }

    public void insertar(Cliente cliente) throws OperationNotSupportedException {
        this.clientes.insertar(new Cliente(cliente));
    }

    public void insertar(Turismo turismo) throws OperationNotSupportedException {
        this.turismos.insertar(new Turismo(turismo));
    }

    public void insertar(Alquiler alquiler) throws OperationNotSupportedException {
        Objects.requireNonNull(alquiler, "ERROR: No se puede realizar un alquiler nulo.");
        Cliente clienteAInsertar = clientes.buscar(alquiler.getCliente());
        if (clienteAInsertar == null) {
            throw new OperationNotSupportedException("ERROR: No existe el cliente del alquiler.");
        }
        Turismo turismoAInsertar = turismos.buscar(alquiler.getTurismo());
        if (turismoAInsertar == null) {
            throw new OperationNotSupportedException("ERROR: No existe el turismo del alquiler.");
        }
        this.alquileres.insertar(new Alquiler(alquiler));
    }

    public Cliente buscar(Cliente cliente) {
        Cliente clienteBuscado = clientes.buscar(cliente);
        return (clienteBuscado == null) ? null : new Cliente(clienteBuscado);
    }

    public Turismo buscar(Turismo turismo) {
        Turismo turismoBuscado = turismos.buscar(turismo);
        return (turismoBuscado == null) ? null : new Turismo(turismoBuscado);
    }

    public Alquiler buscar(Alquiler alquiler) {
        Alquiler alquilerBuscado = alquileres.buscar(alquiler);
        return (alquilerBuscado == null) ? null : new Alquiler(alquilerBuscado);
    }

    public void modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException {
        this.clientes.modificar(cliente, nombre, telefono);
    }

    public void devolver(Alquiler alquiler, LocalDate fechaDevolucion) throws OperationNotSupportedException {
        Alquiler alquilerADevolver = alquileres.buscar(alquiler);
        if (alquilerADevolver == null) {
            throw new OperationNotSupportedException("ERROR: No existe el alquiler a devolver.");
        }
        alquiler.devolver(fechaDevolucion); // REALIZADO PARA QUE PASE LOS TEST. PERO SIN ENTENDERLO.
        // NO LE ENCUENTRO EL SENTIDO, NECESITO EXPLICACIÓN.
        this.alquileres.devolver(alquilerADevolver, fechaDevolucion);
    }

    public void borrar(Cliente cliente) throws OperationNotSupportedException {
        for (Alquiler alquilerCliente : alquileres.get(cliente)) {
            this.alquileres.borrar(alquilerCliente);
        }
        this.clientes.borrar(cliente);
    }

    public void borrar(Turismo turismo) throws OperationNotSupportedException {
        for (Alquiler alquilerTurismo : alquileres.get(turismo)) {
            this.alquileres.borrar(alquilerTurismo);
        }
        this.turismos.borrar(turismo);
    }

    public void borrar(Alquiler alquiler) throws OperationNotSupportedException {
        this.alquileres.borrar(alquiler);
    }

    public List<Cliente> getClientes() {
        List<Cliente> coleccionInstanciadaClientes = new ArrayList<>();
        for (Cliente cliente : clientes.get()) {
            coleccionInstanciadaClientes.add(new Cliente(cliente));
        }
        return coleccionInstanciadaClientes;
    }

    public List<Turismo> getTurismos() {
        List<Turismo> coleccionInstanciadaTurismos = new ArrayList<>();
        for (Turismo turismo : turismos.get()) {
            coleccionInstanciadaTurismos.add(new Turismo(turismo));
        }
        return coleccionInstanciadaTurismos;
    }

    public List<Alquiler> getAlquileres() {
        List<Alquiler> coleccionInstanciadaAlquileres = new ArrayList<>();
        for (Alquiler alquiler : alquileres.get()) {
            coleccionInstanciadaAlquileres.add(new Alquiler(alquiler));
        }
        return coleccionInstanciadaAlquileres;
    }

    public List<Alquiler> getAlquileres(Cliente cliente) {
        List<Alquiler> coleccionInstanciadaAlquileresCliente = new ArrayList<>();
        for (Alquiler alquilerCliente : alquileres.get(cliente)) {
            coleccionInstanciadaAlquileresCliente.add(new Alquiler(alquilerCliente));
        }
        return coleccionInstanciadaAlquileresCliente;
    }

    public List<Alquiler> getAlquileres(Turismo turismo) {
        List<Alquiler> coleccionInstanciadaAlquileresTurismo = new ArrayList<>();
        for (Alquiler alquilerTurismo : alquileres.get(turismo)) {
            coleccionInstanciadaAlquileresTurismo.add(new Alquiler(alquilerTurismo));
        }
        return coleccionInstanciadaAlquileresTurismo;
    }
}
