package core;

import tokens.PurpleTarget;
import tokens.RedLaser;
import tokens.Token;
import tokens.WhiteObstacle;

import java.awt.*;
import java.util.ArrayList;

/**
 * GameMap of the game, where tokens are placed and laser(s) pass.
 */

//TODO: Static methods needs to be resolved, either they  will be deleted or GameMap object in Arenaframe will be deleted
// this class may be Singleton

public class GameMap {
    public static final String IMG_FOLDER = "img/", IMG_EXTENSION = ".png";
    public static final int MAX_BEAMS = 10;
    private static final int MAX_LOOP = 1000;
    private static int width, height;

    public void setNoOfTargets(int noOfTargets) {
        this.noOfTargets = noOfTargets;
    }

    public static int getNoOfTargets() {
        return noOfTargets;
    }

    private static int noOfTargets = 1;
    private static Token[][] tokens;
    private static ArrayList<Token> waitingTokens; // Tokens that are not in the initial map, but waiting to be added by the user

    public static ArrayList<Boolean> getIsWaitingTokenActive() {
        return isWaitingTokenActive;
    }

    private static ArrayList<Boolean> isWaitingTokenActive; //true if not added to the map, false if it is on the map
    private static ArrayList<Token> randomTargetsHit, mandatoryTargetsHit;
    private static ArrayList<LaserBeam> beams;

    public GameMap(int width, int height) {
        waitingTokens = new ArrayList<>();
        randomTargetsHit = new ArrayList<>();
        mandatoryTargetsHit = new ArrayList<>();
        isWaitingTokenActive = new ArrayList<>();
        beams = new ArrayList<>(MAX_BEAMS);
        this.setWidth(width);
        this.setHeight(height);
        tokens = new Token[width][height];
    }


    public static boolean isAllTokensPassed() {
        //TODO: YellowBridge pass also includes Stuck laser Beam, should we fix that?
        //WhiteObstacle does not need to be passed
        for (int i = 0; i < tokens.length; i++) {
            for (int j = 0; j < tokens[i].length; j++) {
                if (tokens[i][j] != null && !(tokens[i][j] instanceof RedLaser) &&
                        !(tokens[i][j] instanceof WhiteObstacle) && !tokens[i][j].isPassed()) {
                    //System.out.println(i + "," + j + " not passed");
                    return false;
                }
            }
        }
        return true;
    }

    public static ArrayList<Token> getWaitingTokens() {
        return waitingTokens;
    }

    public static Token getTokenLocatedInXY(int x, int y) {
        return tokens[x][y];
    }

    private static Token getTokenLocatedInPoint(Point p) {
        return tokens[(int) p.getX()][(int) p.getY()];
    }

    public static void removeTokenLocatedInXY(int x, int y) {
        tokens[x][y] = null;
    }


    public static boolean addToken(Token token, Point point) {
        if (point.getY() < height && point.getY() >= 0
                && point.getX() < width && point.getX() >= 0) {
            tokens[(int) point.getX()][(int) point.getY()] = token;
			/*if(token instanceof RedLaser)
				addLaserBeam(new LaserBeam(point, ((RedLaser) token).getGeneratedLaserDirection()));*/
            return true;
        } else
            return false;
    }

    public static void addWaitingToken(Token token) {
        int loc = waitingTokens.indexOf(token);
        if (loc == -1) //new Token
        {
            waitingTokens.add(token);
            isWaitingTokenActive.add(true);
        } else //current Token that needs to be reactivated
        {
            isWaitingTokenActive.set(loc, true);
        }
    }

