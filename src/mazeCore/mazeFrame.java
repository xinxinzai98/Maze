package mazeCore;

import DefaultParam.menuDefault;

import javax.swing.*;
import java.awt.*;

/**
 * mazeFrame用于显示基本的new/load/create窗口背板
 */
public class mazeFrame extends JFrame {
    private static final String programName = "MAZE";
    public mazeMenuToolBar menuToolBar;
    public mazePanel mazepanel;
    public mazeToolPanel toolBarDown;

    public mazeFrame(Dimension mazeDimension) {
        //Frame基础配置
        setTitle(programName);  //设置窗口标题
        setIconImage(menuDefault.titleIcon.getImage());
        setResizable(false);    //设置窗口无法改变大小
        setLocationRelativeTo(null);    //居中显示
        setLayout(new BorderLayout());  //设置布局管理器
        setFrameSize(mazeDimension);
        //添加menuBar
        menuToolBar = new mazeMenuToolBar();
        setJMenuBar(menuToolBar);

        mazepanel = new mazePanel(mazeDimension);
        add(mazepanel, BorderLayout.CENTER);

        toolBarDown = new mazeToolPanel(mazeDimension);
        add(toolBarDown, BorderLayout.SOUTH);
    }

    /**
     * 通过用户输入确定Frame大小
     *
     * @param mazeDimension 输入迷宫大小
     */
    public void setFrameSize(Dimension mazeDimension) {
        setSize(menuDefault.menuWidth, menuDefault.menuHeight);
    }
}
