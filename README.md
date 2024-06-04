# Car Rental System

This Java program implements a Car Rental System, which allows customers to rent and return cars, view available and rented cars, view rental history, and search for cars by brand or model. The system is designed with several classes to encapsulate the functionalities and data involved in car rentals.

## Key Classes and Their Responsibilities

1. **Car**
   - **Attributes**: numberPlate, brand, model, basePricePerDay, chargePerExtraKm, isAvailable
   - **Methods**:
     - Getters for the attributes.
     - calculateBasePrice(int rentalDays): Calculates the base price for the given rental days.
     - calculateExtraKmCharges(int extraKm): Calculates the charges for extra kilometers driven.
     - isAvailable(): Returns the availability status of the car.
     - rent(): Marks the car as rented (not available).
     - returnCar(): Marks the car as available.
     - toString(): Returns a string representation of the car.

2. **Customer**
   - **Attributes**: customerId, name
   - **Methods**:
     - Getters for the attributes.
     - toString(): Returns a string representation of the customer.

3. **Rental**
   - **Attributes**: rentalId, car, customer, days, rentDate, returnDate, initialOdometer, finalOdometer
   - **Methods**:
     - Getters for the attributes.
     - setFinalOdometer(int finalOdometer): Sets the final odometer reading when the car is returned.
     - calculateExtraKm(): Calculates the extra kilometers driven beyond the allowed limit.
     - calculateTotalPrice(): Calculates the total price of the rental including any extra kilometer charges.
     - toString(): Returns a string representation of the rental.
     - Private method calculateReturnDate(Date rentDate, int days): Calculates the return date based on the rent date and the number of rental days.

4. **CarRentalSystem**
   - **Attributes**: lists of cars, customers, and rentals.
   - **Methods**:
     - addCar(Car car): Adds a car to the system.
     - addCustomer(Customer customer): Adds a customer to the system.
     - rentCar(Car car, Customer customer, int days, Date rentDate, int initialOdometer): Handles the process of renting a car.
     - returnCar(Car car, int finalOdometer): Handles the process of returning a rented car.
     - viewAvailableCars(): Displays all available cars.
     - viewRentedCars(): Displays all rented cars.
     - viewRentalHistory(): Displays the rental history.
     - searchCarByBrandOrModel(String query): Searches for cars by brand or model.
     - menu(): Displays the main menu and handles user input for various operations.
     - Private methods for handling specific menu options like renting a car, returning a car, and searching for a car.

## Main Class

- **Main**
  - **Purpose**: Entry point of the program.
  - **Method**:
    - main(String[] args): Initializes the CarRentalSystem, adds some cars to the system, and starts the menu-driven interface.

## Workflow

1. **Initialization**:
   - Cars are created and added to the CarRentalSystem.
   
2. **Menu-Driven Interface**:
   - The menu method is invoked, displaying options for renting a car, returning a car, viewing available cars, viewing rented cars, viewing rental history, and searching for cars.
   - The user selects an option by entering a number, which triggers the corresponding functionality.
   
3. **Renting a Car**:
   - The system prompts the user for their name, displays available cars, and asks for the number plate of the car to rent.
   - It then asks for the rental duration in days and the initial odometer reading.
   - A new customer is created, and the car rental is processed if confirmed by the user.
   
4. **Returning a Car**:
   - The system prompts for the number plate of the car being returned and the final odometer reading.
   - It calculates any extra kilometer charges and displays the rental details.
   
5. **Viewing Cars**:
   - Users can view all available or rented cars at any time.
   
6. **Rental History**:
   - Users can view the history of all rentals made in the system.
   
7. **Searching for Cars**:
   - Users can search for cars by entering a brand or model name.

## Code Explanation

The code is structured to provide clear separation of concerns:

- **Data Representation**: Car, Customer, and Rental classes.
- **Business Logic**: CarRentalSystem class manages the operations of renting and returning cars.
- **User Interaction**: The menu method provides an interface for user interaction through the console.

This structure allows for easy maintenance and scalability of the system.

## Auther : WICKxDEV
