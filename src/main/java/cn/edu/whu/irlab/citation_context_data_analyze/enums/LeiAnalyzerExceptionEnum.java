package cn.edu.whu.irlab.citation_context_data_analyze.enums;

/**
 * @author gcr19
 * @version 1.0
 * @date 2019/12/21 11:49
 * @desc LeiAnalyzerException 异常值枚举
 **/
public enum LeiAnalyzerExceptionEnum {

    LostNumAttr(101,"丢失number属性");


    private Integer code;
    private String msg;

    LeiAnalyzerExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
