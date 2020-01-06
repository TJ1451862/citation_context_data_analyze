package cn.edu.whu.irlab.citation_context_data_analyze.model;

import lombok.Data;

import java.util.List;

/**
 * @author gcr19
 * @version 1.0
 * @date 2019/12/20 15:35
 * @desc 参考文献 实体
 **/
@Data
public class RefLabelElement {

    private int num;
    private int refNum;
    private List<Integer> contextNum;
    private int sNum;
    private String content;
    private String atSentenceContent;
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

    public int getRefNum() {
        return refNum;
    }

    public void setRefNum(int refNum) {
        this.refNum = refNum;
    }

    public List<Integer> getContextNum() {
        return contextNum;
    }

    public void setContextNum(List<Integer> contextNum) {
        this.contextNum = contextNum;
    }

    public int getsNum() {
        return sNum;
    }

    public void setsNum(int sNum) {
        this.sNum = sNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAtSentenceContent() {
        return atSentenceContent;
    }

    public void setAtSentenceContent(String atSentenceContent) {
        this.atSentenceContent = atSentenceContent;
    }
}
