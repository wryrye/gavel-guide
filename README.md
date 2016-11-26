# final-project-umbreon
Milestone #3  
Changes from last milestone:
- Added API call to get all of the pairings  
- Created an application array to hold all of these pairings to be used throughout the app  
- Added methods in pairing arrays class to disntiguish between current round pairings and previous results  
- Created RecyclerView and PairingAdapter to display the pairings for the current round in the first tab of the main screen  
- Created RecyclerView and PairingAdapter to display the pairings for previous results in the second tab of the main screen  
- Added Splash Screen to make sure API call is returned before starting the main activity  
- Added API call to display the information for a current round pairing  
- Display the information for a current round pairing in new activity screen called ViewPairing  
	-onClick method for these items not currently working, please do not press those at this time since it will cause the app to crash  
	-To get to the pairings page, there are buttons underneath recyclerview in the Pairings Tab (onClick for the pairings does not work currently, but will work in the final build)  
- Add button which sends an intent to the Google Maps App to display walking directions to the pairing location  
- Add data storage for all API calls  


Requirements:  
GPS  
- Intent to open Google Maps and provide directions to the round location added to the pairings info screens

Local Data Storage:
- Everytime we make an API call, we also write the data to local storage.
- If no internet is available when app boots up, will get data from local data storage if available (atleast one API call has been made prior)

Web Service:  
All endpoints constructed and added to the web service described in the readme  
Endpoints include:  
- Get current standings
- Get all pairings information
- Get more specific info about one pairing  

/  
/  
/  
/  
/  

Milestone #2
Our Custom Web Service:

Code found here: https://github.com/bjw4ph/GavelGuide  
Hosted Here: http://ec2-54-145-200-199.compute-1.amazonaws.com:1234/   
Our app uses a Node.js server with a MongoDB database hosted on an t2.micro instance through Amazon Web Services  

Web Service Implementation: 
- Tab 3 (Standings) GET's data from Node.js server at endpoint /getRankedTeams, and displays them in the RecyclerView

Screen loads:  
- Obviously.

Local Data Storage:  
- Everytime we make an API call, we also write the data to local storage.  
- If no internet is available during MainActivity's onCreate(), app will get data from local data storage if available (atleast one API call has been made prior)

User requirements:
- Presents standings in sorted fashion
