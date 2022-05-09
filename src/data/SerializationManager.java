/*
	Classe Serialization Maganer
	Descricao: serializa e deserializa objetos
	Autores: Allan Ferreira, Pedro Alves e Oskar Akama
*/
package data;

import java.io.*;

public class SerializationManager {
    // Metodo que serializa uma instância em um arquivo
    public static void serialize(String filePath, Object object) {
        try {
            // Instancia os streams de escrita
            FileOutputStream fos = new FileOutputStream(filePath);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            // Escreve o objeto no arquivo
            oos.writeObject(object);

            // Fecha os streams
            oos.close();
            fos.close();
        } catch (IOException e) {
            System.err.println("Erro ao escrever o arquivo \'" + filePath + "\'!");
        }
    }

    // Metodo que desserializa um arquivo em uma instância da classe Objeto
    public static Object deserialize(String filePath) {
        Object objeto = null;
        
        try {
            // Instancia os streams de leitura
            FileInputStream fis = new FileInputStream(filePath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            
            // Le o objeto do arquivo
            objeto = ois.readObject();

            // Fecha os streams
            ois.close();
            fis.close();
        } catch (IOException e) {
            System.err.println("O arquivo \'" + filePath + "\' nao pode ser aberto!");
        } catch (ClassNotFoundException e) {
            System.err.println("A classe nao foi encontrada!");
        }

        return objeto;
    }
}

