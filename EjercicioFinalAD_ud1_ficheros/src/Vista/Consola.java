package Vista;

import Model.Departamento;
import Model.Empleado;
import Service.DepartamentoService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Consola {

    // Atributo requerido: servicio de productos con crearNuevo y listarTodos
    private final DepartamentoService dService;

    public Consola(DepartamentoService dService) {
        this.dService = dService;
    }

    public void iniciar() {
        try (Scanner sc = new Scanner(System.in)) {
            int opcion;
            do {
                mostrarMenu();
                opcion = leerEntero(sc, "Elige opción: ");
                switch (opcion) {
                    case 1 -> anadirDepartamento(sc);
                    case 2 -> listarDepartamentos();
                    case 0 -> System.out.println("Saliendo... ¡Hasta luego!");
                    default -> System.out.println("Opción no válida.");
                }
            } while (opcion != 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void mostrarMenu() {
        System.out.println("\n===== MENÚ DEPARTAMENTOS =====");
        System.out.println("1) Añadir departamento");
        System.out.println("2) Listar departamentos");
        System.out.println("0) Salir");
    }


    private void anadirDepartamento(Scanner sc) throws IOException {
        System.out.println("\n-- Nuevo departamento --");
        int id = leerEntero(sc, "Id (int): ");

        System.out.print("Nombre (String): ");
        String nombre = sc.nextLine().trim();

        int capacidadMaxima = leerEntero(sc, "capacidadMaxima (int): ");

        Departamento d = new Departamento(id, nombre, capacidadMaxima);
        dService.crearNuevo(d);
        System.out.println("✅ Departamento añadido.");
    }

    private void listarDepartamentos() throws IOException {
        System.out.println("\n-- Listado de departamentos --");
        List<Departamento> lista = dService.mostrarTodos();

        if (lista.isEmpty()) {
            System.out.println("(sin departamentos)");
            return;
        }

        for (Departamento d : lista) {
            System.out.println(d);
            String emps = d.getEmpleados().stream()
                    .map(Empleado::getNombre)
                    .collect(Collectors.joining(", "));
            System.out.println("   -> Empleados Asignados: " + (emps.isEmpty() ? "Ninguno" : emps));
        }
    }

    // Helpers robustos para leer numéricos desde consola:
    private int leerEntero(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String linea = sc.nextLine().trim();
            try {
                return Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                System.out.println("❌ Debe ser un número entero. Inténtalo de nuevo.");
            }
        }
    }

    private double leerDouble(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String linea = sc.nextLine().trim().replace(",", "."); // admite coma decimal
            try {
                return Double.parseDouble(linea);
            } catch (NumberFormatException e) {
                System.out.println("❌ Debe ser un número decimal. Inténtalo de nuevo.");
            }
        }
    }

}