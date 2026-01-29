## Functional Requirements

### FR1 – Create Delivery Package  XXXX
The system shall allow employees to create a delivery package with the following attributes:
- unique identifier
- start location
- end location
- weight (in kilograms)
- customer contact information

---

### FR2 – Create Delivery Route    XXXXXXX
The system shall allow employees to create a delivery route with:
- a unique identifier
- a list of locations (minimum of two)

---

### FR3 – Define Route Schedule
The system shall support:
- a departure time for the first location in a route
- an expected arrival time for each subsequent location

---

### FR4 – Search Delivery Route
The system shall allow employees to search for delivery routes based on a package’s start and end locations.

---

### FR5 – Assign Truck to Route
The system shall allow employees to assign a free transport vehicle to a delivery route, provided that:
- the vehicle has sufficient remaining capacity
- the vehicle’s maximum range supports the route distance

---

### FR6 – Assign Package to Route
The system shall allow employees to assign a delivery package to a delivery route if:
- the package locations match the route
- the route has sufficient remaining capacity

---

### FR7 – Calculate Route Distance
The system shall calculate the total distance of a delivery route using predefined distances between locations.

---

### FR8 – Calculate Estimated Arrival Times
The system shall calculate estimated arrival times for each route location based on:
- total distance
- a predefined average vehicle speed

---

### FR9 – Track Route Progress
The system shall determine the current or expected route stop based on the current time of day.

---

### FR10 – View Route Information
The system shall allow users to view information about delivery routes, including:
- route stops
- assigned vehicle
- total delivery weight
- current or expected stop

---

### FR11 – View Package Information
The system shall allow users to view information about delivery packages, including:
- package status
- current location
- assigned route (if any)

---

### FR12 – View Unassigned Packages
The system shall allow users to view all delivery packages that are not assigned to any delivery route.

---

### FR13 – View Vehicle Information
The system shall allow users to view information about transport vehicles, including:
- vehicle type
- capacity
- maximum range
- availability status

---

### FR14 – Persist Application State
The system shall allow saving and loading of the application state to and from the file system.

---

### FR15 – Validate User Input
The system shall validate all user input and display meaningful error messages in case of invalid data.
