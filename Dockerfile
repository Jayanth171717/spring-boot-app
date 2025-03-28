from maven:3.8.5-openjdk-17 as build
copy . .
run mvn clean package -DskipTests

from openjdk:17.0.1-jdk-slim 
copy --from=build /target/EmployeeFormApp-0.0.1-SNAPSHOT.jar EmployeeFormApp.jar
expose 8080
entrypoint ["java","-jar","EmployeeFormApp.jar"]