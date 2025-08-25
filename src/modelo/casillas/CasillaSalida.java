package modelo.casillas;

import modelo.jugador.Jugador;
import modelo.enums.TipoCasilla;
import modelo.tablero.Tablero;
import vista.DialogosJuego;

import javax.swing.*;

public class CasillaSalida extends Casilla {
    private int premio;

    public CasillaSalida() {
        super("Salida", TipoCasilla.SALIDA);
        this.premio = 200;
    }

    public CasillaSalida(String nombre) {
        super(nombre, TipoCasilla.SALIDA);
        this.premio = 0;
    }

    public int getPremio() {
        return premio;
    }

    public void setPremio(int premio) {
        this.premio = premio;
    }

    @Override
    public void ejecutarAccion(Jugador jugador, Tablero tablero) {
        JFrame ventana = encontrarVentanaPrincipal();

        if (premio > 0) {
            jugador.setDinero(jugador.getDinero() + premio);
            DialogosJuego.mostrarTransaccion("🎉 ¡Pasaste por la Salida!",
                    jugador.getNombre() + " cobra por pasar por la salida.",
                    premio, jugador, ventana);
        } else {
            DialogosJuego.mostrarInformacion("📍 En " + getNombre(),
                    jugador.getNombre() + " está en " + getNombre(),
                    ventana);
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
