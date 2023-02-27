package fun.haolo.bigLandlord.core.param;

/**
 * @author haolo
 * @since 2023-02-20 11:10
 */
public class ResetPassWordParam {
    private String oldPw;
    private String newPw;

    public ResetPassWordParam(String oldPw, String newPw) {

        this.oldPw = oldPw;
        this.newPw = newPw;
    }

    public String getOldPw() {
        return oldPw;
    }

    public void setOldPw(String oldPw) {
        this.oldPw = oldPw;
    }

    public String getNewPw() {
        return newPw;
    }

    public void setNewPw(String newPw) {
        this.newPw = newPw;
    }
}
