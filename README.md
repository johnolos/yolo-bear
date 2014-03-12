ProgArk V2014 - Group no.13
=========

Table-of-Contents:
------------------
*	[Project priotiries](#project-priorities)
	1.	[Non-implemented features](#non-implemented-features)
		1.	[Key features](#key-features)
		2.	[Non-key features](#non-key-features)
	2.	[Implemented features](#implemented-features)
	3.	[Buggy features](#buggy-features)
*	[Setting up ADT project](#setting-up-adt-project)
	1.	[Initial git commands](#initial-git-commands)
	2.	[Commit sequence](#commit-sequence)
	3.	[After being used first time](#after-being-used-first-time)
	

#Project priorities
[[Back to top]](#table-of-contents)
## Non-implemented features
[[Back to top]](#table-of-contents)
###Key features:
[[Back to top]](#table-of-contents)
*	Make adjustments in code to meet architectual description of our system.
	1.	Are we doing proper MVC architecture.
		1.	Board or BoardModel.java (does not exist) of Game should be it's own model class.
			The model should be edited through Controller, which right now happens to be MultiplayerGameState.java.
			Futher, I think we should avoid drawing BoardModel when there's no change. When a change is made, i.e. a crate is destoryed, the model should fireBoardChange which calls a function in MultiplayerGameState or SinglePlayerState which draws the board again.
	2.	~~Peer-to-peer:~~
		1.	There is not really much to it. We are already doing this.
	3.	Server:
		1.	We have a lot of work to be done when we are able to run different rounds.
*	Intro game screen: Graphics and all it's glory.
*	Seperation between Multiplayer and Singleplayer
	1.	Singleplayer doesn't work.
	2.	AI for singleplayer is not implemented.
*	New round for all players. Server assigning colors to players
	1.	Detect end of game.
*	Stats in multiplayer and transistion between rounds.
	1. After detecting of a end of game. We should run a sequence of actions to restart everything to start a new clean round. Everything except player scores whould be wiped.
*	Powerups
	1.	Throw-implementation.
		Idea: If you try to place a bomb at a location which already contains bomb. Throw it if that player has picked up throw-powerup.
	2.	Powerup graphics.
		Currently, just a placeholder image is given. Make static image placeholder for the various powerup-images in PowerUpType.java so save memory.
*	Graphics:
	1.	~~~Enable magnitude range of bombs in UP, DOWN, LEFT, RIGHT directions after bomb impact.
		Image resources already available.~~~
	2. Remove duplicate bears at startup and bears not controlled by anyone.

###Non-key features:
[[Back to top]](#table-of-contents)
*	Tool-assisted-movements
	Hard to move in-game. Make it a little easier?
*	Bomb.java
	Images are loaded every time in each Bomb-objected created which is very resourceful.
	Try make a static variables with the animations for Bomb's lifespan?


## Implemented features
[[Back to top]](#table-of-contents)
*	Powerups:
	1.	Place additional bombs.
	2.	Increased player speed.


## Buggy features
[[Back to top]](#table-of-contents)
*	Powerups:
	1.	Kick: Does not work as intended.
	2.	Movements: Hard to navigate through game. Especially when having high movement speed.
*	Server:
	1.	Currently, server does not assign colors to players connecting. Player figures are hard-coded.
		Reason: Java sockets give CorruptedSocketException because of trying to send over object before

# Setting up ADT project
[[Back to top]](#table-of-contents)
```
File > New > Android Application Project
Application Name: BomberMan
Project Name:     BomberMan
Package Name:     bomberman.game

Finish project creation without editing anything standard.
Delete MainActivity.java in bomberman.game.

Install git bash. Go to BomberMan-folder and do commands written below.
```

## Initial git commands.
[[Back to top]](#table-of-contents)
```
git clone https://github.com/johnolos/yolo-bear.git

```

## Commit sequence
[[Back to top]](#table-of-contents)
```
git add --all "folder"
git add "path_to_file_and_filename"
git commit -m "Message for commit"
git push
```
## After being used first time
[[Back to top]](#table-of-contents)
```
git pull
git push
```

