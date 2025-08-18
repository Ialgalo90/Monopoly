package model;

import java.util.Scanner;

public class Propiedad extends Casilla {

    private int precio;
    private int rentaBase;
    private Jugador dueno;
    private int numCasas;

    public Propiedad(String nombre, int precio, int rentaBase) {
        super(nombre, TipoCasilla.PROPIEDAD);
        this.precio = precio;
        this.rentaBase = rentaBase;
        this.dueno = null;
        this.numCasas = 0;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getRentaBase() {
        return rentaBase;
    }

    public void setRentaBase(int rentaBase) {
        this.rentaBase = rentaBase;
    }

    public Jugador getDueno() {
        return dueno;
    }

    public void setDueno(Jugador dueno) {
        this.dueno = dueno;
    }

    public int getNumCasas() {
        return numCasas;
    }

    public void setNumCasas(int numCasas) {
        this.numCasas = numCasas;
    }

    @Override
    public void ejecutarAccion(Jugador jugador, Tablero tablero) {
        if (dueno == null) {
            System.out.println(jugador.getNombre() + " puede comprar " + getNombre() + " por " + getPrecio());
            System.out.println("¿Quieres comprar " +  getNombre() + " por " + getPrecio() + "? Escríbe Si o No");
            Scanner sc = new Scanner(System.in);
            String opcion = sc.nextLine();
            if (opcion.equalsIgnoreCase("Si")) {
                if (jugador.getDinero() >= getPrecio()){
                    jugador.setDinero(jugador.getDinero() - getPrecio());
                    this.dueno = jugador;
                    jugador.getPropiedades().add(this);
                    System.out.println("El jugador " + jugador.getNombre() + " ha comprado " + getNombre());
                    System.out.println("Dinero restante: " + jugador.getDinero());
                } else {
                    System.out.println("No tienes sufiente dinero para comprar esta propiedad.");
                }
            } else if (opcion.equalsIgnoreCase("No")) {
                System.out.println(jugador.getNombre() + " decidió no comprar " + getNombre());
            }
        } else if (dueno != jugador) {
            if (jugador.getDinero() >= rentaBase){
                jugador.setDinero(jugador.getDinero() - rentaBase);
                dueno.setDinero(dueno.getDinero() + rentaBase);
                System.out.println(jugador.getNombre() + " ha pagado " + rentaBase + " de renta a " + dueno.getNombre());
            } else {
                System.out.println(jugador.getNombre() + " no tiene suficiente dinero para pagar la renta.");
                // Añadir lógica de bancarrota más tarde
            }
        } else {
            System.out.println(jugador.getNombre() + " está en su propia propiedad " + getNombre());
        }
    }
}
