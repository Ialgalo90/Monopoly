package controlador;

import modelo.casillas.Casilla;
import modelo.casillas.CasillaCarcel;
import modelo.casillas.CasillaPropiedad;
import modelo.jugador.Jugador;
import modelo.tablero.Tablero;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Juego {
    private Tablero tablero;
    private List<Jugador> jugadores;
    private int indiceTurno;
    private Scanner scanner;

    public Juego(){
        this.tablero = new Tablero();
        this.jugadores = new ArrayList<>();
        this.indiceTurno = 0;
        this.scanner = new Scanner(System.in);
    }

    // Método para iniciar una partida desde el MAIN
    public void iniciar() {
        System.out.println("------ Simulación de Monopoly (Modo consola) ------");
        tablero.inicializarCasillas();
        inicializarJugadores();
        tablero.setJugadores(jugadores);

        System.out.println("\nEmpieza la partida con " + jugadores.size() + " jugadores.\n");

        // Bucle principal, se repite mientras quedan más de 1 jugador
        while (!juegoTerminado()) {
            jugarTurno();
        }

        if (jugadores.size() == 1) {
            System.out.println("¡Fin de la partida! Ganador: " + jugadores.get(0).getNombre());
        } else {
            System.out.println("Partida finalizada");
        }
    }

    private void inicializarJugadores() {
        int num = 0;
        while (num < 2 || num > 6) {
        System.out.println("Introduce un número de jugadores (2-6): ");
        int line = scanner.nextInt();
        scanner.nextLine();
            try {
                num = line;
                if (num < 2 || num > 6) {
                    System.out.println("Número de jugadores no permitido. Inténtalo de nuevo");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Escribe un número");
            }
        }

        // Pedimos nombre para cada jugador y lo añadimos
        for (int i = 1; i <= num; i++) {
            System.out.println("Nombre del jugador " + i + ": ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) name = "Jugador " + i;
            Jugador jugador = new Jugador(name);
            jugadores.add(jugador);
        }
    }

    private void jugarTurno() {
        if (jugadores.isEmpty()) return;

        Jugador jugador = jugadores.get(indiceTurno);
        mostrarEstado(jugador);

        System.out.println("Pulsa Enter para lanzar los dados (o escribe 'q' + Enter para salir):");
        String entrada = scanner.nextLine().trim();
        if ("q".equalsIgnoreCase(entrada)) {
            System.out.println("Salida solicitada. Terminando la partida.");
            jugadores.clear();
            return;
        }

        // Comprobamos si el jugador está en la cárcel
        Casilla casillaActual = tablero.getCasillas().get(jugador.getPosicion());
        if (casillaActual instanceof CasillaCarcel && jugador.isEnCarcel()) {
            // Se ejecuta la acción especial de la cárcel
            casillaActual.ejecutarAccion(jugador, tablero);
            // Sólo si ya no está en la cárcel, se permite mover en este turno
            if (!jugador.isEnCarcel()) {
                if (jugador.getUltimaTirada() > 0) {
                    jugador.avanzar(jugador.getUltimaTirada(), tablero);
                    jugador.setUltimaTirada(0);
                }
            } else {
                System.out.println(jugador.getNombre() + " sigue en la cárcel y pierde el turno.");
            }
        } else {
            // Jugador no está en la cárcel y hace su turno normal
            int resultado = jugador.lanzarDados();
            jugador.avanzar(resultado, tablero);
        }

        if (jugador.getDinero() <= 0){
            declararBancarrota(jugador);

            if (indiceTurno >= jugadores.size()) {
                indiceTurno = 0;
            }
        } else {
            indiceTurno = (indiceTurno + 1) % jugadores.size();
        }
    }

    private void mostrarEstado(Jugador jugador) {
        System.out.println("-------------------------------------------------");
        System.out.println("Turno de: " + jugador.getNombre());
        System.out.println("Dinero: " + jugador.getDinero());
        System.out.println("Posición: " + jugador.getPosicion());
        System.out.println("Propiedades: " + jugador.getPropiedades().size());
    }

    private void declararBancarrota(Jugador jugador) {
        System.out.println(jugador.getNombre() + " está en bancarrota y abandona la partida.");

        // Se libera las propiedades que podría tener el jugador
        for (Casilla c : tablero.getCasillas()) {
            if (c instanceof CasillaPropiedad) {
                CasillaPropiedad p = (CasillaPropiedad) c;
                if (p.getDueno() == jugador) {
                    p.setDueno(null);
                }
            }
        }

        // Eliminamos al jugador de la lista de jugadores
        int idx = jugadores.indexOf(jugador);
        if (idx != -1) {
            jugadores.remove(idx);
        }
    }

    private boolean juegoTerminado() {
        return jugadores.size() <= 1;
    }
}
