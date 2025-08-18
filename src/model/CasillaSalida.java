package model;

public class CasillaSalida extends Casilla {
    private int premio;

    public CasillaSalida() {
        super("Salida", TipoCasilla.SALIDA);
        this.premio = 200;
    }

    public CasillaSalida(String nombre) {
        super(nombre, TipoCasilla.SALIDA);
        this.premio = 0;
    }

    public int getPremio() {
        return premio;
    }

    public void setPremio(int premio) {
        this.premio = premio;
    }

    @Override
    public void ejecutarAccion(Jugador jugador, Tablero tablero) {
        if (premio > 0) {
            System.out.println(jugador.getNombre() + " cobra " + premio + " por pasar por la salida.");
            jugador.setDinero(jugador.getDinero() + premio);
        } else {
            System.out.println(jugador.getNombre() + " está en " + getNombre());
        }
    }
}
