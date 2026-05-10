package view;

import controller.TaskController;
import model.Task;
import model.TaskStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MenuView {

  private final TaskController controller;
  private final InputView input;

  public MenuView(TaskController controller) {
    this.controller = controller;
    this.input = new InputView(new Scanner(System.in));
  }

  public void start() {
    int option;
    do {
      printMainMenu();
      option = input.readInt("Escolha uma opção: ");
      handleMainOption(option);
      System.out.println();
    } while (option != 0);
  }

  // -------- MENUS --------

  private void printMainMenu() {
    System.out.println("=== TODO LIST (CLI) ===");
    System.out.println("1 - Criar tarefa");
    System.out.println("2 - Listar");
    System.out.println("3 - Atualizar tarefa");
    System.out.println("4 - Deletar tarefa");
    System.out.println("0 - Sair");
  }

  private void handleMainOption(int option) {
    switch (option) {
      case 1 -> createFlow();
      case 2 -> listMenuFlow();
      case 3 -> updateFlow();
      case 4 -> deleteFlow();
      case 0 -> System.out.println("Saindo...");
      default -> System.out.println("Opção inválida.");
    }
  }

  private void listMenuFlow() {
    int opt;
    do {
      printListMenu();
      opt = input.readInt("Escolha: ");
      handleListOption(opt);
      System.out.println();
    } while (opt != 0);
  }

  private void printListMenu() {
    System.out.println("== LISTAR ==");
    System.out.println("1 - Todas");
    System.out.println("2 - Por status");
    System.out.println("3 - Por categoria");
    System.out.println("4 - Por prioridade");
    System.out.println("5 - Por data exata");
    System.out.println("6 - Até data");
    System.out.println("0 - Voltar");
  }

  private void handleListOption(int opt) {
    switch (opt) {
      case 1 -> controller.handleListAll();
      case 2 -> {
        TaskStatus status = input.readStatusRequired("Status (TODO/DOING/DONE): ");
        controller.handleListByStatus(status);
      }
      case 3 -> {
        String category = input.readLine("Categoria: ");
        controller.handleListByCategory(category);
      }
      case 4 -> {
        int priority = input.readInt("Prioridade 1-5: ");
        controller.handleListByPriority(priority);
      }
      case 5 -> {
        LocalDate date = input.readDateRequired("Data (dd/MM/yyyy): ");
        controller.handleListByEndDate(date);
      }
      case 6 -> {
        LocalDate date = input.readDateRequired("Data (dd/MM/yyyy): ");
        controller.handleListUntilEndDate(date);
      }
      case 0 -> {}
      default -> System.out.println("Opção inválida.");
    }
  }

  // -------- FLOWS --------

  private void createFlow() {
    System.out.println("== Criar tarefa ==");

    String name        = input.readLine("Nome (obrigatório): ");
    String description = input.readOptionalLine("Descrição (opcional): ");
    LocalDate endDate  = input.readOptionalDate("Data término dd/MM/yyyy (opcional): ");
    int priority       = input.readInt("Prioridade 1-5 (obrigatório): ");
    String category    = input.readOptionalLine("Categoria (opcional): ");
    TaskStatus status  = input.readOptionalStatus("Status TODO/DOING/DONE (opcional, enter = TODO): ");

    if (description != null && description.isBlank()) description = null;
    if (category    != null && category.isBlank())    category    = null;

    controller.handleCreate(name, description, endDate, priority, category, status);
  }

  private void updateFlow() {
    System.out.println("== Atualizar tarefa ==");

    Integer id = input.readInt("ID: ");
    if (controller.handleFindById(id) == null) {
      System.out.println("ID não encontrado.");
      return;
    }

    String name        = input.readOptionalLine("Novo nome (enter para ignorar): ");
    String description = input.readOptionalLine("Nova descrição (enter para ignorar): ");
    LocalDate endDate  = input.readOptionalDate("Nova data dd/MM/yyyy (enter para ignorar): ");
    Integer priority   = input.readOptionalInt("Nova prioridade 1-5 (enter para ignorar): ");
    String category    = input.readOptionalLine("Nova categoria (enter para ignorar): ");
    TaskStatus status  = input.readOptionalStatus("Novo status TODO/DOING/DONE (enter para ignorar): ");

    Task patch = new Task(null, null, null, null, null, null, null);
    if (!name.isBlank())        patch.setName(name);
    if (!description.isBlank()) patch.setDescription(description);
    if (endDate != null)        patch.setEndDate(endDate);
    if (priority != null)       patch.setPriority(priority);
    if (!category.isBlank())    patch.setCategory(category);
    if (status != null)         patch.setStatus(status);

    controller.handleUpdate(id, patch);
  }

  private void deleteFlow() {
    System.out.println("== Deletar tarefa ==");
    int id = input.readInt("ID: ");
    controller.handleDelete(id);
  }

  // -------- DISPLAY (chamado pelo controller) --------

  public void showTaskCreated(Task task) {
    System.out.println("Criada: ID " + task.getId());
  }

  public void showTasks(List<Task> tasks) {
    if (tasks.isEmpty()) {
      System.out.println("(vazio)");
      return;
    }
    for (Task t : tasks) {
      System.out.printf("ID: %d | %s | %s | Prioridade: %d | Status: %s | Término: %s | Categoria: %s%n",
          t.getId(), t.getName(), t.getDescription(),
          t.getPriority(), t.getStatus(), t.getEndDate(), t.getCategory());
    }
  }

  public void showUpdated() {
    System.out.println("Atualizada.");
  }

  public void showDeleted() {
    System.out.println("Deletada.");
  }

  public void showError(String message) {
    System.out.println("Erro: " + message);
  }
}