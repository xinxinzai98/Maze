package mazeCore.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

/**
 * 地图生成器
 */
public class generateMaze {
    //内置生成地图
    private final map innerMaze;
    //内置栈
    private final Stack<lattice> resStack = new Stack<>();
    //内置List
    private final List<lattice> resList = new ArrayList<>();

    /**
     * 生成指定行数列数的Map
     *
     * @param inputMapSizeRow    输入行数
     * @param inputMapSizeColumn 输入列数
     */
    public generateMaze(int inputMapSizeRow, int inputMapSizeColumn) {
        innerMaze = new map(inputMapSizeRow, inputMapSizeColumn);
        setWallAll();
        generateRandomOpposingSE();
    }

    /**
     * 设置全为墙
     */
    private void setWallAll() {
        for (int i = 1; i < innerMaze.getMapSizeRow() - 1; i++) {
            for (int j = 1; j < innerMaze.getMapSizeColumn() - 1; j++) {
                innerMaze.setLatticeState(i, j, latticePriority.WALL);
            }
        }
    }

    /**
     * 随机生成对立的起点终点
     */
    private void generateRandomOpposingSE() {
        Random random = new Random();
        int startRow;
        int startColumn;
        int endRow;
        int endColumn;
        if (random.nextDouble() > 0.5) {
            startRow = random.nextInt(innerMaze.getMapSizeRow() - 2) + 1;
            endRow = random.nextInt(innerMaze.getMapSizeRow() - 2) + 1;
            if (random.nextDouble() > 0.5) {
                endColumn = 1;
                startColumn = innerMaze.getMapSizeColumn() - 2;
            } else {
                startColumn = 1;
                endColumn = innerMaze.getMapSizeColumn() - 2;
            }

        } else {
            startColumn = random.nextInt(innerMaze.getMapSizeColumn() - 2) + 1;
            endColumn = random.nextInt(innerMaze.getMapSizeColumn() - 2) + 1;
            if (random.nextDouble() > 0.5) {
                endRow = 1;
                startRow = innerMaze.getMapSizeRow() - 2;
            } else {
                startRow = 1;
                endRow = innerMaze.getMapSizeRow() - 2;
            }
        }
        innerMaze.setStartEnd(startRow, startColumn, endRow, endColumn);
    }

    /**
     * 生成地图
     */
    private void generateMapA() {
        resStack.push(innerMaze.getLattice(innerMaze.getMapStartRow(), innerMaze.getMapStartColumn()));
        do {
            lattice tmp = resStack.pop();
            if (isUsableAgain(tmp) <= 1) {
                randomPush(getCurrentLatticeList(tmp));
                tmp.setLatticeState(latticePriority.UNTOUCHED);
            }
        } while (!resStack.empty());
    }

    /**
     * 将格点列表中各格点随机压栈
     *
     * @param originList 格点列表
     */
    private void randomPush(List<lattice> originList) {
        Random r = new Random();
        while (originList.size() != 0) {
            int chooseLattice = r.nextInt(originList.size());
            resStack.push(originList.get(chooseLattice));
            originList.remove(chooseLattice);
        }
    }

    /**
     * 得到一个格点附近可用格点List
     *
     * @param originLattice 输入格点
     * @return 格点附近可用格点List
     */
    private List<lattice> getCurrentLatticeList(lattice originLattice) {
        List<lattice> currentLattice = new ArrayList<>();
        int row = originLattice.getLatticeRow();
        int column = originLattice.getLatticeColumn();
        if (isUsableLattice(innerMaze.getLattice(row + 1, column))) {//下
            currentLattice.add(innerMaze.getLattice(row + 1, column));
        }
        if (isUsableLattice(innerMaze.getLattice(row, column + 1))) {//右
            currentLattice.add(innerMaze.getLattice(row, column + 1));
        }
        if (isUsableLattice(innerMaze.getLattice(row - 1, column))) {//上
            currentLattice.add(innerMaze.getLattice(row - 1, column));
        }
        if (isUsableLattice(innerMaze.getLattice(row, column - 1))) {//左
            currentLattice.add(innerMaze.getLattice(row, column - 1));
        }
        return currentLattice;
    }

