package view;

import model.TaskStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputView {

  private final Scanner scanner;
  private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

  public InputView(Scanner scanner) {
    this.scanner = scanner;
  }

  public int readInt(String label) {
    while (true) {
      System.out.print(label);
      try {
        return Integer.parseInt(scanner.nextLine().trim());
      } catch (NumberFormatException e) {
        System.out.println("Digite um número inteiro.");
      }
    }
  }

  public Integer readOptionalInt(String label) {
    System.out.print(label);
    String input = scanner.nextLine().trim();
    if (input.isEmpty()) return null;
    try {
      return Integer.parseInt(input);
    } catch (NumberFormatException e) {
      System.out.println("Valor inválido. Ignorando.");
      return null;
    }
  }

  public String readLine(String label) {
    while (true) {
      System.out.print(label);
      String input = scanner.nextLine();
      if (!input.isBlank()) return input;
      System.out.println("Campo obrigatório.");
    }
  }

  public String readOptionalLine(String label) {
    System.out.print(label);
    return scanner.nextLine();
  }

  public LocalDate readDateRequired(String label) {
    while (true) {
      System.out.print(label);
      String input = scanner.nextLine().trim();
      try {
        return LocalDate.parse(input, dateFormatter);
      } catch (DateTimeParseException e) {
        System.out.println("Data inválida. Use dd/MM/yyyy.");
      }
    }
  }

  public LocalDate readOptionalDate(String label) {
    System.out.print(label);
    String input = scanner.nextLine().trim();
    if (input.isEmpty()) return null;
    try {
      return LocalDate.parse(input, dateFormatter);
    } catch (DateTimeParseException e) {
      System.out.println("Data inválida. Ignorando.");
      return null;
    }
  }

  public TaskStatus readStatusRequired(String label) {
    while (true) {
      System.out.print(label);
      String input = scanner.nextLine().trim().toUpperCase();
      try {
        return TaskStatus.valueOf(input);
      } catch (IllegalArgumentException e) {
        System.out.println("Status inválido. Use TODO, DOING ou DONE.");
      }
    }
  }

  public TaskStatus readOptionalStatus(String label) {
    System.out.print(label);
    String input = scanner.nextLine().trim();
    if (input.isEmpty()) return null;
    try {
      return TaskStatus.valueOf(input.toUpperCase());
    } catch (IllegalArgumentException e) {
      System.out.println("Status inválido. Ignorando.");
      return null;
    }
  }
}