import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ChengJi implements Serializable {
    private String xueHao;
    private Map<String, Double> keMuChengJi;

    public ChengJi(String xueHao) {
        this.xueHao = xueHao;
        this.keMuChengJi = new HashMap<>();
    }

    public String getXueHao() {
        return xueHao;
    }

    public Map<String, Double> getKeMuChengJi() {
        return keMuChengJi;
    }

    public void tianJiaChengJi(String keMu, Double chengJi) {
        keMuChengJi.put(keMu, chengJi);
    }

    public Double getChengJi(String keMu) {
        return keMuChengJi.get(keMu);
    }

    @Override
    public String toString() {
        return "学号: " + xueHao + ", 科目成绩: " + keMuChengJi;
    }
}


