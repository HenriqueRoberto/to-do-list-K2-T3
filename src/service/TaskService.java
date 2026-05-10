package service;

import model.Task;
import model.TaskStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TaskService {

  private final List<Task> tasks = new ArrayList<>();
  private int nextId = 1;

  public Task create(String name, String description, LocalDate endDate, Integer priority, String category, TaskStatus status) {
    validateRequired(name, priority);
    validatePriority(priority);
    validateEndDate(endDate);

    if (status == null) status = TaskStatus.TODO;

    Task task = new Task(null, name, description, endDate, priority, category, status);
    task.setId(nextId++);
    insertByPriority(task);

    return task;
  }

  public List<Task> findAll() {
    return new ArrayList<>(tasks);
  }

  public Task findById(Integer id) {
    if (id == null) return null;
    for (Task task : tasks) {
      if (task.getId() != null && task.getId().equals(id)) return task;
    }
    return null;
  }

  public Task update(Integer id, Task patch) {
    if (id == null || patch == null) return null;

    Task task = findById(id);
    if (task == null) return null;

    boolean priorityChanged = applyPatch(task, patch);

    if (priorityChanged) {
      tasks.remove(task);
      insertByPriority(task);
    }

    return task;
  }

  public boolean delete(Integer id) {
    Task task = findById(id);
    if (task == null) return false;
    return tasks.remove(task);
  }

  public List<Task> findByStatus(TaskStatus status) {
    if (status == null) status = TaskStatus.TODO;
    List<Task> result = new ArrayList<>();
    for (Task task : tasks) {
      if (status.equals(task.getStatus())) result.add(task);
    }
    return result;
  }

  public List<Task> findByCategory(String category) {
    List<Task> result = new ArrayList<>();
    if (category == null) return result;
    for (Task task : tasks) {
      if (category.equals(task.getCategory())) result.add(task);
    }
    return result;
  }

  public List<Task> findByPriority(Integer priority) {
    List<Task> result = new ArrayList<>();
    if (priority == null) return result;
    for (Task task : tasks) {
      if (priority.equals(task.getPriority())) result.add(task);
    }
    return result;
  }

  public List<Task> findByEndDate(LocalDate date) {
    List<Task> result = new ArrayList<>();
    for (Task task : tasks) {
      if (task.getEndDate() != null && task.getEndDate().equals(date)) result.add(task);
    }
    return result;
  }

  public List<Task> findUntilEndDate(LocalDate date) {
    List<Task> result = new ArrayList<>();
    for (Task task : tasks) {
      if (task.getEndDate() != null && !task.getEndDate().isAfter(date)) result.add(task);
    }
    return result;
  }

  // -------- PRIVATE --------

  private boolean applyPatch(Task task, Task patch) {
    boolean priorityChanged = false;

    if (patch.getName() != null && !patch.getName().equals(task.getName())) {
      task.setName(patch.getName());
    }
    if (!Objects.equals(patch.getDescription(), task.getDescription())) {
      task.setDescription(patch.getDescription());
    }
    if (!Objects.equals(patch.getEndDate(), task.getEndDate())) {
      if (patch.getEndDate() != null) validateEndDate(patch.getEndDate());
      task.setEndDate(patch.getEndDate());
    }
    if (patch.getPriority() != null && !patch.getPriority().equals(task.getPriority())) {
      validatePriority(patch.getPriority());
      task.setPriority(patch.getPriority());
      priorityChanged = true;
    }
    if (!Objects.equals(patch.getCategory(), task.getCategory())) {
      task.setCategory(patch.getCategory());
    }
    if (!Objects.equals(patch.getStatus(), task.getStatus())) {
      task.setStatus(patch.getStatus() != null ? patch.getStatus() : TaskStatus.TODO);
    }

    return priorityChanged;
  }

  private void insertByPriority(Task task) {
    for (int i = 0; i < tasks.size(); i++) {
      if (task.getPriority() <= tasks.get(i).getPriority()) {
        tasks.add(i, task);
        return;
      }
    }
    tasks.add(task);
  }

  private void validateRequired(String name, Integer priority) {
    if (name == null || name.isBlank()) throw new IllegalArgumentException("Name is required.");
    if (priority == null) throw new IllegalArgumentException("Priority is required.");
  }

  private void validatePriority(Integer priority) {
    if (priority < 1 || priority > 5) throw new IllegalArgumentException("Priority must be between 1 and 5.");
  }

  private void validateEndDate(LocalDate date) {
    if (date != null && date.isBefore(LocalDate.now())) {
      throw new IllegalArgumentException("End date cannot be in the past.");
    }
  }
}