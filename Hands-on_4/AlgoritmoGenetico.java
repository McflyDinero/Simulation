package algoritmogenetico;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AlgoritmoGenetico {

    private List<Individuo> poblacion;
    private int tamanoPoblacion = 200;       // ↑ más diversidad genética
    private double probCruce = 0.95;
    private double probMutacion = 0.05;      // ↑ de 0.01 a 0.05 para escapar óptimos locales
    private double tasaMutacionAdaptiva;
    private int maxGeneraciones = 5000;      // límite de seguridad
    private Random random = new Random();

    public AlgoritmoGenetico() {
        this.poblacion = new ArrayList<>();
        for (int i = 0; i < tamanoPoblacion; i++) {
            poblacion.add(new Individuo());
        }
        this.tasaMutacionAdaptiva = probMutacion;
    }

    public void ejecutarEvolucion(double[] x, double[] y, double mediaY) {
        boolean condicionAlcanzada = false;
        int generacion = 1;
        Individuo mejorGlobal = null;
        int generacionesSinMejora = 0;
        double mejorFitnessAnterior = 0;

        while (!condicionAlcanzada && generacion <= maxGeneraciones) {

            // --- Evaluar fitness ---
            double sumaFitnessTotal = 0;
            Individuo mejorDeGeneracion = poblacion.get(0);

            for (Individuo ind : poblacion) {
                ind.calcularFitness(x, y, mediaY);
                sumaFitnessTotal += ind.fitness;
                if (ind.fitness > mejorDeGeneracion.fitness) {
                    mejorDeGeneracion = ind;
                }
            }

            // Actualizar mejor global
            if (mejorGlobal == null || mejorDeGeneracion.fitness > mejorGlobal.fitness) {
                mejorGlobal = copiarIndividuo(mejorDeGeneracion);
            }

            // Mutación adaptiva: si no hay mejora, aumentar mutación
            if (mejorGlobal.fitness - mejorFitnessAnterior < 0.0001) {
                generacionesSinMejora++;
                if (generacionesSinMejora > 50) {
                    tasaMutacionAdaptiva = Math.min(tasaMutacionAdaptiva * 1.5, 0.30);
                }
            } else {
                generacionesSinMejora = 0;
                tasaMutacionAdaptiva = probMutacion; // resetear
            }
            mejorFitnessAnterior = mejorGlobal.fitness;

            // Log cada 100 generaciones y la primera
            if (generacion % 500 == 0 || generacion == 1) {
                System.out.printf("Generacion: %d | Mejor R^2: %.4f | Ecuacion: y = %.2f + %.2fx | MutRate: %.3f\n",
                        generacion, mejorGlobal.fitness,
                        mejorGlobal.betas[0], mejorGlobal.betas[1],
                        tasaMutacionAdaptiva);
            }

            // Condición de paro
            if (mejorGlobal.fitness >= 0.975) {
                condicionAlcanzada = true;
                imprimirResultados(mejorGlobal, generacion, "R^2 >= 0.98");
                break;
            }

            // --- Nueva población con ELITISMO ---
            List<Individuo> nuevaPoblacion = new ArrayList<>();

            // Elitismo: conservar los 2 mejores sin modificación
            nuevaPoblacion.add(copiarIndividuo(mejorGlobal));
            nuevaPoblacion.add(copiarIndividuo(mejorDeGeneracion));

            while (nuevaPoblacion.size() < tamanoPoblacion) {
                Individuo padre1 = seleccionPorTorneo();   // ← torneo es más robusto
                Individuo padre2 = seleccionPorTorneo();

                Individuo hijo1 = copiarIndividuo(padre1); // ← ya no nacen vacíos
                Individuo hijo2 = copiarIndividuo(padre2);

                if (random.nextDouble() <= probCruce) {
                    cruzarDeUnPunto(padre1, padre2, hijo1, hijo2);
                }

                mutar(hijo1);
                mutar(hijo2);

                nuevaPoblacion.add(hijo1);
                if (nuevaPoblacion.size() < tamanoPoblacion) {
                    nuevaPoblacion.add(hijo2);
                }
            }

            this.poblacion = nuevaPoblacion;
            generacion++;
        }

        if (!condicionAlcanzada) {
            System.out.println("\nLimite de generaciones alcanzado.");
            imprimirResultados(mejorGlobal, maxGeneraciones, "Max generaciones");
        }
    }

    // SELECCIÓN POR TORNEO — más robusta que ruleta con R² negativos
    private Individuo seleccionPorTorneo() {
        int tamanoTorneo = 5;
        Individuo mejor = poblacion.get(random.nextInt(tamanoPoblacion));
        for (int i = 1; i < tamanoTorneo; i++) {
            Individuo candidato = poblacion.get(random.nextInt(tamanoPoblacion));
            if (candidato.fitness > mejor.fitness) {
                mejor = candidato;
            }
        }
        return mejor;
    }

    private void cruzarDeUnPunto(Individuo p1, Individuo p2, Individuo h1, Individuo h2) {
        h1.betas[0] = p1.betas[0];
        h1.betas[1] = p2.betas[1];
        h2.betas[0] = p2.betas[0];
        h2.betas[1] = p1.betas[1];
    }

    // Mutación con rango reducido y adaptivo
    private void mutar(Individuo hijo) {
        if (random.nextDouble() <= tasaMutacionAdaptiva) {
            // Perturbación pequeña en lugar de valor completamente aleatorio
            hijo.betas[0] += (random.nextGaussian() * 50);  // ruido gaussiano ±50
        }
        if (random.nextDouble() <= tasaMutacionAdaptiva) {
            hijo.betas[1] += (random.nextGaussian() * 5);   // ruido gaussiano ±5
        }
    }

    private Individuo copiarIndividuo(Individuo original) {
        Individuo copia = new Individuo();
        copia.betas[0] = original.betas[0];
        copia.betas[1] = original.betas[1];
        copia.fitness = original.fitness;
        return copia;
    }

    private void imprimirResultados(Individuo mejor, int generacion, String razon) {
        System.out.println("\n==================================================");
        System.out.println("EXITO,  " + razon);
        System.out.printf("Generaciones totales: %d\n", generacion);
        System.out.printf("Mejor R^2 Final: %.4f (%.2f%%)\n", mejor.fitness, mejor.fitness * 100);
        System.out.printf("Coeficientes Finales: Beta_0 = %.4f, Beta_1 = %.4f\n",
                mejor.betas[0], mejor.betas[1]);
        System.out.printf("Ecuacion Final: y = %.4f + %.4f * x\n",
                mejor.betas[0], mejor.betas[1]);
    }
}