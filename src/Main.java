public class Main {
    public static void main(String[] args) {
        // 启动登录界面或主界面
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new DengLuJieMian();
            }
        });
    }
}