package algoritmogenetico;

public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando Simulacion\n");

        double[] x = {23, 26, 30, 34, 43, 48, 52, 57, 58};
        double[] y = {651, 762, 856, 1063, 1190, 1298, 1421, 1440, 1518};

        double sumaY = 0;
        for (int i = 0; i < y.length; i++) {
            sumaY += y[i];
        }
        double mediaY = sumaY / y.length;

        AlgoritmoGenetico ag = new AlgoritmoGenetico();
        ag.ejecutarEvolucion(x, y, mediaY);
    }
}