package mazeCore;

import DefaultParam.mazeDefault;
import DefaultParam.menuDefault;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * mazeFrame用于显示基本的new/load/create窗口背板
 */
public class mazeFrame extends JFrame {
    private static final String programName = "MAZE";
    //内部面板
    public JPanel contentPanel;
    //内部组件
    public mazeMenuToolBar menuToolBar;
    //内部动画计时器
    private Timer time;
    public mazePanel mazepanel;
    public mazeToolPanel toolBarDown;

    public mazeFrame(Dimension mazeDimension) {
        //Frame基础配置
        setTitle(programName + mazeDimension.height + "*" + mazeDimension.width);  //设置窗口标题
        setIconImage(menuDefault.titleIcon.getImage());
        setBackground(mazeDefault.mazePanelColor);
        setResizable(false);    //设置窗口无法改变大小
        //默认设置
        new mazeDefault(mazeDimension);
        time = new Timer(0, null);
        //添加menuBar
        menuToolBar = new mazeMenuToolBar();
        //保存文件功能
        menuToolBar.saveFile.addActionListener(e -> {
            if (!mazepanel.isSaved()) {
                saveMap();
            }
        });
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
        //ShowPathA功能
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
        //clear功能
        toolBarDown.clearMap.addActionListener(e -> {
            time.stop();
            mazepanel.paintOrigin();
        });
        //新地图功能
        toolBarDown.newMapA.addActionListener(e -> mazepanel.newMapA());
        toolBarDown.newMapB.addActionListener(e -> mazepanel.newMapB());

        contentPanel.add(toolBarDown);
        pack();//包装
        setLocationRelativeTo(null);    //居中显示
    }

    /**
     * 保存地图至本地文件
     *
     * @return 是否保存成功
     */
    private boolean saveMap() {
        File saveDocument = new File(mazeDefault.savePath);    //文件路径（路径+文件名）
        if (!saveDocument.exists()) {    //目录不存在则创建目录并返回保存名称
            saveDocument.mkdir();
        }
        JFileChooser fc = new JFileChooser(mazeDefault.savePath);
        fc.setSelectedFile(new File(saveMapName()));
        int option = fc.showSaveDialog(null);    //文件保存对话框
        if (option == JFileChooser.APPROVE_OPTION) {//如果选择了文件
            File file = fc.getSelectedFile();
            if (!file.exists()) {    //文件不存在则创建文件
                try {
                    if (file.createNewFile()) {
                        try (OutputStream output = new FileOutputStream(file)) {
                            try (ObjectOutputStream oOutput = new ObjectOutputStream(output)) {
                                oOutput.writeObject(mazepanel.getInnerMaze());
                            }
                        }
                    } else {
                        return false;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {

            }
            mazepanel.setSaved(true);
            return true;
        } else {

        }
        return true;
    }

    /**
     * 读取save文件夹，获取默认保存文档的名称
     * 名称标准为"save" + Row + "*" Column + "&" +num
     * 例如"save300*300&1"
     *
     * @return 可用存档名称
     */
    private String saveMapName() {
        File saveDocument = new File(mazeDefault.savePath);    //文件路径（路径+文件名）
        if (!saveDocument.exists()) {    //目录不存在则创建目录并返回保存名称
            saveDocument.mkdir();
        }
        return "save" + mazepanel.mazeDimension.height + "*" + mazepanel.mazeDimension.width + "&" + getFileSavedOrder(saveDocument.listFiles());
    }

    /**
     * 判断是否为文件名是否为同样大小迷宫
     *
     * @param originFile 输入文件
     * @return 是否为同一大小
     */
    private boolean isSameDimension(File originFile) {
        String fileName = originFile.getName();
        String pattern = "\\d+";
        Pattern rex = Pattern.compile(pattern);
        Matcher matcher = rex.matcher(fileName);
        if (matcher.find()) {
            int matcherHeight = Integer.parseInt(matcher.group());
            if (matcher.find()) {
                int matcherWidth = Integer.parseInt(matcher.group());
                return matcherHeight == mazepanel.mazeDimension.height && matcherWidth == mazepanel.mazeDimension.width;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 计算得出当前地图应该保存的序号
     *
     * @param originFileList 输入文件组
     * @return 地图序号
     */
    private int getFileSavedOrder(File[] originFileList) {
        int order = 1;
        if (originFileList != null) {
            for (File f : sortFilterFileList(originFileList)
            ) {
                if (isSameDimension(f)) {
                    if (getFileOrder(f) == order) {
                        order++;
                    } else {
                        return order;
                    }
                }
            }
        }

        return order;
    }

    /**
     * 通过正则表达式得出文件序号
     *
     * @param originFile 输入文件
     * @return 文件序号
     */
    private int getFileOrder(File originFile) {
        String fileName = originFile.getName();
        String pattern = "\\d+$";
        Pattern rex = Pattern.compile(pattern);
        Matcher matcher = rex.matcher(fileName);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group());
        }
        return 0;
    }

    /**
     * 对原始文件列表进行筛选并排序
     *
     * @param originFileList 原始文件列表
     * @return 筛选排序后的文件列表
     */
    private ArrayList<File> sortFilterFileList(File[] originFileList) {
        ArrayList<File> finallyList = new ArrayList<>();
        for (File f : originFileList
        ) {
            if (isSameDimension(f)) {
                finallyList.add(f);
            }
        }
        finallyList.sort((o1, o2) -> {
            int i1 = getFileOrder(o1);
            int i2 = getFileOrder(o2);
            return Integer.compare(i1, i2);
        });
        return finallyList;
    }
}
