package org.example;

import java.util.ArrayList;
import java.util.List;

/**
 * La clase NodoCalendario representa un nodo en el árbol de calendario para la gestión de horarios.
 * Cada nodo puede representar un año, mes, día u hora, y mantiene información sobre su disponibilidad.
 */
public class NodoCalendario {
    private String valor; // Puede ser año, mes, día u hora
    private List<NodoCalendario> hijos;
    private boolean disponible;
    private String idCancha; // Identificador de la cancha reservada

    /**
     * Constructor para crear una nueva instancia de NodoCalendario.
     *
     * @param valor El valor que representa este nodo (año, mes, día u hora)
     */
    public NodoCalendario(String valor) {
        this.valor = valor;
        this.hijos = new ArrayList<>();
        this.disponible = true;
        this.idCancha = null;
    }

    /**
     * Obtiene el valor almacenado en el nodo.
     *
     * @return El valor del nodo
     */
    public String getValor() {
        return valor;
    }

    /**
     * Obtiene la lista de nodos hijos.
     *
     * @return Lista de nodos hijos
     */
    public List<NodoCalendario> getHijos() {
        return hijos;
    }

    /**
     * Agrega un nuevo nodo hijo a este nodo.
     *
     * @param hijo El nodo hijo a agregar
     */
    public void agregarHijo(NodoCalendario hijo) {
        hijos.add(hijo);
    }

    /**
     * Verifica si el horario está disponible.
     *
     * @return true si está disponible, false si está ocupado
     */
    public boolean isDisponible() {
        return disponible;
    }

    /**
     * Establece la disponibilidad del horario.
     *
     * @param disponible El nuevo estado de disponibilidad
     */
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    /**
     * Obtiene el identificador de la cancha reservada.
     *
     * @return El identificador de la cancha, o null si no hay reserva
     */
    public String getIdCancha() {
        return idCancha;
    }

    /**
     * Establece el identificador de la cancha para una reserva.
     *
     * @param idCancha El identificador de la cancha a reservar
     */
    public void setIdCancha(String idCancha) {
        this.idCancha = idCancha;
    }
}
