package org.example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Main {
    public static void main(String[] args) {
// отдаём класс конфигурации
        final var context = new AnnotationConfigApplicationContext(JavaConfig.class);
        final var controller = context.getBean("postController");
        final var service = context.getBean(PostService.class);

    }

}