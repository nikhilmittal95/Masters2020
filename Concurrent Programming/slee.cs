using System;
using System.Threading;
using System.Collections.Concurrent;
using Microsoft.ConcurrencyVisualizer.Instrumentation;


namespace SleepingBarber
{
    class Program
    {
        internal static AutoResetEvent customerEvent = new AutoResetEvent(false);

        // no explict locks needed as it is a ConcurrentQueue > feature in .NET 4.0
        internal static ConcurrentQueue<Customer> queue = new ConcurrentQueue<Customer>();

        static void Main(string[] args)
        {
            Random rand = new Random();

            // make the barber background so the program can exit once the main thread is finished.
            // this does not impact the thread priority.
            new Thread(Barber.CutHair) { IsBackground = true, Name = "Barber" }.Start();

            Thread.Sleep(100);

            Thread.CurrentThread.Name = "Main";
            // creating 25 customers in random order
            for (int i = 0; i < 25; i++)
            {
                // temp is used to avoid the captured variable problem
                int temp = i;
                Thread.Sleep(rand.Next(600, 2000));
                Customer c = new Customer() { Name = "Customer " + temp };
                queue.Enqueue(c);

                if (queue.Count == 1)
                {
                    Customer.WakeUpBarber();
                }
            }

            Console.ReadKey();
        }
    }

    class Customer
    {
        public string Name { get; set; }

        internal static void WakeUpBarber()
        {
            Program.customerEvent.Set();
        }
    }

    class Barber
    {
        internal static void CutHair()
        {
            while (!Program.queue.IsEmpty)
            {
                Customer c;

                // time to cut the hair is 1 sec
                Thread.Sleep(1000);

                // after cutting hair barber removes him from the queue
                if (Program.queue.TryDequeue(out c))
                {
                    Console.WriteLine("Done hair cutting to {0}", c.Name);
                }
                else
                {
                    // this will never happen in this scenario as only one barber is here
                    Console.WriteLine(" 😦 customer is not going out ...");
                }
            }

            Console.WriteLine("Going to sleep");
            GoToSleep();
        }

        private static void GoToSleep()
        {
            using (Markers.EnterSpan("wait start"))
            {
                Program.customerEvent.WaitOne();
                Console.WriteLine("Waking up..Oh ! Customer arrived..");
            }
            CutHair();
        }
    }
}
