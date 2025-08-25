package modelo.casillas;

import modelo.cartas.CartaSorpresa;
import modelo.jugador.Jugador;
import modelo.enums.TipoCasilla;
import modelo.tablero.Tablero;
import vista.DialogosJuego;

import javax.swing.*;
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
        JFrame ventana = encontrarVentanaPrincipal();

        if (mazo.isEmpty()) {
            DialogosJuego.mostrarAdvertencia("🎴 Sin Cartas",
                    "No hay cartas sorpresas en esta casilla.",
                    ventana);
            return;
        }
        CartaSorpresa carta = mazo.get(random.nextInt(mazo.size()));

        // Muestra la carta sorpresa
        DialogosJuego.mostrarInformacion("🎴 ¡Carta Sorpresa!",
        "🎭 " + jugador.getNombre() + " ha sacado una carta sorpresa:\n\n" +
                "📜 " + carta.getDescripcion(),
                ventana);

        carta.ejecutarAccion(jugador, tablero);
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