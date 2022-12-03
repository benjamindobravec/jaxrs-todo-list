package com.bd993;

import java.util.ArrayList;
import java.util.List;

public class TodoItems {
  private static TodoItems mInstance;

  public static TodoItems getInstance() {
    if (null == mInstance)
      mInstance = new TodoItems();
    return mInstance;
  }
  
  private List<TodoItem> items = new ArrayList<>();
  private Object lock = new Object();
  
  private TodoItems() {
    add(new TodoItem(null, "Shop", "Buy apple"));
    add(new TodoItem(null, "Work", "Create TODO service"));
  }
  
  private String generateId() {
    int i = 0;
    String result = null;
    while (i < 10000) { 
      result = java.util.UUID.randomUUID().toString();
      boolean found = false;
      for (TodoItem item : items) {
        if (result.equals(item.id)) {
          found = true;
          break;
        }
      }
      if (!found)
        break;
      i++;
    }
    return result;
  }
  
  public void add(TodoItem item) {
    if (null == item)
      return;
    TodoItem copy = new TodoItem(item);
    synchronized (lock) {
      copy.id = generateId();
      items.add(copy);
    }
  }
  
  public List<TodoItem> getAll() {
    List<TodoItem> result = null;
    synchronized (lock) {
      result = new ArrayList<>(items.size());
      for (TodoItem item : items)
        result.add(new TodoItem(item));
    }
    return result;
  }
  
  public TodoItem get(String id) {
    if (null == id)
      return null;
    synchronized (lock) {
      for (TodoItem item : items)
        if (id.equals(item.id))
          return new TodoItem(item);
    }
    return null;
  }
  
  public TodoItem set(TodoItem newItem) {
    if (null == newItem || null == newItem.id)
      return null;
    synchronized (lock) {
      for (TodoItem item : items)
        if (newItem.id.equals(item.id)) {
          item.name = newItem.name;
          item.description = newItem.description;
          return new TodoItem(item);
        }
    }
    return null;
  }
  
  public void delete(String id) {
    if (null == id)
      return;
    synchronized (lock) {
      int index = -1;
      for (int i = 0; i < items.size(); i++)
        if (id.equals(items.get(i).id)) {
          index = i;
          break;
        }
      if (-1 < index)
        items.remove(index);
    }
  }
}
