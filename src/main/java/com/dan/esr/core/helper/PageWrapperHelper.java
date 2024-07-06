package com.dan.esr.core.helper;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
public class PageWrapperHelper<T> extends PageImpl<T> {
    @Serial
    private static final long serialVersionUID = 1L;
    private final Pageable pageable;

    private PageWrapperHelper(Page<T> page, Pageable pageable) {
        super(page.getContent(), pageable, page.getTotalElements());
        this.pageable = pageable;
    }

    public static <T> PageWrapperHelper<T> of(Page<T> page, Pageable pageable) {
        return new PageWrapperHelper<>(page, pageable);
    }

    @NonNull
    @Override
    public Pageable getPageable() {
        return pageable;
    }
}