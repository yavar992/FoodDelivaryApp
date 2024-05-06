
# TasteWheels

Introducing FlavorDash: Elevate Your Culinary Experience

FlavorDash is not just another food delivery platform - it's a gastronomic adventure waiting to unfold. With FlavorDash, users embark on a journey through a vibrant culinary landscape, where every dish tells a story and every bite ignites the senses.

For Food Enthusiasts:
Step into a world of flavor exploration with FlavorDash. From mouthwatering street eats to exquisite fine dining, our curated selection of restaurants offers something to tantalize every taste bud. Browse through a kaleidoscope of cuisines, discover hidden gems in your neighborhood, and embark on a culinary adventure like never before. With our intuitive platform, ordering your favorite meals is as easy as a few taps, and our real-time tracking ensures that your food journey is seamless from start to finish.

For Restaurant Visionaries:
Join the FlavorDash revolution and unleash your culinary creativity to the world. Our platform provides a stage for restaurant owners to showcase their culinary masterpieces and connect with a diverse community of food enthusiasts. Seamlessly manage menus, track orders in real-time, and delight customers with unparalleled service. With FlavorDash, the spotlight is on you, as we celebrate the artistry and passion behind every dish.

For Delivery Mavericks:
Become a FlavorDash hero and embark on a mission to deliver happiness, one meal at a time. As a part of our dedicated delivery team, you'll navigate the city streets with speed and precision, ensuring that every order reaches its destination fresh and on time. Our integrated chat feature keeps you connected with users and restaurant owners, providing updates and fostering a sense of camaraderie along the way. Join us in shaping the future of food delivery and leave your mark on the FlavorDash legacy.

Experience FlavorDash:
Prepare to tantalize your taste buds and elevate your dining experience with FlavorDash. Whether you're a food enthusiast seeking new culinary adventures, a visionary restaurant owner ready to shine, or a delivery maverick with a passion for service, FlavorDash invites you to join us on a journey of flavor discovery. Get ready to savor the extraordinary and redefine the way you dine, one delicious moment at a time. FlavorDash - where every bite is a celebration of taste, passion, and community.

## Installation

``Step 1``: Set Up MySQL Database

1. Install MySQL if you haven't already. You can download it from the official MySQL website: MySQL Downloads.

2. Once installed, start the MySQL service and log in to the MySQL command-line client using the root user or a user with administrative privileges:

```bash
  mysql -u root -p
```
3. Create a new database for your Spring Boot project:
```bash
CREATE DATABASE userservice;
```
4. Create a user and grant necessary privileges to the database:
```bash
CREATE USER 'tastewheels'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON userservice.* TO 'tastewheels'@'localhost';
FLUSH PRIVILEGES;
```
``Step 2`` : Set Up Spring Boot Project

1. Create a new Spring Boot project named TasteWheels using your preferred IDE or Spring Initializr: Spring Initializr.
2. Add the required dependencies for Spring Boot and MySQL to your pom.xml (if using Maven) or build.gradle (if using Gradle).
3. Configure the database connection in your application.properties or application.yml file:

```bash
spring.datasource.url=jdbc:mysql://localhost:3306/userservice
spring.datasource.username=tastewheels
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5Dialect
```
``Step 3 `` : Set Up the Spring Boot Application
1. Build your Spring Boot project using Maven or Gradle.
2. Run the application using your IDE or command-line interface:

```bash
java -jar tastewheels.jar
```

``Step 5 `` : Test the Application
1. Use tools like Postman or Swagger UI to test your API endpoints.
2. Perform CRUD operations to ensure that data is correctly persisted and retrieved from the MySQL database## Technologies Used

- Spring Boot
- MySQL
- Thymeleaf (for front-end templating)
- Bootstrap (for styling)
- etc.

## Documentation

[Documentation](http://localhost:8082/happyMeals/swagger-ui/index.html#/)


## Project Structure


- src/
    - main/
        - java/
            - com.tastewheels/
                - controller/
                - service/
                - repository/
                - model/
        - resources/
            - application.properties
            - templates/
            - static/
    - test/
## Contributing

Contributions are always welcome!

See `contributing.md` for ways to get started.

Please adhere to this project's `code of conduct`.


## Contributing.md

# Contributing to TasteWheels

Thank you for considering contributing to TasteWheels! We appreciate any contributions that help improve the project and make it better for everyone. By contributing, you'll help us build a more robust and feature-rich platform.

Contribution Guidelines
Before contributing, please take a moment to review the following guidelines:

**Contribution Guidelines**

Before contributing, please take a moment to review the following guidelines:

***1. Fork the Repository:*** Fork the TasteWheels repository to your GitHub account.

***2. Clone the Repository:*** Clone the forked repository to your local machine using the following command:
```bash
git clone https://github.com/yavar992/FoodDelivaryApp.git
```
***3. Create a New Branch:*** Create a new branch to work on your contribution:

```bash
git checkout -b feature/new-feature
```
***4. Make Changes:*** Implement your changes or additions to the project. Ensure that your code follows the project's coding conventions and style guidelines.  
***5. Test Your Changes:*** Test your changes thoroughly to ensure that they work as expected and do not introduce any regressions.           
***6. Commit Your Changes:***   Commit your changes with a descriptive commit message that explains the purpose of your changes:
```bash
git commit -m "Add new feature"
```
***7. Push Changes:*** Push your changes to your forked repository:
```bash
git push origin feature/new-feature
```

**What You Can Contribute**              
You can contribute to TasteWheels in various ways, including but not limited to:

1. Adding new features or functionalities
2. Fixing bugs or issues
3. Improving documentation
4. Refactoring code for better performance or readability
5. Writing tests to increase test coverage
   Providing feedback and suggestions for improvement

**Code Review Process**   
All pull requests will be reviewed by the project maintainers. During the review process, feedback may be provided, and changes may be requested before the pull request is merged into the main repository.
## Feedback

If you have any feedback, please reach out to us at yavarkhan892300@gmail.com


## FAQ

#### does this prooject completed ?

NO this project in under development


## Authors

- [@yavar992](https://github.com/yavar992)


## License

[MIT](https://choosealicense.com/licenses/mit/)

