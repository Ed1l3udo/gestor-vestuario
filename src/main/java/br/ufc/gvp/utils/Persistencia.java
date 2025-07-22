package br.ufc.gvp.utils;

import java.io.*;

public class Persistencia {

    public static void salvar(Object objeto, String caminho) {
        if (objeto == null) {
            System.err.println("Tentando salvar objeto nulo em " + caminho);
            return;
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminho))) {
            oos.writeObject(objeto);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> T carregar(String caminho) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminho))) {
            return (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
}
