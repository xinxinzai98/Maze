package mazeCore;

import DefaultParam.mazeDefault;

import javax.swing.*;
import java.awt.*;

/**
 * 放置绘制按钮Panel
 */
public class mazeToolPanel extends JPanel {
    public toolPanelButton showPathA;
    public toolPanelButton showPathB;
    public toolPanelButton clearMap;
    public toolPanelButton newMapA;
    public toolPanelButton newMapB;
    public solutionInfo time;
    public solutionInfo step;

    public mazeToolPanel() {
        //panel基础设置
        setBackground(mazeDefault.mazePanelColor);
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 20));
        time = new solutionInfo("用时");
        add(time);

        step = new solutionInfo("总步数");
        add(step);

        showPathA = new toolPanelButton("PathA");
        add(showPathA);

        showPathB = new toolPanelButton("PathB");
        add(showPathB);

        clearMap = new toolPanelButton("Clear");
        add(clearMap);

        newMapA = new toolPanelButton("NewA");
        add(newMapA);

        newMapB = new toolPanelButton("NewB");
        add(newMapB);

        setPreferredSize(new Dimension(mazeDefault.mazeToolBarDownWidth, 400));
    }
}

/**
 * 放置迷宫解信息
 */
class solutionInfo extends JPanel {
    public JLabel data;
    public solutionInfo(String text) {
        //基本设置
        setBackground(mazeDefault.mazePanelColor);
        //TODO 信息列表式样调整
        JLabel info = new JLabel(text + ":");
        add(info);
        data = new JLabel("0");
        add(data);
    }
}

/**
 * 题解按钮
 */
class toolPanelButton extends JButton {
    //TODO 按钮式样调整
    public toolPanelButton(String text) {
        super(text);
    }
}
