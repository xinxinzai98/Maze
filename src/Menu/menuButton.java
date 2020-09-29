package Menu;

import DefaultParam.menuDefault;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 主菜单中的按钮类
 * 与鼠标焦点绑定实现动画效果
 */
public class menuButton extends JButton {
    /**
     * 内部动画计时器
     */
    private Timer time;

    /**
     * 按钮类构造器
     *
     * @param text 按钮文本
     * @param no   按钮在主菜单中的位置
     */
    public menuButton(String text, int no) {
        //初始化按钮
        super(text);
        time = new Timer(0, null);
        //设置Button基本属性
        setContentAreaFilled(false);    //设置透明
        setBorderPainted(false);        //设置无边框
        setForeground(menuDefault.buttonColor);     //设置背景颜色
        setFont(menuDefault.buttonFont);
        setBounds((menuDefault.menuWidth - menuDefault.buttonWidth) / 2, menuDefault.buttonOffset + menuDefault.buttonHeight * no,
                menuDefault.buttonWidth, menuDefault.buttonHeight);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                setForeground(menuDefault.hoverColor);
                setFont(menuDefault.buttonFont);
                time.stop();
                time = new Timer(10, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        setFont(new Font("思源宋体", Font.PLAIN, getFont().getSize() + 1));
                        repaint();
                        if (getFont().getSize() >= menuDefault.hoverFont.getSize()) {
                            time.stop();
                        }
                    }
                });
                time.start();

            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                setForeground(menuDefault.buttonColor);
                setFont(menuDefault.hoverFont);
                time.stop();
                time = new Timer(10, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        setFont(new Font("思源宋体", Font.PLAIN, getFont().getSize() - 1));
                        repaint();
                        if (getFont().getSize() <= menuDefault.buttonFont.getSize()) {
                            time.stop();
                        }
                    }
                });
                time.start();
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(menuDefault.menuLineColor);
    }
}
