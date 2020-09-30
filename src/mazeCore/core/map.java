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
                innerMap[i][j] = new lattice();
            }
        }
        // 设置地图边界和内墙
        for (int i = 0; i < mapSizeRow; i++) {
            innerMap[i][0].setLatticeState(latticePriority.EDGE);
            innerMap[i][mapSizeColumn - 1].setLatticeState(latticePriority.EDGE);
            innerMap[i][1].setLatticeState(latticePriority.WALL);
            innerMap[i][mapSizeColumn - 2].setLatticeState(latticePriority.WALL);
        }
        for (int i = 1; i < mapSizeColumn - 1; i++) {
            innerMap[0][i].setLatticeState(latticePriority.EDGE);
            innerMap[mapSizeRow - 1][i].setLatticeState(latticePriority.EDGE);
            innerMap[1][i].setLatticeState(latticePriority.WALL);
            innerMap[mapSizeRow - 2][i].setLatticeState(latticePriority.WALL);
        }

        // 地图起点终点设置
        innerMap[mapStartRow][mapStartColumn].setLatticeState(latticePriority.UNTOUCHED);
        innerMap[mapEndRow][mapEndColumn].setLatticeState(latticePriority.UNTOUCHED);
    }
}
