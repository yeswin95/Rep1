import java.io.File;
import java.nio.file.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        Set<String> commands = Set.of("echo", "exit", "type");

        while (true) {
            System.out.print("$ "); // Print shell prompt

            String input = scanner.nextLine().trim(); // Read and trim input

            if (input.isEmpty()) continue; // Ignore empty input

            String[] parts = input.split(" ");
            String command = parts[0]; // Extract command name
            String[] commandArgs = Arrays.copyOfRange(parts, 1, parts.length); // Extract arguments

            if (command.equals("exit")) {
                System.exit(0);
            } else if (command.equals("echo")) {
                System.out.println(String.join(" ", commandArgs));
            } else if (command.equals("type")) {
                handleTypeCommand(commandArgs);
            } else {
                executeExternalCommand(command, commandArgs);
            }
        }
    }

    private static void handleTypeCommand(String[] args) {
        Set<String> builtins = Set.of("echo", "exit", "type");

        if (args.length != 1) {
            System.out.println("Usage: type <command>");
            return;
        }

        String command = args[0];
        if (builtins.contains(command)) {
            System.out.printf("%s is a shell builtin%n", command);
        } else {
            String path = getPath(command);
            if (path == null) {
                System.out.printf("%s: not found%n", command);
            } else {
                System.out.printf("%s is %s%n", command, path);
            }
        }
    }

    private static void executeExternalCommand(String command, String[] args) {
        String path = getPath(command);
        if (path == null) {
            System.out.printf("%s: command not found%n", command);
            return;
        }

        try {
            // Build full command with arguments
            List<String> commandList = new ArrayList<>();
            commandList.add(path); // Add executable path
            commandList.addAll(Arrays.asList(args)); // Add arguments

            ProcessBuilder processBuilder = new ProcessBuilder(commandList);
            Process process = processBuilder.start();

            process.getInputStream().transferTo(System.out);
            process.waitFor(); // Wait for process to finish
        } catch (Exception e) {
            System.out.println("Error executing command: " + command);
        }
    }

    private static String getPath(String command) {
        for (String path : System.getenv("PATH").split(":")) {
            Path fullPath = Path.of(path, command);
            if (Files.isRegularFile(fullPath) && Files.isExecutable(fullPath)) {
                return fullPath.toString();
            }
        }
        return null;
    }
}
