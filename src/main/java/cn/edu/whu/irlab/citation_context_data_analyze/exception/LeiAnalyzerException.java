package cn.edu.whu.irlab.citation_context_data_analyze.exception;

import cn.edu.whu.irlab.citation_context_data_analyze.enums.LeiAnalyzerExceptionEnum;

/**
 * @author gcr19
 * @version 1.0
 * @date 2019/12/21 11:46
 * @desc 解析异常
 **/
public class LeiAnalyzerException extends Exception {

    private Integer code;

    public LeiAnalyzerException(String message) {
        super(message);
    }

    public LeiAnalyzerException(LeiAnalyzerExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMsg());
        this.code = exceptionEnum.getCode();
    }

    public LeiAnalyzerException(String msg,LeiAnalyzerExceptionEnum exceptionEnum) {
        super(msg+exceptionEnum.getMsg());
        this.code = exceptionEnum.getCode();
    }
}
