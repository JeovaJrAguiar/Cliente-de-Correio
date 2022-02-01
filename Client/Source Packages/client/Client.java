import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.System.Logger;
import java.net.Socket;
import java.util.logging.Level;

import server.Mensagem;

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

                /* Tratar a conversação entre cliente e servidor (tratar protocolo).
                    HELLO
                    nome: string
                    sobrenome : String

                    HELLOREPLY
                    OK, ERRO, PARAMERROR
                    mensagem: String
                */

            Mensagem m = new Mensagem("HELLO");
            //m.setStatus(Status.SOLICITACAO);
            m.setParam("nome", "Eduardo");
            m.setParam("sobrenome", "Dipp");

            output.writeObject(m);
            output.flush(); //libera buffer para envio

            System.out.println("Mensagem " + m + " enviada");

            m = (Mensagem) input.readObject();
            System.out.println("Resposta: " + m);

            if(m.getStatus() == Status.OK){

            }

            input.close();
            output.close();
            socket.close();
        } catch (IOException ex) {
            System.out.println("Erro no cliente: " + ex);
            //Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            //Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex){
            System.out.println("Erro no cliente: " + ex.getMessage());
            //Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}