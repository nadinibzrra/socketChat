import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import javax.sound.sampled.Port;

public class Server {

    private ServerSocket serverSocket;

    private void createServerSocket(int port) throws IOException{
        serverSocket = new ServerSocket(port);
    }

    private Socket waitConnection() throws IOException{
        Socket socket = serverSocket.accept();
        return socket;
    }

    private void closeSocket(Socket s) throws IOException{
        s.close();
    }

    private void treatsConnection(Socket socket) throws IOException{
        try{
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

            String msg = input.readUTF();
            System.out.println("Mensagem recebida: " + msg);
            String reply;
            Scanner scanner = new Scanner(System.in);
            reply = scanner.nextLine();
            output.writeUTF(reply);
            output.flush();

            //fechar streams
            input.close();
            output.close();
        }catch(IOException e){
            //trata erros
            System.out.println("Problema no tratamento de conexão com o cliente: " + socket.getInetAddress());
            System.out.println("Error: " + e.getMessage());
        }finally{
            //fechar Socket
            closeSocket(socket);
        }
    
    }

    public static void main(String[] args) {
        try{
            Server server = new Server();
            System.out.println("Aguardando conexão...");
            server.createServerSocket(5555);

            while(true){
                System.out.println("Aguardando Cliente...");    
                Socket socket = server.waitConnection(); //protocolo
                System.out.println("Cliente conectado.");
                server.treatsConnection(socket); 
            }
            
        }catch(IOException e){
            System.out.println("Erro ao executar");
        }
    System.out.println("Cliente finalizado");
    }
}
