package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.CarService;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context =
              new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

      User user1 = new User("Maxim","Galkin","BetterThatPetrosyan@gmail.com");
      Car car1 = new Car("AUDI",2023);
      user1.setCar(car1);
      car1.setUser(user1);
      userService.add(user1);

      User user2 = new User("Alla","Pugacheva","BetterThatMadonna@gmail.com");
      Car car2 = new Car("Zeekr",001);
      user2.setCar(car2);
      car2.setUser(user2);
      userService.add(user2);

      //проверка на oneToOne
      System.out.println(car1.getModel());
      System.out.println(car1.getUser().getFirstName());
      System.out.println(car1.getSeries());
      System.out.println("_______________________________");
      System.out.println(user1.getCar().getModel());
      System.out.println(user1.getCar().getUser().getFirstName());
      System.out.println(user1.getCar().getSeries());

      List<User> users = userService.getUserByCarModelAndSeries("Zeekr",001);
      for (User user : users) {
         System.out.println("Id = "+user.getId());
         System.out.println("First Name = "+user.getFirstName());
         System.out.println("Last Name = "+user.getLastName());
         System.out.println("Email = "+user.getEmail());
         System.out.println();
      }
      context.close();
   }
}
