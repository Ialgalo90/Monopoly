package modelo.cartas;

import modelo.jugador.Jugador;
import modelo.tablero.Tablero;
import vista.DialogosJuego;

import javax.swing.*;

public class CartaCobraBanco extends CartaSorpresa {
    private int cantidad;

    public CartaCobraBanco(String descripcion, int cantidad) {
        super(descripcion);
        this.cantidad = cantidad;
    }

    @Override
    public void ejecutarAccion(Jugador jugador, Tablero tablero) {
        JFrame ventana = encontrarVentanaPrincipal();

        jugador.setDinero(jugador.getDinero() + cantidad);

        if (cantidad > 0) {
            DialogosJuego.mostrarTransaccion("💰 ¡Dinero del Banco!",
                    getDescripcion(),
                    cantidad, jugador, ventana);
        } else {
            DialogosJuego.mostrarTransaccion("💸 Pago al Banco",
                    getDescripcion(),
                    cantidad, jugador, ventana);
        }
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
