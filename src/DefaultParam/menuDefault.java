package DefaultParam;

import javax.swing.*;
import java.awt.*;

/**
 * 菜单默认设置
 */
public class menuDefault {
    /**
     * menuFrame默认设置
     */
    public static final int menuHeight = 575;
    public static final int menuWidth = 400;
    public static final Color menuColor = new Color(0, 197, 239);//好颜色
    public static final ImageIcon icon = new ImageIcon("image/mazeIcon.png");
    public static final int buttonDelay = 300;
    /**
     * menuTitle默认设置
     */
    public static final ImageIcon titleIcon = new ImageIcon("image/MAZE.png");
    public static final int titleHeight = 100;
    public static final int titleWidth = menuWidth;
    public static final int titleOffset = 25;

    /**
     * menuButton默认设置
     */
    public static final int buttonHeight = 100;
    public static final int buttonWidth = menuWidth;
    public static final int buttonOffset = 40;
    public static final Color buttonColor = Color.WHITE;
    public static final Color hoverColor = new Color(0, 103, 127);
    public static final Font buttonFont = new Font("思源宋体", Font.PLAIN, 50);
    public static final Font hoverFont = new Font("思源宋体", Font.PLAIN, 60);
    /**
     * 菜单装饰线默认设置
     */
    public static final Color menuLineColor = Color.WHITE;
    /**
     * 动画计时器速度
     */
    public static final int timerDelay = 10;
}
