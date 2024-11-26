package org.example;

import java.util.Scanner;

/**
 * La clase Main contiene el método principal para ejecutar el sistema de gestión de canchas de tenis.
 * Permite agregar, buscar, eliminar, mostrar y actualizar canchas, así como gestionar horarios.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TablaHashCanchas sistemaCanchas = new TablaHashCanchas();

        while (true) {
            System.out.println("\nSistema de Gestión de Canchas de Tenis");
            System.out.println("1. Agregar nueva cancha");
            System.out.println("2. Buscar cancha");
            System.out.println("3. Eliminar cancha");
            System.out.println("4. Mostrar todas las canchas");
            System.out.println("5. Actualizar estado de cancha");
            System.out.println("6. Agregar horario a cancha");
            System.out.println("7. Eliminar horario de cancha");
            System.out.println("8. Salir");
            System.out.print("Ingrese su opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir salto de línea

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el identificador de la cancha: ");
                    String id = scanner.nextLine();
                    System.out.print("Ingrese el estado de la cancha (Disponible/Mantenimiento): ");
                    String estado = scanner.nextLine();
                    Cancha nuevaCancha = new Cancha(id, estado);
                    sistemaCanchas.insertar(nuevaCancha);
                    System.out.println("¡Cancha agregada exitosamente!");
                    break;

                case 2:
                    System.out.print("Ingrese el identificador de la cancha a buscar: ");
                    String idBusqueda = scanner.nextLine();
                    Cancha canchaEncontrada = sistemaCanchas.buscar(idBusqueda);
                    if (canchaEncontrada != null) {
                        System.out.println("Cancha encontrada: " + canchaEncontrada);
                    } else {
                        System.out.println("¡Cancha no encontrada!");
                    }
                    break;

                case 3:
                    System.out.print("Ingrese el identificador de la cancha a eliminar: ");
                    String idEliminar = scanner.nextLine();
                    if (sistemaCanchas.eliminar(idEliminar)) {
                        System.out.println("¡Cancha eliminada exitosamente!");
                    } else {
                        System.out.println("¡Cancha no encontrada!");
                    }
                    break;

                case 4:
                    sistemaCanchas.mostrar();
                    break;

                case 5:
                    System.out.print("Ingrese el identificador de la cancha a actualizar: ");
                    String idActualizar = scanner.nextLine();
                    Cancha canchaActualizar = sistemaCanchas.buscar(idActualizar);
                    if (canchaActualizar != null) {
                        System.out.print("Ingrese el nuevo estado: ");
                        String nuevoEstado = scanner.nextLine();
                        canchaActualizar.setEstado(nuevoEstado);
                        System.out.println("¡Estado actualizado exitosamente!");
                    } else {
                        System.out.println("¡Cancha no encontrada!");
                    }
                    break;

                case 6:
                    System.out.print("Ingrese el identificador de la cancha: ");
                    String idAgregarHorario = scanner.nextLine();
                    Cancha canchaHorario = sistemaCanchas.buscar(idAgregarHorario);
                    if (canchaHorario != null) {
                        System.out.print("Ingrese el horario a agregar (ej: '14:00-15:00'): ");
                        String horario = scanner.nextLine();
                        canchaHorario.agregarHorario(horario);
                        System.out.println("¡Horario agregado exitosamente!");
                    } else {
                        System.out.println("¡Cancha no encontrada!");
                    }
                    break;

                case 7:
                    System.out.print("Ingrese el identificador de la cancha: ");
                    String idEliminarHorario = scanner.nextLine();
                    Cancha canchaEliminarHorario = sistemaCanchas.buscar(idEliminarHorario);
                    if (canchaEliminarHorario != null) {
                        System.out.print("Ingrese el horario a eliminar: ");
                        String horario = scanner.nextLine();
                        canchaEliminarHorario.eliminarHorario(horario);
                        System.out.println("¡Horario eliminado exitosamente!");
                    } else {
                        System.out.println("¡Cancha no encontrada!");
                    }
                    break;

                case 8:
                    System.out.println("¡Gracias por usar el Sistema de Gestión de Canchas de Tenis!");
                    scanner.close();
                    return;

                default:
                    System.out.println("¡Opción inválida! Por favor, intente nuevamente.");
            }
        }
    }
}