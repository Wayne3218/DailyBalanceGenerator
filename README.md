# DailyBalanceGenerator


## Install and run the app
- Required : Maven, JDK 8
- To build the app, run `mvn clean install -U` in app root directory
- Navigate into `cd ./target`
- To run the app as customer : `java -jar .\DailyBalanceGenerator-1.jar`
- To run the app with reports : `java -jar .\DailyBalanceGenerator-1.jar report`

## App overview
This is a command line executable app with one optional argument built on Java 8.


## Assumptions 
- Main audiences for this app are the customers
- Secondary audiences are internal staff for investigations
- Data returned by API should not be altered
    - Duplicated data is found when assessing API returns, the app is not supposed to change/exclude them


## Design decision
- When the app is running in default mode (for customer), it will show the following:
    - App finished without any errors/data issue &rarr; Daily running balances will show
    - App finished with possible data issue (duplication in this case) &rarr; Daily running balances will show (including duplicated data)
    - App finished with error &rarr; error message `"Service Temporarily Unavailable"` will be displayed
      - this is to ensure that customer doesn't see partial balances (which will be incorrect to them)
- When the app is running with `report` flag on (for internal staff), it will display the followings for all scenarios mentioned above:
  - Errors &rarr; a list of errors encountered from start to finish
  - Flag Transactions &rarr; a list of flag transactions (currently only for duplicated transactions)
  - Full/partial daily running balance that's generated with/without errors


## Stretch goals
- Added duplicated transaction detection
- Added argument to generate a report for errors and flag transactions when running the app


## Things to be improved on
- Implement Logging for easier monitoring/traceability/troubleshooting 
- Currently, the app is taking a long time fetching from the provided API. Multithreading can be implemented to shorten the fetch time