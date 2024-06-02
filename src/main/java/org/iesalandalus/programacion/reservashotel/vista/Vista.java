package org.iesalandalus.programacion.reservashotel.vista;

import org.iesalandalus.programacion.reservashotel.controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion;
import org.iesalandalus.programacion.utilidades.Entrada;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Comparator;

import static org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva.FORMATO_FECHA_RESERVA;

public class Vista {
    private static Controlador controlador;

    public void setControlador(Controlador controlador) {
        if (controlador == null) {
            throw new NullPointerException("ERROR: No se puede asignar un controlador nulo");
        }
        Vista.controlador = controlador;
    }

    public void comenzar() {
        Opcion opcion;
        do {
            Consola.mostrarMenu();
            opcion = Consola.elegirOpcion();
            ejecutarOpcion(opcion);
        }
        while (opcion != Opcion.SALIR);
    }
    public void terminar() {
        System.out.print("¡Hasta luego! - Tarea Online 5 | Jose Javier Sierra Berdún");
    }

    private static void ejecutarOpcion(Opcion opcion) {
        switch (opcion) {
            case SALIR -> System.out.print("¡Hasta luego! - Tarea Online 4 | Jose Javier Sierra Berdún");
            case INSERTAR_HUESPED -> insertarHuesped();
            case BUSCAR_HUESPED -> buscarHuesped();
            case BORRAR_HUESPED -> borrarHuesped();
            case MOSTRAR_HUESPEDES -> mostrarHuespedes();
            case INSERTAR_HABITACION -> insertarHabitacion();
            case BUSCAR_HABITACION -> buscarHabitacion();
            case BORRAR_HABITACION -> borrarHabitacion();
            case MOSTRAR_HABITACIONES -> mostrarHabitaciones();
            case INSERTAR_RESERVA -> insertarReserva();
            case ANULAR_RESERVA -> anularReserva();
            case MOSTRAR_RESERVAS -> mostrarReservas();
            case CONSULTAR_DISPONIBILIDAD -> {
                TipoHabitacion tipoHabitacion = Consola.leerTipoHabitacion();
                LocalDate fechaInicioReserva = Consola.leerFecha("Introduzca fecha inicio reserva (" + FORMATO_FECHA_RESERVA + "):");
                LocalDate fechaFinReserva = Consola.leerFecha("Introduzca fecha inicio reserva (" + FORMATO_FECHA_RESERVA + "):");
                consultarDisponibilidad(tipoHabitacion, fechaInicioReserva, fechaFinReserva);
            }
            case REALIZAR_CHECKIN -> realizarCheckin();
            case REALIZAR_CHECKOUT -> realizarCheckout();
            default -> System.out.print("Opción no válida: " + opcion);
        }
    }

    private static void insertarHuesped() {
        try {
            Huesped huesped = Consola.leerHuesped();
            controlador.insertar(huesped);
            System.out.print("El huesped ha sido insertado.");
        } catch (IllegalArgumentException | NullPointerException | OperationNotSupportedException | DateTimeParseException e) {
            System.out.print(e.getMessage());
        }
    }

    private static void buscarHuesped() {
        try {
            Huesped huesped = Consola.leerHuespedPorDni();
            huesped = controlador.buscar(huesped);
            if (huesped != null) {
                System.out.println(huesped);
            } else {
                System.out.print("El huesped no existe");
            }
        } catch (IllegalArgumentException | NullPointerException e) {
            System.out.print(e.getMessage());
        }
    }

    private static void borrarHuesped() {
        try {
            Huesped huesped = Consola.leerHuespedPorDni();
            huesped = controlador.buscar(huesped);
            controlador.borrar(huesped);
            System.out.print("Huesped ha sido borrado");
        } catch (IllegalArgumentException | NullPointerException | OperationNotSupportedException e) {
            System.out.print(e.getMessage());
        }
    }

    private static void mostrarHuespedes() {
        try {
            if (controlador.getHuespedes().length > 0) {
                System.out.println("Estos son los huespedes existentes: ");
                System.out.println(" ");
                for (int i = 0; i < controlador.getHuespedes().length; i++) {
                    System.out.println(controlador.getHuespedes()[i].toString());
                    System.out.println(" ");
                }
            } else {
                System.out.println("No existen huéspedes ");
            }
        } catch (IllegalArgumentException | NullPointerException e) {
            System.out.print(e.getMessage());
        }
    }

