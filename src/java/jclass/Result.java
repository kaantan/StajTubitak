package jclass;



/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author maktas
 */
public class Result<T> implements Serializable {

    public static Result SUCCESS;
    public static Result SUCCESS_EMPTY;
    public static Result FAILURE_AUTH_MEBBIS;
    public static Result FAILURE_AUTH_SSO;
    public static Result FAILURE_AUTH_WRONG;
    public static Result FAILURE_CODE_GAME;
    public static Result FAILURE_CODE_CLASS_SESSION;
    public static Result FAILURE_COOKIE_CREATION;
    public static Result FAILURE_DB;
    public static Result FAILURE_DB_DUPLICATE_ENTRY;
    public static Result FAILURE_DB_HIBERNATE;
    public static Result FAILURE_DB_MONGO;
    public static Result FAILURE_DB_PRIMARY_KEY;
    public static Result FAILURE_EMAIL_SEND;
    public static Result FAILURE_INDEX;
    public static Result FAILURE_JSON_MALFORMED;
    public static Result FAILURE_LOCKED_USER;
    public static Result FAILURE_LOGIN_UNSUCCESSFUL;
    public static Result FAILURE_MAC_WRONG;
    public static Result FAILURE_PASSWORD_WRONG;
    public static Result FAILURE_PASSWORD_MATCH;
    public static Result FAILURE_PARAM_WRONG;
    public static Result FAILURE_PARAM_MISMATCH;
    public static Result FAILURE_PROCESS;
    public static Result FAILURE_SCHOOL_CODE;
    public static Result FAILURE_SCHOOL_SAME;
    public static Result FAILURE_TCKN_WRONG;
    public static Result FAILURE_WS_NULL;
    public static Result FAILURE_SESSION_CLOSED;
    public static Result FAILURE_ELASTICSEARCH;

    //Last Code : EBA.029 - FAILURE_ELASTICSEARCH
    static {
        initializeStaticObjects(null);
        //initializeStaticObjects(ResourceBundle.getBundle("resources", Locale.ENGLISH));
    }

    @Expose
    private String resultCode;
    @Expose
    private String resultText;
    @Expose
    private Object parameter;
    @Expose
    private T content;

    public Result() {
    }

    private Result(String resultCode, String resultText) {
        this.resultCode = resultCode;
        this.resultText = resultText;
    }

    /**
     * This method checks whether the resultCode of the object equals to the
     * resultCode of the given result object
     *
     * @param result The result parameter to be compared with the object
     * @return Returns true if given result object matches the current one
     */
    public boolean checkResult(Result result) {
        return result != null && this.getResultCode().equals(result.getResultCode());
    }

    /**
     * This method is used to set a content into the Result object. One
     * important thing to consider is that this method creates a new instance of
     * Result class. So calling this method after calling setParameter() method
     * will erase the information that you set in setParameter() method. You
     * should use setContentAndParameter() method instead.
     *
     * @param content the object to be set which is an instance of given generic
     * class T
     * @return Returns the result object with the content set
     */
    public Result<T> setContent(T content) {
        Result<T> returnResult = new Result<T>(this.resultCode, this.resultText);
        returnResult.content = content;
        return returnResult;
    }

    /**
     * This method is used to set a custom parameter or message into the Result
     * object. One important thing to consider is that this method creates a new
     * instance of Result class. So calling this method after calling
     * setContent() method will erase the information that you set in
     * setContent() method. You should use setContentAndParameter() method
     * instead.
     *
     * @param parameter the parameter to be set which can be a custom message or
     * another object
     * @return Returns the result object with the parameter set
     */
    public Result<T> setParameter(Object parameter) {
        Result<T> returnResult = new Result<T>(this.resultCode, this.resultText);
        returnResult.parameter = parameter;
        return returnResult;
    }

    /**
     * This method is used to set a content and a custom parameter or message
     * into the Result object. One important thing to consider is that this
     * method creates a new instance of Result class. So calling this method
     * after calling setContent() method will erase the information that you set
     * in setContent() method. You should use setContentAndParameter() method
     * instead.
     *
     * @param content the object to be set which is an instance of given generic
     * class T
     * @param parameter the parameter to be set which can be a custom message or
     * another object
     * @return Returns the result object with the content and the parameter set
     */
    public Result<T> setContentAndParameter(T content, Object parameter) {
        Result<T> returnResult = new Result<T>(this.resultCode, this.resultText);
        returnResult.content = content;
        returnResult.parameter = parameter;
        return returnResult;
    }

