### TODOApp 

TODOApp is the sample app to get all the list of todos and their status

### Architecture 

Android MVVM Architecture with Retrofit, Coroutines, Hilt, Kotlin Flow and Room Database.
UnitTesting - MockWebserver, Mockito


Summary -> The view(Activity/Fragment) is used for TODOListing, ViewModel is responsible for getting the data(LiveData).
Room database has been used for storing the data, once the data is fetch from network. Room database is acting as the single 
source of truth. While fetching data, if data is available on databse it is fetch from there, in case its not available in database
it tries to get it from network and then store in the database

<img width="1054" alt="Screenshot 2022-10-12 at 12 01 44 PM" src="https://user-images.githubusercontent.com/11705671/195267199-aba47288-fe0c-4719-9ddf-a043657fe907.png">



### Unit testing 

Todo View Model Unit testing is present which is used along with Mockwebserver to test the network call of retrofit
Unit test has also been provided in android test for the Database (DAO) classes.


Basic flow is ->
  View -> ViewModel (With LiveData)->Repository(Flow)
                                        /    \
                           RemoteDataSource  LocalDataSource
                             Retrofit         Room


