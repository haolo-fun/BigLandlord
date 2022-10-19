package fun.haolo.bigLandlord.db.param;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author haolo
 * @since 2022-10-18 14:37
 */
public class TenantParam {

    private Long id;

    @ApiModelProperty("租客姓名")
    private String name;

    @ApiModelProperty("租客电话")
    private String mobile;

    @ApiModelProperty("身份证")
    private String idcard;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }
}
