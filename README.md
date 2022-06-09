# LaserArena

Desktop application of Thinkfun’s [Lazer Maze™](https://www.thinkfun.com/products/laser-maze/) board game. Game rules can be reached from [here.](https://www.thinkfun.com/wp-content/uploads/2013/09/Laser-1014-Instructions.pdf)

All images, levels, and other relevant game contents are copyrighted by ThinkFun, Inc. This is an open-source implementation of a game that I like to play.

**How to Play**

User can change the orientations of the tokens by LEFT CLICKING on it, if their orientation is not fixed. If there is a question mark in the middle of a token it represents that its orientation is not fixed and can be reorientated.

On the top panel there may be tokens that are waiting to be added to the map. User can add them by LEFT CLICKING an available waiting token from the top panel and then LEFT CLICKING an available cell on the map. Waiting tokens can be placed on an empty cell or it can replace another waiting token placed on map. User can remove placed waiting token from a map by MIDDLE CLICKING. Pre-placed tokens that displayed at the beginning of the level or tokens placed by Solution or Hint Buttons cannot be moved or removed. 

Required number of targets to be hit can be seen from upper-right corner. Level is finished when it displays 0 and turns green. If there are waiting tokens to be placed or all tokens are not passed (except White Obstacle) it will not turn green although all targets are hit. Also, if there are mandatory targets denoted by red and white target sign, they must be targeted in order to finish the level.


![laserArena](https://user-images.githubusercontent.com/3480398/165636360-44fb5f43-8612-4a1e-a5db-84613472fe6f.gif)

<!--![image](https://user-images.githubusercontent.com/3480398/144624455-ce9595c3-11e5-4979-b326-bfeba76dbe4f.png)-->

BUTTONS and LABELS:

**|<:** Goes to first level

**<:** Goes to previous level if it exists

**Level X:** Indicates current level and user can RIGHT CLICK to skip to another level or LEFT CLICK to refresh the game into its initial state

**>:** Goes to next level if it exists and it is unlocked. Displays FIN if game is finished.

**>|:** Goes to last unlocked level

**Solution/Game:** Moves between the solution and the game. This button is enabled if the solution of the level is unlocked before.

**Hint:** Gives one hint by correctly placing a token or changing the orientation of it.




