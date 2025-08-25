package vista;


import modelo.casillas.CasillaPropiedad;
import modelo.jugador.Jugador;

import javax.swing.*;
import java.awt.*;

/**
 * Clase para manejar todos los diálogos de interacción del juego
 */
public class DialogosJuego {

   // Diálogo para decidir si comprar una propiedad o no
   public static boolean mostrarDialogoCompra(Jugador jugador, CasillaPropiedad propiedad, Component padre) {
       JDialog dialogo = new JDialog((JFrame) SwingUtilities.getWindowAncestor(padre), "💰 Oportunidad de Compra", true);
       dialogo.setSize(400, 300);
       dialogo.setLocationRelativeTo(padre);
       dialogo.setLayout(new BorderLayout());

       // Panel principal de información
       JPanel panelInfo = new JPanel();
       panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
       panelInfo.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
       panelInfo.setBackground(new Color(240,248,255));

       // Título
       JLabel titulo = new JLabel("🏠 " + propiedad.getNombre(), JLabel.CENTER);
       titulo.setFont(new Font("Arial", Font.BOLD, 18));
       titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

       // Información de la propiedad
       JLabel infoPrecio = new JLabel("💰 Precio: " + propiedad.getPrecio() + "€", JLabel.CENTER);
       infoPrecio.setFont(new Font("Arial", Font.PLAIN, 14));
       infoPrecio.setAlignmentX(Component.CENTER_ALIGNMENT);

       JLabel infoRenta = new JLabel("🏡 Renta: " + propiedad.getRentaBase() + "€", JLabel.CENTER);
       infoRenta.setFont(new Font("Arial", Font.PLAIN, 14));
       infoRenta.setAlignmentX(Component.CENTER_ALIGNMENT);

       // Información del jugador
       JLabel infojugador = new JLabel("👤 " + jugador.getNombre(), JLabel.CENTER);
       infojugador.setFont(new Font("Arial", Font.PLAIN, 16));
       infojugador.setAlignmentX(Component.CENTER_ALIGNMENT);

       JLabel infoDinero = new JLabel("💰 Dinero disponible: " + jugador.getDinero() + "€", JLabel.CENTER);
       infoDinero.setFont(new Font("Arial", Font.PLAIN, 14));
       infoDinero.setAlignmentX(Component.CENTER_ALIGNMENT);

       // Color según si puede comprar o no
       Color colorDinero = jugador.getDinero() >= propiedad.getPrecio() ? new Color(0,128,0) : Color.RED;
       infoDinero.setForeground(colorDinero);

       // Pregunta
       JLabel pregunta = new JLabel("¿Quieres comprar esta propiedad?", JLabel.CENTER);
       pregunta.setFont(new Font("Arial", Font.BOLD, 16));
       pregunta.setAlignmentX(Component.CENTER_ALIGNMENT);

       // Añadir los componentes al panel
       panelInfo.add(titulo);
       panelInfo.add(Box.createVerticalStrut(15));
       panelInfo.add(infoPrecio);
       panelInfo.add(infoRenta);
       panelInfo.add(Box.createVerticalStrut(15));
       panelInfo.add(infojugador);
       panelInfo.add(infoDinero);
       panelInfo.add(Box.createVerticalStrut(15));
       panelInfo.add(pregunta);

       // Panel de botones
       JPanel panelBotones = new JPanel(new FlowLayout());
       panelBotones.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

       // Para capturar las respuestas
       final boolean[] resultado = {false};

       JButton botonComprar = new JButton("✅ Comprar");
       botonComprar.setFont(new Font("Arial", Font.BOLD, 14));
       botonComprar.setPreferredSize(new Dimension(120, 40));
       botonComprar.setBackground(new Color(34, 139, 34));
       botonComprar.setForeground(Color.WHITE);
       botonComprar.setEnabled(jugador.getDinero() >= propiedad.getPrecio());

       JButton botonRechazar = new JButton("❌ No Comprar");
       botonRechazar.setFont(new Font("Arial", Font.BOLD, 14));
       botonRechazar.setPreferredSize(new Dimension(120, 40));
       botonRechazar.setBackground(new Color(220, 20, 60));
       botonRechazar.setForeground(Color.WHITE);

       botonComprar.addActionListener(e -> {
           resultado[0] = true;
           dialogo.dispose();
       });

       botonRechazar.addActionListener(e -> {
           resultado[0] = false;
           dialogo.dispose();
       });

       panelBotones.add(botonComprar);
       panelBotones.add(Box.createHorizontalStrut(20));
       panelBotones.add(botonRechazar);

       // Ensamblar diálogo
       dialogo.add(panelInfo,BorderLayout.CENTER);
       dialogo.add(panelBotones,BorderLayout.SOUTH);

       // Mostrar y esperar respuesta
       dialogo.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
       dialogo.setVisible(true);

       return resultado[0];
   }

