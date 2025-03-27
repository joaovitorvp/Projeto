public class SimuladorArduino {
   
    public static String [] leitura(){
        String temperatura = String.valueOf((int) (Math.random() * 100));
        String umidade = String.valueOf((int) (Math.random() * 100));
        String luminosidade = String.valueOf((int) (Math.random() * 100));
        return new String[] {temperatura, umidade, luminosidade};
    }
    
}
