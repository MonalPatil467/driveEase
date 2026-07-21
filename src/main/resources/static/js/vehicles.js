const API_URL = "http://localhost:8080/api/vehicles";

let currentPage = 0;
const pageSize = 6;

document.addEventListener("DOMContentLoaded", () => {

    loadVehicles();

});

async function loadVehicles() {

    try {

        const response = await fetch(API_URL);

        if (!response.ok) {

            throw new Error();

        }

        const vehicles = await response.json();

        displayVehicles(vehicles);

    }

    catch {

        document.getElementById("vehicleContainer").innerHTML =

            `<h3 class="text-danger text-center">
                Unable to load vehicles.
            </h3>`;

    }

}

function displayVehicles(vehicles) {

    const container = document.getElementById("vehicleContainer");

    container.innerHTML = "";

    const start = currentPage * pageSize;

    const end = start + pageSize;

    const pageVehicles = vehicles.slice(start, end);

    if (pageVehicles.length === 0) {

        container.innerHTML =

            `<h3 class="text-danger text-center">
                No Vehicles Found
            </h3>`;

        return;

    }

    pageVehicles.forEach(vehicle => {

        container.innerHTML += `

        <div class="col-md-4">

            <div class="card vehicle-card h-100">

                <img src="${vehicle.imageUrl}"
                     class="card-img-top"
                     alt="Vehicle">

                <div class="card-body">

                    <h5>

                        ${vehicle.brand} ${vehicle.model}

                    </h5>

                    <p>

                        <strong>Type :</strong>

                        ${vehicle.vehicleType}

                    </p>

                    <p>

                        <strong>Fuel :</strong>

                        ${vehicle.fuelType}

                    </p>

                    <p>

                        <strong>Transmission :</strong>

                        ${vehicle.transmission}

                    </p>

                    <p>

                        <strong>Seats :</strong>

                        ${vehicle.seatingCapacity}

                    </p>

                    <p>

                        <strong>Location :</strong>

                        ${vehicle.location}

                    </p>

                    <h5>

                        ₹${vehicle.pricePerDay}/Day

                    </h5>

                    <a href="vehicle-details.html?id=${vehicle.id}"

                       class="btn btn-secondary w-100">

                        View Details

                    </a>

                </div>

            </div>

        </div>

        `;

    });

    document.getElementById("pageNumber").innerText = currentPage + 1;

}

document.getElementById("searchBtn").addEventListener("click", async () => {

    const location = document.getElementById("location").value;

    if (location === "") {

        loadVehicles();

        return;

    }

    try {

        const response = await fetch(API_URL + "/search?location=" + location);

        const vehicles = await response.json();

        currentPage = 0;

        displayVehicles(vehicles);

    }

    catch {

        alert("Search failed");

    }

});

document.getElementById("nextBtn").addEventListener("click", () => {

    currentPage++;

    loadVehicles();

});

document.getElementById("previousBtn").addEventListener("click", () => {

    if (currentPage > 0) {

        currentPage--;

        loadVehicles();

    }

});

document.getElementById("logoutBtn").addEventListener("click", () => {

    localStorage.clear();

    window.location.href = "login.html";

});