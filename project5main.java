import java.io.PrintStream;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class project5main {

    public static class Duration implements Comparable<Duration> {

        public int startTime;
        public int endTime;

        public Duration(int startTime, int endTime){

            this.startTime = startTime;
            this.endTime = endTime;
        }

        public int compareTo(Duration d) {

            if(this.endTime > d.endTime) return 1;
            else if(this.endTime == d.endTime) return 0;
            else return -1;
        }

        public String toString() {

            return "(" + startTime + ", " + endTime + ")"; 
        }
    }

    public static void main(String[] args) throws FileNotFoundException {

        Scanner in = new Scanner(new File(args[0]));
        PrintStream out = new PrintStream(args[1]);
        
        String[] types = in.nextLine().split(" ");
        String[] durationsA = in.nextLine().split(" "); //for solids
        String[] durationsB = in.nextLine().split(" "); //for liquids
        String[] profits = in.nextLine().split(" ");
        String[] receivingTime = in.nextLine().split(" ");

        int len = types.length;

        TreeMap<Duration, Integer> durations = new TreeMap<Duration, Integer>();
        ArrayList<Duration> materials = new ArrayList<>();

        for(int i = 0; i < len; ++i) {
            
            Duration d; 

            if(types[i].equals("s")) {
                
                d = new Duration( Integer.parseInt(receivingTime[i]),  Integer.parseInt(receivingTime[i]) + Integer.parseInt(durationsA[i]));
                
            }else d = new Duration( Integer.parseInt(receivingTime[i]),  Integer.parseInt(receivingTime[i]) + Integer.parseInt(durationsB[i]));

            durations.put(d, Integer.parseInt(profits[i]));
            materials.add(d);
        }
        
        Collections.sort(materials);
        
        int[] maxProfits = new int[len];
 
        for(int i = 0; i < len; i++) {

            maxProfits[i] = 0;
        }
        
        maxProfits[0] = durations.get(materials.get(0));

        for(int i = 1; i < len; i++) {

            boolean notBreaked = true;

            for(int j = i - 1; j >= 0; --j) {

                if(materials.get(j).endTime <= materials.get(i).startTime) {
                    
                    maxProfits[i] = Integer.max(maxProfits[i - 1], durations.get(materials.get(i)) + maxProfits[j]);
                    notBreaked = !notBreaked;
                    break;
                }

                if(notBreaked) {

                    maxProfits[i] = Integer.max(maxProfits[i - 1], durations.get(materials.get(i)));
                }


                
            }
        }
    
        System.out.println(maxProfits[len-1]);
        System.out.println(durations);
        in.close();
        out.close();
    }
}
