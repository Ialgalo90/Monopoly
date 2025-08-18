package model;

public class CartaCumpleanos extends CartaSorpresa {

    public CartaCumpleanos(String descripcion, int cantidad) {
        super("Es tu cumpleaños: cada jugador te da 20€");
    }

    @Override
    public void ejecutarAccion(Jugador jugador, Tablero tablero) {
        for (Jugador j : tablero.getJugadores()) {
            if ( j != jugador) {
                j.setDinero(jugador.getDinero() - 20);
                jugador.setDinero(jugador.getDinero() + 20);
            }
        }
    }
}
