import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client{

    public static void main(String[] args) {
        try{
           String msg;
        do{ 
               Socket socket = new Socket("localhost", 5555);

                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

                Scanner scanner = new Scanner(System.in);
            
                System.out.println("Digite uma mensagem (ou exit para sair)");
                msg = scanner.nextLine();
                if(msg.equalsIgnoreCase("exit")){
                    break;
                }
                output.writeUTF(msg);
                output.flush(); //liberar buffer para envio
                System.out.println(msg + " enviada");
                String receive;
                receive = input.readUTF();
                System.out.println("Rsposta: " + receive);

                input.close();
                output.close();
                socket.close();
            }while(!msg.equalsIgnoreCase("exit"));

        }catch(IOException ex){
            System.out.println("Erro no cliente: " + ex);
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
}