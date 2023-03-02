package unam.ciencias.computoconcurrente;

public class MatrixUtils implements Runnable{
    private int threads;
    private static float promedio; // Arreglo para que cada hilo guarde su promedio
    private static int[][] matrixGlobal; 
    private int secciones; 

    public MatrixUtils() {
        this.threads = 1;
    }

    public MatrixUtils(int threads) {
        this.threads = threads;
    }

    public MatrixUtils(int threads, int[][] matrix) {
        this.threads = threads;
        this.matrixGlobal = matrix;
    }

    @Override
    public void run() {

        int intervalo = matrixGlobal.length / this.threads;
        int limite = secciones + intervalo;
        
        int numElementos = 0;
        this.promedio = 0;

        for(int i=secciones; i<limite; i++){
            for(int j=0; j<matrixGlobal[i].length; j++){
                promedio += matrixGlobal[i][j];
                numElementos++;
            }
        }

        this.promedio = this.promedio / numElementos;
    }

    public double findAverage(int[][] matrix) throws InterruptedException{

        double promedioFinal = 0;
        int rows = matrix.length;
        int intervalo = rows / this.threads;

        secciones = 0;

        MatrixUtils[] mu = new MatrixUtils[threads];
        Thread[] hilos = new Thread[threads];

        for(int i = 0; i < threads; i++){
            MatrixUtils h = new MatrixUtils(1, matrix);
            Thread hilo = new Thread(h);
            mu[i] = h;
            hilos[i] = hilo;
            hilo.start();
            secciones += intervalo;
        }
        
        for(int j=0; j < threads; j++){
            hilos[j].join();
            promedioFinal += mu[j].getPromedio();
        }

        return promedioFinal / threads;
    }

    public double getPromedio(){
        return this.promedio;
    }
}