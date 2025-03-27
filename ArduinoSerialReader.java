import com.fazecast.jSerialComm.SerialPort;

import javax.swing.*;
import java.awt.*;

public class ArduinoSerialReader extends JFrame {

    private JLabel lblTemperatura;
    private JLabel lblUmidade;
    private JLabel lblLuminosidade;
    private SerialPort comPort;
    private volatile boolean lendo = false;

    public ArduinoSerialReader() {
        setTitle("Monitor de Arduino - Temperatura, Umidade, Luminosidade");
        setLayout(new FlowLayout());
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        lblTemperatura = new JLabel("Temperatura: --");
        lblUmidade = new JLabel("Umidade: --");
        lblLuminosidade = new JLabel("Luminosidade: --");

        add(lblTemperatura);
        add(lblUmidade);
        add(lblLuminosidade);

        JButton btnIniciar = new JButton("Iniciar Leitura");
        btnIniciar.addActionListener(e -> iniciarLeitura());
        add(btnIniciar);

        
        JButton btnParar = new JButton("Parar Leitura");
        btnParar.addActionListener(e -> stopLeitura());
        add(btnParar);
    }

    private void iniciarLeitura() {
        lendo = true; 
        Thread leitura = new Thread(() -> {
            while (lendo) { 
                String[] valores = SimuladorArduino.leitura();

                SwingUtilities.invokeLater(() -> {
                    lblTemperatura.setText("Temperatura: " + valores[0]);
                    lblUmidade.setText("Umidade: " + valores[1]);
                    lblLuminosidade.setText("Luminosidade: " + valores[2]);
                });

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        leitura.start();
    }

    private void stopLeitura() {
        lendo = false; 
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ArduinoSerialReader app = new ArduinoSerialReader();
            app.setVisible(true);
        });
    }
}
