package javaapplication15;

import static java.lang.Thread.sleep;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Barber extends Thread {

    BlockingQueue queue = null;
    String BarberName = " ";

  
   

    public Barber(String Barber, BlockingQueue q) {
        this.queue = q;//barber and customer in queue,,any customer can get any barber
        this.BarberName = Barber;//barber name decalartion
       
    }

  
    
    @Override
    public void run() {
        while (true) { // runs in an infinite loop

            try {
                Object i = this.queue.poll(10000, TimeUnit.MILLISECONDS);
                if (i == null) {//barber sleeping for a long time
                    break; 
                }
                this.cutHair((Object) i);//Cutting hair function called

            } catch (InterruptedException e) {

            }
        }
    }

    
    public void cutHair(Object i) {
      
        try {
            System.out.println(" Customer " +i+ " wakes" +this.BarberName + " for his turn" );
            sleep(2000);
               System.out.println(this.BarberName + " is now cutting hair of customer " + i);//barber 1 or 2 or 3 will cut hair of customer n randomnly
            System.out.println(" Customer " +i+ " exits  barbershop after hair cut");//Customer exits the shop after cut hair done
                 sleep(4000);
              
        } catch (InterruptedException ex) {
        }
    }

   
        }

    
        

  
    