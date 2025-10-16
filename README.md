# MinecraftPlugin
In this Project im gonna implement different Plugins, all combined to one.
At first im focused to implement the following functions:
- Economy System
- Traiding System
- Operator tools
- Shop System (in game market place)

Im gonna implement a function, so that you can enable the Plugins that you need.

For feedback or another idea for a Plugin to implement just write a message to the following e-mail: Mq187ark@gmail.com

Installation:

If you use every Plugin install the Plugin.jar file and move it to your /plugin Directory on your Minecraft Server.
Pleas keep in mind that this Plugin works on Java 21 so not every Minecraft Version works on it, also this Plugin was made for Spigiot. 
So do not try to use it on Paper/Forge/.. Servers, it wont work.

In cas you want to use only some Plugins from the implemented ones you need to follow these steps:
1. Download the Source Code and open it on the IDEA of your choice.
2. Go to the following Path : /src/Java/de/MinecraftPlugin there you find many other Directorys
3. Select the Directorys with the Plugins you do not need and delete them
4. Open the Main.java (/src/Java/de/MinecraftPlugin/Main.java) file and delete all the red underlined getCommand lines.
5. After this there shouldnt be any problems
6. On Intellij you can go to the right Menu and press on the Maven Symbol, then go to /maven/lifecycle/package.
7. This should build a new jar file on your PC, you can finde it at your Project .../IdeaProjects/MinecraftPlugin/target/Plugin.jar
8. Then you just need to move this generated file to your plugins file on your Minecraft Server.

To get all the Commands you can just type /<command> help in the chat it should send you the usage of the commands and which arguments are neccessary
