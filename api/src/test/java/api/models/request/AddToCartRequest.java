package api.models.request;

import api.models.CommonDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AddToCartRequest extends CommonDto {

    @JsonProperty("product_id")
    private Integer productId;

    private Integer quantity;

}
