package app;

import controller.TaskController;
import service.TaskService;
import view.MenuView;

public class Main {
  public static void main(String[] args) {
    TaskService    service    = new TaskService();
    TaskController controller = new TaskController(service);
    MenuView       view       = new MenuView(controller);
    controller.setView(view);
    view.start();
  }
}