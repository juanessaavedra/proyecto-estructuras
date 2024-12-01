package org.example;

import java.util.ArrayList;
import java.util.List;

/**
 * La clase Cancha representa una cancha de tenis con un identificador, estado y horarios disponibles.
 * @Autor Ana Lucelly Lizcano, Juan Esteban Saavedra, Esteban Salazar Mejía
 * Fecha: 1 de diciembre del 2024
 * Licencia: GNU GPE
 */
public class Cancha {
    private String identificador;
    private String estado;
    private List<String> horariosDisponibles;

    /**
     * Constructor para crear una nueva instancia de Cancha.
     *
     * @param identificador El identificador único de la cancha.
     * @param estado El estado actual de la cancha (por ejemplo, Disponible, Mantenimiento).
     */
    public Cancha(String identificador, String estado) {
        this.identificador = identificador;
        this.estado = estado;
        this.horariosDisponibles = new ArrayList<>();
    }

    /**
     * Obtiene el identificador de la cancha.
     *
     * @return El identificador de la cancha.
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Establece un nuevo identificador para la cancha.
     *
     * @param identificador El nuevo identificador de la cancha.
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     * Obtiene el estado actual de la cancha.
     *
     * @return El estado de la cancha.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece un nuevo estado para la cancha.
     *
     * @param estado El nuevo estado de la cancha.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Obtiene la lista de horarios disponibles para la cancha.
     *
     * @return Una lista de horarios disponibles.
     */
    public List<String> getHorariosDisponibles() {
        return horariosDisponibles;
    }

    /**
     * Agrega un nuevo horario a la lista de horarios disponibles de la cancha.
     *
     * @param horario El horario a agregar (por ejemplo, '14:00-15:00').
     */
    public void agregarHorario(String horario) {
        horariosDisponibles.add(horario);
    }

    /**
     * Elimina un horario de la lista de horarios disponibles de la cancha.
     *
     * @param horario El horario a eliminar.
     */
    public void eliminarHorario(String horario) {
        horariosDisponibles.remove(horario);
    }

    /**
     * Devuelve una representación en cadena de la cancha.
     *
     * @return Una cadena que representa la cancha.
     */
    @Override
    public String toString() {
        return "Cancha{" +
                "identificador='" + identificador + '\'' +
                ", estado='" + estado + '\'' +
                ", horariosDisponibles=" + horariosDisponibles +
                '}';
    }
}