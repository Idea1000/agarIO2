# AgarIO 2

## :twisted_rightwards_arrows: Branches

### 💻 Local

[mergeFinal](https://github.com/Idea1000/agarIO2/tree/mergeFinal)

### 📡 Online

[connecting_serv2](https://github.com/Idea1000/agarIO2/tree/mergeFinal](https://github.com/Idea1000/agarIO2/tree/connecting_serv2))

## 🚀 Start app 
*(make sure to use the correct branch depending on if you want to use local mode or online mode)*

### 💻 Start in local mode

run ```mvn clean install compile``` to install necessary dependencies<br>
then run ```mvn -pl Client javafx:run``` to start the game

**OR** (windows only)

start ```fullStartApp.bat``` the first time you start the game then ```quickStartApp.bat```

### 📡 Start in Online mode

:warning: Start the Server before the client !

#### For the client

Put the server ip in the **SERVERIP** variable in the file located in ```Client/src/main/java/fr/unicaen/iutcaen/network/Client.java```<br>
Then run ```mvn install``` to install necessary dependencies<br>
finaly run ```mvn -pl Client javafx:run``` to start the game

#### For the Server

run ```mvn install```<br>
then start the ```Launcher.java``` file located in the directory ```Client/src/main/java/fr/unicaen/iutcaen/launcher```

## :star2: Functionality

- Playable online with 2 player
- Playable locally
- Player can split
- Special pellets
- Adaptative AI
- Minimap
- Virus (trap for the player)

## :pushpin: Dependencies

- [Maven](https://maven.apache.org/)
- [Java JDK](https://www.oracle.com/java/technologies/downloads/)

## 📝 Authors

- [Aubignat Baptiste](https://github.com/baptiste-aubignat)
- [Brault Mathéo](https://github.com/Idea1000)
- [Cherrier Mathias](https://github.com/Dyonaea)
- [Courtois Louison](https://github.com/itchnisan)
- [Jonas Le Duff](https://github.com/PtitJoe)
- [Remo Tarrek](https://github.com/TarekRemo)
