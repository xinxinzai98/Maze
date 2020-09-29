package Menu;

import DefaultParam.menuDefault;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 主菜单Panel
 * 按钮等控件添加在这里
 */
public class menuPanel extends JPanel {
    public menuTitle helloLabel;
    public menuButton newButton;
    public menuButton loadButton;
    public menuButton createButton;
    public menuButton exitButton;

    /**
     * 菜单面板构造器
     * 创建菜单面板并添加按钮与装饰线
     */
    public menuPanel() {
        //设置面板基本参数
        setBackground(menuDefault.menuColor);//背景颜色设置
        setLayout(null);//布局结构设置

        //添加组件
        helloLabel = new menuTitle();
        add(helloLabel);

        newButton = new menuButton("New", 1);
        add(newButton);

        loadButton = new menuButton("Load", 2);
        add(loadButton);

        createButton = new menuButton("Create", 3);
        add(createButton);

        exitButton = new menuButton("Exit", 4);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("1");
            }
        });
        add(exitButton);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(menuDefault.menuLineColor);
        g.drawLine(50, 125, 350, 125);
    }
}
