package ru.fastfood.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.fastfood.model.Order;
import ru.fastfood.model.OrderStatus;

public interface OrderRepository extends CrudRepository<Order, Integer> {

    @Modifying
    @Transactional()
    @Query("update Order set status=?2 where id=?1")
    void setStatusOrder(int id, OrderStatus status);
}
