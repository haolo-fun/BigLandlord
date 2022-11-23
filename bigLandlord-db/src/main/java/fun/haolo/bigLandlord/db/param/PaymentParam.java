package fun.haolo.bigLandlord.db.param;

/**
 * @author haolo
 * @since 2022-11-22 15:32
 */
public class PaymentParam {

    private String phone;
    private String code;

    public String getPhone() {
        return phone;
    }

    public void setPhoneNumber(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
