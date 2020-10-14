package Menu;

import DefaultParam.menuDefault;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 主菜单Frame
 */
public class menuFrame extends JFrame {
    //程序名称
    private static final String projectName = "MAZE";
    public menuPanel menupanel;
    private static Point origin = new Point();
    public menuFrame() {
        //Frame基础配置
        setUndecorated(true);
        setTitle(projectName);          //设置窗口标题
        setSize(menuDefault.menuWidth, menuDefault.menuHeight);
        setIconImage(menuDefault.titleIcon.getImage());
        setResizable(false);            //设置窗口无法改变大小
        setLocationRelativeTo(null);    //居中显示
        menupanel = new menuPanel();
        //配置标题拖动效果
        menupanel.helloLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                origin.x = e.getX();
                origin.y = e.getY();
            }
        });
        menupanel.helloLabel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point p = getLocation();
                setLocation(p.x + e.getX() - origin.x, p.y + e.getY() - origin.y);
            }
        });
        add(menupanel);    //将标签组件添加到内容窗格上
    }
}
