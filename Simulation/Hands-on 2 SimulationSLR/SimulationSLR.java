/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package simulationslr;

/**
 *
 * @author Emiliano
 */
public class SimulationSLR {
    
    //Data Set ={(1,2), (2,4), (3,6), (4,8), (5, 10)} 

    int x[]={1, 2, 3, 4, 5};
    int y[]= {2, 4, 6, 8, 10};
    
    int sumX = 0, sumY = 0, sumXY = 0, sumXQ = 0, n=5;
    double beta0=0, beta1=0;
    
    public SimulationSLR (){
        this.sumX=0;
        this.sumY=0;
        this.sumXY=0;
        this.sumXQ=0;
        this.beta0=0;
        this.beta1=0;
        this.n=5;
        
        for(int i=0; i<n; i++){
           sumX += x[i];
           sumY += y[i];
           sumXY += x[i] * y[i];
           sumXQ += x[i] * x[i];
        }
    }
    
     public double CalcularB1(){
        beta1 = (n * sumXY - (sumX * sumY)) / (n * sumXQ - (sumX * sumX));
        //System.out.println(beta1);
        return beta1;
    }
    
    
    public double CalcularB0(){
        CalcularB1();
        beta0 = (sumY/n) - (beta1 * (sumX/n)) ;
        //System.out.println(beta0);
        return beta0;
    
    }
    
    
    public static void main(String[] args) {
        
        SimulationSLR obsimulation = new SimulationSLR();
        
        System.out.println("Xi: " + obsimulation.sumX);
        System.out.println("Yi: "+ obsimulation.sumY);
        System.out.println("XiYi: "+ obsimulation.sumXY);
        System.out.println("Xi^2: "+ obsimulation.sumXQ);
        System.out.println("B0: " + obsimulation.beta0);
        System.out.println("B1: "+obsimulation.beta1);
        
        System.out.println("\n");

        
        System.out.println("La aproximacion de Beta0: " + obsimulation.CalcularB0());
        System.out.println("La aproximacion de Beta1: " + obsimulation.CalcularB1());
      
       
    }
    
}
