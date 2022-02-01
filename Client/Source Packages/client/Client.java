import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.System.Logger;
import java.net.Socket;
import java.util.logging.Level;

public class Client{
    public static void main(String[] args) {
        try {
            //cria conexao entre cliente e o servidor
            System.out.println("Estabelecendo conexao...");
            Socket socket = new Socket("localhost", 5555); 
            System.out.println("Conexao estabelecida.");

            //para poder manipular o socket, preciso criar streams de entrada e saida
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

            System.out.println("Enviando mensagem...");
            String msg = "HELLO";
            output.writeUTF(msg);
            output.flush(); //libera buffer para envio
            System.out.println("Mensagem " + msg + " enviada.");

            msg = input.readUTF();
            System.out.println("Resposta: " + msg);

            input.close();
            output.close();
            socket.close();
        } catch (IOException ex) {
            System.out.println("Erro no cliente: " + ex);
            //Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            //Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}