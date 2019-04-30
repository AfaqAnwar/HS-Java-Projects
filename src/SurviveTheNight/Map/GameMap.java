package SurviveTheNight.Map;

/**
 * GameMap class.
 * @Author Afaq Anwar
 * @Verison 11/07/2018
 */
public class GameMap {
    // The map will be a 2D Array.
    Cell[][] gameMap;

    /**
     * First constructor for each new instance of a GameMap.
     * This is used in a scenario where we would like to create our own custom map.
     * @param gameMap 2D Cell Array, representing the map.
     */
    public GameMap(Cell[][] gameMap) {
        this.gameMap = gameMap;
    }

    /**
     * Second constructor for each new instance of a GameMap.
     * This is used to have the game automatically generate and fill the map.
     * @param height desired height of the gameMap (2D Array).
     * @param width desired width of the gameMap (2D Array).
     */
    public GameMap(int height, int width) {
        this.gameMap = new MapCell[height][width];
        fillMap();
    }

    public int getHeight() { return gameMap.length; }
    public int getWidth() {return  gameMap[0].length;}

    /**
     * Fills in the 2D map "gameMap" (Array) of the Cell object with MapCells.
     */
    public void fillMap() {
        for (int x = 0; x < gameMap.length; x++) {
            for (int y = 0; y <gameMap.length; y++) {
                MapCell cell = new MapCell(x,y);
                this.gameMap[x][y] = cell;
            }
        }
    }

    /**
     * Returns the Cell at a desired position.
     * @param x desired X position to search at. (Row)
     * @param y desired Y position to search at. (Column)
     * @return Cell in position x,y.
     */
    public Cell getCell(int x, int y) {
        return gameMap[x][y];
    }

    /**
     * Returns the Cell array at a desired position.
     * @param x desited X position to search at. (Row)
     * @return Cell[] containing all the cells in the array.
     */
    public Cell[] getCellRow (int x) { return gameMap[x];}

    /**
     * Fills in a given position on gameMap with the desired Cell.
     * @param x X position of the Cell in the gameMap.
     * @param y Y position of the Cell in the gameMap.
     * @param newCell
     */
    public void editMap(int x, int y, Cell newCell) {
        newCell.xPos = x;
        newCell.yPos =y;
        gameMap[x][y] = newCell;
    }

    @Override
    /**
     * Prints out the map in a readable format.
     */
    public String toString() {
        String returnString = "";
        for (Cell[] cellArr : gameMap) {
            for (Cell cell : cellArr) {
                returnString += cell + "  ";
            }
            returnString += "\n";
        }
        return returnString;
    }
}
