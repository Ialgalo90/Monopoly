package modelo.casillas;

import modelo.jugador.Jugador;
import modelo.enums.TipoCasilla;
import modelo.tablero.Tablero;
import vista.DialogosJuego;

import javax.swing.*;

public class CasillaIrCarcel extends Casilla {
    public CasillaIrCarcel() {
       super("Ir a la cárcel", TipoCasilla.IR_CARCEL);
    }

    @Override
    public void ejecutarAccion(Jugador jugador, Tablero tablero) {
        JFrame ventana = encontrarVentanaPrincipal();

        DialogosJuego.mostrarAdvertencia("🚔 ¡Directo a la Cárcel!",
                "¡" + jugador.getNombre() + " va directo a la cárcel!\n" +
                        "No pasa por la salida, no cobra 200€.",
                ventana);

        jugador.setPosicion(tablero.getPosicionCarcel());
        jugador.setEnCarcel(true);

        // Ejecuta la acción de la cárcel inmediatamente
        CasillaCarcel carcel = (CasillaCarcel) tablero.getCasilla(tablero.getPosicionCarcel());
        carcel.ejecutarAccion(jugador, tablero);
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
