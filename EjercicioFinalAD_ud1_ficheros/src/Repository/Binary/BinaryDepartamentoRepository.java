package Repository.Binary;

import Model.Departamento;
import Repository.DepartamentoRepository;

import java.io.IOException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class BinaryDepartamentoRepository implements DepartamentoRepository {
    private static final String FILE_PATH = "data/departamentos.bin";

    public BinaryDepartamentoRepository() {
        new File("data").mkdirs();
    }

    @Override
    public void guardar(Departamento departamento) throws IOException {
        throw new UnsupportedOperationException("Usar guardarTodos para Binario.");
    }

    @Override
    public void guardarTodos(List<Departamento> departamentos) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(departamentos);
            System.out.println("Guardado en BINARIO: " + departamentos.size() + " departamentos.");
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Departamento> findAll() throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            System.out.println("Cargando departamentos desde BINARIO...");
            return (List<Departamento>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Archivo binario no encontrado. Retornando lista vacía.");
            return new ArrayList<>();
        } catch (ClassNotFoundException e) {
            // Mensaje soso para ClassNotFoundException
            throw new IOException("Error de clase al deserializar.", e);
        }
    }

    @Override
    public Departamento findByID(int id) {
        return null;
    }
}