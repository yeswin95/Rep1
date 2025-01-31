import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("$ ");  // Print the shell prompt
            String input = scanner.nextLine(); // Read user input

            // Handle "exit" to terminate the shell
            if (input.equals("exit")) {
                break;
            }

            // Handle "type" command
            if (input.startsWith("type ")) {
                String command = input.substring(5); // Extract the command after "type "

                // Check if it's a shell builtin
                if (command.equals("echo") || command.equals("exit") || command.equals("type")) {
                    System.out.println(command + " is a shell builtin");
                } else {
                    System.out.println(command + ": not found");
                }
            } else {
                // Handle other invalid commands
                System.out.println(input + ": command not found");
            }
        }

        scanner.close(); // Close scanner when exiting
    }
}
