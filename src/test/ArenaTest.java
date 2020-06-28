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

class ArenaTest {
    private LaserBeam beam;
    private GameMap map;

    @BeforeEach
    void initEach() {
        beam = new LaserBeam(new Point(2, 1), Direction.EAST);
        map = GameMap.getInstance();
        map.initiateMap(5,5);
    }

    @Test
    void BackSlashBlueMirrorCreationTest() {
        Token blueMirror = new BlueMirror(Orientation.BACKSLASH_MIRROR); //Backslash
        assertTrue(blueMirror.getSide(Direction.WEST).action(beam) == Direction.SOUTH);

    }

    @Test
    void SlashBlueMirrorCreationTest() {
        Token blueMirror = new BlueMirror(Orientation.SLASH_MIRROR); //SLASH
        assertTrue(blueMirror.getSide(Direction.WEST).action(beam) == Direction.NORTH);
    }

    @Test
    void YellowBridgeCreationTest() {
        Token yellowBridge = new YellowBridge(Orientation.VERTICAL_BRIDGE); //"|" Bridge

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
    void RedLaserCreationTest() {
        Token generator1 = new RedLaser(Orientation.GENERATOR_ON_WEST);
        Token generator2 = new RedLaser(Orientation.GENERATOR_ON_NORTH);

        assertTrue(generator1.getSide(Direction.WEST).action(beam) == Direction.STUCK);
        assertTrue(generator2.getSide(Direction.WEST).action(beam) == Direction.STUCK);
    }

    @Test
    void CreateLaserAndHitAToken() {
        LaserBeam beam = new LaserBeam(new Point(1, 1), Direction.EAST);

        beam.move();
        assertTrue(map.getTokenLocatedInXY(2, 1) == null);
        assertTrue(beam.getLocation().getX() == 2 && beam.getLocation().getY() == 1);
        beam.move();
        assertTrue(map.addToken(new PurpleTarget(Orientation.TARGET_ON_WEST), new Point(3, 1))); //"/" Mirror

        assertFalse(map.getTokenLocatedInXY(3, 1) == null);
        Token t = map.getTokenLocatedInXY(3, 1);
        //assertTrue(t instanceof YellowBridge);
        beam.setDirection(t.getSide(beam.getDirection().getOppositeDirection()).action(beam));
        //beam.move();
        //System.out.println(beam);
        //System.out.println(beam.getPathHistory());
        //System.out.println(Direction.SOUTH.ordinal());
    }

    @Test
    void CheckIfTokenTypesAreEqual() {
        RedLaser r1 = new RedLaser(Orientation.GENERATOR_ON_EAST);
        RedLaser r2 = new RedLaser(Orientation.GENERATOR_ON_SOUTH);
        assertTrue(r1.isTokenTypeSameWith(r2));
        GreenMirror g1 = new GreenMirror();
        assertFalse(r1.isTokenTypeSameWith(g1));
        PurpleTarget p1 = new PurpleTarget(Orientation.TARGET_ON_SOUTH, false, false,true);
        PurpleTarget p2 = new PurpleTarget(Orientation.TARGET_ON_WEST, false, true,false);
        assertTrue(p1.isTokenTypeSameWith(p2));
        assertFalse(p1.isTokenTypeSameWith(g1));
        PurpleTarget p3 = new PurpleTarget(Orientation.TARGET_ON_EAST, true, false,false);
        PurpleTarget p4 = new PurpleTarget(Orientation.TARGET_ON_WEST, true, true,false);
        assertTrue(p3.isTokenTypeSameWith(p4));
        assertFalse(p3.isTokenTypeSameWith(p2));
        GreenMirror g2 = new GreenMirror(Orientation.BACKSLASH_MIRROR);
        GreenMirror g3 = new GreenMirror(Orientation.SLASH_MIRROR);
        assertTrue(g1.isTokenTypeSameWith(g2));
        assertTrue(g1.isTokenTypeSameWith(g3));
    }


    void GameMapWithGreenAndPurpleTokens() {
        Token greenMirror = new GreenMirror(Orientation.BACKSLASH_MIRROR);
        Token target1 = new PurpleTarget(Orientation.TARGET_ON_WEST);
        Token target2 = new PurpleTarget(Orientation.TARGET_ON_NORTH);
        //Token bridge1 = new YellowBridge(Orientation.HORIZONTAL_BRIDGE);
        Token greenMirror2 = new GreenMirror(Orientation.SLASH_MIRROR);

        map.addToken(greenMirror2, new Point(2, 1));
        map.addToken(greenMirror, new Point(3, 1));
        map.addToken(target1, new Point(4, 1));
        map.addToken(target2, new Point(3, 3));

    }

    @Test
    void GameMapWitAllTokens() {
        map.addToken(new RedLaser(Orientation.GENERATOR_ON_EAST), new Point(0, 3));
        map.addToken(new PurpleTarget(Orientation.TARGET_ON_EAST), new Point(0, 0));
        map.addToken(new PurpleTarget(Orientation.TARGET_ON_SOUTH), new Point(0, 2));
        map.addToken(new PurpleTarget(Orientation.TARGET_ON_SOUTH), new Point(3, 4));
        map.addToken(new PurpleTarget(Orientation.TARGET_ON_WEST), new Point(4, 3));
        map.addToken(new BlueMirror(Orientation.SLASH_MIRROR), new Point(4, 4));
        map.addToken(new BlueMirror(Orientation.BACKSLASH_MIRROR), new Point(2, 2));
        map.addToken(new GreenMirror(Orientation.BACKSLASH_MIRROR), new Point(3, 3));
        map.addToken(new GreenMirror(Orientation.SLASH_MIRROR), new Point(2, 3));
        map.addToken(new YellowBridge(Orientation.HORIZONTAL_BRIDGE), new Point(0, 1));
        map.addToken(new YellowBridge(Orientation.VERTICAL_BRIDGE), new Point(1, 3));
        map.addToken(new WhiteObstacle(), new Point(1, 2));
        map.addToken(new WhiteObstacle(), new Point(4, 0));
        map.moveBeamsUntilNotMovable();

    }

    @Test
    void checkMoveBeamsUntilNotMovable() {
       /* GameMapWithGreenAndPurpleTokens();
        map.addToken(new RedLaser(Orientation.GENERATOR_ON_EAST),new Point(1,1));
        map.moveBeamsUntilNotMovable();*/
    }

}
