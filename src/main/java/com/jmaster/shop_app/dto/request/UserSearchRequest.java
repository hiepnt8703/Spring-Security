package com.jmaster.shop_app.dto.request;

import lombok.*;

@Getter
@Setter
public class UserSearchRequest {
    private Integer page = 0;

    private Integer size = 10;

    private String sortBy = "id";

    private String sortDir = "desc";

    private Boolean includeDeleted = false;
}
