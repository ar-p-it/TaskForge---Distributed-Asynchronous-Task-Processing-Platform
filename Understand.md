First: How To Read Any Spring Boot Repository

Whenever you open a Spring Boot project, follow this order:

1. Application Class
2. Controller
3. Service
4. Repository
5. Entity
6. Config
7. External Systems




Annotations:
What does @SpringBootApplication do?

This annotation is actually:

@Configuration
@EnableAutoConfiguration
@ComponentScan

combined.

Think:

Start Spring
Find Components
Create Objects
Wire Dependencies