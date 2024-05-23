import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

class Car {
    private String numberPlate;
    private String brand;
    private String model;
    private double basePricePerDay;
    private double chargePerExtraKm;
    private boolean isAvailable;

    public Car(String numberPlate, String brand, String model, double basePricePerDay, double chargePerExtraKm) {
        this.numberPlate = numberPlate;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.chargePerExtraKm = chargePerExtraKm;
        this.isAvailable = true;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double calculateBasePrice(int rentalDays) {
        return basePricePerDay * rentalDays;
    }

    public double calculateExtraKmCharges(int extraKm) {
        return extraKm * chargePerExtraKm;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void rent() {
        isAvailable = false;
    }

    public void returnCar() {
        isAvailable = true;
    }

    @Override
    public String toString() {
        return numberPlate + " - " + brand + " " + model;
    }
}

class Customer {
    private String customerId;
    private String name;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return customerId + " - " + name;
    }
}

class Rental {
    private String rentalId;
    private Car car;
    private Customer customer;
    private int days;
    private Date rentDate;
    private Date returnDate;
    private int initialOdometer;
    private int finalOdometer;

    public Rental(Car car, Customer customer, int days, Date rentDate, int initialOdometer) {
        this.rentalId = UUID.randomUUID().toString();
        this.car = car;
        this.customer = customer;
        this.days = days;
        this.rentDate = rentDate;
        this.returnDate = calculateReturnDate(rentDate, days);
        this.initialOdometer = initialOdometer;
    }

    public String getRentalId() {
        return rentalId;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }

    public Date getRentDate() {
        return rentDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public int getInitialOdometer() {
        return initialOdometer;
    }

    public int getFinalOdometer() {
        return finalOdometer;
    }

    public void setFinalOdometer(int finalOdometer) {
        this.finalOdometer = finalOdometer;
    }

    public int calculateExtraKm() {
        int totalAllowedKm = days * 200;
        int actualKmDriven = finalOdometer - initialOdometer;
        return Math.max(0, actualKmDriven - totalAllowedKm);
    }

    public double calculateTotalPrice() {
        double basePrice = car.calculateBasePrice(days);
        double extraKmCharges = car.calculateExtraKmCharges(calculateExtraKm());
        return basePrice + extraKmCharges;
    }

    private Date calculateReturnDate(Date rentDate, int days) {
        long time = rentDate.getTime();
        long daysInMillis = days * 24 * 60 * 60 * 1000L;
        return new Date(time + daysInMillis);
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        int extraKm = calculateExtraKm();
        double extraKmCharges = car.calculateExtraKmCharges(extraKm);
        double totalPrice = calculateTotalPrice();
        return "Rental ID: " + rentalId + ", Car: " + car + ", Customer: " + customer +
            ", Days: " + days + ", Rent Date: " + dateFormat.format(rentDate) +
            ", Return Date: " + dateFormat.format(returnDate) +
            ", Initial Odometer: " + initialOdometer + ", Final Odometer: " + finalOdometer +
            ", Extra Km: " + extraKm + ", Extra Km Charges: Rs. " + extraKmCharges +
            ", Total Price: Rs. " + totalPrice;
    }

}

class CarRentalSystem {
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public CarRentalSystem() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer, int days, Date rentDate, int initialOdometer) {
        if (car.isAvailable()) {
            car.rent();
            rentals.add(new Rental(car, customer, days, rentDate, initialOdometer));
        } else {
            System.out.println("Car is not available for rent.");
        }
    }

    public void returnCar(Car car, int finalOdometer) {
        Rental rentalToRemove = null;
        for (Rental rental : rentals) {
            if (rental.getCar().equals(car)) {
                rental.setFinalOdometer(finalOdometer);
                rentalToRemove = rental;
                break;
            }
        }
        if (rentalToRemove != null) {
            car.returnCar();
            rentals.remove(rentalToRemove);
            System.out.println("Car returned successfully by " + rentalToRemove.getCustomer().getName());
            System.out.println("Rental details: " + rentalToRemove);
        } else {
            System.out.println("Car was not rented.");
        }
    }

    public void viewAvailableCars() {
        System.out.println("\nAvailable Cars:");
        for (Car car : cars) {
            if (car.isAvailable()) {
                System.out.println(car);
            }
        }
    }

    public void viewRentedCars() {
        System.out.println("\nRented Cars:");
        for (Car car : cars) {
            if (!car.isAvailable()) {
                System.out.println(car);
            }
        }
    }

    public void viewRentalHistory() {
        System.out.println("\nRental History:");
        for (Rental rental : rentals) {
            System.out.println(rental);
        }
    }

    public void searchCarByBrandOrModel(String query) {
        System.out.println("\nSearch Results:");
        for (Car car : cars) {
            if (car.getBrand().equalsIgnoreCase(query) || car.getModel().equalsIgnoreCase(query)) {
                System.out.println(car);
            }
        }
    }

