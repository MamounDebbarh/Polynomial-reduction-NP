import java.io.*;
import java.util.*;


public class Coltosat{

    // Reduction k-COL to SAT algorithm.
    void reduction() throws IOException {
//        long startTime = System.nanoTime();
        COL col = new COL();
        CNF cnf = new CNF();
        List<List<String>> list = col.read();
        cnf.write(list);
//        long endTime   = System.nanoTime();
//        long totalTime = endTime - startTime;
//        System.out.println(totalTime);

    }
}
