package org.iesalandalus.programacion.alquilervehiculos.vista;

import org.iesalandalus.programacion.alquilervehiculos.controlador.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;

import javax.naming.OperationNotSupportedException;
import java.util.IllformedLocaleException;
import java.util.List;
import java.util.Objects;

public class Vista {
    private Controlador controlador;

    public void setControlador(Controlador controlador) {
        Objects.requireNonNull(controlador, "ERROR: El controlador no puede ser nulo.");
        this.controlador = controlador;
    }

    public void comenzar() {
        Consola.mostrarMenu();
        Opcion opcionElegida = Consola.elegirOpcion();
        ejecutar(opcionElegida);
        while (!opcionElegida.equals(Opcion.SALIR)) {
            Consola.mostrarMenu();
            opcionElegida = Consola.elegirOpcion();
            ejecutar(opcionElegida);
        }
        controlador.terminar();
    }

    public void terminar() {
        System.out.println("¡Gracias por utilizar nuestra aplicación! Esperamos haber hecho tu experiencia más fácil y agradable. ¡Hasta pronto!");
    }

    private void ejecutar(Opcion opcion) {
        switch (opcion) {
            case INSERTAR_CLIENTE -> insertarCliente();
            case INSERTAR_TURISMO -> insertarTurismo();
            case INSERTAR_ALQUILER -> insertarAlquiler();
            case BUSCAR_CLIENTE -> buscarCliente();
            case BUSCAR_TURISMO -> buscarTurismo();
            case BUSCAR_ALQUILER -> buscarAlquiler();
            case MODIFICAR_CLIENTE -> modificarCliente();
            case DEVOLVER_ALQUILER -> devolverAlquiler();
            case BORRAR_CLIENTE -> borrarCliente();
            case BORRAR_TURISMO -> borrarTurismo();
            case BORRAR_ALQUILER -> borrarAlquiler();
            case LISTAR_CLIENTES -> listarClientes();
            case LISTAR_TURISMOS -> listarTurismos();
            case LISTAR_ALQUILERES -> listarAlquileres();
            case LISTAR_ALQUILERES_CLIENTE -> listarAlquileresCliente();
            case LISTAR_ALQUILERES_TURISMO -> listarAlquileresTurismo();
        }
    }

