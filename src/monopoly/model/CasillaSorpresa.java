package monopoly.model;

public class CasillaSorpresa extends Casilla {
    public CasillaSorpresa() {
        super("Sorpresa", TipoCasilla.SORPRESA);
    }

    @Override
    public void ejecutarAccion(Jugador jugador, Tablero tablero) {
    }
}