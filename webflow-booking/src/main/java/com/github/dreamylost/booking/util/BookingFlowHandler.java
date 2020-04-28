package com.github.dreamylost.booking.util;

import org.springframework.webflow.core.FlowException;
import org.springframework.webflow.execution.FlowExecutionOutcome;
import org.springframework.webflow.execution.repository.NoSuchFlowExecutionException;
import org.springframework.webflow.mvc.servlet.AbstractFlowHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 实现自己的流程处理，异常时匹配/hotels/search
 */
public class BookingFlowHandler extends AbstractFlowHandler {

    private static final String DEFAULT_URL = "/hotels/search";

    @Override
    public String handleExecutionOutcome(FlowExecutionOutcome outcome, HttpServletRequest req, HttpServletResponse res) {
        return DEFAULT_URL;
    }

    @Override
    public String handleException(FlowException e, HttpServletRequest request, HttpServletResponse response) {
        if (e instanceof NoSuchFlowExecutionException) {
            return DEFAULT_URL;
        } else {
            throw e;
        }
    }

}
