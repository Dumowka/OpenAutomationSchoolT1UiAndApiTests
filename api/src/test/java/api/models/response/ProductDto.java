package api.models.response;

import api.models.CommonDto;
import api.models.request.ProductRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ProductDto extends CommonDto {

    private Integer id;

    private String name;

    private String category;

    private BigDecimal price;

    private BigDecimal discount;

    public ProductRequestDto mapToProductRequestDto() {
        return new ModelMapper().map(this, ProductRequestDto.class);
    }

}
