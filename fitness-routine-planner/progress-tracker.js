document.addEventListener("DOMContentLoaded", () => {
    const historyList = document.getElementById("history-list");
    const workoutChartCanvas = document.getElementById("workoutChart");
    const exercisePieChartCanvas = document.getElementById("exercisePieChart");
    const workoutForm = document.getElementById("workout-form");
    const dateInput = document.getElementById("date");
    const exerciseInput = document.getElementById("exercise");
    const durationInput = document.getElementById("duration");

    let workoutData = JSON.parse(localStorage.getItem("workoutHistory")) || [];
    let workoutChart, exercisePieChart;

    function displayWorkoutHistory() {
        historyList.innerHTML = "";
        if (workoutData.length === 0) {
            historyList.innerHTML = "<p>No workout history available.</p>";
            return;
        }

        workoutData.forEach((workout, index) => {
            const li = document.createElement("li");
            li.innerHTML = `
                ${workout.date}: ${capitalize(workout.exercise)} - ${workout.duration} min
                <button class="delete-btn" data-index="${index}">X</button>
            `;
            historyList.appendChild(li);
        });

        document.querySelectorAll(".delete-btn").forEach(button => {
            button.addEventListener("click", (e) => {
                const index = e.target.getAttribute("data-index");
                workoutData.splice(index, 1);
                saveAndRender();
            });
        });
    }

    function destroyCharts() {
        if (workoutChart) {
            workoutChart.destroy();
            workoutChart = null;
        }
        if (exercisePieChart) {
            exercisePieChart.destroy();
            exercisePieChart = null;
        }
    }

    function renderCharts() {
        if (!workoutChartCanvas || !exercisePieChartCanvas) return;

        if (!workoutData.length) {
            destroyCharts();
            return;
        }

        destroyCharts();

        const groupedData = workoutData.reduce((acc, { date, duration }) => {
            acc[date] = (acc[date] || 0) + parseInt(duration);
            return acc;
        }, {});

        const ctxBar = workoutChartCanvas.getContext("2d");
        workoutChart = new Chart(ctxBar, {
            type: "bar",
            data: {
                labels: Object.keys(groupedData),
                datasets: [{
                    label: "Workout Duration (min)",
                    data: Object.values(groupedData),
                    backgroundColor: "#d6752a",
                    borderColor: "#9b440d",
                    borderWidth: 1
                }]
            }
        });

        // Normalize exercise names (case-insensitive)
        const exerciseCounts = workoutData.reduce((acc, { exercise }) => {
            const key = exercise.toLowerCase();
            acc[key] = (acc[key] || 0) + 1;
            return acc;
        }, {});

        const ctxPie = exercisePieChartCanvas.getContext("2d");
        exercisePieChart = new Chart(ctxPie, {
            type: "pie",
            data: {
                labels: Object.keys(exerciseCounts).map(capitalize),
                datasets: [{
                    data: Object.values(exerciseCounts),
                    backgroundColor: ["#f8b57d", "#d6752a", "#9b440d"]
                }]
            }
        });
    }

    function saveAndRender() {
        localStorage.setItem("workoutHistory", JSON.stringify(workoutData));
        displayWorkoutHistory();
        renderCharts();
    }

    function capitalize(str) {
        return str.charAt(0).toUpperCase() + str.slice(1).toLowerCase();
    }

    workoutForm.addEventListener("submit", (event) => {
        event.preventDefault();
        const newWorkout = {
            date: dateInput.value,
            exercise: exerciseInput.value.toLowerCase(),
            duration: durationInput.value
        };
        workoutData.push(newWorkout);
        saveAndRender();
        workoutForm.reset();
    });

    saveAndRender();
});
