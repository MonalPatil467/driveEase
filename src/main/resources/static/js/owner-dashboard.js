const API = "http://localhost:8080/api/vehicles/owner";

const token = localStorage.getItem("token");

const vehicleTable = document.getElementById("vehicleTable");

const message = document.getElementById("message");

loadVehicles();

async function loadVehicles() {

    try {

        const response = await fetch(API, {

            headers: {

                "Authorization": "Bearer " + token

            }

        });

        if (!response.ok) {

            throw new Error("Unable to load vehicles.");

        }

        const vehicles = await response.json();

        vehicleTable.innerHTML = "";

        if (vehicles.length === 0) {

            message.innerHTML = "No vehicles found.";

            return;

        }

        vehicles.forEach(vehicle => {

            vehicleTable.innerHTML += `

                <tr>

                    <td>

                        ${vehicle.id}

                    </td>

                    <td>

                        ${vehicle.brand}

                    </td>

                    <td>

                        ${vehicle.model}

                    </td>

                    <td>

                        ${vehicle.vehicleType}

                    </td>

                    <td>

                        ₹${vehicle.pricePerDay}

                    </td>

                    <td>

                        ${vehicle.availabilityStatus}

                    </td>

                    <td>

                        <a href="edit-vehicle.html?id=${vehicle.id}"

                           class="btn btn-warning btn-sm">

                            Edit

                        </a>

                    </td>

                </tr>

            `;

        });

    }

    catch (error) {

        message.innerHTML = error.message;

    }

}