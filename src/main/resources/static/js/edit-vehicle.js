const token = localStorage.getItem("token");

const API = "http://localhost:8080/api/vehicles";

const params = new URLSearchParams(window.location.search);

const id = params.get("id");

window.onload = loadVehicle;

async function loadVehicle() {

    const response = await fetch(API + "/" + id, {

        headers: {

            Authorization: "Bearer " + token

        }

    });

    const vehicle = await response.json();

    brand.value = vehicle.brand;
    model.value = vehicle.model;
    registrationNumber.value = vehicle.registrationNumber;
    manufacturingYear.value = vehicle.manufacturingYear;
    color.value = vehicle.color;
    vehicleType.value = vehicle.vehicleType;
    fuelType.value = vehicle.fuelType;
    transmission.value = vehicle.transmission;
    seatingCapacity.value = vehicle.seatingCapacity;
    pricePerDay.value = vehicle.pricePerDay;
    location.value = vehicle.location;
    imageUrl.value = vehicle.imageUrl;
    description.value = vehicle.description;

}

updateBtn.onclick = async function () {

    const vehicle = {

        brand: brand.value,

        model: model.value,

        registrationNumber: registrationNumber.value,

        manufacturingYear: manufacturingYear.value,

        color: color.value,

        vehicleType: vehicleType.value,

        fuelType: fuelType.value,

        transmission: transmission.value,

        seatingCapacity: seatingCapacity.value,

        pricePerDay: pricePerDay.value,

        location: location.value,

        imageUrl: imageUrl.value,

        description: description.value

    };

    const response = await fetch(API + "/" + id, {

        method: "PUT",

        headers: {

            "Content-Type": "application/json",

            Authorization: "Bearer " + token

        },

        body: JSON.stringify(vehicle)

    });

    if (response.ok) {

        message.className = "text-success mt-3";

        message.innerHTML = "Vehicle updated successfully.";

        setTimeout(() => {

            window.location.href = "owner-dashboard.html";

        }, 1200);

    } else {

        message.className = "text-danger mt-3";

        message.innerHTML = "Failed to update vehicle.";

    }

};