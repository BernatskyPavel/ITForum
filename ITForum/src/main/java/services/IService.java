package services;

import java.util.List;

public interface IService<E> {
    public List<E> getAll();
    public E getById(Object Id);
    public E create(E entity);
    public E update(E entity);
    public void deleteById(Object Id);
}
