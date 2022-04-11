# DailyBalanceGenerator


## Install and run the app
- Required : Maven, JDK 8
- To build the app, run `mvn clean install -U` in app root directory or to build with tests skipped `mvn clean install -U -DskipTests` 
- Navigate into ./target folder : `cd ./target`
- To run the app as customer : `java -jar DailyBalanceGenerator-1.jar`
- To run the app with reports : `java -jar DailyBalanceGenerator-1.jar report`

## App overview
This is a Java app that generates daily running balance for customer.


## Assumptions 
- Main audience for this app are the customers
- Secondary audience are internal staff for investigations
- Data returned by API should not be altered
    - Duplicated data is found when assessing API returns, the app is not supposed to exclude them
    - This app needs to maintain the data integrity, return result needs to match what the API is provided
    - This app shouldn't show customers any balances when API fetching was only done partially or with errors

## Design consideration
- This app is not meant to validate/alter data. The main task is to calculate the daily running balance with duplicated transaction tracking
- When the app is running in default mode (for customer), it will show the following:
    - App finished without any errors/data issue &rarr; Daily running balances will show
    - App finished with possible data issue (duplication in this case) &rarr; Daily running balances will show (including duplicated data)
    - App finished with error &rarr; error message `"Service Temporarily Unavailable"` will be displayed
      - this is to ensure that customer doesn't see partial balances (which will be incorrect to them)
- When the app is running with `report` flag on (for internal staff), it will display the followings for all scenarios mentioned above:
  - Errors &rarr; a list of errors encountered from start to finish
  - Flagged Transactions &rarr; a list of flagged transactions (currently only for duplicated transactions)
  - Full/partial daily running balance that's generated with/without errors


## Stretch goals
- Added duplicated transaction detection
- Added argument to generate a report for errors and flagged transactions when running the app
- Implement proper formatted logging in the app to be saved into storage space and be used with log management tools such as ELK
- Currently, the main performance bottleneck happens when the app is fetching data from provided API. Multithreading can be implemented to shorten the fetch time to improve performance


## Things to be improved on
- Error/Exception handling can be improved on. Currently, custom exceptions are written for fetch and parse data. However, there are no details for those exceptions
- Json to Object mapping method could be written to be generic instead of only handling TransactionPage object
- Other library, such as RestTemplate, could be used to handle both fetch data and map data instead of writing the methods