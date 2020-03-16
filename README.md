

# Schoology Demonstration Project
## Summary
This is a demo application base on Java and Spring boot. Also this application is using docker 
in order to make things easy to run in a new enviroment and to build automated CI/CD pipelines

## Prerequisites
Make sure that you have all the tools described in this section before run the application
1. Java at least on version 8
2. A valid instalation of docker in the local machine that can run a docker-compose file and build containers
3. Maven in version 3 or higher

## Running the application
### First step
Clone or download the source code using the link "https://github.com/FilipeFit/course-demo.git" in a folder that you prefer
EX: git clone https://github.com/FilipeFit/course-demo.git

### Second Step
Import the project in a IDE like Intellij, this project is using maven as de dependency manager, so since the project is 
imported just wait a little in order to maven build a download all the dependencies
IMPORTANT: Make sure that you don't have any other application running in por 8080 or 27017 to avoid issues with mongo and the 
application itself

OBS: If you need to change any ports or global configurations of the application just change de application.properties fi

## Third Step 
Build the application sugin maven to generate th jar file "mvn clean install" 
Build the application container using the command "docker build -f Dockerfile -t schoology ."
Use the command "docker-compose up" to run the application and mogodb as well and that's it the application is running 

## API Usage
The Application is to expose a CRUD API to manage courses like explained bellow

| URL                       |            HTTP Method         | Description                    |Payload                    |
| ------------------------- | ------------------------------ | ------------------------------ | --------------------------|
| `/v1/courses`             | GET                            | Returns a list of courses      |                           |
| `/v1/courses`             | POST                           | Add a new course               |`{ name: String, description: String, active: boolean}`
| `/v1/courses/{id}`        | DELETE                         | Remove a course by its id      |                           |
| `/v1/courses/{id}`        | UPDATE                         | UPDATE a course by its id      |`{ name: String, description: String, active: boolean}`                          |
| `/v1/courses/search?name=`| GET                            | Search all courses by the name |                           |

## Curl for the operations
GET /v1/courses:
curl --location --request GET 'localhost:8080/v1/courses'

POST /v1/courses
curl --location --request POST 'localhost:8080/v1/courses' \
--header 'Content-Type: application/json' \
--data-raw '{
	"name": "test",
	"description": "test",
	"active": true
}'

DELETE /v1/courses
curl --location --request DELETE 'localhost:8080/v1/courses/5e6e8bb8599d28324241cb60'

PUT /v1/courses
curl --location --request PUT 'localhost:8080/v1/courses/5e6f791cb252db7ea0fd4b19' \
--header 'Content-Type: application/json' \
--data-raw '{
	"name": "test 1",
	"description": "change description",
	"active": true
}'

GET /v1/courses/search?name=

curl --location --request GET 'localhost:8080/v1/courses/search?name=test%201'

## Tests
The code have a Test structure using Mockito and jUnit 5 as base in oder to make unit testing

Also if it's require we can build integration test using RestAsured framework 

## Next Steps
1 - Add security using oAuth2 and a security server 
2 - Include Integration tests
3 - CI-CD automation and code quality tools

## Final considerations
Hope that show a little of my skillset and you guys enjoy the application :)



