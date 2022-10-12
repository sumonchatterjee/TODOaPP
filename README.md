TODOApp is the sample app to get all the list of todos and their status
Architecture Used - Android MVVM Architecture with Retrofit, Coroutines, Hilt, Kotlin Flow and Room Database.
UnitTesting - MockWebserver, Mockito


Summary -> The view(Activity/Fragment) is used for TODOListing, ViewModel is responsible for getting the data(LiveData).
Room database has been used for storing the data, once the data is fetch from network. Room database is acting as the single 
source of truth. While fetching data, if data is available on databse it is fetch from there, in case its not available in database
it tries to get it from network and then store in the database

Unit testing - View Model Unit testing is present which is used along with Mockwebserver to test the network call of retrofit
Unit test has also been provided in android test for the Database (DAO) classes.


Basic flow is ->
  View -> ViewModel (With LiveData)->Repository(Flow)
                                        /    \
                           RemoteDataSource  LocalDataSource
                             Retrofit         Room


