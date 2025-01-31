import java.io.File;
import java.nio.file.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        Set<String> commands = Set.of("echo", "exit", "type");

        while (true) {
            System.out.print("$ "); // Print prompt first
            
            String input = scanner.nextLine();

            // Print program name *after* first prompt (only once)
            if (input.equals("print_program_name")) {
                String programName = (args.length > 0) ? new File(args[0]).getName() : "unknown";
                System.out.printf("Arg #0 (program name): %s%n", programName);
                continue;
            }

            if (input.equals("exit")) {
                System.exit(0);
            } else if (input.startsWith("echo ")) {
                System.out.println(input.substring(5));
            } else if (input.startsWith("type ")) {
                String arg = input.substring(5);
                if (commands.contains(arg)) {
                    System.out.printf("%s is a shell builtin%n", arg);
                } else {
                    String path = getPath(arg);
                    if (path == null) {
                        System.out.printf("%s: not found%n", arg);
                    } else {
                        System.out.printf("%s is %s%n", arg, path);
                    }
                }
            } else {
                String command = input.split(" ")[0];
                String path = getPath(command);
                if (path == null) {
                    System.out.printf("%s: command not found%n", command);
                } else {
                    Process p = new ProcessBuilder(path).start();
                    p.getInputStream().transferTo(System.out);
                }
            }
        }
    }

    private static String getPath(String command) {
        for (String path : System.getenv("PATH").split(":")) {
            Path fullPath = Path.of(path, command);
            if (Files.isRegularFile(fullPath)) {
                return fullPath.toString();
            }
        }
        return null;
    }
}
