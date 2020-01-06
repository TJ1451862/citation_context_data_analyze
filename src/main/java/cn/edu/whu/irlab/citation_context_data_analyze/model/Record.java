package cn.edu.whu.irlab.citation_context_data_analyze.model;

/**
 * @author gcr19
 * @version 1.0
 * @date 2019/12/20 15:40
 * @desc 生成excel记录的实体
 **/
public class Record {

    private String articleId;
    private String sentenceHasLabel;
    private String neighborSentence;
    private int groupId;
    private int distance;
    private int isCitationContext;

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getSentenceHasLabel() {
        return sentenceHasLabel;
    }

    public void setSentenceHasLabel(String sentenceHasLabel) {
        this.sentenceHasLabel = sentenceHasLabel;
    }

    public String getNeighborSentence() {
        return neighborSentence;
    }

    public void setNeighborSentence(String neighborSentence) {
        this.neighborSentence = neighborSentence;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getIsCitationContext() {
        return isCitationContext;
    }

    public void setIsCitationContext(int isCitationContext) {
        this.isCitationContext = isCitationContext;
    }

    @Override
    public String toString() {
        return "Record{" +
                "articleId='" + articleId + '\'' +
                ", sentenceHasLabel='" + sentenceHasLabel + '\'' +
                ", neighborSentence='" + neighborSentence + '\'' +
                ", groupId=" + groupId +
                ", distance=" + distance +
                ", isCitationContext=" + isCitationContext +
                '}';
    }
}
