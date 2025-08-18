package model;

public class CartaCobraBanco extends CartaSorpresa {
    private int cantidad;

    public CartaCobraBanco(String descripcion, int cantidad) {
        super(descripcion);
        this.cantidad = cantidad;
    }

    @Override
    public void ejecutarAccion(Jugador jugador, Tablero tablero) {
        jugador.setDinero(jugador.getDinero() + cantidad);
        System.out.println(jugador.getNombre() + " cobra " + cantidad + "€.");
    }
}
