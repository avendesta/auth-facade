# Auth Facade Application

## How to Compile and Run the Application

To compile and run the application, use the following commands:

```bash
./mvnw clean install && ./mvnw spring-boot:run
```

## Future Improvements

1. **Thread Pool Configuration**  
   - Configure the JVM to use a specific number of threads in the thread pool to maximize the application's ability to handle concurrent requests efficiently.

2. **Switch to Gradle**  
   - Gradle could be a better alternative to Maven due to its flexible scripting capabilities and better handling of multi-modular projects.

3. **Optimize Non-Blocking Behavior**  
   - Since the application uses `Flux` for reactive programming, it is already non-blocking. However, there might still be opportunities to optimize thread usage for better performance.

4. **Caching for External Endpoint**  
   - Implement caching to reduce the number of calls to the external endpoint, improving performance and reducing latency.

5. **Spring Boot Actuator**  
   - I added the Spring Boot Actuator dependency, which provides endpoints to monitor the application's health and view available APIs easily.

6. **Test not written**