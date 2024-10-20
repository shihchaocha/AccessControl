package org.example.models;

public class FilterEntityDTO {

    private Long id;
    private int order;
    private String name;
    private String className;

    // Constructors, getters, and setters
    public FilterEntityDTO() {}

    public FilterEntityDTO(Long id, int order, String name, String className) {
        this.id = id;
        this.order = order;
        this.name = name;
        this.className = className;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
