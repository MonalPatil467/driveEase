const token = localStorage.getItem("token");

const dashboardAPI = "http://localhost:8080/admin/dashboard";

const usersAPI = "http://localhost:8080/api/users";

window.onload = () => {

    loadDashboard();

    loadUsers();

};

async function loadDashboard() {

    const response = await fetch(dashboardAPI, {

        headers: {

            Authorization: "Bearer " + token

        }

    });

    const data = await response.json();

    users.innerHTML = data.totalUsers;

    vehicles.innerHTML = data.totalVehicles;

    bookings.innerHTML = data.totalBookings;

    revenue.innerHTML = "₹" + data.totalRevenue;

}

async function loadUsers() {

    const response = await fetch(usersAPI, {

        headers: {

            Authorization: "Bearer " + token

        }

    });

    const list = await response.json();

    userTable.innerHTML = "";

    list.forEach(user => {

        userTable.innerHTML += `

        <tr>

            <td>${user.id}</td>

            <td>${user.name}</td>

            <td>${user.email}</td>

            <td>${user.role}</td>

        </tr>

        `;

    });

}

function logout(){

    localStorage.removeItem("token");

    window.location.href="login.html";

}