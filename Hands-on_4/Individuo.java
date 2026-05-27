/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package algoritmogenetico;

import java.util.Random;

/**
 *
 * @author Emiliano
 */
public class Individuo {
    public double betas []; 
    public double fitness;
    private static Random random = new Random();

    public Individuo() {
        this.betas = new double[2];
        // Inicializamos con un rango más amplio (-500 a 500) porque las ventas llegan hasta 1500
        this.betas[0] = (random.nextDouble() * 1000) - 500; 
        this.betas[1] = (random.nextDouble() * 200) - 100;
        this.fitness = 0;
    }
    
    public void calcularFitness(double[] x, double[] y, double mediaY) {
       double sumaErroresCuadrados = 0; // SS_res (Numerador)
        double sumaTotalCuadrados = 0;   // SS_tot (Denominador)

        for (int i = 0; i < x.length; i++) {
            // Predicción: y_hat = beta0 + beta1 * x
            double y_hat = betas[0] + (betas[1] * x[i]);

            // Error de predicción (Valor Real - Predicción)^2
            sumaErroresCuadrados += Math.pow(y[i] - y_hat, 2);
            
            // Variación total (Valor Real - Promedio de Y)^2
            sumaTotalCuadrados += Math.pow(y[i] - mediaY, 2);
        }

        if (sumaTotalCuadrados == 0) {
            this.fitness = 0;
        } else {
            // Fórmula real del R^2: 1 - (SS_res / SS_tot)
            double r2 = 1.0 - (sumaErroresCuadrados / sumaTotalCuadrados);
            
            // Si el R^2 es negativo (la línea es peor que el promedio horizontal), 
            // le damos un fitness casi nulo para que tenga muy poca chance en la ruleta.
            this.fitness = Math.max(r2, 0.0001); 
        }
}
}

