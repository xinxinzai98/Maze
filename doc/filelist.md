# 文件结构
在控制台使用以下命令可以生成文件结构树
```
tree -I out
```
效果如下
```
├── README.md
├── doc 文档文件
│   ├── filelist.md
│   ├── info.md
│   └── property.md
├── image 美术文件
│   ├── MAZE.png
│   ├── mazeIcon.png
│   └── project
│       └── MAZE.psd
├── save 试用存档文件夹
│   ├── save100*100&1
│   └── save30*30&1
└── src
    ├── DefaultParam 默认参数位置
    │   ├── mapDefault.java
    │   ├── mazeDefault.java
    │   └── menuDefault.java
    ├── Maze.java 程式主入口
    ├── Menu 运行开始菜单
    │   ├── menuButton.java
    │   ├── menuFrame.java
    │   ├── menuPanel.java
    │   └── menuTitle.java
    ├── mazeCore 迷宫可视化文件
    │   ├── core 迷宫核心实现
    │   │   ├── generateMaze.java 迷宫生成器
    │   │   ├── lattice.java 
    │   │   ├── latticePriority.java
    │   │   ├── map.java 迷宫类
    │   │   └── solution.java 迷宫解类
    │   ├── mazeFrame.java
    │   ├── mazeMenuToolBar.java
    │   ├── mazePanel.java
    │   └── mazeToolPanel.java
    └── mazeEditor 地图编辑器功能'未来完成'
```
