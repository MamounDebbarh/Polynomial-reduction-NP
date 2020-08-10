import java.io.*;
import java.util.*;

public class Threesattocol{

    // Reduction 3SAT to k-COL algorithm.
    void reduction() throws IOException {
//        long startTime = System.nanoTime();
        CNF cnf = new CNF();
        COL col = new COL();
        //list of edges
        List<List<String>> list = cnf.read();
        List<List<String>> nlist = new ArrayList<>();
        int numVar = cnf.getNumVar();
        if (numVar< 4){
            numVar = 4;
        }
        // join x and not x.
        // not x is x + numVar.
        for (int i = 1 ; i <= numVar ; i++){
            List<String> myN = new ArrayList<>();
            myN.add(String.valueOf(i));
            myN.add(String.valueOf(i+numVar));
            nlist.add(myN);
        }

        // join all new y's.
        // y is 2*numVar + x
        int f = numVar*2 +1;
        int s = f+1;
        while (s <= numVar*3) {
            List<String> myN = new ArrayList<>();
            myN.add(String.valueOf(f));
            myN.add(String.valueOf(s));
            nlist.add(myN);
            if (s < numVar*3) {
                s++;
            } else {
                f++;
                s = f + 1;
            }
        }
        // join x's and y's.
        int p = numVar*2 +1;
        int d = 1;
        while (p <= numVar*3) {
            if (p-numVar*2 != d && p-numVar != d) {
                List<String> myN = new ArrayList<>();
                myN.add(String.valueOf(p));
                myN.add(String.valueOf(d));
                nlist.add(myN);
            }
            if (d < numVar*2) {
                d++;
            } else {
                p++;
                d = 1;
            }
        }

        // join clauses.
        int x = (numVar*3) + 1;
        int y = 1;
        int count = 0;
        while ( count < list.size()) {
            if (list.get(count).size() < 4) {
                if (y <= numVar) {
                    if (!list.get(count).contains(String.valueOf(y))) {
                        List<String> myN = new ArrayList<>();
                        myN.add(String.valueOf(x));
                        myN.add(String.valueOf(y));
                        nlist.add(myN);
                    }
                } else {
                    if (!list.get(count).contains("-" + (y-numVar))) {
                        List<String> myN = new ArrayList<>();
                        myN.add(String.valueOf(x));
                        myN.add(String.valueOf(y));
                        nlist.add(myN);
                    }
                }
            } else {
                System.out.println();
                System.out.println(" not 3SAT");
                System.exit(2);
            }
            if (y < numVar*2) {
                y++;
            } else {
                x++;
                y = 1;
                count++;
            }
        }

        // number of nodes in graph
        int numNod = 3*numVar + list.size();

        col.write(nlist,numNod, numVar);
//        long endTime   = System.nanoTime();
//        long totalTime = endTime - startTime;
//        System.out.println(totalTime);
    }
}
