package com.znylle.observers;

@SuppressWarnings("rawtypes")
public interface Observer<T extends Observable> {

	void update(T obj);

}