    public void menu() {
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        while (true) {
            System.out.println("\n===== Car Rental System =====");
            System.out.println("1. Rent a Car");
            System.out.println("2. Return a Car");
            System.out.println("3. View Available Cars");
            System.out.println("4. View Rented Cars");
            System.out.println("5. View Rental History");
            System.out.println("6. Search Car by Brand or Model");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 7.");
                continue;
            }

            switch (choice) {
                case 1:
                    rentCarMenu(scanner, dateFormat);
                    break;
                case 2:
                    returnCarMenu(scanner);
                    break;
                case 3:
                    viewAvailableCars();
                    break;
                case 4:
                    viewRentedCars();
                    break;
                case 5:
                    viewRentalHistory();
                    break;
                case 6:
                    searchCarMenu(scanner);
                    break;
                case 7:
                    System.out.println("Thank you for using the Car Rental System!");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 7.");
            }
        }
    }

    private void rentCarMenu(Scanner scanner, SimpleDateFormat dateFormat) {
        System.out.println("\n== Rent a Car ==\n");
        System.out.print("Enter your name: ");
        String customerName = scanner.nextLine();

        viewAvailableCars();

        Car selectedCar = null;
        while (selectedCar == null) {
            System.out.print("\nEnter the number plate of the car you want to rent: ");
            String numberPlate = scanner.nextLine();
            for (Car car : cars) {
                if (car.getNumberPlate().equals(numberPlate) && car.isAvailable()) {
                    selectedCar = car;
                    break;
                }
            }
            if (selectedCar == null) {
                System.out.println("Invalid car number plate or car not available. Please try again.");
            }
        }

        System.out.print("Enter the number of days for rental: ");
        int rentalDays;
        try {
            rentalDays = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number of days.");
            return;
        }

        Date rentDate = new Date();
        System.out.print("Enter the initial odometer reading: ");
        int initialOdometer;
        try {
            initialOdometer = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid odometer reading.");
            return;
        }

        Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName);
        addCustomer(newCustomer);

        Rental rental = new Rental(selectedCar, newCustomer, rentalDays, rentDate, initialOdometer);
        double totalPrice = rental.calculateTotalPrice();
        System.out.println("\n== Rental Information ==\n");
        System.out.println("Customer ID: " + newCustomer.getCustomerId());
        System.out.println("Customer Name: " + newCustomer.getName());
        System.out.println("Car: " + selectedCar.getBrand() + " " + selectedCar.getModel());
        System.out.println("Rental Days: " + rentalDays);
        System.out.println("Rent Date: " + dateFormat.format(rentDate));
        System.out.println("Return Date: " + dateFormat.format(rental.getReturnDate()));
        System.out.println("Initial Odometer: " + initialOdometer);
        System.out.printf("Total Price: Rs. %.2f%n", totalPrice);

        System.out.print("\nConfirm rental (Y/N): ");
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("Y")) {
            rentCar(selectedCar, newCustomer, rentalDays, rentDate, initialOdometer);
            System.out.println("\nCar rented successfully.");
        } else {
            System.out.println("\nRental canceled.");
        }
    }

    private void returnCarMenu(Scanner scanner) {
        System.out.println("\n== Return a Car ==\n");

        Car carToReturn = null;
        while (carToReturn == null) {
            System.out.print("Enter the number plate of the car you want to return: ");
            String numberPlate = scanner.nextLine();
            for (Car car : cars) {
                if (car.getNumberPlate().equals(numberPlate) && !car.isAvailable()) {
                    carToReturn = car;
                    break;
                }
            }
            if (carToReturn == null) {
                System.out.println("Invalid car number plate or car is not rented. Please try again.");
            }
        }

        System.out.print("Enter the final odometer reading: ");
        int finalOdometer;
        try {
            finalOdometer = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid odometer reading.");
            return;
        }

        returnCar(carToReturn, finalOdometer);
    }

    private void searchCarMenu(Scanner scanner) {
        System.out.println("\n== Search Car by Brand or Model ==\n");
        System.out.print("Enter the brand or model to search: ");
        String query = scanner.nextLine();
        searchCarByBrandOrModel(query);
    }
}

public class Main {
    public static void main(String[] args) {
        CarRentalSystem rentalSystem = new CarRentalSystem();

        Car car1 = new Car("CBC-1322", "Toyota", "WagonR", 6000.00, 50.00);
        Car car2 = new Car("CAC-6880", "Toyota", "Prius", 8000.00, 70.00);
        Car car3 = new Car("CAB-9101", "Honda", "Vezel", 9000.00, 60.00);
        Car car4 = new Car("PH-2432", "Toyota", "KDH", 12500.00, 80.00);
        Car car5 = new Car("CAA-8855", "Toyota", "Prado", 17000.00, 100.00);
        Car car6 = new Car("CBC-1617", "BMW", "M5", 21000.00, 120.00);
        Car car7 = new Car("CBH-1819", "Benz-AMG", "GT", 25000.00, 150.00);
        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);
        rentalSystem.addCar(car3);
        rentalSystem.addCar(car4);
        rentalSystem.addCar(car5);
        rentalSystem.addCar(car6);
        rentalSystem.addCar(car7);

        rentalSystem.menu();
    }
}
