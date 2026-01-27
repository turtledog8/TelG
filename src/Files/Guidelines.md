# Logistics App

## Project Description
Design and implement a Logistics console application.

The application will be used by employees of a large Australian company aiming to expand its activities to the freight industry. The app will manage the delivery of packages between hubs in major Australian cities.

Employees must be able to:
- Record delivery package details
- Create or search for delivery routes
- Inspect the current state of packages, vehicles, and routes

---

## Functional Requirements

### Core Operations
- **Create a delivery package**
    - Unique ID
    - Start location
    - End location
    - Weight (kg)
    - Customer contact information

- **Create a delivery route**
    - Unique ID
    - List of locations (minimum two)
    - First location: departure time
    - Other locations: expected arrival times

- **Search for a route**
    - Based on package start and end locations

- **Update a delivery route**
    - Assign a free truck
    - Assign delivery packages

- **View information**
    - Routes
    - Packages
    - Trucks

- **Persist application state**
    - Save data to the file system

---

## Transport Vehicles

| Vehicle IDs | Name    | Capacity (kg) | Max Range (km) | Number |
|------------|---------|---------------|---------------|--------|
| 1001–1010  | Scania  | 42000         | 8000          | 10     |
| 1011–1025  | Man     | 37000         | 10000         | 15     |
| 1026–1040  | Actros  | 26000         | 13000         | 15     |

---

## Distances Between Cities (km)

|     | SYD | MEL | ADL | ASP | BRI | DAR | PER |
|-----|-----|-----|-----|-----|-----|-----|-----|
| SYD |     | 877 | 1376| 2762| 909 | 3935| 4016|
| MEL | 877 |     | 725 | 2255| 1765| 3752| 3509|
| ADL |1376 | 725 |     | 1530| 1927| 3027| 2785|
| ASP |2762 |2255 |1530 |     | 2993| 1497| 2481|
| BRI | 909 |1765 |1927 |2993 |     | 3426| 4311|
| DAR |3935 |3752 |3027 |1497 |3426 |     | 4025|
| PER |4016 |3509 |2785 |2481 |4311 |4025 |     |

---

## Use Cases

### Use Case #1
A customer visits the office in Sydney on **Oct 8th** to send a **45kg** package to Melbourne.  
The system finds two suitable routes:

- Brisbane (Oct 10th 06:00) → Sydney (Oct 10th 20:00) → Melbourne (Oct 11th 18:00)
- Sydney (Oct 12th 06:00) → Melbourne (Oct 12th 20:00) → Adelaide (Oct 13th 15:00)

Both have free capacity. The earlier route is chosen and the package arrival time is updated to **Oct 11th 18:00**.

---

### Use Case #2
Packages totaling **23000kg** are gathered in Alice Springs.  
A route is created on **Sep 12th 06:00**:

Alice Springs → Adelaide → Melbourne → Sydney → Brisbane

- Total distance: **4041km**
- Average speed: **87 km/h**
- Arrival times are auto-calculated
- A suitable truck is assigned
- Packages are bulk assigned using IDs

---

### Use Case #3
A manager requests all active routes.  
The system displays:
- Stops
- Total delivery weight
- Current expected stop based on time

---

### Use Case #4
A supervising employee views all unassigned packages.  
The system returns a list with:
- Package IDs
- Current locations

---

### Use Case #5
A customer asks about their package using its ID.  
The system returns detailed package information, which is emailed to the customer.

---

## Technical Requirements

- Follow **OOP principles**
    - Encapsulation
    - Information hiding
    - Proper use of inheritance vs composition
    - Polymorphism

- Code quality
    - Clear naming
    - Consistent formatting
    - Readable logic

- Robustness
    - Input validation
    - Meaningful user messages
    - Proper error handling

- Prefer **Stream API**
- Unit tests for core functionality
- Use **Git** for version control and collaboration

