document.addEventListener("DOMContentLoaded", () => {
    const routineList = document.getElementById("routine-list");
    const clearAllButton = document.getElementById("clear-all");

    let routines = JSON.parse(localStorage.getItem("routines")) || [];

    function displayRoutines() {
        routineList.innerHTML = "";
        if (routines.length === 0) {
            routineList.innerHTML = "<p>No saved routines. Start by creating one!</p>";
            return;
        }

        routines.forEach((routine, index) => {
            const li = document.createElement("li");
            li.classList.add("routine-item");
            li.draggable = true;
            li.dataset.index = index;

            if (routine.completed) li.classList.add("completed");

            li.innerHTML = `
                <span>${routine.name} - ${routine.duration} min (Rest: ${routine.rest} sec)</span>
                <div>
                    <button class="complete-btn" onclick="toggleComplete(${index})">
                        ${routine.completed ? "Undo" : "Complete"}
                    </button>
                    <button class="edit-btn" onclick="editRoutine(${index})">Edit</button>
                    <button class="delete-btn" onclick="deleteRoutine(${index})">Delete</button>
                </div>
            `;

            // Drag and drop event listeners
            li.addEventListener("dragstart", handleDragStart);
            li.addEventListener("dragover", handleDragOver);
            li.addEventListener("drop", handleDrop);
            li.addEventListener("dragend", handleDragEnd);

            routineList.appendChild(li);
        });
    }

    window.toggleComplete = (index) => {
        routines[index].completed = !routines[index].completed;
        localStorage.setItem("routines", JSON.stringify(routines));
        displayRoutines();
    };

    window.deleteRoutine = (index) => {
        routines.splice(index, 1);
        localStorage.setItem("routines", JSON.stringify(routines));
        displayRoutines();
    };

    window.editRoutine = (index) => {
        const newName = prompt("Edit Exercise Name:", routines[index].name);
        const newDuration = prompt("Edit Duration (minutes):", routines[index].duration);
        const newRest = prompt("Edit Rest Time (seconds):", routines[index].rest);

        if (newName && newDuration && newRest) {
            routines[index] = { name: newName, duration: newDuration, rest: newRest, completed: routines[index].completed };
            localStorage.setItem("routines", JSON.stringify(routines));
            displayRoutines();
        }
    };

    clearAllButton.addEventListener("click", () => {
        if (confirm("Are you sure you want to delete all routines?")) {
            localStorage.removeItem("routines");
            routines = [];
            displayRoutines();
        }
    });

    let draggedItemIndex = null;

    function handleDragStart(event) {
        draggedItemIndex = event.target.dataset.index;
        event.target.classList.add("dragging");
    }

    function handleDragOver(event) {
        event.preventDefault();
        const afterElement = getDragAfterElement(routineList, event.clientY);
        const draggedElement = document.querySelector(".dragging");
        if (afterElement == null) {
            routineList.appendChild(draggedElement);
        } else {
            routineList.insertBefore(draggedElement, afterElement);
        }
    }

    function handleDrop(event) {
        event.preventDefault();
        const droppedIndex = Array.from(routineList.children).indexOf(event.target);
        if (draggedItemIndex !== null && droppedIndex !== -1 && draggedItemIndex !== droppedIndex) {
            const movedItem = routines.splice(draggedItemIndex, 1)[0];
            routines.splice(droppedIndex, 0, movedItem);
            localStorage.setItem("routines", JSON.stringify(routines));
            displayRoutines();
        }
    }

    function handleDragEnd(event) {
        event.target.classList.remove("dragging");
    }

    function getDragAfterElement(container, y) {
        const draggableElements = [...container.querySelectorAll(".routine-item:not(.dragging)")];
        return draggableElements.reduce((closest, child) => {
            const box = child.getBoundingClientRect();
            const offset = y - box.top - box.height / 2;
            if (offset < 0 && offset > closest.offset) {
                return { offset: offset, element: child };
            } else {
                return closest;
            }
        }, { offset: Number.NEGATIVE_INFINITY }).element;
    }

    displayRoutines();
});
