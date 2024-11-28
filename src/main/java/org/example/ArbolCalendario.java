package org.example;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * La clase ArbolCalendario implementa un árbol enario para gestionar el calendario
 * de disponibilidad de las canchas de tenis. Permite reservar y liberar horarios,
 * así como consultar la disponibilidad en fechas específicas.
 */
public class ArbolCalendario {
    private NodoCalendario raiz;
    private static final String[] HORAS_DISPONIBLES = {
            "09:00", "10:00", "11:00", "12:00", "13:00", "14:00",
            "15:00", "16:00", "17:00", "18:00", "19:00", "20:00"
    };

    /**
     * Constructor para crear una nueva instancia de ArbolCalendario.
     * Inicializa el árbol con el año actual como raíz y genera la estructura inicial.
     */
    public ArbolCalendario() {
        raiz = new NodoCalendario(String.valueOf(LocalDate.now().getYear()));
        inicializarArbol();
    }

    /**
     * Inicializa la estructura del árbol creando nodos para los próximos 12 meses,
     * sus días correspondientes y las horas disponibles para cada día.
     */
    private void inicializarArbol() {
        LocalDate fechaActual = LocalDate.now();
        for (int mes = 1; mes <= 12; mes++) {
            NodoCalendario nodoMes = new NodoCalendario(String.format("%02d", mes));
            raiz.agregarHijo(nodoMes);

            int diasEnMes = fechaActual.plusMonths(mes - 1).lengthOfMonth();
            for (int dia = 1; dia <= diasEnMes; dia++) {
                NodoCalendario nodoDia = new NodoCalendario(String.format("%02d", dia));
                nodoMes.agregarHijo(nodoDia);

                // Agregar horas disponibles
                for (String hora : HORAS_DISPONIBLES) {
                    nodoDia.agregarHijo(new NodoCalendario(hora));
                }
            }
        }
    }

    /**
     * Reserva un horario específico para una cancha.
     *
     * @param idCancha El identificador de la cancha a reservar
     * @param fecha La fecha y hora de la reserva
     * @return true si la reserva fue exitosa, false si no se pudo realizar
     */
    public boolean reservarHorario(String idCancha, LocalDateTime fecha) {
        String mes = String.format("%02d", fecha.getMonthValue());
        String dia = String.format("%02d", fecha.getDayOfMonth());
        String hora = String.format("%02d:00", fecha.getHour());

        NodoCalendario nodoMes = buscarHijo(raiz, mes);
        if (nodoMes == null) return false;

        NodoCalendario nodoDia = buscarHijo(nodoMes, dia);
        if (nodoDia == null) return false;

        NodoCalendario nodoHora = buscarHijo(nodoDia, hora);
        if (nodoHora == null || !nodoHora.isDisponible()) return false;

        nodoHora.setDisponible(false);
        nodoHora.setIdCancha(idCancha);
        return true;
    }

    /**
     * Libera un horario previamente reservado.
     *
     * @param fecha La fecha y hora a liberar
     * @return true si se liberó exitosamente, false si no se pudo liberar
     */
    public boolean liberarHorario(LocalDateTime fecha) {
        String mes = String.format("%02d", fecha.getMonthValue());
        String dia = String.format("%02d", fecha.getDayOfMonth());
        String hora = String.format("%02d:00", fecha.getHour());

        NodoCalendario nodoMes = buscarHijo(raiz, mes);
        if (nodoMes == null) return false;

        NodoCalendario nodoDia = buscarHijo(nodoMes, dia);
        if (nodoDia == null) return false;

        NodoCalendario nodoHora = buscarHijo(nodoDia, hora);
        if (nodoHora == null) return false;

        nodoHora.setDisponible(true);
        nodoHora.setIdCancha(null);
        return true;
    }

    /**
     * Busca un nodo hijo específico en un nodo padre dado.
     *
     * @param padre El nodo padre donde buscar
     * @param valor El valor del hijo a buscar
     * @return El nodo hijo encontrado, o null si no existe
     */
    private NodoCalendario buscarHijo(NodoCalendario padre, String valor) {
        for (NodoCalendario hijo : padre.getHijos()) {
            if (hijo.getValor().equals(valor)) {
                return hijo;
            }
        }
        return null;
    }

    /**
     * Obtiene una lista de todos los horarios disponibles para una fecha específica.
     *
     * @param fecha La fecha para la cual se quieren obtener los horarios disponibles
     * @return Lista de horarios disponibles en formato String
     */
    public List<String> obtenerHorariosDisponibles(LocalDate fecha) {
        List<String> horariosDisponibles = new ArrayList<>();
        String mes = String.format("%02d", fecha.getMonthValue());
        String dia = String.format("%02d", fecha.getDayOfMonth());

        NodoCalendario nodoMes = buscarHijo(raiz, mes);
        if (nodoMes == null) return horariosDisponibles;

        NodoCalendario nodoDia = buscarHijo(nodoMes, dia);
        if (nodoDia == null) return horariosDisponibles;

        for (NodoCalendario nodoHora : nodoDia.getHijos()) {
            if (nodoHora.isDisponible()) {
                horariosDisponibles.add(nodoHora.getValor());
            }
        }

        return horariosDisponibles;
    }
}
