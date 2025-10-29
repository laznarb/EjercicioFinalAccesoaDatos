package Service;

import Model.Departamento;
import Model.Empleado;
import Repository.DepartamentoRepository;

import java.io.IOException;
import java.util.List;

public class DepartamentoService {
    DepartamentoRepository productoRepository;
    // Categoria Repository
    //CategoriaRepository .....

    public DepartamentoService(DepartamentoRepository departamentoRepository) {
        this.productoRepository = departamentoRepository;
    }

    public void crearNuevo(Departamento departamento) throws IOException, IOException {
        System.out.println("Creando departamento");
        productoRepository.guardar(departamento);
    }

    public List<Departamento> mostrarTodos() throws IOException {
        System.out.println("Mostrando todos los productos");
        return productoRepository.findAll();
    }

}
