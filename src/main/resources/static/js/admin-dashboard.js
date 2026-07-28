const API = "http://localhost:8080/api/admin";

const token = localStorage.getItem("token");

const userTable = document.getElementById("userTable");

const vehicleTable = document.getElementById("vehicleTable");

const bookingTable = document.getElementById("bookingTable");

const message = document.getElementById("message");

loadDashboard();

async function loadDashboard() {

    try {

        const response = await fetch(API + "/dashboard", {

            headers: {

                "Authorization": "Bearer " + token

            }

        });

        if (!response.ok) {

            throw new Error("Unable to load dashboard.");

        }

        const dashboard = await response.json();

        loadUsers(dashboard.users);

        loadVehicles(dashboard.vehicles);

        loadBookings(dashboard.bookings);

    }

    catch (error) {

        message.className = "error";

        message.innerHTML = error.message;

    }

}

function loadUsers(users) {

    userTable.innerHTML = "";

    users.forEach(user => {

        userTable.innerHTML += `

            <tr>

                <td>${user.id}</td>

                <td>${user.firstName} ${user.lastName}</td>

                <td>${user.email}</td>

                <td>${user.role}</td>

            </tr>

        `;

    });

}

function loadVehicles(vehicles) {

    vehicleTable.innerHTML = "";

    vehicles.forEach(vehicle => {

        vehicleTable.innerHTML += `

            <tr>

                <td>${vehicle.id}</td>

                <td>${vehicle.brand}</td>

                <td>${vehicle.model}</td>

                <td>${vehicle.vehicleType}</td>

                <td>${vehicle.availabilityStatus}</td>

            </tr>

        `;

    });

}

function loadBookings(bookings) {

    bookingTable.innerHTML = "";

    bookings.forEach(booking => {

        bookingTable.innerHTML += `

            <tr>

                <td>${booking.id}</td>

                <td>${booking.user.firstName}</td>

                <td>${booking.vehicle.brand}</td>

                <td>${booking.pickupDate}</td>

                <td>${booking.returnDate}</td>

                <td>${booking.bookingStatus}</td>

            </tr>

        `;

    });

}