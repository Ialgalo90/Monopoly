package vista;

import controlador.JuegoGUI;
import modelo.casillas.Casilla;
import modelo.jugador.Jugador;
import modelo.tablero.Dado;
import modelo.tablero.Tablero;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TableroGUI extends JFrame {

    private JButton[][] tableroVisual;
    private JButton botonLanzar;
    private JLabel infoJugadorActual;
    private JLabel infoDados;
    private JTextArea logAcciones;
    private JPanel panelJugadores;

    private JuegoGUI controladorJuego;
    private Tablero tablero;
    private List<Jugador> jugadores;
    private int turnoActual = 0;

    // Colores para cada jugador
    private Color[] coloresJugadores = {
        Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.MAGENTA, Color.ORANGE
    };

    // Mapeo del tablero (sentido horario)
    private int[][] posicionesTablero = {
        {5, 0}, {4, 0}, {3, 0}, {2, 0}, {1, 0}, {0, 0},     // Fila superior
        {0, 1}, {0, 2}, {0, 3}, {0, 4}, {0, 5},             // Columna derecha
        {1, 5}, {2, 5}, {3, 5}, {4, 5}, {5, 5},             // Fila inferior
        {5, 4}, {5, 3}, {5, 2}, {5, 1}                      // Columna izquierda
    };

    public TableroGUI(Tablero tablero, List<Jugador> jugadores) {
        this.tablero = tablero;
        this.jugadores = jugadores;

        configurarVentana();
        inicializarComponentes();
        actualizarTablero();
    }

    private void configurarVentana() {
        setTitle("🎲 Monopoly - Juego Interactivo");
        setSize(1000, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Centrar ventana
        setLocationRelativeTo(null);
    }

    private void inicializarComponentes() {
        // Panel principal del tablero (6x6)
        JPanel panelTablero = new JPanel(new GridLayout(6,6,2,2));
        panelTablero.setBackground(new Color(34,139, 34));
        panelTablero.setBorder(BorderFactory.createRaisedBevelBorder());

        tableroVisual = new JButton[6][6];

        // Inicializamos todas las casilla
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                tableroVisual[i][j] = new JButton();
                tableroVisual[i][j].setPreferredSize(new Dimension(120, 80));
                tableroVisual[i][j].setFont(new  Font("Arial", Font.BOLD, 10));
                tableroVisual[i][j].setBackground(Color.WHITE);
                tableroVisual[i][j].setBorder(BorderFactory.createRaisedBevelBorder());
                tableroVisual[i][j].setEnabled(false);
                panelTablero.add(tableroVisual[i][j]);
            }
        }

        // LLenamos el centro con logo/info
        tableroVisual[2][2].setText("<html><center>🎲<br>MONOPOLY<br>🏠</center></html>");
        tableroVisual[2][2].setBackground(new Color(220, 220, 220));
        tableroVisual[2][3].setText("<html><center>💰<br>BANCO<br>🏦</center></html>");
        tableroVisual[2][3].setBackground(new Color(220, 220, 220));
        tableroVisual[3][2].setText("<html><center>🎯<br>JUEGO<br>🎮</center></html>");
        tableroVisual[3][2].setBackground(new Color(220, 220, 220));
        tableroVisual[3][3].setText("<html><center>🏆<br>WINNER<br>👑</center></html>");
        tableroVisual[3][3].setBackground(new Color(220, 220, 220));

        // Panel lateral derecho para la información
        JPanel panelInfo = new JPanel(new BorderLayout());
        panelInfo.setPreferredSize(new Dimension(250,0));
        panelInfo.setBorder(BorderFactory.createTitledBorder("Información del juego"));

        // Info del jugador actual
        infoJugadorActual = new JLabel("<html><b>Turno de:</b><br>" + jugadores.get(turnoActual).getNombre() + "</html>");
        infoJugadorActual.setFont(new Font("Arial", Font.BOLD, 14));
        infoJugadorActual.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        // Info dados
        infoDados = new JLabel("<html><b>Dados:</b><br>¡Lanza los dados!</html>");
        infoDados.setFont(new Font("Arial", Font.BOLD, 12));
        infoDados.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        // Botón lanzar dados
        botonLanzar = new JButton("\uD83C\uDFB2 Lanzar Dados");
        botonLanzar.setFont(new Font("Arial", Font.BOLD, 14));
        botonLanzar.setPreferredSize(new Dimension(0, 50));
        botonLanzar.setBackground(new Color(70, 130, 180));
        botonLanzar.setForeground(Color.WHITE);

        // Panel Jugadores
        panelJugadores = new JPanel();
        panelJugadores.setLayout(new BoxLayout(panelJugadores, BoxLayout.Y_AXIS));
        JScrollPane scrollJugadores = new JScrollPane(panelJugadores);
        scrollJugadores.setPreferredSize(new Dimension(0,200));
        scrollJugadores.setBorder(BorderFactory.createTitledBorder("Jugadores"));

        // Log de acciones
        logAcciones = new JTextArea(10,20);
        logAcciones.setFont(new Font("Monospaced", Font.PLAIN, 11));
        logAcciones.setEditable(false);
        logAcciones.setBackground(new Color(248, 248, 248));
        JScrollPane scrollLog = new JScrollPane(logAcciones);
        scrollLog.setBorder(BorderFactory.createTitledBorder("Log de Acciones"));

        // Ensamblar panel info
        JPanel panelSuperior = new JPanel(new  BorderLayout());
        panelSuperior.add(infoJugadorActual, BorderLayout.NORTH);
        panelSuperior.add(infoDados, BorderLayout.CENTER);
        panelSuperior.add(botonLanzar, BorderLayout.SOUTH);

        panelInfo.add(panelSuperior, BorderLayout.NORTH);
        panelInfo.add(scrollJugadores, BorderLayout.CENTER);
        panelInfo.add(scrollLog, BorderLayout.SOUTH);

        // Añadimos los componentes a la ventana
        add(panelTablero, BorderLayout.CENTER);
        add(panelInfo, BorderLayout.EAST);

        // Evento Listener
        botonLanzar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lanzarDadosYAvanzar();
            }
        });

        actualizarPanelJugadores();
    }

    private void lanzarDadosYAvanzar() {
        // Si tenemos controlador, delegar la lógica
        if (controladorJuego != null) {
            controladorJuego.ejecutarTurno();

            // Actualizar la vista
            this.jugadores = controladorJuego.getJugadores();
            this.turnoActual = controladorJuego.getTurnoActual();

            actualizarTablero();
            actualizarPanelJugadores();

            // Actualizar info del turno
            if (!controladorJuego.juegoTerminado()) {
                Jugador siguienteJugador = controladorJuego.getJugadorActual();
                if (siguienteJugador != null) {
                    infoJugadorActual.setText("<html><b>Turno de:</b><br>" +
                            siguienteJugador.getNombre() + "</html>");
                }
            } else {
                mostrarFinJuego();
            }

            return;
        }

        // LÓGICA ORIGINAL (para compatibilidad hacia atrás)
        if (jugadores.isEmpty()) return;

        Jugador jugadorActual = jugadores.get(turnoActual);

        // Lanzar dados
        int dado1 = Dado.lanzar();
        int dado2 = Dado.lanzar();
        int total = dado1 + dado2;

        // Actualizar info dados
        infoDados.setText("<html><b>Dados:</b><br>" +
                "🎲 " + dado1 + " + 🎲 " + dado2 + " = " + total + "</html>");

        // Log de acción
        logAcciones.append(jugadorActual.getNombre() + " lanza dados: " +
                dado1 + " + " + dado2 + " = " + total + "\n");

        // Mover jugador
        int posicionAnterior = jugadorActual.getPosicion();
        jugadorActual.avanzar(total, tablero);

        // Log movimiento
        Casilla casillaDestino = tablero.getCasilla(jugadorActual.getPosicion());
        logAcciones.append("→ Se mueve a: " + casillaDestino.getNombre() + "\n");

        // Ejecutar acción de casilla (esto ya se hace en jugador.avanzar())
        // casillaDestino.ejecutarAccion(jugadorActual, tablero);

        // Actualizar tablero visual
        actualizarTablero();
        actualizarPanelJugadores();

        // Verificar si el juego terminó
        if (juegoTerminado()) {
            mostrarGanador();
            return;
        }

        // Cambiar turno
        turnoActual = (turnoActual + 1) % jugadores.size();
        infoJugadorActual.setText("<html><b>Turno de:</b><br>" +
                jugadores.get(turnoActual).getNombre() + "</html>");

        // Auto-scroll del log
        logAcciones.setCaretPosition(logAcciones.getDocument().getLength());
        logAcciones.append("\n");
    }

    private void actualizarTablero() {

        // Limpiar el tablero visual
        for (int i = 0; i < posicionesTablero.length; i++) {
            int fila = posicionesTablero[i][0];
            int col = posicionesTablero[i][1];

            if (i < tablero.getCasillas().size()) {
                Casilla casilla = tablero.getCasilla(i);
                String nombreCasilla = casilla.getNombre();

                // Acortar nombres muy largos
                if (nombreCasilla.length() > 15) {
                    nombreCasilla = nombreCasilla.substring(0, 12) + "...";
                }

                tableroVisual[fila][col].setText("<html><center>" + nombreCasilla + "</center></html>");

                // Color según tipo de casilla
                Color colorFondo = obtenerColorCasilla(casilla);
                tableroVisual[fila][col].setBackground(colorFondo);
            }
        }

        // Mostrar jugadores en sus posiciones
        for (int i = 0; i < jugadores.size(); i++) {
            Jugador jugador = jugadores.get(i);
            int posicion = jugador.getPosicion();

            if (posicion < posicionesTablero.length) {
                int fila = posicionesTablero[posicion][0];
                int col = posicionesTablero[posicion][1];

                String textoActual = tableroVisual[fila][col].getText();
                String jugadorTexto = "●";

                // Añadir jugador al texto
                if (textoActual.contains("</center></html>")) {
                    textoActual = textoActual.replace("</center></html>", "<br><font color='" + obtenerColorHex(coloresJugadores[i % coloresJugadores.length]) + "'>" + jugadorTexto + "</font></center></html>");
                } else {
                    textoActual += " " + jugadorTexto;
                }

                tableroVisual[fila][col].setText(textoActual);
            }
        }
    }

    private Color obtenerColorCasilla(Casilla casilla) {
        switch (casilla.getTipo()) {
            case SALIDA:
                return new Color(144, 238, 144); // Verde claro
            case PROPIEDAD:
                return new Color(176, 196, 222); // Azul acero claro
            case CARCEL:
                return new Color(255, 160, 122); // Salmón claro
            case IR_CARCEL:
                return new Color(255, 99, 71);   // Tomate
            case SORPRESA:
                return new Color(255, 218, 185); // Durazno
            case IMPUESTO:
                return new Color(255, 192, 203); // Rosa
            default:
                return Color.WHITE;
        }
    }

    private String obtenerColorHex(Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }

    private void actualizarPanelJugadores() {
        panelJugadores.removeAll();

        for (int i = 0; i < jugadores.size(); i++) {
            Jugador jugador = jugadores.get(i);

            JPanel panelJugador = new JPanel(new BorderLayout());
            panelJugador.setBorder(BorderFactory.createLineBorder(coloresJugadores[i % coloresJugadores.length], 2));
            panelJugador.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

            // Indicador visual del color
            JPanel indicadorColor = new JPanel();
            indicadorColor.setBackground(coloresJugadores[i % coloresJugadores.length]);
            indicadorColor.setPreferredSize(new Dimension(20, 0));

            // Info del jugador
            JLabel infoJugador = new JLabel("<html>" +
                    "<b>" + jugador.getNombre() + "</b><br>" +
                    "💰 " + jugador.getDinero() + "€<br>" +
                    "🏠 " + jugador.getPropiedades().size() + " propiedades" +
                    "</html>");
            infoJugador.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

            panelJugador.add(indicadorColor, BorderLayout.WEST);
            panelJugador.add(infoJugador, BorderLayout.CENTER);

            // Resaltar jugador actual
            if (i == turnoActual) {
                panelJugador.setBackground(new Color(255,255,200));
            } else {
                panelJugador.setBackground(Color.WHITE);
            }

            panelJugadores.add(panelJugador);
            panelJugadores.add(Box.createVerticalStrut(5));
        }

        panelJugadores.revalidate();
        panelJugadores.repaint();
    }

    private boolean juegoTerminado() {
        return jugadores.size() <= 1;
    }

    private void mostrarGanador() {
        if (jugadores.size() == 1) {
            Jugador ganador = jugadores.get(0);
            logAcciones.append("🏆 ¡¡¡ GANADOR: " + ganador.getNombre() + " !!!\n");

            JOptionPane.showMessageDialog(this,
                    "🏆 ¡Felicidades!\n\n" + ganador.getNombre() + " ha ganado la partida!\n" +
                            "💰 Dinero final: " + ganador.getDinero() + "€\n" +
                            "🏠 Propiedades: " + ganador.getPropiedades().size(),
                    "¡Fin del Juego!",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        botonLanzar.setEnabled(false);
        botonLanzar.setText("Juego Terminado");
    }

    private void mostrarFinJuego() {
        botonLanzar.setEnabled(false);
        botonLanzar.setText("Juego Terminado");
        infoJugadorActual.setText("<html><b>🏆 Juego Terminado</b></html>");
    }

    // Método para agregar mensajes al log (usado por el controlador)
    public void agregarMensajeLog(String mensaje) {
        if (logAcciones != null) {
            logAcciones.append(mensaje + "\n");
            logAcciones.setCaretPosition(logAcciones.getDocument().getLength());
        }
    }
}
