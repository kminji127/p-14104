package com.back.global.rsData;

import com.back.domain.comment.dto.CommentDto;

public record RsData(
        String resultCode,
        String msg,
        CommentDto data
) {
}
