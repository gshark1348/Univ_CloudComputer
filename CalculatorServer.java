import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class CalculatorServer {
    private static final int DEFAULT_PORT = 1234;

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(5);
        try (ServerSocket serverSocket = new ServerSocket(DEFAULT_PORT)) {
            System.out.println("Server started on port " + DEFAULT_PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                pool.execute(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler implements Runnable {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("Received: " + line);
                String response = processRequest(line.trim());
                out.println(response);
                if (line.equalsIgnoreCase("EXIT")) break;
            }
        } catch (IOException e) {
            System.err.println("Client connection error: " + e.getMessage());
        }
    }

    private String processRequest(String req) {
        try {
            String[] parts = req.split(" ");
            if (parts.length < 3) return "ERR ARGUMENT_MISSING";
            if (parts.length > 3) return "ERR TOO MANY ARGUMENTS";
            String cmd = parts[0].toUpperCase();
            double a = Double.parseDouble(parts[1]);
            double b = Double.parseDouble(parts[2]);

            switch (cmd) {
                case "ADD": return "RES OK " + (a + b);
                case "SUB": return "RES OK " + (a - b);
                case "MUL": return "RES OK " + (a * b);
                case "DIV":
                    if (b == 0) return "ERR DIVIDE_BY_ZERO";
                    return "RES OK " + (a / b);
                default:
                    return "ERR UNKNOWN_COMMAND";
            }
        } catch (NumberFormatException e) {
            return "ERR INVALID_NUMBER";
        } catch (Exception e) {
            return "ERR UNKNOWN_ERROR";
        }
    }
}
