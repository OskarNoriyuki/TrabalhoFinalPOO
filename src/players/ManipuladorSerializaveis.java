/*
    Classe ManipuladorSerializaveis
    Autor: Allan Ferreira
*/
package players;

import java.io.*;

public class ManipuladorSerializaveis {
    // Metodo que serializa uma instância em um arquivo
    public static void serializa(String nomeArquivo, Object objeto) {
        try {
            // Instancia os streams de escrita
            FileOutputStream fos = new FileOutputStream(nomeArquivo);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            // Escreve o objeto no arquivo
            oos.writeObject(objeto);

            // Fecha os streams
            oos.close();
            fos.close();
        } catch (IOException e) {
            System.err.println("Erro ao escrever o arquivo \'" + nomeArquivo + "\'!");
        }
    }

    // Metodo que desserializa um arquivo em uma instância da classe Objeto
    public static Object desserializa(String nomeArquivo) {
        Object objeto = null;
        
        try {
            // Instancia os streams de leitura
            FileInputStream fis = new FileInputStream(nomeArquivo);
            ObjectInputStream ois = new ObjectInputStream(fis);
            
            // Le o objeto do arquivo
            objeto = ois.readObject();

            // Fecha os streams
            ois.close();
            fis.close();
        } catch (IOException e) {
            System.err.println("O arquivo \'" + nomeArquivo + "\' nao pode ser aberto!");
        } catch (ClassNotFoundException e) {
            System.err.println("A classe nao foi encontrada!");
        }

        return objeto;
    }
}

