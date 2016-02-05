package ru.atott.combiq.web.controller.admin;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.atott.combiq.service.bean.Event;
import ru.atott.combiq.service.site.EventService;
import ru.atott.combiq.web.bean.PagingBean;
import ru.atott.combiq.web.bean.PagingBeanBuilder;
import ru.atott.combiq.web.controller.BaseController;

import javax.servlet.http.HttpServletRequest;

@Controller
public class EventAdminController extends BaseController {

    private PagingBeanBuilder pagingBeanBuilder = new PagingBeanBuilder();

    @Autowired
    private EventService eventService;

    @RequestMapping(value = "/admin/events", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('sa')")
    public ModelAndView list(
            HttpServletRequest httpServletRequest,
            @RequestParam(defaultValue = "1") int page) {
        page = getZeroBasedPage(page);

        Page<Event> events = eventService.getEvents(page, 20);
        PagingBean paging = pagingBeanBuilder.build(events, page, httpServletRequest);

        ModelAndView modelAndView = new ModelAndView("admin/events");
        modelAndView.addObject("paging", paging);
        modelAndView.addObject("events", Lists.newArrayList(events.getContent()));
        return modelAndView;
    }

}
