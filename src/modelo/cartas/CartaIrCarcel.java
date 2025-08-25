package modelo.cartas;

import modelo.jugador.Jugador;
import modelo.tablero.Tablero;
import vista.DialogosJuego;

import javax.swing.*;

public class CartaIrCarcel extends CartaSorpresa {

    public CartaIrCarcel(String descripcion) {
        super(descripcion);
    }

    @Override
    public void ejecutarAccion(Jugador jugador, Tablero tablero) {
        JFrame ventana = encontrarVentanaPrincipal();

        DialogosJuego.mostrarAdvertencia("🚔 ¡A la Cárcel!",
                "🎴 Carta: " + getDescripcion() + "\n\n" +
                        "¡" + jugador.getNombre() + " va directo a la cárcel!\n" +
                        "No pasa por la salida, no cobra 200€.",
                ventana);

        jugador.setEnCarcel(true);
        jugador.setPosicion(tablero.getPosicionCarcel());
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
