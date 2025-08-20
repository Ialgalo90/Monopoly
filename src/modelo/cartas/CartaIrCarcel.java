package modelo.cartas;

import modelo.jugador.Jugador;
import modelo.tablero.Tablero;

public class CartaIrCarcel extends CartaSorpresa {

    public CartaIrCarcel(String descripcion) {
        super(descripcion);
    }

    @Override
    public void ejecutarAccion(Jugador jugador, Tablero tablero) {
        jugador.setEnCarcel(true);
        jugador.setPosicion(tablero.getPosicionCarcel());
    }
}
