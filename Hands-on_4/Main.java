/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package algoritmogenetico;

/**
 *
 * @author Emiliano
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando Simulacion - Caso Benetton...\n");

        // Datos del Caso Benetton extraídos de la tabla
        // x = Advertising (Million Euro)
        double[] x = {23, 26, 30, 34, 43, 48, 52, 57, 58};
        
        // y = Sales (Million Euro)
        double[] y = {651, 762, 856, 1063, 1190, 1298, 1421, 1440, 1518};

        // Calcular Media de Y
        double sumaY = 0;
        for (double val : y) { sumaY += val; }
        double mediaY = sumaY / y.length;

        // Ejecutar el Algoritmo
        AlgoritmoGenetico ag = new AlgoritmoGenetico();
        ag.ejecutarEvolucion(x, y, mediaY);
    }
    
}
