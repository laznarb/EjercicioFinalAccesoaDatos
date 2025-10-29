package Repository.Binary;

import Model.Departamento;

import java.io.*;

public class BinaryDepartamentoRepository {

    public void escribirDepartamento(Departamento d, String nombreArchivo) throws IOException {
        DataOutputStream dOutput = new DataOutputStream(new FileOutputStream(nombreArchivo, true));
        dOutput.writeInt(d.getId());
        dOutput.writeUTF(d.getNombre());
        dOutput.writeInt(d.getCapacidadMaxima());
        dOutput.close();
    }

    public void leerDepartamentos(String nombreArchivo) throws IOException {
        DataInputStream dInput = new DataInputStream(new FileInputStream(nombreArchivo));
        try {
            while (true) {
                int id = dInput.readInt();
                String nombre = dInput.readUTF();
                int capacidad = dInput.readInt();
                System.out.println(id + " - " + nombre + " - " + capacidad);
            }
        } catch (EOFException e) {
            // Fin del archivo
        }
        dInput.close();
    }
}