    /**
     * This method is used to set language of the messages that Result objects
     * contain. One call for this method is enough to change all of the messages
     * to given language. Default language parameter for a Result object is
     * English. This language can be changed by using this method.
     *
     * @param lang Language parameter that changes the message language. Default
     * is 'en'. For Turkish, should be set as 'tr'
     */
    public static void setLanguage(String lang) {
        //initializeStaticObjects(ResourceBundle.getBundle("resources", lang==null ? Locale.ENGLISH : lang.equals("tr") ? new Locale("tr") : Locale.ENGLISH));
        initializeStaticObjects(null);
    }

    /**
     * This method initializes the static Result object with the given language
     * via Resource Bundle
     *
     * @param rs Resource Bundle that contains a properties file with desired
     * language
     */
    private static void initializeStaticObjects(ResourceBundle rs) {
        SUCCESS = new Result("ULAK.001", "result.ULAK.001");
        SUCCESS_EMPTY = new Result("ULAK.101", "result.ULAK.101");
        FAILURE_AUTH_MEBBIS = new Result("ULAK.023", "result.ULAK.023");
        FAILURE_AUTH_SSO = new Result("ULAK.007", "result.ULAK.007");
        FAILURE_AUTH_WRONG = new Result("ULAK.004", "result.ULAK.004");
        FAILURE_CODE_GAME = new Result("ULAK.014", "result.ULAK.014");
        FAILURE_COOKIE_CREATION = new Result("ULAK.020", "result.ULAK.020");
        FAILURE_DB = new Result("ULAK.008", "result.ULAK.008");
        FAILURE_DB_DUPLICATE_ENTRY = new Result("ULAK.017", "result.ULAK.017");
        FAILURE_DB_HIBERNATE = new Result("ULAK.009", "result.ULAK.009");
        FAILURE_DB_MONGO = new Result("ULAK.022", "result.ULAK.022");
        FAILURE_DB_PRIMARY_KEY = new Result("ULAK.018", "result.ULAK.018");
        FAILURE_EMAIL_SEND = new Result("ULAK.026", "result.ULAK.026");
        FAILURE_INDEX = new Result("ULAK.025", "result.ULAK.025");
        FAILURE_JSON_MALFORMED = new Result("ULAK.019", "result.ULAK.019");
        FAILURE_LOCKED_USER = new Result("ULAK.021", "result.ULAK.021");
        FAILURE_LOGIN_UNSUCCESSFUL = new Result("ULAK.013", "result.ULAK.013");
        FAILURE_MAC_WRONG = new Result("ULAK.015", "result.ULAK.015");
        FAILURE_PASSWORD_WRONG = new Result("ULAK.003", "result.ULAK.003");
        FAILURE_PASSWORD_MATCH = new Result("ULAK.011", "result.ULAK.011");
        FAILURE_PARAM_WRONG = new Result("ULAK.005", "result.ULAK.005");
        FAILURE_PARAM_MISMATCH = new Result("ULAK.024", "result.ULAK.024");
        FAILURE_PROCESS = new Result("ULAK.006", "result.ULAK.006");
        FAILURE_SCHOOL_CODE = new Result("ULAK.010", "result.ULAK.010");
        FAILURE_SCHOOL_SAME = new Result("ULAK.016", "result.ULAK.016");
        FAILURE_TCKN_WRONG = new Result("ULAK.002", "result.ULAK.002");
        FAILURE_WS_NULL = new Result("ULAK.012", "result.ULAK.012");
        FAILURE_CODE_CLASS_SESSION = new Result("ULAK.027", "result.ULAK.027");
        FAILURE_SESSION_CLOSED = new Result("ULAK.028", "result.ULAK.028");
        FAILURE_ELASTICSEARCH = new Result("ULAK.029", "result.ULAK.029");
    }

    /*Default Getters-Setters*/
    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultText() {
        return resultText;
    }

    public void setResultText(String resultText) {
        this.resultText = resultText;
    }

    public Object getParameter() {
        return parameter;
    }

    public T getContent() {
        return content;
    }
}

