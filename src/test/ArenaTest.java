/**
 * Test class for LaserArena Game
 */
package test;

import core.Direction;
import core.Orientation;
import org.junit.jupiter.api.Test;
import tokens.BlueMirror;
import tokens.Token;
import static org.junit.jupiter.api.Assertions.*;

public class ArenaTest {



    @Test
    public void BackSlashBlueMirrorCreationTest(){
        Token blueMirror = new BlueMirror(Orientation.O1); //Backslash
        //assertTrue(blueMirror.getSides()[0] instanceof SlashReflectorSide);
        for (Direction dir: Direction.values()){
            if(dir != Direction.NONE)
            {
                assertTrue(blueMirror.getSide(dir).action(Direction.SOUTH) == Direction.WEST);
                assertTrue(blueMirror.getSide(dir).action(Direction.WEST) == Direction.SOUTH);
                assertTrue(blueMirror.getSide(dir).action(Direction.NORTH) == Direction.EAST);
                assertTrue(blueMirror.getSide(dir).action(Direction.EAST) == Direction.NORTH);
            }
        }

        //assertThrows(IllegalArgumentException.class, () -> blueMirror.getSides()[0].action(Direction.DENEME));

    }

    @Test
    public void SlashBlueMirrorCreationTest(){
        Token blueMirror = new BlueMirror(Orientation.O2); //SLASH
        //assertTrue(blueMirror.getSides()[0] instanceof SlashReflectorSide);
        for (Direction dir: Direction.values()){
            if(dir != Direction.NONE) {
                assertTrue(blueMirror.getSide(dir).action(Direction.SOUTH) == Direction.EAST);
                assertTrue(blueMirror.getSide(dir).action(Direction.EAST) == Direction.SOUTH);
                assertTrue(blueMirror.getSide(dir).action(Direction.NORTH) == Direction.WEST);
                assertTrue(blueMirror.getSide(dir).action(Direction.WEST) == Direction.NORTH);
            }
        }

        //assertThrows(IllegalArgumentException.class, () -> blueMirror.getSides()[0].action(Direction.DENEME));
    }

}
