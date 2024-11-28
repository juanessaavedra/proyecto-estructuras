package org.example;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

/**
 * La clase Main contiene el método principal para ejecutar el sistema de gestión de canchas de tenis.
 * Permite agregar, buscar, eliminar, mostrar y actualizar canchas, así como gestionar horarios.
 */
public class Main {
    public static void main(String[] args) {
        /**
         * Scanner para manejar la entrada del usuario
         */
        Scanner scanner = new Scanner(System.in);

        /**
         * Sistema de almacenamiento de canchas utilizando una tabla hash
         */
        TablaHashCanchas sistemaCanchas = new TablaHashCanchas();

        /**
         * Sistema de calendario para manejar las reservas
         */
        ArbolCalendario calendario = new ArbolCalendario();

        /**
         * Bucle principal del programa que muestra el menú y procesa las opciones
         */

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
                    /**
                     * Opción para realizar una reserva de cancha
                     * Proceso:
                     * 1. Solicita y valida el identificador de la cancha
                     * 2. Verifica la disponibilidad de la cancha
                     * 3. Solicita y procesa la fecha y hora de la reserva
                     * 4. Registra la reserva en el sistema si es posible
                     */
                    System.out.print("Ingrese el identificador de la cancha: ");
                    String idCancha = scanner.nextLine();

                    /**
                     * Busca la cancha en el sistema y verifica su disponibilidad
                     */
                    Cancha cancha = sistemaCanchas.buscar(idCancha);

                    if (cancha != null && cancha.getEstado().equalsIgnoreCase("Disponible")) {
                        // Solicitud de fecha y hora para la reserva
                        System.out.print("Ingrese la fecha (formato DD-MM-YYYY): ");
                        String fecha = scanner.nextLine();
                        System.out.print("Ingrese la hora (formato HH, ejemplo: 14): ");
                        int hora = scanner.nextInt();

                        /**
                         * Procesa la reserva con manejo de excepciones para formato de fecha/hora
                         */
                        try {
                            // Configuración del formateador de fecha
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                            // Parseo de la fecha ingresada
                            LocalDate fechaReserva = LocalDate.parse(fecha, formatter);
                            // Creación del objeto DateTime completo
                            LocalDateTime fechaHora = fechaReserva.atTime(hora, 0);

                            // Intento de reserva
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
                    /**
                     * Opción para liberar un horario previamente reservado
                     * Proceso:
                     * 1. Solicita la fecha y hora a liberar
                     * 2. Valida el formato de los datos ingresados
                     * 3. Intenta liberar el horario en el sistema
                     */
                    System.out.print("Ingrese la fecha a liberar (formato DD-MM-YYYY): ");
                    String fechaLiberar = scanner.nextLine();
                    System.out.print("Ingrese la hora a liberar (formato HH): ");
                    int horaLiberar = scanner.nextInt();

                    /**
                     * Procesa la liberación del horario con manejo de excepciones
                     */
                    try {
                        // Configuración del formateador de fecha
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                        // Parseo de la fecha ingresada
                        LocalDate fechaLiberacion = LocalDate.parse(fechaLiberar, formatter);
                        // Creación del objeto DateTime completo
                        LocalDateTime fechaHoraLiberar = fechaLiberacion.atTime(horaLiberar, 0);

                        // Intento de liberación del horario
                        if (calendario.liberarHorario(fechaHoraLiberar)) {
                            System.out.println("¡Horario liberado exitosamente!");
                        } else {
                            System.out.println("No se pudo liberar el horario.");
                        }
                    } catch (Exception e) {
                        System.out.println("Error en el formato de fecha u hora.");
                    }
                    break;

                case 8:
                    /**
                     * Opción para consultar horarios disponibles en una fecha específica
                     * Proceso:
                     * 1. Solicita la fecha a consultar
                     * 2. Obtiene y muestra los horarios disponibles
                     */
                    System.out.print("Ingrese la fecha a consultar (formato DD-MM-YYYY): ");
                    String fechaConsulta = scanner.nextLine();

                    /**
                     * Procesa la consulta de disponibilidad con manejo de excepciones
                     */
                    try {
                        // Configuración del formateador de fecha
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                        // Parseo de la fecha ingresada
                        LocalDate fechaDisponibilidad = LocalDate.parse(fechaConsulta, formatter);
                        // Obtención de la lista de horarios disponibles
                        List<String> horariosDisponibles = calendario.obtenerHorariosDisponibles(fechaDisponibilidad);

                        // Muestra los resultados
                        System.out.println("\nHorarios disponibles para " + fechaConsulta + ":");
                        if (horariosDisponibles.isEmpty()) {
                            System.out.println("No hay horarios disponibles para esta fecha.");
                        } else {
                            for (String horario : horariosDisponibles) {
                                System.out.println("- " + horario);
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Error en el formato de fecha.");
                    }
                    break;

                case 9:
                    /**
                     * Opción para terminar la ejecución del programa
                     * Realiza una salida limpia cerrando los recursos utilizados
                     */
                    System.out.println("¡Gracias por usar el Sistema de Gestión de Canchas de Tenis!");
                    scanner.close();
                    return;

                default:
                    /**
                     * Manejo de opciones inválidas
                     * Muestra un mensaje de error y permite al usuario intentar nuevamente
                     */
                    System.out.println("¡Opción inválida! Por favor, intente nuevamente.");
            }
        }
    }
}