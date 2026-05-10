// DROPDOWN
const dropdownButtons = document.querySelectorAll(".btn-dropdown");
const dropdownMenus = document.querySelectorAll(".dropdown-menu");

dropdownButtons.forEach((btn) => {
  btn.addEventListener("click", () => {
    dropdownMenus.forEach((menu) => menu.classList.remove("active-dropdown"));
    btn.nextElementSibling.classList.add("active-dropdown");
  });
});

document.addEventListener("click", (e) => {
  if (!e.target.closest(".dropdown")) {
    dropdownMenus.forEach((menu) => menu.classList.remove("active-dropdown"));
  }
});

// FILTRO PRIORIDADE
//prettier-ignore
document.querySelectorAll(".dropdown-menu")[1].querySelectorAll(".dropdown-item").forEach((item) => {
  item.addEventListener("click", (e) => {
    e.preventDefault();
    const value = item.textContent.trim();
    filters.priority = filters.priority == value ? null : value;
    renderTasks();
  });
});

// FILTRO STATUS
//prettier-ignore
document.querySelectorAll(".dropdown-menu")[2].querySelectorAll(".dropdown-item").forEach((item) => {
  item.addEventListener("click", (e) => {
    e.preventDefault();
    const status = item.textContent.toLowerCase();
    if (filters.status === status) {
      filters.status = null;
      resetColumns();
    } else {
      filters.status = status;
      expandColumn(status);
    }
    renderTasks();
  });
});

function expandColumn(status) {
  document.querySelectorAll(".column-task").forEach((col) => {
    col.style.display = "none";
  });

  const column = document.querySelector("." + status);
  column.style.display = "flex";
  column.style.width = "100%";

  const board = column.querySelector(".task-board");
  board.style.display = "flex";
  board.style.flexWrap = "wrap";
  board.style.gap = "1rem";
  board.classList.add("expanded-board");
}

function resetColumns() {
  document.querySelectorAll(".column-task").forEach((col) => {
    col.style.display = "flex";
    col.style.width = "30%";

    const board = col.querySelector(".task-board");
    board.style.display = "block";
    board.classList.remove("expanded-board");
  });
}
