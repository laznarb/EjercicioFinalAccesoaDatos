package Repository;

import Model.Departamento;

import java.io.IOException;
import java.util.List;

public interface DepartamentoRepository {

    void guardar(Departamento departamento) throws IOException;

    Departamento findByID(int id);

    List<Departamento> findAll() throws IOException;

}