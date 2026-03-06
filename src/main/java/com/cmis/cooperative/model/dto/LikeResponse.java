package com.cmis.cooperative.model.dto;

import com.cmis.cooperative.views.BaseView;
import com.fasterxml.jackson.annotation.JsonView;

public record LikeResponse(Boolean isLiked,
                           Long count) {
}
