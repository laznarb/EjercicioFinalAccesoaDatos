package Repository.Binary;

import Model.Departamento;
import Repository.DepartamentoRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BinaryDepartamentoRepository implements DepartamentoRepository {

    final String nombreArchivo;

    public BinaryDepartamentoRepository(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    @Override
    public void guardar(Departamento d) throws IOException {
        DataOutputStream dOutput = new DataOutputStream(new FileOutputStream(nombreArchivo, true));
        dOutput.writeInt(d.getId());
        dOutput.writeUTF(d.getNombre());
        dOutput.writeInt(d.getCapacidadMaxima());
        dOutput.close();
    }

    @Override
    public List<Departamento> findAll() throws IOException {
        List<Departamento> departamentosEncontrados = new ArrayList<>();
        DataInputStream dInput = new DataInputStream(new FileInputStream(nombreArchivo));
        try {
            while (true) {
                int id = dInput.readInt();
                String nombre = dInput.readUTF();
                int capacidad = dInput.readInt();
                departamentosEncontrados.add(new Departamento(id, nombre, capacidad));
                // System.out.println(id + " - " + nombre + " - " + capacidad); // Se elimina el System.out para cumplir con el contrato de la interfaz.
            }
        } catch (EOFException e) {
            // Fin del archivo
        } finally {
            dInput.close();
        }
        return departamentosEncontrados;
    }
}

