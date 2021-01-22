package by.tsvirko.music_shop.filter;

import by.tsvirko.music_shop.constant.ParameterConstant;
import by.tsvirko.music_shop.constant.ResourceBundleAttributes;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Locale filter
 */
public class CookieLocaleFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        String cookieLocale = req.getParameter(ParameterConstant.COOKIE_LOCALE.value());
        if (cookieLocale != null) {
            Cookie cookie = new Cookie(ResourceBundleAttributes.LANGUAGE.value(), cookieLocale);
            res.addCookie(cookie);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
