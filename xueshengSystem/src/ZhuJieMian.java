import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class ZhuJieMian extends JFrame {
    private XueShengGuanLi xueShengGuanLi;
    private ChengJiGuanLi chengJiGuanLi;
    private BaoBiaoShengCheng baoBiaoShengCheng;
    public ZhuJieMian() {
        setTitle("学生成绩管理系统");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        xueShengGuanLi = new XueShengGuanLi();
        chengJiGuanLi = new ChengJiGuanLi();
        baoBiaoShengCheng = new BaoBiaoShengCheng();
        new ExcelDaoChu();

        JTabbedPane tabbedPane = new JTabbedPane();

        // 学生管理界面
        JPanel xueShengPanel = new JPanel(new BorderLayout());
        tabbedPane.addTab("学生管理", xueShengPanel);
        chuangJianXueShengGuanLiJieMian(xueShengPanel);

        // 成绩管理界面
        JPanel chengJiPanel = new JPanel(new BorderLayout());
        tabbedPane.addTab("成绩管理", chengJiPanel);
        chuangJianChengJiGuanLiJieMian(chengJiPanel);

        // 报表生成界面
        JPanel baoBiaoPanel = new JPanel(new BorderLayout());
        tabbedPane.addTab("成绩报表", baoBiaoPanel);
        chuangJianBaoBiaoJieMian(baoBiaoPanel);

        add(tabbedPane);
        setVisible(true);
    }

    private void chuangJianXueShengGuanLiJieMian(JPanel panel) {
        // 添加学生
        JPanel addPanel = new JPanel(new GridLayout(4, 2));
        JTextField nameField = new JTextField();
        JTextField genderField = new JTextField();
        JTextField dobField = new JTextField();
        JButton addButton = new JButton("添加学生");

        addPanel.add(new JLabel("姓名:"));
        addPanel.add(nameField);
        addPanel.add(new JLabel("性别:"));
        addPanel.add(genderField);
        addPanel.add(new JLabel("出生年月日:"));
        addPanel.add(dobField);
        addPanel.add(addButton);

        addButton.addActionListener(e -> {
            XueSheng xs = new XueSheng(nameField.getText(), genderField.getText(), dobField.getText());
            xueShengGuanLi.tianJiaXueSheng(xs);
            JOptionPane.showMessageDialog(this, "学生添加成功，学号: " + xs.getXueHao());
        });

        panel.add(addPanel, BorderLayout.NORTH);

        // 显示学生列表
        JTextArea studentListArea = new JTextArea();
        studentListArea.setEditable(false);
        JButton refreshButton = new JButton("刷新学生列表");
        refreshButton.addActionListener(e -> {
            studentListArea.setText("");
            for (XueSheng xs : xueShengGuanLi.getXueShengLieBiao()) {
                studentListArea.append(xs.toString() + "\n");
            }
        });
        panel.add(new JScrollPane(studentListArea), BorderLayout.CENTER);
        panel.add(refreshButton, BorderLayout.SOUTH);
    }

    private void chuangJianChengJiGuanLiJieMian(JPanel panel) {
        // 批量添加成绩
        JPanel addScorePanel = new JPanel(new GridLayout(3, 2));
        JTextField courseField = new JTextField();
        JTextField scoreField = new JTextField();
        JButton addBatchScoreButton = new JButton("批量添加成绩");

        addScorePanel.add(new JLabel("科目:"));
        addScorePanel.add(courseField);
        addScorePanel.add(new JLabel("成绩:"));
        addScorePanel.add(scoreField);
        addScorePanel.add(addBatchScoreButton);

        addBatchScoreButton.addActionListener(e -> {
            String course = courseField.getText();
            double score = Double.parseDouble(scoreField.getText());
            List<String> xueHaoLieBiao = new java.util.ArrayList<>();
            for(XueSheng xs : xueShengGuanLi.getXueShengLieBiao()){
                xueHaoLieBiao.add(xs.getXueHao());
            }
            chengJiGuanLi.piLiangTianJiaChengJi(xueHaoLieBiao, course, score);
            JOptionPane.showMessageDialog(this, "批量添加成绩成功！");
        });
        panel.add(addScorePanel, BorderLayout.NORTH);

        // 查询成绩
        JPanel queryPanel = new JPanel(new GridLayout(2, 2));
        JTextField queryInput = new JTextField();
        JButton queryByIdButton = new JButton("按学号查询");
        JButton queryByNameButton = new JButton("按姓名查询");
        JTextArea queryResultArea = new JTextArea();
        queryResultArea.setEditable(false);

        queryPanel.add(new JLabel("学号/姓名:"));
        queryPanel.add(queryInput);
        queryPanel.add(queryByIdButton);
        queryPanel.add(queryByNameButton);

        queryByIdButton.addActionListener(e -> {
            String xueHao = queryInput.getText();
            ChengJi cj = chengJiGuanLi.chaZhaoChengJiByXueHao(xueHao);
            if (cj != null) {
                XueSheng xs = xueShengGuanLi.chaZhaoXueShengByXueHao(xueHao);
                queryResultArea.setText("姓名: " + xs.getXingMing() + ", 学号: " + xs.getXueHao() + ", 成绩: " + cj.getKeMuChengJi());
            } else {
                queryResultArea.setText("学号不存在！");
            }
        });

        queryByNameButton.addActionListener(e -> {
            String xingMing = queryInput.getText();
            List<ChengJi> chengJiList = chengJiGuanLi.chaZhaoChengJiByXingMing(xueShengGuanLi.getXueShengLieBiao(), xingMing);
            if (!chengJiList.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (ChengJi cj : chengJiList) {
                    XueSheng xs = xueShengGuanLi.chaZhaoXueShengByXueHao(cj.getXueHao());
                    sb.append("姓名: ").append(xs.getXingMing()).append(", 学号: ").append(xs.getXueHao()).append(", 成绩: ").append(cj.getKeMuChengJi()).append("\n");
                }
                queryResultArea.setText(sb.toString());
            } else {
                queryResultArea.setText("姓名不存在！");
            }
        });

        panel.add(queryPanel, BorderLayout.CENTER);
        panel.add(new JScrollPane(queryResultArea), BorderLayout.SOUTH);
    }

    private void chuangJianBaoBiaoJieMian(JPanel panel) {
        JButton generateReportButton = new JButton("生成学生学习情况报表");
        JTextArea reportArea = new JTextArea();
        reportArea.setEditable(false);
        JButton exportExcelButton = new JButton("导出到Excel");

        generateReportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                XueSheng[] xueShengArr = xueShengGuanLi.getXueShengLieBiao().toArray(new XueSheng[0]);
                ChengJi[] chengJiArr = chengJiGuanLi.getChengJiMap().values().toArray(new ChengJi[0]);
                BaoBiaoShengCheng.XueShengBaoBiao[] baoBiaoArr = baoBiaoShengCheng.shengChengBaoBiao(xueShengArr, chengJiArr);
                String text = "";
                for (int i = 0; i < baoBiaoArr.length; i++) {
                    BaoBiaoShengCheng.XueShengBaoBiao sbb = baoBiaoArr[i];
                    text += "学号: " + sbb.getXueHao()
                          + ", 姓名: " + sbb.getXingMing()
                          + ", 数学: " + (sbb.getKeMuChengJi().length > 0 ? sbb.getKeMuChengJi()[0] : "")
                          + ", Java: " + (sbb.getKeMuChengJi().length > 1 ? sbb.getKeMuChengJi()[1] : "")
                          + ", 体育: " + (sbb.getKeMuChengJi().length > 2 ? sbb.getKeMuChengJi()[2] : "")
                          + ", 总成绩: " + sbb.getZongChengJi()
                          + ", 班级总成绩平均值: " + sbb.getBanJiZongChengJiPingJun()
                          + "\n";
                }
                reportArea.setText(text);
            }
        });

        exportExcelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                XueSheng[] xueShengArr = xueShengGuanLi.getXueShengLieBiao().toArray(new XueSheng[0]);
                ChengJi[] chengJiArr = chengJiGuanLi.getChengJiMap().values().toArray(new ChengJi[0]);
                BaoBiaoShengCheng.XueShengBaoBiao[] baoBiaoArr = baoBiaoShengCheng.shengChengBaoBiao(xueShengArr, chengJiArr);
                ExcelDaoChu.exportToExcel(baoBiaoArr, "成绩表.xlsx");
                JOptionPane.showMessageDialog(null, "成绩报表已导出到 成绩表.xlsx");
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(generateReportButton);
        buttonPanel.add(exportExcelButton);

        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(reportArea), BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        // 启动登录界面
        SwingUtilities.invokeLater(DengLuJieMian::new);
    }
}


