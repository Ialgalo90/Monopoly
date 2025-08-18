package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CasillaSorpresa extends Casilla {
    private final List<CartaSorpresa> mazo = new ArrayList<>();
    private final Random random = new Random();

    public CasillaSorpresa() {
        super("Sorpresa", TipoCasilla.SORPRESA);
    }

    // Función agregar carta al mazo
    public void agregarCarta(CartaSorpresa carta) {
        if (carta != null) mazo.add(carta);
    }

    @Override
    public void ejecutarAccion(Jugador jugador, Tablero tablero) {
        if (mazo.isEmpty()) {
            System.out.println("No hay cartas sorpresas en esta casilla.");
            return;
        }
        CartaSorpresa carta = mazo.get(random.nextInt(mazo.size()));
        System.out.println("Has sacado una carta sorpresa: " + carta.getDescripcion());
        carta.ejecutarAccion(jugador, tablero);
    }
}