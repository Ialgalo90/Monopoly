package monopoly.model;

public class Main {
    public static void main(String[] args) {

        Tablero tablero = new Tablero();
        tablero.inicializarCasillas();

        Jugador jugador1 = new Jugador("Ismael");
        int resultado = jugador1.lanzarDados();

        jugador1.avanzar(resultado, tablero);


    }
}
