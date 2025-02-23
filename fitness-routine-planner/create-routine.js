document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("routine-form");
    const exerciseList = document.getElementById("exercise-list");
    const saveButton = document.getElementById("save-routine");

    let routines = JSON.parse(localStorage.getItem("routines")) || [];

    form.addEventListener("submit", (e) => {
        e.preventDefault();

        const name = document.getElementById("exercise-name").value;
        const duration = document.getElementById("duration").value;
        const rest = document.getElementById("rest").value;

        if (name && duration && rest) {
            const exercise = { name, duration, rest };
            routines.push(exercise);

            displayExercises();
            form.reset();
        }
    });

    function displayExercises() {
        exerciseList.innerHTML = "";
        routines.forEach((exercise, index) => {
            const li = document.createElement("li");
            li.innerHTML = `
                ${exercise.name} - ${exercise.duration} min (Rest: ${exercise.rest} sec) 
                <button onclick="removeExercise(${index})">X</button>
            `;
            exerciseList.appendChild(li);
        });
    }

    window.removeExercise = (index) => {
        routines.splice(index, 1);
        displayExercises();
    };

    saveButton.addEventListener("click", () => {
        localStorage.setItem("routines", JSON.stringify(routines));
        alert("Routine Saved!");
    });

    displayExercises();
});
