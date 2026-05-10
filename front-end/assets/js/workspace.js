const workspaces = {};
let currentWorkspace = null;
let deleteMode = false;

// WORKSPACE
function createWorkspace() {
  const div = document.createElement("div");
  div.classList.add("workspace-area-group");

  const input = document.createElement("input");
  input.type = "text";
  input.value = "Workspace";
  input.placeholder = "Nova área de trabalho";
  input.maxLength = 20;
  input.classList.add("workspace-task");
  input.readOnly = true;

  const span = document.createElement("span");
  span.textContent = "|";

  div.appendChild(input);
  div.appendChild(span);
  document.getElementById("workspace-area").appendChild(div);

  const workspaceId = Date.now().toString();
  workspaces[workspaceId] = [];

  addWorkspaceEvents(div, workspaceId);

  if (!currentWorkspace) selectWorkspace(workspaceId);
}

function selectWorkspace(id) {
  currentWorkspace = id;
  renderTasks();
}

function addWorkspaceEvents(element, id) {
  const input = element.querySelector("input");

  element.addEventListener("click", () => selectWorkspace(id));

  element.addEventListener("dblclick", () => {
    input.readOnly = false;
    input.focus();
  });

  input.addEventListener("blur", () => {
    input.readOnly = true;
  });
  input.addEventListener("keydown", (e) => {
    if (e.key === "Enter") input.blur();
  });
}

// INIT
document
  .getElementById("btn-add-aside")
  .addEventListener("click", createWorkspace);
document.getElementById("btn-trash").addEventListener("click", () => {
  deleteMode = !deleteMode;
  document.body.classList.toggle("delete-mode");
});

createWorkspace();
