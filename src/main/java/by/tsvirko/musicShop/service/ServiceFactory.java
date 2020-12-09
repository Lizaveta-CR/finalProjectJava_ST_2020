package by.tsvirko.musicShop.service;


public interface ServiceFactory {
    <Type extends Service> Type getService(Class<Type> key);

    void close();
}
