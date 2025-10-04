package org.example;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class PostRepositoryStubImpl implements PostRepository {
    private final Map<Long, Post> storage = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    /**
     * Возвращает все посты в репозитории.
     *
     * @return список всех постов
     */
    public List<Post> all() {
        return new ArrayList<>(storage.values());
    }

    /**
     * Находит пост по идентификатору.
     *
     * @param id идентификатор поста
     * @return Optional с постом, если найден
     */
    public Optional<Post> getById(long id) {
        return Optional.ofNullable(storage.get(id));
    }

    /**
     * Сохраняет пост. Если у поста нет ID, генерирует новый.
     *
     * @param post пост для сохранения
     * @return сохраненный пост с ID
     */
    public Post save(Post post) {
        if (post.getId() == 0) {
            long newId = idCounter.getAndIncrement();
            Post newPost = new Post(newId, post.getContent());
            storage.put(newId, newPost);
            return newPost;
        } else {
            storage.put(post.getId(), post);
            return post;
        }
    }

    /**
     * Удаляет пост по идентификатору.
     *
     * @param id идентификатор поста для удаления
     * @throws NoSuchElementException если пост не найден
     */
    public void removeById(long id) {
        if (storage.remove(id) == null) {
            throw new NoSuchElementException("Post with id" + id + "not found");
        }
    }
}