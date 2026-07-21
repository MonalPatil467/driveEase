const API = "http://localhost:8080/api/vehicles";

const params = new URLSearchParams(window.location.search);

const id = params.get("id");

loadVehicle();

async function loadVehicle() {

    const response = await fetch(API + "/" + id);

    const vehicle = await response.json();

    document.getElementById("vehicleImage").src = vehicle.imageUrl;

    document.getElementById("vehicleName").innerHTML =
        vehicle.brand + " " + vehicle.model;

    document.getElementById("vehicleType").innerHTML =
        vehicle.vehicleType;

    document.getElementById("fuelType").innerHTML =
        vehicle.fuelType;

    document.getElementById("transmission").innerHTML =
        vehicle.transmission;

    document.getElementById("capacity").innerHTML =
        vehicle.seatingCapacity;

    document.getElementById("location").innerHTML =
        vehicle.location;

    document.getElementById("color").innerHTML =
        vehicle.color;

    document.getElementById("year").innerHTML =
        vehicle.manufacturingYear;

    document.getElementById("description").innerHTML =
        vehicle.description;

    document.getElementById("price").innerHTML =
        vehicle.pricePerDay;

    document.getElementById("bookBtn").onclick = function () {

        window.location.href = "booking.html?id=" + vehicle.id;

    };

}