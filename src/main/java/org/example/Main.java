package org.example;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * La clase Main contiene el método principal para ejecutar el sistema de gestión de canchas de tenis.
 * Permite agregar, buscar, eliminar, mostrar y actualizar canchas, así como gestionar horarios.
 */
public class Main {
    public static void main(String[] args) {
        // Scanner para manejar la entrada del usuario
        Scanner scanner = new Scanner(System.in);

        // Sistema de almacenamiento de canchas utilizando una tabla hash
        TablaHashCanchas sistemaCanchas = new TablaHashCanchas();

        // Sistema de calendario para manejar las reservas
        ArbolCalendario calendario = new ArbolCalendario();

        // Bucle principal del programa que muestra el menú y procesa las opciones
        while (true) {
            System.out.println("\nSistema de Gestión de Canchas de Tenis");
            System.out.println("1. Agregar nueva cancha");
            System.out.println("2. Buscar cancha");
            System.out.println("3. Eliminar cancha");
            System.out.println("4. Mostrar todas las canchas");
            System.out.println("5. Actualizar estado de cancha");
            System.out.println("6. Reservar horario");
            System.out.println("7. Liberar horario");
            System.out.println("8. Ver horarios disponibles");
            System.out.println("9. Salir");
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
                    String idCancha = scanner.nextLine();
                    Cancha cancha = sistemaCanchas.buscar(idCancha);

                    if (cancha != null && cancha.getEstado().equalsIgnoreCase("Disponible")) {
                        System.out.print("Ingrese la fecha (formato DD-MM-YYYY): ");
                        String fecha = scanner.nextLine();
                        System.out.print("Ingrese la hora (formato HH, ejemplo: 14): ");
                        int hora = scanner.nextInt();

                        try {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                            LocalDate fechaReserva = LocalDate.parse(fecha, formatter);
                            LocalDateTime fechaHora = fechaReserva.atTime(hora, 0);

                            if (calendario.reservarHorario(idCancha, fechaHora)) {
                                System.out.println("¡Horario reservado exitosamente!");
                            } else {
                                System.out.println("No se pudo realizar la reserva. Horario no disponible.");
                            }
                        } catch (Exception e) {
                            System.out.println("Error en el formato de fecha u hora.");
                        }
                    } else {
                        System.out.println("Cancha no encontrada o no disponible.");
                    }
                    break;

                case 7:
                    System.out.print("Ingrese el identificador de la cancha: ");
                    String idLiberar = scanner.nextLine();
                    System.out.print("Ingrese la fecha a liberar (formato DD-MM-YYYY): ");
                    String fechaLiberar = scanner.nextLine();
                    System.out.print("Ingrese la hora a liberar (formato HH): ");
                    int horaLiberar = scanner.nextInt();

                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                        LocalDate fechaLiberacion = LocalDate.parse(fechaLiberar, formatter);
                        LocalDateTime fechaHoraLiberar = fechaLiberacion.atTime(horaLiberar, 0);

                        if (calendario.liberarHorario(idLiberar, fechaHoraLiberar)) {
                            System.out.println("¡Horario liberado exitosamente!");
                        } else {
                            System.out.println("No se pudo liberar el horario.");
                        }
                    } catch (Exception e) {
                        System.out.println("Error en el formato de fecha u hora.");
                    }
                    break;

                case 8:
                    System.out.print("Ingrese la fecha a consultar (formato DD-MM-YYYY): ");
                    String fechaConsulta = scanner.nextLine();

                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                        LocalDate fechaDisponibilidad = LocalDate.parse(fechaConsulta, formatter);
                        calendario.obtenerHorariosDisponibles(fechaDisponibilidad);
                    } catch (Exception e) {
                        System.out.println("Error en el formato de fecha.");
                    }
                    break;

                case 9:
                    System.out.println("¡Gracias por usar el Sistema de Gestión de Canchas de Tenis!");
                    scanner.close();
                    return;

                default:
                    System.out.println("¡Opción inválida! Por favor, intente nuevamente.");
            }
        }
    }
}
