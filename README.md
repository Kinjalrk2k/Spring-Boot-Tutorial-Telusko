# Spring-Boot-Tutorial-Telusko

Learning Spring Boot

# Theory

## Spring Features

- POJO (Plain Old Java Object)
- Dependency Injection
- MVC (Model View Controller)
- REST (REpresentational State Transfer)
- Security
- Data
- AOP (Aspect Oriented Programming)
- _... and many more!_
- Can integrate with other frameworks (like Hibernate)

## Spring Boot

- It is not a replacement for Spring Framework
- We're still using Spring here
- Spring Boot just helps the Developer with dependencies and configurations
- Gives you a production ready application!
- When I waana do manual configuration?
  - Add properties in `application.properties`

## Deploying?

- Aam Zindegi:
  - Hardware (Cloud) -> OS (Linux) -> Web Server (Tomcat) -> App (WAR file)
- Mentos Zindegi:
  - Spring Boot provides an embedded server
  - We'll be making a JAR file and inside it we'll be having Tomcat (Embedded Server)
  - We can then directly run our project on JVM (We dont need a Web server)

## Dependency Injection

- It is a design pattern
- In OOP, we create objects and these objects are inter-dependant (creating an object graph)
- We want to achieve loose coupling.
- Unit testing is easy in loose coupling as it helps to create mock objects
- We dont want to hard-code the dependencies, instead want _someone else_ to give us the dependency. Here's where "injecting" a dependency comes to play.
- We can do this by using some external service who'll inject the dependency
- Spring has: Dependency Injection Containers. They creates the objects for us and then injects in our class
- We can annotate the dependency clases with `@Component` which makes them a component of the spring framework. And then use them in our class using `@Autowire`
