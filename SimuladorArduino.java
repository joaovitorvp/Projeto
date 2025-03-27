public class SimuladorArduino {
   
    public static String [] leitura(){
        // Simular a leitura dos sensores
        String temperatura = String.valueOf(Math.random() * 100);
        String umidade = String.valueOf(Math.random() * 100);
        String luminosidade = String.valueOf(Math.random() * 100);
        return new String[] {temperatura, umidade, luminosidade};
    }
    
}
