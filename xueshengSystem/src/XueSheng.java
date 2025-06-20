import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

//完成学生信息录入:生成学号,输入姓名性别出生年月

//接口实现,允许该类的对象被序列化为字节流
public class XueSheng implements Serializable {
    private String xueHao;
    private String xingMing;
    private String xingBie;
    private String chuShengNianYueRi;

    public XueSheng(String xingMing, String xingBie, String chuShengNianYueRi) {
        this.xueHao = generateXueHao(); // 关键修改：生成学号
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


    // 记录当天已生成的学号数量（重置时间：每天0点）
    private static int dailySequence = 0;

    // 生成格式为 yyyyMMdd+4位序列号的学号（如202503013137）
    private String generateXueHao() {
        // 1. 获取当前日期（yyyyMMdd）
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String datePart = dateFormat.format(new Date());

        // 2. 生成4位序列号（每天从0开始递增）
        String sequencePart = String.format("%04d", ++dailySequence);

        // 3. 拼接成完整学号
        return datePart + sequencePart;
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



