ProgArk V2014 - Group no.13
=========

#Project priorities
## Non-implemented features
Key-features:
* Intro game screen: Graphics and all it's glory.
* Seperation between Multiplayer and Singleplayer
* New round for all players. Server assigning colors to players
* Stats in multiplayer and transistion between rounds.
*	Powerups
	1.	Throw-implementation.
		Idea: If you try to place a bomb at a location which already contains bomb. Throw it if that player has picked up throw-powerup.
	2.	Powerup graphics.
		Currently, just a placeholder image is given. Make static image placeholder for the various powerup-images in PowerUpType.java so save memory.
*	Graphics:
	1.	Enable magnitude range of bombs in UP, DOWN, LEFT, RIGHT directions after bomb impact.
		Image resources already available.
	2. Remove duplicate bears at startup and bears not controlled by anyone.


Non-key features:
*	Tool-assisted-movements
	Hard to move in-game. Make it a little easier?
*	Bomb.java
	Images are loaded every time in each Bomb-objected created which is very resourceful.
	Try make a static variables with the animations for Bomb's lifespan?


## Implemented features
*	Powerups:
	1.	Place additional bombs.
	2.	Increased player speed.


## Buggy features
*	Powerups:
	1.	Kick: Does not work as intended.
	2.	Movements: Hard to navigate through game. Especially when having high movement speed.
*	Server:
	1.	Currently, server does not assign colors to players connecting. Player figures are hard-coded.
		Reason: Java sockets give CorruptedSocketException because of trying to send over object before

# Setting up ADT project
```
File > New > Android Application Project
Application Name: BomberMan
Project Name:     BomberMan
Package Name:     bomberman.game

Finish project creation without editing anything standard.
Delete MainActivity.java in bomberman.game.

Install git bash. Go to BomberMan-folder and do commands written below.
```

# Initial git commands.
```
git init
git pull https://github.com/johnolos/yolo-bear.git

Alternatively:
git clone https://github.com/johnolos/yolo-bear.git

```

# Commit sequence
```
git add --all "folder"
git add "path_to_file_and_filename"
git commit -m "Message for commit"
git push https://github.com/johnolos/yolo-bear.git (master)
```
# After being used first time
```
git pull
git push
```

