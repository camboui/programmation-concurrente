package jus.poc.prodcons.v5;
import jus.poc.prodcons.*;

import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.ArrayList;
import java.util.Properties;

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