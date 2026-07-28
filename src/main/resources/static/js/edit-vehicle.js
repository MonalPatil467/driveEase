const API = "http://localhost:8080/api/vehicles";

const token = localStorage.getItem("token");

const params = new URLSearchParams(window.location.search);

const vehicleId = params.get("id");

const vehicleForm = document.getElementById("vehicleForm");

const message = document.getElementById("message");

loadVehicle();

vehicleForm.addEventListener("submit", updateVehicle);

async function loadVehicle() {

    try {

        const response = await fetch(API + "/" + vehicleId, {

            headers: {

                "Authorization": "Bearer " + token

            }

        });

        if (!response.ok) {

            throw new Error("Unable to load vehicle.");

        }

        const vehicle = await response.json();

        document.getElementById("brand").value =
            vehicle.brand;

        document.getElementById("model").value =
            vehicle.model;

        document.getElementById("vehicleType").value =
            vehicle.vehicleType;

        document.getElementById("fuelType").value =
            vehicle.fuelType;

        document.getElementById("transmission").value =
            vehicle.transmission;

        document.getElementById("seatingCapacity").value =
            vehicle.seatingCapacity;

        document.getElementById("manufacturingYear").value =
            vehicle.manufacturingYear;

        document.getElementById("color").value =
            vehicle.color;

        document.getElementById("location").value =
            vehicle.location;

        document.getElementById("pricePerDay").value =
            vehicle.pricePerDay;

        document.getElementById("imageUrl").value =
            vehicle.imageUrl;

        document.getElementById("description").value =
            vehicle.description;

    }

    catch (error) {

        message.className = "error";

        message.innerHTML = error.message;

    }

}

async function updateVehicle(event) {

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

        const response = await fetch(API + "/" + vehicleId, {

            method: "PUT",

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
                result.message || "Unable to update vehicle.";

            return;

        }

        message.className = "success";

        message.innerHTML =
            "Vehicle Updated Successfully.";

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