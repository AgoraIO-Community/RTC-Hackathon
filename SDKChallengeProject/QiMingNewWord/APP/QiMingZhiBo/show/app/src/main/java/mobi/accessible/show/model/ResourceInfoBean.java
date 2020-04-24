package mobi.accessible.show.model;

public class ResourceInfoBean {

    /**
     * resourceId : Etkl6g-zSB7EpP-Da1zN6xMtA0u8n0fUrmRnvajgSgiddY9O7IjmdfsEms91gH98NdPHZhaHeqpsRjQWDh4oqMTpt3itEA4h28r83bFIfqwtUdCgrxiYptzP1uXrCQBnD7xdpwSIe4ETKa-ViSYOnIP0qETSf8fK_x7FG31cfzXqMAMHeB_qpCG6JRQT7U3Ekpw8lbAgdA7MPqw7MlGbBAHmXnH_bN1VOVxxtvkxKyyPniSYNrFn4TigdrRI5-KfISXdNkPwwz-uIvvf71xkDQ
     */

    private String sid;
    private String resourceId;
    /**
     * code : 1003
     * reason : cname/uid mismatch!
     */

    private int code;
    private String reason;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
