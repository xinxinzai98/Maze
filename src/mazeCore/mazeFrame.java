package mazeCore;

import DefaultParam.mazeDefault;
import DefaultParam.menuDefault;

import javax.swing.*;
import java.awt.*;

/**
 * mazeFrame用于显示基本的new/load/create窗口背板
 */
public class mazeFrame extends JFrame {
    private static final String programName = "MAZE";
    //内部组件
    public JPanel contentPanel;
    /**
     * 内部动画计时器
     */
    private Timer time;
    public mazeMenuToolBar menuToolBar;
    public mazePanel mazepanel;
    public mazeToolPanel toolBarDown;

    public mazeFrame(Dimension mazeDimension) {
        //Frame基础配置
        setTitle(programName + mazeDimension.width + "*" + mazeDimension.height);  //设置窗口标题
        setIconImage(menuDefault.titleIcon.getImage());
        setBackground(mazeDefault.mazePanelColor);
        setResizable(false);    //设置窗口无法改变大小
        //默认设置
        mazeDefault mazedefault = new mazeDefault(mazeDimension);
        time = new Timer(0, null);
        //添加menuBar
        menuToolBar = new mazeMenuToolBar();
        setJMenuBar(menuToolBar);

        //内容面板
        contentPanel = new JPanel();
        contentPanel.setBackground(mazeDefault.mazePanelColor);
        setContentPane(contentPanel);
        //迷宫绘图区域
        mazepanel = new mazePanel(mazeDimension);
        contentPanel.add(mazepanel);
        //解题按钮区域
        toolBarDown = new mazeToolPanel();
        toolBarDown.showPathA.addActionListener(e -> {
            //还原初始条件
            time.stop();
            toolBarDown.time.data.setText("0");
            toolBarDown.step.data.setText("0");
            mazepanel.paintOrigin();
            mazepanel.getSolutionA();
            time = new Timer(mazeDefault.timerDelay, e1 -> {
                mazepanel.paintSolutionA();
                toolBarDown.step.data.setText(String.valueOf(mazepanel.getCount()));
                toolBarDown.time.data.setText(mazepanel.getSolutionTime() + "ns");
                if (mazepanel.isOver()) {
                    time.stop();
                    toolBarDown.time.data.setText(mazepanel.getSolutionTime() + "ns");
                }
            });
            time.start();
        });
        toolBarDown.clearMap.addActionListener(e -> {
            time.stop();
            mazepanel.paintOrigin();
        });
        toolBarDown.newMapA.addActionListener(e -> mazepanel.newMapA());
        toolBarDown.newMapB.addActionListener(e -> mazepanel.newMapB());
        contentPanel.add(toolBarDown);

        pack();//包装
        setLocationRelativeTo(null);    //居中显示
    }
}
