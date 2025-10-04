package org.example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
    //контроллер, который будет управлять запросами
    private PostController controller;

    @Override
    public void init() {

        final var context = new AnnotationConfigApplicationContext("org.example");
        final var controller = context.getBean("postController");
        final var service = context.getBean("postService");
        //final var service = new PostService(repository); //создаем сервис
        //controller = new PostController(service); //создаем контроллер
    }

    @Override
    //обработка http запросов, определение маршрута и метода
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        try {
            final var path = req.getRequestURI();
            final var method = req.getMethod();

            // обработка маршрута метода
            if (method.equals("GET") && path.equals("/api/posts")) {
                controller.all(resp);
                System.out.println("[INFO: catch GET]");
                return;
            }
            if (method.equals("GET") && path.matches("/api/posts/\\d+")) {
                // easy way
                final var id = Long.parseLong(path.substring(path.lastIndexOf("/")+1));
                controller.getById(id, resp);
                System.out.println("[INFO: catch GET by ID]");
                return;
            }
            if (method.equals("POST") && path.equals("/api/posts")) {
                controller.save(req.getReader(), resp);
                System.out.println("[INFO: catch GET by ID]");
                return;
            }
            if (method.equals("DELETE") && path.matches("/api/posts/\\d+")) {
                // easy way
                final var id = Long.parseLong(path.substring(path.lastIndexOf("/")+1));
                controller.removeById(id, resp);
                System.out.println("[INFO: catch Delete]");
                return;
            }

            //если не удалось найти подходящий маршрут, возвращаем 404
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND); // возвращаем 404
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); //Возвращаем ошибку сервера 505
        }
    }
}
