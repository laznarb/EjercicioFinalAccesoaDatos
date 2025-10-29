package Repository.CSV;
import Model.Departamento;
import Repository.DepartamentoRepository;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CSVDepartamentoRepository implements DepartamentoRepository {
    final String nombreArchivo;

    public CSVDepartamentoRepository(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }


    @Override
    public void guardar(Departamento departamento) throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter(nombreArchivo, true));
        pw.println(departamento.getId()+","+departamento.getNombre()+","+departamento.getCapacidadMaxima());
        pw.close();
    }

    @Override
    public Departamento findByID(int id) {

        return null;
    }

    @Override
    public List<Departamento> findAll() throws IOException {

        List<Departamento> departamentosEncontrados = new ArrayList<>();
        Scanner sc = new Scanner(new FileReader(nombreArchivo));

        while(sc.hasNextLine()){
            String[] datos = sc.nextLine().split(",");
            departamentosEncontrados.add(new Departamento(Integer.parseInt(datos[0]), datos[1], Integer.parseInt(datos[2])));
        }

        sc.close();
        return departamentosEncontrados;
    }
}
