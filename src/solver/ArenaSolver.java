package solver;

import core.Game;
import core.GameMap;
import core.Orientation;
import tokens.*;

import java.util.*;

public class ArenaSolver {
    public static void main(String[] args) {
        Game game = Game.getInstance();
    }
    public static boolean solve(Token[][] tokens, ArrayList<Token> waitingTokens) {
        /*
            Solving consists of two steps, where each of them uses permutation to try
            each possible token placement and token orientations(rotations).
            First, we try to find a possible token placement by inserting all waiting
            tokens. Once we find a possible placement, we try each possible orientation
            for each token, e.g., there are four possible orientations for a PurpleTarget,
            and two for BlueMirror.
         */
        Long start = System.currentTimeMillis();
        Map<Integer, Integer[]> emptyCells = new HashMap<>();
        List<Token> rotatedTokens = new ArrayList<>();
        List<Integer> orientationsCounts = new ArrayList<>();

        for (int i = 0; i < tokens.length; i++) {
            for (int j = 0; j < tokens[i].length; j++) {
                if (tokens[i][j] != null) {
                    if (getPossibleOrientationCount(tokens[i][j]) > 1) {
                        rotatedTokens.add(tokens[i][j]);
                        orientationsCounts.add(getPossibleOrientationCount(tokens[i][j]));
                    }
                }
            }
        }
        for (Token t : waitingTokens) {
            if (t != null) {
                rotatedTokens.add(t);
                orientationsCounts.add(getPossibleOrientationCount(t));
            }
        }
        int orientationPositions[] = new int[rotatedTokens.size()];

        int emptyCellsCount = 0;
        for (int i = 0; i < tokens.length; i++) {
            for (int j = 0; j < tokens[i].length; j++) {
                if (tokens[i][j] == null) {
                    emptyCells.put(emptyCellsCount, new Integer[]{i, j});
                    emptyCellsCount++;
                }
            }
        }
        //possible map positions for each waiting list token
        int positions[] = new int[waitingTokens.size()];
        //starting from minimum number with unique values
        for (int i = 0; i < positions.length; i++) {
            positions[i] = i;
        }
        do {
            if (isAllElementsUnique(positions)) {
                Token[][] testTokens = new Token[tokens.length][tokens[0].length];
                for (int i = 0; i < tokens.length; i++) {
                    for (int j = 0; j < tokens[i].length; j++) {
                        testTokens[i][j] = tokens[i][j];
                    }
                }

                for (int i = 0; i < positions.length; i++) {
                    int col = emptyCells.get(positions[i])[0];
                    int row = emptyCells.get(positions[i])[1];
                    testTokens[col][row] = waitingTokens.get(i);
                }
                //check if solution is correct for all possible rotations for each token
                int innerCount = 0;
                do {
                    GameMap map = GameMap.getInstance();
                    map.setTokens(testTokens);
                    map.removeAllWaitingTokens();
                    map.moveBeamsUntilNotMovable();
                    //check if solution is correct
                    if (map.isLevelFinished()) {
                        Long end = System.currentTimeMillis();
                        System.out.println("Solving time: " + (end - start) / 1000);
                        return true;
                    }
                } while(changeOrientation(orientationPositions, orientationsCounts,
                        rotatedTokens));
            }
        } while (increasePositionIfPossible(positions, emptyCellsCount));
        System.out.println(System.currentTimeMillis() - start);
        return false;
    }

    private static boolean changeOrientation(int orientationPositions[], List<Integer> orientationCounts,
                                             List<Token> rotatedTokens) {
        for (int i = orientationPositions.length - 1; i >= 0; i--) {
            orientationPositions[i]++;
            rotatedTokens.get(i).nextOrientation();
            if (orientationPositions[i] == orientationCounts.get(i)) {
                orientationPositions[i] = 0;
            } else {
                return true;
            }
        }
        //orientationPositions array becomes all zero
        return false;
    }

    //like pass by reference, positions array is updated
    //returns true if increase is possible, returns false when all possible combinations are tried
    private static boolean increasePositionIfPossible(int positions[], int emptyCellsCount) {
        for (int i = positions.length - 1; i >= 0; i--) {
            positions[i]++;
            if (positions[i] == emptyCellsCount) {
                positions[i] = 0;
            } else {
                return true;
            }
        }
        //positions array becomes all zero
        return false;
    }

    private static boolean isAllElementsUnique(int positions[]) {
        Set<Integer> elements = new HashSet<>();
        for (int p : positions) {
            if (elements.contains(p)) {
                return false;
            } else {
                elements.add(p);
            }
        }
        return true;
    }

    private static int getPossibleOrientationCount(Token t) {
        if (t.isOrientationFixed()) {
            return 1;
        } else if (t instanceof BlueMirror || t instanceof GreenMirror || t instanceof YellowBridge) {
            return 2;
        } else if (t instanceof PurpleTarget || t instanceof RedLaser) {
            return 4;
        } else if (t instanceof WhiteObstacle) {
            return 1;
        }
        throw new IllegalArgumentException("Token type is not supported");
    }
}
