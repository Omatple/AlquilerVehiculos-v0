package org.iesalandalus.programacion.alquilervehiculos.modelo.dominio;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Turismo {
    private static final String ER_MARCA = "[A-Z][a-z]+(:?(:? |-)?[A-Z][a-z]+)*|[A-Z]{2,}";
    private static final String ER_MATRICULA = "\\d{4}[^\\WAEIOUa-z0-9]{3}";
    private String marca;
    private String modelo;
    private int cilindrada;
    private String matricula;

    public Turismo(String marca, String modelo, int cilindrada, String matricula) {
        setMarca(marca);
        setModelo(modelo);
        setCilindrada(cilindrada);
        setMatricula(matricula);
    }

    public Turismo(Turismo turismo) {
        Objects.requireNonNull(turismo, "ERROR: No es posible copiar un turismo nulo.");
        this.marca = turismo.getMarca();
        this.modelo = turismo.getModelo();
        this.cilindrada = turismo.getCilindrada();
        this.matricula = turismo.getMatricula();
    }

    public static Turismo getTurismoConMatricula(String matricula) {
        Objects.requireNonNull(matricula, "ERROR: La matrícula no puede ser nula.");
        return new Turismo("Predeterminado", "Turismo", 1, matricula);
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public int getCilindrada() {
        return cilindrada;
    }

    public String getMatricula() {
        return matricula;
    }

    private void setMarca(String marca) {
        Objects.requireNonNull(marca, "ERROR: La marca no puede ser nula.");
        Pattern patron = Pattern.compile(ER_MARCA);
        Matcher comprarador = patron.matcher(marca);
        if (!comprarador.matches()) {
            throw new IllegalArgumentException("ERROR: La marca no tiene un formato válido.");
        }
        this.marca = marca;
    }

    private void setModelo(String modelo) {
        Objects.requireNonNull(modelo, "ERROR: El modelo no puede ser nulo.");
        if (modelo.isBlank()) {
            throw new IllegalArgumentException("ERROR: El modelo no puede estar en blanco.");
        }
        this.modelo = modelo;
    }

    private void setCilindrada(int cilindrada) {
        if (cilindrada < 1 | cilindrada > 5000) {
            throw new IllegalArgumentException("ERROR: La cilindrada no es correcta.");
        }
        this.cilindrada = cilindrada;
    }

    private void setMatricula(String matricula) {
        Objects.requireNonNull(matricula, "ERROR: La matrícula no puede ser nula.");
        Pattern patron = Pattern.compile(ER_MATRICULA);
        Matcher comparador = patron.matcher(matricula);
        if (!comparador.matches()) {
            throw new IllegalArgumentException("ERROR: La matrícula no tiene un formato válido.");
        }
        this.matricula = matricula;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Turismo turismo = (Turismo) o;
        return Objects.equals(matricula, turismo.matricula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matricula);
    }

    @Override
    public String toString() {
        return String.format("%s %s (%s cc) - %s", this.marca, this.modelo, this.cilindrada, this.matricula);
    }
}