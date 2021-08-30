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

# Web Application

## Workflow

- We can create any jsp pages provided it's under the `webapp` folder
- Next we need a controller
- Next we need a server!
- Change properties?

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

<details>
<summary>Code!</summary>

```java
@RequestMapping("home")
public String home() {
  System.out.println("Hello");
  return "home.jsp";
}
```

</details>

## Server

- We need a server to run our project. But, we don't have a server
- But, we've an embedded Tomcat server in our Maven dependencies
- Starting the project, starts the internal Tomcat server

## `applications.properties`

- By default Spring boot creates auto configuration. If we want some manual configuration, her's where we do that
- Path: _`src/main/resources/application.properties`_

```.properties
spring.mvc.view.prefix=/pages/
spring.mvc.view.suffix=.jsp
```

- The above properties tells spring boot that:
  - Our views are in a directory `/pages/` (inside `webapp` directory -> `/webapp/pages/`)
  - Our view files are of `.jsp` extension

## Handling Client data

### Receiving Query Parameters

- Data passed in the query parameters like: `/home?name=Kinjal`
- To get the request data, e need to use the `HttpServletRequest` object that is passed in our controller function

<details>
<summary>Code!</summary>

```java
@RequestMapping("home")
public String home(HttpServletRequest req) {
  String name = req.getParameter("name");
  System.out.println("Hello, " + name);
  return "home";
}
```

</details>

### Message Passing to JSP

- We need to send back dynamic data from our controller to the JSP page
- For that we need to set the data in a Session object

```java
HttpSession session = req.getSession();
session.setAttribute("name", name);
```

- `session.setAttribute` takes key folled by value
- In the JSP page, we can use the session values using JSP EL (Expression Language) format.

```jsp
<body>
  Welcome, ${name}
</body>
```

<details>
<summary>Code!</summary>

- Controller:

```java
@RequestMapping("home")
public String home(HttpServletRequest req) {
  String name = req.getParameter("name");
  System.out.println("Hello, " + name);

  HttpSession session = req.getSession();
  session.setAttribute("name", name);

  return "home";
}
```

- JSP

```jsp
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Home</title>
  </head>
  <body>
    Welcome, ${name}
  </body>
</html>

```

</details>

> Spring uses `RequestDispatcher` to call the JSP pages

## Using MVC Structure

- Instead of fetching the query params from the `HttpServletRequest`, we can directly get it in the Contoller function

```java
@RequestMapping("home")
public String home(@RequestParam("name") String myName, HttpSession session) {
  System.out.println("Hello, " + myName);
  session.setAttribute("name", myName);

  return "home";
}
```

- The `@RequestParam("name")` annotation tells that the query parameter that comes from the client is `name`, but it is mapped to the local variable `myName`
- `HttpSession` is automatically attached by Spring using Dependency Injection

### Using `ModelAndView`

- `ModelAndView` can be used to replace the `HttpSession`

```java
@RequestMapping("home")
public ModelAndView home(@RequestParam("name") String myName) {
  ModelAndView mv = new ModelAndView();
  mv.addObject("name", myName);
  mv.setViewName("home");

  return mv;
}
```

- We can set objects (data) and the view directly in the `ModelAndView` and return the same
- It is cleaner code!

### Using Model Object

- When we want to send multiple parameters or when we need to send an object, we'll use this
- Also we can accept a request in a serialized form of an object

```java
@Controller
public class HomeController {

  @RequestMapping("home")
  public ModelAndView home(Alien alien) {
    ModelAndView mv = new ModelAndView();
    mv.addObject("obj", alien);
    mv.setViewName("home");

    return mv;
  }
}
```

- The `alien` object is automatically created from the request parameters and can be used in our controller. The `Alien` class should have variable names same as the parameters passed in the request
- Similarly, we can send an object directly to the JSP using `mv.addObject`

<details>
<summary>Code</summary>

- _`model/Alien.java`_

```java
package com.kinjal.bootjpa.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Alien {

  @Id
  // @GeneratedValue -> auto generated incremened value
  private int aid;
  private String aname;

  // getters, setters and toString
}

```

</details>

## Database

### Setting up H2 Database

- H2 is a in-memory database
- Spring Boot will do the auto-configuration
- In _`application.properties`_:

```properties
spring.h2.console.enabled=true
spring.sql.init.platform=h2
spring.datasource.url=jdbc:h2:mem:testdb
spring.jpa.defer-datasource-initialization=true
```

- After running the Spring Boot Application, go to: `http://localhost:8080/h2-console` to access the console

### Generate tables

- We've to configure JPA for that
- Annotate the class (model class) with `@Entity` and a class variable (which acts like an ID) with `@Id`
- Spring boot will create a table for us
- If you want to add seed data, write the SQL INSERT Queries in a `data.sql` file in the _`resources`_ directory

### Inserting Data

- We need to insert data in the database from a request
- We need to create a repository for that. It is an interface which extends `CrudRepository`
  - Which extending the generic `CrudRepository` we just need to specify out model and the datatype of the primary key
- Next we need to autowire this interface in our controller

> Note: We didn't implement our repository interface, Sping does that for us. Spring Boot instantiate it and will look for the repository object.

- In our request method, we just need to do `repo.save(alien);` to insert the data in the database (`alien` is the object)

<details>
<summary>Code</summary>

- _`dao/AlienRepo.java`_

