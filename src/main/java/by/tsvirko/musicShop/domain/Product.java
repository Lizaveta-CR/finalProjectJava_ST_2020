package by.tsvirko.musicShop.domain;

import java.math.BigDecimal;

/**
 * ProductItem class
 */
public class Product extends Entity {
    private String type;
    private Integer categoryNum;
    private String model;
    private Boolean available;
    private String description;
    private String image_url;
    private BigDecimal price;

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCategoryNum() {
        return categoryNum;
    }

    public void setCategoryNum(Integer categoryNum) {
        this.categoryNum = categoryNum;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + type + '\'' +
                ", categoryNum=" + categoryNum +
                ", model='" + model + '\'' +
                ", available=" + available +
                ", description='" + description + '\'' +
                ", image_url='" + image_url + '\'' +
                ", price=" + price +
                '}';
    }
}
