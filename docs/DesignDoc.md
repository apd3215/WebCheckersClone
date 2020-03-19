
---  
geometry: margin=1in  
---  
# PROJECT Design Documentation  
  
> _The following template provides the headings for your Design  
> Documentation.  As you edit each section make sure you remove these  
> commentary 'blockquotes'; the lines that start with a > character  
> and appear in the generated PDF in italics._  
  
## Team Information  
* Team name: VibeCheckers  
* Team members  
  * Jonathan Baxley
  * Dhaval Shrishrimal
  * Joshua Yoder
  * Joseph Netti
  * Andre Decosta
  
## Executive Summary  
  This project is WebCheckers, it will provide an online tool for people to play checkers with their friends, provide statistics, play against AI, and spectate games.
  
### Purpose  
> _The goal of this project is to learn more about java spark web framework. The user group is anyone who wants to play checkers online. Our goal for users is that they have a positive experience playing WebCheckers online either against a real person or our AI._
> _Provide a very brief statement about the project and the most.
> important user group and user goals._  
  
### Glossary and Acronyms  
> _Provide a table of terms and acronyms._  
  
| Term | Definition |  
|------|------------|  
| UI | User Interface |  
  
  
## Requirements  
  
This section describes the features of the application.  
  >_Sign-in: Feature allows users to sign-in and create accounts from with to play checkers on. Also allows users to sign-out_
  >_Game Creation: Allows signed-in users to create a game with other signed-in users. Doesn't allow users to play with players in other games._
  >_Play Checkers: Checkers needs to be playable according to American Checkers Rules. This includes a couple epics including move making, resignation, and win conditions._
  >_Player must be able to play against an AI._
  >_User must be able to spectate other users games._
> _In this section you do not need to be exhaustive and list every  
> story.  Focus on top-level features from the Vision document and  
> maybe Epics and critical Stories._  
  
### Definition of MVP  
> _The minimum viable product is a website that has a functioning sign-in page, a home page that allows user to pick who to play against, a working checkers game that properly follows American Checkers rules, allows players to quit, has a proper win condition. In addition to this minimum viable product, it must also have enhancements of spectating games and playing against an AI of varying levels of difficulty._
> _Provide a simple description of the Minimum Viable Product._  
  
### MVP Features
> _Sign_in_
> _Game Creation_
> _Game resignation_
> _Move making_
> _King Crowning_
> _King Movement_
> _Win condition_
> _Provide a list of top-level Epics and/or Stories of the MVP._  
  
### Roadmap of Enhancements  
>_Play against AI_
>_AI difficulty selection_
>_Spectate game_
> _Provide a list of top-level features in the order you plan to consider them._  
  
  
## Application Domain  
  
This section describes the application domain.  
  
![The WebCheckers Domain Model](domain-model-placeholder.png)  
  
> _Provide a high-level overview of the domain for this application. You  
> can discuss the more important domain entities and their relationship  
> to each other._  
  
  
## Architecture and Design  
  
This section describes the application architecture.  
  
### Summary  
  
The following Tiers/Layers model shows a high-level view of the webapp's architecture.  
  
![The Tiers & Layers of the Architecture](architecture-tiers-and-layers.png)  
  
As a web application, the user interacts with the system using a  
browser.  The client-side of the UI is composed of HTML pages with  
some minimal CSS for styling the page.  There is also some JavaScript  
that has been provided to the team by the architect.  
  
The server-side tiers include the UI Tier that is composed of UI Controllers and Views.  
Controllers are built using the Spark framework and View are built using the FreeMarker framework.  The Application and Model tiers are built using plain-old Java objects (POJOs).  
  
Details of the components within these tiers are supplied below.  
  
  
### Overview of User Interface  
  
This section describes the web interface flow; this is how the user views and interacts  
with the WebCheckers application.  
  
![The WebCheckers Web Interface Statechart](web-interface-placeholder.png)  
  
> _Provide a summary of the application's user interface.  Describe, from  
> the user's perspective, the flow of the pages in the web application._  
  
  
### UI Tier  
> _Provide a summary of the Server-side UI tier of your architecture.  
> Describe the types of components in the tier and describe their  
> responsibilities.  This should be a narrative description, i.e. it has  
> a flow or "story line" that the reader can follow._  
  
> _At appropriate places as part of this narrative provide one or more  
> static models (UML class structure or object diagrams) with some  
> details such as critical attributes and methods._  
  
> _You must also provide any dynamic models, such as statechart and  
> sequence diagrams, as is relevant to a particular aspect of the design  
> that you are describing.  For example, in WebCheckers you might create  
> a sequence diagram of the `POST /validateMove` HTTP request processing  
> or you might show a statechart diagram if the Game component uses a  
> state machine to manage the game._  
  
> _If a dynamic model, such as a statechart describes a feature that is  
> not mostly in this tier and cuts across multiple tiers, you can  
> consider placing the narrative description of that feature in a  
> separate section for describing significant features. Place this after  
> you describe the design of the three tiers._  
  
  
### Application Tier  
> _Provide a summary of the Application tier of your architecture. This  
> section will follow the same instructions that are given for the UI  
> Tier above._  
  
  
### Model Tier  
> _Provide a summary of the Application tier of your architecture. This  
> section will follow the same instructions that are given for the UI  
> Tier above._  
  
### Design Improvements  
> _Discuss design improvements that you would make if the project were  
> to continue. These improvement should be based on your direct  
> analysis of where there are problems in the code base which could be  
> addressed with design changes, and describe those suggested design  
> improvements. After completion of the Code metrics exercise, you  
> will also discuss the resutling metric measurements.  Indicate the  
> hot spots the metrics identified in your code base, and your  
> suggested design improvements to address those hot spots._  
  
## Testing  
> _This section will provide information about the testing performed  
> and the results of the testing._  
  
### Acceptance Testing  
> _Report on the number of user stories that have passed all their  
> acceptance criteria tests, the number that have some acceptance  
> criteria tests failing, and the number of user stories that  
> have not had any testing yet. Highlight the issues found during  
> acceptance testing and if there are any concerns._  
  
### Unit Testing and Code Coverage  
> _Discuss your unit testing strategy. Report on the code coverage  
> achieved from unit testing of the code base. Discuss the team's  
> coverage targets, why you selected those values, and how well your  
> code coverage met your targets. If there are any anomalies, discuss  
> those._---  
geometry: margin=1in  
---  
# PROJECT Design Documentation  
  
> _The following template provides the headings for your Design  
> Documentation.  As you edit each section make sure you remove these  
> commentary 'blockquotes'; the lines that start with a > character  
> and appear in the generated PDF in italics._  
  