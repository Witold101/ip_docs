package by.vistar.services.interfaces;

public interface DaoServiceImpl<Key, Entity> {
    Entity add(Entity entity);
    void dell(Key id);
    Entity edit(Entity entity);
    Entity get(Key id);
}
