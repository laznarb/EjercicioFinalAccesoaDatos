import Repository.CSV.CSVDepartamentoRepository;
import Repository.DepartamentoRepository;
import Service.DepartamentoService;
import Vista.Consola;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        // Leo del properties el valor modo (p.e. modo=CSV)

        // En funcion del modo elegido instancio uno u otro reposiotry
        DepartamentoRepository dRepository;

        //if( modoleido.equals("CSV))
             dRepository = new CSVDepartamentoRepository("departamento.csv");
        //else if( modoleido.equals("XML))
            // dRepository = new XMLDepartamentoRepository();

        // Inicializo el Service
        DepartamentoService dServicio = new DepartamentoService(dRepository);

        // Inicializo la consola
        Consola consola = new Consola(dServicio);
        consola.iniciar();
    }
}