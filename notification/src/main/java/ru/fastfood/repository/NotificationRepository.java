package ru.fastfood.repository;

import org.springframework.data.repository.CrudRepository;
import ru.fastfood.model.Notification;

public interface NotificationRepository extends CrudRepository<Notification, Integer> {

}
