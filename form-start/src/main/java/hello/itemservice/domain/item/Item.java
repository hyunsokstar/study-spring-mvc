package hello.itemservice.domain.item;
import lombok.Data;
import java.util.List;


@Data
public class Item {
    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;
    private Boolean open; // ex) name 이 open은 체크 박스 체크 => true 아닐 경우 false
    private List<String> regions; // ex) name이 같은 체크 박스 선택후 submit => [서울, 부산 ]
    private ItemType itemType; // ex) name이 itemType인 라디오 박스의  value
    private String deliveryCode; // ex) name 이 deliveryCode인 셀렉트 박스에서 선택된 value => 배송 방식

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
