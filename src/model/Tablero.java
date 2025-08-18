package model;

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

    // Añadir una casilla al tablero
    public void agregarCasilla(Casilla casilla) {
        casillas.add(casilla);
    }

    // Método para inicializar Casillas
    public void inicializarCasillas() {
        // Por si se llama varias veces
        casillas.clear();

        // La Casilla Salida
        casillas.add(new CasillaSalida());

        casillas.add(new Propiedad("Mediterranean Avenue", 60, 2));

        // Casillas Sopresas 1
        CasillaSorpresa sorpresa1 = new CasillaSorpresa();
        sorpresa1.agregarCarta(new CartaCobraBanco("El banco se ha equivacado a tu favor", 200));
        sorpresa1.agregarCarta(new CartaCobraBanco("Pagas una multa de tráfico", -100));
        sorpresa1.agregarCarta(new CartaIrCarcel("Ve directamente a la cárcel."));
        sorpresa1.agregarCarta(new CartaCumpleanos("Es tu cumpleaños: cada jugador te da 20€", 20));
        casillas.add(sorpresa1);

        casillas.add(new Propiedad("Baltic Avenue", 60, 4));
        casillas.add(new CasillaImpuesto("Impuesto sobre la renta", 200));

        casillas.add(new CasillaIrCarcel());

        casillas.add(new Propiedad("Oriental Avenue", 100, 5));

        //Casillas Sorpresa 2
        CasillaSorpresa sorpresa2 = new CasillaSorpresa();
        sorpresa2.agregarCarta(new CartaCobraBanco("Cobras dividendos de acciones", 100));
        sorpresa2.agregarCarta(new CartaCobraBanco("Pagas reparación de propiedades", -50));
        casillas.add(sorpresa2);

        casillas.add(new Propiedad("Vermont Avenue", 100, 6));
        casillas.add(new Propiedad("Connecticut Avenue", 120, 8));

        casillas.add(new CasillaCarcel());

        casillas.add(new Propiedad("St. Charles Place", 140, 10));

        //Casillas Sorpresa 3
        CasillaSorpresa sorpresa3 = new CasillaSorpresa();
        sorpresa3.agregarCarta(new CartaCobraBanco("Encuentras dinero en la calle", 50));
        sorpresa3.agregarCarta(new CartaIrCarcel("Ve directamente a la cárcel."));
        casillas.add(sorpresa3);

        casillas.add(new Propiedad("States Avenue", 140, 10));
        casillas.add(new Propiedad("Virginia Avenue", 160, 12));
        casillas.add(new CasillaImpuesto("Impuesto sobre Lujo", 100));
        casillas.add(new Propiedad("St. James Place", 180, 14));

        //Casillas Sorpresa
        CasillaSorpresa sorpresa4 = new CasillaSorpresa();
        sorpresa4.agregarCarta(new CartaCobraBanco("Ganaste una apuesta", 75));
        sorpresa4.agregarCarta(new CartaCumpleanos("Es tu cumpleaños: cada jugador te da 20€", 20));

        casillas.add(new Propiedad("Tennessee Avenue", 180, 14));
        casillas.add(new Propiedad("New York Avenue", 200, 16));

        casillas.add(new CasillaSalida("Free Parking"));

    }
}
