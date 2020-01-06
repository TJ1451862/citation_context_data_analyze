package cn.edu.whu.irlab.citation_context_data_analyze.model;

import lombok.Data;

/**
 * @author gcr19
 * @version 1.0
 * @date 2019/12/20 15:31
 * @desc 句子节点 实体
 **/
@Data
public class SentenceElement {

    private int num;
    private String markType;
    private String CType;
    private String content;
    private boolean hasRefLabel;
    private int pNum;

    public int getpNum() {
        return pNum;
    }

    public void setpNum(int pNum) {
        this.pNum = pNum;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getMarkType() {
        return markType;
    }

    public void setMarkType(String markType) {
        this.markType = markType;
    }

    public String getCType() {
        return CType;
    }

    public void setCType(String CType) {
        this.CType = CType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isHasRefLabel() {
        return hasRefLabel;
    }

    public void setHasRefLabel(boolean hasRefLabel) {
        this.hasRefLabel = hasRefLabel;
    }
}
