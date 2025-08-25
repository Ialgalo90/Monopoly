package modelo.cartas;

import modelo.jugador.Jugador;
import modelo.tablero.Tablero;
import vista.DialogosJuego;

import javax.swing.*;

public class CartaCumpleanos extends CartaSorpresa {
    private int cantidad;

    public CartaCumpleanos(String descripcion, int cantidad) {
        super(descripcion);
        this.cantidad = cantidad;
    }

    @Override
    public void ejecutarAccion(Jugador jugador, Tablero tablero) {
        JFrame ventana = encontrarVentanaPrincipal();
        int totalCobrado = 0;
        StringBuilder detalles = new StringBuilder();

        detalles.append("🎉 ¡Es el cumpleaños de ").append(jugador.getNombre()).append("!\n\n");
        detalles.append("💰 Pagos recibidos:\n");

        for (Jugador j : tablero.getJugadores()) {
            if ( j != jugador) {
                if (j.getDinero() >= cantidad) {
                    j.setDinero(j.getDinero() - cantidad);
                    totalCobrado += cantidad;
                    detalles.append("• ").append(j.getNombre()).append(": ").append(cantidad).append("€\n");
                } else {
                    // Si no tiene suficiente dinero, da todo lo que tiene
                    int dineroDisponible = j.getDinero();
                    j.setDinero(0);
                    totalCobrado += dineroDisponible;
                    detalles.append("• ").append(j.getNombre()).append(": ").append(dineroDisponible).append("€ (todo su dinero)\n");
                }
            }
        }

        // El jugador del cumpleaños recibe todo el dinero recaudado
        jugador.setDinero(jugador.getDinero() + totalCobrado);

        detalles.append("\n💎 Total recibido: ").append(totalCobrado).append("€");
        detalles.append("\n💰 Nuevo saldo: ").append(jugador.getDinero()).append("€");

        DialogosJuego.mostrarInformacion("🎂 ¡Feliz Cumpleaños!",
                detalles.toString(), ventana);
    }

    private JFrame encontrarVentanaPrincipal() {
        for (java.awt.Window window : java.awt.Window.getWindows()) {
            if (window instanceof JFrame && window.isVisible()) {
                return (JFrame) window;
            }
        }
        return null;
    }
}
