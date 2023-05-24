import java.util.Scanner;

class Employee {
    private String name;
    private int id;
    private String jobTitle;

    public Employee(String name, int id, String jobTitle) {
        this.name = name;
        this.id = id;
        this.jobTitle = jobTitle;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getJobTitle() {
        return jobTitle;
    }
}

class Customer {
    private String name;
    private int id;
    private String contactInfo;

    public Customer(String name, int id, String contactInfo) {
        this.name = name;
        this.id = id;
        this.contactInfo = contactInfo;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getContactInfo() {
        return contactInfo;
    }
}

class Car {
    private static int nextId = 1;

    private int id;
    private String make;
    private String model;
    private int year;
    private double price;

    public Car(String make, String model, int year, double price) {
        this.id = nextId++;
        this.make = make;
        this.model = model;
        this.year = year;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public double getPrice() {
        return price;
    }
}

class Purchase {
    private Customer customer;
    private Car car;
    private String date;

    public Purchase(Customer customer, Car car) {
        this.customer = customer;
        this.car = car;
        this.date = java.time.LocalDate.now().toString();
    }

    public double getTotalCost() {
        return car.getPrice();
    }
}

class Rental {
    private Customer customer;
    private Car car;
    private String startDate;
    private String endDate;

    public Rental(Customer customer, Car car, String startDate, String endDate) {
        this.customer = customer;
        this.car = car;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public double getTotalCost() {
        // Calculate the number of days rented
        long daysRented = java.time.temporal.ChronoUnit.DAYS.between(java.time.LocalDate.parse(startDate),
                java.time.LocalDate.parse(endDate));
        return car.getPrice() * daysRented;
    }
}

class Inventory {
    private Car[] cars;

    public Inventory(Car[] cars) {
        this.cars = cars;
    }

    public void displayInventory() {
        System.out.println("ID\tMake\tModel\tYear\tPrice");
        for (Car car : cars) {
            System.out.println(car.getId() + "\t" + car.getMake() + "\t" + car.getModel() + "\t" + car.getYear() + "\t"
                    + car.getPrice());
        }
    }
}

public class CarAgency {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Employee[] employees;
        Customer[] customers;
        Car[] cars;
        Inventory inventory;

        try {
            // Add employees to the system
            System.out.println("Enter the number of employees:");
            int numEmployees = scanner.nextInt();
            employees = new Employee[numEmployees];
            for (int i = 0; i < numEmployees; i++) {
                System.out.println("Enter the name of employee " + (i + 1) + ":");
                String name = scanner.next();
                System.out.println("Enter the ID of employee " + (i + 1) + ":");
                int id = scanner.nextInt();
                System.out.println("Enter the job title of employee " + (i + 1) + ":");
                String jobTitle = scanner.next();
                employees[i] = new Employee(name, id, jobTitle);
            }

            // Add customers to the system
            System.out.println("Enter the number of customers:");
            int numCustomers = scanner.nextInt();
            customers = new Customer[numCustomers];
            for (int i = 0; i < numCustomers; i++) {
                System.out.println("Enter the name of customer " + (i + 1) + ":");
                String name = scanner.next();
                System.out.println("Enter the ID of customer " + (i + 1) + ":");
                int id = scanner.nextInt();
                System.out.println("Enter the contact information of customer " + (i + 1) + ":");
                String contactInfo = scanner.next();
                customers[i] = new Customer(name, id, contactInfo);
            }

            // Add cars to the system
            System.out.println("Enter the number of cars:");
            int numCars = scanner.nextInt();
            cars = new Car[numCars];
            for (int i = 0; i < numCars; i++) {
                System.out.println("Enter the make of car " + (i + 1) + ":");
                String make = scanner.next();
                System.out.println("Enter the model of car " + (i + 1) + ":");
                String model = scanner.next();
                System.out.println("Enter the year of car " + (i + 1) + ":");
                int year = scanner.nextInt();
                System.out.println("Enter the price of car " + (i + 1) + ":");
                double price = scanner.nextDouble();
                cars[i] = new Car(make, model, year, price);
            }

            inventory = new Inventory(cars);

            // Display the inventory
            System.out.println("Inventory:");
            inventory.displayInventory();

            // Allow customers to browse and purchase/rent cars
            System.out.println("Enter the ID of the customer:");
            int customerId = scanner.nextInt();
            Customer customer = null;
            for (Customer c : customers) {
                if (c.getId() == customerId) {
                    customer = c;
                    break;
                }
            }
            if (customer == null) {
                System.out.println("Customer not found.");
                return;
            }
            System.out.println("Welcome, " + customer.getName() + "!");
            System.out.println("Would you like to browse available cars? (y/n)");
            String browseResponse = scanner.next();
            if (browseResponse.equalsIgnoreCase("y")) {
                inventory.displayInventory();
                System.out.println("Enter the ID of the car you would like to purchase/rent:");
                int carId = scanner.nextInt();
                Car car = null;
                for (Car c : cars) {
                    if (c.getId() == carId) {
                        car = c;
                        break;
                    }
                }
                if (car == null) {
                    System.out.println("Car not found.");
                    return;
                }
                System.out.println("Would you like to purchase or rent this car? (p/r)");
                String purchaseResponse = scanner.next();
                if (purchaseResponse.equalsIgnoreCase("p")) {
                    Purchase purchase = new Purchase(customer, car);
                    System.out.println("Purchase complete. Total cost: " + purchase.getTotalCost());
                } else if (purchaseResponse.equalsIgnoreCase("r")) {
                    System.out.println("Enter the start date of the rental (yyyy-mm-dd):");
                    String startDateString = scanner.next();
                    System.out.println("Enter the end date of the rental (yyyy-mm-dd):");
                    String endDateString = scanner.next();
                    Rental rental = new Rental(customer, car, startDateString, endDateString);
                    System.out.println("Rental complete. Total cost: " + rental.getTotalCost());
                } else {
                    System.out.println("Invalid response.");
                }
            } else if (browseResponse.equalsIgnoreCase("n")) {
                System.out.println("Thank you for visiting our car agency!");
            } else {
                System.out.println("Invalid response.");
            }
        } catch (Exception e) {
            System.out.println("There is an error in " + e.getMessage());
        } 
    }
}

