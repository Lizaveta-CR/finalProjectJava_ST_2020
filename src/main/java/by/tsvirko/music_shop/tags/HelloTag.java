package by.tsvirko.music_shop.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.Locale;

public class HelloTag extends TagSupport {
//    private Product product;

//    public void setProduct(Product product) {
//        this.product = product;
//    }

    @Override
    public int doStartTag() throws JspException {
//        String welcomeMessage = product.getModel() + " costs " + product.getPrice();
//        try {
//            pageContext.getOut().write("<div>" + welcomeMessage + "</div>");
//        } catch (IOException e) {
//            throw new JspException(e.getMessage());
//        }
//        return SKIP_BODY;
        GregorianCalendar gc = new GregorianCalendar();
        String time = "<hr/>Time : <b> " + gc.getTime() + " </b><hr/>";
        String locale = "Locale : <b> " + Locale.getDefault() + " </b><hr/> ";
        try {
            JspWriter out = pageContext.getOut();
            out.write(time + locale);
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
