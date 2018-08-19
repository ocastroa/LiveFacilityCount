Designed and developed an Android application based on the UCSC OPERS website that allows students to see how many people are at the gym 
•	Connected to the website using jsoup to select the number of people at the gym and the last check in
•	Stored all data into an SQLite database and defined a database controller for all operations
•	Optimized the runtime and workload by using background thread and AsyncTask to sync the app’s data with the website’s data
•	Implemented a SwipeRefreshLayout which updates the database and refreshes the contents of the views
