package Menu;

import DefaultParam.menuDefault;

import javax.swing.*;
import java.awt.*;

/**
 * 主菜单Panel
 * 按钮等控件添加在这里
 */
public class menuPanel extends JPanel {
    //面板内组件
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
        //标题
        helloLabel = new menuTitle();
        add(helloLabel);
        //新建
        newButton = new menuButton("New", 1);
        add(newButton);
        //读取
        loadButton = new menuButton("Load", 2);
        add(loadButton);
        //地图编辑器
        createButton = new menuButton("Create", 3);
        createButton.addActionListener(e -> JOptionPane.showConfirmDialog(null, "功能还在开发中！", "敬请期待", JOptionPane.WARNING_MESSAGE));
        add(createButton);
        //退出
        exitButton = new menuButton("Exit", 4);
        exitButton.addActionListener(e -> System.exit(0));
        add(exitButton);
    }
    /**
     * 绘制装饰线
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(menuDefault.menuLineColor);
        g.drawLine(50, 125, 350, 125);
    }
}
