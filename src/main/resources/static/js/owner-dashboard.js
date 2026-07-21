const token = localStorage.getItem("token");

const dashboardAPI = "http://localhost:8080/owner/dashboard";

const vehicleAPI = "http://localhost:8080/api/vehicles";

document.addEventListener("DOMContentLoaded", () => {

    loadDashboard();

    loadVehicles();

});

async function loadDashboard() {

    const response = await fetch(dashboardAPI, {

        headers: {

            Authorization: "Bearer " + token

        }

    });

    const dashboard = await response.json();

    document.getElementById("vehicleCount").innerHTML =
        dashboard.totalVehicles;

    document.getElementById("bookingCount").innerHTML =
        dashboard.totalBookings;

    document.getElementById("revenue").innerHTML =
        "₹" + dashboard.totalRevenue;

    document.getElementById("rating").innerHTML =
        dashboard.averageRating;

}

async function loadVehicles() {

    const response = await fetch(vehicleAPI, {

        headers: {

            Authorization: "Bearer " + token

        }

    });

    const vehicles = await response.json();

    const container = document.getElementById("vehicleContainer");

    container.innerHTML = "";

    vehicles.forEach(vehicle => {

        container.innerHTML += `

        <div class="col-md-4 mb-4">

            <div class="card bg-dark text-white h-100">

                <img src="${vehicle.imageUrl}"
                     class="card-img-top"
                     style="height:220px;object-fit:cover;">

                <div class="card-body">

                    <h4>${vehicle.brand} ${vehicle.model}</h4>

                    <p>${vehicle.location}</p>

                    <p>₹${vehicle.pricePerDay}/Day</p>

                    <button
                        class="btn btn-secondary w-100 mb-2"
                        onclick="editVehicle(${vehicle.id})">

                        Edit

                    </button>

                    <button
                        class="btn btn-danger w-100"
                        onclick="deleteVehicle(${vehicle.id})">

                        Delete

                    </button>

                </div>

            </div>

        </div>

        `;

    });

}

function editVehicle(id){

    window.location.href =
        "edit-vehicle.html?id=" + id;

}

async function deleteVehicle(id){

    if(!confirm("Delete Vehicle?")){

        return;

    }

    await fetch(vehicleAPI + "/" + id,{

        method:"DELETE",

        headers:{

            Authorization:"Bearer "+token

        }

    });

    loadVehicles();

    loadDashboard();

}

function logout(){

    localStorage.removeItem("token");

    window.location.href="login.html";

}