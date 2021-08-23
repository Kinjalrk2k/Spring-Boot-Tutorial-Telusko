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

# Implementation

## Autowire and Injecting Dependency

- Over the JVM, Spring gives a container: Spring Container
- This container will have objects these are called Spring Beans
- We've 2 scopes available in Spring

  - Singleton Scope
  - Prototype Scope

- The Spring container gets created on: `SpringApplication.run(FirstprojectApplication.class, args);`

  - It returns the context
  - The contianer is initialized here
  - Here it'll try to create all the objects

- Anotating a class with `@Component` tells the Spring Framework that we need the object of that class. The object will be created when the container is initialized

- Whenever we use the `context.getBean(Alien.class)`

  - It means that we need a Spring Bean of `Alien` type.
  - Spring check whether it is available (as it was created during the initialization) and is connected automatically
  - Spring injects that object in our application. This is called dependency injection

- Prototype Scope?

  - Even if we dont call `context.getBeans(...)`, the object will be created. Also, if we call `context.getBeans(...)` more than once, the object will be created only once!
  - The moment we'll start the application, Spring will give us the object
  - This is because, by default Spring used Singleton design Pattern
  - We can make change it to Prototype Scope by using the `@Scope(value = "prototype")` annotation on the class
    - By doing this, Spring will not create an instance by default
    - The moment we use `context.getBeans(...)`, the object will be created
    - So, here we'll be getting multiple objects if `context.getBeans(...)` is called multiple times

- Autowire

  - When an object is dependent on another object, we need to tell the Spring framework how are they dependent. We need to connect them.
  - We want Spring to search automatically for the object and inject it.
  - This can be done using `@Autowired` annotation over the instance variable we need to connect
  - Autowire searches for the type of the object required (by default) and connects it. For searching by name we can use Qualifier:
    - Name the component `@Component("lappy")`
      - The object that Spring creates will be named as `lappy`
    - Use the annotation with the autowire: `@Qualifier("lappy")`

<details>
<summary>Code!</summary>

- Create a class (the dependency)

```java
@Component
@Scope(value = "prototype")
public class Alien {
  private int aid;
  private String aname;
  private String tech;

  @Autowired
  private Laptop laptop;

  // ... getters and setters
}
```

- Another class

```java
@Component
public class Laptop {
  private int lid;
  private String brand;

  // ... getters and setters
}
```

- Use it in the main method using:

```java
ConfigurableApplicationContext context = SpringApplication.run(FirstprojectApplication.class, args);
Alien a = context.getBean(Alien.class);
```

</details>

# Creating a Web Application

## Workflow

- We can create any jsp pages provided it's under the `webapp` folder
- Next we need a controller
- Next we need a server!

## Controller

- It is a class in out main application
- A controller accepts our HTTP request
- Use `@Controller` annotation over the class
- A method inside this class should take up the request. Use `@RequestMapping("home")` annotation over the method to accept the "home" request
- Respone the request (`return something`)
  - If we use `@ResponseBody` annotation, it will send the response as a string [`res.send`]
  - Spring Boot doesn't support JSP by default. We neeed to add the support for it
    - Goto [MVNRepository](https://mvnrepository.com/)
    - Search for [Tomcat Jasper](https://mvnrepository.com/artifact/org.apache.tomcat/tomcat-jasper)
    - Pick up the version that matches with the embedded Tomcat installed in the project
    - Copy the Maven depencency code and paste it in the _`pom.xml`_ file inside `<dependencies>`
    - We just need to return the name of the page as string: `return "home.jsp";`. Spring boot knows where to search for the file (`webapp` directory)

## Server

- We need a server to run our project. But, we don't have a server
- But, we've an embedded Tomcat server in our Maven dependencies
- Starting the project, starts the internal Tomcat server
