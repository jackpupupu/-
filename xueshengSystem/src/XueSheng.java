import java.io.Serializable;
import java.util.HashSet;

//完成学生信息录入:生成学号,输入姓名性别出生年月

//接口实现,允许该类的对象被序列化为字节流
public class XueSheng implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final HashSet<String> usedXueHao = new HashSet<>();
    private String xueHao;
    private String xingMing;
    private String xingBie;
    private String chuShengNianYueRi;

    public XueSheng(String xingMing, String xingBie, String chuShengNianYueRi) {
        this.xueHao = generateXueHao(); // 关键修改：生成学号
        usedXueHao.add(this.xueHao);
        this.xingMing = xingMing;
        this.xingBie = xingBie;
        this.chuShengNianYueRi = chuShengNianYueRi;
    }

    /*

    private String generateXueHao() {
        //UUID.randomUUID()：生成全局唯一标识符
        //new Timestamp(System.currentTimeMillis()).getTime()：获取当前时间戳
        //拼接生成学号
        return UUID.randomUUID().toString() + new Timestamp(System.currentTimeMillis()).getTime();
    }
     */

    //更改学号生成方法

    // 生成六位纯数字且与当前时间相关且不重复的学号
    private synchronized String generateXueHao() {
        long base = System.currentTimeMillis() % 1000000; // 取当前时间戳后六位
        for (int i = 0; i < 1000000; i++) {
            long candidateNum = (base + i) % 1000000;
            String candidate = String.format("%06d", candidateNum);
            if (!usedXueHao.contains(candidate)) {
                return candidate;
            }
        }
        throw new RuntimeException("学号已用尽");
    }

    public String getXueHao() {
        return xueHao;
    }//xueHao只设置getter方法保证学号不可变

    public String getXingMing() {
        return xingMing;
    }

    public void setXingMing(String xingMing) {
        this.xingMing = xingMing;
    }

    public String getXingBie() {
        return xingBie;
    }

    public void setXingBie(String xingBie) {
        this.xingBie = xingBie;
    }

    public String getChuShengNianYueRi() {
        return chuShengNianYueRi;
    }

    public void setChuShengNianYueRi(String chuShengNianYueRi) {
        this.chuShengNianYueRi = chuShengNianYueRi;
    }

    @Override
    public String toString() {
        return "学号: " + xueHao + ", 姓名: " + xingMing + ", 性别: " + xingBie + ", 出生年月日: " + chuShengNianYueRi;
    }
}



