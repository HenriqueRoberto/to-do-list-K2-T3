// MODAL CRIAR TASK
const overlayCreateTask = document.getElementById("create-overlay");
const modalCreateTask = document.getElementById("create-task");

document.getElementById("btn-create-task").addEventListener("click", () => {
  overlayCreateTask.classList.add("active-display-flex");
  modalCreateTask.classList.add("active-display-flex");
});

overlayCreateTask.addEventListener("click", (e) => {
  if (!modalCreateTask.contains(e.target)) {
    overlayCreateTask.classList.remove("active-display-flex");
    modalCreateTask.classList.remove("active-display-flex");
  }
});

document.getElementById("task-form").addEventListener("submit", (e) => {
  e.preventDefault();

  const task = {
    id: Date.now(),
    name: document.getElementById("task-name").value,
    description: document.getElementById("description").value,
    deadline: document.getElementById("task-deadline").value,
    priority: Number(document.getElementById("task-priority").value),
    category: document.getElementById("task-category").value,
    status: document.getElementById("task-status").value,
  };

  saveTask(task);
  renderTasks();
  e.target.reset();

  overlayCreateTask.classList.remove("active-display-flex");
  modalCreateTask.classList.remove("active-display-flex");
});

// MODAL VER/EDITAR TASK
function openTask(id) {
  const task = workspaces[currentWorkspace].find((t) => t.id == id);
  const overlay = document.getElementById("view-overlay");
  const modal = document.getElementById("view-task-modal");

  document.getElementById("view-title").value = task.name;
  document.getElementById("view-description").value = task.description || "";
  document.getElementById("view-deadline").value = task.deadline || "";
  document.getElementById("view-priority").value = task.priority;
  document.getElementById("view-category").value = task.category || "";
  document.getElementById("view-status").value = task.status;

  applyStatusColor(modal, task.status);

  overlay.style.display = "flex";
  modal.dataset.id = id;
}

document.getElementById("view-overlay").addEventListener("click", (e) => {
  if (e.target === e.currentTarget) e.currentTarget.style.display = "none";
});

document.getElementById("save-task").addEventListener("click", () => {
  const modal = document.getElementById("view-task-modal");
  const task = workspaces[currentWorkspace].find(
    (t) => t.id == modal.dataset.id,
  );

  task.description = document.getElementById("view-description").value;
  task.deadline = document.getElementById("view-deadline").value;
  task.priority = Number(document.getElementById("view-priority").value);
  task.category = document.getElementById("view-category").value;
  task.status = document.getElementById("view-status").value;

  renderTasks();
  document.getElementById("view-overlay").style.display = "none";
});
