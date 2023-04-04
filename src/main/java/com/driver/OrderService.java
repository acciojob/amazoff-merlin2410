package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public void addOrder(Order order)
    {
        orderRepository.addOrder(order);
    }

    public void addPartner(String partnerId)
    {
        orderRepository.addPartner(partnerId);
    }

    public void addOrderPartnerPair(String orderId, String partnerId)
    {
        orderRepository.addOrderPartnerPair(orderId,partnerId);
    }

    public Order getOrderById(String orderId)
    {
        Order order = orderRepository.getOrderById(orderId);
        return order;
    }

    public DeliveryPartner getPartnerById(String partnerId)
    {
        DeliveryPartner deliveryPartner = orderRepository.getPartnerById(partnerId);
        return deliveryPartner;
    }

    public Integer getOrderCountByPartnerId(String partnerId)
    {
        DeliveryPartner deliveryPartner = getPartnerById(partnerId);
        return deliveryPartner.getNumberOfOrders();
    }

    public List<String> getOrdersByPartnerId(String partnerId)
    {
        List<Order> orderList = orderRepository.getOrdersByPartnerId(partnerId);
        List<String> orderIdList = new ArrayList<>();
        for(Order order: orderList)
        {
            orderIdList.add(order.getId());
        }
        return orderIdList;
    }


    public List<String> getAllOrders()
    {
        List<Order> orderList = orderRepository.getAllOrders();
        List<String> allOrdersId = new ArrayList<>();
        for(Order order: orderList)
        {
            allOrdersId.add(order.getId());
        }
        return allOrdersId;
    }

    public Integer getCountOfUnassignedOrders()
    {
        List<Order> allOrders = orderRepository.getAllOrders();
        List<Order> assignedOrders = orderRepository.getAllAssignedOrders();
        return allOrders.size()-assignedOrders.size();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId)
    {
        Integer countOfOrders = 0;
        List<Order> orderList = orderRepository.getOrdersByPartnerId(partnerId);
        int deliveryTime = Integer.parseInt(time.substring(0,2))*60 + Integer.parseInt(time.substring(3,5));
        for(Order order: orderList)
        {
            if(deliveryTime<order.getDeliveryTime())
            {
                countOfOrders++;
            }
        }
        return countOfOrders;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId)
    {
        List<Order> orderList = orderRepository.getOrdersByPartnerId(partnerId);
        int maxTime = Integer.MIN_VALUE;
        for(Order order: orderList)
        {
            maxTime = Math.max(maxTime,order.getDeliveryTime());
        }
        String time = (maxTime/60)+":"+(maxTime%60);
        return time;
    }

    public void deletePartnerById(String partnerId)
    {
        orderRepository.deletePartnerById(partnerId);
    }

    public void deleteOrderById(String orderId)
    {
        orderRepository.deleteOrderById(orderId);
    }
}