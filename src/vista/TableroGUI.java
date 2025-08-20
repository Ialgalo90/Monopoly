package vista;

import modelo.casillas.Casilla;
import modelo.jugador.Jugador;
import modelo.tablero.Dado;
import modelo.tablero.Tablero;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TableroGUI extends JFrame {

    private JButton[] casillas;
    private JButton botonLanzar;
    private JLabel infoLabel;

    private Tablero tablero;
    private List<Jugador> jugadores;
    private int turnoActual = 0;

    public TableroGUI(Tablero tablero, List<Jugador> jugadores) {
        this.tablero = tablero;
        this.jugadores = jugadores;

        setTitle("Monopoly");
        setSize(1200, 1200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel del Tablero
        JPanel panelTablero = new JPanel(new GridLayout(6,6));
        casillas = new JButton[36];

        for (int i = 0; i < 36; i++) {
         casillas[i] = new JButton();
         casillas[i].setEnabled(false);
         panelTablero.add(casillas[i]);
        }

        int[] indices = {
                0, 1, 2, 3, 4, 5, // Arriba
                11, 17, 23, // Derecha
                30, 31, 32, 33, 34, 35, // Abajo
                29, 23, 12, // Izquierda
                6
        };

        int[] borde = {
                0, 1, 2, 3, 4, 5,     // fila superior
                11, 17, 23, 29, 35,   // columna derecha
                34, 33, 32, 31, 30,   // fila inferior
                24, 18, 12, 6         // columna izquierda
        };

        for (int i = 0; i < tablero.getCasillas().size(); i++) {
            int pos = borde[i];
            casillas[pos].setText(tablero.getCasilla(i).getNombre());
        }

        // El centro (Casilla 12) lo dejamos para un logo o texto
        // casillas[14].setText("MONOPOLY");
        // casillas[14].setEnabled(false);

        // Panel inferior de info y botón lanzar Dados
        JPanel panelInferior = new JPanel();
        botonLanzar = new JButton("Lanzar Dados");
        infoLabel = new JLabel("Turno de " + jugadores.get(turnoActual).getNombre());

        panelInferior.add(botonLanzar);
        panelInferior.add(infoLabel);

        add(panelTablero, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);

        // Acción del botón lanzar Dados
        botonLanzar.addActionListener(e -> lanzarDadosYAvanzar());

        actualizarTablero(indices);
    }

    private void lanzarDadosYAvanzar() {
        Jugador jugador = jugadores.get(turnoActual);

        int dado1 = Dado.lanzar();
        int dado2 = Dado.lanzar();
        int total = dado1 + dado2;

        infoLabel.setText(jugador.getNombre() + " ha sacado " + dado1 + " y " + dado2);

        jugador.avanzar(total, tablero);

        // Ejecuta la acción de la casilla si lo tiene
        Casilla casillaActual = tablero.getCasilla(jugador.getPosicion());
        casillaActual.ejecutarAccion(jugador, tablero);


        int[] indices = {
                0, 1, 2, 3, 4, 5, // Arriba
                11, 17, 23, // Derecha
                30, 31, 32, 33, 34, 35, // Abajo
                29, 23, 12, // Izquierda
                6
        };

        // Se actualizar el tablero
        actualizarTablero(indices);

        // Cambia de turno
        turnoActual = (turnoActual + 1) % jugadores.size();
        infoLabel.setText("Turno de " + jugadores.get(turnoActual).getNombre());
    }

    private void actualizarTablero(int[] indices) {
        // Limpio todas las casillas
        for (int i = 0; i < indices.length; i++) {
            casillas[indices[i]].setText(tablero.getCasilla(i).getNombre());
        }

        // Pongo a los jugadores en su posición
        for (Jugador j : jugadores) {
            int posCasilla = indices[j.getPosicion()];
            String texto = casillas[posCasilla].getText();
            casillas[posCasilla].setText(texto + " [" + j.getNombre() + "]");
        }
    }

    public static void main(String[] args) {
        Tablero tablero = new Tablero();
        tablero.inicializarCasillas();

        Jugador j1 = new Jugador("Jugador 1");
        Jugador j2 = new Jugador("Jugador 2");

        tablero.setJugadores(List.of(j1, j2));

        SwingUtilities.invokeLater(() -> {
            TableroGUI gui = new TableroGUI(tablero, tablero.getJugadores());
            gui.setVisible(true);
        });
    }
}
