package by.tsvirko.music_shop.tags;

import by.tsvirko.music_shop.service.Service;
import by.tsvirko.music_shop.util.ResourceBundleUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.ResourceBundle;

@SuppressWarnings("serial")
public class HelloTag extends TagSupport {
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int doStartTag() throws JspException {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        ResourceBundle rb = ResourceBundleUtil.getResourceBundle(request);
        String title = rb.getString("label.welcome");
        try {
            pageContext.getOut().write("<hr/>" + title + ", " + name + "<hr/>");
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}
