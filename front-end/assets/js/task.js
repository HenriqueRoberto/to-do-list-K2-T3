// STATE
let filters = {
  priority: null,
  category: null,
  status: null,
};

// CRUD
function getTasks() {
  return workspaces[currentWorkspace] || [];
}

function saveTask(task) {
  if (!currentWorkspace) return;
  workspaces[currentWorkspace].push(task);
}

function deleteTask(id) {
  workspaces[currentWorkspace] = workspaces[currentWorkspace].filter(
    (t) => t.id !== id,
  );
  renderTasks();
}

// CARD
function buildCard() {
  const template = document.getElementById("task-template");
  const card = template.cloneNode(true);

  card.removeAttribute("id");
  card.classList.remove("task-template");
  card.style.display = "block";

  return card;
}

function fillCardData(card, task) {
  card.querySelector(".task-title").textContent = task.name;
  card.querySelector(".task-date").innerHTML = task.deadline
    ? `📅 ${task.deadline}`
    : `📅`;
  card.querySelector(".task-priority").textContent = task.priority;
}

function applyStatusColor(card, status) {
  const header = card.querySelector(".task-card-header");
  if (status === "todo") header.style.background = "#0b3c53";
  if (status === "doing") header.style.background = "#d4aa28";
  if (status === "done") header.style.background = "#2e8b57";
}

function attachCardEvents(card, task) {
  card.addEventListener("click", () => {
    if (deleteMode) {
      if (confirm("Deseja excluir esta tarefa?")) deleteTask(task.id);
      return;
    }
    openTask(task.id);
  });
}

function createTaskCard(task) {
  const card = buildCard();
  fillCardData(card, task);
  applyStatusColor(card, task.status);
  attachCardEvents(card, task);
  return card;
}

// RENDER
function renderTasks() {
  if (!currentWorkspace) return;

  const todo = document.getElementById("todo");
  const doing = document.getElementById("doing");
  const done = document.getElementById("done");

  todo.innerHTML = doing.innerHTML = done.innerHTML = "";

  let tasks = getTasks();

  if (filters.priority)
    tasks = tasks.filter((t) => t.priority == filters.priority);
  if (filters.category)
    tasks = tasks.filter((t) => t.category === filters.category);
  if (filters.status) tasks = tasks.filter((t) => t.status === filters.status);

  tasks.sort((a, b) => a.priority - b.priority);

  tasks.forEach((task) => {
    const card = createTaskCard(task);
    if (task.status === "todo") todo.appendChild(card);
    if (task.status === "doing") doing.appendChild(card);
    if (task.status === "done") done.appendChild(card);
  });

  updateCategoryDropdown();
}

function updateCategoryDropdown() {
  const menu = document.querySelectorAll(".dropdown-menu")[0];
  menu.innerHTML = "";

  const categories = [
    ...new Set(
      workspaces[currentWorkspace].map((t) => t.category).filter(Boolean),
    ),
  ];

  categories.forEach((cat) => {
    const li = document.createElement("li");
    const a = document.createElement("a");

    a.classList.add("dropdown-item");
    a.href = "#";
    a.textContent = cat;

    a.addEventListener("click", (e) => {
      e.preventDefault();
      filters.category = filters.category === cat ? null : cat;
      renderTasks();
    });

    li.appendChild(a);
    menu.appendChild(li);
  });
}
