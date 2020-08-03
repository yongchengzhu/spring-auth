### Run

1. Create environment variables necessary for file `application.properties`.
2. For development, run `mvn spring-boot:run` in root directory, and `npm start` in react directory.
3. For production, run `mvn package -P prod` then `java -jar target/auth-0.0.1-SNAPSHOT.jar`.



### To-dos
- Create annotation to access authentication context.
- Implement authorities / roles into users.
- Implement refresh token protocol.