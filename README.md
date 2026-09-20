# ATM Cash Shortage Prediction & Optimization


The solution analyses historical ATM transaction data to identify usage patterns, predict potential cash shortages, recommend replenishment actions, and analyse denomination usage across ATM locations.

## Problem

ATM cash management requires balancing customer availability with efficient cash allocation.

Insufficient cash can result in cash outs, downtime, lost transactions, and customer frustration, while excessive cash loading can increase idle cash and operational costs.

This prototype demonstrates how historical ATM transaction data can be converted into simple cash management recommendations.

## Core Features

### 1. ATM Usage Trend Analysis
- Analyses withdrawal behaviour by ATM and location
- Calculates average daily withdrawal demand
- Compares demand across salary days, public holidays, weekends, and normal weekdays
- Identifies high- and low-demand ATMs and locations
- Tracks ATM downtime incidents

### 2. Cash Shortage Prediction
- Estimates future ATM demand using historical day-type averages
- Calculates estimated cash depletion time
- Classifies shortage risk as Critical, High, Medium, or Low
- Recommends replenishment actions based on estimated depletion time
- Prioritises ATMs requiring replenishment

### 3. Denomination Analysis
- Analyses usage of R10, R20, R50, R100, and R200 notes
- Aggregates denomination demand by location
- Identifies the most frequently used denomination for each location

## Prediction Approach

The prototype uses transparent, rule-based calculations rather than machine learning.

The forecast date is classified as a:

- Salary Day
- Public Holiday
- Weekend
- Normal Weekday

Historical withdrawal behaviour for the corresponding day type is then used to estimate daily demand.

Estimated cash depletion is calculated as:

`Hours Remaining = (Current Cash Balance / Predicted Daily Demand) × 24`

The estimated depletion time is then mapped to a risk level and recommended action.


## Technology

- Java
- Eclipse IDE
- Java Standard Library
- CSV file input
- Console-based interface
- Git & GitHub

No external libraries, database, machine-learning model, or AI service is required to run the application.

## Running the Prototype

1. Clone or download this repository.
2. Open the project in Eclipse or another Java IDE.
3. Ensure the CSV files in the `data` directory are available.
4. Run `Main.java`.
5. Use the console menu to navigate between:
   - ATM Usage Trend Analysis
   - Cash Shortage Prediction
   - Denomination Analysis

## Prototype Data

The case study specified the data fields available to the solution but did not include an historical transaction dataset.

Mock test data was therefore created using the specified fields to demonstrate the prototype end-to-end. The dataset includes multiple ATMs and locations, transaction timestamps, withdrawals, deposits, cash balances, denomination usage, downtime, salary dates, and public holidays.

Findings produced from this dataset demonstrate the analytical capabilities of the prototype and should not be interpreted as actual bank-customer behaviour.

## Prototype Scope

This project is an MVP intended to demonstrate the core decision logic.

A production implementation could integrate:

- Real historical and live ATM data
- Validated and calibrated forecasting approaches
- ATM and capacity constraints
- Cash-in-transit scheduling and route optimisation
- Operational dashboards and APIs
- Production security, monitoring, and access controls

## AI Use

AI tools were used during development to support mock test-data generation, prioritisation of analysis findings, exploration and refinement of prediction and replenishment logic, and debugging.

Generated suggestions and outputs were reviewed and tested before being incorporated into the prototype. The final implementation uses rule-based calculations and thresholds that can be independently explained and evaluated.


## Author

**Neo Mmekwa**