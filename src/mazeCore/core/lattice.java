package mazeCore.core;

/**
 * 地图格点类
 */
public class lattice {
    //格点状态
    private latticePriority latticeState;

    public lattice() {
        latticeState = latticePriority.UNTOUCHED;
    }

    public latticePriority getLatticeState() {
        return latticeState;
    }

    public void setLatticeState(latticePriority latticeState) {
        this.latticeState = latticeState;
    }
}
