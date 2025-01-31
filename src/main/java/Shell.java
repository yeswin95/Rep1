import java.util.Scanner;

public class Shell {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Print prompt without newline
            System.out.print("$ ");

            // Read user input
            if (!scanner.hasNextLine()) {
                break;  // Handle EOF (Ctrl+D)
            }

            String command = scanner.nextLine();

            // Print error message
            System.out.println(command + ": command not found");
        }

        scanner.close();
    }
}
