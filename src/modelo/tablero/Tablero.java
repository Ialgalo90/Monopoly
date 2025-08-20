package modelo.tablero;

import modelo.cartas.CartaCobraBanco;
import modelo.cartas.CartaCumpleanos;
import modelo.cartas.CartaIrCarcel;
import modelo.casillas.*;
import modelo.jugador.Jugador;

import java.util.ArrayList;
import java.util.List;

public class Tablero {

    private List<Casilla> casillas;
    private int posicionCarcel;
    private List<Jugador> jugadores;

    public Tablero() {
        this.casillas = new ArrayList<>();
        this.posicionCarcel = 10;
    }

    public List<Casilla> getCasillas() {
        return casillas;
    }

    public void setCasillas(List<Casilla> casillas) {
        this.casillas = casillas;
    }

    public int getPosicionCarcel() {
        return posicionCarcel;
    }

    public void setPosicionCarcel(int posicionCarcel) {
        this.posicionCarcel = posicionCarcel;
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    // Método para obtener la casilla por su índice
    public Casilla getCasilla(int indice) {
        if (indice >= 0 && indice < casillas.size()) {
            return casillas.get(indice);
        }
        return null;
    }

    // Añadir una casilla al model.tablero
    public void agregarCasilla(Casilla casilla) {
        casillas.add(casilla);
    }

    // Método para inicializar Casillas
    public void inicializarCasillas() {
        // Por si se llama varias veces
        casillas.clear();

        // La Casilla Salida
        casillas.add(new CasillaSalida());

        casillas.add(new CasillaPropiedad("Mediterranean Avenue", 60, 2));

        // Casillas Sopresas 1
        CasillaSorpresa sorpresa1 = new CasillaSorpresa();
        sorpresa1.agregarCarta(new CartaCobraBanco("El banco se ha equivacado a tu favor", 200));
        sorpresa1.agregarCarta(new CartaCobraBanco("Pagas una multa de tráfico", -100));
        sorpresa1.agregarCarta(new CartaIrCarcel("Ve directamente a la cárcel."));
        sorpresa1.agregarCarta(new CartaCumpleanos("Es tu cumpleaños: cada jugador te da 20€", 20));
        casillas.add(sorpresa1);

        casillas.add(new CasillaPropiedad("Baltic Avenue", 60, 4));
        casillas.add(new CasillaImpuesto("Impuesto sobre la renta", 200));

        casillas.add(new CasillaIrCarcel());

        casillas.add(new CasillaPropiedad("Oriental Avenue", 100, 5));

        //Casillas Sorpresa 2
        CasillaSorpresa sorpresa2 = new CasillaSorpresa();
        sorpresa2.agregarCarta(new CartaCobraBanco("Cobras dividendos de acciones", 100));
        sorpresa2.agregarCarta(new CartaCobraBanco("Pagas reparación de propiedades", -50));
        casillas.add(sorpresa2);

        casillas.add(new CasillaPropiedad("Vermont Avenue", 100, 6));
        casillas.add(new CasillaPropiedad("Connecticut Avenue", 120, 8));

        casillas.add(new CasillaCarcel());

        casillas.add(new CasillaPropiedad("St. Charles Place", 140, 10));

        //Casillas Sorpresa 3
        CasillaSorpresa sorpresa3 = new CasillaSorpresa();
        sorpresa3.agregarCarta(new CartaCobraBanco("Encuentras dinero en la calle", 50));
        sorpresa3.agregarCarta(new CartaIrCarcel("Ve directamente a la cárcel."));
        casillas.add(sorpresa3);

        casillas.add(new CasillaPropiedad("States Avenue", 140, 10));
        casillas.add(new CasillaPropiedad("Virginia Avenue", 160, 12));
        casillas.add(new CasillaImpuesto("Impuesto sobre Lujo", 100));
        casillas.add(new CasillaPropiedad("St. James Place", 180, 14));

        //Casillas Sorpresa
        CasillaSorpresa sorpresa4 = new CasillaSorpresa();
        sorpresa4.agregarCarta(new CartaCobraBanco("Ganaste una apuesta", 75));
        sorpresa4.agregarCarta(new CartaCumpleanos("Es tu cumpleaños: cada jugador te da 20€", 20));

        casillas.add(new CasillaPropiedad("Tennessee Avenue", 180, 14));
        casillas.add(new CasillaPropiedad("New York Avenue", 200, 16));

        casillas.add(new CasillaSalida("Free Parking"));

    }
}
