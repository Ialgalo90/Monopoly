package modelo.cartas;

import modelo.jugador.Jugador;
import modelo.tablero.Tablero;

public class CartaCumpleanos extends CartaSorpresa {
    private int cantidad;

    public CartaCumpleanos(String descripcion, int cantidad) {
        super(descripcion);
        this.cantidad = cantidad;
    }

    @Override
    public void ejecutarAccion(Jugador jugador, Tablero tablero) {
        int totalCobrado = 0;

        System.out.println("🎉 ¡Es el cumpleaños de " + jugador.getNombre() + "!");

        for (Jugador j : tablero.getJugadores()) {
            if ( j != jugador) {
                if (j.getDinero() >= cantidad) {
                    j.setDinero(j.getDinero() - cantidad);
                    totalCobrado += cantidad;
                    System.out.println(j.getNombre() + " le da " + cantidad + "€ a " + jugador.getNombre());
                } else {
                    // Si no tiene suficiente dinero, da todo lo que tiene
                    int dineroDisponible = j.getDinero();
                    j.setDinero(0);
                    totalCobrado += dineroDisponible;
                    System.out.println(j.getNombre() + " solo puede dar " + dineroDisponible + "€ (todo su dinero)");
                }
            }
        }

        // El jugador del cumpleaños recibe todo el dinero recaudado
        jugador.setDinero(jugador.getDinero() + totalCobrado);
        System.out.println(jugador.getNombre() + " recibe un total de " + totalCobrado + "€ por su cumpleaños.");
    }
}
