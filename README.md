# DailyBalanceGenerator
## Install and run the app
- Required : Maven, JDK 8
- To build the app, run `mvn clean install -U` in app root directory 
  - or to build with tests skipped : `mvn clean install -U -DskipTests` 
- Navigate into ./target folder : `cd target`
- To run the app as customer : `java -jar DailyBalanceGenerator-1.jar`
- To run the app with report : `java -jar DailyBalanceGenerator-1.jar report`

## App overview
This is a Java app that generates the daily running balance for customers

## Assumptions 
- Main audience for this app are the customers
- Secondary audience are internal staff for investigations
- Data returned by API should not be altered
    - Duplicated data was found when assessing API response, but the app should not exclude duplicated data because this app should maintain data integrity
    - This app should not show customers any balances if API response was not returned in full or if errors occurred

## Design consideration
- When the app is run without the `report` flag, it will show the following:
    - Daily running balance if app executes without any errors/data issue 
    - Daily running balance if app executes with possible data issue (i.e. duplicated data)
    - `"Service Temporarily Unavailable"` if app executes with error  
      - this is to prevent customers from seeing inaccurate daily running balance due to missing transactions
- When the app is running with the `report` flag on, it will show the following:
  - A list of errors encountered (if applicable)
  - A list of flagged transactions (if applicable) 
  - Full/partial daily running balance that is generated with/without errors


## Stretch goals
- Implement formatted logging which can be saved into storage or can be used with log management tools such as ELK
- The main performance issue is the long API fetch time. Multithreading can be implemented to shorten the fetch time and improve performance


## Things to be improved on
- Configuration
  - Moving configuration into a dedicated configuration file and use a configuration manager 
- Error/Exception handling 
  - Adding more details to custom exceptions to provide more insight into issues
- Json to Object mapping method 
  - Could be written to be generic instead of only handling TransactionPage object
- A library like RestTemplate could be used to handle both fetch data and map data instead of creating methods manually