package modelo.casillas;

import modelo.jugador.Jugador;
import modelo.enums.TipoCasilla;
import modelo.tablero.Tablero;

public abstract class Casilla {

    private final String nombre;
    private final TipoCasilla tipo;

    public Casilla(String nombre, TipoCasilla tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public TipoCasilla getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return "Casilla{" +
                "nombre='" + nombre + '\'' +
                ", tipo=" + tipo +
                '}';
    }

    public abstract void ejecutarAccion(Jugador jugador, Tablero tablero);
}
