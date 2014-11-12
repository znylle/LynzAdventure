package com.znylle.observers;

public interface Observable<T extends Observable<T>> {

	public void removeObserver(Observer<T> obj);

	public void notifyObservers(T obj);

	public void registerObserver(Observer<T> obj);

}
