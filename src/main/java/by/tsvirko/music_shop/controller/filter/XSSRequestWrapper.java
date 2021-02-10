package by.tsvirko.music_shop.controller.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.regex.Pattern;

/**
 * XSS wrapper.
 * <p>
 * Encodes values to prevent Cross Site Scripting attack.
 *
 * @author Elizaveta Tsvirko
 * @version 1.0
 */
public class XSSRequestWrapper extends HttpServletRequestWrapper {
    /**
     * Compiles regex for possible attacks.
     */
    private static Pattern[] patterns = new Pattern[]{
            // Avoid anything between script tags
            Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE),
            // Avoid anything in a src='...' type of expression
            Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            // Remove any lonesome <script ...> tag
            Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            // Avoid eval(...) expressions
            Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            // Avoid expression(...) expressions
            Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            // Avoid javascript:... expressions
            Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE),
            // Avoid vbscript:... expressions
            Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE),
            // Avoid onload= expressions
            Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL)
    };

    /**
     * Class constructor. Creates new {@code XSSRequestWrapper}.
     *
     * @param request - http request
     * @see HttpServletRequest
     */
    public XSSRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    /**
     * Gets parameter values and  encodes their value.
     *
     * @param name - parameter name
     * @return encoded values or {@code null} if parameter values were null
     */
    @Override
    public String[] getParameterValues(String name) {
        String[] parameterValues = super.getParameterValues(name);
        if (parameterValues == null) {
            return null;
        }
        int length = parameterValues.length;
        String[] encodedValues = new String[length];
        for (int i = 0; i < length; i++) {
            encodedValues[i] = unscrewXSSAttack(parameterValues[i]);
        }
        return encodedValues;
    }

    /**
     * Gets parameter encodes it.
     *
     * @param parameter name
     * @return encoded parameter
     */
    @Override
    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        return unscrewXSSAttack(value);
    }

    /**
     * Gets header and encodes it.
     *
     * @param name - header name
     * @return encoded header
     * @see HttpServletRequestWrapper
     */
    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        return unscrewXSSAttack(value);
    }

    /**
     * Remove all sections that match a pattern with possible attack.
     *
     * @param value - value to check
     * @return correct and encoded value
     */
    private String unscrewXSSAttack(String value) {
        if (value != null) {
            // Avoid null characters
            value = value.replaceAll("\0", "");

            // Remove all sections that match a pattern
            for (Pattern scriptPattern : patterns) {
                value = scriptPattern.matcher(value).replaceAll("");
            }
        }
        return value;
    }
}
