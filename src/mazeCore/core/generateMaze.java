package mazeCore.core;

/**
 * 生成地图
 */
public class generateMaze {
    private map innerMaze;

    public generateMaze(int inputMapSizeRow, int inputMapSizeColumn) {
        innerMaze = new map(inputMapSizeRow, inputMapSizeColumn);
    }

    public map getMaze() {
        return new map();
    }
}
