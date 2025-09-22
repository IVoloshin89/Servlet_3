package org.example.controller;


import com.google.gson.Gson;
import org.example.model.Post;
import org.example.service.PostService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

public class PostController {
    //указание типа контента JSON
    public static final String APPLICATION_JSON = "application/json";
    private final PostService service;

    /**
     * Конструктор контроллера
     *
     * @param service - сервис для работы с постами
     */
    public PostController(PostService service) {
        this.service = service;
    }

    /**
     * Обработчик запроса на получение всех постов
     * GET /api/posts
     *
     * @param response - HTTP ответ
     * throws IOException - при ошибках ввода-вывода
     */
    public void all(HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var data = service.all();
        final var gson = new Gson();
        response.getWriter().print(gson.toJson(data));
        response.setStatus(HttpServletResponse.SC_OK);
        System.out.println("[INFO: controller all]");
    }

    /**
     * Обработчик запроса на получение поста по id
     * GET /api/posts
     *
     * @param id - идентификатор поста для возврата
     * @param response - HTTP ответ
     * throws IOException - при ошибках ввода-вывода
     */
    public void getById(long id, HttpServletResponse response) throws IOException {

        Post post = service.getById(id);
        if(post == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().print("Post not Found");
            response.setContentType(APPLICATION_JSON);
            return;            
        }
        response.setContentType(APPLICATION_JSON);
        final var data = service.getById(id);
        final var gson = new Gson();
        response.getWriter().print(gson.toJson(data));
        response.setStatus(HttpServletResponse.SC_OK);
        System.out.println("[INFO: controller all]");
    }

    public void save(Reader body, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var gson = new Gson();
        final var post = gson.fromJson(body, Post.class);
        final var data = service.save(post);
        response.getWriter().print(gson.toJson(data));
        response.setStatus(HttpServletResponse.SC_CREATED);
        System.out.println("[INFO: controller save]");
    }
    /**
     * Обработчик запроса на удаление поста по ID
     * DELETE /api/posts/{id}
     * @param id - идентификатор поста для удаления
     * @param response - HTTP ответ
     * @throws IOException - при ошибках ввода-вывода
     */
    public void removeById(long id, HttpServletResponse response) {
        response.setContentType(APPLICATION_JSON);
        service.removeById(id);
        System.out.println("[INFO: controller remove by ID]");
    }
}