    // Diálogo para las opciones de la cárcel
    public static int mostrarDialogoCarcel(Jugador jugador, Component padre) {
        JDialog dialogo = new JDialog((JFrame) SwingUtilities.getWindowAncestor(padre), "🚔 En la Cárcel", true);
        dialogo.setSize(400, 250);
        dialogo.setLocationRelativeTo(padre);
        dialogo.setLayout(new BorderLayout());

        // Panel principal
        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelInfo.setBackground(new Color(255, 240, 240));

        JLabel titulo = new JLabel("🚔 " + jugador.getNombre() + " está en la cárcel", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel info = new JLabel("Turnos en cárcel: " + jugador.getTurnosEnCarcel() + "/3", JLabel.CENTER);
        info.setFont(new Font("Arial", Font.PLAIN, 14));
        info.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel dineroInfo = new JLabel("💰 Dinero disponible: " + jugador.getDinero() + "€", JLabel.CENTER);
        dineroInfo.setFont(new Font("Arial", Font.PLAIN, 14));
        dineroInfo.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelInfo.add(titulo);
        panelInfo.add(Box.createVerticalStrut(10));
        panelInfo.add(info);
        panelInfo.add(dineroInfo);
        panelInfo.add(Box.createVerticalStrut(15));

        JLabel pregunta = new JLabel("¿Cómo quieres intentar salir?", JLabel.CENTER);
        pregunta.setFont(new Font("Arial", Font.BOLD, 14));
        pregunta.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelInfo.add(pregunta);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        final int[] resultado = {0}; // Para capturar la respuesta

        JButton botonDados = new JButton("🎲 Lanzar dados (intentar dobles)");
        botonDados.setFont(new Font("Arial", Font.BOLD, 12));
        botonDados.setPreferredSize(new Dimension(250, 35));
        botonDados.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonDados.setBackground(new Color(70, 130, 180));
        botonDados.setForeground(Color.WHITE);

        JButton botonPagar = new JButton("💰 Pagar fianza (50€)");
        botonPagar.setFont(new Font("Arial", Font.BOLD, 12));
        botonPagar.setPreferredSize(new Dimension(250, 35));
        botonPagar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonPagar.setBackground(new Color(34, 139, 34));
        botonPagar.setForeground(Color.WHITE);
        botonPagar.setEnabled(jugador.getDinero() >= 50);

        botonDados.addActionListener(e -> {
            resultado[0] = 1;
            dialogo.dispose();
        });

        botonPagar.addActionListener(e -> {
            resultado[0] = 2;
            dialogo.dispose();
        });

        panelBotones.add(botonDados);
        panelBotones.add(Box.createVerticalStrut(10));
        panelBotones.add(botonPagar);

        // Ensamblar diálogo
        dialogo.add(panelInfo, BorderLayout.CENTER);
        dialogo.add(panelBotones, BorderLayout.SOUTH);

        // Mostrar y esperar respuesta
        dialogo.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialogo.setVisible(true);

        return resultado[0];
    }

    // Diálogo para mostrar información personal
    public static void mostrarInformacion(String titulo, String mensaje, Component padre) {
       JOptionPane.showMessageDialog(padre, titulo, mensaje, JOptionPane.INFORMATION_MESSAGE);
    }

    // Diálogo para mostrar advertencias
    public static void mostrarAdvertencia(String titulo, String mensaje, Component padre) {
        JOptionPane.showMessageDialog(padre, titulo, mensaje, JOptionPane.INFORMATION_MESSAGE);
    }

    // Diálogo para mostrar el resultado de los dados
    public static void mostrarResultadoDados(Jugador jugador, int dado1, int dado2, Component padre) {
       String mensaje = "🎲 " + jugador.getNombre() + " lanzó los dados:\n\n" +
               "Dado 1: " + dado1 + "\n" +
               "Dado 2: " + dado2 + "\n" +
               "Total: " + (dado1 + dado2);

       if (dado1 == dado2) {
           mensaje += "\n\n🎉 ¡Dobles! " + (jugador.isEnCarcel() ? "¡Sales de la cárcel!" : "");
       }

       JOptionPane.showMessageDialog(padre, mensaje, "\uD83C\uDFB2 Resultado de dados", JOptionPane.INFORMATION_MESSAGE);
    }

    // Diálogo para mostrar transacciones de dinero
    public static void mostrarTransaccion(String titulo, String descripcion, int cantidad, Jugador jugador, Component padre) {
       String mensaje = descripcion + "\n\n" +
               "Cantidad: " + cantidad + "€\n" +
               "Dinero actual de " + jugador.getNombre() + ": " + jugador.getDinero() + "€";

       // Icon icon = cantidad > 0 ? null : null;
       JOptionPane.showMessageDialog(padre, titulo, mensaje, JOptionPane.INFORMATION_MESSAGE);
    }

    // Diálogo de confirmación simple
    public static boolean mostrarConfirmacion(String titulo, String mensaje, Component padre) {
       int respuesta = JOptionPane.showConfirmDialog(padre, mensaje, titulo,
               JOptionPane.YES_NO_OPTION,
               JOptionPane.QUESTION_MESSAGE);
       return respuesta == JOptionPane.YES_OPTION;
    }
}
