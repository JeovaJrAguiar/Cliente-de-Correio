package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Autor: Aguiar
 */
public class Server{
    private ServerSocket serverSocket;

    /**
     * Criar o servidor de conexões
     * @param porta, a porta utilizada
     * @throws IOException
     */
    private void criarServerSocket(int porta) throws IOException
    {
        serverSocket = new ServerSocket(porta);
    }

    /**
     * Esperar um pedido de conexao, fica 'escutando' a rede ate que seja estabelecida uma conexao
     * @return Socket, um objeto do tipo socket
     * @throws IOException
     */
    private Socket esperaConexao() throws IOException
    {
        Socket socket = serverSocket.accept();
        return socket;
    }

    /**
     * Vai encerrar o socker estabelecido entre cliente e servidor, ou seja, desconectar o cliente do servidor
     * @param socket
     * @throws IOException
     */
    private void fechaSocket(Socket socket) throws IOException
    {
        socket.close();
    }

    /**
     * Protocolo da Aplicação
     * @param socket
     * @throws IOException
     */
    private void trataConexao(Socket socket) throws IOException
    {   
        // CLIENT ------ SOCKET ------ SERVER
        // getInputStream e getOutputStream - pega o socket ao qual esta associado e vai criar um canal para que seja feita leitura/escrita
        // getInputStream - eh um stream de entrada de dados, eu vou utilizar quando eu precisar enviar alguma coisa pro meu socket
        
        try {
            // Criar streams de entrada e de saída.
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
            
            System.out.println("Tratando...");
            Mensagem m = (Mensagem) input.readObject();
            String operacao = m.getOperacao();
            Mensagem reply = null;
            if(operacao == "HELLO")
            {
                String nome = (String) m.getParam("nome");
                String sobrenome= (String) m.getParam("sobrenome");
                reply = new Mensagem("HELLOREPLY");

                if(nome == null || sobrenome == null)
                    reply.setStatus(Status.PARAMERROR);
                else
                {
                    reply.setStatus( Status.OK);
                    reply.setParam("mensagem", "Hello World" + nome + " " + sobrenome);
                }
            }

            output.writeObject(reply);
            output.flush();
            
            // Fechar streams de entrada e de saída.
            input.close();
            output.close();
        } catch (IOException e) {
            //tratar exececao
            System.out.println("Problema no tratamento da conexao com o cliente: " + socket.getInetAddress());
            System.out.println("Erro: " + e.getMessage());
        } catch(ClassNotFoundException e){
            System.out.println("Probelma na leitura do objeto.");
        }finally {
            // Final de tratamento do protocolo
            // Fechar socket de comunicação entre servidor/cliente.
            fechaSocket(socket);
        }
    }

    public static void main(String[] args) {

        try {
                Server server = new Server();
                System.out.println("Criando server... ");
                server.criarServerSocket(5555);//criacao do server socket na porta passada

                while (true){
                    System.out.println("-- ");
                    System.out.println("Aguardando conexao... ");
                    Socket socket = server.esperaConexao();//vai parar até que inicie a conexao
                    System.out.println("Cliente Conectado.");
                    server.trataConexao(socket);
                    System.out.println("Cliente Finalizado. ");
                }
        } catch (IOException e) {
            //tratar execao
            System.out.println("Entrou na excessoa dentro do main do server");
        }   
    }
}
