package by.tsvirko.music_shop.controller.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter to encode request and response.
 */
public class EncodingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * Sets "UTF-8" encoding to request and response.
     *
     * @param servletRequest  -  the ServletRequest object, contains the client's request
     * @param servletResponse - the ServletResponse object, contains the filter's response
     * @param filterChain     - the FilterChain for invoking the next filter or the resource
     * @throws IOException      -  if an I/O related error has occurred during the processing
     * @throws ServletException - if servlet exception occurs
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("UTF-8");
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        httpResponse.setCharacterEncoding("UTF-8");

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
