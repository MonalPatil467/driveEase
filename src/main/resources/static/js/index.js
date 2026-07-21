const API_URL = "http://localhost:8080/api/vehicles";

document.addEventListener("DOMContentLoaded", () => {

    loadVehicles();

});

async function loadVehicles(){

    try{

        const response = await fetch(API_URL);

        if(!response.ok){

            throw new Error("Failed to fetch vehicles.");

        }

        const vehicles = await response.json();

        displayVehicles(vehicles);

    }

    catch(error){

        document.getElementById("vehicleContainer").innerHTML =

        `<h4 class="text-danger text-center">
            Unable to load vehicles.
        </h4>`;

    }

}

function displayVehicles(vehicles){

    const container = document.getElementById("vehicleContainer");

    container.innerHTML = "";

    if(vehicles.length===0){

        container.innerHTML =

        `<h4 class="text-center text-danger">
            No Vehicles Found
        </h4>`;

        return;

    }

    vehicles.forEach(vehicle=>{

        container.innerHTML += `

        <div class="col-md-4">

            <div class="card vehicle-card h-100">

                <img src="${vehicle.imageUrl}"
                     class="card-img-top"
                     alt="Vehicle">

                <div class="card-body">

                    <h5>${vehicle.brand} ${vehicle.model}</h5>

                    <p>

                        <strong>Type:</strong>
                        ${vehicle.vehicleType}

                    </p>

                    <p>

                        <strong>Fuel:</strong>
                        ${vehicle.fuelType}

                    </p>

                    <p>

                        <strong>Transmission:</strong>
                        ${vehicle.transmission}

                    </p>

                    <p>

                        <strong>Location:</strong>
                        ${vehicle.location}

                    </p>

                    <p>

                        <strong>₹${vehicle.pricePerDay}</strong>
                        / Day

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

document.getElementById("searchBtn").addEventListener("click", searchVehicle);

async function searchVehicle(){

    const location = document.getElementById("location").value;

    if(location===""){

        loadVehicles();

        return;

    }

    try{

        const response = await fetch(API_URL + "/search?location=" + location);

        if(!response.ok){

            throw new Error();

        }

        const vehicles = await response.json();

        displayVehicles(vehicles);

    }

    catch(error){

        document.getElementById("vehicleContainer").innerHTML=

        `<h4 class="text-danger text-center">
            Error while searching.
        </h4>`;

    }

}

document.getElementById("logoutBtn").addEventListener("click",()=>{

    localStorage.removeItem("token");

    localStorage.removeItem("role");

    localStorage.removeItem("email");

    window.location.href="login.html";

});