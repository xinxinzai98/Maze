package DefaultParam;

import java.awt.*;

/**
 * maze框架内部默认类
 */
public class mazeDefault {
    public static final Color mazePanelColor = new Color(0, 197, 239);//好颜色;
    public static final int mazeToolBarDownWidth = 100;
    public static final int mazeFrameOffset = 1;
    public static final int screenHeight = 800;
    //地图绘制颜色规则
    public static final Color mazeWallColor = Color.WHITE;
    public static final Color mazeRightPathColor = Color.GREEN;
    public static final Color mazeTouchableColor = Color.RED;
    public static final Color mazeLineColor = Color.WHITE;
    public static final Color mazeSEColor = Color.MAGENTA;
    public static final Color mazePlayerColor = Color.RED;
    public static final Color mazePlayerPassedColor = new Color(0, 103, 127);
    public static final int timerDelay = 2;
    public static int mazeLatticeSize;

    public mazeDefault(Dimension mazeDimension) {
        setLatticeSizeByMaze(mazeDimension);
    }

    private void setLatticeSizeByMaze(Dimension mazeDimension) {
        mazeLatticeSize = 800 / mazeDimension.height;
    }

}
