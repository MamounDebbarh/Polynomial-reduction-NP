
import java.io.*;
import java.util.*;

public class CNF {

    private int numVar= 0;


    public int getNumVar() {
        return numVar;
    }

    public void setNumVar(int numVar) {
        this.numVar = numVar;
    }


    // constructor
    public CNF() {
    }

    List<List<String>> read() throws IOException {

        // TODO If a line is required to start with a character it must be the first character in the line (no whitespace before it)
        // TODO I allow lines containing only whitespace anywhere in the file.
        // variables
        String str;
        int numCla = 0;
        boolean prob = true;
        boolean nocla = false;
        List<String> variables = new ArrayList<>();
        HashMap<String,String> allVar = new HashMap<>();
        List<List<String>> clauses = new ArrayList<>();

        // readers
        InputStreamReader in= new InputStreamReader(System.in);
        BufferedReader input = new BufferedReader(in);

        // reading line by line
        while ((str = input.readLine()) != null){


            // splitting the elements of my line
            String[] line = str.split("\\s+");

            // ignoring comments and new lines
            if((line[0].equals("c") || ((line[0].equals("\n")||line[0].equals("")) && line.length==1) ) && prob){
                continue;
            }

            // reading header
            if (prob) {
                if (line[0].equals("p") && line[1].equals("cnf") && line.length == 4) {
                    // setting number of clauses and variables
                    setNumVar(Integer.parseInt(line[2]));
                    numCla = Integer.parseInt(line[3]);
                    prob = false;
                    continue;
                } else {
                    System.out.println();
                    System.out.println(" Expected 'p cnf' line error");
                    System.exit(2);
                }
            }


            // Error checking
            // if no clauses
            if (numCla == 0){
                nocla = true;
            }
            // if new line after preamble
            if ((line[0].equals("\n") || line[0].equals("")) && input.readLine() != null){
                System.out.println();
                System.out.println(" Out of reach");
                System.exit(2);
            }

            // if comment after preamble
            if (line[0].equals("c")){
                System.out.println();
                System.out.println(" Comment after preamble");
                System.exit(2);
            }

            // Processing the clauses.
            /*
                  processing each element at a time each line at a time.
                  add variables to allVar and check if the allVar size is = numVar.
                  add variables to the HashMap and verify if it exists in the allVar.
                  when we have '0' create a clause with the variables HashMap (that is then set to null).
                  verify the number of clauses.
                  put this clause in the clauses Set.
              */
            for (String s : line) {
                if (s.equals("\n") || s.equals("")){
                    continue;
                }
                String var;
                if (!s.equals("0") ) {
                    // add variables to allVar.
                    if (s.charAt(0) == '-') {
                        var = s.replace("-", "");
//                        allVar.put(allVar.size(),line[i].replace("-",""));
                    } else {
                        var = s;
//                        allVar.put(allVar.size(),line[i]);
                    }

                    if (allVar.size() < getNumVar() && allVar.get(var) == null) {
                        allVar.put(var, var);
                        // add variables to the List.
                        variables.add(s);
                    } else if (allVar.get(var) != null) {
                        variables.add(s);
                    } else {
                        System.out.println();
                        System.out.println(" syntax error in cnf variable length");
                        System.exit(2);
                    }

                } else {
                    // create a clause with the variables HashMap (that is then set to null).
                    // put this clause in the clauses Set.
                    if (clauses.size() < numCla) {
                        clauses.add(variables);
                        variables = new ArrayList<>();
                    } else if (!nocla){
                        System.out.println();
                        System.out.println(" syntax error in cnf clause length");
                        System.exit(2);
                    }
                }
            }
        }
        // TODO variables <= to numVar
        if ((clauses.size() < numCla && numCla != 0)){
            // not allowing blank lines out of reach in the clauses.
            System.out.println();
            System.out.println(" syntax error in cnf clause or variable length");
            System.exit(2);
        }

        input.close();
        in.close();
        return clauses;
    }

    void write(List<List<String>> myl){

        // getting the number of clauses and variables.
        int numVar;
        int numCla = myl.size();

        // Checker Map
        String var;
        HashMap<String,String> allVar = new HashMap<>();

        // iterating through the lists and adding the variables to checker.
        for(List<String> l : myl){
            for(String s : l) {
                if (s.charAt(0) == '-') {
                    var = s.replace("-", "");
                } else {
                    var = s;
                }
                allVar.putIfAbsent(var, var);
            }
        }

        numVar = allVar.size();
        if (numVar == 0){
            numVar++;
        }
        // printing the cnf result
        System.out.println("p cnf "+numVar + " "+numCla);
        for(List<String> l : myl){
            for (String s : l) {
                System.out.print(s + " ");
            }
            System.out.println("0");
        }

    }
}



