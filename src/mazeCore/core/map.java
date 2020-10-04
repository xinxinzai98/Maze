package mazeCore.core;

/**
 * 地图默认设置
 */
class mapDefault {
    /**
     * 地图默认起点
     */
    public static final int mapStartRow = 2;
    public static final int mapStartColumn = 1;
}

/**
 * 迷宫地图类
 * 负责构建地图
 */
public class map {
    //地图参数
    private int mapSizeRow;
    private int mapSizeColumn;
    private int mapEndRow;
    private int mapEndColumn;
    private int mapStartRow;
    private int mapStartColumn;
    //地图数组
    private lattice[][] innerMap;

    //默认构造函数
    public map() {

    }

    //制定迷宫大小的构造函数
    public map(int inputMapSizeRow, int inputMapSizeColumn) {
        //地图参数初始化
        mapSizeRow = inputMapSizeRow + 2;
        mapSizeColumn = inputMapSizeColumn + 2;
        mapStartRow = mapDefault.mapStartRow;
        mapStartColumn = mapDefault.mapStartColumn;
        mapEndRow = mapSizeRow - 3;
        mapEndColumn = mapSizeColumn - 2;

        // 地图格点初始化
        innerMap = new lattice[mapSizeRow][mapSizeColumn];
        for (int i = 0; i < mapSizeRow; i++) {
            for (int j = 0; j < mapSizeColumn; j++) {
                innerMap[i][j] = new lattice(i, j);
            }
        }
        // 设置地图边界和内墙
        for (int i = 0; i < mapSizeRow; i++) {
            innerMap[i][0].setLatticeState(latticePriority.EDGE);
            innerMap[i][mapSizeColumn - 1].setLatticeState(latticePriority.EDGE);
            //innerMap[i][1].setLatticeState(latticePriority.WALL);
            //innerMap[i][mapSizeColumn - 2].setLatticeState(latticePriority.WALL);
        }
        for (int i = 1; i < mapSizeColumn - 1; i++) {
            innerMap[0][i].setLatticeState(latticePriority.EDGE);
            innerMap[mapSizeRow - 1][i].setLatticeState(latticePriority.EDGE);
            //innerMap[1][i].setLatticeState(latticePriority.WALL);
            //innerMap[mapSizeRow - 2][i].setLatticeState(latticePriority.WALL);
        }

        // 地图起点终点设置
        innerMap[mapStartRow][mapStartColumn].setLatticeState(latticePriority.UNTOUCHED);
        innerMap[mapEndRow][mapEndColumn].setLatticeState(latticePriority.UNTOUCHED);
    }

    /**
     * 直接传入原始迷宫进行拷贝
     *
     * @param originMap 原始迷宫
     */
    public map(map originMap) {
        copyMap(originMap);
        this.mapSizeRow = originMap.getMapSizeRow();
        this.mapSizeColumn = originMap.getMapSizeColumn();
        this.mapEndRow = originMap.getMapEndRow();
        this.mapEndColumn = originMap.getMapEndColumn();
        this.mapStartRow = originMap.getMapStartRow();
        this.mapStartColumn = originMap.getMapStartColumn();
    }

    public int getMapSizeRow() {
        return mapSizeRow;
    }

    public void setMapSizeRow(int mapSizeRow) {
        this.mapSizeRow = mapSizeRow;
    }

    public int getMapSizeColumn() {
        return mapSizeColumn;
    }

    public void setMapSizeColumn(int mapSizeColumn) {
        this.mapSizeColumn = mapSizeColumn;
    }

    public int getMapEndRow() {
        return mapEndRow;
    }

    public void setMapEndRow(int mapEndRow) {
        this.mapEndRow = mapEndRow;
    }

    public int getMapEndColumn() {
        return mapEndColumn;
    }

    public void setMapEndColumn(int mapEndColumn) {
        this.mapEndColumn = mapEndColumn;
    }

    public int getMapStartRow() {
        return mapStartRow;
    }

    public void setMapStartRow(int mapStartRow) {
        this.mapStartRow = mapStartRow;
    }

    public int getMapStartColumn() {
        return mapStartColumn;
    }

    public void setMapStartColumn(int mapStartColumn) {
        this.mapStartColumn = mapStartColumn;
    }

    public lattice getLattice(int latticeRow, int latticeColumn) {
        return innerMap[latticeRow][latticeColumn];
    }

    /**
     * 地图拷贝函数
     *
     * @param originMaze 原始迷宫
     */
    private void copyMap(map originMaze) {
        int inputMapSizeRow = originMaze.getMapSizeRow();
        int inputMapSizeColumn = originMaze.getMapSizeColumn();
        innerMap = new lattice[inputMapSizeRow][inputMapSizeColumn];
        for (int i = 0; i < inputMapSizeRow; i++) {
            for (int j = 0; j < inputMapSizeColumn; j++) {
                innerMap[i][j] = new lattice(i, j);
                innerMap[i][j].copy(originMaze.getLattice(i, j));
            }
        }
    }

    public latticePriority getLatticeState(int row, int column) {
        return innerMap[row][column].getLatticeState();
    }

    public void setLatticeState(int latticeRow, int latticeColumn, latticePriority newPriority) {
        innerMap[latticeRow][latticeColumn].setLatticeState(newPriority);
    }

    public boolean isStart(int row, int column) {
        return row == mapStartRow && column == mapStartColumn;
    }

    public boolean isEnd(int row, int column) {
        return row == mapEndRow && column == mapEndColumn;
    }

    /**
     * 设置起点终点
     *
     * @param mapStartRow    起点row
     * @param mapStartColumn 起点column
     * @param mapEndRow      终点row
     * @param mapEndColumn   终点column
     */
    public void setStartEnd(int mapStartRow, int mapStartColumn, int mapEndRow, int mapEndColumn) {
        this.mapStartRow = mapStartRow;
        this.mapStartColumn = mapStartColumn;
        this.mapEndRow = mapEndRow;
        this.mapEndColumn = mapEndColumn;
    }
}
