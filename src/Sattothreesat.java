import java.io.*;
import java.util.*;

public class Sattothreesat{

//    long startTime = System.nanoTime();
    // Reduction SAT to SAT3 algorithm.
    void reduction() throws IOException {

        CNF cnf = new CNF();
        List<List<String>> list = cnf.read();
        List<List<String>> nlist = new ArrayList<>();
        int var = cnf.getNumVar();
        // Algorithm reduction.
        /**
         * iterate through list, if size is > 3 then split the clause adding a new variable.
         * removing the split elements from list and add a new variable.
         * while the clause is > 3, keep splitting and adding the new variables.
         */

        for (List<String> l : list) {

            if (l.size() <= 3) {
                nlist.add(l);
            } else {
                int size = l.size();
                while (size > 3) {
                    String one = l.get(0);
                    String two = l.get(1);
                    List<String> three = new ArrayList<>();
                    three.add(one);
                    three.add(two);
                    var++;
                    three.add(String.valueOf(var));
                    ListIterator itr = l.listIterator();
                    int v = 0;
                    while (itr.hasNext()) {
                        itr.next();
                        if (v == 0 || v == 1) {
                            itr.remove();
                            v++;
                        }
                    }
                    l.add(0, "-" + var);
                    size--;
                    nlist.add(three);
                }
                nlist.add(l);
            }
        }

        cnf.write(nlist);
//        long endTime   = System.nanoTime();
//        long totalTime = endTime - startTime;
//        System.out.println(totalTime);

    }
}
