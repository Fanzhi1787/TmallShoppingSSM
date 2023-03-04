package cn.wmyskxz.service;

import cn.wmyskxz.pojo.Property;

import java.util.List;

public interface PropertyService {

    void add(Property property);

    void delete(Integer id);

    void update(Property property);

    List<Property> list(Integer category_id);

    Property get(Integer id);
}
