import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DengLuJieMian extends JFrame {
    private JTextField yongHuMing;    //用户名
    private JPasswordField miMa;     //密码
    private JButton dengLuAnNiu;      //登录按钮

    //登陆界面
    public DengLuJieMian() {
        setTitle("学生成绩管理系统登录");// 设置窗口标题
        setSize(300, 200);//窗口大小
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//窗口关闭按钮
        setLocationRelativeTo(null);//居中
        JPanel panel = new JPanel();
        add(panel);
        buJu(panel);

        setVisible(true);
    }

    private void buJu(JPanel panel) {
        panel.setLayout(null);// 设置面板定位布局

        // 创建"用户名"标签并设置位置
        JLabel userLabel = new JLabel("用户名:");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        // 创建用户名输入框并设置位置
        yongHuMing = new JTextField(20);
        yongHuMing.setBounds(100, 20, 160, 25);
        panel.add(yongHuMing);

        JLabel passwordLabel = new JLabel("密码:");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        miMa = new JPasswordField(20);
        miMa.setBounds(100, 50, 160, 25);
        panel.add(miMa);

        dengLuAnNiu = new JButton("登录");
        dengLuAnNiu.setBounds(100, 80, 80, 25);
        panel.add(dengLuAnNiu);

        dengLuAnNiu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = yongHuMing.getText();
                String pass = new String(miMa.getPassword());
                if (YongHu.yanZheng(user, pass)) {
                    JOptionPane.showMessageDialog(null, "登录成功！");
                    // TODO: 进入主界面
                    dispose(); // 关闭登录界面
                } else {
                    JOptionPane.showMessageDialog(null, "用户名或密码错误！");
                }
            }
        });
    }

    public static void main(String[] args) {
        new DengLuJieMian();
    }
}

