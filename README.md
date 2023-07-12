# Spending Tracker Tool

Welcome to the Spending Tracker Tool! You can track your transactions here and check out interesting statistics about them.

## Prerequisites

Before getting started with the project, ensure you have the following technologies installed and configured on your machine:

- JDK 17
- Maven
- MongoDB


## MongoDB

MongoDB is necessary for running the application.
Configuration can be set under the application.properties file.
There has to be a database named "stt".

## Getting Started

To get started with the project, follow these steps:

1. Clone the repository to your local machine using the following command:

```shell
git clone https://github.com/jfenyovari/Spending-Tracker-Tool.git
```

2. Change to the project directory:

```shell
cd Spending-Tracker-Tool
```

3. Build the project using Maven:

```shell
mvn clean install
```

4. Run the project:

```shell
Import the given run configuration from the /runConfigurations/run/ folder.
```

## Additional information
1. There are some prepared http requests under the postman folder for testing the application.
2. At first run, the application inserts 100 transaction into the database.
