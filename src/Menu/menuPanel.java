package Menu;

import DefaultParam.menuDefault;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * 菜单Panel
 * 按钮等控件添加在这里
 */
public class menuPanel extends JPanel {
    public JLabel helloLabel;
    public JButton newButton;
    public JButton openButton;
    public JButton createButton;
    public JButton exitButton;

    public menuPanel() {
        //设置面板基本参数
        setBackground(menuDefault.menuColor);
        setLayout(new GridLayout(5, 1));
        //添加组件
        helloLabel = new JLabel("CHOOSE ONE", JLabel.CENTER);
        add(helloLabel);

        newButton = new JButton("NEW");
        add(newButton);

        openButton = new JButton("OPEN");
        add(openButton);

        createButton = new JButton("CREATE");
        add(createButton);

        exitButton = new JButton("EXIT");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("1");
            }
        });
        exitButton.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
        add(exitButton);

    }
}
