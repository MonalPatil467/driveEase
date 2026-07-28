const BASE_URL = "http://localhost:8080";

const VEHICLE_API = BASE_URL + "/api/vehicles";

const SEARCH_API = BASE_URL + "/api/vehicles/search";

const vehicleContainer = document.getElementById("vehicleContainer");

const searchButton = document.getElementById("searchBtn");

const locationInput = document.getElementById("location");

loadVehicles();

searchButton.addEventListener("click", searchVehicles);

async function loadVehicles() {

    try {

        const response = await fetch(VEHICLE_API);

        if (!response.ok) {

            throw new Error("Unable to load vehicles.");

        }

        const page = await response.json();

        displayVehicles(page.content);

    }

    catch (error) {

        vehicleContainer.innerHTML = `

            <div class="col-12 text-center text-danger">

                ${error.message}

            </div>

        `;

    }

}

async function searchVehicles() {

    const location = locationInput.value.trim();

    if (location === "") {

        loadVehicles();

        return;

    }

    try {

        const response = await fetch(

            SEARCH_API + "?location=" + encodeURIComponent(location)

        );

        if (!response.ok) {

            throw new Error("No vehicles found.");

        }

        const vehicles = await response.json();

        displayVehicles(vehicles);

    }

    catch (error) {

        vehicleContainer.innerHTML = `

            <div class="col-12 text-center text-danger">

                ${error.message}

            </div>

        `;

    }

}

function displayVehicles(vehicles) {

    vehicleContainer.innerHTML = "";

    if (vehicles.length === 0) {

        vehicleContainer.innerHTML = `

            <div class="col-12 text-center">

                <h5>No Vehicles Available</h5>

            </div>

        `;

        return;

    }

    vehicles.forEach(vehicle => {

        vehicleContainer.innerHTML += `

            <div class="col-md-4 mb-4">

                <div class="card vehicle-card h-100">

                    <img src="${vehicle.imageUrl}"

                         class="card-img-top"

                         alt="${vehicle.brand}">

                    <div class="card-body">

                        <h5>

                            ${vehicle.brand} ${vehicle.model}

                        </h5>

                        <p>

                            ${vehicle.vehicleType}

                        </p>

                        <p>

                            📍 ${vehicle.location}

                        </p>

                        <p>

                            ₹${vehicle.pricePerDay} / Day

                        </p>

                        <a href="vehicle-details.html?id=${vehicle.id}"

                           class="btn btn-secondary w-100">

                           View Details

                        </a>

                    </div>

                </div>

            </div>

        `;

    });

}