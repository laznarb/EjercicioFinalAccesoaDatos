package Repository;

import Model.Departamento;

import java.io.IOException;
import java.util.List;

public interface DepartamentoRepository {

    void guardar(Departamento departamento) throws IOException;

    void guardarTodos(List<Departamento> departamentos) throws IOException;

    Departamento findByID(int id);

    List<Departamento> findAll() throws IOException;

}