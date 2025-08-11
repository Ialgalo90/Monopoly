package monopoly.model;

import java.util.Scanner;

public class CasillaCarcel extends Casilla {
    public CasillaCarcel() {
        super("Cárcel", TipoCasilla.CARCEL);
    }

    @Override
    public void ejecutarAccion(Jugador jugador, Tablero tablero) {
        if (jugador.isEnCarcel()) {
            System.out.println(jugador.getNombre() + " está en la carcel.");

            Scanner sc = new Scanner(System.in);

            System.out.println("Elige una opción para poder salir de la cárcel:");
            System.out.println("1. Intenta sacar dobles en los dados");
            System.out.println("2. Pagar fianza de 50€ para salir");

            int opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    int dado1 = Dado.lanzar();
                    int dado2 = Dado.lanzar();
                    System.out.println(jugador.getNombre() + " lanza los dados: "+ dado1 + " y " + dado2);
                    if (dado1 == dado2) {
                        jugador.setEnCarcel(false);
                        jugador.setTurnosEnCarcel(0);
                        System.out.println(jugador.getNombre() + " ha sacado dobles y sale de la cárcel.");

                        // El jugador avanza con la suma de los dos dados
                        int nuevaPosicion = ((jugador.getPosicion() + dado1 + dado2) % tablero.getCasillas().size());
                        jugador.setPosicion(nuevaPosicion);

                        System.out.println(jugador.getNombre() + " avanza a la casilla " + nuevaPosicion);
                    } else {
                        jugador.incrementarTurnosEnCarcel();
                        System.out.println("No sacó dobles. Turnos en cárcel: " + jugador.getTurnosEnCarcel());

                        if (jugador.getTurnosEnCarcel() >= 3) {
                            if (jugador.getDinero() >= 50) {
                                jugador.setDinero(jugador.getDinero() - 50);
                                jugador.setEnCarcel(false);
                                jugador.setTurnosEnCarcel(0);
                                System.out.println(jugador.getNombre() + " ha pagado la fianza después de 3 turnos y sale de la cárcel.");
                            } else {
                                System.out.println(jugador.getNombre() + " no tiene sufiente dinero para pagar la fianza");
                            }
                        }
                    }
                    break;
                case 2:
                        if (jugador.getDinero() >= 50) {
                            jugador.setDinero(jugador.getDinero() - 50);
                            jugador.setEnCarcel(false);
                            jugador.setTurnosEnCarcel(0);
                            System.out.println(jugador.getNombre() + " ha pagado la fianza y sale de la cárcel.");
                            System.out.println(jugador.getNombre() + " ahora tiene " + jugador.getDinero() + " €");
                        } else {
                            System.out.println(jugador.getNombre() + " no tiene sufiente dinero para pagar la fianza");
                        }
                    break;
                default:
                    System.out.println("Opción no válida. Pierdes el turno.");
                    jugador.incrementarTurnosEnCarcel();
                    break;
            }
        } else {
            System.out.println(jugador.getNombre() + " solo está visitando la cárcel.");
        }
    }
}
