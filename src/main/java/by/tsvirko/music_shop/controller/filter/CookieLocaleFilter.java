package by.tsvirko.music_shop.controller.filter;

import by.tsvirko.music_shop.controller.constant.ParameterConstant;
import by.tsvirko.music_shop.controller.constant.ResourceBundleConstant;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Locale filter.
 * <p>
 * Stores language in cookie.
 * </p>
 */
public class CookieLocaleFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * Checks for language in cookie. Sets cookie with language to response.
     *
     * @param servletRequest  -  the ServletRequest object, contains the client's request
     * @param servletResponse - the ServletResponse object, contains the filter's response
     * @param filterChain     - the FilterChain for invoking the next filter or the resource
     * @throws IOException      -  if an I/O related error has occurred during the processing
     * @throws ServletException - if servlet exception occurs
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        String cookieLocale = req.getParameter(ParameterConstant.COOKIE_LOCALE.value());
        if (cookieLocale != null) {
            Cookie cookie = new Cookie(ResourceBundleConstant.LANGUAGE.value(), cookieLocale);
            res.addCookie(cookie);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
