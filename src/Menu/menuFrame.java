package Menu;

import DefaultParam.menuDefault;

import javax.swing.*;

/**
 * 菜单Frame
 */
public class menuFrame extends JFrame {

    private static final String projectName = "MAZE";

    public menuPanel menupanel;

    public menuFrame() {
        setTitle(projectName);  //设置窗口标题
        setSize(menuDefault.menuWidth, menuDefault.menuHeight);
        setResizable(false);    //设置窗口无法改变大小
        setLocationRelativeTo(null);    //居中显示
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //设置窗口退出
        menupanel = new menuPanel();
        add(menupanel);    //将标签组件添加到内容窗格上
    }
}
