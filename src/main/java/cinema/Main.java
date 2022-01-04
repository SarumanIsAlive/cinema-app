package cinema;

import cinema.lib.Injector;
import cinema.model.CinemaHall;
import cinema.model.Movie;
import cinema.model.MovieSession;
import cinema.model.User;
import cinema.security.AuthenticationService;
import cinema.service.CinemaHallService;
import cinema.service.MovieService;
import cinema.service.MovieSessionService;
import cinema.service.OrderService;
import cinema.service.ShoppingCartService;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    private static final Injector injector = Injector.getInstance("cinema");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);

        Movie lordOfTheRings = new Movie("Lord Of The Rings");
        lordOfTheRings.setDescription("u already know!");
        movieService.add(lordOfTheRings);
        System.out.println(movieService.get(lordOfTheRings.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall firstCinemaHall = new CinemaHall();
        firstCinemaHall.setCapacity(100);
        firstCinemaHall.setDescription("first hall with capacity 100");

        CinemaHall secondCinemaHall = new CinemaHall();
        secondCinemaHall.setCapacity(200);
        secondCinemaHall.setDescription("second hall with capacity 200");

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(firstCinemaHall);
        cinemaHallService.add(secondCinemaHall);

        System.out.println(cinemaHallService.getAll());
        System.out.println(cinemaHallService.get(firstCinemaHall.getId()));

        MovieSession tomorrowMovieSession = new MovieSession();
        tomorrowMovieSession.setCinemaHall(firstCinemaHall);
        tomorrowMovieSession.setMovie(lordOfTheRings);
        tomorrowMovieSession.setShowTime(LocalDateTime.now().plusDays(1L));

        MovieSession yesterdayMovieSession = new MovieSession();
        yesterdayMovieSession.setCinemaHall(firstCinemaHall);
        yesterdayMovieSession.setMovie(lordOfTheRings);
        yesterdayMovieSession.setShowTime(LocalDateTime.now().minusDays(1L));

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(tomorrowMovieSession);
        movieSessionService.add(yesterdayMovieSession);

        System.out.println(movieSessionService.get(yesterdayMovieSession.getId()));
        System.out.println(movieSessionService.findAvailableSessions(
                lordOfTheRings.getId(), LocalDate.now()));

        AuthenticationService authenticationService =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);
        User user = authenticationService.register("Sauron@mordor.com", "elfsmustdie");

        ShoppingCartService shoppingCartService =
                (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
        shoppingCartService.addSession(tomorrowMovieSession, user);
        shoppingCartService.addSession(yesterdayMovieSession, user);

        OrderService orderService = (OrderService) injector.getInstance(OrderService.class);
        System.out.println(orderService.completeOrder(shoppingCartService.getByUser(user)));
        orderService.getOrdersHistory(user).forEach(System.out::println);
    }
}
