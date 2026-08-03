const API = "http://localhost:8080/api/vehicles";

const vehicleContainer = document.getElementById("vehicleContainer");
const searchBtn = document.getElementById("searchBtn");
const locationInput = document.getElementById("location");
const vehicleTypeInput = document.getElementById("vehicleType");

const token = localStorage.getItem("token");

if (!token) {
    alert("Please login first.");
    window.location.href = "login.html";
}

loadVehicles();

searchBtn.addEventListener("click", searchVehicles);

async function loadVehicles() {

    try {

        const response = await fetch(API, {
            method: "GET",
            headers: {
                "Authorization": "Bearer " + token,
                "Content-Type": "application/json"
            }
        });

        if (!response.ok) {
            throw new Error("Unable to load vehicles.");
        }

        const vehicles = await response.json();

        displayVehicles(vehicles);

    } catch (error) {

        vehicleContainer.innerHTML = `
            <div class="col-12">
                <div class="alert alert-danger">
                    ${error.message}
                </div>
            </div>
        `;
    }

}

function displayVehicles(vehicles) {

    vehicleContainer.innerHTML = "";

    if (vehicles.length === 0) {

        vehicleContainer.innerHTML = `
            <div class="col-12 text-center">
                <h4>No Vehicles Found</h4>
            </div>
        `;

        return;
    }

    vehicles.forEach(vehicle => {

        vehicleContainer.innerHTML += `
            <div class="col-lg-4 col-md-6 mb-4">

                <div class="card vehicle-card h-100">

                    <img src="${vehicle.imageUrl}"
                         class="card-img-top"
                         alt="${vehicle.brand}">

                    <div class="card-body">

                        <h5 class="card-title">
                            ${vehicle.brand} ${vehicle.model}
                        </h5>

                        <p><strong>Type:</strong> ${vehicle.vehicleType}</p>

                        <p><strong>Fuel:</strong> ${vehicle.fuelType}</p>

                        <p><strong>Location:</strong> ${vehicle.location}</p>

                        <p><strong>Price:</strong> ₹${vehicle.pricePerDay}/Day</p>

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

async function searchVehicles() {

    const location = locationInput.value.trim().toLowerCase();
    const vehicleType = vehicleTypeInput.value;

    try {

        const response = await fetch(API, {
            method: "GET",
            headers: {
                "Authorization": "Bearer " + token,
                "Content-Type": "application/json"
            }
        });

        if (!response.ok) {
            throw new Error("Unable to search vehicles.");
        }

        const vehicles = await response.json();

        const filteredVehicles = vehicles.filter(vehicle => {

            const locationMatch =
                vehicle.location.toLowerCase().includes(location);

            const typeMatch =
                vehicleType === "" ||
                vehicle.vehicleType === vehicleType;

            return locationMatch && typeMatch;

        });

        displayVehicles(filteredVehicles);

    } catch (error) {

        vehicleContainer.innerHTML = `
            <div class="col-12">
                <div class="alert alert-danger">
                    Unable to search vehicles.
                </div>
            </div>
        `;

    }

}