```java
package com.kinjal.bootjpa.dao;

import com.kinjal.bootjpa.model.Alien;

import org.springframework.data.repository.CrudRepository;

public interface AlienRepo extends CrudRepository<Alien, Integer> {

}

```

- _`controller/AlienController.java`_

```java
package com.kinjal.bootjpa.controller;

import com.kinjal.bootjpa.dao.AlienRepo;
import com.kinjal.bootjpa.model.Alien;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AlienController {

  @Autowired
  AlienRepo repo;

  @RequestMapping("/")
  public String home() {
    return "home.jsp";
  }

  @RequestMapping("/addAlien")
  public String addAlien(Alien alien) {
    repo.save(alien);
    return "home.jsp";
  }
}

```

</details>

### Fetching Data

```java
Alien alien = repo.findById(aid).orElse(new Alien());
```

- `.findById` can be used to find a data with the primary key
- `.orElse` ensures that if the data couldn't be found by `.findById`, something else is returned
  - `new Aline()` creates a blank object and returns it

### Service Layer

- In a controller, we're not always sure that the data is coming from the database. Sometimes it comes from network or input from user
- In case the data is coming from the repository, the service layer will interact with it

### Complex Queries

- We can declare our own queries in our repository instance
- Like if we want to query by `tech`, we'll use:

```java
List<Alien> findByTech(String tech);
```

- We need to name our methods as:
  - staring with `findBy....` or `getBy...`
  - and ending with the property name
  - for Spring to automatically create such queries for us
- Structure:

```
findBy<property><relation>
getBy<property><relation>
```

- Examples:
  - `findByTech`
  - `findByAidGreaterThan`

#### Defining my own query

- We can define our own query using the `@Query` annotation

```java
@Query("from Alien where tech=?1 order by aname")
List<Alien> findByTechSorted(String tech);
```

- In the above example, the method `findByTechSorted` will return the result of the query
- The query is in based on JPQL (Java Persistence Query Language) which is in turn based on HQL (Hibernate Query Language)
- We do not need to write `SELECT *` as its a part of the boilerplate code
- Parameters are passed as: `?1`, the number denotes the positions of the parameters passed in the query, as there can be multiple parameters passed to it using the method

# REST

### Basic

```java
@RequestMapping("/aliens")
  @ResponseBody
  public String getAliens() {
    return repo.findAll().toString();
  }
```

- The `repo.findAll` returns an iterable, but we need a String (JSON is a string right?), so we need to convert it into a `String` using the `.toString()` method.
- By default Spring expects a view name to be returned as a String, but in RESTful services, we'll return data. `@ResponseBody` annotation is used to specify the response dispatcher that we're returning a data
- This returns just a string, not in a JSSON format :"(

### Path params

```java
@RequestMapping("/alien/{aid}")
@ResponseBody
public String getAlien(@PathVariable int aid) {
  return repo.findById(aid).toString();
}
```

- Use the wildcard with `{...}`
- Use the `@PathVariable` annotation

### Need JSON?

- We'll need to use `JpaRepository` instead of `CrudRepository`
- This is because `JpaRepository`'s find methods returns `List` (`CrudRepository` returns `Iterable`)
- We needn't use `.toString` in our find methods anymore and set the return of the view function accordingly
- Spring will automatically return JSON data

```java
@RequestMapping("/aliens")
@ResponseBody
public List<Alien> getAliens() {
  return repo.findAll();
}

@RequestMapping("/alien/{aid}")
@ResponseBody
public Optional<Alien> getAlien(@PathVariable int aid) {
  return repo.findById(aid);
}
```

> `Optional` is a Generic, which can hols the type of data passes or `null`. When used with `JpaRepository`, `null` is returned when an object is not found in the database

### Content Negotiation

- Data can be received by a client in various formats. By default it's JSON, but we might also need to send data in XML format
- From the client side, we can set this using the `Accept` header
  - `application/json`
  - `application/xml`
- To get XML response, just install the dependency: [Jackson Dataformat XML](https://mvnrepository.com/artifact/com.fasterxml.jackson.dataformat/jackson-dataformat-xml)
- To restrict a route to use a single type of content:

```java
@RequestMapping(path = "/aliens", produces = { "application/xml" })
```

> When data is send from client to server, it is `consumes`. The other way round is `produces`

### POST Request

- Use the `@PostMapping` annotation, instead of `@RequestMapping`

> We need to use `@ResponseBody` with each route. Instead we can use `@RestController` instead of `@Controller`

```java
@PostMapping("/alien")
public Alien addAlien(@RequestBody Alien alien) {
  repo.save(alien);
  return alien;
}
```

- Remeber to use `@RequestBody` annotation to receive a JSON data

### DELETE Request

```java
@DeleteMapping("/alien/{aid}")
public String deleteAlien(@PathVariable int aid) {
  Alien toDelete = repo.getById(aid);
  repo.delete(toDelete);

  return "Deleted";
}
```

### PUT Request

- If the data is present, then update or else create a new data

```java
@PutMapping("/alien")
public Alien saveOrUpdateAlien(@RequestBody Alien alien) {
  repo.save(alien);
  return alien;
}
```

## Spring Data REST

- Refractoring RESTful Applications in Spring!
- We no more need the Controller. Our Repository can handle all the requests

```java
@RepositoryRestResource(collectionResourceRel = "aliens", path = "aliens")
public interface AlienRepo extends JpaRepository<Alien, Integer> {

}

```
