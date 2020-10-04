package mazeCore.core;

/**
 * 地图格点类
 */
public class lattice {
    //格点状态
    private latticePriority latticeState;
    private boolean playerPassed;
    private int latticeRow;
    private int latticeColumn;

    public lattice(int row, int column) {
        latticeRow = row;
        latticeColumn = column;
        latticeState = latticePriority.UNTOUCHED;
        playerPassed = false;
    }

    /**
     * 拷贝函数
     *
     * @param originLattice 原始格点
     */
    public void copy(lattice originLattice) {
        latticeState = originLattice.getLatticeState();
        playerPassed = originLattice.isPlayerPassed();
        latticeRow = originLattice.getLatticeRow();
        latticeColumn = originLattice.getLatticeColumn();

    }

    public latticePriority getLatticeState() {
        return latticeState;
    }

    public void setLatticeState(latticePriority latticeState) {
        this.latticeState = latticeState;
    }

    public boolean isPlayerPassed() {
        return playerPassed;
    }

    public void setPlayerPassed(boolean playerPassed) {
        this.playerPassed = playerPassed;
    }

    public int getLatticeRow() {
        return latticeRow;
    }

    public void setLatticeRow(int latticeRow) {
        this.latticeRow = latticeRow;
    }

    public int getLatticeColumn() {
        return latticeColumn;
    }

    public void setLatticeColumn(int latticeColumn) {
        this.latticeColumn = latticeColumn;
    }

    @Override
    public String toString() {
        return "[" + latticeRow + "," + latticeColumn + "," + latticeState + "]";
    }
}
