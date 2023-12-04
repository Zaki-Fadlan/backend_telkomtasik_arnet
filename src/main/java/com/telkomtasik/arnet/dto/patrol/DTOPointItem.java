package com.telkomtasik.arnet.dto.patrol;

import com.telkomtasik.arnet.model.patrol.Item;
import com.telkomtasik.arnet.model.patrol.Point;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DTOPointItem {
    @NotNull(message = "Point is Required")
    private Point point;
    @NotNull(message = "Item is Required")
    private Item item;
}
