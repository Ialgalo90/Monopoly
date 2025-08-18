package model;

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
        System.out.println(jugador.getNombre() + " debe pagar impuesto de " + cantidad);
        jugador.setDinero(jugador.getDinero() - cantidad);
    }
}
