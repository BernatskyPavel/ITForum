package services;

import java.util.List;

public interface IService<E> {
    public List<E> getAll();
    public E getById(Object Id);
    public void create(E entity);
    public void update(E entity);
    public void deleteById(Object Id);
}