    private static void insertarHabitacion() {
        try {
            Habitacion habitacion = Consola.leerHabitacion();
            controlador.insertar(habitacion);
            System.out.print("La Habitacion ha sido insertada");
        } catch (IllegalArgumentException | NullPointerException | OperationNotSupportedException e) {
            System.out.print(e.getMessage());
        }
    }

    private static void buscarHabitacion() {
        try {
            Habitacion habitacion = Consola.leerHabitacionPorIdentificador();
            habitacion = controlador.buscar(habitacion);
            if (habitacion != null) {
                System.out.println(habitacion);
            } else {
                System.out.print("La Habitación no existe");
            }
        } catch (IllegalArgumentException | NullPointerException e) {
            System.out.print(e.getMessage());
        }
    }

    private static void borrarHabitacion() {
        try {
            Habitacion habitacion = Consola.leerHabitacionPorIdentificador();
            habitacion = controlador.buscar(habitacion);
            controlador.borrar(habitacion);
            System.out.print("La Habitación ha sido borrada");
        } catch (IllegalArgumentException | NullPointerException | OperationNotSupportedException e) {
            System.out.print(e.getMessage());
        }
    }

    private static void mostrarHabitaciones() {
        try {
            if (controlador.getHabitaciones().length > 0) {
                System.out.println("Estas son las Habitaciones existentes: ");
                System.out.println(" ");
                for (int i = 0; i < controlador.getHabitaciones().length; i++) {
                    System.out.println(controlador.getHabitaciones()[i].toString());
                    System.out.println(" ");
                }
            } else {
                System.out.println("No existen habitaciones ");
            }
        } catch (IllegalArgumentException | NullPointerException e) {
            System.out.print(e.getMessage());
        }
    }

