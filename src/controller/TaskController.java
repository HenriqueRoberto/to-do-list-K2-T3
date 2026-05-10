package controller;

import model.Task;
import model.TaskStatus;
import service.TaskService;
import view.MenuView;

import java.time.LocalDate;
import java.util.List;

public class TaskController {

  private final TaskService service;
  private MenuView view;

  public TaskController(TaskService service) {
    this.service = service;
  }

  public void setView(MenuView view) {
    this.view = view;
  }

  public void handleCreate(String name, String description, LocalDate endDate, Integer priority, String category, TaskStatus status) {
    try {
      Task created = service.create(name, description, endDate, priority, category, status);
      view.showTaskCreated(created);
    } catch (IllegalArgumentException e) {
      view.showError(e.getMessage());
    }
  }

  public void handleListAll() {
    view.showTasks(service.findAll());
  }

  public Task handleFindById(Integer id) {
    return service.findById(id);
  }

  public void handleUpdate(Integer id, Task patch) {
    try {
      Task updated = service.update(id, patch);
      if (updated == null) view.showError("ID não encontrado.");
      else view.showUpdated();
    } catch (IllegalArgumentException e) {
      view.showError(e.getMessage());
    }
  }

  public void handleDelete(Integer id) {
    if (service.delete(id)) view.showDeleted();
    else view.showError("ID não encontrado.");
  }

  public void handleListByStatus(TaskStatus status) {
    view.showTasks(service.findByStatus(status));
  }

  public void handleListByCategory(String category) {
    view.showTasks(service.findByCategory(category));
  }

  public void handleListByPriority(Integer priority) {
    view.showTasks(service.findByPriority(priority));
  }

  public void handleListByEndDate(LocalDate date) {
    view.showTasks(service.findByEndDate(date));
  }

  public void handleListUntilEndDate(LocalDate date) {
    view.showTasks(service.findUntilEndDate(date));
  }
}