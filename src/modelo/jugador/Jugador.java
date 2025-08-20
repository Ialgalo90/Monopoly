package modelo.jugador;

import modelo.casillas.Casilla;
import modelo.casillas.CasillaPropiedad;
import modelo.tablero.Dado;
import modelo.tablero.Tablero;

import java.util.ArrayList;
import java.util.List;

public class Jugador {

    private String nombre;
    private int posicion;
    private int dinero;
    private List<CasillaPropiedad> propiedades;
    private boolean enCarcel;
    private int turnosEnCarcel;
    private int ultimaTirada = 0;

    public Jugador() {
        this.nombre = "";
        this.posicion = 0;
        this.dinero = 1500;
        this.propiedades = new ArrayList<>();
        this.enCarcel = false;
        this.turnosEnCarcel = 0;
    }

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.posicion = 0;
        this.dinero = 1500;
        this.propiedades = new ArrayList<>();
        this.enCarcel = false;
    }

    public Jugador(String nombre, int posicion, int dinero, List<CasillaPropiedad> propiedades, boolean enCarcel) {
        this.nombre = nombre;
        this.posicion = posicion;
        this.dinero = dinero;
        this.propiedades = propiedades != null ? propiedades : new ArrayList<>();
        this.enCarcel = enCarcel;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public int getDinero() {
        return dinero;
    }

    public void setDinero(int dinero) {
        this.dinero = dinero;
    }

    public List<CasillaPropiedad> getPropiedades() {
        return propiedades;
    }

    public void setPropiedades(List<CasillaPropiedad> propiedades) {
        this.propiedades = propiedades;
    }

    public boolean isEnCarcel() {
        return enCarcel;
    }

    public void setEnCarcel(boolean enCarcel) {
        this.enCarcel = enCarcel;
    }

    public int getTurnosEnCarcel() {
        return turnosEnCarcel;
    }

    public void setTurnosEnCarcel(int turnosEnCarcel) {
        this.turnosEnCarcel = turnosEnCarcel;
    }

    public int getUltimaTirada() {
        return ultimaTirada;
    }

    public void setUltimaTirada(int ultimaTirada) {
        this.ultimaTirada = ultimaTirada;
    }

    @Override
    public String toString() {
        return "Jugador{" +
                "nombre='" + nombre + '\'' +
                ", posicion=" + posicion +
                ", dinero=" + dinero +
                ", propiedades=" + propiedades +
                ", enCarcel=" + enCarcel +
                '}';
    }

    public int lanzarDados() {
        int resultado = Dado.lanzarDados();
        System.out.println(nombre + " ha lanzado los dados y ha sacado: " + resultado);
        return resultado;
    }

    public void avanzar(int pasos, Tablero tablero) {
        int totalCasillas = tablero.getCasillas().size();
        posicion = (posicion + pasos) % totalCasillas;

        Casilla casillaActual = tablero.getCasilla(posicion);
        System.out.println(nombre + " avanza " + pasos + " casillas y cae en " + casillaActual.getNombre());

        casillaActual.ejecutarAccion(this, tablero);
    }

    public void incrementarTurnosEnCarcel() {
        this.turnosEnCarcel++;
    }
}

