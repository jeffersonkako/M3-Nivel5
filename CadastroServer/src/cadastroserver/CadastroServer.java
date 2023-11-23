
package cadastroserver;

import cadastroserver.controller.UsuarioJpaController;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class CadastroServer {


    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CadastroServerPU");
        UsuarioJpaController ctrlUsu = new UsuarioJpaController(emf);

        try (ServerSocket serverSocket = new ServerSocket(4321)) {
            System.out.println("======== SERVIDOR CONECTADO - PORTA 4321 ========");
            while (true) {
                Socket socket = serverSocket.accept();

                CadastroThread teste = new CadastroThread(ctrlUsu, socket);
                teste.start();
                System.out.println("======== thread iniciado ========");

            }
        } catch (IOException ex) {
            Logger.getLogger(CadastroServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
