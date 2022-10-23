package ru.fastfood.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.fastfood.model.Kitchen;
import ru.fastfood.model.Status;

public interface KitchenRepository extends CrudRepository<Kitchen, Integer> {

    @Modifying
    @Transactional()
    @Query("update Kitchen set status=?2 where orderId=?1")
    void setStatusByOrderId(int id, Status status);
}

