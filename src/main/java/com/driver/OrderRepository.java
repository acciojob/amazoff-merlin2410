package com.driver;



import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class OrderRepository {

    HashMap<String,Order> orderDb = new HashMap<>();
    HashMap<String,DeliveryPartner> deliveryPartnerDb = new HashMap<>();
    HashMap<Order,DeliveryPartner> orderDeliveryPartnerDb = new HashMap<>();


    public void addOrder(Order order)
    {
        String id = order.getId();
        orderDb.put(id,order);
    }

    public void addPartner(String partnerId)
    {
        DeliveryPartner deliveryPartner = new DeliveryPartner(partnerId);
        deliveryPartnerDb.put(partnerId,deliveryPartner);

    }

    public void addOrderPartnerPair(String orderId, String partnerId)
    {
        if(orderDb.containsKey(orderId) && deliveryPartnerDb.containsKey(partnerId))
        {
            Order order = orderDb.get(orderId);
            DeliveryPartner deliveryPartner = deliveryPartnerDb.get(partnerId);
            orderDeliveryPartnerDb.put(order,deliveryPartner);
            deliveryPartner.setNumberOfOrders(deliveryPartner.getNumberOfOrders()+1);
        }

    }

    public Order getOrderById(String orderId)
    {
        Order order = orderDb.get(orderId);
        return order;
    }

    public DeliveryPartner getPartnerById(String partnerId)
    {
        DeliveryPartner deliveryPartner = deliveryPartnerDb.get(partnerId);
        return deliveryPartner;
    }

    public List<Order> getOrdersByPartnerId(String partnerId)
    {
        List<Order> orderList = new ArrayList<>();
        for(Order order: orderDeliveryPartnerDb.keySet())
        {
            DeliveryPartner deliveryPartner = orderDeliveryPartnerDb.get(order);
            if(deliveryPartner.getId().equals(partnerId))
            {
                orderList.add(order);
            }
        }
        return orderList;
    }

    public List<Order> getAllOrders()
    {
        List<Order> orderList = new ArrayList<>();
        for(Order order: orderDb.values())
        {
            orderList.add(order);
        }
        return orderList;
    }

    public List<Order> getAllAssignedOrders()
    {
        List<Order> orderList = new ArrayList<>();
        for(Order order: orderDeliveryPartnerDb.keySet())
        {
            orderList.add(order);
        }
        return orderList;
    }

    public void deletePartnerById(String partnerId)
    {
        List<Order> orderList = getOrdersByPartnerId(partnerId);
        for(Order order: orderList)
        {
            orderDeliveryPartnerDb.remove(order);
        }
        deliveryPartnerDb.remove(partnerId);
    }

    public void deleteOrderById(String orderId)
    {
        Order order = orderDb.get(orderId);
        orderDeliveryPartnerDb.remove(order);
        orderDb.remove(orderId);
    }


}
