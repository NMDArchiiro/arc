package com.arc.app.request.search;

import com.arc.app.utils.constants.ValidationMessage;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class SearchRequest {
    @NotNull(message = ValidationMessage.NULL)
    private Integer pageIndex;
    @NotNull(message = ValidationMessage.NULL)
    private Integer pageSize;
    private Integer parentId;
    private String textSearch;
    private Integer level;
    private List<Long> provinceIds;
    private List<Long> districtIds;
    private List<Long> communeIds;
}
