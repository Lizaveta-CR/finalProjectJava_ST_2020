package by.tsvirko.music_shop.controller.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter to turn off cashing.
 */
public class CashFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * Turns of response cashing to prevent changing cashing while developing.
     *
     * @param servletRequest  -  the ServletRequest object, contains the client's request
     * @param servletResponse - the ServletResponse object, contains the filter's response
     * @param filterChain     - the FilterChain for invoking the next filter or the resource
     * @throws IOException      -  if an I/O related error has occurred during the processing
     * @throws ServletException - if servlet exception occurs
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        //browser may cache a response, but must first submit a validation request to an origin server.
        httpResponse.setHeader("Cache-Control", "no-cache");//HTTP/1.1
        httpResponse.setHeader("Pragma", "no-cache");//HTTP/1.0
        httpResponse.setDateHeader("Expires", 0);

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
