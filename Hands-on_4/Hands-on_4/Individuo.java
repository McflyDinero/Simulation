package algoritmogenetico;

import java.util.Random;

public class Individuo {
    public double[] betas; 
    public double fitness;
    private static Random random = new Random();

    public Individuo() {
        this.betas = new double[2];
        
        // Inicialización de coeficientes
        this.betas[0] = (random.nextDouble() * 1000) - 500; 
        this.betas[1] = (random.nextDouble() * 200) - 100;
        this.fitness = 0;
    }
    
    public void calcularFitness(double[] x, double[] y, double mediaY) {
        double sumaErroresCuadrados = 0; 
        double sumaTotalCuadrados = 0;   

        for (int i = 0; i < x.length; i++) {
            double y_hat = betas[0] + (betas[1] * x[i]);

            sumaErroresCuadrados += Math.pow(y[i] - y_hat, 2);
            sumaTotalCuadrados += Math.pow(y[i] - mediaY, 2);
        }

        if (sumaTotalCuadrados == 0) {
            this.fitness = 0;
        } else {
            // formula
            double r2 = 1.0 - (sumaErroresCuadrados / sumaTotalCuadrados);
            
            // Evitar fitness negativo
            this.fitness = Math.max(r2, 0.0001); 
        }
    }
}