    /**
     * 确认一个格点是否可用
     *
     * @param originLattice 输入格点
     * @return 是否可用
     */
    private boolean isUsableLattice(lattice originLattice) {
        int row = originLattice.getLatticeRow();
        int column = originLattice.getLatticeColumn();
        if (originLattice.getLatticeState() != latticePriority.EDGE && originLattice.getLatticeState() != latticePriority.UNTOUCHED) {
            return innerMaze.getLatticeState(row + 1, column) != latticePriority.UNTOUCHED//下
                    && innerMaze.getLatticeState(row, column + 1) != latticePriority.UNTOUCHED //右
                    && innerMaze.getLatticeState(row - 1, column) != latticePriority.UNTOUCHED//上
                    && innerMaze.getLatticeState(row, column - 1) != latticePriority.UNTOUCHED;//左
        }
        return false;
    }

    /**
     * 确认一个格点附近空位个数
     *
     * @param originLattice 输入格点
     * @return 空位个数
     */
    private int isUsableAgain(lattice originLattice) {
        int row = originLattice.getLatticeRow();
        int column = originLattice.getLatticeColumn();
        int flag = 0;
        if (innerMaze.getLatticeState(row + 1, column) == latticePriority.UNTOUCHED) {
            flag++;
        }
        if (innerMaze.getLatticeState(row, column + 1) == latticePriority.UNTOUCHED) {
            flag++;
        }
        if (innerMaze.getLatticeState(row - 1, column) == latticePriority.UNTOUCHED) {
            flag++;
        }
        if (innerMaze.getLatticeState(row, column - 1) == latticePriority.UNTOUCHED) {
            flag++;
        }
        return flag;
    }

    /**
     * 生成地图B
     */
    private void generateMapB() {
        resList.add(innerMaze.getLattice(innerMaze.getMapStartRow(), innerMaze.getMapStartColumn()));
        do {
            Random r = new Random();
            lattice tmp = resList.get(r.nextInt(resList.size()));
            if (isUsableAgain(tmp) <= 1) {
                addLatticeList(getCurrentLatticeList(tmp));
                tmp.setLatticeState(latticePriority.UNTOUCHED);
            }
            resList.remove(tmp);
        } while (!resList.isEmpty());
    }

    /**
     * 将格点列表中各格点进List
     *
     * @param originList 格点列表
     */
    private void addLatticeList(List<lattice> originList) {
        resList.addAll(originList);
    }

    /**
     * 将地图起点终点开放
     */
    private void setSEState() {
        innerMaze.setLatticeState(innerMaze.getMapStartRow(), innerMaze.getMapStartColumn(), latticePriority.UNTOUCHED);
        innerMaze.setLatticeState(innerMaze.getMapEndRow(), innerMaze.getMapEndColumn(), latticePriority.UNTOUCHED);
    }

    /**
     * 根据不同参数执行不同生成策略
     *
     * @param argue 参数
     * @return 生成地图
     */
    public map getMaze(String argue) {
        switch (argue) {
            case "A": {
                generateMapA();
                break;
            }
            case "B": {
                generateMapB();
                break;
            }
        }
        randomCrack();
        setSEState();
        return innerMaze;
    }

    /**
     * 根据地图的大小，随机敲碎几面墙壁
     */
    private void randomCrack() {
        int count = 0;
        for (int i = 1; i < innerMaze.getMapSizeRow() - 1; i++) {
            for (int j = 1; j < innerMaze.getMapSizeColumn() - 1; j++) {
                if (innerMaze.getLatticeState(i, j) == latticePriority.WALL) {
                    count++;
                }
                if (count == (innerMaze.getMapSizeRow() + innerMaze.getMapSizeColumn()) / 2) {
                    count = 0;
                    innerMaze.setLatticeState(i, j, latticePriority.UNTOUCHED);
                }
            }
        }
    }

    /**
     * 开放起点终点附近四格，增加可能性
     */
    private void addFully() {
        innerMaze.setLatticeState(innerMaze.getMapStartRow(), innerMaze.getMapStartColumn(), latticePriority.UNTOUCHED);
        innerMaze.setLatticeState(innerMaze.getMapEndRow(), innerMaze.getMapEndColumn(), latticePriority.UNTOUCHED);
    }
}
