/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication15;

import static java.lang.Thread.sleep;
import java.util.Scanner;
import java.util.concurrent.*;

public class Generator extends Thread {

    public static BlockingQueue queue = new ArrayBlockingQueue(7);//queue and number of chairs creation

    public static void main(String[] args) {
        Generator barberShop = new Generator();

        barberShop.openShop();//Shop open when function called
        
        
    }

    public void openShop() {
         int Cus;
        
        Scanner sc1 = new Scanner(System.in);
        System.out.println("Enter number of customers ");
        Cus = sc1.nextInt();
           Barber aBarber = new Barber("Barber 1", Generator.queue);
        aBarber.start();//barber 1
                
        Barber cBarber = new Barber("Barber 2", Generator.queue); 
        cBarber.start();//barber 2
         Barber dBarber = new Barber("Barber 3", Generator.queue); 
        dBarber.start();//barber 3
        for (int i = 1; i <= Cus; i++) {//Customer input from user

          
            Customer aCustomer = new Customer(i, Generator.queue);//Customer Goes to Queue
            aCustomer.WakeUpBarber();
            try {
                sleep(3000); //Next Customer coming in 2 milliseconds
            } catch (InterruptedException ex) {
            }
        }       
    }

}
