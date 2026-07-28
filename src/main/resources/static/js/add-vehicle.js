const API = "http://localhost:8080/api/vehicles";

const token = localStorage.getItem("token");

const vehicleForm = document.getElementById("vehicleForm");

const message = document.getElementById("message");

vehicleForm.addEventListener("submit", addVehicle);

async function addVehicle(event) {

    event.preventDefault();

    const vehicle = {

        brand: document.getElementById("brand").value,

        model: document.getElementById("model").value,

        vehicleType: document.getElementById("vehicleType").value,

        fuelType: document.getElementById("fuelType").value,

        transmission: document.getElementById("transmission").value,

        seatingCapacity: document.getElementById("seatingCapacity").value,

        manufacturingYear: document.getElementById("manufacturingYear").value,

        color: document.getElementById("color").value,

        location: document.getElementById("location").value,

        pricePerDay: document.getElementById("pricePerDay").value,

        imageUrl: document.getElementById("imageUrl").value,

        description: document.getElementById("description").value

    };

    try {

        const response = await fetch(API, {

            method: "POST",

            headers: {

                "Content-Type": "application/json",

                "Authorization": "Bearer " + token

            },

            body: JSON.stringify(vehicle)

        });

        const result = await response.json();

        if (!response.ok) {

            message.className = "error";

            message.innerHTML =
                result.message || "Unable to add vehicle.";

            return;

        }

        message.className = "success";

        message.innerHTML =
            "Vehicle Added Successfully.";

        setTimeout(() => {

            window.location.href =
                "owner-dashboard.html";

        }, 1500);

    }

    catch (error) {

        message.className = "error";

        message.innerHTML =
            "Unable to connect to server.";

    }

}