    public static boolean isTokenActive(Token t) {
        int index = waitingTokens.indexOf(t);
        if (index >= 0) {
            return isWaitingTokenActive.get(index);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static void removeWaitingToken(Token token) {
        int loc = waitingTokens.indexOf(token);
        if (loc == -1) {
            throw new IllegalArgumentException();
        } else {
            isWaitingTokenActive.set(loc, false);
        }
    }

    public static Token getNthActiveToken(int n) {
        return waitingTokens.get(getLocationOfNthActiveToken(n));
    }

    public static int getActiveTokensCount() {
        int activeTokensCount = 0;

        for (boolean bool : isWaitingTokenActive) {
            if (bool)
                activeTokensCount++;
        }

        return activeTokensCount;
    }

    public static int getLocationOfNthActiveToken(int n) {
        int index = -1;

        n = n % getActiveTokensCount();

        for (int i = 0; i < isWaitingTokenActive.size(); i++) {
            if (isWaitingTokenActive.get(i)) {
                index++;
                if (index == n)
                    return i; //location of nth active token
            }

        }
        return -1;

    }

    public static ArrayList<Token> getActiveWaitingTokens()
    {
        ArrayList<Token> actives = new ArrayList<>();
        for(int i = 0; i < waitingTokens.size(); i++){
            if (isWaitingTokenActive.get(i)) {
                actives.add(waitingTokens.get(i));
            }
        }
        return actives;
    }

    public void moveBeamsUntilNotMovable() {
        for (int i = 0; i < tokens.length; i++) {
            for (int j = 0; j < tokens[i].length; j++) {
                if (tokens[i][j] != null)
                    tokens[i][j].setPassed(false);
            }
        }
        beams = new ArrayList<>(4);
        for (int i = 0; i < tokens.length; i++) {
            for (int j = 0; j < tokens[i].length; j++) {
                if (tokens[i][j] instanceof RedLaser) {
                    addLaserBeam(new LaserBeam(new Point(i, j), ((RedLaser) tokens[i][j]).getGeneratedLaserDirection()));
                }
            }
        }
        LaserBeam beam;
        int i;
        for (int k = 0; k < beams.size(); k++) {
            beam = beams.get(k);
            i = 0;
            while (beam.getDirection().isMovable() && i < MAX_LOOP) {
                beam.move();
                if (isOutOfBounds(beam.getLocation())) {
                    beam.setDirection(Direction.OUT_OF_BOUNDS);
                } else {
                    Token t = getTokenLocatedInPoint(beam.getLocation());
                    if (t != null) {
                        //TODO null exception code below t.getSide(beam.getDirection().getOppositeDirection()).action(beam);
                        beam.setDirection(t.getSide(beam.getDirection().getOppositeDirection()).action(beam));
                        t.setPassed(true);
                    }
                }
                i++;
                beam.updatePath();
            }
            //System.out.println("final beam(s): " + beam);
        }
        //System.out.println();
        //TODO: check if the LaserBeam loops indefinitely, not sure if it is possible???
    }

    private static int getWantedMandatoryTargets() {
        int noOfWantedMandatoryTargets = 0;
        for (Token[] token_arr : tokens) {
            for (Token t : token_arr) {
                if (t instanceof PurpleTarget && ((PurpleTarget) t).isMandatoryTarget()) {
                    noOfWantedMandatoryTargets++;
                }
            }
        }
        return noOfWantedMandatoryTargets;
    }

    //TODO:This method is called 4-5 times when a new token is added to the map, fix it if needed
    public static int getNoOfRandomTargetsHit() {
        randomTargetsHit = new ArrayList<>();
        return getTargetsHit(Direction.TARGET_HIT, randomTargetsHit);
    }

    public static int getNoOfMandatoryTargetsHit() {
        mandatoryTargetsHit = new ArrayList<>();
        return getTargetsHit(Direction.MANDATORY_TARGET_HIT, mandatoryTargetsHit);
    }

    public static int getTargetsHit(Direction direction, ArrayList list)
    {
        Token hitToken;
        for (LaserBeam beam : beams) {
            if (beam.getDirection() ==  direction){
                hitToken = getTokenLocatedInXY((int)beam.getLocation().getX(), (int)beam.getLocation().getY());
                if(!list.contains(hitToken))
                    list.add(hitToken);
            }
        }
        return list.size();
    }

    public static void setAllWaitingTokensActiveness(boolean isActive) {
        for (int i = 0; i < isWaitingTokenActive.size(); i++)
            isWaitingTokenActive.set(i, isActive);
    }

    //checksIfAllWantedTargetsHitAndAllTokensArePassed
    public static boolean isLevelFinished() {
        //TODO: When mouse clicked this method is called twice, it should be once
        if (getActiveTokensCount() > 0)
            return false;
        if (!isAllTokensPassed())
            return false;
        //TODO: not tested
        int noOfMandatoryTargetsHit = getNoOfMandatoryTargetsHit();
        int noOfRandomTargetsHit = getNoOfRandomTargetsHit();

        return (noOfMandatoryTargetsHit == getWantedMandatoryTargets() &&
                (noOfMandatoryTargetsHit + noOfRandomTargetsHit) == noOfTargets);
    }

    private boolean isOutOfBounds(Point p) {
        return (p.getX() >= width || p.getY() >= height
                || p.getX() < 0 || p.getY() < 0);
    }

    public int getWidth() {
        return width;
    }

    private void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    private void setHeight(int height) {
        this.height = height;
    }

    public static ArrayList<LaserBeam> getBeams() {
        return beams;
    }

    public static void setBeams(ArrayList<LaserBeam> beams) {
        GameMap.beams = beams;
    }

    public static Token[][] getTokens() {
        return tokens;
    }

    public static void setTokens(Token[][] tokens) {
        GameMap.tokens = tokens;
    }

    public static void addLaserBeam(LaserBeam l) {
        beams.add(l);
    }

    public void print() {
        String str = "";
        Token t;
        for (int i = 0; i < tokens.length; i++) {
            for (int j = 0; j < tokens[i].length; j++) {
                t = tokens[i][j];
                if (t != null)
                    System.out.println(t.toIconString() + " " + i + "," + j);
            }

        }
    }
}
