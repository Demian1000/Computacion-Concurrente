package unam.ciencias.computoconcurrente;
import java.lang.Math;

public class PrimeNumberCalculator implements Runnable{

    private int threads;
    private int a;
    private int b;
    private int num;
    public static boolean result;

    public PrimeNumberCalculator() {
        this.threads = 1;
    }

    public PrimeNumberCalculator(int threads) {
        this.threads = threads > 1 ? threads : 1;
    }
    
    public PrimeNumberCalculator(int threads, int a, int b, int n) {
        this.a = a;
        this.b = b;
        this.num = n;
    }
    

    public boolean isPrime(int n) throws InterruptedException{

        if(n < 0) return isPrime(-n);
        if(n < 2) return false;
        result = true;
        //boolean esPrimo = true;
        int longitudIntervalo = ((int)Math.sqrt(n))+1 / threads;
        int x = 0;
        int y = longitudIntervalo;
        PrimeNumberCalculator[] pn = new PrimeNumberCalculator[threads];
        Thread[] hilos = new Thread[threads];
        for(int i=0; i < threads; i++){
            PrimeNumberCalculator h = new PrimeNumberCalculator(1, x, y, n);
            Thread hilo = new Thread(h);
            pn[i] = h;
            hilos[i] = hilo;
            hilo.start();
            x += longitudIntervalo;
            y += longitudIntervalo;
        }

        for(int j=0; j < threads; j++){
            hilos[j].join();
            //esPrimo = esPrimo && pn[j].getResult();
        }
        return result;
    }
    
    @Override
    public void run(){
        if(b > num) b = num-1;
        for(int k=a; k<=b; k++){
            if(k < 2) continue;
            if(num % k == 0) result = result && false;
        }
    }

    public boolean getResult(){
        return result;
    }
    
}
