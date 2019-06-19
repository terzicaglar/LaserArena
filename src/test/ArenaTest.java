/**
 * Test class for LaserArena Game
 */
package test;

import core.Direction;
import core.GameMap;
import core.LaserBeam;
import core.Orientation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tokens.*;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class ArenaTest {
    LaserBeam beam;
    GameMap map;
    @BeforeEach
    public void initEach()
    {
        beam = new LaserBeam(new Point(2,1), Direction.EAST);
        map = new GameMap(5,5);
    }

    @Test
    public void BackSlashBlueMirrorCreationTest(){
        Token blueMirror = new BlueMirror(Orientation.O1); //Backslash
        assertTrue(blueMirror.getSide(Direction.WEST).action(beam) == Direction.SOUTH);

    }

    @Test
    public void SlashBlueMirrorCreationTest(){
        Token blueMirror = new BlueMirror(Orientation.O2); //SLASH
        assertTrue(blueMirror.getSide(Direction.WEST).action(beam) == Direction.NORTH);
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
    public void RedLaserCreationTest(){
        Token generator1 = new RedLaser(Orientation.O0);
        Token generator2 = new RedLaser(Orientation.O1);

        assertTrue(generator1.getSide(Direction.WEST).action(beam) == Direction.NONE);
        assertTrue(generator2.getSide(Direction.WEST).action(beam) == Direction.NONE);
    }

    @Test
    public void CreateLaserAndHitAToken(){
        LaserBeam beam = new LaserBeam(new Point(1,1), Direction.EAST);

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
