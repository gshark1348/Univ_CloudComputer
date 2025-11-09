import java.io.*;
import java.net.*;
import java.util.*;

public class CalculatorClient {
    public static void main(String[] args) {
        String serverIP = "localhost";
        int port = 1234;

        // 서버 정보 읽기 (server_info.dat)
        try (BufferedReader br = new BufferedReader(new FileReader("server_info.dat"))) {
            String line = br.readLine();
            if (line != null) {
                String[] info = line.split(":");
                serverIP = info[0];
                port = Integer.parseInt(info[1]);
            }
        } catch (Exception e) {
            System.out.println("server_info.dat not found, using default: localhost:1234");
        }

        try (Socket socket = new Socket(serverIP, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner sc = new Scanner(System.in)) {

            System.out.println("Connected to server " + serverIP + ":" + port);
            System.out.println("Enter commands (ADD/SUB/MUL/DIV num1 num2), or EXIT");

            while (true) {
                System.out.print("> ");
                String input = sc.nextLine();
                out.println(input);
                if (input.equalsIgnoreCase("EXIT")) break;

                String response = in.readLine();
                System.out.println("Server: " + response);
            }

        } catch (IOException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
    }
}
