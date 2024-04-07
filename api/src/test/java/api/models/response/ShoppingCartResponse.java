package api.models.response;

import api.models.CommonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ShoppingCartResponse extends CommonDto {

    private List<ProductInCartDto> cart;

    private BigDecimal total_price;

    private BigDecimal total_discount;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Accessors(chain = true)
    public static class ProductInCartDto extends CommonDto {

        private Integer id;

        private String name;

        private String category;

        private BigDecimal price;

        private BigDecimal discount;

        private Integer quantity;

    }

}
