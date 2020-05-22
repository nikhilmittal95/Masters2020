
package javaapplication15;


import java.util.concurrent.BlockingQueue;

public class Customer extends Thread {
     int iD;
        boolean notCut = true;
        BlockingQueue queue = null;
 
        public Customer(int i, BlockingQueue queue) {
        iD=i;//number of cutsomers from input data
            this.queue = queue;//customer from input data is put in queue
        }

      public void WakeUpBarber() {
            while (true) { 
                try {
                    this.queue.add(this.iD);
 
                    this.takeChair(); //checking free chairs from array blocking queue and if barber has cut hair
                } catch (IllegalStateException e) {
 
                    System.out.println("There are no free chairs. Customer "
                            + this.iD + " exits  barbershop");// cutsomer exits if no chairs in waiting area and barbers chairs full
                }
                break;
            }
        }
 
        public void takeChair() {
            System.out.println("Customer " + this.iD + " took a chair in waiting area");//customer waits for his turn in waiting area
        }
         
}