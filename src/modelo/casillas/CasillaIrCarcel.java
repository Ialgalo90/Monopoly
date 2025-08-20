package modelo.casillas;

import modelo.jugador.Jugador;
import modelo.enums.TipoCasilla;
import modelo.tablero.Tablero;

public class CasillaIrCarcel extends Casilla {
    public CasillaIrCarcel() {
       super("Ir a la cárcel", TipoCasilla.IR_CARCEL);
    }

    @Override
    public void ejecutarAccion(Jugador jugador, Tablero tablero) {
        System.out.println("¡" + jugador.getNombre() + " va directo a la cárcel!");
        jugador.setPosicion(tablero.getPosicionCarcel());
        jugador.setEnCarcel(true);

        CasillaCarcel carcel = (CasillaCarcel) tablero.getCasilla(tablero.getPosicionCarcel());
        carcel.ejecutarAccion(jugador, tablero);
    }
}
