
import java.util.*;

class Movie {
    String name;
    String[] shows;
    double price;
    int totalSeats;

    Movie(String name, String[] shows, double price, int totalSeats) {
        this.name = name;
        this.shows = shows;
        this.price = price;
        this.totalSeats = totalSeats;
    }
}

class Booking {
    int bookingId;
    String movieName;
    String showTime;
    int tickets;
    double amount;
    boolean active;

    Booking(int bookingId, String movieName, String showTime,
            int tickets, double amount) {
        this.bookingId = bookingId;
        this.movieName = movieName;
        this.showTime = showTime;
        this.tickets = tickets;
        this.amount = amount;
        this.active = true;
    }

    void display() {
        System.out.println("Booking ID : " + bookingId);
        System.out.println("Movie      : " + movieName);
        System.out.println("Show Time  : " + showTime);
        System.out.println("Tickets    : " + tickets);
        System.out.println("Amount     : Rs. " + amount);
        System.out.println("Status     : " +
                (active ? "Confirmed" : "Cancelled"));
        System.out.println("-----------------------------");
    }
}

public class MovieTicketBookingSystem {
    static Scanner sc = new Scanner(System.in);

    static Movie[] movies = {
        new Movie("Avengers",
                new String[]{"10:00 AM", "2:00 PM", "6:00 PM"},
                200, 50),
        new Movie("Leo",
                new String[]{"11:00 AM", "3:00 PM", "7:00 PM"},
                180, 40),
        new Movie("Jawan",
                new String[]{"9:00 AM", "1:00 PM", "5:00 PM"},
                150, 30)
    };

    static ArrayList<Booking> bookings = new ArrayList<>();
    static int nextBookingId = 1001;

    static void displayMovies() {
        System.out.println("\n--- Available Movies ---");
        for (int i = 0; i < movies.length; i++) {
            System.out.println((i + 1) + ". " + movies[i].name
                    + " | Ticket Price: Rs. " + movies[i].price);
        }
    }

    static void displayShows(Movie movie) {
        System.out.println("\nAvailable Shows for " + movie.name);
        for (int i = 0; i < movie.shows.length; i++) {
            System.out.println((i + 1) + ". " + movie.shows[i]);
        }
    }

    static void displaySeats(Movie movie) {
        int bookedSeats = 0;

        for (Booking b : bookings) {
            if (b.active && b.movieName.equals(movie.name)) {
                bookedSeats += b.tickets;
            }
        }

        System.out.println("Total Seats     : " + movie.totalSeats);
        System.out.println("Booked Seats    : " + bookedSeats);
        System.out.println("Available Seats : "
                + (movie.totalSeats - bookedSeats));
    }

    static void bookTicket() {
        displayMovies();
        System.out.print("Select movie number: ");
        int choice = sc.nextInt();

        if (choice < 1 || choice > movies.length) {
            System.out.println("Invalid movie selection!");
            return;
        }

        Movie movie = movies[choice - 1];
        displayShows(movie);

        System.out.print("Select show number: ");
        int showChoice = sc.nextInt();

        if (showChoice < 1 || showChoice > movie.shows.length) {
            System.out.println("Invalid show selection!");
            return;
        }

        displaySeats(movie);

        System.out.print("Enter number of tickets: ");
        int tickets = sc.nextInt();

        if (tickets <= 0) {
            System.out.println("Enter a valid number of tickets!");
            return;
        }

        int bookedSeats = 0;
        for (Booking b : bookings) {
            if (b.active && b.movieName.equals(movie.name)) {
                bookedSeats += b.tickets;
            }
        }

        int availableSeats = movie.totalSeats - bookedSeats;

        if (tickets > availableSeats) {
            System.out.println("Sorry! Not enough seats available.");
            return;
        }

        double amount = tickets * movie.price;

        System.out.println("\n--- Booking Summary ---");
        System.out.println("Movie   : " + movie.name);
        System.out.println("Show    : " + movie.shows[showChoice - 1]);
        System.out.println("Tickets : " + tickets);
        System.out.println("Amount  : Rs. " + amount);

        System.out.print("Confirm booking? (yes/no): ");
        String confirm = sc.next();

        if (confirm.equalsIgnoreCase("yes")) {
            Booking booking = new Booking(
                    nextBookingId++,
                    movie.name,
                    movie.shows[showChoice - 1],
                    tickets,
                    amount
            );

            bookings.add(booking);

            System.out.println("\nBooking successful!");
            System.out.println("Your Booking ID: " + booking.bookingId);
        } else {
            System.out.println("Booking not confirmed.");
        }
    }

    static void cancelBooking() {
        System.out.print("Enter Booking ID to cancel: ");
        int id = sc.nextInt();

        for (Booking b : bookings) {
            if (b.bookingId == id && b.active) {
                b.active = false;
                System.out.println("Booking cancelled successfully!");
                return;
            }
        }

        System.out.println("Active booking not found!");
    }

    static void searchBooking() {
        System.out.print("Enter Booking ID: ");
        int id = sc.nextInt();

        for (Booking b : bookings) {
            if (b.bookingId == id) {
                b.display();
                return;
            }
        }

        System.out.println("Booking not found!");
    }

    static void displayAllBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No bookings available.");
            return;
        }

        System.out.println("\n--- All Bookings ---");
        for (Booking b : bookings) {
            b.display();
        }
    }

    static void checkSeats() {
        displayMovies();
        System.out.print("Select movie number: ");
        int choice = sc.nextInt();

        if (choice < 1 || choice > movies.length) {
            System.out.println("Invalid movie selection!");
            return;
        }

        displaySeats(movies[choice - 1]);
    }

    public static void main(String[] args) {
        int option;

        do {
            System.out.println("\n================================");
            System.out.println("   MOVIE TICKET BOOKING SYSTEM");
            System.out.println("================================");
            System.out.println("1. Display Available Movies");
            System.out.println("2. Display Show Timings");
            System.out.println("3. Book Tickets");
            System.out.println("4. Cancel Booking");
            System.out.println("5. Search Booking by ID");
            System.out.println("6. Display All Bookings");
            System.out.println("7. Display Available Seats");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            option = sc.nextInt();

            switch (option) {
                case 1:
                    displayMovies();
                    break;

                case 2:
                    displayMovies();
                    System.out.print("Select movie number: ");
                    int choice = sc.nextInt();

                    if (choice >= 1 && choice <= movies.length) {
                        displayShows(movies[choice - 1]);
                    } else {
                        System.out.println("Invalid movie selection!");
                    }
                    break;

                case 3:
                    bookTicket();
                    break;

                case 4:
                    cancelBooking();
                    break;

                case 5:
                    searchBooking();
                    break;

                case 6:
                    displayAllBookings();
                    break;

                case 7:
                    checkSeats();
                    break;

                case 8:
                    System.out.println("Thank you for using the system!");
                    break;

                default:
                    System.out.println("Invalid choice! Try again.");
            }

        } while (option != 8);

        sc.close();
    }
}