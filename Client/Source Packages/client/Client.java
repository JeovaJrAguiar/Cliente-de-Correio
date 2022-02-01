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
            Socket socket = new Socket("localhost", 5555);    
            //para poder manipular o socket, preciso criar streams de entrada e saida
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());

            String msg = "HELLO";
            output.writeUTF(msg);
            msg = input.readUTF();
            System.out.println("Resposta: " + msg);

            input.close();
            output.close();
            socket.close();

        } catch (IOException ex) {
            System.out.println("Entrou na excecao do main dentro do client");
            //Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            //Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}