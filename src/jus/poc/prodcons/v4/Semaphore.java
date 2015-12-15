package jus.poc.prodcons.v4;

public class Semaphore {
    int residu;

    public Semaphore(int residu) {
        this.residu = residu;// le nombre de ressources
    }
    
    public synchronized void V() {
        if(++residu<=0) {
            notify(); // S'il y a des ressources on va reveiller un thread
        }
    }
    
    public synchronized void P() throws InterruptedException {
        if(--residu<0){  // S'il n'y a plus de ressources, on attend
            wait();
        }
    }
}