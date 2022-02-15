package cz.cvut.fel.omo;

import cz.cvut.fel.omo.scripts.*;

/**
 * Launch the program with a single argument of a number from 1 to 6. Each number plays a different Script of
 * configuration of the factory.
 *
 * Script 1 is meant to create 25 Audi type cars. But it's meant to stop after 150 ticks, so not all the cars
 * are produced yet. Some material is left in the material storage. One worker is assigned to work and there is
 * only one repairman. One worker needs 16 ticks to rest. It takes 3 ticks to repair a machine with one repairman.
 * Script 1 prints all the available Reports:
 *      - Consumption report with electricity consumption and prices and overall consumption.
 *      - Configuration report showing configuration of the factory with the state of machines, material storage
 *      contents and car storage contents.
 *      - Event report showing all the events that happened grouping them by the type of the event and by source.
 *      - Outages report showing the number of outages, longest and shortest outage and average time of outages.
 * Script 1 also makes the Manager visit the factory and Inspector check machines based on their health.
 *
 * Script 2 is meant to create 100 Skoda type cars and 10 Audi type cars. Ticks are not limited.
 * Two workers and two repairmen work here. Repairmen can help each other when they are not busy,
 * so sometimes it can take only two ticks to repair a machine. Reports are printed
 * except Event report as it would be too long.
 *
 * In Script 3 machine breaks but there are no repairmen to repair them, so the production
 * has to be stopped.
 *
 * In Script 4 material storage is limited only to 400 pieces of components, but there are
 * stored many more. This results into missing material for creating even one car and the production
 * can't be started.
 *
 * Script 5 concerns a problem where an AssemblyEngine machine is not in storage and therefore
 * production can't start.
 *
 * Script 6 - Endurance test - creating 1000 Mercedes cars. Buying 18 machines. Buying over 16000 material pieces.
 * Hiring 7 workers and 5 repairmen.
 *
 */
public class Main {

    /**
     * Launch the program with a single argument of a number from 1 to 6.
     * @param args - single number from 1 to 6
     */
    public static void main(String[] args) {
        if (args.length > 1) {
            System.err.println("Too many arguments");
            return;
        } else if (args.length == 0) {
            System.err.println("Too few arguments.");
            return;
        }

        switch(args[0]) {
            case "1":
                Script1 script1 = new Script1();
                script1.play();
                break;

            case "2":
                Script2 script2 = new Script2();
                script2.play();
                break;

            case "3":
                Script3 script3 = new Script3();
                script3.play();
                break;

            case "4":
                Script4 script4 = new Script4();
                script4.play();
                break;

            case "5":
                Script5 script5 = new Script5();
                script5.play();
                break;

            case "6":
                Script6 script6 = new Script6();
                script6.play();
                break;

            default:
                System.err.println("Invalid argument.");
                break;
        }
    }
}
