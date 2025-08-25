package modelo.casillas;

import modelo.jugador.Jugador;
import modelo.enums.TipoCasilla;
import modelo.tablero.Tablero;
import vista.DialogosJuego;

import javax.swing.*;

public class CasillaImpuesto extends Casilla {
   private int cantidad;

    public CasillaImpuesto(String nombre, int cantidad) {
        super("Impuesto", TipoCasilla.IMPUESTO);
        this.cantidad = cantidad;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public void ejecutarAccion(Jugador jugador, Tablero tablero){
        JFrame ventana = encontrarVentanaPrincipal();

        jugador.setDinero(jugador.getDinero() - cantidad);

        DialogosJuego.mostrarTransaccion("💸 Pago de Impuesto",
                jugador.getNombre() + " debe pagar impuesto por " + getNombre(),
                -cantidad, jugador, ventana);
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
