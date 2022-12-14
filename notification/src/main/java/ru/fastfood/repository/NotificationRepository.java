package ru.fastfood.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.fastfood.model.Notification;
import ru.fastfood.model.Status;

public interface NotificationRepository extends CrudRepository<Notification, Integer> {

    @Modifying
    @Transactional()
    @Query("update Notification set status=?2 where itemIdFromService=?1")
    void setStatusByItemIdFromService(int id, Status status);
}
