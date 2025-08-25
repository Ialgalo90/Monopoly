package app;

import controlador.Juego;
import controlador.JuegoGUI;
import modelo.jugador.Jugador;
import modelo.tablero.Tablero;
import vista.TableroGUI;

import javax.swing.*;
import java.sql.SQLOutput;

public class Main {
    public static void main(String[] args) {

        // Preguntamos al usuario que modo prefiere
        String[] options = {"\uD83C\uDFAE Interfaz Gráfica\", \"\uD83D\uDCBB Modo Consola\", \"❌ Salir"};

        int seleccion = JOptionPane.showOptionDialog(
                null,
                "¡Bienvenido a Monopoly!\n¿Cómo quieres jugar?",
                "\uD83C\uDFB2 Monopoly - Selecciona Modo de Juego",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        switch (seleccion) {
            case 0:
                iniciarModoGrafico();
                break;
            case 1:
                iniciarModoConsola();
                break;
            default:
                System.out.println("¡Gracias por jugar!");
                System.exit(0);
                break;
        }
    }

    private static void iniciarModoGrafico() {
        System.out.println("🎮 Iniciando modo gráfico...");

        SwingUtilities.invokeLater(() -> {
            // Crear tablero y configurar jugadores
           Tablero tablero = new Tablero();
           tablero.inicializarCasillas();

           // Pedir número de jugadores
            String numStr = JOptionPane.showInputDialog(
                    null,
                    "¿Cuántos jugadores van a jugar? (2-6)",
                    "Configuración del Juego",
                    JOptionPane.QUESTION_MESSAGE
            );

            if (numStr == null) {
                System.exit(0);
                return;
            }

            try {
                int numJugadores = Integer.parseInt(numStr);

                if (numJugadores < 2 || numJugadores > 6) {
                    JOptionPane.showMessageDialog(null,
                            "El número de jugadores debe estar entre 2 y 6");
                    System.exit(0);
                    return;
                }

                // Crear jugadores
                java.util.List<Jugador> jugadores = new java.util.ArrayList<>();
                for (int i = 0; i < numJugadores; i++) {
                    String nombre = JOptionPane.showInputDialog(
                            null,
                            "Nombre del jugador " + i + ":",
                            "Jugador " + i
                    );

                    if (nombre == null) {
                        System.exit(0);
                        return;
                    }

                    if (nombre.trim().isEmpty()) nombre = "Jugador " + i;
                    Jugador jugador = new Jugador(nombre.trim());
                    jugadores.add(jugador);
                }

                tablero.setJugadores(jugadores);

                // Mostrar GUI
                TableroGUI gui = new TableroGUI(tablero, jugadores);
                gui.setVisible(true);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Por favor, ingresa un número válido de jugadores");
            }
        });


    }

    private static void iniciarModoConsola() {
        System.out.println("💻 Iniciando modo consola...");

        // Usar el controlador existente
        Juego juego = new Juego();
        juego.iniciar();
    }
}
