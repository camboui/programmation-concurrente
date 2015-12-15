package jus.poc.prodcons.v4;

public class Semaphore {
    int residu;

    public Semaphore(int residu) {
        this.residu = residu;
    }
    
    public synchronized void V() {
        if(++residu<=0) {
            notify();
        }
    }
    
    public synchronized void P() throws InterruptedException {
        if(--residu<0){
            wait();
        }
    }
}