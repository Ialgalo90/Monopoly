package modelo.casillas;

import controlador.Juego;
import modelo.jugador.Jugador;
import modelo.enums.TipoCasilla;
import modelo.tablero.Tablero;
import vista.DialogosJuego;

import javax.swing.*;
import java.util.Scanner;

public class CasillaPropiedad extends Casilla {

    private int precio;
    private int rentaBase;
    private Jugador dueno;
    private int numCasas;

    public CasillaPropiedad(String nombre, int precio, int rentaBase) {
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
        // Busca la ventana activa para mostrar diálogos
        JFrame ventanaPrincipal = encontrarVentanaPrincipal();

        if (dueno == null) {
            // Propiedad disponible para comprar
            manejarCompra(jugador, ventanaPrincipal);
        } else if (dueno != jugador) {
            //Jugador debe pagar Renta
            manejarPagoRenta(jugador, ventanaPrincipal);
        } else {
            // Es propiedad del jugador actual
            DialogosJuego.mostrarInformacion("🏠 Tu Propiedad",
                    jugador.getNombre() + " está en su propia propiedad: " + getNombre(),
                    ventanaPrincipal);
        }
    }

    private void manejarCompra(Jugador jugador, JFrame ventana) {
        if (jugador.getDinero() >= precio) {
            // Muestra el diálogo de compra
            boolean quiereComprar = DialogosJuego.mostrarDialogoCompra(jugador, this, ventana);

            if (quiereComprar) {
                jugador.setDinero(jugador.getDinero() - precio);
                this.dueno = jugador;
                jugador.getPropiedades().add(this);

                DialogosJuego.mostrarTransaccion("✅ Compra Realizada",
                        jugador.getNombre() + " ha comprado " + getNombre(),
                        -precio, jugador, ventana);
            } else {
                DialogosJuego.mostrarInformacion("❌ Compra Rechazada",
                        jugador.getNombre() + " decidió no comprar " + getNombre(),
                        ventana);
            }
        } else {
            // No tiene suficiente dinero
            DialogosJuego.mostrarAdvertencia("💸 Sin Dinero Suficiente",
                    "No tienes suficiente dinero para comprar " + getNombre() + "\n\n" +
                            "Precio: " + precio + "€\n" +
                            "Tu dinero: " + jugador.getDinero() + "€",
                    ventana);

        }
    }

    private void manejarPagoRenta(Jugador inquilino, JFrame ventana) {
        if (inquilino.getDinero() >= rentaBase) {
            // Puede pagar la renta
            inquilino.setDinero(inquilino.getDinero() - rentaBase);
            dueno.setDinero(dueno.getDinero() + rentaBase);

            DialogosJuego.mostrarTransaccion("💰 Pago de Renta",
                    inquilino.getNombre() + " paga " + rentaBase + "€ de renta a " + dueno.getNombre() +
                            " por " + getNombre(),
                    -rentaBase, inquilino, ventana);
        } else {
            // No puede pagar la renta
            DialogosJuego.mostrarAdvertencia("⚠️ No Puedes Pagar la Renta",
                    inquilino.getNombre() + " no tiene suficiente dinero para pagar la renta.\n\n" +
                            "Renta: " + rentaBase + "€\n" +
                            "Tu dinero: " + inquilino.getDinero() + "€\n\n" +
                            "⚠️ Esto puede llevarte a la bancarrota.",
                    ventana);
        }
    }

    // Método auxiliar para encontrar la ventana principal activa
    private JFrame encontrarVentanaPrincipal() {
        for (java.awt.Window window : java.awt.Window.getWindows()) {
            if (window instanceof JFrame && window.isVisible()) {
                return (JFrame) window;
            }
        }
        return null; // Si no encuentra ventana, los diálogos se mostrarán sin padre
    }
}
