import com.fazecast.jSerialComm.SerialPort;

import javax.swing.*;
import java.awt.*;

public class ArduinoSerialReader extends JFrame {

    private JLabel lblTemperatura;
    private JLabel lblUmidade;
    private JLabel lblLuminosidade;
    private JLabel lblSugestao;
    private SerialPort comPort;
    private volatile boolean lendo = false;

    public ArduinoSerialReader() {
        setTitle("Casa Inteligente");
        setLayout(new FlowLayout());
        setSize(600, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        lblTemperatura = new JLabel("Temperatura: --");
        lblUmidade = new JLabel("Umidade: --");
        lblLuminosidade = new JLabel("Luminosidade: --");
        lblSugestao = new JLabel("Sugestão: --");

        add(lblTemperatura);
        add(lblUmidade);
        add(lblLuminosidade);
        add(lblSugestao);
        
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
                String sugestao = gerarSugestao(Integer.parseInt(valores[0]), Integer.parseInt(valores[1]), Integer.parseInt(valores[2]));
                

                SwingUtilities.invokeLater(() -> {
                    lblTemperatura.setText("Temperatura: " + valores[0]+"ºC");
                    lblUmidade.setText("Umidade: " + valores[1]+"%");
                    lblLuminosidade.setText("Luminosidade: " + valores[2]+"%");
                    lblSugestao.setText("Sugestão: " + sugestao);
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

     private String gerarSugestao(int temperatura, int umidade, int luminosidade) {
        StringBuilder sugestao = new StringBuilder();
        
        if (temperatura > 30) {
            sugestao.append("Temperatura elevada! Ar condicionado ativado para resfriamento. ");
        } else if (temperatura < 18) {
            sugestao.append("Temperatura baixa! Aquecedor ativado para conforto térmico. ");
        } else {
            sugestao.append("Temperatura estável. Nenhuma ação necessária. ");
        }
        
        if (umidade < 30) {
            sugestao.append("Umidade baixa! Umidificador ativado para melhorar a qualidade do ar. ");
        } else if (umidade > 70) {
            sugestao.append("Umidade alta! Desumidificador ativado para evitar mofo e desconforto. ");
        } else {
            sugestao.append("Umidade em nível adequado. ");
        }
        
        if (luminosidade < 30) {
            sugestao.append("Ambiente escuro! Luzes inteligentes ativadas para melhor visibilidade. ");
        } else if (luminosidade > 80) {
            sugestao.append("Ambiente muito iluminado! Reduzindo intensidade da luz para economia de energia. ");
        } else {
            sugestao.append("Iluminação equilibrada. Nenhuma ação necessária. ");
        }
        
        return sugestao.toString();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ArduinoSerialReader app = new ArduinoSerialReader();
            app.setVisible(true);
        });
    }
}
