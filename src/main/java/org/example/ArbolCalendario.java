package org.example;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
/**
 * Clase ArbolCalendario que implementa un árbol n-ario para gestionar reservas
 * de canchas de tenis.
 * @Autor Ana Lucelly Lizcano, Juan Esteban Saavedra, Esteban Salazar Mejía
 * Fecha: 1 de diciembre del 2024
 * Licencia: GNU GPE
 */
public class ArbolCalendario {
    private NodoCalendario raiz;
    private static final String[] HORAS_DISPONIBLES = {
            "09:00", "10:00", "11:00", "12:00", "13:00", "14:00",
            "15:00", "16:00", "17:00", "18:00", "19:00", "20:00"
    };
    private static final int DIAS_CERCANOS = 7; // Días visibles antes y después de la fecha actual

    /**
     * Constructor que inicializa el árbol con la fecha actual como raíz
     */
    public ArbolCalendario() {
        actualizarRaiz();
    }

    /**
     * Actualiza la raíz del árbol para reflejar la fecha actual.
     */
    public void actualizarRaiz() {
        LocalDate fechaActual = LocalDate.now();
        raiz = new NodoCalendario(fechaActual.toString());
        inicializarArbol(fechaActual);
    }

    /**
     * Inicializa la estructura del árbol centrada en la fecha actual.
     */
    private void inicializarArbol(LocalDate fechaCentral) {
        LocalDate fechaInicio = fechaCentral.minusDays(DIAS_CERCANOS);
        LocalDate fechaFin = fechaCentral.plusDays(DIAS_CERCANOS);

        for (LocalDate fecha = fechaInicio; !fecha.isAfter(fechaFin); fecha = fecha.plusDays(1)) {
            String mes = String.format("%02d", fecha.getMonthValue());
            String dia = String.format("%02d", fecha.getDayOfMonth());

            NodoCalendario nodoMes = buscarHijo(raiz, mes);
            if (nodoMes == null) {
                nodoMes = new NodoCalendario(mes);
                raiz.agregarHijo(nodoMes);
            }

            NodoCalendario nodoDia = new NodoCalendario(dia);
            nodoMes.agregarHijo(nodoDia);

            for (String hora : HORAS_DISPONIBLES) {
                nodoDia.agregarHijo(new NodoCalendario(hora));
            }
        }
    }

    /**
     * Obtiene los horarios disponibles para una fecha específica.
     */
    public List<String> obtenerHorariosDisponibles(LocalDate fecha) {
        LocalDate fechaRaiz = LocalDate.parse(raiz.getValor());
        if (fecha.isBefore(fechaRaiz.minusDays(DIAS_CERCANOS)) ||
                fecha.isAfter(fechaRaiz.plusDays(DIAS_CERCANOS))) {
            actualizarRaiz();
        }

        String mes = String.format("%02d", fecha.getMonthValue());
        String dia = String.format("%02d", fecha.getDayOfMonth());

        List<String> horariosDisponibles = new ArrayList<>();
        NodoCalendario nodoMes = buscarHijo(raiz, mes);
        if (nodoMes != null) {
            NodoCalendario nodoDia = buscarHijo(nodoMes, dia);
            if (nodoDia != null) {
                for (NodoCalendario nodoHora : nodoDia.getHijos()) {
                    if (nodoHora.isDisponible()) {
                        horariosDisponibles.add(nodoHora.getValor());
                    }
                }
            }
        }

        return horariosDisponibles;
    }

    /**
     * Reserva un horario específico para una cancha.
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
     */
    public boolean liberarHorario(String idLiberar, LocalDateTime fecha) {
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
     */
    private NodoCalendario buscarHijo(NodoCalendario padre, String valor) {
        for (NodoCalendario hijo : padre.getHijos()) {
            if (hijo.getValor().equals(valor)) {
                return hijo;
            }
        }
        return null;
    }
}

