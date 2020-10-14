package mazeCore;

import DefaultParam.mapDefault;
import DefaultParam.mazeDefault;
import DefaultParam.menuDefault;
import mazeCore.core.map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

    /**
     * 随机生成构造函数
     *
     * @param mazeDimension 用户输入的地图大小
     */
    public mazeFrame(Dimension mazeDimension) {
        //Frame基础配置
        setTitle(programName + mazeDimension.height + "*" + mazeDimension.width);  //设置窗口标题
        new mazeDefault(mazeDimension);
        mazepanel = new mazePanel(mazeDimension);
        addFunction();
        mazeFrameDefaultSetting();
    }

    /**
     * 读取地图直接生成
     *
     * @param newMap 读取到的地图
     */
    public mazeFrame(map newMap) {
        //Frame基础配置
        setTitle(programName + newMap.getDimension().height + "*" + newMap.getDimension().width);  //设置窗口标题
        new mazeDefault(newMap.getDimension());
        mazepanel = new mazePanel(newMap);
        addFunction();
        mazeFrameDefaultSetting();
    }

    /**
     * 默认设置
     */
    public void mazeFrameDefaultSetting() {
        setIconImage(menuDefault.titleIcon.getImage());
        setBackground(mazeDefault.mazePanelColor);
        setResizable(false);    //设置窗口无法改变大小
        time = new Timer(0, null);  //重置计时器
        setLocationRelativeTo(null);    //居中显示
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                remindSave();
                int result = JOptionPane.showConfirmDialog(null, "确认退出?", "确认", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    // 退出
                    System.exit(0);
                }
            }
        });
    }

    /**
     * 为按钮绑定监听器
     */
    private void addFunction() {
        //添加menuBar
        menuToolBar = new mazeMenuToolBar();
        menuToolBar.newFile.addActionListener(e -> {
            remindSave();
            newMap();
        });
        //保存文件功能
        menuToolBar.saveFile.addActionListener(e -> {
            if (!mazepanel.isSaved()) {
                saveMap();
            }
        });
        //打开文件功能
        menuToolBar.openFile.addActionListener(e -> {
            remindSave();
            loadMap();
        });
        //退出功能
        menuToolBar.exit.addActionListener(e -> {
            remindSave();
            System.exit(0);
        });
        setJMenuBar(menuToolBar);
        //内容面板
        contentPanel = new JPanel();
        contentPanel.setBackground(mazeDefault.mazePanelColor);
        setContentPane(contentPanel);
        //迷宫绘图区域
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
        toolBarDown.showPathB.addActionListener(e ->
                JOptionPane.showConfirmDialog(null, "功能还在开发中！", "敬请期待", JOptionPane.WARNING_MESSAGE)
        );
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
    }

    /**
     * 新建地图
     *
     * @return 是否新建成功
     */
    private boolean newMap() {
        try {
            Dimension mazeDimension = userInitializeMapSize();
            changeFrame(mazeDimension);
            new mazeDefault(mazeDimension);
            contentPanel.removeAll();
            mazepanel = new mazePanel(mazeDimension);
            addFunction();
            contentPanel.revalidate();
            contentPanel.repaint();
            mazeFrameDefaultSetting();
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    /**
     * 初始化迷宫大小
     * 弹出对话框接受用户输入
     *
     * @return 用户输入的迷宫大小
     */
    public Dimension userInitializeMapSize() throws Exception {
        String sMazeSizeRow = JOptionPane.showInputDialog(
                "请输入迷宫行数(row):",
                mapDefault.mapSizeRow);
        int iMazeSizeRow;
        if (sMazeSizeRow != null) {
            try {
                iMazeSizeRow = Integer.parseInt(sMazeSizeRow);
            } catch (NumberFormatException e) {
                iMazeSizeRow = mapDefault.mapSizeRow;
            }
        } else {
            throw new Exception();
        }
        String sMazeSizeColumn = JOptionPane.showInputDialog(
                "请输入迷宫列数(column):",
                mapDefault.mapSizeColumn);
        int iMazeSizeColumn;
        if (sMazeSizeColumn != null) {
            try {
                iMazeSizeColumn = Integer.parseInt(sMazeSizeColumn);
            } catch (NumberFormatException e) {
                iMazeSizeColumn = mapDefault.mapSizeColumn;
            }
        } else {
            throw new Exception();
        }
        return new Dimension(iMazeSizeColumn, iMazeSizeRow);
    }

    /**
     * 保存地图至本地文件
     *
     * @return 是否保存成功
     */
    private boolean saveMap() {
        File saveDocument = new File(mazeDefault.savePath);    //文件路径（路径+文件名）
        if (!saveDocument.exists()) {    //目录不存在则创建目录
            saveDocument.mkdir();
            return false;
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
            }
            mazepanel.setSaved(true);
            return true;
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

    /**
     * 打开已有地图
     *
     * @return 是否打开成功
     */
    private boolean loadMap() {
        File saveDocument = new File(mazeDefault.savePath);    //文件路径（路径+文件名）
        if (!saveDocument.exists()) {    //目录不存在则创建目录并返回保存名称
            saveDocument.mkdir();
        }
        JFileChooser fc = new JFileChooser(mazeDefault.savePath);
        int option = fc.showOpenDialog(null);    //文件保存对话框
        if (option == JFileChooser.APPROVE_OPTION) {//如果选择了文件
            File file = fc.getSelectedFile();
            if (!file.exists()) {                //文件不存在
                JOptionPane.showConfirmDialog(null, "文件不存在！", "WARNING", JOptionPane.WARNING_MESSAGE);
                return false;
            } else {
                try (InputStream input = new FileInputStream(file)) {
                    try (ObjectInputStream oInput = new ObjectInputStream(input)) {
                        try {//成功读取到地图
                            map readMap = new map((map) oInput.readObject());
                            changeFrame(readMap.getDimension());
                            contentPanel.removeAll();
                            mazepanel = new mazePanel(readMap);
                            addFunction();
                            contentPanel.revalidate();
                            contentPanel.repaint();
                        } catch (ClassNotFoundException e) {//文件内容错误
                            JOptionPane.showConfirmDialog(null, "无法读取文件！", "WARNING", JOptionPane.WARNING_MESSAGE);
                            return false;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 改变Frame大小
     *
     * @param newDimension 新大小
     */
    private void changeFrame(Dimension newDimension) {
        setTitle(programName + newDimension.height + "*" + newDimension.width);  //设置窗口标题
        new mazeDefault(newDimension);
        setLocationRelativeTo(null);    //居中显示
    }

    /**
     * 提醒用户保存地图
     */
    private void remindSave() {
        if (!mazepanel.isSaved()) {
            int result = JOptionPane.showConfirmDialog(null, "是否保存当前地图?", "地图未保存", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                saveMap();
            }
        }
    }
}
