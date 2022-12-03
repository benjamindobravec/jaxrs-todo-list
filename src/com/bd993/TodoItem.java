package com.bd993;

public class TodoItem {
  public String id;
  public String name;
  public String description;
  
  public TodoItem() {  
  }
  
  public TodoItem(TodoItem copy) {
    id = copy.id;
    name = copy.name;
    description = copy.description;
  }
  
  public TodoItem(String id, String name, String description) {
    this.id = id;
    this.name = name;
    this.description = description;
  }
}
