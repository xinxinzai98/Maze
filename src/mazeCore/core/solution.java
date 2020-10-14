package mazeCore.core;

/**
 * 迷宫解
 */
public class solution {
    //保存原始迷宫
    private final map innerMaze;
    //输出迷宫解
    private map solutionMap;
    //迷宫解品质
    private long solutionTime;  //最后一次解题用时
    private int count;          //最后一次解题总步数
    //可视化小球运动组件
    private int playerLocationRow;
    private int playerLocationColumn;

    /**
     * 传入待解迷宫构造
     *
     * @param originMaze 待解迷宫
     */
    public solution(map originMaze) {
        innerMaze = new map(originMaze);
        resetPlayerLocation();//重置小球位置
    }

    /**
     * 迷宫解A
     *
     * @return 标记好路线的迷宫
     */
    public map solutionA() {
        solutionMap = new map(innerMaze);
        count = 0;
        long startTime = System.nanoTime(); //程序开始记录时间
        setWay(solutionMap.getMapStartRow(), solutionMap.getMapStartColumn());
        long endTime = System.nanoTime(); //程序结束记录时间
        solutionTime = endTime - startTime;
        return solutionMap;
    }

    private boolean setWay(int row, int column) {
        if (solutionMap.getLatticeState(solutionMap.getMapEndRow()
                , solutionMap.getMapEndColumn()) == latticePriority.TOUCHABLE) {
            return true;
        } else {
            if (solutionMap.getLatticeState(row, column) == latticePriority.UNTOUCHED) {
                solutionMap.setLatticeState(row, column, latticePriority.TOUCHABLE);
                if (setWay(row + 1, column)) {//下
                    return true;
                } else if (setWay(row, column + 1)) {//右
                    return true;
                } else if (setWay(row - 1, column)) {//上
                    return true;
                } else if (setWay(row, column - 1)) {//左
                    return true;
                } else {
                    solutionMap.setLatticeState(row, column, latticePriority.IMPASSES);
                    return false;
                }
            } else {
                return false;
            }
        }
    }

    /**
     * 迷宫解B
     *
     * @return 标记好路线的迷宫
     */
    public map solutionB() {
        //TODO 完成B算法
        return solutionMap;
    }


    public map getInnerMaze() {
        return innerMaze;
    }

    public map getSolutionMap() {
        return solutionMap;
    }

    public long getSolutionTime() {
        return solutionTime;
    }

    public int getCount() {
        return count;
    }

    public void resetSolution() {
        solutionMap = new map(innerMaze);
    }

    public void resetPlayerLocation() {
        playerLocationRow = innerMaze.getMapStartRow();
        playerLocationColumn = innerMaze.getMapStartColumn();
    }

    public int getPlayerLocationRow() {
        return playerLocationRow;
    }

    public void setPlayerLocationRow(int playerLocationRow) {
        this.playerLocationRow = playerLocationRow;
    }

    public int getPlayerLocationColumn() {
        return playerLocationColumn;
    }

    public void setPlayerLocationColumn(int playerLocationColumn) {
        this.playerLocationColumn = playerLocationColumn;
    }

    public boolean isPlayer(int row, int column) {
        return row == playerLocationRow && column == playerLocationColumn;
    }

    /**
     * 根据周围状态重新定位小球
     */
    public void relocatePlayer() {
        count++;
        solutionMap.getLattice(playerLocationRow, playerLocationColumn).setPlayerPassed(true);
        if (solutionMap.getLatticeState(playerLocationRow + 1, playerLocationColumn) == latticePriority.TOUCHABLE
                && !solutionMap.getLattice(playerLocationRow + 1, playerLocationColumn).isPlayerPassed()
        ) {
            playerLocationRow = playerLocationRow + 1;//下
        } else if (solutionMap.getLatticeState(playerLocationRow, playerLocationColumn + 1) == latticePriority.TOUCHABLE
                && !solutionMap.getLattice(playerLocationRow, playerLocationColumn + 1).isPlayerPassed()
        ) {
            playerLocationColumn = playerLocationColumn + 1;//右
        } else if (solutionMap.getLatticeState(playerLocationRow - 1, playerLocationColumn) == latticePriority.TOUCHABLE
                && !solutionMap.getLattice(playerLocationRow - 1, playerLocationColumn).isPlayerPassed()

        ) {
            playerLocationRow = playerLocationRow - 1;//上
        } else if (solutionMap.getLatticeState(playerLocationRow, playerLocationColumn - 1) == latticePriority.TOUCHABLE
                && !solutionMap.getLattice(playerLocationRow, playerLocationColumn - 1).isPlayerPassed()
        ) {
            playerLocationColumn = playerLocationColumn - 1;//左
        }
    }

}
