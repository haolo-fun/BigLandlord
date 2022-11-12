package fun.haolo.bigLandlord.db.vo;

/**
 * @author haolo
 * @since 2022-11-09 16:34
 */
public class HouseOptionsVO {

    private Long houseId;
    private String address;

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