    private static void insertarReserva() {
        try {
            Reserva reserva = Consola.leerReserva();

            Huesped huespedReserva = reserva.getHuesped();
            huespedReserva = controlador.buscar(huespedReserva);
            reserva.setHuesped(huespedReserva);

            Habitacion habitacionReserva = reserva.getHabitacion();
            habitacionReserva = controlador.buscar(habitacionReserva);
            reserva.setHabitacion(habitacionReserva);

            TipoHabitacion tipoHabitacionReserva = reserva.getHabitacion().getTipoHabitacion();

            Habitacion habitacionDisponible = consultarDisponibilidad(tipoHabitacionReserva, reserva.getFechaInicioReserva(), reserva.getFechaFinReserva());

            if (habitacionDisponible != null) {
                Reserva reservaExistente = controlador.buscar(reserva);

                if (reservaExistente == null) {
                    controlador.insertar(reserva);
                    System.out.print("La reserva ha sido registrada");
                } else {
                    System.out.print("ERROR: No es posible registrar esta reserva porque ya existe otra reserva para la misma fecha y habitación seleccionada");
                }
            } else {
                System.out.println("ERROR: La habitación que intentas reservar no está disponible");
            }

        } catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException |
                 DateTimeParseException e) {
            System.out.print(e.getMessage());
        }
    }

    private static void listarReservas(Huesped huesped) {
        try {
            if (controlador.getReservas(huesped).length > 0) {
                System.out.println("Estas son las reservas para este huesped: ");
                System.out.println(" ");
                Reserva[] reservasHuesped = controlador.getReservas(huesped);
                if (reservasHuesped.length > 0) {
                    for (int i = 0; i < reservasHuesped.length; i++) {
                        System.out.println(reservasHuesped[i].toString());
                        System.out.println(" ");
                    }
                } else {
                    System.out.println("No existen reservas para este huesped");
                }

            } else {
                System.out.println("No existen reservas ");
            }
        } catch (NullPointerException | IllegalArgumentException | DateTimeParseException e) {
            System.out.print(e.getMessage());
        }
    }

    private static void listarReservas(TipoHabitacion tipoHabitacion) {
        try {
            if (controlador.getReservas(tipoHabitacion).length > 0) {
                System.out.println("Estas son las reservas para este tipo de habitación: ");
                System.out.println(" ");
                Reserva[] reservasTipo = controlador.getReservas(tipoHabitacion);
                if (reservasTipo.length > 0) {
                    for (int i = 0; i < reservasTipo.length; i++) {
                        System.out.println(reservasTipo[i].toString());
                        System.out.println(" ");
                    }
                } else {
                    System.out.println("No existen reservas para este tipo de habitación");
                }
            } else {
                System.out.println("No existen reservas ");
            }
        } catch (NullPointerException | IllegalArgumentException | DateTimeParseException e) {
            System.out.print(e.getMessage());
        }
    }

    private static Reserva[] getReservasAnulables(Reserva[] reservas) throws OperationNotSupportedException {
        Reserva[] reservasAnulables = controlador.getReservas();
        for (int i = 0; i < reservas.length; i++) {
            LocalDate fechaActual = LocalDate.now();
            if (fechaActual.isBefore(reservas[i].getFechaInicioReserva())) {
                controlador.insertar(reservasAnulables[i]);
            }
        }
        return reservasAnulables;
    }

    private static void anularReserva() {
        try {
            Huesped huesped = Consola.leerHuespedPorDni();
            huesped = controlador.buscar(huesped);
            if (huesped != null) {
                Reserva[] reservasHuesped = controlador.getReservas(huesped);
                if (reservasHuesped.length > 0) {
                    Reserva[] reservasAnulables = getReservasAnulables(reservasHuesped);
                    if (reservasAnulables.length <= 0) {
                        System.out.println("No existen reservas anulables para este huesped");
                    } else {
                        if (reservasAnulables.length > 1) {
                            int eleccion = -1;
                            do {
                                for (int j = 0; j < reservasAnulables.length; j++) {
                                    System.out.println(j + " - " + reservasAnulables[j].toString());
                                }
                                System.out.print("Escoja la reserva que desea anular: ");
                                eleccion = Entrada.entero();
                            } while (eleccion < 0 || eleccion > reservasAnulables.length);
                            controlador.borrar(reservasAnulables[eleccion]);
                        } else {
                            //Solo Existe una reserva anulable para este huesped
                            System.out.println(reservasAnulables[0].toString());
                            System.out.println("Está seguro de que desea anular esta reserva (S/N): ");
                            char respuesta = Entrada.caracter();
                            if (Character.toString(respuesta).equalsIgnoreCase("s"))
                                controlador.borrar(reservasAnulables[0]);
                        }
                    }
                } else {
                    System.out.println("No existen reservas para este huesped");
                }

            } else {
                System.out.print("El huesped no existe");
            }

        } catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException |
                 DateTimeParseException e) {
            System.out.print(e.getMessage());
        }
    }

    private static void mostrarReservas() {
        Reserva[] reservas = controlador.getReservas();
        try {
            if (reservas != null && reservas.length > 0) {
                System.out.println("Estas son las reservas existentes: ");
                System.out.println(" ");
                for (int i = 0; i < reservas.length; i++) {
                    System.out.println(reservas[i].toString());
                    System.out.println(" ");
                }
            } else {
                System.out.println("No existen reservas ");
            }
        } catch (NullPointerException | IllegalArgumentException | DateTimeParseException e) {
            System.out.print(e.getMessage());
        }
    }

    private static int getNumElementosNoNulos(Reserva[] arrayReservas) {
        int noNulos = 0;
        for (int i = 0; i < arrayReservas.length; i++) {
            if (arrayReservas[i] != null) {
                noNulos++;
            }
        }
        return noNulos;
    }

    private static Habitacion consultarDisponibilidad(TipoHabitacion tipoHabitacion, LocalDate fechaInicioReserva, LocalDate fechaFinReserva) {
        boolean tipoHabitacionEncontrada = false;
        Habitacion habitacionDisponible = null;
        int numElementos = 0;
        try {
            Habitacion[] habitacionesTipoSolicitado = controlador.getHabitaciones(tipoHabitacion);

            if (habitacionesTipoSolicitado == null)
                return habitacionDisponible;

            for (int i = 0; i < habitacionesTipoSolicitado.length && !tipoHabitacionEncontrada; i++) {

                if (habitacionesTipoSolicitado[i] != null) {
                    Reserva[] reservasFuturas = controlador.getReservasFuturas(habitacionesTipoSolicitado[i]);
                    numElementos = getNumElementosNoNulos(reservasFuturas);

                    if (numElementos == 0) {
                        habitacionDisponible = new Habitacion(habitacionesTipoSolicitado[i]);
                        tipoHabitacionEncontrada = true;
                    } else {

                        Arrays.sort(reservasFuturas, 0, numElementos, Comparator.comparing(Reserva::getFechaFinReserva).reversed());

                        if (fechaInicioReserva.isAfter(reservasFuturas[0].getFechaFinReserva())) {
                            habitacionDisponible = new Habitacion(habitacionesTipoSolicitado[i]);
                            tipoHabitacionEncontrada = true;
                        }

                        if (!tipoHabitacionEncontrada) {

                            Arrays.sort(reservasFuturas, 0, numElementos, Comparator.comparing(Reserva::getFechaInicioReserva));

                            if (fechaFinReserva.isBefore(reservasFuturas[0].getFechaInicioReserva())) {
                                habitacionDisponible = new Habitacion(habitacionesTipoSolicitado[i]);
                                tipoHabitacionEncontrada = true;
                            }
                        }

                        if (!tipoHabitacionEncontrada) {
                            for (int j = 1; j < reservasFuturas.length && !tipoHabitacionEncontrada; j++) {
                                if (reservasFuturas[j] != null && reservasFuturas[j - 1] != null) {
                                    if (fechaInicioReserva.isAfter(reservasFuturas[j - 1].getFechaFinReserva()) &&
                                            fechaFinReserva.isBefore(reservasFuturas[j].getFechaInicioReserva())) {

                                        habitacionDisponible = new Habitacion(habitacionesTipoSolicitado[i]);
                                        tipoHabitacionEncontrada = true;
                                    }
                                }
                            }
                        }


                    }
                }
            }
            if (!tipoHabitacionEncontrada) {
                System.out.println("AVISO: No hay habitaciones disponibles.");
            }
            if (tipoHabitacionEncontrada) {
                System.out.println("La siguiente habitacion está disponible: " + habitacionDisponible);
            }
        } catch (NullPointerException | IllegalArgumentException | DateTimeParseException e) {
            System.out.println(e.getMessage());
        }
        return habitacionDisponible;
    }

    private static void realizarCheckin() {
        try {
            Huesped huesped = Consola.leerHuespedPorDni();
            huesped = controlador.buscar(huesped);
            boolean checkinFallido = false;

            Reserva[] reservasDelHuesped = controlador.getReservas(huesped);

            for (int i = 0; i < reservasDelHuesped.length; i++) {

                if (reservasDelHuesped[i].getFechaInicioReserva().isEqual(LocalDate.now())) {
                    controlador.realizarCheckin(reservasDelHuesped[i], LocalDateTime.now());
                } else {
                    checkinFallido = true;
                }
            }
            if (checkinFallido) {
                System.out.println("AVISO: Hay al menos 1 reserva de la que no se ha podido hacer un Checkin al ser de un día distinto");
            }
        } catch (NullPointerException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void realizarCheckout() {
        try {
            Huesped huesped = Consola.leerHuespedPorDni();
            huesped = controlador.buscar(huesped);
            boolean checkinFallido = false;

            Reserva[] reservasDelHuesped = controlador.getReservas(huesped);

            for (int i = 0; i < reservasDelHuesped.length; i++) {

                if (reservasDelHuesped[i].getFechaFinReserva().isEqual(LocalDate.now())) {
                    controlador.realizarCheckout(reservasDelHuesped[i], LocalDateTime.now());
                } else {
                    checkinFallido = true;
                }
            }
            if (checkinFallido) {
                System.out.println("AVISO: Hay al menos 1 reserva de la que no se ha podido hacer un Checkout al ser de un día distinto");
            }
        } catch (NullPointerException | IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }
}
