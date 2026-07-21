const API = "http://localhost:8080/api/vehicles";

const token = localStorage.getItem("token");

document.getElementById("addVehicleBtn")
    .addEventListener("click", addVehicle);

async function addVehicle() {

    const vehicle = {

        brand: document.getElementById("brand").value,

        model: document.getElementById("model").value,

        registrationNumber: document.getElementById("registrationNumber").value,

        manufacturingYear: document.getElementById("manufacturingYear").value,

        color: document.getElementById("color").value,

        vehicleType: document.getElementById("vehicleType").value,

        fuelType: document.getElementById("fuelType").value,

        transmission: document.getElementById("transmission").value,

        seatingCapacity: document.getElementById("seatingCapacity").value,

        pricePerDay: document.getElementById("pricePerDay").value,

        location: document.getElementById("location").value,

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

        if (!response.ok) {

            throw new Error();

        }

        document.getElementById("message").className =
            "text-success mt-3";

        document.getElementById("message").innerHTML =
            "Vehicle added successfully.";

        setTimeout(() => {

            window.location.href = "owner-dashboard.html";

        }, 1500);

    }

    catch {

        document.getElementById("message").className =
            "text-danger mt-3";

        document.getElementById("message").innerHTML =
            "Failed to add vehicle.";

    }

}