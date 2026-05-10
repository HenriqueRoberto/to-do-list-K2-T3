package app;

import controller.TaskController;
import view.MenuView;

public class Main {
  public static void main(String[] args) {
    TaskController controller = new TaskController();
    MenuView menu = new MenuView(controller);
    menu.start();
  }
}
