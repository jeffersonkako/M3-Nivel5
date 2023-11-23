package cadastrocliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class ThreadClient extends Thread {

    private final ObjectOutputStream out;
    private final ObjectInputStream in;

    private JTextArea textArea;
    private JFrame frame;

    public ThreadClient(ObjectInputStream in, ObjectOutputStream out) {
        this.in = in;
        this.out = out;
    }

    @Override
    public void run() {
        try {
            Boolean validate = (Boolean) in.readObject();
            Integer idUsuario = (Integer) in.readObject();

            if (validate) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

                String comando;
                String idPessoa;
                String idProduto;
                String quantidade;
                String valor_unitario;

                do {
                    System.out.print("======= Comandos ==============\n" +
                            "\n L - Listar " +
                            "\n F - Finalizar " +
                            "\n E - Entrada " +
                            "\n S - Saída " +
                            "\n\nDigite o comando: ");
                    comando = reader.readLine().toLowerCase();

                    switch (comando) {
                        case "e":
                            out.writeObject("e");

                            System.out.println("======== Entrada ========");

                            System.out.print("ID Pessoa: ");
                            idPessoa = reader.readLine();
                            out.writeObject(idPessoa);

                            System.out.print("ID Produto: ");
                            idProduto = reader.readLine();
                            out.writeObject(idProduto);

                            System.out.print("ID Usuario: " + idUsuario);
                            out.writeObject(idUsuario);
                            System.out.println("");

                            System.out.print("Quantidade: ");
                            quantidade = reader.readLine();
                            out.writeObject(quantidade);

                            System.out.print("Valor Unitário: ");
                            valor_unitario = reader.readLine();
                            out.writeObject(valor_unitario);

                            break;

                        case "s":
                            out.writeObject("s");

                            System.out.println("======== Saida ========");

                            System.out.print("ID Pessoa: ");
                            idPessoa = reader.readLine();
                            out.writeObject(idPessoa);

                            System.out.print("ID Produto: ");
                            idProduto = reader.readLine();
                            out.writeObject(idProduto);

                            System.out.print("ID Usuario: " + idUsuario);
                            out.writeObject(idUsuario);
                            System.out.println("");

                            System.out.print("Quantidade: ");
                            quantidade = reader.readLine();
                            out.writeObject(quantidade);

                            System.out.print("Valor Unitário: ");
                            valor_unitario = reader.readLine();
                            out.writeObject(valor_unitario);

                            break;

                        case "l":
                            out.writeObject("l");

                            try {
                                ArrayList<String> produtoList = (ArrayList<String>) in.readObject();
                                ArrayList<Integer> produtoQuantidade = (ArrayList<Integer>) in.readObject();

                                if (frame == null || !frame.isVisible()) {
                                    frame = new JFrame("Retorno do Servidor");
                                    textArea = new JTextArea(20, 50);
                                    textArea.setEditable(false);
                                    frame.add(new JScrollPane(textArea));
                                    frame.pack();
                                    frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                                    frame.setVisible(true);

                                    SwingUtilities.invokeLater(() -> {
                                        textArea.append("Lista de Produtos:\n");

                                        for (int i = 0; i < produtoList.size(); i++) {
                                            textArea.append(produtoList.get(i) + " " + produtoQuantidade.get(i) + "\n");
                                        }

                                        textArea.setCaretPosition(textArea.getDocument().getLength());
                                    });

                                } else {
                                    frame.setVisible(false);
                                }
                            } catch (ClassNotFoundException | IOException e) {
                                e.printStackTrace();
                            }
                            break;

                        case "f":
                            out.writeObject("f");

                            System.out.println("======== Programa finalizado ========");
                            break;

                        default:
                            System.out.println("Opção inválida. Escolha novamente.");
                            break;
                    }

                } while (!"f".equalsIgnoreCase(comando));

            } else {
                System.out.println("Usuario e senha não confere!");
            }

        } catch (Exception e) {
            if (!(e instanceof java.io.EOFException)) {
                System.out.println("======== Thread Finalizada ========");
            }
        }
    }
}
