package modelo.casillas;

import modelo.jugador.Jugador;
import modelo.enums.TipoCasilla;
import modelo.tablero.Dado;
import modelo.tablero.Tablero;
import vista.DialogosJuego;

import javax.swing.*;
import java.util.Scanner;

public class CasillaCarcel extends Casilla {
    public CasillaCarcel() {
        super("Cárcel", TipoCasilla.CARCEL);
    }

    @Override
    public void ejecutarAccion(Jugador jugador, Tablero tablero) {
        // Buscar ventana principal
        JFrame ventanaPrincipal = encontrarVentanaPrincipal();

        if (jugador.isEnCarcel()) {
            manejarTurnoCarcel(jugador, ventanaPrincipal);
        } else {
            // Sólo está visitando la cárcel
            DialogosJuego.mostrarInformacion("🚔 Visitando la Cárcel",
                    jugador.getNombre() + " está solo visitando la cárcel.\n¡No hay problema!",
                    ventanaPrincipal);
        }
    }

    private void manejarTurnoCarcel(Jugador jugador, JFrame ventana) {
        //Mostrar diálogo de opciones en cárcel
        int opcion = DialogosJuego.mostrarDialogoCarcel(jugador,ventana);

        switch (opcion) {
            case 1: // Intentar con dados
                intentarSalirConDados(jugador, ventana);
                break;
            case 2: // Pagar fianza
                pagarFianza(jugador, ventana);
                break;
            default:
                // No debería llegar aquí, pero por si acaso
                DialogosJuego.mostrarAdvertencia("⚠️ Opción No Válida",
                        "No se seleccionó una opción válida. Pierdes el turno.",
                        ventana);
                jugador.incrementarTurnosEnCarcel();
                break;
        }
    }

    private void intentarSalirConDados(Jugador jugador, JFrame ventana) {
        int dado1 = Dado.lanzar();
        int dado2 = Dado.lanzar();
        int sumaDados = dado1 + dado2;

        // Mostrar resultado de los dados
        DialogosJuego.mostrarResultadoDados(jugador, dado1, dado2, ventana);

        if (dado1 == dado2) {
            // Sacó doble, sale de la cárcel
            jugador.setEnCarcel(false);
            jugador.setTurnosEnCarcel(0);
            jugador.setUltimaTirada(sumaDados);

            DialogosJuego.mostrarInformacion("🎉 ¡Libertad!",
                    "¡" + jugador.getNombre() + " sacó dobles y sale de la cárcel!\n" +
                            "Puede moverse " + sumaDados + " casillas en este turno.",
                    ventana);
        } else {
            // No sacó dobles
            jugador.incrementarTurnosEnCarcel();

            if (jugador.getTurnosEnCarcel() >= 3) {
                // Debe pagar obligatoriamente después de 3 turnos
                if (jugador.getDinero() >= 50) {
                    jugador.setDinero(jugador.getDinero() - 50);
                    jugador.setEnCarcel(false);
                    jugador.setTurnosEnCarcel(0);
                    jugador.setUltimaTirada(sumaDados);

                    DialogosJuego.mostrarTransaccion("💰 Fianza Obligatoria",
                            "Después de 3 turnos, " + jugador.getNombre() +
                                    " debe pagar la fianza y sale de la cárcel.\n" +
                                    "Puede moverse " + sumaDados + " casillas.",
                            -50, jugador, ventana);
                } else {
                    DialogosJuego.mostrarAdvertencia("💸 Sin Dinero para Fianza",
                            jugador.getNombre() + " no tiene dinero para pagar la fianza obligatoria.\n" +
                                    "¡Esto puede llevar a la bancarrota!",
                            ventana);
                }
            } else {
                DialogosJuego.mostrarInformacion("😔 Sin Suerte",
                        "No sacó dobles. " + jugador.getNombre() +
                                " permanece en la cárcel.\n" +
                                "Turnos en cárcel: " + jugador.getTurnosEnCarcel() + "/3",
                        ventana);
            }
        }
    }

    private void pagarFianza(Jugador jugador, JFrame ventana) {
        if (jugador.getDinero() >= 50) {
            jugador.setDinero(jugador.getDinero() - 50);
            jugador.setEnCarcel(false);
            jugador.setTurnosEnCarcel(0);
            jugador.setUltimaTirada(0); // No se mueve en este turno si paga

            DialogosJuego.mostrarTransaccion("💰 Fianza Pagada",
                    jugador.getNombre() + " ha pagado la fianza y sale de la cárcel.\n" +
                            "El turno termina aquí.",
                    -50, jugador, ventana);
        } else {
            DialogosJuego.mostrarAdvertencia("💸 Sin Dinero Suficiente",
                    jugador.getNombre() + " no tiene suficiente dinero para pagar la fianza.\n" +
                            "Dinero disponible: " + jugador.getDinero() + "€\n" +
                            "Fianza requerida: 50€",
                    ventana);
        }
    }

    // Método auxiliar para encontrar la ventana principal activa
    private JFrame encontrarVentanaPrincipal() {
        for (java.awt.Window window : java.awt.Window.getWindows()) {
            if (window instanceof JFrame && window.isVisible()) {
                return (JFrame) window;
            }
        }
        return null; // Si no encuentra ventana, los diálogos se mostrarán sin padre
    }
}
