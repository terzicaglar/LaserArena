# LaserArena

Java GUI for puzzle game [Laser Maze](https://www.thinkfun.com/products/laser-maze/). Game rules can be reached from [here.](https://www.thinkfun.com/wp-content/uploads/2013/09/Laser-1014-Instructions.pdf)

**How to Play**

User can change the orientations of the tokens by LEFT CLICKING on it, if their orientation is not fixed. If there is a question mark in the middle of a token it represents that its orientation is not fixed and can be reorientated.

On the top panel there may be tokens that are waiting to be added to the map. User can add them by RIGHT CLICKING an available cell on the map. User can move between waiting tokens by by continuing to LEFT CLICK. Waiting tokens can be placed on an empty cell or it can replace another waiting token placed on map. User can remove placed waiting token from a map by MIDDLE CLICKING. Pre-placed tokens that displayed at the beginning of the level cannot be moved or removed. 

Required number of targets to be hit can be seen from upper-right corner. Level is finished when it displays 0 and turns green. If there are waiting tokens to be placed or all tokens are not passed (except White Obstacle) it will not turn green although all targets are hit. Also, if there are mandatory targets denoted by red and white target sign, they must be targeted in order to finish the level.

BUTTONS and LABELS:

**|<:** Goes to first level

**<:** Goes to previous level if it exists

**Level X:** Indicates current level and user can RIGHT CLICK to skip to another level or LEFT CLICK to refresh the game into its initial state

**>:** Goes to next level if it exists and it is unlocked. Displays FIN if game is finished.

**>|:** Goes to last unlocked level

**Solution/Game:** Moves between the solution and the game. This button is enabled if the solution of the level is unlocked before.

**Hint:** Gives one hint by correctly placing a token or changing the orientation of it.




