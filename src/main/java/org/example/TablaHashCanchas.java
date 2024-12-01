package org.example;

/**
 * La clase TablaHashCanchas representa una tabla hash para gestionar canchas de tenis.
 * Permite insertar, buscar, eliminar y mostrar canchas.
 * @Autor Ana Lucelly Lizcano, Juan Esteban Saavedra, Esteban Salazar Mejía
 * Fecha: 1 de diciembre del 2024
 * Licencia: GNU GPE
 */
public class TablaHashCanchas {
    private static final int TAMANIO_TABLA = 10;
    private Cancha[] tabla;
    private int tamanio;

    /**
     * Constructor para crear una nueva instancia de TablaHashCanchas.
     * Inicializa la tabla hash con el tamaño definido y establece el tamaño actual a 0.
     */
    public TablaHashCanchas() {
        tabla = new Cancha[TAMANIO_TABLA];
        tamanio = 0;
    }

    /**
     * Calcula el índice hash para una clave dada.
     *
     * @param clave La clave para la cual se calculará el índice hash.
     * @return El índice hash calculado.
     */
    private int funcionHash(String clave) {
        return Math.abs(clave.hashCode() % TAMANIO_TABLA);
    }

    /**
     * Inserta una nueva cancha en la tabla hash.
     *
     * @param cancha La cancha a insertar.
     */
    public void insertar(Cancha cancha) {
        if (tamanio >= TAMANIO_TABLA) {
            System.out.println("La tabla hash está llena");
            return;
        }

        int indice = funcionHash(cancha.getIdentificador());
        while (tabla[indice] != null) {
            indice = (indice + 1) % TAMANIO_TABLA; // Sondeo lineal
        }
        tabla[indice] = cancha;
        tamanio++;
    }

    /**
     * Busca una cancha en la tabla hash por su identificador.
     *
     * @param identificador El identificador de la cancha a buscar.
     * @return La cancha encontrada, o null si no se encuentra.
     */
    public Cancha buscar(String identificador) {
        int indice = funcionHash(identificador);
        int indiceOriginal = indice;

        while (tabla[indice] != null) {
            if (tabla[indice].getIdentificador().equals(identificador)) {
                return tabla[indice];
            }
            indice = (indice + 1) % TAMANIO_TABLA;
            if (indice == indiceOriginal) break;
        }
        return null;
    }

    /**
     * Elimina una cancha de la tabla hash por su identificador.
     *
     * @param identificador El identificador de la cancha a eliminar.
     * @return true si la cancha fue eliminada exitosamente, false si no se encuentra.
     */
    public boolean eliminar(String identificador) {
        int indice = funcionHash(identificador);
        int indiceOriginal = indice;

        while (tabla[indice] != null) {
            if (tabla[indice].getIdentificador().equals(identificador)) {
                tabla[indice] = null;
                tamanio--;
                return true;
            }
            indice = (indice + 1) % TAMANIO_TABLA;
            if (indice == indiceOriginal) break;
        }
        return false;
    }

    /**
     * Muestra todas las canchas almacenadas en la tabla hash.
     */
    public void mostrar() {
        System.out.println("\nSistema de Gestión de Canchas - Estado Actual:");
        System.out.println("---------------------------------------------");
        for (int i = 0; i < TAMANIO_TABLA; i++) {
            if (tabla[i] != null) {
                System.out.println("Posición " + i + ": " + tabla[i]);
            }
        }
        System.out.println("---------------------------------------------");
    }
}