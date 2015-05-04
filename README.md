# SoniPlane 2.0+
SoniPlane is a Sega Mega Drive/Genesis plane mappings editor, primarily targeted at Sonic games on MD, but will work on essentially any game with the regular mappings format. While SoniPlane is primarily targeted for mappings editor, the core will be made to support practically any task a GUI-based application should be able to handle and be infinitely expendable. This is open source and community-oriented project, so any contributions are welcome!

## Motivation
There was no really useful mappings editor out there, so being able to edit them as well as design would of been quite the difficult task with unreliable tools designed poorly or slowness of plain hex editing. Therefore I thought to create the ultimate plane mappings editor!

## Application design
SoniPlane is designed to be very extendable and modifyable, yet safe for user. Because of this, SoniPlane API blocks access to most features that could potentially be harmful to one's computer or programs functionality. But it will grant you access to features that are of no harm and give freedom to the developers.
SoniPlane has few preset components that make it function:
* **[Launcher tool]** - Used to launch SoniPlane and update componenets and make sure the system works correctly. Also error reporting when needed
* **[lib/core.jar]** - Core of the program. All the functionality and API are loaded from here by the Launcher tool, and executed. This is the "guts" of the program and without it you can't run anything
* **[api.jar]** - API to be used when building appications that target SoniPlane. This is mirror of `soni.plane.api` contents in [lib/core.jar]. This in itself does nothing but provides easier usage of the API without all other classes bother you (afterall, using functions outside the API will not work)
* **[lib/gdx.jar], [lib/gdx-backend-lwjgl.jar], [lib/gdx-freetype.jar]** - These are libraries which SoniPlane utilises to function. SoniPlane is based on [LibGDX], ([OpenGL]-based library which provides helpful classes and code to run applications efficiently and neatly) which these jars contain.

SoniPlane works based on modules which can either be Window's or misc libraries. Window's are all the components you see on screen, or objects that call said components (from other libraries typically). A window practically is a rectangular area with certain specifications, which controller object can draw items over. They of course can have their own logic an functionality as much as wished and cpable with API and default functions. Here is how Window's are arranged:
_LibGDX internal draw calls -> core draw initializer -> draw each Window -> clip to Window area (prevents drawing outside of it) -> call draw() method of target Window -> Window does whatever -> return and remove clipping -> repeat for all loaded Window's -> finalize drawing and exit draw methods application-side_

Window's are on separate jars supplied by mod creators or original SoniPlane configuration (downloaded from SoniPlane servers) and are loaded runtime with their own specific systems. Libraries other than Window's are loaded within code of specific Window or library for its uses. These can be useful for functions not supplied with SoniPlane or new features or such.

## API reference
API is still in the works, this is going to be created eventually

## Installation
[Click here!](http://discocentral.digibase.ca/SoniPlane/installation-Git.html)

## Contribution
You can talk to the development team from our IRC channel; [irc.badnik.net](http://irc.badnik.net/) **#SoniPlane**

[lib/core.jar]: https://github.com/TheRetroSnake/.SoniPlane/blob/master/lib/core.jar
[Launcher tool]: https://github.com/TheRetroSnake/.SoniPlane/blob/master/launch.jar
[api.jar]: https://github.com/TheRetroSnake/.SoniPlane/blob/master/api.jar
[lib/gdx.jar]: https://github.com/TheRetroSnake/.SoniPlane/blob/master/lib/gdx.jar
[lib/gdx-backend-lwjgl.jar]: https://github.com/TheRetroSnake/.SoniPlane/blob/master/lib/gdx-backend-lwjgl.jar
[lib/gdx-freetype.jar]: https://github.com/TheRetroSnake/.SoniPlane/blob/master/lib/gdx-freetype.jar
[LibGDX]: http://libgdx.badlogicgames.com/
[OpenGL]: https://www.opengl.org/
