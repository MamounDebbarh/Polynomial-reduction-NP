import java.io.*;
import java.util.*;

public class COL {

    public COL() {
    }

    List<List<String>> read() throws IOException {

        // TODO If a line is required to start with a character it must be the first character in the line (no whitespace before it)

        // variables
        String str;
        boolean prob = true;
        boolean nocol = true;
        int numNod = 0;
        int edgChecker = 0;
        int numEdg = 0;
        int numCol = 0;
        // readers
        InputStreamReader in= new InputStreamReader(System.in);
        BufferedReader input = new BufferedReader(in);
        List<List<String>> clauses = new ArrayList<>();
        List<List<String>> nodes = new ArrayList<>();
        List<String> edges = new ArrayList<>();

        // reading line by line
        while ((str = input.readLine()) != null){

            // splitting the elements of my line
            String[] line = str.split("\\s+");

            // ignoring comments and new lines
            if((line[0].equals("c") || ((line[0].equals("\n")||line[0].equals("")) && line.length==1) )){
                continue;
            }

            // reading header
            if (prob) {
                if (line[0].equals("p") && line[1].equals("edge") && line.length == 4) {
                    // set up
                    numNod = Integer.parseInt(line[2]);
                    numEdg = Integer.parseInt(line[3]);
                    prob = false;
                    continue;
                } else {
                    System.out.println();
                    System.out.println(" Expected 'p edge' line error");
                    System.exit(2);
                }

            }
            // getting k
            if (nocol){
                if (line[0].equals("colours") && line.length == 2){
                    try {
                        // checking valid integer using parseInt() method
                        numCol = Integer.parseInt(line[1]);
                    } catch (NumberFormatException e)
                    {
                        System.out.println(line[1] + " is not a valid integer number");
                        System.exit(2);
                    }
                }else {
                    System.out.println();
                    System.out.println(" Expected 'colours' line error");
                    System.exit(2);
                }
                 /*
                SAT variables = nodes * colors.
                at least one color true for each node:
                not more than one color per node:
                clause { -1x , -1y } and x,y in colors

                This can happen just once when we have the numNod, numEdg and numCol.
             */

                // ALO
                for (int i = 1 ; i <= numNod*numCol;){
                    List<String> myN = new ArrayList<>();
                    for (int j = 0; j < numCol ; j++) {
                        myN.add(String.valueOf(i + j));
                    }

                    nodes.add(myN);
                    clauses.add(myN);

                    i += numCol;
                }

                // AMO
                for (List<String> node : nodes) {
                    // incrementing f and s allows us to get all possibilities and ignore the past elements of the list.
                    int f = 0;
                    int s = 1;
                    while (s < node.size()) {
                        List<String> myN = new ArrayList<>();
                        myN.add("-" + node.get(f));
                        myN.add("-" + node.get(s));
                        clauses.add(myN);
                        if (s + 1 < node.size()) {
                            s++;
                        } else {
                            f++;
                            s = f + 1;
                        }
                    }
                }

                nocol = false;
                continue;
            }

            // Edge clause done for each line.
            if (line[0].equals("e")) {
                // check for duplicate clauses.
                String checkDup = line[1] + line[2];
                String checkRev = line[2] + line[1];
                if (!edges.contains(checkDup) && !edges.contains(checkRev)) {
                for (int i = 0; i < numCol; i++) {
                        List<String> myN = new ArrayList<>();
                        myN.add("-" + nodes.get(Integer.parseInt(line[1]) - 1).get(i));
                        myN.add("-" + nodes.get(Integer.parseInt(line[2]) - 1).get(i));
                        edges.add(line[1] + line[2]);
                        clauses.add(myN);

                }
                edgChecker++;
                } else {
                    System.out.println();
                    System.out.println(" Duplicate error");
                    System.exit(2);
                }
            } else{
                System.out.println();
                System.out.println(" Expected 'e' line error");
                System.exit(2);
            }


        }

        // edges checking
//        System.out.println(" edge check : " + numEdg);
        if (numEdg != edgChecker){
            System.out.println();
            System.out.println(" wrond number of edges");
            System.exit(2);
        }

        //debugging
//        System.out.println("numNode : " + numNod + " numCol : " + numCol + " numEdg : " + numEdg);
//        System.out.println("clauses : " + clauses);
//        System.out.println("nodes : "+nodes);

        return clauses;
    }

    // writing col format
    void write(List<List<String>> list, int numNod, int numVar){
        System.out.println("p edge "+ numNod + " "+ list.size());
        System.out.println("colours " + (numVar+1));
        for(List<String> l : list){
            System.out.println("e " + l.get(0) + " " + l.get(1));
        }

    }

}
