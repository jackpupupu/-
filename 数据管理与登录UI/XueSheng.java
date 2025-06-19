import java.io.Serializable;
import java.util.UUID;
import java.sql.Timestamp;

public class XueSheng implements Serializable {
    private String xueHao;
    private String xingMing;
    private String xingBie;
    private String chuShengNianYueRi;

    public XueSheng(String xingMing, String xingBie, String chuShengNianYueRi) {
        this.xueHao = generateXueHao();
        this.xingMing = xingMing;
        this.xingBie = xingBie;
        this.chuShengNianYueRi = chuShengNianYueRi;
    }

    private String generateXueHao() {
        return UUID.randomUUID().toString() + new Timestamp(System.currentTimeMillis()).getTime();
    }

    public String getXueHao() {
        return xueHao;
    }

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