    private void insertarCliente() {
        Consola.mostrarCabezera("INSERCIÓN DE CLIENTE ACTIVADA");
        String mensajeCliente = null;
        boolean saltaExcepcion = true;
        while (saltaExcepcion) {
            try {
                Cliente clienteAInsertar = Consola.leerCliente();
                if (controlador.buscar(clienteAInsertar) != null) {
                    mensajeCliente = "El cliente ya ha sido registrado anteriormente. Por favor, verifica los detalles e inténtalo de nuevo.";
                } else {
                    controlador.insertar(clienteAInsertar);
                    mensajeCliente = String.format("¡Cliente insertado exitosamente en el sistema! A continuación se muestran los detalles: %s", controlador.buscar(clienteAInsertar));
                }
                saltaExcepcion = false;
            } catch (OperationNotSupportedException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(mensajeCliente);
    }

    private void insertarTurismo() {
        Consola.mostrarCabezera("INSERCIÓN DE TURISMO ACTIVADA");
        String mensajeTurismo = null;
        boolean saltaExcepcion = true;
        while (saltaExcepcion) {
            try {
                Turismo turismoAInsertar = Consola.leerTurismo();
                if (controlador.buscar(turismoAInsertar) != null) {
                    mensajeTurismo = "El turismo ya ha sido registrado anteriormente. Por favor, verifica los detalles e inténtalo de nuevo.";
                } else {
                    controlador.insertar(turismoAInsertar);
                    mensajeTurismo = String.format("¡Turismo insertado exitosamente en el sistema! A continuación se muestran los detalles: %s", controlador.buscar(turismoAInsertar));
                }
                saltaExcepcion = false;
            } catch (OperationNotSupportedException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(mensajeTurismo);
    }

    private void insertarAlquiler() {
        Consola.mostrarCabezera("INSERCIÓN DE ALQUILER ACTIVADA");
        String mensajeAlquiler = null;
        boolean saltaExcepcion = true;
        while (saltaExcepcion) {
            try {
                Alquiler alquilerAInsertar = Consola.leerAlquiler();
                Cliente clienteAlquiler = controlador.buscar(alquilerAInsertar.getCliente());
                Turismo turismoAlquiler = controlador.buscar(alquilerAInsertar.getTurismo());
                if (clienteAlquiler == null) {
                    mensajeAlquiler = "No se puede insertar el alquiler porque el cliente especificado no existe en la base de datos. Por favor, registra al cliente primero y luego intenta nuevamente.";
                } else if (turismoAlquiler == null) {
                    mensajeAlquiler = "No se puede insertar el alquiler porque el turismo especificado no existe en la base de datos. Por favor, registra al turismo primero y luego intenta nuevamente.";
                } else {
                    if (controlador.buscar(alquilerAInsertar) != null) {
                        mensajeAlquiler = "El alquiler ya ha sido registrado anteriormente. Por favor, verifica los detalles e inténtalo de nuevo.";
                    } else {
                        Alquiler alquilerInsertado = new Alquiler(clienteAlquiler, turismoAlquiler, alquilerAInsertar.getFechaAlquiler());
                        controlador.insertar(alquilerInsertado);
                        mensajeAlquiler = String.format("¡Alquiler insertado exitosamente en el sistema! A continuación se muestran los detalles: %s", controlador.buscar(alquilerAInsertar));
                    }
                }
                saltaExcepcion = false;
            } catch (OperationNotSupportedException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(mensajeAlquiler);
    }

    private void buscarCliente() {
        Consola.mostrarCabezera("BÚSQUEDA DE CLIENTE ACTIVADA");
        boolean saltaExcepcion = true;
        Cliente clienteBuscado = null;
        while (saltaExcepcion) {
            try {
                clienteBuscado = controlador.buscar(Consola.leerClienteDni());
                saltaExcepcion = false;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        String mensajeCliente = (clienteBuscado == null) ? "No se ha encontrado ningún cliente con esa información. Por favor, inténtalo de nuevo." : String.format("¡Cliente encontrado! A continuación se muestran los detalles: %s", clienteBuscado);
        System.out.println(mensajeCliente);
    }

    private void buscarTurismo() {
        Consola.mostrarCabezera("BÚSQUEDA DE TURISMO ACTIVADA");
        boolean saltaExcepcion = true;
        Turismo turismoBuscado = null;
        while (saltaExcepcion) {
            try {
                turismoBuscado = controlador.buscar(Consola.leerTurismoMatricula());
                saltaExcepcion = false;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        String mensajeTurismo = (turismoBuscado == null) ? "No se ha encontrado ningún turismo con esa información. Por favor, inténtalo de nuevo." : String.format("¡Turismo encontrado! A continuación se muestran los detalles: %s", turismoBuscado);
        System.out.println(mensajeTurismo);
    }

    private void buscarAlquiler() {
        Consola.mostrarCabezera("BÚSQUEDA DE ALQUILER ACTIVADA");
        boolean saltaExcepcion = true;
        Alquiler alquilerBuscado = null;
        while (saltaExcepcion) {
            try {
                alquilerBuscado = controlador.buscar(Consola.leerAlquiler());
                saltaExcepcion = false;
            } catch (IllformedLocaleException e) {
                System.out.println(e.getMessage());
            }
        }
        String mensajeAlquiler = (alquilerBuscado == null) ? "No se ha encontrado ningún alquiler con esa información. Por favor, inténtalo de nuevo." : String.format("¡Alquiler encontrado! A continuación se muestran los detalles: %s", alquilerBuscado);
        System.out.println(mensajeAlquiler);
    }

    private void modificarCliente() {
        Consola.mostrarCabezera("MODIFICACIÓN DE CLIENTE ACTIVADA");
        boolean saltaExcepcion = true;
        String mensajeCliente = null;
        while (saltaExcepcion) {
            try {
                Cliente clienteAModificar = controlador.buscar(Consola.leerClienteDni());
                if (clienteAModificar == null) {
                    mensajeCliente = "No se ha encontrado ningún cliente con esa información. Por favor, inténtalo de nuevo.";
                } else {
                    boolean haSidoModificado = false;
                    String nombreCliente = Consola.leerNombre();
                    String telefonoCliente = Consola.leerTelefono();
                    if (nombreCliente != null && !nombreCliente.isBlank()) {
                        haSidoModificado = true;
                    } else if (telefonoCliente != null && !telefonoCliente.isBlank()) {
                        haSidoModificado = true;
                    }
                    controlador.modificar(clienteAModificar, nombreCliente, telefonoCliente);
                    mensajeCliente = (haSidoModificado) ? String.format("¡Cliente modificado exitosamente! A continuación se muestran los detalles: %s", controlador.buscar(clienteAModificar)) : "Lo siento, no se ha podido modificar al cliente. Por favor, verifica los detalles e inténtalo de nuevo.";
                }
                saltaExcepcion = false;
            } catch (OperationNotSupportedException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(mensajeCliente);
    }

    private void devolverAlquiler() {
        Consola.mostrarCabezera("DEVOLUCIÓN DE ALQUILER ACTIVADA");
        boolean saltaExcepcion = true;
        String mensajeAlquiler = null;
        boolean yaTieneFechaDevolucion = true;
        Alquiler alquilerADevolver = null;
        while (saltaExcepcion) {
            try {
                List<Alquiler> alquileresTurismo = controlador.getAlquileres(Consola.leerTurismoMatricula());
                if (alquileresTurismo.isEmpty()) {
                    mensajeAlquiler = "No se ha encontrado ningún alquiler con esa información. Por favor, inténtalo de nuevo.";
                } else {
                    for (Alquiler alquilerTurismo : alquileresTurismo) {
                        if (alquilerTurismo.getFechaDevolucion() == null) {
                            yaTieneFechaDevolucion = false;
                            alquilerADevolver = alquilerTurismo;
                        }
                    }
                    if (yaTieneFechaDevolucion) {
                        mensajeAlquiler = "La devolución del alquiler ya ha sido registrada anteriormente.";
                    }
                }
                saltaExcepcion = false;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
            if (!yaTieneFechaDevolucion) {
                saltaExcepcion = true;
                while (saltaExcepcion) {
                    try {
                        controlador.devolver(alquilerADevolver, Consola.leerFechaDevolucion());
                        mensajeAlquiler = String.format("¡Alquiler devuelto con éxito! A continuación se muestran los detalles: %s", controlador.buscar(alquilerADevolver));
                        saltaExcepcion = false;
                    } catch (OperationNotSupportedException | IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
        System.out.println(mensajeAlquiler);
    }

    private void borrarCliente() {
        Consola.mostrarCabezera("ELIMINACIÓN DE CLIENTE ACTIVADA");
        String mensajeCliente = null;
        boolean saltaExcepcion = true;
        while (saltaExcepcion) {
            try {
                Cliente clienteABorrar = controlador.buscar(Consola.leerClienteDni());
                if (clienteABorrar == null) {
                    mensajeCliente = "No se ha encontrado ningún cliente con esa información. Por favor, inténtalo de nuevo.";
                } else {
                    controlador.borrar(clienteABorrar);
                    mensajeCliente = "¡Cliente eliminado exitosamente del sistema! Todos los alquileres asociados a este cliente también han sido eliminados.";
                }
                saltaExcepcion = false;
            } catch (OperationNotSupportedException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(mensajeCliente);
    }

    private void borrarTurismo() {
        Consola.mostrarCabezera("ELIMINACIÓN DE TURISMO ACTIVADA");
        String mensajeTurismo = null;
        boolean saltaExcepcion = true;
        while (saltaExcepcion) {
            try {
                Turismo turismoABorrar = controlador.buscar(Consola.leerTurismoMatricula());
                if (turismoABorrar == null) {
                    mensajeTurismo = "No se ha encontrado ningún turismo con esa información. Por favor, inténtalo de nuevo.";
                } else {
                    controlador.borrar(turismoABorrar);
                    mensajeTurismo = "¡Turismo eliminado exitosamente del sistema! Todos los alquileres asociados a este cliente también han sido eliminados.";
                }
                saltaExcepcion = false;
            } catch (OperationNotSupportedException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(mensajeTurismo);
    }

    private void borrarAlquiler() {
        Consola.mostrarCabezera("ELIMINACIÓN DE ALQUILER ACTIVADA");
        String mensajeAlquiler = null;
        boolean saltaExcepcion = true;
        while (saltaExcepcion) {
            try {
                Alquiler alquilerABorrar = controlador.buscar(Consola.leerAlquiler());
                if (alquilerABorrar == null) {
                    mensajeAlquiler = "No se ha encontrado ningún alquiler con esa información. Por favor, inténtalo de nuevo.";
                } else {
                    controlador.borrar(alquilerABorrar);
                    mensajeAlquiler = "¡Alquiler eliminado exitosamente del sistema!";
                }
                saltaExcepcion = false;
            } catch (OperationNotSupportedException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(mensajeAlquiler);
    }

    public void listarClientes() {
        Consola.mostrarCabezera("LISTADO DE CLIENTES ACTIVADA");
        List<Cliente> clientes = controlador.getClientes();
        StringBuilder mensajeCliente = new StringBuilder();
        if (clientes.isEmpty()) {
            mensajeCliente.append("No se encontraron clientes en la lista. ¡Registra un nuevo cliente para comenzar!  ");
        } else {
            int indiceLista = 1;
            mensajeCliente.append("Mostrando lista de clientes disponibles: \n");
            for (Cliente cliente : clientes) {
                mensajeCliente.append(String.format("%s. %s%n", indiceLista++, cliente));
            }
        }
        mensajeCliente.replace(mensajeCliente.length() - 2, mensajeCliente.length(), "");
        System.out.println(mensajeCliente);
    }

    public void listarTurismos() {
        Consola.mostrarCabezera("LISTADO DE TURISMOS ACTIVADA");
        List<Turismo> turismos = controlador.getTurismos();
        StringBuilder mensajeTurismo = new StringBuilder();
        if (turismos.isEmpty()) {
            mensajeTurismo.append("No se encontraron turismos en la lista. ¡Registra un nuevo turismo para comenzar!  ");
        } else {
            int indiceLista = 1;
            mensajeTurismo.append("Mostrando lista de turismos disponibles: \n");
            for (Turismo turismo : turismos) {
                mensajeTurismo.append(String.format("%s. %s%n", indiceLista++, turismo));
            }
        }
        mensajeTurismo.replace(mensajeTurismo.length() - 2, mensajeTurismo.length(), "");
        System.out.println(mensajeTurismo);
    }

    public void listarAlquileres() {
        Consola.mostrarCabezera("LISTADO DE ALQUILERES ACTIVADA");
        List<Alquiler> alquileres = controlador.getAlquileres();
        StringBuilder mensajeAlquileres = new StringBuilder();
        if (alquileres.isEmpty()) {
            mensajeAlquileres.append("No se encontraron alquileres en la lista. ¡Registra un nuevo alquiler para comenzar!  ");
        } else {
            int indiceLista = 1;
            mensajeAlquileres.append("Mostrando lista de alquileres disponibles: \n");
            for (Alquiler alquiler : alquileres) {
                mensajeAlquileres.append(String.format("%s. %s%n", indiceLista++, alquiler));
            }
        }
        mensajeAlquileres.replace(mensajeAlquileres.length() - 2, mensajeAlquileres.length(), "");
        System.out.println(mensajeAlquileres);
    }

    public void listarAlquileresCliente() {
        Consola.mostrarCabezera("LISTADO DE ALQUILERES POR CLIENTE ACTIVADA");
        System.out.print("Introduce el DNI del cliente que deseas el listado de sus alquileres: ");
        boolean saltaExcepcion = true;
        List<Alquiler> alquileresCliente = null;
        while (saltaExcepcion) {
            try {
                alquileresCliente = controlador.getAlquileres(Consola.leerClienteDni());
                saltaExcepcion = false;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        StringBuilder mensajeAlquileresCliente = new StringBuilder();
        if (alquileresCliente.isEmpty()) {
            mensajeAlquileresCliente.append("No se encontraron alquileres asociados a este cliente en la lista. ¡Asegúrate de haber registrado al menos un alquiler para este cliente!  ");
        } else {
            int indiceLista = 1;
            mensajeAlquileresCliente.append("Mostrando lista de alquileres asociados a este cliente disponibles: \n");
            for (Alquiler alquilerCliente : alquileresCliente) {
                mensajeAlquileresCliente.append(String.format("%s. %s%n", indiceLista++, alquilerCliente));
            }
        }
        mensajeAlquileresCliente.replace(mensajeAlquileresCliente.length() - 2, mensajeAlquileresCliente.length(), "");
        System.out.println(mensajeAlquileresCliente);
    }

    public void listarAlquileresTurismo() {
        Consola.mostrarCabezera("LISTADO DE ALQUILERES POR TURISMO ACTIVADA");
        System.out.print("Introduce la matrícula del turismo que deseas el listado de sus alquileres: ");
        boolean saltaExcepcion = true;
        List<Alquiler> alquileresTurismo = null;
        while (saltaExcepcion) {
            try {
                alquileresTurismo = controlador.getAlquileres(Consola.leerTurismoMatricula());
                saltaExcepcion = false;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        StringBuilder mensajeAlquileresTurismo = new StringBuilder();
        if (alquileresTurismo.isEmpty()) {
            mensajeAlquileresTurismo.append("No se encontraron alquileres asociados a este turismo en la lista. ¡Asegúrate de haber registrado al menos un alquiler para este turismo!  ");
        } else {
            int indiceLista = 1;
            mensajeAlquileresTurismo.append("Mostrando lista de alquileres asociados a este turismo disponibles: \n");
            for (Alquiler alquilerTurismo : alquileresTurismo) {
                mensajeAlquileresTurismo.append(String.format("%s. %s%n", indiceLista++, alquilerTurismo));
            }
        }
        mensajeAlquileresTurismo.replace(mensajeAlquileresTurismo.length() - 2, mensajeAlquileresTurismo.length(), "");
        System.out.println(mensajeAlquileresTurismo);
    }
}
