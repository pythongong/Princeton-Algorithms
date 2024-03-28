package assign0;
public class HelloGoodbye {
    public static void main(String[] args) {
        // Check if exactly two command-line arguments were provided
        if (args.length != 2) {
            return;
        }

        // Extract the names from the command-line arguments
        String name1 = args[0];
        String name2 = args[1];

        // Print hello messages with the names in the same order as command-line arguments
        System.out.println("Hello " + name1 + " and " + name2 + ".");

        // Print goodbye messages with the names in reverse order
        System.out.println("Goodbye " + name2 + " and " + name1 + ".");
    }
}
