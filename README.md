# Ramsey Design Goals

The main goal of this project is to create an application which can
take an income, a basic rule for resource allocation (expenses/spending/savings)
and help the user create a budget.

Rather than creating a comprehensive itemized budget, as many tools do,
this application is meant to provide guidelines about resource allocation
based on the rule they supplied. For example, if a user decided that
they wanted to save 20% of their income, spend 10% of their income and allocate
70% of her income toward fixed expenses, the application could inform her
about how much money she would have to allocate to each of those categories
and give suggestions about spending within those categories.   

NOTE: this is a work in progress, it is by no means a complete product.

## Features Currently Working
- User can enter a set of categories and percentages and the application will generate
  a table showing how much money should be allocated to each category. This information
  is persisted to a database. The user can retrieve budget information for a given month
  and year by accessing <app_url>/#month/<month-num>/year/<year>

## In Progress
- Better graphical method for user to retrieve budget information
- Trends analysis (analysing changes in resource allocation over time)
- Improved UI. Current UI is a very basic skeleton used to test backend functionality.
- Validation - better input checking is required client-side. There are a lot of potential problems with this. 
- Ways to remove categories in UI. Currently categories can be added, but can't be removed.

## Application Flow

- User enters information from paystub, information about pay interval
- User enters categories they would like to divide income into (ex: save, spend, fixed)
- User enters rule by which they would like money to be allocated into the different categories
- Program calculates monthly income based on paystub information
- Program computes resources per category based on user supplied rule
- Interface displays in a grid amount of money per-category

## Running

- Start server with `lein ring server-headless`
- Run tests with `lein test`
