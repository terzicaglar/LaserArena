/**
 * Test class for LaserArena Game
 */
package test;

import core.Direction;
import core.GameMap;
import core.LaserBeam;
import core.Orientation;
import org.junit.jupiter.api.Test;
import tokens.BlueMirror;
import tokens.PurpleTarget;
import tokens.Token;
import tokens.YellowBridge;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class ArenaTest {



    @Test
    public void BackSlashBlueMirrorCreationTest(){
        Token blueMirror = new BlueMirror(Orientation.O1); //Backslash

        //this loop checks all conditions regardless of the side and incoming laser beam direction, i.e., it also checks an incoming laser beam from EAST when checking
        //side located at SOUTH, although it is impossilble, since only laser beam from NORTH can hit side located at SOUTH

        //TODO code below will be modified for LaserBeam parameter given to action method
        /*for (Direction dir: Direction.values()){
            if(dir == Direction.WEST || dir == Direction.EAST || dir == Direction.NORTH || dir == Direction.SOUTH)
            {
                assertTrue(blueMirror.getSide(dir).action(Direction.SOUTH) == Direction.EAST);
                assertTrue(blueMirror.getSide(dir).action(Direction.EAST) == Direction.SOUTH);
                assertTrue(blueMirror.getSide(dir).action(Direction.NORTH) == Direction.WEST);
                assertTrue(blueMirror.getSide(dir).action(Direction.WEST) == Direction.NORTH);
            }
        }*/

        //assertThrows(IllegalArgumentException.class, () -> blueMirror.getSides()[0].action(Direction.DENEME));

    }

    @Test
    public void SlashBlueMirrorCreationTest(){
        Token blueMirror = new BlueMirror(Orientation.O2); //SLASH

        //this loop checks all conditions regardless of the side and incoming laser beam direction, i.e., it also checks an incoming laser beam from EAST when checking
            //side located at SOUTH, although it is impossilble, since only laser beam from NORTH can hit side located at SOUTH

        //TODO code below will be modified for LaserBeam parameter given to action method
        /*for (Direction dir: Direction.values()){
            if(dir == Direction.WEST || dir == Direction.EAST || dir == Direction.NORTH || dir == Direction.SOUTH) {
                assertTrue(blueMirror.getSide(dir).action(Direction.SOUTH) == Direction.WEST);
                assertTrue(blueMirror.getSide(dir).action(Direction.WEST) == Direction.SOUTH);
                assertTrue(blueMirror.getSide(dir).action(Direction.NORTH) == Direction.EAST);
                assertTrue(blueMirror.getSide(dir).action(Direction.EAST) == Direction.NORTH);
            }
        }*/

        //assertThrows(IllegalArgumentException.class, () -> blueMirror.getSides()[0].action(Direction.DENEME));
    }

    @Test
    public void YellowBridgeCreationTest(){
        Token yellowBridge = new YellowBridge(Orientation.O0); //"|" Bridge

        //TODO code below will be modified for LaserBeam parameter given to action method
        /*assertTrue(yellowBridge.getSide(Direction.NORTH).action(Direction.SOUTH) == Direction.NONE); //stucks
        assertTrue(yellowBridge.getSide(Direction.SOUTH).action(Direction.NORTH) == Direction.NONE); //stucks
        assertTrue(yellowBridge.getSide(Direction.EAST).action(Direction.WEST) == Direction.WEST); //passes directly
        assertTrue(yellowBridge.getSide(Direction.WEST).action(Direction.EAST) == Direction.EAST); //passes directly

        Token yellowBridge2 = new YellowBridge(Orientation.O3); //"--" Bridge

        assertTrue(yellowBridge2.getSide(Direction.NORTH).action(Direction.SOUTH) == Direction.SOUTH); //passes directly
        assertTrue(yellowBridge2.getSide(Direction.SOUTH).action(Direction.NORTH) == Direction.NORTH); //passes directly
        assertTrue(yellowBridge2.getSide(Direction.EAST).action(Direction.WEST) == Direction.NONE); //stucks
        assertTrue(yellowBridge2.getSide(Direction.WEST).action(Direction.EAST) == Direction.NONE); //stucks
        */

    }

    @Test
    public void CreateLaserAndHitAToken(){
        LaserBeam beam = new LaserBeam(new Point(1,1), Direction.EAST);
        GameMap map = new GameMap(5,5);
        beam.move();
        assertTrue(GameMap.getTokenLocatedInXY(2,1) == null);
        assertTrue(beam.getLocation().getX() == 2 && beam.getLocation().getY() == 1);
        beam.move();
        assertTrue( map.addToken(new PurpleTarget(Orientation.O0),new Point(3,1))); //"/" Mirror

        assertFalse(GameMap.getTokenLocatedInXY(3,1) == null);
        Token t = GameMap.getTokenLocatedInXY(3,1);
        //assertTrue(t instanceof YellowBridge);
        beam.setDirection( t.getSide(beam.getDirection().getOppositeDirection()).action(beam));
        beam.move();
        System.out.println(beam);
        System.out.println(beam.getPathHistory());
        //System.out.println(Direction.SOUTH.ordinal());

    }

}
