package controlador;

import modelo.casillas.Casilla;
import modelo.casillas.CasillaCarcel;
import modelo.casillas.CasillaPropiedad;
import modelo.jugador.Jugador;
import modelo.tablero.Tablero;
import vista.TableroGUI;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class JuegoGUI {
    private Tablero tablero;
    private List<Jugador> jugadores;
    private int indiceTurno;
    private TableroGUI interfazGrafica;

    public JuegoGUI() {
        this.tablero = new Tablero();
        this.jugadores = new ArrayList<>();
        this.indiceTurno = 0;
    }

    public void iniciar() {
        // Se inicializa el tablero
        tablero.inicializarCasillas();

        // Se inicializa los jugadores con interfaz gráfica
        inicializarJugadoresGUI();

        if (jugadores.isEmpty()) {
            JOptionPane.showConfirmDialog(null, "No se puede inidiciar el juego sin jugadores");
            return;
        }

        // Asignamos jugadores al tablero
        tablero.setJugadores(jugadores);

        // Se crea y se muestra la interfaz gráfica
        SwingUtilities.invokeLater(() -> {
            interfazGrafica = new TableroGUI(tablero, jugadores);
            interfazGrafica.setVisible(true);
        });
    }

    private void inicializarJugadoresGUI() {
        // Se pide el número de jugadores
        String numStr = JOptionPane.showInputDialog(
                null,
                "¿Cúantos jugadores van a jugar? (2-6)",
                "Configuración del juego",
                JOptionPane.QUESTION_MESSAGE
        );

        if (numStr == null) return;

        try {
            int numJugadores = Integer.parseInt(numStr);

            if (numJugadores < 2 || numJugadores > 6) {
                JOptionPane.showMessageDialog(null, "El número de jugadores debe de estar entre 2 y 6");
                return;
            }

            // Se pide el nombre de los jugadores
            for (int i = 1; i <= numJugadores; i++) {
                String nombre = JOptionPane.showInputDialog(
                        null,
                        "Nombre del jugador " + i + ":",
                        "Jugadoir " + i
                );

                if (nombre == null ) return;
                if (nombre.trim().isEmpty()) nombre = "Jugador " + i;

                Jugador jugador = new Jugador(nombre.trim());
                jugadores.add(jugador);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Por favor, ingresa un número válido");
        }
    }

    // Método principal para ejecutar un turno (llamado desde la GUI)
    public void ejecutarTurno() {
        if (jugadores.isEmpty() || juegoTerminado()) return;

        Jugador jugadorActual = jugadores.get(indiceTurno);

        // Se verifica primero si está en la cárcel
        if (jugadorActual.isEnCarcel()) {
            manejarTurnoCarcel(jugadorActual);
        } else {
            manejarTurnoNormal(jugadorActual);
        }

        // Se verifica si está en bancarrota
        if (jugadorActual.getDinero() <= 0) {
            declararBancarrota(jugadorActual);
        }

        if (juegoTerminado()){
            if (jugadores.size() == 1) {
                mostrarGanador(jugadores.get(0));
            }
            return;
        }

        // Cambio de turno
        indiceTurno = (indiceTurno + 1) % jugadores.size();
    }

    private void manejarTurnoCarcel(Jugador jugador) {
        Casilla casillaCarcel = tablero.getCasillas().get(jugador.getPosicion());
        if (casillaCarcel instanceof CasillaCarcel) {
            casillaCarcel.ejecutarAccion(jugador, tablero);

            // Si sale de la cárcel y tiene una tirada pendiente, se mueve
            if (!jugador.isEnCarcel() && jugador.getUltimaTirada() > 0) {
                jugador.avanzar(jugador.getUltimaTirada(), tablero);
                jugador.setUltimaTirada(0);
            }
        }
    }

    private void manejarTurnoNormal(Jugador jugador) {
        int resultado = jugador.lanzarDados();
        jugador.avanzar(resultado, tablero);
    }

    // Método de bancarrota (Integrado con GUI)
    private void declararBancarrota(Jugador jugador) {
        int valorPropiedades = 0;
        int numPropiedades = jugador.getPropiedades().size();

        // Se liberan las propiedades
        for (Casilla c : tablero.getCasillas()) {
            if (c instanceof CasillaPropiedad) {
                CasillaPropiedad propiedad = (CasillaPropiedad) c;
                if (propiedad.getDueno() == jugador) {
                    valorPropiedades += propiedad.getPrecio();
                    propiedad.setDueno(null);
                    propiedad.setNumCasas(0);
                }
            }
        }

        // Mostrar el mensaje de bancarrota
        String mensaje = "💸 " + jugador.getNombre() + " está en bancarrota!\n\n" +
                "📊 Estadísticas finales:\n" +
                "💰 Dinero final: " + jugador.getDinero() + "€\n" +
                "🏠 Propiedades perdidas: " + numPropiedades + "\n" +
                "💎 Valor total perdido: " + valorPropiedades + "€";

        JOptionPane.showMessageDialog(interfazGrafica, mensaje, "Jugador Eliminado",  JOptionPane.INFORMATION_MESSAGE);

        // Se limpia todas las propiedades y se elimina el jugador
        jugador.getPropiedades().clear();
        int indiceJugador = jugadores.indexOf(jugador);

        if (indiceJugador != -1) {
            jugadores.remove(indiceJugador);

            // Se ajusta el índice de turno
            if (indiceJugador <= indiceTurno && indiceTurno > 0) {
                indiceTurno--;
            }
            if (indiceTurno >= jugadores.size()) {
                indiceTurno = 0;
            }
        }
    }

    private void mostrarGanador(Jugador ganador) {
        int valorTotal = ganador.getDinero();
        StringBuilder propiedades = new StringBuilder();

        for (CasillaPropiedad prop : ganador.getPropiedades()) {
            valorTotal +=  prop.getPrecio();
            propiedades.append("• ").append(prop.getNombre()).append("\n");
        }

        String mensaje = "🏆 ¡¡¡ FELICIDADES " + ganador.getNombre().toUpperCase() + " !!!\n\n" +
                "💰 Dinero final: " + ganador.getDinero() + "€\n" +
                "🏠 Propiedades: " + ganador.getPropiedades().size() + "\n" +
                "💎 Patrimonio total: " + valorTotal + "€\n\n" +
                "📋 Propiedades adquiridas:\n" + propiedades.toString();

        JOptionPane.showMessageDialog(interfazGrafica, mensaje, "🏆 ¡GANADOR!", JOptionPane.INFORMATION_MESSAGE);
    }

    // Getters para la GUI
    public Tablero getTablero() { return tablero; }
    public List<Jugador> getJugadores() { return jugadores; }
    public int getTurnoActual() { return indiceTurno; }
    public Jugador getJugadorActual() {
        return jugadores.isEmpty() ? null : jugadores.get(indiceTurno);
    }
    public boolean juegoTerminado() { return jugadores.size() <= 1; }

    // Método para procesar pagos (usado por las casillas)
    public boolean procesarPago(Jugador pagador, Jugador receptor, int cantidad, String concepto) {
        if (pagador.getDinero() >= cantidad) {
            pagador.setDinero(pagador.getDinero() - cantidad);
            if (receptor != null) {
                receptor.setDinero(receptor.getDinero() + cantidad);
            }

            if (interfazGrafica != null) {
                String mensaje = pagador.getNombre() + " paga" + cantidad + "€";
                if (receptor != null) {
                    mensaje += " a" + receptor.getNombre();
                } else {
                    mensaje += " al banco";
                }
                mensaje += " por " + concepto;
                interfazGrafica.agregarMensajeLog(mensaje);
            }

            return true;
        } else {
            // Notificar la falta de dinero
            if (interfazGrafica != null) {
                interfazGrafica.agregarMensajeLog("⚠️ " + pagador.getNombre() +
                        " no tiene suficiente dinero (" + pagador.getDinero() + "€)");
            }
            return false;
        }
    }
}

