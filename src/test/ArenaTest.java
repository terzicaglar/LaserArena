/**
 * Test class for LaserArena Game
 */
package test;

import core.Direction;
import core.MirrorDirection;
import org.junit.jupiter.api.Test;
import sides.SlashReflectorSide;
import tokens.BlueMirror;
import tokens.Token;
import static org.junit.jupiter.api.Assertions.*;

public class ArenaTest {

    @Test
    public void BackSlashBlueMirrorCreationTest(){
        Token blueMirror = new BlueMirror(MirrorDirection.BACK_SLASH);
        //assertTrue(blueMirror.getSides()[0] instanceof SlashReflectorSide);
        assertTrue(blueMirror.getSides()[0].action(Direction.SOUTH) == Direction.WEST);
        assertTrue(blueMirror.getSides()[0].action(Direction.WEST) == Direction.SOUTH);
        assertTrue(blueMirror.getSides()[0].action(Direction.NORTH) == Direction.EAST);
        assertTrue(blueMirror.getSides()[0].action(Direction.EAST) == Direction.NORTH);

    }

    @Test
    public void SlashBlueMirrorCreationTest(){
        Token blueMirror = new BlueMirror(MirrorDirection.SLASH);
        //assertTrue(blueMirror.getSides()[0] instanceof SlashReflectorSide);
        assertTrue(blueMirror.getSides()[0].action(Direction.SOUTH) == Direction.EAST);
        assertTrue(blueMirror.getSides()[0].action(Direction.EAST) == Direction.SOUTH);
        assertTrue(blueMirror.getSides()[0].action(Direction.NORTH) == Direction.WEST);
        assertTrue(blueMirror.getSides()[0].action(Direction.WEST) == Direction.NORTH);

    }

}
