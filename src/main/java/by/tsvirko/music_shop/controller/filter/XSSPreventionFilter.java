package by.tsvirko.music_shop.controller.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Filter to prevent XSS attack.
 *
 * @author Elizaveta Tsvirko
 */
public class XSSPreventionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * Wraps request with {@code XSSRequestWrapper} to prevent XSS attack.
     *
     * @param servletRequest  - the ServletRequest object, contains the client's request
     * @param servletResponse - the ServletResponse object, contains the filter's response
     * @param filterChain     - the FilterChain for invoking the next filter or the resource
     * @throws IOException      -  if an I/O related error has occurred
     * @throws ServletException - if operation exception occurs
     * @see XSSRequestWrapper
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(new XSSRequestWrapper((HttpServletRequest) servletRequest), servletResponse);
    }

    @Override
    public void destroy() {
    }
}
