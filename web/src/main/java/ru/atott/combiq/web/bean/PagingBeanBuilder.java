package ru.atott.combiq.web.bean;

import org.springframework.data.domain.Page;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PagingBeanBuilder {
    public <T> PagingBean build(Page<T> page, int currentPage, HttpServletRequest request) {
        PagingBean bean = new PagingBean();
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(request.getRequestURI());

        if (page.getTotalPages() <= 5) {
            bean.setPages(getPages(0, page.getTotalPages() - 1, currentPage, uriBuilder));
        } else {
            int leftEdge = Math.max(0, currentPage - 2);
            int rightEdge = Math.min(leftEdge + 4, page.getTotalPages());

            List<PagingBean.Page> pages = new ArrayList<>();
            if (leftEdge > 2) {
                pages.addAll(getPages(0, 0, currentPage, uriBuilder));
                pages.add(getOmission());
                pages.addAll(getPages(leftEdge, currentPage, currentPage, uriBuilder));
            } else {
                pages.addAll(getPages(0, currentPage, currentPage, uriBuilder));
            }

            if (rightEdge < page.getTotalPages() - 3) {
                pages.addAll(getPages(currentPage + 1, rightEdge, currentPage, uriBuilder));
                pages.add(getOmission());
                pages.addAll(getPages(page.getTotalPages() - 1, page.getTotalPages() - 1, currentPage, uriBuilder));
            } else {
                pages.addAll(getPages(currentPage + 1, page.getTotalPages() - 1, currentPage, uriBuilder));
            }

            bean.setPages(pages);
        }

        return bean;
    }

    private PagingBean.Page getOmission() {
        PagingBean.Page omission = new PagingBean.Page();
        omission.setTitle("...");
        omission.setOmission(true);
        return omission;
    }

    private List<PagingBean.Page> getPages(int from, int to, int currentPage, UriComponentsBuilder uriBuilder) {
        if (from > to) {
            return Collections.emptyList();
        }
        return IntStream.range(from, to + 1)
                .mapToObj(i -> {
                    PagingBean.Page beanPage = new PagingBean.Page();
                    beanPage.setTitle(String.valueOf(i + 1));
                    beanPage.setUrl(uriBuilder.replaceQueryParam("page", i + 1).build().toUriString());
                    beanPage.setActive(currentPage == i);
                    return beanPage;
                })
                .collect(Collectors.toList());
    }
}
