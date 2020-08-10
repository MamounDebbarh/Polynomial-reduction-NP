import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length > 0){
            // We choose which reduction we are running depending of the script executable.
            switch(args[0]) {
                case "1":
                    run1();
                    break;
                case "2":
                    run2();
                    break;
                case "3":
                    run3();
                    break;
                default:
                    // code block
            }
        } else {
            System.out.println(" Error!!!");
            System.exit(1);
        }
    }

    // run1 is the function that runs the SAT to SAT3 reduction.
    private static void run1() throws IOException {
        Sattothreesat algo = new Sattothreesat();
        algo.reduction();
    }

    // run2 is the function that runs the k-COL to SAT reduction.
    private static void run2() throws IOException {
        Coltosat algo = new Coltosat();
        algo.reduction();
    }

    // run3 is the function that runs the 3SAT to k-COL reduction.
    private static void run3() throws IOException {
        Threesattocol algo = new Threesattocol();
        algo.reduction();
    }
}
