package fun.haolo.bigLandlord.db.vo;

/**
 * @author haolo
 * @since 2022-11-03 15:42
 */
public class TenantOptionsVO {

    private Long TenantId;
    private String name;

    private String mobile;

    public Long getTenantId() {
        return TenantId;
    }

    public void setTenantId(Long tenantId) {
        TenantId = tenantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "TenantOptionsVO{" +
                "TenantId=" + TenantId +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}
