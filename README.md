# CRMApp
Customer Relation Management System App

Application for Customer Relationship Management System 

General Features of the App
This directory contains the full implementation of the basic features in a Customer Relationship Management System (It is named CRM App). The app will display the list of customers including the details of the customers such as
1. id(customer_id)
2. name
3. status 
4. date(created date)


On top of the screen there are arrows facing upwards depicting ascending order and the arrows facing downwards depicting descending order. So by clicking the arrows next to the relevant title (id, Name, status, date) users shall sort the list. In the top righthand corner, there is a filter button. By tapping on it users will be shown a seperate dialog in the screen where they can filter based on the status. Users are allowed to select more than one option. Once the ok button for the selections is clicked the users will be taken back to the landing page of the app with the filtered list.
By clicking the bottom floating button users can add new customer information. By tapping on a particular customer in the list a new interface with customer details of that customer will be shown along with the list of opportunities (one or more) related to that customer. Users can edit customer details by clicking on the "Edit" button. Opportunities can be editted and new opportunities can be added using the respective buttons in the opportunities segment.

Technical Configuration
CompileSDK: 32
Target SDK: 32
Minimum SDK: 26

AndroidManifest.xml 
This xml file consists of the permissions such as Internet permission and Network access state inorder to use Volley, which is a library used to handle networking in Android Apps faster and easier (https://google.github.io/volley/).

build.gradle(Module)
Dependencies for the following were added:
volley, live data, viewmodeletc.

java directory 
The following java files are organized inside the java directory. 
1. activites 
2.adapters
3. dialogfragments
4.fragments
5.models
6. utility
7.viewholders

RecyclerView's adapters and viewholders are used to list customerdetails as well as opportunities. Each element in the list is designed as a CardView in the layout file. In order to decouple or modularize customer details and opportunities are handled as seperate ui elements. Further they are kept in seperate data model classes such as "Customer Information" and "Opportunities" so that each can be handled seperatly enhancing seperation of concerns. The fragment used to add customerdetails in the landing page is reused (reusability) from a seperate place for editing. Likewise, the fragment used for adding new opportunity is reused for editing each opportunity. LiveData with observable in Viewmodels are used to handle changing data. Further since the api calls are handled in viewmodels it can be accessed and used by the other parts of the project too. FragmentResultListener is used to parse data as bundle and receive results in the host activity.

