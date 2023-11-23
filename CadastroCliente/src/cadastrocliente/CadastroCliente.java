package cadastrocliente;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CadastroCliente {

   public static void main(String[] args) {
    try {
        Socket socket = new Socket("localhost", 4321);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("===============================");
        System.out.print("Login: ");
        String login = reader.readLine();
        System.out.print("Senha: ");
        String senha = reader.readLine();
        System.out.println("===============================");



        out.writeObject(login);
        out.writeObject(senha);
        out.flush();


             ThreadClient threadClient = new ThreadClient(in,out);
        threadClient.start();




    } catch (IOException ex) {
        Logger.getLogger(CadastroCliente.class.getName()).log(Level.SEVERE, null, ex);
    }
}


}
