# final-project-umbreon

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
- Provides "Refresh" button for latest updates (EXPERIMENTAL: DON'T USE IT)
